package ru.ifmo.vizi.SegmentsTree;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class SegmentsTree extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public SegmentsTree(Locale locale) {
        super("ru.ifmo.vizi.SegmentsTree.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ��������� �������.
          */
        public SegmentsTreeVisualizer visualizer = null;

        /**
          * ����� ������� ������.
          */
        public int left = 0;

        /**
          * ������ ������� ������.
          */
        public int right = 0;

        /**
          * ������ ��� ������.
          */
        public int[] h = null;

        /**
          * ������� ����� ������� ������.
          */
        public int l = 0;

        /**
          * ������� ������ ������� ������.
          */
        public int r = 0;

        /**
          * ������� �������.
          */
        public int a = 0;

        /**
          * ������ ������� ��� ��������� ������.
          */
        public int lowCellsBound = 0;

        /**
          * ������ ������� ���������.
          */
        public int lowMinBound = 0;

        /**
          * ������ ������.
          */
        public int height = 0;

        /**
          * ������, ������������ ������� �������.
          */
        public String minim = \u0022\u221E\u0022;

        /**
          * ������ �������������.
          */
        public String infinity = \u0022\u221E\u0022;

        /**
          * ����� ������� �������.
          */
        public String tl = \u0022\u221E\u0022;

        /**
          * ������ ������� �������.
          */
        public String tr = \u0022\u221E\u0022;

        public String toString() {
            return "" + d.a;
        }
    }

    /**
      * ���� ������� � �������, ��������� ��������� ������ - ������ ��������.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 31;

        /**
          * �����������.
          */
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                31, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������������", 
                    "���������� � ���������� ������ ��������", 
                    "�������� ������", 
                    "���� �� ��������������� ���������� ������", 
                    "���� �� ��������������� ���������� ������ (���������)", 
                    "���������� ������", 
                    "������ ����� �������", 
                    "�������� �������������", 
                    "�������� ������������� (���������)", 
                    "� ����������� ������ ������ �������������", 
                    "� ����������� ������ �������� ��������", 
                    "�������� �������������", 
                    "�������� ������������� (���������)", 
                    "� ����������� ������ ������ �������������", 
                    "� ����������� ������ �������� ��������", 
                    "������������ ���������� ������", 
                    "������ ��� ���������", 
                    "������������� ��������", 
                    "������ ������ ������", 
                    "������ �� ������ ����� � ����������� �������� �� ��������", 
                    "�������� ����� ������� �� �������������", 
                    "�������� ����� ������� �� ������������� (���������)", 
                    "���������� ��������", 
                    "�������� �� �������������", 
                    "�������� �� ������������� (���������)", 
                    "���������� ��������", 
                    "��������", 
                    "�������� (���������)", 
                    "������ ������ ������", 
                    "������� ������ ����� ���� �� �����", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� 
                    0, // ���������� � ���������� ������ �������� 
                    0, // �������� ������ 
                    -1, // ���� �� ��������������� ���������� ������ 
                    -1, // ���� �� ��������������� ���������� ������ (���������) 
                    -1, // ���������� ������ 
                    -1, // ������ ����� ������� 
                    -1, // �������� ������������� 
                    -1, // �������� ������������� (���������) 
                    -1, // � ����������� ������ ������ ������������� 
                    -1, // � ����������� ������ �������� �������� 
                    -1, // �������� ������������� 
                    -1, // �������� ������������� (���������) 
                    -1, // � ����������� ������ ������ ������������� 
                    -1, // � ����������� ������ �������� �������� 
                    0, // ������������ ���������� ������ 
                    0, // ������ ��� ��������� 
                    1, // ������������� �������� 
                    1, // ������ ������ ������ 
                    -1, // ������ �� ������ ����� � ����������� �������� �� �������� 
                    0, // �������� ����� ������� �� ������������� 
                    -1, // �������� ����� ������� �� ������������� (���������) 
                    0, // ���������� �������� 
                    0, // �������� �� ������������� 
                    -1, // �������� �� ������������� (���������) 
                    0, // ���������� �������� 
                    -1, // �������� 
                    -1, // �������� (���������) 
                    0, // ������ ������ ������ 
                    0, // ������� ������ ����� ���� �� ����� 
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
                    state = 2; // ���������� � ���������� ������ ��������
                    break;
                }
                case 2: { // ���������� � ���������� ������ ��������
                    state = 3; // �������� ������
                    break;
                }
                case 3: { // �������� ������
                    state = 4; // ���� �� ��������������� ���������� ������
                    break;
                }
                case 4: { // ���� �� ��������������� ���������� ������
                    if (d.visualizer.showBuilding()) {
                        stack.pushBoolean(false); 
                        state = 6; // ���������� ������
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // ���� �� ��������������� ���������� ������ (���������)
                    }
                    break;
                }
                case 5: { // ���� �� ��������������� ���������� ������ (���������)
                    state = 17; // ������ ��� ���������
                    break;
                }
                case 6: { // ���������� ������
                    if (d.lowCellsBound >= 2) {
                        state = 7; // ������ ����� �������
                    } else {
                        stack.pushBoolean(true); 
                        state = 5; // ���� �� ��������������� ���������� ������ (���������)
                    }
                    break;
                }
                case 7: { // ������ ����� �������
                    state = 8; // �������� �������������
                    break;
                }
                case 8: { // �������� �������������
                    if (d.h[2 * d.lowCellsBound] == Integer.MAX_VALUE) {
                        state = 10; // � ����������� ������ ������ �������������
                    } else {
                        state = 11; // � ����������� ������ �������� ��������
                    }
                    break;
                }
                case 9: { // �������� ������������� (���������)
                    state = 12; // �������� �������������
                    break;
                }
                case 10: { // � ����������� ������ ������ �������������
                    stack.pushBoolean(true); 
                    state = 9; // �������� ������������� (���������)
                    break;
                }
                case 11: { // � ����������� ������ �������� ��������
                    stack.pushBoolean(false); 
                    state = 9; // �������� ������������� (���������)
                    break;
                }
                case 12: { // �������� �������������
                    if (d.h[2 * d.lowCellsBound + 1] == Integer.MAX_VALUE) {
                        state = 14; // � ����������� ������ ������ �������������
                    } else {
                        state = 15; // � ����������� ������ �������� ��������
                    }
                    break;
                }
                case 13: { // �������� ������������� (���������)
                    state = 16; // ������������ ���������� ������
                    break;
                }
                case 14: { // � ����������� ������ ������ �������������
                    stack.pushBoolean(true); 
                    state = 13; // �������� ������������� (���������)
                    break;
                }
                case 15: { // � ����������� ������ �������� ��������
                    stack.pushBoolean(false); 
                    state = 13; // �������� ������������� (���������)
                    break;
                }
                case 16: { // ������������ ���������� ������
                    stack.pushBoolean(true); 
                    state = 6; // ���������� ������
                    break;
                }
                case 17: { // ������ ��� ���������
                    state = 18; // ������������� ��������
                    break;
                }
                case 18: { // ������������� ��������
                    state = 19; // ������ ������ ������
                    break;
                }
                case 19: { // ������ ������ ������
                    stack.pushBoolean(false); 
                    state = 20; // ������ �� ������ ����� � ����������� �������� �� ��������
                    break;
                }
                case 20: { // ������ �� ������ ����� � ����������� �������� �� ��������
                    if (d.l <= d.r) {
                        state = 21; // �������� ����� ������� �� �������������
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 21: { // �������� ����� ������� �� �������������
                    if (d.a > d.h[d.l]) {
                        state = 23; // ���������� ��������
                    } else {
                        stack.pushBoolean(false); 
                        state = 22; // �������� ����� ������� �� ������������� (���������)
                    }
                    break;
                }
                case 22: { // �������� ����� ������� �� ������������� (���������)
                    state = 24; // �������� �� �������������
                    break;
                }
                case 23: { // ���������� ��������
                    stack.pushBoolean(true); 
                    state = 22; // �������� ����� ������� �� ������������� (���������)
                    break;
                }
                case 24: { // �������� �� �������������
                    if (d.a > d.h[d.r]) {
                        state = 26; // ���������� ��������
                    } else {
                        stack.pushBoolean(false); 
                        state = 25; // �������� �� ������������� (���������)
                    }
                    break;
                }
                case 25: { // �������� �� ������������� (���������)
                    state = 27; // ��������
                    break;
                }
                case 26: { // ���������� ��������
                    stack.pushBoolean(true); 
                    state = 25; // �������� �� ������������� (���������)
                    break;
                }
                case 27: { // ��������
                    if (((d.l + 1)) / 2 <= ((d.r - 1) / 2)) {
                        state = 29; // ������ ������ ������
                    } else {
                        state = 30; // ������� ������ ����� ���� �� �����
                    }
                    break;
                }
                case 28: { // �������� (���������)
                    stack.pushBoolean(true); 
                    state = 20; // ������ �� ������ ����� � ����������� �������� �� ��������
                    break;
                }
                case 29: { // ������ ������ ������
                    stack.pushBoolean(true); 
                    state = 28; // �������� (���������)
                    break;
                }
                case 30: { // ������� ������ ����� ���� �� �����
                    stack.pushBoolean(false); 
                    state = 28; // �������� (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������
                    startSection();
                    storeField(d, "lowCellsBound");
                    d.lowCellsBound = d.h.length / 2;
                    break;
                }
                case 2: { // ���������� � ���������� ������ ��������
                    startSection();
                    break;
                }
                case 3: { // �������� ������
                    startSection();
                    break;
                }
                case 4: { // ���� �� ��������������� ���������� ������
                    break;
                }
                case 5: { // ���� �� ��������������� ���������� ������ (���������)
                    break;
                }
                case 6: { // ���������� ������
                    break;
                }
                case 7: { // ������ ����� �������
                    startSection();
                    storeField(d, "lowCellsBound");
                    	d.lowCellsBound = d.lowCellsBound - 1;
                    break;
                }
                case 8: { // �������� �������������
                    break;
                }
                case 9: { // �������� ������������� (���������)
                    break;
                }
                case 10: { // � ����������� ������ ������ �������������
                    startSection();
                    storeField(d, "tl");
                    	d.tl = d.infinity;
                    break;
                }
                case 11: { // � ����������� ������ �������� ��������
                    startSection();
                    storeField(d, "tl");
                    	d.tl = Integer.toString(d.h[2 * d.lowCellsBound]);
                    break;
                }
                case 12: { // �������� �������������
                    break;
                }
                case 13: { // �������� ������������� (���������)
                    break;
                }
                case 14: { // � ����������� ������ ������ �������������
                    startSection();
                    storeField(d, "tr");
                    	d.tr = d.infinity;
                    break;
                }
                case 15: { // � ����������� ������ �������� ��������
                    startSection();
                    storeField(d, "tr");
                    	d.tr = Integer.toString(d.h[2 * d.lowCellsBound + 1]);
                    break;
                }
                case 16: { // ������������ ���������� ������
                    startSection();
                    break;
                }
                case 17: { // ������ ��� ���������
                    startSection();
                    break;
                }
                case 18: { // ������������� ��������
                    startSection();
                    storeField(d, "a");
                    d.a = Integer.MAX_VALUE;
                    storeField(d, "minim");
                    d.minim = "\u221E";
                    break;
                }
                case 19: { // ������ ������ ������
                    startSection();
                    storeField(d, "l");
                    d.l = d.left;
                    storeField(d, "r");
                    d.r = d.right;
                    break;
                }
                case 20: { // ������ �� ������ ����� � ����������� �������� �� ��������
                    break;
                }
                case 21: { // �������� ����� ������� �� �������������
                    break;
                }
                case 22: { // �������� ����� ������� �� ������������� (���������)
                    break;
                }
                case 23: { // ���������� ��������
                    startSection();
                    storeField(d, "a");
                    d.a = d.h[d.l];
                    storeField(d, "minim");
                    d.minim = Integer.toString(d.a);
                    break;
                }
                case 24: { // �������� �� �������������
                    break;
                }
                case 25: { // �������� �� ������������� (���������)
                    break;
                }
                case 26: { // ���������� ��������
                    startSection();
                    storeField(d, "a");
                    d.a = d.h[d.r];
                    storeField(d, "minim");
                    d.minim = Integer.toString(d.a);
                    break;
                }
                case 27: { // ��������
                    break;
                }
                case 28: { // �������� (���������)
                    break;
                }
                case 29: { // ������ ������ ������
                    startSection();
                    int next = (d.l + 1) / 2;
                    storeField(d, "l");
                    d.l = next;
                    
                    next = (d.r - 1) / 2;
                    storeField(d, "r");
                    d.r = next;
                    break;
                }
                case 30: { // ������� ������ ����� ���� �� �����
                    startSection();
                    int next = (d.l + 1) / 2;
                    storeField(d, "l");
                    d.l = next;
                    
                    next = (d.r - 1) / 2;
                    storeField(d, "r");
                    d.r = next;
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
                case 2: { // ���������� � ���������� ������ ��������
                    restoreSection();
                    break;
                }
                case 3: { // �������� ������
                    restoreSection();
                    break;
                }
                case 4: { // ���� �� ��������������� ���������� ������
                    break;
                }
                case 5: { // ���� �� ��������������� ���������� ������ (���������)
                    break;
                }
                case 6: { // ���������� ������
                    break;
                }
                case 7: { // ������ ����� �������
                    restoreSection();
                    break;
                }
                case 8: { // �������� �������������
                    break;
                }
                case 9: { // �������� ������������� (���������)
                    break;
                }
                case 10: { // � ����������� ������ ������ �������������
                    restoreSection();
                    break;
                }
                case 11: { // � ����������� ������ �������� ��������
                    restoreSection();
                    break;
                }
                case 12: { // �������� �������������
                    break;
                }
                case 13: { // �������� ������������� (���������)
                    break;
                }
                case 14: { // � ����������� ������ ������ �������������
                    restoreSection();
                    break;
                }
                case 15: { // � ����������� ������ �������� ��������
                    restoreSection();
                    break;
                }
                case 16: { // ������������ ���������� ������
                    restoreSection();
                    break;
                }
                case 17: { // ������ ��� ���������
                    restoreSection();
                    break;
                }
                case 18: { // ������������� ��������
                    restoreSection();
                    break;
                }
                case 19: { // ������ ������ ������
                    restoreSection();
                    break;
                }
                case 20: { // ������ �� ������ ����� � ����������� �������� �� ��������
                    break;
                }
                case 21: { // �������� ����� ������� �� �������������
                    break;
                }
                case 22: { // �������� ����� ������� �� ������������� (���������)
                    break;
                }
                case 23: { // ���������� ��������
                    restoreSection();
                    break;
                }
                case 24: { // �������� �� �������������
                    break;
                }
                case 25: { // �������� �� ������������� (���������)
                    break;
                }
                case 26: { // ���������� ��������
                    restoreSection();
                    break;
                }
                case 27: { // ��������
                    break;
                }
                case 28: { // �������� (���������)
                    break;
                }
                case 29: { // ������ ������ ������
                    restoreSection();
                    break;
                }
                case 30: { // ������� ������ ����� ���� �� �����
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
                case 2: { // ���������� � ���������� ������ ��������
                    state = 1; // �������������
                    break;
                }
                case 3: { // �������� ������
                    state = 2; // ���������� � ���������� ������ ��������
                    break;
                }
                case 4: { // ���� �� ��������������� ���������� ������
                    state = 3; // �������� ������
                    break;
                }
                case 5: { // ���� �� ��������������� ���������� ������ (���������)
                    if (stack.popBoolean()) {
                        state = 6; // ���������� ������
                    } else {
                        state = 4; // ���� �� ��������������� ���������� ������
                    }
                    break;
                }
                case 6: { // ���������� ������
                    if (stack.popBoolean()) {
                        state = 16; // ������������ ���������� ������
                    } else {
                        state = 4; // ���� �� ��������������� ���������� ������
                    }
                    break;
                }
                case 7: { // ������ ����� �������
                    state = 6; // ���������� ������
                    break;
                }
                case 8: { // �������� �������������
                    state = 7; // ������ ����� �������
                    break;
                }
                case 9: { // �������� ������������� (���������)
                    if (stack.popBoolean()) {
                        state = 10; // � ����������� ������ ������ �������������
                    } else {
                        state = 11; // � ����������� ������ �������� ��������
                    }
                    break;
                }
                case 10: { // � ����������� ������ ������ �������������
                    state = 8; // �������� �������������
                    break;
                }
                case 11: { // � ����������� ������ �������� ��������
                    state = 8; // �������� �������������
                    break;
                }
                case 12: { // �������� �������������
                    state = 9; // �������� ������������� (���������)
                    break;
                }
                case 13: { // �������� ������������� (���������)
                    if (stack.popBoolean()) {
                        state = 14; // � ����������� ������ ������ �������������
                    } else {
                        state = 15; // � ����������� ������ �������� ��������
                    }
                    break;
                }
                case 14: { // � ����������� ������ ������ �������������
                    state = 12; // �������� �������������
                    break;
                }
                case 15: { // � ����������� ������ �������� ��������
                    state = 12; // �������� �������������
                    break;
                }
                case 16: { // ������������ ���������� ������
                    state = 13; // �������� ������������� (���������)
                    break;
                }
                case 17: { // ������ ��� ���������
                    state = 5; // ���� �� ��������������� ���������� ������ (���������)
                    break;
                }
                case 18: { // ������������� ��������
                    state = 17; // ������ ��� ���������
                    break;
                }
                case 19: { // ������ ������ ������
                    state = 18; // ������������� ��������
                    break;
                }
                case 20: { // ������ �� ������ ����� � ����������� �������� �� ��������
                    if (stack.popBoolean()) {
                        state = 28; // �������� (���������)
                    } else {
                        state = 19; // ������ ������ ������
                    }
                    break;
                }
                case 21: { // �������� ����� ������� �� �������������
                    state = 20; // ������ �� ������ ����� � ����������� �������� �� ��������
                    break;
                }
                case 22: { // �������� ����� ������� �� ������������� (���������)
                    if (stack.popBoolean()) {
                        state = 23; // ���������� ��������
                    } else {
                        state = 21; // �������� ����� ������� �� �������������
                    }
                    break;
                }
                case 23: { // ���������� ��������
                    state = 21; // �������� ����� ������� �� �������������
                    break;
                }
                case 24: { // �������� �� �������������
                    state = 22; // �������� ����� ������� �� ������������� (���������)
                    break;
                }
                case 25: { // �������� �� ������������� (���������)
                    if (stack.popBoolean()) {
                        state = 26; // ���������� ��������
                    } else {
                        state = 24; // �������� �� �������������
                    }
                    break;
                }
                case 26: { // ���������� ��������
                    state = 24; // �������� �� �������������
                    break;
                }
                case 27: { // ��������
                    state = 25; // �������� �� ������������� (���������)
                    break;
                }
                case 28: { // �������� (���������)
                    if (stack.popBoolean()) {
                        state = 29; // ������ ������ ������
                    } else {
                        state = 30; // ������� ������ ����� ���� �� �����
                    }
                    break;
                }
                case 29: { // ������ ������ ������
                    state = 27; // ��������
                    break;
                }
                case 30: { // ������� ������ ����� ���� �� �����
                    state = 27; // ��������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 20; // ������ �� ������ ����� � ����������� �������� �� ��������
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
                    comment = SegmentsTree.this.getComment("Main.START_STATE"); 
                    args = new Object[]{new Integer(d.left - d.h.length / 2 + 1), new Integer(d.right - d.h.length / 2 + 1)}; 
                    break;
                }
                case 2: { // ���������� � ���������� ������ ��������
                    comment = SegmentsTree.this.getComment("Main.treeBuilding1"); 
                    break;
                }
                case 3: { // �������� ������
                    comment = SegmentsTree.this.getComment("Main.move"); 
                    args = new Object[]{new Integer(d.h.length / 2 - 1)}; 
                    break;
                }
                case 16: { // ������������ ���������� ������
                    comment = SegmentsTree.this.getComment("Main.tree"); 
                    args = new Object[]{new Integer(2 * d.lowCellsBound), d.tl, new Integer(2 * d.lowCellsBound + 1), d.tr}; 
                    break;
                }
                case 17: { // ������ ��� ���������
                    comment = SegmentsTree.this.getComment("Main.treeBuilded"); 
                    break;
                }
                case 18: { // ������������� ��������
                    comment = SegmentsTree.this.getComment("Main.initialization"); 
                    break;
                }
                case 19: { // ������ ������ ������
                    comment = SegmentsTree.this.getComment("Main.firstRise"); 
                    args = new Object[]{new Integer(d.h.length / 2 - 1), new Integer(d.left), new Integer(d.right)}; 
                    break;
                }
                case 21: { // �������� ����� ������� �� �������������
                    if (d.a > d.h[d.l]) {
                        comment = SegmentsTree.this.getComment("Main.Cond1.true"); 
                    } else {
                        comment = SegmentsTree.this.getComment("Main.Cond1.false"); 
                    }
                    args = new Object[]{new Integer(d.h[d.l]), d.minim}; 
                    break;
                }
                case 23: { // ���������� ��������
                    comment = SegmentsTree.this.getComment("Main.newMin1"); 
                    args = new Object[]{new Integer(d.a)}; 
                    break;
                }
                case 24: { // �������� �� �������������
                    if (d.a > d.h[d.r]) {
                        comment = SegmentsTree.this.getComment("Main.Cond2.true"); 
                    } else {
                        comment = SegmentsTree.this.getComment("Main.Cond2.false"); 
                    }
                    args = new Object[]{new Integer(d.h[d.r]), d.minim}; 
                    break;
                }
                case 26: { // ���������� ��������
                    comment = SegmentsTree.this.getComment("Main.newMin2"); 
                    args = new Object[]{new Integer(d.a)}; 
                    break;
                }
                case 29: { // ������ ������ ������
                    comment = SegmentsTree.this.getComment("Main.rise2"); 
                    break;
                }
                case 30: { // ������� ������ ����� ���� �� �����
                    comment = SegmentsTree.this.getComment("Main.rise3"); 
                    args = new Object[]{new Integer((d.l + 1) / 2), new Integer((d.r - 1) / 2)}; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = SegmentsTree.this.getComment("Main.END_STATE"); 
                    args = new Object[]{new Integer(d.a)}; 
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
                    d.visualizer.drawBrackets(0, 0, false, false);
                    d.visualizer.drawCells(d.h.length / 2, 0, 0, 0, 0, d.h.length, false);
                    d.visualizer.drawMin(false);
                    break;
                }
                case 2: { // ���������� � ���������� ������ ��������
                    	d.visualizer.drawBrackets(0, 0, false, false);                
                    	d.visualizer.drawCells(d.h.length / 2, 0, 0, 0, 0, d.h.length, false);
                    	d.visualizer.drawMin(false);
                    break;
                }
                case 3: { // �������� ������
                    	d.visualizer.drawBrackets(0, 0, false, false);                
                    	d.visualizer.drawCells(d.h.length / 2, 0, 0, 0, 0, d.h.length, true);
                    	d.visualizer.drawMin(false);
                    break;
                }
                case 16: { // ������������ ���������� ������
                    	d.visualizer.drawCells(d.lowCellsBound, 0, 0, 0, 0, d.h.length, true);
                    	d.visualizer.drawBrackets(0, 0, false, false);
                    	d.visualizer.drawMin(false);
                    break;
                }
                case 17: { // ������ ��� ���������
                    d.visualizer.drawCells(0, 0, 0, 0, 0, d.h.length, true);
                    d.visualizer.drawBrackets(0, 0, false, false);
                    d.visualizer.drawMin(false);
                    break;
                }
                case 18: { // ������������� ��������
                    	d.visualizer.drawBrackets(0, 0, false, false);                
                    	d.visualizer.drawCells(0, 0, 0, 0, 0, d.h.length, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 19: { // ������ ������ ������
                    	d.visualizer.drawCells(0, d.l, d.r, 0, 0, d.h.length, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                     d.visualizer.setSecondBigStep();
                    break;
                }
                case 21: { // �������� ����� ������� �� �������������
                    	d.visualizer.drawCells(0, d.l, d.r, d.l, 0, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 23: { // ���������� ��������
                    	d.visualizer.drawCells(0, d.l, d.r, d.l, d.l, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 24: { // �������� �� �������������
                    	d.visualizer.drawCells(0, d.l, d.r, d.r, 0, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 26: { // ���������� ��������
                    	d.visualizer.drawCells(0, d.l, d.r, d.r, d.r, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 29: { // ������ ������ ������
                    	d.visualizer.drawCells(0, d.l, d.r, 0, 0, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 30: { // ������� ������ ����� ���� �� �����
                    	d.visualizer.drawCells(0, d.l, d.r, 0, 0, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case END_STATE: { // �������� ���������
                    	d.visualizer.drawCells(0, 0, 0, 0, 0, 0, true);
                    	d.visualizer.drawBrackets(d.l, d.r, false, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
            }
        }
    }
}
