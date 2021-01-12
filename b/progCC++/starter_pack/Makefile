
clean:
	rm summary extract
	rm *.o

summary: summary.c pcolparse.c pcolparse.h
	gcc -std=c99 -c pcolparse.c -Wall --pedantic
	gcc -std=c99 -c summary.c -Wall --pedantic
	gcc summary.o pcolparse.o -o summary

extract: extract.cc pcolparse.c pcolparse.h
	g++ -std=c++98 -c extract.cc -Wall --pedantic
	gcc -std=c99 -c pcolparse.c -Wall --pedantic
	g++ extract.o pcolparse.o -o extract
