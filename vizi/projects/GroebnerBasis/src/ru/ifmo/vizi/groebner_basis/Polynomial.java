package ru.ifmo.vizi.groebner_basis;

import java.util.Stack;
import java.io.*;


public class Polynomial {

	public Monomial[] m;

	public Polynomial(Polynomial c) {
		this(c.m);
	}

	public Polynomial(Monomial[] ar) {
		m = new Monomial[ar.length];
		for (int i = 0; i < ar.length; ++i) {
			m[i] = new Monomial(ar[i]);
		}
		lexSort();
	}

	public Polynomial(int p) {
		m = new Monomial[p];
		for (int i = 0; i < p; ++i) {
			m[i] = new Monomial();
		}
    }

	public Polynomial(String s) {
		String pos = "^.+-*";
		String q = "";
		int len = s.length();
		for (int i = 0; i < len; ++i) {
			if ((pos.indexOf(s.charAt(i)) >= 0) || (s.charAt(i) >= 'a') && (s.charAt(i) <= 'z') || (s.charAt(i) >= '0') && (s.charAt(i) <= '9')) {
				q += s.charAt(i);
			}
		}
		s = q;
		len = s.length();
		Stack ms = new Stack();
		String cs = "";
		for (int i = 0; i < len; ++i) {
			if ((s.charAt(i) == '+') || (s.charAt(i) == '-')) {
				ms.push(new String(cs));
				cs = s.substring(i, i + 1);
				continue;
			}
			cs += s.charAt(i);
		}
		if (!cs.equals("+") && !cs.equals("-")) ms.push(cs);
		len = ms.size();
		m = new Monomial[len];
		for (int i = 0; i < len; ++i) {
			m[i] = new Monomial((String)ms.elementAt(i));
		}
	}

	public boolean equals(Polynomial p) {
		if ((p == null) || (p.m == null)) return false;
		lexSort();
		p.lexSort();
		if (m.length != p.m.length) return false;
		for (int i = 0; i < m.length; ++i) {
			if (Math.abs(m[i].c - p.m[i].c) > 1e-9) return false;
			for (int j = 0; j < 26; ++j) {
				if (m[i].deg[j] != p.m[i].deg[j]) return false;
			}
		}
		return true;
	}

	public int length() {
		if (m == null) return 0;
		lexSort();
		for (int i = 0; i < m.length; ++i) {
			if (m[i] == null) return i;
		}
		return m.length;
	}

    public void lexSort() {
		boolean b = true;
		while (b) {
			b = false;
			for (int i = 0; i < m.length - 1; ++i) {
				if (m[i + 1].less(m[i])) {
					Monomial t = m[i];
					m[i] = m[i + 1];
					m[i + 1] = t;
					b = true;
				}
			}
		}
		for (int i = 0; i < m.length; ++i) {
			if (Math.abs(m[i].c) < 1e-9) continue;
			int j = i + 1;
			for (; (j < m.length) && (m[i].similarTo(m[j]) && (Math.abs(m[i].c) > 1e-9)); ++j) {
				m[i].c += m[j].c;
				m[j].c = 0;
			}
			i = j - 1;
		}
		for (int i = 0; i < m.length; ++i) {
			if (Math.abs(m[i].c) < 1e-9) {
				int j = i + 1;
				for (; (j < m.length) && (Math.abs(m[j].c) < 1e-9); ++j) {}
				if (j < m.length) {
					m[i] = new Monomial(m[j]);
					m[j].c = 0;
				}
			}
		}
		int i = 0;
		for (; (i < m.length) && (Math.abs(m[i].c) >= 1e-9); ++i) {}
		if (i < m.length) {
			Monomial t[] = new Monomial[i];
			for (int j = 0; j < i; ++j) {
				t[j] = new Monomial(m[j]);
			}
			m = t;
		}
	}
     
	public String toString() {
		if ((this == null) || (this.m == null) || (this.m.length == 0)) return "0";
		String ans = new String(m[0].toString());
		for (int i = 1; i < m.length; ++i) {
			if (m[i].c > 1e-9) {
				ans += "+";
            }
            ans += m[i].toString();
		}
        return ans;
	}

	public String toSLString() {
		if ((this == null) || (this.m == null) || (this.m.length == 0)) return "0";
		String ans = new String(m[0].toSLString());
		for (int i = 1; i < m.length; ++i) {
			if (m[i].c > 1e-9) {
				ans += "+";
            }
            ans += m[i].toSLString();
		}
        return ans;
	}

	public void normalize() {
		lexSort();
		double q = m[0].c;
		int l = length();
		if (Math.abs(q) < 1e-9) return;
		for (int i = 0; i < l; ++i) {
			m[i].divide(q);
		}
	}

	public static boolean isValid(String s) {
		String pos = "^.+-*";
		int len = s.length();
		if (len == 0) return false;
		String q = "";
		for (int i = 0; i < len; ++i) {
			if ((pos.indexOf(s.charAt(i)) >= 0) || (s.charAt(i) >= 'a') && (s.charAt(i) <= 'z') || (s.charAt(i) >= '0') && (s.charAt(i) <= '9')) {
				q += s.charAt(i);
			}
			else {
				if (s.charAt(i) != ' ') return false;
			}
		}
		s = q;
		String cs = "";
		len = s.length();
		for (int i = 0; i < len; ++i) {
			if ((s.charAt(i) == '+') || (s.charAt(i) == '-')) {
				if (cs.length() == 0) return false;
				if (!Monomial.isValid(cs)) return false;
				cs = s.substring(i, i + 1);
				continue;
			}
			cs += s.charAt(i);
		}
		if (!cs.equals("+") && !cs.equals("-")) return Monomial.isValid(cs);
		return true;
	}
}
