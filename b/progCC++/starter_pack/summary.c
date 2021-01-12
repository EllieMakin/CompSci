#include "pcolparse.h"
#include <stdio.h>
#include <stdint.h>
#include <arpa/inet.h>  // inet_ntop

int main (int argc, char *argv[]) {
  
  FILE *fp;
  struct concathdr hdr;
  
  // strings to store ip addresses in readable format
  char src_addr[16];
  char dest_addr[16];
   
  if (argc != 2) {
    puts("Usage: summary <log file>");
    return 1;
  }
  
  if ((fp=fopen(argv[1],"rb")) == 0) {
    perror("Cannot find log file");
    return 2;
  }
  
  int n_packet = 0;
  while (!feof(fp)) {
    read_headers(&hdr, fp);
    if (feof(fp))
      break;
    
    // ihl refers to 32 bit words, so multiply by 4
    int seek_len = hdr.ip.tot_len - 4*hdr.ip.ihl - 20;
    
    if (n_packet == 0) {
      inet_ntop(AF_INET, &hdr.ip.saddr, src_addr, 16);
      inet_ntop(AF_INET, &hdr.ip.daddr, dest_addr, 16);
      
      printf(
        "%s %s %d %d %d ",
        src_addr,
        dest_addr,
        hdr.ip.ihl,
        hdr.ip.tot_len,
        hdr.tcp.off
      );
    }
    fseek(fp, seek_len, SEEK_CUR);
    n_packet++;
  }
  printf("%d\n", n_packet);
  return 0;
}
