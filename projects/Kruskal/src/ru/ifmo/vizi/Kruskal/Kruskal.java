package ru.ifmo.vizi.Kruskal;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class Kruskal extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public Kruskal(Locale locale) {
        super("ru.ifmo.vizi.Kruskal.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ������ ����� ������ �����.
          */
        public int[] l = new int[4];

        /**
          * ������ ������ ������ �����.
          */
        public int[] r = new int[4];

        /**
          * ������ ����� �����.
          */
        public int[] weights = new int[4];

        /**
          * ������� �����.
          */
        public int edgeMin = 0;

        /**
          * ����� ������.
          */
        public int[] color = new int[5];

        /**
          * ��������� �������������.
          */
        public KruskalVisualizer visualizer = null;

        /**
          * ���������� ����� (��������� Main).
          */
        public int Main_i;

        /**
          * ���������� ��������� ����� (��������� Main).
          */
        public int Main_m;

        /**
          * ���������� ����� (��������� Sort).
          */
        public int Sort_i;

        /**
          * ���������� ����� 2 (��������� Sort).
          */
        public int Sort_j;

        /**
          * ��������� ���������� (��������� Sort).
          */
        public int Sort_temp;

        /**
          * ��������� ���������� (��������� Join).
          */
        public int Join_temp;

        /**
          * ���������� ����� (��������� Join).
          */
        public int Join_i;

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
        private final int END_STATE = 14;

        /**
          * �����������.
          */
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                14, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "���������� ����� �� ����� (�������)", 
                    "������������� �����", 
                    "������������� ������� ���������������� ��������", 
                    "������������� �������", 
                    "������������� ���������� ��������� ����� �����", 
                    "���������� ����� � ������", 
                    "�������� �����", 
                    "�������� ����� (���������)", 
                    "������� ��������� ��������� (�������)", 
                    "���������� �����", 
                    "������� � ����������", 
                    "������� �� ������", 
                    "������� �� ������ (���������)", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    CALL_AUTO_LEVEL, // ���������� ����� �� ����� (�������) 
                    0, // ������������� ����� 
                    -1, // ������������� ������� ���������������� �������� 
                    -1, // ������������� ������� 
                    -1, // ������������� ���������� ��������� ����� ����� 
                    -1, // ���������� ����� � ������ 
                    0, // �������� ����� 
                    -1, // �������� ����� (���������) 
                    CALL_AUTO_LEVEL, // ������� ��������� ��������� (�������) 
                    0, // ���������� ����� 
                    -1, // ������� � ���������� 
                    0, // ������� �� ������ 
                    -1, // ������� �� ������ (���������) 
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
                    state = 1; // ���������� ����� �� ����� (�������)
                    break;
                }
                case 1: { // ���������� ����� �� ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // ������������� �����
                    }
                    break;
                }
                case 2: { // ������������� �����
                    stack.pushBoolean(false); 
                    state = 3; // ������������� ������� ���������������� ��������
                    break;
                }
                case 3: { // ������������� ������� ���������������� ��������
                    if (d.Main_i < d.color.length) {
                        state = 4; // ������������� �������
                    } else {
                        state = 5; // ������������� ���������� ��������� ����� �����
                    }
                    break;
                }
                case 4: { // ������������� �������
                    stack.pushBoolean(true); 
                    state = 3; // ������������� ������� ���������������� ��������
                    break;
                }
                case 5: { // ������������� ���������� ��������� ����� �����
                    stack.pushBoolean(false); 
                    state = 6; // ���������� ����� � ������
                    break;
                }
                case 6: { // ���������� ����� � ������
                    if (d.edgeMin < d.l.length) {
                        state = 7; // �������� �����
                    } else {
                        state = 12; // ������� �� ������
                    }
                    break;
                }
                case 7: { // �������� �����
                    if (d.color[d.l[d.edgeMin]] != d.color[d.r[d.edgeMin]]) {
                        state = 9; // ������� ��������� ��������� (�������)
                    } else {
                        stack.pushBoolean(false); 
                        state = 8; // �������� ����� (���������)
                    }
                    break;
                }
                case 8: { // �������� ����� (���������)
                    state = 11; // ������� � ����������
                    break;
                }
                case 9: { // ������� ��������� ��������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // ���������� �����
                    }
                    break;
                }
                case 10: { // ���������� �����
                    stack.pushBoolean(true); 
                    state = 8; // �������� ����� (���������)
                    break;
                }
                case 11: { // ������� � ����������
                    stack.pushBoolean(true); 
                    state = 6; // ���������� ����� � ������
                    break;
                }
                case 12: { // ������� �� ������
                    if (d.Main_m == d.color.length - 1) {
                        stack.pushBoolean(true); 
                        state = 13; // ������� �� ������ (���������)
                    } else {
                        stack.pushBoolean(false); 
                        state = 13; // ������� �� ������ (���������)
                    }
                    break;
                }
                case 13: { // ������� �� ������ (���������)
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ���������� ����� �� ����� (�������)
                    if (child == null) {
                        child = new Sort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // ������������� �����
                    startSection();
                    storeField(d, "Main_i");
                    d.Main_i = 0;
                    break;
                }
                case 3: { // ������������� ������� ���������������� ��������
                    break;
                }
                case 4: { // ������������� �������
                    startSection();
                    storeArray(d.color, d.Main_i);
                    d.color[d.Main_i] = d.Main_i;
                    storeField(d, "Main_i");
                    d.Main_i = d.Main_i + 1;
                    break;
                }
                case 5: { // ������������� ���������� ��������� ����� �����
                    startSection();
                    storeField(d, "Main_m");
                    d.Main_m = 0;
                    storeField(d, "edgeMin");
                    d.edgeMin = 0;
                    break;
                }
                case 6: { // ���������� ����� � ������
                    break;
                }
                case 7: { // �������� �����
                    break;
                }
                case 8: { // �������� ����� (���������)
                    break;
                }
                case 9: { // ������� ��������� ��������� (�������)
                    if (child == null) {
                        child = new Join(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // ���������� �����
                    startSection();
                    storeField(d, "Main_m");
                    d.Main_m = d.Main_m + 1;
                    break;
                }
                case 11: { // ������� � ����������
                    startSection();
                    storeField(d, "edgeMin");
                    d.edgeMin = d.edgeMin + 1;
                    break;
                }
                case 12: { // ������� �� ������
                    break;
                }
                case 13: { // ������� �� ������ (���������)
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
                case 1: { // ���������� ����� �� ����� (�������)
                    if (child == null) {
                        child = new Sort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // ������������� �����
                    restoreSection();
                    break;
                }
                case 3: { // ������������� ������� ���������������� ��������
                    break;
                }
                case 4: { // ������������� �������
                    restoreSection();
                    break;
                }
                case 5: { // ������������� ���������� ��������� ����� �����
                    restoreSection();
                    break;
                }
                case 6: { // ���������� ����� � ������
                    break;
                }
                case 7: { // �������� �����
                    break;
                }
                case 8: { // �������� ����� (���������)
                    break;
                }
                case 9: { // ������� ��������� ��������� (�������)
                    if (child == null) {
                        child = new Join(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // ���������� �����
                    restoreSection();
                    break;
                }
                case 11: { // ������� � ����������
                    restoreSection();
                    break;
                }
                case 12: { // ������� �� ������
                    break;
                }
                case 13: { // ������� �� ������ (���������)
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ���������� ����� �� ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // ������������� �����
                    state = 1; // ���������� ����� �� ����� (�������)
                    break;
                }
                case 3: { // ������������� ������� ���������������� ��������
                    if (stack.popBoolean()) {
                        state = 4; // ������������� �������
                    } else {
                        state = 2; // ������������� �����
                    }
                    break;
                }
                case 4: { // ������������� �������
                    state = 3; // ������������� ������� ���������������� ��������
                    break;
                }
                case 5: { // ������������� ���������� ��������� ����� �����
                    state = 3; // ������������� ������� ���������������� ��������
                    break;
                }
                case 6: { // ���������� ����� � ������
                    if (stack.popBoolean()) {
                        state = 11; // ������� � ����������
                    } else {
                        state = 5; // ������������� ���������� ��������� ����� �����
                    }
                    break;
                }
                case 7: { // �������� �����
                    state = 6; // ���������� ����� � ������
                    break;
                }
                case 8: { // �������� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 10; // ���������� �����
                    } else {
                        state = 7; // �������� �����
                    }
                    break;
                }
                case 9: { // ������� ��������� ��������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 7; // �������� �����
                    }
                    break;
                }
                case 10: { // ���������� �����
                    state = 9; // ������� ��������� ��������� (�������)
                    break;
                }
                case 11: { // ������� � ����������
                    state = 8; // �������� ����� (���������)
                    break;
                }
                case 12: { // ������� �� ������
                    state = 6; // ���������� ����� � ������
                    break;
                }
                case 13: { // ������� �� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 12; // ������� �� ������
                    } else {
                        state = 12; // ������� �� ������
                    }
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 13; // ������� �� ������ (���������)
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
                    comment = Kruskal.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // ���������� ����� �� ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 2: { // ������������� �����
                    comment = Kruskal.this.getComment("Main.LoopInit"); 
                    break;
                }
                case 7: { // �������� �����
                    if (d.color[d.l[d.edgeMin]] != d.color[d.r[d.edgeMin]]) {
                        comment = Kruskal.this.getComment("Main.cond.true"); 
                    } else {
                        comment = Kruskal.this.getComment("Main.cond.false"); 
                    }
                    break;
                }
                case 9: { // ������� ��������� ��������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // ���������� �����
                    comment = Kruskal.this.getComment("Main.AddEdge"); 
                    break;
                }
                case 12: { // ������� �� ������
                    if (d.Main_m == d.color.length - 1) {
                        comment = Kruskal.this.getComment("Main.isFound.true"); 
                    } else {
                        comment = Kruskal.this.getComment("Main.isFound.false"); 
                    }
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = Kruskal.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.showWeights(true);
                    d.visualizer.updateView(-1, 0);
                    break;
                }
                case 1: { // ���������� ����� �� ����� (�������)
                    child.drawState(); 
                    break;
                }
                case 2: { // ������������� �����
                    d.visualizer.showWeights(false);    
                    d.visualizer.updateView(-1, 0);
                    break;
                }
                case 7: { // �������� �����
                    d.visualizer.updateView(d.edgeMin, 3);
                    break;
                }
                case 9: { // ������� ��������� ��������� (�������)
                    child.drawState(); 
                    break;
                }
                case 10: { // ���������� �����
                    d.visualizer.updateView(d.edgeMin, 1);
                    break;
                }
                case 12: { // ������� �� ������
                    d.visualizer.updateView(1000, 0);
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.updateView(1000, 0);
                    break;
                }
            }
        }
    }

    /**
      * ���������� ����� �� �����.
      */
    private final class Sort extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 11;

        /**
          * �����������.
          */
        public Sort() {
            super( 
                "Sort", 
                0, // ����� ���������� ��������� 
                11, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� �����", 
                    "���� �� i", 
                    "init", 
                    "���� �� j", 
                    "������� ������������", 
                    "������� ������������ (���������)", 
                    "������������", 
                    "��������� j", 
                    "��������� i", 
                    "����� ���������������� ������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ����� 
                    -1, // ���� �� i 
                    -1, // init 
                    -1, // ���� �� j 
                    -1, // ������� ������������ 
                    -1, // ������� ������������ (���������) 
                    -1, // ������������ 
                    -1, // ��������� j 
                    -1, // ��������� i 
                    0, // ����� ���������������� ������ 
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
                    state = 1; // ������������� �����
                    break;
                }
                case 1: { // ������������� �����
                    stack.pushBoolean(false); 
                    state = 2; // ���� �� i
                    break;
                }
                case 2: { // ���� �� i
                    if (d.Sort_i < d.l.length) {
                        state = 3; // init
                    } else {
                        state = 10; // ����� ���������������� ������
                    }
                    break;
                }
                case 3: { // init
                    stack.pushBoolean(false); 
                    state = 4; // ���� �� j
                    break;
                }
                case 4: { // ���� �� j
                    if (d.Sort_j < d.l.length) {
                        state = 5; // ������� ������������
                    } else {
                        state = 9; // ��������� i
                    }
                    break;
                }
                case 5: { // ������� ������������
                    if (d.weights[d.Sort_i] > d.weights[d.Sort_j]) {
                        state = 7; // ������������
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // ������� ������������ (���������)
                    }
                    break;
                }
                case 6: { // ������� ������������ (���������)
                    state = 8; // ��������� j
                    break;
                }
                case 7: { // ������������
                    stack.pushBoolean(true); 
                    state = 6; // ������� ������������ (���������)
                    break;
                }
                case 8: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 4; // ���� �� j
                    break;
                }
                case 9: { // ��������� i
                    stack.pushBoolean(true); 
                    state = 2; // ���� �� i
                    break;
                }
                case 10: { // ����� ���������������� ������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� �����
                    startSection();
                    storeField(d, "Sort_i");
                    d.Sort_i = 0;
                    break;
                }
                case 2: { // ���� �� i
                    break;
                }
                case 3: { // init
                    startSection();
                    storeField(d, "Sort_j");
                    d.Sort_j = d.Sort_i + 1;
                    break;
                }
                case 4: { // ���� �� j
                    break;
                }
                case 5: { // ������� ������������
                    break;
                }
                case 6: { // ������� ������������ (���������)
                    break;
                }
                case 7: { // ������������
                    startSection();
                    storeField(d, "Sort_temp");
                    d.Sort_temp = d.weights[d.Sort_i];
                    storeArray(d.weights, d.Sort_i);
                    d.weights[d.Sort_i] = d.weights[d.Sort_j];
                    storeArray(d.weights, d.Sort_j);
                    d.weights[d.Sort_j] = d.Sort_temp;
                    storeField(d, "Sort_temp");
                    d.Sort_temp = d.l[d.Sort_i];
                    storeArray(d.l, d.Sort_i);
                    d.l[d.Sort_i] = d.l[d.Sort_j];
                    storeArray(d.l, d.Sort_j);
                    d.l[d.Sort_j] = d.Sort_temp;
                    storeField(d, "Sort_temp");
                    d.Sort_temp = d.r[d.Sort_i];
                    storeArray(d.r, d.Sort_i);
                    d.r[d.Sort_i] = d.r[d.Sort_j];
                    storeArray(d.r, d.Sort_j);
                    d.r[d.Sort_j] = d.Sort_temp;
                    break;
                }
                case 8: { // ��������� j
                    startSection();
                    storeField(d, "Sort_j");
                    d.Sort_j = d.Sort_j + 1;
                    break;
                }
                case 9: { // ��������� i
                    startSection();
                    storeField(d, "Sort_i");
                    d.Sort_i = d.Sort_i + 1;
                    break;
                }
                case 10: { // ����� ���������������� ������
                    startSection();
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
                case 1: { // ������������� �����
                    restoreSection();
                    break;
                }
                case 2: { // ���� �� i
                    break;
                }
                case 3: { // init
                    restoreSection();
                    break;
                }
                case 4: { // ���� �� j
                    break;
                }
                case 5: { // ������� ������������
                    break;
                }
                case 6: { // ������� ������������ (���������)
                    break;
                }
                case 7: { // ������������
                    restoreSection();
                    break;
                }
                case 8: { // ��������� j
                    restoreSection();
                    break;
                }
                case 9: { // ��������� i
                    restoreSection();
                    break;
                }
                case 10: { // ����� ���������������� ������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���� �� i
                    if (stack.popBoolean()) {
                        state = 9; // ��������� i
                    } else {
                        state = 1; // ������������� �����
                    }
                    break;
                }
                case 3: { // init
                    state = 2; // ���� �� i
                    break;
                }
                case 4: { // ���� �� j
                    if (stack.popBoolean()) {
                        state = 8; // ��������� j
                    } else {
                        state = 3; // init
                    }
                    break;
                }
                case 5: { // ������� ������������
                    state = 4; // ���� �� j
                    break;
                }
                case 6: { // ������� ������������ (���������)
                    if (stack.popBoolean()) {
                        state = 7; // ������������
                    } else {
                        state = 5; // ������� ������������
                    }
                    break;
                }
                case 7: { // ������������
                    state = 5; // ������� ������������
                    break;
                }
                case 8: { // ��������� j
                    state = 6; // ������� ������������ (���������)
                    break;
                }
                case 9: { // ��������� i
                    state = 4; // ���� �� j
                    break;
                }
                case 10: { // ����� ���������������� ������
                    state = 2; // ���� �� i
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 10; // ����� ���������������� ������
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
                case 10: { // ����� ���������������� ������
                    comment = Kruskal.this.getComment("Sort."); 
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
                case 10: { // ����� ���������������� ������
                    d.visualizer.showWeights(true);
                    d.visualizer.updateView(-1, 0);
                    break;
                }
            }
        }
    }

    /**
      * ������� ��������� ���������.
      */
    private final class Join extends BaseAutomata implements Automata {
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
        public Join() {
            super( 
                "Join", 
                0, // ����� ���������� ��������� 
                7, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������������", 
                    "������ �� ��������", 
                    "������� ����������", 
                    "������� ���������� (���������)", 
                    "����������", 
                    "���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� 
                    -1, // ������ �� �������� 
                    -1, // ������� ���������� 
                    -1, // ������� ���������� (���������) 
                    -1, // ���������� 
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
                    stack.pushBoolean(false); 
                    state = 2; // ������ �� ��������
                    break;
                }
                case 2: { // ������ �� ��������
                    if (d.Join_i < d.color.length) {
                        state = 3; // ������� ����������
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ������� ����������
                    if (d.color[d.Join_i] == d.Join_temp) {
                        state = 5; // ����������
                    } else {
                        stack.pushBoolean(false); 
                        state = 4; // ������� ���������� (���������)
                    }
                    break;
                }
                case 4: { // ������� ���������� (���������)
                    state = 6; // ���������
                    break;
                }
                case 5: { // ����������
                    stack.pushBoolean(true); 
                    state = 4; // ������� ���������� (���������)
                    break;
                }
                case 6: { // ���������
                    stack.pushBoolean(true); 
                    state = 2; // ������ �� ��������
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������
                    startSection();
                    storeField(d, "Join_i");
                    d.Join_i = 0;
                    storeField(d, "Join_temp");
                    d.Join_temp = d.color[d.l[d.edgeMin]];
                    break;
                }
                case 2: { // ������ �� ��������
                    break;
                }
                case 3: { // ������� ����������
                    break;
                }
                case 4: { // ������� ���������� (���������)
                    break;
                }
                case 5: { // ����������
                    startSection();
                    storeArray(d.color, d.Join_i);
                    d.color[d.Join_i] = d.color[d.r[d.edgeMin]];
                    break;
                }
                case 6: { // ���������
                    startSection();
                    storeField(d, "Join_i");
                    d.Join_i = d.Join_i + 1;
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
                case 2: { // ������ �� ��������
                    break;
                }
                case 3: { // ������� ����������
                    break;
                }
                case 4: { // ������� ���������� (���������)
                    break;
                }
                case 5: { // ����������
                    restoreSection();
                    break;
                }
                case 6: { // ���������
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
                case 2: { // ������ �� ��������
                    if (stack.popBoolean()) {
                        state = 6; // ���������
                    } else {
                        state = 1; // �������������
                    }
                    break;
                }
                case 3: { // ������� ����������
                    state = 2; // ������ �� ��������
                    break;
                }
                case 4: { // ������� ���������� (���������)
                    if (stack.popBoolean()) {
                        state = 5; // ����������
                    } else {
                        state = 3; // ������� ����������
                    }
                    break;
                }
                case 5: { // ����������
                    state = 3; // ������� ����������
                    break;
                }
                case 6: { // ���������
                    state = 4; // ������� ���������� (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ������ �� ��������
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
}
