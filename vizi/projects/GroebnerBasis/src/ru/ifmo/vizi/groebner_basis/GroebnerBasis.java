package ru.ifmo.vizi.groebner_basis;

import 
		java.util.Stack
	;
import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class GroebnerBasis extends BaseAutoReverseAutomata {
    /**
      * ���������� ���������� ��������� � ������ a
      */
    int length(Polynomial[] a) {
        
        		int i;
        		for (i = 0; i < a.length; ++i) {
        			if (a[i] == null) break;
        		}
        		return i;
    }

    /**
      * ���������� ���������� ����� ������� 2 �������
      */
    Monomial lcm(Monomial a, Monomial b) {
        
        		Monomial temp = new Monomial();
        		temp.c = Math.max(Math.abs(a.c), Math.abs(b.c));
        		for (int i = 0; i < 26; ++i) {
        			temp.deg[i] = Math.max(a.deg[i], b.deg[i]);
        		}
        		return temp;
    }

    /**
      * ����������� 2 ������
      */
    Monomial multiply(Monomial a, Monomial b) {
        
            	Monomial temp = new Monomial();
            	temp.c = a.c * b.c;
            	for (int i = 0; i < 26; ++i) {
            		temp.deg[i] = a.deg[i] + b.deg[i];
            	}
            	return temp;
    }

    /**
      * �������� ������� �� �����
      */
    Polynomial multiply(Polynomial a, Monomial b) {
        
            		Polynomial temp = new Polynomial(a);
            	 	for (int i = 0; i < a.length(); ++i) {
        				temp.m[i] = multiply(a.m[i], b);
        			}
        			temp.lexSort();
            	 	return temp;
    }

    /**
      * ����� ����� �� �����
      */
    Monomial divide(Monomial a, Monomial b) {
        
        		Monomial temp = new Monomial();
            	temp.c = a.c / b.c;
            	for (int i = 0; i < a.deg.length; ++i) {
            		temp.deg[i] = a.deg[i] - b.deg[i];
             	}
        		return temp;
    }

    /**
      * ���������� �� ��������� ��� ����� � ��������� ������.
      */
    boolean eq(double a, double b) {
        
            		return (Math.abs(a-b) < d.EPS);
    }

    /**
      * �������� �� �������� �������
      */
    Polynomial subtract(Polynomial a, Polynomial b) {
        
        		int la = a.length(), lb = b.length();
        	 	Polynomial temp = new Polynomial(la + lb);
             	for (int i = 0; i < la; ++i) {
            		temp.m[i] = new Monomial(a.m[i]);
        		}
            	for (int i = 0; i < b.length(); ++i) {
            		temp.m[i + la] = new Monomial(b.m[i]);
            		temp.m[i + la].c = -b.m[i].c;
            	}
        		temp.lexSort();
            	boolean bb = true;
            	while (bb) {
            		bb = false;
        	    	for (int i = 0; i < temp.length(); ++i) {
        	    		int j;
        	    		for (j = i + 1; (j < temp.m.length) && (temp.m[i].similarTo(temp.m[j])); ++j) {
        	    			if (temp.m[j].c != 0) {
        	    				bb = true;
            			 	}
            				temp.m[i].c += temp.m[j].c;
            			 	temp.m[j].c = 0;
            		 	}
        				if (eq(temp.m[i].c, 0)) {
        					for (int q = 0; q < 26; ++q) {
            					temp.m[i].deg[q] = 0;
        					}
            		 	}
            		 	i = j - 1;
            	 	}
            	 	temp.lexSort();
           	 	}
           	 	int lt = 0;
           	 	for (int i = 0; (i < temp.m.length) && (temp.m[i] != null) && (!eq(temp.m[i].c, 0)); ++i) {
           			++lt;
           	 	}
           	 	Polynomial res = new Polynomial(lt);
           	 	for (int i = 0; i < lt; ++i) {
           			res.m[i] = new Monomial(temp.m[i]);
        		}
        		return res;
    }

    /**
      * ������ ������� 2 ������
      */
    void swap(Monomial a, Monomial b) {
        
            	Monomial t = new Monomial(a);
             	a = b;
        		b = t;
    }

    /**
      * ��������� ����������� ������� a �� b
      */
    boolean isDivisibleBy(Monomial a, Monomial b) {
        
            		if (eq(b.c, 0)) return false;
            	 	for (int i = 0; i < 26; ++i) {
            		 	if (a.deg[i] < b.deg[i]) return false;
            	 	}
        		return true;
    }

    /**
      * ��������� ������� �� ��������� ����
      */
    boolean eq0(Polynomial a) {
        
            		return (a.m.length == 0) || eq(a.m[0].c, 0);
    }

    /**
      * ��������� ����� � �������
      */
    Polynomial add(Polynomial p, Monomial b) {
        
        		Polynomial a = new Polynomial(p);
        		int la = a.length();
        		for (int i = 0; i < la; ++i) {
            	 	if (a.m[i].similarTo(b)) {
        				a.m[i].c += b.c;
        				a.lexSort();
            		 	return a;
            	 	}
        			if (eq(a.m[i].c, 0)) {
        				a.m[i] = new Monomial(b);
        				a.lexSort();
            		 	return a;
            	 	}
        		}
        		int ll = la > 0 ? la * 2 : 1;
        		Polynomial temp = new Polynomial(ll);
            	for (int i = 0; i < la; ++i) {
            		temp.m[i] = new Monomial(a.m[i]);
            	}
            	temp.m[la] = new Monomial(b);
        		return temp;
    }

    /**
      * ��������� ������� � �����
      */
    Polynomial[] add(Polynomial b) {
        
        		if ((d.g1 == null) || (length(d.g1) == 0)) {
        			Polynomial[] t = new Polynomial[]{new Polynomial(b)};
        			return t;
        		}
        		Polynomial[] res = new Polynomial[d.g1.length];
        		int q = 0;
        		for (int i = 0; i < d.g1.length; ++i) {
        			if (d.g1[i] == null) continue;
        			res[q] = new Polynomial(d.g1[i]);
        			res[q++].lexSort();
        		}
            	for (int i = 0; i < q; ++i) {
            	 	if (eq(res[i].m[0].c, 0)) {
            		 	res[i] = new Polynomial(b);
            		 	return res;
            	 	}
             	}
             	int lg = q;
             	int lt = lg * 2;
             	Polynomial[] temp = new Polynomial[lt];
                q = 0;
             	for (int i = 0; i < lg; ++i) {
            	 	if (eq(res[i].m[0].c, 0)) continue;
        			temp[q++] = new Polynomial(res[i]);
             	}
        		temp[q] = new Polynomial(b);
        		return temp;
    }

    /**
      * ������� ������� �� ������ g
      */
    Polynomial[] subtract(int q) {
        
        		int lg = length(d.g);
        		if (q >= lg) return d.g;
        		Polynomial[] t = new Polynomial[lg - 1];
        		for (int i = 0; i < q; ++i) {
        			t[i] = new Polynomial(d.g[i]);
        		}
        		for (int i = q + 1; i < lg; ++i) {
        			t[i - 1] = new Polynomial(d.g[i]);
        		}
        		return t;
    }

    /**
      * ���� ������� � G1
      */
    int indexIn(Polynomial p) {
        
        		int lg = length(d.g1);
        		for (int i = 0; i < lg; ++i) {
        			if (p.equals(d.g1[i])) {
        				return i;
        			}
        		}
        		return -1;
    }

    /**
      * �������� g � g1
      */
    Polynomial[] gToG1() {
        
            		int lg = length(d.g);
        			Polynomial[] temp = new Polynomial[lg];
        			int q = 0;
        			for (int i = 0; i < lg; ++i) {
        				if (d.g[i] == null) continue;
        				temp[q++] = new Polynomial(d.g[i]);
        			}
        			return temp;
    }

    /**
      * �������� x � spol
      */
    void copy2spol(Polynomial x) {
        
        		d.spol = new Polynomial(x);
    }

    /**
      * �������� reminder � g[x]
      */
    Polynomial[] copy2x(int x) {
        
        		int lg = length(d.g);
        		Polynomial[] t = new Polynomial[lg + 1];
        		for (int i = 0; i < x; ++i) {
        			t[i] = new Polynomial(d.g[i]);
        		}
        		t[x] = new Polynomial(d.reminder);
        		for (int i = x + 1; i < lg + 1; ++i) {
        			t[i] = new Polynomial(d.g[i - 1]);
        		}
        		return t;
    }

    /**
      * �������� g1 � g
      */
    Polynomial[] g1ToG() {
        
        		int lg = length(d.g1);
        		Polynomial[] temp = new Polynomial[lg];
        		int q = 0;
        		for (int i = 0; i < lg; ++i) {
        			if (d.g1[i] == null) continue;
        			temp[q++] = new Polynomial(d.g1[i]);
        		}
        		return temp;
    }

    /**
      * �������� g1 � given
      */
    void g12given() {
        
        		int lg = length(d.g1);
        		d.given = new Polynomial[lg];
        		for (int i = 0; i < lg; ++i) {
        			d.given[i] = new Polynomial(d.g1[i]);
        		}
    }

    /**
      * �������� given � g1
      */
    void given2g1() {
        
        		int lg = length(d.given);
        		d.g1 = new Polynomial[lg];
        		for (int i = 0; i < lg; ++i) {
        			d.g1[i] = new Polynomial(d.given[i]);
        		}
    }

    /**
      * ������������ �� 1-�� �����������
      */
    Polynomial[] norm() {
        
        		int lg = length(d.g);
        		Polynomial[] t = new Polynomial[lg];
        		for (int i = 0; i < lg; ++i) {
        			t[i] = new Polynomial(d.g[i]);
        			t[i].normalize();
        		}
        		return t;
    }

    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public GroebnerBasis(Locale locale) {
        super("ru.ifmo.vizi.groebner_basis.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ������� �������.
          */
        public GroebnerBasisVisualizer visualizer = null;

        /**
          * ��������� ���������� ��� �������� ���������� ������..
          */
        public Polynomial[] given = null;

        /**
          * ���������� ��������.
          */
        public int[] checked = null;

        /**
          * ����������� � ����������.
          */
        public String comment = new String();

        /**
          * �������� ����������.
          */
        public double EPS = 1e-9;

        /**
          * ���������� �� G2.
          */
        public boolean showG2 = false;

        /**
          * ������������ ����� "rem".
          */
        public String showS = "S = ";

        /**
          * ����� G1..
          */
        public Polynomial[] g1 = new Polynomial[] {new Polynomial("x^2+y^2"), new Polynomial("x-y")};

        /**
          * ����� G2..
          */
        public Polynomial[] g = null;

        /**
          * ���������� ��������� � ������.
          */
        public int n = 2;

        /**
          * ����������, ������������ ��� �������� ��������� � �������.
          */
        public int numb = 0;

        /**
          * ����������, ������������ ��� �������� ��������� � �������.
          */
        public int numb2 = 0;

        /**
          * S-�������.
          */
        public Polynomial spol = null;

        /**
          * ��������� ����������, ������������ ��� �������� ����������.
          */
        public Polynomial temp = null;

        /**
          * ������� �� ������� ���������� �������� �� ����� g.
          */
        public Polynomial reminder = null;

        /**
          * ���������� ����� ������� ������� ������ 2 ��������� (��������� spolynomial).
          */
        public Monomial spolynomial_lcm;

        /**
          * ��������� lcm � �������� ����� ������� �������� (��������� spolynomial).
          */
        public Monomial spolynomial_x;

        /**
          * ��������� lcm � �������� ����� ������� �������� (��������� spolynomial).
          */
        public Monomial spolynomial_y;

        /**
          * ������������ x � ������� �������� (��������� spolynomial).
          */
        public Polynomial spolynomial_a;

        /**
          * ������������ y � ������� �������� (��������� spolynomial).
          */
        public Polynomial spolynomial_b;

        /**
          * ��������� ���������� (��������� rem).
          */
        public Polynomial rem_t;

        /**
          * ���������� ����� (��������� rem).
          */
        public int rem_i;

        /**
          * ���������� ����� (��������� rem).
          */
        public int rem_j;

        /**
          * ���������� ����� (��������� rem).
          */
        public int rem_z;

        /**
          * ����� �������� temp (��������� rem).
          */
        public int rem_lt;

        /**
          * ����� ������ g (��������� rem).
          */
        public int rem_lg;

        /**
          * ���� ��������� ���������� (��������� rem).
          */
        public boolean rem_er;

        /**
          * ��������� ���������� ��� ���������� (��������� rem).
          */
        public Polynomial rem_tr;

        /**
          * ��������� ���������� ��� ���������� (��������� rem).
          */
        public Polynomial rem_tr1;

        /**
          * ��������� ����� ��� ���������� (��������� rem).
          */
        public Monomial rem_tm;

        /**
          * ��������� ������� ��� ���������� (��������� rem).
          */
        public Polynomial rem_tp;

        /**
          * ����������, ������������ ��� �������� ���������� ��������� ������ (��������� MakingBasis).
          */
        public int MakingBasis_lg;

        /**
          * ����������, ������������ ��� �������� ���������� ��������� ������ (��������� MakingBasis).
          */
        public int MakingBasis_lg1;

        /**
          * ���������� ����� (��������� MakingBasis).
          */
        public int MakingBasis_i;

        /**
          * ���������� ����� (��������� MakingBasis).
          */
        public int MakingBasis_j;

        /**
          * ����������-���� (��������� MakingBasis).
          */
        public boolean MakingBasis_b;

        /**
          * ����������, ������������ ��� �������� ���������� ������� �������� (��������� reduce).
          */
        public int reduce_lgi;

        /**
          * ����������, ������������ ��� �������� ���������� ��������� ������ (��������� reduce).
          */
        public int reduce_lg;

        /**
          * ���������� ����� (��������� reduce).
          */
        public int reduce_i;

        /**
          * ���������� ����� (��������� reduce).
          */
        public int reduce_j;

        /**
          * ����������-���� (��������� reduce).
          */
        public boolean reduce_b;

        /**
          * ��������� ���������� (��������� reduce).
          */
        public Polynomial reduce_tp;

        public String toString() {
            		StringBuffer s = new StringBuffer();
            		return s.toString();
        }
    }

    /**
      * ��������� S-������� 2 ��������� �� g1.
      */
    private final class spolynomial extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 9;

        /**
          * �����������.
          */
        public spolynomial() {
            super( 
                "spolynomial", 
                0, // ����� ���������� ��������� 
                9, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "ToDO", 
                    "���������� ����������� ������ �������� ������� ������", 
                    "���������� ��������� LCM � �������� ����� ������� ��������", 
                    "��������� ������� �������� �� x", 
                    "���������� ��������� LCM � �������� ����� ������� ��������", 
                    "��������� ������� �������� �� y", 
                    "s = a - b", 
                    "print s", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    1, // ToDO 
                    0, // ���������� ����������� ������ �������� ������� ������ 
                    0, // ���������� ��������� LCM � �������� ����� ������� �������� 
                    0, // ��������� ������� �������� �� x 
                    0, // ���������� ��������� LCM � �������� ����� ������� �������� 
                    0, // ��������� ������� �������� �� y 
                    0, // s = a - b 
                    0, // print s 
                    Integer.MAX_VALUE, // �������� ��������� 
                } 
            ); 
        }

        /**
          * ������� ���� ��� �������� � �����.
          */
        protected void doStepForward(int level) {
            // ������� � ��������� ���������
            switch (state) {
                case START_STATE: { // ��������� ���������
                    state = 1; // ToDO
                    break;
                }
                case 1: { // ToDO
                    state = 2; // ���������� ����������� ������ �������� ������� ������
                    break;
                }
                case 2: { // ���������� ����������� ������ �������� ������� ������
                    state = 3; // ���������� ��������� LCM � �������� ����� ������� ��������
                    break;
                }
                case 3: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    state = 4; // ��������� ������� �������� �� x
                    break;
                }
                case 4: { // ��������� ������� �������� �� x
                    state = 5; // ���������� ��������� LCM � �������� ����� ������� ��������
                    break;
                }
                case 5: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    state = 6; // ��������� ������� �������� �� y
                    break;
                }
                case 6: { // ��������� ������� �������� �� y
                    state = 7; // s = a - b
                    break;
                }
                case 7: { // s = a - b
                    state = 8; // print s
                    break;
                }
                case 8: { // print s
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ToDO
                    startSection();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.hide();
                    break;
                }
                case 2: { // ���������� ����������� ������ �������� ������� ������
                    startSection();
                    storeField(d, "spolynomial_lcm");
                    				d.spolynomial_lcm = lcm (d.g1[d.numb].m[0], d.g1[d.numb2].m[0]);
                    storeField(d, "comment");
                    				d.comment = "lcm (" + d.g1[d.numb].m[0].toString() + ", " + d.g1[d.numb2].m[0].toString() + ") = " + d.spolynomial_lcm.toString();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printResult();
                    break;
                }
                case 3: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    startSection();
                    storeField(d, "spolynomial_x");
                    				d.spolynomial_x = divide(d.spolynomial_lcm,d.g1[d.numb].m[0]);
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printAction('/', new Polynomial(new Monomial[] {d.spolynomial_lcm}), new Polynomial(new Monomial[] {d.g1[d.numb].m[0]}), new Polynomial(new Monomial[] {d.spolynomial_x}), 1);
                    break;
                }
                case 4: { // ��������� ������� �������� �� x
                    startSection();
                    storeField(d, "spolynomial_a");
                    				d.spolynomial_a = multiply(d.g1[d.numb],d.spolynomial_x);	
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printAction('*', d.g1[d.numb], new Polynomial(new Monomial[] {d.spolynomial_x}), d.spolynomial_a, 2);
                    break;
                }
                case 5: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    startSection();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.hideAction();
                    storeField(d, "spolynomial_y");
                    				d.spolynomial_y = divide(d.spolynomial_lcm,d.g1[d.numb2].m[0]);
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printAction('/', new Polynomial(new Monomial[] {d.spolynomial_lcm}), new Polynomial(new Monomial[] {d.g1[d.numb2].m[0]}), new Polynomial(new Monomial[] {d.spolynomial_y}), 1);
                    break;
                }
                case 6: { // ��������� ������� �������� �� y
                    startSection();
                    storeField(d, "spolynomial_b");
                    				d.spolynomial_b = multiply(d.g1[d.numb2],d.spolynomial_y);
                    storeField(d, "comment");
                    				d.comment = "lcm (" + d.g1[d.numb].m[0].toString() + ", " + d.g1[d.numb2].m[0].toString() + ") = " + d.spolynomial_lcm.toString();
                    				d.visualizer.labels = d.visualizer.printResult();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printAction('*', d.g1[d.numb2], new Polynomial(new Monomial[] {d.spolynomial_y}), d.spolynomial_b, 2);
                    break;
                }
                case 7: { // s = a - b
                    startSection();
                    storeField(d, "spol");
                    				d.spol = new Polynomial(subtract(d.spolynomial_a, d.spolynomial_b));
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.hide();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printAction('-', d.spolynomial_a, d.spolynomial_b, d.spol, 1);
                    break;
                }
                case 8: { // print s
                    startSection();
                    storeField(d, "spol");
                    				d.spol = new Polynomial(subtract(d.spolynomial_a, d.spolynomial_b));
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.hide();
                    storeField(d, "comment");
                    				d.comment = "S = " + d.spol.toString();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printResult();
                    break;
                }
            }
        }

        /**
          * ������� ���� ��� �������� �����.
          */
        protected void doStepBackward(int level) {
            // ��������� �������� � ������� ���������
            switch (state) {
                case 1: { // ToDO
                    restoreSection();
                    break;
                }
                case 2: { // ���������� ����������� ������ �������� ������� ������
                    restoreSection();
                    break;
                }
                case 3: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    restoreSection();
                    break;
                }
                case 4: { // ��������� ������� �������� �� x
                    restoreSection();
                    break;
                }
                case 5: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    restoreSection();
                    break;
                }
                case 6: { // ��������� ������� �������� �� y
                    restoreSection();
                    break;
                }
                case 7: { // s = a - b
                    restoreSection();
                    break;
                }
                case 8: { // print s
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ToDO
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���������� ����������� ������ �������� ������� ������
                    state = 1; // ToDO
                    break;
                }
                case 3: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    state = 2; // ���������� ����������� ������ �������� ������� ������
                    break;
                }
                case 4: { // ��������� ������� �������� �� x
                    state = 3; // ���������� ��������� LCM � �������� ����� ������� ��������
                    break;
                }
                case 5: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    state = 4; // ��������� ������� �������� �� x
                    break;
                }
                case 6: { // ��������� ������� �������� �� y
                    state = 5; // ���������� ��������� LCM � �������� ����� ������� ��������
                    break;
                }
                case 7: { // s = a - b
                    state = 6; // ��������� ������� �������� �� y
                    break;
                }
                case 8: { // print s
                    state = 7; // s = a - b
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 8; // print s
                    break;
                }
            }
        }

        /**
          * ����������� � �������� ���������
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // ����� �����������
            switch (state) {
                case 1: { // ToDO
                    comment = GroebnerBasis.this.getComment("spolynomial.todo"); 
                    args = new Object[]{d.g1[d.numb], d.g1[d.numb2]}; 
                    break;
                }
                case 2: { // ���������� ����������� ������ �������� ������� ������
                    comment = GroebnerBasis.this.getComment("spolynomial.CalculateLCM"); 
                    args = new Object[]{d.g1[d.numb], d.g1[d.numb2], d.spolynomial_lcm}; 
                    break;
                }
                case 3: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    comment = GroebnerBasis.this.getComment("spolynomial.DivideLCMByLT1"); 
                    args = new Object[]{d.spolynomial_lcm, d.g1[d.numb].m[0], d.spolynomial_x}; 
                    break;
                }
                case 4: { // ��������� ������� �������� �� x
                    comment = GroebnerBasis.this.getComment("spolynomial.Multiply1ByX"); 
                    args = new Object[]{d.spolynomial_x, d.g1[d.numb], d.spolynomial_a}; 
                    break;
                }
                case 5: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    comment = GroebnerBasis.this.getComment("spolynomial.DivideLCMByLT2"); 
                    args = new Object[]{d.spolynomial_lcm, d.g1[d.numb2].m[0], d.spolynomial_y}; 
                    break;
                }
                case 6: { // ��������� ������� �������� �� y
                    comment = GroebnerBasis.this.getComment("spolynomial.Multiply2ByY"); 
                    args = new Object[]{d.spolynomial_y, d.g1[d.numb2], d.spolynomial_b}; 
                    break;
                }
                case 7: { // s = a - b
                    comment = GroebnerBasis.this.getComment("spolynomial.SubtractBFromA"); 
                    args = new Object[]{d.spol}; 
                    break;
                }
                case 8: { // print s
                    comment = GroebnerBasis.this.getComment("spolynomial.Result"); 
                    args = new Object[]{d.spol}; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * ��������� �������� �� ��������� ���������
          */
        public void drawState() {
            switch (state) {
                case 1: { // ToDO
                    				d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    				d.visualizer.redraw();
                    break;
                }
                case 2: { // ���������� ����������� ������ �������� ������� ������
                    				d.visualizer.redraw();
                    break;
                }
                case 3: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    				d.visualizer.redraw();
                    break;
                }
                case 4: { // ��������� ������� �������� �� x
                    				d.visualizer.redraw();
                    break;
                }
                case 5: { // ���������� ��������� LCM � �������� ����� ������� ��������
                    				d.visualizer.redraw();
                    break;
                }
                case 6: { // ��������� ������� �������� �� y
                    				d.visualizer.redraw();
                    break;
                }
                case 7: { // s = a - b
                    				d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    				d.visualizer.redraw();
                    break;
                }
                case 8: { // print s
                    				d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    				d.visualizer.redraw();
                    break;
                }
            }
        }
    }

    /**
      * ���������� �������� � �����.
      */
    private final class AddPolynomial2Ideal extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 2;

        /**
          * �����������.
          */
        public AddPolynomial2Ideal() {
            super( 
                "AddPolynomial2Ideal", 
                0, // ����� ���������� ��������� 
                2, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "���������� �������� � ����� g1", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    1, // ���������� �������� � ����� g1 
                    Integer.MAX_VALUE, // �������� ��������� 
                } 
            ); 
        }

        /**
          * ������� ���� ��� �������� � �����.
          */
        protected void doStepForward(int level) {
            // ������� � ��������� ���������
            switch (state) {
                case START_STATE: { // ��������� ���������
                    state = 1; // ���������� �������� � ����� g1
                    break;
                }
                case 1: { // ���������� �������� � ����� g1
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ���������� �������� � ����� g1
                    startSection();
                    storeField(d, "g1");
                    				d.g1 = add(d.reminder);
                    break;
                }
            }
        }

        /**
          * ������� ���� ��� �������� �����.
          */
        protected void doStepBackward(int level) {
            // ��������� �������� � ������� ���������
            switch (state) {
                case 1: { // ���������� �������� � ����� g1
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ���������� �������� � ����� g1
                    state = START_STATE; 
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 1; // ���������� �������� � ����� g1
                    break;
                }
            }
        }

        /**
          * ����������� � �������� ���������
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // ����� �����������
            switch (state) {
                case 1: { // ���������� �������� � ����� g1
                    comment = GroebnerBasis.this.getComment("AddPolynomial2Ideal.AddingPolynomial2Ideal"); 
                    args = new Object[]{d.reminder}; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * ��������� �������� �� ��������� ���������
          */
        public void drawState() {
            switch (state) {
                case 1: { // ���������� �������� � ����� g1
                    				d.visualizer.printIdealsWithPolynomials(new boolean[] {false, true}, new Polynomial[] {d.reminder});
                    break;
                }
            }
        }
    }

    /**
      * ������� �� ������� �������� �� �����.
      */
    private final class rem extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 32;

        /**
          * �����������.
          */
        public rem() {
            super( 
                "rem", 
                0, // ����� ���������� ��������� 
                32, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "if showS", 
                    "if showS (���������)", 
                    "������� S", 
                    "�� ������� S", 
                    "ToDo", 
                    "� �� ������ �� �������?", 
                    "� �� ������ �� �������? (���������)", 
                    "������� ������� ����� 0", 
                    "����������", 
                    "����������� spol � t", 
                    "���������� � �����", 
                    "����������� spol[i] � t[i]", 
                    "����������� spol[i].deg � t[i].deg", 
                    "i++", 
                    "������� ��������� ������", 
                    "���������� � �����", 
                    "������� t �� �������� g", 
                    "�������� ��������� LT(t) �� LT(g[i])", 
                    "������� ����� t ������� �� ������� ����� g[i]", 
                    "������� ����� t ������� �� ������� ����� g[i] (���������)", 
                    "������� ����� t ������� �� ������� ����� g[i]", 
                    "����� t �� g[i]", 
                    "����� t �� g[i]", 
                    "���������� �������", 
                    "�� �������", 
                    "Increment of i", 
                    "������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������", 
                    "������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ (���������)", 
                    "����������� � ������� LT(t)", 
                    "���������� t[0] � �������", 
                    "���������� � �����", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // if showS 
                    -1, // if showS (���������) 
                    -1, // ������� S 
                    -1, // �� ������� S 
                    0, // ToDo 
                    -1, // � �� ������ �� �������? 
                    -1, // � �� ������ �� �������? (���������) 
                    0, // ������� ������� ����� 0 
                    -1, // ���������� 
                    -1, // ����������� spol � t 
                    -1, // ���������� � ����� 
                    -1, // ����������� spol[i] � t[i] 
                    -1, // ����������� spol[i].deg � t[i].deg 
                    -1, // i++ 
                    -1, // ������� ��������� ������ 
                    -1, // ���������� � ����� 
                    -1, // ������� t �� �������� g 
                    0, // �������� ��������� LT(t) �� LT(g[i]) 
                    -1, // ������� ����� t ������� �� ������� ����� g[i] 
                    -1, // ������� ����� t ������� �� ������� ����� g[i] (���������) 
                    0, // ������� ����� t ������� �� ������� ����� g[i] 
                    -1, // ����� t �� g[i] 
                    0, // ����� t �� g[i] 
                    -1, // ���������� ������� 
                    0, // �� ������� 
                    -1, // Increment of i 
                    -1, // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ 
                    -1, // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ (���������) 
                    0, // ����������� � ������� LT(t) 
                    -1, // ���������� t[0] � ������� 
                    -1, // ���������� � ����� 
                    Integer.MAX_VALUE, // �������� ��������� 
                } 
            ); 
        }

        /**
          * ������� ���� ��� �������� � �����.
          */
        protected void doStepForward(int level) {
            // ������� � ��������� ���������
            switch (state) {
                case START_STATE: { // ��������� ���������
                    state = 1; // if showS
                    break;
                }
                case 1: { // if showS
                    if (d.showS.equals("S = ")) {
                        state = 3; // ������� S
                    } else {
                        state = 4; // �� ������� S
                    }
                    break;
                }
                case 2: { // if showS (���������)
                    state = 5; // ToDo
                    break;
                }
                case 3: { // ������� S
                    stack.pushBoolean(true); 
                    state = 2; // if showS (���������)
                    break;
                }
                case 4: { // �� ������� S
                    stack.pushBoolean(false); 
                    state = 2; // if showS (���������)
                    break;
                }
                case 5: { // ToDo
                    state = 6; // � �� ������ �� �������?
                    break;
                }
                case 6: { // � �� ������ �� �������?
                    if (d.spol.m.length == 0) {
                        state = 8; // ������� ������� ����� 0
                    } else {
                        state = 9; // ����������
                    }
                    break;
                }
                case 7: { // � �� ������ �� �������? (���������)
                    state = END_STATE; 
                    break;
                }
                case 8: { // ������� ������� ����� 0
                    stack.pushBoolean(true); 
                    state = 7; // � �� ������ �� �������? (���������)
                    break;
                }
                case 9: { // ����������
                    stack.pushBoolean(false); 
                    state = 10; // ����������� spol � t
                    break;
                }
                case 10: { // ����������� spol � t
                    if (d.rem_i < d.rem_lt) {
                        state = 11; // ���������� � �����
                    } else {
                        stack.pushBoolean(false); 
                        state = 15; // ������� ��������� ������
                    }
                    break;
                }
                case 11: { // ���������� � �����
                    stack.pushBoolean(false); 
                    state = 12; // ����������� spol[i] � t[i]
                    break;
                }
                case 12: { // ����������� spol[i] � t[i]
                    if (d.rem_j < 26) {
                        state = 13; // ����������� spol[i].deg � t[i].deg
                    } else {
                        state = 14; // i++
                    }
                    break;
                }
                case 13: { // ����������� spol[i].deg � t[i].deg
                    stack.pushBoolean(true); 
                    state = 12; // ����������� spol[i] � t[i]
                    break;
                }
                case 14: { // i++
                    stack.pushBoolean(true); 
                    state = 10; // ����������� spol � t
                    break;
                }
                case 15: { // ������� ��������� ������
                    if (!eq0(d.rem_t)) {
                        state = 16; // ���������� � �����
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // � �� ������ �� �������? (���������)
                    }
                    break;
                }
                case 16: { // ���������� � �����
                    stack.pushBoolean(false); 
                    state = 17; // ������� t �� �������� g
                    break;
                }
                case 17: { // ������� t �� �������� g
                    if ((d.rem_i < d.rem_lg) && d.rem_er) {
                        state = 18; // �������� ��������� LT(t) �� LT(g[i])
                    } else {
                        state = 27; // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������
                    }
                    break;
                }
                case 18: { // �������� ��������� LT(t) �� LT(g[i])
                    state = 19; // ������� ����� t ������� �� ������� ����� g[i]
                    break;
                }
                case 19: { // ������� ����� t ������� �� ������� ����� g[i]
                    if (isDivisibleBy(d.rem_t.m[0], d.g[d.rem_i].m[0])) {
                        state = 21; // ������� ����� t ������� �� ������� ����� g[i]
                    } else {
                        state = 25; // �� �������
                    }
                    break;
                }
                case 20: { // ������� ����� t ������� �� ������� ����� g[i] (���������)
                    state = 26; // Increment of i
                    break;
                }
                case 21: { // ������� ����� t ������� �� ������� ����� g[i]
                    state = 22; // ����� t �� g[i]
                    break;
                }
                case 22: { // ����� t �� g[i]
                    state = 23; // ����� t �� g[i]
                    break;
                }
                case 23: { // ����� t �� g[i]
                    state = 24; // ���������� �������
                    break;
                }
                case 24: { // ���������� �������
                    stack.pushBoolean(true); 
                    state = 20; // ������� ����� t ������� �� ������� ����� g[i] (���������)
                    break;
                }
                case 25: { // �� �������
                    stack.pushBoolean(false); 
                    state = 20; // ������� ����� t ������� �� ������� ����� g[i] (���������)
                    break;
                }
                case 26: { // Increment of i
                    stack.pushBoolean(true); 
                    state = 17; // ������� t �� �������� g
                    break;
                }
                case 27: { // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������
                    if (d.rem_er) {
                        state = 29; // ����������� � ������� LT(t)
                    } else {
                        stack.pushBoolean(false); 
                        state = 28; // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ (���������)
                    }
                    break;
                }
                case 28: { // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ (���������)
                    stack.pushBoolean(true); 
                    state = 15; // ������� ��������� ������
                    break;
                }
                case 29: { // ����������� � ������� LT(t)
                    state = 30; // ���������� t[0] � �������
                    break;
                }
                case 30: { // ���������� t[0] � �������
                    state = 31; // ���������� � �����
                    break;
                }
                case 31: { // ���������� � �����
                    stack.pushBoolean(true); 
                    state = 28; // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // if showS
                    break;
                }
                case 2: { // if showS (���������)
                    break;
                }
                case 3: { // ������� S
                    startSection();
                    storeField(d.visualizer, "labels");
                    					d.visualizer.labels = d.visualizer.hide();
                    storeField(d, "comment");
                    					d.comment = d.showS + d.spol.toString();
                    storeField(d.visualizer, "labels");
                    					d.visualizer.labels = d.visualizer.printResult();
                    					d.visualizer.redraw();
                    break;
                }
                case 4: { // �� ������� S
                    startSection();
                    storeField(d.visualizer, "labels");
                    					d.visualizer.labels = d.visualizer.hide();
                    					d.visualizer.redraw();
                    break;
                }
                case 5: { // ToDo
                    startSection();
                    break;
                }
                case 6: { // � �� ������ �� �������?
                    break;
                }
                case 7: { // � �� ������ �� �������? (���������)
                    break;
                }
                case 8: { // ������� ������� ����� 0
                    startSection();
                        storeField(d, "reminder");
                        		 		d.reminder = new Polynomial(1);
                        storeArray(d.reminder.m, 0);
                        		 		d.reminder.m[0] = new Monomial();
                        storeField(d.reminder.m[0], "c");
                        		 		d.reminder.m[0].c = 0;
                    storeField(d, "comment");
                    					d.comment = d.spol.toString() + " % G2 = " + d.spol.toString();
                    storeField(d.visualizer, "labels");
                    					d.visualizer.labels = d.visualizer.printResult();
                    break;
                }
                case 9: { // ����������
                    startSection();
                        storeField(d, "reminder");
                        	 			d.reminder = new Polynomial(d.spol.length());
                        storeArray(d.reminder.m, 0);
                        	 			d.reminder.m[0] = new Monomial();
                    storeField(d, "rem_lt");
                    					d.rem_lt = d.spol.length();
                    storeField(d, "rem_t");
                    					d.rem_t = new Polynomial(d.rem_lt);
                    storeField(d, "rem_i");
                    					d.rem_i = 0;
                    storeField(d, "rem_lg");
                    					d.rem_lg = length(d.g);
                    break;
                }
                case 10: { // ����������� spol � t
                    break;
                }
                case 11: { // ���������� � �����
                    startSection();
                        storeArray(d.rem_t.m, d.rem_i);
                        		 			d.rem_t.m[d.rem_i] = new Monomial();
                    storeField(d.rem_t.m[d.rem_i], "c");
                    						d.rem_t.m[d.rem_i].c = d.spol.m[d.rem_i].c;
                    storeField(d, "rem_j");
                    						d.rem_j = 0;
                    break;
                }
                case 12: { // ����������� spol[i] � t[i]
                    break;
                }
                case 13: { // ����������� spol[i].deg � t[i].deg
                    startSection();
                    storeArray(d.rem_t.m[d.rem_i].deg, d.rem_j);
                    							d.rem_t.m[d.rem_i].deg[d.rem_j] = d.spol.m[d.rem_i].deg[d.rem_j];
                    storeField(d, "rem_j");
                    							d.rem_j = d.rem_j + 1;
                    break;
                }
                case 14: { // i++
                    startSection();
                    storeField(d, "rem_i");
                    						d.rem_i = d.rem_i + 1;
                    break;
                }
                case 15: { // ������� ��������� ������
                    break;
                }
                case 16: { // ���������� � �����
                    startSection();
                    storeField(d, "rem_er");
                    						d.rem_er = true;
                    storeField(d, "rem_i");
                    						d.rem_i = 0;
                    break;
                }
                case 17: { // ������� t �� �������� g
                    break;
                }
                case 18: { // �������� ��������� LT(t) �� LT(g[i])
                    startSection();
                    storeField(d, "checked");
                    							d.checked = new int[] {- d.rem_i - 1};
                    break;
                }
                case 19: { // ������� ����� t ������� �� ������� ����� g[i]
                    break;
                }
                case 20: { // ������� ����� t ������� �� ������� ����� g[i] (���������)
                    break;
                }
                case 21: { // ������� ����� t ������� �� ������� ����� g[i]
                    startSection();
                    break;
                }
                case 22: { // ����� t �� g[i]
                    startSection();
                    storeField(d, "rem_tr");
                    								d.rem_tr = multiply(d.g[d.rem_i], divide(d.rem_t.m[0],d.g[d.rem_i].m[0]));
                    storeField(d, "rem_er");
                    								d.rem_er = false;
                    break;
                }
                case 23: { // ����� t �� g[i]
                    startSection();
                    storeField(d, "rem_tr1");
                    								d.rem_tr1 = subtract(d.rem_t, d.rem_tr);
                    storeField(d.visualizer, "labels");
                    								d.visualizer.labels = d.visualizer.hide();
                    storeField(d.visualizer, "labels");
                    								d.visualizer.labels = d.visualizer.printAction('%', d.rem_t, d.g[d.rem_i], d.rem_tr1, 1);
                    break;
                }
                case 24: { // ���������� �������
                    startSection();
                    storeField(d, "rem_t");
                    								d.rem_t = d.rem_tr1;
                    break;
                }
                case 25: { // �� �������
                    startSection();
                    break;
                }
                case 26: { // Increment of i
                    startSection();
                    storeField(d, "rem_i");
                    							d.rem_i = d.rem_i + 1;
                    break;
                }
                case 27: { // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������
                    break;
                }
                case 28: { // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ (���������)
                    break;
                }
                case 29: { // ����������� � ������� LT(t)
                    startSection();
                    storeField(d, "checked");
                    							d.checked = null;
                    storeField(d, "rem_tm");
                    							d.rem_tm = new Monomial(d.rem_t.m[0]);
                    storeField(d, "rem_tp");
                    							d.rem_tp = new Polynomial(d.rem_t);
                    storeField(d, "rem_t");
                    							d.rem_t = add(d.rem_t, new Monomial(multiply(d.rem_tm, new Monomial(-1))));
                    storeField(d, "reminder");
                    							d.reminder = new Polynomial(add(d.reminder, d.rem_tm));
                    storeField(d, "comment");
                    							d.comment = d.showS + d.rem_t.toString();
                    storeField(d.visualizer, "labels");
                    							d.visualizer.labels = d.visualizer.printResult();
                    storeField(d.visualizer, "labels");
                    							d.visualizer.labels = d.visualizer.printReminder(d.reminder);
                    break;
                }
                case 30: { // ���������� t[0] � �������
                    startSection();
                    break;
                }
                case 31: { // ���������� � �����
                    startSection();
                    storeField(d, "rem_z");
                    							d.rem_z = 0;
                    break;
                }
            }
        }

        /**
          * ������� ���� ��� �������� �����.
          */
        protected void doStepBackward(int level) {
            // ��������� �������� � ������� ���������
            switch (state) {
                case 1: { // if showS
                    break;
                }
                case 2: { // if showS (���������)
                    break;
                }
                case 3: { // ������� S
                    restoreSection();
                    break;
                }
                case 4: { // �� ������� S
                    restoreSection();
                    break;
                }
                case 5: { // ToDo
                    restoreSection();
                    break;
                }
                case 6: { // � �� ������ �� �������?
                    break;
                }
                case 7: { // � �� ������ �� �������? (���������)
                    break;
                }
                case 8: { // ������� ������� ����� 0
                    restoreSection();
                    break;
                }
                case 9: { // ����������
                    restoreSection();
                    break;
                }
                case 10: { // ����������� spol � t
                    break;
                }
                case 11: { // ���������� � �����
                    restoreSection();
                    break;
                }
                case 12: { // ����������� spol[i] � t[i]
                    break;
                }
                case 13: { // ����������� spol[i].deg � t[i].deg
                    restoreSection();
                    break;
                }
                case 14: { // i++
                    restoreSection();
                    break;
                }
                case 15: { // ������� ��������� ������
                    break;
                }
                case 16: { // ���������� � �����
                    restoreSection();
                    break;
                }
                case 17: { // ������� t �� �������� g
                    break;
                }
                case 18: { // �������� ��������� LT(t) �� LT(g[i])
                    restoreSection();
                    break;
                }
                case 19: { // ������� ����� t ������� �� ������� ����� g[i]
                    break;
                }
                case 20: { // ������� ����� t ������� �� ������� ����� g[i] (���������)
                    break;
                }
                case 21: { // ������� ����� t ������� �� ������� ����� g[i]
                    restoreSection();
                    break;
                }
                case 22: { // ����� t �� g[i]
                    restoreSection();
                    break;
                }
                case 23: { // ����� t �� g[i]
                    restoreSection();
                    break;
                }
                case 24: { // ���������� �������
                    restoreSection();
                    break;
                }
                case 25: { // �� �������
                    restoreSection();
                    break;
                }
                case 26: { // Increment of i
                    restoreSection();
                    break;
                }
                case 27: { // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������
                    break;
                }
                case 28: { // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ (���������)
                    break;
                }
                case 29: { // ����������� � ������� LT(t)
                    restoreSection();
                    break;
                }
                case 30: { // ���������� t[0] � �������
                    restoreSection();
                    break;
                }
                case 31: { // ���������� � �����
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // if showS
                    state = START_STATE; 
                    break;
                }
                case 2: { // if showS (���������)
                    if (stack.popBoolean()) {
                        state = 3; // ������� S
                    } else {
                        state = 4; // �� ������� S
                    }
                    break;
                }
                case 3: { // ������� S
                    state = 1; // if showS
                    break;
                }
                case 4: { // �� ������� S
                    state = 1; // if showS
                    break;
                }
                case 5: { // ToDo
                    state = 2; // if showS (���������)
                    break;
                }
                case 6: { // � �� ������ �� �������?
                    state = 5; // ToDo
                    break;
                }
                case 7: { // � �� ������ �� �������? (���������)
                    if (stack.popBoolean()) {
                        state = 8; // ������� ������� ����� 0
                    } else {
                        state = 15; // ������� ��������� ������
                    }
                    break;
                }
                case 8: { // ������� ������� ����� 0
                    state = 6; // � �� ������ �� �������?
                    break;
                }
                case 9: { // ����������
                    state = 6; // � �� ������ �� �������?
                    break;
                }
                case 10: { // ����������� spol � t
                    if (stack.popBoolean()) {
                        state = 14; // i++
                    } else {
                        state = 9; // ����������
                    }
                    break;
                }
                case 11: { // ���������� � �����
                    state = 10; // ����������� spol � t
                    break;
                }
                case 12: { // ����������� spol[i] � t[i]
                    if (stack.popBoolean()) {
                        state = 13; // ����������� spol[i].deg � t[i].deg
                    } else {
                        state = 11; // ���������� � �����
                    }
                    break;
                }
                case 13: { // ����������� spol[i].deg � t[i].deg
                    state = 12; // ����������� spol[i] � t[i]
                    break;
                }
                case 14: { // i++
                    state = 12; // ����������� spol[i] � t[i]
                    break;
                }
                case 15: { // ������� ��������� ������
                    if (stack.popBoolean()) {
                        state = 28; // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ (���������)
                    } else {
                        state = 10; // ����������� spol � t
                    }
                    break;
                }
                case 16: { // ���������� � �����
                    state = 15; // ������� ��������� ������
                    break;
                }
                case 17: { // ������� t �� �������� g
                    if (stack.popBoolean()) {
                        state = 26; // Increment of i
                    } else {
                        state = 16; // ���������� � �����
                    }
                    break;
                }
                case 18: { // �������� ��������� LT(t) �� LT(g[i])
                    state = 17; // ������� t �� �������� g
                    break;
                }
                case 19: { // ������� ����� t ������� �� ������� ����� g[i]
                    state = 18; // �������� ��������� LT(t) �� LT(g[i])
                    break;
                }
                case 20: { // ������� ����� t ������� �� ������� ����� g[i] (���������)
                    if (stack.popBoolean()) {
                        state = 24; // ���������� �������
                    } else {
                        state = 25; // �� �������
                    }
                    break;
                }
                case 21: { // ������� ����� t ������� �� ������� ����� g[i]
                    state = 19; // ������� ����� t ������� �� ������� ����� g[i]
                    break;
                }
                case 22: { // ����� t �� g[i]
                    state = 21; // ������� ����� t ������� �� ������� ����� g[i]
                    break;
                }
                case 23: { // ����� t �� g[i]
                    state = 22; // ����� t �� g[i]
                    break;
                }
                case 24: { // ���������� �������
                    state = 23; // ����� t �� g[i]
                    break;
                }
                case 25: { // �� �������
                    state = 19; // ������� ����� t ������� �� ������� ����� g[i]
                    break;
                }
                case 26: { // Increment of i
                    state = 20; // ������� ����� t ������� �� ������� ����� g[i] (���������)
                    break;
                }
                case 27: { // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������
                    state = 17; // ������� t �� �������� g
                    break;
                }
                case 28: { // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 31; // ���������� � �����
                    } else {
                        state = 27; // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������
                    }
                    break;
                }
                case 29: { // ����������� � ������� LT(t)
                    state = 27; // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������
                    break;
                }
                case 30: { // ���������� t[0] � �������
                    state = 29; // ����������� � ������� LT(t)
                    break;
                }
                case 31: { // ���������� � �����
                    state = 30; // ���������� t[0] � �������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 7; // � �� ������ �� �������? (���������)
                    break;
                }
            }
        }

        /**
          * ����������� � �������� ���������
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // ����� �����������
            switch (state) {
                case 5: { // ToDo
                    comment = GroebnerBasis.this.getComment("rem.todo"); 
                    args = new Object[]{d.spol}; 
                    break;
                }
                case 6: { // � �� ������ �� �������?
                    if (d.spol.m.length == 0) {
                        comment = GroebnerBasis.this.getComment("rem.CondPolynomialIsEmpty.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("rem.CondPolynomialIsEmpty.false"); 
                    }
                    args = new Object[]{d.spol}; 
                    break;
                }
                case 8: { // ������� ������� ����� 0
                    comment = GroebnerBasis.this.getComment("rem.EmptyPolynom"); 
                    break;
                }
                case 18: { // �������� ��������� LT(t) �� LT(g[i])
                    comment = GroebnerBasis.this.getComment("rem.BeforeIf"); 
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i], d.g[d.rem_i].m[0]}; 
                    break;
                }
                case 19: { // ������� ����� t ������� �� ������� ����� g[i]
                    if (isDivisibleBy(d.rem_t.m[0], d.g[d.rem_i].m[0])) {
                        comment = GroebnerBasis.this.getComment("rem.CondTIsDivisibleByGi.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("rem.CondTIsDivisibleByGi.false"); 
                    }
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i], d.g[d.rem_i].m[0]}; 
                    break;
                }
                case 21: { // ������� ����� t ������� �� ������� ����� g[i]
                    comment = GroebnerBasis.this.getComment("rem.TIsDivisibleByGi"); 
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i], d.g[d.rem_i].m[0], d.rem_t}; 
                    break;
                }
                case 22: { // ����� t �� g[i]
                    comment = GroebnerBasis.this.getComment("rem.DivisingTByGi1"); 
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i].m[0]}; 
                    break;
                }
                case 23: { // ����� t �� g[i]
                    comment = GroebnerBasis.this.getComment("rem.DivisingTByGi"); 
                    args = new Object[]{d.rem_t, d.g[d.rem_i], d.rem_tr1}; 
                    break;
                }
                case 25: { // �� �������
                    comment = GroebnerBasis.this.getComment("rem.TIsNotDivisibleByGi"); 
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i], d.g[d.rem_i].m[0], d.rem_t}; 
                    break;
                }
                case 27: { // ������� ���� t ({0}) �� ������� �� �� ���� �� ������� ������ ��������� ������
                    if (d.rem_er) {
                        comment = GroebnerBasis.this.getComment("rem.ThereIsSomethingNewToAdd.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("rem.ThereIsSomethingNewToAdd.false"); 
                    }
                    args = new Object[]{d.rem_t.m[0], d.rem_t}; 
                    break;
                }
                case 29: { // ����������� � ������� LT(t)
                    comment = GroebnerBasis.this.getComment("rem.AddingLT2Rem"); 
                    args = new Object[]{d.rem_tm, d.reminder, d.rem_tp}; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * ��������� �������� �� ��������� ���������
          */
        public void drawState() {
            switch (state) {
                case 5: { // ToDo
                    				d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 8: { // ������� ������� ����� 0
                    					d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    					d.visualizer.redraw();
                    break;
                }
                case 18: { // �������� ��������� LT(t) �� LT(g[i])
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 21: { // ������� ����� t ������� �� ������� ����� g[i]
                    								d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 23: { // ����� t �� g[i]
                    								d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    								d.visualizer.redraw();
                    break;
                }
                case 25: { // �� �������
                    								d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 29: { // ����������� � ������� LT(t)
                    							d.visualizer.redraw();
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 30: { // ���������� t[0] � �������
                    break;
                }
            }
        }
    }

    /**
      * ���������� ������ ������� ��� g1.
      */
    private final class MakingBasis extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 19;

        /**
          * �����������.
          */
        public MakingBasis() {
            super( 
                "MakingBasis", 
                0, // ����� ���������� ��������� 
                19, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "���������� � �����", 
                    "������� ����", 
                    "���������� � ���������� ������", 
                    "������� ���� ��� ���������", 
                    "���������� � ����������� �����", 
                    "������� ���� ��� ���������", 
                    "������� ��������� � ���������� ��������", 
                    "���������� S-�������� �� i-�� � j-�� ���������", 
                    "��������� S-������� 2 ��������� �� g1 (�������)", 
                    "��������� G1 � G2", 
                    "������� �� ������� �������� �� ����� (�������)", 
                    "�������� �� ������", 
                    "S-������� �� ������� �� g", 
                    "S-������� �� ������� �� g (���������)", 
                    "���������� �������� � ����� (�������)", 
                    "���������� ������� � �����", 
                    "Increment of J", 
                    "Increment of I", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ���������� � ����� 
                    -1, // ������� ���� 
                    -1, // ���������� � ���������� ������ 
                    -1, // ������� ���� ��� ��������� 
                    -1, // ���������� � ����������� ����� 
                    -1, // ������� ���� ��� ��������� 
                    0, // ������� ��������� � ���������� �������� 
                    -1, // ���������� S-�������� �� i-�� � j-�� ��������� 
                    CALL_AUTO_LEVEL, // ��������� S-������� 2 ��������� �� g1 (�������) 
                    0, // ��������� G1 � G2 
                    CALL_AUTO_LEVEL, // ������� �� ������� �������� �� ����� (�������) 
                    -1, // �������� �� ������ 
                    0, // S-������� �� ������� �� g 
                    -1, // S-������� �� ������� �� g (���������) 
                    CALL_AUTO_LEVEL, // ���������� �������� � ����� (�������) 
                    -1, // ���������� ������� � ����� 
                    -1, // Increment of J 
                    -1, // Increment of I 
                    Integer.MAX_VALUE, // �������� ��������� 
                } 
            ); 
        }

        /**
          * ������� ���� ��� �������� � �����.
          */
        protected void doStepForward(int level) {
            // ������� � ��������� ���������
            switch (state) {
                case START_STATE: { // ��������� ���������
                    state = 1; // ���������� � �����
                    break;
                }
                case 1: { // ���������� � �����
                    stack.pushBoolean(false); 
                    state = 2; // ������� ����
                    break;
                }
                case 2: { // ������� ����
                    if (d.MakingBasis_b) {
                        state = 3; // ���������� � ���������� ������
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ���������� � ���������� ������
                    stack.pushBoolean(false); 
                    state = 4; // ������� ���� ��� ���������
                    break;
                }
                case 4: { // ������� ���� ��� ���������
                    if (d.MakingBasis_i < d.MakingBasis_lg1) {
                        state = 5; // ���������� � ����������� �����
                    } else {
                        stack.pushBoolean(true); 
                        state = 2; // ������� ����
                    }
                    break;
                }
                case 5: { // ���������� � ����������� �����
                    stack.pushBoolean(false); 
                    state = 6; // ������� ���� ��� ���������
                    break;
                }
                case 6: { // ������� ���� ��� ���������
                    if (d.MakingBasis_j < d.MakingBasis_lg1) {
                        state = 7; // ������� ��������� � ���������� ��������
                    } else {
                        state = 18; // Increment of I
                    }
                    break;
                }
                case 7: { // ������� ��������� � ���������� ��������
                    state = 8; // ���������� S-�������� �� i-�� � j-�� ���������
                    break;
                }
                case 8: { // ���������� S-�������� �� i-�� � j-�� ���������
                    state = 9; // ��������� S-������� 2 ��������� �� g1 (�������)
                    break;
                }
                case 9: { // ��������� S-������� 2 ��������� �� g1 (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // ��������� G1 � G2
                    }
                    break;
                }
                case 10: { // ��������� G1 � G2
                    state = 11; // ������� �� ������� �������� �� ����� (�������)
                    break;
                }
                case 11: { // ������� �� ������� �������� �� ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 12; // �������� �� ������
                    }
                    break;
                }
                case 12: { // �������� �� ������
                    state = 13; // S-������� �� ������� �� g
                    break;
                }
                case 13: { // S-������� �� ������� �� g
                    if (!eq0(d.reminder)) {
                        state = 15; // ���������� �������� � ����� (�������)
                    } else {
                        stack.pushBoolean(false); 
                        state = 14; // S-������� �� ������� �� g (���������)
                    }
                    break;
                }
                case 14: { // S-������� �� ������� �� g (���������)
                    state = 17; // Increment of J
                    break;
                }
                case 15: { // ���������� �������� � ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 16; // ���������� ������� � �����
                    }
                    break;
                }
                case 16: { // ���������� ������� � �����
                    stack.pushBoolean(true); 
                    state = 14; // S-������� �� ������� �� g (���������)
                    break;
                }
                case 17: { // Increment of J
                    stack.pushBoolean(true); 
                    state = 6; // ������� ���� ��� ���������
                    break;
                }
                case 18: { // Increment of I
                    stack.pushBoolean(true); 
                    state = 4; // ������� ���� ��� ���������
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ���������� � �����
                    startSection();
                    storeField(d, "MakingBasis_b");
                    				d.MakingBasis_b = true;
                    break;
                }
                case 2: { // ������� ����
                    break;
                }
                case 3: { // ���������� � ���������� ������
                    startSection();
                    storeField(d, "MakingBasis_b");
                    					d.MakingBasis_b = false;
                    storeField(d, "MakingBasis_lg1");
                    					d.MakingBasis_lg1 = length(d.g1);
                    storeField(d, "g");
                    					d.g = g1ToG();
                    storeField(d, "g1");
                    					d.g1 = gToG1();
                    storeField(d, "MakingBasis_i");
                    					d.MakingBasis_i = 0;
                    break;
                }
                case 4: { // ������� ���� ��� ���������
                    break;
                }
                case 5: { // ���������� � ����������� �����
                    startSection();
                    storeField(d, "MakingBasis_j");
                    						d.MakingBasis_j = d.MakingBasis_i + 1;
                    break;
                }
                case 6: { // ������� ���� ��� ���������
                    break;
                }
                case 7: { // ������� ��������� � ���������� ��������
                    startSection();
                    break;
                }
                case 8: { // ���������� S-�������� �� i-�� � j-�� ���������
                    startSection();
                    storeField(d, "numb");
                    							d.numb = d.MakingBasis_i;
                    storeField(d, "numb2");
                    							d.numb2 = d.MakingBasis_j;
                    storeField(d, "checked");
                    							d.checked = new int[] {d.MakingBasis_i, d.MakingBasis_j};
                    break;
                }
                case 9: { // ��������� S-������� 2 ��������� �� g1 (�������)
                    if (child == null) {
                        child = new spolynomial(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // ��������� G1 � G2
                    startSection();
                    storeField(d, "checked");
                    							d.checked = null;
                    storeField(d, "g");
                    							d.g = g1ToG();
                    storeField(d.visualizer, "labels");
                    							d.visualizer.labels = d.visualizer.hideAction();
                    break;
                }
                case 11: { // ������� �� ������� �������� �� ����� (�������)
                    if (child == null) {
                        child = new rem(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 12: { // �������� �� ������
                    startSection();
                    storeField(d.visualizer, "labels");
                    							d.visualizer.labels = d.visualizer.hide();
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    							d.visualizer.redraw();
                    break;
                }
                case 13: { // S-������� �� ������� �� g
                    break;
                }
                case 14: { // S-������� �� ������� �� g (���������)
                    break;
                }
                case 15: { // ���������� �������� � ����� (�������)
                    if (child == null) {
                        child = new AddPolynomial2Ideal(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 16: { // ���������� ������� � �����
                    startSection();
                    storeField(d, "MakingBasis_b");
                    								d.MakingBasis_b = true;
                    break;
                }
                case 17: { // Increment of J
                    startSection();
                    storeField(d, "MakingBasis_j");
                    							d.MakingBasis_j = d.MakingBasis_j + 1;
                    break;
                }
                case 18: { // Increment of I
                    startSection();
                    storeField(d, "MakingBasis_i");
                    						d.MakingBasis_i = d.MakingBasis_i + 1;
                    break;
                }
            }
        }

        /**
          * ������� ���� ��� �������� �����.
          */
        protected void doStepBackward(int level) {
            // ��������� �������� � ������� ���������
            switch (state) {
                case 1: { // ���������� � �����
                    restoreSection();
                    break;
                }
                case 2: { // ������� ����
                    break;
                }
                case 3: { // ���������� � ���������� ������
                    restoreSection();
                    break;
                }
                case 4: { // ������� ���� ��� ���������
                    break;
                }
                case 5: { // ���������� � ����������� �����
                    restoreSection();
                    break;
                }
                case 6: { // ������� ���� ��� ���������
                    break;
                }
                case 7: { // ������� ��������� � ���������� ��������
                    restoreSection();
                    break;
                }
                case 8: { // ���������� S-�������� �� i-�� � j-�� ���������
                    restoreSection();
                    break;
                }
                case 9: { // ��������� S-������� 2 ��������� �� g1 (�������)
                    if (child == null) {
                        child = new spolynomial(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // ��������� G1 � G2
                    restoreSection();
                    break;
                }
                case 11: { // ������� �� ������� �������� �� ����� (�������)
                    if (child == null) {
                        child = new rem(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 12: { // �������� �� ������
                    restoreSection();
                    break;
                }
                case 13: { // S-������� �� ������� �� g
                    break;
                }
                case 14: { // S-������� �� ������� �� g (���������)
                    break;
                }
                case 15: { // ���������� �������� � ����� (�������)
                    if (child == null) {
                        child = new AddPolynomial2Ideal(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 16: { // ���������� ������� � �����
                    restoreSection();
                    break;
                }
                case 17: { // Increment of J
                    restoreSection();
                    break;
                }
                case 18: { // Increment of I
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ���������� � �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������� ����
                    if (stack.popBoolean()) {
                        state = 4; // ������� ���� ��� ���������
                    } else {
                        state = 1; // ���������� � �����
                    }
                    break;
                }
                case 3: { // ���������� � ���������� ������
                    state = 2; // ������� ����
                    break;
                }
                case 4: { // ������� ���� ��� ���������
                    if (stack.popBoolean()) {
                        state = 18; // Increment of I
                    } else {
                        state = 3; // ���������� � ���������� ������
                    }
                    break;
                }
                case 5: { // ���������� � ����������� �����
                    state = 4; // ������� ���� ��� ���������
                    break;
                }
                case 6: { // ������� ���� ��� ���������
                    if (stack.popBoolean()) {
                        state = 17; // Increment of J
                    } else {
                        state = 5; // ���������� � ����������� �����
                    }
                    break;
                }
                case 7: { // ������� ��������� � ���������� ��������
                    state = 6; // ������� ���� ��� ���������
                    break;
                }
                case 8: { // ���������� S-�������� �� i-�� � j-�� ���������
                    state = 7; // ������� ��������� � ���������� ��������
                    break;
                }
                case 9: { // ��������� S-������� 2 ��������� �� g1 (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // ���������� S-�������� �� i-�� � j-�� ���������
                    }
                    break;
                }
                case 10: { // ��������� G1 � G2
                    state = 9; // ��������� S-������� 2 ��������� �� g1 (�������)
                    break;
                }
                case 11: { // ������� �� ������� �������� �� ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 10; // ��������� G1 � G2
                    }
                    break;
                }
                case 12: { // �������� �� ������
                    state = 11; // ������� �� ������� �������� �� ����� (�������)
                    break;
                }
                case 13: { // S-������� �� ������� �� g
                    state = 12; // �������� �� ������
                    break;
                }
                case 14: { // S-������� �� ������� �� g (���������)
                    if (stack.popBoolean()) {
                        state = 16; // ���������� ������� � �����
                    } else {
                        state = 13; // S-������� �� ������� �� g
                    }
                    break;
                }
                case 15: { // ���������� �������� � ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 13; // S-������� �� ������� �� g
                    }
                    break;
                }
                case 16: { // ���������� ������� � �����
                    state = 15; // ���������� �������� � ����� (�������)
                    break;
                }
                case 17: { // Increment of J
                    state = 14; // S-������� �� ������� �� g (���������)
                    break;
                }
                case 18: { // Increment of I
                    state = 6; // ������� ���� ��� ���������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ������� ����
                    break;
                }
            }
        }

        /**
          * ����������� � �������� ���������
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // ����� �����������
            switch (state) {
                case 7: { // ������� ��������� � ���������� ��������
                    comment = GroebnerBasis.this.getComment("MakingBasis.PrintMessage"); 
                    break;
                }
                case 8: { // ���������� S-�������� �� i-�� � j-�� ���������
                    comment = GroebnerBasis.this.getComment("MakingBasis.MakingSPolynomial"); 
                    args = new Object[]{d.g1[d.MakingBasis_i], d.g1[d.MakingBasis_j]}; 
                    break;
                }
                case 9: { // ��������� S-������� 2 ��������� �� g1 (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // ��������� G1 � G2
                    comment = GroebnerBasis.this.getComment("MakingBasis.G1ToG2"); 
                    break;
                }
                case 11: { // ������� �� ������� �������� �� ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 12: { // �������� �� ������
                    comment = GroebnerBasis.this.getComment("MakingBasis.draw"); 
                    break;
                }
                case 13: { // S-������� �� ������� �� g
                    if (!eq0(d.reminder)) {
                        comment = GroebnerBasis.this.getComment("MakingBasis.CondRemSNotZero.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("MakingBasis.CondRemSNotZero.false"); 
                    }
                    break;
                }
                case 15: { // ���������� �������� � ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * ��������� �������� �� ��������� ���������
          */
        public void drawState() {
            switch (state) {
                case 7: { // ������� ��������� � ���������� ��������
                    							d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    break;
                }
                case 9: { // ��������� S-������� 2 ��������� �� g1 (�������)
                    child.drawState(); 
                    break;
                }
                case 10: { // ��������� G1 � G2
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 11: { // ������� �� ������� �������� �� ����� (�������)
                    child.drawState(); 
                    break;
                }
                case 15: { // ���������� �������� � ����� (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * ������������� ����������� ������.
      */
    private final class reduce extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 17;

        /**
          * �����������.
          */
        public reduce() {
            super( 
                "reduce", 
                0, // ����� ���������� ��������� 
                17, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "���������� � �����", 
                    "������� ����", 
                    "���������� � �����", 
                    "������� ��������� g1", 
                    "Deleting g[i]", 
                    "Checking g1[i]", 
                    "������� �� ������� �������� �� ����� (�������)", 
                    "���� ������� �� ����� ������ ��������", 
                    "���� ������� �� ����� ������ �������� (���������)", 
                    "������", 
                    "�������� �� 0", 
                    "�������� �� 0 (���������)", 
                    "�� ���������", 
                    "������ �������� � ������ �� ������� �� ������� ��� �� �����", 
                    "���������� ��������", 
                    "���������� ��������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ���������� � ����� 
                    -1, // ������� ���� 
                    -1, // ���������� � ����� 
                    0, // ������� ��������� g1 
                    0, // Deleting g[i] 
                    0, // Checking g1[i] 
                    CALL_AUTO_LEVEL, // ������� �� ������� �������� �� ����� (�������) 
                    0, // ���� ������� �� ����� ������ �������� 
                    -1, // ���� ������� �� ����� ������ �������� (���������) 
                    -1, // ������ 
                    -1, // �������� �� 0 
                    -1, // �������� �� 0 (���������) 
                    0, // �� ��������� 
                    1, // ������ �������� � ������ �� ������� �� ������� ��� �� ����� 
                    -1, // ���������� �������� 
                    0, // ���������� �������� 
                    Integer.MAX_VALUE, // �������� ��������� 
                } 
            ); 
        }

        /**
          * ������� ���� ��� �������� � �����.
          */
        protected void doStepForward(int level) {
            // ������� � ��������� ���������
            switch (state) {
                case START_STATE: { // ��������� ���������
                    state = 1; // ���������� � �����
                    break;
                }
                case 1: { // ���������� � �����
                    stack.pushBoolean(false); 
                    state = 2; // ������� ����
                    break;
                }
                case 2: { // ������� ����
                    if (d.reduce_b) {
                        state = 3; // ���������� � �����
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ���������� � �����
                    stack.pushBoolean(false); 
                    state = 4; // ������� ��������� g1
                    break;
                }
                case 4: { // ������� ��������� g1
                    if ((!d.reduce_b) && (d.reduce_i < d.reduce_lg)) {
                        state = 5; // Deleting g[i]
                    } else {
                        state = 16; // ���������� ��������
                    }
                    break;
                }
                case 5: { // Deleting g[i]
                    state = 6; // Checking g1[i]
                    break;
                }
                case 6: { // Checking g1[i]
                    state = 7; // ������� �� ������� �������� �� ����� (�������)
                    break;
                }
                case 7: { // ������� �� ������� �������� �� ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // ���� ������� �� ����� ������ ��������
                    }
                    break;
                }
                case 8: { // ���� ������� �� ����� ������ ��������
                    if (!d.g1[d.reduce_i].equals(d.reminder)) {
                        state = 10; // ������
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // ���� ������� �� ����� ������ �������� (���������)
                    }
                    break;
                }
                case 9: { // ���� ������� �� ����� ������ �������� (���������)
                    state = 11; // �������� �� 0
                    break;
                }
                case 10: { // ������
                    stack.pushBoolean(true); 
                    state = 9; // ���� ������� �� ����� ������ �������� (���������)
                    break;
                }
                case 11: { // �������� �� 0
                    if (eq0(d.reminder)) {
                        state = 13; // �� ���������
                    } else {
                        state = 14; // ������ �������� � ������ �� ������� �� ������� ��� �� �����
                    }
                    break;
                }
                case 12: { // �������� �� 0 (���������)
                    state = 15; // ���������� ��������
                    break;
                }
                case 13: { // �� ���������
                    stack.pushBoolean(true); 
                    state = 12; // �������� �� 0 (���������)
                    break;
                }
                case 14: { // ������ �������� � ������ �� ������� �� ������� ��� �� �����
                    stack.pushBoolean(false); 
                    state = 12; // �������� �� 0 (���������)
                    break;
                }
                case 15: { // ���������� ��������
                    stack.pushBoolean(true); 
                    state = 4; // ������� ��������� g1
                    break;
                }
                case 16: { // ���������� ��������
                    stack.pushBoolean(true); 
                    state = 2; // ������� ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ���������� � �����
                    startSection();
                    storeField(d, "reduce_b");
                    				d.reduce_b = true;
                    storeField(d, "showG2");
                    				d.showG2 = true;
                    break;
                }
                case 2: { // ������� ����
                    break;
                }
                case 3: { // ���������� � �����
                    startSection();
                    storeField(d, "reduce_b");
                    					d.reduce_b = false;
                    storeField(d, "reduce_lg");
                    					d.reduce_lg = length(d.g);
                    storeField(d, "reduce_i");
                    					d.reduce_i = 0;
                    break;
                }
                case 4: { // ������� ��������� g1
                    break;
                }
                case 5: { // Deleting g[i]
                    startSection();
                    storeField(d, "checked");
                    						d.checked = new int[] {d.reduce_i};
                    storeField(d, "g");
                    						d.g = subtract(d.reduce_i);
                    break;
                }
                case 6: { // Checking g1[i]
                    startSection();
                    storeField(d, "showS");
                    						d.showS = "";
                    						copy2spol(d.g1[d.reduce_i]);
                    break;
                }
                case 7: { // ������� �� ������� �������� �� ����� (�������)
                    if (child == null) {
                        child = new rem(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // ���� ������� �� ����� ������ ��������
                    break;
                }
                case 9: { // ���� ������� �� ����� ������ �������� (���������)
                    break;
                }
                case 10: { // ������
                    startSection();
                    storeField(d, "reduce_b");
                    							d.reduce_b = true;
                    break;
                }
                case 11: { // �������� �� 0
                    break;
                }
                case 12: { // �������� �� 0 (���������)
                    break;
                }
                case 13: { // �� ���������
                    startSection();
                    break;
                }
                case 14: { // ������ �������� � ������ �� ������� �� ������� ��� �� �����
                    startSection();
                    storeField(d, "checked");
                    							d.checked = new int[] {d.reduce_i};
                    storeField(d, "g");
                    							d.g = copy2x(d.reduce_i);
                    break;
                }
                case 15: { // ���������� ��������
                    startSection();
                    storeField(d, "reduce_i");
                    						d.reduce_i = d.reduce_i + 1;
                    break;
                }
                case 16: { // ���������� ��������
                    startSection();
                    storeField(d, "g1");
                    					d.g1 = gToG1();
                    storeField(d, "g");
                    					d.g = g1ToG();
                    break;
                }
            }
        }

        /**
          * ������� ���� ��� �������� �����.
          */
        protected void doStepBackward(int level) {
            // ��������� �������� � ������� ���������
            switch (state) {
                case 1: { // ���������� � �����
                    restoreSection();
                    break;
                }
                case 2: { // ������� ����
                    break;
                }
                case 3: { // ���������� � �����
                    restoreSection();
                    break;
                }
                case 4: { // ������� ��������� g1
                    break;
                }
                case 5: { // Deleting g[i]
                    restoreSection();
                    break;
                }
                case 6: { // Checking g1[i]
                    restoreSection();
                    break;
                }
                case 7: { // ������� �� ������� �������� �� ����� (�������)
                    if (child == null) {
                        child = new rem(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // ���� ������� �� ����� ������ ��������
                    break;
                }
                case 9: { // ���� ������� �� ����� ������ �������� (���������)
                    break;
                }
                case 10: { // ������
                    restoreSection();
                    break;
                }
                case 11: { // �������� �� 0
                    break;
                }
                case 12: { // �������� �� 0 (���������)
                    break;
                }
                case 13: { // �� ���������
                    restoreSection();
                    break;
                }
                case 14: { // ������ �������� � ������ �� ������� �� ������� ��� �� �����
                    restoreSection();
                    break;
                }
                case 15: { // ���������� ��������
                    restoreSection();
                    break;
                }
                case 16: { // ���������� ��������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ���������� � �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������� ����
                    if (stack.popBoolean()) {
                        state = 16; // ���������� ��������
                    } else {
                        state = 1; // ���������� � �����
                    }
                    break;
                }
                case 3: { // ���������� � �����
                    state = 2; // ������� ����
                    break;
                }
                case 4: { // ������� ��������� g1
                    if (stack.popBoolean()) {
                        state = 15; // ���������� ��������
                    } else {
                        state = 3; // ���������� � �����
                    }
                    break;
                }
                case 5: { // Deleting g[i]
                    state = 4; // ������� ��������� g1
                    break;
                }
                case 6: { // Checking g1[i]
                    state = 5; // Deleting g[i]
                    break;
                }
                case 7: { // ������� �� ������� �������� �� ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // Checking g1[i]
                    }
                    break;
                }
                case 8: { // ���� ������� �� ����� ������ ��������
                    state = 7; // ������� �� ������� �������� �� ����� (�������)
                    break;
                }
                case 9: { // ���� ������� �� ����� ������ �������� (���������)
                    if (stack.popBoolean()) {
                        state = 10; // ������
                    } else {
                        state = 8; // ���� ������� �� ����� ������ ��������
                    }
                    break;
                }
                case 10: { // ������
                    state = 8; // ���� ������� �� ����� ������ ��������
                    break;
                }
                case 11: { // �������� �� 0
                    state = 9; // ���� ������� �� ����� ������ �������� (���������)
                    break;
                }
                case 12: { // �������� �� 0 (���������)
                    if (stack.popBoolean()) {
                        state = 13; // �� ���������
                    } else {
                        state = 14; // ������ �������� � ������ �� ������� �� ������� ��� �� �����
                    }
                    break;
                }
                case 13: { // �� ���������
                    state = 11; // �������� �� 0
                    break;
                }
                case 14: { // ������ �������� � ������ �� ������� �� ������� ��� �� �����
                    state = 11; // �������� �� 0
                    break;
                }
                case 15: { // ���������� ��������
                    state = 12; // �������� �� 0 (���������)
                    break;
                }
                case 16: { // ���������� ��������
                    state = 4; // ������� ��������� g1
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ������� ����
                    break;
                }
            }
        }

        /**
          * ����������� � �������� ���������
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // ����� �����������
            switch (state) {
                case 4: { // ������� ��������� g1
                    if ((!d.reduce_b) && (d.reduce_i < d.reduce_lg)) {
                        comment = GroebnerBasis.this.getComment("reduce.LoopI.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("reduce.LoopI.false"); 
                    }
                    break;
                }
                case 5: { // Deleting g[i]
                    comment = GroebnerBasis.this.getComment("reduce.DeleteI"); 
                    args = new Object[]{d.g1[d.reduce_i]}; 
                    break;
                }
                case 6: { // Checking g1[i]
                    comment = GroebnerBasis.this.getComment("reduce.CheckingI"); 
                    args = new Object[]{d.g1[d.reduce_i]}; 
                    break;
                }
                case 7: { // ������� �� ������� �������� �� ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 8: { // ���� ������� �� ����� ������ ��������
                    if (!d.g1[d.reduce_i].equals(d.reminder)) {
                        comment = GroebnerBasis.this.getComment("reduce.CondPolEqToRem.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("reduce.CondPolEqToRem.false"); 
                    }
                    args = new Object[]{d.g1[d.reduce_i], d.reminder}; 
                    break;
                }
                case 13: { // �� ���������
                    comment = GroebnerBasis.this.getComment("reduce.NothingToDo"); 
                    break;
                }
                case 14: { // ������ �������� � ������ �� ������� �� ������� ��� �� �����
                    comment = GroebnerBasis.this.getComment("reduce.ReplacingPolynomialByReminder"); 
                    args = new Object[]{d.g1[d.reduce_i], d.reminder}; 
                    break;
                }
                case 15: { // ���������� ��������
                    comment = GroebnerBasis.this.getComment("reduce.EndingIteration"); 
                    break;
                }
                case 16: { // ���������� ��������
                    comment = GroebnerBasis.this.getComment("reduce.AfterIteration"); 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * ��������� �������� �� ��������� ���������
          */
        public void drawState() {
            switch (state) {
                case 5: { // Deleting g[i]
                    						d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 7: { // ������� �� ������� �������� �� ����� (�������)
                    child.drawState(); 
                    break;
                }
                case 8: { // ���� ������� �� ����� ������ ��������
                    						d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 13: { // �� ���������
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 14: { // ������ �������� � ������ �� ������� �� ������� ��� �� �����
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 16: { // ���������� ��������
                    					d.visualizer.printIdeals(new boolean[] {true, true});
                    break;
                }
            }
        }
    }

    /**
      * ������� �������.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 8;

        /**
          * �����������.
          */
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                8, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "����������� g1 � given.", 
                    "���������� ������ ������� ��� g1 (�������)", 
                    "BasisIsMade", 
                    "������������� ����������� ������ (�������)", 
                    "����������� � g1 ���������� ������.", 
                    "����������", 
                    "HideAll", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ����������� g1 � given. 
                    CALL_AUTO_LEVEL, // ���������� ������ ������� ��� g1 (�������) 
                    1, // BasisIsMade 
                    CALL_AUTO_LEVEL, // ������������� ����������� ������ (�������) 
                    -1, // ����������� � g1 ���������� ������. 
                    1, // ���������� 
                    -1, // HideAll 
                    Integer.MAX_VALUE, // �������� ��������� 
                } 
            ); 
        }

        /**
          * ������� ���� ��� �������� � �����.
          */
        protected void doStepForward(int level) {
            // ������� � ��������� ���������
            switch (state) {
                case START_STATE: { // ��������� ���������
                    state = 1; // ����������� g1 � given.
                    break;
                }
                case 1: { // ����������� g1 � given.
                    state = 2; // ���������� ������ ������� ��� g1 (�������)
                    break;
                }
                case 2: { // ���������� ������ ������� ��� g1 (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 3; // BasisIsMade
                    }
                    break;
                }
                case 3: { // BasisIsMade
                    state = 4; // ������������� ����������� ������ (�������)
                    break;
                }
                case 4: { // ������������� ����������� ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 5; // ����������� � g1 ���������� ������.
                    }
                    break;
                }
                case 5: { // ����������� � g1 ���������� ������.
                    state = 6; // ����������
                    break;
                }
                case 6: { // ����������
                    state = 7; // HideAll
                    break;
                }
                case 7: { // HideAll
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ����������� g1 � given.
                    startSection();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.hide();
                    				d.visualizer.redraw();
                    				g12given();
                    break;
                }
                case 2: { // ���������� ������ ������� ��� g1 (�������)
                    if (child == null) {
                        child = new MakingBasis(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 3: { // BasisIsMade
                    startSection();
                    break;
                }
                case 4: { // ������������� ����������� ������ (�������)
                    if (child == null) {
                        child = new reduce(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 5: { // ����������� � g1 ���������� ������.
                    startSection();
                    				given2g1();
                    break;
                }
                case 6: { // ����������
                    startSection();
                    storeField(d, "g");
                    				d.g = norm();
                    break;
                }
                case 7: { // HideAll
                    startSection();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.hide();
                    break;
                }
            }
        }

        /**
          * ������� ���� ��� �������� �����.
          */
        protected void doStepBackward(int level) {
            // ��������� �������� � ������� ���������
            switch (state) {
                case 1: { // ����������� g1 � given.
                    restoreSection();
                    break;
                }
                case 2: { // ���������� ������ ������� ��� g1 (�������)
                    if (child == null) {
                        child = new MakingBasis(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 3: { // BasisIsMade
                    restoreSection();
                    break;
                }
                case 4: { // ������������� ����������� ������ (�������)
                    if (child == null) {
                        child = new reduce(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 5: { // ����������� � g1 ���������� ������.
                    restoreSection();
                    break;
                }
                case 6: { // ����������
                    restoreSection();
                    break;
                }
                case 7: { // HideAll
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ����������� g1 � given.
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���������� ������ ������� ��� g1 (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // ����������� g1 � given.
                    }
                    break;
                }
                case 3: { // BasisIsMade
                    state = 2; // ���������� ������ ������� ��� g1 (�������)
                    break;
                }
                case 4: { // ������������� ����������� ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 3; // BasisIsMade
                    }
                    break;
                }
                case 5: { // ����������� � g1 ���������� ������.
                    state = 4; // ������������� ����������� ������ (�������)
                    break;
                }
                case 6: { // ����������
                    state = 5; // ����������� � g1 ���������� ������.
                    break;
                }
                case 7: { // HideAll
                    state = 6; // ����������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 7; // HideAll
                    break;
                }
            }
        }

        /**
          * ����������� � �������� ���������
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // ����� �����������
            switch (state) {
                case START_STATE: { // ��������� ���������
                    comment = GroebnerBasis.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 2: { // ���������� ������ ������� ��� g1 (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 3: { // BasisIsMade
                    comment = GroebnerBasis.this.getComment("Main.PreparingForReducing"); 
                    break;
                }
                case 4: { // ������������� ����������� ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = GroebnerBasis.this.getComment("Main.END_STATE"); 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * ��������� �������� �� ��������� ���������
          */
        public void drawState() {
            switch (state) {
                case START_STATE: { // ��������� ���������
                    				d.visualizer.hide();
                    				d.visualizer.redraw();
                    				d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    break;
                }
                case 2: { // ���������� ������ ������� ��� g1 (�������)
                    child.drawState(); 
                    break;
                }
                case 3: { // BasisIsMade
                    				d.visualizer.hide();
                    				d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    break;
                }
                case 4: { // ������������� ����������� ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 6: { // ����������
                    				d.visualizer.printIdeals(new boolean[] {true, true});
                    break;
                }
                case END_STATE: { // �������� ���������
                    				d.visualizer.redraw();
                    				d.visualizer.printIdeals(new boolean[] {true, true});
                    break;
                }
            }
        }
    }
}
