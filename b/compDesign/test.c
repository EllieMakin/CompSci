#include <stdio.h>

void print_array(int* a, int length)
{
    printf("[");
    for (int j = 0; j < length; j++)
    {
        if (j == 0)
            printf("%d", a[j]);
        else
            printf(", %d", a[j]);
    }
    printf("]\n");
}

int main(int argc, char* argv[]) {
    int A[] = {7,6,5,4,3,2,1,0};
    int B[] = {0,0,0,0,0,0,0,0};
    int f = 3;
    int g = 5;
    
    B[g] = A[f+1] + A[f=A[f]];
    
    print_array(A, 8);
    print_array(B, 8);
    printf("%d, %d\n", f, g);
    return 0;
}
