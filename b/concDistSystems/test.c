#include <stdio.h>
#include <unistd.h> 
#include <stdlib.h> // malloc, free
#include <pthread.h> // pthread_create, pthread_join

#define NUMTHREADS 4

char* threadstr = "Thread";

void* threadfn(void* threadnum) {
    printf("%s %d\n", threadstr, *(int*)threadnum);
}

void main(void) {
    pthread_t threads[NUMTHREADS]; // Thread IDs

    int i;
    for (i = 0; i < NUMTHREADS; i++)
    {
        int *arg = malloc(sizeof(*arg));
        *arg = i;
        pthread_create(
            &threads[i],
            NULL,
            threadfn,
            arg
        );
    }

    for (i = 0; i < NUMTHREADS; i++)
        pthread_join(threads[i], NULL);
}
