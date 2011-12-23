package ru.ifmo.vizi.suff_auto;

import java.lang.String;
import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class SuffixAutomaton extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public SuffixAutomaton(Locale locale) {
        super("ru.ifmo.vizi.suff_auto.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экземпляр апплета.
          */
        public SuffixAutomatonVisualizer visualizer = null;

        /**
          * Количество состояний.
          */
        public int nv = 0;

        /**
          * Номер последнего состояния.
          */
        public int last = 0;

        /**
          * Строка.
          */
        public String s = new String("abbac");

        /**
          * Массив переходов.
          */
        public int[][] next = new int[11][256];

        /**
          * Массив глубин состояний.
          */
        public int[] l = new int[11];

        /**
          * Массив суффиксных ссылок.
          */
        public int[] suff = new int[11];

        /**
          * Массив пометок типа состояния.
          */
        public boolean[] upper = new boolean[11];

        /**
          * Массив символов по которым происходит переход.
          */
        public String[] chars = new String[11];

        /**
          * Переменная цикла (Процедура Main).
          */
        public int Main_i;

        /**
          * Вспомогательная переменная (Процедура Main).
          */
        public int Main_p;

        /**
          * Вспомогательная переменная (Процедура Main).
          */
        public int Main_q;

        /**
          * Вспомогательная переменная (Процедура Main).
          */
        public int Main_r;

        /**
          * Вспомогательная переменная цикла (Процедура Main).
          */
        public int Main_j;

        public String toString() {
                	StringBuffer s = new StringBuffer();
                	s.append("nv = ").append(d.nv).append("\n");
            	  	s.append("last = ").append(d.last).append("\n");
            	    s.append("next = \n");
            	    for (int i = 0; i < d.nv; i++) {
            	    	for (int j = 0; j < d.next[i].length; j++) {
            	    		s.append(d.next[i][j]).append(" ");
            	    	}
            	    	s.append("\n");
            	    }
            		s.append("l = \n");
            		for (int i = 0; i < d.nv; i++) {
            			s.append(d.l[i]).append(" ");
            		}
            		s.append("suff = \n");
            		for (int i = 0; i < d.nv; i++) {
            			s.append(d.suff[i]).append(" ");
            		}
            		s.append("upper = \n");
            		for (int i = 0; i < d.nv; i++) {
            			s.append(d.upper[i]).append(" ");
            		}
                    return s.toString();
        }
    }

    /**
      * Строит суффиксный автомат.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 18;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                18, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация", 
                    "Начало цикла", 
                    "Цикл", 
                    "Создание нового состояния", 
                    "Добавление переходов в новое состояние", 
                    "Добавление перехода", 
                    "Условие", 
                    "Условие (окончание)", 
                    "Добавление суффиксной ссылки", 
                    "Инициализация переменной q", 
                    "", 
                    " (окончание)", 
                    "Добавление суффиксной ссылки", 
                    "Создание нового состояния", 
                    "Добавление переходов в новое состояние", 
                    "Добавление перехода", 
                    "Инкремент", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    0, // Инициализация 
                    -1, // Начало цикла 
                    1, // Цикл 
                    0, // Создание нового состояния 
                    0, // Добавление переходов в новое состояние 
                    0, // Добавление перехода 
                    0, // Условие 
                    -1, // Условие (окончание) 
                    0, // Добавление суффиксной ссылки 
                    -1, // Инициализация переменной q 
                    0, //  
                    -1, //  (окончание) 
                    0, // Добавление суффиксной ссылки 
                    0, // Создание нового состояния 
                    0, // Добавление переходов в новое состояние 
                    0, // Добавление перехода 
                    -1, // Инкремент 
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
                    state = 1; // Инициализация
                    break;
                }
                case 1: { // Инициализация
                    state = 2; // Начало цикла
                    break;
                }
                case 2: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 3; // Цикл
                    break;
                }
                case 3: { // Цикл
                    if (d.Main_i < d.s.length()) {
                        state = 4; // Создание нового состояния
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 4: { // Создание нового состояния
                    stack.pushBoolean(false); 
                    state = 5; // Добавление переходов в новое состояние
                    break;
                }
                case 5: { // Добавление переходов в новое состояние
                    if (d.Main_p >= 0 && d.next[d.Main_p][d.s.charAt(d.Main_i)] < 0) {
                        state = 6; // Добавление перехода
                    } else {
                        state = 7; // Условие
                    }
                    break;
                }
                case 6: { // Добавление перехода
                    stack.pushBoolean(true); 
                    state = 5; // Добавление переходов в новое состояние
                    break;
                }
                case 7: { // Условие
                    if (d.Main_p < 0) {
                        state = 9; // Добавление суффиксной ссылки
                    } else {
                        state = 10; // Инициализация переменной q
                    }
                    break;
                }
                case 8: { // Условие (окончание)
                    state = 17; // Инкремент
                    break;
                }
                case 9: { // Добавление суффиксной ссылки
                    stack.pushBoolean(true); 
                    state = 8; // Условие (окончание)
                    break;
                }
                case 10: { // Инициализация переменной q
                    state = 11; 
                    break;
                }
                case 11: { 
                    if (d.l[d.Main_q] <= d.l[d.Main_p] + 1) {
                        state = 13; // Добавление суффиксной ссылки
                    } else {
                        state = 14; // Создание нового состояния
                    }
                    break;
                }
                case 12: { //  (окончание)
                    stack.pushBoolean(false); 
                    state = 8; // Условие (окончание)
                    break;
                }
                case 13: { // Добавление суффиксной ссылки
                    stack.pushBoolean(true); 
                    state = 12; //  (окончание)
                    break;
                }
                case 14: { // Создание нового состояния
                    stack.pushBoolean(false); 
                    state = 15; // Добавление переходов в новое состояние
                    break;
                }
                case 15: { // Добавление переходов в новое состояние
                    if (d.Main_p >= 0 && d.next[d.Main_p][d.s.charAt(d.Main_i)] == d.Main_q) {
                        state = 16; // Добавление перехода
                    } else {
                        stack.pushBoolean(false); 
                        state = 12; //  (окончание)
                    }
                    break;
                }
                case 16: { // Добавление перехода
                    stack.pushBoolean(true); 
                    state = 15; // Добавление переходов в новое состояние
                    break;
                }
                case 17: { // Инкремент
                    stack.pushBoolean(true); 
                    state = 3; // Цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация
                    startSection();
                    storeField(d, "last");
                    d.last = 0;
                    storeArray(d.suff, 0);
                    d.suff[0] = -1;
                    storeField(d, "nv");
                    d.nv = 1;
                    storeArray(d.upper, 0);
                    d.upper[0] = true;
                    storeArray(d.chars, 0);
                    d.chars[0] = "nil";
                    for (int i = 0; i < d.next.length; i++) {
                    	for (int j = 0; j < d.next[0].length; j++) {
                    storeArray(d.next[i], j);
                    		d.next[i][j] = -1;
                    	}
                    }
                    for (int i = 0; i < d.suff.length; i++) {
                    storeArray(d.suff, i);
                    	d.suff[i] = -1;
                    }
                    break;
                }
                case 2: { // Начало цикла
                    d.Main_i = 0;
                    break;
                }
                case 3: { // Цикл
                    break;
                }
                case 4: { // Создание нового состояния
                    startSection();
                    storeField(d, "Main_p");
                    					d.Main_p = d.last;
                    storeField(d, "last");
                    					d.last = d.nv;
                    storeField(d, "nv");
                    					d.nv = d.nv + 1;
                    storeArray(d.l, d.last);
                    					d.l[d.last] = d.l[d.Main_p] + 1;	
                    storeArray(d.upper, d.last);
                    					d.upper[d.last] = true;
                    storeArray(d.chars, d.last);
                    					d.chars[d.last] = "" + d.s.charAt(d.Main_i);
                    break;
                }
                case 5: { // Добавление переходов в новое состояние
                    break;
                }
                case 6: { // Добавление перехода
                    startSection();
                    storeArray(d.next[d.Main_p], d.s.charAt(d.Main_i));
                    						d.next[d.Main_p][d.s.charAt(d.Main_i)] = d.last;
                      storeField(d, "Main_p");
                      						d.Main_p = d.suff[d.Main_p];
                    break;
                }
                case 7: { // Условие
                    break;
                }
                case 8: { // Условие (окончание)
                    break;
                }
                case 9: { // Добавление суффиксной ссылки
                    startSection();
                    storeArray(d.suff, d.last);
                    	            		d.suff[d.last] = 0;
                    break;
                }
                case 10: { // Инициализация переменной q
                    startSection();
                    storeField(d, "Main_q");
                    			d.Main_q = d.next[d.Main_p][d.s.charAt(d.Main_i)];
                    break;
                }
                case 11: { 
                    break;
                }
                case 12: { //  (окончание)
                    break;
                }
                case 13: { // Добавление суффиксной ссылки
                    startSection();
                    storeArray(d.suff, d.last);
                    					d.suff[d.last] = d.Main_q;
                    break;
                }
                case 14: { // Создание нового состояния
                    startSection();
                    storeField(d, "Main_r");
                    	        				d.Main_r = d.nv;
                    storeField(d, "nv");
                    	        				d.nv = d.nv + 1;
                    storeArray(d.upper, d.Main_r);
                    	        				d.upper[d.Main_r] = false;
                    storeArray(d.chars, d.Main_r);
                    	        				d.chars[d.Main_r] = "" + d.s.charAt(d.Main_i);
                    storeArray(d.l, d.Main_r);
                    	        				d.l[d.Main_r] = d.l[d.Main_p] + 1;
                    storeArray(d.suff, d.last);
                    	        				d.suff[d.last] = d.Main_r;
                    storeArray(d.suff, d.Main_r);
                    	        				d.suff[d.Main_r] = d.suff[d.Main_q];
                    storeArray(d.suff, d.Main_q);
                    	        				d.suff[d.Main_q] = d.Main_r;
                    	        				for (d.Main_j = 0; d.Main_j < 256; d.Main_j++) {
                    storeArray(d.next[d.Main_r], d.Main_j);
                    	        					d.next[d.Main_r][d.Main_j] = d.next[d.Main_q][d.Main_j];
                    	        				}
                    break;
                }
                case 15: { // Добавление переходов в новое состояние
                    break;
                }
                case 16: { // Добавление перехода
                    startSection();
                    storeArray(d.next[d.Main_p], d.s.charAt(d.Main_i));
                    										d.next[d.Main_p][d.s.charAt(d.Main_i)] = d.Main_r;
                    storeField(d, "Main_p");
                    										d.Main_p = d.suff[d.Main_p];
                    break;
                }
                case 17: { // Инкремент
                    startSection();
                    storeField(d, "Main_i");
                    d.Main_i = d.Main_i + 1;
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
                case 1: { // Инициализация
                    restoreSection();
                    break;
                }
                case 2: { // Начало цикла
                    break;
                }
                case 3: { // Цикл
                    break;
                }
                case 4: { // Создание нового состояния
                    restoreSection();
                    break;
                }
                case 5: { // Добавление переходов в новое состояние
                    break;
                }
                case 6: { // Добавление перехода
                    restoreSection();
                    break;
                }
                case 7: { // Условие
                    break;
                }
                case 8: { // Условие (окончание)
                    break;
                }
                case 9: { // Добавление суффиксной ссылки
                    restoreSection();
                    break;
                }
                case 10: { // Инициализация переменной q
                    restoreSection();
                    break;
                }
                case 11: { 
                    break;
                }
                case 12: { //  (окончание)
                    break;
                }
                case 13: { // Добавление суффиксной ссылки
                    restoreSection();
                    break;
                }
                case 14: { // Создание нового состояния
                    restoreSection();
                    break;
                }
                case 15: { // Добавление переходов в новое состояние
                    break;
                }
                case 16: { // Добавление перехода
                    restoreSection();
                    break;
                }
                case 17: { // Инкремент
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация
                    state = START_STATE; 
                    break;
                }
                case 2: { // Начало цикла
                    state = 1; // Инициализация
                    break;
                }
                case 3: { // Цикл
                    if (stack.popBoolean()) {
                        state = 17; // Инкремент
                    } else {
                        state = 2; // Начало цикла
                    }
                    break;
                }
                case 4: { // Создание нового состояния
                    state = 3; // Цикл
                    break;
                }
                case 5: { // Добавление переходов в новое состояние
                    if (stack.popBoolean()) {
                        state = 6; // Добавление перехода
                    } else {
                        state = 4; // Создание нового состояния
                    }
                    break;
                }
                case 6: { // Добавление перехода
                    state = 5; // Добавление переходов в новое состояние
                    break;
                }
                case 7: { // Условие
                    state = 5; // Добавление переходов в новое состояние
                    break;
                }
                case 8: { // Условие (окончание)
                    if (stack.popBoolean()) {
                        state = 9; // Добавление суффиксной ссылки
                    } else {
                        state = 12; //  (окончание)
                    }
                    break;
                }
                case 9: { // Добавление суффиксной ссылки
                    state = 7; // Условие
                    break;
                }
                case 10: { // Инициализация переменной q
                    state = 7; // Условие
                    break;
                }
                case 11: { 
                    state = 10; // Инициализация переменной q
                    break;
                }
                case 12: { //  (окончание)
                    if (stack.popBoolean()) {
                        state = 13; // Добавление суффиксной ссылки
                    } else {
                        state = 15; // Добавление переходов в новое состояние
                    }
                    break;
                }
                case 13: { // Добавление суффиксной ссылки
                    state = 11; 
                    break;
                }
                case 14: { // Создание нового состояния
                    state = 11; 
                    break;
                }
                case 15: { // Добавление переходов в новое состояние
                    if (stack.popBoolean()) {
                        state = 16; // Добавление перехода
                    } else {
                        state = 14; // Создание нового состояния
                    }
                    break;
                }
                case 16: { // Добавление перехода
                    state = 15; // Добавление переходов в новое состояние
                    break;
                }
                case 17: { // Инкремент
                    state = 8; // Условие (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 3; // Цикл
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
                    comment = SuffixAutomaton.this.getComment("Main.START_STATE"); 
                    args = new Object[]{new String(d.s)}; 
                    break;
                }
                case 1: { // Инициализация
                    comment = SuffixAutomaton.this.getComment("Main.Initialization"); 
                    break;
                }
                case 3: { // Цикл
                    if (d.Main_i < d.s.length()) {
                        comment = SuffixAutomaton.this.getComment("Main.Loop.true"); 
                    } else {
                        comment = SuffixAutomaton.this.getComment("Main.Loop.false"); 
                    }
                    args = new Object[]{d.Main_i < d.s.length() ? new Character(d.s.charAt(d.Main_i)) : new Character(' ')}; 
                    break;
                }
                case 4: { // Создание нового состояния
                    comment = SuffixAutomaton.this.getComment("Main.NewState"); 
                    args = new Object[]{new Integer(d.last)}; 
                    break;
                }
                case 5: { // Добавление переходов в новое состояние
                    if (d.Main_p >= 0 && d.next[d.Main_p][d.s.charAt(d.Main_i)] < 0) {
                        comment = SuffixAutomaton.this.getComment("Main.AddingEdgeLoop.true"); 
                    } else {
                        comment = SuffixAutomaton.this.getComment("Main.AddingEdgeLoop.false"); 
                    }
                    args = new Object[]{new Integer(d.Main_p), new Character(d.s.charAt(d.Main_i))}; 
                    break;
                }
                case 6: { // Добавление перехода
                    comment = SuffixAutomaton.this.getComment("Main.AddEdge"); 
                    break;
                }
                case 7: { // Условие
                    if (d.Main_p < 0) {
                        comment = SuffixAutomaton.this.getComment("Main.Cond.true"); 
                    } else {
                        comment = SuffixAutomaton.this.getComment("Main.Cond.false"); 
                    }
                    args = new Object[]{new Character(d.s.charAt(d.Main_i))}; 
                    break;
                }
                case 9: { // Добавление суффиксной ссылки
                    comment = SuffixAutomaton.this.getComment("Main.AddingSuffixReference"); 
                    break;
                }
                case 11: { 
                    if (d.l[d.Main_q] <= d.l[d.Main_p] + 1) {
                        comment = SuffixAutomaton.this.getComment("Main.Cond2.true"); 
                    } else {
                        comment = SuffixAutomaton.this.getComment("Main.Cond2.false"); 
                    }
                    args = new Object[]{new Integer(d.Main_q), new Integer(d.Main_p)}; 
                    break;
                }
                case 13: { // Добавление суффиксной ссылки
                    comment = SuffixAutomaton.this.getComment("Main.AddingSuffixReference2"); 
                    args = new Object[]{new Integer(d.Main_q)}; 
                    break;
                }
                case 14: { // Создание нового состояния
                    comment = SuffixAutomaton.this.getComment("Main.AddingState2"); 
                    args = new Object[]{new Integer(d.Main_r), new Integer(d.Main_q)}; 
                    break;
                }
                case 15: { // Добавление переходов в новое состояние
                    if (d.Main_p >= 0 && d.next[d.Main_p][d.s.charAt(d.Main_i)] == d.Main_q) {
                        comment = SuffixAutomaton.this.getComment("Main.AddingEdgeLoop2.true"); 
                    } else {
                        comment = SuffixAutomaton.this.getComment("Main.AddingEdgeLoop2.false"); 
                    }
                    args = new Object[]{new Integer(d.Main_p), new Character(d.s.charAt(d.Main_i))}; 
                    break;
                }
                case 16: { // Добавление перехода
                    comment = SuffixAutomaton.this.getComment("Main.AddEdge2"); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = SuffixAutomaton.this.getComment("Main.END_STATE"); 
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
                    	d.visualizer.draw();
                    break;
                }
                case 1: { // Инициализация
                    		d.visualizer.updateRect(0, 1);
                    		d.visualizer.draw();
                    break;
                }
                case 3: { // Цикл
                    		d.visualizer.clearStates();
                    		d.visualizer.draw();
                    break;
                }
                case 4: { // Создание нового состояния
                    		        		d.visualizer.clearStates();
                    		        		d.visualizer.updateRect(d.last, 1);
                            				d.visualizer.draw();
                    break;
                }
                case 5: { // Добавление переходов в новое состояние
                    	        		d.visualizer.clearStates();
                    		      		d.visualizer.updateRect(d.last, 1);
                    		       		d.visualizer.updateRect(d.Main_p, 2);
                    		      		d.visualizer.draw();
                    break;
                }
                case 6: { // Добавление перехода
                    					d.visualizer.draw();
                    break;
                }
                case 9: { // Добавление суффиксной ссылки
                    				d.visualizer.draw();
                    break;
                }
                case 10: { // Инициализация переменной q
                    		        		d.visualizer.draw();
                    break;
                }
                case 13: { // Добавление суффиксной ссылки
                    				        		d.visualizer.draw();
                    break;
                }
                case 14: { // Создание нового состояния
                    				        		d.visualizer.clearStates();
                    				        		d.visualizer.updateRect(d.Main_r, 1);
                    				        		d.visualizer.updateRect(d.Main_p, 2);
                    				        		d.visualizer.draw();
                    break;
                }
                case 15: { // Добавление переходов в новое состояние
                    									d.visualizer.clearStates();
                    									d.visualizer.updateRect(d.Main_r, 1);
                    									d.visualizer.updateRect(d.Main_p, 2);
                    									d.visualizer.draw();
                    break;
                }
                case 16: { // Добавление перехода
                    							        		d.visualizer.draw();
                    break;
                }
                case END_STATE: { // Конечное состояние
                    		d.visualizer.clearStates();
                    		d.visualizer.draw();
                    break;
                }
            }
        }
    }
}
