#pragma once

#include <netinet/ip.h>
#include <stdio.h>

# ifdef __cplusplus
extern "C"
{
# endif

struct tcphdr
  {
    uint16_t sport;
    uint16_t dport;
    uint32_t seq;
    uint32_t ack;
    uint8_t x2:4,
            off:4;
    uint8_t flags;
    uint16_t win;
    uint16_t sum;
    uint16_t urp;
};

struct concathdr {
  struct iphdr ip;
  struct tcphdr tcp;
};

void read_headers (struct concathdr *phdr, FILE *fp);

# ifdef __cplusplus
}
# endif
