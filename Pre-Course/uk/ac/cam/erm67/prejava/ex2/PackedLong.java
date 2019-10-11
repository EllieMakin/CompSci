package uk.ac.cam.erm67.prejava.ex2;

public class PackedLong {

	/*	
	* Unpack and return the nth bit from the packed number at index position;
	* position counts from zero (representing the least significant bit)
	* up to 63 (representing the most significant bit).
	*/
	public static boolean get(long packed, int position) {
		// set "check" to equal 1 if the "position" bit in "packed" is set to 1
		// you should use bitwise operators (not % or similar)
		long check = (packed >> position) & 0x1;
		return (check == 1);
	}
	
	/*
	* Set the nth bit in the packed number to the value given
	* and return the new packed number
	*/
	public static long set(long packed, int position, boolean value) {
		long longOne = 1;
		if (value) {
			packed |= (longOne << position);
		}
		else {
			packed -= packed & (longOne << position);
		}
		return packed;
	}
}