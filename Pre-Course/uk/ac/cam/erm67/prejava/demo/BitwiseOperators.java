package uk.ac.cam.erm67.prejava.demo;

class BitwiseOperators{
	public static void main(String[] args) {
		System.out.println(85 & 0x2B);
		System.out.println(0x55 | 0x2B);
		System.out.println(0105 ^ 0x2B);
		System.out.println(0x55 >> 4);
		System.out.println(0x55 << 7);
		System.out.println(-1 >>> 1);
		System.out.println(~0x55);
	}
}