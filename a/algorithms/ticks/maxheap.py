from math import ceil
import random

def siftDown(a, iStart, iEnd):
    iParent = iStart

    while 2*iParent + 1 <= iEnd:
        iSwap = iParent

        if a[2*iParent+1] > a[iSwap]:
            iSwap = 2*iParent+1
        if 2*iParent+2 <= iEnd:
            if a[2*iParent+2] > a[iSwap]:
                iSwap = 2*iParent+2

        if iSwap == iParent:
            return
        else:
            a[iParent], a[iSwap] = a[iSwap], a[iParent]
            iParent = iSwap

# rearranges the list x so that it satisfies the heap property
def heapify(x):
    iEnd = len(a)-1
    jNode = iEnd
    while jNode >= 0:
        siftDown(x, jNode, iEnd)
        jNode -= 1

# insert a new element e into the heap x
def push (x, e):
    x.append(e)
    i = len(x)-1
    while x[i] > x[ceil(i/2) - 1] and i != 0:
        x[ceil(i/2) - 1], x[i] = x[i], x[ceil(i/2) - 1]
        i = ceil(i/2) - 1
    return x

# update the heap x by removing its maximum element, and return that element;
# or raise IndexError if x is an empty list
def popmax(x):
    x[0], x[len(x)-1] = x[len(x)-1], x[0]
    result = x.pop()
    siftDown(x, 0, len(x)-1)
    return result
