#include <netinet/ip.h>
#include <stdio.h>
#include "pcolparse.h"

void read_headers (struct concathdr *phdr, FILE *fp) {
  fread(phdr, sizeof(struct concathdr), 1, fp);
  phdr->ip.tot_len = htons(phdr->ip.tot_len);
}
