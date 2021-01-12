/* server.c */
#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netinet/in.h>

#define MAXOPEN 5
#define BUFSIZE 1024

int main(int argc, char *argv[]) {
  
  int listenfd, connfd;
  FILE *fp; // pointer to file to serve
  struct sockaddr_in server;
  
  // check number of args
  if (argc != 3) {  // !!! corrected argc
    puts("Usage: server <port> <file>");
    return 1;
  }
  
  // open the file for reading
  if ((fp=fopen(argv[2],"rb")) == NULL) {
    perror("Cannot find file to serve");
    return 2;
  }
  
  // initialize the server address
  memset(&server,0,sizeof(server));
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = htonl(INADDR_ANY);
  server.sin_port = htons(atoi(argv[1]));
  
  // create a socket for ipv4 address, 2 way byte stream, TCP protocol
  if ((listenfd = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0) {
    perror("Cannot create server socket");
    return 3;
  }
  
  // bind the server address to the socket
  if (bind(listenfd, (struct sockaddr *) &server, sizeof(server)) < 0) {
    perror("Cannot open the interface");
    return 4;
  }
  
  // mark the socket as accepting connections
  if (listen(listenfd,MAXOPEN) < 0) {
    perror("Cannot listen on the interface");
    return 5;
  }
  
  for(;;) {
    // wait for client connection
    if ( (connfd = accept(listenfd, (struct sockaddr *) NULL, NULL)) < 0 ) {
      perror("Error accepting a client connection");
      return 6;
    }
    
    while(!feof(fp)) {
      char bytes[BUFSIZE];
      int r,w=0;
      
      // read bytes from file into buffer
      r = fread(bytes,sizeof(char),BUFSIZE,fp);
      
      // write the buffer to the connection socket
      while(w<r) {
        int total = write(connfd,bytes,r);
        if (total < 0) {
          perror("Error writing data to client");
          return 7;
        }
        w+=total;
      }
    }
    
    // seek back to the start of the file... for some reason?
    fseek(fp,0,SEEK_SET);
    
    close(connfd);
    
    return 0;
  } // !!! corrected for loop closure
}