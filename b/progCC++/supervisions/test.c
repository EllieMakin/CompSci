#include <stdio.h>
#include <inttypes.h>

typedef struct s {
    union {
        int32_t i;
        float f;
    } data32;
    union {
        int64_t i;
        double d;
    } data64;
} S;

int main()
{
    S s_int = { 204648531, 18436744072709551614 };
    printf("f: %f, d: %f\n", s_int.data32.f, s_int.data64.d);
    
    S s_float = { 1122.340163525, 44555666777888.9990121 };
    printf("i32: %d, i64: %ld\n", s_float.data32.i, s_float.data64.i);

    return 0;
}
