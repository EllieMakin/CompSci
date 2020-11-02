#include <ctype.h>
#include <string.h>
#include "revwords.h"

void reverse_substring(char str[], int start, int end) {
  int length = end - start;
  for (int j = 0; j < length/2; j++)
  {
    char temp_char = str[start+j];
    str[start+j] = str[end-j-1];
    str[end-j-1] = temp_char;
  }
}


int find_next_start(char str[], int len, int i) { 
  while (i < len) {
    if (
      ('a' <= str[i] && str[i] <= 'z') ||
      ('A' <= str[i] && str[i] <= 'Z')
    ) {
      return i;
    }
    
    i++;
  }
  return -1;
}

int find_next_end(char str[], int len, int i) {
  while (i < len)
  {
    if (
      (str[i] < 'a' || 'z' < str[i]) &&
      (str[i] < 'A' || 'Z' < str[i])
    ) {
      return i;
    }
    i++;
  }
  return len;
}

void reverse_words(char s[]) {
  int len = strlen(s);
  int start = find_next_start(s, len, 0);
  int end;
  while (start != -1)
  {
    end = find_next_end(s, len, start);
    reverse_substring(s, start, end);
    start = find_next_start(s, len, end+1);
  }
  return;
}
