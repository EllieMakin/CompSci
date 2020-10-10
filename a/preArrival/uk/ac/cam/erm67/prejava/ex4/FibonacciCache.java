package uk.ac.cam.erm67.prejava.ex4;

public class FibonacciCache {
	
	// Uninitialised array
	public static long[] fib = null;
	
	public static void store() throws Exception {
		if (fib == null) {
			throw new Exception("Exception: fib is null");
		}
		
		for (int jN = 0; jN < fib.length; jN++) {
			if (jN == 1 || jN == 0) {
				fib[jN] = 1;
			}
			else {
				fib[jN] = fib[jN-1] + fib[jN-2];
			}
		}
	}
	
	public static void reset(int cachesize) {
		fib = new long[cachesize];
		for (int jN = 0; jN < cachesize; jN++) {
			fib[jN] = 0;
		}
	}
	
	public static long get(int i) throws Exception {
		if (fib == null) {
			throw new Exception("Exception: fib is null");
		}
		
		return fib[i];
	}
	
	public static void main(String[] args) {
		try {
			reset(20);
			store();
			int i = Integer.decode(args[0]);
			System.out.println(get(i));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}