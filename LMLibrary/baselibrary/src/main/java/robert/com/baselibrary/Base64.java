package robert.com.baselibrary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64 {
//	private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
//			.toCharArray();

	/*public static String encode(byte[] data) {
		int start = 0;
		int len = data.length;
		StringBuffer buf = new StringBuffer(data.length * 3 / 2);

		int end = len - 3;
		int i = start;
		int n = 0;

		while (i <= end) {
			int d = ((((int) data[i]) & 0x0ff) << 16)
					| ((((int) data[i + 1]) & 0x0ff) << 8)
					| (((int) data[i + 2]) & 0x0ff);

			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append(legalChars[(d >> 6) & 63]);
			buf.append(legalChars[d & 63]);

			i += 3;

			if (n++ >= 14) {
				n = 0;
				buf.append(" ");
			}
		}

		if (i == start + len - 2) {
			int d = ((((int) data[i]) & 0x0ff) << 16)
					| ((((int) data[i + 1]) & 255) << 8);

			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append(legalChars[(d >> 6) & 63]);
			buf.append("=");
		} else if (i == start + len - 1) {
			int d = (((int) data[i]) & 0x0ff) << 16;

			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append("==");
		}

		return buf.toString();
	}
*/

    /**
     * Base64是一种基于64个可打印字符来表示二进制数据的表示方法
     0 	    A 	16 	Q 	32 	g 	48 	w
     1 	    B 	17 	R 	33 	h 	49 	x
     2 	    C 	18 	S 	34 	i 	50 	y
     3 	    D 	19 	T 	35 	j 	51 	z
     4 	    E 	20 	U 	36 	k 	52 	0
     5 	    F 	21 	V 	37 	l 	53 	1
     6 	    G 	22 	W 	38 	m 	54 	2
     7 	    H 	23 	X 	39 	n 	55 	3
     8 	    I 	24 	Y 	40 	o 	56 	4
     9 	    J 	25 	Z 	41 	p 	57 	5
     10 	K 	26 	a 	42 	q 	58 	6
     11 	L 	27 	b 	43 	r 	59 	7
     12 	M 	28 	c 	44 	s 	60 	8
     13 	N 	29 	d 	45 	t 	61 	9
     14 	O 	30 	e 	46 	u 	62 	+
     15 	P 	31 	f 	47 	v 	63 	/
     */
	private static int decode(char c) {
		if (c >= 'A' && c <= 'Z')
			return ((int) c) - 65;
		else if (c >= 'a' && c <= 'z')
			return ((int) c) - 97 + 26;
		else if (c >= '0' && c <= '9')
			return ((int) c) - 48 + 26 + 26;
		else
			switch (c) {
			case '+':
				return 62;
			case '/':
				return 63;
			case '=':
				return 0;
			default:
				throw new RuntimeException("unexpected code: " + c);
			}
	}

	public static byte[] decode(String s) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			decode(s, bos);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		byte[] decodedBytes = bos.toByteArray();
		try {
			bos.close();
        } catch (IOException ex) {
			System.err.println("Error while decoding BASE64: " + ex.toString());
		}
		return decodedBytes;
	}

	private static void decode(String s, OutputStream os) throws IOException {
		int i = 0;

		int len = s.length();

		while (true) {
			while (i < len && s.charAt(i) <= ' ')
				i++;

			if (i == len)
				break;

			int tri = (decode(s.charAt(i)) << 18)
					+ (decode(s.charAt(i + 1)) << 12)
					+ (decode(s.charAt(i + 2)) << 6)
					+ (decode(s.charAt(i + 3)));

			os.write((tri >> 16) & 255);
			if (s.charAt(i + 2) == '=')
				break;
			os.write((tri >> 8) & 255);
			if (s.charAt(i + 3) == '=')
				break;
			os.write(tri & 255);

			i += 4;
		}
	}
}
