#include "pcolparse.h"
#include <iostream>
#include <cstdio>

int main(int argc, char const *argv[]) {
  
  FILE *fp_log;
  FILE *fp_target;
  struct concathdr hdr;
  
  if (argc != 3) {
    std::cerr << "Usage: extract <log file> <output>" << std::endl;
    return 1;
  }
  
  if ((fp_log=fopen(argv[1],"rb")) == 0) {
    perror("Cannot find log file");
    return 2;
  }
  if ((fp_target=fopen(argv[2],"w")) == 0) {
    perror("Cannot write to target file");
    fclose(fp_log);
    return 3;
  }
  
  while (!feof(fp_log)) {
    read_headers(&hdr, fp_log);
    if (feof(fp_log))
      break;
      
    fseek(fp_log, 4*hdr.tcp.off - 20, SEEK_CUR);
    if (feof(fp_log))
      break;
      
    int content_len = hdr.ip.tot_len - 4*hdr.ip.ihl - 4*hdr.tcp.off;
    for (int jByte = 0; jByte < content_len; jByte++) {
      fputc(fgetc(fp_log), fp_target);
    }
  }
  
  fclose(fp_log);
  fclose(fp_target);
  
  return 0;
}
