package ru.ifmo.vizi.ApproxTour;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class ApproxTour extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public ApproxTour(Locale locale) {
        super("ru.ifmo.vizi.ApproxTour.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ��������� �������.
          */
        public ApproxTourVisualizer visualizer = null;

        /**
          * ���������� ���������.
          */
        public int count = 0;

        /**
          * ������� ���������.
          */
        public int[][] a = new int[22][22];

        /**
          * ���������� �����.
          */
        public int i = 0;

        /**
          * ���������� �����.
          */
        public int j = 0;

        /**
          * ������ ������ ������.
          */
        public int[] key = new int[22];

        /**
          * ������ ����� ������ ��� �������� �����.
          */
        public boolean[] w = new boolean[22];

        /**
          * ���������� ������ � ������ �����.
          */
        public int m = 0;

        /**
          * ����������� �������.
          */
        public int min = 0;

        /**
          * ������ ��������� ������.
          */
        public int[] p = new int[22];

        /**
          * ����.
          */
        public int[][] ch = new int[22][22];

        /**
          * ������ ����� ������.
          */
        public boolean[] q = new boolean[22];

        /**
          * �������.
          */
        public int k = 0;

        /**
          * ����� ������ � �������.
          */
        public boolean dfsEnd = false;

        /**
          * ����.
          */
        public int[] c = new int[22];

        /**
          * ����� ���������.
          */
        public boolean endAlg = false;

        public String toString() {
            StringBuffer s=new StringBuffer();
            return s.toString();
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
        private final int END_STATE = 4;

        /**
          * �����������.
          */
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                4, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������� ����� (�������)", 
                    "������� ������ ������ (�������)", 
                    "end", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    CALL_AUTO_LEVEL, // �������� ����� (�������) 
                    CALL_AUTO_LEVEL, // ������� ������ ������ (�������) 
                    -1, // end 
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
                        state = 2; // ������� ������ ������ (�������)
                    }
                    break;
                }
                case 2: { // ������� ������ ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 3; // end
                    }
                    break;
                }
                case 3: { // end
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������� ����� (�������)
                    if (child == null) {
                        child = new Prim(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // ������� ������ ������ (�������)
                    if (child == null) {
                        child = new DFS(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 3: { // end
                    startSection();
                    storeField(d, "endAlg");
                    d.endAlg = true;
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
                        child = new Prim(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // ������� ������ ������ (�������)
                    if (child == null) {
                        child = new DFS(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 3: { // end
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
                case 2: { // ������� ������ ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // �������� ����� (�������)
                    }
                    break;
                }
                case 3: { // end
                    state = 2; // ������� ������ ������ (�������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 3; // end
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
                    comment = ApproxTour.this.getComment("Main.START_STATE"); 
                    args = new Object[]{new Integer(d.count)}; 
                    break;
                }
                case 1: { // �������� ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 2: { // ������� ������ ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = ApproxTour.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.updateView(true, -1, false);
                    break;
                }
                case 1: { // �������� ����� (�������)
                    child.drawState(); 
                    break;
                }
                case 2: { // ������� ������ ������ (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.updateView(false, -1, true);
                    break;
                }
            }
        }
    }

    /**
      * �������� �����.
      */
    private final class Prim extends BaseAutomata implements Automata {
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
        public Prim() {
            super( 
                "Prim", 
                0, // ����� ���������� ��������� 
                9, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ����� 1", 
                    "���� 1", 
                    "��� ����� 1", 
                    "������������� ������ ������", 
                    "������� ����", 
                    "������� ���������� �� ������� ����� ������� � ����������� ������ (�������)", 
                    "������� ���������� ������ ������ (�������)", 
                    "���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ����� 1 
                    -1, // ���� 1 
                    -1, // ��� ����� 1 
                    -1, // ������������� ������ ������ 
                    -1, // ������� ���� 
                    CALL_AUTO_LEVEL, // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������) 
                    CALL_AUTO_LEVEL, // ������� ���������� ������ ������ (�������) 
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
                    if ((d.i <= d.count)) {
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
                        state = 7; // ������� ���������� ������ ������ (�������)
                    }
                    break;
                }
                case 7: { // ������� ���������� ������ ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // ���������
                    }
                    break;
                }
                case 8: { // ���������
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
                    d.m = d.count;
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
                case 7: { // ������� ���������� ������ ������ (�������)
                    if (child == null) {
                        child = new updateKeys(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // ���������
                    startSection();
                    storeField(d, "m");
                    d.m = d.m - 1;
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
                case 7: { // ������� ���������� ������ ������ (�������)
                    if (child == null) {
                        child = new updateKeys(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // ���������
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
                        state = 8; // ���������
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
                case 7: { // ������� ���������� ������ ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    }
                    break;
                }
                case 8: { // ���������
                    state = 7; // ������� ���������� ������ ������ (�������)
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
                case 6: { // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // ������� ���������� ������ ������ (�������)
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
                case 6: { // ������� ���������� �� ������� ����� ������� � ����������� ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 7: { // ������� ���������� ������ ������ (�������)
                    child.drawState(); 
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
                    if ((d.i <= d.count)) {
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
        private final int END_STATE = 4;

        /**
          * �����������.
          */
        public updateKeys() {
            super( 
                "updateKeys", 
                0, // ����� ���������� ��������� 
                4, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ����� 3", 
                    "����3", 
                    "��� ����� 3", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ����� 3 
                    -1, // ����3 
                    -1, // ��� ����� 3 
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
                    if ((d.i <= d.count)) {
                        state = 3; // ��� ����� 3
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ��� ����� 3
                    stack.pushBoolean(true); 
                    state = 2; // ����3
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
                    }
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
                case END_STATE: { // ��������� ���������
                    state = 2; // ����3
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
      * ������� ������ ������.
      */
    private final class DFS extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 14;

        /**
          * �����������.
          */
        public DFS() {
            super( 
                "DFS", 
                0, // ����� ���������� ��������� 
                14, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "init", 
                    "initLoop", 
                    "loopInitStep", 
                    "������������� ��������� ����������", 
                    "loop", 
                    "�������", 
                    "������� (���������)", 
                    "�������", 
                    "������� (���������)", 
                    "", 
                    "up", 
                    "end", 
                    "down", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // init 
                    -1, // initLoop 
                    -1, // loopInitStep 
                    1, // ������������� ��������� ���������� 
                    -1, // loop 
                    -1, // ������� 
                    -1, // ������� (���������) 
                    -1, // ������� 
                    -1, // ������� (���������) 
                    1, //  
                    -1, // up 
                    1, // end 
                    1, // down 
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
                    state = 1; // init
                    break;
                }
                case 1: { // init
                    stack.pushBoolean(false); 
                    state = 2; // initLoop
                    break;
                }
                case 2: { // initLoop
                    if (d.i <= d.count) {
                        state = 3; // loopInitStep
                    } else {
                        state = 4; // ������������� ��������� ����������
                    }
                    break;
                }
                case 3: { // loopInitStep
                    stack.pushBoolean(true); 
                    state = 2; // initLoop
                    break;
                }
                case 4: { // ������������� ��������� ����������
                    stack.pushBoolean(false); 
                    state = 5; // loop
                    break;
                }
                case 5: { // loop
                    if (!(d.dfsEnd)) {
                        state = 6; // �������
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 6: { // �������
                    if (d.ch[d.k][0] == 0) {
                        state = 8; // �������
                    } else {
                        state = 13; // down
                    }
                    break;
                }
                case 7: { // ������� (���������)
                    stack.pushBoolean(true); 
                    state = 5; // loop
                    break;
                }
                case 8: { // �������
                    if (d.p[d.k] != 0) {
                        state = 10; 
                    } else {
                        state = 12; // end
                    }
                    break;
                }
                case 9: { // ������� (���������)
                    stack.pushBoolean(true); 
                    state = 7; // ������� (���������)
                    break;
                }
                case 10: { 
                    state = 11; // up
                    break;
                }
                case 11: { // up
                    stack.pushBoolean(true); 
                    state = 9; // ������� (���������)
                    break;
                }
                case 12: { // end
                    stack.pushBoolean(false); 
                    state = 9; // ������� (���������)
                    break;
                }
                case 13: { // down
                    stack.pushBoolean(false); 
                    state = 7; // ������� (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // init
                    startSection();
                    storeField(d, "k");
                    d.k = 1;
                    storeField(d, "i");
                    d.i = 1;
                    break;
                }
                case 2: { // initLoop
                    break;
                }
                case 3: { // loopInitStep
                    startSection();
                    storeArray(d.q, d.i);
                    d.q[d.i] = false;
                    storeArray(d.c, d.i);
                    d.c[d.i] = 0;
                    
                    storeArray(d.ch[ d.p[d.i] ], 0);
                    d.ch[ d.p[d.i] ][0] = d.ch[ d.p[d.i] ][0] + 1;
                    storeArray(d.ch[ d.p[d.i] ] ,  d.ch[d.p[d.i]][0] );
                    d.ch[ d.p[d.i] ] [ d.ch[d.p[d.i]][0] ] = d.i;
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 4: { // ������������� ��������� ����������
                    startSection();
                    storeArray(d.q, d.k);
                    d.q[d.k] = true;
                    storeArray(d.c, 0);
                    d.c[0] = d.c[0] + 1;
                    storeArray(d.c, d.c[0]);
                    d.c[d.c[0]] = d.k;
                    break;
                }
                case 5: { // loop
                    break;
                }
                case 6: { // �������
                    break;
                }
                case 7: { // ������� (���������)
                    break;
                }
                case 8: { // �������
                    break;
                }
                case 9: { // ������� (���������)
                    break;
                }
                case 10: { 
                    startSection();
                    break;
                }
                case 11: { // up
                    startSection();
                    storeField(d, "k");
                    d.k = d.p[d.k];
                    break;
                }
                case 12: { // end
                    startSection();
                    storeField(d, "dfsEnd");
                    d.dfsEnd = true;
                    storeArray(d.c, 0);
                    d.c[0] = d.c[0] + 1;
                    storeArray(d.c, d.c[0]);
                    d.c[d.c[0]] = 1;
                    break;
                }
                case 13: { // down
                    startSection();
                    storeArray(d.ch[d.k], 0);
                    d.ch[d.k][0] = d.ch[d.k][0] - 1;
                    storeField(d, "k");
                    d.k = d.ch[d.k][ d.ch[d.k][0] + 1 ];
                    storeArray(d.q, d.k);
                    d.q[d.k] = true;
                    storeArray(d.c, 0);
                    d.c[0] = d.c[0] + 1;
                    storeArray(d.c, d.c[0]);
                    d.c[d.c[0]] = d.k;
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
                case 1: { // init
                    restoreSection();
                    break;
                }
                case 2: { // initLoop
                    break;
                }
                case 3: { // loopInitStep
                    restoreSection();
                    break;
                }
                case 4: { // ������������� ��������� ����������
                    restoreSection();
                    break;
                }
                case 5: { // loop
                    break;
                }
                case 6: { // �������
                    break;
                }
                case 7: { // ������� (���������)
                    break;
                }
                case 8: { // �������
                    break;
                }
                case 9: { // ������� (���������)
                    break;
                }
                case 10: { 
                    restoreSection();
                    break;
                }
                case 11: { // up
                    restoreSection();
                    break;
                }
                case 12: { // end
                    restoreSection();
                    break;
                }
                case 13: { // down
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // init
                    state = START_STATE; 
                    break;
                }
                case 2: { // initLoop
                    if (stack.popBoolean()) {
                        state = 3; // loopInitStep
                    } else {
                        state = 1; // init
                    }
                    break;
                }
                case 3: { // loopInitStep
                    state = 2; // initLoop
                    break;
                }
                case 4: { // ������������� ��������� ����������
                    state = 2; // initLoop
                    break;
                }
                case 5: { // loop
                    if (stack.popBoolean()) {
                        state = 7; // ������� (���������)
                    } else {
                        state = 4; // ������������� ��������� ����������
                    }
                    break;
                }
                case 6: { // �������
                    state = 5; // loop
                    break;
                }
                case 7: { // ������� (���������)
                    if (stack.popBoolean()) {
                        state = 9; // ������� (���������)
                    } else {
                        state = 13; // down
                    }
                    break;
                }
                case 8: { // �������
                    state = 6; // �������
                    break;
                }
                case 9: { // ������� (���������)
                    if (stack.popBoolean()) {
                        state = 11; // up
                    } else {
                        state = 12; // end
                    }
                    break;
                }
                case 10: { 
                    state = 8; // �������
                    break;
                }
                case 11: { // up
                    state = 10; 
                    break;
                }
                case 12: { // end
                    state = 8; // �������
                    break;
                }
                case 13: { // down
                    state = 6; // �������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 5; // loop
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
                case 4: { // ������������� ��������� ����������
                    comment = ApproxTour.this.getComment("DFS.dfsInit"); 
                    args = new Object[]{new Integer(d.k)}; 
                    break;
                }
                case 10: { 
                    comment = ApproxTour.this.getComment("DFS.stepUp"); 
                    args = new Object[]{new Integer(d.k), new Integer(d.p[d.k])}; 
                    break;
                }
                case 12: { // end
                    comment = ApproxTour.this.getComment("DFS.endDFS"); 
                    break;
                }
                case 13: { // down
                    comment = ApproxTour.this.getComment("DFS.stepDown"); 
                    args = new Object[]{new Integer(d.c[d.c[0]])}; 
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
                case 4: { // ������������� ��������� ����������
                    d.visualizer.updateView(false, d.k, false);
                    break;
                }
                case 10: { 
                    d.visualizer.updateView(false, d.p[d.k], true);
                    break;
                }
                case 12: { // end
                    d.visualizer.updateView(false, -1, true);
                    break;
                }
                case 13: { // down
                    d.visualizer.updateView(false, d.k, true);
                    break;
                }
            }
        }
    }
}
