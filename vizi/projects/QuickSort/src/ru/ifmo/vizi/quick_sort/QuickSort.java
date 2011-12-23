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
        public int[] a = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * ������ ����� ������ (��� �����������).
          */
        public int[] globL = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * ������ ������ ������ (��� �����������).
          */
        public int[] globR = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * ������ �����.
          */
        public int[] globI = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * ������� ��������.
          */
        public int ptr = 0;

        /**
          * ��������� �������.
          */
        public QuickSortVisualizer visualizer = null;

        /**
          * ������.
          */
        public int barrier = 0;

        /**
          * ����� ������� (��������� QSort).
          */
        public int QSort_l;

        /**
          * ������ ������� (��������� QSort).
          */
        public int QSort_r;

        /**
          * ������ (��������� QSort).
          */
        public int QSort_m;

        /**
          * ��������� ���������� (��������� QSort).
          */
        public int QSort_c;

        /**
          * ���������� ����� (��������� QSort).
          */
        public int QSort_i;

        /**
          * ���������� ����� (��������� QSort).
          */
        public int QSort_j;

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
                    storeField(d, "ptr");
                    d.ptr = 0;
                    storeArray(d.globL, d.ptr);
                    d.globL[d.ptr] = 0;
                    storeArray(d.globR, d.ptr);
                    d.globR[d.ptr] = d.a.length - 1;
                    storeField(d, "ptr");
                    d.ptr = d.ptr + 1;
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
                    d.visualizer.updateArrow(-666, -666, false);
                    d.visualizer.updateMainArray(-666, -666, -666, -666, 0, 0, 0, 5);
                    d.visualizer.updateStack(0, 0, 1);
                    break;
                }
                case 2: { // ���������� (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.updateArrow(-666, -666, false);                
                    d.visualizer.updateMainArray(-666, -666, -666, -666, 3, 0, 0, 5);
                    d.visualizer.updateStack(d.ptr, 0, 1);
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
        private final int END_STATE = 37;

        /**
          * �����������.
          */
        public QSort() {
            super( 
                "QSort", 
                0, // ����� ���������� ��������� 
                37, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "���������������� ���������� ����� ������", 
                    "����� �������", 
                    "��������� � ����������", 
                    "���������� ��������� ����������� �����", 
                    "����� �������� �������� ��� ������� �������", 
                    "����������� i", 
                    "������� 1 ��� ����� ������", 
                    "����� �������� �������� ��� ������� �������", 
                    "��������� j", 
                    "������� 2 ��� ����� ������", 
                    "������� ����� ���� ���������", 
                    "������� ����� ���� ��������� (���������)", 
                    "����� ��������� ������� (���������)", 
                    "����� ��������� �������", 
                    "����� ����������", 
                    "�������� �� ���� ������ �������", 
                    "������� ��� ������������ ������ ��� ����� �����", 
                    "������� ��� ������������ ������ ��� ����� ����� (���������)", 
                    "����������� ����� ��� ����� �����", 
                    "���������� (�������)", 
                    "�������������� ���������� ����� ��������", 
                    "�������� ���� ������� � ����� ����� ��� ��� �����", 
                    "�������� ���� ������� � ����� ����� ��� ��� ����� (���������)", 
                    "leftNotRec", 
                    "leftNotRec", 
                    "������� ��� ������������ ������ ��� ������ �����", 
                    "������� ��� ������������ ������ ��� ������ ����� (���������)", 
                    "����������� ����� ��� ������ �����", 
                    "���������� (�������)", 
                    "�������������� ���������� ����� ��������", 
                    "�������� ���� ������� � ����� ����� ��� ��� �����", 
                    "�������� ���� ������� � ����� ����� ��� ��� ����� (���������)", 
                    "rightNotRecOne", 
                    "rightNotRecEmpty", 
                    "����������� �� ����� ��������� �������", 
                    "�������������� ������ ��������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    1, // ���������������� ���������� ����� ������ 
                    1, // ����� ������� 
                    1, // ��������� � ���������� 
                    -1, // ���������� ��������� ����������� ����� 
                    -1, // ����� �������� �������� ��� ������� ������� 
                    0, // ����������� i 
                    0, // ������� 1 ��� ����� ������ 
                    -1, // ����� �������� �������� ��� ������� ������� 
                    0, // ��������� j 
                    0, // ������� 2 ��� ����� ������ 
                    -1, // ������� ����� ���� ��������� 
                    -1, // ������� ����� ���� ��������� (���������) 
                    0, // ����� ��������� ������� (���������) 
                    -1, // ����� ��������� ������� 
                    0, // ����� ���������� 
                    0, // �������� �� ���� ������ ������� 
                    -1, // ������� ��� ������������ ������ ��� ����� ����� 
                    -1, // ������� ��� ������������ ������ ��� ����� ����� (���������) 
                    1, // ����������� ����� ��� ����� ����� 
                    CALL_AUTO_LEVEL, // ���������� (�������) 
                    -1, // �������������� ���������� ����� �������� 
                    -1, // �������� ���� ������� � ����� ����� ��� ��� ����� 
                    -1, // �������� ���� ������� � ����� ����� ��� ��� ����� (���������) 
                    1, // leftNotRec 
                    1, // leftNotRec 
                    -1, // ������� ��� ������������ ������ ��� ������ ����� 
                    -1, // ������� ��� ������������ ������ ��� ������ ����� (���������) 
                    1, // ����������� ����� ��� ������ ����� 
                    CALL_AUTO_LEVEL, // ���������� (�������) 
                    -1, // �������������� ���������� ����� �������� 
                    -1, // �������� ���� ������� � ����� ����� ��� ��� ����� 
                    -1, // �������� ���� ������� � ����� ����� ��� ��� ����� (���������) 
                    1, // rightNotRecOne 
                    1, // rightNotRecEmpty 
                    0, // ����������� �� ����� ��������� ������� 
                    -1, // �������������� ������ �������� 
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
                    state = 1; // ���������������� ���������� ����� ������
                    break;
                }
                case 1: { // ���������������� ���������� ����� ������
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
                    if (d.a[d.QSort_i] < d.QSort_m) {
                        state = 6; // ����������� i
                    } else {
                        state = 7; // ������� 1 ��� ����� ������
                    }
                    break;
                }
                case 6: { // ����������� i
                    stack.pushBoolean(true); 
                    state = 5; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 7: { // ������� 1 ��� ����� ������
                    stack.pushBoolean(false); 
                    state = 8; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 8: { // ����� �������� �������� ��� ������� �������
                    if (d.a[d.QSort_j] > d.QSort_m) {
                        state = 9; // ��������� j
                    } else {
                        state = 10; // ������� 2 ��� ����� ������
                    }
                    break;
                }
                case 9: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 8; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 10: { // ������� 2 ��� ����� ������
                    state = 11; // ������� ����� ���� ���������
                    break;
                }
                case 11: { // ������� ����� ���� ���������
                    if (d.QSort_i <= d.QSort_j) {
                        state = 13; // ����� ��������� ������� (���������)
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
                case 13: { // ����� ��������� ������� (���������)
                    state = 14; // ����� ��������� �������
                    break;
                }
                case 14: { // ����� ��������� �������
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
                    if (d.QSort_l < d.QSort_j) {
                        state = 19; // ����������� ����� ��� ����� �����
                    } else {
                        state = 22; // �������� ���� ������� � ����� ����� ��� ��� �����
                    }
                    break;
                }
                case 18: { // ������� ��� ������������ ������ ��� ����� ����� (���������)
                    state = 26; // ������� ��� ������������ ������ ��� ������ �����
                    break;
                }
                case 19: { // ����������� ����� ��� ����� �����
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
                    if (d.QSort_l == d.QSort_j) {
                        state = 24; // leftNotRec
                    } else {
                        state = 25; // leftNotRec
                    }
                    break;
                }
                case 23: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    stack.pushBoolean(false); 
                    state = 18; // ������� ��� ������������ ������ ��� ����� ����� (���������)
                    break;
                }
                case 24: { // leftNotRec
                    stack.pushBoolean(true); 
                    state = 23; // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 25: { // leftNotRec
                    stack.pushBoolean(false); 
                    state = 23; // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 26: { // ������� ��� ������������ ������ ��� ������ �����
                    if (d.QSort_i < d.QSort_r) {
                        state = 28; // ����������� ����� ��� ������ �����
                    } else {
                        state = 31; // �������� ���� ������� � ����� ����� ��� ��� �����
                    }
                    break;
                }
                case 27: { // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    state = 35; // ����������� �� ����� ��������� �������
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
                case 31: { // �������� ���� ������� � ����� ����� ��� ��� �����
                    if (d.QSort_r == d.QSort_i) {
                        state = 33; // rightNotRecOne
                    } else {
                        state = 34; // rightNotRecEmpty
                    }
                    break;
                }
                case 32: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    stack.pushBoolean(false); 
                    state = 27; // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    break;
                }
                case 33: { // rightNotRecOne
                    stack.pushBoolean(true); 
                    state = 32; // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 34: { // rightNotRecEmpty
                    stack.pushBoolean(false); 
                    state = 32; // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 35: { // ����������� �� ����� ��������� �������
                    state = 36; // �������������� ������ ��������
                    break;
                }
                case 36: { // �������������� ������ ��������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ���������������� ���������� ����� ������
                    startSection();
                    storeField(d, "QSort_l");
                    d.QSort_l = d.globL[d.ptr - 1];
                    storeField(d, "QSort_i");
                    d.QSort_i = d.QSort_l;
                    storeField(d, "QSort_r");
                    d.QSort_r = d.globR[d.ptr - 1];
                    storeField(d, "QSort_j");
                    d.QSort_j = d.QSort_r;
                    break;
                }
                case 2: { // ����� �������
                    startSection();
                    storeField(d, "QSort_m");
                    d.QSort_m = d.a[d.QSort_l];
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
                case 7: { // ������� 1 ��� ����� ������
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
                case 10: { // ������� 2 ��� ����� ������
                    startSection();
                    break;
                }
                case 11: { // ������� ����� ���� ���������
                    break;
                }
                case 12: { // ������� ����� ���� ��������� (���������)
                    break;
                }
                case 13: { // ����� ��������� ������� (���������)
                    startSection();
                    break;
                }
                case 14: { // ����� ��������� �������
                    startSection();
                    storeField(d, "QSort_c");
                    d.QSort_c = d.a[d.QSort_i];
                    storeArray(d.a, d.QSort_i);
                    d.a[d.QSort_i] = d.a[d.QSort_j];
                    storeArray(d.a, d.QSort_j);
                    d.a[d.QSort_j] = d.QSort_c;
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
                case 19: { // ����������� ����� ��� ����� �����
                    startSection();
                    storeArray(d.globL, d.ptr);
                    d.globL[d.ptr] = d.QSort_l;
                    storeArray(d.globR, d.ptr);
                    d.globR[d.ptr] = d.QSort_j;
                    storeArray(d.globI, d.ptr - 1);
                    d.globI[d.ptr - 1] = d.QSort_i;
                    storeField(d, "ptr");
                    d.ptr = d.ptr + 1;
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
                    storeField(d, "QSort_l");
                    d.QSort_l = d.globL[d.ptr - 1];
                    storeField(d, "QSort_r");
                    d.QSort_r = d.globR[d.ptr - 1];
                    storeField(d, "QSort_i");
                    d.QSort_i = d.globI[d.ptr - 1];
                    break;
                }
                case 22: { // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 23: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 24: { // leftNotRec
                    startSection();
                    break;
                }
                case 25: { // leftNotRec
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
                    storeArray(d.globL, d.ptr);
                    d.globL[d.ptr] = d.QSort_i;
                    storeArray(d.globR, d.ptr);
                    d.globR[d.ptr] = d.QSort_r;
                    storeField(d, "ptr");
                    d.ptr = d.ptr + 1;
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
                    storeField(d, "QSort_l");
                    d.QSort_l = d.globL[d.ptr - 1];
                    storeField(d, "QSort_r");
                    d.QSort_r = d.globR[d.ptr - 1];
                    break;
                }
                case 31: { // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 32: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 33: { // rightNotRecOne
                    startSection();
                    break;
                }
                case 34: { // rightNotRecEmpty
                    startSection();
                    break;
                }
                case 35: { // ����������� �� ����� ��������� �������
                    startSection();
                    break;
                }
                case 36: { // �������������� ������ ��������
                    startSection();
                    storeField(d, "ptr");
                    d.ptr = d.ptr - 1;
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
                case 1: { // ���������������� ���������� ����� ������
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
                case 7: { // ������� 1 ��� ����� ������
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
                case 10: { // ������� 2 ��� ����� ������
                    restoreSection();
                    break;
                }
                case 11: { // ������� ����� ���� ���������
                    break;
                }
                case 12: { // ������� ����� ���� ��������� (���������)
                    break;
                }
                case 13: { // ����� ��������� ������� (���������)
                    restoreSection();
                    break;
                }
                case 14: { // ����� ��������� �������
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
                case 19: { // ����������� ����� ��� ����� �����
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
                case 24: { // leftNotRec
                    restoreSection();
                    break;
                }
                case 25: { // leftNotRec
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
                case 31: { // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 32: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    break;
                }
                case 33: { // rightNotRecOne
                    restoreSection();
                    break;
                }
                case 34: { // rightNotRecEmpty
                    restoreSection();
                    break;
                }
                case 35: { // ����������� �� ����� ��������� �������
                    restoreSection();
                    break;
                }
                case 36: { // �������������� ������ ��������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ���������������� ���������� ����� ������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����� �������
                    state = 1; // ���������������� ���������� ����� ������
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
                case 7: { // ������� 1 ��� ����� ������
                    state = 5; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 8: { // ����� �������� �������� ��� ������� �������
                    if (stack.popBoolean()) {
                        state = 9; // ��������� j
                    } else {
                        state = 7; // ������� 1 ��� ����� ������
                    }
                    break;
                }
                case 9: { // ��������� j
                    state = 8; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 10: { // ������� 2 ��� ����� ������
                    state = 8; // ����� �������� �������� ��� ������� �������
                    break;
                }
                case 11: { // ������� ����� ���� ���������
                    state = 10; // ������� 2 ��� ����� ������
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
                case 13: { // ����� ��������� ������� (���������)
                    state = 11; // ������� ����� ���� ���������
                    break;
                }
                case 14: { // ����� ��������� �������
                    state = 13; // ����� ��������� ������� (���������)
                    break;
                }
                case 15: { // ����� ����������
                    state = 14; // ����� ��������� �������
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
                case 19: { // ����������� ����� ��� ����� �����
                    state = 17; // ������� ��� ������������ ������ ��� ����� �����
                    break;
                }
                case 20: { // ���������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // ����������� ����� ��� ����� �����
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
                        state = 24; // leftNotRec
                    } else {
                        state = 25; // leftNotRec
                    }
                    break;
                }
                case 24: { // leftNotRec
                    state = 22; // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 25: { // leftNotRec
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
                        state = 32; // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
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
                case 31: { // �������� ���� ������� � ����� ����� ��� ��� �����
                    state = 26; // ������� ��� ������������ ������ ��� ������ �����
                    break;
                }
                case 32: { // �������� ���� ������� � ����� ����� ��� ��� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 33; // rightNotRecOne
                    } else {
                        state = 34; // rightNotRecEmpty
                    }
                    break;
                }
                case 33: { // rightNotRecOne
                    state = 31; // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 34: { // rightNotRecEmpty
                    state = 31; // �������� ���� ������� � ����� ����� ��� ��� �����
                    break;
                }
                case 35: { // ����������� �� ����� ��������� �������
                    state = 27; // ������� ��� ������������ ������ ��� ������ ����� (���������)
                    break;
                }
                case 36: { // �������������� ������ ��������
                    state = 35; // ����������� �� ����� ��������� �������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 36; // �������������� ������ ��������
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
                case 1: { // ���������������� ���������� ����� ������
                    comment = QuickSort.this.getComment("QSort.SetVars"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_r + 1)}; 
                    break;
                }
                case 2: { // ����� �������
                    comment = QuickSort.this.getComment("QSort.ChBarrier"); 
                    break;
                }
                case 3: { // ��������� � ����������
                    comment = QuickSort.this.getComment("QSort.partitionMessage"); 
                    break;
                }
                case 6: { // ����������� i
                    comment = QuickSort.this.getComment("QSort.iInc"); 
                    args = new Object[]{new Integer(d.a[d.QSort_i - 1]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 7: { // ������� 1 ��� ����� ������
                    comment = QuickSort.this.getComment("QSort.element1found"); 
                    args = new Object[]{new Integer(d.a[d.QSort_i]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 9: { // ��������� j
                    comment = QuickSort.this.getComment("QSort.jDec"); 
                    args = new Object[]{new Integer(d.a[d.QSort_j + 1]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 10: { // ������� 2 ��� ����� ������
                    comment = QuickSort.this.getComment("QSort.element2found"); 
                    args = new Object[]{new Integer(d.a[d.QSort_j]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 13: { // ����� ��������� ������� (���������)
                    comment = QuickSort.this.getComment("QSort.elementsSwapComment"); 
                    break;
                }
                case 15: { // ����� ����������
                    comment = QuickSort.this.getComment("QSort.elementsNext"); 
                    break;
                }
                case 16: { // �������� �� ���� ������ �������
                    comment = QuickSort.this.getComment("QSort.notElementsSwap"); 
                    args = new Object[]{new Integer(d.a[d.QSort_j]),                                        new Integer(d.a[d.QSort_i])}; 
                    break;
                }
                case 19: { // ����������� ����� ��� ����� �����
                    comment = QuickSort.this.getComment("QSort.leftRec"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_j + 1)}; 
                    break;
                }
                case 20: { // ���������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 24: { // leftNotRec
                    comment = QuickSort.this.getComment("QSort.leftNotRecOne"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_j + 1)}; 
                    break;
                }
                case 25: { // leftNotRec
                    comment = QuickSort.this.getComment("QSort.leftNotRecNone"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_j + 1)}; 
                    break;
                }
                case 28: { // ����������� ����� ��� ������ �����
                    comment = QuickSort.this.getComment("QSort.rightRec"); 
                    args = new Object[]{new Integer(d.QSort_i + 1), new Integer(d.QSort_r + 1)}; 
                    break;
                }
                case 29: { // ���������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 33: { // rightNotRecOne
                    comment = QuickSort.this.getComment("QSort.rightNotRecOne"); 
                    args = new Object[]{new Integer(d.QSort_i + 1), new Integer(d.QSort_r + 1)}; 
                    break;
                }
                case 34: { // rightNotRecEmpty
                    comment = QuickSort.this.getComment("QSort.rightNotRecEmpty"); 
                    args = new Object[]{new Integer(d.QSort_i + 1), new Integer(d.QSort_r + 1)}; 
                    break;
                }
                case 35: { // ����������� �� ����� ��������� �������
                    comment = QuickSort.this.getComment("QSort.popFromStack"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_r + 1)}; 
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
                case 1: { // ���������������� ���������� ����� ������
                    				d.visualizer.updateArrow(-666, -666, false);
                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, -666, 0, 1, 0, 5);
                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 2: { // ����� �������
                    				d.visualizer.updateArrow(-666, -666, false);
                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_l, 0, 1, 2, 5);
                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 3: { // ��������� � ����������
                    				d.visualizer.updateArrow(-666, -666, false);
                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_l, 0, 1, 2, 5);
                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 6: { // ����������� i
                    						d.visualizer.updateArrow(-666, -666, false);
                                            d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_j, d.QSort_i - 1,
                                                                         0, 1, 2, 5);
                                            d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 7: { // ������� 1 ��� ����� ������
                    					d.visualizer.updateArrow(-666, -666, false);
                                        d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_j, d.QSort_i,
                                                                     0, 1, 2, 5);
                                        d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 9: { // ��������� j
                    						d.visualizer.updateArrow(-666, -666, false);
                                            d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j + 1,
                                                                         0, 1, 2, 5);
                                            d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 10: { // ������� 2 ��� ����� ������
                    					d.visualizer.updateArrow(-666, -666, false);
                                        d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j,
                                                                     0, 1, 2, 5);
                                        d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 13: { // ����� ��������� ������� (���������)
                    d.visualizer.updateArrow(d.QSort_i + 1, d.QSort_j + 1, true);
                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j,
                                                 0, 1, 5, 5);
                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 15: { // ����� ����������
                    d.visualizer.updateArrow(-666, -666, false);
                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j,
                                                 0, 1, 5, 5);
                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 16: { // �������� �� ���� ������ �������
                    d.visualizer.updateArrow(-666, -666, false);
                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j,
                                                 0, 1, 5, 5);
                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 19: { // ����������� ����� ��� ����� �����
                    						d.visualizer.updateArrow(-666, -666, false);
                                            d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_j, 0, 1, 2, 5);
                                            d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 20: { // ���������� (�������)
                    child.drawState(); 
                    break;
                }
                case 24: { // leftNotRec
                    								d.visualizer.updateArrow(-666, -666, false);
                                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_j, 
                                                                                 0, 1, 2, 5);
                                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 25: { // leftNotRec
                    								d.visualizer.updateArrow(-666, -666, false);
                                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_j, 
                                                                                 0, 1, 2, 5);
                                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 28: { // ����������� ����� ��� ������ �����
                    						d.visualizer.updateArrow(-666, -666, false);
                                            d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_i, 0, 1, 2, 5);
                                            d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 29: { // ���������� (�������)
                    child.drawState(); 
                    break;
                }
                case 33: { // rightNotRecOne
                    								d.visualizer.updateArrow(-666, -666, false);
                                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_i,
                                                                                 0, 1, 2, 5);
                                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 34: { // rightNotRecEmpty
                    								d.visualizer.updateArrow(-666, -666, false);
                                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_i,
                                                                                 0, 1, 2, 5);
                                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 35: { // ����������� �� ����� ��������� �������
                    				d.visualizer.updateArrow(-666, -666, false);
                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, -666, 0, 1, 2, 5);
                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
            }
        }
    }
}
