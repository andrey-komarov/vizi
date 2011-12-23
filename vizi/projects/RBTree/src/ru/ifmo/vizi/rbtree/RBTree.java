package ru.ifmo.vizi.rbtree;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class RBTree extends BaseAutoReverseAutomata {
    /**
      * ������ ������.
      */
    public final Data d = new Data();

    /**
      * ����������� ��� �����
      */
    public RBTree(Locale locale) {
        super("ru.ifmo.vizi.rbtree.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * ������.
      */
    public final class Data {
        /**
          * ��������� �������.
          */
        public RBTreeVisualizer visualizer = null;

        /**
          * ������ ������.
          */
        public Point root = null;

        /**
          * ���� ��� ������ �� �����.
          */
        public boolean flag = true;

        /**
          * ������� ����.
          */
        public Point x = null;

        /**
          * ���� ��� ��������� ��������.
          */
        public Point z = null;

        /**
          * ����������.
          */
        public int key = 0;

        /**
          *  (��������� LeftRotate).
          */
        public Point LeftRotate_y;

        /**
          *  (��������� RightRotate).
          */
        public Point RightRotate_y;

        /**
          *  (��������� RBInsert).
          */
        public Point RBInsert_y;

        /**
          *  (��������� RBInsert).
          */
        public Point RBInsert_x2;

        /**
          * ������� ���� (��������� RBInsertFixup).
          */
        public Point RBInsertFixup_y;

        /**
          * ������� ���� (��������� RBDelete).
          */
        public Point RBDelete_x2;

        /**
          * ������� ���� (��������� RBDelete).
          */
        public Point RBDelete_y;

        /**
          * ��������������� ������� (��������� RBDeleteFixup).
          */
        public Point RBDeleteFixup_w;

        public String toString() {
            	return "";
        }
    }

    /**
      * ����� �������.
      */
    private final class LeftRotate extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 14;

        /**
          * �����������.
          */
        public LeftRotate() {
            super( 
                "LeftRotate", 
                0, // ����� ���������� ��������� 
                14, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ������ ������", 
                    "���� ��� ������ ���������", 
                    "���� ��� ������ ��������� (���������)", 
                    "�������� ������ � ������ ����", 
                    "��������� �������� z � y", 
                    "���� �������� z == null", 
                    "���� �������� z == null (���������)", 
                    "XZ", 
                    "���� x ����� ���", 
                    "���� x ����� ��� (���������)", 
                    "XZ", 
                    "XZ", 
                    "��������� �������� z � y", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ������ ������ 
                    -1, // ���� ��� ������ ��������� 
                    -1, // ���� ��� ������ ��������� (���������) 
                    -1, // �������� ������ � ������ ���� 
                    -1, // ��������� �������� z � y 
                    -1, // ���� �������� z == null 
                    -1, // ���� �������� z == null (���������) 
                    -1, // XZ 
                    -1, // ���� x ����� ��� 
                    -1, // ���� x ����� ��� (���������) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ��������� �������� z � y 
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
                    state = 1; // ������������� ������ ������
                    break;
                }
                case 1: { // ������������� ������ ������
                    state = 2; // ���� ��� ������ ���������
                    break;
                }
                case 2: { // ���� ��� ������ ���������
                    if (d.LeftRotate_y.left != null) {
                        state = 4; // �������� ������ � ������ ����
                    } else {
                        stack.pushBoolean(false); 
                        state = 3; // ���� ��� ������ ��������� (���������)
                    }
                    break;
                }
                case 3: { // ���� ��� ������ ��������� (���������)
                    state = 5; // ��������� �������� z � y
                    break;
                }
                case 4: { // �������� ������ � ������ ����
                    stack.pushBoolean(true); 
                    state = 3; // ���� ��� ������ ��������� (���������)
                    break;
                }
                case 5: { // ��������� �������� z � y
                    state = 6; // ���� �������� z == null
                    break;
                }
                case 6: { // ���� �������� z == null
                    if (d.z.p == null) {
                        state = 8; // XZ
                    } else {
                        state = 9; // ���� x ����� ���
                    }
                    break;
                }
                case 7: { // ���� �������� z == null (���������)
                    state = 13; // ��������� �������� z � y
                    break;
                }
                case 8: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // ���� �������� z == null (���������)
                    break;
                }
                case 9: { // ���� x ����� ���
                    if (d.z == d.z.p.left) {
                        state = 11; // XZ
                    } else {
                        state = 12; // XZ
                    }
                    break;
                }
                case 10: { // ���� x ����� ��� (���������)
                    stack.pushBoolean(false); 
                    state = 7; // ���� �������� z == null (���������)
                    break;
                }
                case 11: { // XZ
                    stack.pushBoolean(true); 
                    state = 10; // ���� x ����� ��� (���������)
                    break;
                }
                case 12: { // XZ
                    stack.pushBoolean(false); 
                    state = 10; // ���� x ����� ��� (���������)
                    break;
                }
                case 13: { // ��������� �������� z � y
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� ������ ������
                    startSection();
                    storeField(d, "LeftRotate_y");
                    				d.LeftRotate_y = d.z.right;
                    storeField(d.z, "right");
                    				d.z.right = d.LeftRotate_y.left;
                    break;
                }
                case 2: { // ���� ��� ������ ���������
                    break;
                }
                case 3: { // ���� ��� ������ ��������� (���������)
                    break;
                }
                case 4: { // �������� ������ � ������ ����
                    startSection();
                    storeField(d.LeftRotate_y.left, "p");
                    						d.LeftRotate_y.left.p = d.z;
                    break;
                }
                case 5: { // ��������� �������� z � y
                    startSection();
                    				d.LeftRotate_y.p = d.z.p;
                    break;
                }
                case 6: { // ���� �������� z == null
                    break;
                }
                case 7: { // ���� �������� z == null (���������)
                    break;
                }
                case 8: { // XZ
                    startSection();
                    storeField(d, "root");
                    					d.root = d.LeftRotate_y;
                    break;
                }
                case 9: { // ���� x ����� ���
                    break;
                }
                case 10: { // ���� x ����� ��� (���������)
                    break;
                }
                case 11: { // XZ
                    startSection();
                    storeField(d.z.p, "left");
                    						d.z.p.left = d.LeftRotate_y;
                    break;
                }
                case 12: { // XZ
                    startSection();
                    storeField(d.z.p, "right");
                    						d.z.p.right = d.LeftRotate_y;
                    break;
                }
                case 13: { // ��������� �������� z � y
                    startSection();
                    storeField(d.LeftRotate_y, "left");
                    				d.LeftRotate_y.left = d.z;
                    storeField(d.z, "p");
                    				d.z.p = d.LeftRotate_y;
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
                case 1: { // ������������� ������ ������
                    restoreSection();
                    break;
                }
                case 2: { // ���� ��� ������ ���������
                    break;
                }
                case 3: { // ���� ��� ������ ��������� (���������)
                    break;
                }
                case 4: { // �������� ������ � ������ ����
                    restoreSection();
                    break;
                }
                case 5: { // ��������� �������� z � y
                    restoreSection();
                    break;
                }
                case 6: { // ���� �������� z == null
                    break;
                }
                case 7: { // ���� �������� z == null (���������)
                    break;
                }
                case 8: { // XZ
                    restoreSection();
                    break;
                }
                case 9: { // ���� x ����� ���
                    break;
                }
                case 10: { // ���� x ����� ��� (���������)
                    break;
                }
                case 11: { // XZ
                    restoreSection();
                    break;
                }
                case 12: { // XZ
                    restoreSection();
                    break;
                }
                case 13: { // ��������� �������� z � y
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� ������ ������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���� ��� ������ ���������
                    state = 1; // ������������� ������ ������
                    break;
                }
                case 3: { // ���� ��� ������ ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 4; // �������� ������ � ������ ����
                    } else {
                        state = 2; // ���� ��� ������ ���������
                    }
                    break;
                }
                case 4: { // �������� ������ � ������ ����
                    state = 2; // ���� ��� ������ ���������
                    break;
                }
                case 5: { // ��������� �������� z � y
                    state = 3; // ���� ��� ������ ��������� (���������)
                    break;
                }
                case 6: { // ���� �������� z == null
                    state = 5; // ��������� �������� z � y
                    break;
                }
                case 7: { // ���� �������� z == null (���������)
                    if (stack.popBoolean()) {
                        state = 8; // XZ
                    } else {
                        state = 10; // ���� x ����� ��� (���������)
                    }
                    break;
                }
                case 8: { // XZ
                    state = 6; // ���� �������� z == null
                    break;
                }
                case 9: { // ���� x ����� ���
                    state = 6; // ���� �������� z == null
                    break;
                }
                case 10: { // ���� x ����� ��� (���������)
                    if (stack.popBoolean()) {
                        state = 11; // XZ
                    } else {
                        state = 12; // XZ
                    }
                    break;
                }
                case 11: { // XZ
                    state = 9; // ���� x ����� ���
                    break;
                }
                case 12: { // XZ
                    state = 9; // ���� x ����� ���
                    break;
                }
                case 13: { // ��������� �������� z � y
                    state = 7; // ���� �������� z == null (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 13; // ��������� �������� z � y
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
      * ������ �������.
      */
    private final class RightRotate extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 14;

        /**
          * �����������.
          */
        public RightRotate() {
            super( 
                "RightRotate", 
                0, // ����� ���������� ��������� 
                14, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ������ ������", 
                    "���� ��� ������� ���������", 
                    "���� ��� ������� ��������� (���������)", 
                    "Step", 
                    "��������� �������� z � y", 
                    "���� �������� z == null", 
                    "���� �������� z == null (���������)", 
                    "XZ", 
                    "���� x ������ ���", 
                    "���� x ������ ��� (���������)", 
                    "XZ", 
                    "XZ", 
                    "��������� �������� z � y", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ������ ������ 
                    -1, // ���� ��� ������� ��������� 
                    -1, // ���� ��� ������� ��������� (���������) 
                    -1, // Step 
                    -1, // ��������� �������� z � y 
                    -1, // ���� �������� z == null 
                    -1, // ���� �������� z == null (���������) 
                    -1, // XZ 
                    -1, // ���� x ������ ��� 
                    -1, // ���� x ������ ��� (���������) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ��������� �������� z � y 
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
                    state = 1; // ������������� ������ ������
                    break;
                }
                case 1: { // ������������� ������ ������
                    state = 2; // ���� ��� ������� ���������
                    break;
                }
                case 2: { // ���� ��� ������� ���������
                    if (d.RightRotate_y.right != null) {
                        state = 4; // Step
                    } else {
                        stack.pushBoolean(false); 
                        state = 3; // ���� ��� ������� ��������� (���������)
                    }
                    break;
                }
                case 3: { // ���� ��� ������� ��������� (���������)
                    state = 5; // ��������� �������� z � y
                    break;
                }
                case 4: { // Step
                    stack.pushBoolean(true); 
                    state = 3; // ���� ��� ������� ��������� (���������)
                    break;
                }
                case 5: { // ��������� �������� z � y
                    state = 6; // ���� �������� z == null
                    break;
                }
                case 6: { // ���� �������� z == null
                    if (d.z.p == null) {
                        state = 8; // XZ
                    } else {
                        state = 9; // ���� x ������ ���
                    }
                    break;
                }
                case 7: { // ���� �������� z == null (���������)
                    state = 13; // ��������� �������� z � y
                    break;
                }
                case 8: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // ���� �������� z == null (���������)
                    break;
                }
                case 9: { // ���� x ������ ���
                    if (d.z == d.z.p.right) {
                        state = 11; // XZ
                    } else {
                        state = 12; // XZ
                    }
                    break;
                }
                case 10: { // ���� x ������ ��� (���������)
                    stack.pushBoolean(false); 
                    state = 7; // ���� �������� z == null (���������)
                    break;
                }
                case 11: { // XZ
                    stack.pushBoolean(true); 
                    state = 10; // ���� x ������ ��� (���������)
                    break;
                }
                case 12: { // XZ
                    stack.pushBoolean(false); 
                    state = 10; // ���� x ������ ��� (���������)
                    break;
                }
                case 13: { // ��������� �������� z � y
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� ������ ������
                    startSection();
                    storeField(d, "RightRotate_y");
                    				d.RightRotate_y = d.z.left;
                    storeField(d.z, "left");
                    				d.z.left = d.RightRotate_y.right;
                    break;
                }
                case 2: { // ���� ��� ������� ���������
                    break;
                }
                case 3: { // ���� ��� ������� ��������� (���������)
                    break;
                }
                case 4: { // Step
                    startSection();
                    storeField(d.RightRotate_y.right, "p");
                    						d.RightRotate_y.right.p = d.z;
                    break;
                }
                case 5: { // ��������� �������� z � y
                    startSection();
                    				d.RightRotate_y.p = d.z.p;
                    break;
                }
                case 6: { // ���� �������� z == null
                    break;
                }
                case 7: { // ���� �������� z == null (���������)
                    break;
                }
                case 8: { // XZ
                    startSection();
                    storeField(d, "root");
                    					d.root = d.RightRotate_y;
                    break;
                }
                case 9: { // ���� x ������ ���
                    break;
                }
                case 10: { // ���� x ������ ��� (���������)
                    break;
                }
                case 11: { // XZ
                    startSection();
                    storeField(d.z.p, "right");
                    						d.z.p.right = d.RightRotate_y;
                    break;
                }
                case 12: { // XZ
                    startSection();
                    storeField(d.z.p, "left");
                    						d.z.p.left = d.RightRotate_y;
                    break;
                }
                case 13: { // ��������� �������� z � y
                    startSection();
                    storeField(d.RightRotate_y, "right");
                    				d.RightRotate_y.right = d.z;
                    storeField(d.z, "p");
                    				d.z.p = d.RightRotate_y;
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
                case 1: { // ������������� ������ ������
                    restoreSection();
                    break;
                }
                case 2: { // ���� ��� ������� ���������
                    break;
                }
                case 3: { // ���� ��� ������� ��������� (���������)
                    break;
                }
                case 4: { // Step
                    restoreSection();
                    break;
                }
                case 5: { // ��������� �������� z � y
                    restoreSection();
                    break;
                }
                case 6: { // ���� �������� z == null
                    break;
                }
                case 7: { // ���� �������� z == null (���������)
                    break;
                }
                case 8: { // XZ
                    restoreSection();
                    break;
                }
                case 9: { // ���� x ������ ���
                    break;
                }
                case 10: { // ���� x ������ ��� (���������)
                    break;
                }
                case 11: { // XZ
                    restoreSection();
                    break;
                }
                case 12: { // XZ
                    restoreSection();
                    break;
                }
                case 13: { // ��������� �������� z � y
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� ������ ������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���� ��� ������� ���������
                    state = 1; // ������������� ������ ������
                    break;
                }
                case 3: { // ���� ��� ������� ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 4; // Step
                    } else {
                        state = 2; // ���� ��� ������� ���������
                    }
                    break;
                }
                case 4: { // Step
                    state = 2; // ���� ��� ������� ���������
                    break;
                }
                case 5: { // ��������� �������� z � y
                    state = 3; // ���� ��� ������� ��������� (���������)
                    break;
                }
                case 6: { // ���� �������� z == null
                    state = 5; // ��������� �������� z � y
                    break;
                }
                case 7: { // ���� �������� z == null (���������)
                    if (stack.popBoolean()) {
                        state = 8; // XZ
                    } else {
                        state = 10; // ���� x ������ ��� (���������)
                    }
                    break;
                }
                case 8: { // XZ
                    state = 6; // ���� �������� z == null
                    break;
                }
                case 9: { // ���� x ������ ���
                    state = 6; // ���� �������� z == null
                    break;
                }
                case 10: { // ���� x ������ ��� (���������)
                    if (stack.popBoolean()) {
                        state = 11; // XZ
                    } else {
                        state = 12; // XZ
                    }
                    break;
                }
                case 11: { // XZ
                    state = 9; // ���� x ������ ���
                    break;
                }
                case 12: { // XZ
                    state = 9; // ���� x ������ ���
                    break;
                }
                case 13: { // ��������� �������� z � y
                    state = 7; // ���� �������� z == null (���������)
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 13; // ��������� �������� z � y
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
      * �����.
      */
    private final class findPoint extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 13;

        /**
          * �����������.
          */
        public findPoint() {
            super( 
                "findPoint", 
                0, // ����� ���������� ��������� 
                13, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "�������� ������ � �����", 
                    "����", 
                    "���� �����", 
                    "���� ����� (���������)", 
                    "XZ", 
                    "���� �����", 
                    "���� ����� (���������)", 
                    "XZ", 
                    "XZ", 
                    "���� �����", 
                    "���� ����� (���������)", 
                    "XZ", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // �������� ������ � ����� 
                    -1, // ���� 
                    0, // ���� ����� 
                    -1, // ���� ����� (���������) 
                    -1, // XZ 
                    0, // ���� ����� 
                    -1, // ���� ����� (���������) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ���� ����� 
                    -1, // ���� ����� (���������) 
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
                    state = 1; // �������� ������ � �����
                    break;
                }
                case 1: { // �������� ������ � �����
                    stack.pushBoolean(false); 
                    state = 2; // ����
                    break;
                }
                case 2: { // ����
                    if (d.x != null && flag) {
                        state = 3; // ���� �����
                    } else {
                        state = 10; // ���� �����
                    }
                    break;
                }
                case 3: { // ���� �����
                    if (d.x.key == d.key) {
                        state = 5; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 4; // ���� ����� (���������)
                    }
                    break;
                }
                case 4: { // ���� ����� (���������)
                    state = 6; // ���� �����
                    break;
                }
                case 5: { // XZ
                    stack.pushBoolean(true); 
                    state = 4; // ���� ����� (���������)
                    break;
                }
                case 6: { // ���� �����
                    if (d.x.key > d.key) {
                        state = 8; // XZ
                    } else {
                        state = 9; // XZ
                    }
                    break;
                }
                case 7: { // ���� ����� (���������)
                    stack.pushBoolean(true); 
                    state = 2; // ����
                    break;
                }
                case 8: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // ���� ����� (���������)
                    break;
                }
                case 9: { // XZ
                    stack.pushBoolean(false); 
                    state = 7; // ���� ����� (���������)
                    break;
                }
                case 10: { // ���� �����
                    if (flag) {
                        state = 12; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 11; // ���� ����� (���������)
                    }
                    break;
                }
                case 11: { // ���� ����� (���������)
                    state = END_STATE; 
                    break;
                }
                case 12: { // XZ
                    stack.pushBoolean(true); 
                    state = 11; // ���� ����� (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // �������� ������ � �����
                    startSection();
                    storeField(d, "x");
                    				d.x = d.root;
                    				d.flag = true;
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ���� �����
                    break;
                }
                case 4: { // ���� ����� (���������)
                    break;
                }
                case 5: { // XZ
                    startSection();
                    					flag = false;
                    break;
                }
                case 6: { // ���� �����
                    break;
                }
                case 7: { // ���� ����� (���������)
                    break;
                }
                case 8: { // XZ
                    startSection();
                    storeField(d, "x");
                    					d.x = d.x.left;
                    break;
                }
                case 9: { // XZ
                    startSection();
                    storeField(d, "x");
                    					d.x = d.x.right;
                    break;
                }
                case 10: { // ���� �����
                    break;
                }
                case 11: { // ���� ����� (���������)
                    break;
                }
                case 12: { // XZ
                    startSection();
                    				d.flag = true;
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
                case 1: { // �������� ������ � �����
                    restoreSection();
                    break;
                }
                case 2: { // ����
                    break;
                }
                case 3: { // ���� �����
                    break;
                }
                case 4: { // ���� ����� (���������)
                    break;
                }
                case 5: { // XZ
                    restoreSection();
                    break;
                }
                case 6: { // ���� �����
                    break;
                }
                case 7: { // ���� ����� (���������)
                    break;
                }
                case 8: { // XZ
                    restoreSection();
                    break;
                }
                case 9: { // XZ
                    restoreSection();
                    break;
                }
                case 10: { // ���� �����
                    break;
                }
                case 11: { // ���� ����� (���������)
                    break;
                }
                case 12: { // XZ
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // �������� ������ � �����
                    state = START_STATE; 
                    break;
                }
                case 2: { // ����
                    if (stack.popBoolean()) {
                        state = 7; // ���� ����� (���������)
                    } else {
                        state = 1; // �������� ������ � �����
                    }
                    break;
                }
                case 3: { // ���� �����
                    state = 2; // ����
                    break;
                }
                case 4: { // ���� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 5; // XZ
                    } else {
                        state = 3; // ���� �����
                    }
                    break;
                }
                case 5: { // XZ
                    state = 3; // ���� �����
                    break;
                }
                case 6: { // ���� �����
                    state = 4; // ���� ����� (���������)
                    break;
                }
                case 7: { // ���� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 8; // XZ
                    } else {
                        state = 9; // XZ
                    }
                    break;
                }
                case 8: { // XZ
                    state = 6; // ���� �����
                    break;
                }
                case 9: { // XZ
                    state = 6; // ���� �����
                    break;
                }
                case 10: { // ���� �����
                    state = 2; // ����
                    break;
                }
                case 11: { // ���� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 12; // XZ
                    } else {
                        state = 10; // ���� �����
                    }
                    break;
                }
                case 12: { // XZ
                    state = 10; // ���� �����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 11; // ���� ����� (���������)
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
                case 3: { // ���� �����
                    if (d.x.key == d.key) {
                        comment = RBTree.this.getComment("findPoint.If.true"); 
                    } else {
                        comment = RBTree.this.getComment("findPoint.If.false"); 
                    }
                    args = new Object[]{new Integer(d.key)}; 
                    break;
                }
                case 6: { // ���� �����
                    if (d.x.key > d.key) {
                        comment = RBTree.this.getComment("findPoint.If.true"); 
                    } else {
                        comment = RBTree.this.getComment("findPoint.If.false"); 
                    }
                    args = new Object[]{new Integer(d.x.key), new Integer(d.key)}; 
                    break;
                }
                case 12: { // XZ
                    comment = RBTree.this.getComment("findPoint.StepInIf"); 
                    args = new Object[]{new Integer(d.key)}; 
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
            }
        }
    }

    /**
      * �������.
      */
    private final class RBInsert extends BaseAutomata implements Automata {
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
        public RBInsert() {
            super( 
                "RBInsert", 
                0, // ����� ���������� ��������� 
                18, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ������ ������", 
                    "���� ��� ���������� ����� ��� �������", 
                    "XZ", 
                    "����������", 
                    "���������� (���������)", 
                    "XZ", 
                    "XZ", 
                    "������������� ���� Z", 
                    "�� ����� ��", 
                    "�� ����� �� (���������)", 
                    "XZ", 
                    "���������", 
                    "��������� (���������)", 
                    "XZ", 
                    "XZ", 
                    "����������", 
                    "��������������� ������-������ �������� (�������)", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ������ ������ 
                    -1, // ���� ��� ���������� ����� ��� ������� 
                    -1, // XZ 
                    0, // ���������� 
                    -1, // ���������� (���������) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ������������� ���� Z 
                    -1, // �� ����� �� 
                    -1, // �� ����� �� (���������) 
                    -1, // XZ 
                    0, // ��������� 
                    -1, // ��������� (���������) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ���������� 
                    CALL_AUTO_LEVEL, // ��������������� ������-������ �������� (�������) 
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
                    state = 1; // ������������� ������ ������
                    break;
                }
                case 1: { // ������������� ������ ������
                    stack.pushBoolean(false); 
                    state = 2; // ���� ��� ���������� ����� ��� �������
                    break;
                }
                case 2: { // ���� ��� ���������� ����� ��� �������
                    if (d.RBInsert_x2 != null) {
                        state = 3; // XZ
                    } else {
                        state = 8; // ������������� ���� Z
                    }
                    break;
                }
                case 3: { // XZ
                    state = 4; // ����������
                    break;
                }
                case 4: { // ����������
                    if (d.RBInsert_x2.key > d.z.key) {
                        state = 6; // XZ
                    } else {
                        state = 7; // XZ
                    }
                    break;
                }
                case 5: { // ���������� (���������)
                    stack.pushBoolean(true); 
                    state = 2; // ���� ��� ���������� ����� ��� �������
                    break;
                }
                case 6: { // XZ
                    stack.pushBoolean(true); 
                    state = 5; // ���������� (���������)
                    break;
                }
                case 7: { // XZ
                    stack.pushBoolean(false); 
                    state = 5; // ���������� (���������)
                    break;
                }
                case 8: { // ������������� ���� Z
                    state = 9; // �� ����� ��
                    break;
                }
                case 9: { // �� ����� ��
                    if (d.RBInsert_y == null) {
                        state = 11; // XZ
                    } else {
                        state = 12; // ���������
                    }
                    break;
                }
                case 10: { // �� ����� �� (���������)
                    state = 16; // ����������
                    break;
                }
                case 11: { // XZ
                    stack.pushBoolean(true); 
                    state = 10; // �� ����� �� (���������)
                    break;
                }
                case 12: { // ���������
                    if (d.z.key < d.RBInsert_y.key) {
                        state = 14; // XZ
                    } else {
                        state = 15; // XZ
                    }
                    break;
                }
                case 13: { // ��������� (���������)
                    stack.pushBoolean(false); 
                    state = 10; // �� ����� �� (���������)
                    break;
                }
                case 14: { // XZ
                    stack.pushBoolean(true); 
                    state = 13; // ��������� (���������)
                    break;
                }
                case 15: { // XZ
                    stack.pushBoolean(false); 
                    state = 13; // ��������� (���������)
                    break;
                }
                case 16: { // ����������
                    state = 17; // ��������������� ������-������ �������� (�������)
                    break;
                }
                case 17: { // ��������������� ������-������ �������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = END_STATE; 
                    }
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� ������ ������
                    startSection();
                    storeField(d, "RBInsert_x2");
                    				d.RBInsert_x2 = root;
                    storeField(d, "RBInsert_y");
                    				d.RBInsert_y = null;
                    break;
                }
                case 2: { // ���� ��� ���������� ����� ��� �������
                    break;
                }
                case 3: { // XZ
                    startSection();
                    storeField(d, "RBInsert_y");
                    					d.RBInsert_y = d.RBInsert_x2;
                    break;
                }
                case 4: { // ����������
                    break;
                }
                case 5: { // ���������� (���������)
                    break;
                }
                case 6: { // XZ
                    startSection();
                    storeField(d, "RBInsert_x2");
                    					d.RBInsert_x2 = d.RBInsert_x2.left;
                    break;
                }
                case 7: { // XZ
                    startSection();
                    storeField(d, "RBInsert_x2");
                    					d.RBInsert_x2 = d.RBInsert_x2.right;
                    break;
                }
                case 8: { // ������������� ���� Z
                    startSection();
                    storeField(d.z, "p");
                    				d.z.p = d.RBInsert_y;
                    break;
                }
                case 9: { // �� ����� ��
                    break;
                }
                case 10: { // �� ����� �� (���������)
                    break;
                }
                case 11: { // XZ
                    startSection();
                    storeField(d, "root");
                    				d.root = d.z;
                    break;
                }
                case 12: { // ���������
                    break;
                }
                case 13: { // ��������� (���������)
                    break;
                }
                case 14: { // XZ
                    startSection();
                    storeField(d.RBInsert_y, "left");
                    					d.RBInsert_y.left = d.z;
                    break;
                }
                case 15: { // XZ
                    startSection();
                    storeField(d.RBInsert_y, "right");
                    					d.RBInsert_y.right = d.z;
                    break;
                }
                case 16: { // ����������
                    startSection();
                    storeField(d.z, "left");
                    				d.z.left = null;
                    storeField(d.z, "right");
                    				d.z.right = null;
                    storeField(d.z, "color");
                    				d.z.color = true;
                    break;
                }
                case 17: { // ��������������� ������-������ �������� (�������)
                    if (child == null) {
                        child = new RBInsertFixup(); 
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
                case 1: { // ������������� ������ ������
                    restoreSection();
                    break;
                }
                case 2: { // ���� ��� ���������� ����� ��� �������
                    break;
                }
                case 3: { // XZ
                    restoreSection();
                    break;
                }
                case 4: { // ����������
                    break;
                }
                case 5: { // ���������� (���������)
                    break;
                }
                case 6: { // XZ
                    restoreSection();
                    break;
                }
                case 7: { // XZ
                    restoreSection();
                    break;
                }
                case 8: { // ������������� ���� Z
                    restoreSection();
                    break;
                }
                case 9: { // �� ����� ��
                    break;
                }
                case 10: { // �� ����� �� (���������)
                    break;
                }
                case 11: { // XZ
                    restoreSection();
                    break;
                }
                case 12: { // ���������
                    break;
                }
                case 13: { // ��������� (���������)
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
                case 16: { // ����������
                    restoreSection();
                    break;
                }
                case 17: { // ��������������� ������-������ �������� (�������)
                    if (child == null) {
                        child = new RBInsertFixup(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� ������ ������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���� ��� ���������� ����� ��� �������
                    if (stack.popBoolean()) {
                        state = 5; // ���������� (���������)
                    } else {
                        state = 1; // ������������� ������ ������
                    }
                    break;
                }
                case 3: { // XZ
                    state = 2; // ���� ��� ���������� ����� ��� �������
                    break;
                }
                case 4: { // ����������
                    state = 3; // XZ
                    break;
                }
                case 5: { // ���������� (���������)
                    if (stack.popBoolean()) {
                        state = 6; // XZ
                    } else {
                        state = 7; // XZ
                    }
                    break;
                }
                case 6: { // XZ
                    state = 4; // ����������
                    break;
                }
                case 7: { // XZ
                    state = 4; // ����������
                    break;
                }
                case 8: { // ������������� ���� Z
                    state = 2; // ���� ��� ���������� ����� ��� �������
                    break;
                }
                case 9: { // �� ����� ��
                    state = 8; // ������������� ���� Z
                    break;
                }
                case 10: { // �� ����� �� (���������)
                    if (stack.popBoolean()) {
                        state = 11; // XZ
                    } else {
                        state = 13; // ��������� (���������)
                    }
                    break;
                }
                case 11: { // XZ
                    state = 9; // �� ����� ��
                    break;
                }
                case 12: { // ���������
                    state = 9; // �� ����� ��
                    break;
                }
                case 13: { // ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 14; // XZ
                    } else {
                        state = 15; // XZ
                    }
                    break;
                }
                case 14: { // XZ
                    state = 12; // ���������
                    break;
                }
                case 15: { // XZ
                    state = 12; // ���������
                    break;
                }
                case 16: { // ����������
                    state = 10; // �� ����� �� (���������)
                    break;
                }
                case 17: { // ��������������� ������-������ �������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 16; // ����������
                    }
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 17; // ��������������� ������-������ �������� (�������)
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
                case 4: { // ����������
                    if (d.RBInsert_x2.key > d.z.key) {
                        comment = RBTree.this.getComment("RBInsert.If.true"); 
                    } else {
                        comment = RBTree.this.getComment("RBInsert.If.false"); 
                    }
                    args = new Object[]{new Integer(d.RBInsert_x2.key), new Integer(d.z.key)}; 
                    break;
                }
                case 12: { // ���������
                    if (d.z.key < d.RBInsert_y.key) {
                        comment = RBTree.this.getComment("RBInsert.If.true"); 
                    } else {
                        comment = RBTree.this.getComment("RBInsert.If.false"); 
                    }
                    args = new Object[]{new Integer(d.z.key), new Integer(d.RBInsert_y.key)}; 
                    break;
                }
                case 17: { // ��������������� ������-������ �������� (�������)
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
                case 17: { // ��������������� ������-������ �������� (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * ��������������� ������-������ ��������.
      */
    private final class RBInsertFixup extends BaseAutomata implements Automata {
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
        public RBInsertFixup() {
            super( 
                "RBInsertFixup", 
                0, // ����� ���������� ��������� 
                27, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������� ����", 
                    "��������� �������� �� ���� z ����� ����� ������ ����", 
                    "��������� �������� �� ���� z ����� ����� ������ ���� (���������)", 
                    "����������", 
                    "��������� �������� �� y �������", 
                    "��������� �������� �� y ������� (���������)", 
                    "������ 1", 
                    "��������� �������� �� y �������", 
                    "��������� �������� �� y ������� (���������)", 
                    "������ 2", 
                    "����� ������� (�������)", 
                    "������ 3", 
                    "������ ������� (�������)", 
                    "����������", 
                    "��������� �������� �� y �������", 
                    "��������� �������� �� y ������� (���������)", 
                    "������ 1", 
                    "��������� �������� �� y �������", 
                    "��������� �������� �� y ������� (���������)", 
                    "������ 2", 
                    "������ ������� (�������)", 
                    "������ 3", 
                    "����� ������� (�������)", 
                    "��������� �������� �� y �������", 
                    "��������� �������� �� y ������� (���������)", 
                    "XZ", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������� ���� 
                    0, // ��������� �������� �� ���� z ����� ����� ������ ���� 
                    -1, // ��������� �������� �� ���� z ����� ����� ������ ���� (���������) 
                    -1, // ���������� 
                    -1, // ��������� �������� �� y ������� 
                    -1, // ��������� �������� �� y ������� (���������) 
                    0, // ������ 1 
                    -1, // ��������� �������� �� y ������� 
                    -1, // ��������� �������� �� y ������� (���������) 
                    0, // ������ 2 
                    CALL_AUTO_LEVEL, // ����� ������� (�������) 
                    0, // ������ 3 
                    CALL_AUTO_LEVEL, // ������ ������� (�������) 
                    -1, // ���������� 
                    -1, // ��������� �������� �� y ������� 
                    -1, // ��������� �������� �� y ������� (���������) 
                    0, // ������ 1 
                    -1, // ��������� �������� �� y ������� 
                    -1, // ��������� �������� �� y ������� (���������) 
                    0, // ������ 2 
                    CALL_AUTO_LEVEL, // ������ ������� (�������) 
                    0, // ������ 3 
                    CALL_AUTO_LEVEL, // ����� ������� (�������) 
                    -1, // ��������� �������� �� y ������� 
                    -1, // ��������� �������� �� y ������� (���������) 
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
                    state = 1; // ������� ����
                    break;
                }
                case 1: { // ������� ����
                    if (z.p.color) {
                        state = 2; // ��������� �������� �� ���� z ����� ����� ������ ����
                    } else {
                        state = 24; // ��������� �������� �� y �������
                    }
                    break;
                }
                case 2: { // ��������� �������� �� ���� z ����� ����� ������ ����
                    if (d.z.p == d.z.p.p.left) {
                        state = 4; // ����������
                    } else {
                        state = 14; // ����������
                    }
                    break;
                }
                case 3: { // ��������� �������� �� ���� z ����� ����� ������ ���� (���������)
                    stack.pushBoolean(true); 
                    state = 1; // ������� ����
                    break;
                }
                case 4: { // ����������
                    state = 5; // ��������� �������� �� y �������
                    break;
                }
                case 5: { // ��������� �������� �� y �������
                    if (d.RBInsertFixup_y.color) {
                        state = 7; // ������ 1
                    } else {
                        state = 8; // ��������� �������� �� y �������
                    }
                    break;
                }
                case 6: { // ��������� �������� �� y ������� (���������)
                    stack.pushBoolean(true); 
                    state = 3; // ��������� �������� �� ���� z ����� ����� ������ ���� (���������)
                    break;
                }
                case 7: { // ������ 1
                    stack.pushBoolean(true); 
                    state = 6; // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 8: { // ��������� �������� �� y �������
                    if (d.z == d.z.p.right) {
                        state = 10; // ������ 2
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // ��������� �������� �� y ������� (���������)
                    }
                    break;
                }
                case 9: { // ��������� �������� �� y ������� (���������)
                    state = 12; // ������ 3
                    break;
                }
                case 10: { // ������ 2
                    state = 11; // ����� ������� (�������)
                    break;
                }
                case 11: { // ����� ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 9; // ��������� �������� �� y ������� (���������)
                    }
                    break;
                }
                case 12: { // ������ 3
                    state = 13; // ������ ������� (�������)
                    break;
                }
                case 13: { // ������ ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 6; // ��������� �������� �� y ������� (���������)
                    }
                    break;
                }
                case 14: { // ����������
                    state = 15; // ��������� �������� �� y �������
                    break;
                }
                case 15: { // ��������� �������� �� y �������
                    if (d.RBInsertFixup_y.color) {
                        state = 17; // ������ 1
                    } else {
                        state = 18; // ��������� �������� �� y �������
                    }
                    break;
                }
                case 16: { // ��������� �������� �� y ������� (���������)
                    stack.pushBoolean(false); 
                    state = 3; // ��������� �������� �� ���� z ����� ����� ������ ���� (���������)
                    break;
                }
                case 17: { // ������ 1
                    stack.pushBoolean(true); 
                    state = 16; // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 18: { // ��������� �������� �� y �������
                    if (d.z == d.z.p.left) {
                        state = 20; // ������ 2
                    } else {
                        stack.pushBoolean(false); 
                        state = 19; // ��������� �������� �� y ������� (���������)
                    }
                    break;
                }
                case 19: { // ��������� �������� �� y ������� (���������)
                    state = 22; // ������ 3
                    break;
                }
                case 20: { // ������ 2
                    state = 21; // ������ ������� (�������)
                    break;
                }
                case 21: { // ������ ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 19; // ��������� �������� �� y ������� (���������)
                    }
                    break;
                }
                case 22: { // ������ 3
                    state = 23; // ����� ������� (�������)
                    break;
                }
                case 23: { // ����� ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 16; // ��������� �������� �� y ������� (���������)
                    }
                    break;
                }
                case 24: { // ��������� �������� �� y �������
                    if (d.root.color == true) {
                        state = 26; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 25; // ��������� �������� �� y ������� (���������)
                    }
                    break;
                }
                case 25: { // ��������� �������� �� y ������� (���������)
                    state = END_STATE; 
                    break;
                }
                case 26: { // XZ
                    stack.pushBoolean(true); 
                    state = 25; // ��������� �������� �� y ������� (���������)
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������� ����
                    break;
                }
                case 2: { // ��������� �������� �� ���� z ����� ����� ������ ����
                    break;
                }
                case 3: { // ��������� �������� �� ���� z ����� ����� ������ ���� (���������)
                    break;
                }
                case 4: { // ����������
                    startSection();
                    storeField(d, "RBInsertFixup_y");
                    					d.RBInsertFixup_y = d.z.p.p.right;
                    break;
                }
                case 5: { // ��������� �������� �� y �������
                    break;
                }
                case 6: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 7: { // ������ 1
                    startSection();
                    storeField(d.z.p, "color");
                    						d.z.p.color = false;
                    storeField(d.RBInsertFixup_y, "color");
                    						d.RBInsertFixup_y.color = false;
                    storeField(d.z.p.p, "color");
                    						d.z.p.p.color = true;
                    storeField(d, "z");
                    						d.z = d.z.p.p;
                    break;
                }
                case 8: { // ��������� �������� �� y �������
                    break;
                }
                case 9: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 10: { // ������ 2
                    startSection();
                    storeField(d, "z");
                    							d.z = d.z.p;
                    break;
                }
                case 11: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 12: { // ������ 3
                    startSection();
                    storeField(d.z.p, "color");
                    						d.z.p.color = false;
                    storeField(d.z.p.p, "color");
                    						d.z.p.p.color = true;
                    						
                    storeField(d, "z");
                    						d.z = d.z.p.p;
                    break;
                }
                case 13: { // ������ ������� (�������)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 14: { // ����������
                    startSection();
                    storeField(d, "RBInsertFixup_y");
                    					d.RBInsertFixup_y = d.z.p.p.left;
                    break;
                }
                case 15: { // ��������� �������� �� y �������
                    break;
                }
                case 16: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 17: { // ������ 1
                    startSection();
                    storeField(d.z.p, "color");
                    						d.z.p.color = false;
                    storeField(d.RBInsertFixup_y, "color");
                    						d.RBInsertFixup_y.color = false;
                    storeField(d.z.p.p, "color");
                    						d.z.p.p.color = true;
                    storeField(d, "z");
                    						d.z = d.z.p.p;
                    break;
                }
                case 18: { // ��������� �������� �� y �������
                    break;
                }
                case 19: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 20: { // ������ 2
                    startSection();
                    storeField(d, "z");
                    							d.z = d.z.p;
                    break;
                }
                case 21: { // ������ ������� (�������)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 22: { // ������ 3
                    startSection();
                    storeField(d.z.p, "color");
                    						d.z.p.color = false;
                    storeField(d.z.p.p, "color");
                    						d.z.p.p.color = true;
                    storeField(d, "z");
                    						d.z = d.z.p.p;
                    break;
                }
                case 23: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 24: { // ��������� �������� �� y �������
                    break;
                }
                case 25: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 26: { // XZ
                    startSection();
                    storeField(d.root, "color");
                    					d.root.color = false;
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
                case 1: { // ������� ����
                    break;
                }
                case 2: { // ��������� �������� �� ���� z ����� ����� ������ ����
                    break;
                }
                case 3: { // ��������� �������� �� ���� z ����� ����� ������ ���� (���������)
                    break;
                }
                case 4: { // ����������
                    restoreSection();
                    break;
                }
                case 5: { // ��������� �������� �� y �������
                    break;
                }
                case 6: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 7: { // ������ 1
                    restoreSection();
                    break;
                }
                case 8: { // ��������� �������� �� y �������
                    break;
                }
                case 9: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 10: { // ������ 2
                    restoreSection();
                    break;
                }
                case 11: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 12: { // ������ 3
                    restoreSection();
                    break;
                }
                case 13: { // ������ ������� (�������)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 14: { // ����������
                    restoreSection();
                    break;
                }
                case 15: { // ��������� �������� �� y �������
                    break;
                }
                case 16: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 17: { // ������ 1
                    restoreSection();
                    break;
                }
                case 18: { // ��������� �������� �� y �������
                    break;
                }
                case 19: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 20: { // ������ 2
                    restoreSection();
                    break;
                }
                case 21: { // ������ ������� (�������)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 22: { // ������ 3
                    restoreSection();
                    break;
                }
                case 23: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 24: { // ��������� �������� �� y �������
                    break;
                }
                case 25: { // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 26: { // XZ
                    restoreSection();
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������� ����
                    if (stack.popBoolean()) {
                        state = 3; // ��������� �������� �� ���� z ����� ����� ������ ���� (���������)
                    } else {
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // ��������� �������� �� ���� z ����� ����� ������ ����
                    state = 1; // ������� ����
                    break;
                }
                case 3: { // ��������� �������� �� ���� z ����� ����� ������ ���� (���������)
                    if (stack.popBoolean()) {
                        state = 6; // ��������� �������� �� y ������� (���������)
                    } else {
                        state = 16; // ��������� �������� �� y ������� (���������)
                    }
                    break;
                }
                case 4: { // ����������
                    state = 2; // ��������� �������� �� ���� z ����� ����� ������ ����
                    break;
                }
                case 5: { // ��������� �������� �� y �������
                    state = 4; // ����������
                    break;
                }
                case 6: { // ��������� �������� �� y ������� (���������)
                    if (stack.popBoolean()) {
                        state = 7; // ������ 1
                    } else {
                        state = 13; // ������ ������� (�������)
                    }
                    break;
                }
                case 7: { // ������ 1
                    state = 5; // ��������� �������� �� y �������
                    break;
                }
                case 8: { // ��������� �������� �� y �������
                    state = 5; // ��������� �������� �� y �������
                    break;
                }
                case 9: { // ��������� �������� �� y ������� (���������)
                    if (stack.popBoolean()) {
                        state = 11; // ����� ������� (�������)
                    } else {
                        state = 8; // ��������� �������� �� y �������
                    }
                    break;
                }
                case 10: { // ������ 2
                    state = 8; // ��������� �������� �� y �������
                    break;
                }
                case 11: { // ����� ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 10; // ������ 2
                    }
                    break;
                }
                case 12: { // ������ 3
                    state = 9; // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 13: { // ������ ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 12; // ������ 3
                    }
                    break;
                }
                case 14: { // ����������
                    state = 2; // ��������� �������� �� ���� z ����� ����� ������ ����
                    break;
                }
                case 15: { // ��������� �������� �� y �������
                    state = 14; // ����������
                    break;
                }
                case 16: { // ��������� �������� �� y ������� (���������)
                    if (stack.popBoolean()) {
                        state = 17; // ������ 1
                    } else {
                        state = 23; // ����� ������� (�������)
                    }
                    break;
                }
                case 17: { // ������ 1
                    state = 15; // ��������� �������� �� y �������
                    break;
                }
                case 18: { // ��������� �������� �� y �������
                    state = 15; // ��������� �������� �� y �������
                    break;
                }
                case 19: { // ��������� �������� �� y ������� (���������)
                    if (stack.popBoolean()) {
                        state = 21; // ������ ������� (�������)
                    } else {
                        state = 18; // ��������� �������� �� y �������
                    }
                    break;
                }
                case 20: { // ������ 2
                    state = 18; // ��������� �������� �� y �������
                    break;
                }
                case 21: { // ������ ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 20; // ������ 2
                    }
                    break;
                }
                case 22: { // ������ 3
                    state = 19; // ��������� �������� �� y ������� (���������)
                    break;
                }
                case 23: { // ����� ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 22; // ������ 3
                    }
                    break;
                }
                case 24: { // ��������� �������� �� y �������
                    state = 1; // ������� ����
                    break;
                }
                case 25: { // ��������� �������� �� y ������� (���������)
                    if (stack.popBoolean()) {
                        state = 26; // XZ
                    } else {
                        state = 24; // ��������� �������� �� y �������
                    }
                    break;
                }
                case 26: { // XZ
                    state = 24; // ��������� �������� �� y �������
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 25; // ��������� �������� �� y ������� (���������)
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
                case 2: { // ��������� �������� �� ���� z ����� ����� ������ ����
                    if (d.z.p == d.z.p.p.left) {
                        comment = RBTree.this.getComment("RBInsertFixup.If.true"); 
                    } else {
                        comment = RBTree.this.getComment("RBInsertFixup.If.false"); 
                    }
                    args = new Object[]{new Integer(d.z.p.key), new Integer(d.z.key), new Integer(d.z.p.p.key)}; 
                    break;
                }
                case 7: { // ������ 1
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 10: { // ������ 2
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 11: { // ����� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 12: { // ������ 3
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 13: { // ������ ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 17: { // ������ 1
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 20: { // ������ 2
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 21: { // ������ ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 22: { // ������ 3
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 23: { // ����� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 26: { // XZ
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
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
                case 11: { // ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 13: { // ������ ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 21: { // ������ ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 23: { // ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * ��������.
      */
    private final class RBDelete extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 32;

        /**
          * �����������.
          */
        public RBDelete() {
            super( 
                "RBDelete", 
                0, // ����� ���������� ��������� 
                32, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "������������� ������ ������", 
                    "���������", 
                    "��������� (���������)", 
                    "XZ", 
                    "���������", 
                    "��������� (���������)", 
                    "XZ", 
                    "���� ��� ���������� ����� ��������� ������� � ��������� � ������ �", 
                    "XZ", 
                    "XZ", 
                    "����", 
                    "XZ", 
                    "���� ������ ��������� � �", 
                    "���� ������ ��������� � � (���������)", 
                    "XZ", 
                    "XZ", 
                    "XZ", 
                    "���� ������ ��������� � �", 
                    "���� ������ ��������� � � (���������)", 
                    "XZ", 
                    "���������", 
                    "��������� (���������)", 
                    "XZ", 
                    "XZ", 
                    "���� ������ ��������� � �", 
                    "���� ������ ��������� � � (���������)", 
                    "XZ", 
                    "�������� �� ������-������ ��������", 
                    "�������� �� ������-������ �������� (���������)", 
                    "���� ���� ��������� ��������", 
                    "�������������� ������-������ ������� ����� �������� (�������)", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // ������������� ������ ������ 
                    -1, // ��������� 
                    -1, // ��������� (���������) 
                    -1, // XZ 
                    -1, // ��������� 
                    -1, // ��������� (���������) 
                    -1, // XZ 
                    -1, // ���� ��� ���������� ����� ��������� ������� � ��������� � ������ � 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ���� 
                    -1, // XZ 
                    -1, // ���� ������ ��������� � � 
                    -1, // ���� ������ ��������� � � (���������) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ���� ������ ��������� � � 
                    -1, // ���� ������ ��������� � � (���������) 
                    -1, // XZ 
                    -1, // ��������� 
                    -1, // ��������� (���������) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ���� ������ ��������� � � 
                    -1, // ���� ������ ��������� � � (���������) 
                    -1, // XZ 
                    -1, // �������� �� ������-������ �������� 
                    -1, // �������� �� ������-������ �������� (���������) 
                    -1, // ���� ���� ��������� �������� 
                    CALL_AUTO_LEVEL, // �������������� ������-������ ������� ����� �������� (�������) 
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
                    state = 1; // ������������� ������ ������
                    break;
                }
                case 1: { // ������������� ������ ������
                    state = 2; // ���������
                    break;
                }
                case 2: { // ���������
                    if ((d.z.left == null) || (d.z.right == null)) {
                        state = 4; // XZ
                    } else {
                        state = 5; // ���������
                    }
                    break;
                }
                case 3: { // ��������� (���������)
                    state = 13; // ���� ������ ��������� � �
                    break;
                }
                case 4: { // XZ
                    stack.pushBoolean(true); 
                    state = 3; // ��������� (���������)
                    break;
                }
                case 5: { // ���������
                    if (d.z.right != null) {
                        state = 7; // XZ
                    } else {
                        state = 10; // XZ
                    }
                    break;
                }
                case 6: { // ��������� (���������)
                    stack.pushBoolean(false); 
                    state = 3; // ��������� (���������)
                    break;
                }
                case 7: { // XZ
                    stack.pushBoolean(false); 
                    state = 8; // ���� ��� ���������� ����� ��������� ������� � ��������� � ������ �
                    break;
                }
                case 8: { // ���� ��� ���������� ����� ��������� ������� � ��������� � ������ �
                    if (y.left != null) {
                        state = 9; // XZ
                    } else {
                        stack.pushBoolean(true); 
                        state = 6; // ��������� (���������)
                    }
                    break;
                }
                case 9: { // XZ
                    stack.pushBoolean(true); 
                    state = 8; // ���� ��� ���������� ����� ��������� ������� � ��������� � ������ �
                    break;
                }
                case 10: { // XZ
                    stack.pushBoolean(false); 
                    state = 11; // ����
                    break;
                }
                case 11: { // ����
                    if ((d.RBDelete_y != null) && (d.RBDelete_x2 == d.RBDelete_y.right)) {
                        state = 12; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // ��������� (���������)
                    }
                    break;
                }
                case 12: { // XZ
                    stack.pushBoolean(true); 
                    state = 11; // ����
                    break;
                }
                case 13: { // ���� ������ ��������� � �
                    if (d.RBDelete_y.left != null) {
                        state = 15; // XZ
                    } else {
                        state = 16; // XZ
                    }
                    break;
                }
                case 14: { // ���� ������ ��������� � � (���������)
                    state = 17; // XZ
                    break;
                }
                case 15: { // XZ
                    stack.pushBoolean(true); 
                    state = 14; // ���� ������ ��������� � � (���������)
                    break;
                }
                case 16: { // XZ
                    stack.pushBoolean(false); 
                    state = 14; // ���� ������ ��������� � � (���������)
                    break;
                }
                case 17: { // XZ
                    state = 18; // ���� ������ ��������� � �
                    break;
                }
                case 18: { // ���� ������ ��������� � �
                    if (d.RBDelete_y.p == null) {
                        state = 20; // XZ
                    } else {
                        state = 21; // ���������
                    }
                    break;
                }
                case 19: { // ���� ������ ��������� � � (���������)
                    state = 25; // ���� ������ ��������� � �
                    break;
                }
                case 20: { // XZ
                    stack.pushBoolean(true); 
                    state = 19; // ���� ������ ��������� � � (���������)
                    break;
                }
                case 21: { // ���������
                    if (y == y.p.left) {
                        state = 23; // XZ
                    } else {
                        state = 24; // XZ
                    }
                    break;
                }
                case 22: { // ��������� (���������)
                    stack.pushBoolean(false); 
                    state = 19; // ���� ������ ��������� � � (���������)
                    break;
                }
                case 23: { // XZ
                    stack.pushBoolean(true); 
                    state = 22; // ��������� (���������)
                    break;
                }
                case 24: { // XZ
                    stack.pushBoolean(false); 
                    state = 22; // ��������� (���������)
                    break;
                }
                case 25: { // ���� ������ ��������� � �
                    if (d.RBDelete_y != d.z) {
                        state = 27; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 26; // ���� ������ ��������� � � (���������)
                    }
                    break;
                }
                case 26: { // ���� ������ ��������� � � (���������)
                    state = 28; // �������� �� ������-������ ��������
                    break;
                }
                case 27: { // XZ
                    stack.pushBoolean(true); 
                    state = 26; // ���� ������ ��������� � � (���������)
                    break;
                }
                case 28: { // �������� �� ������-������ ��������
                    if (!y.color) {
                        state = 30; // ���� ���� ��������� ��������
                    } else {
                        stack.pushBoolean(false); 
                        state = 29; // �������� �� ������-������ �������� (���������)
                    }
                    break;
                }
                case 29: { // �������� �� ������-������ �������� (���������)
                    state = END_STATE; 
                    break;
                }
                case 30: { // ���� ���� ��������� ��������
                    state = 31; // �������������� ������-������ ������� ����� �������� (�������)
                    break;
                }
                case 31: { // �������������� ������-������ ������� ����� �������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 29; // �������� �� ������-������ �������� (���������)
                    }
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // ������������� ������ ������
                    startSection();
                    storeField(d, "RBDelete_x2");
                    				d.RBDelete_x2 = null;
                    storeField(d, "RBDelete_y");
                    				d.RBDelete_y = null;
                    break;
                }
                case 2: { // ���������
                    break;
                }
                case 3: { // ��������� (���������)
                    break;
                }
                case 4: { // XZ
                    startSection();
                    storeField(d, "RBDelete_y");
                    					d.RBDelete_y = d.z;
                    break;
                }
                case 5: { // ���������
                    break;
                }
                case 6: { // ��������� (���������)
                    break;
                }
                case 7: { // XZ
                    startSection();
                    storeField(d, "RBDelete_y");
                    						d.RBDelete_y = d.z.right;
                    break;
                }
                case 8: { // ���� ��� ���������� ����� ��������� ������� � ��������� � ������ �
                    break;
                }
                case 9: { // XZ
                    startSection();
                    storeField(d, "RBDelete_y");
                    						d.RBDelete_y = d.RBDelete_y.left;
                    break;
                }
                case 10: { // XZ
                    startSection();
                    storeField(d, "RBDelete_x2");
                    						d.RBDelete_x2 = d.z;
                    storeField(d, "RBDelete_y");
                    						d.RBDelete_y = d.z.p;
                    break;
                }
                case 11: { // ����
                    break;
                }
                case 12: { // XZ
                    startSection();
                    storeField(d, "RBDelete_x2");
                    						d.RBDelete_x2 = d.RBDelete_y;
                    storeField(d, "RBDelete_y");
                    						d.RBDelete_y = d.RBDelete_y.p;
                    break;
                }
                case 13: { // ���� ������ ��������� � �
                    break;
                }
                case 14: { // ���� ������ ��������� � � (���������)
                    break;
                }
                case 15: { // XZ
                    startSection();
                    storeField(d, "RBDelete_x2");
                    					d.RBDelete_x2 = d.RBDelete_y.left;
                    break;
                }
                case 16: { // XZ
                    startSection();
                    storeField(d, "RBDelete_x2");
                    					d.RBDelete_x2 = d.RBDelete_y.right;
                    break;
                }
                case 17: { // XZ
                    startSection();
                    storeField(d.RBDelete_x2, "p");
                    				d.RBDelete_x2.p = d.RBDelete_y.p; 
                    break;
                }
                case 18: { // ���� ������ ��������� � �
                    break;
                }
                case 19: { // ���� ������ ��������� � � (���������)
                    break;
                }
                case 20: { // XZ
                    startSection();
                    storeField(d, "root");
                    					d.root = d.RBDelete_x2;
                    break;
                }
                case 21: { // ���������
                    break;
                }
                case 22: { // ��������� (���������)
                    break;
                }
                case 23: { // XZ
                    startSection();
                    storeField(d.RBDelete_y.p, "left");
                    						d.RBDelete_y.p.left = d.RBDelete_x2;
                    break;
                }
                case 24: { // XZ
                    startSection();
                    storeField(d.RBDelete_y.p, "reght");
                    						d.RBDelete_y.p.reght = d.RBDelete_x2;
                    break;
                }
                case 25: { // ���� ������ ��������� � �
                    break;
                }
                case 26: { // ���� ������ ��������� � � (���������)
                    break;
                }
                case 27: { // XZ
                    startSection();
                    storeField(d.z, "key");
                    					d.z.key = d.RBDelete_y.key;
                    break;
                }
                case 28: { // �������� �� ������-������ ��������
                    break;
                }
                case 29: { // �������� �� ������-������ �������� (���������)
                    break;
                }
                case 30: { // ���� ���� ��������� ��������
                    startSection();
                    storeField(d, "z");
                    					d.z = d.RBDelete_x2;
                    break;
                }
                case 31: { // �������������� ������-������ ������� ����� �������� (�������)
                    if (child == null) {
                        child = new RBDeleteFixup(); 
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
                case 1: { // ������������� ������ ������
                    restoreSection();
                    break;
                }
                case 2: { // ���������
                    break;
                }
                case 3: { // ��������� (���������)
                    break;
                }
                case 4: { // XZ
                    restoreSection();
                    break;
                }
                case 5: { // ���������
                    break;
                }
                case 6: { // ��������� (���������)
                    break;
                }
                case 7: { // XZ
                    restoreSection();
                    break;
                }
                case 8: { // ���� ��� ���������� ����� ��������� ������� � ��������� � ������ �
                    break;
                }
                case 9: { // XZ
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
                case 12: { // XZ
                    restoreSection();
                    break;
                }
                case 13: { // ���� ������ ��������� � �
                    break;
                }
                case 14: { // ���� ������ ��������� � � (���������)
                    break;
                }
                case 15: { // XZ
                    restoreSection();
                    break;
                }
                case 16: { // XZ
                    restoreSection();
                    break;
                }
                case 17: { // XZ
                    restoreSection();
                    break;
                }
                case 18: { // ���� ������ ��������� � �
                    break;
                }
                case 19: { // ���� ������ ��������� � � (���������)
                    break;
                }
                case 20: { // XZ
                    restoreSection();
                    break;
                }
                case 21: { // ���������
                    break;
                }
                case 22: { // ��������� (���������)
                    break;
                }
                case 23: { // XZ
                    restoreSection();
                    break;
                }
                case 24: { // XZ
                    restoreSection();
                    break;
                }
                case 25: { // ���� ������ ��������� � �
                    break;
                }
                case 26: { // ���� ������ ��������� � � (���������)
                    break;
                }
                case 27: { // XZ
                    restoreSection();
                    break;
                }
                case 28: { // �������� �� ������-������ ��������
                    break;
                }
                case 29: { // �������� �� ������-������ �������� (���������)
                    break;
                }
                case 30: { // ���� ���� ��������� ��������
                    restoreSection();
                    break;
                }
                case 31: { // �������������� ������-������ ������� ����� �������� (�������)
                    if (child == null) {
                        child = new RBDeleteFixup(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // ������� � ���������� ���������
            switch (state) {
                case 1: { // ������������� ������ ������
                    state = START_STATE; 
                    break;
                }
                case 2: { // ���������
                    state = 1; // ������������� ������ ������
                    break;
                }
                case 3: { // ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 4; // XZ
                    } else {
                        state = 6; // ��������� (���������)
                    }
                    break;
                }
                case 4: { // XZ
                    state = 2; // ���������
                    break;
                }
                case 5: { // ���������
                    state = 2; // ���������
                    break;
                }
                case 6: { // ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 8; // ���� ��� ���������� ����� ��������� ������� � ��������� � ������ �
                    } else {
                        state = 11; // ����
                    }
                    break;
                }
                case 7: { // XZ
                    state = 5; // ���������
                    break;
                }
                case 8: { // ���� ��� ���������� ����� ��������� ������� � ��������� � ������ �
                    if (stack.popBoolean()) {
                        state = 9; // XZ
                    } else {
                        state = 7; // XZ
                    }
                    break;
                }
                case 9: { // XZ
                    state = 8; // ���� ��� ���������� ����� ��������� ������� � ��������� � ������ �
                    break;
                }
                case 10: { // XZ
                    state = 5; // ���������
                    break;
                }
                case 11: { // ����
                    if (stack.popBoolean()) {
                        state = 12; // XZ
                    } else {
                        state = 10; // XZ
                    }
                    break;
                }
                case 12: { // XZ
                    state = 11; // ����
                    break;
                }
                case 13: { // ���� ������ ��������� � �
                    state = 3; // ��������� (���������)
                    break;
                }
                case 14: { // ���� ������ ��������� � � (���������)
                    if (stack.popBoolean()) {
                        state = 15; // XZ
                    } else {
                        state = 16; // XZ
                    }
                    break;
                }
                case 15: { // XZ
                    state = 13; // ���� ������ ��������� � �
                    break;
                }
                case 16: { // XZ
                    state = 13; // ���� ������ ��������� � �
                    break;
                }
                case 17: { // XZ
                    state = 14; // ���� ������ ��������� � � (���������)
                    break;
                }
                case 18: { // ���� ������ ��������� � �
                    state = 17; // XZ
                    break;
                }
                case 19: { // ���� ������ ��������� � � (���������)
                    if (stack.popBoolean()) {
                        state = 20; // XZ
                    } else {
                        state = 22; // ��������� (���������)
                    }
                    break;
                }
                case 20: { // XZ
                    state = 18; // ���� ������ ��������� � �
                    break;
                }
                case 21: { // ���������
                    state = 18; // ���� ������ ��������� � �
                    break;
                }
                case 22: { // ��������� (���������)
                    if (stack.popBoolean()) {
                        state = 23; // XZ
                    } else {
                        state = 24; // XZ
                    }
                    break;
                }
                case 23: { // XZ
                    state = 21; // ���������
                    break;
                }
                case 24: { // XZ
                    state = 21; // ���������
                    break;
                }
                case 25: { // ���� ������ ��������� � �
                    state = 19; // ���� ������ ��������� � � (���������)
                    break;
                }
                case 26: { // ���� ������ ��������� � � (���������)
                    if (stack.popBoolean()) {
                        state = 27; // XZ
                    } else {
                        state = 25; // ���� ������ ��������� � �
                    }
                    break;
                }
                case 27: { // XZ
                    state = 25; // ���� ������ ��������� � �
                    break;
                }
                case 28: { // �������� �� ������-������ ��������
                    state = 26; // ���� ������ ��������� � � (���������)
                    break;
                }
                case 29: { // �������� �� ������-������ �������� (���������)
                    if (stack.popBoolean()) {
                        state = 31; // �������������� ������-������ ������� ����� �������� (�������)
                    } else {
                        state = 28; // �������� �� ������-������ ��������
                    }
                    break;
                }
                case 30: { // ���� ���� ��������� ��������
                    state = 28; // �������� �� ������-������ ��������
                    break;
                }
                case 31: { // �������������� ������-������ ������� ����� �������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 30; // ���� ���� ��������� ��������
                    }
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 29; // �������� �� ������-������ �������� (���������)
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
                case 31: { // �������������� ������-������ ������� ����� �������� (�������)
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
                case 31: { // �������������� ������-������ ������� ����� �������� (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * �������������� ������-������ ������� ����� ��������.
      */
    private final class RBDeleteFixup extends BaseAutomata implements Automata {
        /**
          * ��������� ��������� ��������.
          */
        private final int START_STATE = 0;

        /**
          * �������� ��������� ��������.
          */
        private final int END_STATE = 40;

        /**
          * �����������.
          */
        public RBDeleteFixup() {
            super( 
                "RBDeleteFixup", 
                0, // ����� ���������� ��������� 
                40, // ����� ��������� ��������� 
                new String[]{ 
                    "��������� ���������",  
                    "XZ", 
                    "������� ����", 
                    "�������� �� � ����� �����", 
                    "�������� �� � ����� ����� (���������)", 
                    "XZ", 
                    "������ 1", 
                    "������ 1 (���������)", 
                    "XZ", 
                    "����� ������� (�������)", 
                    "XZ", 
                    "������ 2", 
                    "������ 2 (���������)", 
                    "XZ", 
                    "������ 3", 
                    "������ 3 (���������)", 
                    "XZ", 
                    "������ ������� (�������)", 
                    "XZ", 
                    "XZ", 
                    "����� ������� (�������)", 
                    "XZ", 
                    "XZ", 
                    "������ 1", 
                    "������ 1 (���������)", 
                    "XZ", 
                    "����� ������� (�������)", 
                    "XZ", 
                    "������ 2", 
                    "������ 2 (���������)", 
                    "XZ", 
                    "������ 3", 
                    "������ 3 (���������)", 
                    "XZ", 
                    "������ ������� (�������)", 
                    "XZ", 
                    "XZ", 
                    "����� ������� (�������)", 
                    "XZ", 
                    "XZ", 
                    "�������� ���������" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // ��������� ���������,  
                    -1, // XZ 
                    -1, // ������� ���� 
                    -1, // �������� �� � ����� ����� 
                    -1, // �������� �� � ����� ����� (���������) 
                    -1, // XZ 
                    -1, // ������ 1 
                    -1, // ������ 1 (���������) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // ����� ������� (�������) 
                    -1, // XZ 
                    -1, // ������ 2 
                    -1, // ������ 2 (���������) 
                    -1, // XZ 
                    -1, // ������ 3 
                    -1, // ������ 3 (���������) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // ������ ������� (�������) 
                    -1, // XZ 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // ����� ������� (�������) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // ������ 1 
                    -1, // ������ 1 (���������) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // ����� ������� (�������) 
                    -1, // XZ 
                    -1, // ������ 2 
                    -1, // ������ 2 (���������) 
                    -1, // XZ 
                    -1, // ������ 3 
                    -1, // ������ 3 (���������) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // ������ ������� (�������) 
                    -1, // XZ 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // ����� ������� (�������) 
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
                    state = 1; // XZ
                    break;
                }
                case 1: { // XZ
                    stack.pushBoolean(false); 
                    state = 2; // ������� ����
                    break;
                }
                case 2: { // ������� ����
                    if ((d.x != d.root) && (!d.x.color)) {
                        state = 3; // �������� �� � ����� �����
                    } else {
                        state = 39; // XZ
                    }
                    break;
                }
                case 3: { // �������� �� � ����� �����
                    if (d.x == d.x.p.left) {
                        state = 5; // XZ
                    } else {
                        state = 22; // XZ
                    }
                    break;
                }
                case 4: { // �������� �� � ����� ����� (���������)
                    stack.pushBoolean(true); 
                    state = 2; // ������� ����
                    break;
                }
                case 5: { // XZ
                    state = 6; // ������ 1
                    break;
                }
                case 6: { // ������ 1
                    if (w.color) {
                        state = 8; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // ������ 1 (���������)
                    }
                    break;
                }
                case 7: { // ������ 1 (���������)
                    state = 11; // ������ 2
                    break;
                }
                case 8: { // XZ
                    state = 9; // ����� ������� (�������)
                    break;
                }
                case 9: { // ����� ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // XZ
                    }
                    break;
                }
                case 10: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // ������ 1 (���������)
                    break;
                }
                case 11: { // ������ 2
                    if ((!d.RBDeleteFixup_w.left.color) && (!d.RBDeleteFixup_w.right.color)) {
                        state = 13; // XZ
                    } else {
                        state = 14; // ������ 3
                    }
                    break;
                }
                case 12: { // ������ 2 (���������)
                    stack.pushBoolean(true); 
                    state = 4; // �������� �� � ����� ����� (���������)
                    break;
                }
                case 13: { // XZ
                    stack.pushBoolean(true); 
                    state = 12; // ������ 2 (���������)
                    break;
                }
                case 14: { // ������ 3
                    if (!d.RBDeleteFixup_w.right.color) {
                        state = 16; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 15; // ������ 3 (���������)
                    }
                    break;
                }
                case 15: { // ������ 3 (���������)
                    state = 19; // XZ
                    break;
                }
                case 16: { // XZ
                    state = 17; // ������ ������� (�������)
                    break;
                }
                case 17: { // ������ ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 18; // XZ
                    }
                    break;
                }
                case 18: { // XZ
                    stack.pushBoolean(true); 
                    state = 15; // ������ 3 (���������)
                    break;
                }
                case 19: { // XZ
                    state = 20; // ����� ������� (�������)
                    break;
                }
                case 20: { // ����� ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 21; // XZ
                    }
                    break;
                }
                case 21: { // XZ
                    stack.pushBoolean(false); 
                    state = 12; // ������ 2 (���������)
                    break;
                }
                case 22: { // XZ
                    state = 23; // ������ 1
                    break;
                }
                case 23: { // ������ 1
                    if (w.color) {
                        state = 25; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 24; // ������ 1 (���������)
                    }
                    break;
                }
                case 24: { // ������ 1 (���������)
                    state = 28; // ������ 2
                    break;
                }
                case 25: { // XZ
                    state = 26; // ����� ������� (�������)
                    break;
                }
                case 26: { // ����� ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 27; // XZ
                    }
                    break;
                }
                case 27: { // XZ
                    stack.pushBoolean(true); 
                    state = 24; // ������ 1 (���������)
                    break;
                }
                case 28: { // ������ 2
                    if ((!d.RBDeleteFixup_w.left.color) && (!d.RBDeleteFixup_w.right.color)) {
                        state = 30; // XZ
                    } else {
                        state = 31; // ������ 3
                    }
                    break;
                }
                case 29: { // ������ 2 (���������)
                    stack.pushBoolean(false); 
                    state = 4; // �������� �� � ����� ����� (���������)
                    break;
                }
                case 30: { // XZ
                    stack.pushBoolean(true); 
                    state = 29; // ������ 2 (���������)
                    break;
                }
                case 31: { // ������ 3
                    if (!d.RBDeleteFixup_w.left.color) {
                        state = 33; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 32; // ������ 3 (���������)
                    }
                    break;
                }
                case 32: { // ������ 3 (���������)
                    state = 36; // XZ
                    break;
                }
                case 33: { // XZ
                    state = 34; // ������ ������� (�������)
                    break;
                }
                case 34: { // ������ ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 35; // XZ
                    }
                    break;
                }
                case 35: { // XZ
                    stack.pushBoolean(true); 
                    state = 32; // ������ 3 (���������)
                    break;
                }
                case 36: { // XZ
                    state = 37; // ����� ������� (�������)
                    break;
                }
                case 37: { // ����� ������� (�������)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 38; // XZ
                    }
                    break;
                }
                case 38: { // XZ
                    stack.pushBoolean(false); 
                    state = 29; // ������ 2 (���������)
                    break;
                }
                case 39: { // XZ
                    state = END_STATE; 
                    break;
                }
            }

            // �������� � ������� ���������
            switch (state) {
                case 1: { // XZ
                    startSection();
                    storeField(d, "x");
                    				d.x = d.z;
                    break;
                }
                case 2: { // ������� ����
                    break;
                }
                case 3: { // �������� �� � ����� �����
                    break;
                }
                case 4: { // �������� �� � ����� ����� (���������)
                    break;
                }
                case 5: { // XZ
                    startSection();
                    storeField(d, "RBDeleteFixup_w");
                    					d.RBDeleteFixup_w = d.x.p.right;
                    break;
                }
                case 6: { // ������ 1
                    break;
                }
                case 7: { // ������ 1 (���������)
                    break;
                }
                case 8: { // XZ
                    startSection();
                    storeField(d.RBDeleteFixup_w, "color");
                    						d.RBDeleteFixup_w.color = false;
                    storeField(d.x.p, "color");
                    						d.x.p.color = true;
                    storeField(d, "z");
                    						d.z = d.x.p;
                    break;
                }
                case 9: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // XZ
                    startSection();
                    storeField(d, "RBDeleteFixup_w");
                    						d.RBDeleteFixup_w = d.x.p.right;
                    break;
                }
                case 11: { // ������ 2
                    break;
                }
                case 12: { // ������ 2 (���������)
                    break;
                }
                case 13: { // XZ
                    startSection();
                    storeField(d.RBDeleteFixup_w, "color");
                    						d.RBDeleteFixup_w.color = true;
                    storeField(d, "x");
                    						d.x = d.x.p;
                    break;
                }
                case 14: { // ������ 3
                    break;
                }
                case 15: { // ������ 3 (���������)
                    break;
                }
                case 16: { // XZ
                    startSection();
                    storeField(d.RBDeleteFixup_w.left, "color");
                    							d.RBDeleteFixup_w.left.color = false;
                    storeField(d.RBDeleteFixup_w, "color");
                    							d.RBDeleteFixup_w.color = true;
                    							d.z = d.RBDeleteFixup_w;
                    break;
                }
                case 17: { // ������ ������� (�������)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 18: { // XZ
                    startSection();
                    storeField(d, "RBDeleteFixup_w");
                    							d.RBDeleteFixup_w = d.x.p.right;
                    break;
                }
                case 19: { // XZ
                    startSection();
                    storeField(d.RBDeleteFixup_w, "color");
                    						d.RBDeleteFixup_w.color = d.x.p.color;
                    storeField(d.x.p, "color");
                    						d.x.p.color = false;
                    storeField(d.RBDeleteFixup_w.right, "color");
                    						d.RBDeleteFixup_w.right.color = false;
                    storeField(d, "z");
                    						d.z = d.x.p;
                    break;
                }
                case 20: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 21: { // XZ
                    startSection();
                    storeField(d, "x");
                    						d.x = d.root;
                    break;
                }
                case 22: { // XZ
                    startSection();
                    storeField(d, "RBDeleteFixup_w");
                    					d.RBDeleteFixup_w = d.x.p.left;
                    break;
                }
                case 23: { // ������ 1
                    break;
                }
                case 24: { // ������ 1 (���������)
                    break;
                }
                case 25: { // XZ
                    startSection();
                    storeField(d.RBDeleteFixup_w, "color");
                    						d.RBDeleteFixup_w.color = false;
                    storeField(d.x.p, "color");
                    						d.x.p.color = true;
                    storeField(d, "z");
                    						d.z = d.x.p;
                    break;
                }
                case 26: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 27: { // XZ
                    startSection();
                    storeField(d, "RBDeleteFixup_w");
                    						d.RBDeleteFixup_w = d.x.p.left;
                    break;
                }
                case 28: { // ������ 2
                    break;
                }
                case 29: { // ������ 2 (���������)
                    break;
                }
                case 30: { // XZ
                    startSection();
                    storeField(d.RBDeleteFixup_w, "color");
                    						d.RBDeleteFixup_w.color = true;
                    storeField(d, "x");
                    						d.x = d.x.p;
                    break;
                }
                case 31: { // ������ 3
                    break;
                }
                case 32: { // ������ 3 (���������)
                    break;
                }
                case 33: { // XZ
                    startSection();
                    storeField(d.RBDeleteFixup_w.right, "color");
                    							d.RBDeleteFixup_w.right.color = false;
                    storeField(d.RBDeleteFixup_w, "color");
                    							d.RBDeleteFixup_w.color = true;
                    storeField(d, "z");
                    							d.z = d.RBDeleteFixup_w;
                    break;
                }
                case 34: { // ������ ������� (�������)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 35: { // XZ
                    startSection();
                    storeField(d, "RBDeleteFixup_w");
                    							d.RBDeleteFixup_w = d.x.p.left;
                    break;
                }
                case 36: { // XZ
                    startSection();
                    storeField(d.RBDeleteFixup_w, "color");
                    						d.RBDeleteFixup_w.color = d.x.p.color;
                    storeField(d.x.p, "color");
                    						d.x.p.color = false;
                    storeField(d.RBDeleteFixup_w.left, "color");
                    						d.RBDeleteFixup_w.left.color = false;
                    storeField(d, "z");
                    						d.z = d.x.p;
                    break;
                }
                case 37: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 38: { // XZ
                    startSection();
                    storeField(d, "x");
                    						d.x = d.root;
                    break;
                }
                case 39: { // XZ
                    startSection();
                    storeField(d.x, "color");
                    				d.x.color = false;
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
                case 2: { // ������� ����
                    break;
                }
                case 3: { // �������� �� � ����� �����
                    break;
                }
                case 4: { // �������� �� � ����� ����� (���������)
                    break;
                }
                case 5: { // XZ
                    restoreSection();
                    break;
                }
                case 6: { // ������ 1
                    break;
                }
                case 7: { // ������ 1 (���������)
                    break;
                }
                case 8: { // XZ
                    restoreSection();
                    break;
                }
                case 9: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // XZ
                    restoreSection();
                    break;
                }
                case 11: { // ������ 2
                    break;
                }
                case 12: { // ������ 2 (���������)
                    break;
                }
                case 13: { // XZ
                    restoreSection();
                    break;
                }
                case 14: { // ������ 3
                    break;
                }
                case 15: { // ������ 3 (���������)
                    break;
                }
                case 16: { // XZ
                    restoreSection();
                    break;
                }
                case 17: { // ������ ������� (�������)
                    if (child == null) {
                        child = new RightRotate(); 
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
                    restoreSection();
                    break;
                }
                case 20: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
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
                case 23: { // ������ 1
                    break;
                }
                case 24: { // ������ 1 (���������)
                    break;
                }
                case 25: { // XZ
                    restoreSection();
                    break;
                }
                case 26: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 27: { // XZ
                    restoreSection();
                    break;
                }
                case 28: { // ������ 2
                    break;
                }
                case 29: { // ������ 2 (���������)
                    break;
                }
                case 30: { // XZ
                    restoreSection();
                    break;
                }
                case 31: { // ������ 3
                    break;
                }
                case 32: { // ������ 3 (���������)
                    break;
                }
                case 33: { // XZ
                    restoreSection();
                    break;
                }
                case 34: { // ������ ������� (�������)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 35: { // XZ
                    restoreSection();
                    break;
                }
                case 36: { // XZ
                    restoreSection();
                    break;
                }
                case 37: { // ����� ������� (�������)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 38: { // XZ
                    restoreSection();
                    break;
                }
                case 39: { // XZ
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
                case 2: { // ������� ����
                    if (stack.popBoolean()) {
                        state = 4; // �������� �� � ����� ����� (���������)
                    } else {
                        state = 1; // XZ
                    }
                    break;
                }
                case 3: { // �������� �� � ����� �����
                    state = 2; // ������� ����
                    break;
                }
                case 4: { // �������� �� � ����� ����� (���������)
                    if (stack.popBoolean()) {
                        state = 12; // ������ 2 (���������)
                    } else {
                        state = 29; // ������ 2 (���������)
                    }
                    break;
                }
                case 5: { // XZ
                    state = 3; // �������� �� � ����� �����
                    break;
                }
                case 6: { // ������ 1
                    state = 5; // XZ
                    break;
                }
                case 7: { // ������ 1 (���������)
                    if (stack.popBoolean()) {
                        state = 10; // XZ
                    } else {
                        state = 6; // ������ 1
                    }
                    break;
                }
                case 8: { // XZ
                    state = 6; // ������ 1
                    break;
                }
                case 9: { // ����� ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // XZ
                    }
                    break;
                }
                case 10: { // XZ
                    state = 9; // ����� ������� (�������)
                    break;
                }
                case 11: { // ������ 2
                    state = 7; // ������ 1 (���������)
                    break;
                }
                case 12: { // ������ 2 (���������)
                    if (stack.popBoolean()) {
                        state = 13; // XZ
                    } else {
                        state = 21; // XZ
                    }
                    break;
                }
                case 13: { // XZ
                    state = 11; // ������ 2
                    break;
                }
                case 14: { // ������ 3
                    state = 11; // ������ 2
                    break;
                }
                case 15: { // ������ 3 (���������)
                    if (stack.popBoolean()) {
                        state = 18; // XZ
                    } else {
                        state = 14; // ������ 3
                    }
                    break;
                }
                case 16: { // XZ
                    state = 14; // ������ 3
                    break;
                }
                case 17: { // ������ ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 16; // XZ
                    }
                    break;
                }
                case 18: { // XZ
                    state = 17; // ������ ������� (�������)
                    break;
                }
                case 19: { // XZ
                    state = 15; // ������ 3 (���������)
                    break;
                }
                case 20: { // ����� ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // XZ
                    }
                    break;
                }
                case 21: { // XZ
                    state = 20; // ����� ������� (�������)
                    break;
                }
                case 22: { // XZ
                    state = 3; // �������� �� � ����� �����
                    break;
                }
                case 23: { // ������ 1
                    state = 22; // XZ
                    break;
                }
                case 24: { // ������ 1 (���������)
                    if (stack.popBoolean()) {
                        state = 27; // XZ
                    } else {
                        state = 23; // ������ 1
                    }
                    break;
                }
                case 25: { // XZ
                    state = 23; // ������ 1
                    break;
                }
                case 26: { // ����� ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 25; // XZ
                    }
                    break;
                }
                case 27: { // XZ
                    state = 26; // ����� ������� (�������)
                    break;
                }
                case 28: { // ������ 2
                    state = 24; // ������ 1 (���������)
                    break;
                }
                case 29: { // ������ 2 (���������)
                    if (stack.popBoolean()) {
                        state = 30; // XZ
                    } else {
                        state = 38; // XZ
                    }
                    break;
                }
                case 30: { // XZ
                    state = 28; // ������ 2
                    break;
                }
                case 31: { // ������ 3
                    state = 28; // ������ 2
                    break;
                }
                case 32: { // ������ 3 (���������)
                    if (stack.popBoolean()) {
                        state = 35; // XZ
                    } else {
                        state = 31; // ������ 3
                    }
                    break;
                }
                case 33: { // XZ
                    state = 31; // ������ 3
                    break;
                }
                case 34: { // ������ ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 33; // XZ
                    }
                    break;
                }
                case 35: { // XZ
                    state = 34; // ������ ������� (�������)
                    break;
                }
                case 36: { // XZ
                    state = 32; // ������ 3 (���������)
                    break;
                }
                case 37: { // ����� ������� (�������)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 36; // XZ
                    }
                    break;
                }
                case 38: { // XZ
                    state = 37; // ����� ������� (�������)
                    break;
                }
                case 39: { // XZ
                    state = 2; // ������� ����
                    break;
                }
                case END_STATE: { // ��������� ���������
                    state = 39; // XZ
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
                case 9: { // ����� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 17: { // ������ ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 20: { // ����� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 26: { // ����� ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 34: { // ������ ������� (�������)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 37: { // ����� ������� (�������)
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
                case 9: { // ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 17: { // ������ ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 20: { // ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 26: { // ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 34: { // ������ ������� (�������)
                    child.drawState(); 
                    break;
                }
                case 37: { // ����� ������� (�������)
                    child.drawState(); 
                    break;
                }
            }
        }
    }
}
