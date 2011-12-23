package ru.ifmo.vizi.quick_sort;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class QuickSort extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public QuickSort(Locale locale) {
        super("ru.ifmo.vizi.quick_sort.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ������ ��� ����������.
          */
        public int[] array = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * ������ ����� ������.
          */
        public int[] leftBorders = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * ������ ������ ������.
          */
        public int[] rightBorders = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * ������� ��������.
          */
        public int depth = 0;

        /**
          * ������.
          */
        public int bar = 0;

        /**
          * ��������� �������.
          */
        public QuickSortVisualizer visualizer = null;

        /**
          * ������� ����� ������� (��������� QSort).
          */
        public int QSort_left;

        /**
          * ������� ������ ������� (��������� QSort).
          */
        public int QSort_right;

        /**
          * ������ (��������� QSort).
          */
        public int QSort_m;

        /**
          * ��������� ���������� (��������� QSort).
          */
        public int QSort_i;

        /**
          * ��������� ���������� (��������� QSort).
          */
        public int QSort_j;

        /**
          * ��������� ���������� (��������� QSort).
          */
        public int QSort_tmp;

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
        private final int END_STATE = 3;

        /**
          * �����������.
          */
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                3, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �������� ���������� �������", 
                    "���������� (�������)", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ �������� ���������� ������� 
                    CALL_AUTO_LEVEL, // ���������� (�������) 
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
                    state = 1; // ������ �������� ���������� �������
                    break;
                }
                case 1: { // ������ �������� ���������� �������
                    state = 2; // ���������� (�������)
                    break;
                }
                case 2: { // ���������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = END_STATE; 
                    }
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �������� ���������� �������
                    startSection();
                    storeField(d, "depth");
                    					d.depth = 0;
                    storeArray(d.leftBorders, d.depth);
                    					d.leftBorders[d.depth] = 0;
                    storeArray(d.rightBorders, d.depth);
                    					d.rightBorders[d.depth] = d.array.length - 1;
                    storeField(d, "depth");
                    					d.depth = d.depth + 1;
                    break;
                }
                case 2: { // ���������� (�������)
                    if (child == null) {
                        child = new QSort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
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
                case 1: { // ������ �������� ���������� �������
                    restoreSection();
                    break;
                }
                case 2: { // ���������� (�������)
                    if (child == null) {
                        child = new QSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �������� ���������� �������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // ������ �������� ���������� �������
                    }
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ���������� (�������)
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
                    comment = QuickSort.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 2: { // ���������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = QuickSort.this.getComment("Main.END_STATE"); 
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
                    					d.visualizer.updateArray(0, d.array.length - 1);
                    					d.visualizer.updateBorders();
                    					d.visualizer.updateBar();
                    break;
                }
                case 1: { // ������ �������� ���������� �������
                    					d.visualizer.updateArray(0, d.array.length - 1);
                    					d.visualizer.updateBorders();
                    break;
                }
                case 2: { // ���������� (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    					d.visualizer.updateArray(0, d.array.length - 1);
                    					d.visualizer.updateBorders();
                    break;
                }
            }
        }
    }

    /**
      * ����������.
      */
    private final class QSort extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 36;

        /**
          * �����������.
          */
        public QSort() {
            super( 
                "QSort", 
                0, // ����� ���������� ��������� 
                36, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ��������� ����������", 
                    "����� �������", 
                    "��������� � ����������", 
                    "���������� ��������� ����������� �����", 
                    "����� �������� �������� ��� ������� �������", 
                    "����������� i", 
                    "������ ������ ������� ��� ������", 
                    "����� �������� �������� ��� ������� �������", 
                    "��������� j", 
                    "������ ������ ������� ��� ������", 
                    "������� ����� ���� ���������", 
                    "������� ����� ���� ��������� (���������)", 
                    "��������� �� ������ ���������", 
                    "����� ����������", 
                    "����� ����������", 
                    "�������� �� ���� ������ �������", 
                    "������� ��� ������������ ������ ��� ����� �����", 
                    "������� ��� ������������ ������ ��� ����� ����� (���������)", 
                    "����������� ����� ����� �����", 
                    "���������� (�������)", 
                    "�������������� ���������� ����� ��������", 
                    "�������� ���� ������� � ����� ����� ��� ��� �����", 
                    "�������� ���� ������� � ����� ����� ��� ��� ����� (���������)", 
                    "����� ����� ������� �� ������ ��������", 
                    "����� ����� �����", 
                    "������� ��� ������������ ������ ��� ������ �����", 
                    "������� ��� ������������ ������ ��� ������ ����� (���������)", 
                    "����������� ����� ��� ������ �����", 
                    "���������� (�������)", 
                    "�������������� ���������� ����� ��������", 
                    "�������� ���� ������� � ������ ����� ��� ��� �����", 
                    "�������� ���� ������� � ������ ����� ��� ��� ����� (���������)", 
                    "������ ����� ������� �� ������ ��������", 
                    "������ ����� �����", 
                    "������ �� ����� ��������� �������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    1, // ������������� ��������� ���������� 
                    1, // ����� ������� 
                    1, // ��������� � ���������� 
                    -1, // ���������� ��������� ����������� ����� 
                    -1, // ����� �������� �������� ��� ������� ������� 
                    0, // ����������� i 
                    0, // ������ ������ ������� ��� ������ 
                    -1, // ����� �������� �������� ��� ������� ������� 
                    0, // ��������� j 
                    0, // ������ ������ ������� ��� ������ 
                    -1, // ������� ����� ���� ��������� 
                    -1, // ������� ����� ���� ��������� (���������) 
                    0, // ��������� �� ������ ��������� 
                    -1, // ����� ���������� 
                    0, // ����� ���������� 
                    0, // �������� �� ���� ������ ������� 
                    -1, // ������� ��� ������������ ������ ��� ����� ����� 
                    -1, // ������� ��� ������������ ������ ��� ����� ����� (���������) 
                    1, // ����������� ����� ����� ����� 
                    CALL_AUTO_LEVEL, // ���������� (�������) 
                    -1, // �������������� ���������� ����� �������� 
                    -1, // �������� ���� ������� � ����� ����� ��� ��� ����� 
                    -1, // �������� ���� ������� � ����� ����� ��� ��� ����� (���������) 
                    1, // ����� ����� ������� �� ������ �������� 
                    1, // ����� ����� ����� 
                    -1, // ������� ��� ������������ ������ ��� ������ ����� 
                    -1, // ������� ��� ������������ ������ ��� ������ ����� (���������) 
                    1, // ����������� ����� ��� ������ ����� 
                    CALL_AUTO_LEVEL, // ���������� (�������) 
                    -1, // �������������� ���������� ����� �������� 
                    -1, // �������� ���� ������� � ������ ����� ��� ��� ����� 
                    -1, // �������� ���� ������� � ������ ����� ��� ��� ����� (���������) 
                    1, // ������ ����� ������� �� ������ �������� 
                    1, // ������ ����� ����� 
                    0, // ������ �� ����� ��������� ������� 
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
                    state = 1; // ������������� ��������� ����������
                    break;
                }
                case 1: { // ������������� ��������� ����������
                    state = 2; // ����� �������
                    break;
                }
                case 2: { // ����� �������
                    state = 3; // ��������� � ����������
                    break;
                }
                case 3: { // ��������� � ����������
                    stack.pushBoolean(false); 
                    state = 4; // ���������� ��������� ����������� �����
                    break;
                }
                case 4: { // ���������� ��������� ����������� �����
                    if (d.QSort_i <= d.QSort_j) {
                        stack.pushBoolean(false); 
                        state = 5; // ����� �������� �������� ��� ������� �������
                    } else {
                        state = 17; // ������� ��� ������������ ������ ��� ����� �����
                    }
                    break;
                }
                case 5: { // ����� �������� �������� ��� ������� �������
                    if (d.array[d.QSort_i] < d.QSort_m) {
                        state = 6; // ����������� i
                    } else {
                        state = 7; // ������ ������ ������� ��� ������
                    }
                    break;
                }
                case 6: { // ����������� i
                    stack.pushBoolean(true); 
                    state = 5; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 7: { // ������ ������ ������� ��� ������
                    stack.pushBoolean(false); 
                    state = 8; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 8: { // ����� �������� �������� ��� ������� �������
                    if (d.array[d.QSort_j] > d.QSort_m) {
                        state = 9; // ��������� j
                    } else {
                        state = 10; // ������ ������ ������� ��� ������
                    }
                    break;
                }
                case 9: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 8; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 10: { // ������ ������ ������� ��� ������
                    state = 11; // ������� ����� ���� ���������
                    break;
                }
                case 11: { // ������� ����� ���� ���������
                    if (d.QSort_i <= d.QSort_j) {
                        state = 13; // ��������� �� ������ ���������
                    } else {
                        state = 16; // �������� �� ���� ������ �������
                    }
                    break;
                }
                case 12: { // ������� ����� ���� ��������� (���������)
                    stack.pushBoolean(true); 
                    state = 4; // ���������� ��������� ����������� �����
                    break;
                }
                case 13: { // ��������� �� ������ ���������
                    state = 14; // ����� ����������
                    break;
                }
                case 14: { // ����� ����������
                    state = 15; // ����� ����������
                    break;
                }
                case 15: { // ����� ����������
                    stack.pushBoolean(true); 
                    state = 12; // ������� ����� ���� ��������� (���������)
                    break;
                }
                case 16: { // �������� �� ���� ������ �������
                    stack.pushBoolean(false); 
                    state = 12; // ������� ����� ���� ��������� (���������)
                    break;
                }
                case 17: { // ������� ��� ������������ ������ ��� ����� �����
                    if (d.QSort_left < d.QSort_j) {
                        state = 19; // ����������� ����� ����� �����
                    } else {
                        state = 22; // �������� ���� ������� � ����� ����� ��� ��� �����
                    }
                    break;
                }
                case 18: { // ������� ��� ������������ ������ ��� ����� ����� (���������)
                    state = 26; // ������� ��� ������������ ������ ��� ������ �����
                    break;
                }
                case 19: { // ����������� ����� ����� �����
                    state = 20; // ���������� (�������)
                    break;
                }
                case 20: { // ���������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 21; // �������������� ���������� ����� ��������
                    }
                    break;
                }
                case 21: { // �������������� ���������� ����� ��������
                    stack.pushBoolean(true); 
                    state = 18; // ������� ��� ������������ ������ ��� ����� ����� (���������)
                    break;
                }
                case 22: { // �������� ���� ������� � ����� ����� ��� ��� �����
                    if (d.QSort_left == d.QSort_j) {
                        state = 24; // ����� ����� ������� �� ������ ��������
                    } else {
                        state = 25; // ����� ����� �����
                    }
                    break;
                }
                case 23: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    stack.pushBoolean(false); 
                    state = 18; // ������� ��� ������������ ������ ��� ����� ����� (���������)
                    break;
                }
                case 24: { // ����� ����� ������� �� ������ ��������
                    stack.pushBoolean(true); 
                    state = 23; // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 25: { // ����� ����� �����
                    stack.pushBoolean(false); 
                    state = 23; // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 26: { // ������� ��� ������������ ������ ��� ������ �����
                    if (d.QSort_i < d.QSort_right) {
                        state = 28; // ����������� ����� ��� ������ �����
                    } else {
                        state = 31; // �������� ���� ������� � ������ ����� ��� ��� �����
                    }
                    break;
                }
                case 27: { // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    state = 35; // ������ �� ����� ��������� �������
                    break;
                }
                case 28: { // ����������� ����� ��� ������ �����
                    state = 29; // ���������� (�������)
                    break;
                }
                case 29: { // ���������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 30; // �������������� ���������� ����� ��������
                    }
                    break;
                }
                case 30: { // �������������� ���������� ����� ��������
                    stack.pushBoolean(true); 
                    state = 27; // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    break;
                }
                case 31: { // �������� ���� ������� � ������ ����� ��� ��� �����
                    if (d.QSort_right == d.QSort_i) {
                        state = 33; // ������ ����� ������� �� ������ ��������
                    } else {
                        state = 34; // ������ ����� �����
                    }
                    break;
                }
                case 32: { // �������� ���� ������� � ������ ����� ��� ��� ����� (���������)
                    stack.pushBoolean(false); 
                    state = 27; // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    break;
                }
                case 33: { // ������ ����� ������� �� ������ ��������
                    stack.pushBoolean(true); 
                    state = 32; // �������� ���� ������� � ������ ����� ��� ��� ����� (���������)
                    break;
                }
                case 34: { // ������ ����� �����
                    stack.pushBoolean(false); 
                    state = 32; // �������� ���� ������� � ������ ����� ��� ��� ����� (���������)
                    break;
                }
                case 35: { // ������ �� ����� ��������� �������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� ��������� ����������
                    startSection();
                    storeField(d, "QSort_left");
                    d.QSort_left = d.leftBorders[d.depth - 1];
                    storeField(d, "QSort_i");
                    d.QSort_i = d.QSort_left;
                    storeField(d, "QSort_right");
                    d.QSort_right = d.rightBorders[d.depth - 1];
                    storeField(d, "QSort_j");
                    d.QSort_j = d.QSort_right;
                    break;
                }
                case 2: { // ����� �������
                    startSection();
                    storeField(d, "QSort_m");
                    d.QSort_m = d.array[(d.QSort_left + d.QSort_right) / 2];
                    break;
                }
                case 3: { // ��������� � ����������
                    startSection();
                    break;
                }
                case 4: { // ���������� ��������� ����������� �����
                    break;
                }
                case 5: { // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 6: { // ����������� i
                    startSection();
                    storeField(d, "QSort_i");
                    						d.QSort_i = d.QSort_i + 1;
                    break;
                }
                case 7: { // ������ ������ ������� ��� ������
                    startSection();
                    break;
                }
                case 8: { // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 9: { // ��������� j
                    startSection();
                    storeField(d, "QSort_j");
                    d.QSort_j = d.QSort_j - 1;
                    break;
                }
                case 10: { // ������ ������ ������� ��� ������
                    startSection();
                    break;
                }
                case 11: { // ������� ����� ���� ���������
                    break;
                }
                case 12: { // ������� ����� ���� ��������� (���������)
                    break;
                }
                case 13: { // ��������� �� ������ ���������
                    startSection();
                    break;
                }
                case 14: { // ����� ����������
                    startSection();
                    storeField(d, "QSort_tmp");
                    d.QSort_tmp = d.array[d.QSort_i];
                    storeArray(d.array, d.QSort_i);
                    d.array[d.QSort_i] = d.array[d.QSort_j];
                    storeArray(d.array, d.QSort_j);
                    d.array[d.QSort_j] = d.QSort_tmp;
                    break;
                }
                case 15: { // ����� ����������
                    startSection();
                    storeField(d, "QSort_i");
                    d.QSort_i = d.QSort_i + 1;
                    storeField(d, "QSort_j");
                    d.QSort_j = d.QSort_j - 1;
                    break;
                }
                case 16: { // �������� �� ���� ������ �������
                    startSection();
                    break;
                }
                case 17: { // ������� ��� ������������ ������ ��� ����� �����
                    break;
                }
                case 18: { // ������� ��� ������������ ������ ��� ����� ����� (���������)
                    break;
                }
                case 19: { // ����������� ����� ����� �����
                    startSection();
                    storeArray(d.leftBorders, d.depth);
                    d.leftBorders[d.depth] = d.QSort_left;
                    storeArray(d.rightBorders, d.depth);
                    d.rightBorders[d.depth] = d.QSort_j;
                    storeField(d, "depth");
                    d.depth = d.depth + 1;
                    break;
                }
                case 20: { // ���������� (�������)
                    if (child == null) {
                        child = new QSort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 21: { // �������������� ���������� ����� ��������
                    startSection();
                    storeField(d, "QSort_left");
                    d.QSort_left = d.leftBorders[d.depth - 1];
                    storeField(d, "QSort_right");
                    d.QSort_right = d.rightBorders[d.depth - 1];
                    break;
                }
                case 22: { // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 23: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 24: { // ����� ����� ������� �� ������ ��������
                    startSection();
                    break;
                }
                case 25: { // ����� ����� �����
                    startSection();
                    break;
                }
                case 26: { // ������� ��� ������������ ������ ��� ������ �����
                    break;
                }
                case 27: { // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    break;
                }
                case 28: { // ����������� ����� ��� ������ �����
                    startSection();
                    storeArray(d.leftBorders, d.depth);
                    d.leftBorders[d.depth] = d.QSort_i;
                    storeArray(d.rightBorders, d.depth);
                    d.rightBorders[d.depth] = d.QSort_right;
                    storeField(d, "depth");
                    d.depth = d.depth + 1;
                    break;
                }
                case 29: { // ���������� (�������)
                    if (child == null) {
                        child = new QSort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 30: { // �������������� ���������� ����� ��������
                    startSection();
                    storeField(d, "QSort_left");
                    d.QSort_left = d.leftBorders[d.depth - 1];
                    storeField(d, "QSort_right");
                    d.QSort_right = d.rightBorders[d.depth - 1];
                    break;
                }
                case 31: { // �������� ���� ������� � ������ ����� ��� ��� �����
                    break;
                }
                case 32: { // �������� ���� ������� � ������ ����� ��� ��� ����� (���������)
                    break;
                }
                case 33: { // ������ ����� ������� �� ������ ��������
                    startSection();
                    break;
                }
                case 34: { // ������ ����� �����
                    startSection();
                    break;
                }
                case 35: { // ������ �� ����� ��������� �������
                    startSection();
                    storeField(d, "depth");
                    				d.depth = d.depth - 1;
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
                case 1: { // ������������� ��������� ����������
                    restoreSection();
                    break;
                }
                case 2: { // ����� �������
                    restoreSection();
                    break;
                }
                case 3: { // ��������� � ����������
                    restoreSection();
                    break;
                }
                case 4: { // ���������� ��������� ����������� �����
                    break;
                }
                case 5: { // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 6: { // ����������� i
                    restoreSection();
                    break;
                }
                case 7: { // ������ ������ ������� ��� ������
                    restoreSection();
                    break;
                }
                case 8: { // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 9: { // ��������� j
                    restoreSection();
                    break;
                }
                case 10: { // ������ ������ ������� ��� ������
                    restoreSection();
                    break;
                }
                case 11: { // ������� ����� ���� ���������
                    break;
                }
                case 12: { // ������� ����� ���� ��������� (���������)
                    break;
                }
                case 13: { // ��������� �� ������ ���������
                    restoreSection();
                    break;
                }
                case 14: { // ����� ����������
                    restoreSection();
                    break;
                }
                case 15: { // ����� ����������
                    restoreSection();
                    break;
                }
                case 16: { // �������� �� ���� ������ �������
                    restoreSection();
                    break;
                }
                case 17: { // ������� ��� ������������ ������ ��� ����� �����
                    break;
                }
                case 18: { // ������� ��� ������������ ������ ��� ����� ����� (���������)
                    break;
                }
                case 19: { // ����������� ����� ����� �����
                    restoreSection();
                    break;
                }
                case 20: { // ���������� (�������)
                    if (child == null) {
                        child = new QSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 21: { // �������������� ���������� ����� ��������
                    restoreSection();
                    break;
                }
                case 22: { // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 23: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 24: { // ����� ����� ������� �� ������ ��������
                    restoreSection();
                    break;
                }
                case 25: { // ����� ����� �����
                    restoreSection();
                    break;
                }
                case 26: { // ������� ��� ������������ ������ ��� ������ �����
                    break;
                }
                case 27: { // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    break;
                }
                case 28: { // ����������� ����� ��� ������ �����
                    restoreSection();
                    break;
                }
                case 29: { // ���������� (�������)
                    if (child == null) {
                        child = new QSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 30: { // �������������� ���������� ����� ��������
                    restoreSection();
                    break;
                }
                case 31: { // �������� ���� ������� � ������ ����� ��� ��� �����
                    break;
                }
                case 32: { // �������� ���� ������� � ������ ����� ��� ��� ����� (���������)
                    break;
                }
                case 33: { // ������ ����� ������� �� ������ ��������
                    restoreSection();
                    break;
                }
                case 34: { // ������ ����� �����
                    restoreSection();
                    break;
                }
                case 35: { // ������ �� ����� ��������� �������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� ��������� ����������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����� �������
                    state = 1; // ������������� ��������� ����������
                    break;
                }
                case 3: { // ��������� � ����������
                    state = 2; // ����� �������
                    break;
                }
                case 4: { // ���������� ��������� ����������� �����
                    if (stack.popBoolean()) {
                        state = 12; // ������� ����� ���� ��������� (���������)
                    } else {
                        state = 3; // ��������� � ����������
                    }
                    break;
                }
                case 5: { // ����� �������� �������� ��� ������� �������
                    if (stack.popBoolean()) {
                        state = 6; // ����������� i
                    } else {
                        state = 4; // ���������� ��������� ����������� �����
                    }
                    break;
                }
                case 6: { // ����������� i
                    state = 5; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 7: { // ������ ������ ������� ��� ������
                    state = 5; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 8: { // ����� �������� �������� ��� ������� �������
                    if (stack.popBoolean()) {
                        state = 9; // ��������� j
                    } else {
                        state = 7; // ������ ������ ������� ��� ������
                    }
                    break;
                }
                case 9: { // ��������� j
                    state = 8; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 10: { // ������ ������ ������� ��� ������
                    state = 8; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 11: { // ������� ����� ���� ���������
                    state = 10; // ������ ������ ������� ��� ������
                    break;
                }
                case 12: { // ������� ����� ���� ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 15; // ����� ����������
                    } else {
                        state = 16; // �������� �� ���� ������ �������
                    }
                    break;
                }
                case 13: { // ��������� �� ������ ���������
                    state = 11; // ������� ����� ���� ���������
                    break;
                }
                case 14: { // ����� ����������
                    state = 13; // ��������� �� ������ ���������
                    break;
                }
                case 15: { // ����� ����������
                    state = 14; // ����� ����������
                    break;
                }
                case 16: { // �������� �� ���� ������ �������
                    state = 11; // ������� ����� ���� ���������
                    break;
                }
                case 17: { // ������� ��� ������������ ������ ��� ����� �����
                    state = 4; // ���������� ��������� ����������� �����
                    break;
                }
                case 18: { // ������� ��� ������������ ������ ��� ����� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 21; // �������������� ���������� ����� ��������
                    } else {
                        state = 23; // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    }
                    break;
                }
                case 19: { // ����������� ����� ����� �����
                    state = 17; // ������� ��� ������������ ������ ��� ����� �����
                    break;
                }
                case 20: { // ���������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // ����������� ����� ����� �����
                    }
                    break;
                }
                case 21: { // �������������� ���������� ����� ��������
                    state = 20; // ���������� (�������)
                    break;
                }
                case 22: { // �������� ���� ������� � ����� ����� ��� ��� �����
                    state = 17; // ������� ��� ������������ ������ ��� ����� �����
                    break;
                }
                case 23: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 24; // ����� ����� ������� �� ������ ��������
                    } else {
                        state = 25; // ����� ����� �����
                    }
                    break;
                }
                case 24: { // ����� ����� ������� �� ������ ��������
                    state = 22; // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 25: { // ����� ����� �����
                    state = 22; // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 26: { // ������� ��� ������������ ������ ��� ������ �����
                    state = 18; // ������� ��� ������������ ������ ��� ����� ����� (���������)
                    break;
                }
                case 27: { // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    if (stack.popBoolean()) {
                        state = 30; // �������������� ���������� ����� ��������
                    } else {
                        state = 32; // �������� ���� ������� � ������ ����� ��� ��� ����� (���������)
                    }
                    break;
                }
                case 28: { // ����������� ����� ��� ������ �����
                    state = 26; // ������� ��� ������������ ������ ��� ������ �����
                    break;
                }
                case 29: { // ���������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 28; // ����������� ����� ��� ������ �����
                    }
                    break;
                }
                case 30: { // �������������� ���������� ����� ��������
                    state = 29; // ���������� (�������)
                    break;
                }
                case 31: { // �������� ���� ������� � ������ ����� ��� ��� �����
                    state = 26; // ������� ��� ������������ ������ ��� ������ �����
                    break;
                }
                case 32: { // �������� ���� ������� � ������ ����� ��� ��� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 33; // ������ ����� ������� �� ������ ��������
                    } else {
                        state = 34; // ������ ����� �����
                    }
                    break;
                }
                case 33: { // ������ ����� ������� �� ������ ��������
                    state = 31; // �������� ���� ������� � ������ ����� ��� ��� �����
                    break;
                }
                case 34: { // ������ ����� �����
                    state = 31; // �������� ���� ������� � ������ ����� ��� ��� �����
                    break;
                }
                case 35: { // ������ �� ����� ��������� �������
                    state = 27; // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 35; // ������ �� ����� ��������� �������
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
                case 1: { // ������������� ��������� ����������
                    comment = QuickSort.this.getComment("QSort.SetVariables"); 
                    args = new Object[]{new Integer(d.QSort_left + 1), new Integer(d.QSort_right + 1)}; 
                    break;
                }
                case 2: { // ����� �������
                    comment = QuickSort.this.getComment("QSort.ChooseBarrier"); 
                    break;
                }
                case 3: { // ��������� � ����������
                    comment = QuickSort.this.getComment("QSort.partitionMessage"); 
                    break;
                }
                case 6: { // ����������� i
                    comment = QuickSort.this.getComment("QSort.incrementI"); 
                    args = new Object[]{new Integer(d.array[d.QSort_i - 1]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 7: { // ������ ������ ������� ��� ������
                    comment = QuickSort.this.getComment("QSort.firstElementFound"); 
                    args = new Object[]{new Integer(d.array[d.QSort_i]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 9: { // ��������� j
                    comment = QuickSort.this.getComment("QSort.decrementJ"); 
                    args = new Object[]{new Integer(d.array[d.QSort_j + 1]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 10: { // ������ ������ ������� ��� ������
                    comment = QuickSort.this.getComment("QSort.secondElementFound"); 
                    args = new Object[]{new Integer(d.array[d.QSort_j]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 13: { // ��������� �� ������ ���������
                    comment = QuickSort.this.getComment("QSort.elementsSwapComment"); 
                    break;
                }
                case 15: { // ����� ����������
                    comment = QuickSort.this.getComment("QSort.shiftPointers"); 
                    break;
                }
                case 16: { // �������� �� ���� ������ �������
                    comment = QuickSort.this.getComment("QSort.notElementsSwap"); 
                    args = new Object[]{new Integer(d.array[d.QSort_j]),                                        new Integer(d.array[d.QSort_i])}; 
                    break;
                }
                case 19: { // ����������� ����� ����� �����
                    comment = QuickSort.this.getComment("QSort.launchLeft"); 
                    args = new Object[]{new Integer(d.QSort_left + 1), new Integer(d.QSort_j + 1)}; 
                    break;
                }
                case 20: { // ���������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 24: { // ����� ����� ������� �� ������ ��������
                    comment = QuickSort.this.getComment("QSort.leftContainsOneElement"); 
                    break;
                }
                case 25: { // ����� ����� �����
                    comment = QuickSort.this.getComment("QSort.leftContainsZeroElements"); 
                    break;
                }
                case 28: { // ����������� ����� ��� ������ �����
                    comment = QuickSort.this.getComment("QSort.launchRight"); 
                    args = new Object[]{new Integer(d.QSort_i + 1), new Integer(d.QSort_right + 1)}; 
                    break;
                }
                case 29: { // ���������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 33: { // ������ ����� ������� �� ������ ��������
                    comment = QuickSort.this.getComment("QSort.rightContainsOneElement"); 
                    break;
                }
                case 34: { // ������ ����� �����
                    comment = QuickSort.this.getComment("QSort.rightContainsZeroElements"); 
                    break;
                }
                case 35: { // ������ �� ����� ��������� �������
                    comment = QuickSort.this.getComment("QSort.popBorders"); 
                    args = new Object[]{new Integer(d.QSort_left + 1), new Integer(d.QSort_right + 1)}; 
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
                case 1: { // ������������� ��������� ����������
                    					d.visualizer.updateArray(d.QSort_left, d.QSort_right);
                    					d.visualizer.updateBar();
                    					d.visualizer.updateBorders();
                    break;
                }
                case 2: { // ����� �������
                    				d.visualizer.updateBar();
                    				d.visualizer.updateBorders();
                    break;
                }
                case 3: { // ��������� � ����������
                    				d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 1, 1);
                    				d.visualizer.updateBorders();
                    break;
                }
                case 6: { // ����������� i
                    						d.visualizer.updatePointers(d.QSort_i - 1, d.QSort_j, 1, 1);
                    						d.visualizer.updateBorders();
                    break;
                }
                case 7: { // ������ ������ ������� ��� ������
                    					d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 2, 1);
                    					d.visualizer.updateBorders();
                    break;
                }
                case 9: { // ��������� j
                    						d.visualizer.updatePointers(d.QSort_i, d.QSort_j + 1, 2, 1);
                    						d.visualizer.updateBorders();
                    break;
                }
                case 10: { // ������ ������ ������� ��� ������
                    					d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 2, 2);
                    					d.visualizer.updateBorders();
                    break;
                }
                case 15: { // ����� ����������
                    							d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 1, 1);
                    							d.visualizer.updateBorders();
                    break;
                }
                case 16: { // �������� �� ���� ������ �������
                    							d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 1, 1);
                    							d.visualizer.updateBorders();
                    break;
                }
                case 19: { // ����������� ����� ����� �����
                    						d.visualizer.updateArray(d.QSort_left, d.QSort_right);
                    						d.visualizer.updateBorders();
                    break;
                }
                case 20: { // ���������� (�������)
                    child.drawState(); 
                    break;
                }
                case 24: { // ����� ����� ������� �� ������ ��������
                    								d.visualizer.updateBorders();
                    break;
                }
                case 25: { // ����� ����� �����
                    								d.visualizer.updateBorders();
                    break;
                }
                case 28: { // ����������� ����� ��� ������ �����
                    						d.visualizer.updateArray(d.QSort_left, d.QSort_right);
                    						d.visualizer.updateBorders();
                    break;
                }
                case 29: { // ���������� (�������)
                    child.drawState(); 
                    break;
                }
                case 33: { // ������ ����� ������� �� ������ ��������
                    								d.visualizer.updateBorders();
                    break;
                }
                case 34: { // ������ ����� �����
                    								d.visualizer.updateBorders();
                    break;
                }
                case 35: { // ������ �� ����� ��������� �������
                    				d.visualizer.updateBar();
                    				d.visualizer.updateBorders();
                    break;
                }
            }
        }
    }
}
