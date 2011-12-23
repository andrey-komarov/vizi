package ru.ifmo.vizi.groebner_basis;

import 
		java.util.Stack
	;
import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class GroebnerBasis extends BaseAutoReverseAutomata {
    /**
      * Возвращает количество полиномов в идеале a
      */
    int length(Polynomial[] a) {
        
        		int i;
        		for (i = 0; i < a.length; ++i) {
        			if (a[i] == null) break;
        		}
        		return i;
    }

    /**
      * Возвращает наименьшее общее кратное 2 мономов
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
      * Перемножает 2 монома
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
      * Умножает полином на моном
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
      * Делит моном на моном
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
      * Сравнивает на равенство два числа с плавающей точкой.
      */
    boolean eq(double a, double b) {
        
            		return (Math.abs(a-b) < d.EPS);
    }

    /**
      * Вычетает из полинома полином
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
      * Меняет местами 2 монома
      */
    void swap(Monomial a, Monomial b) {
        
            	Monomial t = new Monomial(a);
             	a = b;
        		b = t;
    }

    /**
      * Проверяет возможность деления a на b
      */
    boolean isDivisibleBy(Monomial a, Monomial b) {
        
            		if (eq(b.c, 0)) return false;
            	 	for (int i = 0; i < 26; ++i) {
            		 	if (a.deg[i] < b.deg[i]) return false;
            	 	}
        		return true;
    }

    /**
      * Проверяет полином на равенство нулю
      */
    boolean eq0(Polynomial a) {
        
            		return (a.m.length == 0) || eq(a.m[0].c, 0);
    }

    /**
      * Добавляет моном в полином
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
      * Добавляет полином в идеал
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
      * Удаляет полином из идеала g
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
      * Ищет полином в G1
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
      * Копирует g в g1
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
      * Копирует x в spol
      */
    void copy2spol(Polynomial x) {
        
        		d.spol = new Polynomial(x);
    }

    /**
      * Копирует reminder в g[x]
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
      * Копирует g1 в g
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
      * Копирует g1 в given
      */
    void g12given() {
        
        		int lg = length(d.g1);
        		d.given = new Polynomial[lg];
        		for (int i = 0; i < lg; ++i) {
        			d.given[i] = new Polynomial(d.g1[i]);
        		}
    }

    /**
      * Копирует given в g1
      */
    void given2g1() {
        
        		int lg = length(d.given);
        		d.g1 = new Polynomial[lg];
        		for (int i = 0; i < lg; ++i) {
        			d.g1[i] = new Polynomial(d.given[i]);
        		}
    }

    /**
      * Нормирование на 1-ый коэффициент
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
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public GroebnerBasis(Locale locale) {
        super("ru.ifmo.vizi.groebner_basis.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экзмляр апплета.
          */
        public GroebnerBasisVisualizer visualizer = null;

        /**
          * Временная переменная для хранения начального идеала..
          */
        public Polynomial[] given = null;

        /**
          * Выделенные полиномы.
          */
        public int[] checked = null;

        /**
          * Комментарий к результату.
          */
        public String comment = new String();

        /**
          * Точность вычислений.
          */
        public double EPS = 1e-9;

        /**
          * Показывать ли G2.
          */
        public boolean showG2 = false;

        /**
          * Показывается перед "rem".
          */
        public String showS = "S = ";

        /**
          * Идеал G1..
          */
        public Polynomial[] g1 = new Polynomial[] {new Polynomial("x^2+y^2"), new Polynomial("x-y")};

        /**
          * Идеал G2..
          */
        public Polynomial[] g = null;

        /**
          * Количество полиномов в идеале.
          */
        public int n = 2;

        /**
          * Переменная, используемая для передачи параметра в автомат.
          */
        public int numb = 0;

        /**
          * Переменная, используемая для передачи параметра в автомат.
          */
        public int numb2 = 0;

        /**
          * S-полином.
          */
        public Polynomial spol = null;

        /**
          * Временная переменная, используемая для передачи параметров.
          */
        public Polynomial temp = null;

        /**
          * Остаток от деления некоторого полинома на идеал g.
          */
        public Polynomial reminder = null;

        /**
          * Наименьшее общее кратное старших членов 2 полиномов (Процедура spolynomial).
          */
        public Monomial spolynomial_lcm;

        /**
          * Отношение lcm к старшему члену первого полинома (Процедура spolynomial).
          */
        public Monomial spolynomial_x;

        /**
          * Отношение lcm к старшему члену второго полинома (Процедура spolynomial).
          */
        public Monomial spolynomial_y;

        /**
          * Произведение x и первого полинома (Процедура spolynomial).
          */
        public Polynomial spolynomial_a;

        /**
          * Произведение y и второго полинома (Процедура spolynomial).
          */
        public Polynomial spolynomial_b;

        /**
          * Временная переменная (Процедура rem).
          */
        public Polynomial rem_t;

        /**
          * Переменная цикла (Процедура rem).
          */
        public int rem_i;

        /**
          * Переменная цикла (Процедура rem).
          */
        public int rem_j;

        /**
          * Переменная цикла (Процедура rem).
          */
        public int rem_z;

        /**
          * Длина полинома temp (Процедура rem).
          */
        public int rem_lt;

        /**
          * Длина идеала g (Процедура rem).
          */
        public int rem_lg;

        /**
          * Флаг окончания вычисления (Процедура rem).
          */
        public boolean rem_er;

        /**
          * Временная переменная для вычислений (Процедура rem).
          */
        public Polynomial rem_tr;

        /**
          * Временная переменная для вычислений (Процедура rem).
          */
        public Polynomial rem_tr1;

        /**
          * Временный моном для отображеня (Процедура rem).
          */
        public Monomial rem_tm;

        /**
          * Временный полином для отображеня (Процедура rem).
          */
        public Polynomial rem_tp;

        /**
          * Переменная, используемая для подсчета количества полиномов идеала (Процедура MakingBasis).
          */
        public int MakingBasis_lg;

        /**
          * Переменная, используемая для подсчета количества полиномов идеала (Процедура MakingBasis).
          */
        public int MakingBasis_lg1;

        /**
          * Переменная цикла (Процедура MakingBasis).
          */
        public int MakingBasis_i;

        /**
          * Переменная цикла (Процедура MakingBasis).
          */
        public int MakingBasis_j;

        /**
          * Переменная-флаг (Процедура MakingBasis).
          */
        public boolean MakingBasis_b;

        /**
          * Переменная, используемая для подсчета количества мономов полинома (Процедура reduce).
          */
        public int reduce_lgi;

        /**
          * Переменная, используемая для подсчета количества полиномов идеала (Процедура reduce).
          */
        public int reduce_lg;

        /**
          * Переменная цикла (Процедура reduce).
          */
        public int reduce_i;

        /**
          * Переменная цикла (Процедура reduce).
          */
        public int reduce_j;

        /**
          * Переменная-флаг (Процедура reduce).
          */
        public boolean reduce_b;

        /**
          * Временная переменная (Процедура reduce).
          */
        public Polynomial reduce_tp;

        public String toString() {
            		StringBuffer s = new StringBuffer();
            		return s.toString();
        }
    }

    /**
      * Вычисляет S-полином 2 полиномов из g1.
      */
    private final class spolynomial extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 9;

        /**
          * Конструктор.
          */
        public spolynomial() {
            super( 
                "spolynomial", 
                0, // Номер начального состояния 
                9, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "ToDO", 
                    "Вычисление наименьшего общего кратного старших членов", 
                    "Вычисление отношения LCM к старшему члену первого полинома", 
                    "Умножение первого полинома на x", 
                    "Вычисление отношения LCM к старшему члену второго полинома", 
                    "Умножение второго полинома на y", 
                    "s = a - b", 
                    "print s", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    1, // ToDO 
                    0, // Вычисление наименьшего общего кратного старших членов 
                    0, // Вычисление отношения LCM к старшему члену первого полинома 
                    0, // Умножение первого полинома на x 
                    0, // Вычисление отношения LCM к старшему члену второго полинома 
                    0, // Умножение второго полинома на y 
                    0, // s = a - b 
                    0, // print s 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // ToDO
                    break;
                }
                case 1: { // ToDO
                    state = 2; // Вычисление наименьшего общего кратного старших членов
                    break;
                }
                case 2: { // Вычисление наименьшего общего кратного старших членов
                    state = 3; // Вычисление отношения LCM к старшему члену первого полинома
                    break;
                }
                case 3: { // Вычисление отношения LCM к старшему члену первого полинома
                    state = 4; // Умножение первого полинома на x
                    break;
                }
                case 4: { // Умножение первого полинома на x
                    state = 5; // Вычисление отношения LCM к старшему члену второго полинома
                    break;
                }
                case 5: { // Вычисление отношения LCM к старшему члену второго полинома
                    state = 6; // Умножение второго полинома на y
                    break;
                }
                case 6: { // Умножение второго полинома на y
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

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // ToDO
                    startSection();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.hide();
                    break;
                }
                case 2: { // Вычисление наименьшего общего кратного старших членов
                    startSection();
                    storeField(d, "spolynomial_lcm");
                    				d.spolynomial_lcm = lcm (d.g1[d.numb].m[0], d.g1[d.numb2].m[0]);
                    storeField(d, "comment");
                    				d.comment = "lcm (" + d.g1[d.numb].m[0].toString() + ", " + d.g1[d.numb2].m[0].toString() + ") = " + d.spolynomial_lcm.toString();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printResult();
                    break;
                }
                case 3: { // Вычисление отношения LCM к старшему члену первого полинома
                    startSection();
                    storeField(d, "spolynomial_x");
                    				d.spolynomial_x = divide(d.spolynomial_lcm,d.g1[d.numb].m[0]);
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printAction('/', new Polynomial(new Monomial[] {d.spolynomial_lcm}), new Polynomial(new Monomial[] {d.g1[d.numb].m[0]}), new Polynomial(new Monomial[] {d.spolynomial_x}), 1);
                    break;
                }
                case 4: { // Умножение первого полинома на x
                    startSection();
                    storeField(d, "spolynomial_a");
                    				d.spolynomial_a = multiply(d.g1[d.numb],d.spolynomial_x);	
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printAction('*', d.g1[d.numb], new Polynomial(new Monomial[] {d.spolynomial_x}), d.spolynomial_a, 2);
                    break;
                }
                case 5: { // Вычисление отношения LCM к старшему члену второго полинома
                    startSection();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.hideAction();
                    storeField(d, "spolynomial_y");
                    				d.spolynomial_y = divide(d.spolynomial_lcm,d.g1[d.numb2].m[0]);
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.printAction('/', new Polynomial(new Monomial[] {d.spolynomial_lcm}), new Polynomial(new Monomial[] {d.g1[d.numb2].m[0]}), new Polynomial(new Monomial[] {d.spolynomial_y}), 1);
                    break;
                }
                case 6: { // Умножение второго полинома на y
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // ToDO
                    restoreSection();
                    break;
                }
                case 2: { // Вычисление наименьшего общего кратного старших членов
                    restoreSection();
                    break;
                }
                case 3: { // Вычисление отношения LCM к старшему члену первого полинома
                    restoreSection();
                    break;
                }
                case 4: { // Умножение первого полинома на x
                    restoreSection();
                    break;
                }
                case 5: { // Вычисление отношения LCM к старшему члену второго полинома
                    restoreSection();
                    break;
                }
                case 6: { // Умножение второго полинома на y
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

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // ToDO
                    state = START_STATE; 
                    break;
                }
                case 2: { // Вычисление наименьшего общего кратного старших членов
                    state = 1; // ToDO
                    break;
                }
                case 3: { // Вычисление отношения LCM к старшему члену первого полинома
                    state = 2; // Вычисление наименьшего общего кратного старших членов
                    break;
                }
                case 4: { // Умножение первого полинома на x
                    state = 3; // Вычисление отношения LCM к старшему члену первого полинома
                    break;
                }
                case 5: { // Вычисление отношения LCM к старшему члену второго полинома
                    state = 4; // Умножение первого полинома на x
                    break;
                }
                case 6: { // Умножение второго полинома на y
                    state = 5; // Вычисление отношения LCM к старшему члену второго полинома
                    break;
                }
                case 7: { // s = a - b
                    state = 6; // Умножение второго полинома на y
                    break;
                }
                case 8: { // print s
                    state = 7; // s = a - b
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 8; // print s
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 1: { // ToDO
                    comment = GroebnerBasis.this.getComment("spolynomial.todo"); 
                    args = new Object[]{d.g1[d.numb], d.g1[d.numb2]}; 
                    break;
                }
                case 2: { // Вычисление наименьшего общего кратного старших членов
                    comment = GroebnerBasis.this.getComment("spolynomial.CalculateLCM"); 
                    args = new Object[]{d.g1[d.numb], d.g1[d.numb2], d.spolynomial_lcm}; 
                    break;
                }
                case 3: { // Вычисление отношения LCM к старшему члену первого полинома
                    comment = GroebnerBasis.this.getComment("spolynomial.DivideLCMByLT1"); 
                    args = new Object[]{d.spolynomial_lcm, d.g1[d.numb].m[0], d.spolynomial_x}; 
                    break;
                }
                case 4: { // Умножение первого полинома на x
                    comment = GroebnerBasis.this.getComment("spolynomial.Multiply1ByX"); 
                    args = new Object[]{d.spolynomial_x, d.g1[d.numb], d.spolynomial_a}; 
                    break;
                }
                case 5: { // Вычисление отношения LCM к старшему члену второго полинома
                    comment = GroebnerBasis.this.getComment("spolynomial.DivideLCMByLT2"); 
                    args = new Object[]{d.spolynomial_lcm, d.g1[d.numb2].m[0], d.spolynomial_y}; 
                    break;
                }
                case 6: { // Умножение второго полинома на y
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
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case 1: { // ToDO
                    				d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    				d.visualizer.redraw();
                    break;
                }
                case 2: { // Вычисление наименьшего общего кратного старших членов
                    				d.visualizer.redraw();
                    break;
                }
                case 3: { // Вычисление отношения LCM к старшему члену первого полинома
                    				d.visualizer.redraw();
                    break;
                }
                case 4: { // Умножение первого полинома на x
                    				d.visualizer.redraw();
                    break;
                }
                case 5: { // Вычисление отношения LCM к старшему члену второго полинома
                    				d.visualizer.redraw();
                    break;
                }
                case 6: { // Умножение второго полинома на y
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
      * Добавление полинома в идеал.
      */
    private final class AddPolynomial2Ideal extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 2;

        /**
          * Конструктор.
          */
        public AddPolynomial2Ideal() {
            super( 
                "AddPolynomial2Ideal", 
                0, // Номер начального состояния 
                2, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Добавление полинома в идеал g1", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    1, // Добавление полинома в идеал g1 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Добавление полинома в идеал g1
                    break;
                }
                case 1: { // Добавление полинома в идеал g1
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Добавление полинома в идеал g1
                    startSection();
                    storeField(d, "g1");
                    				d.g1 = add(d.reminder);
                    break;
                }
            }
        }

        /**
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Добавление полинома в идеал g1
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Добавление полинома в идеал g1
                    state = START_STATE; 
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 1; // Добавление полинома в идеал g1
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 1: { // Добавление полинома в идеал g1
                    comment = GroebnerBasis.this.getComment("AddPolynomial2Ideal.AddingPolynomial2Ideal"); 
                    args = new Object[]{d.reminder}; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case 1: { // Добавление полинома в идеал g1
                    				d.visualizer.printIdealsWithPolynomials(new boolean[] {false, true}, new Polynomial[] {d.reminder});
                    break;
                }
            }
        }
    }

    /**
      * Остаток от деления полинома на идеал.
      */
    private final class rem extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 32;

        /**
          * Конструктор.
          */
        public rem() {
            super( 
                "rem", 
                0, // Номер начального состояния 
                32, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "if showS", 
                    "if showS (окончание)", 
                    "Покажем S", 
                    "Не покажем S", 
                    "ToDo", 
                    "А не пустой ли полином?", 
                    "А не пустой ли полином? (окончание)", 
                    "Искомый остаток равен 0", 
                    "Подготовка", 
                    "Копирование spol в t", 
                    "Подготовка к циклу", 
                    "Копирование spol[i] в t[i]", 
                    "Копирование spol[i].deg в t[i].deg", 
                    "i++", 
                    "Признак окончания работы", 
                    "Подготовка к циклу", 
                    "Деление t на полиномы g", 
                    "Проверка делимости LT(t) на LT(g[i])", 
                    "Старший моном t делится на старший моном g[i]", 
                    "Старший моном t делится на старший моном g[i] (окончание)", 
                    "Старший моном t делится на старший моном g[i]", 
                    "Делим t на g[i]", 
                    "Делим t на g[i]", 
                    "Завершение деления", 
                    "Не делится", 
                    "Increment of i", 
                    "Старший член t ({0}) не делится ни на один из старших членов полиномов идеала", 
                    "Старший член t ({0}) не делится ни на один из старших членов полиномов идеала (окончание)", 
                    "Отправление в остаток LT(t)", 
                    "Добавление t[0] в остаток", 
                    "Подготовка к циклу", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // if showS 
                    -1, // if showS (окончание) 
                    -1, // Покажем S 
                    -1, // Не покажем S 
                    0, // ToDo 
                    -1, // А не пустой ли полином? 
                    -1, // А не пустой ли полином? (окончание) 
                    0, // Искомый остаток равен 0 
                    -1, // Подготовка 
                    -1, // Копирование spol в t 
                    -1, // Подготовка к циклу 
                    -1, // Копирование spol[i] в t[i] 
                    -1, // Копирование spol[i].deg в t[i].deg 
                    -1, // i++ 
                    -1, // Признак окончания работы 
                    -1, // Подготовка к циклу 
                    -1, // Деление t на полиномы g 
                    0, // Проверка делимости LT(t) на LT(g[i]) 
                    -1, // Старший моном t делится на старший моном g[i] 
                    -1, // Старший моном t делится на старший моном g[i] (окончание) 
                    0, // Старший моном t делится на старший моном g[i] 
                    -1, // Делим t на g[i] 
                    0, // Делим t на g[i] 
                    -1, // Завершение деления 
                    0, // Не делится 
                    -1, // Increment of i 
                    -1, // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала 
                    -1, // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала (окончание) 
                    0, // Отправление в остаток LT(t) 
                    -1, // Добавление t[0] в остаток 
                    -1, // Подготовка к циклу 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // if showS
                    break;
                }
                case 1: { // if showS
                    if (d.showS.equals("S = ")) {
                        state = 3; // Покажем S
                    } else {
                        state = 4; // Не покажем S
                    }
                    break;
                }
                case 2: { // if showS (окончание)
                    state = 5; // ToDo
                    break;
                }
                case 3: { // Покажем S
                    stack.pushBoolean(true); 
                    state = 2; // if showS (окончание)
                    break;
                }
                case 4: { // Не покажем S
                    stack.pushBoolean(false); 
                    state = 2; // if showS (окончание)
                    break;
                }
                case 5: { // ToDo
                    state = 6; // А не пустой ли полином?
                    break;
                }
                case 6: { // А не пустой ли полином?
                    if (d.spol.m.length == 0) {
                        state = 8; // Искомый остаток равен 0
                    } else {
                        state = 9; // Подготовка
                    }
                    break;
                }
                case 7: { // А не пустой ли полином? (окончание)
                    state = END_STATE; 
                    break;
                }
                case 8: { // Искомый остаток равен 0
                    stack.pushBoolean(true); 
                    state = 7; // А не пустой ли полином? (окончание)
                    break;
                }
                case 9: { // Подготовка
                    stack.pushBoolean(false); 
                    state = 10; // Копирование spol в t
                    break;
                }
                case 10: { // Копирование spol в t
                    if (d.rem_i < d.rem_lt) {
                        state = 11; // Подготовка к циклу
                    } else {
                        stack.pushBoolean(false); 
                        state = 15; // Признак окончания работы
                    }
                    break;
                }
                case 11: { // Подготовка к циклу
                    stack.pushBoolean(false); 
                    state = 12; // Копирование spol[i] в t[i]
                    break;
                }
                case 12: { // Копирование spol[i] в t[i]
                    if (d.rem_j < 26) {
                        state = 13; // Копирование spol[i].deg в t[i].deg
                    } else {
                        state = 14; // i++
                    }
                    break;
                }
                case 13: { // Копирование spol[i].deg в t[i].deg
                    stack.pushBoolean(true); 
                    state = 12; // Копирование spol[i] в t[i]
                    break;
                }
                case 14: { // i++
                    stack.pushBoolean(true); 
                    state = 10; // Копирование spol в t
                    break;
                }
                case 15: { // Признак окончания работы
                    if (!eq0(d.rem_t)) {
                        state = 16; // Подготовка к циклу
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // А не пустой ли полином? (окончание)
                    }
                    break;
                }
                case 16: { // Подготовка к циклу
                    stack.pushBoolean(false); 
                    state = 17; // Деление t на полиномы g
                    break;
                }
                case 17: { // Деление t на полиномы g
                    if ((d.rem_i < d.rem_lg) && d.rem_er) {
                        state = 18; // Проверка делимости LT(t) на LT(g[i])
                    } else {
                        state = 27; // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала
                    }
                    break;
                }
                case 18: { // Проверка делимости LT(t) на LT(g[i])
                    state = 19; // Старший моном t делится на старший моном g[i]
                    break;
                }
                case 19: { // Старший моном t делится на старший моном g[i]
                    if (isDivisibleBy(d.rem_t.m[0], d.g[d.rem_i].m[0])) {
                        state = 21; // Старший моном t делится на старший моном g[i]
                    } else {
                        state = 25; // Не делится
                    }
                    break;
                }
                case 20: { // Старший моном t делится на старший моном g[i] (окончание)
                    state = 26; // Increment of i
                    break;
                }
                case 21: { // Старший моном t делится на старший моном g[i]
                    state = 22; // Делим t на g[i]
                    break;
                }
                case 22: { // Делим t на g[i]
                    state = 23; // Делим t на g[i]
                    break;
                }
                case 23: { // Делим t на g[i]
                    state = 24; // Завершение деления
                    break;
                }
                case 24: { // Завершение деления
                    stack.pushBoolean(true); 
                    state = 20; // Старший моном t делится на старший моном g[i] (окончание)
                    break;
                }
                case 25: { // Не делится
                    stack.pushBoolean(false); 
                    state = 20; // Старший моном t делится на старший моном g[i] (окончание)
                    break;
                }
                case 26: { // Increment of i
                    stack.pushBoolean(true); 
                    state = 17; // Деление t на полиномы g
                    break;
                }
                case 27: { // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала
                    if (d.rem_er) {
                        state = 29; // Отправление в остаток LT(t)
                    } else {
                        stack.pushBoolean(false); 
                        state = 28; // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала (окончание)
                    }
                    break;
                }
                case 28: { // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала (окончание)
                    stack.pushBoolean(true); 
                    state = 15; // Признак окончания работы
                    break;
                }
                case 29: { // Отправление в остаток LT(t)
                    state = 30; // Добавление t[0] в остаток
                    break;
                }
                case 30: { // Добавление t[0] в остаток
                    state = 31; // Подготовка к циклу
                    break;
                }
                case 31: { // Подготовка к циклу
                    stack.pushBoolean(true); 
                    state = 28; // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // if showS
                    break;
                }
                case 2: { // if showS (окончание)
                    break;
                }
                case 3: { // Покажем S
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
                case 4: { // Не покажем S
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
                case 6: { // А не пустой ли полином?
                    break;
                }
                case 7: { // А не пустой ли полином? (окончание)
                    break;
                }
                case 8: { // Искомый остаток равен 0
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
                case 9: { // Подготовка
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
                case 10: { // Копирование spol в t
                    break;
                }
                case 11: { // Подготовка к циклу
                    startSection();
                        storeArray(d.rem_t.m, d.rem_i);
                        		 			d.rem_t.m[d.rem_i] = new Monomial();
                    storeField(d.rem_t.m[d.rem_i], "c");
                    						d.rem_t.m[d.rem_i].c = d.spol.m[d.rem_i].c;
                    storeField(d, "rem_j");
                    						d.rem_j = 0;
                    break;
                }
                case 12: { // Копирование spol[i] в t[i]
                    break;
                }
                case 13: { // Копирование spol[i].deg в t[i].deg
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
                case 15: { // Признак окончания работы
                    break;
                }
                case 16: { // Подготовка к циклу
                    startSection();
                    storeField(d, "rem_er");
                    						d.rem_er = true;
                    storeField(d, "rem_i");
                    						d.rem_i = 0;
                    break;
                }
                case 17: { // Деление t на полиномы g
                    break;
                }
                case 18: { // Проверка делимости LT(t) на LT(g[i])
                    startSection();
                    storeField(d, "checked");
                    							d.checked = new int[] {- d.rem_i - 1};
                    break;
                }
                case 19: { // Старший моном t делится на старший моном g[i]
                    break;
                }
                case 20: { // Старший моном t делится на старший моном g[i] (окончание)
                    break;
                }
                case 21: { // Старший моном t делится на старший моном g[i]
                    startSection();
                    break;
                }
                case 22: { // Делим t на g[i]
                    startSection();
                    storeField(d, "rem_tr");
                    								d.rem_tr = multiply(d.g[d.rem_i], divide(d.rem_t.m[0],d.g[d.rem_i].m[0]));
                    storeField(d, "rem_er");
                    								d.rem_er = false;
                    break;
                }
                case 23: { // Делим t на g[i]
                    startSection();
                    storeField(d, "rem_tr1");
                    								d.rem_tr1 = subtract(d.rem_t, d.rem_tr);
                    storeField(d.visualizer, "labels");
                    								d.visualizer.labels = d.visualizer.hide();
                    storeField(d.visualizer, "labels");
                    								d.visualizer.labels = d.visualizer.printAction('%', d.rem_t, d.g[d.rem_i], d.rem_tr1, 1);
                    break;
                }
                case 24: { // Завершение деления
                    startSection();
                    storeField(d, "rem_t");
                    								d.rem_t = d.rem_tr1;
                    break;
                }
                case 25: { // Не делится
                    startSection();
                    break;
                }
                case 26: { // Increment of i
                    startSection();
                    storeField(d, "rem_i");
                    							d.rem_i = d.rem_i + 1;
                    break;
                }
                case 27: { // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала
                    break;
                }
                case 28: { // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала (окончание)
                    break;
                }
                case 29: { // Отправление в остаток LT(t)
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
                case 30: { // Добавление t[0] в остаток
                    startSection();
                    break;
                }
                case 31: { // Подготовка к циклу
                    startSection();
                    storeField(d, "rem_z");
                    							d.rem_z = 0;
                    break;
                }
            }
        }

        /**
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // if showS
                    break;
                }
                case 2: { // if showS (окончание)
                    break;
                }
                case 3: { // Покажем S
                    restoreSection();
                    break;
                }
                case 4: { // Не покажем S
                    restoreSection();
                    break;
                }
                case 5: { // ToDo
                    restoreSection();
                    break;
                }
                case 6: { // А не пустой ли полином?
                    break;
                }
                case 7: { // А не пустой ли полином? (окончание)
                    break;
                }
                case 8: { // Искомый остаток равен 0
                    restoreSection();
                    break;
                }
                case 9: { // Подготовка
                    restoreSection();
                    break;
                }
                case 10: { // Копирование spol в t
                    break;
                }
                case 11: { // Подготовка к циклу
                    restoreSection();
                    break;
                }
                case 12: { // Копирование spol[i] в t[i]
                    break;
                }
                case 13: { // Копирование spol[i].deg в t[i].deg
                    restoreSection();
                    break;
                }
                case 14: { // i++
                    restoreSection();
                    break;
                }
                case 15: { // Признак окончания работы
                    break;
                }
                case 16: { // Подготовка к циклу
                    restoreSection();
                    break;
                }
                case 17: { // Деление t на полиномы g
                    break;
                }
                case 18: { // Проверка делимости LT(t) на LT(g[i])
                    restoreSection();
                    break;
                }
                case 19: { // Старший моном t делится на старший моном g[i]
                    break;
                }
                case 20: { // Старший моном t делится на старший моном g[i] (окончание)
                    break;
                }
                case 21: { // Старший моном t делится на старший моном g[i]
                    restoreSection();
                    break;
                }
                case 22: { // Делим t на g[i]
                    restoreSection();
                    break;
                }
                case 23: { // Делим t на g[i]
                    restoreSection();
                    break;
                }
                case 24: { // Завершение деления
                    restoreSection();
                    break;
                }
                case 25: { // Не делится
                    restoreSection();
                    break;
                }
                case 26: { // Increment of i
                    restoreSection();
                    break;
                }
                case 27: { // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала
                    break;
                }
                case 28: { // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала (окончание)
                    break;
                }
                case 29: { // Отправление в остаток LT(t)
                    restoreSection();
                    break;
                }
                case 30: { // Добавление t[0] в остаток
                    restoreSection();
                    break;
                }
                case 31: { // Подготовка к циклу
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // if showS
                    state = START_STATE; 
                    break;
                }
                case 2: { // if showS (окончание)
                    if (stack.popBoolean()) {
                        state = 3; // Покажем S
                    } else {
                        state = 4; // Не покажем S
                    }
                    break;
                }
                case 3: { // Покажем S
                    state = 1; // if showS
                    break;
                }
                case 4: { // Не покажем S
                    state = 1; // if showS
                    break;
                }
                case 5: { // ToDo
                    state = 2; // if showS (окончание)
                    break;
                }
                case 6: { // А не пустой ли полином?
                    state = 5; // ToDo
                    break;
                }
                case 7: { // А не пустой ли полином? (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // Искомый остаток равен 0
                    } else {
                        state = 15; // Признак окончания работы
                    }
                    break;
                }
                case 8: { // Искомый остаток равен 0
                    state = 6; // А не пустой ли полином?
                    break;
                }
                case 9: { // Подготовка
                    state = 6; // А не пустой ли полином?
                    break;
                }
                case 10: { // Копирование spol в t
                    if (stack.popBoolean()) {
                        state = 14; // i++
                    } else {
                        state = 9; // Подготовка
                    }
                    break;
                }
                case 11: { // Подготовка к циклу
                    state = 10; // Копирование spol в t
                    break;
                }
                case 12: { // Копирование spol[i] в t[i]
                    if (stack.popBoolean()) {
                        state = 13; // Копирование spol[i].deg в t[i].deg
                    } else {
                        state = 11; // Подготовка к циклу
                    }
                    break;
                }
                case 13: { // Копирование spol[i].deg в t[i].deg
                    state = 12; // Копирование spol[i] в t[i]
                    break;
                }
                case 14: { // i++
                    state = 12; // Копирование spol[i] в t[i]
                    break;
                }
                case 15: { // Признак окончания работы
                    if (stack.popBoolean()) {
                        state = 28; // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала (окончание)
                    } else {
                        state = 10; // Копирование spol в t
                    }
                    break;
                }
                case 16: { // Подготовка к циклу
                    state = 15; // Признак окончания работы
                    break;
                }
                case 17: { // Деление t на полиномы g
                    if (stack.popBoolean()) {
                        state = 26; // Increment of i
                    } else {
                        state = 16; // Подготовка к циклу
                    }
                    break;
                }
                case 18: { // Проверка делимости LT(t) на LT(g[i])
                    state = 17; // Деление t на полиномы g
                    break;
                }
                case 19: { // Старший моном t делится на старший моном g[i]
                    state = 18; // Проверка делимости LT(t) на LT(g[i])
                    break;
                }
                case 20: { // Старший моном t делится на старший моном g[i] (окончание)
                    if (stack.popBoolean()) {
                        state = 24; // Завершение деления
                    } else {
                        state = 25; // Не делится
                    }
                    break;
                }
                case 21: { // Старший моном t делится на старший моном g[i]
                    state = 19; // Старший моном t делится на старший моном g[i]
                    break;
                }
                case 22: { // Делим t на g[i]
                    state = 21; // Старший моном t делится на старший моном g[i]
                    break;
                }
                case 23: { // Делим t на g[i]
                    state = 22; // Делим t на g[i]
                    break;
                }
                case 24: { // Завершение деления
                    state = 23; // Делим t на g[i]
                    break;
                }
                case 25: { // Не делится
                    state = 19; // Старший моном t делится на старший моном g[i]
                    break;
                }
                case 26: { // Increment of i
                    state = 20; // Старший моном t делится на старший моном g[i] (окончание)
                    break;
                }
                case 27: { // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала
                    state = 17; // Деление t на полиномы g
                    break;
                }
                case 28: { // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала (окончание)
                    if (stack.popBoolean()) {
                        state = 31; // Подготовка к циклу
                    } else {
                        state = 27; // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала
                    }
                    break;
                }
                case 29: { // Отправление в остаток LT(t)
                    state = 27; // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала
                    break;
                }
                case 30: { // Добавление t[0] в остаток
                    state = 29; // Отправление в остаток LT(t)
                    break;
                }
                case 31: { // Подготовка к циклу
                    state = 30; // Добавление t[0] в остаток
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 7; // А не пустой ли полином? (окончание)
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 5: { // ToDo
                    comment = GroebnerBasis.this.getComment("rem.todo"); 
                    args = new Object[]{d.spol}; 
                    break;
                }
                case 6: { // А не пустой ли полином?
                    if (d.spol.m.length == 0) {
                        comment = GroebnerBasis.this.getComment("rem.CondPolynomialIsEmpty.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("rem.CondPolynomialIsEmpty.false"); 
                    }
                    args = new Object[]{d.spol}; 
                    break;
                }
                case 8: { // Искомый остаток равен 0
                    comment = GroebnerBasis.this.getComment("rem.EmptyPolynom"); 
                    break;
                }
                case 18: { // Проверка делимости LT(t) на LT(g[i])
                    comment = GroebnerBasis.this.getComment("rem.BeforeIf"); 
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i], d.g[d.rem_i].m[0]}; 
                    break;
                }
                case 19: { // Старший моном t делится на старший моном g[i]
                    if (isDivisibleBy(d.rem_t.m[0], d.g[d.rem_i].m[0])) {
                        comment = GroebnerBasis.this.getComment("rem.CondTIsDivisibleByGi.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("rem.CondTIsDivisibleByGi.false"); 
                    }
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i], d.g[d.rem_i].m[0]}; 
                    break;
                }
                case 21: { // Старший моном t делится на старший моном g[i]
                    comment = GroebnerBasis.this.getComment("rem.TIsDivisibleByGi"); 
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i], d.g[d.rem_i].m[0], d.rem_t}; 
                    break;
                }
                case 22: { // Делим t на g[i]
                    comment = GroebnerBasis.this.getComment("rem.DivisingTByGi1"); 
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i].m[0]}; 
                    break;
                }
                case 23: { // Делим t на g[i]
                    comment = GroebnerBasis.this.getComment("rem.DivisingTByGi"); 
                    args = new Object[]{d.rem_t, d.g[d.rem_i], d.rem_tr1}; 
                    break;
                }
                case 25: { // Не делится
                    comment = GroebnerBasis.this.getComment("rem.TIsNotDivisibleByGi"); 
                    args = new Object[]{d.rem_t.m[0], d.g[d.rem_i], d.g[d.rem_i].m[0], d.rem_t}; 
                    break;
                }
                case 27: { // Старший член t ({0}) не делится ни на один из старших членов полиномов идеала
                    if (d.rem_er) {
                        comment = GroebnerBasis.this.getComment("rem.ThereIsSomethingNewToAdd.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("rem.ThereIsSomethingNewToAdd.false"); 
                    }
                    args = new Object[]{d.rem_t.m[0], d.rem_t}; 
                    break;
                }
                case 29: { // Отправление в остаток LT(t)
                    comment = GroebnerBasis.this.getComment("rem.AddingLT2Rem"); 
                    args = new Object[]{d.rem_tm, d.reminder, d.rem_tp}; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case 5: { // ToDo
                    				d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 8: { // Искомый остаток равен 0
                    					d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    					d.visualizer.redraw();
                    break;
                }
                case 18: { // Проверка делимости LT(t) на LT(g[i])
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 21: { // Старший моном t делится на старший моном g[i]
                    								d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 23: { // Делим t на g[i]
                    								d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    								d.visualizer.redraw();
                    break;
                }
                case 25: { // Не делится
                    								d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 29: { // Отправление в остаток LT(t)
                    							d.visualizer.redraw();
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 30: { // Добавление t[0] в остаток
                    break;
                }
            }
        }
    }

    /**
      * Построение базиса Грёбнера для g1.
      */
    private final class MakingBasis extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 19;

        /**
          * Конструктор.
          */
        public MakingBasis() {
            super( 
                "MakingBasis", 
                0, // Номер начального состояния 
                19, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Подготовка к циклу", 
                    "Внешний цикл", 
                    "Подготовка к внутренним циклам", 
                    "Перебор всех пар полиномов", 
                    "Подготовка к внутреннему циклу", 
                    "Перебор всех пар полиномов", 
                    "Выводим сообщение о дальнейшем действии", 
                    "Построение S-полинома от i-го и j-го полиномов", 
                    "Вычисляет S-полином 2 полиномов из g1 (автомат)", 
                    "Скопируем G1 в G2", 
                    "Остаток от деления полинома на идеал (автомат)", 
                    "Нарисуем на экране", 
                    "S-полином не делится на g", 
                    "S-полином не делится на g (окончание)", 
                    "Добавление полинома в идеал (автомат)", 
                    "Добавление остатка в идеал", 
                    "Increment of J", 
                    "Increment of I", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Подготовка к циклу 
                    -1, // Внешний цикл 
                    -1, // Подготовка к внутренним циклам 
                    -1, // Перебор всех пар полиномов 
                    -1, // Подготовка к внутреннему циклу 
                    -1, // Перебор всех пар полиномов 
                    0, // Выводим сообщение о дальнейшем действии 
                    -1, // Построение S-полинома от i-го и j-го полиномов 
                    CALL_AUTO_LEVEL, // Вычисляет S-полином 2 полиномов из g1 (автомат) 
                    0, // Скопируем G1 в G2 
                    CALL_AUTO_LEVEL, // Остаток от деления полинома на идеал (автомат) 
                    -1, // Нарисуем на экране 
                    0, // S-полином не делится на g 
                    -1, // S-полином не делится на g (окончание) 
                    CALL_AUTO_LEVEL, // Добавление полинома в идеал (автомат) 
                    -1, // Добавление остатка в идеал 
                    -1, // Increment of J 
                    -1, // Increment of I 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Подготовка к циклу
                    break;
                }
                case 1: { // Подготовка к циклу
                    stack.pushBoolean(false); 
                    state = 2; // Внешний цикл
                    break;
                }
                case 2: { // Внешний цикл
                    if (d.MakingBasis_b) {
                        state = 3; // Подготовка к внутренним циклам
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Подготовка к внутренним циклам
                    stack.pushBoolean(false); 
                    state = 4; // Перебор всех пар полиномов
                    break;
                }
                case 4: { // Перебор всех пар полиномов
                    if (d.MakingBasis_i < d.MakingBasis_lg1) {
                        state = 5; // Подготовка к внутреннему циклу
                    } else {
                        stack.pushBoolean(true); 
                        state = 2; // Внешний цикл
                    }
                    break;
                }
                case 5: { // Подготовка к внутреннему циклу
                    stack.pushBoolean(false); 
                    state = 6; // Перебор всех пар полиномов
                    break;
                }
                case 6: { // Перебор всех пар полиномов
                    if (d.MakingBasis_j < d.MakingBasis_lg1) {
                        state = 7; // Выводим сообщение о дальнейшем действии
                    } else {
                        state = 18; // Increment of I
                    }
                    break;
                }
                case 7: { // Выводим сообщение о дальнейшем действии
                    state = 8; // Построение S-полинома от i-го и j-го полиномов
                    break;
                }
                case 8: { // Построение S-полинома от i-го и j-го полиномов
                    state = 9; // Вычисляет S-полином 2 полиномов из g1 (автомат)
                    break;
                }
                case 9: { // Вычисляет S-полином 2 полиномов из g1 (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // Скопируем G1 в G2
                    }
                    break;
                }
                case 10: { // Скопируем G1 в G2
                    state = 11; // Остаток от деления полинома на идеал (автомат)
                    break;
                }
                case 11: { // Остаток от деления полинома на идеал (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 12; // Нарисуем на экране
                    }
                    break;
                }
                case 12: { // Нарисуем на экране
                    state = 13; // S-полином не делится на g
                    break;
                }
                case 13: { // S-полином не делится на g
                    if (!eq0(d.reminder)) {
                        state = 15; // Добавление полинома в идеал (автомат)
                    } else {
                        stack.pushBoolean(false); 
                        state = 14; // S-полином не делится на g (окончание)
                    }
                    break;
                }
                case 14: { // S-полином не делится на g (окончание)
                    state = 17; // Increment of J
                    break;
                }
                case 15: { // Добавление полинома в идеал (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 16; // Добавление остатка в идеал
                    }
                    break;
                }
                case 16: { // Добавление остатка в идеал
                    stack.pushBoolean(true); 
                    state = 14; // S-полином не делится на g (окончание)
                    break;
                }
                case 17: { // Increment of J
                    stack.pushBoolean(true); 
                    state = 6; // Перебор всех пар полиномов
                    break;
                }
                case 18: { // Increment of I
                    stack.pushBoolean(true); 
                    state = 4; // Перебор всех пар полиномов
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Подготовка к циклу
                    startSection();
                    storeField(d, "MakingBasis_b");
                    				d.MakingBasis_b = true;
                    break;
                }
                case 2: { // Внешний цикл
                    break;
                }
                case 3: { // Подготовка к внутренним циклам
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
                case 4: { // Перебор всех пар полиномов
                    break;
                }
                case 5: { // Подготовка к внутреннему циклу
                    startSection();
                    storeField(d, "MakingBasis_j");
                    						d.MakingBasis_j = d.MakingBasis_i + 1;
                    break;
                }
                case 6: { // Перебор всех пар полиномов
                    break;
                }
                case 7: { // Выводим сообщение о дальнейшем действии
                    startSection();
                    break;
                }
                case 8: { // Построение S-полинома от i-го и j-го полиномов
                    startSection();
                    storeField(d, "numb");
                    							d.numb = d.MakingBasis_i;
                    storeField(d, "numb2");
                    							d.numb2 = d.MakingBasis_j;
                    storeField(d, "checked");
                    							d.checked = new int[] {d.MakingBasis_i, d.MakingBasis_j};
                    break;
                }
                case 9: { // Вычисляет S-полином 2 полиномов из g1 (автомат)
                    if (child == null) {
                        child = new spolynomial(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // Скопируем G1 в G2
                    startSection();
                    storeField(d, "checked");
                    							d.checked = null;
                    storeField(d, "g");
                    							d.g = g1ToG();
                    storeField(d.visualizer, "labels");
                    							d.visualizer.labels = d.visualizer.hideAction();
                    break;
                }
                case 11: { // Остаток от деления полинома на идеал (автомат)
                    if (child == null) {
                        child = new rem(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 12: { // Нарисуем на экране
                    startSection();
                    storeField(d.visualizer, "labels");
                    							d.visualizer.labels = d.visualizer.hide();
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    							d.visualizer.redraw();
                    break;
                }
                case 13: { // S-полином не делится на g
                    break;
                }
                case 14: { // S-полином не делится на g (окончание)
                    break;
                }
                case 15: { // Добавление полинома в идеал (автомат)
                    if (child == null) {
                        child = new AddPolynomial2Ideal(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 16: { // Добавление остатка в идеал
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Подготовка к циклу
                    restoreSection();
                    break;
                }
                case 2: { // Внешний цикл
                    break;
                }
                case 3: { // Подготовка к внутренним циклам
                    restoreSection();
                    break;
                }
                case 4: { // Перебор всех пар полиномов
                    break;
                }
                case 5: { // Подготовка к внутреннему циклу
                    restoreSection();
                    break;
                }
                case 6: { // Перебор всех пар полиномов
                    break;
                }
                case 7: { // Выводим сообщение о дальнейшем действии
                    restoreSection();
                    break;
                }
                case 8: { // Построение S-полинома от i-го и j-го полиномов
                    restoreSection();
                    break;
                }
                case 9: { // Вычисляет S-полином 2 полиномов из g1 (автомат)
                    if (child == null) {
                        child = new spolynomial(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // Скопируем G1 в G2
                    restoreSection();
                    break;
                }
                case 11: { // Остаток от деления полинома на идеал (автомат)
                    if (child == null) {
                        child = new rem(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 12: { // Нарисуем на экране
                    restoreSection();
                    break;
                }
                case 13: { // S-полином не делится на g
                    break;
                }
                case 14: { // S-полином не делится на g (окончание)
                    break;
                }
                case 15: { // Добавление полинома в идеал (автомат)
                    if (child == null) {
                        child = new AddPolynomial2Ideal(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 16: { // Добавление остатка в идеал
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

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Подготовка к циклу
                    state = START_STATE; 
                    break;
                }
                case 2: { // Внешний цикл
                    if (stack.popBoolean()) {
                        state = 4; // Перебор всех пар полиномов
                    } else {
                        state = 1; // Подготовка к циклу
                    }
                    break;
                }
                case 3: { // Подготовка к внутренним циклам
                    state = 2; // Внешний цикл
                    break;
                }
                case 4: { // Перебор всех пар полиномов
                    if (stack.popBoolean()) {
                        state = 18; // Increment of I
                    } else {
                        state = 3; // Подготовка к внутренним циклам
                    }
                    break;
                }
                case 5: { // Подготовка к внутреннему циклу
                    state = 4; // Перебор всех пар полиномов
                    break;
                }
                case 6: { // Перебор всех пар полиномов
                    if (stack.popBoolean()) {
                        state = 17; // Increment of J
                    } else {
                        state = 5; // Подготовка к внутреннему циклу
                    }
                    break;
                }
                case 7: { // Выводим сообщение о дальнейшем действии
                    state = 6; // Перебор всех пар полиномов
                    break;
                }
                case 8: { // Построение S-полинома от i-го и j-го полиномов
                    state = 7; // Выводим сообщение о дальнейшем действии
                    break;
                }
                case 9: { // Вычисляет S-полином 2 полиномов из g1 (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // Построение S-полинома от i-го и j-го полиномов
                    }
                    break;
                }
                case 10: { // Скопируем G1 в G2
                    state = 9; // Вычисляет S-полином 2 полиномов из g1 (автомат)
                    break;
                }
                case 11: { // Остаток от деления полинома на идеал (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 10; // Скопируем G1 в G2
                    }
                    break;
                }
                case 12: { // Нарисуем на экране
                    state = 11; // Остаток от деления полинома на идеал (автомат)
                    break;
                }
                case 13: { // S-полином не делится на g
                    state = 12; // Нарисуем на экране
                    break;
                }
                case 14: { // S-полином не делится на g (окончание)
                    if (stack.popBoolean()) {
                        state = 16; // Добавление остатка в идеал
                    } else {
                        state = 13; // S-полином не делится на g
                    }
                    break;
                }
                case 15: { // Добавление полинома в идеал (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 13; // S-полином не делится на g
                    }
                    break;
                }
                case 16: { // Добавление остатка в идеал
                    state = 15; // Добавление полинома в идеал (автомат)
                    break;
                }
                case 17: { // Increment of J
                    state = 14; // S-полином не делится на g (окончание)
                    break;
                }
                case 18: { // Increment of I
                    state = 6; // Перебор всех пар полиномов
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Внешний цикл
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 7: { // Выводим сообщение о дальнейшем действии
                    comment = GroebnerBasis.this.getComment("MakingBasis.PrintMessage"); 
                    break;
                }
                case 8: { // Построение S-полинома от i-го и j-го полиномов
                    comment = GroebnerBasis.this.getComment("MakingBasis.MakingSPolynomial"); 
                    args = new Object[]{d.g1[d.MakingBasis_i], d.g1[d.MakingBasis_j]}; 
                    break;
                }
                case 9: { // Вычисляет S-полином 2 полиномов из g1 (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // Скопируем G1 в G2
                    comment = GroebnerBasis.this.getComment("MakingBasis.G1ToG2"); 
                    break;
                }
                case 11: { // Остаток от деления полинома на идеал (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 12: { // Нарисуем на экране
                    comment = GroebnerBasis.this.getComment("MakingBasis.draw"); 
                    break;
                }
                case 13: { // S-полином не делится на g
                    if (!eq0(d.reminder)) {
                        comment = GroebnerBasis.this.getComment("MakingBasis.CondRemSNotZero.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("MakingBasis.CondRemSNotZero.false"); 
                    }
                    break;
                }
                case 15: { // Добавление полинома в идеал (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case 7: { // Выводим сообщение о дальнейшем действии
                    							d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    break;
                }
                case 9: { // Вычисляет S-полином 2 полиномов из g1 (автомат)
                    child.drawState(); 
                    break;
                }
                case 10: { // Скопируем G1 в G2
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 11: { // Остаток от деления полинома на идеал (автомат)
                    child.drawState(); 
                    break;
                }
                case 15: { // Добавление полинома в идеал (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Редуцирование полученного базиса.
      */
    private final class reduce extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 17;

        /**
          * Конструктор.
          */
        public reduce() {
            super( 
                "reduce", 
                0, // Номер начального состояния 
                17, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Подготовка к циклу", 
                    "Внешний цикл", 
                    "Подготовка к циклу", 
                    "Перебор полиномов g1", 
                    "Deleting g[i]", 
                    "Checking g1[i]", 
                    "Остаток от деления полинома на идеал (автомат)", 
                    "Если остаток не равен самому полиному", 
                    "Если остаток не равен самому полиному (окончание)", 
                    "Флажок", 
                    "Проверка на 0", 
                    "Проверка на 0 (окончание)", 
                    "Не добавляем", 
                    "Замена полинома в идеале на остаток от деления его на идеал", 
                    "Завершение итерации", 
                    "Завершение итерации", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Подготовка к циклу 
                    -1, // Внешний цикл 
                    -1, // Подготовка к циклу 
                    0, // Перебор полиномов g1 
                    0, // Deleting g[i] 
                    0, // Checking g1[i] 
                    CALL_AUTO_LEVEL, // Остаток от деления полинома на идеал (автомат) 
                    0, // Если остаток не равен самому полиному 
                    -1, // Если остаток не равен самому полиному (окончание) 
                    -1, // Флажок 
                    -1, // Проверка на 0 
                    -1, // Проверка на 0 (окончание) 
                    0, // Не добавляем 
                    1, // Замена полинома в идеале на остаток от деления его на идеал 
                    -1, // Завершение итерации 
                    0, // Завершение итерации 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Подготовка к циклу
                    break;
                }
                case 1: { // Подготовка к циклу
                    stack.pushBoolean(false); 
                    state = 2; // Внешний цикл
                    break;
                }
                case 2: { // Внешний цикл
                    if (d.reduce_b) {
                        state = 3; // Подготовка к циклу
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Подготовка к циклу
                    stack.pushBoolean(false); 
                    state = 4; // Перебор полиномов g1
                    break;
                }
                case 4: { // Перебор полиномов g1
                    if ((!d.reduce_b) && (d.reduce_i < d.reduce_lg)) {
                        state = 5; // Deleting g[i]
                    } else {
                        state = 16; // Завершение итерации
                    }
                    break;
                }
                case 5: { // Deleting g[i]
                    state = 6; // Checking g1[i]
                    break;
                }
                case 6: { // Checking g1[i]
                    state = 7; // Остаток от деления полинома на идеал (автомат)
                    break;
                }
                case 7: { // Остаток от деления полинома на идеал (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // Если остаток не равен самому полиному
                    }
                    break;
                }
                case 8: { // Если остаток не равен самому полиному
                    if (!d.g1[d.reduce_i].equals(d.reminder)) {
                        state = 10; // Флажок
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // Если остаток не равен самому полиному (окончание)
                    }
                    break;
                }
                case 9: { // Если остаток не равен самому полиному (окончание)
                    state = 11; // Проверка на 0
                    break;
                }
                case 10: { // Флажок
                    stack.pushBoolean(true); 
                    state = 9; // Если остаток не равен самому полиному (окончание)
                    break;
                }
                case 11: { // Проверка на 0
                    if (eq0(d.reminder)) {
                        state = 13; // Не добавляем
                    } else {
                        state = 14; // Замена полинома в идеале на остаток от деления его на идеал
                    }
                    break;
                }
                case 12: { // Проверка на 0 (окончание)
                    state = 15; // Завершение итерации
                    break;
                }
                case 13: { // Не добавляем
                    stack.pushBoolean(true); 
                    state = 12; // Проверка на 0 (окончание)
                    break;
                }
                case 14: { // Замена полинома в идеале на остаток от деления его на идеал
                    stack.pushBoolean(false); 
                    state = 12; // Проверка на 0 (окончание)
                    break;
                }
                case 15: { // Завершение итерации
                    stack.pushBoolean(true); 
                    state = 4; // Перебор полиномов g1
                    break;
                }
                case 16: { // Завершение итерации
                    stack.pushBoolean(true); 
                    state = 2; // Внешний цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Подготовка к циклу
                    startSection();
                    storeField(d, "reduce_b");
                    				d.reduce_b = true;
                    storeField(d, "showG2");
                    				d.showG2 = true;
                    break;
                }
                case 2: { // Внешний цикл
                    break;
                }
                case 3: { // Подготовка к циклу
                    startSection();
                    storeField(d, "reduce_b");
                    					d.reduce_b = false;
                    storeField(d, "reduce_lg");
                    					d.reduce_lg = length(d.g);
                    storeField(d, "reduce_i");
                    					d.reduce_i = 0;
                    break;
                }
                case 4: { // Перебор полиномов g1
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
                case 7: { // Остаток от деления полинома на идеал (автомат)
                    if (child == null) {
                        child = new rem(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // Если остаток не равен самому полиному
                    break;
                }
                case 9: { // Если остаток не равен самому полиному (окончание)
                    break;
                }
                case 10: { // Флажок
                    startSection();
                    storeField(d, "reduce_b");
                    							d.reduce_b = true;
                    break;
                }
                case 11: { // Проверка на 0
                    break;
                }
                case 12: { // Проверка на 0 (окончание)
                    break;
                }
                case 13: { // Не добавляем
                    startSection();
                    break;
                }
                case 14: { // Замена полинома в идеале на остаток от деления его на идеал
                    startSection();
                    storeField(d, "checked");
                    							d.checked = new int[] {d.reduce_i};
                    storeField(d, "g");
                    							d.g = copy2x(d.reduce_i);
                    break;
                }
                case 15: { // Завершение итерации
                    startSection();
                    storeField(d, "reduce_i");
                    						d.reduce_i = d.reduce_i + 1;
                    break;
                }
                case 16: { // Завершение итерации
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Подготовка к циклу
                    restoreSection();
                    break;
                }
                case 2: { // Внешний цикл
                    break;
                }
                case 3: { // Подготовка к циклу
                    restoreSection();
                    break;
                }
                case 4: { // Перебор полиномов g1
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
                case 7: { // Остаток от деления полинома на идеал (автомат)
                    if (child == null) {
                        child = new rem(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // Если остаток не равен самому полиному
                    break;
                }
                case 9: { // Если остаток не равен самому полиному (окончание)
                    break;
                }
                case 10: { // Флажок
                    restoreSection();
                    break;
                }
                case 11: { // Проверка на 0
                    break;
                }
                case 12: { // Проверка на 0 (окончание)
                    break;
                }
                case 13: { // Не добавляем
                    restoreSection();
                    break;
                }
                case 14: { // Замена полинома в идеале на остаток от деления его на идеал
                    restoreSection();
                    break;
                }
                case 15: { // Завершение итерации
                    restoreSection();
                    break;
                }
                case 16: { // Завершение итерации
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Подготовка к циклу
                    state = START_STATE; 
                    break;
                }
                case 2: { // Внешний цикл
                    if (stack.popBoolean()) {
                        state = 16; // Завершение итерации
                    } else {
                        state = 1; // Подготовка к циклу
                    }
                    break;
                }
                case 3: { // Подготовка к циклу
                    state = 2; // Внешний цикл
                    break;
                }
                case 4: { // Перебор полиномов g1
                    if (stack.popBoolean()) {
                        state = 15; // Завершение итерации
                    } else {
                        state = 3; // Подготовка к циклу
                    }
                    break;
                }
                case 5: { // Deleting g[i]
                    state = 4; // Перебор полиномов g1
                    break;
                }
                case 6: { // Checking g1[i]
                    state = 5; // Deleting g[i]
                    break;
                }
                case 7: { // Остаток от деления полинома на идеал (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // Checking g1[i]
                    }
                    break;
                }
                case 8: { // Если остаток не равен самому полиному
                    state = 7; // Остаток от деления полинома на идеал (автомат)
                    break;
                }
                case 9: { // Если остаток не равен самому полиному (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // Флажок
                    } else {
                        state = 8; // Если остаток не равен самому полиному
                    }
                    break;
                }
                case 10: { // Флажок
                    state = 8; // Если остаток не равен самому полиному
                    break;
                }
                case 11: { // Проверка на 0
                    state = 9; // Если остаток не равен самому полиному (окончание)
                    break;
                }
                case 12: { // Проверка на 0 (окончание)
                    if (stack.popBoolean()) {
                        state = 13; // Не добавляем
                    } else {
                        state = 14; // Замена полинома в идеале на остаток от деления его на идеал
                    }
                    break;
                }
                case 13: { // Не добавляем
                    state = 11; // Проверка на 0
                    break;
                }
                case 14: { // Замена полинома в идеале на остаток от деления его на идеал
                    state = 11; // Проверка на 0
                    break;
                }
                case 15: { // Завершение итерации
                    state = 12; // Проверка на 0 (окончание)
                    break;
                }
                case 16: { // Завершение итерации
                    state = 4; // Перебор полиномов g1
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Внешний цикл
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 4: { // Перебор полиномов g1
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
                case 7: { // Остаток от деления полинома на идеал (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 8: { // Если остаток не равен самому полиному
                    if (!d.g1[d.reduce_i].equals(d.reminder)) {
                        comment = GroebnerBasis.this.getComment("reduce.CondPolEqToRem.true"); 
                    } else {
                        comment = GroebnerBasis.this.getComment("reduce.CondPolEqToRem.false"); 
                    }
                    args = new Object[]{d.g1[d.reduce_i], d.reminder}; 
                    break;
                }
                case 13: { // Не добавляем
                    comment = GroebnerBasis.this.getComment("reduce.NothingToDo"); 
                    break;
                }
                case 14: { // Замена полинома в идеале на остаток от деления его на идеал
                    comment = GroebnerBasis.this.getComment("reduce.ReplacingPolynomialByReminder"); 
                    args = new Object[]{d.g1[d.reduce_i], d.reminder}; 
                    break;
                }
                case 15: { // Завершение итерации
                    comment = GroebnerBasis.this.getComment("reduce.EndingIteration"); 
                    break;
                }
                case 16: { // Завершение итерации
                    comment = GroebnerBasis.this.getComment("reduce.AfterIteration"); 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case 5: { // Deleting g[i]
                    						d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 7: { // Остаток от деления полинома на идеал (автомат)
                    child.drawState(); 
                    break;
                }
                case 8: { // Если остаток не равен самому полиному
                    						d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 13: { // Не добавляем
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 14: { // Замена полинома в идеале на остаток от деления его на идеал
                    							d.visualizer.printIdeals(new boolean[] {true, true}, d.checked);
                    break;
                }
                case 16: { // Завершение итерации
                    					d.visualizer.printIdeals(new boolean[] {true, true});
                    break;
                }
            }
        }
    }

    /**
      * Главный автомат.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 8;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                8, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Копирование g1 в given.", 
                    "Построение базиса Грёбнера для g1 (автомат)", 
                    "BasisIsMade", 
                    "Редуцирование полученного базиса (автомат)", 
                    "Копирование в g1 начального идеала.", 
                    "Нормировка", 
                    "HideAll", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Копирование g1 в given. 
                    CALL_AUTO_LEVEL, // Построение базиса Грёбнера для g1 (автомат) 
                    1, // BasisIsMade 
                    CALL_AUTO_LEVEL, // Редуцирование полученного базиса (автомат) 
                    -1, // Копирование в g1 начального идеала. 
                    1, // Нормировка 
                    -1, // HideAll 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Копирование g1 в given.
                    break;
                }
                case 1: { // Копирование g1 в given.
                    state = 2; // Построение базиса Грёбнера для g1 (автомат)
                    break;
                }
                case 2: { // Построение базиса Грёбнера для g1 (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 3; // BasisIsMade
                    }
                    break;
                }
                case 3: { // BasisIsMade
                    state = 4; // Редуцирование полученного базиса (автомат)
                    break;
                }
                case 4: { // Редуцирование полученного базиса (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 5; // Копирование в g1 начального идеала.
                    }
                    break;
                }
                case 5: { // Копирование в g1 начального идеала.
                    state = 6; // Нормировка
                    break;
                }
                case 6: { // Нормировка
                    state = 7; // HideAll
                    break;
                }
                case 7: { // HideAll
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Копирование g1 в given.
                    startSection();
                    storeField(d.visualizer, "labels");
                    				d.visualizer.labels = d.visualizer.hide();
                    				d.visualizer.redraw();
                    				g12given();
                    break;
                }
                case 2: { // Построение базиса Грёбнера для g1 (автомат)
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
                case 4: { // Редуцирование полученного базиса (автомат)
                    if (child == null) {
                        child = new reduce(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 5: { // Копирование в g1 начального идеала.
                    startSection();
                    				given2g1();
                    break;
                }
                case 6: { // Нормировка
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Копирование g1 в given.
                    restoreSection();
                    break;
                }
                case 2: { // Построение базиса Грёбнера для g1 (автомат)
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
                case 4: { // Редуцирование полученного базиса (автомат)
                    if (child == null) {
                        child = new reduce(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 5: { // Копирование в g1 начального идеала.
                    restoreSection();
                    break;
                }
                case 6: { // Нормировка
                    restoreSection();
                    break;
                }
                case 7: { // HideAll
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Копирование g1 в given.
                    state = START_STATE; 
                    break;
                }
                case 2: { // Построение базиса Грёбнера для g1 (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // Копирование g1 в given.
                    }
                    break;
                }
                case 3: { // BasisIsMade
                    state = 2; // Построение базиса Грёбнера для g1 (автомат)
                    break;
                }
                case 4: { // Редуцирование полученного базиса (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 3; // BasisIsMade
                    }
                    break;
                }
                case 5: { // Копирование в g1 начального идеала.
                    state = 4; // Редуцирование полученного базиса (автомат)
                    break;
                }
                case 6: { // Нормировка
                    state = 5; // Копирование в g1 начального идеала.
                    break;
                }
                case 7: { // HideAll
                    state = 6; // Нормировка
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 7; // HideAll
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case START_STATE: { // Начальное состояние
                    comment = GroebnerBasis.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 2: { // Построение базиса Грёбнера для g1 (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 3: { // BasisIsMade
                    comment = GroebnerBasis.this.getComment("Main.PreparingForReducing"); 
                    break;
                }
                case 4: { // Редуцирование полученного базиса (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = GroebnerBasis.this.getComment("Main.END_STATE"); 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case START_STATE: { // Начальное состояние
                    				d.visualizer.hide();
                    				d.visualizer.redraw();
                    				d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    break;
                }
                case 2: { // Построение базиса Грёбнера для g1 (автомат)
                    child.drawState(); 
                    break;
                }
                case 3: { // BasisIsMade
                    				d.visualizer.hide();
                    				d.visualizer.printIdeals(new boolean[] {false, true}, d.checked);
                    break;
                }
                case 4: { // Редуцирование полученного базиса (автомат)
                    child.drawState(); 
                    break;
                }
                case 6: { // Нормировка
                    				d.visualizer.printIdeals(new boolean[] {true, true});
                    break;
                }
                case END_STATE: { // Конечное состояние
                    				d.visualizer.redraw();
                    				d.visualizer.printIdeals(new boolean[] {true, true});
                    break;
                }
            }
        }
    }
}
