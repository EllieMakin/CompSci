#include <stdlib.h>
#include <stdio.h>
#include "re.h"

arena_t create_arena(int size) {
  arena_t arena = malloc(sizeof *arena);
  arena->size = size;
  arena->current = 0;
  arena->elts = malloc(size * sizeof(Regexp));
  return arena;
}

void arena_free(arena_t a) {
  free(a->elts);
  free(a);
}

Regexp *re_alloc(arena_t a) {
  if (a->current < a->size)
    return a->elts + a->current++;
  else
    return NULL;
}

Regexp *re_chr(arena_t a, char c) {
  Regexp *new_re = re_alloc(a);
  if (new_re != NULL)
  {
    new_re->type = CHR;
    new_re->data.chr = c;
    return new_re;
  }
  return NULL;
}

Regexp *re_alt(arena_t a, Regexp *r1, Regexp *r2) {
  Regexp *new_re = re_alloc(a);
  if (new_re != NULL)
  {
    new_re->type = ALT;
    new_re->data.pair.fst = r1;
    new_re->data.pair.snd = r2;
    return new_re;
  }
  return NULL;
}

Regexp *re_seq(arena_t a, Regexp *r1, Regexp *r2) {
  Regexp *new_re = re_alloc(a);
  if (new_re != NULL)
  {
    new_re->type = SEQ;
    new_re->data.pair.fst = r1;
    new_re->data.pair.snd = r2;
    return new_re;
  }
  return NULL;
}

int re_match(Regexp *r, char *s, int i) {
  if (r != NULL)
  {
    switch (r->type)
    {
      case CHR:
      {
        if (s[i] == r->data.chr)
          return i+1;
        else
          return -1;
      }
      case ALT:
      {
        int match1 = re_match(r->data.pair.fst, s, i);
        int match2 = re_match(r->data.pair.snd, s, i);
        return (match1 <= match2 ? match2 : match1);
      }
      case SEQ:
      {
        if (s[i] == '\0')
          return -1;
        int match1 = re_match(r->data.pair.fst, s, i);
        if (match1 > -1)
        {
          if (s[match1] == '\0')
              return -1;
              
          int match2 = re_match(r->data.pair.snd, s, match1);
          if (match2 > -1)
            return match2;
          else
            return match1;
        }
        else
          return -1;
      }
    }
  }
  return -1;
}



void re_print(Regexp *r) {
  if (r != NULL) {
    switch (r->type) {
    case CHR:
      printf("%c", r->data.chr);
      break;
    case SEQ:
      if (r->data.pair.fst->type == ALT) {
        printf("(");
        re_print(r->data.pair.fst);
        printf(")");
      } else {
        re_print(r->data.pair.fst);
      }
      if (r->data.pair.snd->type == ALT) {
        printf("(");
        re_print(r->data.pair.snd);
        printf(")");
      } else {
        re_print(r->data.pair.snd);
      }
      break;
    case ALT:
      re_print(r->data.pair.fst);
      printf("+");
      re_print(r->data.pair.snd);
      break;
    }
  } else {
    printf("NULL");
  }
}
