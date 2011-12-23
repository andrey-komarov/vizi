package ru.ifmo.vizi.Prim;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class Prim extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public Prim(Locale locale) {
        super("ru.ifmo.vizi.Prim.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ������� ����������.
          */
        public int[][] a = new int[21][21];

        /**
          * ������ ������ ������.
          */
        public int[] key = new int[21];

        /**
          * ������ ����� ������.
          */
        public boolean[] q = new boolean[21];

        /**
          * ������ ����� ������ ��� �������� �����.
          */
        public boolean[] w = new boolean[21];

        /**
          * ������ ��������� ������.
          */
        public int[] p = new int[21];

        /**
          * ����.
          */
        public int[] st = new int[21];

        /**
          * ���������� ������ � ������ �����.
          */
        public int m = 0;

        /**
          * ���������� ������.
          */
        public int countVertex = 0;

        /**
          * ����������� �������.
          */
        public int min = 0;

        /**
          * ������������� ���� ���������.
          */
        public int step = 0;

        /**
          * ���������� �����.
          */
        public int i = 0;

        /**
          * ���������� �����.
          */
        public int j = 0;

        /**
          * ������ �����.
          */
        public int nstack = 0;

        /**
          * �����������.
          */
        public String comment = new String();

        /**
          * ��������� �������������.
          */
        public PrimVisualizer visualizer = null;

        public String toString() {
            			return("");
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
        private final int END_STATE = 6;

        /**
          * �����������.
          */
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                6, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������� ����� (�������)", 
                    "������� ����� �� ���������", 
                    "������� ����� �� ��������� (���������)", 
                    "�������� ����� (�������)", 
                    "������� ���� 5", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    CALL_AUTO_LEVEL, // �������� ����� (�������) 
                    0, // ������� ����� �� ��������� 
                    -1, // ������� ����� �� ��������� (���������) 
                    CALL_AUTO_LEVEL, // �������� ����� (�������) 
                    -1, // ������� ���� 5 
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
                    state = 1; // �������� ����� (�������)
                    break;
                }
                case 1: { // �������� ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // ������� ����� �� ���������
                    }
                    break;
                }
                case 2: { // ������� ����� �� ���������
                    if (d.m == d.countVertex) {
                        state = 4; // �������� ����� (�������)
                    } else {
                        stack.pushBoolean(false); 
                        state = 3; // ������� ����� �� ��������� (���������)
                    }
                    break;
                }
                case 3: { // ������� ����� �� ��������� (���������)
                    state = END_STATE; 
                    break;
                }
                case 4: { // �������� ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 5; // ������� ���� 5
                    }
                    break;
                }
                case 5: { // ������� ���� 5
                    stack.pushBoolean(true); 
                    state = 3; // ������� ����� �� ��������� (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������� ����� (�������)
                    if (child == null) {
                        child = new CheckGraph(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // ������� ����� �� ���������
                    break;
                }
                case 3: { // ������� ����� �� ��������� (���������)
                    break;
                }
                case 4: { // �������� ����� (�������)
                    if (child == null) {
                        child = new PrimAlgorithm(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 5: { // ������� ���� 5
                    startSection();
                    storeField(d, "step");
                    						d.step = 5;
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
                case 1: { // �������� ����� (�������)
                    if (child == null) {
                        child = new CheckGraph(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // ������� ����� �� ���������
                    break;
                }
                case 3: { // ������� ����� �� ��������� (���������)
                    break;
                }
                case 4: { // �������� ����� (�������)
                    if (child == null) {
                        child = new PrimAlgorithm(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 5: { // ������� ���� 5
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // �������� ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // ������� ����� �� ���������
                    state = 1; // �������� ����� (�������)
                    break;
                }
                case 3: { // ������� ����� �� ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 5; // ������� ���� 5
                    } else {
                        state = 2; // ������� ����� �� ���������
                    }
                    break;
                }
                case 4: { // �������� ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 2; // ������� ����� �� ���������
                    }
                    break;
                }
                case 5: { // ������� ���� 5
                    state = 4; // �������� ����� (�������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 3; // ������� ����� �� ��������� (���������)
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
                    comment = Prim.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // �������� ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 2: { // ������� ����� �� ���������
                    if (d.m == d.countVertex) {
                        comment = Prim.this.getComment("Main.connectednessCheck.true"); 
                    } else {
                        comment = Prim.this.getComment("Main.connectednessCheck.false"); 
                    }
                    break;
                }
                case 4: { // �������� ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 5: { // ������� ���� 5
                    comment = Prim.this.getComment("Main.MarkStep5"); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = Prim.this.getComment("Main.END_STATE"); 
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
                case 1: { // �������� ����� (�������)
                    child.drawState(); 
                    break;
                }
                case 4: { // �������� ����� (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    				d.visualizer.updateView();
                    break;
                }
            }
        }
    }

    /**
      * �������� �����.
      */
    private final class PrimAlgorithm extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 13;

        /**
          * �����������.
          */
        public PrimAlgorithm() {
            super( 
                "PrimAlgorithm", 
                0, // ����� ���������� ��������� 
                13, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ����� 1", 
                    "���� 1", 
                    "��� ����� 1", 
                    "������������� ������ ������", 
                    "������� ����", 
                    "������� ���������� �� ������� ����� ������� � ����������� ������ (�������)", 
                    "���������� �� ������� ����� ����������� �������", 
                    "������� ���������� ������ ������ (�������)", 
                    "�������� ����� ���������", 
                    "�������� ����� ��������� (���������)", 
                    "���������� ������ ������", 
                    "���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ����� 1 
                    -1, // ���� 1 
                    -1, // ��� ����� 1 
                    0, // ������������� ������ ������ 
                    -1, // ������� ���� 
                    CALL_AUTO_LEVEL, // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������) 
                    0, // ���������� �� ������� ����� ����������� ������� 
                    CALL_AUTO_LEVEL, // ������� ���������� ������ ������ (�������) 
                    -1, // �������� ����� ��������� 
                    -1, // �������� ����� ��������� (���������) 
                    0, // ���������� ������ ������ 
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
                    state = 1; // ������������� ����� 1
                    break;
                }
                case 1: { // ������������� ����� 1
                    stack.pushBoolean(false); 
                    state = 2; // ���� 1
                    break;
                }
                case 2: { // ���� 1
                    if ((d.i <= d.countVertex)) {
                        state = 3; // ��� ����� 1
                    } else {
                        state = 4; // ������������� ������ ������
                    }
                    break;
                }
                case 3: { // ��� ����� 1
                    stack.pushBoolean(true); 
                    state = 2; // ���� 1
                    break;
                }
                case 4: { // ������������� ������ ������
                    stack.pushBoolean(false); 
                    state = 5; // ������� ����
                    break;
                }
                case 5: { // ������� ����
                    if (d.m > 0) {
                        state = 6; // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 6: { // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 7; // ���������� �� ������� ����� ����������� �������
                    }
                    break;
                }
                case 7: { // ���������� �� ������� ����� ����������� �������
                    state = 8; // ������� ���������� ������ ������ (�������)
                    break;
                }
                case 8: { // ������� ���������� ������ ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 9; // �������� ����� ���������
                    }
                    break;
                }
                case 9: { // �������� ����� ���������
                    if (d.m > 1) {
                        state = 11; // ���������� ������ ������
                    } else {
                        stack.pushBoolean(false); 
                        state = 10; // �������� ����� ��������� (���������)
                    }
                    break;
                }
                case 10: { // �������� ����� ��������� (���������)
                    state = 12; // ���������
                    break;
                }
                case 11: { // ���������� ������ ������
                    stack.pushBoolean(true); 
                    state = 10; // �������� ����� ��������� (���������)
                    break;
                }
                case 12: { // ���������
                    stack.pushBoolean(true); 
                    state = 5; // ������� ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� ����� 1
                    startSection();
                    storeField(d, "m");
                    				d.m = d.countVertex;
                    storeField(d, "i");
                    				d.i = 1;
                    storeField(d, "min");
                    				d.min = 0;
                    break;
                }
                case 2: { // ���� 1
                    break;
                }
                case 3: { // ��� ����� 1
                    startSection();
                    storeArray(d.q, d.i);
                    					d.q[d.i] = true;
                    storeArray(d.key, d.i);
                    					d.key[d.i] = 1000000;
                    storeField(d, "i");
                    					d.i = d.i + 1;	
                    break;
                }
                case 4: { // ������������� ������ ������
                    startSection();
                    storeArray(d.key, 1);
                    				d.key[1] = 0;	
                    break;
                }
                case 5: { // ������� ����
                    break;
                }
                case 6: { // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    if (child == null) {
                        child = new extractMinimum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 7: { // ���������� �� ������� ����� ����������� �������
                    startSection();
                    storeField(d, "step");
                    					d.step = 1;
                    storeField(d, "nstack");
                    					d.nstack = 0;
                    break;
                }
                case 8: { // ������� ���������� ������ ������ (�������)
                    if (child == null) {
                        child = new updateKeys(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 9: { // �������� ����� ���������
                    break;
                }
                case 10: { // �������� ����� ��������� (���������)
                    break;
                }
                case 11: { // ���������� ������ ������
                    startSection();
                    break;
                }
                case 12: { // ���������
                    startSection();
                    storeField(d, "m");
                    					d.m = d.m - 1;
                    storeField(d, "step");
                    					d.step = 2;
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
                case 1: { // ������������� ����� 1
                    restoreSection();
                    break;
                }
                case 2: { // ���� 1
                    break;
                }
                case 3: { // ��� ����� 1
                    restoreSection();
                    break;
                }
                case 4: { // ������������� ������ ������
                    restoreSection();
                    break;
                }
                case 5: { // ������� ����
                    break;
                }
                case 6: { // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    if (child == null) {
                        child = new extractMinimum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 7: { // ���������� �� ������� ����� ����������� �������
                    restoreSection();
                    break;
                }
                case 8: { // ������� ���������� ������ ������ (�������)
                    if (child == null) {
                        child = new updateKeys(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 9: { // �������� ����� ���������
                    break;
                }
                case 10: { // �������� ����� ��������� (���������)
                    break;
                }
                case 11: { // ���������� ������ ������
                    restoreSection();
                    break;
                }
                case 12: { // ���������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� ����� 1
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���� 1
                    if (stack.popBoolean()) {
                        state = 3; // ��� ����� 1
                    } else {
                        state = 1; // ������������� ����� 1
                    }
                    break;
                }
                case 3: { // ��� ����� 1
                    state = 2; // ���� 1
                    break;
                }
                case 4: { // ������������� ������ ������
                    state = 2; // ���� 1
                    break;
                }
                case 5: { // ������� ����
                    if (stack.popBoolean()) {
                        state = 12; // ���������
                    } else {
                        state = 4; // ������������� ������ ������
                    }
                    break;
                }
                case 6: { // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 5; // ������� ����
                    }
                    break;
                }
                case 7: { // ���������� �� ������� ����� ����������� �������
                    state = 6; // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    break;
                }
                case 8: { // ������� ���������� ������ ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 7; // ���������� �� ������� ����� ����������� �������
                    }
                    break;
                }
                case 9: { // �������� ����� ���������
                    state = 8; // ������� ���������� ������ ������ (�������)
                    break;
                }
                case 10: { // �������� ����� ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 11; // ���������� ������ ������
                    } else {
                        state = 9; // �������� ����� ���������
                    }
                    break;
                }
                case 11: { // ���������� ������ ������
                    state = 9; // �������� ����� ���������
                    break;
                }
                case 12: { // ���������
                    state = 10; // �������� ����� ��������� (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 5; // ������� ����
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
                case 1: { // ������������� ����� 1
                    comment = Prim.this.getComment("PrimAlgorithm.InitLoop1"); 
                    break;
                }
                case 3: { // ��� ����� 1
                    comment = Prim.this.getComment("PrimAlgorithm.StepLoop1"); 
                    break;
                }
                case 4: { // ������������� ������ ������
                    comment = Prim.this.getComment("PrimAlgorithm.InitKeys"); 
                    break;
                }
                case 6: { // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // ���������� �� ������� ����� ����������� �������
                    comment = Prim.this.getComment("PrimAlgorithm.ExtractMin"); 
                    args = new Object[]{new Integer(d.min)}; 
                    break;
                }
                case 8: { // ������� ���������� ������ ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 11: { // ���������� ������ ������
                    comment = Prim.this.getComment("PrimAlgorithm.UpdateKeysOfVertex"); 
                    args = new Object[]{d.comment}; 
                    break;
                }
                case 12: { // ���������
                    comment = Prim.this.getComment("PrimAlgorithm.Decrement"); 
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
                case 4: { // ������������� ������ ������
                    				d.visualizer.updateView();	
                    break;
                }
                case 6: { // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 7: { // ���������� �� ������� ����� ����������� �������
                    					d.visualizer.updateView();	
                    break;
                }
                case 8: { // ������� ���������� ������ ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 11: { // ���������� ������ ������
                    							d.visualizer.updateView();	
                    break;
                }
            }
        }
    }

    /**
      * ������� ���������� �� ������� ����� ������� � ����������� ������.
      */
    private final class extractMinimum extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 5;

        /**
          * �����������.
          */
        public extractMinimum() {
            super( 
                "extractMinimum", 
                0, // ����� ���������� ��������� 
                5, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ����� 2", 
                    "����2", 
                    "��� ����� 2", 
                    "�������� ����������� �������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ����� 2 
                    -1, // ����2 
                    -1, // ��� ����� 2 
                    -1, // �������� ����������� ������� 
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
                    state = 1; // ������������� ����� 2
                    break;
                }
                case 1: { // ������������� ����� 2
                    stack.pushBoolean(false); 
                    state = 2; // ����2
                    break;
                }
                case 2: { // ����2
                    if ((d.i <= d.countVertex)) {
                        state = 3; // ��� ����� 2
                    } else {
                        state = 4; // �������� ����������� �������
                    }
                    break;
                }
                case 3: { // ��� ����� 2
                    stack.pushBoolean(true); 
                    state = 2; // ����2
                    break;
                }
                case 4: { // �������� ����������� �������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� ����� 2
                    startSection();
                    storeField(d, "min");
                    				d.min = 0;
                    storeField(d, "i");
                    				d.i = 1;	
                    break;
                }
                case 2: { // ����2
                    break;
                }
                case 3: { // ��� ����� 2
                    startSection();
                    					if ((d.q[d.i]) & ((d.min == 0) || (d.key[d.min] > d.key[d.i]))) {
                    storeField(d, "min");
                    					d.min = d.i;
                    					}
                    storeField(d, "i");
                    					d.i = d.i + 1;
                    break;
                }
                case 4: { // �������� ����������� �������
                    startSection();
                    storeArray(d.q, d.min);
                    				d.q[d.min] = false;
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
                case 1: { // ������������� ����� 2
                    restoreSection();
                    break;
                }
                case 2: { // ����2
                    break;
                }
                case 3: { // ��� ����� 2
                    restoreSection();
                    break;
                }
                case 4: { // �������� ����������� �������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� ����� 2
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����2
                    if (stack.popBoolean()) {
                        state = 3; // ��� ����� 2
                    } else {
                        state = 1; // ������������� ����� 2
                    }
                    break;
                }
                case 3: { // ��� ����� 2
                    state = 2; // ����2
                    break;
                }
                case 4: { // �������� ����������� �������
                    state = 2; // ����2
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 4; // �������� ����������� �������
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
                case 1: { // ������������� ����� 2
                    comment = Prim.this.getComment("extractMinimum.InitLoop2"); 
                    break;
                }
                case 3: { // ��� ����� 2
                    comment = Prim.this.getComment("extractMinimum.StepLoop2"); 
                    break;
                }
                case 4: { // �������� ����������� �������
                    comment = Prim.this.getComment("extractMinimum.MarkMinimalVertex"); 
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
            }
        }
    }

    /**
      * ������� ���������� ������ ������.
      */
    private final class updateKeys extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 7;

        /**
          * �����������.
          */
        public updateKeys() {
            super( 
                "updateKeys", 
                0, // ����� ���������� ��������� 
                7, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ����� 3", 
                    "����3", 
                    "��� ����� 3", 
                    "������������� ����� 6", 
                    "����6", 
                    "��� ����� 6", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ����� 3 
                    -1, // ����3 
                    -1, // ��� ����� 3 
                    -1, // ������������� ����� 6 
                    -1, // ����6 
                    -1, // ��� ����� 6 
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
                    state = 1; // ������������� ����� 3
                    break;
                }
                case 1: { // ������������� ����� 3
                    stack.pushBoolean(false); 
                    state = 2; // ����3
                    break;
                }
                case 2: { // ����3
                    if ((d.i <= d.countVertex)) {
                        state = 3; // ��� ����� 3
                    } else {
                        state = 4; // ������������� ����� 6
                    }
                    break;
                }
                case 3: { // ��� ����� 3
                    stack.pushBoolean(true); 
                    state = 2; // ����3
                    break;
                }
                case 4: { // ������������� ����� 6
                    stack.pushBoolean(false); 
                    state = 5; // ����6
                    break;
                }
                case 5: { // ����6
                    if ((d.i <= d.nstack)) {
                        state = 6; // ��� ����� 6
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 6: { // ��� ����� 6
                    stack.pushBoolean(true); 
                    state = 5; // ����6
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� ����� 3
                    startSection();
                    storeField(d, "i");
                    				d.i = 1;	
                    break;
                }
                case 2: { // ����3
                    break;
                }
                case 3: { // ��� ����� 3
                    startSection();
                    					if ((d.q[d.i]) & (0 < d.a[d.min][d.i]) & (d.a[d.min][d.i] < d.key[d.i])) {
                    storeArray(d.p, d.i);
                    						d.p[d.i] = d.min;
                    storeArray(d.key, d.i);
                    						d.key[d.i] = d.a[d.min][d.i];
                    storeField(d, "nstack");
                    						d.nstack = d.nstack + 1;
                    storeArray(d.st, d.nstack);
                    						d.st[d.nstack] = d.i;
                    					}
                    storeField(d, "i");
                    					d.i = d.i + 1;
                    break;
                }
                case 4: { // ������������� ����� 6
                    startSection();
                    storeField(d, "i");
                    				d.i = 1;
                    storeField(d, "comment");
                    				d.comment = "";	
                    break;
                }
                case 5: { // ����6
                    break;
                }
                case 6: { // ��� ����� 6
                    startSection();
                    				        if (d.i > 1) {
                    storeField(d, "comment");
                    						d.comment = d.comment + ", ";
                    					}
                    storeField(d, "comment");
                    					d.comment = d.comment + Integer.toString(d.st[d.i]);
                    storeField(d, "i");
                    					d.i = d.i + 1;
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
                case 1: { // ������������� ����� 3
                    restoreSection();
                    break;
                }
                case 2: { // ����3
                    break;
                }
                case 3: { // ��� ����� 3
                    restoreSection();
                    break;
                }
                case 4: { // ������������� ����� 6
                    restoreSection();
                    break;
                }
                case 5: { // ����6
                    break;
                }
                case 6: { // ��� ����� 6
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� ����� 3
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����3
                    if (stack.popBoolean()) {
                        state = 3; // ��� ����� 3
                    } else {
                        state = 1; // ������������� ����� 3
                    }
                    break;
                }
                case 3: { // ��� ����� 3
                    state = 2; // ����3
                    break;
                }
                case 4: { // ������������� ����� 6
                    state = 2; // ����3
                    break;
                }
                case 5: { // ����6
                    if (stack.popBoolean()) {
                        state = 6; // ��� ����� 6
                    } else {
                        state = 4; // ������������� ����� 6
                    }
                    break;
                }
                case 6: { // ��� ����� 6
                    state = 5; // ����6
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 5; // ����6
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
                case 1: { // ������������� ����� 3
                    comment = Prim.this.getComment("updateKeys.InitLoop3"); 
                    break;
                }
                case 3: { // ��� ����� 3
                    comment = Prim.this.getComment("updateKeys.StepLoop3"); 
                    break;
                }
                case 4: { // ������������� ����� 6
                    comment = Prim.this.getComment("updateKeys.InitLoop6"); 
                    break;
                }
                case 6: { // ��� ����� 6
                    comment = Prim.this.getComment("updateKeys.StepLoop6"); 
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
            }
        }
    }

    /**
      * �������� �����.
      */
    private final class CheckGraph extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 7;

        /**
          * �����������.
          */
        public CheckGraph() {
            super( 
                "CheckGraph", 
                0, // ����� ���������� ��������� 
                7, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ����� 4", 
                    "���� 4", 
                    "��� ����� 4", 
                    "������������� ����� 5", 
                    "���� 5", 
                    "��� ����� 5", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ����� 4 
                    -1, // ���� 4 
                    -1, // ��� ����� 4 
                    -1, // ������������� ����� 5 
                    -1, // ���� 5 
                    -1, // ��� ����� 5 
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
                    state = 1; // ������������� ����� 4
                    break;
                }
                case 1: { // ������������� ����� 4
                    stack.pushBoolean(false); 
                    state = 2; // ���� 4
                    break;
                }
                case 2: { // ���� 4
                    if ((d.nstack > 0)) {
                        state = 3; // ��� ����� 4
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ��� ����� 4
                    state = 4; // ������������� ����� 5
                    break;
                }
                case 4: { // ������������� ����� 5
                    stack.pushBoolean(false); 
                    state = 5; // ���� 5
                    break;
                }
                case 5: { // ���� 5
                    if ((d.j <= d.countVertex)) {
                        state = 6; // ��� ����� 5
                    } else {
                        stack.pushBoolean(true); 
                        state = 2; // ���� 4
                    }
                    break;
                }
                case 6: { // ��� ����� 5
                    stack.pushBoolean(true); 
                    state = 5; // ���� 5
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� ����� 4
                    startSection();
                    storeArray(d.w, 1);
                    				d.w[1] = true;
                    storeArray(d.st, 1);
                    				d.st[1] = 1;
                    storeField(d, "nstack");
                    				d.nstack = 1;
                    				d.m = 1;
                    break;
                }
                case 2: { // ���� 4
                    break;
                }
                case 3: { // ��� ����� 4
                    startSection();
                    storeField(d, "i");
                    					d.i = d.st[d.nstack];
                    					d.nstack = d.nstack - 1;
                    break;
                }
                case 4: { // ������������� ����� 5
                    startSection();
                    storeField(d, "j");
                    					d.j = 1;
                    break;
                }
                case 5: { // ���� 5
                    break;
                }
                case 6: { // ��� ����� 5
                    startSection();
                    						if (!(d.w[d.j]) & (d.a[d.i][d.j] > 0)) {
                    storeArray(d.w, d.j);
                    							d.w[d.j] = true;
                    storeField(d, "nstack");
                    							d.nstack = d.nstack + 1;
                    storeArray(d.st, d.nstack);
                    							d.st[d.nstack] = d.j;
                    storeField(d, "m");
                    							d.m = d.m + 1;
                    						}
                    storeField(d, "j");
                    						d.j = d.j + 1;	
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
                case 1: { // ������������� ����� 4
                    restoreSection();
                    break;
                }
                case 2: { // ���� 4
                    break;
                }
                case 3: { // ��� ����� 4
                    restoreSection();
                    break;
                }
                case 4: { // ������������� ����� 5
                    restoreSection();
                    break;
                }
                case 5: { // ���� 5
                    break;
                }
                case 6: { // ��� ����� 5
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� ����� 4
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���� 4
                    if (stack.popBoolean()) {
                        state = 5; // ���� 5
                    } else {
                        state = 1; // ������������� ����� 4
                    }
                    break;
                }
                case 3: { // ��� ����� 4
                    state = 2; // ���� 4
                    break;
                }
                case 4: { // ������������� ����� 5
                    state = 3; // ��� ����� 4
                    break;
                }
                case 5: { // ���� 5
                    if (stack.popBoolean()) {
                        state = 6; // ��� ����� 5
                    } else {
                        state = 4; // ������������� ����� 5
                    }
                    break;
                }
                case 6: { // ��� ����� 5
                    state = 5; // ���� 5
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ���� 4
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
                case 1: { // ������������� ����� 4
                    comment = Prim.this.getComment("CheckGraph.InitLoop4"); 
                    break;
                }
                case 3: { // ��� ����� 4
                    comment = Prim.this.getComment("CheckGraph.StepLoop4"); 
                    break;
                }
                case 4: { // ������������� ����� 5
                    comment = Prim.this.getComment("CheckGraph.InitLoop5"); 
                    break;
                }
                case 6: { // ��� ����� 5
                    comment = Prim.this.getComment("CheckGraph.StepLoop5"); 
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
            }
        }
    }
}
