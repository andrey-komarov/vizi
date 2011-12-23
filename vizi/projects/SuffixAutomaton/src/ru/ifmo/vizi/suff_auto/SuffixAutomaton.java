package ru.ifmo.vizi.suff_auto;

import java.lang.String;
import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class SuffixAutomaton extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public SuffixAutomaton(Locale locale) {
        super("ru.ifmo.vizi.suff_auto.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ��������� �������.
          */
        public SuffixAutomatonVisualizer visualizer = null;

        /**
          * ���������� ���������.
          */
        public int nv = 0;

        /**
          * ����� ���������� ���������.
          */
        public int last = 0;

        /**
          * ������.
          */
        public String s = new String("abbac");

        /**
          * ������ ���������.
          */
        public int[][] next = new int[11][256];

        /**
          * ������ ������ ���������.
          */
        public int[] l = new int[11];

        /**
          * ������ ���������� ������.
          */
        public int[] suff = new int[11];

        /**
          * ������ ������� ���� ���������.
          */
        public boolean[] upper = new boolean[11];

        /**
          * ������ �������� �� ������� ���������� �������.
          */
        public String[] chars = new String[11];

        /**
          * ���������� ����� (��������� Main).
          */
        public int Main_i;

        /**
          * ��������������� ���������� (��������� Main).
          */
        public int Main_p;

        /**
          * ��������������� ���������� (��������� Main).
          */
        public int Main_q;

        /**
          * ��������������� ���������� (��������� Main).
          */
        public int Main_r;

        /**
          * ��������������� ���������� ����� (��������� Main).
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
      * ������ ���������� �������.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 18;

        /**
          * �����������.
          */
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                18, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������������", 
                    "������ �����", 
                    "����", 
                    "�������� ������ ���������", 
                    "���������� ��������� � ����� ���������", 
                    "���������� ��������", 
                    "�������", 
                    "������� (���������)", 
                    "���������� ���������� ������", 
                    "������������� ���������� q", 
                    "", 
                    " (���������)", 
                    "���������� ���������� ������", 
                    "�������� ������ ���������", 
                    "���������� ��������� � ����� ���������", 
                    "���������� ��������", 
                    "���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    0, // ������������� 
                    -1, // ������ ����� 
                    1, // ���� 
                    0, // �������� ������ ��������� 
                    0, // ���������� ��������� � ����� ��������� 
                    0, // ���������� �������� 
                    0, // ������� 
                    -1, // ������� (���������) 
                    0, // ���������� ���������� ������ 
                    -1, // ������������� ���������� q 
                    0, //  
                    -1, //  (���������) 
                    0, // ���������� ���������� ������ 
                    0, // �������� ������ ��������� 
                    0, // ���������� ��������� � ����� ��������� 
                    0, // ���������� �������� 
                    -1, // ��������� 
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
                    state = 1; // �������������
                    break;
                }
                case 1: { // �������������
                    state = 2; // ������ �����
                    break;
                }
                case 2: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 3; // ����
                    break;
                }
                case 3: { // ����
                    if (d.Main_i < d.s.length()) {
                        state = 4; // �������� ������ ���������
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 4: { // �������� ������ ���������
                    stack.pushBoolean(false); 
                    state = 5; // ���������� ��������� � ����� ���������
                    break;
                }
                case 5: { // ���������� ��������� � ����� ���������
                    if (d.Main_p >= 0 && d.next[d.Main_p][d.s.charAt(d.Main_i)] < 0) {
                        state = 6; // ���������� ��������
                    } else {
                        state = 7; // �������
                    }
                    break;
                }
                case 6: { // ���������� ��������
                    stack.pushBoolean(true); 
                    state = 5; // ���������� ��������� � ����� ���������
                    break;
                }
                case 7: { // �������
                    if (d.Main_p < 0) {
                        state = 9; // ���������� ���������� ������
                    } else {
                        state = 10; // ������������� ���������� q
                    }
                    break;
                }
                case 8: { // ������� (���������)
                    state = 17; // ���������
                    break;
                }
                case 9: { // ���������� ���������� ������
                    stack.pushBoolean(true); 
                    state = 8; // ������� (���������)
                    break;
                }
                case 10: { // ������������� ���������� q
                    state = 11; 
                    break;
                }
                case 11: { 
                    if (d.l[d.Main_q] <= d.l[d.Main_p] + 1) {
                        state = 13; // ���������� ���������� ������
                    } else {
                        state = 14; // �������� ������ ���������
                    }
                    break;
                }
                case 12: { //  (���������)
                    stack.pushBoolean(false); 
                    state = 8; // ������� (���������)
                    break;
                }
                case 13: { // ���������� ���������� ������
                    stack.pushBoolean(true); 
                    state = 12; //  (���������)
                    break;
                }
                case 14: { // �������� ������ ���������
                    stack.pushBoolean(false); 
                    state = 15; // ���������� ��������� � ����� ���������
                    break;
                }
                case 15: { // ���������� ��������� � ����� ���������
                    if (d.Main_p >= 0 && d.next[d.Main_p][d.s.charAt(d.Main_i)] == d.Main_q) {
                        state = 16; // ���������� ��������
                    } else {
                        stack.pushBoolean(false); 
                        state = 12; //  (���������)
                    }
                    break;
                }
                case 16: { // ���������� ��������
                    stack.pushBoolean(true); 
                    state = 15; // ���������� ��������� � ����� ���������
                    break;
                }
                case 17: { // ���������
                    stack.pushBoolean(true); 
                    state = 3; // ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������
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
                case 2: { // ������ �����
                    d.Main_i = 0;
                    break;
                }
                case 3: { // ����
                    break;
                }
                case 4: { // �������� ������ ���������
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
                case 5: { // ���������� ��������� � ����� ���������
                    break;
                }
                case 6: { // ���������� ��������
                    startSection();
                    storeArray(d.next[d.Main_p], d.s.charAt(d.Main_i));
                    						d.next[d.Main_p][d.s.charAt(d.Main_i)] = d.last;
                      storeField(d, "Main_p");
                      						d.Main_p = d.suff[d.Main_p];
                    break;
                }
                case 7: { // �������
                    break;
                }
                case 8: { // ������� (���������)
                    break;
                }
                case 9: { // ���������� ���������� ������
                    startSection();
                    storeArray(d.suff, d.last);
                    	            		d.suff[d.last] = 0;
                    break;
                }
                case 10: { // ������������� ���������� q
                    startSection();
                    storeField(d, "Main_q");
                    			d.Main_q = d.next[d.Main_p][d.s.charAt(d.Main_i)];
                    break;
                }
                case 11: { 
                    break;
                }
                case 12: { //  (���������)
                    break;
                }
                case 13: { // ���������� ���������� ������
                    startSection();
                    storeArray(d.suff, d.last);
                    					d.suff[d.last] = d.Main_q;
                    break;
                }
                case 14: { // �������� ������ ���������
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
                case 15: { // ���������� ��������� � ����� ���������
                    break;
                }
                case 16: { // ���������� ��������
                    startSection();
                    storeArray(d.next[d.Main_p], d.s.charAt(d.Main_i));
                    										d.next[d.Main_p][d.s.charAt(d.Main_i)] = d.Main_r;
                    storeField(d, "Main_p");
                    										d.Main_p = d.suff[d.Main_p];
                    break;
                }
                case 17: { // ���������
                    startSection();
                    storeField(d, "Main_i");
                    d.Main_i = d.Main_i + 1;
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
                case 1: { // �������������
                    restoreSection();
                    break;
                }
                case 2: { // ������ �����
                    break;
                }
                case 3: { // ����
                    break;
                }
                case 4: { // �������� ������ ���������
                    restoreSection();
                    break;
                }
                case 5: { // ���������� ��������� � ����� ���������
                    break;
                }
                case 6: { // ���������� ��������
                    restoreSection();
                    break;
                }
                case 7: { // �������
                    break;
                }
                case 8: { // ������� (���������)
                    break;
                }
                case 9: { // ���������� ���������� ������
                    restoreSection();
                    break;
                }
                case 10: { // ������������� ���������� q
                    restoreSection();
                    break;
                }
                case 11: { 
                    break;
                }
                case 12: { //  (���������)
                    break;
                }
                case 13: { // ���������� ���������� ������
                    restoreSection();
                    break;
                }
                case 14: { // �������� ������ ���������
                    restoreSection();
                    break;
                }
                case 15: { // ���������� ��������� � ����� ���������
                    break;
                }
                case 16: { // ���������� ��������
                    restoreSection();
                    break;
                }
                case 17: { // ���������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // �������������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������ �����
                    state = 1; // �������������
                    break;
                }
                case 3: { // ����
                    if (stack.popBoolean()) {
                        state = 17; // ���������
                    } else {
                        state = 2; // ������ �����
                    }
                    break;
                }
                case 4: { // �������� ������ ���������
                    state = 3; // ����
                    break;
                }
                case 5: { // ���������� ��������� � ����� ���������
                    if (stack.popBoolean()) {
                        state = 6; // ���������� ��������
                    } else {
                        state = 4; // �������� ������ ���������
                    }
                    break;
                }
                case 6: { // ���������� ��������
                    state = 5; // ���������� ��������� � ����� ���������
                    break;
                }
                case 7: { // �������
                    state = 5; // ���������� ��������� � ����� ���������
                    break;
                }
                case 8: { // ������� (���������)
                    if (stack.popBoolean()) {
                        state = 9; // ���������� ���������� ������
                    } else {
                        state = 12; //  (���������)
                    }
                    break;
                }
                case 9: { // ���������� ���������� ������
                    state = 7; // �������
                    break;
                }
                case 10: { // ������������� ���������� q
                    state = 7; // �������
                    break;
                }
                case 11: { 
                    state = 10; // ������������� ���������� q
                    break;
                }
                case 12: { //  (���������)
                    if (stack.popBoolean()) {
                        state = 13; // ���������� ���������� ������
                    } else {
                        state = 15; // ���������� ��������� � ����� ���������
                    }
                    break;
                }
                case 13: { // ���������� ���������� ������
                    state = 11; 
                    break;
                }
                case 14: { // �������� ������ ���������
                    state = 11; 
                    break;
                }
                case 15: { // ���������� ��������� � ����� ���������
                    if (stack.popBoolean()) {
                        state = 16; // ���������� ��������
                    } else {
                        state = 14; // �������� ������ ���������
                    }
                    break;
                }
                case 16: { // ���������� ��������
                    state = 15; // ���������� ��������� � ����� ���������
                    break;
                }
                case 17: { // ���������
                    state = 8; // ������� (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 3; // ����
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
                    comment = SuffixAutomaton.this.getComment("Main.START_STATE"); 
                    args = new Object[]{new String(d.s)}; 
                    break;
                }
                case 1: { // �������������
                    comment = SuffixAutomaton.this.getComment("Main.Initialization"); 
                    break;
                }
                case 3: { // ����
                    if (d.Main_i < d.s.length()) {
                        comment = SuffixAutomaton.this.getComment("Main.Loop.true"); 
                    } else {
                        comment = SuffixAutomaton.this.getComment("Main.Loop.false"); 
                    }
                    args = new Object[]{d.Main_i < d.s.length() ? new Character(d.s.charAt(d.Main_i)) : new Character(' ')}; 
                    break;
                }
                case 4: { // �������� ������ ���������
                    comment = SuffixAutomaton.this.getComment("Main.NewState"); 
                    args = new Object[]{new Integer(d.last)}; 
                    break;
                }
                case 5: { // ���������� ��������� � ����� ���������
                    if (d.Main_p >= 0 && d.next[d.Main_p][d.s.charAt(d.Main_i)] < 0) {
                        comment = SuffixAutomaton.this.getComment("Main.AddingEdgeLoop.true"); 
                    } else {
                        comment = SuffixAutomaton.this.getComment("Main.AddingEdgeLoop.false"); 
                    }
                    args = new Object[]{new Integer(d.Main_p), new Character(d.s.charAt(d.Main_i))}; 
                    break;
                }
                case 6: { // ���������� ��������
                    comment = SuffixAutomaton.this.getComment("Main.AddEdge"); 
                    break;
                }
                case 7: { // �������
                    if (d.Main_p < 0) {
                        comment = SuffixAutomaton.this.getComment("Main.Cond.true"); 
                    } else {
                        comment = SuffixAutomaton.this.getComment("Main.Cond.false"); 
                    }
                    args = new Object[]{new Character(d.s.charAt(d.Main_i))}; 
                    break;
                }
                case 9: { // ���������� ���������� ������
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
                case 13: { // ���������� ���������� ������
                    comment = SuffixAutomaton.this.getComment("Main.AddingSuffixReference2"); 
                    args = new Object[]{new Integer(d.Main_q)}; 
                    break;
                }
                case 14: { // �������� ������ ���������
                    comment = SuffixAutomaton.this.getComment("Main.AddingState2"); 
                    args = new Object[]{new Integer(d.Main_r), new Integer(d.Main_q)}; 
                    break;
                }
                case 15: { // ���������� ��������� � ����� ���������
                    if (d.Main_p >= 0 && d.next[d.Main_p][d.s.charAt(d.Main_i)] == d.Main_q) {
                        comment = SuffixAutomaton.this.getComment("Main.AddingEdgeLoop2.true"); 
                    } else {
                        comment = SuffixAutomaton.this.getComment("Main.AddingEdgeLoop2.false"); 
                    }
                    args = new Object[]{new Integer(d.Main_p), new Character(d.s.charAt(d.Main_i))}; 
                    break;
                }
                case 16: { // ���������� ��������
                    comment = SuffixAutomaton.this.getComment("Main.AddEdge2"); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = SuffixAutomaton.this.getComment("Main.END_STATE"); 
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
                    	d.visualizer.draw();
                    break;
                }
                case 1: { // �������������
                    		d.visualizer.updateRect(0, 1);
                    		d.visualizer.draw();
                    break;
                }
                case 3: { // ����
                    		d.visualizer.clearStates();
                    		d.visualizer.draw();
                    break;
                }
                case 4: { // �������� ������ ���������
                    		        		d.visualizer.clearStates();
                    		        		d.visualizer.updateRect(d.last, 1);
                            				d.visualizer.draw();
                    break;
                }
                case 5: { // ���������� ��������� � ����� ���������
                    	        		d.visualizer.clearStates();
                    		      		d.visualizer.updateRect(d.last, 1);
                    		       		d.visualizer.updateRect(d.Main_p, 2);
                    		      		d.visualizer.draw();
                    break;
                }
                case 6: { // ���������� ��������
                    					d.visualizer.draw();
                    break;
                }
                case 9: { // ���������� ���������� ������
                    				d.visualizer.draw();
                    break;
                }
                case 10: { // ������������� ���������� q
                    		        		d.visualizer.draw();
                    break;
                }
                case 13: { // ���������� ���������� ������
                    				        		d.visualizer.draw();
                    break;
                }
                case 14: { // �������� ������ ���������
                    				        		d.visualizer.clearStates();
                    				        		d.visualizer.updateRect(d.Main_r, 1);
                    				        		d.visualizer.updateRect(d.Main_p, 2);
                    				        		d.visualizer.draw();
                    break;
                }
                case 15: { // ���������� ��������� � ����� ���������
                    									d.visualizer.clearStates();
                    									d.visualizer.updateRect(d.Main_r, 1);
                    									d.visualizer.updateRect(d.Main_p, 2);
                    									d.visualizer.draw();
                    break;
                }
                case 16: { // ���������� ��������
                    							        		d.visualizer.draw();
                    break;
                }
                case END_STATE: { // �������� ���������
                    		d.visualizer.clearStates();
                    		d.visualizer.draw();
                    break;
                }
            }
        }
    }
}
