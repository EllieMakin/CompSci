#include "matrix.h"
#include <stdbool.h>

matrix_t matrix_create(int rows, int cols) { 
  matrix_t m = {
    rows,
    cols,
    calloc(rows * cols, sizeof(double))
  };
  return m;
}

double matrix_get(matrix_t m, int r, int c) { 
  assert(r < m.rows && c < m.cols);
  return m.elts[r*m.cols + c];
}

void matrix_set(matrix_t m, int r, int c, double d) { 
  assert(r < m.rows && c < m.cols);
  m.elts[r*m.cols + c] = d;
}


void matrix_free(matrix_t m) { 
  free(m.elts);
}

matrix_t matrix_multiply(matrix_t m1, matrix_t m2) { 
  assert(m1.cols == m2.rows);
  matrix_t m = matrix_create(m1.rows, m2.cols);
  for (int jRow = 0; jRow < m1.rows; jRow++) {
    for (int jCol = 0; jCol < m2.cols; jCol++) {
      double sum = 0.0;
      for (int k = 0; k < m1.cols; k++)
        sum += matrix_get(m1, jRow, k) * matrix_get(m2, k, jCol);
      
      matrix_set(m, jRow, jCol, sum);
    }
  }
  return m;
}

matrix_t matrix_transpose(matrix_t m) { 
  matrix_t t = matrix_create(m.cols, m.rows);
  for (int jRow = 0; jRow < m.rows; jRow++)
    for (int jCol = 0; jCol < m.cols; jCol++)
      matrix_set(t, jCol, jRow, matrix_get(m, jRow, jCol));
  return t;
}

matrix_t matrix_multiply_transposed(matrix_t m1, matrix_t m2) { 
  assert(m1.cols == m2.cols);
  matrix_t m = matrix_create(m1.rows, m2.rows);
  for (int jRow = 0; jRow < m1.rows; jRow++) {
    for (int jCol = 0; jCol < m2.rows; jCol++) {
      double sum = 0.0;
      for (int k = 0; k < m1.cols; k++)
        sum += matrix_get(m1, jRow, k) * matrix_get(m2, jCol, k);
      
      matrix_set(m, jRow, jCol, sum);
    }
  }
  
  return m;
}

matrix_t matrix_multiply_fast(matrix_t m1, matrix_t m2) { 
  matrix_t m2t = matrix_transpose(m2);
  matrix_t result = matrix_multiply_transposed(m1, m2t);
  matrix_free(m2t);
  return result;
}

void matrix_print(matrix_t m) { 
  for (int i = 0; i < m.rows; i++) { 
    for (int j = 0; j < m.cols; j++) { 
      printf("%g\t", matrix_get(m, i, j));
    }
    printf("\n");
  }
  printf("\n");
}
