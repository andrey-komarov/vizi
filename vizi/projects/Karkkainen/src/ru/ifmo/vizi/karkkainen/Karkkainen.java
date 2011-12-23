package ru.ifmo.vizi.karkkainen;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class Karkkainen extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public Karkkainen(Locale locale) {
        super("ru.ifmo.vizi.karkkainen.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ��������� �������.
          */
        public KarkkainenVisualizer visualizer = null;

        /**
          * ��������� ������.
          */
        public char[] s = new char[]{'a'};

        /**
          * ����� ��������� ������.
          */
        public int[] sSt = new int[0];

        /**
          * ����� �������� ��������� ������.
          */
        public int[] sNumSt = new int[3];

        /**
          * ���������� ����������� ������.
          */
        public int[][] recStr = new int[0][0];

        /**
          * ����� ���������� ����������� �����.
          */
        public int[][] recStrSt = new int[0][0];

        /**
          * ����� �������� ���������� ����������� �����.
          */
        public int[][] recStrNumSt = new int[0][0];

        /**
          * ����� �����.
          */
        public int[][][] tripplesSt = new int[0][0][0];

        /**
          * ������ �����.
          */
        public int[][][] tripplesNum = new int[0][0][0];

        /**
          * ����� ������� �����.
          */
        public int[][][] tripplesNumSt = new int[0][0][0];

        /**
          * Recursive built array 1 style.
          */
        public int[][] recS1St = new int[0][0];

        /**
          * ���������� �������.
          */
        public int[][] suffArr = new int[0][0];

        /**
          * ����� ���������� ��������.
          */
        public int[][] suffArrSt = new int[0][0];

        /**
          * ������ ����� ����������� �������.
          */
        public int[][] suffArrSt2 = new int[0][0];

        /**
          * ����� ����������� ������� ��� ��������� ������.
          */
        public int[][] lastSuffArrSt = new int[0][0];

        /**
          * �������� � ����������� ������ ������������.
          */
        public int[][][] invSuffArr = new int[0][0][0];

        /**
          * ����� �������� � ����������� ������� ������������.
          */
        public int[][][] invSuffArrSt = new int[0][0][0];

        /**
          * ����.
          */
        public int[][][] pairs = new int[0][0][0];

        /**
          * ����� ���.
          */
        public int[][][] pairsSt = new int[0][0][0];

        /**
          * ������ ���.
          */
        public int[][][] pairsNum = new int[0][0][0];

        /**
          * ����� ������� ���.
          */
        public int[][][] pairsNumSt = new int[0][0][0];

        /**
          * ����� �������� �������� � ����������� ������� ������������.
          */
        public int[][][] invSuffArrNumSt = new int[0][0][0];

        /**
          * ������ ��������� ���� 2 � 3 � ������.
          */
        public int[][][] trp23Num = new int[0][0][0];

        /**
          * ����� ������� ��������� ���� 2 � 3.
          */
        public int[][][] trp23NumSt = new int[0][0][0];

        /**
          * ��������������� ������ ��������� ���� 2 � 3.
          */
        public int[][][] suff23List = new int[0][0][0];

        /**
          * ����� ���������������� ������ ��������� ���� 2 � 3.
          */
        public int[][][] suff23ListSt = new int[0][0][0];

        /**
          * ������ ����� ���������������� ������ ��������� ���� 2 � 3.
          */
        public int[][][] suff23ListSt2 = new int[0][0][0];

        /**
          * ����� �������� ���������������� ������ ��������� ���� 2 � 3.
          */
        public int[][][] suff23ListNumSt = new int[0][0][0];

        /**
          * ��������������� ������ ��������� ���� 0.
          */
        public int[][][] suff0List = new int[0][0][0];

        /**
          * ����� ���������������� ������ ��������� ���� 0.
          */
        public int[][][] suff0ListSt = new int[0][0][0];

        /**
          * ���� ����� ���������������� ������ ��������� ���� 0.
          */
        public int[][][] suff0ListSt2 = new int[0][0][0];

        /**
          * ����� �������� ���������������� ������ ��������� ���� 0.
          */
        public int[][][] suff0ListNumSt = new int[0][0][0];

        /**
          * ������ ��������� ���� 2 � 3.
          */
        public int[][] suff23Num = new int[0][0];

        /**
          * ���������� ����� - ������� �������� (��������� Main).
          */
        public int Main_i;

        /**
          * ���������� ����� (��������� Main).
          */
        public int Main_j;

        /**
          * ���������� ����� - ��������� � ����� ���������� ������� (��������� Main).
          */
        public int Main_l;

        /**
          * ���������� ����� - ��������� � ������ ���������� ������� (��������� Main).
          */
        public int Main_r;

        /**
          * ��������� ���������� (��������� Main).
          */
        public int Main_tl;

        /**
          * ��������� ���������� (��������� Main).
          */
        public int Main_tr;

        /**
          * ���������� ����� - �������, �� ������� �� ���� ��� ��������� ��������� (��������� Main).
          */
        public int Main_sh;

        /**
          * ���������� ����� (��������� Main).
          */
        public boolean Main_goOn;

        public String toString() {
            StringBuffer s = new StringBuffer();
            return s.toString();
        }
    }

    /**
      * ������ ���������� ������.
      */
    private final class Main extends BaseAutomata implements Automata {
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
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                59, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������������", 
                    "������� ���� ��������", 
                    "������� ������", 
                    "�������� �� ��, ��� ��� ������� ��������", 
                    "�������� �� ��, ��� ��� ������� �������� (���������)", 
                    "���������� �����", 
                    "���������� ����� (���������)", 
                    "�����������", 
                    "���", 
                    "���������� �����", 
                    "���������� �����", 
                    "���� �� ������� ���� 1", 
                    "���������� ������ ����� ���� 1", 
                    "���� �� ������� ���� 2", 
                    "���������� ������ ����� ���� 2", 
                    "�����������", 
                    "����", 
                    "���������������� ���������� ����������� �������", 
                    "����", 
                    "������ ���� ��������", 
                    "???", 
                    "�����������", 
                    "���� ���������� �������� ������������", 
                    "???", 
                    "�����������", 
                    "����", 
                    "��� �����", 
                    "�������� ��������������� �������", 
                    "�����������", 
                    "���� ���������� ���", 
                    "���������� ��������� ����", 
                    "���������� ���", 
                    "�����������", 
                    "����", 
                    "???", 
                    "???", 
                    "???", 
                    "??? (���������)", 
                    "???", 
                    "����", 
                    "???", 
                    "����", 
                    "???", 
                    "��������� ��������", 
                    "��������� �������� (���������)", 
                    "�������� �� �������������� � ����� 1-2", 
                    "�������� �� �������������� � ����� 1-2 (���������)", 
                    "��������, �� ���������� �� ������", 
                    "��������, �� ���������� �� ������ (���������)", 
                    "???", 
                    "��������� ������� ���������", 
                    "��������� ������� ��������� (���������)", 
                    "???", 
                    "???", 
                    "��������� ��������", 
                    "���������� �������", 
                    "���������� �������", 
                    "�������� �������� ��������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    0, // ������������� 
                    -1, // ������� ���� �������� 
                    0, // ������� ������ 
                    0, // �������� �� ��, ��� ��� ������� �������� 
                    -1, // �������� �� ��, ��� ��� ������� �������� (���������) 
                    0, // ���������� ����� 
                    -1, // ���������� ����� (���������) 
                    0, // ����������� 
                    -2, // ��� 
                    0, // ���������� ����� 
                    0, // ���������� ����� 
                    -2, // ���� �� ������� ���� 1 
                    0, // ���������� ������ ����� ���� 1 
                    -2, // ���� �� ������� ���� 2 
                    0, // ���������� ������ ����� ���� 2 
                    0, // ����������� 
                    -1, // ���� 
                    0, // ���������������� ���������� ����������� ������� 
                    -1, // ���� 
                    0, // ������ ���� �������� 
                    0, // ??? 
                    0, // ����������� 
                    -1, // ���� ���������� �������� ������������ 
                    0, // ??? 
                    0, // ����������� 
                    -1, // ���� 
                    0, // ��� ����� 
                    0, // �������� ��������������� ������� 
                    0, // ����������� 
                    -1, // ���� ���������� ��� 
                    0, // ���������� ��������� ���� 
                    0, // ���������� ��� 
                    0, // ����������� 
                    -1, // ���� 
                    0, // ??? 
                    0, // ??? 
                    0, // ??? 
                    -1, // ??? (���������) 
                    0, // ??? 
                    -1, // ���� 
                    0, // ??? 
                    -2, // ���� 
                    0, // ??? 
                    0, // ��������� �������� 
                    -1, // ��������� �������� (���������) 
                    0, // �������� �� �������������� � ����� 1-2 
                    -1, // �������� �� �������������� � ����� 1-2 (���������) 
                    0, // ��������, �� ���������� �� ������ 
                    -1, // ��������, �� ���������� �� ������ (���������) 
                    0, // ??? 
                    0, // ��������� ������� ��������� 
                    -1, // ��������� ������� ��������� (���������) 
                    0, // ??? 
                    0, // ??? 
                    0, // ��������� �������� 
                    -1, // ���������� ������� 
                    0, // ���������� ������� 
                    0, // �������� �������� �������� 
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
                    state = 2; // ������� ���� ��������
                    break;
                }
                case 2: { // ������� ���� ��������
                    if (d.Main_i < d.recStr.length) {
                        state = 3; // ������� ������
                    } else {
                        stack.pushBoolean(false); 
                        state = 17; // ����
                    }
                    break;
                }
                case 3: { // ������� ������
                    state = 4; // �������� �� ��, ��� ��� ������� ��������
                    break;
                }
                case 4: { // �������� �� ��, ��� ��� ������� ��������
                    if (d.Main_i < d.recStr.length) {
                        state = 6; // ���������� �����
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // �������� �� ��, ��� ��� ������� �������� (���������)
                    }
                    break;
                }
                case 5: { // �������� �� ��, ��� ��� ������� �������� (���������)
                    stack.pushBoolean(true); 
                    state = 2; // ������� ���� ��������
                    break;
                }
                case 6: { // ���������� �����
                    if (d.recStr[d.Main_i - 1][d.recStr[d.Main_i - 1].length - 1] == 0) {
                        state = 8; // �����������
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // ���������� ����� (���������)
                    }
                    break;
                }
                case 7: { // ���������� ����� (���������)
                    stack.pushBoolean(false); 
                    state = 9; // ���
                    break;
                }
                case 8: { // �����������
                    stack.pushBoolean(true); 
                    state = 7; // ���������� ����� (���������)
                    break;
                }
                case 9: { // ���
                    if (d.Main_j < d.tripplesSt[d.Main_i - 1].length) {
                        state = 10; // ���������� �����
                    } else {
                        state = 11; // ���������� �����
                    }
                    break;
                }
                case 10: { // ���������� �����
                    stack.pushBoolean(true); 
                    state = 9; // ���
                    break;
                }
                case 11: { // ���������� �����
                    stack.pushBoolean(false); 
                    state = 12; // ���� �� ������� ���� 1
                    break;
                }
                case 12: { // ���� �� ������� ���� 1
                    if (d.Main_j < (d.tripplesNumSt[d.Main_i - 1].length + 1) / 2) {
                        state = 13; // ���������� ������ ����� ���� 1
                    } else {
                        stack.pushBoolean(false); 
                        state = 14; // ���� �� ������� ���� 2
                    }
                    break;
                }
                case 13: { // ���������� ������ ����� ���� 1
                    stack.pushBoolean(true); 
                    state = 12; // ���� �� ������� ���� 1
                    break;
                }
                case 14: { // ���� �� ������� ���� 2
                    if (d.Main_j < d.tripplesNumSt[d.Main_i - 1].length) {
                        state = 15; // ���������� ������ ����� ���� 2
                    } else {
                        state = 16; // �����������
                    }
                    break;
                }
                case 15: { // ���������� ������ ����� ���� 2
                    stack.pushBoolean(true); 
                    state = 14; // ���� �� ������� ���� 2
                    break;
                }
                case 16: { // �����������
                    stack.pushBoolean(true); 
                    state = 5; // �������� �� ��, ��� ��� ������� �������� (���������)
                    break;
                }
                case 17: { // ����
                    if (d.Main_j < d.lastSuffArrSt.length) {
                        state = 18; // ���������������� ���������� ����������� �������
                    } else {
                        stack.pushBoolean(false); 
                        state = 19; // ����
                    }
                    break;
                }
                case 18: { // ���������������� ���������� ����������� �������
                    stack.pushBoolean(true); 
                    state = 17; // ����
                    break;
                }
                case 19: { // ����
                    if (d.Main_i > 1) {
                        state = 20; // ������ ���� ��������
                    } else {
                        state = 58; // �������� �������� ��������
                    }
                    break;
                }
                case 20: { // ������ ���� ��������
                    state = 21; // ???
                    break;
                }
                case 21: { // ???
                    state = 22; // �����������
                    break;
                }
                case 22: { // �����������
                    stack.pushBoolean(false); 
                    state = 23; // ���� ���������� �������� ������������
                    break;
                }
                case 23: { // ���� ���������� �������� ������������
                    if (d.Main_j < d.invSuffArr[d.Main_i].length) {
                        state = 24; // ???
                    } else {
                        state = 25; // �����������
                    }
                    break;
                }
                case 24: { // ???
                    stack.pushBoolean(true); 
                    state = 23; // ���� ���������� �������� ������������
                    break;
                }
                case 25: { // �����������
                    stack.pushBoolean(false); 
                    state = 26; // ����
                    break;
                }
                case 26: { // ����
                    if (d.Main_j < d.trp23Num[d.Main_i].length) {
                        state = 27; // ��� �����
                    } else {
                        state = 28; // �������� ��������������� �������
                    }
                    break;
                }
                case 27: { // ��� �����
                    stack.pushBoolean(true); 
                    state = 26; // ����
                    break;
                }
                case 28: { // �������� ��������������� �������
                    state = 29; // �����������
                    break;
                }
                case 29: { // �����������
                    stack.pushBoolean(false); 
                    state = 30; // ���� ���������� ���
                    break;
                }
                case 30: { // ���� ���������� ���
                    if (d.Main_j < d.pairs[d.Main_i].length) {
                        state = 31; // ���������� ��������� ����
                    } else {
                        state = 32; // ���������� ���
                    }
                    break;
                }
                case 31: { // ���������� ��������� ����
                    stack.pushBoolean(true); 
                    state = 30; // ���� ���������� ���
                    break;
                }
                case 32: { // ���������� ���
                    state = 33; // �����������
                    break;
                }
                case 33: { // �����������
                    stack.pushBoolean(false); 
                    state = 34; // ����
                    break;
                }
                case 34: { // ����
                    if (d.Main_j < d.suff0List[d.Main_i].length) {
                        state = 35; // ???
                    } else {
                        state = 36; // ???
                    }
                    break;
                }
                case 35: { // ???
                    stack.pushBoolean(true); 
                    state = 34; // ����
                    break;
                }
                case 36: { // ???
                    state = 37; // ???
                    break;
                }
                case 37: { // ???
                    if (d.recStr[d.Main_i - 1][d.recStr[d.Main_i - 1].length - 1] == 0) {
                        state = 39; // ???
                    } else {
                        stack.pushBoolean(false); 
                        state = 38; // ??? (���������)
                    }
                    break;
                }
                case 38: { // ??? (���������)
                    stack.pushBoolean(false); 
                    state = 40; // ����
                    break;
                }
                case 39: { // ???
                    stack.pushBoolean(true); 
                    state = 38; // ??? (���������)
                    break;
                }
                case 40: { // ����
                    if (d.Main_l < d.suff0List[d.Main_i].length && d.Main_r < d.suff23List[d.Main_i].length) {
                        state = 41; // ???
                    } else {
                        stack.pushBoolean(false); 
                        state = 56; // ���������� �������
                    }
                    break;
                }
                case 41: { // ???
                    stack.pushBoolean(false); 
                    state = 42; // ����
                    break;
                }
                case 42: { // ����
                    if (d.Main_goOn == true) {
                        state = 43; // ???
                    } else {
                        stack.pushBoolean(true); 
                        state = 40; // ����
                    }
                    break;
                }
                case 43: { // ???
                    state = 44; // ��������� ��������
                    break;
                }
                case 44: { // ��������� ��������
                    if (d.recStr[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] == d.recStr[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        state = 46; // �������� �� �������������� � ����� 1-2
                    } else {
                        state = 55; // ��������� ��������
                    }
                    break;
                }
                case 45: { // ��������� �������� (���������)
                    stack.pushBoolean(true); 
                    state = 42; // ����
                    break;
                }
                case 46: { // �������� �� �������������� � ����� 1-2
                    if ((d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh) % 3 == 0 || (d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh) % 3 == 0) {
                        state = 48; // ��������, �� ���������� �� ������
                    } else {
                        state = 51; // ��������� ������� ���������
                    }
                    break;
                }
                case 47: { // �������� �� �������������� � ����� 1-2 (���������)
                    stack.pushBoolean(true); 
                    state = 45; // ��������� �������� (���������)
                    break;
                }
                case 48: { // ��������, �� ���������� �� ������
                    if (d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh + 1 == d.recStr[d.Main_i - 1].length || d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh + 1 == d.recStr[d.Main_i - 1].length) {
                        state = 50; // ???
                    } else {
                        stack.pushBoolean(false); 
                        state = 49; // ��������, �� ���������� �� ������ (���������)
                    }
                    break;
                }
                case 49: { // ��������, �� ���������� �� ������ (���������)
                    stack.pushBoolean(true); 
                    state = 47; // �������� �� �������������� � ����� 1-2 (���������)
                    break;
                }
                case 50: { // ???
                    stack.pushBoolean(true); 
                    state = 49; // ��������, �� ���������� �� ������ (���������)
                    break;
                }
                case 51: { // ��������� ������� ���������
                    if (d.suff23Num[d.Main_i][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] < d.suff23Num[d.Main_i][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        state = 53; // ???
                    } else {
                        state = 54; // ???
                    }
                    break;
                }
                case 52: { // ��������� ������� ��������� (���������)
                    stack.pushBoolean(false); 
                    state = 47; // �������� �� �������������� � ����� 1-2 (���������)
                    break;
                }
                case 53: { // ???
                    stack.pushBoolean(true); 
                    state = 52; // ��������� ������� ��������� (���������)
                    break;
                }
                case 54: { // ???
                    stack.pushBoolean(false); 
                    state = 52; // ��������� ������� ��������� (���������)
                    break;
                }
                case 55: { // ��������� ��������
                    stack.pushBoolean(false); 
                    state = 45; // ��������� �������� (���������)
                    break;
                }
                case 56: { // ���������� �������
                    if (d.Main_l < d.suff0List[d.Main_i].length || d.Main_r < d.suff23List[d.Main_i].length) {
                        state = 57; // ���������� �������
                    } else {
                        stack.pushBoolean(true); 
                        state = 19; // ����
                    }
                    break;
                }
                case 57: { // ���������� �������
                    stack.pushBoolean(true); 
                    state = 56; // ���������� �������
                    break;
                }
                case 58: { // �������� �������� ��������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������
                    startSection();
                     storeField(d, "Main_i");
                     d.Main_i = 0;
                     for (int ti = 0; ti < d.recStrSt[d.Main_i].length; ti++) {
                         storeArray(d.recStrSt[d.Main_i], ti);
                         d.recStrSt[d.Main_i][ti] = 1 + ti % 3;
                         storeArray(d.recStrNumSt[d.Main_i], ti);
                         d.recStrNumSt[d.Main_i][ti] = 1;
                     }
                     
                     if (d.recStr[d.Main_i][d.recStr[d.Main_i].length - 1] == 0) {
                         storeArray(d.recStrSt[d.Main_i], d.recStr[d.Main_i].length - 1);
                         d.recStrSt[d.Main_i][d.recStr[d.Main_i].length - 1] = -1;
                         storeArray(d.recStrNumSt[d.Main_i], d.recStr[d.Main_i].length - 1);
                         d.recStrNumSt[d.Main_i][d.recStr[d.Main_i].length - 1] = -1;
                    }
                    break;
                }
                case 2: { // ������� ���� ��������
                    break;
                }
                case 3: { // ������� ������
                    startSection();
                    if (d.Main_i > 0) {
                        for (int tj = 0; tj < d.tripplesSt[d.Main_i - 1].length; tj++) {
                            for (int tk = 0; tk < d.tripplesSt[d.Main_i - 1][tj].length; tk++) {
                                storeArray(d.tripplesSt[d.Main_i - 1][tj], tk);
                                d.tripplesSt[d.Main_i - 1][tj][tk] = -1;
                            }                            
                        }
                        for (int ti = 0; ti < d.tripplesNumSt[d.Main_i - 1].length; ti++) {
                            for (int tk = 0; tk < d.tripplesNumSt[d.Main_i - 1][ti].length; tk++) {
                                storeArray(d.tripplesNumSt[d.Main_i - 1][ti], tk);
                                d.tripplesNumSt[d.Main_i - 1][ti][tk] = -1;   
                            }
                        }
                        for (int ti = 0; ti < d.recS1St[d.Main_i].length; ti++) {
                            storeArray(d.recS1St[d.Main_i], ti);
                            d.recS1St[d.Main_i][ti] = -1;       
                        }                        
                    }
                    for (int ti = 0; ti < d.recStrSt[d.Main_i].length; ti++) {
                        storeArray(d.recStrSt[d.Main_i], ti);
                        d.recStrSt[d.Main_i][ti] = 1 + ti % 3;    
                        storeArray(d.recStrNumSt[d.Main_i], ti);
                        d.recStrNumSt[d.Main_i][ti] = 1;
                    }                    
                    
                    if (d.recStr[d.Main_i][d.recStr[d.Main_i].length - 1] == 0) {
                        storeArray(d.recStrSt[d.Main_i], d.recStr[d.Main_i].length - 1);
                        d.recStrSt[d.Main_i][d.recStr[d.Main_i].length - 1] = -1;
                        storeArray(d.recStrNumSt[d.Main_i], d.recStr[d.Main_i].length - 1);
                        d.recStrNumSt[d.Main_i][d.recStr[d.Main_i].length - 1] = -1;
                    }
                    storeField(d, "Main_i");
                    d.Main_i = d.Main_i + 1;
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    break;
                }
                case 4: { // �������� �� ��, ��� ��� ������� ��������
                    break;
                }
                case 5: { // �������� �� ��, ��� ��� ������� �������� (���������)
                    break;
                }
                case 6: { // ���������� �����
                    break;
                }
                case 7: { // ���������� ����� (���������)
                    break;
                }
                case 8: { // �����������
                    startSection();
                    storeArray(d.recStrSt[d.Main_i - 1], d.recStrSt[d.Main_i - 1].length - 1);
                    d.recStrSt[d.Main_i - 1][d.recStrSt[d.Main_i - 1].length - 1] = 1 + (d.recStrSt[d.Main_i - 1].length - 1) % 3;
                    storeArray(d.recStrNumSt[d.Main_i - 1], d.recStrSt[d.Main_i - 1].length - 1);
                    d.recStrNumSt[d.Main_i - 1][d.recStrSt[d.Main_i - 1].length - 1] = 1;    
                    break;
                }
                case 9: { // ���
                    break;
                }
                case 10: { // ���������� �����
                    startSection();
                    int col = 2 + (d.Main_j % 2);
                    for (int ti = 0; ti < d.tripplesSt[d.Main_i - 1][d.Main_j].length; ti++) {
                        storeArray(d.tripplesSt[d.Main_i - 1][d.Main_j], ti);
                        d.tripplesSt[d.Main_i - 1][d.Main_j][ti] = col;        
                    }
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    break;
                }
                case 11: { // ���������� �����
                    startSection();
                    for (int ti = 0; ti < d.tripplesNumSt[d.Main_i - 1].length; ti++) {
                        storeArray(d.tripplesNumSt[d.Main_i - 1][ti], 0);
                        d.tripplesNumSt[d.Main_i - 1][ti][0] = 2 + (ti % 2);
                    }
                    d.Main_j = 0;                                                         
                    break;
                }
                case 12: { // ���� �� ������� ���� 1
                    break;
                }
                case 13: { // ���������� ������ ����� ���� 1
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.tripplesNumSt[d.Main_i - 1][(d.Main_j - 1) * 2], 0);
                        d.tripplesNumSt[d.Main_i - 1][(d.Main_j - 1) * 2][0] = 2;
                        storeArray(d.recS1St[d.Main_i], d.Main_j - 1);
                        d.recS1St[d.Main_i][d.Main_j - 1] = 2;
                    }
                    storeArray(d.tripplesNumSt[d.Main_i - 1][d.Main_j * 2], 0);
                    d.tripplesNumSt[d.Main_i - 1][d.Main_j * 2][0] = 4;
                    
                    
                    storeArray(d.recS1St[d.Main_i], d.Main_j);
                    d.recS1St[d.Main_i][d.Main_j] = 4;
                    
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    break;
                }
                case 14: { // ���� �� ������� ���� 2
                    break;
                }
                case 15: { // ���������� ������ ����� ���� 2
                    startSection();
                    if (d.Main_j > (d.tripplesNumSt[d.Main_i - 1].length + 1) / 2) {
                        storeArray(d.tripplesNumSt[d.Main_i - 1][(d.Main_j - 1 - ((d.tripplesNumSt[d.Main_i - 1].length + 1) / 2)) * 2 + 1], 0);
                        d.tripplesNumSt[d.Main_i - 1][(d.Main_j - 1 - ((d.tripplesNumSt[d.Main_i - 1].length + 1) / 2)) * 2 + 1][0] = 3;
                        storeArray(d.recS1St[d.Main_i], d.Main_j - 1);
                        d.recS1St[d.Main_i][d.Main_j - 1] = 3;
                    } else {
                        storeArray(d.tripplesNumSt[d.Main_i - 1][2 * d.Main_j - 2], 0);
                        d.tripplesNumSt[d.Main_i - 1][2 * d.Main_j - 2][0] = 2;
                        storeArray(d.recS1St[d.Main_i], d.Main_j - 1);
                        d.recS1St[d.Main_i][d.Main_j - 1] = 2;
                    }
                    storeArray(d.tripplesNumSt[d.Main_i - 1][(d.Main_j - ((d.tripplesNumSt[d.Main_i - 1].length + 1) / 2)) * 2 + 1], 0);
                    d.tripplesNumSt[d.Main_i - 1][(d.Main_j - ((d.tripplesNumSt[d.Main_i - 1].length + 1) / 2)) * 2 + 1][0] = 4;
                    
                    storeArray(d.recS1St[d.Main_i], d.Main_j);
                    d.recS1St[d.Main_i][d.Main_j] = 4;
                    
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    break;
                }
                case 16: { // �����������
                    startSection();
                    storeArray(d.recS1St[d.Main_i], d.Main_j - 1);
                    d.recS1St[d.Main_i][d.Main_j - 1] = 3;
                    storeArray(d.tripplesNumSt[d.Main_i - 1][d.tripplesNumSt[d.Main_i - 1].length - 1 - (d.tripplesNumSt[d.Main_i - 1].length % 2)], 0);
                    d.tripplesNumSt[d.Main_i - 1][d.tripplesNumSt[d.Main_i - 1].length - 1 - (d.tripplesNumSt[d.Main_i - 1].length % 2)][0] = 3;
                    break;
                }
                case 17: { // ����
                    break;
                }
                case 18: { // ���������������� ���������� ����������� �������
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.recStrSt[d.Main_i - 1], d.Main_j - 1);
                        d.recStrSt[d.Main_i - 1][d.Main_j - 1] = 1 + (d.Main_j % 3);
                        storeArray(d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j - 1] - 1], 0);
                        d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j - 1] - 1][0] = 1;
                    }
                    storeArray(d.recStrSt[d.Main_i - 1], d.Main_j);
                    d.recStrSt[d.Main_i - 1][d.Main_j] = 4;
                    storeArray(d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j] - 1], 0);
                    d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j] - 1][0] = 4;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 19: { // ����
                    break;
                }
                case 20: { // ������ ���� ��������
                    startSection();
                    if (d.Main_i == d.recStr.length) {
                        storeArray(d.recStrSt[d.Main_i - 1], d.Main_j - 1);
                        d.recStrSt[d.Main_i - 1][d.Main_j - 1] = 1 + (d.Main_j % 3);
                        storeArray(d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j - 1] - 1], 0);
                        d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j - 1] - 1][0] = 1;    
                    }
                    if (d.Main_i < d.suff0ListSt.length) {
                        storeArray(d.suff0ListSt2[d.Main_i][d.suff0ListSt2[d.Main_i].length - 1], 0);
                        d.suff0ListSt2[d.Main_i][d.suff0ListSt2[d.Main_i].length - 1][0] = 1;
                        storeArray(d.suff23ListSt2[d.Main_i][d.suff23ListSt2[d.Main_i].length - 1], 0);
                        d.suff23ListSt2[d.Main_i][d.suff23ListSt2[d.Main_i].length - 1][0] = 1;
                    }
                    break;
                }
                case 21: { // ???
                    startSection();
                    for (int ti = 0; ti < d.recStrSt[d.Main_i - 1].length; ti++) {
                        storeArray(d.recStrSt[d.Main_i - 1], ti);
                        d.recStrSt[d.Main_i - 1][ti] = -1;    
                        storeArray(d.recStrNumSt[d.Main_i - 1], ti);
                        d.recStrNumSt[d.Main_i - 1][ti] = -1;
                    }
                    if (d.Main_i < d.suff0ListSt2.length) {
                        for (int ti = 0; ti < d.suff0ListSt2[d.Main_i].length; ti++) {
                            storeArray(d.suff0ListSt2[d.Main_i][ti], 0);
                            d.suff0ListSt2[d.Main_i][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.suff23ListSt2[d.Main_i].length; ti++) {
                            storeArray(d.suff23ListSt2[d.Main_i][ti], 0);
                            d.suff23ListSt2[d.Main_i][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.trp23NumSt[d.Main_i].length; ti++) {
                            storeArray(d.trp23NumSt[d.Main_i][ti], 0);
                            d.trp23NumSt[d.Main_i][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.suffArrSt[d.Main_i - 1].length; ti++) {
                            storeArray(d.suffArrSt2[d.Main_i - 1], ti);
                            d.suffArrSt2[d.Main_i - 1][ti] = -1;
                        }
                    }
                    if (d.Main_i < d.suffArr.length) {                        
                        storeArray(d.suffArrSt[d.Main_i], 0);
                        d.suffArrSt[d.Main_i][0] = -1;                        
                    } else {
                        for (int ti = 0; ti < d.lastSuffArrSt.length; ti++) {
                            storeArray(d.lastSuffArrSt[ti], 0);
                            d.lastSuffArrSt[ti][0] = -1;
                        }
                    }
                                     
                    storeField(d, "Main_i");
                    d.Main_i = d.Main_i - 1;
                    for (int ti = 0; ti < d.suffArrSt[d.Main_i].length; ti++) {
                        storeArray(d.suffArrSt[d.Main_i], ti);
                        d.suffArrSt[d.Main_i][ti] = 1;
                    }                   
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    break;
                }
                case 22: { // �����������
                    startSection();
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    for (int ti = 0; ti < d.invSuffArrSt[d.Main_i].length; ti++) {
                        storeArray(d.invSuffArrNumSt[d.Main_i][ti], 0);
                        d.invSuffArrNumSt[d.Main_i][ti][0] = 1;
                    }
                    break;
                }
                case 23: { // ���� ���������� �������� ������������
                    break;
                }
                case 24: { // ???
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.suffArrSt[d.Main_i], d.Main_j - 1);
                        d.suffArrSt[d.Main_i][d.Main_j - 1] = 1;
                        storeArray(d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j - 1]], 0);
                        d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j - 1]][0] = 1;
                    }
                    storeArray(d.suffArrSt[d.Main_i], d.Main_j);
                    d.suffArrSt[d.Main_i][d.Main_j] = 4;
                    storeArray(d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j]], 0);
                    d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j]][0] = 4;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 25: { // �����������
                    startSection();
                    storeArray(d.suffArrSt[d.Main_i], d.Main_j - 1);
                    d.suffArrSt[d.Main_i][d.Main_j - 1] = 1;        
                    storeArray(d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j - 1]], 0);
                    d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j - 1]][0] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    for (int ti = 0; ti < d.suff23ListNumSt[d.Main_i].length; ti++) {
                        storeArray(d.suff23ListNumSt[d.Main_i][ti], 0);
                        d.suff23ListNumSt[d.Main_i][ti][0] = 1;
                    }
                    break;
                }
                case 26: { // ����
                    break;
                }
                case 27: { // ��� �����
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.invSuffArrSt[d.Main_i][d.Main_j - 1], 0);
                        d.invSuffArrSt[d.Main_i][d.Main_j - 1][0] = 1;
                        storeArray(d.trp23NumSt[d.Main_i][d.Main_j - 1], 0);
                        d.trp23NumSt[d.Main_i][d.Main_j - 1][0] = 1;
                        storeArray(d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]], 0);
                        d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]][0] = 1;
                    }
                    storeArray(d.invSuffArrSt[d.Main_i][d.Main_j], 0);
                    d.invSuffArrSt[d.Main_i][d.Main_j][0] = 4;
                    storeArray(d.trp23NumSt[d.Main_i][d.Main_j], 0);
                    d.trp23NumSt[d.Main_i][d.Main_j][0] = 4;
                    storeArray(d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j][0]], 0);
                    d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j][0]][0] = 4;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 28: { // �������� ��������������� �������
                    startSection();
                    storeArray(d.invSuffArrSt[d.Main_i][d.Main_j - 1], 0);
                    d.invSuffArrSt[d.Main_i][d.Main_j - 1][0] = 1;       
                    storeArray(d.trp23NumSt[d.Main_i][d.Main_j - 1], 0);
                    d.trp23NumSt[d.Main_i][d.Main_j - 1][0] = 1;
                    storeArray(d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]], 0);
                    d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]][0] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    for (int ti = 0; ti < d.suff23List[d.Main_i].length; ti++) {
                        storeArray(d.suff23ListSt[d.Main_i][ti], 0);
                        d.suff23ListSt[d.Main_i][ti][0] = -1;
                        storeArray(d.suff23ListSt2[d.Main_i][ti], 0);
                        d.suff23ListSt2[d.Main_i][ti][0] = 1;
                        storeArray(d.suff23ListNumSt[d.Main_i][ti], 0);
                        d.suff23ListNumSt[d.Main_i][ti][0] = -1;
                        storeArray(d.invSuffArrSt[d.Main_i][ti], 0);
                        d.invSuffArrSt[d.Main_i][ti][0] = -1;
                        storeArray(d.invSuffArrNumSt[d.Main_i][ti], 0);
                        d.invSuffArrNumSt[d.Main_i][ti][0] = -1;
                        storeArray(d.suffArrSt[d.Main_i], ti);
                        d.suffArrSt[d.Main_i][ti] = -1;
                    }
                    break;
                }
                case 29: { // �����������
                    startSection();
                    break;
                }
                case 30: { // ���� ���������� ���
                    break;
                }
                case 31: { // ���������� ��������� ����
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.recStrSt[d.Main_i - 1], 3 * (d.Main_j - 1));
                        d.recStrSt[d.Main_i - 1][3 * (d.Main_j - 1)] = 1;
                        storeArray(d.trp23NumSt[d.Main_i][d.Main_j - 1], 0);
                        d.trp23NumSt[d.Main_i][d.Main_j - 1][0] = 1;
                    }
                    storeArray(d.recStrSt[d.Main_i - 1], 3 * d.Main_j);
                    d.recStrSt[d.Main_i - 1][3 * d.Main_j] = 4;
                    storeArray(d.trp23NumSt[d.Main_i][d.Main_j], 0);
                    d.trp23NumSt[d.Main_i][d.Main_j][0] = 4;
                    for (int ti = 0; ti < d.pairsSt[d.Main_i][d.Main_j].length; ti++) {
                        storeArray(d.pairsSt[d.Main_i][d.Main_j], ti);
                        d.pairsSt[d.Main_i][d.Main_j][ti] = 1;
                    }
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 32: { // ���������� ���
                    startSection();
                    storeArray(d.recStrSt[d.Main_i - 1], 3 * (d.Main_j - 1));
                    d.recStrSt[d.Main_i - 1][3 * (d.Main_j - 1)] = 1;
                    storeArray(d.trp23NumSt[d.Main_i][d.Main_j - 1], 0);
                    d.trp23NumSt[d.Main_i][d.Main_j - 1][0] = 1;         
                    for (int ti = 0; ti < d.pairsNumSt[d.Main_i].length; ti++) {
                        storeArray(d.pairsNumSt[d.Main_i][ti], 0);
                        d.pairsNumSt[d.Main_i][ti][0] = 1;
                    }
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    break;
                }
                case 33: { // �����������
                    startSection();
                    for (int ti = 0; ti < d.suff0ListNumSt[d.Main_i].length; ti++) {
                        storeArray(d.suff0ListNumSt[d.Main_i][ti], 0);
                        d.suff0ListNumSt[d.Main_i][ti][0] = 1;
                    }
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    break;
                }
                case 34: { // ����
                    break;
                }
                case 35: { // ???
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.pairsSt[d.Main_i][d.Main_j - 1], 0);
                        d.pairsSt[d.Main_i][d.Main_j - 1][0] = 1;
                        storeArray(d.pairsSt[d.Main_i][d.Main_j - 1], 1);
                        d.pairsSt[d.Main_i][d.Main_j - 1][1] = 1;
                        storeArray(d.pairsNumSt[d.Main_i][d.Main_j - 1], 0);
                        d.pairsNumSt[d.Main_i][d.Main_j - 1][0] = 1;
                        storeArray(d.recStrSt[d.Main_i - 1], 3 * (d.Main_j - 1));
                        d.recStrSt[d.Main_i - 1][3 * (d.Main_j - 1)] = 1;
                        storeArray(d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][d.Main_j - 1][0]], 0);
                        d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][d.Main_j - 1][0]][0] = 1;
                    }
                    storeArray(d.pairsSt[d.Main_i][d.Main_j], 0);
                    d.pairsSt[d.Main_i][d.Main_j][0] = 4;
                    storeArray(d.pairsSt[d.Main_i][d.Main_j], 1);
                    d.pairsSt[d.Main_i][d.Main_j][1] = 4;
                    storeArray(d.pairsNumSt[d.Main_i][d.Main_j], 0);
                    d.pairsNumSt[d.Main_i][d.Main_j][0] = 4;
                    storeArray(d.recStrSt[d.Main_i - 1], 3 * (d.Main_j));
                    d.recStrSt[d.Main_i - 1][3 * (d.Main_j)] = 4;
                    storeArray(d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][d.Main_j][0]], 0);
                    d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][d.Main_j][0]][0] = 4;                        
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 36: { // ???
                    startSection();
                    int pj = d.pairs[d.Main_i].length;
                    storeArray(d.pairsSt[d.Main_i][pj - 1], 0);
                    d.pairsSt[d.Main_i][pj - 1][0] = 1;
                    storeArray(d.pairsSt[d.Main_i][pj - 1], 1);
                    d.pairsSt[d.Main_i][pj - 1][1] = 1;
                    storeArray(d.pairsNumSt[d.Main_i][pj - 1], 0);
                    d.pairsNumSt[d.Main_i][pj - 1][0] = 1;
                    storeArray(d.recStrSt[d.Main_i - 1], 3 * (pj - 1));
                    d.recStrSt[d.Main_i - 1][3 * (pj - 1)] = 1;
                    storeArray(d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][pj - 1][0]], 0);
                    d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][pj - 1][0]][0] = 1;      
                    
                    for (int ti = 0; ti < d.pairsNum[d.Main_i].length; ti++) {
                        storeArray(d.pairsNumSt[d.Main_i][ti], 0);
                        d.pairsNumSt[d.Main_i][ti][0] = -1;
                        for (int tj = 0; tj < d.pairsSt[d.Main_i][ti].length; tj++) {
                            storeArray(d.pairsSt[d.Main_i][ti], tj);
                            d.pairsSt[d.Main_i][ti][tj] = -1;
                        }
                    }
                    
                    for (int ti = 0; ti < d.suff0List[d.Main_i].length; ti++) {
                        storeArray(d.suff0ListSt[d.Main_i][ti], 0);
                        d.suff0ListSt[d.Main_i][ti][0] = -1;
                        storeArray(d.suff0ListSt2[d.Main_i][ti], 0);
                        d.suff0ListSt2[d.Main_i][ti][0] = 1;
                        storeArray(d.suff0ListNumSt[d.Main_i][ti], 0);
                        d.suff0ListNumSt[d.Main_i][ti][0] = -1;
                    }
                    
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    storeField(d, "Main_l");
                    d.Main_l = 0;
                    storeField(d, "Main_r");
                    d.Main_r = 0;
                    storeArray(d.suff0ListSt2[d.Main_i][d.Main_l], 0);
                    d.suff0ListSt2[d.Main_i][d.Main_l][0] = 4;
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l][0]);
                    d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0]] = 4;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0]);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0]] = 5;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = true;
                    storeField(d, "Main_sh");
                    d.Main_sh = -1;
                    break;
                }
                case 37: { // ???
                    break;
                }
                case 38: { // ??? (���������)
                    break;
                }
                case 39: { // ???
                    startSection();
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0]);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0]] = 1 + (d.suff23List[d.Main_i][d.Main_r][0] % 3);
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 1;
                    storeField(d, "Main_r");
                    d.Main_r = d.Main_r + 1;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0]);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0]] = 5;
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    break;
                }
                case 40: { // ����
                    break;
                }
                case 41: { // ???
                    startSection();
                    for (int ti = 0; ti < d.recStrSt[d.Main_i - 1].length; ti++) {
                         storeArray(d.recStrSt[d.Main_i - 1], ti);
                         d.recStrSt[d.Main_i - 1][ti] = 1 + (ti % 3);
                    }
                        
                    if (d.Main_l > 0) {
                        storeArray(d.suff0ListSt2[d.Main_i][d.Main_l - 1], 0);
                        d.suff0ListSt2[d.Main_i][d.Main_l - 1][0] = 1;           
                    }       
                    
                    if (d.Main_r > 0) {
                        storeArray(d.suff23ListSt2[d.Main_i][d.Main_r - 1], 0);
                        d.suff23ListSt2[d.Main_i][d.Main_r - 1][0] = 1;           
                    }
                    
                    storeArray(d.suff0ListSt2[d.Main_i][d.Main_l], 0);
                    d.suff0ListSt2[d.Main_i][d.Main_l][0] = 4;
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l][0]);
                    d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0]] = 4;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0]);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0]] = 5;
                    storeField(d, "Main_sh");
                    d.Main_sh = -1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = true;
                    break;
                }
                case 42: { // ����
                    break;
                }
                case 43: { // ???
                    startSection();
                    for (int ti = 0; ti < d.recStrSt[d.Main_i - 1].length; ti++) {
                        storeArray(d.recStrSt[d.Main_i - 1], ti);
                        d.recStrSt[d.Main_i - 1][ti] = 1 + (ti % 3);
                    }
                    storeField(d, "Main_sh");
                    d.Main_sh = d.Main_sh + 1;
                    
                    if (d.Main_l > 0) {
                        storeArray(d.suff0ListSt2[d.Main_i][d.Main_l - 1], 0);
                        d.suff0ListSt2[d.Main_i][d.Main_l - 1][0] = 1;           
                    }       
                    
                    if (d.Main_r > 0) {
                        storeArray(d.suff23ListSt2[d.Main_i][d.Main_r - 1], 0);
                        d.suff23ListSt2[d.Main_i][d.Main_r - 1][0] = 1;           
                    }
                    
                    storeArray(d.suff0ListSt2[d.Main_i][d.Main_l], 0);
                    d.suff0ListSt2[d.Main_i][d.Main_l][0] = 4;       
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh);
                    d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] = 4;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh] = 5;
                    
                    storeField(d, "Main_goOn");
                    d.Main_goOn = true;
                    break;
                }
                case 44: { // ��������� ��������
                    break;
                }
                case 45: { // ��������� �������� (���������)
                    break;
                }
                case 46: { // �������� �� �������������� � ����� 1-2
                    break;
                }
                case 47: { // �������� �� �������������� � ����� 1-2 (���������)
                    break;
                }
                case 48: { // ��������, �� ���������� �� ������
                    break;
                }
                case 49: { // ��������, �� ���������� �� ������ (���������)
                    break;
                }
                case 50: { // ???
                    startSection();
                    if (d.suff0List[d.Main_i][d.Main_l][0] + 1 == d.recStr[d.Main_i - 1].length) {
                        storeField(d, "Main_r");
                        d.Main_r = d.Main_r + 1;
                    } else {
                        storeField(d, "Main_l");
                        d.Main_l = d.Main_l + 1;
                    }
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = false;
                    break;
                }
                case 51: { // ��������� ������� ���������
                    break;
                }
                case 52: { // ��������� ������� ��������� (���������)
                    break;
                }
                case 53: { // ???
                    startSection();
                    storeField(d, "Main_l");
                    d.Main_l = d.Main_l + 1;
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = false;
                    break;
                }
                case 54: { // ???
                    startSection();
                    storeField(d, "Main_r");
                    d.Main_r = d.Main_r + 1;
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = false;
                    break;
                }
                case 55: { // ��������� ��������
                    startSection();
                    storeField(d, "Main_tl");
                    d.Main_tl = d.Main_l;
                    storeField(d, "Main_tr");
                    d.Main_tr = d.Main_r;
                    if (d.recStr[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] < d.recStr[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        storeField(d, "Main_l");
                        d.Main_l = d.Main_l + 1;
                    } else {
                        storeField(d, "Main_r");
                        d.Main_r = d.Main_r + 1;
                    }
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = false;
                    break;
                }
                case 56: { // ���������� �������
                    break;
                }
                case 57: { // ���������� �������
                    startSection();
                    if (d.Main_l < d.suff0List[d.Main_i].length) {
                         storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh);
                         d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] = 1;
                    }
                    
                    if (d.Main_l - 1 < d.suff0List[d.Main_i].length && d.Main_l > 0) { 
                        storeArray(d.suff0ListSt2[d.Main_i][d.Main_l - 1], 0);
                        d.suff0ListSt2[d.Main_i][d.Main_l - 1][0] = 1;
                        storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l - 1][0] + d.Main_sh);
                        d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l - 1][0] + d.Main_sh] = 1;
                    }
                    
                    if (d.Main_r < d.suff23List[d.Main_i].length) {
                        storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh);
                        d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh] = 1 + ((d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh) % 3); 
                    }
                    if (d.Main_r - 1 < d.suff23List[d.Main_i].length && d.Main_r > 0) {
                        storeArray(d.suff23ListSt2[d.Main_i][d.Main_r - 1], 0);
                        d.suff23ListSt2[d.Main_i][d.Main_r - 1][0] = 1;
                        storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r - 1][0] + d.Main_sh);
                        d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r - 1][0] + d.Main_sh] = 1 + ((d.suff23List[d.Main_i][d.Main_r - 1][0] + d.Main_sh) % 3);
                    } 
                    
                    if (d.Main_l < d.suff0List[d.Main_i].length) { 
                        storeArray(d.suff0ListSt2[d.Main_i][d.Main_l], 0);
                        d.suff0ListSt2[d.Main_i][d.Main_l][0] = 4;
                    }
                    if (d.Main_r < d.suff23List[d.Main_i].length) {
                        storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                        d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    }
                    
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_l");
                    d.Main_l = d.Main_l + 1;
                    storeField(d, "Main_r");
                    d.Main_r = d.Main_r + 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;                                                                               
                    break;
                }
                case 58: { // �������� �������� ��������
                    startSection();
                    for (int ti = 0; ti < d.recStrSt[0].length; ti++) {
                        storeArray(d.recStrSt[0], ti);
                        d.recStrSt[0][ti] = -1;
                        storeArray(d.recStrNumSt[0], ti);
                        d.recStrNumSt[0][ti] = -1;
                    }
                    if (d.suff0ListSt2.length > 1) {
                        for (int ti = 0; ti < d.suff0ListSt2[1].length; ti++) {
                            storeArray(d.suff0ListSt2[1][ti], 0);
                            d.suff0ListSt2[1][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.suff23ListSt2[1].length; ti++) {
                            storeArray(d.suff23ListSt2[1][ti], 0);
                            d.suff23ListSt2[1][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.trp23NumSt[1].length; ti++) {
                            storeArray(d.trp23NumSt[1][ti], 0);
                            d.trp23NumSt[1][ti][0] = -1;
                        }
                    }
                    for (int ti = 0; ti < d.suffArrSt[0].length; ti++) {
                        storeArray(d.suffArrSt2[0], ti);
                        d.suffArrSt2[0][ti] = -1;
                    }
                    for (int ti = 0; ti < d.lastSuffArrSt.length; ti++) {
                        storeArray(d.lastSuffArrSt[ti], 0);
                        d.lastSuffArrSt[ti][0] = -1;
                    }
                    for (int ti = 0; ti < d.suffArrSt[0].length; ti++) {
                        storeArray(d.suffArrSt[0], ti);
                        d.suffArrSt[0][ti] = 1;
                    }
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
                case 2: { // ������� ���� ��������
                    break;
                }
                case 3: { // ������� ������
                    restoreSection();
                    break;
                }
                case 4: { // �������� �� ��, ��� ��� ������� ��������
                    break;
                }
                case 5: { // �������� �� ��, ��� ��� ������� �������� (���������)
                    break;
                }
                case 6: { // ���������� �����
                    break;
                }
                case 7: { // ���������� ����� (���������)
                    break;
                }
                case 8: { // �����������
                    restoreSection();
                    break;
                }
                case 9: { // ���
                    break;
                }
                case 10: { // ���������� �����
                    restoreSection();
                    break;
                }
                case 11: { // ���������� �����
                    restoreSection();
                    break;
                }
                case 12: { // ���� �� ������� ���� 1
                    break;
                }
                case 13: { // ���������� ������ ����� ���� 1
                    restoreSection();
                    break;
                }
                case 14: { // ���� �� ������� ���� 2
                    break;
                }
                case 15: { // ���������� ������ ����� ���� 2
                    restoreSection();
                    break;
                }
                case 16: { // �����������
                    restoreSection();
                    break;
                }
                case 17: { // ����
                    break;
                }
                case 18: { // ���������������� ���������� ����������� �������
                    restoreSection();
                    break;
                }
                case 19: { // ����
                    break;
                }
                case 20: { // ������ ���� ��������
                    restoreSection();
                    break;
                }
                case 21: { // ???
                    restoreSection();
                    break;
                }
                case 22: { // �����������
                    restoreSection();
                    break;
                }
                case 23: { // ���� ���������� �������� ������������
                    break;
                }
                case 24: { // ???
                    restoreSection();
                    break;
                }
                case 25: { // �����������
                    restoreSection();
                    break;
                }
                case 26: { // ����
                    break;
                }
                case 27: { // ��� �����
                    restoreSection();
                    break;
                }
                case 28: { // �������� ��������������� �������
                    restoreSection();
                    break;
                }
                case 29: { // �����������
                    restoreSection();
                    break;
                }
                case 30: { // ���� ���������� ���
                    break;
                }
                case 31: { // ���������� ��������� ����
                    restoreSection();
                    break;
                }
                case 32: { // ���������� ���
                    restoreSection();
                    break;
                }
                case 33: { // �����������
                    restoreSection();
                    break;
                }
                case 34: { // ����
                    break;
                }
                case 35: { // ???
                    restoreSection();
                    break;
                }
                case 36: { // ???
                    restoreSection();
                    break;
                }
                case 37: { // ???
                    break;
                }
                case 38: { // ??? (���������)
                    break;
                }
                case 39: { // ???
                    restoreSection();
                    break;
                }
                case 40: { // ����
                    break;
                }
                case 41: { // ???
                    restoreSection();
                    break;
                }
                case 42: { // ����
                    break;
                }
                case 43: { // ???
                    restoreSection();
                    break;
                }
                case 44: { // ��������� ��������
                    break;
                }
                case 45: { // ��������� �������� (���������)
                    break;
                }
                case 46: { // �������� �� �������������� � ����� 1-2
                    break;
                }
                case 47: { // �������� �� �������������� � ����� 1-2 (���������)
                    break;
                }
                case 48: { // ��������, �� ���������� �� ������
                    break;
                }
                case 49: { // ��������, �� ���������� �� ������ (���������)
                    break;
                }
                case 50: { // ???
                    restoreSection();
                    break;
                }
                case 51: { // ��������� ������� ���������
                    break;
                }
                case 52: { // ��������� ������� ��������� (���������)
                    break;
                }
                case 53: { // ???
                    restoreSection();
                    break;
                }
                case 54: { // ???
                    restoreSection();
                    break;
                }
                case 55: { // ��������� ��������
                    restoreSection();
                    break;
                }
                case 56: { // ���������� �������
                    break;
                }
                case 57: { // ���������� �������
                    restoreSection();
                    break;
                }
                case 58: { // �������� �������� ��������
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
                case 2: { // ������� ���� ��������
                    if (stack.popBoolean()) {
                        state = 5; // �������� �� ��, ��� ��� ������� �������� (���������)
                    } else {
                        state = 1; // �������������
                    }
                    break;
                }
                case 3: { // ������� ������
                    state = 2; // ������� ���� ��������
                    break;
                }
                case 4: { // �������� �� ��, ��� ��� ������� ��������
                    state = 3; // ������� ������
                    break;
                }
                case 5: { // �������� �� ��, ��� ��� ������� �������� (���������)
                    if (stack.popBoolean()) {
                        state = 16; // �����������
                    } else {
                        state = 4; // �������� �� ��, ��� ��� ������� ��������
                    }
                    break;
                }
                case 6: { // ���������� �����
                    state = 4; // �������� �� ��, ��� ��� ������� ��������
                    break;
                }
                case 7: { // ���������� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 8; // �����������
                    } else {
                        state = 6; // ���������� �����
                    }
                    break;
                }
                case 8: { // �����������
                    state = 6; // ���������� �����
                    break;
                }
                case 9: { // ���
                    if (stack.popBoolean()) {
                        state = 10; // ���������� �����
                    } else {
                        state = 7; // ���������� ����� (���������)
                    }
                    break;
                }
                case 10: { // ���������� �����
                    state = 9; // ���
                    break;
                }
                case 11: { // ���������� �����
                    state = 9; // ���
                    break;
                }
                case 12: { // ���� �� ������� ���� 1
                    if (stack.popBoolean()) {
                        state = 13; // ���������� ������ ����� ���� 1
                    } else {
                        state = 11; // ���������� �����
                    }
                    break;
                }
                case 13: { // ���������� ������ ����� ���� 1
                    state = 12; // ���� �� ������� ���� 1
                    break;
                }
                case 14: { // ���� �� ������� ���� 2
                    if (stack.popBoolean()) {
                        state = 15; // ���������� ������ ����� ���� 2
                    } else {
                        state = 12; // ���� �� ������� ���� 1
                    }
                    break;
                }
                case 15: { // ���������� ������ ����� ���� 2
                    state = 14; // ���� �� ������� ���� 2
                    break;
                }
                case 16: { // �����������
                    state = 14; // ���� �� ������� ���� 2
                    break;
                }
                case 17: { // ����
                    if (stack.popBoolean()) {
                        state = 18; // ���������������� ���������� ����������� �������
                    } else {
                        state = 2; // ������� ���� ��������
                    }
                    break;
                }
                case 18: { // ���������������� ���������� ����������� �������
                    state = 17; // ����
                    break;
                }
                case 19: { // ����
                    if (stack.popBoolean()) {
                        state = 56; // ���������� �������
                    } else {
                        state = 17; // ����
                    }
                    break;
                }
                case 20: { // ������ ���� ��������
                    state = 19; // ����
                    break;
                }
                case 21: { // ???
                    state = 20; // ������ ���� ��������
                    break;
                }
                case 22: { // �����������
                    state = 21; // ???
                    break;
                }
                case 23: { // ���� ���������� �������� ������������
                    if (stack.popBoolean()) {
                        state = 24; // ???
                    } else {
                        state = 22; // �����������
                    }
                    break;
                }
                case 24: { // ???
                    state = 23; // ���� ���������� �������� ������������
                    break;
                }
                case 25: { // �����������
                    state = 23; // ���� ���������� �������� ������������
                    break;
                }
                case 26: { // ����
                    if (stack.popBoolean()) {
                        state = 27; // ��� �����
                    } else {
                        state = 25; // �����������
                    }
                    break;
                }
                case 27: { // ��� �����
                    state = 26; // ����
                    break;
                }
                case 28: { // �������� ��������������� �������
                    state = 26; // ����
                    break;
                }
                case 29: { // �����������
                    state = 28; // �������� ��������������� �������
                    break;
                }
                case 30: { // ���� ���������� ���
                    if (stack.popBoolean()) {
                        state = 31; // ���������� ��������� ����
                    } else {
                        state = 29; // �����������
                    }
                    break;
                }
                case 31: { // ���������� ��������� ����
                    state = 30; // ���� ���������� ���
                    break;
                }
                case 32: { // ���������� ���
                    state = 30; // ���� ���������� ���
                    break;
                }
                case 33: { // �����������
                    state = 32; // ���������� ���
                    break;
                }
                case 34: { // ����
                    if (stack.popBoolean()) {
                        state = 35; // ???
                    } else {
                        state = 33; // �����������
                    }
                    break;
                }
                case 35: { // ???
                    state = 34; // ����
                    break;
                }
                case 36: { // ???
                    state = 34; // ����
                    break;
                }
                case 37: { // ???
                    state = 36; // ???
                    break;
                }
                case 38: { // ??? (���������)
                    if (stack.popBoolean()) {
                        state = 39; // ???
                    } else {
                        state = 37; // ???
                    }
                    break;
                }
                case 39: { // ???
                    state = 37; // ???
                    break;
                }
                case 40: { // ����
                    if (stack.popBoolean()) {
                        state = 42; // ����
                    } else {
                        state = 38; // ??? (���������)
                    }
                    break;
                }
                case 41: { // ???
                    state = 40; // ����
                    break;
                }
                case 42: { // ����
                    if (stack.popBoolean()) {
                        state = 45; // ��������� �������� (���������)
                    } else {
                        state = 41; // ???
                    }
                    break;
                }
                case 43: { // ???
                    state = 42; // ����
                    break;
                }
                case 44: { // ��������� ��������
                    state = 43; // ???
                    break;
                }
                case 45: { // ��������� �������� (���������)
                    if (stack.popBoolean()) {
                        state = 47; // �������� �� �������������� � ����� 1-2 (���������)
                    } else {
                        state = 55; // ��������� ��������
                    }
                    break;
                }
                case 46: { // �������� �� �������������� � ����� 1-2
                    state = 44; // ��������� ��������
                    break;
                }
                case 47: { // �������� �� �������������� � ����� 1-2 (���������)
                    if (stack.popBoolean()) {
                        state = 49; // ��������, �� ���������� �� ������ (���������)
                    } else {
                        state = 52; // ��������� ������� ��������� (���������)
                    }
                    break;
                }
                case 48: { // ��������, �� ���������� �� ������
                    state = 46; // �������� �� �������������� � ����� 1-2
                    break;
                }
                case 49: { // ��������, �� ���������� �� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 50; // ???
                    } else {
                        state = 48; // ��������, �� ���������� �� ������
                    }
                    break;
                }
                case 50: { // ???
                    state = 48; // ��������, �� ���������� �� ������
                    break;
                }
                case 51: { // ��������� ������� ���������
                    state = 46; // �������� �� �������������� � ����� 1-2
                    break;
                }
                case 52: { // ��������� ������� ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 53; // ???
                    } else {
                        state = 54; // ???
                    }
                    break;
                }
                case 53: { // ???
                    state = 51; // ��������� ������� ���������
                    break;
                }
                case 54: { // ???
                    state = 51; // ��������� ������� ���������
                    break;
                }
                case 55: { // ��������� ��������
                    state = 44; // ��������� ��������
                    break;
                }
                case 56: { // ���������� �������
                    if (stack.popBoolean()) {
                        state = 57; // ���������� �������
                    } else {
                        state = 40; // ����
                    }
                    break;
                }
                case 57: { // ���������� �������
                    state = 56; // ���������� �������
                    break;
                }
                case 58: { // �������� �������� ��������
                    state = 19; // ����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 58; // �������� �������� ��������
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
                    comment = Karkkainen.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // �������������
                    comment = Karkkainen.this.getComment("Main.Renumerating"); 
                    break;
                }
                case 3: { // ������� ������
                    comment = Karkkainen.this.getComment("Main.step1"); 
                    break;
                }
                case 4: { // �������� �� ��, ��� ��� ������� ��������
                    if (d.Main_i < d.recStr.length) {
                        comment = Karkkainen.this.getComment("Main.if0.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if0.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 6: { // ���������� �����
                    if (d.recStr[d.Main_i - 1][d.recStr[d.Main_i - 1].length - 1] == 0) {
                        comment = Karkkainen.this.getComment("Main.if1.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if1.false"); 
                    }
                    args = new Object[]{new Integer(d.recStr[d.Main_i - 1].length % 3)}; 
                    break;
                }
                case 8: { // �����������
                    comment = Karkkainen.this.getComment("Main.step2"); 
                    break;
                }
                case 10: { // ���������� �����
                    comment = Karkkainen.this.getComment("Main.bla"); 
                    break;
                }
                case 11: { // ���������� �����
                    comment = Karkkainen.this.getComment("Main.I"); 
                    break;
                }
                case 13: { // ���������� ������ ����� ���� 1
                    comment = Karkkainen.this.getComment("Main.step5"); 
                    break;
                }
                case 15: { // ���������� ������ ����� ���� 2
                    comment = Karkkainen.this.getComment("Main.step6"); 
                    break;
                }
                case 16: { // �����������
                    comment = Karkkainen.this.getComment("Main.Idi"); 
                    break;
                }
                case 18: { // ���������������� ���������� ����������� �������
                    comment = Karkkainen.this.getComment("Main.step12"); 
                    args = new Object[]{new Integer(d.recStr[d.Main_i - 1][d.Main_j - 1]), new Integer(d.Main_j - 1), new Integer(d.recStr[d.Main_i - 1][d.Main_j - 1] - 1)}; 
                    break;
                }
                case 20: { // ������ ���� ��������
                    comment = Karkkainen.this.getComment("Main.step14"); 
                    args = new Object[]{}; 
                    break;
                }
                case 21: { // ???
                    comment = Karkkainen.this.getComment("Main.step13"); 
                    args = new Object[]{}; 
                    break;
                }
                case 22: { // �����������
                    comment = Karkkainen.this.getComment("Main.step23"); 
                    args = new Object[]{}; 
                    break;
                }
                case 24: { // ???
                    comment = Karkkainen.this.getComment("Main.step19"); 
                    args = new Object[]{new Integer(d.Main_j - 1), new Integer(d.suffArr[d.Main_i][d.Main_j - 1])}; 
                    break;
                }
                case 25: { // �����������
                    comment = Karkkainen.this.getComment("Main.step21"); 
                    args = new Object[]{}; 
                    break;
                }
                case 27: { // ��� �����
                    comment = Karkkainen.this.getComment("Main.step29"); 
                    args = new Object[]{new Integer(d.Main_j - 1), new Integer(d.suff23List[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]][0])}; 
                    break;
                }
                case 28: { // �������� ��������������� �������
                    comment = Karkkainen.this.getComment("Main.step37364"); 
                    break;
                }
                case 29: { // �����������
                    comment = Karkkainen.this.getComment("Main.step31"); 
                    break;
                }
                case 31: { // ���������� ��������� ����
                    comment = Karkkainen.this.getComment("Main.step39"); 
                    args = new Object[]{}; 
                    break;
                }
                case 32: { // ���������� ���
                    comment = Karkkainen.this.getComment("Main.step41"); 
                    break;
                }
                case 33: { // �����������
                    comment = Karkkainen.this.getComment("Main.step61"); 
                    break;
                }
                case 35: { // ???
                    comment = Karkkainen.this.getComment("Main.step49"); 
                    args = new Object[]{new Integer(3 * (d.Main_j - 1)), new Integer(d.pairsNumSt[d.Main_i][d.Main_j - 1][0])}; 
                    break;
                }
                case 36: { // ???
                    comment = Karkkainen.this.getComment("Main.step71"); 
                    args = new Object[]{}; 
                    break;
                }
                case 37: { // ???
                    if (d.recStr[d.Main_i - 1][d.recStr[d.Main_i - 1].length - 1] == 0) {
                        comment = Karkkainen.this.getComment("Main.if6.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if6.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 39: { // ???
                    comment = Karkkainen.this.getComment("Main.step911"); 
                    break;
                }
                case 41: { // ???
                    comment = Karkkainen.this.getComment("Main.step267"); 
                    break;
                }
                case 43: { // ???
                    comment = Karkkainen.this.getComment("Main.step2327"); 
                    break;
                }
                case 44: { // ��������� ��������
                    if (d.recStr[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] == d.recStr[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        comment = Karkkainen.this.getComment("Main.if90.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if90.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 46: { // �������� �� �������������� � ����� 1-2
                    if ((d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh) % 3 == 0 || (d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh) % 3 == 0) {
                        comment = Karkkainen.this.getComment("Main.if66.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if66.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 48: { // ��������, �� ���������� �� ������
                    if (d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh + 1 == d.recStr[d.Main_i - 1].length || d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh + 1 == d.recStr[d.Main_i - 1].length) {
                        comment = Karkkainen.this.getComment("Main.if316.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if316.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 50: { // ???
                    comment = Karkkainen.this.getComment("Main.step924"); 
                    break;
                }
                case 51: { // ��������� ������� ���������
                    if (d.suff23Num[d.Main_i][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] < d.suff23Num[d.Main_i][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        comment = Karkkainen.this.getComment("Main.if346.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if346.false"); 
                    }
                    args = new Object[]{new Integer(d.suff23Num[d.Main_i][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh]), new Integer(d.suff23Num[d.Main_i][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh])}; 
                    break;
                }
                case 53: { // ???
                    comment = Karkkainen.this.getComment("Main.step7624"); 
                    break;
                }
                case 54: { // ???
                    comment = Karkkainen.this.getComment("Main.step7224"); 
                    break;
                }
                case 55: { // ��������� ��������
                    comment = Karkkainen.this.getComment("Main.step766"); 
                    args = new Object[]{new Integer(d.recStr[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_tl][0] + d.Main_sh]), new Integer(d.recStr[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_tr][0] + d.Main_sh])}; 
                    break;
                }
                case 57: { // ���������� �������
                    comment = Karkkainen.this.getComment("Main.step32415"); 
                    args = new Object[]{}; 
                    break;
                }
                case 58: { // �������� �������� ��������
                    comment = Karkkainen.this.getComment("Main.step15"); 
                    args = new Object[]{}; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = Karkkainen.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.draw();
                    break;
                }
                case 3: { // ������� ������
                    d.visualizer.draw();
                    break;
                }
                case 4: { // �������� �� ��, ��� ��� ������� ��������
                    d.visualizer.draw();
                    break;
                }
                case 6: { // ���������� �����
                    d.visualizer.draw();
                    break;
                }
                case 8: { // �����������
                    d.visualizer.draw();
                    break;
                }
                case 10: { // ���������� �����
                    d.visualizer.draw();
                    break;
                }
                case 11: { // ���������� �����
                    d.visualizer.draw();                                                
                    break;
                }
                case 13: { // ���������� ������ ����� ���� 1
                    d.visualizer.draw();
                    break;
                }
                case 15: { // ���������� ������ ����� ���� 2
                    d.visualizer.draw();
                    break;
                }
                case 16: { // �����������
                    d.visualizer.draw();
                    break;
                }
                case 18: { // ���������������� ���������� ����������� �������
                    d.visualizer.draw();
                    break;
                }
                case 20: { // ������ ���� ��������
                    d.visualizer.draw();
                    break;
                }
                case 21: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 22: { // �����������
                    d.visualizer.draw();
                    break;
                }
                case 24: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 25: { // �����������
                    d.visualizer.draw();
                    break;
                }
                case 27: { // ��� �����
                    d.visualizer.draw();
                    break;
                }
                case 28: { // �������� ��������������� �������
                    d.visualizer.draw();
                    break;
                }
                case 29: { // �����������
                    d.visualizer.draw();
                    break;
                }
                case 31: { // ���������� ��������� ����
                    d.visualizer.draw();
                    break;
                }
                case 32: { // ���������� ���
                    d.visualizer.draw();
                    break;
                }
                case 33: { // �����������
                    d.visualizer.draw();
                    break;
                }
                case 35: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 36: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 37: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 39: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 41: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 43: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 44: { // ��������� ��������
                    d.visualizer.draw();
                    break;
                }
                case 46: { // �������� �� �������������� � ����� 1-2
                    d.visualizer.draw();
                    break;
                }
                case 48: { // ��������, �� ���������� �� ������
                    d.visualizer.draw();
                    break;
                }
                case 50: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 51: { // ��������� ������� ���������
                    d.visualizer.draw();
                    break;
                }
                case 53: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 54: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 55: { // ��������� ��������
                    d.visualizer.draw();
                    break;
                }
                case 57: { // ���������� �������
                    d.visualizer.draw();                                                                     
                    break;
                }
                case 58: { // �������� �������� ��������
                    d.visualizer.draw();
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.draw();
                    break;
                }
            }
        }
    }
}
