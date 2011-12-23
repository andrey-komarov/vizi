package ru.ifmo.vizi.heap_sort;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class HeapSort extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public HeapSort(Locale locale) {
        super("ru.ifmo.vizi.heap_sort.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ������ ��� ����������.
          */
        public int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        /**
          * ��������� �������.
          */
        public HeapSortVisualizer visualizer = null;

        /**
          * ������ ����.
          */
        public int heapSize = a.length - 1;

        /**
          * ���������� ��� �������� � Heapify.
          */
        public int iHeapify = 0;

        /**
          * ����� ������ (��������� Heapify).
          */
        public int Heapify_l;

        /**
          * ������ ������ (��������� Heapify).
          */
        public int Heapify_r;

        /**
          * ���������� �� ����� (��������� Heapify).
          */
        public int Heapify_largest;

        /**
          * ���������� ��� ������ (��������� Heapify).
          */
        public int Heapify_swapTmp;

        /**
          * ���������� ����� (��������� BuildHeap).
          */
        public int BuildHeap_i;

        /**
          * ���������� ����� (��������� MakeHeapSort).
          */
        public int MakeHeapSort_i;

        /**
          * ���������� ��� ������ (��������� MakeHeapSort).
          */
        public int MakeHeapSort_swapTmp;

        public String toString() {
            StringBuffer s = new StringBuffer();
            return s.toString();
        }
    }

    /**
      * ���c������ �������.
      */
    private final class Heapify extends BaseAutomata implements Automata {
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
        public Heapify() {
            super( 
                "Heapify", 
                0, // ����� ���������� ��������� 
                18, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������������", 
                    "�������", 
                    "������� (���������)", 
                    "����� �����", 
                    "�������", 
                    "������� (���������)", 
                    "���� ������ ������� ��������", 
                    "�������� ������ ������ ������ ������", 
                    "�������", 
                    "������� (���������)", 
                    "���� ������ ������� ��������", 
                    "�������", 
                    "������� (���������)", 
                    "������ �����������", 
                    "������ ������� �������� ������", 
                    "���c������ ������� (�������)", 
                    "����� �����", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    0, // ������������� 
                    0, // ������� 
                    -1, // ������� (���������) 
                    -1, // ����� ����� 
                    0, // ������� 
                    -1, // ������� (���������) 
                    -1, // ���� ������ ������� �������� 
                    -1, // �������� ������ ������ ������ ������ 
                    0, // ������� 
                    -1, // ������� (���������) 
                    -1, // ���� ������ ������� �������� 
                    0, // ������� 
                    -1, // ������� (���������) 
                    1, // ������ ����������� 
                    1, // ������ ������� �������� ������ 
                    CALL_AUTO_LEVEL, // ���c������ ������� (�������) 
                    -1, // ����� ����� 
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
                    state = 2; // �������
                    break;
                }
                case 2: { // �������
                    if (d.Heapify_l <= d.heapSize) {
                        state = 4; // ����� �����
                    } else {
                        state = 17; // ����� �����
                    }
                    break;
                }
                case 3: { // ������� (���������)
                    state = END_STATE; 
                    break;
                }
                case 4: { // ����� �����
                    state = 5; // �������
                    break;
                }
                case 5: { // �������
                    if (d.a[d.iHeapify] < d.a[d.Heapify_l]) {
                        state = 7; // ���� ������ ������� ��������
                    } else {
                        state = 8; // �������� ������ ������ ������ ������
                    }
                    break;
                }
                case 6: { // ������� (���������)
                    state = 9; // �������
                    break;
                }
                case 7: { // ���� ������ ������� ��������
                    stack.pushBoolean(true); 
                    state = 6; // ������� (���������)
                    break;
                }
                case 8: { // �������� ������ ������ ������ ������
                    stack.pushBoolean(false); 
                    state = 6; // ������� (���������)
                    break;
                }
                case 9: { // �������
                    if (d.Heapify_r <= d.heapSize && d.a[d.Heapify_largest] < d.a[d.Heapify_r] ) {
                        state = 11; // ���� ������ ������� ��������
                    } else {
                        stack.pushBoolean(false); 
                        state = 10; // ������� (���������)
                    }
                    break;
                }
                case 10: { // ������� (���������)
                    state = 12; // �������
                    break;
                }
                case 11: { // ���� ������ ������� ��������
                    stack.pushBoolean(true); 
                    state = 10; // ������� (���������)
                    break;
                }
                case 12: { // �������
                    if (d.Heapify_largest != d.iHeapify) {
                        state = 14; // ������ �����������
                    } else {
                        stack.pushBoolean(false); 
                        state = 13; // ������� (���������)
                    }
                    break;
                }
                case 13: { // ������� (���������)
                    stack.pushBoolean(true); 
                    state = 3; // ������� (���������)
                    break;
                }
                case 14: { // ������ �����������
                    state = 15; // ������ ������� �������� ������
                    break;
                }
                case 15: { // ������ ������� �������� ������
                    state = 16; // ���c������ ������� (�������)
                    break;
                }
                case 16: { // ���c������ ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 13; // ������� (���������)
                    }
                    break;
                }
                case 17: { // ����� �����
                    stack.pushBoolean(false); 
                    state = 3; // ������� (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������
                    startSection();
                    storeField(d, "Heapify_l");
                    d.Heapify_l = d.iHeapify * 2 + 1;
                    storeField(d, "Heapify_r");
                    d.Heapify_r = d.iHeapify * 2 + 1 + 1;
                    break;
                }
                case 2: { // �������
                    break;
                }
                case 3: { // ������� (���������)
                    break;
                }
                case 4: { // ����� �����
                    startSection();
                    break;
                }
                case 5: { // �������
                    break;
                }
                case 6: { // ������� (���������)
                    break;
                }
                case 7: { // ���� ������ ������� ��������
                    startSection();
                    storeField(d, "Heapify_largest");
                    d.Heapify_largest = d.Heapify_l;
                    break;
                }
                case 8: { // �������� ������ ������ ������ ������
                    startSection();
                    storeField(d, "Heapify_largest");
                    d.Heapify_largest = d.iHeapify;
                    break;
                }
                case 9: { // �������
                    break;
                }
                case 10: { // ������� (���������)
                    break;
                }
                case 11: { // ���� ������ ������� ��������
                    startSection();
                    storeField(d, "Heapify_largest");
                    d.Heapify_largest = d.Heapify_r;
                    break;
                }
                case 12: { // �������
                    break;
                }
                case 13: { // ������� (���������)
                    break;
                }
                case 14: { // ������ �����������
                    startSection();
                    storeField(d, "Heapify_swapTmp");
                    d.Heapify_swapTmp = d.a[d.Heapify_largest];
                    storeArray(d.a, d.Heapify_largest);
                    d.a[d.Heapify_largest] = d.a[d.iHeapify];
                    storeArray(d.a, d.iHeapify);
                    d.a[d.iHeapify] = d.Heapify_swapTmp;
                    break;
                }
                case 15: { // ������ ������� �������� ������
                    startSection();
                    storeField(d, "iHeapify");
                    d.iHeapify = d.Heapify_largest;
                    break;
                }
                case 16: { // ���c������ ������� (�������)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 17: { // ����� �����
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
                case 1: { // �������������
                    restoreSection();
                    break;
                }
                case 2: { // �������
                    break;
                }
                case 3: { // ������� (���������)
                    break;
                }
                case 4: { // ����� �����
                    restoreSection();
                    break;
                }
                case 5: { // �������
                    break;
                }
                case 6: { // ������� (���������)
                    break;
                }
                case 7: { // ���� ������ ������� ��������
                    restoreSection();
                    break;
                }
                case 8: { // �������� ������ ������ ������ ������
                    restoreSection();
                    break;
                }
                case 9: { // �������
                    break;
                }
                case 10: { // ������� (���������)
                    break;
                }
                case 11: { // ���� ������ ������� ��������
                    restoreSection();
                    break;
                }
                case 12: { // �������
                    break;
                }
                case 13: { // ������� (���������)
                    break;
                }
                case 14: { // ������ �����������
                    restoreSection();
                    break;
                }
                case 15: { // ������ ������� �������� ������
                    restoreSection();
                    break;
                }
                case 16: { // ���c������ ������� (�������)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 17: { // ����� �����
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
                case 2: { // �������
                    state = 1; // �������������
                    break;
                }
                case 3: { // ������� (���������)
                    if (stack.popBoolean()) {
                        state = 13; // ������� (���������)
                    } else {
                        state = 17; // ����� �����
                    }
                    break;
                }
                case 4: { // ����� �����
                    state = 2; // �������
                    break;
                }
                case 5: { // �������
                    state = 4; // ����� �����
                    break;
                }
                case 6: { // ������� (���������)
                    if (stack.popBoolean()) {
                        state = 7; // ���� ������ ������� ��������
                    } else {
                        state = 8; // �������� ������ ������ ������ ������
                    }
                    break;
                }
                case 7: { // ���� ������ ������� ��������
                    state = 5; // �������
                    break;
                }
                case 8: { // �������� ������ ������ ������ ������
                    state = 5; // �������
                    break;
                }
                case 9: { // �������
                    state = 6; // ������� (���������)
                    break;
                }
                case 10: { // ������� (���������)
                    if (stack.popBoolean()) {
                        state = 11; // ���� ������ ������� ��������
                    } else {
                        state = 9; // �������
                    }
                    break;
                }
                case 11: { // ���� ������ ������� ��������
                    state = 9; // �������
                    break;
                }
                case 12: { // �������
                    state = 10; // ������� (���������)
                    break;
                }
                case 13: { // ������� (���������)
                    if (stack.popBoolean()) {
                        state = 16; // ���c������ ������� (�������)
                    } else {
                        state = 12; // �������
                    }
                    break;
                }
                case 14: { // ������ �����������
                    state = 12; // �������
                    break;
                }
                case 15: { // ������ ������� �������� ������
                    state = 14; // ������ �����������
                    break;
                }
                case 16: { // ���c������ ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 15; // ������ ������� �������� ������
                    }
                    break;
                }
                case 17: { // ����� �����
                    state = 2; // �������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 3; // ������� (���������)
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
                    comment = HeapSort.this.getComment("Heapify.START_STATE"); 
                    break;
                }
                case 1: { // �������������
                    comment = HeapSort.this.getComment("Heapify.Initialization1"); 
                    args = new Object[]{new Integer(d.iHeapify + 1), new Integer(d.a[d.iHeapify + 1 - 1])}; 
                    break;
                }
                case 2: { // �������
                    if (d.Heapify_l <= d.heapSize) {
                        comment = HeapSort.this.getComment("Heapify.ifChildsCond.true"); 
                    } else {
                        comment = HeapSort.this.getComment("Heapify.ifChildsCond.false"); 
                    }
                    args = new Object[]{new Integer(d.iHeapify + 1), new Integer(d.Heapify_l + 1), new Integer(d.a[d.iHeapify + 1 - 1]), new Integer(d.a[( d.Heapify_l + 1 - 1 ) %  d.a.length])}; 
                    break;
                }
                case 5: { // �������
                    if (d.a[d.iHeapify] < d.a[d.Heapify_l]) {
                        comment = HeapSort.this.getComment("Heapify.compareLeftChild.true"); 
                    } else {
                        comment = HeapSort.this.getComment("Heapify.compareLeftChild.false"); 
                    }
                    args = new Object[]{new Integer(d.Heapify_l + 1), new Integer(d.iHeapify + 1), new Integer(d.a[d.Heapify_l + 1 - 1]), new Integer(d.a[d.iHeapify + 1 - 1])}; 
                    break;
                }
                case 9: { // �������
                    if (d.Heapify_r <= d.heapSize && d.a[d.Heapify_largest] < d.a[d.Heapify_r] ) {
                        comment = HeapSort.this.getComment("Heapify.rightChildCompare.true"); 
                    } else {
                        comment = HeapSort.this.getComment("Heapify.rightChildCompare.false"); 
                    }
                    args = new Object[]{new Integer(d.Heapify_r + 1), new Integer(d.Heapify_largest + 1), new Integer(d.a[(d.Heapify_r + 1 - 1) % d.a.length]), new Integer(d.a[d.Heapify_largest + 1 - 1])}; 
                    break;
                }
                case 12: { // �������
                    if (d.Heapify_largest != d.iHeapify) {
                        comment = HeapSort.this.getComment("Heapify.isLargest.true"); 
                    } else {
                        comment = HeapSort.this.getComment("Heapify.isLargest.false"); 
                    }
                    args = new Object[]{new Integer(d.Heapify_largest + 1), new Integer(d.iHeapify + 1),new Integer(d.a[d.Heapify_largest + 1 - 1]), new Integer(d.a[d.iHeapify + 1 - 1])}; 
                    break;
                }
                case 14: { // ������ �����������
                    comment = HeapSort.this.getComment("Heapify.swapLargest"); 
                    args = new Object[]{new Integer(d.iHeapify + 1), new Integer(d.Heapify_largest + 1), new Integer(d.a[d.iHeapify + 1 - 1]), new Integer(d.a[d.Heapify_largest + 1 - 1])}; 
                    break;
                }
                case 15: { // ������ ������� �������� ������
                    comment = HeapSort.this.getComment("Heapify.proccedHeapify"); 
                    args = new Object[]{new Integer(d.Heapify_largest + 1), new Integer(d.a[d.Heapify_largest + 1 - 1])}; 
                    break;
                }
                case 16: { // ���c������ ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = HeapSort.this.getComment("Heapify.END_STATE"); 
                    args = new Object[]{new Integer(d.iHeapify + 1), new Integer(d.a[d.iHeapify + 1 - 1])}; 
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
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // �������������
                    d.visualizer.updateAll(d.iHeapify, d.iHeapify, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 2: { // �������
                    d.visualizer.updateAll(d.iHeapify, d.iHeapify, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 4: { // ����� �����
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_l, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 5: { // �������
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_l, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 7: { // ���� ������ ������� ��������
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_l, 2, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 8: { // �������� ������ ������ ������ ������
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_l, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 9: { // �������
                    d.visualizer.updateAll(d.Heapify_largest, d.Heapify_r, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 11: { // ���� ������ ������� ��������
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_r, 2, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 12: { // �������
                    d.visualizer.updateAll(d.Heapify_largest, d.iHeapify, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 14: { // ������ �����������
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_largest, 2, d.heapSize, d.Heapify_largest - 1);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 15: { // ������ ������� �������� ������
                    d.visualizer.updateAll(d.Heapify_largest, d.Heapify_largest, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 16: { // ���c������ ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 17: { // ����� �����
                    d.visualizer.updateAll(d.iHeapify, d.iHeapify, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * ���������� ������ � ����.
      */
    private final class BuildHeap extends BaseAutomata implements Automata {
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
        public BuildHeap() {
            super( 
                "BuildHeap", 
                0, // ����� ���������� ��������� 
                10, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "bHeapRaport", 
                    "����������� �������� ������� ����", 
                    "��������� ������ �������� � ��������", 
                    "������ �����", 
                    "����", 
                    "��������� �������� ���������� �������� Heapify", 
                    "���c������ ������� (�������)", 
                    "���������", 
                    "heapRaport", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    1, // bHeapRaport 
                    1, // ����������� �������� ������� ���� 
                    1, // ��������� ������ �������� � �������� 
                    -1, // ������ ����� 
                    -1, // ���� 
                    -1, // ��������� �������� ���������� �������� Heapify 
                    CALL_AUTO_LEVEL, // ���c������ ������� (�������) 
                    -1, // ��������� 
                    1, // heapRaport 
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
                    state = 1; // bHeapRaport
                    break;
                }
                case 1: { // bHeapRaport
                    state = 2; // ����������� �������� ������� ����
                    break;
                }
                case 2: { // ����������� �������� ������� ����
                    state = 3; // ��������� ������ �������� � ��������
                    break;
                }
                case 3: { // ��������� ������ �������� � ��������
                    state = 4; // ������ �����
                    break;
                }
                case 4: { // ������ �����
                    stack.pushBoolean(false); 
                    state = 5; // ����
                    break;
                }
                case 5: { // ����
                    if (0 <= d.BuildHeap_i) {
                        state = 6; // ��������� �������� ���������� �������� Heapify
                    } else {
                        state = 9; // heapRaport
                    }
                    break;
                }
                case 6: { // ��������� �������� ���������� �������� Heapify
                    state = 7; // ���c������ ������� (�������)
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // ���������
                    }
                    break;
                }
                case 8: { // ���������
                    stack.pushBoolean(true); 
                    state = 5; // ����
                    break;
                }
                case 9: { // heapRaport
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // bHeapRaport
                    startSection();
                    break;
                }
                case 2: { // ����������� �������� ������� ����
                    startSection();
                    storeField(d, "heapSize");
                    d.heapSize = d.a.length - 1;
                    break;
                }
                case 3: { // ��������� ������ �������� � ��������
                    startSection();
                    break;
                }
                case 4: { // ������ �����
                    startSection();
                    storeField(d, "BuildHeap_i");
                    d.BuildHeap_i = d.a.length / 2 - 1;
                    break;
                }
                case 5: { // ����
                    break;
                }
                case 6: { // ��������� �������� ���������� �������� Heapify
                    startSection();
                    storeField(d, "iHeapify");
                    d.iHeapify = d.BuildHeap_i;
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // ���������
                    startSection();
                    storeField(d, "BuildHeap_i");
                    d.BuildHeap_i = d.BuildHeap_i - 1;
                    break;
                }
                case 9: { // heapRaport
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
                case 1: { // bHeapRaport
                    restoreSection();
                    break;
                }
                case 2: { // ����������� �������� ������� ����
                    restoreSection();
                    break;
                }
                case 3: { // ��������� ������ �������� � ��������
                    restoreSection();
                    break;
                }
                case 4: { // ������ �����
                    restoreSection();
                    break;
                }
                case 5: { // ����
                    break;
                }
                case 6: { // ��������� �������� ���������� �������� Heapify
                    restoreSection();
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    if (child == null) {
                        child = new Heapify(); 
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
                case 9: { // heapRaport
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // bHeapRaport
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����������� �������� ������� ����
                    state = 1; // bHeapRaport
                    break;
                }
                case 3: { // ��������� ������ �������� � ��������
                    state = 2; // ����������� �������� ������� ����
                    break;
                }
                case 4: { // ������ �����
                    state = 3; // ��������� ������ �������� � ��������
                    break;
                }
                case 5: { // ����
                    if (stack.popBoolean()) {
                        state = 8; // ���������
                    } else {
                        state = 4; // ������ �����
                    }
                    break;
                }
                case 6: { // ��������� �������� ���������� �������� Heapify
                    state = 5; // ����
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // ��������� �������� ���������� �������� Heapify
                    }
                    break;
                }
                case 8: { // ���������
                    state = 7; // ���c������ ������� (�������)
                    break;
                }
                case 9: { // heapRaport
                    state = 5; // ����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 9; // heapRaport
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
                    comment = HeapSort.this.getComment("BuildHeap.START_STATE"); 
                    break;
                }
                case 1: { // bHeapRaport
                    comment = HeapSort.this.getComment("BuildHeap.buildHeapRaport"); 
                    break;
                }
                case 2: { // ����������� �������� ������� ����
                    comment = HeapSort.this.getComment("BuildHeap.SetHeapSize"); 
                    break;
                }
                case 3: { // ��������� ������ �������� � ��������
                    comment = HeapSort.this.getComment("BuildHeap.DescribeWhyHalf"); 
                    args = new Object[]{new Integer(d.a.length / 2 - 1 + 1), new Integer(d.a[d.a.length / 2 - 1 + 1 - 1])}; 
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 9: { // heapRaport
                    comment = HeapSort.this.getComment("BuildHeap.HeapAlready"); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = HeapSort.this.getComment("BuildHeap.END_STATE"); 
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
                    d.visualizer.updateAll(0, 0, 0);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // bHeapRaport
                    d.visualizer.updateAll(0, 0, 0);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 2: { // ����������� �������� ������� ����
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 9: { // heapRaport
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * ��������� ������ �����.
      */
    private final class MakeHeapSort extends BaseAutomata implements Automata {
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
        public MakeHeapSort() {
            super( 
                "MakeHeapSort", 
                0, // ����� ���������� ��������� 
                10, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "���������� ������ � ���� (�������)", 
                    "������ �����", 
                    "����", 
                    "���������� ������ � �����", 
                    "��������� ������ ����", 
                    "��������� ������� ���������� �������� Heapify", 
                    "���c������ ������� (�������)", 
                    "���������", 
                    "makes all end", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    CALL_AUTO_LEVEL, // ���������� ������ � ���� (�������) 
                    -1, // ������ ����� 
                    -1, // ���� 
                    1, // ���������� ������ � ����� 
                    0, // ��������� ������ ���� 
                    -1, // ��������� ������� ���������� �������� Heapify 
                    CALL_AUTO_LEVEL, // ���c������ ������� (�������) 
                    -1, // ��������� 
                    -1, // makes all end 
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
                    state = 1; // ���������� ������ � ���� (�������)
                    break;
                }
                case 1: { // ���������� ������ � ���� (�������)
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
                    if (0 < d.MakeHeapSort_i) {
                        state = 4; // ���������� ������ � �����
                    } else {
                        state = 9; // makes all end
                    }
                    break;
                }
                case 4: { // ���������� ������ � �����
                    state = 5; // ��������� ������ ����
                    break;
                }
                case 5: { // ��������� ������ ����
                    state = 6; // ��������� ������� ���������� �������� Heapify
                    break;
                }
                case 6: { // ��������� ������� ���������� �������� Heapify
                    state = 7; // ���c������ ������� (�������)
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // ���������
                    }
                    break;
                }
                case 8: { // ���������
                    stack.pushBoolean(true); 
                    state = 3; // ����
                    break;
                }
                case 9: { // makes all end
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ���������� ������ � ���� (�������)
                    if (child == null) {
                        child = new BuildHeap(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // ������ �����
                    startSection();
                    storeField(d, "MakeHeapSort_i");
                    d.MakeHeapSort_i = d.a.length - 1;
                    break;
                }
                case 3: { // ����
                    break;
                }
                case 4: { // ���������� ������ � �����
                    startSection();
                    storeField(d, "MakeHeapSort_swapTmp");
                    d.MakeHeapSort_swapTmp = d.a[0];
                    storeArray(d.a, 0);
                    d.a[0] = d.a[d.MakeHeapSort_i];
                    storeArray(d.a, d.MakeHeapSort_i);
                    d.a[d.MakeHeapSort_i] = d.MakeHeapSort_swapTmp;
                    break;
                }
                case 5: { // ��������� ������ ����
                    startSection();
                    storeField(d, "heapSize");
                    d.heapSize = d.heapSize - 1;
                    break;
                }
                case 6: { // ��������� ������� ���������� �������� Heapify
                    startSection();
                    storeField(d, "iHeapify");
                    d.iHeapify = 0;
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // ���������
                    startSection();
                    storeField(d, "MakeHeapSort_i");
                    d.MakeHeapSort_i = d.MakeHeapSort_i - 1;
                    break;
                }
                case 9: { // makes all end
                    startSection();
                    d.heapSize = -1;
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
                case 1: { // ���������� ������ � ���� (�������)
                    if (child == null) {
                        child = new BuildHeap(); 
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
                case 4: { // ���������� ������ � �����
                    restoreSection();
                    break;
                }
                case 5: { // ��������� ������ ����
                    restoreSection();
                    break;
                }
                case 6: { // ��������� ������� ���������� �������� Heapify
                    restoreSection();
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    if (child == null) {
                        child = new Heapify(); 
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
                case 9: { // makes all end
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ���������� ������ � ���� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // ������ �����
                    state = 1; // ���������� ������ � ���� (�������)
                    break;
                }
                case 3: { // ����
                    if (stack.popBoolean()) {
                        state = 8; // ���������
                    } else {
                        state = 2; // ������ �����
                    }
                    break;
                }
                case 4: { // ���������� ������ � �����
                    state = 3; // ����
                    break;
                }
                case 5: { // ��������� ������ ����
                    state = 4; // ���������� ������ � �����
                    break;
                }
                case 6: { // ��������� ������� ���������� �������� Heapify
                    state = 5; // ��������� ������ ����
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // ��������� ������� ���������� �������� Heapify
                    }
                    break;
                }
                case 8: { // ���������
                    state = 7; // ���c������ ������� (�������)
                    break;
                }
                case 9: { // makes all end
                    state = 3; // ����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 9; // makes all end
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
                    comment = HeapSort.this.getComment("MakeHeapSort.START_STATE"); 
                    break;
                }
                case 1: { // ���������� ������ � ���� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 4: { // ���������� ������ � �����
                    comment = HeapSort.this.getComment("MakeHeapSort.swapFirstNLast"); 
                    break;
                }
                case 5: { // ��������� ������ ����
                    comment = HeapSort.this.getComment("MakeHeapSort.decHeapSize"); 
                    args = new Object[]{new Integer(d.heapSize + 1 + 1), new Integer(d.a[d.iHeapify + 1 + 1 - 1])}; 
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = HeapSort.this.getComment("MakeHeapSort.END_STATE"); 
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
                    d.visualizer.updateAll(0, 0, 0);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // ���������� ������ � ���� (�������)
                    child.drawState(); 
                    break;
                }
                case 4: { // ���������� ������ � �����
                    d.visualizer.updateAll(d.MakeHeapSort_i, 0, 2, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 5: { // ��������� ������ ����
                    d.visualizer.updateAll(0, 0, 2, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 7: { // ���c������ ������� (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.updateAll(0, 0, 3, -1);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * ��������� ������ ����������� �����.
      */
    private final class Main extends BaseAutomata implements Automata {
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
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                2, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "��������� ������ ����� (�������)", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    CALL_AUTO_LEVEL, // ��������� ������ ����� (�������) 
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
                    state = 1; // ��������� ������ ����� (�������)
                    break;
                }
                case 1: { // ��������� ������ ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = END_STATE; 
                    }
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ��������� ������ ����� (�������)
                    if (child == null) {
                        child = new MakeHeapSort(); 
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
                case 1: { // ��������� ������ ����� (�������)
                    if (child == null) {
                        child = new MakeHeapSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ��������� ������ ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 1; // ��������� ������ ����� (�������)
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
                    comment = HeapSort.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // ��������� ������ ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = HeapSort.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.updateAll(0, 0, 0);
                    d.visualizer.makeAllStart();
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // ��������� ������ ����� (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.stopEditMode();
                    d.visualizer.updateAll(0, 0, 3, -1);
                    break;
                }
            }
        }
    }
}
