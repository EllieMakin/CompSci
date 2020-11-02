FLAGS=-g -Wall -pedantic 
DEBUG_FLAGS=-fsanitize=address -fsanitize=undefined -g -fno-omit-frame-pointer -Wall -pedantic 

all: lab9.pdf
	gcc ${FLAGS} -Wall -pedantic -c lab9.c
	gcc ${FLAGS} -Wall -pedantic -c parse.c
	gcc ${FLAGS} -Wall -pedantic -c expr.c
	gcc ${FLAGS} -o lab9 lab9.o parse.o expr.o 

sane:
	gcc ${DEBUG_FLAGS} -Wall -pedantic -c lab9.c
	gcc ${DEBUG_FLAGS} -Wall -pedantic -c parse.c
	gcc ${DEBUG_FLAGS} -Wall -pedantic -c expr.c
	gcc ${DEBUG_FLAGS} -o lab9 lab9.o parse.o expr.o 

lab9.pdf:
	pdflatex -shell-escape lab9.tex

clean:
	rm -f lab9 lab9.o parse.o expr.o 
