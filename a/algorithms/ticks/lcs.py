s1 = "ABAC"
s2 = "AABC"

# returns the full table of LCS lengths, or [] if either s1 or s2 is empty
def table(s1, s2):
    tbl = [[None for j in range(len(s2)+1)] for i in range(len(s1)+1)]

    for i in range(len(s1)+1):
        tbl[i][len(s2)] = 0
    for j in range(len(s2)+1):
        tbl[len(s1)][j] = 0

    for i in range(len(s1)):
        for j in range(len(s2)):
            if s1[i] == s2[j]:
                tbl[i][j] = tbl[i-1][j-1] + 1
            else:
                tbl[i][j] = max(tbl[i][j-1], tbl[i-1][j])

    return list(map(lambda x: x[:-1], tbl[:-1]))

def printTable(tbl):
    for row in tbl:
        print(row)

# return the final LCS length and string, given  tbl = table(s1, s2)
def match_length(tbl):
    return tbl[len(tbl)-1][len(tbl[0])-1]

def match_string(s1, s2, tbl):
    result = ""
    i = len(s1) - 1
    j = len(s2) - 1
    while i >= 0 or j >= 0:
        if s1[i] == s2[j]:
            result = s1[i] + result
            i -= 1
            j -= 1
        elif tbl[i][j-1] > tbl[i-1][j]:
            j -= 1
        else:
            i -= 1
    return result

tbl = table(s1, s2)
printTable(tbl)
print(match_length(tbl))
print(match_string(s1, s2, tbl))
