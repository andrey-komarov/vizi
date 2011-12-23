package ru.ifmo.vizi.rbtree;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class RBTree extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public RBTree(Locale locale) {
        super("ru.ifmo.vizi.rbtree.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экземпляр апплета.
          */
        public RBTreeVisualizer visualizer = null;

        /**
          * Корень дерева.
          */
        public Point root = null;

        /**
          * Флаг для выхода из цикла.
          */
        public boolean flag = true;

        /**
          * Текущий узел.
          */
        public Point x = null;

        /**
          * Узел для различных операций.
          */
        public Point z = null;

        /**
          * Переменная.
          */
        public int key = 0;

        /**
          *  (Процедура LeftRotate).
          */
        public Point LeftRotate_y;

        /**
          *  (Процедура RightRotate).
          */
        public Point RightRotate_y;

        /**
          *  (Процедура RBInsert).
          */
        public Point RBInsert_y;

        /**
          *  (Процедура RBInsert).
          */
        public Point RBInsert_x2;

        /**
          * Текущий узел (Процедура RBInsertFixup).
          */
        public Point RBInsertFixup_y;

        /**
          * Текущий узел (Процедура RBDelete).
          */
        public Point RBDelete_x2;

        /**
          * Текущий узел (Процедура RBDelete).
          */
        public Point RBDelete_y;

        /**
          * Вспомогательная вершина (Процедура RBDeleteFixup).
          */
        public Point RBDeleteFixup_w;

        public String toString() {
            	return "";
        }
    }

    /**
      * Левый поворот.
      */
    private final class LeftRotate extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 14;

        /**
          * Конструктор.
          */
        public LeftRotate() {
            super( 
                "LeftRotate", 
                0, // Номер начального состояния 
                14, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация нужных вершин", 
                    "Если нет левого поддерева", 
                    "Если нет левого поддерева (окончание)", 
                    "Родитель левого у ставим нашу", 
                    "Переносим родителя z в y", 
                    "Если родитель z == null", 
                    "Если родитель z == null (окончание)", 
                    "XZ", 
                    "Если x левый сын", 
                    "Если x левый сын (окончание)", 
                    "XZ", 
                    "XZ", 
                    "Переносим родителя z в y", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация нужных вершин 
                    -1, // Если нет левого поддерева 
                    -1, // Если нет левого поддерева (окончание) 
                    -1, // Родитель левого у ставим нашу 
                    -1, // Переносим родителя z в y 
                    -1, // Если родитель z == null 
                    -1, // Если родитель z == null (окончание) 
                    -1, // XZ 
                    -1, // Если x левый сын 
                    -1, // Если x левый сын (окончание) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Переносим родителя z в y 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Инициализация нужных вершин
                    break;
                }
                case 1: { // Инициализация нужных вершин
                    state = 2; // Если нет левого поддерева
                    break;
                }
                case 2: { // Если нет левого поддерева
                    if (d.LeftRotate_y.left != null) {
                        state = 4; // Родитель левого у ставим нашу
                    } else {
                        stack.pushBoolean(false); 
                        state = 3; // Если нет левого поддерева (окончание)
                    }
                    break;
                }
                case 3: { // Если нет левого поддерева (окончание)
                    state = 5; // Переносим родителя z в y
                    break;
                }
                case 4: { // Родитель левого у ставим нашу
                    stack.pushBoolean(true); 
                    state = 3; // Если нет левого поддерева (окончание)
                    break;
                }
                case 5: { // Переносим родителя z в y
                    state = 6; // Если родитель z == null
                    break;
                }
                case 6: { // Если родитель z == null
                    if (d.z.p == null) {
                        state = 8; // XZ
                    } else {
                        state = 9; // Если x левый сын
                    }
                    break;
                }
                case 7: { // Если родитель z == null (окончание)
                    state = 13; // Переносим родителя z в y
                    break;
                }
                case 8: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // Если родитель z == null (окончание)
                    break;
                }
                case 9: { // Если x левый сын
                    if (d.z == d.z.p.left) {
                        state = 11; // XZ
                    } else {
                        state = 12; // XZ
                    }
                    break;
                }
                case 10: { // Если x левый сын (окончание)
                    stack.pushBoolean(false); 
                    state = 7; // Если родитель z == null (окончание)
                    break;
                }
                case 11: { // XZ
                    stack.pushBoolean(true); 
                    state = 10; // Если x левый сын (окончание)
                    break;
                }
                case 12: { // XZ
                    stack.pushBoolean(false); 
                    state = 10; // Если x левый сын (окончание)
                    break;
                }
                case 13: { // Переносим родителя z в y
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    startSection();
                    storeField(d, "LeftRotate_y");
                    				d.LeftRotate_y = d.z.right;
                    storeField(d.z, "right");
                    				d.z.right = d.LeftRotate_y.left;
                    break;
                }
                case 2: { // Если нет левого поддерева
                    break;
                }
                case 3: { // Если нет левого поддерева (окончание)
                    break;
                }
                case 4: { // Родитель левого у ставим нашу
                    startSection();
                    storeField(d.LeftRotate_y.left, "p");
                    						d.LeftRotate_y.left.p = d.z;
                    break;
                }
                case 5: { // Переносим родителя z в y
                    startSection();
                    				d.LeftRotate_y.p = d.z.p;
                    break;
                }
                case 6: { // Если родитель z == null
                    break;
                }
                case 7: { // Если родитель z == null (окончание)
                    break;
                }
                case 8: { // XZ
                    startSection();
                    storeField(d, "root");
                    					d.root = d.LeftRotate_y;
                    break;
                }
                case 9: { // Если x левый сын
                    break;
                }
                case 10: { // Если x левый сын (окончание)
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
                case 13: { // Переносим родителя z в y
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    restoreSection();
                    break;
                }
                case 2: { // Если нет левого поддерева
                    break;
                }
                case 3: { // Если нет левого поддерева (окончание)
                    break;
                }
                case 4: { // Родитель левого у ставим нашу
                    restoreSection();
                    break;
                }
                case 5: { // Переносим родителя z в y
                    restoreSection();
                    break;
                }
                case 6: { // Если родитель z == null
                    break;
                }
                case 7: { // Если родитель z == null (окончание)
                    break;
                }
                case 8: { // XZ
                    restoreSection();
                    break;
                }
                case 9: { // Если x левый сын
                    break;
                }
                case 10: { // Если x левый сын (окончание)
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
                case 13: { // Переносим родителя z в y
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    state = START_STATE; 
                    break;
                }
                case 2: { // Если нет левого поддерева
                    state = 1; // Инициализация нужных вершин
                    break;
                }
                case 3: { // Если нет левого поддерева (окончание)
                    if (stack.popBoolean()) {
                        state = 4; // Родитель левого у ставим нашу
                    } else {
                        state = 2; // Если нет левого поддерева
                    }
                    break;
                }
                case 4: { // Родитель левого у ставим нашу
                    state = 2; // Если нет левого поддерева
                    break;
                }
                case 5: { // Переносим родителя z в y
                    state = 3; // Если нет левого поддерева (окончание)
                    break;
                }
                case 6: { // Если родитель z == null
                    state = 5; // Переносим родителя z в y
                    break;
                }
                case 7: { // Если родитель z == null (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // XZ
                    } else {
                        state = 10; // Если x левый сын (окончание)
                    }
                    break;
                }
                case 8: { // XZ
                    state = 6; // Если родитель z == null
                    break;
                }
                case 9: { // Если x левый сын
                    state = 6; // Если родитель z == null
                    break;
                }
                case 10: { // Если x левый сын (окончание)
                    if (stack.popBoolean()) {
                        state = 11; // XZ
                    } else {
                        state = 12; // XZ
                    }
                    break;
                }
                case 11: { // XZ
                    state = 9; // Если x левый сын
                    break;
                }
                case 12: { // XZ
                    state = 9; // Если x левый сын
                    break;
                }
                case 13: { // Переносим родителя z в y
                    state = 7; // Если родитель z == null (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 13; // Переносим родителя z в y
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
            }
        }
    }

    /**
      * Правый поворот.
      */
    private final class RightRotate extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 14;

        /**
          * Конструктор.
          */
        public RightRotate() {
            super( 
                "RightRotate", 
                0, // Номер начального состояния 
                14, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация нужных вершин", 
                    "Если нет правого поддерева", 
                    "Если нет правого поддерева (окончание)", 
                    "Step", 
                    "Переносим родителя z в y", 
                    "Если родитель z == null", 
                    "Если родитель z == null (окончание)", 
                    "XZ", 
                    "Если x правый сын", 
                    "Если x правый сын (окончание)", 
                    "XZ", 
                    "XZ", 
                    "Переносим родителя z в y", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация нужных вершин 
                    -1, // Если нет правого поддерева 
                    -1, // Если нет правого поддерева (окончание) 
                    -1, // Step 
                    -1, // Переносим родителя z в y 
                    -1, // Если родитель z == null 
                    -1, // Если родитель z == null (окончание) 
                    -1, // XZ 
                    -1, // Если x правый сын 
                    -1, // Если x правый сын (окончание) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Переносим родителя z в y 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Инициализация нужных вершин
                    break;
                }
                case 1: { // Инициализация нужных вершин
                    state = 2; // Если нет правого поддерева
                    break;
                }
                case 2: { // Если нет правого поддерева
                    if (d.RightRotate_y.right != null) {
                        state = 4; // Step
                    } else {
                        stack.pushBoolean(false); 
                        state = 3; // Если нет правого поддерева (окончание)
                    }
                    break;
                }
                case 3: { // Если нет правого поддерева (окончание)
                    state = 5; // Переносим родителя z в y
                    break;
                }
                case 4: { // Step
                    stack.pushBoolean(true); 
                    state = 3; // Если нет правого поддерева (окончание)
                    break;
                }
                case 5: { // Переносим родителя z в y
                    state = 6; // Если родитель z == null
                    break;
                }
                case 6: { // Если родитель z == null
                    if (d.z.p == null) {
                        state = 8; // XZ
                    } else {
                        state = 9; // Если x правый сын
                    }
                    break;
                }
                case 7: { // Если родитель z == null (окончание)
                    state = 13; // Переносим родителя z в y
                    break;
                }
                case 8: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // Если родитель z == null (окончание)
                    break;
                }
                case 9: { // Если x правый сын
                    if (d.z == d.z.p.right) {
                        state = 11; // XZ
                    } else {
                        state = 12; // XZ
                    }
                    break;
                }
                case 10: { // Если x правый сын (окончание)
                    stack.pushBoolean(false); 
                    state = 7; // Если родитель z == null (окончание)
                    break;
                }
                case 11: { // XZ
                    stack.pushBoolean(true); 
                    state = 10; // Если x правый сын (окончание)
                    break;
                }
                case 12: { // XZ
                    stack.pushBoolean(false); 
                    state = 10; // Если x правый сын (окончание)
                    break;
                }
                case 13: { // Переносим родителя z в y
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    startSection();
                    storeField(d, "RightRotate_y");
                    				d.RightRotate_y = d.z.left;
                    storeField(d.z, "left");
                    				d.z.left = d.RightRotate_y.right;
                    break;
                }
                case 2: { // Если нет правого поддерева
                    break;
                }
                case 3: { // Если нет правого поддерева (окончание)
                    break;
                }
                case 4: { // Step
                    startSection();
                    storeField(d.RightRotate_y.right, "p");
                    						d.RightRotate_y.right.p = d.z;
                    break;
                }
                case 5: { // Переносим родителя z в y
                    startSection();
                    				d.RightRotate_y.p = d.z.p;
                    break;
                }
                case 6: { // Если родитель z == null
                    break;
                }
                case 7: { // Если родитель z == null (окончание)
                    break;
                }
                case 8: { // XZ
                    startSection();
                    storeField(d, "root");
                    					d.root = d.RightRotate_y;
                    break;
                }
                case 9: { // Если x правый сын
                    break;
                }
                case 10: { // Если x правый сын (окончание)
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
                case 13: { // Переносим родителя z в y
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    restoreSection();
                    break;
                }
                case 2: { // Если нет правого поддерева
                    break;
                }
                case 3: { // Если нет правого поддерева (окончание)
                    break;
                }
                case 4: { // Step
                    restoreSection();
                    break;
                }
                case 5: { // Переносим родителя z в y
                    restoreSection();
                    break;
                }
                case 6: { // Если родитель z == null
                    break;
                }
                case 7: { // Если родитель z == null (окончание)
                    break;
                }
                case 8: { // XZ
                    restoreSection();
                    break;
                }
                case 9: { // Если x правый сын
                    break;
                }
                case 10: { // Если x правый сын (окончание)
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
                case 13: { // Переносим родителя z в y
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    state = START_STATE; 
                    break;
                }
                case 2: { // Если нет правого поддерева
                    state = 1; // Инициализация нужных вершин
                    break;
                }
                case 3: { // Если нет правого поддерева (окончание)
                    if (stack.popBoolean()) {
                        state = 4; // Step
                    } else {
                        state = 2; // Если нет правого поддерева
                    }
                    break;
                }
                case 4: { // Step
                    state = 2; // Если нет правого поддерева
                    break;
                }
                case 5: { // Переносим родителя z в y
                    state = 3; // Если нет правого поддерева (окончание)
                    break;
                }
                case 6: { // Если родитель z == null
                    state = 5; // Переносим родителя z в y
                    break;
                }
                case 7: { // Если родитель z == null (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // XZ
                    } else {
                        state = 10; // Если x правый сын (окончание)
                    }
                    break;
                }
                case 8: { // XZ
                    state = 6; // Если родитель z == null
                    break;
                }
                case 9: { // Если x правый сын
                    state = 6; // Если родитель z == null
                    break;
                }
                case 10: { // Если x правый сын (окончание)
                    if (stack.popBoolean()) {
                        state = 11; // XZ
                    } else {
                        state = 12; // XZ
                    }
                    break;
                }
                case 11: { // XZ
                    state = 9; // Если x правый сын
                    break;
                }
                case 12: { // XZ
                    state = 9; // Если x правый сын
                    break;
                }
                case 13: { // Переносим родителя z в y
                    state = 7; // Если родитель z == null (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 13; // Переносим родителя z в y
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
            }
        }
    }

    /**
      * Поиск.
      */
    private final class findPoint extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 13;

        /**
          * Конструктор.
          */
        public findPoint() {
            super( 
                "findPoint", 
                0, // Номер начального состояния 
                13, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начинаем искать с корня", 
                    "Цикл", 
                    "Если нашли", 
                    "Если нашли (окончание)", 
                    "XZ", 
                    "Если нашли", 
                    "Если нашли (окончание)", 
                    "XZ", 
                    "XZ", 
                    "Если нашли", 
                    "Если нашли (окончание)", 
                    "XZ", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начинаем искать с корня 
                    -1, // Цикл 
                    0, // Если нашли 
                    -1, // Если нашли (окончание) 
                    -1, // XZ 
                    0, // Если нашли 
                    -1, // Если нашли (окончание) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Если нашли 
                    -1, // Если нашли (окончание) 
                    0, // XZ 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Начинаем искать с корня
                    break;
                }
                case 1: { // Начинаем искать с корня
                    stack.pushBoolean(false); 
                    state = 2; // Цикл
                    break;
                }
                case 2: { // Цикл
                    if (d.x != null && flag) {
                        state = 3; // Если нашли
                    } else {
                        state = 10; // Если нашли
                    }
                    break;
                }
                case 3: { // Если нашли
                    if (d.x.key == d.key) {
                        state = 5; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 4; // Если нашли (окончание)
                    }
                    break;
                }
                case 4: { // Если нашли (окончание)
                    state = 6; // Если нашли
                    break;
                }
                case 5: { // XZ
                    stack.pushBoolean(true); 
                    state = 4; // Если нашли (окончание)
                    break;
                }
                case 6: { // Если нашли
                    if (d.x.key > d.key) {
                        state = 8; // XZ
                    } else {
                        state = 9; // XZ
                    }
                    break;
                }
                case 7: { // Если нашли (окончание)
                    stack.pushBoolean(true); 
                    state = 2; // Цикл
                    break;
                }
                case 8: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // Если нашли (окончание)
                    break;
                }
                case 9: { // XZ
                    stack.pushBoolean(false); 
                    state = 7; // Если нашли (окончание)
                    break;
                }
                case 10: { // Если нашли
                    if (flag) {
                        state = 12; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 11; // Если нашли (окончание)
                    }
                    break;
                }
                case 11: { // Если нашли (окончание)
                    state = END_STATE; 
                    break;
                }
                case 12: { // XZ
                    stack.pushBoolean(true); 
                    state = 11; // Если нашли (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начинаем искать с корня
                    startSection();
                    storeField(d, "x");
                    				d.x = d.root;
                    				d.flag = true;
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Если нашли
                    break;
                }
                case 4: { // Если нашли (окончание)
                    break;
                }
                case 5: { // XZ
                    startSection();
                    					flag = false;
                    break;
                }
                case 6: { // Если нашли
                    break;
                }
                case 7: { // Если нашли (окончание)
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
                case 10: { // Если нашли
                    break;
                }
                case 11: { // Если нашли (окончание)
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Начинаем искать с корня
                    restoreSection();
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Если нашли
                    break;
                }
                case 4: { // Если нашли (окончание)
                    break;
                }
                case 5: { // XZ
                    restoreSection();
                    break;
                }
                case 6: { // Если нашли
                    break;
                }
                case 7: { // Если нашли (окончание)
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
                case 10: { // Если нашли
                    break;
                }
                case 11: { // Если нашли (окончание)
                    break;
                }
                case 12: { // XZ
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Начинаем искать с корня
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл
                    if (stack.popBoolean()) {
                        state = 7; // Если нашли (окончание)
                    } else {
                        state = 1; // Начинаем искать с корня
                    }
                    break;
                }
                case 3: { // Если нашли
                    state = 2; // Цикл
                    break;
                }
                case 4: { // Если нашли (окончание)
                    if (stack.popBoolean()) {
                        state = 5; // XZ
                    } else {
                        state = 3; // Если нашли
                    }
                    break;
                }
                case 5: { // XZ
                    state = 3; // Если нашли
                    break;
                }
                case 6: { // Если нашли
                    state = 4; // Если нашли (окончание)
                    break;
                }
                case 7: { // Если нашли (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // XZ
                    } else {
                        state = 9; // XZ
                    }
                    break;
                }
                case 8: { // XZ
                    state = 6; // Если нашли
                    break;
                }
                case 9: { // XZ
                    state = 6; // Если нашли
                    break;
                }
                case 10: { // Если нашли
                    state = 2; // Цикл
                    break;
                }
                case 11: { // Если нашли (окончание)
                    if (stack.popBoolean()) {
                        state = 12; // XZ
                    } else {
                        state = 10; // Если нашли
                    }
                    break;
                }
                case 12: { // XZ
                    state = 10; // Если нашли
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 11; // Если нашли (окончание)
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 3: { // Если нашли
                    if (d.x.key == d.key) {
                        comment = RBTree.this.getComment("findPoint.If.true"); 
                    } else {
                        comment = RBTree.this.getComment("findPoint.If.false"); 
                    }
                    args = new Object[]{new Integer(d.key)}; 
                    break;
                }
                case 6: { // Если нашли
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
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
            }
        }
    }

    /**
      * Вставка.
      */
    private final class RBInsert extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 18;

        /**
          * Конструктор.
          */
        public RBInsert() {
            super( 
                "RBInsert", 
                0, // Номер начального состояния 
                18, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация нужных вершин", 
                    "Цикл для нахождения места для вставки", 
                    "XZ", 
                    "Сравниваем", 
                    "Сравниваем (окончание)", 
                    "XZ", 
                    "XZ", 
                    "Устанавливаем отца Z", 
                    "не пусто ли", 
                    "не пусто ли (окончание)", 
                    "XZ", 
                    "проверяем", 
                    "проверяем (окончание)", 
                    "XZ", 
                    "XZ", 
                    "Назначение", 
                    "Восстанавливаем красно-черные свойства (автомат)", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация нужных вершин 
                    -1, // Цикл для нахождения места для вставки 
                    -1, // XZ 
                    0, // Сравниваем 
                    -1, // Сравниваем (окончание) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Устанавливаем отца Z 
                    -1, // не пусто ли 
                    -1, // не пусто ли (окончание) 
                    -1, // XZ 
                    0, // проверяем 
                    -1, // проверяем (окончание) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Назначение 
                    CALL_AUTO_LEVEL, // Восстанавливаем красно-черные свойства (автомат) 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Инициализация нужных вершин
                    break;
                }
                case 1: { // Инициализация нужных вершин
                    stack.pushBoolean(false); 
                    state = 2; // Цикл для нахождения места для вставки
                    break;
                }
                case 2: { // Цикл для нахождения места для вставки
                    if (d.RBInsert_x2 != null) {
                        state = 3; // XZ
                    } else {
                        state = 8; // Устанавливаем отца Z
                    }
                    break;
                }
                case 3: { // XZ
                    state = 4; // Сравниваем
                    break;
                }
                case 4: { // Сравниваем
                    if (d.RBInsert_x2.key > d.z.key) {
                        state = 6; // XZ
                    } else {
                        state = 7; // XZ
                    }
                    break;
                }
                case 5: { // Сравниваем (окончание)
                    stack.pushBoolean(true); 
                    state = 2; // Цикл для нахождения места для вставки
                    break;
                }
                case 6: { // XZ
                    stack.pushBoolean(true); 
                    state = 5; // Сравниваем (окончание)
                    break;
                }
                case 7: { // XZ
                    stack.pushBoolean(false); 
                    state = 5; // Сравниваем (окончание)
                    break;
                }
                case 8: { // Устанавливаем отца Z
                    state = 9; // не пусто ли
                    break;
                }
                case 9: { // не пусто ли
                    if (d.RBInsert_y == null) {
                        state = 11; // XZ
                    } else {
                        state = 12; // проверяем
                    }
                    break;
                }
                case 10: { // не пусто ли (окончание)
                    state = 16; // Назначение
                    break;
                }
                case 11: { // XZ
                    stack.pushBoolean(true); 
                    state = 10; // не пусто ли (окончание)
                    break;
                }
                case 12: { // проверяем
                    if (d.z.key < d.RBInsert_y.key) {
                        state = 14; // XZ
                    } else {
                        state = 15; // XZ
                    }
                    break;
                }
                case 13: { // проверяем (окончание)
                    stack.pushBoolean(false); 
                    state = 10; // не пусто ли (окончание)
                    break;
                }
                case 14: { // XZ
                    stack.pushBoolean(true); 
                    state = 13; // проверяем (окончание)
                    break;
                }
                case 15: { // XZ
                    stack.pushBoolean(false); 
                    state = 13; // проверяем (окончание)
                    break;
                }
                case 16: { // Назначение
                    state = 17; // Восстанавливаем красно-черные свойства (автомат)
                    break;
                }
                case 17: { // Восстанавливаем красно-черные свойства (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = END_STATE; 
                    }
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    startSection();
                    storeField(d, "RBInsert_x2");
                    				d.RBInsert_x2 = root;
                    storeField(d, "RBInsert_y");
                    				d.RBInsert_y = null;
                    break;
                }
                case 2: { // Цикл для нахождения места для вставки
                    break;
                }
                case 3: { // XZ
                    startSection();
                    storeField(d, "RBInsert_y");
                    					d.RBInsert_y = d.RBInsert_x2;
                    break;
                }
                case 4: { // Сравниваем
                    break;
                }
                case 5: { // Сравниваем (окончание)
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
                case 8: { // Устанавливаем отца Z
                    startSection();
                    storeField(d.z, "p");
                    				d.z.p = d.RBInsert_y;
                    break;
                }
                case 9: { // не пусто ли
                    break;
                }
                case 10: { // не пусто ли (окончание)
                    break;
                }
                case 11: { // XZ
                    startSection();
                    storeField(d, "root");
                    				d.root = d.z;
                    break;
                }
                case 12: { // проверяем
                    break;
                }
                case 13: { // проверяем (окончание)
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
                case 16: { // Назначение
                    startSection();
                    storeField(d.z, "left");
                    				d.z.left = null;
                    storeField(d.z, "right");
                    				d.z.right = null;
                    storeField(d.z, "color");
                    				d.z.color = true;
                    break;
                }
                case 17: { // Восстанавливаем красно-черные свойства (автомат)
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    restoreSection();
                    break;
                }
                case 2: { // Цикл для нахождения места для вставки
                    break;
                }
                case 3: { // XZ
                    restoreSection();
                    break;
                }
                case 4: { // Сравниваем
                    break;
                }
                case 5: { // Сравниваем (окончание)
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
                case 8: { // Устанавливаем отца Z
                    restoreSection();
                    break;
                }
                case 9: { // не пусто ли
                    break;
                }
                case 10: { // не пусто ли (окончание)
                    break;
                }
                case 11: { // XZ
                    restoreSection();
                    break;
                }
                case 12: { // проверяем
                    break;
                }
                case 13: { // проверяем (окончание)
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
                case 16: { // Назначение
                    restoreSection();
                    break;
                }
                case 17: { // Восстанавливаем красно-черные свойства (автомат)
                    if (child == null) {
                        child = new RBInsertFixup(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл для нахождения места для вставки
                    if (stack.popBoolean()) {
                        state = 5; // Сравниваем (окончание)
                    } else {
                        state = 1; // Инициализация нужных вершин
                    }
                    break;
                }
                case 3: { // XZ
                    state = 2; // Цикл для нахождения места для вставки
                    break;
                }
                case 4: { // Сравниваем
                    state = 3; // XZ
                    break;
                }
                case 5: { // Сравниваем (окончание)
                    if (stack.popBoolean()) {
                        state = 6; // XZ
                    } else {
                        state = 7; // XZ
                    }
                    break;
                }
                case 6: { // XZ
                    state = 4; // Сравниваем
                    break;
                }
                case 7: { // XZ
                    state = 4; // Сравниваем
                    break;
                }
                case 8: { // Устанавливаем отца Z
                    state = 2; // Цикл для нахождения места для вставки
                    break;
                }
                case 9: { // не пусто ли
                    state = 8; // Устанавливаем отца Z
                    break;
                }
                case 10: { // не пусто ли (окончание)
                    if (stack.popBoolean()) {
                        state = 11; // XZ
                    } else {
                        state = 13; // проверяем (окончание)
                    }
                    break;
                }
                case 11: { // XZ
                    state = 9; // не пусто ли
                    break;
                }
                case 12: { // проверяем
                    state = 9; // не пусто ли
                    break;
                }
                case 13: { // проверяем (окончание)
                    if (stack.popBoolean()) {
                        state = 14; // XZ
                    } else {
                        state = 15; // XZ
                    }
                    break;
                }
                case 14: { // XZ
                    state = 12; // проверяем
                    break;
                }
                case 15: { // XZ
                    state = 12; // проверяем
                    break;
                }
                case 16: { // Назначение
                    state = 10; // не пусто ли (окончание)
                    break;
                }
                case 17: { // Восстанавливаем красно-черные свойства (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 16; // Назначение
                    }
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 17; // Восстанавливаем красно-черные свойства (автомат)
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 4: { // Сравниваем
                    if (d.RBInsert_x2.key > d.z.key) {
                        comment = RBTree.this.getComment("RBInsert.If.true"); 
                    } else {
                        comment = RBTree.this.getComment("RBInsert.If.false"); 
                    }
                    args = new Object[]{new Integer(d.RBInsert_x2.key), new Integer(d.z.key)}; 
                    break;
                }
                case 12: { // проверяем
                    if (d.z.key < d.RBInsert_y.key) {
                        comment = RBTree.this.getComment("RBInsert.If.true"); 
                    } else {
                        comment = RBTree.this.getComment("RBInsert.If.false"); 
                    }
                    args = new Object[]{new Integer(d.z.key), new Integer(d.RBInsert_y.key)}; 
                    break;
                }
                case 17: { // Восстанавливаем красно-черные свойства (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case 17: { // Восстанавливаем красно-черные свойства (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Восстанавливаем красно-черные свойства.
      */
    private final class RBInsertFixup extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 27;

        /**
          * Конструктор.
          */
        public RBInsertFixup() {
            super( 
                "RBInsertFixup", 
                0, // Номер начального состояния 
                27, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Главный цикл", 
                    "Проверяем является ли отец z левым сыном своего отца", 
                    "Проверяем является ли отец z левым сыном своего отца (окончание)", 
                    "Назначение", 
                    "Проверяем является ли y красным", 
                    "Проверяем является ли y красным (окончание)", 
                    "Случай 1", 
                    "Проверяем является ли y красным", 
                    "Проверяем является ли y красным (окончание)", 
                    "Случай 2", 
                    "Левый поворот (автомат)", 
                    "Случай 3", 
                    "Правый поворот (автомат)", 
                    "Назначение", 
                    "Проверяем является ли y красным", 
                    "Проверяем является ли y красным (окончание)", 
                    "Случай 1", 
                    "Проверяем является ли y красным", 
                    "Проверяем является ли y красным (окончание)", 
                    "Случай 2", 
                    "Правый поворот (автомат)", 
                    "Случай 3", 
                    "Левый поворот (автомат)", 
                    "Проверяем является ли y красным", 
                    "Проверяем является ли y красным (окончание)", 
                    "XZ", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Главный цикл 
                    0, // Проверяем является ли отец z левым сыном своего отца 
                    -1, // Проверяем является ли отец z левым сыном своего отца (окончание) 
                    -1, // Назначение 
                    -1, // Проверяем является ли y красным 
                    -1, // Проверяем является ли y красным (окончание) 
                    0, // Случай 1 
                    -1, // Проверяем является ли y красным 
                    -1, // Проверяем является ли y красным (окончание) 
                    0, // Случай 2 
                    CALL_AUTO_LEVEL, // Левый поворот (автомат) 
                    0, // Случай 3 
                    CALL_AUTO_LEVEL, // Правый поворот (автомат) 
                    -1, // Назначение 
                    -1, // Проверяем является ли y красным 
                    -1, // Проверяем является ли y красным (окончание) 
                    0, // Случай 1 
                    -1, // Проверяем является ли y красным 
                    -1, // Проверяем является ли y красным (окончание) 
                    0, // Случай 2 
                    CALL_AUTO_LEVEL, // Правый поворот (автомат) 
                    0, // Случай 3 
                    CALL_AUTO_LEVEL, // Левый поворот (автомат) 
                    -1, // Проверяем является ли y красным 
                    -1, // Проверяем является ли y красным (окончание) 
                    0, // XZ 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Главный цикл
                    break;
                }
                case 1: { // Главный цикл
                    if (z.p.color) {
                        state = 2; // Проверяем является ли отец z левым сыном своего отца
                    } else {
                        state = 24; // Проверяем является ли y красным
                    }
                    break;
                }
                case 2: { // Проверяем является ли отец z левым сыном своего отца
                    if (d.z.p == d.z.p.p.left) {
                        state = 4; // Назначение
                    } else {
                        state = 14; // Назначение
                    }
                    break;
                }
                case 3: { // Проверяем является ли отец z левым сыном своего отца (окончание)
                    stack.pushBoolean(true); 
                    state = 1; // Главный цикл
                    break;
                }
                case 4: { // Назначение
                    state = 5; // Проверяем является ли y красным
                    break;
                }
                case 5: { // Проверяем является ли y красным
                    if (d.RBInsertFixup_y.color) {
                        state = 7; // Случай 1
                    } else {
                        state = 8; // Проверяем является ли y красным
                    }
                    break;
                }
                case 6: { // Проверяем является ли y красным (окончание)
                    stack.pushBoolean(true); 
                    state = 3; // Проверяем является ли отец z левым сыном своего отца (окончание)
                    break;
                }
                case 7: { // Случай 1
                    stack.pushBoolean(true); 
                    state = 6; // Проверяем является ли y красным (окончание)
                    break;
                }
                case 8: { // Проверяем является ли y красным
                    if (d.z == d.z.p.right) {
                        state = 10; // Случай 2
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // Проверяем является ли y красным (окончание)
                    }
                    break;
                }
                case 9: { // Проверяем является ли y красным (окончание)
                    state = 12; // Случай 3
                    break;
                }
                case 10: { // Случай 2
                    state = 11; // Левый поворот (автомат)
                    break;
                }
                case 11: { // Левый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 9; // Проверяем является ли y красным (окончание)
                    }
                    break;
                }
                case 12: { // Случай 3
                    state = 13; // Правый поворот (автомат)
                    break;
                }
                case 13: { // Правый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 6; // Проверяем является ли y красным (окончание)
                    }
                    break;
                }
                case 14: { // Назначение
                    state = 15; // Проверяем является ли y красным
                    break;
                }
                case 15: { // Проверяем является ли y красным
                    if (d.RBInsertFixup_y.color) {
                        state = 17; // Случай 1
                    } else {
                        state = 18; // Проверяем является ли y красным
                    }
                    break;
                }
                case 16: { // Проверяем является ли y красным (окончание)
                    stack.pushBoolean(false); 
                    state = 3; // Проверяем является ли отец z левым сыном своего отца (окончание)
                    break;
                }
                case 17: { // Случай 1
                    stack.pushBoolean(true); 
                    state = 16; // Проверяем является ли y красным (окончание)
                    break;
                }
                case 18: { // Проверяем является ли y красным
                    if (d.z == d.z.p.left) {
                        state = 20; // Случай 2
                    } else {
                        stack.pushBoolean(false); 
                        state = 19; // Проверяем является ли y красным (окончание)
                    }
                    break;
                }
                case 19: { // Проверяем является ли y красным (окончание)
                    state = 22; // Случай 3
                    break;
                }
                case 20: { // Случай 2
                    state = 21; // Правый поворот (автомат)
                    break;
                }
                case 21: { // Правый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 19; // Проверяем является ли y красным (окончание)
                    }
                    break;
                }
                case 22: { // Случай 3
                    state = 23; // Левый поворот (автомат)
                    break;
                }
                case 23: { // Левый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 16; // Проверяем является ли y красным (окончание)
                    }
                    break;
                }
                case 24: { // Проверяем является ли y красным
                    if (d.root.color == true) {
                        state = 26; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 25; // Проверяем является ли y красным (окончание)
                    }
                    break;
                }
                case 25: { // Проверяем является ли y красным (окончание)
                    state = END_STATE; 
                    break;
                }
                case 26: { // XZ
                    stack.pushBoolean(true); 
                    state = 25; // Проверяем является ли y красным (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Главный цикл
                    break;
                }
                case 2: { // Проверяем является ли отец z левым сыном своего отца
                    break;
                }
                case 3: { // Проверяем является ли отец z левым сыном своего отца (окончание)
                    break;
                }
                case 4: { // Назначение
                    startSection();
                    storeField(d, "RBInsertFixup_y");
                    					d.RBInsertFixup_y = d.z.p.p.right;
                    break;
                }
                case 5: { // Проверяем является ли y красным
                    break;
                }
                case 6: { // Проверяем является ли y красным (окончание)
                    break;
                }
                case 7: { // Случай 1
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
                case 8: { // Проверяем является ли y красным
                    break;
                }
                case 9: { // Проверяем является ли y красным (окончание)
                    break;
                }
                case 10: { // Случай 2
                    startSection();
                    storeField(d, "z");
                    							d.z = d.z.p;
                    break;
                }
                case 11: { // Левый поворот (автомат)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 12: { // Случай 3
                    startSection();
                    storeField(d.z.p, "color");
                    						d.z.p.color = false;
                    storeField(d.z.p.p, "color");
                    						d.z.p.p.color = true;
                    						
                    storeField(d, "z");
                    						d.z = d.z.p.p;
                    break;
                }
                case 13: { // Правый поворот (автомат)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 14: { // Назначение
                    startSection();
                    storeField(d, "RBInsertFixup_y");
                    					d.RBInsertFixup_y = d.z.p.p.left;
                    break;
                }
                case 15: { // Проверяем является ли y красным
                    break;
                }
                case 16: { // Проверяем является ли y красным (окончание)
                    break;
                }
                case 17: { // Случай 1
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
                case 18: { // Проверяем является ли y красным
                    break;
                }
                case 19: { // Проверяем является ли y красным (окончание)
                    break;
                }
                case 20: { // Случай 2
                    startSection();
                    storeField(d, "z");
                    							d.z = d.z.p;
                    break;
                }
                case 21: { // Правый поворот (автомат)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 22: { // Случай 3
                    startSection();
                    storeField(d.z.p, "color");
                    						d.z.p.color = false;
                    storeField(d.z.p.p, "color");
                    						d.z.p.p.color = true;
                    storeField(d, "z");
                    						d.z = d.z.p.p;
                    break;
                }
                case 23: { // Левый поворот (автомат)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 24: { // Проверяем является ли y красным
                    break;
                }
                case 25: { // Проверяем является ли y красным (окончание)
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Главный цикл
                    break;
                }
                case 2: { // Проверяем является ли отец z левым сыном своего отца
                    break;
                }
                case 3: { // Проверяем является ли отец z левым сыном своего отца (окончание)
                    break;
                }
                case 4: { // Назначение
                    restoreSection();
                    break;
                }
                case 5: { // Проверяем является ли y красным
                    break;
                }
                case 6: { // Проверяем является ли y красным (окончание)
                    break;
                }
                case 7: { // Случай 1
                    restoreSection();
                    break;
                }
                case 8: { // Проверяем является ли y красным
                    break;
                }
                case 9: { // Проверяем является ли y красным (окончание)
                    break;
                }
                case 10: { // Случай 2
                    restoreSection();
                    break;
                }
                case 11: { // Левый поворот (автомат)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 12: { // Случай 3
                    restoreSection();
                    break;
                }
                case 13: { // Правый поворот (автомат)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 14: { // Назначение
                    restoreSection();
                    break;
                }
                case 15: { // Проверяем является ли y красным
                    break;
                }
                case 16: { // Проверяем является ли y красным (окончание)
                    break;
                }
                case 17: { // Случай 1
                    restoreSection();
                    break;
                }
                case 18: { // Проверяем является ли y красным
                    break;
                }
                case 19: { // Проверяем является ли y красным (окончание)
                    break;
                }
                case 20: { // Случай 2
                    restoreSection();
                    break;
                }
                case 21: { // Правый поворот (автомат)
                    if (child == null) {
                        child = new RightRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 22: { // Случай 3
                    restoreSection();
                    break;
                }
                case 23: { // Левый поворот (автомат)
                    if (child == null) {
                        child = new LeftRotate(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 24: { // Проверяем является ли y красным
                    break;
                }
                case 25: { // Проверяем является ли y красным (окончание)
                    break;
                }
                case 26: { // XZ
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Главный цикл
                    if (stack.popBoolean()) {
                        state = 3; // Проверяем является ли отец z левым сыном своего отца (окончание)
                    } else {
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // Проверяем является ли отец z левым сыном своего отца
                    state = 1; // Главный цикл
                    break;
                }
                case 3: { // Проверяем является ли отец z левым сыном своего отца (окончание)
                    if (stack.popBoolean()) {
                        state = 6; // Проверяем является ли y красным (окончание)
                    } else {
                        state = 16; // Проверяем является ли y красным (окончание)
                    }
                    break;
                }
                case 4: { // Назначение
                    state = 2; // Проверяем является ли отец z левым сыном своего отца
                    break;
                }
                case 5: { // Проверяем является ли y красным
                    state = 4; // Назначение
                    break;
                }
                case 6: { // Проверяем является ли y красным (окончание)
                    if (stack.popBoolean()) {
                        state = 7; // Случай 1
                    } else {
                        state = 13; // Правый поворот (автомат)
                    }
                    break;
                }
                case 7: { // Случай 1
                    state = 5; // Проверяем является ли y красным
                    break;
                }
                case 8: { // Проверяем является ли y красным
                    state = 5; // Проверяем является ли y красным
                    break;
                }
                case 9: { // Проверяем является ли y красным (окончание)
                    if (stack.popBoolean()) {
                        state = 11; // Левый поворот (автомат)
                    } else {
                        state = 8; // Проверяем является ли y красным
                    }
                    break;
                }
                case 10: { // Случай 2
                    state = 8; // Проверяем является ли y красным
                    break;
                }
                case 11: { // Левый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 10; // Случай 2
                    }
                    break;
                }
                case 12: { // Случай 3
                    state = 9; // Проверяем является ли y красным (окончание)
                    break;
                }
                case 13: { // Правый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 12; // Случай 3
                    }
                    break;
                }
                case 14: { // Назначение
                    state = 2; // Проверяем является ли отец z левым сыном своего отца
                    break;
                }
                case 15: { // Проверяем является ли y красным
                    state = 14; // Назначение
                    break;
                }
                case 16: { // Проверяем является ли y красным (окончание)
                    if (stack.popBoolean()) {
                        state = 17; // Случай 1
                    } else {
                        state = 23; // Левый поворот (автомат)
                    }
                    break;
                }
                case 17: { // Случай 1
                    state = 15; // Проверяем является ли y красным
                    break;
                }
                case 18: { // Проверяем является ли y красным
                    state = 15; // Проверяем является ли y красным
                    break;
                }
                case 19: { // Проверяем является ли y красным (окончание)
                    if (stack.popBoolean()) {
                        state = 21; // Правый поворот (автомат)
                    } else {
                        state = 18; // Проверяем является ли y красным
                    }
                    break;
                }
                case 20: { // Случай 2
                    state = 18; // Проверяем является ли y красным
                    break;
                }
                case 21: { // Правый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 20; // Случай 2
                    }
                    break;
                }
                case 22: { // Случай 3
                    state = 19; // Проверяем является ли y красным (окончание)
                    break;
                }
                case 23: { // Левый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 22; // Случай 3
                    }
                    break;
                }
                case 24: { // Проверяем является ли y красным
                    state = 1; // Главный цикл
                    break;
                }
                case 25: { // Проверяем является ли y красным (окончание)
                    if (stack.popBoolean()) {
                        state = 26; // XZ
                    } else {
                        state = 24; // Проверяем является ли y красным
                    }
                    break;
                }
                case 26: { // XZ
                    state = 24; // Проверяем является ли y красным
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 25; // Проверяем является ли y красным (окончание)
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 2: { // Проверяем является ли отец z левым сыном своего отца
                    if (d.z.p == d.z.p.p.left) {
                        comment = RBTree.this.getComment("RBInsertFixup.If.true"); 
                    } else {
                        comment = RBTree.this.getComment("RBInsertFixup.If.false"); 
                    }
                    args = new Object[]{new Integer(d.z.p.key), new Integer(d.z.key), new Integer(d.z.p.p.key)}; 
                    break;
                }
                case 7: { // Случай 1
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 10: { // Случай 2
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 11: { // Левый поворот (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 12: { // Случай 3
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 13: { // Правый поворот (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 17: { // Случай 1
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 20: { // Случай 2
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 21: { // Правый поворот (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 22: { // Случай 3
                    comment = RBTree.this.getComment("RBInsertFixup.StepInIf"); 
                    break;
                }
                case 23: { // Левый поворот (автомат)
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
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case 11: { // Левый поворот (автомат)
                    child.drawState(); 
                    break;
                }
                case 13: { // Правый поворот (автомат)
                    child.drawState(); 
                    break;
                }
                case 21: { // Правый поворот (автомат)
                    child.drawState(); 
                    break;
                }
                case 23: { // Левый поворот (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Удаление.
      */
    private final class RBDelete extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 32;

        /**
          * Конструктор.
          */
        public RBDelete() {
            super( 
                "RBDelete", 
                0, // Номер начального состояния 
                32, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация нужных вершин", 
                    "Проверяем", 
                    "Проверяем (окончание)", 
                    "XZ", 
                    "Проверяем", 
                    "Проверяем (окончание)", 
                    "XZ", 
                    "Цикл для нахождения самой маленькой вершины в поддереве с корнем у", 
                    "XZ", 
                    "XZ", 
                    "Цикл", 
                    "XZ", 
                    "Нету левого поддерева у У", 
                    "Нету левого поддерева у У (окончание)", 
                    "XZ", 
                    "XZ", 
                    "XZ", 
                    "Нету левого поддерева у У", 
                    "Нету левого поддерева у У (окончание)", 
                    "XZ", 
                    "Проверяем", 
                    "Проверяем (окончание)", 
                    "XZ", 
                    "XZ", 
                    "Нету левого поддерева у У", 
                    "Нету левого поддерева у У (окончание)", 
                    "XZ", 
                    "Нарушали ли красно-черные свойства", 
                    "Нарушали ли красно-черные свойства (окончание)", 
                    "Надо чтоб правильно работало", 
                    "Восстановление красно-черных свойств после удаления (автомат)", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация нужных вершин 
                    -1, // Проверяем 
                    -1, // Проверяем (окончание) 
                    -1, // XZ 
                    -1, // Проверяем 
                    -1, // Проверяем (окончание) 
                    -1, // XZ 
                    -1, // Цикл для нахождения самой маленькой вершины в поддереве с корнем у 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Цикл 
                    -1, // XZ 
                    -1, // Нету левого поддерева у У 
                    -1, // Нету левого поддерева у У (окончание) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Нету левого поддерева у У 
                    -1, // Нету левого поддерева у У (окончание) 
                    -1, // XZ 
                    -1, // Проверяем 
                    -1, // Проверяем (окончание) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Нету левого поддерева у У 
                    -1, // Нету левого поддерева у У (окончание) 
                    -1, // XZ 
                    -1, // Нарушали ли красно-черные свойства 
                    -1, // Нарушали ли красно-черные свойства (окончание) 
                    -1, // Надо чтоб правильно работало 
                    CALL_AUTO_LEVEL, // Восстановление красно-черных свойств после удаления (автомат) 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Инициализация нужных вершин
                    break;
                }
                case 1: { // Инициализация нужных вершин
                    state = 2; // Проверяем
                    break;
                }
                case 2: { // Проверяем
                    if ((d.z.left == null) || (d.z.right == null)) {
                        state = 4; // XZ
                    } else {
                        state = 5; // Проверяем
                    }
                    break;
                }
                case 3: { // Проверяем (окончание)
                    state = 13; // Нету левого поддерева у У
                    break;
                }
                case 4: { // XZ
                    stack.pushBoolean(true); 
                    state = 3; // Проверяем (окончание)
                    break;
                }
                case 5: { // Проверяем
                    if (d.z.right != null) {
                        state = 7; // XZ
                    } else {
                        state = 10; // XZ
                    }
                    break;
                }
                case 6: { // Проверяем (окончание)
                    stack.pushBoolean(false); 
                    state = 3; // Проверяем (окончание)
                    break;
                }
                case 7: { // XZ
                    stack.pushBoolean(false); 
                    state = 8; // Цикл для нахождения самой маленькой вершины в поддереве с корнем у
                    break;
                }
                case 8: { // Цикл для нахождения самой маленькой вершины в поддереве с корнем у
                    if (y.left != null) {
                        state = 9; // XZ
                    } else {
                        stack.pushBoolean(true); 
                        state = 6; // Проверяем (окончание)
                    }
                    break;
                }
                case 9: { // XZ
                    stack.pushBoolean(true); 
                    state = 8; // Цикл для нахождения самой маленькой вершины в поддереве с корнем у
                    break;
                }
                case 10: { // XZ
                    stack.pushBoolean(false); 
                    state = 11; // Цикл
                    break;
                }
                case 11: { // Цикл
                    if ((d.RBDelete_y != null) && (d.RBDelete_x2 == d.RBDelete_y.right)) {
                        state = 12; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // Проверяем (окончание)
                    }
                    break;
                }
                case 12: { // XZ
                    stack.pushBoolean(true); 
                    state = 11; // Цикл
                    break;
                }
                case 13: { // Нету левого поддерева у У
                    if (d.RBDelete_y.left != null) {
                        state = 15; // XZ
                    } else {
                        state = 16; // XZ
                    }
                    break;
                }
                case 14: { // Нету левого поддерева у У (окончание)
                    state = 17; // XZ
                    break;
                }
                case 15: { // XZ
                    stack.pushBoolean(true); 
                    state = 14; // Нету левого поддерева у У (окончание)
                    break;
                }
                case 16: { // XZ
                    stack.pushBoolean(false); 
                    state = 14; // Нету левого поддерева у У (окончание)
                    break;
                }
                case 17: { // XZ
                    state = 18; // Нету левого поддерева у У
                    break;
                }
                case 18: { // Нету левого поддерева у У
                    if (d.RBDelete_y.p == null) {
                        state = 20; // XZ
                    } else {
                        state = 21; // Проверяем
                    }
                    break;
                }
                case 19: { // Нету левого поддерева у У (окончание)
                    state = 25; // Нету левого поддерева у У
                    break;
                }
                case 20: { // XZ
                    stack.pushBoolean(true); 
                    state = 19; // Нету левого поддерева у У (окончание)
                    break;
                }
                case 21: { // Проверяем
                    if (y == y.p.left) {
                        state = 23; // XZ
                    } else {
                        state = 24; // XZ
                    }
                    break;
                }
                case 22: { // Проверяем (окончание)
                    stack.pushBoolean(false); 
                    state = 19; // Нету левого поддерева у У (окончание)
                    break;
                }
                case 23: { // XZ
                    stack.pushBoolean(true); 
                    state = 22; // Проверяем (окончание)
                    break;
                }
                case 24: { // XZ
                    stack.pushBoolean(false); 
                    state = 22; // Проверяем (окончание)
                    break;
                }
                case 25: { // Нету левого поддерева у У
                    if (d.RBDelete_y != d.z) {
                        state = 27; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 26; // Нету левого поддерева у У (окончание)
                    }
                    break;
                }
                case 26: { // Нету левого поддерева у У (окончание)
                    state = 28; // Нарушали ли красно-черные свойства
                    break;
                }
                case 27: { // XZ
                    stack.pushBoolean(true); 
                    state = 26; // Нету левого поддерева у У (окончание)
                    break;
                }
                case 28: { // Нарушали ли красно-черные свойства
                    if (!y.color) {
                        state = 30; // Надо чтоб правильно работало
                    } else {
                        stack.pushBoolean(false); 
                        state = 29; // Нарушали ли красно-черные свойства (окончание)
                    }
                    break;
                }
                case 29: { // Нарушали ли красно-черные свойства (окончание)
                    state = END_STATE; 
                    break;
                }
                case 30: { // Надо чтоб правильно работало
                    state = 31; // Восстановление красно-черных свойств после удаления (автомат)
                    break;
                }
                case 31: { // Восстановление красно-черных свойств после удаления (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 29; // Нарушали ли красно-черные свойства (окончание)
                    }
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    startSection();
                    storeField(d, "RBDelete_x2");
                    				d.RBDelete_x2 = null;
                    storeField(d, "RBDelete_y");
                    				d.RBDelete_y = null;
                    break;
                }
                case 2: { // Проверяем
                    break;
                }
                case 3: { // Проверяем (окончание)
                    break;
                }
                case 4: { // XZ
                    startSection();
                    storeField(d, "RBDelete_y");
                    					d.RBDelete_y = d.z;
                    break;
                }
                case 5: { // Проверяем
                    break;
                }
                case 6: { // Проверяем (окончание)
                    break;
                }
                case 7: { // XZ
                    startSection();
                    storeField(d, "RBDelete_y");
                    						d.RBDelete_y = d.z.right;
                    break;
                }
                case 8: { // Цикл для нахождения самой маленькой вершины в поддереве с корнем у
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
                case 11: { // Цикл
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
                case 13: { // Нету левого поддерева у У
                    break;
                }
                case 14: { // Нету левого поддерева у У (окончание)
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
                case 18: { // Нету левого поддерева у У
                    break;
                }
                case 19: { // Нету левого поддерева у У (окончание)
                    break;
                }
                case 20: { // XZ
                    startSection();
                    storeField(d, "root");
                    					d.root = d.RBDelete_x2;
                    break;
                }
                case 21: { // Проверяем
                    break;
                }
                case 22: { // Проверяем (окончание)
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
                case 25: { // Нету левого поддерева у У
                    break;
                }
                case 26: { // Нету левого поддерева у У (окончание)
                    break;
                }
                case 27: { // XZ
                    startSection();
                    storeField(d.z, "key");
                    					d.z.key = d.RBDelete_y.key;
                    break;
                }
                case 28: { // Нарушали ли красно-черные свойства
                    break;
                }
                case 29: { // Нарушали ли красно-черные свойства (окончание)
                    break;
                }
                case 30: { // Надо чтоб правильно работало
                    startSection();
                    storeField(d, "z");
                    					d.z = d.RBDelete_x2;
                    break;
                }
                case 31: { // Восстановление красно-черных свойств после удаления (автомат)
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    restoreSection();
                    break;
                }
                case 2: { // Проверяем
                    break;
                }
                case 3: { // Проверяем (окончание)
                    break;
                }
                case 4: { // XZ
                    restoreSection();
                    break;
                }
                case 5: { // Проверяем
                    break;
                }
                case 6: { // Проверяем (окончание)
                    break;
                }
                case 7: { // XZ
                    restoreSection();
                    break;
                }
                case 8: { // Цикл для нахождения самой маленькой вершины в поддереве с корнем у
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
                case 11: { // Цикл
                    break;
                }
                case 12: { // XZ
                    restoreSection();
                    break;
                }
                case 13: { // Нету левого поддерева у У
                    break;
                }
                case 14: { // Нету левого поддерева у У (окончание)
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
                case 18: { // Нету левого поддерева у У
                    break;
                }
                case 19: { // Нету левого поддерева у У (окончание)
                    break;
                }
                case 20: { // XZ
                    restoreSection();
                    break;
                }
                case 21: { // Проверяем
                    break;
                }
                case 22: { // Проверяем (окончание)
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
                case 25: { // Нету левого поддерева у У
                    break;
                }
                case 26: { // Нету левого поддерева у У (окончание)
                    break;
                }
                case 27: { // XZ
                    restoreSection();
                    break;
                }
                case 28: { // Нарушали ли красно-черные свойства
                    break;
                }
                case 29: { // Нарушали ли красно-черные свойства (окончание)
                    break;
                }
                case 30: { // Надо чтоб правильно работало
                    restoreSection();
                    break;
                }
                case 31: { // Восстановление красно-черных свойств после удаления (автомат)
                    if (child == null) {
                        child = new RBDeleteFixup(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация нужных вершин
                    state = START_STATE; 
                    break;
                }
                case 2: { // Проверяем
                    state = 1; // Инициализация нужных вершин
                    break;
                }
                case 3: { // Проверяем (окончание)
                    if (stack.popBoolean()) {
                        state = 4; // XZ
                    } else {
                        state = 6; // Проверяем (окончание)
                    }
                    break;
                }
                case 4: { // XZ
                    state = 2; // Проверяем
                    break;
                }
                case 5: { // Проверяем
                    state = 2; // Проверяем
                    break;
                }
                case 6: { // Проверяем (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // Цикл для нахождения самой маленькой вершины в поддереве с корнем у
                    } else {
                        state = 11; // Цикл
                    }
                    break;
                }
                case 7: { // XZ
                    state = 5; // Проверяем
                    break;
                }
                case 8: { // Цикл для нахождения самой маленькой вершины в поддереве с корнем у
                    if (stack.popBoolean()) {
                        state = 9; // XZ
                    } else {
                        state = 7; // XZ
                    }
                    break;
                }
                case 9: { // XZ
                    state = 8; // Цикл для нахождения самой маленькой вершины в поддереве с корнем у
                    break;
                }
                case 10: { // XZ
                    state = 5; // Проверяем
                    break;
                }
                case 11: { // Цикл
                    if (stack.popBoolean()) {
                        state = 12; // XZ
                    } else {
                        state = 10; // XZ
                    }
                    break;
                }
                case 12: { // XZ
                    state = 11; // Цикл
                    break;
                }
                case 13: { // Нету левого поддерева у У
                    state = 3; // Проверяем (окончание)
                    break;
                }
                case 14: { // Нету левого поддерева у У (окончание)
                    if (stack.popBoolean()) {
                        state = 15; // XZ
                    } else {
                        state = 16; // XZ
                    }
                    break;
                }
                case 15: { // XZ
                    state = 13; // Нету левого поддерева у У
                    break;
                }
                case 16: { // XZ
                    state = 13; // Нету левого поддерева у У
                    break;
                }
                case 17: { // XZ
                    state = 14; // Нету левого поддерева у У (окончание)
                    break;
                }
                case 18: { // Нету левого поддерева у У
                    state = 17; // XZ
                    break;
                }
                case 19: { // Нету левого поддерева у У (окончание)
                    if (stack.popBoolean()) {
                        state = 20; // XZ
                    } else {
                        state = 22; // Проверяем (окончание)
                    }
                    break;
                }
                case 20: { // XZ
                    state = 18; // Нету левого поддерева у У
                    break;
                }
                case 21: { // Проверяем
                    state = 18; // Нету левого поддерева у У
                    break;
                }
                case 22: { // Проверяем (окончание)
                    if (stack.popBoolean()) {
                        state = 23; // XZ
                    } else {
                        state = 24; // XZ
                    }
                    break;
                }
                case 23: { // XZ
                    state = 21; // Проверяем
                    break;
                }
                case 24: { // XZ
                    state = 21; // Проверяем
                    break;
                }
                case 25: { // Нету левого поддерева у У
                    state = 19; // Нету левого поддерева у У (окончание)
                    break;
                }
                case 26: { // Нету левого поддерева у У (окончание)
                    if (stack.popBoolean()) {
                        state = 27; // XZ
                    } else {
                        state = 25; // Нету левого поддерева у У
                    }
                    break;
                }
                case 27: { // XZ
                    state = 25; // Нету левого поддерева у У
                    break;
                }
                case 28: { // Нарушали ли красно-черные свойства
                    state = 26; // Нету левого поддерева у У (окончание)
                    break;
                }
                case 29: { // Нарушали ли красно-черные свойства (окончание)
                    if (stack.popBoolean()) {
                        state = 31; // Восстановление красно-черных свойств после удаления (автомат)
                    } else {
                        state = 28; // Нарушали ли красно-черные свойства
                    }
                    break;
                }
                case 30: { // Надо чтоб правильно работало
                    state = 28; // Нарушали ли красно-черные свойства
                    break;
                }
                case 31: { // Восстановление красно-черных свойств после удаления (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 30; // Надо чтоб правильно работало
                    }
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 29; // Нарушали ли красно-черные свойства (окончание)
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 31: { // Восстановление красно-черных свойств после удаления (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case 31: { // Восстановление красно-черных свойств после удаления (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Восстановление красно-черных свойств после удаления.
      */
    private final class RBDeleteFixup extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 40;

        /**
          * Конструктор.
          */
        public RBDeleteFixup() {
            super( 
                "RBDeleteFixup", 
                0, // Номер начального состояния 
                40, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "XZ", 
                    "Главный цикл", 
                    "Является ли х левым сыном", 
                    "Является ли х левым сыном (окончание)", 
                    "XZ", 
                    "Случай 1", 
                    "Случай 1 (окончание)", 
                    "XZ", 
                    "Левый поворот (автомат)", 
                    "XZ", 
                    "Случай 2", 
                    "Случай 2 (окончание)", 
                    "XZ", 
                    "Случай 3", 
                    "Случай 3 (окончание)", 
                    "XZ", 
                    "Правый поворот (автомат)", 
                    "XZ", 
                    "XZ", 
                    "Левый поворот (автомат)", 
                    "XZ", 
                    "XZ", 
                    "Случай 1", 
                    "Случай 1 (окончание)", 
                    "XZ", 
                    "Левый поворот (автомат)", 
                    "XZ", 
                    "Случай 2", 
                    "Случай 2 (окончание)", 
                    "XZ", 
                    "Случай 3", 
                    "Случай 3 (окончание)", 
                    "XZ", 
                    "Правый поворот (автомат)", 
                    "XZ", 
                    "XZ", 
                    "Левый поворот (автомат)", 
                    "XZ", 
                    "XZ", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // XZ 
                    -1, // Главный цикл 
                    -1, // Является ли х левым сыном 
                    -1, // Является ли х левым сыном (окончание) 
                    -1, // XZ 
                    -1, // Случай 1 
                    -1, // Случай 1 (окончание) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // Левый поворот (автомат) 
                    -1, // XZ 
                    -1, // Случай 2 
                    -1, // Случай 2 (окончание) 
                    -1, // XZ 
                    -1, // Случай 3 
                    -1, // Случай 3 (окончание) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // Правый поворот (автомат) 
                    -1, // XZ 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // Левый поворот (автомат) 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Случай 1 
                    -1, // Случай 1 (окончание) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // Левый поворот (автомат) 
                    -1, // XZ 
                    -1, // Случай 2 
                    -1, // Случай 2 (окончание) 
                    -1, // XZ 
                    -1, // Случай 3 
                    -1, // Случай 3 (окончание) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // Правый поворот (автомат) 
                    -1, // XZ 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // Левый поворот (автомат) 
                    -1, // XZ 
                    -1, // XZ 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // XZ
                    break;
                }
                case 1: { // XZ
                    stack.pushBoolean(false); 
                    state = 2; // Главный цикл
                    break;
                }
                case 2: { // Главный цикл
                    if ((d.x != d.root) && (!d.x.color)) {
                        state = 3; // Является ли х левым сыном
                    } else {
                        state = 39; // XZ
                    }
                    break;
                }
                case 3: { // Является ли х левым сыном
                    if (d.x == d.x.p.left) {
                        state = 5; // XZ
                    } else {
                        state = 22; // XZ
                    }
                    break;
                }
                case 4: { // Является ли х левым сыном (окончание)
                    stack.pushBoolean(true); 
                    state = 2; // Главный цикл
                    break;
                }
                case 5: { // XZ
                    state = 6; // Случай 1
                    break;
                }
                case 6: { // Случай 1
                    if (w.color) {
                        state = 8; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // Случай 1 (окончание)
                    }
                    break;
                }
                case 7: { // Случай 1 (окончание)
                    state = 11; // Случай 2
                    break;
                }
                case 8: { // XZ
                    state = 9; // Левый поворот (автомат)
                    break;
                }
                case 9: { // Левый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // XZ
                    }
                    break;
                }
                case 10: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // Случай 1 (окончание)
                    break;
                }
                case 11: { // Случай 2
                    if ((!d.RBDeleteFixup_w.left.color) && (!d.RBDeleteFixup_w.right.color)) {
                        state = 13; // XZ
                    } else {
                        state = 14; // Случай 3
                    }
                    break;
                }
                case 12: { // Случай 2 (окончание)
                    stack.pushBoolean(true); 
                    state = 4; // Является ли х левым сыном (окончание)
                    break;
                }
                case 13: { // XZ
                    stack.pushBoolean(true); 
                    state = 12; // Случай 2 (окончание)
                    break;
                }
                case 14: { // Случай 3
                    if (!d.RBDeleteFixup_w.right.color) {
                        state = 16; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 15; // Случай 3 (окончание)
                    }
                    break;
                }
                case 15: { // Случай 3 (окончание)
                    state = 19; // XZ
                    break;
                }
                case 16: { // XZ
                    state = 17; // Правый поворот (автомат)
                    break;
                }
                case 17: { // Правый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 18; // XZ
                    }
                    break;
                }
                case 18: { // XZ
                    stack.pushBoolean(true); 
                    state = 15; // Случай 3 (окончание)
                    break;
                }
                case 19: { // XZ
                    state = 20; // Левый поворот (автомат)
                    break;
                }
                case 20: { // Левый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 21; // XZ
                    }
                    break;
                }
                case 21: { // XZ
                    stack.pushBoolean(false); 
                    state = 12; // Случай 2 (окончание)
                    break;
                }
                case 22: { // XZ
                    state = 23; // Случай 1
                    break;
                }
                case 23: { // Случай 1
                    if (w.color) {
                        state = 25; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 24; // Случай 1 (окончание)
                    }
                    break;
                }
                case 24: { // Случай 1 (окончание)
                    state = 28; // Случай 2
                    break;
                }
                case 25: { // XZ
                    state = 26; // Левый поворот (автомат)
                    break;
                }
                case 26: { // Левый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 27; // XZ
                    }
                    break;
                }
                case 27: { // XZ
                    stack.pushBoolean(true); 
                    state = 24; // Случай 1 (окончание)
                    break;
                }
                case 28: { // Случай 2
                    if ((!d.RBDeleteFixup_w.left.color) && (!d.RBDeleteFixup_w.right.color)) {
                        state = 30; // XZ
                    } else {
                        state = 31; // Случай 3
                    }
                    break;
                }
                case 29: { // Случай 2 (окончание)
                    stack.pushBoolean(false); 
                    state = 4; // Является ли х левым сыном (окончание)
                    break;
                }
                case 30: { // XZ
                    stack.pushBoolean(true); 
                    state = 29; // Случай 2 (окончание)
                    break;
                }
                case 31: { // Случай 3
                    if (!d.RBDeleteFixup_w.left.color) {
                        state = 33; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 32; // Случай 3 (окончание)
                    }
                    break;
                }
                case 32: { // Случай 3 (окончание)
                    state = 36; // XZ
                    break;
                }
                case 33: { // XZ
                    state = 34; // Правый поворот (автомат)
                    break;
                }
                case 34: { // Правый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 35; // XZ
                    }
                    break;
                }
                case 35: { // XZ
                    stack.pushBoolean(true); 
                    state = 32; // Случай 3 (окончание)
                    break;
                }
                case 36: { // XZ
                    state = 37; // Левый поворот (автомат)
                    break;
                }
                case 37: { // Левый поворот (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 38; // XZ
                    }
                    break;
                }
                case 38: { // XZ
                    stack.pushBoolean(false); 
                    state = 29; // Случай 2 (окончание)
                    break;
                }
                case 39: { // XZ
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // XZ
                    startSection();
                    storeField(d, "x");
                    				d.x = d.z;
                    break;
                }
                case 2: { // Главный цикл
                    break;
                }
                case 3: { // Является ли х левым сыном
                    break;
                }
                case 4: { // Является ли х левым сыном (окончание)
                    break;
                }
                case 5: { // XZ
                    startSection();
                    storeField(d, "RBDeleteFixup_w");
                    					d.RBDeleteFixup_w = d.x.p.right;
                    break;
                }
                case 6: { // Случай 1
                    break;
                }
                case 7: { // Случай 1 (окончание)
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
                case 9: { // Левый поворот (автомат)
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
                case 11: { // Случай 2
                    break;
                }
                case 12: { // Случай 2 (окончание)
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
                case 14: { // Случай 3
                    break;
                }
                case 15: { // Случай 3 (окончание)
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
                case 17: { // Правый поворот (автомат)
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
                case 20: { // Левый поворот (автомат)
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
                case 23: { // Случай 1
                    break;
                }
                case 24: { // Случай 1 (окончание)
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
                case 26: { // Левый поворот (автомат)
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
                case 28: { // Случай 2
                    break;
                }
                case 29: { // Случай 2 (окончание)
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
                case 31: { // Случай 3
                    break;
                }
                case 32: { // Случай 3 (окончание)
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
                case 34: { // Правый поворот (автомат)
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
                case 37: { // Левый поворот (автомат)
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // XZ
                    restoreSection();
                    break;
                }
                case 2: { // Главный цикл
                    break;
                }
                case 3: { // Является ли х левым сыном
                    break;
                }
                case 4: { // Является ли х левым сыном (окончание)
                    break;
                }
                case 5: { // XZ
                    restoreSection();
                    break;
                }
                case 6: { // Случай 1
                    break;
                }
                case 7: { // Случай 1 (окончание)
                    break;
                }
                case 8: { // XZ
                    restoreSection();
                    break;
                }
                case 9: { // Левый поворот (автомат)
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
                case 11: { // Случай 2
                    break;
                }
                case 12: { // Случай 2 (окончание)
                    break;
                }
                case 13: { // XZ
                    restoreSection();
                    break;
                }
                case 14: { // Случай 3
                    break;
                }
                case 15: { // Случай 3 (окончание)
                    break;
                }
                case 16: { // XZ
                    restoreSection();
                    break;
                }
                case 17: { // Правый поворот (автомат)
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
                case 20: { // Левый поворот (автомат)
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
                case 23: { // Случай 1
                    break;
                }
                case 24: { // Случай 1 (окончание)
                    break;
                }
                case 25: { // XZ
                    restoreSection();
                    break;
                }
                case 26: { // Левый поворот (автомат)
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
                case 28: { // Случай 2
                    break;
                }
                case 29: { // Случай 2 (окончание)
                    break;
                }
                case 30: { // XZ
                    restoreSection();
                    break;
                }
                case 31: { // Случай 3
                    break;
                }
                case 32: { // Случай 3 (окончание)
                    break;
                }
                case 33: { // XZ
                    restoreSection();
                    break;
                }
                case 34: { // Правый поворот (автомат)
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
                case 37: { // Левый поворот (автомат)
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

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // XZ
                    state = START_STATE; 
                    break;
                }
                case 2: { // Главный цикл
                    if (stack.popBoolean()) {
                        state = 4; // Является ли х левым сыном (окончание)
                    } else {
                        state = 1; // XZ
                    }
                    break;
                }
                case 3: { // Является ли х левым сыном
                    state = 2; // Главный цикл
                    break;
                }
                case 4: { // Является ли х левым сыном (окончание)
                    if (stack.popBoolean()) {
                        state = 12; // Случай 2 (окончание)
                    } else {
                        state = 29; // Случай 2 (окончание)
                    }
                    break;
                }
                case 5: { // XZ
                    state = 3; // Является ли х левым сыном
                    break;
                }
                case 6: { // Случай 1
                    state = 5; // XZ
                    break;
                }
                case 7: { // Случай 1 (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // XZ
                    } else {
                        state = 6; // Случай 1
                    }
                    break;
                }
                case 8: { // XZ
                    state = 6; // Случай 1
                    break;
                }
                case 9: { // Левый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // XZ
                    }
                    break;
                }
                case 10: { // XZ
                    state = 9; // Левый поворот (автомат)
                    break;
                }
                case 11: { // Случай 2
                    state = 7; // Случай 1 (окончание)
                    break;
                }
                case 12: { // Случай 2 (окончание)
                    if (stack.popBoolean()) {
                        state = 13; // XZ
                    } else {
                        state = 21; // XZ
                    }
                    break;
                }
                case 13: { // XZ
                    state = 11; // Случай 2
                    break;
                }
                case 14: { // Случай 3
                    state = 11; // Случай 2
                    break;
                }
                case 15: { // Случай 3 (окончание)
                    if (stack.popBoolean()) {
                        state = 18; // XZ
                    } else {
                        state = 14; // Случай 3
                    }
                    break;
                }
                case 16: { // XZ
                    state = 14; // Случай 3
                    break;
                }
                case 17: { // Правый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 16; // XZ
                    }
                    break;
                }
                case 18: { // XZ
                    state = 17; // Правый поворот (автомат)
                    break;
                }
                case 19: { // XZ
                    state = 15; // Случай 3 (окончание)
                    break;
                }
                case 20: { // Левый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // XZ
                    }
                    break;
                }
                case 21: { // XZ
                    state = 20; // Левый поворот (автомат)
                    break;
                }
                case 22: { // XZ
                    state = 3; // Является ли х левым сыном
                    break;
                }
                case 23: { // Случай 1
                    state = 22; // XZ
                    break;
                }
                case 24: { // Случай 1 (окончание)
                    if (stack.popBoolean()) {
                        state = 27; // XZ
                    } else {
                        state = 23; // Случай 1
                    }
                    break;
                }
                case 25: { // XZ
                    state = 23; // Случай 1
                    break;
                }
                case 26: { // Левый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 25; // XZ
                    }
                    break;
                }
                case 27: { // XZ
                    state = 26; // Левый поворот (автомат)
                    break;
                }
                case 28: { // Случай 2
                    state = 24; // Случай 1 (окончание)
                    break;
                }
                case 29: { // Случай 2 (окончание)
                    if (stack.popBoolean()) {
                        state = 30; // XZ
                    } else {
                        state = 38; // XZ
                    }
                    break;
                }
                case 30: { // XZ
                    state = 28; // Случай 2
                    break;
                }
                case 31: { // Случай 3
                    state = 28; // Случай 2
                    break;
                }
                case 32: { // Случай 3 (окончание)
                    if (stack.popBoolean()) {
                        state = 35; // XZ
                    } else {
                        state = 31; // Случай 3
                    }
                    break;
                }
                case 33: { // XZ
                    state = 31; // Случай 3
                    break;
                }
                case 34: { // Правый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 33; // XZ
                    }
                    break;
                }
                case 35: { // XZ
                    state = 34; // Правый поворот (автомат)
                    break;
                }
                case 36: { // XZ
                    state = 32; // Случай 3 (окончание)
                    break;
                }
                case 37: { // Левый поворот (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 36; // XZ
                    }
                    break;
                }
                case 38: { // XZ
                    state = 37; // Левый поворот (автомат)
                    break;
                }
                case 39: { // XZ
                    state = 2; // Главный цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 39; // XZ
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case 9: { // Левый поворот (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 17: { // Правый поворот (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 20: { // Левый поворот (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 26: { // Левый поворот (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 34: { // Правый поворот (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 37: { // Левый поворот (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case 9: { // Левый поворот (автомат)
                    child.drawState(); 
                    break;
                }
                case 17: { // Правый поворот (автомат)
                    child.drawState(); 
                    break;
                }
                case 20: { // Левый поворот (автомат)
                    child.drawState(); 
                    break;
                }
                case 26: { // Левый поворот (автомат)
                    child.drawState(); 
                    break;
                }
                case 34: { // Правый поворот (автомат)
                    child.drawState(); 
                    break;
                }
                case 37: { // Левый поворот (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }
}
