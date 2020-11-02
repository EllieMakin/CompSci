#include <stdlib.h>
#include <stdio.h>
#include "tree.h"

static Tree *empty = NULL;

/* BASE EXERCISE */

int tree_member(int x, Tree *tree) { 
  if (tree == empty)
  {
    return 0;
  }
  else if (x == tree->value)
  {
    return 1;
  }
  else
  {
    return tree_member(x, (x < tree->value ? tree->left : tree->right));
  }
}

Tree *tree_insert(int x, Tree *tree) { 
  if (tree == empty) {
    tree = malloc(sizeof *tree);
    tree->value = x;
  }
  else if (x < tree->value)
  {
    tree->left = tree_insert(x, tree->left);
  }
  else if (tree->value < x)
  {
    tree->right = tree_insert(x, tree->right);
  }
  return tree;
}

void tree_free(Tree *tree) { 
  if (tree != empty)
  {
    tree_free(tree->left);
    tree_free(tree->right);
    free(tree);
  }
}

/* CHALLENGE EXERCISE */ 

void pop_minimum(Tree *tree, int *min, Tree **new_tree) {
  if (tree == empty) {
    *new_tree = tree;
    return;
  }
  
  if (tree->left == empty)
  {
    *min = tree->value;
    Tree* temp = tree->right;
    free(tree);
    *new_tree = temp;
  }
  else {
    pop_minimum(tree->left, min, &tree->left);
  }
}

Tree *tree_remove(int x, Tree *tree) { 
  if (tree == empty)
  {
    return tree;
  }
  else if (x < tree->value)
  {
    tree->left = tree_remove(x, tree->left);
    return tree;
  }
  else if (x > tree->value)
  {
    tree->right = tree_remove(x, tree->right);
    return tree;
  }
  else
  {
    if (tree->right == empty)
    {
      return tree->left;
    }
    else
    {
      pop_minimum(tree->right, &tree->value, &tree->right);
      return tree;
    }
  }
}
