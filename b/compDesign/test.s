# RV32I
  slli  s0, x28, 3        # s0 = i*8
  slli  s1, x29, 3        # s1 = j*8
  add   s0, x10, s0       # s0 = &A[i]
  add   s1, x10, s1       # s1 = &A[j]
  lw    s2, 0(s0)         # s2 = low-order word of A[i]
  lw    s3, 4(s0)         # s3 = high-order word of A[i]
  lw    s4, 0(s1)         # s4 = low-order word of A[j]
  lw    s5, 4(s1)         # s5 = high-order word of A[j]
  add   s6, s2, s4        # add low-order words
  bgeu  s6, s2, no_carry  # branch if no carry (assuming unsigned)
  addi  s3, s3, 1         # add the carry bit
no_carry:
  add   s7, s3, s5        # add high-order words
  addi  s0, x11, 64       # s0 = pointer to B[8]
  sw    s6, 0(s0)         # store low-order word
  sw    s7, 4(s0)         # store high-order word
  
# RV64I
# I've slightly optimised the order of the instructions here
# to avoid load-use data hazards when pipelining
  slli  s0, x28, 3        # s0 = i*8
  add   s0, x10, s0       # s0 = &A[i]
  ld    s2, 0(s0)         # load A[i]
  slli  s1, x29, 3        # s1 = j*8
  add   s1, x10, s1       # s1 = pointer to A[j]
  ld    s3, 0(s1)         # load A[j]
  addi  s5, x11, 64       # s5 = pointer to B[8]
  add   s4, s2, s3        # s4 = A[i] + A[j] 
  sd    s4, 0(s5)         # store result
