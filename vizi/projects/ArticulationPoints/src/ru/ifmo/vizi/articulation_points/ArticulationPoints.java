package ru.ifmo.vizi.articulation_points;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class ArticulationPoints extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public ArticulationPoints(Locale locale) {
        super("ru.ifmo.vizi.articulation_points.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ����� ������ � �����.
          */
        public int numberOfVertices = 10;

        /**
          * ������� ���������.
          */
        public int[][] aMatrix = new int[10][10];

        /**
          * ������, ���������� ������ ������ ��� ������ ����� � �������.
          */
        public int[] dfnumber = new int[10];

        /**
          * ������, ���������� �������� ����� low ���� ������.
          */
        public int[] low = new int[10];

        /**
          * ������, ���������� ������ ������, � ��� �������, � ������� ��� ����������.
          */
        public int[] way = new int[10];

        /**
          * ������ ���������.
          */
        public boolean[] visited = new boolean[10];

        /**
          * ����� ���������� ������.
          */
        public int numberOfVisited = 0;

        /**
          * �������� �������.
          */
        public int activeAuto = 0;

        /**
          * ������ ������ ������.
          */
        public int root = 0;

        /**
          * ����� ����������.
          */
        public int[] articulationPoints = new int[10];

        /**
          * ��������� �������.
          */
        public ArticulationPointsVisualizer visualizer = null;

        /**
          * ���� (��������� DFS).
          */
        public int[] DFS_stack;

        /**
          * ������ '������' ����� (��������� DFS).
          */
        public int DFS_head;

        /**
          * ���������� ����� (��������� DFS).
          */
        public int DFS_i;

        /**
          * ���������� ������� ������ �� ����� (��������� DFS).
          */
        public boolean DFS_flag;

        /**
          * �������� ������� (��������� DFS).
          */
        public int DFS_activeVertex;

        /**
          * ������� ������� (��������� findLow).
          */
        public int findLow_currentVertex;

        /**
          * ���������� ����� (��������� findLow).
          */
        public int findLow_i;

        /**
          * ���������� ����� (��������� findLow).
          */
        public int findLow_j;

        /**
          * �������� ������� � ������� low (��������� findLow).
          */
        public int findLow_activeVertexInLow;

        /**
          * �������� ������� � ������� dfnum (��������� findLow).
          */
        public int findLow_activeVertexInDfnum;

        /**
          * ���������� ����� � ����� (��������� findArticulationPoints).
          */
        public int findArticulationPoints_childsCounter;

        /**
          * ���������� ����� (��������� findArticulationPoints).
          */
        public int findArticulationPoints_i;

        /**
          * ���������� ����� (��������� findArticulationPoints).
          */
        public int findArticulationPoints_j;

        /**
          * ���������� ������� ������ �� ����� (��������� findArticulationPoints).
          */
        public boolean findArticulationPoints_flag;

        /**
          * �������� ������� � ������� low (��������� findArticulationPoints).
          */
        public int findArticulationPoints_activeVertexInLow;

        /**
          * ������� ������� (��������� findArticulationPoints).
          */
        public int findArticulationPoints_currentVertex;

        /**
          * ���������� ����� (��������� findBridges).
          */
        public int findBridges_i;

        /**
          * ���������� ����� (��������� findBridges).
          */
        public int findBridges_j;

        /**
          * ������ �������� ������� � ������� low (��������� findBridges).
          */
        public int findBridges_activeVertexInLow1;

        /**
          * ������ �������� ������� � ������� low (��������� findBridges).
          */
        public int findBridges_activeVertexInLow2;

        /**
          * ������ �������� ������� � ������� dfnumber (��������� findBridges).
          */
        public int findBridges_activeVertexInDfnum1;

        /**
          * ������ �������� ������� � ������� dfnumber (��������� findBridges).
          */
        public int findBridges_activeVertexInDfnum2;

        /**
          * ���������� ����� (��������� Main).
          */
        public int Main_i;

        public String toString() {
            			StringBuffer s = new StringBuffer();
                        return s.toString();
        }
    }

    /**
      * ������� ���� � �������.
      */
    private final class DFS extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 19;

        /**
          * �����������.
          */
        public DFS() {
            super( 
                "DFS", 
                0, // ����� ���������� ��������� 
                19, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "����� � �������", 
                    "�������� �������, � ������� �������� ����� � ��������� �� � ����", 
                    "����� ������", 
                    "���������� i � ����", 
                    "���� ���������� �������", 
                    "���������, ���� �� i-��� ������� ��������", 
                    "���������, ���� �� i-��� ������� �������� (���������)", 
                    "�������, ���� �� �����, ����������� ��������� ��������� ������� � i-���", 
                    "�������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� (���������)", 
                    "�������� ��������� ������� � ��������� �� � ����", 
                    "��������� i", 
                    "������� ������������� ��������", 
                    "������� ������������� �������� (���������)", 
                    "����� �����������", 
                    "�������� �� ������� ���������", 
                    "�������� �� ������� ��������� (���������)", 
                    "������� ��������� � �����", 
                    "������� �� ��������� � �����", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    1, // ����� � ������� 
                    0, // �������� �������, � ������� �������� ����� � ��������� �� � ���� 
                    -1, // ����� ������ 
                    -1, // ���������� i � ���� 
                    -1, // ���� ���������� ������� 
                    -1, // ���������, ���� �� i-��� ������� �������� 
                    -1, // ���������, ���� �� i-��� ������� �������� (���������) 
                    -1, // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� 
                    -1, // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� (���������) 
                    0, // �������� ��������� ������� � ��������� �� � ���� 
                    -1, // ��������� i 
                    -1, // ������� ������������� �������� 
                    -1, // ������� ������������� �������� (���������) 
                    0, // ����� ����������� 
                    -1, // �������� �� ������� ��������� 
                    -1, // �������� �� ������� ��������� (���������) 
                    0, // ������� ��������� � ����� 
                    0, // ������� �� ��������� � ����� 
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
                    state = 1; // ����� � �������
                    break;
                }
                case 1: { // ����� � �������
                    state = 2; // �������� �������, � ������� �������� ����� � ��������� �� � ����
                    break;
                }
                case 2: { // �������� �������, � ������� �������� ����� � ��������� �� � ����
                    stack.pushBoolean(false); 
                    state = 3; // ����� ������
                    break;
                }
                case 3: { // ����� ������
                    if (d.DFS_head > 0) {
                        state = 4; // ���������� i � ����
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 4: { // ���������� i � ����
                    stack.pushBoolean(false); 
                    state = 5; // ���� ���������� �������
                    break;
                }
                case 5: { // ���� ���������� �������
                    if (d.DFS_i < d.numberOfVertices && d.DFS_flag) {
                        state = 6; // ���������, ���� �� i-��� ������� ��������
                    } else {
                        state = 12; // ������� ������������� ��������
                    }
                    break;
                }
                case 6: { // ���������, ���� �� i-��� ������� ��������
                    if (!d.visited[d.DFS_i]) {
                        state = 8; // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-���
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // ���������, ���� �� i-��� ������� �������� (���������)
                    }
                    break;
                }
                case 7: { // ���������, ���� �� i-��� ������� �������� (���������)
                    state = 11; // ��������� i
                    break;
                }
                case 8: { // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-���
                    if (d.aMatrix[d.DFS_stack[d.DFS_head]][d.DFS_i] == 3 && d.DFS_stack[d.DFS_head] != d.DFS_i) {
                        state = 10; // �������� ��������� ������� � ��������� �� � ����
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� (���������)
                    }
                    break;
                }
                case 9: { // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� (���������)
                    stack.pushBoolean(true); 
                    state = 7; // ���������, ���� �� i-��� ������� �������� (���������)
                    break;
                }
                case 10: { // �������� ��������� ������� � ��������� �� � ����
                    stack.pushBoolean(true); 
                    state = 9; // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� (���������)
                    break;
                }
                case 11: { // ��������� i
                    stack.pushBoolean(true); 
                    state = 5; // ���� ���������� �������
                    break;
                }
                case 12: { // ������� ������������� ��������
                    if (d.DFS_flag) {
                        state = 14; // ����� �����������
                    } else {
                        stack.pushBoolean(false); 
                        state = 13; // ������� ������������� �������� (���������)
                    }
                    break;
                }
                case 13: { // ������� ������������� �������� (���������)
                    stack.pushBoolean(true); 
                    state = 3; // ����� ������
                    break;
                }
                case 14: { // ����� �����������
                    state = 15; // �������� �� ������� ���������
                    break;
                }
                case 15: { // �������� �� ������� ���������
                    if (d.DFS_head == 1) {
                        state = 17; // ������� ��������� � �����
                    } else {
                        state = 18; // ������� �� ��������� � �����
                    }
                    break;
                }
                case 16: { // �������� �� ������� ��������� (���������)
                    stack.pushBoolean(true); 
                    state = 13; // ������� ������������� �������� (���������)
                    break;
                }
                case 17: { // ������� ��������� � �����
                    stack.pushBoolean(true); 
                    state = 16; // �������� �� ������� ��������� (���������)
                    break;
                }
                case 18: { // ������� �� ��������� � �����
                    stack.pushBoolean(false); 
                    state = 16; // �������� �� ������� ��������� (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ����� � �������
                    startSection();
                    storeField(d, "activeAuto");
                    				d.activeAuto = 1;
                    storeField(d, "DFS_activeVertex");
                    				d.DFS_activeVertex = -1;
                    storeField(d, "DFS_stack");
                    				d.DFS_stack = new int[d.numberOfVertices + 1];
                    storeArray(d.DFS_stack, 0);
                    				d.DFS_stack[0] = -1;
                    storeField(d, "DFS_head");
                    				d.DFS_head = 0;
                    break;
                }
                case 2: { // �������� �������, � ������� �������� ����� � ��������� �� � ����
                    startSection();
                    storeArray(d.visited, d.root);
                    				d.visited[d.root] = true;
                    storeField(d, "DFS_activeVertex");
                    				d.DFS_activeVertex = d.root;
                    storeField(d, "numberOfVisited");
                    				d.numberOfVisited = 1;
                    storeArray(d.way, d.numberOfVisited - 1);
                    				d.way[d.numberOfVisited - 1] = d.root;
                    storeArray(d.dfnumber, d.root);
                    				d.dfnumber[d.root] = d.numberOfVisited;
                    storeField(d, "DFS_head");
                    				d.DFS_head = 1;
                    storeArray(d.DFS_stack, d.DFS_head);
                    				d.DFS_stack[d.DFS_head] = d.root;
                    break;
                }
                case 3: { // ����� ������
                    break;
                }
                case 4: { // ���������� i � ����
                    startSection();
                    storeField(d, "DFS_i");
                    					d.DFS_i = 0;
                    storeField(d, "DFS_flag");
                    					d.DFS_flag = true;
                    break;
                }
                case 5: { // ���� ���������� �������
                    break;
                }
                case 6: { // ���������, ���� �� i-��� ������� ��������
                    break;
                }
                case 7: { // ���������, ���� �� i-��� ������� �������� (���������)
                    break;
                }
                case 8: { // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-���
                    break;
                }
                case 9: { // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� (���������)
                    break;
                }
                case 10: { // �������� ��������� ������� � ��������� �� � ����
                    startSection();
                    storeArray(d.visited, d.DFS_i);
                    										d.visited[d.DFS_i] = true;
                    storeField(d, "DFS_activeVertex");
                    										d.DFS_activeVertex = d.DFS_i;
                    storeField(d, "numberOfVisited");
                    										d.numberOfVisited = d.numberOfVisited + 1;
                    storeArray(d.dfnumber, d.DFS_i);
                    										d.dfnumber[d.DFS_i] = d.numberOfVisited;
                    storeArray(d.way, d.numberOfVisited - 1);
                    										d.way[d.numberOfVisited - 1] = d.DFS_i;
                    storeArray(d.aMatrix[d.DFS_stack[d.DFS_head]], d.DFS_i);
                    										d.aMatrix[d.DFS_stack[d.DFS_head]][d.DFS_i] = 4;
                    storeField(d, "DFS_head");
                    										d.DFS_head = d.DFS_head + 1;
                    storeArray(d.DFS_stack, d.DFS_head);
                    										d.DFS_stack[d.DFS_head] = d.DFS_i;
                    storeField(d, "DFS_flag");
                    										d.DFS_flag = false;
                    break;
                }
                case 11: { // ��������� i
                    startSection();
                    storeField(d, "DFS_i");
                    						d.DFS_i = d.DFS_i + 1;
                    break;
                }
                case 12: { // ������� ������������� ��������
                    break;
                }
                case 13: { // ������� ������������� �������� (���������)
                    break;
                }
                case 14: { // ����� �����������
                    startSection();
                    storeField(d, "DFS_activeVertex");
                    							d.DFS_activeVertex = -1;
                    break;
                }
                case 15: { // �������� �� ������� ���������
                    break;
                }
                case 16: { // �������� �� ������� ��������� (���������)
                    break;
                }
                case 17: { // ������� ��������� � �����
                    startSection();
                    storeField(d, "DFS_head");
                    									d.DFS_head = d.DFS_head - 1;
                    storeField(d, "DFS_activeVertex");
                    									d.DFS_activeVertex = -1;
                    break;
                }
                case 18: { // ������� �� ��������� � �����
                    startSection();
                    storeField(d, "DFS_head");
                    									d.DFS_head = d.DFS_head - 1;
                    storeField(d, "DFS_activeVertex");
                    									d.DFS_activeVertex = -1;
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
                case 1: { // ����� � �������
                    restoreSection();
                    break;
                }
                case 2: { // �������� �������, � ������� �������� ����� � ��������� �� � ����
                    restoreSection();
                    break;
                }
                case 3: { // ����� ������
                    break;
                }
                case 4: { // ���������� i � ����
                    restoreSection();
                    break;
                }
                case 5: { // ���� ���������� �������
                    break;
                }
                case 6: { // ���������, ���� �� i-��� ������� ��������
                    break;
                }
                case 7: { // ���������, ���� �� i-��� ������� �������� (���������)
                    break;
                }
                case 8: { // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-���
                    break;
                }
                case 9: { // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� (���������)
                    break;
                }
                case 10: { // �������� ��������� ������� � ��������� �� � ����
                    restoreSection();
                    break;
                }
                case 11: { // ��������� i
                    restoreSection();
                    break;
                }
                case 12: { // ������� ������������� ��������
                    break;
                }
                case 13: { // ������� ������������� �������� (���������)
                    break;
                }
                case 14: { // ����� �����������
                    restoreSection();
                    break;
                }
                case 15: { // �������� �� ������� ���������
                    break;
                }
                case 16: { // �������� �� ������� ��������� (���������)
                    break;
                }
                case 17: { // ������� ��������� � �����
                    restoreSection();
                    break;
                }
                case 18: { // ������� �� ��������� � �����
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ����� � �������
                    state = START_STATE; 
                    break;
                }
                case 2: { // �������� �������, � ������� �������� ����� � ��������� �� � ����
                    state = 1; // ����� � �������
                    break;
                }
                case 3: { // ����� ������
                    if (stack.popBoolean()) {
                        state = 13; // ������� ������������� �������� (���������)
                    } else {
                        state = 2; // �������� �������, � ������� �������� ����� � ��������� �� � ����
                    }
                    break;
                }
                case 4: { // ���������� i � ����
                    state = 3; // ����� ������
                    break;
                }
                case 5: { // ���� ���������� �������
                    if (stack.popBoolean()) {
                        state = 11; // ��������� i
                    } else {
                        state = 4; // ���������� i � ����
                    }
                    break;
                }
                case 6: { // ���������, ���� �� i-��� ������� ��������
                    state = 5; // ���� ���������� �������
                    break;
                }
                case 7: { // ���������, ���� �� i-��� ������� �������� (���������)
                    if (stack.popBoolean()) {
                        state = 9; // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� (���������)
                    } else {
                        state = 6; // ���������, ���� �� i-��� ������� ��������
                    }
                    break;
                }
                case 8: { // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-���
                    state = 6; // ���������, ���� �� i-��� ������� ��������
                    break;
                }
                case 9: { // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-��� (���������)
                    if (stack.popBoolean()) {
                        state = 10; // �������� ��������� ������� � ��������� �� � ����
                    } else {
                        state = 8; // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-���
                    }
                    break;
                }
                case 10: { // �������� ��������� ������� � ��������� �� � ����
                    state = 8; // �������, ���� �� �����, ����������� ��������� ��������� ������� � i-���
                    break;
                }
                case 11: { // ��������� i
                    state = 7; // ���������, ���� �� i-��� ������� �������� (���������)
                    break;
                }
                case 12: { // ������� ������������� ��������
                    state = 5; // ���� ���������� �������
                    break;
                }
                case 13: { // ������� ������������� �������� (���������)
                    if (stack.popBoolean()) {
                        state = 16; // �������� �� ������� ��������� (���������)
                    } else {
                        state = 12; // ������� ������������� ��������
                    }
                    break;
                }
                case 14: { // ����� �����������
                    state = 12; // ������� ������������� ��������
                    break;
                }
                case 15: { // �������� �� ������� ���������
                    state = 14; // ����� �����������
                    break;
                }
                case 16: { // �������� �� ������� ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 17; // ������� ��������� � �����
                    } else {
                        state = 18; // ������� �� ��������� � �����
                    }
                    break;
                }
                case 17: { // ������� ��������� � �����
                    state = 15; // �������� �� ������� ���������
                    break;
                }
                case 18: { // ������� �� ��������� � �����
                    state = 15; // �������� �� ������� ���������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 3; // ����� ������
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
                case 1: { // ����� � �������
                    comment = ArticulationPoints.this.getComment("DFS.startDFS"); 
                    break;
                }
                case 2: { // �������� �������, � ������� �������� ����� � ��������� �� � ����
                    comment = ArticulationPoints.this.getComment("DFS.VisitFirst"); 
                    args = new Object[]{new Integer(d.root)}; 
                    break;
                }
                case 10: { // �������� ��������� ������� � ��������� �� � ����
                    comment = ArticulationPoints.this.getComment("DFS.VisitNext"); 
                    args = new Object[]{new Integer(d.DFS_stack[d.DFS_head-1]), new Integer(d.DFS_i), new Integer(d.dfnumber[d.DFS_i])}; 
                    break;
                }
                case 14: { // ����� �����������
                    comment = ArticulationPoints.this.getComment("DFS.noChilds"); 
                    args = new Object[]{new Integer(d.DFS_stack[d.DFS_head])}; 
                    break;
                }
                case 17: { // ������� ��������� � �����
                    comment = ArticulationPoints.this.getComment("DFS.last"); 
                    args = new Object[]{new Integer(d.DFS_stack[d.DFS_head + 1])}; 
                    break;
                }
                case 18: { // ������� �� ��������� � �����
                    comment = ArticulationPoints.this.getComment("DFS.notLast"); 
                    args = new Object[]{new Integer(d.DFS_stack[d.DFS_head])}; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = ArticulationPoints.this.getComment("DFS.END_STATE"); 
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
                case 1: { // ����� � �������
                    				d.visualizer.updateVertex(-1);
                    				d.visualizer.stopEditMode();
                    break;
                }
                case 2: { // �������� �������, � ������� �������� ����� � ��������� �� � ����
                    				d.visualizer.updateVertex(d.root);
                    break;
                }
                case 10: { // �������� ��������� ������� � ��������� �� � ����
                    										d.visualizer.updateVertex(d.DFS_i);
                    break;
                }
                case 14: { // ����� �����������
                    							d.visualizer.updateVertex(d.DFS_stack[d.DFS_head]);
                    break;
                }
                case 17: { // ������� ��������� � �����
                    									d.visualizer.updateVertex(d.DFS_stack[d.DFS_head]);
                    break;
                }
                case 18: { // ������� �� ��������� � �����
                    									d.visualizer.updateVertex(d.DFS_stack[d.DFS_head]);
                    break;
                }
            }
        }
    }

    /**
      * ������� low ��� ������ �������..
      */
    private final class findLow extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 27;

        /**
          * �����������.
          */
        public findLow() {
            super( 
                "findLow", 
                0, // ����� ���������� ��������� 
                27, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� i", 
                    "������� ������ ������ � �������� �������", 
                    "������������� ����������", 
                    "������������� ���� �������� �������", 
                    "���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������", 
                    "���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ (���������)", 
                    "Low ������� ������, ��� ������� �������� low", 
                    "Low ������� ������, ��� ������� �������� low (���������)", 
                    "����� ������ �������", 
                    "����������� low ������� ������� ����� ��������", 
                    "����� ������ �������", 
                    "��������� j", 
                    "������������� j", 
                    "������������� ��� �������� �����", 
                    "���������� �� �������� �����, ����������� ������� ������� � j-���", 
                    "���������� �� �������� �����, ����������� ������� ������� � j-��� (���������)", 
                    "����� j-��� ������� ��� ������ ������, ��� ������� �������� low", 
                    "����� j-��� ������� ��� ������ ������, ��� ������� �������� low (���������)", 
                    "����� ����� �������� �����", 
                    "����������� low ������� ������� ����� ��������", 
                    "��������� j", 
                    "����� ����� �������� �����", 
                    "��������� j", 
                    "��������� j", 
                    "��������� j", 
                    "����� low ��� ���� ������ �������.", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    1, // ������������� i 
                    -1, // ������� ������ ������ � �������� ������� 
                    0, // ������������� ���������� 
                    -1, // ������������� ���� �������� ������� 
                    -1, // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ 
                    -1, // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ (���������) 
                    -1, // Low ������� ������, ��� ������� �������� low 
                    -1, // Low ������� ������, ��� ������� �������� low (���������) 
                    0, // ����� ������ ������� 
                    0, // ����������� low ������� ������� ����� �������� 
                    0, // ����� ������ ������� 
                    -1, // ��������� j 
                    -1, // ������������� j 
                    -1, // ������������� ��� �������� ����� 
                    -1, // ���������� �� �������� �����, ����������� ������� ������� � j-��� 
                    -1, // ���������� �� �������� �����, ����������� ������� ������� � j-��� (���������) 
                    -1, // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low 
                    -1, // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low (���������) 
                    0, // ����� ����� �������� ����� 
                    0, // ����������� low ������� ������� ����� �������� 
                    -1, // ��������� j 
                    0, // ����� ����� �������� ����� 
                    -1, // ��������� j 
                    -1, // ��������� j 
                    -1, // ��������� j 
                    0, // ����� low ��� ���� ������ �������. 
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
                    state = 1; // ������������� i
                    break;
                }
                case 1: { // ������������� i
                    stack.pushBoolean(false); 
                    state = 2; // ������� ������ ������ � �������� �������
                    break;
                }
                case 2: { // ������� ������ ������ � �������� �������
                    if (d.findLow_i >= 0) {
                        state = 3; // ������������� ����������
                    } else {
                        state = 26; // ����� low ��� ���� ������ �������.
                    }
                    break;
                }
                case 3: { // ������������� ����������
                    stack.pushBoolean(false); 
                    state = 4; // ������������� ���� �������� �������
                    break;
                }
                case 4: { // ������������� ���� �������� �������
                    if (d.findLow_j < d.numberOfVertices) {
                        state = 5; // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������
                    } else {
                        state = 13; // ������������� j
                    }
                    break;
                }
                case 5: { // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������
                    if (d.aMatrix[d.findLow_currentVertex][d.findLow_j] == 4) {
                        state = 7; // Low ������� ������, ��� ������� �������� low
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ (���������)
                    }
                    break;
                }
                case 6: { // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ (���������)
                    state = 12; // ��������� j
                    break;
                }
                case 7: { // Low ������� ������, ��� ������� �������� low
                    if (d.low[d.findLow_j] < d.low[d.findLow_currentVertex]) {
                        state = 9; // ����� ������ �������
                    } else {
                        state = 11; // ����� ������ �������
                    }
                    break;
                }
                case 8: { // Low ������� ������, ��� ������� �������� low (���������)
                    stack.pushBoolean(true); 
                    state = 6; // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ (���������)
                    break;
                }
                case 9: { // ����� ������ �������
                    state = 10; // ����������� low ������� ������� ����� ��������
                    break;
                }
                case 10: { // ����������� low ������� ������� ����� ��������
                    stack.pushBoolean(true); 
                    state = 8; // Low ������� ������, ��� ������� �������� low (���������)
                    break;
                }
                case 11: { // ����� ������ �������
                    stack.pushBoolean(false); 
                    state = 8; // Low ������� ������, ��� ������� �������� low (���������)
                    break;
                }
                case 12: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 4; // ������������� ���� �������� �������
                    break;
                }
                case 13: { // ������������� j
                    stack.pushBoolean(false); 
                    state = 14; // ������������� ��� �������� �����
                    break;
                }
                case 14: { // ������������� ��� �������� �����
                    if (d.findLow_j < d.numberOfVertices) {
                        state = 15; // ���������� �� �������� �����, ����������� ������� ������� � j-���
                    } else {
                        state = 25; // ��������� j
                    }
                    break;
                }
                case 15: { // ���������� �� �������� �����, ����������� ������� ������� � j-���
                    if (d.aMatrix[d.findLow_currentVertex][d.findLow_j] == 3 && d.aMatrix[d.findLow_j][d.findLow_currentVertex] != 4                 && d.dfnumber[d.findLow_j] < d.dfnumber[d.findLow_currentVertex]) {
                        state = 17; // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low
                    } else {
                        stack.pushBoolean(false); 
                        state = 16; // ���������� �� �������� �����, ����������� ������� ������� � j-��� (���������)
                    }
                    break;
                }
                case 16: { // ���������� �� �������� �����, ����������� ������� ������� � j-��� (���������)
                    state = 24; // ��������� j
                    break;
                }
                case 17: { // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low
                    if (d.dfnumber[d.findLow_j] < d.low[d.findLow_currentVertex]) {
                        state = 19; // ����� ����� �������� �����
                    } else {
                        state = 22; // ����� ����� �������� �����
                    }
                    break;
                }
                case 18: { // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low (���������)
                    stack.pushBoolean(true); 
                    state = 16; // ���������� �� �������� �����, ����������� ������� ������� � j-��� (���������)
                    break;
                }
                case 19: { // ����� ����� �������� �����
                    state = 20; // ����������� low ������� ������� ����� ��������
                    break;
                }
                case 20: { // ����������� low ������� ������� ����� ��������
                    state = 21; // ��������� j
                    break;
                }
                case 21: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 18; // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low (���������)
                    break;
                }
                case 22: { // ����� ����� �������� �����
                    state = 23; // ��������� j
                    break;
                }
                case 23: { // ��������� j
                    stack.pushBoolean(false); 
                    state = 18; // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low (���������)
                    break;
                }
                case 24: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 14; // ������������� ��� �������� �����
                    break;
                }
                case 25: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 2; // ������� ������ ������ � �������� �������
                    break;
                }
                case 26: { // ����� low ��� ���� ������ �������.
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� i
                    startSection();
                    storeField(d, "findLow_i");
                    				d.findLow_i = d.numberOfVisited - 1;
                    storeField(d, "activeAuto");
                    				d.activeAuto = 2;
                    storeField(d, "findLow_activeVertexInLow");
                    				d.findLow_activeVertexInLow = -1;
                    storeField(d, "findLow_activeVertexInDfnum");
                    				d.findLow_activeVertexInDfnum = -1;
                    storeField(d, "findLow_currentVertex");
                    				d.findLow_currentVertex = -1;
                    break;
                }
                case 2: { // ������� ������ ������ � �������� �������
                    break;
                }
                case 3: { // ������������� ����������
                    startSection();
                    storeField(d, "findLow_j");
                    					d.findLow_j = 0;
                    storeField(d, "findLow_currentVertex");
                    					d.findLow_currentVertex = d.way[d.findLow_i];
                    storeArray(d.low, d.findLow_currentVertex);
                    					d.low[d.findLow_currentVertex] = d.dfnumber[d.findLow_currentVertex];
                    storeField(d, "findLow_activeVertexInLow");
                    					d.findLow_activeVertexInLow = -1;
                    storeField(d, "findLow_activeVertexInDfnum");
                    					d.findLow_activeVertexInDfnum = -1;
                    break;
                }
                case 4: { // ������������� ���� �������� �������
                    break;
                }
                case 5: { // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������
                    break;
                }
                case 6: { // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ (���������)
                    break;
                }
                case 7: { // Low ������� ������, ��� ������� �������� low
                    break;
                }
                case 8: { // Low ������� ������, ��� ������� �������� low (���������)
                    break;
                }
                case 9: { // ����� ������ �������
                    startSection();
                    storeField(d, "findLow_activeVertexInLow");
                    										d.findLow_activeVertexInLow = d.findLow_j;
                    storeField(d, "findLow_activeVertexInDfnum");
                    										d.findLow_activeVertexInDfnum = -1;
                    break;
                }
                case 10: { // ����������� low ������� ������� ����� ��������
                    startSection();
                    storeArray(d.low, d.findLow_currentVertex);
                    										d.low[d.findLow_currentVertex] = d.low[d.findLow_j];
                    break;
                }
                case 11: { // ����� ������ �������
                    startSection();
                    storeField(d, "findLow_activeVertexInLow");
                    										d.findLow_activeVertexInLow = d.findLow_j;
                    storeField(d, "findLow_activeVertexInDfnum");
                    										d.findLow_activeVertexInDfnum = -1;
                    break;
                }
                case 12: { // ��������� j
                    startSection();
                    storeField(d, "findLow_j");
                    						d.findLow_j = d.findLow_j + 1;
                    break;
                }
                case 13: { // ������������� j
                    startSection();
                    storeField(d, "findLow_j");
                    					d.findLow_j = 0;
                    break;
                }
                case 14: { // ������������� ��� �������� �����
                    break;
                }
                case 15: { // ���������� �� �������� �����, ����������� ������� ������� � j-���
                    break;
                }
                case 16: { // ���������� �� �������� �����, ����������� ������� ������� � j-��� (���������)
                    break;
                }
                case 17: { // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low
                    break;
                }
                case 18: { // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low (���������)
                    break;
                }
                case 19: { // ����� ����� �������� �����
                    startSection();
                    storeField(d, "findLow_activeVertexInLow");
                    										d.findLow_activeVertexInLow = -1;
                    storeField(d, "findLow_activeVertexInDfnum");
                    										d.findLow_activeVertexInDfnum = d.findLow_j;
                    storeArray(d.aMatrix[d.findLow_currentVertex], d.findLow_j);
                    										d.aMatrix[d.findLow_currentVertex][d.findLow_j] = 10;
                    break;
                }
                case 20: { // ����������� low ������� ������� ����� ��������
                    startSection();
                    storeArray(d.low, d.findLow_currentVertex);
                    										d.low[d.findLow_currentVertex] = d.dfnumber[d.findLow_j];
                    break;
                }
                case 21: { // ��������� j
                    startSection();
                    storeArray(d.aMatrix[d.findLow_currentVertex], d.findLow_j);
                    										d.aMatrix[d.findLow_currentVertex][d.findLow_j] = 3;
                    break;
                }
                case 22: { // ����� ����� �������� �����
                    startSection();
                    storeField(d, "findLow_activeVertexInLow");
                    										d.findLow_activeVertexInLow = -1;
                    storeField(d, "findLow_activeVertexInDfnum");
                    										d.findLow_activeVertexInDfnum = d.findLow_j;
                    storeArray(d.aMatrix[d.findLow_currentVertex], d.findLow_j);
                    										d.aMatrix[d.findLow_currentVertex][d.findLow_j] = 10;
                    break;
                }
                case 23: { // ��������� j
                    startSection();
                    storeArray(d.aMatrix[d.findLow_currentVertex], d.findLow_j);
                    										d.aMatrix[d.findLow_currentVertex][d.findLow_j] = 3;
                    break;
                }
                case 24: { // ��������� j
                    startSection();
                    storeField(d, "findLow_j");
                    						d.findLow_j = d.findLow_j + 1;
                    break;
                }
                case 25: { // ��������� j
                    startSection();
                    storeField(d, "findLow_i");
                    					d.findLow_i = d.findLow_i - 1;
                    break;
                }
                case 26: { // ����� low ��� ���� ������ �������.
                    startSection();
                    storeField(d, "findLow_activeVertexInLow");
                    				d.findLow_activeVertexInLow = -1;
                    storeField(d, "findLow_activeVertexInDfnum");
                    				d.findLow_activeVertexInDfnum = -1;
                    storeField(d, "findLow_currentVertex");
                    				d.findLow_currentVertex = -1;
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
                case 1: { // ������������� i
                    restoreSection();
                    break;
                }
                case 2: { // ������� ������ ������ � �������� �������
                    break;
                }
                case 3: { // ������������� ����������
                    restoreSection();
                    break;
                }
                case 4: { // ������������� ���� �������� �������
                    break;
                }
                case 5: { // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������
                    break;
                }
                case 6: { // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ (���������)
                    break;
                }
                case 7: { // Low ������� ������, ��� ������� �������� low
                    break;
                }
                case 8: { // Low ������� ������, ��� ������� �������� low (���������)
                    break;
                }
                case 9: { // ����� ������ �������
                    restoreSection();
                    break;
                }
                case 10: { // ����������� low ������� ������� ����� ��������
                    restoreSection();
                    break;
                }
                case 11: { // ����� ������ �������
                    restoreSection();
                    break;
                }
                case 12: { // ��������� j
                    restoreSection();
                    break;
                }
                case 13: { // ������������� j
                    restoreSection();
                    break;
                }
                case 14: { // ������������� ��� �������� �����
                    break;
                }
                case 15: { // ���������� �� �������� �����, ����������� ������� ������� � j-���
                    break;
                }
                case 16: { // ���������� �� �������� �����, ����������� ������� ������� � j-��� (���������)
                    break;
                }
                case 17: { // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low
                    break;
                }
                case 18: { // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low (���������)
                    break;
                }
                case 19: { // ����� ����� �������� �����
                    restoreSection();
                    break;
                }
                case 20: { // ����������� low ������� ������� ����� ��������
                    restoreSection();
                    break;
                }
                case 21: { // ��������� j
                    restoreSection();
                    break;
                }
                case 22: { // ����� ����� �������� �����
                    restoreSection();
                    break;
                }
                case 23: { // ��������� j
                    restoreSection();
                    break;
                }
                case 24: { // ��������� j
                    restoreSection();
                    break;
                }
                case 25: { // ��������� j
                    restoreSection();
                    break;
                }
                case 26: { // ����� low ��� ���� ������ �������.
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� i
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������� ������ ������ � �������� �������
                    if (stack.popBoolean()) {
                        state = 25; // ��������� j
                    } else {
                        state = 1; // ������������� i
                    }
                    break;
                }
                case 3: { // ������������� ����������
                    state = 2; // ������� ������ ������ � �������� �������
                    break;
                }
                case 4: { // ������������� ���� �������� �������
                    if (stack.popBoolean()) {
                        state = 12; // ��������� j
                    } else {
                        state = 3; // ������������� ����������
                    }
                    break;
                }
                case 5: { // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������
                    state = 4; // ������������� ���� �������� �������
                    break;
                }
                case 6: { // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ (���������)
                    if (stack.popBoolean()) {
                        state = 8; // Low ������� ������, ��� ������� �������� low (���������)
                    } else {
                        state = 5; // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������
                    }
                    break;
                }
                case 7: { // Low ������� ������, ��� ������� �������� low
                    state = 5; // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������
                    break;
                }
                case 8: { // Low ������� ������, ��� ������� �������� low (���������)
                    if (stack.popBoolean()) {
                        state = 10; // ����������� low ������� ������� ����� ��������
                    } else {
                        state = 11; // ����� ������ �������
                    }
                    break;
                }
                case 9: { // ����� ������ �������
                    state = 7; // Low ������� ������, ��� ������� �������� low
                    break;
                }
                case 10: { // ����������� low ������� ������� ����� ��������
                    state = 9; // ����� ������ �������
                    break;
                }
                case 11: { // ����� ������ �������
                    state = 7; // Low ������� ������, ��� ������� �������� low
                    break;
                }
                case 12: { // ��������� j
                    state = 6; // ���������� �� �����, ����������� ������� ������� � �� �������� � ������ ������ (���������)
                    break;
                }
                case 13: { // ������������� j
                    state = 4; // ������������� ���� �������� �������
                    break;
                }
                case 14: { // ������������� ��� �������� �����
                    if (stack.popBoolean()) {
                        state = 24; // ��������� j
                    } else {
                        state = 13; // ������������� j
                    }
                    break;
                }
                case 15: { // ���������� �� �������� �����, ����������� ������� ������� � j-���
                    state = 14; // ������������� ��� �������� �����
                    break;
                }
                case 16: { // ���������� �� �������� �����, ����������� ������� ������� � j-��� (���������)
                    if (stack.popBoolean()) {
                        state = 18; // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low (���������)
                    } else {
                        state = 15; // ���������� �� �������� �����, ����������� ������� ������� � j-���
                    }
                    break;
                }
                case 17: { // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low
                    state = 15; // ���������� �� �������� �����, ����������� ������� ������� � j-���
                    break;
                }
                case 18: { // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low (���������)
                    if (stack.popBoolean()) {
                        state = 21; // ��������� j
                    } else {
                        state = 23; // ��������� j
                    }
                    break;
                }
                case 19: { // ����� ����� �������� �����
                    state = 17; // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low
                    break;
                }
                case 20: { // ����������� low ������� ������� ����� ��������
                    state = 19; // ����� ����� �������� �����
                    break;
                }
                case 21: { // ��������� j
                    state = 20; // ����������� low ������� ������� ����� ��������
                    break;
                }
                case 22: { // ����� ����� �������� �����
                    state = 17; // ����� j-��� ������� ��� ������ ������, ��� ������� �������� low
                    break;
                }
                case 23: { // ��������� j
                    state = 22; // ����� ����� �������� �����
                    break;
                }
                case 24: { // ��������� j
                    state = 16; // ���������� �� �������� �����, ����������� ������� ������� � j-��� (���������)
                    break;
                }
                case 25: { // ��������� j
                    state = 14; // ������������� ��� �������� �����
                    break;
                }
                case 26: { // ����� low ��� ���� ������ �������.
                    state = 2; // ������� ������ ������ � �������� �������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 26; // ����� low ��� ���� ������ �������.
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
                case 1: { // ������������� i
                    comment = ArticulationPoints.this.getComment("findLow.LowLoopInitI"); 
                    break;
                }
                case 3: { // ������������� ����������
                    comment = ArticulationPoints.this.getComment("findLow.InitVariables"); 
                    args = new Object[]{new Integer(d.findLow_currentVertex)}; 
                    break;
                }
                case 9: { // ����� ������ �������
                    comment = ArticulationPoints.this.getComment("findLow.newChild"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex), new Integer(d.low[d.findLow_j]), new Integer(d.low[d.findLow_currentVertex])}; 
                    break;
                }
                case 10: { // ����������� low ������� ������� ����� ��������
                    comment = ArticulationPoints.this.getComment("findLow.newValue1"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex)}; 
                    break;
                }
                case 11: { // ����� ������ �������
                    comment = ArticulationPoints.this.getComment("findLow.newChild2"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex), new Integer(d.low[d.findLow_j]), new Integer(d.low[d.findLow_currentVertex])}; 
                    break;
                }
                case 19: { // ����� ����� �������� �����
                    comment = ArticulationPoints.this.getComment("findLow.newBackEdge1"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex), new Integer(d.dfnumber[d.findLow_j]), new Integer(d.low[d.findLow_currentVertex])}; 
                    break;
                }
                case 20: { // ����������� low ������� ������� ����� ��������
                    comment = ArticulationPoints.this.getComment("findLow.newValue2"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex)}; 
                    break;
                }
                case 22: { // ����� ����� �������� �����
                    comment = ArticulationPoints.this.getComment("findLow.newBackEdge2"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex), new Integer(d.dfnumber[d.findLow_j]), new Integer(d.low[d.findLow_currentVertex])}; 
                    break;
                }
                case 26: { // ����� low ��� ���� ������ �������.
                    comment = ArticulationPoints.this.getComment("findLow.endFindLow"); 
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
                case 1: { // ������������� i
                    				d.visualizer.updateVertex(-1);
                    break;
                }
                case 3: { // ������������� ����������
                    					d.visualizer.updateVertex(-1);
                    break;
                }
                case 9: { // ����� ������ �������
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 10: { // ����������� low ������� ������� ����� ��������
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 11: { // ����� ������ �������
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 19: { // ����� ����� �������� �����
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 20: { // ����������� low ������� ������� ����� ��������
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 22: { // ����� ����� �������� �����
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 26: { // ����� low ��� ���� ������ �������.
                    				d.visualizer.updateVertex(-1);
                    break;
                }
            }
        }
    }

    /**
      * ���� ����� ����������..
      */
    private final class findArticulationPoints extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 39;

        /**
          * �����������.
          */
        public findArticulationPoints() {
            super( 
                "findArticulationPoints", 
                0, // ����� ���������� ��������� 
                39, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� i", 
                    "������� ����� �����", 
                    "�������� �� i-��� ������� ��������", 
                    "�������� �� i-��� ������� �������� (���������)", 
                    "��������� �������� �����", 
                    "��������� i", 
                    "�������� �� ������ ������ ������ ������ ����������", 
                    "�������� �� ������ ������ ������ ������ ���������� (���������)", 
                    "������ �������� ������ ����������", 
                    "������ �� �������� ������ ����������", 
                    "������������� i", 
                    "����� ���������� ������", 
                    "���� �� i-��� ������� ��������", 
                    "���� �� i-��� ������� �������� (���������)", 
                    "������������� j", 
                    "����� ����� i-��� �������", 
                    "�������� �� j-��� ������� �������� i-��� �������", 
                    "�������� �� j-��� ������� �������� i-��� ������� (���������)", 
                    "�������", 
                    "������� (���������)", 
                    "�������� ����", 
                    "������� �����������", 
                    "��������� j", 
                    "��� �� ������ ������ �������", 
                    "��� �� ������ ������ ������� (���������)", 
                    "i-��� ������� ����� ����������", 
                    "i-��� ������� �� �������� ������ ����������", 
                    "��������� i", 
                    "������������� i", 
                    "����� �����.", 
                    "������������� j", 
                    "����� �����.", 
                    "���� �� �����", 
                    "���� �� ����� (���������)", 
                    "������ ������ �����", 
                    "��������� j", 
                    "��������� i", 
                    "����� ������� ��������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    1, // ������������� i 
                    -1, // ������� ����� ����� 
                    -1, // �������� �� i-��� ������� �������� 
                    -1, // �������� �� i-��� ������� �������� (���������) 
                    -1, // ��������� �������� ����� 
                    -1, // ��������� i 
                    -1, // �������� �� ������ ������ ������ ������ ���������� 
                    -1, // �������� �� ������ ������ ������ ������ ���������� (���������) 
                    0, // ������ �������� ������ ���������� 
                    0, // ������ �� �������� ������ ���������� 
                    -1, // ������������� i 
                    -1, // ����� ���������� ������ 
                    -1, // ���� �� i-��� ������� �������� 
                    -1, // ���� �� i-��� ������� �������� (���������) 
                    0, // ������������� j 
                    -1, // ����� ����� i-��� ������� 
                    -1, // �������� �� j-��� ������� �������� i-��� ������� 
                    -1, // �������� �� j-��� ������� �������� i-��� ������� (���������) 
                    -1, // ������� 
                    -1, // ������� (���������) 
                    0, // �������� ���� 
                    0, // ������� ����������� 
                    -1, // ��������� j 
                    -1, // ��� �� ������ ������ ������� 
                    -1, // ��� �� ������ ������ ������� (���������) 
                    0, // i-��� ������� ����� ���������� 
                    0, // i-��� ������� �� �������� ������ ���������� 
                    -1, // ��������� i 
                    -1, // ������������� i 
                    -1, // ����� �����. 
                    -1, // ������������� j 
                    -1, // ����� �����. 
                    -1, // ���� �� ����� 
                    -1, // ���� �� ����� (���������) 
                    -1, // ������ ������ ����� 
                    -1, // ��������� j 
                    -1, // ��������� i 
                    0, // ����� ������� �������� 
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
                    state = 1; // ������������� i
                    break;
                }
                case 1: { // ������������� i
                    stack.pushBoolean(false); 
                    state = 2; // ������� ����� �����
                    break;
                }
                case 2: { // ������� ����� �����
                    if (d.findArticulationPoints_i < d.numberOfVertices) {
                        state = 3; // �������� �� i-��� ������� ��������
                    } else {
                        state = 7; // �������� �� ������ ������ ������ ������ ����������
                    }
                    break;
                }
                case 3: { // �������� �� i-��� ������� ��������
                    if (d.aMatrix[d.root][d.findArticulationPoints_i] == 4) {
                        state = 5; // ��������� �������� �����
                    } else {
                        stack.pushBoolean(false); 
                        state = 4; // �������� �� i-��� ������� �������� (���������)
                    }
                    break;
                }
                case 4: { // �������� �� i-��� ������� �������� (���������)
                    state = 6; // ��������� i
                    break;
                }
                case 5: { // ��������� �������� �����
                    stack.pushBoolean(true); 
                    state = 4; // �������� �� i-��� ������� �������� (���������)
                    break;
                }
                case 6: { // ��������� i
                    stack.pushBoolean(true); 
                    state = 2; // ������� ����� �����
                    break;
                }
                case 7: { // �������� �� ������ ������ ������ ������ ����������
                    if (d.findArticulationPoints_childsCounter > 1) {
                        state = 9; // ������ �������� ������ ����������
                    } else {
                        state = 10; // ������ �� �������� ������ ����������
                    }
                    break;
                }
                case 8: { // �������� �� ������ ������ ������ ������ ���������� (���������)
                    state = 11; // ������������� i
                    break;
                }
                case 9: { // ������ �������� ������ ����������
                    stack.pushBoolean(true); 
                    state = 8; // �������� �� ������ ������ ������ ������ ���������� (���������)
                    break;
                }
                case 10: { // ������ �� �������� ������ ����������
                    stack.pushBoolean(false); 
                    state = 8; // �������� �� ������ ������ ������ ������ ���������� (���������)
                    break;
                }
                case 11: { // ������������� i
                    stack.pushBoolean(false); 
                    state = 12; // ����� ���������� ������
                    break;
                }
                case 12: { // ����� ���������� ������
                    if (d.findArticulationPoints_i < d.numberOfVertices) {
                        state = 13; // ���� �� i-��� ������� ��������
                    } else {
                        state = 29; // ������������� i
                    }
                    break;
                }
                case 13: { // ���� �� i-��� ������� ��������
                    if (d.visited[d.findArticulationPoints_i] && d.root != d.findArticulationPoints_i) {
                        state = 15; // ������������� j
                    } else {
                        stack.pushBoolean(false); 
                        state = 14; // ���� �� i-��� ������� �������� (���������)
                    }
                    break;
                }
                case 14: { // ���� �� i-��� ������� �������� (���������)
                    state = 28; // ��������� i
                    break;
                }
                case 15: { // ������������� j
                    stack.pushBoolean(false); 
                    state = 16; // ����� ����� i-��� �������
                    break;
                }
                case 16: { // ����� ����� i-��� �������
                    if (d.findArticulationPoints_j < d.numberOfVertices && !d.findArticulationPoints_flag) {
                        state = 17; // �������� �� j-��� ������� �������� i-��� �������
                    } else {
                        state = 24; // ��� �� ������ ������ �������
                    }
                    break;
                }
                case 17: { // �������� �� j-��� ������� �������� i-��� �������
                    if (d.aMatrix[d.findArticulationPoints_i][d.findArticulationPoints_j] == 4) {
                        state = 19; // �������
                    } else {
                        stack.pushBoolean(false); 
                        state = 18; // �������� �� j-��� ������� �������� i-��� ������� (���������)
                    }
                    break;
                }
                case 18: { // �������� �� j-��� ������� �������� i-��� ������� (���������)
                    state = 23; // ��������� j
                    break;
                }
                case 19: { // �������
                    if (d.low[d.findArticulationPoints_j] >= d.dfnumber[d.findArticulationPoints_i]) {
                        state = 21; // �������� ����
                    } else {
                        state = 22; // ������� �����������
                    }
                    break;
                }
                case 20: { // ������� (���������)
                    stack.pushBoolean(true); 
                    state = 18; // �������� �� j-��� ������� �������� i-��� ������� (���������)
                    break;
                }
                case 21: { // �������� ����
                    stack.pushBoolean(true); 
                    state = 20; // ������� (���������)
                    break;
                }
                case 22: { // ������� �����������
                    stack.pushBoolean(false); 
                    state = 20; // ������� (���������)
                    break;
                }
                case 23: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 16; // ����� ����� i-��� �������
                    break;
                }
                case 24: { // ��� �� ������ ������ �������
                    if (d.findArticulationPoints_flag) {
                        state = 26; // i-��� ������� ����� ����������
                    } else {
                        state = 27; // i-��� ������� �� �������� ������ ����������
                    }
                    break;
                }
                case 25: { // ��� �� ������ ������ ������� (���������)
                    stack.pushBoolean(true); 
                    state = 14; // ���� �� i-��� ������� �������� (���������)
                    break;
                }
                case 26: { // i-��� ������� ����� ����������
                    stack.pushBoolean(true); 
                    state = 25; // ��� �� ������ ������ ������� (���������)
                    break;
                }
                case 27: { // i-��� ������� �� �������� ������ ����������
                    stack.pushBoolean(false); 
                    state = 25; // ��� �� ������ ������ ������� (���������)
                    break;
                }
                case 28: { // ��������� i
                    stack.pushBoolean(true); 
                    state = 12; // ����� ���������� ������
                    break;
                }
                case 29: { // ������������� i
                    stack.pushBoolean(false); 
                    state = 30; // ����� �����.
                    break;
                }
                case 30: { // ����� �����.
                    if (d.findArticulationPoints_i < d.numberOfVertices) {
                        state = 31; // ������������� j
                    } else {
                        state = 38; // ����� ������� ��������
                    }
                    break;
                }
                case 31: { // ������������� j
                    stack.pushBoolean(false); 
                    state = 32; // ����� �����.
                    break;
                }
                case 32: { // ����� �����.
                    if (d.findArticulationPoints_j < d.numberOfVertices) {
                        state = 33; // ���� �� �����
                    } else {
                        state = 37; // ��������� i
                    }
                    break;
                }
                case 33: { // ���� �� �����
                    if (d.aMatrix[d.findArticulationPoints_i][d.findArticulationPoints_j] > 0) {
                        state = 35; // ������ ������ �����
                    } else {
                        stack.pushBoolean(false); 
                        state = 34; // ���� �� ����� (���������)
                    }
                    break;
                }
                case 34: { // ���� �� ����� (���������)
                    state = 36; // ��������� j
                    break;
                }
                case 35: { // ������ ������ �����
                    stack.pushBoolean(true); 
                    state = 34; // ���� �� ����� (���������)
                    break;
                }
                case 36: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 32; // ����� �����.
                    break;
                }
                case 37: { // ��������� i
                    stack.pushBoolean(true); 
                    state = 30; // ����� �����.
                    break;
                }
                case 38: { // ����� ������� ��������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    				d.findArticulationPoints_i = 0;
                    storeField(d, "activeAuto");
                    				d.activeAuto = 3;
                    storeField(d, "findArticulationPoints_childsCounter");
                    				d.findArticulationPoints_childsCounter = 0;
                    storeField(d, "findArticulationPoints_currentVertex");
                    				d.findArticulationPoints_currentVertex = -1;
                    storeField(d, "findArticulationPoints_activeVertexInLow");
                    				d.findArticulationPoints_activeVertexInLow = -1;
                    break;
                }
                case 2: { // ������� ����� �����
                    break;
                }
                case 3: { // �������� �� i-��� ������� ��������
                    break;
                }
                case 4: { // �������� �� i-��� ������� �������� (���������)
                    break;
                }
                case 5: { // ��������� �������� �����
                    startSection();
                    storeField(d, "findArticulationPoints_childsCounter");
                    							d.findArticulationPoints_childsCounter = d.findArticulationPoints_childsCounter + 1;
                    break;
                }
                case 6: { // ��������� i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    					d.findArticulationPoints_i = d.findArticulationPoints_i + 1;
                    break;
                }
                case 7: { // �������� �� ������ ������ ������ ������ ����������
                    break;
                }
                case 8: { // �������� �� ������ ������ ������ ������ ���������� (���������)
                    break;
                }
                case 9: { // ������ �������� ������ ����������
                    startSection();
                    storeArray(d.articulationPoints, d.root);
                    						d.articulationPoints[d.root] = 1;
                    storeArray(d.visited, d.root);
                    						d.visited[d.root] = false;
                    break;
                }
                case 10: { // ������ �� �������� ������ ����������
                    startSection();
                    storeArray(d.articulationPoints, d.root);
                    						d.articulationPoints[d.root] = -1;
                    storeArray(d.visited, d.root);
                    						d.visited[d.root] = false;
                    break;
                }
                case 11: { // ������������� i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    				d.findArticulationPoints_i = 0;
                    break;
                }
                case 12: { // ����� ���������� ������
                    break;
                }
                case 13: { // ���� �� i-��� ������� ��������
                    break;
                }
                case 14: { // ���� �� i-��� ������� �������� (���������)
                    break;
                }
                case 15: { // ������������� j
                    startSection();
                    storeField(d, "findArticulationPoints_j");
                    							d.findArticulationPoints_j = 0;
                    storeField(d, "findArticulationPoints_flag");
                    							d.findArticulationPoints_flag = false;
                    storeField(d, "findArticulationPoints_currentVertex");
                    							d.findArticulationPoints_currentVertex = d.findArticulationPoints_i;
                    break;
                }
                case 16: { // ����� ����� i-��� �������
                    break;
                }
                case 17: { // �������� �� j-��� ������� �������� i-��� �������
                    break;
                }
                case 18: { // �������� �� j-��� ������� �������� i-��� ������� (���������)
                    break;
                }
                case 19: { // �������
                    break;
                }
                case 20: { // ������� (���������)
                    break;
                }
                case 21: { // �������� ����
                    startSection();
                    storeField(d, "findArticulationPoints_flag");
                    												d.findArticulationPoints_flag = true;
                    storeField(d, "findArticulationPoints_activeVertexInLow");
                    												d.findArticulationPoints_activeVertexInLow = d.findArticulationPoints_j;
                    break;
                }
                case 22: { // ������� �����������
                    startSection();
                    storeField(d, "findArticulationPoints_activeVertexInLow");
                    												d.findArticulationPoints_activeVertexInLow = d.findArticulationPoints_j;
                    break;
                }
                case 23: { // ��������� j
                    startSection();
                    storeField(d, "findArticulationPoints_j");
                    								d.findArticulationPoints_j = d.findArticulationPoints_j + 1;
                    break;
                }
                case 24: { // ��� �� ������ ������ �������
                    break;
                }
                case 25: { // ��� �� ������ ������ ������� (���������)
                    break;
                }
                case 26: { // i-��� ������� ����� ����������
                    startSection();
                    storeArray(d.articulationPoints, d.findArticulationPoints_i);
                    									d.articulationPoints[d.findArticulationPoints_i]	= 1;
                    storeArray(d.visited, d.findArticulationPoints_i);
                    									d.visited[d.findArticulationPoints_i] = false;
                    storeField(d, "findArticulationPoints_activeVertexInLow");
                    									d.findArticulationPoints_activeVertexInLow = -1;
                    storeField(d, "findArticulationPoints_currentVertex");
                    									d.findArticulationPoints_currentVertex = -1;
                    break;
                }
                case 27: { // i-��� ������� �� �������� ������ ����������
                    startSection();
                    storeArray(d.articulationPoints, d.findArticulationPoints_i);
                    									d.articulationPoints[d.findArticulationPoints_i]	= -1;
                    storeArray(d.visited, d.findArticulationPoints_i);
                    									d.visited[d.findArticulationPoints_i] = false;
                    storeField(d, "findArticulationPoints_activeVertexInLow");
                    									d.findArticulationPoints_activeVertexInLow = -1;
                    storeField(d, "findArticulationPoints_currentVertex");
                    									d.findArticulationPoints_currentVertex = -1;
                    break;
                }
                case 28: { // ��������� i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    					d.findArticulationPoints_i = d.findArticulationPoints_i + 1;
                    break;
                }
                case 29: { // ������������� i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    				d.findArticulationPoints_i = 0;
                    break;
                }
                case 30: { // ����� �����.
                    break;
                }
                case 31: { // ������������� j
                    startSection();
                    storeField(d, "findArticulationPoints_j");
                    					d.findArticulationPoints_j = 0;
                    break;
                }
                case 32: { // ����� �����.
                    break;
                }
                case 33: { // ���� �� �����
                    break;
                }
                case 34: { // ���� �� ����� (���������)
                    break;
                }
                case 35: { // ������ ������ �����
                    startSection();
                    storeArray(d.aMatrix[d.findArticulationPoints_i], d.findArticulationPoints_j);
                    								d.aMatrix[d.findArticulationPoints_i][d.findArticulationPoints_j] = 3;
                    break;
                }
                case 36: { // ��������� j
                    startSection();
                    storeField(d, "findArticulationPoints_j");
                    						d.findArticulationPoints_j = d.findArticulationPoints_j + 1;
                    break;
                }
                case 37: { // ��������� i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    					d.findArticulationPoints_i = d.findArticulationPoints_i + 1;
                    break;
                }
                case 38: { // ����� ������� ��������
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
                case 1: { // ������������� i
                    restoreSection();
                    break;
                }
                case 2: { // ������� ����� �����
                    break;
                }
                case 3: { // �������� �� i-��� ������� ��������
                    break;
                }
                case 4: { // �������� �� i-��� ������� �������� (���������)
                    break;
                }
                case 5: { // ��������� �������� �����
                    restoreSection();
                    break;
                }
                case 6: { // ��������� i
                    restoreSection();
                    break;
                }
                case 7: { // �������� �� ������ ������ ������ ������ ����������
                    break;
                }
                case 8: { // �������� �� ������ ������ ������ ������ ���������� (���������)
                    break;
                }
                case 9: { // ������ �������� ������ ����������
                    restoreSection();
                    break;
                }
                case 10: { // ������ �� �������� ������ ����������
                    restoreSection();
                    break;
                }
                case 11: { // ������������� i
                    restoreSection();
                    break;
                }
                case 12: { // ����� ���������� ������
                    break;
                }
                case 13: { // ���� �� i-��� ������� ��������
                    break;
                }
                case 14: { // ���� �� i-��� ������� �������� (���������)
                    break;
                }
                case 15: { // ������������� j
                    restoreSection();
                    break;
                }
                case 16: { // ����� ����� i-��� �������
                    break;
                }
                case 17: { // �������� �� j-��� ������� �������� i-��� �������
                    break;
                }
                case 18: { // �������� �� j-��� ������� �������� i-��� ������� (���������)
                    break;
                }
                case 19: { // �������
                    break;
                }
                case 20: { // ������� (���������)
                    break;
                }
                case 21: { // �������� ����
                    restoreSection();
                    break;
                }
                case 22: { // ������� �����������
                    restoreSection();
                    break;
                }
                case 23: { // ��������� j
                    restoreSection();
                    break;
                }
                case 24: { // ��� �� ������ ������ �������
                    break;
                }
                case 25: { // ��� �� ������ ������ ������� (���������)
                    break;
                }
                case 26: { // i-��� ������� ����� ����������
                    restoreSection();
                    break;
                }
                case 27: { // i-��� ������� �� �������� ������ ����������
                    restoreSection();
                    break;
                }
                case 28: { // ��������� i
                    restoreSection();
                    break;
                }
                case 29: { // ������������� i
                    restoreSection();
                    break;
                }
                case 30: { // ����� �����.
                    break;
                }
                case 31: { // ������������� j
                    restoreSection();
                    break;
                }
                case 32: { // ����� �����.
                    break;
                }
                case 33: { // ���� �� �����
                    break;
                }
                case 34: { // ���� �� ����� (���������)
                    break;
                }
                case 35: { // ������ ������ �����
                    restoreSection();
                    break;
                }
                case 36: { // ��������� j
                    restoreSection();
                    break;
                }
                case 37: { // ��������� i
                    restoreSection();
                    break;
                }
                case 38: { // ����� ������� ��������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� i
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������� ����� �����
                    if (stack.popBoolean()) {
                        state = 6; // ��������� i
                    } else {
                        state = 1; // ������������� i
                    }
                    break;
                }
                case 3: { // �������� �� i-��� ������� ��������
                    state = 2; // ������� ����� �����
                    break;
                }
                case 4: { // �������� �� i-��� ������� �������� (���������)
                    if (stack.popBoolean()) {
                        state = 5; // ��������� �������� �����
                    } else {
                        state = 3; // �������� �� i-��� ������� ��������
                    }
                    break;
                }
                case 5: { // ��������� �������� �����
                    state = 3; // �������� �� i-��� ������� ��������
                    break;
                }
                case 6: { // ��������� i
                    state = 4; // �������� �� i-��� ������� �������� (���������)
                    break;
                }
                case 7: { // �������� �� ������ ������ ������ ������ ����������
                    state = 2; // ������� ����� �����
                    break;
                }
                case 8: { // �������� �� ������ ������ ������ ������ ���������� (���������)
                    if (stack.popBoolean()) {
                        state = 9; // ������ �������� ������ ����������
                    } else {
                        state = 10; // ������ �� �������� ������ ����������
                    }
                    break;
                }
                case 9: { // ������ �������� ������ ����������
                    state = 7; // �������� �� ������ ������ ������ ������ ����������
                    break;
                }
                case 10: { // ������ �� �������� ������ ����������
                    state = 7; // �������� �� ������ ������ ������ ������ ����������
                    break;
                }
                case 11: { // ������������� i
                    state = 8; // �������� �� ������ ������ ������ ������ ���������� (���������)
                    break;
                }
                case 12: { // ����� ���������� ������
                    if (stack.popBoolean()) {
                        state = 28; // ��������� i
                    } else {
                        state = 11; // ������������� i
                    }
                    break;
                }
                case 13: { // ���� �� i-��� ������� ��������
                    state = 12; // ����� ���������� ������
                    break;
                }
                case 14: { // ���� �� i-��� ������� �������� (���������)
                    if (stack.popBoolean()) {
                        state = 25; // ��� �� ������ ������ ������� (���������)
                    } else {
                        state = 13; // ���� �� i-��� ������� ��������
                    }
                    break;
                }
                case 15: { // ������������� j
                    state = 13; // ���� �� i-��� ������� ��������
                    break;
                }
                case 16: { // ����� ����� i-��� �������
                    if (stack.popBoolean()) {
                        state = 23; // ��������� j
                    } else {
                        state = 15; // ������������� j
                    }
                    break;
                }
                case 17: { // �������� �� j-��� ������� �������� i-��� �������
                    state = 16; // ����� ����� i-��� �������
                    break;
                }
                case 18: { // �������� �� j-��� ������� �������� i-��� ������� (���������)
                    if (stack.popBoolean()) {
                        state = 20; // ������� (���������)
                    } else {
                        state = 17; // �������� �� j-��� ������� �������� i-��� �������
                    }
                    break;
                }
                case 19: { // �������
                    state = 17; // �������� �� j-��� ������� �������� i-��� �������
                    break;
                }
                case 20: { // ������� (���������)
                    if (stack.popBoolean()) {
                        state = 21; // �������� ����
                    } else {
                        state = 22; // ������� �����������
                    }
                    break;
                }
                case 21: { // �������� ����
                    state = 19; // �������
                    break;
                }
                case 22: { // ������� �����������
                    state = 19; // �������
                    break;
                }
                case 23: { // ��������� j
                    state = 18; // �������� �� j-��� ������� �������� i-��� ������� (���������)
                    break;
                }
                case 24: { // ��� �� ������ ������ �������
                    state = 16; // ����� ����� i-��� �������
                    break;
                }
                case 25: { // ��� �� ������ ������ ������� (���������)
                    if (stack.popBoolean()) {
                        state = 26; // i-��� ������� ����� ����������
                    } else {
                        state = 27; // i-��� ������� �� �������� ������ ����������
                    }
                    break;
                }
                case 26: { // i-��� ������� ����� ����������
                    state = 24; // ��� �� ������ ������ �������
                    break;
                }
                case 27: { // i-��� ������� �� �������� ������ ����������
                    state = 24; // ��� �� ������ ������ �������
                    break;
                }
                case 28: { // ��������� i
                    state = 14; // ���� �� i-��� ������� �������� (���������)
                    break;
                }
                case 29: { // ������������� i
                    state = 12; // ����� ���������� ������
                    break;
                }
                case 30: { // ����� �����.
                    if (stack.popBoolean()) {
                        state = 37; // ��������� i
                    } else {
                        state = 29; // ������������� i
                    }
                    break;
                }
                case 31: { // ������������� j
                    state = 30; // ����� �����.
                    break;
                }
                case 32: { // ����� �����.
                    if (stack.popBoolean()) {
                        state = 36; // ��������� j
                    } else {
                        state = 31; // ������������� j
                    }
                    break;
                }
                case 33: { // ���� �� �����
                    state = 32; // ����� �����.
                    break;
                }
                case 34: { // ���� �� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 35; // ������ ������ �����
                    } else {
                        state = 33; // ���� �� �����
                    }
                    break;
                }
                case 35: { // ������ ������ �����
                    state = 33; // ���� �� �����
                    break;
                }
                case 36: { // ��������� j
                    state = 34; // ���� �� ����� (���������)
                    break;
                }
                case 37: { // ��������� i
                    state = 32; // ����� �����.
                    break;
                }
                case 38: { // ����� ������� ��������
                    state = 30; // ����� �����.
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 38; // ����� ������� ��������
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
                case 1: { // ������������� i
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.FAPInitI1"); 
                    break;
                }
                case 9: { // ������ �������� ������ ����������
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isRootArticulationPoint"); 
                    args = new Object[]{new Integer(d.root)}; 
                    break;
                }
                case 10: { // ������ �� �������� ������ ����������
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isNotRootArticulationPoint"); 
                    args = new Object[]{new Integer(d.root)}; 
                    break;
                }
                case 15: { // ������������� j
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.FAPInitJ"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i)}; 
                    break;
                }
                case 21: { // �������� ����
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isGreater"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i), new Integer(d.findArticulationPoints_j), new Integer(d.dfnumber[d.findArticulationPoints_i]), new Integer(d.low[d.findArticulationPoints_j])}; 
                    break;
                }
                case 22: { // ������� �����������
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isNotGreater"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i), new Integer(d.findArticulationPoints_j), new Integer(d.dfnumber[d.findArticulationPoints_i]), new Integer(d.low[d.findArticulationPoints_j])}; 
                    break;
                }
                case 26: { // i-��� ������� ����� ����������
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isArticulationPoint"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i)}; 
                    break;
                }
                case 27: { // i-��� ������� �� �������� ������ ����������
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isNotArticulationPoint"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i)}; 
                    break;
                }
                case 38: { // ����� ������� ��������
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.AllArticulationPointsFound"); 
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
                case 1: { // ������������� i
                    				d.visualizer.updateVertex(-1);
                    break;
                }
                case 9: { // ������ �������� ������ ����������
                    						d.visualizer.updateVertex(-1);
                    break;
                }
                case 10: { // ������ �� �������� ������ ����������
                    						d.visualizer.updateVertex(-1);
                    break;
                }
                case 15: { // ������������� j
                    							d.visualizer.updateVertex(-1);
                    break;
                }
                case 21: { // �������� ����
                    												d.visualizer.updateVertex(d.findArticulationPoints_j);
                    break;
                }
                case 22: { // ������� �����������
                    												d.visualizer.updateVertex(d.findArticulationPoints_j);
                    break;
                }
                case 26: { // i-��� ������� ����� ����������
                    									d.visualizer.updateVertex(-1);
                    break;
                }
                case 27: { // i-��� ������� �� �������� ������ ����������
                    									d.visualizer.updateVertex(-1);
                    break;
                }
                case 38: { // ����� ������� ��������
                    				d.visualizer.updateVertex(-1);
                    break;
                }
            }
        }
    }

    /**
      * ���� �����.
      */
    private final class findBridges extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 22;

        /**
          * �����������.
          */
        public findBridges() {
            super( 
                "findBridges", 
                0, // ����� ���������� ��������� 
                22, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� i", 
                    "����� ������", 
                    "������������� j", 
                    "����� ������", 
                    "���������� �� �����", 
                    "���������� �� ����� (���������)", 
                    "���������� �����", 
                    "���������� low � ������ �����", 
                    "���������� low � ������ ����� (���������)", 
                    "����� �� �������� ������", 
                    "Low ������ �� �����", 
                    "���������� low � dfnum � ������ �����", 
                    "���������� low � dfnum � ������ ����� (���������)", 
                    "���������� low � dfnum � ������ �����", 
                    "���������� low � dfnum � ������ ����� (���������)", 
                    "��� ����� �������� ������.", 
                    "��� ����� �������� ������.", 
                    "����� �� �������� ������.", 
                    "��������� j", 
                    "��������� i", 
                    "����� ������� ��������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    1, // ������������� i 
                    -1, // ����� ������ 
                    -1, // ������������� j 
                    -1, // ����� ������ 
                    -1, // ���������� �� ����� 
                    -1, // ���������� �� ����� (���������) 
                    0, // ���������� ����� 
                    -1, // ���������� low � ������ ����� 
                    -1, // ���������� low � ������ ����� (���������) 
                    0, // ����� �� �������� ������ 
                    0, // Low ������ �� ����� 
                    -1, // ���������� low � dfnum � ������ ����� 
                    -1, // ���������� low � dfnum � ������ ����� (���������) 
                    -1, // ���������� low � dfnum � ������ ����� 
                    -1, // ���������� low � dfnum � ������ ����� (���������) 
                    0, // ��� ����� �������� ������. 
                    0, // ��� ����� �������� ������. 
                    0, // ����� �� �������� ������. 
                    -1, // ��������� j 
                    -1, // ��������� i 
                    0, // ����� ������� �������� 
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
                    state = 1; // ������������� i
                    break;
                }
                case 1: { // ������������� i
                    stack.pushBoolean(false); 
                    state = 2; // ����� ������
                    break;
                }
                case 2: { // ����� ������
                    if (d.findBridges_i < d.numberOfVertices) {
                        state = 3; // ������������� j
                    } else {
                        state = 21; // ����� ������� ��������
                    }
                    break;
                }
                case 3: { // ������������� j
                    stack.pushBoolean(false); 
                    state = 4; // ����� ������
                    break;
                }
                case 4: { // ����� ������
                    if (d.findBridges_j < d.numberOfVertices) {
                        state = 5; // ���������� �� �����
                    } else {
                        state = 20; // ��������� i
                    }
                    break;
                }
                case 5: { // ���������� �� �����
                    if (d.aMatrix[d.findBridges_i][d.findBridges_j] != 0) {
                        state = 7; // ���������� �����
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // ���������� �� ����� (���������)
                    }
                    break;
                }
                case 6: { // ���������� �� ����� (���������)
                    state = 19; // ��������� j
                    break;
                }
                case 7: { // ���������� �����
                    state = 8; // ���������� low � ������ �����
                    break;
                }
                case 8: { // ���������� low � ������ �����
                    if (d.low[d.findBridges_i] == d.low[d.findBridges_j]) {
                        state = 10; // ����� �� �������� ������
                    } else {
                        state = 11; // Low ������ �� �����
                    }
                    break;
                }
                case 9: { // ���������� low � ������ ����� (���������)
                    stack.pushBoolean(true); 
                    state = 6; // ���������� �� ����� (���������)
                    break;
                }
                case 10: { // ����� �� �������� ������
                    stack.pushBoolean(true); 
                    state = 9; // ���������� low � ������ ����� (���������)
                    break;
                }
                case 11: { // Low ������ �� �����
                    state = 12; // ���������� low � dfnum � ������ �����
                    break;
                }
                case 12: { // ���������� low � dfnum � ������ �����
                    if (d.low[d.findBridges_i] == d.dfnumber[d.findBridges_i] || d.low[d.findBridges_j] == d.dfnumber[d.findBridges_j]) {
                        state = 14; // ���������� low � dfnum � ������ �����
                    } else {
                        state = 18; // ����� �� �������� ������.
                    }
                    break;
                }
                case 13: { // ���������� low � dfnum � ������ ����� (���������)
                    stack.pushBoolean(false); 
                    state = 9; // ���������� low � ������ ����� (���������)
                    break;
                }
                case 14: { // ���������� low � dfnum � ������ �����
                    if (d.low[d.findBridges_i] == d.dfnumber[d.findBridges_i]) {
                        state = 16; // ��� ����� �������� ������.
                    } else {
                        state = 17; // ��� ����� �������� ������.
                    }
                    break;
                }
                case 15: { // ���������� low � dfnum � ������ ����� (���������)
                    stack.pushBoolean(true); 
                    state = 13; // ���������� low � dfnum � ������ ����� (���������)
                    break;
                }
                case 16: { // ��� ����� �������� ������.
                    stack.pushBoolean(true); 
                    state = 15; // ���������� low � dfnum � ������ ����� (���������)
                    break;
                }
                case 17: { // ��� ����� �������� ������.
                    stack.pushBoolean(false); 
                    state = 15; // ���������� low � dfnum � ������ ����� (���������)
                    break;
                }
                case 18: { // ����� �� �������� ������.
                    stack.pushBoolean(false); 
                    state = 13; // ���������� low � dfnum � ������ ����� (���������)
                    break;
                }
                case 19: { // ��������� j
                    stack.pushBoolean(true); 
                    state = 4; // ����� ������
                    break;
                }
                case 20: { // ��������� i
                    stack.pushBoolean(true); 
                    state = 2; // ����� ������
                    break;
                }
                case 21: { // ����� ������� ��������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� i
                    startSection();
                    storeField(d, "findBridges_i");
                    				d.findBridges_i = 0;
                    storeField(d, "activeAuto");
                    				d.activeAuto = 4;
                    storeField(d, "findBridges_activeVertexInLow1");
                    				d.findBridges_activeVertexInLow1 = -1;
                    storeField(d, "findBridges_activeVertexInLow2");
                    				d.findBridges_activeVertexInLow2 = -1;
                    storeField(d, "findBridges_activeVertexInDfnum1");
                    				d.findBridges_activeVertexInDfnum1 = -1;
                    storeField(d, "findBridges_activeVertexInDfnum2");
                    				d.findBridges_activeVertexInDfnum2 = -1;
                    break;
                }
                case 2: { // ����� ������
                    break;
                }
                case 3: { // ������������� j
                    startSection();
                    storeField(d, "findBridges_j");
                    					d.findBridges_j = d.findBridges_i + 1;
                    break;
                }
                case 4: { // ����� ������
                    break;
                }
                case 5: { // ���������� �� �����
                    break;
                }
                case 6: { // ���������� �� ����� (���������)
                    break;
                }
                case 7: { // ���������� �����
                    startSection();
                    storeArray(d.aMatrix[d.findBridges_i], d.findBridges_j);
                    								d.aMatrix[d.findBridges_i][d.findBridges_j] = 7;
                    storeArray(d.aMatrix[d.findBridges_j], d.findBridges_i);
                    								d.aMatrix[d.findBridges_j][d.findBridges_i] = 7;
                    storeField(d, "findBridges_activeVertexInLow1");
                    								d.findBridges_activeVertexInLow1 = -1;
                    storeField(d, "findBridges_activeVertexInLow2");
                    								d.findBridges_activeVertexInLow2 = -1;
                    storeField(d, "findBridges_activeVertexInDfnum1");
                    								d.findBridges_activeVertexInDfnum1 = -1;
                    storeField(d, "findBridges_activeVertexInDfnum2");
                    								d.findBridges_activeVertexInDfnum2 = -1;
                    break;
                }
                case 8: { // ���������� low � ������ �����
                    break;
                }
                case 9: { // ���������� low � ������ ����� (���������)
                    break;
                }
                case 10: { // ����� �� �������� ������
                    startSection();
                    storeArray(d.aMatrix[d.findBridges_i], d.findBridges_j);
                    										d.aMatrix[d.findBridges_i][d.findBridges_j] = 3;
                    storeArray(d.aMatrix[d.findBridges_j], d.findBridges_i);
                    										d.aMatrix[d.findBridges_j][d.findBridges_i] = 3;
                    storeField(d, "findBridges_activeVertexInLow1");
                    										d.findBridges_activeVertexInLow1 = d.findBridges_i;
                    storeField(d, "findBridges_activeVertexInLow2");
                    										d.findBridges_activeVertexInLow2 = d.findBridges_j;
                    break;
                }
                case 11: { // Low ������ �� �����
                    startSection();
                    storeField(d, "findBridges_activeVertexInLow1");
                    										d.findBridges_activeVertexInLow1 = d.findBridges_i;
                    storeField(d, "findBridges_activeVertexInLow2");
                    										d.findBridges_activeVertexInLow2 = d.findBridges_j;
                    break;
                }
                case 12: { // ���������� low � dfnum � ������ �����
                    break;
                }
                case 13: { // ���������� low � dfnum � ������ ����� (���������)
                    break;
                }
                case 14: { // ���������� low � dfnum � ������ �����
                    break;
                }
                case 15: { // ���������� low � dfnum � ������ ����� (���������)
                    break;
                }
                case 16: { // ��� ����� �������� ������.
                    startSection();
                    storeArray(d.aMatrix[d.findBridges_i], d.findBridges_j);
                    														d.aMatrix[d.findBridges_i][d.findBridges_j] = 8;
                    storeArray(d.aMatrix[d.findBridges_j], d.findBridges_i);
                    														d.aMatrix[d.findBridges_j][d.findBridges_i] = 8;
                    storeField(d, "findBridges_activeVertexInDfnum1");
                    														d.findBridges_activeVertexInDfnum1 = d.findBridges_i;
                    storeField(d, "findBridges_activeVertexInDfnum2");
                    														d.findBridges_activeVertexInDfnum2 = d.findBridges_j;
                    break;
                }
                case 17: { // ��� ����� �������� ������.
                    startSection();
                    storeArray(d.aMatrix[d.findBridges_i], d.findBridges_j);
                    														d.aMatrix[d.findBridges_i][d.findBridges_j] = 8;
                    storeArray(d.aMatrix[d.findBridges_j], d.findBridges_i);
                    														d.aMatrix[d.findBridges_j][d.findBridges_i] = 8;
                    storeField(d, "findBridges_activeVertexInDfnum1");
                    														d.findBridges_activeVertexInDfnum1 = d.findBridges_i;
                    storeField(d, "findBridges_activeVertexInDfnum2");
                    														d.findBridges_activeVertexInDfnum2 = d.findBridges_j;
                    break;
                }
                case 18: { // ����� �� �������� ������.
                    startSection();
                    storeArray(d.aMatrix[d.findBridges_i], d.findBridges_j);
                    												d.aMatrix[d.findBridges_i][d.findBridges_j] = 3;
                    storeArray(d.aMatrix[d.findBridges_j], d.findBridges_i);
                    												d.aMatrix[d.findBridges_j][d.findBridges_i] = 3;
                    storeField(d, "findBridges_activeVertexInDfnum1");
                    												d.findBridges_activeVertexInDfnum1 = d.findBridges_i;
                    storeField(d, "findBridges_activeVertexInDfnum2");
                    												d.findBridges_activeVertexInDfnum2 = d.findBridges_j;
                    break;
                }
                case 19: { // ��������� j
                    startSection();
                    storeField(d, "findBridges_j");
                    						d.findBridges_j = d.findBridges_j + 1;
                    break;
                }
                case 20: { // ��������� i
                    startSection();
                    storeField(d, "findBridges_i");
                    					d.findBridges_i = d.findBridges_i + 1;
                    break;
                }
                case 21: { // ����� ������� ��������
                    startSection();
                    storeField(d, "findBridges_activeVertexInLow1");
                    				d.findBridges_activeVertexInLow1 = -1;
                    storeField(d, "findBridges_activeVertexInLow2");
                    				d.findBridges_activeVertexInLow2 = -1;
                    storeField(d, "findBridges_activeVertexInDfnum1");
                    				d.findBridges_activeVertexInDfnum1 = -1;
                    storeField(d, "findBridges_activeVertexInDfnum2");
                    				d.findBridges_activeVertexInDfnum2 = -1;
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
                case 1: { // ������������� i
                    restoreSection();
                    break;
                }
                case 2: { // ����� ������
                    break;
                }
                case 3: { // ������������� j
                    restoreSection();
                    break;
                }
                case 4: { // ����� ������
                    break;
                }
                case 5: { // ���������� �� �����
                    break;
                }
                case 6: { // ���������� �� ����� (���������)
                    break;
                }
                case 7: { // ���������� �����
                    restoreSection();
                    break;
                }
                case 8: { // ���������� low � ������ �����
                    break;
                }
                case 9: { // ���������� low � ������ ����� (���������)
                    break;
                }
                case 10: { // ����� �� �������� ������
                    restoreSection();
                    break;
                }
                case 11: { // Low ������ �� �����
                    restoreSection();
                    break;
                }
                case 12: { // ���������� low � dfnum � ������ �����
                    break;
                }
                case 13: { // ���������� low � dfnum � ������ ����� (���������)
                    break;
                }
                case 14: { // ���������� low � dfnum � ������ �����
                    break;
                }
                case 15: { // ���������� low � dfnum � ������ ����� (���������)
                    break;
                }
                case 16: { // ��� ����� �������� ������.
                    restoreSection();
                    break;
                }
                case 17: { // ��� ����� �������� ������.
                    restoreSection();
                    break;
                }
                case 18: { // ����� �� �������� ������.
                    restoreSection();
                    break;
                }
                case 19: { // ��������� j
                    restoreSection();
                    break;
                }
                case 20: { // ��������� i
                    restoreSection();
                    break;
                }
                case 21: { // ����� ������� ��������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� i
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����� ������
                    if (stack.popBoolean()) {
                        state = 20; // ��������� i
                    } else {
                        state = 1; // ������������� i
                    }
                    break;
                }
                case 3: { // ������������� j
                    state = 2; // ����� ������
                    break;
                }
                case 4: { // ����� ������
                    if (stack.popBoolean()) {
                        state = 19; // ��������� j
                    } else {
                        state = 3; // ������������� j
                    }
                    break;
                }
                case 5: { // ���������� �� �����
                    state = 4; // ����� ������
                    break;
                }
                case 6: { // ���������� �� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 9; // ���������� low � ������ ����� (���������)
                    } else {
                        state = 5; // ���������� �� �����
                    }
                    break;
                }
                case 7: { // ���������� �����
                    state = 5; // ���������� �� �����
                    break;
                }
                case 8: { // ���������� low � ������ �����
                    state = 7; // ���������� �����
                    break;
                }
                case 9: { // ���������� low � ������ ����� (���������)
                    if (stack.popBoolean()) {
                        state = 10; // ����� �� �������� ������
                    } else {
                        state = 13; // ���������� low � dfnum � ������ ����� (���������)
                    }
                    break;
                }
                case 10: { // ����� �� �������� ������
                    state = 8; // ���������� low � ������ �����
                    break;
                }
                case 11: { // Low ������ �� �����
                    state = 8; // ���������� low � ������ �����
                    break;
                }
                case 12: { // ���������� low � dfnum � ������ �����
                    state = 11; // Low ������ �� �����
                    break;
                }
                case 13: { // ���������� low � dfnum � ������ ����� (���������)
                    if (stack.popBoolean()) {
                        state = 15; // ���������� low � dfnum � ������ ����� (���������)
                    } else {
                        state = 18; // ����� �� �������� ������.
                    }
                    break;
                }
                case 14: { // ���������� low � dfnum � ������ �����
                    state = 12; // ���������� low � dfnum � ������ �����
                    break;
                }
                case 15: { // ���������� low � dfnum � ������ ����� (���������)
                    if (stack.popBoolean()) {
                        state = 16; // ��� ����� �������� ������.
                    } else {
                        state = 17; // ��� ����� �������� ������.
                    }
                    break;
                }
                case 16: { // ��� ����� �������� ������.
                    state = 14; // ���������� low � dfnum � ������ �����
                    break;
                }
                case 17: { // ��� ����� �������� ������.
                    state = 14; // ���������� low � dfnum � ������ �����
                    break;
                }
                case 18: { // ����� �� �������� ������.
                    state = 12; // ���������� low � dfnum � ������ �����
                    break;
                }
                case 19: { // ��������� j
                    state = 6; // ���������� �� ����� (���������)
                    break;
                }
                case 20: { // ��������� i
                    state = 4; // ����� ������
                    break;
                }
                case 21: { // ����� ������� ��������
                    state = 2; // ����� ������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 21; // ����� ������� ��������
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
                case 1: { // ������������� i
                    comment = ArticulationPoints.this.getComment("findBridges.FBInitI"); 
                    break;
                }
                case 7: { // ���������� �����
                    comment = ArticulationPoints.this.getComment("findBridges.LookAtEdge"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.findBridges_j)}; 
                    break;
                }
                case 10: { // ����� �� �������� ������
                    comment = ArticulationPoints.this.getComment("findBridges.NotFoundBridge1"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.findBridges_j), new Integer(d.low[d.findBridges_i])}; 
                    break;
                }
                case 11: { // Low ������ �� �����
                    comment = ArticulationPoints.this.getComment("findBridges.LowNotEqual"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.findBridges_j), new Integer(d.low[d.findBridges_i]), new Integer(d.low[d.findBridges_j])}; 
                    break;
                }
                case 16: { // ��� ����� �������� ������.
                    comment = ArticulationPoints.this.getComment("findBridges.FoundBridge1"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.low[d.findBridges_i]), new Integer(d.findBridges_j)}; 
                    break;
                }
                case 17: { // ��� ����� �������� ������.
                    comment = ArticulationPoints.this.getComment("findBridges.FoundBridge2"); 
                    args = new Object[]{new Integer(d.findBridges_j), new Integer(d.low[d.findBridges_j]), new Integer(d.findBridges_i)}; 
                    break;
                }
                case 18: { // ����� �� �������� ������.
                    comment = ArticulationPoints.this.getComment("findBridges.NotFoundBridge2"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.findBridges_j)}; 
                    break;
                }
                case 21: { // ����� ������� ��������
                    comment = ArticulationPoints.this.getComment("findBridges.AllBridgesFound"); 
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
                case 1: { // ������������� i
                    				d.visualizer.updateVertex(-1);
                    break;
                }
                case 7: { // ���������� �����
                    								d.visualizer.updateVertex(-1);
                    break;
                }
                case 10: { // ����� �� �������� ������
                    										d.visualizer.updateVertex(-1);
                    break;
                }
                case 11: { // Low ������ �� �����
                    										d.visualizer.updateVertex(-1);
                    break;
                }
                case 16: { // ��� ����� �������� ������.
                    														d.visualizer.updateVertex(-1);
                    break;
                }
                case 17: { // ��� ����� �������� ������.
                    														d.visualizer.updateVertex(-1);
                    break;
                }
                case 18: { // ����� �� �������� ������.
                    												d.visualizer.updateVertex(-1);
                    break;
                }
                case 21: { // ����� ������� ��������
                    				d.visualizer.updateVertex(-1);
                    break;
                }
            }
        }
    }

    /**
      * ���� ����� ����������, �����, ���������� ������������.
      */
    private final class Main extends BaseAutomata implements Automata {
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
        public Main() {
            super( 
                "Main", 
                0, // ����� ���������� ��������� 
                11, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� i", 
                    "����� ������������ ������.", 
                    "���������� �� �������", 
                    "���������� �� ������� (���������)", 
                    "������������� ����������", 
                    "������� ���� � ������� (�������)", 
                    "������� low ��� ������ �������. (�������)", 
                    "���� ����� ����������. (�������)", 
                    "��������� i", 
                    "���� ����� (�������)", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� i 
                    -1, // ����� ������������ ������. 
                    -1, // ���������� �� ������� 
                    -1, // ���������� �� ������� (���������) 
                    -1, // ������������� ���������� 
                    CALL_AUTO_LEVEL, // ������� ���� � ������� (�������) 
                    CALL_AUTO_LEVEL, // ������� low ��� ������ �������. (�������) 
                    CALL_AUTO_LEVEL, // ���� ����� ����������. (�������) 
                    -1, // ��������� i 
                    CALL_AUTO_LEVEL, // ���� ����� (�������) 
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
                    state = 1; // ������������� i
                    break;
                }
                case 1: { // ������������� i
                    stack.pushBoolean(false); 
                    state = 2; // ����� ������������ ������.
                    break;
                }
                case 2: { // ����� ������������ ������.
                    if (d.Main_i < d.numberOfVertices) {
                        state = 3; // ���������� �� �������
                    } else {
                        state = 10; // ���� ����� (�������)
                    }
                    break;
                }
                case 3: { // ���������� �� �������
                    if (d.articulationPoints[d.Main_i] == 0) {
                        state = 5; // ������������� ����������
                    } else {
                        stack.pushBoolean(false); 
                        state = 4; // ���������� �� ������� (���������)
                    }
                    break;
                }
                case 4: { // ���������� �� ������� (���������)
                    state = 9; // ��������� i
                    break;
                }
                case 5: { // ������������� ����������
                    state = 6; // ������� ���� � ������� (�������)
                    break;
                }
                case 6: { // ������� ���� � ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 7; // ������� low ��� ������ �������. (�������)
                    }
                    break;
                }
                case 7: { // ������� low ��� ������ �������. (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // ���� ����� ����������. (�������)
                    }
                    break;
                }
                case 8: { // ���� ����� ����������. (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 4; // ���������� �� ������� (���������)
                    }
                    break;
                }
                case 9: { // ��������� i
                    stack.pushBoolean(true); 
                    state = 2; // ����� ������������ ������.
                    break;
                }
                case 10: { // ���� ����� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = END_STATE; 
                    }
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� i
                    startSection();
                    storeField(d, "Main_i");
                    				d.Main_i = 0;
                    break;
                }
                case 2: { // ����� ������������ ������.
                    break;
                }
                case 3: { // ���������� �� �������
                    break;
                }
                case 4: { // ���������� �� ������� (���������)
                    break;
                }
                case 5: { // ������������� ����������
                    startSection();
                    storeField(d, "root");
                    							d.root = d.Main_i;
                    break;
                }
                case 6: { // ������� ���� � ������� (�������)
                    if (child == null) {
                        child = new DFS(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 7: { // ������� low ��� ������ �������. (�������)
                    if (child == null) {
                        child = new findLow(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // ���� ����� ����������. (�������)
                    if (child == null) {
                        child = new findArticulationPoints(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 9: { // ��������� i
                    startSection();
                    storeField(d, "Main_i");
                    					d.Main_i = d.Main_i + 1;
                    break;
                }
                case 10: { // ���� ����� (�������)
                    if (child == null) {
                        child = new findBridges(); 
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
                case 1: { // ������������� i
                    restoreSection();
                    break;
                }
                case 2: { // ����� ������������ ������.
                    break;
                }
                case 3: { // ���������� �� �������
                    break;
                }
                case 4: { // ���������� �� ������� (���������)
                    break;
                }
                case 5: { // ������������� ����������
                    restoreSection();
                    break;
                }
                case 6: { // ������� ���� � ������� (�������)
                    if (child == null) {
                        child = new DFS(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 7: { // ������� low ��� ������ �������. (�������)
                    if (child == null) {
                        child = new findLow(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // ���� ����� ����������. (�������)
                    if (child == null) {
                        child = new findArticulationPoints(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 9: { // ��������� i
                    restoreSection();
                    break;
                }
                case 10: { // ���� ����� (�������)
                    if (child == null) {
                        child = new findBridges(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� i
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����� ������������ ������.
                    if (stack.popBoolean()) {
                        state = 9; // ��������� i
                    } else {
                        state = 1; // ������������� i
                    }
                    break;
                }
                case 3: { // ���������� �� �������
                    state = 2; // ����� ������������ ������.
                    break;
                }
                case 4: { // ���������� �� ������� (���������)
                    if (stack.popBoolean()) {
                        state = 8; // ���� ����� ����������. (�������)
                    } else {
                        state = 3; // ���������� �� �������
                    }
                    break;
                }
                case 5: { // ������������� ����������
                    state = 3; // ���������� �� �������
                    break;
                }
                case 6: { // ������� ���� � ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 5; // ������������� ����������
                    }
                    break;
                }
                case 7: { // ������� low ��� ������ �������. (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // ������� ���� � ������� (�������)
                    }
                    break;
                }
                case 8: { // ���� ����� ����������. (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 7; // ������� low ��� ������ �������. (�������)
                    }
                    break;
                }
                case 9: { // ��������� i
                    state = 4; // ���������� �� ������� (���������)
                    break;
                }
                case 10: { // ���� ����� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 2; // ����� ������������ ������.
                    }
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 10; // ���� ����� (�������)
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
                    comment = ArticulationPoints.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 6: { // ������� ���� � ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // ������� low ��� ������ �������. (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 8: { // ���� ����� ����������. (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // ���� ����� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = ArticulationPoints.this.getComment("Main.END_STATE"); 
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
                    				d.visualizer.updateVertex(-1);
                    break;
                }
                case 6: { // ������� ���� � ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 7: { // ������� low ��� ������ �������. (�������)
                    child.drawState(); 
                    break;
                }
                case 8: { // ���� ����� ����������. (�������)
                    child.drawState(); 
                    break;
                }
                case 10: { // ���� ����� (�������)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    				d.visualizer.updateVertex(-1);
                    break;
                }
            }
        }
    }
}
