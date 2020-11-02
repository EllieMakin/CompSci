#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include "graph.h"

Node *empty = NULL;

Node *node(int value, Node *left, Node *right) { 
  Node *r = malloc(sizeof(Node));
  r->marked = false;
  r->value = value;
  r->left = left;
  r->right = right;
  return r;
}


/* Basic Problems */

int size(Node *node) { 
  if (node == empty)
    return 0;
  else if (node->marked == false)
  {
    node->marked = true;
    return size(node->left) + size(node->right) + 1;
  }
  else
    return 0;
}


void unmark(Node *node) { 
  if (node != empty)
  {
    if (node->marked)
    {
      node->marked = false;
      unmark(node->left);
      unmark(node->right);
    }
  }
}

bool path_from(Node *node1, Node *node2) {
  if (node1 == empty || node2 == empty)
    return false;
  else if (node1 == node2)
    return true;
  else
    return path_from(node1->left, node2) || path_from(node1->right, node2);
}

bool cyclic(Node *node) { 
  if (node == empty)
    return false;
  else
    return path_from(node->left, node) || path_from(node->right, node);
} 


/* Challenge problems */

void get_nodes(Node *node, Node **dest) { 
  if (node != empty)
  {
    if (node->marked == false)
    {
      node->marked = true;
      for (int offset = 0; ; offset++)
        if (dest[offset] == NULL)
        {
          dest[offset] = node;
          break;
        }
      
      get_nodes(node->left, dest);
      get_nodes(node->right, dest);
    }
  }
}

void graph_free(Node *node) { 
  int nNodes = size(node);
  unmark(node);
  
  Node **nodeArray = calloc(nNodes, sizeof(Node *));
  get_nodes(node, nodeArray);
  
  for (int jNode = 0; jNode < nNodes; jNode++)
  {
    free(nodeArray[jNode]);
  }
  free(nodeArray);
}
