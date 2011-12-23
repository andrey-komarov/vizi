package ru.ifmo.vizi.groebner_basis;


public class Monomial {

	public 	double c;
    
	public int[] deg;

	public Monomial() {
		this(0);
	}

	public Monomial(double q) {
		c = q;
		deg = new int[26];
		for (int i = 0; i < 26; ++i) {
			deg[i] = 0;
		}
	}

	public Monomial(Monomial m) {
		if (m == null) return;
		c = m.c;
		deg = new int[26];
		for (int i = 0; i < 26; ++i) {
			deg[i] = m.deg[i];
		}
	}

	public Monomial(String s) {
		System.out.println(s);
		deg = new int[26];
		for (int i = 0; i < 26; ++i) {
			deg[i] = 0;
		}
		c = 1;
		int n = -1;
		while ((s != null) && (s.length() > 0)) {
			String cs = nextToken(s);
		System.out.println(cs);
			s = deleteToken(s);
			if ((cs.charAt(0) >= '0') && (cs.charAt(0) <= '9') || (cs.charAt(0) == '-')) {
				if (cs.equals("-")) cs = "-1";
				c *= Double.valueOf(cs).doubleValue();
				n = -1;
				continue;
			}
			if ((cs.charAt(0) >= 'a') && (cs.charAt(0) <= 'z')) {
				if (n >= 0) deg[n] += 1;
				n = cs.charAt(0) - 'a';
				continue;
			}
			if (cs.charAt(0) == '^') {
				cs = cs.substring(1);
				deg[n] += Integer.valueOf(cs).intValue();
				n = -1;
			}
		}
		if (n >= 0) deg[n] += 1;
	}

	public boolean similarTo(Monomial m) {
		if (m == null) return false;
		for (int i = 0; i < 26; ++i) {
			if (deg[i] != m.deg[i]) return false;
		}
		return true;
	}

	public boolean less(Monomial m) {
		for (int i = 0; i < 26; ++i) {
			if (deg[i] == 0) {
				if (m.deg[i] > 0) return false;
				continue;
			}
			if (deg[i] > m.deg[i]) return true;
			if (deg[i] < m.deg[i]) return false;
		}
		return false;
	}

	public String toString() {
		if (Math.abs(c) < 1e-9) return "0";
		String s = "\u2070\u00B9\u00B2\u00B3\u2074\u2075\u2076\u2077\u2078\u2079";
		String ans = "";
		boolean one = true;
		if (Math.abs(c + 1) < 1e-9) {
			ans = "-";
		}
		else {
			if (Math.abs(c - 1) >= 1e-9) {
				one = false;
				if (c - Math.floor(c) < 1e-9) {
					ans = (new Integer((int)c)).toString();
				}
				else {
					ans = (new Double(c)).toString();
				}
			}
		}
		boolean was = false;
		for (int i = 0; i < 26; ++i) {
			if (deg[i] == 0) continue;
			was = true;
			ans += (char)('a' + i);
			int t = deg[i];
			if (t == 1) continue;
			String temp = new String();
			while (t > 0) {
				temp = s.charAt(t % 10) + temp;
				t /= 10;
			}
			ans += temp;
		}
		if (!was && one) {
			ans += "1";
		}
		return ans;
	}

	public String toSLString() {
		if (Math.abs(c) < 1e-9) return "0";
		String ans = "";
		boolean one = true;
		if (Math.abs(c + 1) < 1e-9) {
			ans = "-";
		}
		else {
			if (Math.abs(c - 1) >= 1e-9) {
				one = false;
				if (c - Math.floor(c) < 1e-9) {
					ans = (new Integer((int)c)).toString();
				}
				else {
					ans = (new Double(c)).toString();
				}
			}
		}
		boolean was = false;
		for (int i = 0; i < 26; ++i) {
			if (deg[i] == 0) continue;
			was = true;
			ans += (char)('a' + i);
			if (deg[i] == 1) continue;
			ans += "^";
			ans += new Integer(deg[i]).toString();
		}
		if (!was && one) {
			ans += "1";
		}
		return ans;
	}

	public void divide(double q) {
		c /= q;
	}

	private static String nextToken(String s) {
		if ((s == null) || (s.length() == 0)) return "";
		int len = s.length();
		if ((s.charAt(0) >= 'a') && (s.charAt(0) <= 'z')) {
			String c = s.substring(0, 1);
			return c;
		}
		if ((s.charAt(0) == '*') || (s.charAt(0) == '+')) {
			return nextToken(s.substring(1));
		}
		if (s.charAt(0) == '^') {
			return '^' + nextToken(s.substring(1));
		}
		int i = 0;
		while ((i < len) && ((s.charAt(i) >= '0') && (s.charAt(i) <= '9') || (s.charAt(i) == '-'))) {
			++i;
		}
		String t = s.substring(0, i);
		return t;
	}

	private static String deleteToken(String s) {
		if ((s.charAt(0) == '+') || (s.charAt(0) == '*')) return s.substring(1 + nextToken(s).length());
		return s.substring(nextToken(s).length());
	}

	public static boolean isValid(String s) {
		while (s.length() > 0) {
			String cs = nextToken(s);
			s = deleteToken(s);
			if (cs.length() == 0) return true;
			if ((cs.charAt(0) >= 'a') && (cs.charAt(0) <= 'z')) {
				if (cs.length() > 1) return false;
				continue;
			}
			if ((cs.charAt(0) >= '0') && (cs.charAt(0) <= '9')) {
				int j = 0;
				for (int i = 0; i < cs.length(); ++i) {
					if ((cs.charAt(i) == '.') || (cs.charAt(i) == ',')) ++j;
				}
				if (j > 1) return false;
				continue;
			}
			if (cs.charAt(0) == '^') {
				int j = 0;
				for (int i = 1; i < cs.length(); ++i) {
					if ((cs.charAt(i) == '^') || (cs.charAt(i) == '.') || (cs.charAt(i) == '.')) ++j;
				}
				if (j > 0) return false;
			}
		}
		return true;
	}
}
