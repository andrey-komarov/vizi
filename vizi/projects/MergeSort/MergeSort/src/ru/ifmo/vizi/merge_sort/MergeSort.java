package ru.ifmo.vizi.merge_sort;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class MergeSort extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public MergeSort(Locale locale) {
        super("ru.ifmo.vizi.merge_sort.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ������, ���������� ����������.
          */
        public int[] a = new int[]{15, 14, 65, 24, 98, 73, 21, 7};

        /**
          * ������ ��� �������� ����� ������ �����������                              �������� (��������� ��� ��������).
          */
        public int[] l = new int[]{0, 0, 0, 0, 0, 0, 0};

        /**
          * ������ ��� �������� ������ ������ �����������                              �������� (��������� ��� ��������).
          */
        public int[] r = new int[]{0, 0, 0, 0, 0, 0, 0};

        /**
          * ���������� ��� �������� ������� ��������.
          */
        public int h = 0;

        /**
          * ��������� �������.
          */
        public MergeSortVisualizer visualizer = null;

        /**
          * ��������������� ������ (��������� Merge).
          */
        public int[] Merge_b;

        /**
          * ��������� �� ������� �������                                   ����� �������� ������� (��������� Merge).
          */
        public int Merge_i;

        /**
          * ��������� �� ������� �������                                   ������ �������� ������� (��������� Merge).
          */
        public int Merge_j;

        /**
          * ������� ����� �� ����� ������� (��������� Merge).
          */
        public int Merge_k;

        public String toString() {
            StringBuffer s = new StringBuffer();
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
                    "���������� � ������� �������� ���������� �������", 
                    "�������, ����������� ����� ������� (�������)", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ���������� � ������� �������� ���������� ������� 
                    1, // �������, ����������� ����� ������� (�������) 
                    Integer.MAX_VALUE, // �������� ��������� 
                } 
            ); 
        }

        /**
          * ������� ���� ��� �������� � �����.
          */
        protected void doStepForward(int level) {
            do {
                // ������� � ��������� ���������
                switch (state) {
                    case START_STATE: { // ��������� ���������
                        state = 1; // ���������� � ������� �������� ���������� �������
                        break;
                    }
                    case 1: { // ���������� � ������� �������� ���������� �������
                        state = 2; // �������, ����������� ����� ������� (�������)
                        break;
                    }
                    case 2: { // �������, ����������� ����� ������� (�������)
                        if (child.isAtEnd()) {
                            child = null; 
                            state = END_STATE; 
                        }
                        break;
                    }
                }

                // �������� � ������� ���������
                switch (state) {
                    case 1: { // ���������� � ������� �������� ���������� �������
                        startSection();
                        storeField(d, "h");
                        d.h = -1;
                        storeArray(d.l, 0);
                        d.l[0] = 0;
                        storeArray(d.r, 0);
                        d.r[0] = d.a.length - 1;
                        break;
                    }
                    case 2: { // �������, ����������� ����� ������� (�������)
                        if (child == null) {
                            child = new MSort(); 
                            child.toStart(); 
                        }
                        child.stepForward(level); 
                        step--; 
                        break;
                    }
                }
            } while (!isInterestingTemp(level));
        }

        /**
          * ������� ���� ��� �������� �����.
          */
        protected void doStepBackward(int level) {
            do {
                // ��������� �������� � ������� ���������
                switch (state) {
                    case 1: { // ���������� � ������� �������� ���������� �������
                        restoreSection();
                        break;
                    }
                    case 2: { // �������, ����������� ����� ������� (�������)
                        if (child == null) {
                            child = new MSort(); 
                            child.toEnd(); 
                        }
                        child.stepBackward(level); 
                        step++; 
                        break;
                    }
                }

                // ������� � ���������� ���������
                switch (state) {
                    case 1: { // ���������� � ������� �������� ���������� �������
                        state = START_STATE; 
                        break;
                    }
                    case 2: { // �������, ����������� ����� ������� (�������)
                        if (child.isAtStart()) {
                            child = null; 
                            state = 1; // ���������� � ������� �������� ���������� �������
                        }
                        break;
                    }
                    case END_STATE: { // ��������� ���������
                        state = 2; // �������, ����������� ����� ������� (�������)
                        break;
                    }
                }
            } while (!isInterestingTemp(level));
        }

        /**
          * ��������� �� ������� ��������� (����������� ���� � call-auto).
          */
        public boolean isInterestingTemp(int level) {
            // ������������
            switch (state) {
                case START_STATE: // ��������� ���������
                    return true; 
                case 1: // ���������� � ������� �������� ���������� �������
                    return true; 
                case 2: // �������, ����������� ����� ������� (�������)
                    return (child != null) && !child.isAtEnd(); 
                case END_STATE: // �������� ���������
                    return true; 
            }

            throw new RuntimeException("isInterest"); 
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
                    comment = MergeSort.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 2: { // �������, ����������� ����� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = MergeSort.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.hideStack();
                    d.visualizer.updateBrackets(-1, 0);
                    d.visualizer.updateMainArray(0, 0, d.a.length - 1, 0, 0, 0, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // ���������� � ������� �������� ���������� �������
                    d.visualizer.hideStack();
                    d.visualizer.updateMainArray(0, 0, d.a.length - 1, 0, 0, 0, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 2: { // �������, ����������� ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.hideStack();
                    d.visualizer.updateBrackets(-1, 1);
                    d.visualizer.updateMainArray(0, 0, d.a.length - 1, 9, 9, 9, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * �������, ����������� ����� �������.
      */
    private final class MSort extends BaseAutomata implements Automata {
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
        public MSort() {
            super( 
                "MSort", 
                0, // ����� ���������� ��������� 
                11, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "���������� � ���������� ��������� ����� �������", 
                    "��������� ����� ����������� ����� �������", 
                    "��������� ����� ����������� ����� ������� (���������)", 
                    "������ ������� �� ������ ��������", 
                    "������ ������� �� ���������� ���������.                                    ���������� ����� ��������", 
                    "�������, ����������� ����� ������� (�������)", 
                    "������ ������� �� ���������� ���������.                                    ���������� ������ ��������", 
                    "�������, ����������� ����� ������� (�������)", 
                    "�������, ����������� ������� �������� (�������)", 
                    "������� ���������. ������ ������������.", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    0, // ���������� � ���������� ��������� ����� ������� 
                    -1, // ��������� ����� ����������� ����� ������� 
                    -1, // ��������� ����� ����������� ����� ������� (���������) 
                    0, // ������ ������� �� ������ �������� 
                    0, // ������ ������� �� ���������� ���������.                                    ���������� ����� �������� 
                    1, // �������, ����������� ����� ������� (�������) 
                    0, // ������ ������� �� ���������� ���������.                                    ���������� ������ �������� 
                    1, // �������, ����������� ����� ������� (�������) 
                    1, // �������, ����������� ������� �������� (�������) 
                    1, // ������� ���������. ������ ������������. 
                    Integer.MAX_VALUE, // �������� ��������� 
                } 
            ); 
        }

        /**
          * ������� ���� ��� �������� � �����.
          */
        protected void doStepForward(int level) {
            do {
                // ������� � ��������� ���������
                switch (state) {
                    case START_STATE: { // ��������� ���������
                        state = 1; // ���������� � ���������� ��������� ����� �������
                        break;
                    }
                    case 1: { // ���������� � ���������� ��������� ����� �������
                        state = 2; // ��������� ����� ����������� ����� �������
                        break;
                    }
                    case 2: { // ��������� ����� ����������� ����� �������
                        if (d.l[d.h] == d.r[d.h]) {
                            state = 4; // ������ ������� �� ������ ��������
                        } else {
                            state = 5; // ������ ������� �� ���������� ���������.                                    ���������� ����� ��������
                        }
                        break;
                    }
                    case 3: { // ��������� ����� ����������� ����� ������� (���������)
                        state = END_STATE; 
                        break;
                    }
                    case 4: { // ������ ������� �� ������ ��������
                        stack.pushBoolean(true); 
                        state = 3; // ��������� ����� ����������� ����� ������� (���������)
                        break;
                    }
                    case 5: { // ������ ������� �� ���������� ���������.                                    ���������� ����� ��������
                        state = 6; // �������, ����������� ����� ������� (�������)
                        break;
                    }
                    case 6: { // �������, ����������� ����� ������� (�������)
                        if (child.isAtEnd()) {
                            child = null; 
                            state = 7; // ������ ������� �� ���������� ���������.                                    ���������� ������ ��������
                        }
                        break;
                    }
                    case 7: { // ������ ������� �� ���������� ���������.                                    ���������� ������ ��������
                        state = 8; // �������, ����������� ����� ������� (�������)
                        break;
                    }
                    case 8: { // �������, ����������� ����� ������� (�������)
                        if (child.isAtEnd()) {
                            child = null; 
                            state = 9; // �������, ����������� ������� �������� (�������)
                        }
                        break;
                    }
                    case 9: { // �������, ����������� ������� �������� (�������)
                        if (child.isAtEnd()) {
                            child = null; 
                            state = 10; // ������� ���������. ������ ������������.
                        }
                        break;
                    }
                    case 10: { // ������� ���������. ������ ������������.
                        stack.pushBoolean(false); 
                        state = 3; // ��������� ����� ����������� ����� ������� (���������)
                        break;
                    }
                }

                // �������� � ������� ���������
                switch (state) {
                    case 1: { // ���������� � ���������� ��������� ����� �������
                        startSection();
                        storeField(d, "h");
                        d.h = d.h + 1;
                        break;
                    }
                    case 2: { // ��������� ����� ����������� ����� �������
                        break;
                    }
                    case 3: { // ��������� ����� ����������� ����� ������� (���������)
                        break;
                    }
                    case 4: { // ������ ������� �� ������ ��������
                        startSection();
                        storeField(d, "h");
                        d.h = d.h - 1;
                        break;
                    }
                    case 5: { // ������ ������� �� ���������� ���������.                                    ���������� ����� ��������
                        startSection();
                        storeArray(d.l, d.h + 1);
                        d.l[d.h + 1] = d.l[d.h];
                        storeArray(d.r, d.h + 1);
                        d.r[d.h + 1] = (d.l[d.h] + d.r[d.h]) / 2;
                        break;
                    }
                    case 6: { // �������, ����������� ����� ������� (�������)
                        if (child == null) {
                            child = new MSort(); 
                            child.toStart(); 
                        }
                        child.stepForward(level); 
                        step--; 
                        break;
                    }
                    case 7: { // ������ ������� �� ���������� ���������.                                    ���������� ������ ��������
                        startSection();
                        storeArray(d.l, d.h + 1);
                        d.l[d.h + 1] = (d.l[d.h] + d.r[d.h]) / 2 + 1;
                        storeArray(d.r, d.h + 1);
                        d.r[d.h + 1] = d.r[d.h];
                        break;
                    }
                    case 8: { // �������, ����������� ����� ������� (�������)
                        if (child == null) {
                            child = new MSort(); 
                            child.toStart(); 
                        }
                        child.stepForward(level); 
                        step--; 
                        break;
                    }
                    case 9: { // �������, ����������� ������� �������� (�������)
                        if (child == null) {
                            child = new Merge(); 
                            child.toStart(); 
                        }
                        child.stepForward(level); 
                        step--; 
                        break;
                    }
                    case 10: { // ������� ���������. ������ ������������.
                        startSection();
                        storeField(d, "h");
                        d.h = d.h - 1;
                        break;
                    }
                }
            } while (!isInterestingTemp(level));
        }

        /**
          * ������� ���� ��� �������� �����.
          */
        protected void doStepBackward(int level) {
            do {
                // ��������� �������� � ������� ���������
                switch (state) {
                    case 1: { // ���������� � ���������� ��������� ����� �������
                        restoreSection();
                        break;
                    }
                    case 2: { // ��������� ����� ����������� ����� �������
                        break;
                    }
                    case 3: { // ��������� ����� ����������� ����� ������� (���������)
                        break;
                    }
                    case 4: { // ������ ������� �� ������ ��������
                        restoreSection();
                        break;
                    }
                    case 5: { // ������ ������� �� ���������� ���������.                                    ���������� ����� ��������
                        restoreSection();
                        break;
                    }
                    case 6: { // �������, ����������� ����� ������� (�������)
                        if (child == null) {
                            child = new MSort(); 
                            child.toEnd(); 
                        }
                        child.stepBackward(level); 
                        step++; 
                        break;
                    }
                    case 7: { // ������ ������� �� ���������� ���������.                                    ���������� ������ ��������
                        restoreSection();
                        break;
                    }
                    case 8: { // �������, ����������� ����� ������� (�������)
                        if (child == null) {
                            child = new MSort(); 
                            child.toEnd(); 
                        }
                        child.stepBackward(level); 
                        step++; 
                        break;
                    }
                    case 9: { // �������, ����������� ������� �������� (�������)
                        if (child == null) {
                            child = new Merge(); 
                            child.toEnd(); 
                        }
                        child.stepBackward(level); 
                        step++; 
                        break;
                    }
                    case 10: { // ������� ���������. ������ ������������.
                        restoreSection();
                        break;
                    }
                }

                // ������� � ���������� ���������
                switch (state) {
                    case 1: { // ���������� � ���������� ��������� ����� �������
                        state = START_STATE; 
                        break;
                    }
                    case 2: { // ��������� ����� ����������� ����� �������
                        state = 1; // ���������� � ���������� ��������� ����� �������
                        break;
                    }
                    case 3: { // ��������� ����� ����������� ����� ������� (���������)
                        if (stack.popBoolean()) {
                            state = 4; // ������ ������� �� ������ ��������
                        } else {
                            state = 10; // ������� ���������. ������ ������������.
                        }
                        break;
                    }
                    case 4: { // ������ ������� �� ������ ��������
                        state = 2; // ��������� ����� ����������� ����� �������
                        break;
                    }
                    case 5: { // ������ ������� �� ���������� ���������.                                    ���������� ����� ��������
                        state = 2; // ��������� ����� ����������� ����� �������
                        break;
                    }
                    case 6: { // �������, ����������� ����� ������� (�������)
                        if (child.isAtStart()) {
                            child = null; 
                            state = 5; // ������ ������� �� ���������� ���������.                                    ���������� ����� ��������
                        }
                        break;
                    }
                    case 7: { // ������ ������� �� ���������� ���������.                                    ���������� ������ ��������
                        state = 6; // �������, ����������� ����� ������� (�������)
                        break;
                    }
                    case 8: { // �������, ����������� ����� ������� (�������)
                        if (child.isAtStart()) {
                            child = null; 
                            state = 7; // ������ ������� �� ���������� ���������.                                    ���������� ������ ��������
                        }
                        break;
                    }
                    case 9: { // �������, ����������� ������� �������� (�������)
                        if (child.isAtStart()) {
                            child = null; 
                            state = 8; // �������, ����������� ����� ������� (�������)
                        }
                        break;
                    }
                    case 10: { // ������� ���������. ������ ������������.
                        state = 9; // �������, ����������� ������� �������� (�������)
                        break;
                    }
                    case END_STATE: { // ��������� ���������
                        state = 3; // ��������� ����� ����������� ����� ������� (���������)
                        break;
                    }
                }
            } while (!isInterestingTemp(level));
        }

        /**
          * ��������� �� ������� ��������� (����������� ���� � call-auto).
          */
        public boolean isInterestingTemp(int level) {
            // ������������
            switch (state) {
                case START_STATE: // ��������� ���������
                    return true; 
                case 1: // ���������� � ���������� ��������� ����� �������
                    return true; 
                case 2: // ��������� ����� ����������� ����� �������
                    return true; 
                case 3: // ��������� ����� ����������� ����� ������� (���������)
                    return true; 
                case 4: // ������ ������� �� ������ ��������
                    return true; 
                case 5: // ������ ������� �� ���������� ���������.                                    ���������� ����� ��������
                    return true; 
                case 6: // �������, ����������� ����� ������� (�������)
                    return (child != null) && !child.isAtEnd(); 
                case 7: // ������ ������� �� ���������� ���������.                                    ���������� ������ ��������
                    return true; 
                case 8: // �������, ����������� ����� ������� (�������)
                    return (child != null) && !child.isAtEnd(); 
                case 9: // �������, ����������� ������� �������� (�������)
                    return (child != null) && !child.isAtEnd(); 
                case 10: // ������� ���������. ������ ������������.
                    return true; 
                case END_STATE: // �������� ���������
                    return true; 
            }

            throw new RuntimeException("isInterest"); 
        }

        /**
          * ����������� � �������� ���������
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // ����� �����������
            switch (state) {
                case 1: { // ���������� � ���������� ��������� ����� �������
                    comment = MergeSort.this.getComment("MSort.StepPreparingForSortingArrayPart"); 
                    args = new Object[]{new Integer(d.l[d.h] + 1),                             new Integer(d.r[d.h] + 1)}; 
                    break;
                }
                case 4: { // ������ ������� �� ������ ��������
                    comment = MergeSort.this.getComment("MSort.StepOneElementInArrayPart"); 
                    args = new Object[]{new Integer(d.a[d.l[d.h + 1]]),                                     new Integer(d.l[d.h + 1] + 1)}; 
                    break;
                }
                case 5: { // ������ ������� �� ���������� ���������.                                    ���������� ����� ��������
                    comment = MergeSort.this.getComment("MSort.StepManyElementsLeftSide"); 
                    args = new Object[]{new Integer(d.l[d.h] + 1),                                     new Integer(d.r[d.h + 1] + 1),                                     new Integer(d.r[d.h + 1] + 2),                                     new Integer(d.r[d.h] + 1)}; 
                    break;
                }
                case 6: { // �������, ����������� ����� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // ������ ������� �� ���������� ���������.                                    ���������� ������ ��������
                    comment = MergeSort.this.getComment("MSort.StepManyElementsRightSide"); 
                    args = new Object[]{new Integer(d.l[d.h] + 1),                                     new Integer(d.l[d.h + 1] + 1),                                     new Integer(d.r[d.h] + 1)}; 
                    break;
                }
                case 8: { // �������, ����������� ����� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 9: { // �������, ����������� ������� �������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // ������� ���������. ������ ������������.
                    comment = MergeSort.this.getComment("MSort.StepAfterMerging"); 
                    args = new Object[]{new Integer(d.l[d.h + 1] + 1),                                     new Integer(d.r[d.h + 1] + 1)}; 
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
                case 1: { // ���������� � ���������� ��������� ����� �������
                    d.visualizer.showStack(d.h, 0, true);
                    d.visualizer.updateMainArray(d.l[d.h], d.l[d.h], d.r[d.h],
                                                 3, 3, 3, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 4: { // ������ ������� �� ������ ��������
                    d.visualizer.showStack(d.h + 1, 2, true);
                    d.visualizer.updateMainArray(d.l[d.h + 1], d.l[d.h + 1],
                                                 d.l[d.h + 1], 4, 4, 4, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 5: { // ������ ������� �� ���������� ���������.                                    ���������� ����� ��������
                    d.visualizer.showStack(d.h, 0, true);
                    d.visualizer.updateMainArray(d.l[d.h], d.r[d.h + 1],
                                                 d.r[d.h], 1, 1, 2, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 6: { // �������, ����������� ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 7: { // ������ ������� �� ���������� ���������.                                    ���������� ������ ��������
                    d.visualizer.showStack(d.h, 1, true);
                    d.visualizer.updateMainArray(d.l[d.h], d.l[d.h + 1] - 1,
                                                 d.r[d.h], 1, 1, 2, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 8: { // �������, ����������� ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 9: { // �������, ����������� ������� �������� (�������)
                    child.drawState(); 
                    break;
                }
                case 10: { // ������� ���������. ������ ������������.
                    d.visualizer.showStack(d.h + 1, 2, true);
                    d.visualizer.updateMainArray(d.l[d.h + 1], d.l[d.h + 1],
                                                 d.r[d.h + 1], 4, 4, 4, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * �������, ����������� ������� ��������.
      */
    private final class Merge extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 17;

        /**
          * �����������.
          */
        public Merge() {
            super( 
                "Merge", 
                0, // ����� ���������� ��������� 
                17, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "���������� � ����������� ���������", 
                    "���� ����������� ���������", 
                    "����������� ��������", 
                    "���������� � ������� ��������", 
                    "���� ������� ��������", 
                    "�������� ���� ��������", 
                    "�������� ���� �������� (���������)", 
                    "�������� ����� ������� �������", 
                    "�������� ����� ������� ������� (���������)", 
                    "������ ������ ����������", 
                    "������ ������ ����������", 
                    "���� ���������� �������� ��������", 
                    "��������� ��������� ��������", 
                    "��������� ��������� �������� (���������)", 
                    "������� ������� ������� ������                                                �������� ������� �������", 
                    "������� ������� ������� ��                                                ����������� �������� �������                                                �������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ���������� � ����������� ��������� 
                    -1, // ���� ����������� ��������� 
                    -1, // ����������� �������� 
                    1, // ���������� � ������� �������� 
                    -1, // ���� ������� �������� 
                    -1, // �������� ���� �������� 
                    -1, // �������� ���� �������� (���������) 
                    -1, // �������� ����� ������� ������� 
                    -1, // �������� ����� ������� ������� (���������) 
                    1, // ������ ������ ���������� 
                    1, // ������ ������ ���������� 
                    1, // ���� ���������� �������� �������� 
                    -1, // ��������� ��������� �������� 
                    -1, // ��������� ��������� �������� (���������) 
                    1, // ������� ������� ������� ������                                                �������� ������� ������� 
                    1, // ������� ������� ������� ��                                                ����������� �������� �������                                                ������� 
                    Integer.MAX_VALUE, // �������� ��������� 
                } 
            ); 
        }

        /**
          * ������� ���� ��� �������� � �����.
          */
        protected void doStepForward(int level) {
            do {
                // ������� � ��������� ���������
                switch (state) {
                    case START_STATE: { // ��������� ���������
                        state = 1; // ���������� � ����������� ���������
                        break;
                    }
                    case 1: { // ���������� � ����������� ���������
                        stack.pushBoolean(false); 
                        state = 2; // ���� ����������� ���������
                        break;
                    }
                    case 2: { // ���� ����������� ���������
                        if (d.Merge_k <= d.r[d.h]) {
                            state = 3; // ����������� ��������
                        } else {
                            state = 4; // ���������� � ������� ��������
                        }
                        break;
                    }
                    case 3: { // ����������� ��������
                        stack.pushBoolean(true); 
                        state = 2; // ���� ����������� ���������
                        break;
                    }
                    case 4: { // ���������� � ������� ��������
                        stack.pushBoolean(false); 
                        state = 5; // ���� ������� ��������
                        break;
                    }
                    case 5: { // ���� ������� ��������
                        if (d.Merge_k <= d.r[d.h]) {
                            state = 6; // �������� ���� ��������
                        } else {
                            state = END_STATE; 
                        }
                        break;
                    }
                    case 6: { // �������� ���� ��������
                        if ((d.Merge_i >= d.l[d.h + 1]) || (d.Merge_j > d.r[d.h])) {
                            state = 8; // �������� ����� ������� �������
                        } else {
                            state = 12; // ���� ���������� �������� ��������
                        }
                        break;
                    }
                    case 7: { // �������� ���� �������� (���������)
                        stack.pushBoolean(true); 
                        state = 5; // ���� ������� ��������
                        break;
                    }
                    case 8: { // �������� ����� ������� �������
                        if (d.Merge_i >= d.l[d.h + 1]) {
                            state = 10; // ������ ������ ����������
                        } else {
                            state = 11; // ������ ������ ����������
                        }
                        break;
                    }
                    case 9: { // �������� ����� ������� ������� (���������)
                        stack.pushBoolean(true); 
                        state = 7; // �������� ���� �������� (���������)
                        break;
                    }
                    case 10: { // ������ ������ ����������
                        stack.pushBoolean(true); 
                        state = 9; // �������� ����� ������� ������� (���������)
                        break;
                    }
                    case 11: { // ������ ������ ����������
                        stack.pushBoolean(false); 
                        state = 9; // �������� ����� ������� ������� (���������)
                        break;
                    }
                    case 12: { // ���� ���������� �������� ��������
                        state = 13; // ��������� ��������� ��������
                        break;
                    }
                    case 13: { // ��������� ��������� ��������
                        if (d.Merge_b[d.Merge_i] > d.Merge_b[d.Merge_j]) {
                            state = 15; // ������� ������� ������� ������                                                �������� ������� �������
                        } else {
                            state = 16; // ������� ������� ������� ��                                                ����������� �������� �������                                                �������
                        }
                        break;
                    }
                    case 14: { // ��������� ��������� �������� (���������)
                        stack.pushBoolean(false); 
                        state = 7; // �������� ���� �������� (���������)
                        break;
                    }
                    case 15: { // ������� ������� ������� ������                                                �������� ������� �������
                        stack.pushBoolean(true); 
                        state = 14; // ��������� ��������� �������� (���������)
                        break;
                    }
                    case 16: { // ������� ������� ������� ��                                                ����������� �������� �������                                                �������
                        stack.pushBoolean(false); 
                        state = 14; // ��������� ��������� �������� (���������)
                        break;
                    }
                }

                // �������� � ������� ���������
                switch (state) {
                    case 1: { // ���������� � ����������� ���������
                        startSection();
                        storeField(d, "Merge_k");
                        d.Merge_k = d.l[d.h];
                        storeField(d, "Merge_b");
                        d.Merge_b = (int[]) (d.a.clone());
                        break;
                    }
                    case 2: { // ���� ����������� ���������
                        break;
                    }
                    case 3: { // ����������� ��������
                        startSection();
                        storeArray(d.Merge_b, d.Merge_k);
                        d.Merge_b[d.Merge_k] = d.a[d.Merge_k];
                        storeField(d, "Merge_k");
                        d.Merge_k = d.Merge_k + 1;
                        break;
                    }
                    case 4: { // ���������� � ������� ��������
                        startSection();
                        storeField(d, "Merge_i");
                        d.Merge_i = d.l[d.h];
                        storeField(d, "Merge_j");
                        d.Merge_j = d.l[d.h + 1];
                        storeField(d, "Merge_k");
                        d.Merge_k = d.l[d.h];
                        break;
                    }
                    case 5: { // ���� ������� ��������
                        break;
                    }
                    case 6: { // �������� ���� ��������
                        break;
                    }
                    case 7: { // �������� ���� �������� (���������)
                        break;
                    }
                    case 8: { // �������� ����� ������� �������
                        break;
                    }
                    case 9: { // �������� ����� ������� ������� (���������)
                        break;
                    }
                    case 10: { // ������ ������ ����������
                        startSection();
                        storeArray(d.a, d.Merge_k);
                        d.a[d.Merge_k] = d.Merge_b[d.Merge_j];
                        storeField(d, "Merge_j");
                        d.Merge_j = d.Merge_j + 1;
                        storeField(d, "Merge_k");
                        d.Merge_k = d.Merge_k + 1;
                        break;
                    }
                    case 11: { // ������ ������ ����������
                        startSection();
                        storeArray(d.a, d.Merge_k);
                        d.a[d.Merge_k] = d.Merge_b[d.Merge_i];
                        storeField(d, "Merge_i");
                        d.Merge_i = d.Merge_i + 1;
                        storeField(d, "Merge_k");
                        d.Merge_k = d.Merge_k + 1;
                        break;
                    }
                    case 12: { // ���� ���������� �������� ��������
                        startSection();
                        break;
                    }
                    case 13: { // ��������� ��������� ��������
                        break;
                    }
                    case 14: { // ��������� ��������� �������� (���������)
                        break;
                    }
                    case 15: { // ������� ������� ������� ������                                                �������� ������� �������
                        startSection();
                        storeArray(d.a, d.Merge_k);
                        d.a[d.Merge_k] = d.Merge_b[d.Merge_j];
                        storeField(d, "Merge_j");
                        d.Merge_j = d.Merge_j + 1;
                        storeField(d, "Merge_k");
                        d.Merge_k = d.Merge_k + 1;
                        break;
                    }
                    case 16: { // ������� ������� ������� ��                                                ����������� �������� �������                                                �������
                        startSection();
                        storeArray(d.a, d.Merge_k);
                        d.a[d.Merge_k] = d.Merge_b[d.Merge_i];
                        storeField(d, "Merge_i");
                        d.Merge_i = d.Merge_i + 1;
                        storeField(d, "Merge_k");
                        d.Merge_k = d.Merge_k + 1;
                        break;
                    }
                }
            } while (!isInterestingTemp(level));
        }

        /**
          * ������� ���� ��� �������� �����.
          */
        protected void doStepBackward(int level) {
            do {
                // ��������� �������� � ������� ���������
                switch (state) {
                    case 1: { // ���������� � ����������� ���������
                        restoreSection();
                        break;
                    }
                    case 2: { // ���� ����������� ���������
                        break;
                    }
                    case 3: { // ����������� ��������
                        restoreSection();
                        break;
                    }
                    case 4: { // ���������� � ������� ��������
                        restoreSection();
                        break;
                    }
                    case 5: { // ���� ������� ��������
                        break;
                    }
                    case 6: { // �������� ���� ��������
                        break;
                    }
                    case 7: { // �������� ���� �������� (���������)
                        break;
                    }
                    case 8: { // �������� ����� ������� �������
                        break;
                    }
                    case 9: { // �������� ����� ������� ������� (���������)
                        break;
                    }
                    case 10: { // ������ ������ ����������
                        restoreSection();
                        break;
                    }
                    case 11: { // ������ ������ ����������
                        restoreSection();
                        break;
                    }
                    case 12: { // ���� ���������� �������� ��������
                        restoreSection();
                        break;
                    }
                    case 13: { // ��������� ��������� ��������
                        break;
                    }
                    case 14: { // ��������� ��������� �������� (���������)
                        break;
                    }
                    case 15: { // ������� ������� ������� ������                                                �������� ������� �������
                        restoreSection();
                        break;
                    }
                    case 16: { // ������� ������� ������� ��                                                ����������� �������� �������                                                �������
                        restoreSection();
                        break;
                    }
                }

                // ������� � ���������� ���������
                switch (state) {
                    case 1: { // ���������� � ����������� ���������
                        state = START_STATE; 
                        break;
                    }
                    case 2: { // ���� ����������� ���������
                        if (stack.popBoolean()) {
                            state = 3; // ����������� ��������
                        } else {
                            state = 1; // ���������� � ����������� ���������
                        }
                        break;
                    }
                    case 3: { // ����������� ��������
                        state = 2; // ���� ����������� ���������
                        break;
                    }
                    case 4: { // ���������� � ������� ��������
                        state = 2; // ���� ����������� ���������
                        break;
                    }
                    case 5: { // ���� ������� ��������
                        if (stack.popBoolean()) {
                            state = 7; // �������� ���� �������� (���������)
                        } else {
                            state = 4; // ���������� � ������� ��������
                        }
                        break;
                    }
                    case 6: { // �������� ���� ��������
                        state = 5; // ���� ������� ��������
                        break;
                    }
                    case 7: { // �������� ���� �������� (���������)
                        if (stack.popBoolean()) {
                            state = 9; // �������� ����� ������� ������� (���������)
                        } else {
                            state = 14; // ��������� ��������� �������� (���������)
                        }
                        break;
                    }
                    case 8: { // �������� ����� ������� �������
                        state = 6; // �������� ���� ��������
                        break;
                    }
                    case 9: { // �������� ����� ������� ������� (���������)
                        if (stack.popBoolean()) {
                            state = 10; // ������ ������ ����������
                        } else {
                            state = 11; // ������ ������ ����������
                        }
                        break;
                    }
                    case 10: { // ������ ������ ����������
                        state = 8; // �������� ����� ������� �������
                        break;
                    }
                    case 11: { // ������ ������ ����������
                        state = 8; // �������� ����� ������� �������
                        break;
                    }
                    case 12: { // ���� ���������� �������� ��������
                        state = 6; // �������� ���� ��������
                        break;
                    }
                    case 13: { // ��������� ��������� ��������
                        state = 12; // ���� ���������� �������� ��������
                        break;
                    }
                    case 14: { // ��������� ��������� �������� (���������)
                        if (stack.popBoolean()) {
                            state = 15; // ������� ������� ������� ������                                                �������� ������� �������
                        } else {
                            state = 16; // ������� ������� ������� ��                                                ����������� �������� �������                                                �������
                        }
                        break;
                    }
                    case 15: { // ������� ������� ������� ������                                                �������� ������� �������
                        state = 13; // ��������� ��������� ��������
                        break;
                    }
                    case 16: { // ������� ������� ������� ��                                                ����������� �������� �������                                                �������
                        state = 13; // ��������� ��������� ��������
                        break;
                    }
                    case END_STATE: { // ��������� ���������
                        state = 5; // ���� ������� ��������
                        break;
                    }
                }
            } while (!isInterestingTemp(level));
        }

        /**
          * ��������� �� ������� ��������� (����������� ���� � call-auto).
          */
        public boolean isInterestingTemp(int level) {
            // ������������
            switch (state) {
                case START_STATE: // ��������� ���������
                    return true; 
                case 1: // ���������� � ����������� ���������
                    return true; 
                case 2: // ���� ����������� ���������
                    return true; 
                case 3: // ����������� ��������
                    return true; 
                case 4: // ���������� � ������� ��������
                    return true; 
                case 5: // ���� ������� ��������
                    return true; 
                case 6: // �������� ���� ��������
                    return true; 
                case 7: // �������� ���� �������� (���������)
                    return true; 
                case 8: // �������� ����� ������� �������
                    return true; 
                case 9: // �������� ����� ������� ������� (���������)
                    return true; 
                case 10: // ������ ������ ����������
                    return true; 
                case 11: // ������ ������ ����������
                    return true; 
                case 12: // ���� ���������� �������� ��������
                    return true; 
                case 13: // ��������� ��������� ��������
                    return true; 
                case 14: // ��������� ��������� �������� (���������)
                    return true; 
                case 15: // ������� ������� ������� ������                                                �������� ������� �������
                    return true; 
                case 16: // ������� ������� ������� ��                                                ����������� �������� �������                                                �������
                    return true; 
                case END_STATE: // �������� ���������
                    return true; 
            }

            throw new RuntimeException("isInterest"); 
        }

        /**
          * ����������� � �������� ���������
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // ����� �����������
            switch (state) {
                case 4: { // ���������� � ������� ��������
                    comment = MergeSort.this.getComment("Merge.StepPreparingForMerging"); 
                    args = new Object[]{new Integer(d.l[d.h] + 1),                             new Integer(d.l[d.h + 1]),                             new Integer(d.l[d.h + 1] + 1),                             new Integer(d.r[d.h] + 1)}; 
                    break;
                }
                case 10: { // ������ ������ ����������
                    comment = MergeSort.this.getComment("Merge.StepFirstArrayIsEmpty"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_j - 1])}; 
                    break;
                }
                case 11: { // ������ ������ ����������
                    comment = MergeSort.this.getComment("Merge.StepSecondArrayIsEmpty"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_i - 1])}; 
                    break;
                }
                case 12: { // ���� ���������� �������� ��������
                    comment = MergeSort.this.getComment("Merge.StepBeforeCompareElements"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_i]),                                         new Integer(d.Merge_b[d.Merge_j])}; 
                    break;
                }
                case 15: { // ������� ������� ������� ������                                                �������� ������� �������
                    comment = MergeSort.this.getComment("Merge.StepSecondElementIsLower"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_i]),                                                 new Integer(d.Merge_b[d.Merge_j - 1])}; 
                    break;
                }
                case 16: { // ������� ������� ������� ��                                                ����������� �������� �������                                                �������
                    comment = MergeSort.this.getComment("Merge.StepFirstElementNotGreater"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_i - 1]),                                                 new Integer(d.Merge_b[d.Merge_j])}; 
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
                case 1: { // ���������� � ����������� ���������
                    break;
                }
                case 3: { // ����������� ��������
                    break;
                }
                case 4: { // ���������� � ������� ��������
                    d.visualizer.showStack(d.h, 2, true);
                    d.visualizer.updateMainArray(d.l[d.h], d.l[d.h + 1] - 1,
                                                 d.r[d.h], 1, 1, 2, 0);
                    d.visualizer.showAuxArrays(d.l[d.h], d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.l[d.h] - 1, d.l[d.h + 1] - 1, 1,
                                                 0, 2, 2);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 10: { // ������ ������ ����������
                    d.visualizer.updateMainArray(d.l[d.h],
                                                 d.Merge_k - 1,
                                                 d.r[d.h],
                                                 5, 6, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i, d.Merge_j - 1,
                                                 1, 0, 2, 3);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 11: { // ������ ������ ����������
                    d.visualizer.updateMainArray(d.l[d.h],
                                                 d.Merge_k - 1,
                                                 d.r[d.h],
                                                 5, 6, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i - 1, d.Merge_j,
                                                 1, 0, 3, 2);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 12: { // ���� ���������� �������� ��������
                    d.visualizer.updateMainArray(d.l[d.h], d.Merge_k - 1,
                                                 d.r[d.h], 5, 5, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i, d.Merge_j,
                                                 1, 0, 2, 2);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 15: { // ������� ������� ������� ������                                                �������� ������� �������
                    d.visualizer.updateMainArray(d.l[d.h],
                                                 d.Merge_k - 1,
                                                 d.r[d.h],
                                                 5, 6, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i, d.Merge_j - 1,
                                                 1, 0, 2, 3);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 16: { // ������� ������� ������� ��                                                ����������� �������� �������                                                �������
                    d.visualizer.updateMainArray(d.l[d.h],
                                                 d.Merge_k - 1,
                                                 d.r[d.h],
                                                 5, 6, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i - 1, d.Merge_j,
                                                 1, 0, 3, 2);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }
}
