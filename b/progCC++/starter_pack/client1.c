/* client.c */
#include <stdio.h>      // perror, fprintf
#include <sys/socket.h> // socket
#include <arpa/inet.h>  
#include <stdlib.h>
#include <string.h>     // memset
#include <unistd.h>
#include <netinet/in.h> // sockaddr_in

#define BUFSIZE 512

int main(int argc, char *argv[]) {
  
  int sockfd; // file descriptor for the socket connection
  struct sockaddr_in servaddr;  // server address to connect to
  
  // check number of args
  if (argc != 3) {
    puts("Usage: client <host> <port>");  // no longer prints "Success"
    return 1;
  }
  
  // create a socket for ipv4 address, two way byte stream, TCP protocol
  if ((sockfd = socket(AF_INET,SOCK_STREAM,IPPROTO_TCP)) < 0) {
    perror("Cannot create socket");
    return 2;
  }
  
  // initialize the server address by ip address and port number
  memset(&servaddr,0,sizeof(servaddr));
  servaddr.sin_family = AF_INET;  // specifies internet address family
  servaddr.sin_addr.s_addr = inet_addr(argv[1]);
  servaddr.sin_port = htons(atoi(argv[2]));
  // hton[s/l] converts from host to network byte order
  
  // connect the socket
  if (connect(sockfd,(struct sockaddr *) &servaddr,sizeof(servaddr)) < 0) {
    perror("Cannot connect to server");
    return 3;
  }
  
  {
    int n;
    char bytes[BUFSIZE];  // !!! corrected buffer size
    while((n = read(sockfd,bytes,BUFSIZE)) > 0) {
      fwrite(bytes,n,sizeof(char),stdout);
      fflush(stdout);
    }
  }
  
  return 1;
}
