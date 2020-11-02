#include <stdio.h>
#include <stdlib.h>
#include "list.h"

List *cons(int head, List *tail) { 
  /* malloc() will be explained in the next lecture! */
  List *cell = malloc(sizeof(List));
  cell->head = head;
  cell->tail = tail;
  return cell;
}

/* Functions for you to implement */

int sum(List *list) {
  int result = 0;
  while (list != NULL)
  {
    result += list->head;
    list = list->tail;
  }
  return result;
}

void iterate(int (*f)(int), List *list) {
  while (list != NULL)
  {
    list->head = f(list->head);
    list = list->tail;
  }
  return;
}

void print_list(List *list) {
  printf("[");
  if (list != NULL)
  {
    printf("%d", list->head);
    list = list->tail;
  }
  while (list != NULL)
  {
    printf(", %d", list->head);
    list = list->tail;
  }
  printf("]\n");
}

/**** CHALLENGE PROBLEMS ****/

List *merge(List *list1, List *list2) {
  List *result;
  List *current;
  if (list1->head <= list2->head)
  {
    result = list1;
    list1 = list1->tail;
  }
  else
  {
    result = list2;
    list2 = list2->tail;
  }
  current = result;
  while (list2 != NULL)
  {
    if (list1 == NULL)
    {
      current->tail = list2;
      return result;
    }
    else if (list1->head <= list2->head)
    {
      current->tail = list1;
      list1 = list1->tail;
      current = current->tail;
    }
    else
    {
      current->tail = list2;
      list2 = list2->tail;
      current = current->tail;
    }
  }
  current->tail = list1;
  return result;
}

void split(List *list, List **list1, List **list2) {
  List *l1, *l2;
  if (list != NULL)
  {
    *list1 = list;
    l1 = *list1;
    list = list->tail;
    l1->tail = NULL;
  }
  else {
    *list1 = NULL;
  }
  if (list != NULL)
  {
    *list2 = list;
    l2 = *list2;
    list = list->tail;
    l2->tail = NULL;
  }
  else
  {
    *list2 = NULL;
  }
  
  int targetList = 0;
  while (list != NULL)
  {
    if (targetList == 0)
    {
      l1->tail = list;
      l1 = l1->tail;
    }
    else
    {
      l2->tail = list;
      l2 = l2->tail;
    }
    list = list->tail;
    targetList = 1 - targetList;
  }
  l1->tail = NULL;
  l2->tail = NULL;
}

/* You get the mergesort implementation for free. But it won't
   work unless you implement merge() and split() first! */

List *mergesort(List *list) { 
  if (list == NULL || list->tail == NULL) { 
    return list;
  } else { 
    List *list1;
    List *list2;
    split(list, &list1, &list2);
    list1 = mergesort(list1);
    list2 = mergesort(list2);
    return merge(list1, list2);
  }
}
