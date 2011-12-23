package ru.ifmo.vizi.btree;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class BTree extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public BTree(Locale locale) {
        super("ru.ifmo.vizi.btree.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ��������� �������.
          */
        public BTreeVisualizer visualizer = null;

        /**
          * �������� ������.
          */
        public boolean[] leaf = new boolean[26];

        /**
          * ���-�� ������.
          */
        public int[] n = new int[26];

        /**
          * �����.
          */
        public char[][] key = new char[26][26];

        /**
          * ������.
          */
        public int[][] c = new int[26][26];

        /**
          * ���-�� �����.
          */
        public int num = 1;

        /**
          * ������� ����.
          */
        public int x = 1;

        /**
          * ����.
          */
        public char k = 'A';

        /**
          * ������ ������.
          */
        public int root = 1;

        /**
          * ???.
          */
        public int t = 2;

        /**
          * ???.
          */
        public int y = 0;

        /**
          * ???.
          */
        public int m = 0;

        /**
          * ������� ������.
          */
        public String sRu = new String();

        /**
          * English String.
          */
        public String sEn = new String();

        /**
          *  (��������� SplitChild).
          */
        public int SplitChild_z;

        /**
          * ���������� ����� (��������� SplitChild).
          */
        public int SplitChild_j;

        /**
          * ���������� ����� (��������� InsertNonfull).
          */
        public boolean InsertNonfull_flag;

        /**
          * ���������� ����� (��������� InsertNonfull).
          */
        public int InsertNonfull_i;

        public String toString() {
            return("");
        }
    }

    /**
      * ��������� �������.
      */
    private final class SplitChild extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 16;

        /**
          * �����������.
          */
        public SplitChild() {
            super( 
                "SplitChild", 
                0, // ����� ���������� ��������� 
                16, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "����", 
                    "XZ", 
                    "XZ", 
                    "XZ (���������)", 
                    "������ �����", 
                    "����", 
                    "XZ", 
                    "������ �����", 
                    "����", 
                    "XZ", 
                    "������ �����", 
                    "����", 
                    "XZ", 
                    "XZ", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
                    -1, // ���� 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // XZ (���������) 
                    -1, // ������ ����� 
                    -1, // ���� 
                    -1, // XZ 
                    -1, // ������ ����� 
                    -1, // ���� 
                    -1, // XZ 
                    -1, // ������ ����� 
                    -1, // ���� 
                    -1, // XZ 
                    -1, // XZ 
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
                    if (d.SplitChild_j <= d.t - 1) {
                        state = 3; // XZ
                    } else {
                        state = 4; // XZ
                    }
                    break;
                }
                case 3: { // XZ
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
                case 4: { // XZ
                    if (!d.leaf[d.y]) {
                        state = 6; // ������ �����
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // XZ (���������)
                    }
                    break;
                }
                case 5: { // XZ (���������)
                    state = 9; // ������ �����
                    break;
                }
                case 6: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 7; // ����
                    break;
                }
                case 7: { // ����
                    if (d.SplitChild_j <= d.t) {
                        state = 8; // XZ
                    } else {
                        stack.pushBoolean(true); 
                        state = 5; // XZ (���������)
                    }
                    break;
                }
                case 8: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // ����
                    break;
                }
                case 9: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 10; // ����
                    break;
                }
                case 10: { // ����
                    if (d.m <= d.SplitChild_j) {
                        state = 11; // XZ
                    } else {
                        state = 12; // ������ �����
                    }
                    break;
                }
                case 11: { // XZ
                    stack.pushBoolean(true); 
                    state = 10; // ����
                    break;
                }
                case 12: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 13; // ����
                    break;
                }
                case 13: { // ����
                    if (d.m <= d.SplitChild_j) {
                        state = 14; // XZ
                    } else {
                        state = 15; // XZ
                    }
                    break;
                }
                case 14: { // XZ
                    stack.pushBoolean(true); 
                    state = 13; // ����
                    break;
                }
                case 15: { // XZ
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
                    startSection();
                    storeField(d, "num");
                    d.num = d.num + 1;
                    storeField(d, "SplitChild_z");
                    d.SplitChild_z = d.num;
                    
                    storeArray(d.leaf, d.SplitChild_z);
                    d.leaf[d.SplitChild_z] = d.leaf[d.y];
                    storeArray(d.n, d.SplitChild_z);
                    d.n[d.SplitChild_z] = d.t - 1;
                    
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = 1;
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // XZ
                    startSection();
                    storeArray(d.key[d.SplitChild_z], d.SplitChild_j);
                    d.key[d.SplitChild_z][d.SplitChild_j] = d.key[d.y][d.SplitChild_j + d.t];
                    
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.SplitChild_j + 1;
                    break;
                }
                case 4: { // XZ
                    break;
                }
                case 5: { // XZ (���������)
                    break;
                }
                case 6: { // ������ �����
                    startSection();
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = 1;
                    break;
                }
                case 7: { // ����
                    break;
                }
                case 8: { // XZ
                    startSection();
                    d.c[d.SplitChild_z][d.SplitChild_j] = d.c[d.y][d.SplitChild_j + d.t];
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.SplitChild_j + 1;
                    break;
                }
                case 9: { // ������ �����
                    startSection();
                    storeArray(d.n, d.y);
                    d.n[d.y] = d.t - 1;
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.n[d.x] + 1;
                    break;
                }
                case 10: { // ����
                    break;
                }
                case 11: { // XZ
                    startSection();
                    storeArray(d.c[d.x], d.SplitChild_j + 1);
                    d.c[d.x][d.SplitChild_j + 1] = d.c[d.x][d.SplitChild_j];
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.SplitChild_j - 1;
                    break;
                }
                case 12: { // ������ �����
                    startSection();
                    storeArray(d.c[d.x], d.m + 1);
                    d.c[d.x][d.m + 1] = d.SplitChild_z;
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.n[d.x];
                    break;
                }
                case 13: { // ����
                    break;
                }
                case 14: { // XZ
                    startSection();
                    storeArray(d.key[d.x], d.SplitChild_j + 1);
                    d.key[d.x][d.SplitChild_j + 1] = d.key[d.x][d.SplitChild_j];
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.SplitChild_j - 1;
                    break;
                }
                case 15: { // XZ
                    startSection();
                    storeArray(d.key[d.x], d.m);
                    d.key[d.x][d.m] = d.key[d.y][d.t];
                    storeArray(d.n, d.x);
                    d.n[d.x] = d.n[d.x] + 1;
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
                case 3: { // XZ
                    restoreSection();
                    break;
                }
                case 4: { // XZ
                    break;
                }
                case 5: { // XZ (���������)
                    break;
                }
                case 6: { // ������ �����
                    restoreSection();
                    break;
                }
                case 7: { // ����
                    break;
                }
                case 8: { // XZ
                    restoreSection();
                    break;
                }
                case 9: { // ������ �����
                    restoreSection();
                    break;
                }
                case 10: { // ����
                    break;
                }
                case 11: { // XZ
                    restoreSection();
                    break;
                }
                case 12: { // ������ �����
                    restoreSection();
                    break;
                }
                case 13: { // ����
                    break;
                }
                case 14: { // XZ
                    restoreSection();
                    break;
                }
                case 15: { // XZ
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
                        state = 3; // XZ
                    } else {
                        state = 1; // ������ �����
                    }
                    break;
                }
                case 3: { // XZ
                    state = 2; // ����
                    break;
                }
                case 4: { // XZ
                    state = 2; // ����
                    break;
                }
                case 5: { // XZ (���������)
                    if (stack.popBoolean()) {
                        state = 7; // ����
                    } else {
                        state = 4; // XZ
                    }
                    break;
                }
                case 6: { // ������ �����
                    state = 4; // XZ
                    break;
                }
                case 7: { // ����
                    if (stack.popBoolean()) {
                        state = 8; // XZ
                    } else {
                        state = 6; // ������ �����
                    }
                    break;
                }
                case 8: { // XZ
                    state = 7; // ����
                    break;
                }
                case 9: { // ������ �����
                    state = 5; // XZ (���������)
                    break;
                }
                case 10: { // ����
                    if (stack.popBoolean()) {
                        state = 11; // XZ
                    } else {
                        state = 9; // ������ �����
                    }
                    break;
                }
                case 11: { // XZ
                    state = 10; // ����
                    break;
                }
                case 12: { // ������ �����
                    state = 10; // ����
                    break;
                }
                case 13: { // ����
                    if (stack.popBoolean()) {
                        state = 14; // XZ
                    } else {
                        state = 12; // ������ �����
                    }
                    break;
                }
                case 14: { // XZ
                    state = 13; // ����
                    break;
                }
                case 15: { // XZ
                    state = 13; // ����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 15; // XZ
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
      * .
      */
    private final class InsertNonfull extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 23;

        /**
          * �����������.
          */
        public InsertNonfull() {
            super( 
                "InsertNonfull", 
                0, // ����� ���������� ��������� 
                23, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "XZ", 
                    "XZ", 
                    "������ �����", 
                    "���� ������� ������� ����", 
                    "���� ������� ������� ���� (���������)", 
                    "InThis", 
                    "����", 
                    "XZ1", 
                    "XZ2", 
                    "XZ", 
                    "����", 
                    "XZ1", 
                    "XZ2", 
                    "XZ", 
                    "XZ (���������)", 
                    "XZ", 
                    "��������� ������� (�������)", 
                    "XZ", 
                    "XZ", 
                    "XZ (���������)", 
                    "XZ", 
                    "XZ", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ������ ����� 
                    -1, // ���� ������� ������� ���� 
                    -1, // ���� ������� ������� ���� (���������) 
                    0, // InThis 
                    -1, // ���� 
                    -1, // XZ1 
                    0, // XZ2 
                    0, // XZ 
                    -1, // ���� 
                    -1, // XZ1 
                    -1, // XZ2 
                    -1, // XZ 
                    -1, // XZ (���������) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // ��������� ������� (�������) 
                    0, // XZ 
                    -1, // XZ 
                    -1, // XZ (���������) 
                    -1, // XZ 
                    0, // XZ 
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
                    state = 1; // XZ
                    break;
                }
                case 1: { // XZ
                    stack.pushBoolean(false); 
                    state = 2; // XZ
                    break;
                }
                case 2: { // XZ
                    if (d.InsertNonfull_flag) {
                        state = 3; // ������ �����
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // ������ �����
                    state = 4; // ���� ������� ������� ����
                    break;
                }
                case 4: { // ���� ������� ������� ����
                    if (d.leaf[d.x]) {
                        state = 6; // InThis
                    } else {
                        state = 10; // XZ
                    }
                    break;
                }
                case 5: { // ���� ������� ������� ���� (���������)
                    stack.pushBoolean(true); 
                    state = 2; // XZ
                    break;
                }
                case 6: { // InThis
                    stack.pushBoolean(false); 
                    state = 7; // ����
                    break;
                }
                case 7: { // ����
                    if ((1 <= d.InsertNonfull_i) && (d.k < d.key[d.x][d.InsertNonfull_i])) {
                        state = 8; // XZ1
                    } else {
                        state = 9; // XZ2
                    }
                    break;
                }
                case 8: { // XZ1
                    stack.pushBoolean(true); 
                    state = 7; // ����
                    break;
                }
                case 9: { // XZ2
                    stack.pushBoolean(true); 
                    state = 5; // ���� ������� ������� ���� (���������)
                    break;
                }
                case 10: { // XZ
                    stack.pushBoolean(false); 
                    state = 11; // ����
                    break;
                }
                case 11: { // ����
                    if ((1 <= d.InsertNonfull_i) && (d.k < d.key[d.x][d.InsertNonfull_i])) {
                        state = 12; // XZ1
                    } else {
                        state = 13; // XZ2
                    }
                    break;
                }
                case 12: { // XZ1
                    stack.pushBoolean(true); 
                    state = 11; // ����
                    break;
                }
                case 13: { // XZ2
                    state = 14; // XZ
                    break;
                }
                case 14: { // XZ
                    if (d.n[d.c[d.x][d.InsertNonfull_i]] == 2 * d.t - 1) {
                        state = 16; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 15; // XZ (���������)
                    }
                    break;
                }
                case 15: { // XZ (���������)
                    state = 22; // XZ
                    break;
                }
                case 16: { // XZ
                    state = 17; // ��������� ������� (�������)
                    break;
                }
                case 17: { // ��������� ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 18; // XZ
                    }
                    break;
                }
                case 18: { // XZ
                    state = 19; // XZ
                    break;
                }
                case 19: { // XZ
                    if (d.key[d.x][d.InsertNonfull_i] < d.k) {
                        state = 21; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 20; // XZ (���������)
                    }
                    break;
                }
                case 20: { // XZ (���������)
                    stack.pushBoolean(true); 
                    state = 15; // XZ (���������)
                    break;
                }
                case 21: { // XZ
                    stack.pushBoolean(true); 
                    state = 20; // XZ (���������)
                    break;
                }
                case 22: { // XZ
                    stack.pushBoolean(false); 
                    state = 5; // ���� ������� ������� ���� (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // XZ
                    startSection();
                    storeField(d, "InsertNonfull_flag");
                    d.InsertNonfull_flag = true;
                    break;
                }
                case 2: { // XZ
                    break;
                }
                case 3: { // ������ �����
                    startSection();
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.n[d.x];
                    break;
                }
                case 4: { // ���� ������� ������� ����
                    break;
                }
                case 5: { // ���� ������� ������� ���� (���������)
                    break;
                }
                case 6: { // InThis
                    startSection();
                    break;
                }
                case 7: { // ����
                    break;
                }
                case 8: { // XZ1
                    startSection();
                    storeArray(d.key[d.x], d.InsertNonfull_i + 1);
                    d.key[d.x][d.InsertNonfull_i + 1] = d.key[d.x][d.InsertNonfull_i];
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.InsertNonfull_i - 1;
                    break;
                }
                case 9: { // XZ2
                    startSection();
                    storeArray(d.key[d.x], d.InsertNonfull_i + 1);
                    d.key[d.x][d.InsertNonfull_i + 1] = d.k;
                    storeArray(d.n, d.x);
                    d.n[d.x] = d.n[d.x] + 1;
                    storeField(d, "InsertNonfull_flag");
                    d.InsertNonfull_flag = false;
                    break;
                }
                case 10: { // XZ
                    startSection();
                    break;
                }
                case 11: { // ����
                    break;
                }
                case 12: { // XZ1
                    startSection();
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.InsertNonfull_i - 1;
                    break;
                }
                case 13: { // XZ2
                    startSection();
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.InsertNonfull_i + 1;
                    break;
                }
                case 14: { // XZ
                    break;
                }
                case 15: { // XZ (���������)
                    break;
                }
                case 16: { // XZ
                    startSection();
                    storeField(d, "m");
                    d.m = d.InsertNonfull_i;
                    storeField(d, "y");
                    d.y = d.c[d.x][d.InsertNonfull_i];
                    break;
                }
                case 17: { // ��������� ������� (�������)
                    if (child == null) {
                        child = new SplitChild(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 18: { // XZ
                    startSection();
                    break;
                }
                case 19: { // XZ
                    break;
                }
                case 20: { // XZ (���������)
                    break;
                }
                case 21: { // XZ
                    startSection();
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.InsertNonfull_i + 1;
                    break;
                }
                case 22: { // XZ
                    startSection();
                    storeField(d, "x");
                    d.x = d.c[d.x][d.InsertNonfull_i];
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
                case 1: { // XZ
                    restoreSection();
                    break;
                }
                case 2: { // XZ
                    break;
                }
                case 3: { // ������ �����
                    restoreSection();
                    break;
                }
                case 4: { // ���� ������� ������� ����
                    break;
                }
                case 5: { // ���� ������� ������� ���� (���������)
                    break;
                }
                case 6: { // InThis
                    restoreSection();
                    break;
                }
                case 7: { // ����
                    break;
                }
                case 8: { // XZ1
                    restoreSection();
                    break;
                }
                case 9: { // XZ2
                    restoreSection();
                    break;
                }
                case 10: { // XZ
                    restoreSection();
                    break;
                }
                case 11: { // ����
                    break;
                }
                case 12: { // XZ1
                    restoreSection();
                    break;
                }
                case 13: { // XZ2
                    restoreSection();
                    break;
                }
                case 14: { // XZ
                    break;
                }
                case 15: { // XZ (���������)
                    break;
                }
                case 16: { // XZ
                    restoreSection();
                    break;
                }
                case 17: { // ��������� ������� (�������)
                    if (child == null) {
                        child = new SplitChild(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 18: { // XZ
                    restoreSection();
                    break;
                }
                case 19: { // XZ
                    break;
                }
                case 20: { // XZ (���������)
                    break;
                }
                case 21: { // XZ
                    restoreSection();
                    break;
                }
                case 22: { // XZ
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // XZ
                    state = START_STATE; 
                    break;
                }
                case 2: { // XZ
                    if (stack.popBoolean()) {
                        state = 5; // ���� ������� ������� ���� (���������)
                    } else {
                        state = 1; // XZ
                    }
                    break;
                }
                case 3: { // ������ �����
                    state = 2; // XZ
                    break;
                }
                case 4: { // ���� ������� ������� ����
                    state = 3; // ������ �����
                    break;
                }
                case 5: { // ���� ������� ������� ���� (���������)
                    if (stack.popBoolean()) {
                        state = 9; // XZ2
                    } else {
                        state = 22; // XZ
                    }
                    break;
                }
                case 6: { // InThis
                    state = 4; // ���� ������� ������� ����
                    break;
                }
                case 7: { // ����
                    if (stack.popBoolean()) {
                        state = 8; // XZ1
                    } else {
                        state = 6; // InThis
                    }
                    break;
                }
                case 8: { // XZ1
                    state = 7; // ����
                    break;
                }
                case 9: { // XZ2
                    state = 7; // ����
                    break;
                }
                case 10: { // XZ
                    state = 4; // ���� ������� ������� ����
                    break;
                }
                case 11: { // ����
                    if (stack.popBoolean()) {
                        state = 12; // XZ1
                    } else {
                        state = 10; // XZ
                    }
                    break;
                }
                case 12: { // XZ1
                    state = 11; // ����
                    break;
                }
                case 13: { // XZ2
                    state = 11; // ����
                    break;
                }
                case 14: { // XZ
                    state = 13; // XZ2
                    break;
                }
                case 15: { // XZ (���������)
                    if (stack.popBoolean()) {
                        state = 20; // XZ (���������)
                    } else {
                        state = 14; // XZ
                    }
                    break;
                }
                case 16: { // XZ
                    state = 14; // XZ
                    break;
                }
                case 17: { // ��������� ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 16; // XZ
                    }
                    break;
                }
                case 18: { // XZ
                    state = 17; // ��������� ������� (�������)
                    break;
                }
                case 19: { // XZ
                    state = 18; // XZ
                    break;
                }
                case 20: { // XZ (���������)
                    if (stack.popBoolean()) {
                        state = 21; // XZ
                    } else {
                        state = 19; // XZ
                    }
                    break;
                }
                case 21: { // XZ
                    state = 19; // XZ
                    break;
                }
                case 22: { // XZ
                    state = 15; // XZ (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 2; // XZ
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
                case 6: { // InThis
                    comment = BTree.this.getComment("InsertNonfull.DrawStep1"); 
                    break;
                }
                case 9: { // XZ2
                    comment = BTree.this.getComment("InsertNonfull.StepAfterLoop1"); 
                    break;
                }
                case 10: { // XZ
                    comment = BTree.this.getComment("InsertNonfull.DrawStep2"); 
                    break;
                }
                case 17: { // ��������� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 18: { // XZ
                    comment = BTree.this.getComment("InsertNonfull.test"); 
                    break;
                }
                case 22: { // XZ
                    comment = BTree.this.getComment("InsertNonfull.FinalStep"); 
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
                case 6: { // InThis
                    d.visualizer.showTree(d.x, 2);
                    break;
                }
                case 9: { // XZ2
                    d.visualizer.showTree(-1, 0);
                    break;
                }
                case 10: { // XZ
                    d.visualizer.showTree(d.x, 2);
                    break;
                }
                case 17: { // ��������� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 18: { // XZ
                    d.visualizer.showTree(-1, 0);
                    break;
                }
                case 22: { // XZ
                    d.visualizer.showTree(d.x, 1);
                    break;
                }
            }
        }
    }

    /**
      * ��������� ���� � ������.
      */
    private final class Insert extends BaseAutomata implements Automata {
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
        public Insert() {
            super( 
                "Insert", 
                0, // ����� ���������� ��������� 
                9, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "XZ", 
                    "���� ������ ������", 
                    "���� ������ ������ (���������)", 
                    "XZ", 
                    "��������� ������� (�������)", 
                    "XZ", 
                    " (�������)", 
                    " (�������)", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // XZ 
                    -1, // ���� ������ ������ 
                    -1, // ���� ������ ������ (���������) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // ��������� ������� (�������) 
                    0, // XZ 
                    CALL_AUTO_LEVEL, //  (�������) 
                    CALL_AUTO_LEVEL, //  (�������) 
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
                    state = 1; // XZ
                    break;
                }
                case 1: { // XZ
                    state = 2; // ���� ������ ������
                    break;
                }
                case 2: { // ���� ������ ������
                    if (d.n[d.y] == 2 * d.t - 1) {
                        state = 4; // XZ
                    } else {
                        state = 8; //  (�������)
                    }
                    break;
                }
                case 3: { // ���� ������ ������ (���������)
                    state = END_STATE; 
                    break;
                }
                case 4: { // XZ
                    state = 5; // ��������� ������� (�������)
                    break;
                }
                case 5: { // ��������� ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 6; // XZ
                    }
                    break;
                }
                case 6: { // XZ
                    state = 7; //  (�������)
                    break;
                }
                case 7: { //  (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 3; // ���� ������ ������ (���������)
                    }
                    break;
                }
                case 8: { //  (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 3; // ���� ������ ������ (���������)
                    }
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // XZ
                    startSection();
                    storeField(d, "sRu");
                    d.sRu = new String("������! ���� �������� �� ������ �����.");
                    storeField(d, "sEn");
                    d.sEn = new String("Completed!");
                    
                    storeField(d, "x");
                    d.x = d.root;
                    storeField(d, "y");
                    d.y = d.root;
                    break;
                }
                case 2: { // ���� ������ ������
                    break;
                }
                case 3: { // ���� ������ ������ (���������)
                    break;
                }
                case 4: { // XZ
                    startSection();
                    storeField(d, "num");
                    d.num = d.num + 1;
                    storeField(d, "x");
                    d.x = d.num;
                    
                    storeField(d, "root");
                    d.root = d.x;
                    storeArray(d.leaf, d.x);
                    d.leaf[d.x] = false;
                    storeArray(d.n, d.x);
                    d.n[d.x] = 0;
                    storeArray(d.c[d.x], 1);
                    d.c[d.x][1] = d.y;
                    storeField(d, "m");
                    d.m = 1;
                    break;
                }
                case 5: { // ��������� ������� (�������)
                    if (child == null) {
                        child = new SplitChild(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 6: { // XZ
                    startSection();
                    storeField(d, "x");
                    d.x = d.root;
                    break;
                }
                case 7: { //  (�������)
                    if (child == null) {
                        child = new InsertNonfull(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { //  (�������)
                    if (child == null) {
                        child = new InsertNonfull(); 
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
                case 1: { // XZ
                    restoreSection();
                    break;
                }
                case 2: { // ���� ������ ������
                    break;
                }
                case 3: { // ���� ������ ������ (���������)
                    break;
                }
                case 4: { // XZ
                    restoreSection();
                    break;
                }
                case 5: { // ��������� ������� (�������)
                    if (child == null) {
                        child = new SplitChild(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 6: { // XZ
                    restoreSection();
                    break;
                }
                case 7: { //  (�������)
                    if (child == null) {
                        child = new InsertNonfull(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { //  (�������)
                    if (child == null) {
                        child = new InsertNonfull(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // XZ
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���� ������ ������
                    state = 1; // XZ
                    break;
                }
                case 3: { // ���� ������ ������ (���������)
                    if (stack.popBoolean()) {
                        state = 7; //  (�������)
                    } else {
                        state = 8; //  (�������)
                    }
                    break;
                }
                case 4: { // XZ
                    state = 2; // ���� ������ ������
                    break;
                }
                case 5: { // ��������� ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 4; // XZ
                    }
                    break;
                }
                case 6: { // XZ
                    state = 5; // ��������� ������� (�������)
                    break;
                }
                case 7: { //  (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // XZ
                    }
                    break;
                }
                case 8: { //  (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 2; // ���� ������ ������
                    }
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 3; // ���� ������ ������ (���������)
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
                case 5: { // ��������� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 6: { // XZ
                    comment = BTree.this.getComment("Insert.test"); 
                    break;
                }
                case 7: { //  (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 8: { //  (�������)
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
                case 1: { // XZ
                    d.visualizer.showTree(-1, 0);
                    break;
                }
                case 5: { // ��������� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 6: { // XZ
                    d.visualizer.showTree(-1, 0);
                    break;
                }
                case 7: { //  (�������)
                    child.drawState(); 
                    break;
                }
                case 8: { //  (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * ������� ���� �� ������.
      */
    private final class Delete extends BaseAutomata implements Automata {
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
        public Delete() {
            super( 
                "Delete", 
                0, // ����� ���������� ��������� 
                2, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������ �����", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������ ����� 
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
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������ �����
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
                case 1: { // ������ �����
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
                case END_STATE: { // ��������� ���������
                    state = 1; // ������ �����
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
                    "������� ����������", 
                    "������� ���������� (���������)", 
                    "this is test", 
                    "��������� ���� � ������ (�������)", 
                    "������� ��������", 
                    "������� �������� (���������)", 
                    "������� ���� �� ������ (�������)", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������� ���������� 
                    -1, // ������� ���������� (���������) 
                    0, // this is test 
                    CALL_AUTO_LEVEL, // ��������� ���� � ������ (�������) 
                    -1, // ������� �������� 
                    -1, // ������� �������� (���������) 
                    CALL_AUTO_LEVEL, // ������� ���� �� ������ (�������) 
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
                    state = 1; // ������� ����������
                    break;
                }
                case 1: { // ������� ����������
                    if (d.visualizer.combobox1.getSelectedIndex() == 0) {
                        state = 3; // this is test
                    } else {
                        stack.pushBoolean(false); 
                        state = 2; // ������� ���������� (���������)
                    }
                    break;
                }
                case 2: { // ������� ���������� (���������)
                    state = 5; // ������� ��������
                    break;
                }
                case 3: { // this is test
                    state = 4; // ��������� ���� � ������ (�������)
                    break;
                }
                case 4: { // ��������� ���� � ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 2; // ������� ���������� (���������)
                    }
                    break;
                }
                case 5: { // ������� ��������
                    if (d.visualizer.combobox1.getSelectedIndex() == 1) {
                        state = 7; // ������� ���� �� ������ (�������)
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // ������� �������� (���������)
                    }
                    break;
                }
                case 6: { // ������� �������� (���������)
                    state = END_STATE; 
                    break;
                }
                case 7: { // ������� ���� �� ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 6; // ������� �������� (���������)
                    }
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������� ����������
                    break;
                }
                case 2: { // ������� ���������� (���������)
                    break;
                }
                case 3: { // this is test
                    startSection();
                    d.k = d.visualizer.get();
                    break;
                }
                case 4: { // ��������� ���� � ������ (�������)
                    if (child == null) {
                        child = new Insert(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 5: { // ������� ��������
                    break;
                }
                case 6: { // ������� �������� (���������)
                    break;
                }
                case 7: { // ������� ���� �� ������ (�������)
                    if (child == null) {
                        child = new Delete(); 
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
                case 1: { // ������� ����������
                    break;
                }
                case 2: { // ������� ���������� (���������)
                    break;
                }
                case 3: { // this is test
                    restoreSection();
                    break;
                }
                case 4: { // ��������� ���� � ������ (�������)
                    if (child == null) {
                        child = new Insert(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 5: { // ������� ��������
                    break;
                }
                case 6: { // ������� �������� (���������)
                    break;
                }
                case 7: { // ������� ���� �� ������ (�������)
                    if (child == null) {
                        child = new Delete(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������� ����������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������� ���������� (���������)
                    if (stack.popBoolean()) {
                        state = 4; // ��������� ���� � ������ (�������)
                    } else {
                        state = 1; // ������� ����������
                    }
                    break;
                }
                case 3: { // this is test
                    state = 1; // ������� ����������
                    break;
                }
                case 4: { // ��������� ���� � ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 3; // this is test
                    }
                    break;
                }
                case 5: { // ������� ��������
                    state = 2; // ������� ���������� (���������)
                    break;
                }
                case 6: { // ������� �������� (���������)
                    if (stack.popBoolean()) {
                        state = 7; // ������� ���� �� ������ (�������)
                    } else {
                        state = 5; // ������� ��������
                    }
                    break;
                }
                case 7: { // ������� ���� �� ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 5; // ������� ��������
                    }
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 6; // ������� �������� (���������)
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
                    comment = BTree.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 3: { // this is test
                    comment = BTree.this.getComment("Main.test"); 
                    break;
                }
                case 4: { // ��������� ���� � ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // ������� ���� �� ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = BTree.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.showTree(-1, 0);
                    break;
                }
                case 3: { // this is test
                    d.visualizer.showTree(-1, 0);
                    break;
                }
                case 4: { // ��������� ���� � ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 7: { // ������� ���� �� ������ (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.showTree(-1, 0);
                    break;
                }
            }
        }
    }
}
