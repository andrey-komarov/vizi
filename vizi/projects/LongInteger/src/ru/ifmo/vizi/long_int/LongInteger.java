package ru.ifmo.vizi.long_int;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class LongInteger extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public LongInteger(Locale locale) {
        super("ru.ifmo.vizi.long_int.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ��������� �������.
          */
        public LongIntegerVisualizer visualizer = null;

        /**
          * ������ ��� ������.
          */
        public int[][] store = new int[200][100];

        /**
          * ������������ ������.
          */
        public int maxL = 0;

        /**
          * ������ ��� ����� �����.
          */
        public int[] storeL = new int[200];

        /**
          * ���������� ��� �������� �����������.
          */
        public int commentLevel = 10000;

        /**
          * ���������� �����.
          */
        public int i = 0;

        /**
          * ���������� �����.
          */
        public int j = 0;

        /**
          * ���������.
          */
        public int t = 0;

        /**
          * ����������� �� ���� �������.
          */
        public int indFrom = 0;

        /**
          * ����������� � ��� �������.
          */
        public int indTo = 0;

        /**
          * ����������� ������� �.
          */
        public int indFirst = 0;

        /**
          * ����������� �������.
          */
        public int indLength = 0;

        /**
          * ����� ������� ������� �������� ������ � ��������.
          */
        public int storeInd = 0;

        /**
          * ������� ������.
          */
        public String sRu = new String();

        /**
          * English String.
          */
        public String sEn = new String();

        /**
          * ������ �������� ��������.
          */
        public String smu = new String(new char[]{'+'});

        /**
          * ����� ����� ������.
          */
        public boolean firstBig = true;

        /**
          * ���������� �������.
          */
        public int ost = 0;

        /**
          * ��������� ���������� �������.
          */
        public int tempOst = 0;

        /**
          * ���������� ���������� ��������.
          */
        public int k = 0;

        /**
          * �������� ������ (��������� FastSum).
          */
        public int FastSum_leng;

        /**
          * ������ ����� (��������� KarSum).
          */
        public int KarSum_leng;

        public String toString() {
            return "" + d.storeInd;
        }
    }

    /**
      * ����������.
      */
    private final class Sum extends BaseAutomata implements Automata {
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
        public Sum() {
            super( 
                "Sum", 
                0, // ����� ���������� ��������� 
                11, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "����", 
                    "��������� ���", 
                    "���������� �������", 
                    "����������: t mod 10", 
                    "����������: k div 10", 
                    "���������", 
                    "���� ������� �� ����� 0", 
                    "���� ������� �� ����� 0 (���������)", 
                    "���������: ������������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    -1, // ���� 
                    1, // ��������� ��� 
                    0, // ���������� ������� 
                    0, // ����������: t mod 10 
                    0, // ����������: k div 10 
                    -1, // ��������� 
                    -1, // ���� ������� �� ����� 0 
                    -1, // ���� ������� �� ����� 0 (���������) 
                    0, // ���������: ������������ 
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
                    state = 1; // ������ �����
                    break;
                }
                case 1: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 2; // ����
                    break;
                }
                case 2: { // ����
                    if (d.i < Math.max(d.storeL[0], d.storeL[1])) {
                        state = 3; // ��������� ���
                    } else {
                        state = 8; // ���� ������� �� ����� 0
                    }
                    break;
                }
                case 3: { // ��������� ���
                    state = 4; // ���������� �������
                    break;
                }
                case 4: { // ���������� �������
                    state = 5; // ����������: t mod 10
                    break;
                }
                case 5: { // ����������: t mod 10
                    state = 6; // ����������: k div 10
                    break;
                }
                case 6: { // ����������: k div 10
                    state = 7; // ���������
                    break;
                }
                case 7: { // ���������
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
                case 8: { // ���� ������� �� ����� 0
                    if (d.ost != 0) {
                        state = 10; // ���������: ������������
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // ���� ������� �� ����� 0 (���������)
                    }
                    break;
                }
                case 9: { // ���� ������� �� ����� 0 (���������)
                    state = END_STATE; 
                    break;
                }
                case 10: { // ���������: ������������
                    stack.pushBoolean(true); 
                    state = 9; // ���� ������� �� ����� 0 (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "ost");
                    d.ost = 0;
                    storeField(d, "smu");
                    d.smu = "+";
                    storeField(d, "tempOst");
                    d.tempOst = 0;
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ��������� ���
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    break;
                }
                case 4: { // ���������� �������
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = d.store[0][d.i] + d.store[1][d.i] + d.ost;
                    break;
                }
                case 5: { // ����������: t mod 10
                    startSection();
                    storeArray(d.store[2], d.i);
                    d.store[2][d.i] = d.tempOst % 10;
                    storeField(d, "ost");
                    d.ost = -10000;
                    break;
                }
                case 6: { // ����������: k div 10
                    startSection();
                    storeField(d, "ost");
                    d.ost = d.tempOst / 10;
                    break;
                }
                case 7: { // ���������
                    startSection();
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 8: { // ���� ������� �� ����� 0
                    break;
                }
                case 9: { // ���� ������� �� ����� 0 (���������)
                    break;
                }
                case 10: { // ���������: ������������
                    startSection();
                    storeField(d, "sRu");
                    d.sRu = d.sRu + " (������������)";
                    storeField(d, "sEn");
                    d.sEn = d.sEn + " (repletion)";
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
                case 1: { // ������ �����
                    restoreSection();
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ��������� ���
                    restoreSection();
                    break;
                }
                case 4: { // ���������� �������
                    restoreSection();
                    break;
                }
                case 5: { // ����������: t mod 10
                    restoreSection();
                    break;
                }
                case 6: { // ����������: k div 10
                    restoreSection();
                    break;
                }
                case 7: { // ���������
                    restoreSection();
                    break;
                }
                case 8: { // ���� ������� �� ����� 0
                    break;
                }
                case 9: { // ���� ������� �� ����� 0 (���������)
                    break;
                }
                case 10: { // ���������: ������������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����
                    if (stack.popBoolean()) {
                        state = 7; // ���������
                    } else {
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // ��������� ���
                    state = 2; // ����
                    break;
                }
                case 4: { // ���������� �������
                    state = 3; // ��������� ���
                    break;
                }
                case 5: { // ����������: t mod 10
                    state = 4; // ���������� �������
                    break;
                }
                case 6: { // ����������: k div 10
                    state = 5; // ����������: t mod 10
                    break;
                }
                case 7: { // ���������
                    state = 6; // ����������: k div 10
                    break;
                }
                case 8: { // ���� ������� �� ����� 0
                    state = 2; // ����
                    break;
                }
                case 9: { // ���� ������� �� ����� 0 (���������)
                    if (stack.popBoolean()) {
                        state = 10; // ���������: ������������
                    } else {
                        state = 8; // ���� ������� �� ����� 0
                    }
                    break;
                }
                case 10: { // ���������: ������������
                    state = 8; // ���� ������� �� ����� 0
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 9; // ���� ������� �� ����� 0 (���������)
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
                case 3: { // ��������� ���
                    comment = LongInteger.this.getComment("Sum.showItem"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.i]), new Integer(d.i + 1)}; 
                    break;
                }
                case 4: { // ���������� �������
                    comment = LongInteger.this.getComment("Sum.sumShow"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.i]), new Integer(d.store[0][d.i] + d.store[1][d.i]), new Integer(d.tempOst)}; 
                    break;
                }
                case 5: { // ����������: t mod 10
                    comment = LongInteger.this.getComment("Sum.sumWrite"); 
                    args = new Object[]{new Integer(d.tempOst % 10)}; 
                    break;
                }
                case 6: { // ����������: k div 10
                    comment = LongInteger.this.getComment("Sum.remainderSumWrite"); 
                    args = new Object[]{new Integer(d.tempOst / 10)}; 
                    break;
                }
                case 10: { // ���������: ������������
                    comment = LongInteger.this.getComment("Sum.showOverF"); 
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
                case 3: { // ��������� ���
                    d.visualizer.showIntegers(d.i, d.i, -1, 1, 1, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 4: { // ���������� �������
                    d.visualizer.showIntegers(d.i, d.i, d.i, 1, 1, 0);
                    d.visualizer.showTemp(2, 1);
                    break;
                }
                case 5: { // ����������: t mod 10
                    d.visualizer.showIntegers(d.i, d.i, d.i, 3, 3, 2);
                    d.visualizer.showTemp(1, 0);
                    break;
                }
                case 6: { // ����������: k div 10
                    d.visualizer.showIntegers(d.i, d.i, d.i, 3, 3, 0);
                    d.visualizer.showTemp(1, 2);
                    break;
                }
                case 10: { // ���������: ������������
                    d.visualizer.showIntegers(d.i, d.i, d.i, 0, 0, 0);
                    d.visualizer.showTemp(0, 2);
                    break;
                }
            }
        }
    }

    /**
      * ��������.
      */
    private final class Mines extends BaseAutomata implements Automata {
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
        public Mines() {
            super( 
                "Mines", 
                0, // ����� ���������� ��������� 
                8, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "����", 
                    "��������� ���", 
                    "��������� ��������", 
                    "����������: t mod 10", 
                    "����������: t / 10", 
                    "���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    -1, // ���� 
                    1, // ��������� ��� 
                    0, // ��������� �������� 
                    0, // ����������: t mod 10 
                    0, // ����������: t / 10 
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
                    state = 1; // ������ �����
                    break;
                }
                case 1: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 2; // ����
                    break;
                }
                case 2: { // ����
                    if (d.i < d.maxL) {
                        state = 3; // ��������� ���
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ��������� ���
                    state = 4; // ��������� ��������
                    break;
                }
                case 4: { // ��������� ��������
                    state = 5; // ����������: t mod 10
                    break;
                }
                case 5: { // ����������: t mod 10
                    state = 6; // ����������: t / 10
                    break;
                }
                case 6: { // ����������: t / 10
                    state = 7; // ���������
                    break;
                }
                case 7: { // ���������
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "smu");
                    d.smu = "-";
                    storeField(d, "ost");
                    d.ost = 0;
                    storeField(d, "tempOst");
                    d.tempOst = 0;
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ��������� ���
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    break;
                }
                case 4: { // ��������� ��������
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = d.store[0][d.i] - d.store[1][d.i] + d.ost;
                    break;
                }
                case 5: { // ����������: t mod 10
                    startSection();
                    storeArray(d.store[2], d.i);
                    d.store[2][d.i] = (d.tempOst + 1000) % 10;
                    storeField(d, "ost");
                    d.ost = -10000;
                    break;
                }
                case 6: { // ����������: t / 10
                    startSection();
                    storeField(d, "ost");
                    d.ost = (int)Math.floor(d.tempOst / 10.);
                    break;
                }
                case 7: { // ���������
                    startSection();
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
                case 1: { // ������ �����
                    restoreSection();
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ��������� ���
                    restoreSection();
                    break;
                }
                case 4: { // ��������� ��������
                    restoreSection();
                    break;
                }
                case 5: { // ����������: t mod 10
                    restoreSection();
                    break;
                }
                case 6: { // ����������: t / 10
                    restoreSection();
                    break;
                }
                case 7: { // ���������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����
                    if (stack.popBoolean()) {
                        state = 7; // ���������
                    } else {
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // ��������� ���
                    state = 2; // ����
                    break;
                }
                case 4: { // ��������� ��������
                    state = 3; // ��������� ���
                    break;
                }
                case 5: { // ����������: t mod 10
                    state = 4; // ��������� ��������
                    break;
                }
                case 6: { // ����������: t / 10
                    state = 5; // ����������: t mod 10
                    break;
                }
                case 7: { // ���������
                    state = 6; // ����������: t / 10
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ����
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
                case 3: { // ��������� ���
                    comment = LongInteger.this.getComment("Mines.showItem"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.i]), new Integer(d.i + 1)}; 
                    break;
                }
                case 4: { // ��������� ��������
                    comment = LongInteger.this.getComment("Mines.mines"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.i]), new Integer(d.store[0][d.i] - d.store[1][d.i]), new Integer(d.tempOst)}; 
                    break;
                }
                case 5: { // ����������: t mod 10
                    comment = LongInteger.this.getComment("Mines.minesWrite"); 
                    args = new Object[]{new Integer(d.store[2][d.i])}; 
                    break;
                }
                case 6: { // ����������: t / 10
                    comment = LongInteger.this.getComment("Mines.remainderMinesWrite"); 
                    args = new Object[]{new Integer(d.ost)}; 
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
                case 3: { // ��������� ���
                    d.visualizer.showIntegers(d.i, d.i, -1, 1, 1, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 4: { // ��������� ��������
                    d.visualizer.showIntegers(d.i, d.i, d.i, 1, 1, 0);
                    d.visualizer.showTemp(2, 1);
                    break;
                }
                case 5: { // ����������: t mod 10
                    d.visualizer.showIntegers(d.i, d.i, d.i, 3, 3, 2);
                    d.visualizer.showTemp(1, 0);
                    break;
                }
                case 6: { // ����������: t / 10
                    d.visualizer.showIntegers(d.i, d.i, d.i, 3, 3, 0);
                    d.visualizer.showTemp(1, 2);
                    break;
                }
            }
        }
    }

    /**
      * ��������.
      */
    private final class power extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 15;

        /**
          * �����������.
          */
        public power() {
            super( 
                "power", 
                0, // ����� ���������� ��������� 
                15, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "����", 
                    "������ �����", 
                    "��� �� ������� �����", 
                    "����", 
                    "��� �� ������� �����", 
                    "��������� ��������", 
                    "����������: t mod 10", 
                    "����������: t div 10", 
                    "���������", 
                    "���� ������� �� ����� ����", 
                    "���� ������� �� ����� ���� (���������)", 
                    "���������� � ��������� ������", 
                    "���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    -1, // ���� 
                    -1, // ������ ����� 
                    1, // ��� �� ������� ����� 
                    -1, // ���� 
                    0, // ��� �� ������� ����� 
                    0, // ��������� �������� 
                    0, // ����������: t mod 10 
                    0, // ����������: t div 10 
                    -1, // ��������� 
                    -1, // ���� ������� �� ����� ���� 
                    -1, // ���� ������� �� ����� ���� (���������) 
                    0, // ���������� � ��������� ������ 
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
                    state = 1; // ������ �����
                    break;
                }
                case 1: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 2; // ����
                    break;
                }
                case 2: { // ����
                    if (d.j < d.storeL[1]) {
                        state = 3; // ������ �����
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ������ �����
                    state = 4; // ��� �� ������� �����
                    break;
                }
                case 4: { // ��� �� ������� �����
                    stack.pushBoolean(false); 
                    state = 5; // ����
                    break;
                }
                case 5: { // ����
                    if (d.i < d.storeL[0]) {
                        state = 6; // ��� �� ������� �����
                    } else {
                        state = 11; // ���� ������� �� ����� ����
                    }
                    break;
                }
                case 6: { // ��� �� ������� �����
                    state = 7; // ��������� ��������
                    break;
                }
                case 7: { // ��������� ��������
                    state = 8; // ����������: t mod 10
                    break;
                }
                case 8: { // ����������: t mod 10
                    state = 9; // ����������: t div 10
                    break;
                }
                case 9: { // ����������: t div 10
                    state = 10; // ���������
                    break;
                }
                case 10: { // ���������
                    stack.pushBoolean(true); 
                    state = 5; // ����
                    break;
                }
                case 11: { // ���� ������� �� ����� ����
                    if (d.ost > 0) {
                        state = 13; // ���������� � ��������� ������
                    } else {
                        stack.pushBoolean(false); 
                        state = 12; // ���� ������� �� ����� ���� (���������)
                    }
                    break;
                }
                case 12: { // ���� ������� �� ����� ���� (���������)
                    state = 14; // ���������
                    break;
                }
                case 13: { // ���������� � ��������� ������
                    stack.pushBoolean(true); 
                    state = 12; // ���� ������� �� ����� ���� (���������)
                    break;
                }
                case 14: { // ���������
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "j");
                    d.j = 0;
                    storeField(d, "smu");
                    d.smu = "*";
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ������ �����
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    break;
                }
                case 4: { // ��� �� ������� �����
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    storeField(d, "ost");
                    d.ost = 0;
                    break;
                }
                case 5: { // ����
                    break;
                }
                case 6: { // ��� �� ������� �����
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    break;
                }
                case 7: { // ��������� ��������
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = d.store[0][d.i] * d.store[1][d.j] + d.store[2][d.i + d.j] + d.ost;
                    break;
                }
                case 8: { // ����������: t mod 10
                    startSection();
                    storeArray(d.store[2], d.i + d.j);
                    d.store[2][d.i + d.j] = d.tempOst % 10;
                    storeField(d, "ost");
                    d.ost = -10000;
                    break;
                }
                case 9: { // ����������: t div 10
                    startSection();
                    storeField(d, "ost");
                    d.ost = d.tempOst / 10;
                    break;
                }
                case 10: { // ���������
                    startSection();
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 11: { // ���� ������� �� ����� ����
                    break;
                }
                case 12: { // ���� ������� �� ����� ���� (���������)
                    break;
                }
                case 13: { // ���������� � ��������� ������
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    storeField(d, "i");
                    d.i = d.i - 1;
                    storeArray(d.store[2], d.i + d.j + 1);
                    d.store[2][d.i + d.j + 1] = d.ost;
                    break;
                }
                case 14: { // ���������
                    startSection();
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
                case 1: { // ������ �����
                    restoreSection();
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ������ �����
                    restoreSection();
                    break;
                }
                case 4: { // ��� �� ������� �����
                    restoreSection();
                    break;
                }
                case 5: { // ����
                    break;
                }
                case 6: { // ��� �� ������� �����
                    restoreSection();
                    break;
                }
                case 7: { // ��������� ��������
                    restoreSection();
                    break;
                }
                case 8: { // ����������: t mod 10
                    restoreSection();
                    break;
                }
                case 9: { // ����������: t div 10
                    restoreSection();
                    break;
                }
                case 10: { // ���������
                    restoreSection();
                    break;
                }
                case 11: { // ���� ������� �� ����� ����
                    break;
                }
                case 12: { // ���� ������� �� ����� ���� (���������)
                    break;
                }
                case 13: { // ���������� � ��������� ������
                    restoreSection();
                    break;
                }
                case 14: { // ���������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����
                    if (stack.popBoolean()) {
                        state = 14; // ���������
                    } else {
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // ������ �����
                    state = 2; // ����
                    break;
                }
                case 4: { // ��� �� ������� �����
                    state = 3; // ������ �����
                    break;
                }
                case 5: { // ����
                    if (stack.popBoolean()) {
                        state = 10; // ���������
                    } else {
                        state = 4; // ��� �� ������� �����
                    }
                    break;
                }
                case 6: { // ��� �� ������� �����
                    state = 5; // ����
                    break;
                }
                case 7: { // ��������� ��������
                    state = 6; // ��� �� ������� �����
                    break;
                }
                case 8: { // ����������: t mod 10
                    state = 7; // ��������� ��������
                    break;
                }
                case 9: { // ����������: t div 10
                    state = 8; // ����������: t mod 10
                    break;
                }
                case 10: { // ���������
                    state = 9; // ����������: t div 10
                    break;
                }
                case 11: { // ���� ������� �� ����� ����
                    state = 5; // ����
                    break;
                }
                case 12: { // ���� ������� �� ����� ���� (���������)
                    if (stack.popBoolean()) {
                        state = 13; // ���������� � ��������� ������
                    } else {
                        state = 11; // ���� ������� �� ����� ����
                    }
                    break;
                }
                case 13: { // ���������� � ��������� ������
                    state = 11; // ���� ������� �� ����� ����
                    break;
                }
                case 14: { // ���������
                    state = 12; // ���� ������� �� ����� ���� (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ����
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
                case 4: { // ��� �� ������� �����
                    comment = LongInteger.this.getComment("power.show3"); 
                    args = new Object[]{new Integer(d.j + 1)}; 
                    break;
                }
                case 6: { // ��� �� ������� �����
                    comment = LongInteger.this.getComment("power.show2"); 
                    args = new Object[]{new Integer(d.i + 1)}; 
                    break;
                }
                case 7: { // ��������� ��������
                    comment = LongInteger.this.getComment("power.poow"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.j]), new Integer(d.store[0][d.i] * d.store[1][d.j]), new Integer(d.i + d.j + 1), new Integer(d.tempOst)}; 
                    break;
                }
                case 8: { // ����������: t mod 10
                    comment = LongInteger.this.getComment("power.poowWrite"); 
                    args = new Object[]{new Integer(d.store[2][d.i + d.j])}; 
                    break;
                }
                case 9: { // ����������: t div 10
                    comment = LongInteger.this.getComment("power.poowRemainderWrite"); 
                    args = new Object[]{new Integer(d.ost)}; 
                    break;
                }
                case 13: { // ���������� � ��������� ������
                    comment = LongInteger.this.getComment("power.writeRemainderpower"); 
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
                case 4: { // ��� �� ������� �����
                    d.visualizer.showIntegers(-1, d.j, -1, 0, 1, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 6: { // ��� �� ������� �����
                    d.visualizer.showIntegers(d.i, d.j, -1, 1, 1, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 7: { // ��������� ��������
                    d.visualizer.showIntegers(d.i, d.j, d.i + d.j, 1, 1, 1);
                    d.visualizer.showTemp(2, 1);
                    break;
                }
                case 8: { // ����������: t mod 10
                    d.visualizer.showIntegers(d.i, d.j, d.i + d.j, 3, 3, 2);
                    d.visualizer.showTemp(1, 0);
                    break;
                }
                case 9: { // ����������: t div 10
                    d.visualizer.showIntegers(d.i, d.j, d.i + d.j, 3, 3, 0);
                    d.visualizer.showTemp(1, 2);
                    break;
                }
                case 13: { // ���������� � ��������� ������
                    d.visualizer.showIntegers(d.i, d.j, d.i + d.j + 1, 3, 3, 2);
                    d.visualizer.showTemp(0, 1);
                    break;
                }
            }
        }
    }

    /**
      * ��������� ����� ����� �� ������ ������.
      */
    private final class testFirstBig extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 10;

        /**
          * �����������.
          */
        public testFirstBig() {
            super( 
                "testFirstBig", 
                0, // ����� ���������� ��������� 
                10, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "����", 
                    "���� ������ ����� ������", 
                    "���� ������ ����� ������ (���������)", 
                    "������ ����� ������", 
                    "���� ������ ����� ������", 
                    "���� ������ ����� ������ (���������)", 
                    "������ ����� ������", 
                    "���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    -1, // ���� 
                    -1, // ���� ������ ����� ������ 
                    -1, // ���� ������ ����� ������ (���������) 
                    -1, // ������ ����� ������ 
                    -1, // ���� ������ ����� ������ 
                    -1, // ���� ������ ����� ������ (���������) 
                    -1, // ������ ����� ������ 
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
                    state = 1; // ������ �����
                    break;
                }
                case 1: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 2; // ����
                    break;
                }
                case 2: { // ����
                    if (d.i >= 0) {
                        state = 3; // ���� ������ ����� ������
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ���� ������ ����� ������
                    if (d.store[0][d.i] < d.store[1][d.i]) {
                        state = 5; // ������ ����� ������
                    } else {
                        state = 6; // ���� ������ ����� ������
                    }
                    break;
                }
                case 4: { // ���� ������ ����� ������ (���������)
                    state = 9; // ���������
                    break;
                }
                case 5: { // ������ ����� ������
                    stack.pushBoolean(true); 
                    state = 4; // ���� ������ ����� ������ (���������)
                    break;
                }
                case 6: { // ���� ������ ����� ������
                    if (d.store[0][d.i] > d.store[1][d.i]) {
                        state = 8; // ������ ����� ������
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // ���� ������ ����� ������ (���������)
                    }
                    break;
                }
                case 7: { // ���� ������ ����� ������ (���������)
                    stack.pushBoolean(false); 
                    state = 4; // ���� ������ ����� ������ (���������)
                    break;
                }
                case 8: { // ������ ����� ������
                    stack.pushBoolean(true); 
                    state = 7; // ���� ������ ����� ������ (���������)
                    break;
                }
                case 9: { // ���������
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "i");
                    d.i = d.store[0].length / 2 - 1;
                    storeField(d, "firstBig");
                    d.firstBig = true;
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ���� ������ ����� ������
                    break;
                }
                case 4: { // ���� ������ ����� ������ (���������)
                    break;
                }
                case 5: { // ������ ����� ������
                    startSection();
                    storeField(d, "firstBig");
                    d.firstBig = false; 
                    storeField(d, "i");
                    d.i = -1;
                    break;
                }
                case 6: { // ���� ������ ����� ������
                    break;
                }
                case 7: { // ���� ������ ����� ������ (���������)
                    break;
                }
                case 8: { // ������ ����� ������
                    startSection();
                    storeField(d, "i");
                    d.i = -1;
                    break;
                }
                case 9: { // ���������
                    startSection();
                    storeField(d, "i");
                    d.i = d.i - 1;
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
                case 1: { // ������ �����
                    restoreSection();
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ���� ������ ����� ������
                    break;
                }
                case 4: { // ���� ������ ����� ������ (���������)
                    break;
                }
                case 5: { // ������ ����� ������
                    restoreSection();
                    break;
                }
                case 6: { // ���� ������ ����� ������
                    break;
                }
                case 7: { // ���� ������ ����� ������ (���������)
                    break;
                }
                case 8: { // ������ ����� ������
                    restoreSection();
                    break;
                }
                case 9: { // ���������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����
                    if (stack.popBoolean()) {
                        state = 9; // ���������
                    } else {
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // ���� ������ ����� ������
                    state = 2; // ����
                    break;
                }
                case 4: { // ���� ������ ����� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 5; // ������ ����� ������
                    } else {
                        state = 7; // ���� ������ ����� ������ (���������)
                    }
                    break;
                }
                case 5: { // ������ ����� ������
                    state = 3; // ���� ������ ����� ������
                    break;
                }
                case 6: { // ���� ������ ����� ������
                    state = 3; // ���� ������ ����� ������
                    break;
                }
                case 7: { // ���� ������ ����� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 8; // ������ ����� ������
                    } else {
                        state = 6; // ���� ������ ����� ������
                    }
                    break;
                }
                case 8: { // ������ ����� ������
                    state = 6; // ���� ������ ����� ������
                    break;
                }
                case 9: { // ���������
                    state = 4; // ���� ������ ����� ������ (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ����
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
      * ������ ����� �������.
      */
    private final class Replace extends BaseAutomata implements Automata {
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
        public Replace() {
            super( 
                "Replace", 
                0, // ����� ���������� ��������� 
                4, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "����", 
                    "���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    -1, // ���� 
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
                    state = 1; // ������ �����
                    break;
                }
                case 1: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 2; // ����
                    break;
                }
                case 2: { // ����
                    if (d.i < d.store[0].length) {
                        state = 3; // ���������
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ���������
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ���������
                    startSection();
                    storeField(d, "t");
                    d.t = d.store[0][d.i];
                    storeArray(d.store[0], d.i);
                    d.store[0][d.i] = d.store[1][d.i];
                    storeArray(d.store[1], d.i);
                    d.store[1][d.i] = d.t;
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
                case 1: { // ������ �����
                    restoreSection();
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ���������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����
                    if (stack.popBoolean()) {
                        state = 3; // ���������
                    } else {
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // ���������
                    state = 2; // ����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ����
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
      * ������� ���������� (storeInd + 10)..(storeInd + 15).
      */
    private final class SuperClear extends BaseAutomata implements Automata {
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
        public SuperClear() {
            super( 
                "SuperClear", 
                0, // ����� ���������� ��������� 
                5, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "����", 
                    "������� ���������� indTo (�������)", 
                    "��� �����", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    -1, // ���� 
                    CALL_AUTO_LEVEL, // ������� ���������� indTo (�������) 
                    -1, // ��� ����� 
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
                    state = 1; // ������ �����
                    break;
                }
                case 1: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 2; // ����
                    break;
                }
                case 2: { // ����
                    if (d.i < (d.storeInd + 16)) {
                        state = 3; // ������� ���������� indTo (�������)
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ������� ���������� indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 4; // ��� �����
                    }
                    break;
                }
                case 4: { // ��� �����
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 10;
                    storeField(d, "i");
                    d.i = d.indTo;
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ������� ���������� indTo (�������)
                    if (child == null) {
                        child = new Clear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 4: { // ��� �����
                    startSection();
                    storeArray(d.storeL, d.i);
                    d.storeL[d.i] = 1;
                    storeField(d, "i");
                    d.i = d.i + 1;
                    storeField(d, "indTo");
                    d.indTo = d.i;
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
                case 1: { // ������ �����
                    restoreSection();
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ������� ���������� indTo (�������)
                    if (child == null) {
                        child = new Clear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 4: { // ��� �����
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����
                    if (stack.popBoolean()) {
                        state = 4; // ��� �����
                    } else {
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // ������� ���������� indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 2; // ����
                    }
                    break;
                }
                case 4: { // ��� �����
                    state = 3; // ������� ���������� indTo (�������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ����
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
                case 3: { // ������� ���������� indTo (�������)
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
                case 3: { // ������� ���������� indTo (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * ������� ���������� indTo.
      */
    private final class Clear extends BaseAutomata implements Automata {
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
        public Clear() {
            super( 
                "Clear", 
                0, // ����� ���������� ��������� 
                4, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "����", 
                    "��� �����", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    -1, // ���� 
                    -1, // ��� ����� 
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
                    state = 1; // ������ �����
                    break;
                }
                case 1: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 2; // ����
                    break;
                }
                case 2: { // ����
                    if (d.t < d.store[d.indFrom].length) {
                        state = 3; // ��� �����
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ��� �����
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "t");
                    d.t = 0;
                    storeArray(d.storeL, d.indTo);
                    d.storeL[d.indTo] = 0;
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ��� �����
                    startSection();
                     storeArray(d.store[d.indTo], d.t);
                     d.store[d.indTo][d.t] = 0; d.t += 1;
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
                case 1: { // ������ �����
                    restoreSection();
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ��� �����
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����
                    if (stack.popBoolean()) {
                        state = 3; // ��� �����
                    } else {
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // ��� �����
                    state = 2; // ����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // ����
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
      * �������� ����� �� indFrom � indTo.
      */
    private final class Copy extends BaseAutomata implements Automata {
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
        public Copy() {
            super( 
                "Copy", 
                0, // ����� ���������� ��������� 
                5, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������� ���������� indTo (�������)", 
                    "������ �����", 
                    "����", 
                    "��� �����", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    CALL_AUTO_LEVEL, // ������� ���������� indTo (�������) 
                    -1, // ������ ����� 
                    -1, // ���� 
                    -1, // ��� ����� 
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
                    state = 1; // ������� ���������� indTo (�������)
                    break;
                }
                case 1: { // ������� ���������� indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // ������ �����
                    }
                    break;
                }
                case 2: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 3; // ����
                    break;
                }
                case 3: { // ����
                    if (d.i < d.indLength) {
                        state = 4; // ��� �����
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 4: { // ��� �����
                    stack.pushBoolean(true); 
                    state = 3; // ����
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������� ���������� indTo (�������)
                    if (child == null) {
                        child = new Clear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // ������ �����
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeArray(d.storeL, d.indTo);
                    d.storeL[d.indTo] = d.indLength;
                    break;
                }
                case 3: { // ����
                    break;
                }
                case 4: { // ��� �����
                    startSection();
                     storeArray(d.store[d.indTo], d.i);
                     d.store[d.indTo][d.i] = d.store[d.indFrom][d.i + d.indFirst]; d.i += 1;
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
                case 1: { // ������� ���������� indTo (�������)
                    if (child == null) {
                        child = new Clear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // ������ �����
                    restoreSection();
                    break;
                }
                case 3: { // ����
                    break;
                }
                case 4: { // ��� �����
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������� ���������� indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // ������ �����
                    state = 1; // ������� ���������� indTo (�������)
                    break;
                }
                case 3: { // ����
                    if (stack.popBoolean()) {
                        state = 4; // ��� �����
                    } else {
                        state = 2; // ������ �����
                    }
                    break;
                }
                case 4: { // ��� �����
                    state = 3; // ����
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
                case 1: { // ������� ���������� indTo (�������)
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
                case 1: { // ������� ���������� indTo (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * ������ ���������� � �������� �� ������.
      */
    private final class FastSum extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 25;

        /**
          * �����������.
          */
        public FastSum() {
            super( 
                "FastSum", 
                0, // ����� ���������� ��������� 
                25, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "������� ���������� indTo (�������)", 
                    "����", 
                    "�����1", 
                    "�����1 (���������)", 
                    "����������", 
                    "�����2", 
                    "�����2 (���������)", 
                    "����������", 
                    "�����3", 
                    "�����3 (���������)", 
                    "����������", 
                    "�����4", 
                    "�����4 (���������)", 
                    "����������", 
                    "���������", 
                    "�������� ������", 
                    "�������� ������ (���������)", 
                    "������ ������", 
                    "���� ������ 9", 
                    "���������", 
                    "���� ������ 0", 
                    "�����������", 
                    "���������� ������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    CALL_AUTO_LEVEL, // ������� ���������� indTo (�������) 
                    -1, // ���� 
                    -1, // �����1 
                    -1, // �����1 (���������) 
                    -1, // ���������� 
                    -1, // �����2 
                    -1, // �����2 (���������) 
                    -1, // ���������� 
                    -1, // �����3 
                    -1, // �����3 (���������) 
                    -1, // ���������� 
                    -1, // �����4 
                    -1, // �����4 (���������) 
                    -1, // ���������� 
                    -1, // ��������� 
                    -1, // �������� ������ 
                    -1, // �������� ������ (���������) 
                    -1, // ������ ������ 
                    -1, // ���� ������ 9 
                    -1, // ��������� 
                    -1, // ���� ������ 0 
                    -1, // ����������� 
                    -1, // ���������� ������ 
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
                    state = 1; // ������ �����
                    break;
                }
                case 1: { // ������ �����
                    state = 2; // ������� ���������� indTo (�������)
                    break;
                }
                case 2: { // ������� ���������� indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 3; // ����
                    }
                    break;
                }
                case 3: { // ����
                    if (d.i < d.store[0].length) {
                        state = 4; // �����1
                    } else {
                        state = 24; // ���������� ������
                    }
                    break;
                }
                case 4: { // �����1
                    if ((d.i - 2 * d.k) >= 0) {
                        state = 6; // ����������
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // �����1 (���������)
                    }
                    break;
                }
                case 5: { // �����1 (���������)
                    state = 7; // �����2
                    break;
                }
                case 6: { // ����������
                    stack.pushBoolean(true); 
                    state = 5; // �����1 (���������)
                    break;
                }
                case 7: { // �����2
                    if ((d.i - d.k) >= 0) {
                        state = 9; // ����������
                    } else {
                        stack.pushBoolean(false); 
                        state = 8; // �����2 (���������)
                    }
                    break;
                }
                case 8: { // �����2 (���������)
                    state = 10; // �����3
                    break;
                }
                case 9: { // ����������
                    stack.pushBoolean(true); 
                    state = 8; // �����2 (���������)
                    break;
                }
                case 10: { // �����3
                    if ((d.i - d.k) >= 0) {
                        state = 12; // ����������
                    } else {
                        stack.pushBoolean(false); 
                        state = 11; // �����3 (���������)
                    }
                    break;
                }
                case 11: { // �����3 (���������)
                    state = 13; // �����4
                    break;
                }
                case 12: { // ����������
                    stack.pushBoolean(true); 
                    state = 11; // �����3 (���������)
                    break;
                }
                case 13: { // �����4
                    if ((d.i - d.k) >= 0) {
                        state = 15; // ����������
                    } else {
                        stack.pushBoolean(false); 
                        state = 14; // �����4 (���������)
                    }
                    break;
                }
                case 14: { // �����4 (���������)
                    state = 16; // ���������
                    break;
                }
                case 15: { // ����������
                    stack.pushBoolean(true); 
                    state = 14; // �����4 (���������)
                    break;
                }
                case 16: { // ���������
                    state = 17; // �������� ������
                    break;
                }
                case 17: { // �������� ������
                    if (d.store[d.indTo][d.i - 1] != 0) {
                        state = 19; // ������ ������
                    } else {
                        stack.pushBoolean(false); 
                        state = 18; // �������� ������ (���������)
                    }
                    break;
                }
                case 18: { // �������� ������ (���������)
                    stack.pushBoolean(false); 
                    state = 20; // ���� ������ 9
                    break;
                }
                case 19: { // ������ ������
                    stack.pushBoolean(true); 
                    state = 18; // �������� ������ (���������)
                    break;
                }
                case 20: { // ���� ������ 9
                    if (d.store[d.indTo][d.i - 1] > 9) {
                        state = 21; // ���������
                    } else {
                        stack.pushBoolean(false); 
                        state = 22; // ���� ������ 0
                    }
                    break;
                }
                case 21: { // ���������
                    stack.pushBoolean(true); 
                    state = 20; // ���� ������ 9
                    break;
                }
                case 22: { // ���� ������ 0
                    if (d.store[d.indTo][d.i - 1] < 0) {
                        state = 23; // �����������
                    } else {
                        stack.pushBoolean(true); 
                        state = 3; // ����
                    }
                    break;
                }
                case 23: { // �����������
                    stack.pushBoolean(true); 
                    state = 22; // ���� ������ 0
                    break;
                }
                case 24: { // ���������� ������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "FastSum_leng");
                    d.FastSum_leng = 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 2;
                    break;
                }
                case 2: { // ������� ���������� indTo (�������)
                    if (child == null) {
                        child = new Clear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 3: { // ����
                    break;
                }
                case 4: { // �����1
                    break;
                }
                case 5: { // �����1 (���������)
                    break;
                }
                case 6: { // ����������
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.storeInd + 3][d.i - 2 * d.k];
                    break;
                }
                case 7: { // �����2
                    break;
                }
                case 8: { // �����2 (���������)
                    break;
                }
                case 9: { // ����������
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] - d.store[d.storeInd + 3][d.i - d.k];
                    break;
                }
                case 10: { // �����3
                    break;
                }
                case 11: { // �����3 (���������)
                    break;
                }
                case 12: { // ����������
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.storeInd + 6][d.i - d.k];
                    break;
                }
                case 13: { // �����4
                    break;
                }
                case 14: { // �����4 (���������)
                    break;
                }
                case 15: { // ����������
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] - d.store[d.storeInd + 7][d.i - d.k];
                    break;
                }
                case 16: { // ���������
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.storeInd + 7][d.i];
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 17: { // �������� ������
                    break;
                }
                case 18: { // �������� ������ (���������)
                    break;
                }
                case 19: { // ������ ������
                    startSection();
                    storeField(d, "FastSum_leng");
                    d.FastSum_leng = d.i;
                    break;
                }
                case 20: { // ���� ������ 9
                    break;
                }
                case 21: { // ���������
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + 1;
                    storeArray(d.store[d.indTo], d.i - 1);
                    d.store[d.indTo][d.i - 1] = d.store[d.indTo][d.i - 1] - 10;
                    break;
                }
                case 22: { // ���� ������ 0
                    break;
                }
                case 23: { // �����������
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] - 1;
                    storeArray(d.store[d.indTo], d.i - 1);
                    d.store[d.indTo][d.i - 1] = d.store[d.indTo][d.i - 1] + 10;
                    break;
                }
                case 24: { // ���������� ������
                    startSection();
                    storeArray(d.storeL, d.indTo);
                    d.storeL[d.indTo] = d.FastSum_leng;
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
                case 1: { // ������ �����
                    restoreSection();
                    break;
                }
                case 2: { // ������� ���������� indTo (�������)
                    if (child == null) {
                        child = new Clear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 3: { // ����
                    break;
                }
                case 4: { // �����1
                    break;
                }
                case 5: { // �����1 (���������)
                    break;
                }
                case 6: { // ����������
                    restoreSection();
                    break;
                }
                case 7: { // �����2
                    break;
                }
                case 8: { // �����2 (���������)
                    break;
                }
                case 9: { // ����������
                    restoreSection();
                    break;
                }
                case 10: { // �����3
                    break;
                }
                case 11: { // �����3 (���������)
                    break;
                }
                case 12: { // ����������
                    restoreSection();
                    break;
                }
                case 13: { // �����4
                    break;
                }
                case 14: { // �����4 (���������)
                    break;
                }
                case 15: { // ����������
                    restoreSection();
                    break;
                }
                case 16: { // ���������
                    restoreSection();
                    break;
                }
                case 17: { // �������� ������
                    break;
                }
                case 18: { // �������� ������ (���������)
                    break;
                }
                case 19: { // ������ ������
                    restoreSection();
                    break;
                }
                case 20: { // ���� ������ 9
                    break;
                }
                case 21: { // ���������
                    restoreSection();
                    break;
                }
                case 22: { // ���� ������ 0
                    break;
                }
                case 23: { // �����������
                    restoreSection();
                    break;
                }
                case 24: { // ���������� ������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������� ���������� indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // ����
                    if (stack.popBoolean()) {
                        state = 22; // ���� ������ 0
                    } else {
                        state = 2; // ������� ���������� indTo (�������)
                    }
                    break;
                }
                case 4: { // �����1
                    state = 3; // ����
                    break;
                }
                case 5: { // �����1 (���������)
                    if (stack.popBoolean()) {
                        state = 6; // ����������
                    } else {
                        state = 4; // �����1
                    }
                    break;
                }
                case 6: { // ����������
                    state = 4; // �����1
                    break;
                }
                case 7: { // �����2
                    state = 5; // �����1 (���������)
                    break;
                }
                case 8: { // �����2 (���������)
                    if (stack.popBoolean()) {
                        state = 9; // ����������
                    } else {
                        state = 7; // �����2
                    }
                    break;
                }
                case 9: { // ����������
                    state = 7; // �����2
                    break;
                }
                case 10: { // �����3
                    state = 8; // �����2 (���������)
                    break;
                }
                case 11: { // �����3 (���������)
                    if (stack.popBoolean()) {
                        state = 12; // ����������
                    } else {
                        state = 10; // �����3
                    }
                    break;
                }
                case 12: { // ����������
                    state = 10; // �����3
                    break;
                }
                case 13: { // �����4
                    state = 11; // �����3 (���������)
                    break;
                }
                case 14: { // �����4 (���������)
                    if (stack.popBoolean()) {
                        state = 15; // ����������
                    } else {
                        state = 13; // �����4
                    }
                    break;
                }
                case 15: { // ����������
                    state = 13; // �����4
                    break;
                }
                case 16: { // ���������
                    state = 14; // �����4 (���������)
                    break;
                }
                case 17: { // �������� ������
                    state = 16; // ���������
                    break;
                }
                case 18: { // �������� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 19; // ������ ������
                    } else {
                        state = 17; // �������� ������
                    }
                    break;
                }
                case 19: { // ������ ������
                    state = 17; // �������� ������
                    break;
                }
                case 20: { // ���� ������ 9
                    if (stack.popBoolean()) {
                        state = 21; // ���������
                    } else {
                        state = 18; // �������� ������ (���������)
                    }
                    break;
                }
                case 21: { // ���������
                    state = 20; // ���� ������ 9
                    break;
                }
                case 22: { // ���� ������ 0
                    if (stack.popBoolean()) {
                        state = 23; // �����������
                    } else {
                        state = 20; // ���� ������ 9
                    }
                    break;
                }
                case 23: { // �����������
                    state = 22; // ���� ������ 0
                    break;
                }
                case 24: { // ���������� ������
                    state = 3; // ����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 24; // ���������� ������
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
                case 2: { // ������� ���������� indTo (�������)
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
                case 2: { // ������� ���������� indTo (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * ������ ���������� 2 �����.
      */
    private final class KarSum extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 12;

        /**
          * �����������.
          */
        public KarSum() {
            super( 
                "KarSum", 
                0, // ����� ���������� ��������� 
                12, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "������� ���������� indTo (�������)", 
                    "����", 
                    "��� �����", 
                    "�������� ������", 
                    "�������� ������ (���������)", 
                    "������ ������", 
                    "���� ������ 9", 
                    "���� ������ 9 (���������)", 
                    "���������", 
                    "���������� ������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    CALL_AUTO_LEVEL, // ������� ���������� indTo (�������) 
                    -1, // ���� 
                    -1, // ��� ����� 
                    -1, // �������� ������ 
                    -1, // �������� ������ (���������) 
                    -1, // ������ ������ 
                    -1, // ���� ������ 9 
                    -1, // ���� ������ 9 (���������) 
                    -1, // ��������� 
                    -1, // ���������� ������ 
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
                    state = 1; // ������ �����
                    break;
                }
                case 1: { // ������ �����
                    state = 2; // ������� ���������� indTo (�������)
                    break;
                }
                case 2: { // ������� ���������� indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 3; // ����
                    }
                    break;
                }
                case 3: { // ����
                    if (d.i < d.store[0].length) {
                        state = 4; // ��� �����
                    } else {
                        state = 11; // ���������� ������
                    }
                    break;
                }
                case 4: { // ��� �����
                    state = 5; // �������� ������
                    break;
                }
                case 5: { // �������� ������
                    if (d.store[d.indTo][d.i - 1] != 0) {
                        state = 7; // ������ ������
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // �������� ������ (���������)
                    }
                    break;
                }
                case 6: { // �������� ������ (���������)
                    state = 8; // ���� ������ 9
                    break;
                }
                case 7: { // ������ ������
                    stack.pushBoolean(true); 
                    state = 6; // �������� ������ (���������)
                    break;
                }
                case 8: { // ���� ������ 9
                    if (d.store[d.indTo][d.i - 1] > 9) {
                        state = 10; // ���������
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // ���� ������ 9 (���������)
                    }
                    break;
                }
                case 9: { // ���� ������ 9 (���������)
                    stack.pushBoolean(true); 
                    state = 3; // ����
                    break;
                }
                case 10: { // ���������
                    stack.pushBoolean(true); 
                    state = 9; // ���� ������ 9 (���������)
                    break;
                }
                case 11: { // ���������� ������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "KarSum_leng");
                    d.KarSum_leng = 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 10;
                    break;
                }
                case 2: { // ������� ���������� indTo (�������)
                    if (child == null) {
                        child = new Clear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 3: { // ����
                    break;
                }
                case 4: { // ��� �����
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.storeInd + 8][d.i] + d.store[d.storeInd + 9][d.i];
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 5: { // �������� ������
                    break;
                }
                case 6: { // �������� ������ (���������)
                    break;
                }
                case 7: { // ������ ������
                    startSection();
                    storeField(d, "KarSum_leng");
                    d.KarSum_leng = d.i;
                    break;
                }
                case 8: { // ���� ������ 9
                    break;
                }
                case 9: { // ���� ������ 9 (���������)
                    break;
                }
                case 10: { // ���������
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.indTo][d.i - 1] / 10;
                    storeArray(d.store[d.indTo], d.i - 1);
                    d.store[d.indTo][d.i - 1] = d.store[d.indTo][d.i - 1] % 10;
                    break;
                }
                case 11: { // ���������� ������
                    startSection();
                    storeArray(d.storeL, d.indTo);
                    d.storeL[d.indTo] = d.KarSum_leng;
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
                case 1: { // ������ �����
                    restoreSection();
                    break;
                }
                case 2: { // ������� ���������� indTo (�������)
                    if (child == null) {
                        child = new Clear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 3: { // ����
                    break;
                }
                case 4: { // ��� �����
                    restoreSection();
                    break;
                }
                case 5: { // �������� ������
                    break;
                }
                case 6: { // �������� ������ (���������)
                    break;
                }
                case 7: { // ������ ������
                    restoreSection();
                    break;
                }
                case 8: { // ���� ������ 9
                    break;
                }
                case 9: { // ���� ������ 9 (���������)
                    break;
                }
                case 10: { // ���������
                    restoreSection();
                    break;
                }
                case 11: { // ���������� ������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������ �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������� ���������� indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // ����
                    if (stack.popBoolean()) {
                        state = 9; // ���� ������ 9 (���������)
                    } else {
                        state = 2; // ������� ���������� indTo (�������)
                    }
                    break;
                }
                case 4: { // ��� �����
                    state = 3; // ����
                    break;
                }
                case 5: { // �������� ������
                    state = 4; // ��� �����
                    break;
                }
                case 6: { // �������� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 7; // ������ ������
                    } else {
                        state = 5; // �������� ������
                    }
                    break;
                }
                case 7: { // ������ ������
                    state = 5; // �������� ������
                    break;
                }
                case 8: { // ���� ������ 9
                    state = 6; // �������� ������ (���������)
                    break;
                }
                case 9: { // ���� ������ 9 (���������)
                    if (stack.popBoolean()) {
                        state = 10; // ���������
                    } else {
                        state = 8; // ���� ������ 9
                    }
                    break;
                }
                case 10: { // ���������
                    state = 8; // ���� ������ 9
                    break;
                }
                case 11: { // ���������� ������
                    state = 3; // ����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 11; // ���������� ������
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
                case 2: { // ������� ���������� indTo (�������)
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
                case 2: { // ������� ���������� indTo (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * �������� ���������.
      */
    private final class Karpower extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 59;

        /**
          * �����������.
          */
        public Karpower() {
            super( 
                "Karpower", 
                0, // ����� ���������� ��������� 
                59, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������� ���������� (storeInd + 10)..(storeInd + 15) (�������)", 
                    "������ ���������", 
                    "����������������", 
                    "���� ������ �������� ������", 
                    "���� ������ �������� ������ (���������)", 
                    "����������������", 
                    "����������������", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "�������� ��������� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "����������������", 
                    "����������������", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� D", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "�������� ��������� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "����������������", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������ ���������� 2 ����� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� D", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������ ���������� 2 ����� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "����������������", 
                    "������� � + �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� � + D", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "�������� ��������� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "����������������", 
                    "����������������", 
                    "����������������", 
                    "��������� ����������", 
                    "������ ���������� � �������� �� ������ (�������)", 
                    "����������������", 
                    "�������� �� ��������", 
                    "���� ������ 9", 
                    "���� ������ 9 (���������)", 
                    "���������", 
                    "����������������", 
                    "����������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    CALL_AUTO_LEVEL, // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������) 
                    -1, // ������ ��������� 
                    0, // ���������������� 
                    -1, // ���� ������ �������� ������ 
                    -1, // ���� ������ �������� ������ (���������) 
                    0, // ���������������� 
                    0, // ���������������� 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // �������� ��������� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    0, // ���������������� 
                    0, // ���������������� 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� D 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // �������� ��������� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    0, // ���������������� 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // ������ ���������� 2 ����� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� D 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // ������ ���������� 2 ����� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    0, // ���������������� 
                    -1, // ������� � + � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� � + D 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // �������� ��������� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    0, // ���������������� 
                    0, // ���������������� 
                    0, // ���������������� 
                    -1, // ��������� ���������� 
                    CALL_AUTO_LEVEL, // ������ ���������� � �������� �� ������ (�������) 
                    0, // ���������������� 
                    -1, // �������� �� �������� 
                    -1, // ���� ������ 9 
                    -1, // ���� ������ 9 (���������) 
                    -1, // ��������� 
                    0, // ���������������� 
                    -1, // ���������� 
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
                    state = 1; // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    break;
                }
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // ������ ���������
                    }
                    break;
                }
                case 2: { // ������ ���������
                    state = 3; // ����������������
                    break;
                }
                case 3: { // ����������������
                    state = 4; // ���� ������ �������� ������
                    break;
                }
                case 4: { // ���� ������ �������� ������
                    if (d.k != 0) {
                        state = 6; // ����������������
                    } else {
                        state = 53; // �������� �� ��������
                    }
                    break;
                }
                case 5: { // ���� ������ �������� ������ (���������)
                    state = 58; // ����������
                    break;
                }
                case 6: { // ����������������
                    state = 7; // ����������������
                    break;
                }
                case 7: { // ����������������
                    state = 8; // ������� �
                    break;
                }
                case 8: { // ������� �
                    state = 9; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // ������� �
                    }
                    break;
                }
                case 10: { // ������� �
                    state = 11; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 12; // �������� ��������� (�������)
                    }
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 13; // �������� ���������
                    }
                    break;
                }
                case 13: { // �������� ���������
                    state = 14; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 15; // ����������������
                    }
                    break;
                }
                case 15: { // ����������������
                    state = 16; // ����������������
                    break;
                }
                case 16: { // ����������������
                    state = 17; // ������� �
                    break;
                }
                case 17: { // ������� �
                    state = 18; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 19; // ������� D
                    }
                    break;
                }
                case 19: { // ������� D
                    state = 20; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 21; // �������� ��������� (�������)
                    }
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 22; // �������� ���������
                    }
                    break;
                }
                case 22: { // �������� ���������
                    state = 23; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 24; // ����������������
                    }
                    break;
                }
                case 24: { // ����������������
                    state = 25; // ������� �
                    break;
                }
                case 25: { // ������� �
                    state = 26; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 27; // ������� �
                    }
                    break;
                }
                case 27: { // ������� �
                    state = 28; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 29; // ������ ���������� 2 ����� (�������)
                    }
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 30; // �������� ���������
                    }
                    break;
                }
                case 30: { // �������� ���������
                    state = 31; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 32; // ������� �
                    }
                    break;
                }
                case 32: { // ������� �
                    state = 33; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 34; // ������� D
                    }
                    break;
                }
                case 34: { // ������� D
                    state = 35; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 36; // ������ ���������� 2 ����� (�������)
                    }
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 37; // �������� ���������
                    }
                    break;
                }
                case 37: { // �������� ���������
                    state = 38; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 39; // ����������������
                    }
                    break;
                }
                case 39: { // ����������������
                    state = 40; // ������� � + �
                    break;
                }
                case 40: { // ������� � + �
                    state = 41; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 42; // ������� � + D
                    }
                    break;
                }
                case 42: { // ������� � + D
                    state = 43; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 44; // �������� ��������� (�������)
                    }
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 45; // �������� ���������
                    }
                    break;
                }
                case 45: { // �������� ���������
                    state = 46; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 47; // ����������������
                    }
                    break;
                }
                case 47: { // ����������������
                    state = 48; // ����������������
                    break;
                }
                case 48: { // ����������������
                    state = 49; // ����������������
                    break;
                }
                case 49: { // ����������������
                    state = 50; // ��������� ����������
                    break;
                }
                case 50: { // ��������� ����������
                    state = 51; // ������ ���������� � �������� �� ������ (�������)
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 52; // ����������������
                    }
                    break;
                }
                case 52: { // ����������������
                    stack.pushBoolean(true); 
                    state = 5; // ���� ������ �������� ������ (���������)
                    break;
                }
                case 53: { // �������� �� ��������
                    state = 54; // ���� ������ 9
                    break;
                }
                case 54: { // ���� ������ 9
                    if (d.store[d.storeInd + 2][0] > 9) {
                        state = 56; // ���������
                    } else {
                        stack.pushBoolean(false); 
                        state = 55; // ���� ������ 9 (���������)
                    }
                    break;
                }
                case 55: { // ���� ������ 9 (���������)
                    state = 57; // ����������������
                    break;
                }
                case 56: { // ���������
                    stack.pushBoolean(true); 
                    state = 55; // ���� ������ 9 (���������)
                    break;
                }
                case 57: { // ����������������
                    stack.pushBoolean(false); 
                    state = 5; // ���� ������ �������� ������ (���������)
                    break;
                }
                case 58: { // ����������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    if (child == null) {
                        child = new SuperClear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // ������ ���������
                    startSection();
                    storeField(d, "storeInd");
                    d.storeInd = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "t");
                    d.t = Math.max(d.storeL[d.storeInd], d.storeL[d.storeInd + 1]);
                    storeArray(d.storeL, d.storeInd);
                    d.storeL[d.storeInd] = d.t;
                    storeArray(d.storeL, d.storeInd + 1);
                    d.storeL[d.storeInd + 1] = d.t;
                    break;
                }
                case 3: { // ����������������
                    startSection();
                    break;
                }
                case 4: { // ���� ������ �������� ������
                    break;
                }
                case 5: { // ���� ������ �������� ������ (���������)
                    break;
                }
                case 6: { // ����������������
                    startSection();
                    break;
                }
                case 7: { // ����������������
                    startSection();
                    break;
                }
                case 8: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd] - d.k;
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 1] - d.k;
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 13: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 3;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 15: { // ����������������
                    startSection();
                    break;
                }
                case 16: { // ����������������
                    startSection();
                    break;
                }
                case 17: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 19: { // ������� D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 22: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 7;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 24: { // ����������������
                    startSection();
                    break;
                }
                case 25: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd] - d.k;
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 27: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 30: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 4;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 32: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 1] - d.k;
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 34: { // ������� D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 37: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 5;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 39: { // ����������������
                    startSection();
                    break;
                }
                case 40: { // ������� � + �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd+ 4;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 4];
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 42: { // ������� � + D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 5;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 5];
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 45: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 6;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 47: { // ����������������
                    startSection();
                    break;
                }
                case 48: { // ����������������
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 49: { // ����������������
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 50: { // ��������� ����������
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    if (child == null) {
                        child = new FastSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 52: { // ����������������
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 53: { // �������� �� ��������
                    startSection();
                    storeArray(d.storeL, d.storeInd + 2);
                    d.storeL[d.storeInd + 2] = 1;
                    storeArray(d.store[d.storeInd + 2], 0);
                    d.store[d.storeInd + 2][0] = d.store[d.storeInd][0] * d.store[d.storeInd + 1][0];
                    break;
                }
                case 54: { // ���� ������ 9
                    break;
                }
                case 55: { // ���� ������ 9 (���������)
                    break;
                }
                case 56: { // ���������
                    startSection();
                    storeArray(d.store[d.storeInd + 2], 1);
                    d.store[d.storeInd + 2][1] = d.store[d.storeInd + 2][0] / 10;
                    storeArray(d.store[d.storeInd + 2], 0);
                    d.store[d.storeInd + 2][0] = d.store[d.storeInd + 2][0] % 10;
                    storeArray(d.storeL, d.storeInd + 2);
                    d.storeL[d.storeInd + 2] = 2;
                    break;
                }
                case 57: { // ����������������
                    startSection();
                    break;
                }
                case 58: { // ����������
                    startSection();
                    storeField(d, "storeInd");
                    d.storeInd = d.storeInd - 8;
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
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    if (child == null) {
                        child = new SuperClear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // ������ ���������
                    restoreSection();
                    break;
                }
                case 3: { // ����������������
                    restoreSection();
                    break;
                }
                case 4: { // ���� ������ �������� ������
                    break;
                }
                case 5: { // ���� ������ �������� ������ (���������)
                    break;
                }
                case 6: { // ����������������
                    restoreSection();
                    break;
                }
                case 7: { // ����������������
                    restoreSection();
                    break;
                }
                case 8: { // ������� �
                    restoreSection();
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // ������� �
                    restoreSection();
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 13: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 15: { // ����������������
                    restoreSection();
                    break;
                }
                case 16: { // ����������������
                    restoreSection();
                    break;
                }
                case 17: { // ������� �
                    restoreSection();
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 19: { // ������� D
                    restoreSection();
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 22: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 24: { // ����������������
                    restoreSection();
                    break;
                }
                case 25: { // ������� �
                    restoreSection();
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 27: { // ������� �
                    restoreSection();
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 30: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 32: { // ������� �
                    restoreSection();
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 34: { // ������� D
                    restoreSection();
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 37: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 39: { // ����������������
                    restoreSection();
                    break;
                }
                case 40: { // ������� � + �
                    restoreSection();
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 42: { // ������� � + D
                    restoreSection();
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 45: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 47: { // ����������������
                    restoreSection();
                    break;
                }
                case 48: { // ����������������
                    restoreSection();
                    break;
                }
                case 49: { // ����������������
                    restoreSection();
                    break;
                }
                case 50: { // ��������� ����������
                    restoreSection();
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    if (child == null) {
                        child = new FastSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 52: { // ����������������
                    restoreSection();
                    break;
                }
                case 53: { // �������� �� ��������
                    restoreSection();
                    break;
                }
                case 54: { // ���� ������ 9
                    break;
                }
                case 55: { // ���� ������ 9 (���������)
                    break;
                }
                case 56: { // ���������
                    restoreSection();
                    break;
                }
                case 57: { // ����������������
                    restoreSection();
                    break;
                }
                case 58: { // ����������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // ������ ���������
                    state = 1; // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    break;
                }
                case 3: { // ����������������
                    state = 2; // ������ ���������
                    break;
                }
                case 4: { // ���� ������ �������� ������
                    state = 3; // ����������������
                    break;
                }
                case 5: { // ���� ������ �������� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 52; // ����������������
                    } else {
                        state = 57; // ����������������
                    }
                    break;
                }
                case 6: { // ����������������
                    state = 4; // ���� ������ �������� ������
                    break;
                }
                case 7: { // ����������������
                    state = 6; // ����������������
                    break;
                }
                case 8: { // ������� �
                    state = 7; // ����������������
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // ������� �
                    }
                    break;
                }
                case 10: { // ������� �
                    state = 9; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 10; // ������� �
                    }
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 11; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 13: { // �������� ���������
                    state = 12; // �������� ��������� (�������)
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 13; // �������� ���������
                    }
                    break;
                }
                case 15: { // ����������������
                    state = 14; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 16: { // ����������������
                    state = 15; // ����������������
                    break;
                }
                case 17: { // ������� �
                    state = 16; // ����������������
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 17; // ������� �
                    }
                    break;
                }
                case 19: { // ������� D
                    state = 18; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // ������� D
                    }
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 20; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 22: { // �������� ���������
                    state = 21; // �������� ��������� (�������)
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 22; // �������� ���������
                    }
                    break;
                }
                case 24: { // ����������������
                    state = 23; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 25: { // ������� �
                    state = 24; // ����������������
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 25; // ������� �
                    }
                    break;
                }
                case 27: { // ������� �
                    state = 26; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 27; // ������� �
                    }
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 28; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 30: { // �������� ���������
                    state = 29; // ������ ���������� 2 ����� (�������)
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 30; // �������� ���������
                    }
                    break;
                }
                case 32: { // ������� �
                    state = 31; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 32; // ������� �
                    }
                    break;
                }
                case 34: { // ������� D
                    state = 33; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 34; // ������� D
                    }
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 35; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 37: { // �������� ���������
                    state = 36; // ������ ���������� 2 ����� (�������)
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 37; // �������� ���������
                    }
                    break;
                }
                case 39: { // ����������������
                    state = 38; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 40: { // ������� � + �
                    state = 39; // ����������������
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 40; // ������� � + �
                    }
                    break;
                }
                case 42: { // ������� � + D
                    state = 41; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 42; // ������� � + D
                    }
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 43; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 45: { // �������� ���������
                    state = 44; // �������� ��������� (�������)
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 45; // �������� ���������
                    }
                    break;
                }
                case 47: { // ����������������
                    state = 46; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 48: { // ����������������
                    state = 47; // ����������������
                    break;
                }
                case 49: { // ����������������
                    state = 48; // ����������������
                    break;
                }
                case 50: { // ��������� ����������
                    state = 49; // ����������������
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 50; // ��������� ����������
                    }
                    break;
                }
                case 52: { // ����������������
                    state = 51; // ������ ���������� � �������� �� ������ (�������)
                    break;
                }
                case 53: { // �������� �� ��������
                    state = 4; // ���� ������ �������� ������
                    break;
                }
                case 54: { // ���� ������ 9
                    state = 53; // �������� �� ��������
                    break;
                }
                case 55: { // ���� ������ 9 (���������)
                    if (stack.popBoolean()) {
                        state = 56; // ���������
                    } else {
                        state = 54; // ���� ������ 9
                    }
                    break;
                }
                case 56: { // ���������
                    state = 54; // ���� ������ 9
                    break;
                }
                case 57: { // ����������������
                    state = 55; // ���� ������ 9 (���������)
                    break;
                }
                case 58: { // ����������
                    state = 5; // ���� ������ �������� ������ (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 58; // ����������
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
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 3: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.KarEnterShow"); 
                    break;
                }
                case 6: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.KarSplitShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8), new Integer(d.k)}; 
                    break;
                }
                case 7: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.ACShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 15: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.ResultAC"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 16: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.BDShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 24: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.ResultBD"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 39: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.ApBCpDShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 47: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.ResultApBCpD"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 48: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.takeIntegerForSum"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 49: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.ReSumShow"); 
                    args = new Object[]{new Integer(d.k)}; 
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 52: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.SumShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 57: { // ����������������
                    comment = LongInteger.this.getComment("Karpower.showSimplepower"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
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
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    child.drawState(); 
                    break;
                }
                case 3: { // ����������������
                    d.visualizer.showTempPower(0);
                    break;
                }
                case 6: { // ����������������
                    d.visualizer.showTempPower(1);
                    break;
                }
                case 7: { // ����������������
                    d.visualizer.showTempPower(2);
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    child.drawState(); 
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 15: { // ����������������
                    d.visualizer.showTempPower(3);
                    break;
                }
                case 16: { // ����������������
                    d.visualizer.showTempPower(4);
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    child.drawState(); 
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 24: { // ����������������
                    d.visualizer.showTempPower(5);
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    child.drawState(); 
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    child.drawState(); 
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 39: { // ����������������
                    d.visualizer.showTempPower(6);
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    child.drawState(); 
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 47: { // ����������������
                    d.visualizer.showTempPower(7);
                    break;
                }
                case 48: { // ����������������
                    d.visualizer.showTempPower(8);
                    break;
                }
                case 49: { // ����������������
                    d.visualizer.showSum(0);
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 52: { // ����������������
                    d.visualizer.showSum(1);
                    break;
                }
                case 57: { // ����������������
                    d.visualizer.showSum(2);
                    break;
                }
            }
        }
    }

    /**
      * �������� ���������.
      */
    private final class KarpowerMain extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 59;

        /**
          * �����������.
          */
        public KarpowerMain() {
            super( 
                "KarpowerMain", 
                0, // ����� ���������� ��������� 
                59, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������� ���������� (storeInd + 10)..(storeInd + 15) (�������)", 
                    "������ ���������", 
                    "����������������", 
                    "���� ������ �������� ������", 
                    "���� ������ �������� ������ (���������)", 
                    "����������������", 
                    "����������������", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "�������� ��������� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "����������������", 
                    "����������������", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� D", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "�������� ��������� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "����������������", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������ ���������� 2 ����� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� D", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������ ���������� 2 ����� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "����������������", 
                    "������� � + �", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "������� � + D", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "�������� ��������� (�������)", 
                    "�������� ���������", 
                    "�������� ����� �� indFrom � indTo (�������)", 
                    "����������������", 
                    "����������������", 
                    "����������������", 
                    "��������� ����������", 
                    "������ ���������� � �������� �� ������ (�������)", 
                    "����������������", 
                    "�������� �� ��������", 
                    "���� ������ 9", 
                    "���� ������ 9 (���������)", 
                    "���������", 
                    "����������������", 
                    "����������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    CALL_AUTO_LEVEL, // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������) 
                    -1, // ������ ��������� 
                    1, // ���������������� 
                    -1, // ���� ������ �������� ������ 
                    -1, // ���� ������ �������� ������ (���������) 
                    1, // ���������������� 
                    1, // ���������������� 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // �������� ��������� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    1, // ���������������� 
                    1, // ���������������� 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� D 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // �������� ��������� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    1, // ���������������� 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // ������ ���������� 2 ����� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� D 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // ������ ���������� 2 ����� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    1, // ���������������� 
                    -1, // ������� � + � 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    -1, // ������� � + D 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    CALL_AUTO_LEVEL, // �������� ��������� (�������) 
                    -1, // �������� ��������� 
                    CALL_AUTO_LEVEL, // �������� ����� �� indFrom � indTo (�������) 
                    1, // ���������������� 
                    1, // ���������������� 
                    1, // ���������������� 
                    -1, // ��������� ���������� 
                    CALL_AUTO_LEVEL, // ������ ���������� � �������� �� ������ (�������) 
                    1, // ���������������� 
                    -1, // �������� �� �������� 
                    -1, // ���� ������ 9 
                    -1, // ���� ������ 9 (���������) 
                    -1, // ��������� 
                    1, // ���������������� 
                    -1, // ���������� 
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
                    state = 1; // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    break;
                }
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // ������ ���������
                    }
                    break;
                }
                case 2: { // ������ ���������
                    state = 3; // ����������������
                    break;
                }
                case 3: { // ����������������
                    state = 4; // ���� ������ �������� ������
                    break;
                }
                case 4: { // ���� ������ �������� ������
                    if (d.k != 0) {
                        state = 6; // ����������������
                    } else {
                        state = 53; // �������� �� ��������
                    }
                    break;
                }
                case 5: { // ���� ������ �������� ������ (���������)
                    state = 58; // ����������
                    break;
                }
                case 6: { // ����������������
                    state = 7; // ����������������
                    break;
                }
                case 7: { // ����������������
                    state = 8; // ������� �
                    break;
                }
                case 8: { // ������� �
                    state = 9; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // ������� �
                    }
                    break;
                }
                case 10: { // ������� �
                    state = 11; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 12; // �������� ��������� (�������)
                    }
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 13; // �������� ���������
                    }
                    break;
                }
                case 13: { // �������� ���������
                    state = 14; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 15; // ����������������
                    }
                    break;
                }
                case 15: { // ����������������
                    state = 16; // ����������������
                    break;
                }
                case 16: { // ����������������
                    state = 17; // ������� �
                    break;
                }
                case 17: { // ������� �
                    state = 18; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 19; // ������� D
                    }
                    break;
                }
                case 19: { // ������� D
                    state = 20; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 21; // �������� ��������� (�������)
                    }
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 22; // �������� ���������
                    }
                    break;
                }
                case 22: { // �������� ���������
                    state = 23; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 24; // ����������������
                    }
                    break;
                }
                case 24: { // ����������������
                    state = 25; // ������� �
                    break;
                }
                case 25: { // ������� �
                    state = 26; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 27; // ������� �
                    }
                    break;
                }
                case 27: { // ������� �
                    state = 28; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 29; // ������ ���������� 2 ����� (�������)
                    }
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 30; // �������� ���������
                    }
                    break;
                }
                case 30: { // �������� ���������
                    state = 31; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 32; // ������� �
                    }
                    break;
                }
                case 32: { // ������� �
                    state = 33; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 34; // ������� D
                    }
                    break;
                }
                case 34: { // ������� D
                    state = 35; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 36; // ������ ���������� 2 ����� (�������)
                    }
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 37; // �������� ���������
                    }
                    break;
                }
                case 37: { // �������� ���������
                    state = 38; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 39; // ����������������
                    }
                    break;
                }
                case 39: { // ����������������
                    state = 40; // ������� � + �
                    break;
                }
                case 40: { // ������� � + �
                    state = 41; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 42; // ������� � + D
                    }
                    break;
                }
                case 42: { // ������� � + D
                    state = 43; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 44; // �������� ��������� (�������)
                    }
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 45; // �������� ���������
                    }
                    break;
                }
                case 45: { // �������� ���������
                    state = 46; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 47; // ����������������
                    }
                    break;
                }
                case 47: { // ����������������
                    state = 48; // ����������������
                    break;
                }
                case 48: { // ����������������
                    state = 49; // ����������������
                    break;
                }
                case 49: { // ����������������
                    state = 50; // ��������� ����������
                    break;
                }
                case 50: { // ��������� ����������
                    state = 51; // ������ ���������� � �������� �� ������ (�������)
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 52; // ����������������
                    }
                    break;
                }
                case 52: { // ����������������
                    stack.pushBoolean(true); 
                    state = 5; // ���� ������ �������� ������ (���������)
                    break;
                }
                case 53: { // �������� �� ��������
                    state = 54; // ���� ������ 9
                    break;
                }
                case 54: { // ���� ������ 9
                    if (d.store[d.storeInd + 2][0] > 9) {
                        state = 56; // ���������
                    } else {
                        stack.pushBoolean(false); 
                        state = 55; // ���� ������ 9 (���������)
                    }
                    break;
                }
                case 55: { // ���� ������ 9 (���������)
                    state = 57; // ����������������
                    break;
                }
                case 56: { // ���������
                    stack.pushBoolean(true); 
                    state = 55; // ���� ������ 9 (���������)
                    break;
                }
                case 57: { // ����������������
                    stack.pushBoolean(false); 
                    state = 5; // ���� ������ �������� ������ (���������)
                    break;
                }
                case 58: { // ����������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    if (child == null) {
                        child = new SuperClear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // ������ ���������
                    startSection();
                    storeField(d, "storeInd");
                    d.storeInd = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "t");
                    d.t = Math.max(d.storeL[d.storeInd], d.storeL[d.storeInd + 1]);
                    storeArray(d.storeL, d.storeInd);
                    d.storeL[d.storeInd] = d.t;
                    storeArray(d.storeL, d.storeInd + 1);
                    d.storeL[d.storeInd + 1] = d.t;
                    break;
                }
                case 3: { // ����������������
                    startSection();
                    break;
                }
                case 4: { // ���� ������ �������� ������
                    break;
                }
                case 5: { // ���� ������ �������� ������ (���������)
                    break;
                }
                case 6: { // ����������������
                    startSection();
                    break;
                }
                case 7: { // ����������������
                    startSection();
                    break;
                }
                case 8: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd] - d.k;
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 1] - d.k;
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 13: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 3;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 15: { // ����������������
                    startSection();
                    break;
                }
                case 16: { // ����������������
                    startSection();
                    break;
                }
                case 17: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 19: { // ������� D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 22: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 7;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 24: { // ����������������
                    startSection();
                    break;
                }
                case 25: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd] - d.k;
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 27: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 30: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 4;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 32: { // ������� �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 1] - d.k;
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 34: { // ������� D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 37: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 5;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 39: { // ����������������
                    startSection();
                    break;
                }
                case 40: { // ������� � + �
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd+ 4;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 4];
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 42: { // ������� � + D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 5;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 5];
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 45: { // �������� ���������
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 6;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 47: { // ����������������
                    startSection();
                    break;
                }
                case 48: { // ����������������
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 49: { // ����������������
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 50: { // ��������� ����������
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    if (child == null) {
                        child = new FastSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 52: { // ����������������
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 53: { // �������� �� ��������
                    startSection();
                    storeArray(d.storeL, d.storeInd + 2);
                    d.storeL[d.storeInd + 2] = 1;
                    storeArray(d.store[d.storeInd + 2], 0);
                    d.store[d.storeInd + 2][0] = d.store[d.storeInd][0] * d.store[d.storeInd + 1][0];
                    break;
                }
                case 54: { // ���� ������ 9
                    break;
                }
                case 55: { // ���� ������ 9 (���������)
                    break;
                }
                case 56: { // ���������
                    startSection();
                    storeArray(d.store[d.storeInd + 2], 1);
                    d.store[d.storeInd + 2][1] = d.store[d.storeInd + 2][0] / 10;
                    storeArray(d.store[d.storeInd + 2], 0);
                    d.store[d.storeInd + 2][0] = d.store[d.storeInd + 2][0] % 10;
                    storeArray(d.storeL, d.storeInd + 2);
                    d.storeL[d.storeInd + 2] = 2;
                    break;
                }
                case 57: { // ����������������
                    startSection();
                    break;
                }
                case 58: { // ����������
                    startSection();
                    storeField(d, "storeInd");
                    d.storeInd = d.storeInd - 8;
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
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    if (child == null) {
                        child = new SuperClear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // ������ ���������
                    restoreSection();
                    break;
                }
                case 3: { // ����������������
                    restoreSection();
                    break;
                }
                case 4: { // ���� ������ �������� ������
                    break;
                }
                case 5: { // ���� ������ �������� ������ (���������)
                    break;
                }
                case 6: { // ����������������
                    restoreSection();
                    break;
                }
                case 7: { // ����������������
                    restoreSection();
                    break;
                }
                case 8: { // ������� �
                    restoreSection();
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // ������� �
                    restoreSection();
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 13: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 15: { // ����������������
                    restoreSection();
                    break;
                }
                case 16: { // ����������������
                    restoreSection();
                    break;
                }
                case 17: { // ������� �
                    restoreSection();
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 19: { // ������� D
                    restoreSection();
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 22: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 24: { // ����������������
                    restoreSection();
                    break;
                }
                case 25: { // ������� �
                    restoreSection();
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 27: { // ������� �
                    restoreSection();
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 30: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 32: { // ������� �
                    restoreSection();
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 34: { // ������� D
                    restoreSection();
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 37: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 39: { // ����������������
                    restoreSection();
                    break;
                }
                case 40: { // ������� � + �
                    restoreSection();
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 42: { // ������� � + D
                    restoreSection();
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 45: { // �������� ���������
                    restoreSection();
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 47: { // ����������������
                    restoreSection();
                    break;
                }
                case 48: { // ����������������
                    restoreSection();
                    break;
                }
                case 49: { // ����������������
                    restoreSection();
                    break;
                }
                case 50: { // ��������� ����������
                    restoreSection();
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    if (child == null) {
                        child = new FastSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 52: { // ����������������
                    restoreSection();
                    break;
                }
                case 53: { // �������� �� ��������
                    restoreSection();
                    break;
                }
                case 54: { // ���� ������ 9
                    break;
                }
                case 55: { // ���� ������ 9 (���������)
                    break;
                }
                case 56: { // ���������
                    restoreSection();
                    break;
                }
                case 57: { // ����������������
                    restoreSection();
                    break;
                }
                case 58: { // ����������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // ������ ���������
                    state = 1; // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    break;
                }
                case 3: { // ����������������
                    state = 2; // ������ ���������
                    break;
                }
                case 4: { // ���� ������ �������� ������
                    state = 3; // ����������������
                    break;
                }
                case 5: { // ���� ������ �������� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 52; // ����������������
                    } else {
                        state = 57; // ����������������
                    }
                    break;
                }
                case 6: { // ����������������
                    state = 4; // ���� ������ �������� ������
                    break;
                }
                case 7: { // ����������������
                    state = 6; // ����������������
                    break;
                }
                case 8: { // ������� �
                    state = 7; // ����������������
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // ������� �
                    }
                    break;
                }
                case 10: { // ������� �
                    state = 9; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 10; // ������� �
                    }
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 11; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 13: { // �������� ���������
                    state = 12; // �������� ��������� (�������)
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 13; // �������� ���������
                    }
                    break;
                }
                case 15: { // ����������������
                    state = 14; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 16: { // ����������������
                    state = 15; // ����������������
                    break;
                }
                case 17: { // ������� �
                    state = 16; // ����������������
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 17; // ������� �
                    }
                    break;
                }
                case 19: { // ������� D
                    state = 18; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // ������� D
                    }
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 20; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 22: { // �������� ���������
                    state = 21; // �������� ��������� (�������)
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 22; // �������� ���������
                    }
                    break;
                }
                case 24: { // ����������������
                    state = 23; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 25: { // ������� �
                    state = 24; // ����������������
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 25; // ������� �
                    }
                    break;
                }
                case 27: { // ������� �
                    state = 26; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 27; // ������� �
                    }
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 28; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 30: { // �������� ���������
                    state = 29; // ������ ���������� 2 ����� (�������)
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 30; // �������� ���������
                    }
                    break;
                }
                case 32: { // ������� �
                    state = 31; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 32; // ������� �
                    }
                    break;
                }
                case 34: { // ������� D
                    state = 33; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 34; // ������� D
                    }
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 35; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 37: { // �������� ���������
                    state = 36; // ������ ���������� 2 ����� (�������)
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 37; // �������� ���������
                    }
                    break;
                }
                case 39: { // ����������������
                    state = 38; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 40: { // ������� � + �
                    state = 39; // ����������������
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 40; // ������� � + �
                    }
                    break;
                }
                case 42: { // ������� � + D
                    state = 41; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 42; // ������� � + D
                    }
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 43; // �������� ����� �� indFrom � indTo (�������)
                    }
                    break;
                }
                case 45: { // �������� ���������
                    state = 44; // �������� ��������� (�������)
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 45; // �������� ���������
                    }
                    break;
                }
                case 47: { // ����������������
                    state = 46; // �������� ����� �� indFrom � indTo (�������)
                    break;
                }
                case 48: { // ����������������
                    state = 47; // ����������������
                    break;
                }
                case 49: { // ����������������
                    state = 48; // ����������������
                    break;
                }
                case 50: { // ��������� ����������
                    state = 49; // ����������������
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 50; // ��������� ����������
                    }
                    break;
                }
                case 52: { // ����������������
                    state = 51; // ������ ���������� � �������� �� ������ (�������)
                    break;
                }
                case 53: { // �������� �� ��������
                    state = 4; // ���� ������ �������� ������
                    break;
                }
                case 54: { // ���� ������ 9
                    state = 53; // �������� �� ��������
                    break;
                }
                case 55: { // ���� ������ 9 (���������)
                    if (stack.popBoolean()) {
                        state = 56; // ���������
                    } else {
                        state = 54; // ���� ������ 9
                    }
                    break;
                }
                case 56: { // ���������
                    state = 54; // ���� ������ 9
                    break;
                }
                case 57: { // ����������������
                    state = 55; // ���� ������ 9 (���������)
                    break;
                }
                case 58: { // ����������
                    state = 5; // ���� ������ �������� ������ (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 58; // ����������
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
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 3: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.KarEnterShow"); 
                    break;
                }
                case 6: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.KarSplitShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8), new Integer(d.k)}; 
                    break;
                }
                case 7: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.ACShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 15: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.ResultAC"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 16: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.BDShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 24: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.ResultBD"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 39: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.ApBCpDShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 47: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.ResultApBCpD"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 48: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.takeIntegerForSum"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 49: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.ReSumShow"); 
                    args = new Object[]{new Integer(d.k)}; 
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 52: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.SumShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 57: { // ����������������
                    comment = LongInteger.this.getComment("KarpowerMain.showSimplepower"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
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
                case 1: { // ������� ���������� (storeInd + 10)..(storeInd + 15) (�������)
                    child.drawState(); 
                    break;
                }
                case 3: { // ����������������
                    d.visualizer.showTempPower(0);
                    break;
                }
                case 6: { // ����������������
                    d.visualizer.showTempPower(1);
                    break;
                }
                case 7: { // ����������������
                    d.visualizer.showTempPower(2);
                    break;
                }
                case 9: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 11: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 12: { // �������� ��������� (�������)
                    child.drawState(); 
                    break;
                }
                case 14: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 15: { // ����������������
                    d.visualizer.showTempPower(3);
                    break;
                }
                case 16: { // ����������������
                    d.visualizer.showTempPower(4);
                    break;
                }
                case 18: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 20: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 21: { // �������� ��������� (�������)
                    child.drawState(); 
                    break;
                }
                case 23: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 24: { // ����������������
                    d.visualizer.showTempPower(5);
                    break;
                }
                case 26: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 28: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 29: { // ������ ���������� 2 ����� (�������)
                    child.drawState(); 
                    break;
                }
                case 31: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 33: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 35: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 36: { // ������ ���������� 2 ����� (�������)
                    child.drawState(); 
                    break;
                }
                case 38: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 39: { // ����������������
                    d.visualizer.showTempPower(6);
                    break;
                }
                case 41: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 43: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 44: { // �������� ��������� (�������)
                    child.drawState(); 
                    break;
                }
                case 46: { // �������� ����� �� indFrom � indTo (�������)
                    child.drawState(); 
                    break;
                }
                case 47: { // ����������������
                    d.visualizer.showTempPower(7);
                    break;
                }
                case 48: { // ����������������
                    d.visualizer.showTempPower(8);
                    break;
                }
                case 49: { // ����������������
                    d.visualizer.showSum(0);
                    break;
                }
                case 51: { // ������ ���������� � �������� �� ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 52: { // ����������������
                    d.visualizer.showSum(1);
                    break;
                }
                case 57: { // ����������������
                    d.visualizer.showSum(2);
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
        private final int END_STATE = 26;

        /**
          * �����������.
          */
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                26, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������� �����", 
                    "����", 
                    "��� �����", 
                    "��������", 
                    "�������� (���������)", 
                    "��������� ��������", 
                    "���������� (�������)", 
                    "���������", 
                    "��������� (���������)", 
                    "��������� ����� ����� �� ������ ������ (�������)", 
                    "��������� ���������", 
                    "��������� �����", 
                    "��������� ����� (���������)", 
                    "�������� �����", 
                    "������ ����� ������� (�������)", 
                    "�������� (�������)", 
                    "������� ���������", 
                    "������� ��������� (���������)", 
                    "��������� ���������", 
                    "�������� (�������)", 
                    "��������� ���������� ��������", 
                    "��������� ���������� �������� (���������)", 
                    "��������� �������� ��������", 
                    "�������� ��������� (�������)", 
                    "����������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // �������� ����� 
                    -1, // ���� 
                    -1, // ��� ����� 
                    -1, // �������� 
                    -1, // �������� (���������) 
                    -1, // ��������� �������� 
                    CALL_AUTO_LEVEL, // ���������� (�������) 
                    -1, // ��������� 
                    -1, // ��������� (���������) 
                    CALL_AUTO_LEVEL, // ��������� ����� ����� �� ������ ������ (�������) 
                    -1, // ��������� ��������� 
                    -1, // ��������� ����� 
                    -1, // ��������� ����� (���������) 
                    0, // �������� ����� 
                    CALL_AUTO_LEVEL, // ������ ����� ������� (�������) 
                    CALL_AUTO_LEVEL, // �������� (�������) 
                    -1, // ������� ��������� 
                    -1, // ������� ��������� (���������) 
                    -1, // ��������� ��������� 
                    CALL_AUTO_LEVEL, // �������� (�������) 
                    -1, // ��������� ���������� �������� 
                    -1, // ��������� ���������� �������� (���������) 
                    -1, // ��������� �������� �������� 
                    CALL_AUTO_LEVEL, // �������� ��������� (�������) 
                    -1, // ���������� 
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
                    state = 1; // �������� �����
                    break;
                }
                case 1: { // �������� �����
                    stack.pushBoolean(false); 
                    state = 2; // ����
                    break;
                }
                case 2: { // ����
                    if (d.i < d.store[2].length) {
                        state = 3; // ��� �����
                    } else {
                        state = 4; // ��������
                    }
                    break;
                }
                case 3: { // ��� �����
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
                case 4: { // ��������
                    if (d.visualizer.combobox1.getSelectedIndex() == 0) {
                        state = 6; // ��������� ��������
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // �������� (���������)
                    }
                    break;
                }
                case 5: { // �������� (���������)
                    state = 8; // ���������
                    break;
                }
                case 6: { // ��������� ��������
                    state = 7; // ���������� (�������)
                    break;
                }
                case 7: { // ���������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 5; // �������� (���������)
                    }
                    break;
                }
                case 8: { // ���������
                    if (d.visualizer.combobox1.getSelectedIndex() == 1) {
                        state = 10; // ��������� ����� ����� �� ������ ������ (�������)
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // ��������� (���������)
                    }
                    break;
                }
                case 9: { // ��������� (���������)
                    state = 17; // ������� ���������
                    break;
                }
                case 10: { // ��������� ����� ����� �� ������ ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 11; // ��������� ���������
                    }
                    break;
                }
                case 11: { // ��������� ���������
                    state = 12; // ��������� �����
                    break;
                }
                case 12: { // ��������� �����
                    if (!d.firstBig) {
                        state = 14; // �������� �����
                    } else {
                        stack.pushBoolean(false); 
                        state = 13; // ��������� ����� (���������)
                    }
                    break;
                }
                case 13: { // ��������� ����� (���������)
                    state = 16; // �������� (�������)
                    break;
                }
                case 14: { // �������� �����
                    state = 15; // ������ ����� ������� (�������)
                    break;
                }
                case 15: { // ������ ����� ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 13; // ��������� ����� (���������)
                    }
                    break;
                }
                case 16: { // �������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 9; // ��������� (���������)
                    }
                    break;
                }
                case 17: { // ������� ���������
                    if (d.visualizer.combobox1.getSelectedIndex() == 2) {
                        state = 19; // ��������� ���������
                    } else {
                        stack.pushBoolean(false); 
                        state = 18; // ������� ��������� (���������)
                    }
                    break;
                }
                case 18: { // ������� ��������� (���������)
                    state = 21; // ��������� ���������� ��������
                    break;
                }
                case 19: { // ��������� ���������
                    state = 20; // �������� (�������)
                    break;
                }
                case 20: { // �������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 18; // ������� ��������� (���������)
                    }
                    break;
                }
                case 21: { // ��������� ���������� ��������
                    if (d.visualizer.combobox1.getSelectedIndex() == 3) {
                        state = 23; // ��������� �������� ��������
                    } else {
                        stack.pushBoolean(false); 
                        state = 22; // ��������� ���������� �������� (���������)
                    }
                    break;
                }
                case 22: { // ��������� ���������� �������� (���������)
                    state = END_STATE; 
                    break;
                }
                case 23: { // ��������� �������� ��������
                    state = 24; // �������� ��������� (�������)
                    break;
                }
                case 24: { // �������� ��������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 25; // ����������
                    }
                    break;
                }
                case 25: { // ����������
                    stack.pushBoolean(true); 
                    state = 22; // ��������� ���������� �������� (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������� �����
                    startSection();
                    storeField(d, "i");
                    d.i = 0; 
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ��� �����
                    startSection();
                     storeArray(d.store[2], d.i);
                     d.store[2][d.i] = 0; d.i += 1;
                    break;
                }
                case 4: { // ��������
                    break;
                }
                case 5: { // �������� (���������)
                    break;
                }
                case 6: { // ��������� ��������
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "smu");
                    d.smu = "+"; 
                    storeField(d, "sRu");
                    d.sRu = new String("�������� ���������");
                    storeField(d, "sEn");
                    d.sEn = new String("Sum completed");
                    break;
                }
                case 7: { // ���������� (�������)
                    if (child == null) {
                        child = new Sum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // ���������
                    break;
                }
                case 9: { // ��������� (���������)
                    break;
                }
                case 10: { // ��������� ����� ����� �� ������ ������ (�������)
                    if (child == null) {
                        child = new testFirstBig(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 11: { // ��������� ���������
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "smu");
                    d.smu = "-"; 
                    storeField(d, "sRu");
                    d.sRu = new String("��������� ���������");
                    storeField(d, "sEn");
                    d.sEn = new String("Subtraction completed");
                    break;
                }
                case 12: { // ��������� �����
                    break;
                }
                case 13: { // ��������� ����� (���������)
                    break;
                }
                case 14: { // �������� �����
                    startSection();
                    break;
                }
                case 15: { // ������ ����� ������� (�������)
                    if (child == null) {
                        child = new Replace(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 16: { // �������� (�������)
                    if (child == null) {
                        child = new Mines(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 17: { // ������� ���������
                    break;
                }
                case 18: { // ������� ��������� (���������)
                    break;
                }
                case 19: { // ��������� ���������
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "smu");
                    d.smu = "*"; 
                    storeField(d, "sRu");
                    d.sRu = new String("��������� ���������");
                    storeField(d, "sEn");
                    d.sEn = new String("Power completed");
                    break;
                }
                case 20: { // �������� (�������)
                    if (child == null) {
                        child = new power(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 21: { // ��������� ���������� ��������
                    break;
                }
                case 22: { // ��������� ���������� �������� (���������)
                    break;
                }
                case 23: { // ��������� �������� ��������
                    startSection();
                    storeField(d, "smu");
                    d.smu = "**";
                    storeField(d, "storeInd");
                    d.storeInd = -8;
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "sRu");
                    d.sRu = new String("��������� ���������� �������� ���������");
                    storeField(d, "sEn");
                    d.sEn = new String("Algoritm Karatchuba's power completed");
                    break;
                }
                case 24: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new KarpowerMain(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 25: { // ����������
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "storeInd");
                    d.storeInd = 0;
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
                case 1: { // �������� �����
                    restoreSection();
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ��� �����
                    restoreSection();
                    break;
                }
                case 4: { // ��������
                    break;
                }
                case 5: { // �������� (���������)
                    break;
                }
                case 6: { // ��������� ��������
                    restoreSection();
                    break;
                }
                case 7: { // ���������� (�������)
                    if (child == null) {
                        child = new Sum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // ���������
                    break;
                }
                case 9: { // ��������� (���������)
                    break;
                }
                case 10: { // ��������� ����� ����� �� ������ ������ (�������)
                    if (child == null) {
                        child = new testFirstBig(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 11: { // ��������� ���������
                    restoreSection();
                    break;
                }
                case 12: { // ��������� �����
                    break;
                }
                case 13: { // ��������� ����� (���������)
                    break;
                }
                case 14: { // �������� �����
                    restoreSection();
                    break;
                }
                case 15: { // ������ ����� ������� (�������)
                    if (child == null) {
                        child = new Replace(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 16: { // �������� (�������)
                    if (child == null) {
                        child = new Mines(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 17: { // ������� ���������
                    break;
                }
                case 18: { // ������� ��������� (���������)
                    break;
                }
                case 19: { // ��������� ���������
                    restoreSection();
                    break;
                }
                case 20: { // �������� (�������)
                    if (child == null) {
                        child = new power(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 21: { // ��������� ���������� ��������
                    break;
                }
                case 22: { // ��������� ���������� �������� (���������)
                    break;
                }
                case 23: { // ��������� �������� ��������
                    restoreSection();
                    break;
                }
                case 24: { // �������� ��������� (�������)
                    if (child == null) {
                        child = new KarpowerMain(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 25: { // ����������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // �������� �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����
                    if (stack.popBoolean()) {
                        state = 3; // ��� �����
                    } else {
                        state = 1; // �������� �����
                    }
                    break;
                }
                case 3: { // ��� �����
                    state = 2; // ����
                    break;
                }
                case 4: { // ��������
                    state = 2; // ����
                    break;
                }
                case 5: { // �������� (���������)
                    if (stack.popBoolean()) {
                        state = 7; // ���������� (�������)
                    } else {
                        state = 4; // ��������
                    }
                    break;
                }
                case 6: { // ��������� ��������
                    state = 4; // ��������
                    break;
                }
                case 7: { // ���������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // ��������� ��������
                    }
                    break;
                }
                case 8: { // ���������
                    state = 5; // �������� (���������)
                    break;
                }
                case 9: { // ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 16; // �������� (�������)
                    } else {
                        state = 8; // ���������
                    }
                    break;
                }
                case 10: { // ��������� ����� ����� �� ������ ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // ���������
                    }
                    break;
                }
                case 11: { // ��������� ���������
                    state = 10; // ��������� ����� ����� �� ������ ������ (�������)
                    break;
                }
                case 12: { // ��������� �����
                    state = 11; // ��������� ���������
                    break;
                }
                case 13: { // ��������� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 15; // ������ ����� ������� (�������)
                    } else {
                        state = 12; // ��������� �����
                    }
                    break;
                }
                case 14: { // �������� �����
                    state = 12; // ��������� �����
                    break;
                }
                case 15: { // ������ ����� ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 14; // �������� �����
                    }
                    break;
                }
                case 16: { // �������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 13; // ��������� ����� (���������)
                    }
                    break;
                }
                case 17: { // ������� ���������
                    state = 9; // ��������� (���������)
                    break;
                }
                case 18: { // ������� ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 20; // �������� (�������)
                    } else {
                        state = 17; // ������� ���������
                    }
                    break;
                }
                case 19: { // ��������� ���������
                    state = 17; // ������� ���������
                    break;
                }
                case 20: { // �������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // ��������� ���������
                    }
                    break;
                }
                case 21: { // ��������� ���������� ��������
                    state = 18; // ������� ��������� (���������)
                    break;
                }
                case 22: { // ��������� ���������� �������� (���������)
                    if (stack.popBoolean()) {
                        state = 25; // ����������
                    } else {
                        state = 21; // ��������� ���������� ��������
                    }
                    break;
                }
                case 23: { // ��������� �������� ��������
                    state = 21; // ��������� ���������� ��������
                    break;
                }
                case 24: { // �������� ��������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 23; // ��������� �������� ��������
                    }
                    break;
                }
                case 25: { // ����������
                    state = 24; // �������� ��������� (�������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 22; // ��������� ���������� �������� (���������)
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
                    comment = LongInteger.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // �������� �����
                    comment = LongInteger.this.getComment("Main.startStep3"); 
                    break;
                }
                case 6: { // ��������� ��������
                    comment = LongInteger.this.getComment("Main.sumEnter"); 
                    break;
                }
                case 7: { // ���������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // ��������� ����� ����� �� ������ ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 11: { // ��������� ���������
                    comment = LongInteger.this.getComment("Main.minesEnter"); 
                    break;
                }
                case 14: { // �������� �����
                    comment = LongInteger.this.getComment("Main.replaceEnter"); 
                    break;
                }
                case 15: { // ������ ����� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 16: { // �������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 19: { // ��������� ���������
                    comment = LongInteger.this.getComment("Main.choicepower"); 
                    break;
                }
                case 20: { // �������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 23: { // ��������� �������� ��������
                    comment = LongInteger.this.getComment("Main.choiceKarpower"); 
                    break;
                }
                case 24: { // �������� ��������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = LongInteger.this.getComment("Main.END_STATE"); 
                    args = new Object[]{new String(d.sRu), new String(d.sEn)}; 
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
                    d.i = 0;
                    d.j = 0;
                    d.ost = -10000; 
                    d.tempOst = -10000;
                    d.storeInd = 0;
                    d.commentLevel = 10001;
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    break;
                }
                case 6: { // ��������� ��������
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 7: { // ���������� (�������)
                    child.drawState(); 
                    break;
                }
                case 10: { // ��������� ����� ����� �� ������ ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 11: { // ��������� ���������
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 14: { // �������� �����
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 15: { // ������ ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 16: { // �������� (�������)
                    child.drawState(); 
                    break;
                }
                case 19: { // ��������� ���������
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 20: { // �������� (�������)
                    child.drawState(); 
                    break;
                }
                case 23: { // ��������� �������� ��������
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    break;
                }
                case 24: { // �������� ��������� (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.i = -10000;
                    d.j = -10000;
                    d.ost = -10000;
                    d.tempOst = -10000;
                    d.commentLevel = 10000;
                    d.visualizer.antiSOFE = true;
                    d.visualizer.rePaint();
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    break;
                }
            }
        }
    }
}
