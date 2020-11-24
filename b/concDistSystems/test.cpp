int buffer[N]; int in = 0, out = 0;
spaces = new Semaphore(N);
items  = new Semaphore(0);
guard  = new Semaphore(1);

void producer() {
    while(true) {
        item = produce();
        wait(spaces);
        wait(guard);
        buffer[in] = item;
        in = (in + 1) % N;
        signal(guard);
        signal(items);
    }
}

void consumer(int id) {
    while(true) {
        wait(items);
        wait(guard);
        item = buffer[out];
        out = (out+1) % N;
        signal(guard);
        signal(spaces);
        consume(item);
    }
}
