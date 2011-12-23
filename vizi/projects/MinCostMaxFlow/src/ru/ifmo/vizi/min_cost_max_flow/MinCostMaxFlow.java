package ru.ifmo.vizi.min_cost_max_flow;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class MinCostMaxFlow extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public MinCostMaxFlow(Locale locale) {
        super("ru.ifmo.vizi.min_cost_max_flow.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ���������� ������.
          */
        public int n = 6;

        /**
          * ���������� �����.
          */
        public int m = 10;

        /**
          * ��������.
          */
        public int source = 0;

        /**
          * ����.
          */
        public int sink = 1;

        /**
          * ������� ��������� �����.
          */
        public Edge[][] edge = new Edge[n][n];

        /**
          * ������� ��������� ��� �������� ����� �����.
          */
        public Edge[][] reverseEdge = new Edge[n][n];

        /**
          * ������ �����������.
          */
        public long[] phi = new long[n];

        /**
          * ���������� �� ���������.
          */
        public long[] dist = new long[n];

        /**
          * ������������ �� �� ������ �������.
          */
        public boolean[] used = new boolean[n];

        /**
          * ������ ��� ������������� ����������� ����.
          */
        public Edge[] prev = new Edge[n];

        /**
          * ������� ��������� ������.
          */
        public long cost = 0;

        /**
          * ������������ ����������� �����.
          */
        public long maxFlow = 0;

        /**
          * ���� �� ���������� �������� ����� � �������������� ���������� � ��������.
          */
        public boolean extendedDraw = false;

        /**
          * ��� �������� (0 - ������, 1 - ����������, 2 - ����������).
          */
        public int highlight = 0;

        /**
          * ��������� �������.
          */
        public MinCostMaxFlowVisualizer visualizer = null;

        /**
          * ���������� ��� ������������� � ����� (��������� dijkstra).
          */
        public int dijkstra_i;

        /**
          * ���������� ��� ������������� � ����� (��������� dijkstra).
          */
        public int dijkstra_j;

        /**
          * ������� ������� � �������� (��������� dijkstra).
          */
        public int dijkstra_u;

        /**
          * ������� ������� � ���������� ���� (��������� dijkstra).
          */
        public int dijkstra_cur;

        /**
          * ���������� ����� (��������� updatePotentials).
          */
        public int updatePotentials_i;

        /**
          * ������� ������� (��������� recoverPath).
          */
        public int recoverPath_cur;

        /**
          * ������� ������� (��������� letPassFlow).
          */
        public int letPassFlow_cur;

        /**
          * ��������� ������������ ������ (��������� letPassFlow).
          */
        public long letPassFlow_curCost;

        public String toString() {
            return "";
        }
    }

    /**
      * ��������� ���������� �� ��������� �� ������.
      */
    private final class dijkstra extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 34;

        /**
          * �����������.
          */
        public dijkstra() {
            super( 
                "dijkstra", 
                0, // ����� ���������� ��������� 
                34, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������������� cur", 
                    "������� ��������� � ����", 
                    "������� ��������� � �������� �����", 
                    "�������� i", 
                    "��������� ���������� ���������������", 
                    "��� �����", 
                    "�������� i2", 
                    "��� ������ �� ������������", 
                    "��� �����", 
                    "������ �������� ���������� ��� �������� � �������������� i", 
                    "������� ���� ��������", 
                    "�������������� u � j", 
                    "�������� �������", 
                    "�������� �������� ������� ���������������� �������", 
                    "�������� �������� ������� ���������������� ������� (���������)", 
                    "��������� u", 
                    "���������", 
                    "���� �� �������, ������� ����� ������������", 
                    "���� �� �������, ������� ����� ������������ (���������)", 
                    "�������������� j", 
                    "���� �� ������, ����������� u", 
                    "���� �� ��������� ������� �� ����� �����", 
                    "���� �� ��������� ������� �� ����� ����� (���������)", 
                    "��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������", 
                    "��������� �����", 
                    "�������������� j", 
                    "���� �� �������� ������, ����������� u", 
                    "���� �� ��������� ������� �� ����� �����", 
                    "���� �� ��������� ������� �� ����� ����� (���������)", 
                    "��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������", 
                    "��������� �����", 
                    "�� ������������ ������� u", 
                    "���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // �������������� cur 
                    -1, // ������� ��������� � ���� 
                    -1, // ������� ��������� � �������� ����� 
                    -1, // �������� i 
                    -1, // ��������� ���������� ��������������� 
                    -1, // ��� ����� 
                    -1, // �������� i2 
                    -1, // ��� ������ �� ������������ 
                    -1, // ��� ����� 
                    -1, // ������ �������� ���������� ��� �������� � �������������� i 
                    -1, // ������� ���� �������� 
                    -1, // �������������� u � j 
                    -1, // �������� ������� 
                    -1, // �������� �������� ������� ���������������� ������� 
                    -1, // �������� �������� ������� ���������������� ������� (���������) 
                    -1, // ��������� u 
                    -1, // ��������� 
                    -1, // ���� �� �������, ������� ����� ������������ 
                    -1, // ���� �� �������, ������� ����� ������������ (���������) 
                    -1, // �������������� j 
                    -1, // ���� �� ������, ����������� u 
                    -1, // ���� �� ��������� ������� �� ����� ����� 
                    -1, // ���� �� ��������� ������� �� ����� ����� (���������) 
                    -1, // ��������� ���������� �� ������� � ��������� ����� � ������ ��� �������������� 
                    -1, // ��������� ����� 
                    -1, // �������������� j 
                    -1, // ���� �� �������� ������, ����������� u 
                    -1, // ���� �� ��������� ������� �� ����� ����� 
                    -1, // ���� �� ��������� ������� �� ����� ����� (���������) 
                    -1, // ��������� ���������� �� ������� � ��������� ����� � ������ ��� �������������� 
                    -1, // ��������� ����� 
                    -1, // �� ������������ ������� u 
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
                    state = 1; // �������������� cur
                    break;
                }
                case 1: { // �������������� cur
                    stack.pushBoolean(false); 
                    state = 2; // ������� ��������� � ����
                    break;
                }
                case 2: { // ������� ��������� � ����
                    if ((d.prev[d.dijkstra_cur] != null) && (d.dijkstra_cur != d.source)) {
                        state = 3; // ������� ��������� � �������� �����
                    } else {
                        state = 4; // �������� i
                    }
                    break;
                }
                case 3: { // ������� ��������� � �������� �����
                    stack.pushBoolean(true); 
                    state = 2; // ������� ��������� � ����
                    break;
                }
                case 4: { // �������� i
                    stack.pushBoolean(false); 
                    state = 5; // ��������� ���������� ���������������
                    break;
                }
                case 5: { // ��������� ���������� ���������������
                    if (d.dijkstra_i < d.n) {
                        state = 6; // ��� �����
                    } else {
                        state = 7; // �������� i2
                    }
                    break;
                }
                case 6: { // ��� �����
                    stack.pushBoolean(true); 
                    state = 5; // ��������� ���������� ���������������
                    break;
                }
                case 7: { // �������� i2
                    stack.pushBoolean(false); 
                    state = 8; // ��� ������ �� ������������
                    break;
                }
                case 8: { // ��� ������ �� ������������
                    if (d.dijkstra_i < d.n) {
                        state = 9; // ��� �����
                    } else {
                        state = 10; // ������ �������� ���������� ��� �������� � �������������� i
                    }
                    break;
                }
                case 9: { // ��� �����
                    stack.pushBoolean(true); 
                    state = 8; // ��� ������ �� ������������
                    break;
                }
                case 10: { // ������ �������� ���������� ��� �������� � �������������� i
                    stack.pushBoolean(false); 
                    state = 11; // ������� ���� ��������
                    break;
                }
                case 11: { // ������� ���� ��������
                    if (d.dijkstra_i < d.n) {
                        state = 12; // �������������� u � j
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 12: { // �������������� u � j
                    stack.pushBoolean(false); 
                    state = 13; // �������� �������
                    break;
                }
                case 13: { // �������� �������
                    if (d.dijkstra_j < d.n) {
                        state = 14; // �������� �������� ������� ���������������� �������
                    } else {
                        state = 18; // ���� �� �������, ������� ����� ������������
                    }
                    break;
                }
                case 14: { // �������� �������� ������� ���������������� �������
                    if ((!d.used[d.dijkstra_j]) && (d.dist[d.dijkstra_j] >= 0) && ( ( d.dijkstra_u == -1) || ( d.dist[d.dijkstra_j] < d.dist[d.dijkstra_u] ) )) {
                        state = 16; // ��������� u
                    } else {
                        stack.pushBoolean(false); 
                        state = 15; // �������� �������� ������� ���������������� ������� (���������)
                    }
                    break;
                }
                case 15: { // �������� �������� ������� ���������������� ������� (���������)
                    state = 17; // ���������
                    break;
                }
                case 16: { // ��������� u
                    stack.pushBoolean(true); 
                    state = 15; // �������� �������� ������� ���������������� ������� (���������)
                    break;
                }
                case 17: { // ���������
                    stack.pushBoolean(true); 
                    state = 13; // �������� �������
                    break;
                }
                case 18: { // ���� �� �������, ������� ����� ������������
                    if (d.dijkstra_u != -1) {
                        state = 20; // �������������� j
                    } else {
                        stack.pushBoolean(false); 
                        state = 19; // ���� �� �������, ������� ����� ������������ (���������)
                    }
                    break;
                }
                case 19: { // ���� �� �������, ������� ����� ������������ (���������)
                    state = 33; // ���������
                    break;
                }
                case 20: { // �������������� j
                    stack.pushBoolean(false); 
                    state = 21; // ���� �� ������, ����������� u
                    break;
                }
                case 21: { // ���� �� ������, ����������� u
                    if (d.dijkstra_j < d.n) {
                        state = 22; // ���� �� ��������� ������� �� ����� �����
                    } else {
                        state = 26; // �������������� j
                    }
                    break;
                }
                case 22: { // ���� �� ��������� ������� �� ����� �����
                    if ((d.edge[d.dijkstra_u][d.dijkstra_j] != null ) && ( d.edge[d.dijkstra_u][d.dijkstra_j].capacity - d.edge[d.dijkstra_u][d.dijkstra_j].flow > 0 ) && ( (d.dist[d.dijkstra_j] == -1 ) || (d.dist[d.dijkstra_u] + d.edge[d.dijkstra_u][d.dijkstra_j].cost - d.phi[d.dijkstra_j] + d.phi[d.dijkstra_u] < d.dist[d.dijkstra_j] ) )) {
                        state = 24; // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    } else {
                        stack.pushBoolean(false); 
                        state = 23; // ���� �� ��������� ������� �� ����� ����� (���������)
                    }
                    break;
                }
                case 23: { // ���� �� ��������� ������� �� ����� ����� (���������)
                    state = 25; // ��������� �����
                    break;
                }
                case 24: { // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    stack.pushBoolean(true); 
                    state = 23; // ���� �� ��������� ������� �� ����� ����� (���������)
                    break;
                }
                case 25: { // ��������� �����
                    stack.pushBoolean(true); 
                    state = 21; // ���� �� ������, ����������� u
                    break;
                }
                case 26: { // �������������� j
                    stack.pushBoolean(false); 
                    state = 27; // ���� �� �������� ������, ����������� u
                    break;
                }
                case 27: { // ���� �� �������� ������, ����������� u
                    if (d.dijkstra_j < d.n) {
                        state = 28; // ���� �� ��������� ������� �� ����� �����
                    } else {
                        state = 32; // �� ������������ ������� u
                    }
                    break;
                }
                case 28: { // ���� �� ��������� ������� �� ����� �����
                    if ((d.reverseEdge[d.dijkstra_u][d.dijkstra_j] != null ) && ( d.reverseEdge[d.dijkstra_u][d.dijkstra_j].capacity - d.reverseEdge[d.dijkstra_u][d.dijkstra_j].flow > 0 ) && ( (d.dist[d.dijkstra_j] == -1 ) || (d.dist[d.dijkstra_u] + d.reverseEdge[d.dijkstra_u][d.dijkstra_j].cost - d.phi[d.dijkstra_j] + d.phi[d.dijkstra_u] < d.dist[d.dijkstra_j] ) )) {
                        state = 30; // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    } else {
                        stack.pushBoolean(false); 
                        state = 29; // ���� �� ��������� ������� �� ����� ����� (���������)
                    }
                    break;
                }
                case 29: { // ���� �� ��������� ������� �� ����� ����� (���������)
                    state = 31; // ��������� �����
                    break;
                }
                case 30: { // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    stack.pushBoolean(true); 
                    state = 29; // ���� �� ��������� ������� �� ����� ����� (���������)
                    break;
                }
                case 31: { // ��������� �����
                    stack.pushBoolean(true); 
                    state = 27; // ���� �� �������� ������, ����������� u
                    break;
                }
                case 32: { // �� ������������ ������� u
                    stack.pushBoolean(true); 
                    state = 19; // ���� �� �������, ������� ����� ������������ (���������)
                    break;
                }
                case 33: { // ���������
                    stack.pushBoolean(true); 
                    state = 11; // ������� ���� ��������
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������� cur
                    startSection();
                    storeField(d, "dijkstra_cur");
                    d.dijkstra_cur = d.sink;
                    break;
                }
                case 2: { // ������� ��������� � ����
                    break;
                }
                case 3: { // ������� ��������� � �������� �����
                    startSection();
                    storeField(d.prev[d.dijkstra_cur], "highlighted");
                    d.prev[d.dijkstra_cur].highlighted = false;
                    storeField(d, "dijkstra_cur");
                    d.dijkstra_cur = d.prev[d.dijkstra_cur].u;
                    break;
                }
                case 4: { // �������� i
                    startSection();
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = 0;
                    break;
                }
                case 5: { // ��������� ���������� ���������������
                    break;
                }
                case 6: { // ��� �����
                    startSection();
                    storeArray(d.dist, d.dijkstra_i);
                    d.dist[d.dijkstra_i] = -1;
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = d.dijkstra_i + 1;
                    break;
                }
                case 7: { // �������� i2
                    startSection();
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = 0;
                    break;
                }
                case 8: { // ��� ������ �� ������������
                    break;
                }
                case 9: { // ��� �����
                    startSection();
                    storeArray(d.used, d.dijkstra_i);
                    d.used[d.dijkstra_i] = false;
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = d.dijkstra_i + 1;
                    break;
                }
                case 10: { // ������ �������� ���������� ��� �������� � �������������� i
                    startSection();
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = 0;
                    d.dist[d.source] = 0;
                    d.prev[d.source] = null;
                    break;
                }
                case 11: { // ������� ���� ��������
                    break;
                }
                case 12: { // �������������� u � j
                    startSection();
                    storeField(d, "dijkstra_u");
                    d.dijkstra_u = -1;
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = 0;
                    break;
                }
                case 13: { // �������� �������
                    break;
                }
                case 14: { // �������� �������� ������� ���������������� �������
                    break;
                }
                case 15: { // �������� �������� ������� ���������������� ������� (���������)
                    break;
                }
                case 16: { // ��������� u
                    startSection();
                    storeField(d, "dijkstra_u");
                    d.dijkstra_u = d.dijkstra_j;
                    break;
                }
                case 17: { // ���������
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = d.dijkstra_j + 1;
                    break;
                }
                case 18: { // ���� �� �������, ������� ����� ������������
                    break;
                }
                case 19: { // ���� �� �������, ������� ����� ������������ (���������)
                    break;
                }
                case 20: { // �������������� j
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = 0;
                    break;
                }
                case 21: { // ���� �� ������, ����������� u
                    break;
                }
                case 22: { // ���� �� ��������� ������� �� ����� �����
                    break;
                }
                case 23: { // ���� �� ��������� ������� �� ����� ����� (���������)
                    break;
                }
                case 24: { // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    startSection();
                    storeArray(d.dist, d.dijkstra_j);
                    d.dist[d.dijkstra_j] = d.dist[d.dijkstra_u] + d.edge[d.dijkstra_u][d.dijkstra_j].cost - d.phi[d.dijkstra_j] + d.phi[d.dijkstra_u];
                    storeArray(d.prev, d.dijkstra_j);
                    d.prev[d.dijkstra_j] = d.edge[d.dijkstra_u][d.dijkstra_j];
                    break;
                }
                case 25: { // ��������� �����
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = d.dijkstra_j + 1;
                    break;
                }
                case 26: { // �������������� j
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = 0;
                    break;
                }
                case 27: { // ���� �� �������� ������, ����������� u
                    break;
                }
                case 28: { // ���� �� ��������� ������� �� ����� �����
                    break;
                }
                case 29: { // ���� �� ��������� ������� �� ����� ����� (���������)
                    break;
                }
                case 30: { // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    startSection();
                    storeArray(d.dist, d.dijkstra_j);
                    d.dist[d.dijkstra_j] = d.dist[d.dijkstra_u] + d.reverseEdge[d.dijkstra_u][d.dijkstra_j].cost - d.phi[d.dijkstra_j] + d.phi[d.dijkstra_u];
                    storeArray(d.prev, d.dijkstra_j);
                    d.prev[d.dijkstra_j] = d.reverseEdge[d.dijkstra_u][d.dijkstra_j];
                    break;
                }
                case 31: { // ��������� �����
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = d.dijkstra_j + 1;
                    break;
                }
                case 32: { // �� ������������ ������� u
                    startSection();
                    storeArray(d.used, d.dijkstra_u);
                    d.used[d.dijkstra_u] = true;
                    break;
                }
                case 33: { // ���������
                    startSection();
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = d.dijkstra_i + 1;
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
                case 1: { // �������������� cur
                    restoreSection();
                    break;
                }
                case 2: { // ������� ��������� � ����
                    break;
                }
                case 3: { // ������� ��������� � �������� �����
                    restoreSection();
                    break;
                }
                case 4: { // �������� i
                    restoreSection();
                    break;
                }
                case 5: { // ��������� ���������� ���������������
                    break;
                }
                case 6: { // ��� �����
                    restoreSection();
                    break;
                }
                case 7: { // �������� i2
                    restoreSection();
                    break;
                }
                case 8: { // ��� ������ �� ������������
                    break;
                }
                case 9: { // ��� �����
                    restoreSection();
                    break;
                }
                case 10: { // ������ �������� ���������� ��� �������� � �������������� i
                    restoreSection();
                    break;
                }
                case 11: { // ������� ���� ��������
                    break;
                }
                case 12: { // �������������� u � j
                    restoreSection();
                    break;
                }
                case 13: { // �������� �������
                    break;
                }
                case 14: { // �������� �������� ������� ���������������� �������
                    break;
                }
                case 15: { // �������� �������� ������� ���������������� ������� (���������)
                    break;
                }
                case 16: { // ��������� u
                    restoreSection();
                    break;
                }
                case 17: { // ���������
                    restoreSection();
                    break;
                }
                case 18: { // ���� �� �������, ������� ����� ������������
                    break;
                }
                case 19: { // ���� �� �������, ������� ����� ������������ (���������)
                    break;
                }
                case 20: { // �������������� j
                    restoreSection();
                    break;
                }
                case 21: { // ���� �� ������, ����������� u
                    break;
                }
                case 22: { // ���� �� ��������� ������� �� ����� �����
                    break;
                }
                case 23: { // ���� �� ��������� ������� �� ����� ����� (���������)
                    break;
                }
                case 24: { // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    restoreSection();
                    break;
                }
                case 25: { // ��������� �����
                    restoreSection();
                    break;
                }
                case 26: { // �������������� j
                    restoreSection();
                    break;
                }
                case 27: { // ���� �� �������� ������, ����������� u
                    break;
                }
                case 28: { // ���� �� ��������� ������� �� ����� �����
                    break;
                }
                case 29: { // ���� �� ��������� ������� �� ����� ����� (���������)
                    break;
                }
                case 30: { // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    restoreSection();
                    break;
                }
                case 31: { // ��������� �����
                    restoreSection();
                    break;
                }
                case 32: { // �� ������������ ������� u
                    restoreSection();
                    break;
                }
                case 33: { // ���������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // �������������� cur
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������� ��������� � ����
                    if (stack.popBoolean()) {
                        state = 3; // ������� ��������� � �������� �����
                    } else {
                        state = 1; // �������������� cur
                    }
                    break;
                }
                case 3: { // ������� ��������� � �������� �����
                    state = 2; // ������� ��������� � ����
                    break;
                }
                case 4: { // �������� i
                    state = 2; // ������� ��������� � ����
                    break;
                }
                case 5: { // ��������� ���������� ���������������
                    if (stack.popBoolean()) {
                        state = 6; // ��� �����
                    } else {
                        state = 4; // �������� i
                    }
                    break;
                }
                case 6: { // ��� �����
                    state = 5; // ��������� ���������� ���������������
                    break;
                }
                case 7: { // �������� i2
                    state = 5; // ��������� ���������� ���������������
                    break;
                }
                case 8: { // ��� ������ �� ������������
                    if (stack.popBoolean()) {
                        state = 9; // ��� �����
                    } else {
                        state = 7; // �������� i2
                    }
                    break;
                }
                case 9: { // ��� �����
                    state = 8; // ��� ������ �� ������������
                    break;
                }
                case 10: { // ������ �������� ���������� ��� �������� � �������������� i
                    state = 8; // ��� ������ �� ������������
                    break;
                }
                case 11: { // ������� ���� ��������
                    if (stack.popBoolean()) {
                        state = 33; // ���������
                    } else {
                        state = 10; // ������ �������� ���������� ��� �������� � �������������� i
                    }
                    break;
                }
                case 12: { // �������������� u � j
                    state = 11; // ������� ���� ��������
                    break;
                }
                case 13: { // �������� �������
                    if (stack.popBoolean()) {
                        state = 17; // ���������
                    } else {
                        state = 12; // �������������� u � j
                    }
                    break;
                }
                case 14: { // �������� �������� ������� ���������������� �������
                    state = 13; // �������� �������
                    break;
                }
                case 15: { // �������� �������� ������� ���������������� ������� (���������)
                    if (stack.popBoolean()) {
                        state = 16; // ��������� u
                    } else {
                        state = 14; // �������� �������� ������� ���������������� �������
                    }
                    break;
                }
                case 16: { // ��������� u
                    state = 14; // �������� �������� ������� ���������������� �������
                    break;
                }
                case 17: { // ���������
                    state = 15; // �������� �������� ������� ���������������� ������� (���������)
                    break;
                }
                case 18: { // ���� �� �������, ������� ����� ������������
                    state = 13; // �������� �������
                    break;
                }
                case 19: { // ���� �� �������, ������� ����� ������������ (���������)
                    if (stack.popBoolean()) {
                        state = 32; // �� ������������ ������� u
                    } else {
                        state = 18; // ���� �� �������, ������� ����� ������������
                    }
                    break;
                }
                case 20: { // �������������� j
                    state = 18; // ���� �� �������, ������� ����� ������������
                    break;
                }
                case 21: { // ���� �� ������, ����������� u
                    if (stack.popBoolean()) {
                        state = 25; // ��������� �����
                    } else {
                        state = 20; // �������������� j
                    }
                    break;
                }
                case 22: { // ���� �� ��������� ������� �� ����� �����
                    state = 21; // ���� �� ������, ����������� u
                    break;
                }
                case 23: { // ���� �� ��������� ������� �� ����� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 24; // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    } else {
                        state = 22; // ���� �� ��������� ������� �� ����� �����
                    }
                    break;
                }
                case 24: { // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    state = 22; // ���� �� ��������� ������� �� ����� �����
                    break;
                }
                case 25: { // ��������� �����
                    state = 23; // ���� �� ��������� ������� �� ����� ����� (���������)
                    break;
                }
                case 26: { // �������������� j
                    state = 21; // ���� �� ������, ����������� u
                    break;
                }
                case 27: { // ���� �� �������� ������, ����������� u
                    if (stack.popBoolean()) {
                        state = 31; // ��������� �����
                    } else {
                        state = 26; // �������������� j
                    }
                    break;
                }
                case 28: { // ���� �� ��������� ������� �� ����� �����
                    state = 27; // ���� �� �������� ������, ����������� u
                    break;
                }
                case 29: { // ���� �� ��������� ������� �� ����� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 30; // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    } else {
                        state = 28; // ���� �� ��������� ������� �� ����� �����
                    }
                    break;
                }
                case 30: { // ��������� ���������� �� ������� � ��������� ����� � ������ ��� ��������������
                    state = 28; // ���� �� ��������� ������� �� ����� �����
                    break;
                }
                case 31: { // ��������� �����
                    state = 29; // ���� �� ��������� ������� �� ����� ����� (���������)
                    break;
                }
                case 32: { // �� ������������ ������� u
                    state = 27; // ���� �� �������� ������, ����������� u
                    break;
                }
                case 33: { // ���������
                    state = 19; // ���� �� �������, ������� ����� ������������ (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 11; // ������� ���� ��������
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
      * ���������� �����������.
      */
    private final class updatePotentials extends BaseAutomata implements Automata {
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
        public updatePotentials() {
            super( 
                "updatePotentials", 
                0, // ����� ���������� ��������� 
                5, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������������� i", 
                    "���� ��� ���������� �����������", 
                    "��������� ��������� � ��������� � ��������� �������", 
                    "���������� ���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // �������������� i 
                    -1, // ���� ��� ���������� ����������� 
                    -1, // ��������� ��������� � ��������� � ��������� ������� 
                    0, // ���������� ��������� 
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
                    state = 1; // �������������� i
                    break;
                }
                case 1: { // �������������� i
                    stack.pushBoolean(false); 
                    state = 2; // ���� ��� ���������� �����������
                    break;
                }
                case 2: { // ���� ��� ���������� �����������
                    if (d.updatePotentials_i < d.n) {
                        state = 3; // ��������� ��������� � ��������� � ��������� �������
                    } else {
                        state = 4; // ���������� ���������
                    }
                    break;
                }
                case 3: { // ��������� ��������� � ��������� � ��������� �������
                    stack.pushBoolean(true); 
                    state = 2; // ���� ��� ���������� �����������
                    break;
                }
                case 4: { // ���������� ���������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������� i
                    startSection();
                    storeField(d, "updatePotentials_i");
                    d.updatePotentials_i = 0;
                    break;
                }
                case 2: { // ���� ��� ���������� �����������
                    break;
                }
                case 3: { // ��������� ��������� � ��������� � ��������� �������
                    startSection();
                    storeArray(d.phi, d.updatePotentials_i);
                    d.phi[d.updatePotentials_i] = d.dist[d.updatePotentials_i];
                    storeField(d, "updatePotentials_i");
                    d.updatePotentials_i = d.updatePotentials_i + 1;
                    break;
                }
                case 4: { // ���������� ���������
                    startSection();
                    storeField(d, "highlight");
                    d.highlight = 2;
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
                case 1: { // �������������� i
                    restoreSection();
                    break;
                }
                case 2: { // ���� ��� ���������� �����������
                    break;
                }
                case 3: { // ��������� ��������� � ��������� � ��������� �������
                    restoreSection();
                    break;
                }
                case 4: { // ���������� ���������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // �������������� i
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���� ��� ���������� �����������
                    if (stack.popBoolean()) {
                        state = 3; // ��������� ��������� � ��������� � ��������� �������
                    } else {
                        state = 1; // �������������� i
                    }
                    break;
                }
                case 3: { // ��������� ��������� � ��������� � ��������� �������
                    state = 2; // ���� ��� ���������� �����������
                    break;
                }
                case 4: { // ���������� ���������
                    state = 2; // ���� ��� ���������� �����������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 4; // ���������� ���������
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
                case 4: { // ���������� ���������
                    comment = MinCostMaxFlow.this.getComment("updatePotentials.potentialUpdated"); 
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
                case 4: { // ���������� ���������
                    d.visualizer.drawGraph();
                    break;
                }
            }
        }
    }

    /**
      * ������� ������������ ����������� ����� ��� ����������� ����.
      */
    private final class recoverPath extends BaseAutomata implements Automata {
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
        public recoverPath() {
            super( 
                "recoverPath", 
                0, // ����� ���������� ��������� 
                9, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������������� ������� ������� ������ � ������������ ����������� ����� ��������� ������", 
                    "������� ������������ ����������� �����", 
                    "�������� ����� ����", 
                    "���� �� ��������� �����", 
                    "���� �� ��������� ����� (���������)", 
                    "��������� �����", 
                    "��������� � ���������� ������� � ����", 
                    "���� ������������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // �������������� ������� ������� ������ � ������������ ����������� ����� ��������� ������ 
                    -1, // ������� ������������ ����������� ����� 
                    -1, // �������� ����� ���� 
                    -1, // ���� �� ��������� ����� 
                    -1, // ���� �� ��������� ����� (���������) 
                    -1, // ��������� ����� 
                    -1, // ��������� � ���������� ������� � ���� 
                    0, // ���� ������������ 
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
                    state = 1; // �������������� ������� ������� ������ � ������������ ����������� ����� ��������� ������
                    break;
                }
                case 1: { // �������������� ������� ������� ������ � ������������ ����������� ����� ��������� ������
                    stack.pushBoolean(false); 
                    state = 2; // ������� ������������ ����������� �����
                    break;
                }
                case 2: { // ������� ������������ ����������� �����
                    if (d.recoverPath_cur != d.source) {
                        state = 3; // �������� ����� ����
                    } else {
                        state = 8; // ���� ������������
                    }
                    break;
                }
                case 3: { // �������� ����� ����
                    state = 4; // ���� �� ��������� �����
                    break;
                }
                case 4: { // ���� �� ��������� �����
                    if (d.maxFlow > d.prev[d.recoverPath_cur].capacity - d.prev[d.recoverPath_cur].flow) {
                        state = 6; // ��������� �����
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // ���� �� ��������� ����� (���������)
                    }
                    break;
                }
                case 5: { // ���� �� ��������� ����� (���������)
                    state = 7; // ��������� � ���������� ������� � ����
                    break;
                }
                case 6: { // ��������� �����
                    stack.pushBoolean(true); 
                    state = 5; // ���� �� ��������� ����� (���������)
                    break;
                }
                case 7: { // ��������� � ���������� ������� � ����
                    stack.pushBoolean(true); 
                    state = 2; // ������� ������������ ����������� �����
                    break;
                }
                case 8: { // ���� ������������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������� ������� ������� ������ � ������������ ����������� ����� ��������� ������
                    startSection();
                    storeField(d, "recoverPath_cur");
                    d.recoverPath_cur = d.sink;
                    storeField(d, "maxFlow");
                    d.maxFlow = d.prev[d.recoverPath_cur].capacity - d.prev[d.recoverPath_cur].flow;
                    break;
                }
                case 2: { // ������� ������������ ����������� �����
                    break;
                }
                case 3: { // �������� ����� ����
                    startSection();
                    storeField(d.prev[d.recoverPath_cur], "highlighted");
                    d.prev[d.recoverPath_cur].highlighted = true;
                    break;
                }
                case 4: { // ���� �� ��������� �����
                    break;
                }
                case 5: { // ���� �� ��������� ����� (���������)
                    break;
                }
                case 6: { // ��������� �����
                    startSection();
                    storeField(d, "maxFlow");
                    d.maxFlow = d.prev[d.recoverPath_cur].capacity - d.prev[d.recoverPath_cur].flow;
                    break;
                }
                case 7: { // ��������� � ���������� ������� � ����
                    startSection();
                    storeField(d, "recoverPath_cur");
                    d.recoverPath_cur = d.prev[d.recoverPath_cur].u;
                    break;
                }
                case 8: { // ���� ������������
                    startSection();
                    storeField(d, "highlight");
                    d.highlight = 0;
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
                case 1: { // �������������� ������� ������� ������ � ������������ ����������� ����� ��������� ������
                    restoreSection();
                    break;
                }
                case 2: { // ������� ������������ ����������� �����
                    break;
                }
                case 3: { // �������� ����� ����
                    restoreSection();
                    break;
                }
                case 4: { // ���� �� ��������� �����
                    break;
                }
                case 5: { // ���� �� ��������� ����� (���������)
                    break;
                }
                case 6: { // ��������� �����
                    restoreSection();
                    break;
                }
                case 7: { // ��������� � ���������� ������� � ����
                    restoreSection();
                    break;
                }
                case 8: { // ���� ������������
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // �������������� ������� ������� ������ � ������������ ����������� ����� ��������� ������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ������� ������������ ����������� �����
                    if (stack.popBoolean()) {
                        state = 7; // ��������� � ���������� ������� � ����
                    } else {
                        state = 1; // �������������� ������� ������� ������ � ������������ ����������� ����� ��������� ������
                    }
                    break;
                }
                case 3: { // �������� ����� ����
                    state = 2; // ������� ������������ ����������� �����
                    break;
                }
                case 4: { // ���� �� ��������� �����
                    state = 3; // �������� ����� ����
                    break;
                }
                case 5: { // ���� �� ��������� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 6; // ��������� �����
                    } else {
                        state = 4; // ���� �� ��������� �����
                    }
                    break;
                }
                case 6: { // ��������� �����
                    state = 4; // ���� �� ��������� �����
                    break;
                }
                case 7: { // ��������� � ���������� ������� � ����
                    state = 5; // ���� �� ��������� ����� (���������)
                    break;
                }
                case 8: { // ���� ������������
                    state = 2; // ������� ������������ ����������� �����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 8; // ���� ������������
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
                case 8: { // ���� ������������
                    comment = MinCostMaxFlow.this.getComment("recoverPath.pathRecovered"); 
                    args = new Object[]{new Long(d.maxFlow)}; 
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
                case 8: { // ���� ������������
                    d.visualizer.drawGraph();
                    break;
                }
            }
        }
    }

    /**
      * ���������� ����� � ��������� ���������.
      */
    private final class letPassFlow extends BaseAutomata implements Automata {
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
        public letPassFlow() {
            super( 
                "letPassFlow", 
                0, // ����� ���������� ��������� 
                9, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������������� ��������� ������� ������ � ������� ��������� �����", 
                    "���� ��� ���������� ������ � ���������", 
                    "�������� �� ��� �����", 
                    "�������� �� ��� ����� (���������)", 
                    "��������� �����, �������� ��������", 
                    "��������� �����, �������� ��������", 
                    "��������� ����� � ���������", 
                    "���������� �����", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // �������������� ��������� ������� ������ � ������� ��������� ����� 
                    -1, // ���� ��� ���������� ������ � ��������� 
                    -1, // �������� �� ��� ����� 
                    -1, // �������� �� ��� ����� (���������) 
                    -1, // ��������� �����, �������� �������� 
                    -1, // ��������� �����, �������� �������� 
                    -1, // ��������� ����� � ��������� 
                    1, // ���������� ����� 
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
                    state = 1; // �������������� ��������� ������� ������ � ������� ��������� �����
                    break;
                }
                case 1: { // �������������� ��������� ������� ������ � ������� ��������� �����
                    stack.pushBoolean(false); 
                    state = 2; // ���� ��� ���������� ������ � ���������
                    break;
                }
                case 2: { // ���� ��� ���������� ������ � ���������
                    if (d.letPassFlow_cur != d.source) {
                        state = 3; // �������� �� ��� �����
                    } else {
                        state = 8; // ���������� �����
                    }
                    break;
                }
                case 3: { // �������� �� ��� �����
                    if (d.prev[d.letPassFlow_cur].reversed) {
                        state = 5; // ��������� �����, �������� ��������
                    } else {
                        state = 6; // ��������� �����, �������� ��������
                    }
                    break;
                }
                case 4: { // �������� �� ��� ����� (���������)
                    state = 7; // ��������� ����� � ���������
                    break;
                }
                case 5: { // ��������� �����, �������� ��������
                    stack.pushBoolean(true); 
                    state = 4; // �������� �� ��� ����� (���������)
                    break;
                }
                case 6: { // ��������� �����, �������� ��������
                    stack.pushBoolean(false); 
                    state = 4; // �������� �� ��� ����� (���������)
                    break;
                }
                case 7: { // ��������� ����� � ���������
                    stack.pushBoolean(true); 
                    state = 2; // ���� ��� ���������� ������ � ���������
                    break;
                }
                case 8: { // ���������� �����
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������� ��������� ������� ������ � ������� ��������� �����
                    startSection();
                    storeField(d, "letPassFlow_cur");
                    d.letPassFlow_cur = d.sink;
                    storeField(d, "letPassFlow_curCost");
                    d.letPassFlow_curCost = 0;
                    break;
                }
                case 2: { // ���� ��� ���������� ������ � ���������
                    break;
                }
                case 3: { // �������� �� ��� �����
                    break;
                }
                case 4: { // �������� �� ��� ����� (���������)
                    break;
                }
                case 5: { // ��������� �����, �������� ��������
                    startSection();
                    storeField(d.edge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u], "flow");
                    d.edge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u].flow = d.edge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u].flow - d.maxFlow; 
                    break;
                }
                case 6: { // ��������� �����, �������� ��������
                    startSection();
                    storeField(d.reverseEdge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u], "flow");
                    d.reverseEdge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u].flow = d.reverseEdge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u].flow - d.maxFlow; 
                    break;
                }
                case 7: { // ��������� ����� � ���������
                    startSection();
                    storeField(d.prev[d.letPassFlow_cur], "flow");
                    d.prev[d.letPassFlow_cur].flow = d.prev[d.letPassFlow_cur].flow + d.maxFlow;
                    storeField(d, "cost");
                    d.cost = d.cost + d.maxFlow * d.prev[d.letPassFlow_cur].cost;
                    storeField(d, "letPassFlow_curCost");
                    d.letPassFlow_curCost = d.letPassFlow_curCost + d.maxFlow * d.prev[d.letPassFlow_cur].cost;
                    storeField(d, "letPassFlow_cur");
                    d.letPassFlow_cur = d.prev[d.letPassFlow_cur].u;
                    break;
                }
                case 8: { // ���������� �����
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
                case 1: { // �������������� ��������� ������� ������ � ������� ��������� �����
                    restoreSection();
                    break;
                }
                case 2: { // ���� ��� ���������� ������ � ���������
                    break;
                }
                case 3: { // �������� �� ��� �����
                    break;
                }
                case 4: { // �������� �� ��� ����� (���������)
                    break;
                }
                case 5: { // ��������� �����, �������� ��������
                    restoreSection();
                    break;
                }
                case 6: { // ��������� �����, �������� ��������
                    restoreSection();
                    break;
                }
                case 7: { // ��������� ����� � ���������
                    restoreSection();
                    break;
                }
                case 8: { // ���������� �����
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // �������������� ��������� ������� ������ � ������� ��������� �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���� ��� ���������� ������ � ���������
                    if (stack.popBoolean()) {
                        state = 7; // ��������� ����� � ���������
                    } else {
                        state = 1; // �������������� ��������� ������� ������ � ������� ��������� �����
                    }
                    break;
                }
                case 3: { // �������� �� ��� �����
                    state = 2; // ���� ��� ���������� ������ � ���������
                    break;
                }
                case 4: { // �������� �� ��� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 5; // ��������� �����, �������� ��������
                    } else {
                        state = 6; // ��������� �����, �������� ��������
                    }
                    break;
                }
                case 5: { // ��������� �����, �������� ��������
                    state = 3; // �������� �� ��� �����
                    break;
                }
                case 6: { // ��������� �����, �������� ��������
                    state = 3; // �������� �� ��� �����
                    break;
                }
                case 7: { // ��������� ����� � ���������
                    state = 4; // �������� �� ��� ����� (���������)
                    break;
                }
                case 8: { // ���������� �����
                    state = 2; // ���� ��� ���������� ������ � ���������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 8; // ���������� �����
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
                case 8: { // ���������� �����
                    comment = MinCostMaxFlow.this.getComment("letPassFlow.flowPassed"); 
                    args = new Object[]{new Long(d.letPassFlow_curCost)}; 
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
                case 8: { // ���������� �����
                    d.visualizer.drawGraph();
                    break;
                }
            }
        }
    }

    /**
      * ������� ������������ ����� ����������� ���������.
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
                    "�������������", 
                    "��������� ���������� �� ��������� �� ������ (�������)", 
                    "������� ����", 
                    "��������� ����", 
                    "���������� ����������� (�������)", 
                    "������� ������������ ����������� ����� ��� ����������� ���� (�������)", 
                    "���������� ����� � ��������� ��������� (�������)", 
                    "��������� ���������� �� ��������� �� ������ (�������)", 
                    "���� �� ����� �����������", 
                    "������� ����������� ���������", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    0, // ������������� 
                    CALL_AUTO_LEVEL, // ��������� ���������� �� ��������� �� ������ (�������) 
                    -1, // ������� ���� 
                    0, // ��������� ���� 
                    CALL_AUTO_LEVEL, // ���������� ����������� (�������) 
                    CALL_AUTO_LEVEL, // ������� ������������ ����������� ����� ��� ����������� ���� (�������) 
                    CALL_AUTO_LEVEL, // ���������� ����� � ��������� ��������� (�������) 
                    CALL_AUTO_LEVEL, // ��������� ���������� �� ��������� �� ������ (�������) 
                    0, // ���� �� ����� ����������� 
                    -1, // ������� ����������� ��������� 
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
                    state = 2; // ��������� ���������� �� ��������� �� ������ (�������)
                    break;
                }
                case 2: { // ��������� ���������� �� ��������� �� ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 3; // ������� ����
                    }
                    break;
                }
                case 3: { // ������� ����
                    if (d.dist[d.sink] != -1) {
                        state = 4; // ��������� ����
                    } else {
                        state = 9; // ���� �� ����� �����������
                    }
                    break;
                }
                case 4: { // ��������� ����
                    state = 5; // ���������� ����������� (�������)
                    break;
                }
                case 5: { // ���������� ����������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 6; // ������� ������������ ����������� ����� ��� ����������� ���� (�������)
                    }
                    break;
                }
                case 6: { // ������� ������������ ����������� ����� ��� ����������� ���� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 7; // ���������� ����� � ��������� ��������� (�������)
                    }
                    break;
                }
                case 7: { // ���������� ����� � ��������� ��������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // ��������� ���������� �� ��������� �� ������ (�������)
                    }
                    break;
                }
                case 8: { // ��������� ���������� �� ��������� �� ������ (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 3; // ������� ����
                    }
                    break;
                }
                case 9: { // ���� �� ����� �����������
                    state = 10; // ������� ����������� ���������
                    break;
                }
                case 10: { // ������� ����������� ���������
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������������
                    startSection();
                    storeField(d, "extendedDraw");
                    d.extendedDraw = true;
                    break;
                }
                case 2: { // ��������� ���������� �� ��������� �� ������ (�������)
                    if (child == null) {
                        child = new dijkstra(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 3: { // ������� ����
                    break;
                }
                case 4: { // ��������� ����
                    startSection();
                    storeField(d, "highlight");
                    d.highlight = 1;
                    break;
                }
                case 5: { // ���������� ����������� (�������)
                    if (child == null) {
                        child = new updatePotentials(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 6: { // ������� ������������ ����������� ����� ��� ����������� ���� (�������)
                    if (child == null) {
                        child = new recoverPath(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 7: { // ���������� ����� � ��������� ��������� (�������)
                    if (child == null) {
                        child = new letPassFlow(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // ��������� ���������� �� ��������� �� ������ (�������)
                    if (child == null) {
                        child = new dijkstra(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 9: { // ���� �� ����� �����������
                    startSection();
                    storeField(d, "highlight");
                    d.highlight = 1;
                    break;
                }
                case 10: { // ������� ����������� ���������
                    startSection();
                    storeField(d, "extendedDraw");
                    d.extendedDraw = false;
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
                case 2: { // ��������� ���������� �� ��������� �� ������ (�������)
                    if (child == null) {
                        child = new dijkstra(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 3: { // ������� ����
                    break;
                }
                case 4: { // ��������� ����
                    restoreSection();
                    break;
                }
                case 5: { // ���������� ����������� (�������)
                    if (child == null) {
                        child = new updatePotentials(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 6: { // ������� ������������ ����������� ����� ��� ����������� ���� (�������)
                    if (child == null) {
                        child = new recoverPath(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 7: { // ���������� ����� � ��������� ��������� (�������)
                    if (child == null) {
                        child = new letPassFlow(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // ��������� ���������� �� ��������� �� ������ (�������)
                    if (child == null) {
                        child = new dijkstra(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 9: { // ���� �� ����� �����������
                    restoreSection();
                    break;
                }
                case 10: { // ������� ����������� ���������
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
                case 2: { // ��������� ���������� �� ��������� �� ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // �������������
                    }
                    break;
                }
                case 3: { // ������� ����
                    if (stack.popBoolean()) {
                        state = 8; // ��������� ���������� �� ��������� �� ������ (�������)
                    } else {
                        state = 2; // ��������� ���������� �� ��������� �� ������ (�������)
                    }
                    break;
                }
                case 4: { // ��������� ����
                    state = 3; // ������� ����
                    break;
                }
                case 5: { // ���������� ����������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 4; // ��������� ����
                    }
                    break;
                }
                case 6: { // ������� ������������ ����������� ����� ��� ����������� ���� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 5; // ���������� ����������� (�������)
                    }
                    break;
                }
                case 7: { // ���������� ����� � ��������� ��������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // ������� ������������ ����������� ����� ��� ����������� ���� (�������)
                    }
                    break;
                }
                case 8: { // ��������� ���������� �� ��������� �� ������ (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 7; // ���������� ����� � ��������� ��������� (�������)
                    }
                    break;
                }
                case 9: { // ���� �� ����� �����������
                    state = 3; // ������� ����
                    break;
                }
                case 10: { // ������� ����������� ���������
                    state = 9; // ���� �� ����� �����������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 10; // ������� ����������� ���������
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
                    comment = MinCostMaxFlow.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // �������������
                    comment = MinCostMaxFlow.this.getComment("Main.Initialization"); 
                    break;
                }
                case 2: { // ��������� ���������� �� ��������� �� ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 4: { // ��������� ����
                    comment = MinCostMaxFlow.this.getComment("Main.pathFinded"); 
                    break;
                }
                case 5: { // ���������� ����������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 6: { // ������� ������������ ����������� ����� ��� ����������� ���� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // ���������� ����� � ��������� ��������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 8: { // ��������� ���������� �� ��������� �� ������ (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 9: { // ���� �� ����� �����������
                    comment = MinCostMaxFlow.this.getComment("Main.pathNotFinded"); 
                    break;
                }
                case END_STATE: { // �������� ���������
                    comment = MinCostMaxFlow.this.getComment("Main.END_STATE"); 
                    args = new Object[]{new Long(d.cost)}; 
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
                    d.visualizer.drawGraph();
                    break;
                }
                case 1: { // �������������
                    d.visualizer.drawGraph();
                    break;
                }
                case 2: { // ��������� ���������� �� ��������� �� ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 4: { // ��������� ����
                    d.visualizer.drawGraph();
                    break;
                }
                case 5: { // ���������� ����������� (�������)
                    child.drawState(); 
                    break;
                }
                case 6: { // ������� ������������ ����������� ����� ��� ����������� ���� (�������)
                    child.drawState(); 
                    break;
                }
                case 7: { // ���������� ����� � ��������� ��������� (�������)
                    child.drawState(); 
                    break;
                }
                case 8: { // ��������� ���������� �� ��������� �� ������ (�������)
                    child.drawState(); 
                    break;
                }
                case 9: { // ���� �� ����� �����������
                    d.visualizer.drawGraph();
                    break;
                }
                case END_STATE: { // �������� ���������
                    d.visualizer.drawGraph();
                    break;
                }
            }
        }
    }
}
