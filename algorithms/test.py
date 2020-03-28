import random
from math import ceil, floor

def insertionSort(a):
    for i in range(1, len(a)):
        j = i - 1
        tmp = a[j+1]
        while j >= 0 and a[j] > tmp:
            for k in range(i-1):
                assert(a[k] <= a[k+1])
            a[j+1] = a[j]
            j = j - 1
        a[j+1] = tmp

def selectionSort(a):
    for i in range(len(a)):
        jMin = i
        for j in range(jMin + 1, len(a)):
            if a[j] < a[jMin]:
                jMin = j
        tmp = a[i]
        a[i] = a[jMin]
        a[jMin] = tmp

def binaryInsertionSort(a):
    for k in range(1, len(a)):
        # find the right location for a[k] in the sorted sub-array
        i_0 = 0
        i_1 = k
        i = int(k/2)

        while i_0 < i:
            if a[k] < a[i]:
                i_1 = i
            else:
                i_0 = i
            i = int((i_0+i_1)/2)

        if a[k] >= a[i]:
            i += 1

        # shuffle items so it's in the right place.
        if i != k:
            tmp = a[k]
            for j in range(k-1, i-1, -1):
                a[j+1] = a[j]
            a[i] = tmp

def bubbleSort(a):
    swappedThisPass = True
    while swappedThisPass:
        swappedThisPass = False
        for k in range(len(a)-1):
            if a[k] > a[k+1]:
                tmp = a[k+1]
                a[k+1] = a[k]
                a[k] = tmp
                swappedThisPass = True

def mergeSort(a):
    if len(a) < 2:
        return a

    h = int(len(a)/2)
    a1 = mergeSort(a[:h])
    a2 = mergeSort(a[h:])

    a3 = [0 for i in range(len(a))]
    i1 = 0 # index into a1
    i2 = 0 # index into a2
    i3 = 0 # index into a3

    while i1 < len(a1) or i2 < len(a2):
        if i1 >= len(a1):
            a3[i3] = a2[i2]
            i2 += 1
        elif i2 >= len(a2):
            a3[i3] = a1[i1]
            i1 += 1
        else:
            if a1[i1] <= a2[i2]:
                a3[i3] = a1[i1]
                i1 += 1
            else:
                a3[i3] = a2[i2]
                i2 += 1
        i3 += 1

    return a3

def quickSort(a, iBegin, iEnd):
    if iBegin < iEnd:
        pivot = a[iEnd-1]
        iLeft = iBegin
        iRight = iEnd - 1

        while iLeft < iRight:
            while a[iLeft] <= pivot and iLeft < iRight:
                iLeft += 1
            while a[iRight-1] > pivot and iLeft < iRight:
                iRight -= 1
            if iLeft != iRight:
                a[iLeft], a[iRight-1] = a[iRight-1], a[iLeft]

        a[iLeft], a[iEnd-1] = a[iEnd-1], a[iLeft]

        quickSort(a, iBegin, iLeft)
        quickSort(a, iLeft+1, iEnd)

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

def heapify(a, iRoot, iEnd):
    jNode = iEnd
    while jNode >= iRoot:
        siftDown(a, jNode, iEnd)
        jNode -= 1

def heapSort(a):
    heapify(a, 0, len(a)-1)
    for k in range(len(a)-1, 0, -1):
        a[0], a[k] = a[k], a[0]
        print(k, a)
        siftDown(a, 0, k-1)

def countingSort(a):
    # array of counts recorded for each integer
    counts = [0 for i in range(max(a)+1)]

    for i in range(len(a)):
        counts[a[i]] += 1

    positions = [0 for i in range(max(a)+1)]
    total = 0

    for i in range(max(a)+1):
        positions[i] = total
        total += counts[i]

    output = [0 for i in a]
    for i in range(len(a)):
        output[positions[a[i]]] = a[i]
        positions[a[i]] += 1

    return output

def bucketSort(a):
    k = max(a)
    n = len(a)
    buckets = [[] for i in a]

    # separate into buckets
    for i in range(n):
        buckets[floor(a[i] / k * (n-1))].append(a[i])

    # sort each bucket and concatenate results
    output = []
    for list in buckets:
        insertionSort(list)
        output += list

    return output
