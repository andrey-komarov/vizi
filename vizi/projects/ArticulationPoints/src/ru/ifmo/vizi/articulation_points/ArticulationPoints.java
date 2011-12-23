package ru.ifmo.vizi.articulation_points;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class ArticulationPoints extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public ArticulationPoints(Locale locale) {
        super("ru.ifmo.vizi.articulation_points.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Число вершин в графе.
          */
        public int numberOfVertices = 10;

        /**
          * Матрица смежности.
          */
        public int[][] aMatrix = new int[10][10];

        /**
          * Массив, содержащий номера вершин при обходе графа в глубину.
          */
        public int[] dfnumber = new int[10];

        /**
          * Массив, содержащий значения чисел low всех вершин.
          */
        public int[] low = new int[10];

        /**
          * Массив, содержащий номера вершин, в том порядке, в котором они посещались.
          */
        public int[] way = new int[10];

        /**
          * Массив посещений.
          */
        public boolean[] visited = new boolean[10];

        /**
          * Число посещенных вершин.
          */
        public int numberOfVisited = 0;

        /**
          * Активный автомат.
          */
        public int activeAuto = 0;

        /**
          * Корень дерева обхода.
          */
        public int root = 0;

        /**
          * Точки сочленения.
          */
        public int[] articulationPoints = new int[10];

        /**
          * Экземпляр апплета.
          */
        public ArticulationPointsVisualizer visualizer = null;

        /**
          * Стек (Процедура DFS).
          */
        public int[] DFS_stack;

        /**
          * Индекс 'головы' стека (Процедура DFS).
          */
        public int DFS_head;

        /**
          * Переменная цикла (Процедура DFS).
          */
        public int DFS_i;

        /**
          * Переменная условия выхода из цикла (Процедура DFS).
          */
        public boolean DFS_flag;

        /**
          * Активная вершина (Процедура DFS).
          */
        public int DFS_activeVertex;

        /**
          * Текущая вершина (Процедура findLow).
          */
        public int findLow_currentVertex;

        /**
          * Переменная цикла (Процедура findLow).
          */
        public int findLow_i;

        /**
          * Переменная цикла (Процедура findLow).
          */
        public int findLow_j;

        /**
          * Активная вершина в массиве low (Процедура findLow).
          */
        public int findLow_activeVertexInLow;

        /**
          * Активная вершина в массиве dfnum (Процедура findLow).
          */
        public int findLow_activeVertexInDfnum;

        /**
          * Количество детей у корня (Процедура findArticulationPoints).
          */
        public int findArticulationPoints_childsCounter;

        /**
          * Переменная цикла (Процедура findArticulationPoints).
          */
        public int findArticulationPoints_i;

        /**
          * Переменная цикла (Процедура findArticulationPoints).
          */
        public int findArticulationPoints_j;

        /**
          * Переменная условия выхода из цикла (Процедура findArticulationPoints).
          */
        public boolean findArticulationPoints_flag;

        /**
          * Активная вершина в массиве low (Процедура findArticulationPoints).
          */
        public int findArticulationPoints_activeVertexInLow;

        /**
          * Текущая вершина (Процедура findArticulationPoints).
          */
        public int findArticulationPoints_currentVertex;

        /**
          * Переменная цикла (Процедура findBridges).
          */
        public int findBridges_i;

        /**
          * Переменная цикла (Процедура findBridges).
          */
        public int findBridges_j;

        /**
          * Первая активная вершина в массиве low (Процедура findBridges).
          */
        public int findBridges_activeVertexInLow1;

        /**
          * Вторая активная вершина в массиве low (Процедура findBridges).
          */
        public int findBridges_activeVertexInLow2;

        /**
          * Первая активная вершина в массиве dfnumber (Процедура findBridges).
          */
        public int findBridges_activeVertexInDfnum1;

        /**
          * Вторая активная вершина в массиве dfnumber (Процедура findBridges).
          */
        public int findBridges_activeVertexInDfnum2;

        /**
          * Переменная цикла (Процедура Main).
          */
        public int Main_i;

        public String toString() {
            			StringBuffer s = new StringBuffer();
                        return s.toString();
        }
    }

    /**
      * Обходит граф в глубину.
      */
    private final class DFS extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 19;

        /**
          * Конструктор.
          */
        public DFS() {
            super( 
                "DFS", 
                0, // Номер начального состояния 
                19, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Одход в глубину", 
                    "Посещаем вершину, с которой начинаем обход и добавляем ее в стек", 
                    "Обход дерева", 
                    "Инициируем i и флаг", 
                    "Ищем следующего ребенка", 
                    "Проверяем, была ли i-тая вершина посещена", 
                    "Проверяем, была ли i-тая вершина посещена (окончание)", 
                    "Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той", 
                    "Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той (окончание)", 
                    "Посещаем следующую вершину и добавляем ее в стек", 
                    "Инкримент i", 
                    "Условие существования потомков", 
                    "Условие существования потомков (окончание)", 
                    "Вывод комментария", 
                    "Является ли вершина последней", 
                    "Является ли вершина последней (окончание)", 
                    "Вершина последняя в стеке", 
                    "Вершина не последняя в стеке", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    1, // Одход в глубину 
                    0, // Посещаем вершину, с которой начинаем обход и добавляем ее в стек 
                    -1, // Обход дерева 
                    -1, // Инициируем i и флаг 
                    -1, // Ищем следующего ребенка 
                    -1, // Проверяем, была ли i-тая вершина посещена 
                    -1, // Проверяем, была ли i-тая вершина посещена (окончание) 
                    -1, // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той 
                    -1, // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той (окончание) 
                    0, // Посещаем следующую вершину и добавляем ее в стек 
                    -1, // Инкримент i 
                    -1, // Условие существования потомков 
                    -1, // Условие существования потомков (окончание) 
                    0, // Вывод комментария 
                    -1, // Является ли вершина последней 
                    -1, // Является ли вершина последней (окончание) 
                    0, // Вершина последняя в стеке 
                    0, // Вершина не последняя в стеке 
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
                    state = 1; // Одход в глубину
                    break;
                }
                case 1: { // Одход в глубину
                    state = 2; // Посещаем вершину, с которой начинаем обход и добавляем ее в стек
                    break;
                }
                case 2: { // Посещаем вершину, с которой начинаем обход и добавляем ее в стек
                    stack.pushBoolean(false); 
                    state = 3; // Обход дерева
                    break;
                }
                case 3: { // Обход дерева
                    if (d.DFS_head > 0) {
                        state = 4; // Инициируем i и флаг
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 4: { // Инициируем i и флаг
                    stack.pushBoolean(false); 
                    state = 5; // Ищем следующего ребенка
                    break;
                }
                case 5: { // Ищем следующего ребенка
                    if (d.DFS_i < d.numberOfVertices && d.DFS_flag) {
                        state = 6; // Проверяем, была ли i-тая вершина посещена
                    } else {
                        state = 12; // Условие существования потомков
                    }
                    break;
                }
                case 6: { // Проверяем, была ли i-тая вершина посещена
                    if (!d.visited[d.DFS_i]) {
                        state = 8; // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // Проверяем, была ли i-тая вершина посещена (окончание)
                    }
                    break;
                }
                case 7: { // Проверяем, была ли i-тая вершина посещена (окончание)
                    state = 11; // Инкримент i
                    break;
                }
                case 8: { // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той
                    if (d.aMatrix[d.DFS_stack[d.DFS_head]][d.DFS_i] == 3 && d.DFS_stack[d.DFS_head] != d.DFS_i) {
                        state = 10; // Посещаем следующую вершину и добавляем ее в стек
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той (окончание)
                    }
                    break;
                }
                case 9: { // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той (окончание)
                    stack.pushBoolean(true); 
                    state = 7; // Проверяем, была ли i-тая вершина посещена (окончание)
                    break;
                }
                case 10: { // Посещаем следующую вершину и добавляем ее в стек
                    stack.pushBoolean(true); 
                    state = 9; // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той (окончание)
                    break;
                }
                case 11: { // Инкримент i
                    stack.pushBoolean(true); 
                    state = 5; // Ищем следующего ребенка
                    break;
                }
                case 12: { // Условие существования потомков
                    if (d.DFS_flag) {
                        state = 14; // Вывод комментария
                    } else {
                        stack.pushBoolean(false); 
                        state = 13; // Условие существования потомков (окончание)
                    }
                    break;
                }
                case 13: { // Условие существования потомков (окончание)
                    stack.pushBoolean(true); 
                    state = 3; // Обход дерева
                    break;
                }
                case 14: { // Вывод комментария
                    state = 15; // Является ли вершина последней
                    break;
                }
                case 15: { // Является ли вершина последней
                    if (d.DFS_head == 1) {
                        state = 17; // Вершина последняя в стеке
                    } else {
                        state = 18; // Вершина не последняя в стеке
                    }
                    break;
                }
                case 16: { // Является ли вершина последней (окончание)
                    stack.pushBoolean(true); 
                    state = 13; // Условие существования потомков (окончание)
                    break;
                }
                case 17: { // Вершина последняя в стеке
                    stack.pushBoolean(true); 
                    state = 16; // Является ли вершина последней (окончание)
                    break;
                }
                case 18: { // Вершина не последняя в стеке
                    stack.pushBoolean(false); 
                    state = 16; // Является ли вершина последней (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Одход в глубину
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
                case 2: { // Посещаем вершину, с которой начинаем обход и добавляем ее в стек
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
                case 3: { // Обход дерева
                    break;
                }
                case 4: { // Инициируем i и флаг
                    startSection();
                    storeField(d, "DFS_i");
                    					d.DFS_i = 0;
                    storeField(d, "DFS_flag");
                    					d.DFS_flag = true;
                    break;
                }
                case 5: { // Ищем следующего ребенка
                    break;
                }
                case 6: { // Проверяем, была ли i-тая вершина посещена
                    break;
                }
                case 7: { // Проверяем, была ли i-тая вершина посещена (окончание)
                    break;
                }
                case 8: { // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той
                    break;
                }
                case 9: { // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той (окончание)
                    break;
                }
                case 10: { // Посещаем следующую вершину и добавляем ее в стек
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
                case 11: { // Инкримент i
                    startSection();
                    storeField(d, "DFS_i");
                    						d.DFS_i = d.DFS_i + 1;
                    break;
                }
                case 12: { // Условие существования потомков
                    break;
                }
                case 13: { // Условие существования потомков (окончание)
                    break;
                }
                case 14: { // Вывод комментария
                    startSection();
                    storeField(d, "DFS_activeVertex");
                    							d.DFS_activeVertex = -1;
                    break;
                }
                case 15: { // Является ли вершина последней
                    break;
                }
                case 16: { // Является ли вершина последней (окончание)
                    break;
                }
                case 17: { // Вершина последняя в стеке
                    startSection();
                    storeField(d, "DFS_head");
                    									d.DFS_head = d.DFS_head - 1;
                    storeField(d, "DFS_activeVertex");
                    									d.DFS_activeVertex = -1;
                    break;
                }
                case 18: { // Вершина не последняя в стеке
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Одход в глубину
                    restoreSection();
                    break;
                }
                case 2: { // Посещаем вершину, с которой начинаем обход и добавляем ее в стек
                    restoreSection();
                    break;
                }
                case 3: { // Обход дерева
                    break;
                }
                case 4: { // Инициируем i и флаг
                    restoreSection();
                    break;
                }
                case 5: { // Ищем следующего ребенка
                    break;
                }
                case 6: { // Проверяем, была ли i-тая вершина посещена
                    break;
                }
                case 7: { // Проверяем, была ли i-тая вершина посещена (окончание)
                    break;
                }
                case 8: { // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той
                    break;
                }
                case 9: { // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той (окончание)
                    break;
                }
                case 10: { // Посещаем следующую вершину и добавляем ее в стек
                    restoreSection();
                    break;
                }
                case 11: { // Инкримент i
                    restoreSection();
                    break;
                }
                case 12: { // Условие существования потомков
                    break;
                }
                case 13: { // Условие существования потомков (окончание)
                    break;
                }
                case 14: { // Вывод комментария
                    restoreSection();
                    break;
                }
                case 15: { // Является ли вершина последней
                    break;
                }
                case 16: { // Является ли вершина последней (окончание)
                    break;
                }
                case 17: { // Вершина последняя в стеке
                    restoreSection();
                    break;
                }
                case 18: { // Вершина не последняя в стеке
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Одход в глубину
                    state = START_STATE; 
                    break;
                }
                case 2: { // Посещаем вершину, с которой начинаем обход и добавляем ее в стек
                    state = 1; // Одход в глубину
                    break;
                }
                case 3: { // Обход дерева
                    if (stack.popBoolean()) {
                        state = 13; // Условие существования потомков (окончание)
                    } else {
                        state = 2; // Посещаем вершину, с которой начинаем обход и добавляем ее в стек
                    }
                    break;
                }
                case 4: { // Инициируем i и флаг
                    state = 3; // Обход дерева
                    break;
                }
                case 5: { // Ищем следующего ребенка
                    if (stack.popBoolean()) {
                        state = 11; // Инкримент i
                    } else {
                        state = 4; // Инициируем i и флаг
                    }
                    break;
                }
                case 6: { // Проверяем, была ли i-тая вершина посещена
                    state = 5; // Ищем следующего ребенка
                    break;
                }
                case 7: { // Проверяем, была ли i-тая вершина посещена (окончание)
                    if (stack.popBoolean()) {
                        state = 9; // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той (окончание)
                    } else {
                        state = 6; // Проверяем, была ли i-тая вершина посещена
                    }
                    break;
                }
                case 8: { // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той
                    state = 6; // Проверяем, была ли i-тая вершина посещена
                    break;
                }
                case 9: { // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // Посещаем следующую вершину и добавляем ее в стек
                    } else {
                        state = 8; // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той
                    }
                    break;
                }
                case 10: { // Посещаем следующую вершину и добавляем ее в стек
                    state = 8; // Смотрим, есть ли ребро, соединяющее последнею найденную вершину с i-той
                    break;
                }
                case 11: { // Инкримент i
                    state = 7; // Проверяем, была ли i-тая вершина посещена (окончание)
                    break;
                }
                case 12: { // Условие существования потомков
                    state = 5; // Ищем следующего ребенка
                    break;
                }
                case 13: { // Условие существования потомков (окончание)
                    if (stack.popBoolean()) {
                        state = 16; // Является ли вершина последней (окончание)
                    } else {
                        state = 12; // Условие существования потомков
                    }
                    break;
                }
                case 14: { // Вывод комментария
                    state = 12; // Условие существования потомков
                    break;
                }
                case 15: { // Является ли вершина последней
                    state = 14; // Вывод комментария
                    break;
                }
                case 16: { // Является ли вершина последней (окончание)
                    if (stack.popBoolean()) {
                        state = 17; // Вершина последняя в стеке
                    } else {
                        state = 18; // Вершина не последняя в стеке
                    }
                    break;
                }
                case 17: { // Вершина последняя в стеке
                    state = 15; // Является ли вершина последней
                    break;
                }
                case 18: { // Вершина не последняя в стеке
                    state = 15; // Является ли вершина последней
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 3; // Обход дерева
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
                case 1: { // Одход в глубину
                    comment = ArticulationPoints.this.getComment("DFS.startDFS"); 
                    break;
                }
                case 2: { // Посещаем вершину, с которой начинаем обход и добавляем ее в стек
                    comment = ArticulationPoints.this.getComment("DFS.VisitFirst"); 
                    args = new Object[]{new Integer(d.root)}; 
                    break;
                }
                case 10: { // Посещаем следующую вершину и добавляем ее в стек
                    comment = ArticulationPoints.this.getComment("DFS.VisitNext"); 
                    args = new Object[]{new Integer(d.DFS_stack[d.DFS_head-1]), new Integer(d.DFS_i), new Integer(d.dfnumber[d.DFS_i])}; 
                    break;
                }
                case 14: { // Вывод комментария
                    comment = ArticulationPoints.this.getComment("DFS.noChilds"); 
                    args = new Object[]{new Integer(d.DFS_stack[d.DFS_head])}; 
                    break;
                }
                case 17: { // Вершина последняя в стеке
                    comment = ArticulationPoints.this.getComment("DFS.last"); 
                    args = new Object[]{new Integer(d.DFS_stack[d.DFS_head + 1])}; 
                    break;
                }
                case 18: { // Вершина не последняя в стеке
                    comment = ArticulationPoints.this.getComment("DFS.notLast"); 
                    args = new Object[]{new Integer(d.DFS_stack[d.DFS_head])}; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = ArticulationPoints.this.getComment("DFS.END_STATE"); 
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
                case 1: { // Одход в глубину
                    				d.visualizer.updateVertex(-1);
                    				d.visualizer.stopEditMode();
                    break;
                }
                case 2: { // Посещаем вершину, с которой начинаем обход и добавляем ее в стек
                    				d.visualizer.updateVertex(d.root);
                    break;
                }
                case 10: { // Посещаем следующую вершину и добавляем ее в стек
                    										d.visualizer.updateVertex(d.DFS_i);
                    break;
                }
                case 14: { // Вывод комментария
                    							d.visualizer.updateVertex(d.DFS_stack[d.DFS_head]);
                    break;
                }
                case 17: { // Вершина последняя в стеке
                    									d.visualizer.updateVertex(d.DFS_stack[d.DFS_head]);
                    break;
                }
                case 18: { // Вершина не последняя в стеке
                    									d.visualizer.updateVertex(d.DFS_stack[d.DFS_head]);
                    break;
                }
            }
        }
    }

    /**
      * Находим low для каждой вершины..
      */
    private final class findLow extends BaseAutomata implements Automata {
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
        public findLow() {
            super( 
                "findLow", 
                0, // Номер начального состояния 
                27, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация i", 
                    "Обходим дерево поиска в обратном порядке", 
                    "Инициализация переменных", 
                    "Рассматриваем всех потомков вершины", 
                    "Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска", 
                    "Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска (окончание)", 
                    "Low ребенка меньше, чем текущее значение low", 
                    "Low ребенка меньше, чем текущее значение low (окончание)", 
                    "Нашли нового ребенка", 
                    "Присваиваем low текущей вершины новое значение", 
                    "Нашли нового ребенка", 
                    "Инкремент j", 
                    "Инициализация j", 
                    "Рассматриваем все обратные ребра", 
                    "Существует ли обратное ребро, соединяющее текущую вершину с j-той", 
                    "Существует ли обратное ребро, соединяющее текущую вершину с j-той (окончание)", 
                    "Номер j-той вершины при обходе меньше, чем текущее значение low", 
                    "Номер j-той вершины при обходе меньше, чем текущее значение low (окончание)", 
                    "Нашли новое обратное ребро", 
                    "Присваиваем low текущей вершины новое значение", 
                    "Инкремент j", 
                    "Нашли новое обратное ребро", 
                    "Инкремент j", 
                    "Инкремент j", 
                    "Декремент j", 
                    "Числа low для всех вершин найдены.", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    1, // Инициализация i 
                    -1, // Обходим дерево поиска в обратном порядке 
                    0, // Инициализация переменных 
                    -1, // Рассматриваем всех потомков вершины 
                    -1, // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска 
                    -1, // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска (окончание) 
                    -1, // Low ребенка меньше, чем текущее значение low 
                    -1, // Low ребенка меньше, чем текущее значение low (окончание) 
                    0, // Нашли нового ребенка 
                    0, // Присваиваем low текущей вершины новое значение 
                    0, // Нашли нового ребенка 
                    -1, // Инкремент j 
                    -1, // Инициализация j 
                    -1, // Рассматриваем все обратные ребра 
                    -1, // Существует ли обратное ребро, соединяющее текущую вершину с j-той 
                    -1, // Существует ли обратное ребро, соединяющее текущую вершину с j-той (окончание) 
                    -1, // Номер j-той вершины при обходе меньше, чем текущее значение low 
                    -1, // Номер j-той вершины при обходе меньше, чем текущее значение low (окончание) 
                    0, // Нашли новое обратное ребро 
                    0, // Присваиваем low текущей вершины новое значение 
                    -1, // Инкремент j 
                    0, // Нашли новое обратное ребро 
                    -1, // Инкремент j 
                    -1, // Инкремент j 
                    -1, // Декремент j 
                    0, // Числа low для всех вершин найдены. 
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
                    state = 1; // Инициализация i
                    break;
                }
                case 1: { // Инициализация i
                    stack.pushBoolean(false); 
                    state = 2; // Обходим дерево поиска в обратном порядке
                    break;
                }
                case 2: { // Обходим дерево поиска в обратном порядке
                    if (d.findLow_i >= 0) {
                        state = 3; // Инициализация переменных
                    } else {
                        state = 26; // Числа low для всех вершин найдены.
                    }
                    break;
                }
                case 3: { // Инициализация переменных
                    stack.pushBoolean(false); 
                    state = 4; // Рассматриваем всех потомков вершины
                    break;
                }
                case 4: { // Рассматриваем всех потомков вершины
                    if (d.findLow_j < d.numberOfVertices) {
                        state = 5; // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска
                    } else {
                        state = 13; // Инициализация j
                    }
                    break;
                }
                case 5: { // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска
                    if (d.aMatrix[d.findLow_currentVertex][d.findLow_j] == 4) {
                        state = 7; // Low ребенка меньше, чем текущее значение low
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска (окончание)
                    }
                    break;
                }
                case 6: { // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска (окончание)
                    state = 12; // Инкремент j
                    break;
                }
                case 7: { // Low ребенка меньше, чем текущее значение low
                    if (d.low[d.findLow_j] < d.low[d.findLow_currentVertex]) {
                        state = 9; // Нашли нового ребенка
                    } else {
                        state = 11; // Нашли нового ребенка
                    }
                    break;
                }
                case 8: { // Low ребенка меньше, чем текущее значение low (окончание)
                    stack.pushBoolean(true); 
                    state = 6; // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска (окончание)
                    break;
                }
                case 9: { // Нашли нового ребенка
                    state = 10; // Присваиваем low текущей вершины новое значение
                    break;
                }
                case 10: { // Присваиваем low текущей вершины новое значение
                    stack.pushBoolean(true); 
                    state = 8; // Low ребенка меньше, чем текущее значение low (окончание)
                    break;
                }
                case 11: { // Нашли нового ребенка
                    stack.pushBoolean(false); 
                    state = 8; // Low ребенка меньше, чем текущее значение low (окончание)
                    break;
                }
                case 12: { // Инкремент j
                    stack.pushBoolean(true); 
                    state = 4; // Рассматриваем всех потомков вершины
                    break;
                }
                case 13: { // Инициализация j
                    stack.pushBoolean(false); 
                    state = 14; // Рассматриваем все обратные ребра
                    break;
                }
                case 14: { // Рассматриваем все обратные ребра
                    if (d.findLow_j < d.numberOfVertices) {
                        state = 15; // Существует ли обратное ребро, соединяющее текущую вершину с j-той
                    } else {
                        state = 25; // Декремент j
                    }
                    break;
                }
                case 15: { // Существует ли обратное ребро, соединяющее текущую вершину с j-той
                    if (d.aMatrix[d.findLow_currentVertex][d.findLow_j] == 3 && d.aMatrix[d.findLow_j][d.findLow_currentVertex] != 4                 && d.dfnumber[d.findLow_j] < d.dfnumber[d.findLow_currentVertex]) {
                        state = 17; // Номер j-той вершины при обходе меньше, чем текущее значение low
                    } else {
                        stack.pushBoolean(false); 
                        state = 16; // Существует ли обратное ребро, соединяющее текущую вершину с j-той (окончание)
                    }
                    break;
                }
                case 16: { // Существует ли обратное ребро, соединяющее текущую вершину с j-той (окончание)
                    state = 24; // Инкремент j
                    break;
                }
                case 17: { // Номер j-той вершины при обходе меньше, чем текущее значение low
                    if (d.dfnumber[d.findLow_j] < d.low[d.findLow_currentVertex]) {
                        state = 19; // Нашли новое обратное ребро
                    } else {
                        state = 22; // Нашли новое обратное ребро
                    }
                    break;
                }
                case 18: { // Номер j-той вершины при обходе меньше, чем текущее значение low (окончание)
                    stack.pushBoolean(true); 
                    state = 16; // Существует ли обратное ребро, соединяющее текущую вершину с j-той (окончание)
                    break;
                }
                case 19: { // Нашли новое обратное ребро
                    state = 20; // Присваиваем low текущей вершины новое значение
                    break;
                }
                case 20: { // Присваиваем low текущей вершины новое значение
                    state = 21; // Инкремент j
                    break;
                }
                case 21: { // Инкремент j
                    stack.pushBoolean(true); 
                    state = 18; // Номер j-той вершины при обходе меньше, чем текущее значение low (окончание)
                    break;
                }
                case 22: { // Нашли новое обратное ребро
                    state = 23; // Инкремент j
                    break;
                }
                case 23: { // Инкремент j
                    stack.pushBoolean(false); 
                    state = 18; // Номер j-той вершины при обходе меньше, чем текущее значение low (окончание)
                    break;
                }
                case 24: { // Инкремент j
                    stack.pushBoolean(true); 
                    state = 14; // Рассматриваем все обратные ребра
                    break;
                }
                case 25: { // Декремент j
                    stack.pushBoolean(true); 
                    state = 2; // Обходим дерево поиска в обратном порядке
                    break;
                }
                case 26: { // Числа low для всех вершин найдены.
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация i
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
                case 2: { // Обходим дерево поиска в обратном порядке
                    break;
                }
                case 3: { // Инициализация переменных
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
                case 4: { // Рассматриваем всех потомков вершины
                    break;
                }
                case 5: { // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска
                    break;
                }
                case 6: { // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска (окончание)
                    break;
                }
                case 7: { // Low ребенка меньше, чем текущее значение low
                    break;
                }
                case 8: { // Low ребенка меньше, чем текущее значение low (окончание)
                    break;
                }
                case 9: { // Нашли нового ребенка
                    startSection();
                    storeField(d, "findLow_activeVertexInLow");
                    										d.findLow_activeVertexInLow = d.findLow_j;
                    storeField(d, "findLow_activeVertexInDfnum");
                    										d.findLow_activeVertexInDfnum = -1;
                    break;
                }
                case 10: { // Присваиваем low текущей вершины новое значение
                    startSection();
                    storeArray(d.low, d.findLow_currentVertex);
                    										d.low[d.findLow_currentVertex] = d.low[d.findLow_j];
                    break;
                }
                case 11: { // Нашли нового ребенка
                    startSection();
                    storeField(d, "findLow_activeVertexInLow");
                    										d.findLow_activeVertexInLow = d.findLow_j;
                    storeField(d, "findLow_activeVertexInDfnum");
                    										d.findLow_activeVertexInDfnum = -1;
                    break;
                }
                case 12: { // Инкремент j
                    startSection();
                    storeField(d, "findLow_j");
                    						d.findLow_j = d.findLow_j + 1;
                    break;
                }
                case 13: { // Инициализация j
                    startSection();
                    storeField(d, "findLow_j");
                    					d.findLow_j = 0;
                    break;
                }
                case 14: { // Рассматриваем все обратные ребра
                    break;
                }
                case 15: { // Существует ли обратное ребро, соединяющее текущую вершину с j-той
                    break;
                }
                case 16: { // Существует ли обратное ребро, соединяющее текущую вершину с j-той (окончание)
                    break;
                }
                case 17: { // Номер j-той вершины при обходе меньше, чем текущее значение low
                    break;
                }
                case 18: { // Номер j-той вершины при обходе меньше, чем текущее значение low (окончание)
                    break;
                }
                case 19: { // Нашли новое обратное ребро
                    startSection();
                    storeField(d, "findLow_activeVertexInLow");
                    										d.findLow_activeVertexInLow = -1;
                    storeField(d, "findLow_activeVertexInDfnum");
                    										d.findLow_activeVertexInDfnum = d.findLow_j;
                    storeArray(d.aMatrix[d.findLow_currentVertex], d.findLow_j);
                    										d.aMatrix[d.findLow_currentVertex][d.findLow_j] = 10;
                    break;
                }
                case 20: { // Присваиваем low текущей вершины новое значение
                    startSection();
                    storeArray(d.low, d.findLow_currentVertex);
                    										d.low[d.findLow_currentVertex] = d.dfnumber[d.findLow_j];
                    break;
                }
                case 21: { // Инкремент j
                    startSection();
                    storeArray(d.aMatrix[d.findLow_currentVertex], d.findLow_j);
                    										d.aMatrix[d.findLow_currentVertex][d.findLow_j] = 3;
                    break;
                }
                case 22: { // Нашли новое обратное ребро
                    startSection();
                    storeField(d, "findLow_activeVertexInLow");
                    										d.findLow_activeVertexInLow = -1;
                    storeField(d, "findLow_activeVertexInDfnum");
                    										d.findLow_activeVertexInDfnum = d.findLow_j;
                    storeArray(d.aMatrix[d.findLow_currentVertex], d.findLow_j);
                    										d.aMatrix[d.findLow_currentVertex][d.findLow_j] = 10;
                    break;
                }
                case 23: { // Инкремент j
                    startSection();
                    storeArray(d.aMatrix[d.findLow_currentVertex], d.findLow_j);
                    										d.aMatrix[d.findLow_currentVertex][d.findLow_j] = 3;
                    break;
                }
                case 24: { // Инкремент j
                    startSection();
                    storeField(d, "findLow_j");
                    						d.findLow_j = d.findLow_j + 1;
                    break;
                }
                case 25: { // Декремент j
                    startSection();
                    storeField(d, "findLow_i");
                    					d.findLow_i = d.findLow_i - 1;
                    break;
                }
                case 26: { // Числа low для всех вершин найдены.
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Инициализация i
                    restoreSection();
                    break;
                }
                case 2: { // Обходим дерево поиска в обратном порядке
                    break;
                }
                case 3: { // Инициализация переменных
                    restoreSection();
                    break;
                }
                case 4: { // Рассматриваем всех потомков вершины
                    break;
                }
                case 5: { // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска
                    break;
                }
                case 6: { // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска (окончание)
                    break;
                }
                case 7: { // Low ребенка меньше, чем текущее значение low
                    break;
                }
                case 8: { // Low ребенка меньше, чем текущее значение low (окончание)
                    break;
                }
                case 9: { // Нашли нового ребенка
                    restoreSection();
                    break;
                }
                case 10: { // Присваиваем low текущей вершины новое значение
                    restoreSection();
                    break;
                }
                case 11: { // Нашли нового ребенка
                    restoreSection();
                    break;
                }
                case 12: { // Инкремент j
                    restoreSection();
                    break;
                }
                case 13: { // Инициализация j
                    restoreSection();
                    break;
                }
                case 14: { // Рассматриваем все обратные ребра
                    break;
                }
                case 15: { // Существует ли обратное ребро, соединяющее текущую вершину с j-той
                    break;
                }
                case 16: { // Существует ли обратное ребро, соединяющее текущую вершину с j-той (окончание)
                    break;
                }
                case 17: { // Номер j-той вершины при обходе меньше, чем текущее значение low
                    break;
                }
                case 18: { // Номер j-той вершины при обходе меньше, чем текущее значение low (окончание)
                    break;
                }
                case 19: { // Нашли новое обратное ребро
                    restoreSection();
                    break;
                }
                case 20: { // Присваиваем low текущей вершины новое значение
                    restoreSection();
                    break;
                }
                case 21: { // Инкремент j
                    restoreSection();
                    break;
                }
                case 22: { // Нашли новое обратное ребро
                    restoreSection();
                    break;
                }
                case 23: { // Инкремент j
                    restoreSection();
                    break;
                }
                case 24: { // Инкремент j
                    restoreSection();
                    break;
                }
                case 25: { // Декремент j
                    restoreSection();
                    break;
                }
                case 26: { // Числа low для всех вершин найдены.
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация i
                    state = START_STATE; 
                    break;
                }
                case 2: { // Обходим дерево поиска в обратном порядке
                    if (stack.popBoolean()) {
                        state = 25; // Декремент j
                    } else {
                        state = 1; // Инициализация i
                    }
                    break;
                }
                case 3: { // Инициализация переменных
                    state = 2; // Обходим дерево поиска в обратном порядке
                    break;
                }
                case 4: { // Рассматриваем всех потомков вершины
                    if (stack.popBoolean()) {
                        state = 12; // Инкремент j
                    } else {
                        state = 3; // Инициализация переменных
                    }
                    break;
                }
                case 5: { // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска
                    state = 4; // Рассматриваем всех потомков вершины
                    break;
                }
                case 6: { // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // Low ребенка меньше, чем текущее значение low (окончание)
                    } else {
                        state = 5; // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска
                    }
                    break;
                }
                case 7: { // Low ребенка меньше, чем текущее значение low
                    state = 5; // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска
                    break;
                }
                case 8: { // Low ребенка меньше, чем текущее значение low (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // Присваиваем low текущей вершины новое значение
                    } else {
                        state = 11; // Нашли нового ребенка
                    }
                    break;
                }
                case 9: { // Нашли нового ребенка
                    state = 7; // Low ребенка меньше, чем текущее значение low
                    break;
                }
                case 10: { // Присваиваем low текущей вершины новое значение
                    state = 9; // Нашли нового ребенка
                    break;
                }
                case 11: { // Нашли нового ребенка
                    state = 7; // Low ребенка меньше, чем текущее значение low
                    break;
                }
                case 12: { // Инкремент j
                    state = 6; // Существует ли ребро, соединяющее текущую вершину с ее ребенком в дереве поиска (окончание)
                    break;
                }
                case 13: { // Инициализация j
                    state = 4; // Рассматриваем всех потомков вершины
                    break;
                }
                case 14: { // Рассматриваем все обратные ребра
                    if (stack.popBoolean()) {
                        state = 24; // Инкремент j
                    } else {
                        state = 13; // Инициализация j
                    }
                    break;
                }
                case 15: { // Существует ли обратное ребро, соединяющее текущую вершину с j-той
                    state = 14; // Рассматриваем все обратные ребра
                    break;
                }
                case 16: { // Существует ли обратное ребро, соединяющее текущую вершину с j-той (окончание)
                    if (stack.popBoolean()) {
                        state = 18; // Номер j-той вершины при обходе меньше, чем текущее значение low (окончание)
                    } else {
                        state = 15; // Существует ли обратное ребро, соединяющее текущую вершину с j-той
                    }
                    break;
                }
                case 17: { // Номер j-той вершины при обходе меньше, чем текущее значение low
                    state = 15; // Существует ли обратное ребро, соединяющее текущую вершину с j-той
                    break;
                }
                case 18: { // Номер j-той вершины при обходе меньше, чем текущее значение low (окончание)
                    if (stack.popBoolean()) {
                        state = 21; // Инкремент j
                    } else {
                        state = 23; // Инкремент j
                    }
                    break;
                }
                case 19: { // Нашли новое обратное ребро
                    state = 17; // Номер j-той вершины при обходе меньше, чем текущее значение low
                    break;
                }
                case 20: { // Присваиваем low текущей вершины новое значение
                    state = 19; // Нашли новое обратное ребро
                    break;
                }
                case 21: { // Инкремент j
                    state = 20; // Присваиваем low текущей вершины новое значение
                    break;
                }
                case 22: { // Нашли новое обратное ребро
                    state = 17; // Номер j-той вершины при обходе меньше, чем текущее значение low
                    break;
                }
                case 23: { // Инкремент j
                    state = 22; // Нашли новое обратное ребро
                    break;
                }
                case 24: { // Инкремент j
                    state = 16; // Существует ли обратное ребро, соединяющее текущую вершину с j-той (окончание)
                    break;
                }
                case 25: { // Декремент j
                    state = 14; // Рассматриваем все обратные ребра
                    break;
                }
                case 26: { // Числа low для всех вершин найдены.
                    state = 2; // Обходим дерево поиска в обратном порядке
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 26; // Числа low для всех вершин найдены.
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
                case 1: { // Инициализация i
                    comment = ArticulationPoints.this.getComment("findLow.LowLoopInitI"); 
                    break;
                }
                case 3: { // Инициализация переменных
                    comment = ArticulationPoints.this.getComment("findLow.InitVariables"); 
                    args = new Object[]{new Integer(d.findLow_currentVertex)}; 
                    break;
                }
                case 9: { // Нашли нового ребенка
                    comment = ArticulationPoints.this.getComment("findLow.newChild"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex), new Integer(d.low[d.findLow_j]), new Integer(d.low[d.findLow_currentVertex])}; 
                    break;
                }
                case 10: { // Присваиваем low текущей вершины новое значение
                    comment = ArticulationPoints.this.getComment("findLow.newValue1"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex)}; 
                    break;
                }
                case 11: { // Нашли нового ребенка
                    comment = ArticulationPoints.this.getComment("findLow.newChild2"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex), new Integer(d.low[d.findLow_j]), new Integer(d.low[d.findLow_currentVertex])}; 
                    break;
                }
                case 19: { // Нашли новое обратное ребро
                    comment = ArticulationPoints.this.getComment("findLow.newBackEdge1"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex), new Integer(d.dfnumber[d.findLow_j]), new Integer(d.low[d.findLow_currentVertex])}; 
                    break;
                }
                case 20: { // Присваиваем low текущей вершины новое значение
                    comment = ArticulationPoints.this.getComment("findLow.newValue2"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex)}; 
                    break;
                }
                case 22: { // Нашли новое обратное ребро
                    comment = ArticulationPoints.this.getComment("findLow.newBackEdge2"); 
                    args = new Object[]{new Integer(d.findLow_j), new Integer(d.findLow_currentVertex), new Integer(d.dfnumber[d.findLow_j]), new Integer(d.low[d.findLow_currentVertex])}; 
                    break;
                }
                case 26: { // Числа low для всех вершин найдены.
                    comment = ArticulationPoints.this.getComment("findLow.endFindLow"); 
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
                case 1: { // Инициализация i
                    				d.visualizer.updateVertex(-1);
                    break;
                }
                case 3: { // Инициализация переменных
                    					d.visualizer.updateVertex(-1);
                    break;
                }
                case 9: { // Нашли нового ребенка
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 10: { // Присваиваем low текущей вершины новое значение
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 11: { // Нашли нового ребенка
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 19: { // Нашли новое обратное ребро
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 20: { // Присваиваем low текущей вершины новое значение
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 22: { // Нашли новое обратное ребро
                    										d.visualizer.updateVertex(d.findLow_j);
                    break;
                }
                case 26: { // Числа low для всех вершин найдены.
                    				d.visualizer.updateVertex(-1);
                    break;
                }
            }
        }
    }

    /**
      * Ищет точки сочленения..
      */
    private final class findArticulationPoints extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 39;

        /**
          * Конструктор.
          */
        public findArticulationPoints() {
            super( 
                "findArticulationPoints", 
                0, // Номер начального состояния 
                39, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация i", 
                    "Подсчет числа детей", 
                    "Является ли i-тая вершина ребенком", 
                    "Является ли i-тая вершина ребенком (окончание)", 
                    "Инкремент счетчика детей", 
                    "Инкремент i", 
                    "Является ли корень дерева обхода точкой сочленения", 
                    "Является ли корень дерева обхода точкой сочленения (окончание)", 
                    "Корень является точкой сочленения", 
                    "Корень не является точкой сочленения", 
                    "Инициализация i", 
                    "Обход посещенных вершин", 
                    "Была ли i-тая вершина посещена", 
                    "Была ли i-тая вершина посещена (окончание)", 
                    "Инициализация j", 
                    "Обход детей i-той вершины", 
                    "Является ли j-тая вершина ребенком i-той вершины", 
                    "Является ли j-тая вершина ребенком i-той вершины (окончание)", 
                    "Условие", 
                    "Условие (окончание)", 
                    "Изменяем флаг", 
                    "Выводим комментарий", 
                    "Инкремент j", 
                    "Был ли найден нужный ребенок", 
                    "Был ли найден нужный ребенок (окончание)", 
                    "i-тая вершина точка сочленения", 
                    "i-тая вершина не является точкой сочленения", 
                    "Инкремент i", 
                    "Инициализация i", 
                    "Обход ребер.", 
                    "Инициализация j", 
                    "Обход ребер.", 
                    "Есть ли ребро", 
                    "Есть ли ребро (окончание)", 
                    "Меняем статус ребра", 
                    "Инкремент j", 
                    "Инкремент i", 
                    "Конец данного автомата", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    1, // Инициализация i 
                    -1, // Подсчет числа детей 
                    -1, // Является ли i-тая вершина ребенком 
                    -1, // Является ли i-тая вершина ребенком (окончание) 
                    -1, // Инкремент счетчика детей 
                    -1, // Инкремент i 
                    -1, // Является ли корень дерева обхода точкой сочленения 
                    -1, // Является ли корень дерева обхода точкой сочленения (окончание) 
                    0, // Корень является точкой сочленения 
                    0, // Корень не является точкой сочленения 
                    -1, // Инициализация i 
                    -1, // Обход посещенных вершин 
                    -1, // Была ли i-тая вершина посещена 
                    -1, // Была ли i-тая вершина посещена (окончание) 
                    0, // Инициализация j 
                    -1, // Обход детей i-той вершины 
                    -1, // Является ли j-тая вершина ребенком i-той вершины 
                    -1, // Является ли j-тая вершина ребенком i-той вершины (окончание) 
                    -1, // Условие 
                    -1, // Условие (окончание) 
                    0, // Изменяем флаг 
                    0, // Выводим комментарий 
                    -1, // Инкремент j 
                    -1, // Был ли найден нужный ребенок 
                    -1, // Был ли найден нужный ребенок (окончание) 
                    0, // i-тая вершина точка сочленения 
                    0, // i-тая вершина не является точкой сочленения 
                    -1, // Инкремент i 
                    -1, // Инициализация i 
                    -1, // Обход ребер. 
                    -1, // Инициализация j 
                    -1, // Обход ребер. 
                    -1, // Есть ли ребро 
                    -1, // Есть ли ребро (окончание) 
                    -1, // Меняем статус ребра 
                    -1, // Инкремент j 
                    -1, // Инкремент i 
                    0, // Конец данного автомата 
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
                    state = 1; // Инициализация i
                    break;
                }
                case 1: { // Инициализация i
                    stack.pushBoolean(false); 
                    state = 2; // Подсчет числа детей
                    break;
                }
                case 2: { // Подсчет числа детей
                    if (d.findArticulationPoints_i < d.numberOfVertices) {
                        state = 3; // Является ли i-тая вершина ребенком
                    } else {
                        state = 7; // Является ли корень дерева обхода точкой сочленения
                    }
                    break;
                }
                case 3: { // Является ли i-тая вершина ребенком
                    if (d.aMatrix[d.root][d.findArticulationPoints_i] == 4) {
                        state = 5; // Инкремент счетчика детей
                    } else {
                        stack.pushBoolean(false); 
                        state = 4; // Является ли i-тая вершина ребенком (окончание)
                    }
                    break;
                }
                case 4: { // Является ли i-тая вершина ребенком (окончание)
                    state = 6; // Инкремент i
                    break;
                }
                case 5: { // Инкремент счетчика детей
                    stack.pushBoolean(true); 
                    state = 4; // Является ли i-тая вершина ребенком (окончание)
                    break;
                }
                case 6: { // Инкремент i
                    stack.pushBoolean(true); 
                    state = 2; // Подсчет числа детей
                    break;
                }
                case 7: { // Является ли корень дерева обхода точкой сочленения
                    if (d.findArticulationPoints_childsCounter > 1) {
                        state = 9; // Корень является точкой сочленения
                    } else {
                        state = 10; // Корень не является точкой сочленения
                    }
                    break;
                }
                case 8: { // Является ли корень дерева обхода точкой сочленения (окончание)
                    state = 11; // Инициализация i
                    break;
                }
                case 9: { // Корень является точкой сочленения
                    stack.pushBoolean(true); 
                    state = 8; // Является ли корень дерева обхода точкой сочленения (окончание)
                    break;
                }
                case 10: { // Корень не является точкой сочленения
                    stack.pushBoolean(false); 
                    state = 8; // Является ли корень дерева обхода точкой сочленения (окончание)
                    break;
                }
                case 11: { // Инициализация i
                    stack.pushBoolean(false); 
                    state = 12; // Обход посещенных вершин
                    break;
                }
                case 12: { // Обход посещенных вершин
                    if (d.findArticulationPoints_i < d.numberOfVertices) {
                        state = 13; // Была ли i-тая вершина посещена
                    } else {
                        state = 29; // Инициализация i
                    }
                    break;
                }
                case 13: { // Была ли i-тая вершина посещена
                    if (d.visited[d.findArticulationPoints_i] && d.root != d.findArticulationPoints_i) {
                        state = 15; // Инициализация j
                    } else {
                        stack.pushBoolean(false); 
                        state = 14; // Была ли i-тая вершина посещена (окончание)
                    }
                    break;
                }
                case 14: { // Была ли i-тая вершина посещена (окончание)
                    state = 28; // Инкремент i
                    break;
                }
                case 15: { // Инициализация j
                    stack.pushBoolean(false); 
                    state = 16; // Обход детей i-той вершины
                    break;
                }
                case 16: { // Обход детей i-той вершины
                    if (d.findArticulationPoints_j < d.numberOfVertices && !d.findArticulationPoints_flag) {
                        state = 17; // Является ли j-тая вершина ребенком i-той вершины
                    } else {
                        state = 24; // Был ли найден нужный ребенок
                    }
                    break;
                }
                case 17: { // Является ли j-тая вершина ребенком i-той вершины
                    if (d.aMatrix[d.findArticulationPoints_i][d.findArticulationPoints_j] == 4) {
                        state = 19; // Условие
                    } else {
                        stack.pushBoolean(false); 
                        state = 18; // Является ли j-тая вершина ребенком i-той вершины (окончание)
                    }
                    break;
                }
                case 18: { // Является ли j-тая вершина ребенком i-той вершины (окончание)
                    state = 23; // Инкремент j
                    break;
                }
                case 19: { // Условие
                    if (d.low[d.findArticulationPoints_j] >= d.dfnumber[d.findArticulationPoints_i]) {
                        state = 21; // Изменяем флаг
                    } else {
                        state = 22; // Выводим комментарий
                    }
                    break;
                }
                case 20: { // Условие (окончание)
                    stack.pushBoolean(true); 
                    state = 18; // Является ли j-тая вершина ребенком i-той вершины (окончание)
                    break;
                }
                case 21: { // Изменяем флаг
                    stack.pushBoolean(true); 
                    state = 20; // Условие (окончание)
                    break;
                }
                case 22: { // Выводим комментарий
                    stack.pushBoolean(false); 
                    state = 20; // Условие (окончание)
                    break;
                }
                case 23: { // Инкремент j
                    stack.pushBoolean(true); 
                    state = 16; // Обход детей i-той вершины
                    break;
                }
                case 24: { // Был ли найден нужный ребенок
                    if (d.findArticulationPoints_flag) {
                        state = 26; // i-тая вершина точка сочленения
                    } else {
                        state = 27; // i-тая вершина не является точкой сочленения
                    }
                    break;
                }
                case 25: { // Был ли найден нужный ребенок (окончание)
                    stack.pushBoolean(true); 
                    state = 14; // Была ли i-тая вершина посещена (окончание)
                    break;
                }
                case 26: { // i-тая вершина точка сочленения
                    stack.pushBoolean(true); 
                    state = 25; // Был ли найден нужный ребенок (окончание)
                    break;
                }
                case 27: { // i-тая вершина не является точкой сочленения
                    stack.pushBoolean(false); 
                    state = 25; // Был ли найден нужный ребенок (окончание)
                    break;
                }
                case 28: { // Инкремент i
                    stack.pushBoolean(true); 
                    state = 12; // Обход посещенных вершин
                    break;
                }
                case 29: { // Инициализация i
                    stack.pushBoolean(false); 
                    state = 30; // Обход ребер.
                    break;
                }
                case 30: { // Обход ребер.
                    if (d.findArticulationPoints_i < d.numberOfVertices) {
                        state = 31; // Инициализация j
                    } else {
                        state = 38; // Конец данного автомата
                    }
                    break;
                }
                case 31: { // Инициализация j
                    stack.pushBoolean(false); 
                    state = 32; // Обход ребер.
                    break;
                }
                case 32: { // Обход ребер.
                    if (d.findArticulationPoints_j < d.numberOfVertices) {
                        state = 33; // Есть ли ребро
                    } else {
                        state = 37; // Инкремент i
                    }
                    break;
                }
                case 33: { // Есть ли ребро
                    if (d.aMatrix[d.findArticulationPoints_i][d.findArticulationPoints_j] > 0) {
                        state = 35; // Меняем статус ребра
                    } else {
                        stack.pushBoolean(false); 
                        state = 34; // Есть ли ребро (окончание)
                    }
                    break;
                }
                case 34: { // Есть ли ребро (окончание)
                    state = 36; // Инкремент j
                    break;
                }
                case 35: { // Меняем статус ребра
                    stack.pushBoolean(true); 
                    state = 34; // Есть ли ребро (окончание)
                    break;
                }
                case 36: { // Инкремент j
                    stack.pushBoolean(true); 
                    state = 32; // Обход ребер.
                    break;
                }
                case 37: { // Инкремент i
                    stack.pushBoolean(true); 
                    state = 30; // Обход ребер.
                    break;
                }
                case 38: { // Конец данного автомата
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация i
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
                case 2: { // Подсчет числа детей
                    break;
                }
                case 3: { // Является ли i-тая вершина ребенком
                    break;
                }
                case 4: { // Является ли i-тая вершина ребенком (окончание)
                    break;
                }
                case 5: { // Инкремент счетчика детей
                    startSection();
                    storeField(d, "findArticulationPoints_childsCounter");
                    							d.findArticulationPoints_childsCounter = d.findArticulationPoints_childsCounter + 1;
                    break;
                }
                case 6: { // Инкремент i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    					d.findArticulationPoints_i = d.findArticulationPoints_i + 1;
                    break;
                }
                case 7: { // Является ли корень дерева обхода точкой сочленения
                    break;
                }
                case 8: { // Является ли корень дерева обхода точкой сочленения (окончание)
                    break;
                }
                case 9: { // Корень является точкой сочленения
                    startSection();
                    storeArray(d.articulationPoints, d.root);
                    						d.articulationPoints[d.root] = 1;
                    storeArray(d.visited, d.root);
                    						d.visited[d.root] = false;
                    break;
                }
                case 10: { // Корень не является точкой сочленения
                    startSection();
                    storeArray(d.articulationPoints, d.root);
                    						d.articulationPoints[d.root] = -1;
                    storeArray(d.visited, d.root);
                    						d.visited[d.root] = false;
                    break;
                }
                case 11: { // Инициализация i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    				d.findArticulationPoints_i = 0;
                    break;
                }
                case 12: { // Обход посещенных вершин
                    break;
                }
                case 13: { // Была ли i-тая вершина посещена
                    break;
                }
                case 14: { // Была ли i-тая вершина посещена (окончание)
                    break;
                }
                case 15: { // Инициализация j
                    startSection();
                    storeField(d, "findArticulationPoints_j");
                    							d.findArticulationPoints_j = 0;
                    storeField(d, "findArticulationPoints_flag");
                    							d.findArticulationPoints_flag = false;
                    storeField(d, "findArticulationPoints_currentVertex");
                    							d.findArticulationPoints_currentVertex = d.findArticulationPoints_i;
                    break;
                }
                case 16: { // Обход детей i-той вершины
                    break;
                }
                case 17: { // Является ли j-тая вершина ребенком i-той вершины
                    break;
                }
                case 18: { // Является ли j-тая вершина ребенком i-той вершины (окончание)
                    break;
                }
                case 19: { // Условие
                    break;
                }
                case 20: { // Условие (окончание)
                    break;
                }
                case 21: { // Изменяем флаг
                    startSection();
                    storeField(d, "findArticulationPoints_flag");
                    												d.findArticulationPoints_flag = true;
                    storeField(d, "findArticulationPoints_activeVertexInLow");
                    												d.findArticulationPoints_activeVertexInLow = d.findArticulationPoints_j;
                    break;
                }
                case 22: { // Выводим комментарий
                    startSection();
                    storeField(d, "findArticulationPoints_activeVertexInLow");
                    												d.findArticulationPoints_activeVertexInLow = d.findArticulationPoints_j;
                    break;
                }
                case 23: { // Инкремент j
                    startSection();
                    storeField(d, "findArticulationPoints_j");
                    								d.findArticulationPoints_j = d.findArticulationPoints_j + 1;
                    break;
                }
                case 24: { // Был ли найден нужный ребенок
                    break;
                }
                case 25: { // Был ли найден нужный ребенок (окончание)
                    break;
                }
                case 26: { // i-тая вершина точка сочленения
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
                case 27: { // i-тая вершина не является точкой сочленения
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
                case 28: { // Инкремент i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    					d.findArticulationPoints_i = d.findArticulationPoints_i + 1;
                    break;
                }
                case 29: { // Инициализация i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    				d.findArticulationPoints_i = 0;
                    break;
                }
                case 30: { // Обход ребер.
                    break;
                }
                case 31: { // Инициализация j
                    startSection();
                    storeField(d, "findArticulationPoints_j");
                    					d.findArticulationPoints_j = 0;
                    break;
                }
                case 32: { // Обход ребер.
                    break;
                }
                case 33: { // Есть ли ребро
                    break;
                }
                case 34: { // Есть ли ребро (окончание)
                    break;
                }
                case 35: { // Меняем статус ребра
                    startSection();
                    storeArray(d.aMatrix[d.findArticulationPoints_i], d.findArticulationPoints_j);
                    								d.aMatrix[d.findArticulationPoints_i][d.findArticulationPoints_j] = 3;
                    break;
                }
                case 36: { // Инкремент j
                    startSection();
                    storeField(d, "findArticulationPoints_j");
                    						d.findArticulationPoints_j = d.findArticulationPoints_j + 1;
                    break;
                }
                case 37: { // Инкремент i
                    startSection();
                    storeField(d, "findArticulationPoints_i");
                    					d.findArticulationPoints_i = d.findArticulationPoints_i + 1;
                    break;
                }
                case 38: { // Конец данного автомата
                    startSection();
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
                case 1: { // Инициализация i
                    restoreSection();
                    break;
                }
                case 2: { // Подсчет числа детей
                    break;
                }
                case 3: { // Является ли i-тая вершина ребенком
                    break;
                }
                case 4: { // Является ли i-тая вершина ребенком (окончание)
                    break;
                }
                case 5: { // Инкремент счетчика детей
                    restoreSection();
                    break;
                }
                case 6: { // Инкремент i
                    restoreSection();
                    break;
                }
                case 7: { // Является ли корень дерева обхода точкой сочленения
                    break;
                }
                case 8: { // Является ли корень дерева обхода точкой сочленения (окончание)
                    break;
                }
                case 9: { // Корень является точкой сочленения
                    restoreSection();
                    break;
                }
                case 10: { // Корень не является точкой сочленения
                    restoreSection();
                    break;
                }
                case 11: { // Инициализация i
                    restoreSection();
                    break;
                }
                case 12: { // Обход посещенных вершин
                    break;
                }
                case 13: { // Была ли i-тая вершина посещена
                    break;
                }
                case 14: { // Была ли i-тая вершина посещена (окончание)
                    break;
                }
                case 15: { // Инициализация j
                    restoreSection();
                    break;
                }
                case 16: { // Обход детей i-той вершины
                    break;
                }
                case 17: { // Является ли j-тая вершина ребенком i-той вершины
                    break;
                }
                case 18: { // Является ли j-тая вершина ребенком i-той вершины (окончание)
                    break;
                }
                case 19: { // Условие
                    break;
                }
                case 20: { // Условие (окончание)
                    break;
                }
                case 21: { // Изменяем флаг
                    restoreSection();
                    break;
                }
                case 22: { // Выводим комментарий
                    restoreSection();
                    break;
                }
                case 23: { // Инкремент j
                    restoreSection();
                    break;
                }
                case 24: { // Был ли найден нужный ребенок
                    break;
                }
                case 25: { // Был ли найден нужный ребенок (окончание)
                    break;
                }
                case 26: { // i-тая вершина точка сочленения
                    restoreSection();
                    break;
                }
                case 27: { // i-тая вершина не является точкой сочленения
                    restoreSection();
                    break;
                }
                case 28: { // Инкремент i
                    restoreSection();
                    break;
                }
                case 29: { // Инициализация i
                    restoreSection();
                    break;
                }
                case 30: { // Обход ребер.
                    break;
                }
                case 31: { // Инициализация j
                    restoreSection();
                    break;
                }
                case 32: { // Обход ребер.
                    break;
                }
                case 33: { // Есть ли ребро
                    break;
                }
                case 34: { // Есть ли ребро (окончание)
                    break;
                }
                case 35: { // Меняем статус ребра
                    restoreSection();
                    break;
                }
                case 36: { // Инкремент j
                    restoreSection();
                    break;
                }
                case 37: { // Инкремент i
                    restoreSection();
                    break;
                }
                case 38: { // Конец данного автомата
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация i
                    state = START_STATE; 
                    break;
                }
                case 2: { // Подсчет числа детей
                    if (stack.popBoolean()) {
                        state = 6; // Инкремент i
                    } else {
                        state = 1; // Инициализация i
                    }
                    break;
                }
                case 3: { // Является ли i-тая вершина ребенком
                    state = 2; // Подсчет числа детей
                    break;
                }
                case 4: { // Является ли i-тая вершина ребенком (окончание)
                    if (stack.popBoolean()) {
                        state = 5; // Инкремент счетчика детей
                    } else {
                        state = 3; // Является ли i-тая вершина ребенком
                    }
                    break;
                }
                case 5: { // Инкремент счетчика детей
                    state = 3; // Является ли i-тая вершина ребенком
                    break;
                }
                case 6: { // Инкремент i
                    state = 4; // Является ли i-тая вершина ребенком (окончание)
                    break;
                }
                case 7: { // Является ли корень дерева обхода точкой сочленения
                    state = 2; // Подсчет числа детей
                    break;
                }
                case 8: { // Является ли корень дерева обхода точкой сочленения (окончание)
                    if (stack.popBoolean()) {
                        state = 9; // Корень является точкой сочленения
                    } else {
                        state = 10; // Корень не является точкой сочленения
                    }
                    break;
                }
                case 9: { // Корень является точкой сочленения
                    state = 7; // Является ли корень дерева обхода точкой сочленения
                    break;
                }
                case 10: { // Корень не является точкой сочленения
                    state = 7; // Является ли корень дерева обхода точкой сочленения
                    break;
                }
                case 11: { // Инициализация i
                    state = 8; // Является ли корень дерева обхода точкой сочленения (окончание)
                    break;
                }
                case 12: { // Обход посещенных вершин
                    if (stack.popBoolean()) {
                        state = 28; // Инкремент i
                    } else {
                        state = 11; // Инициализация i
                    }
                    break;
                }
                case 13: { // Была ли i-тая вершина посещена
                    state = 12; // Обход посещенных вершин
                    break;
                }
                case 14: { // Была ли i-тая вершина посещена (окончание)
                    if (stack.popBoolean()) {
                        state = 25; // Был ли найден нужный ребенок (окончание)
                    } else {
                        state = 13; // Была ли i-тая вершина посещена
                    }
                    break;
                }
                case 15: { // Инициализация j
                    state = 13; // Была ли i-тая вершина посещена
                    break;
                }
                case 16: { // Обход детей i-той вершины
                    if (stack.popBoolean()) {
                        state = 23; // Инкремент j
                    } else {
                        state = 15; // Инициализация j
                    }
                    break;
                }
                case 17: { // Является ли j-тая вершина ребенком i-той вершины
                    state = 16; // Обход детей i-той вершины
                    break;
                }
                case 18: { // Является ли j-тая вершина ребенком i-той вершины (окончание)
                    if (stack.popBoolean()) {
                        state = 20; // Условие (окончание)
                    } else {
                        state = 17; // Является ли j-тая вершина ребенком i-той вершины
                    }
                    break;
                }
                case 19: { // Условие
                    state = 17; // Является ли j-тая вершина ребенком i-той вершины
                    break;
                }
                case 20: { // Условие (окончание)
                    if (stack.popBoolean()) {
                        state = 21; // Изменяем флаг
                    } else {
                        state = 22; // Выводим комментарий
                    }
                    break;
                }
                case 21: { // Изменяем флаг
                    state = 19; // Условие
                    break;
                }
                case 22: { // Выводим комментарий
                    state = 19; // Условие
                    break;
                }
                case 23: { // Инкремент j
                    state = 18; // Является ли j-тая вершина ребенком i-той вершины (окончание)
                    break;
                }
                case 24: { // Был ли найден нужный ребенок
                    state = 16; // Обход детей i-той вершины
                    break;
                }
                case 25: { // Был ли найден нужный ребенок (окончание)
                    if (stack.popBoolean()) {
                        state = 26; // i-тая вершина точка сочленения
                    } else {
                        state = 27; // i-тая вершина не является точкой сочленения
                    }
                    break;
                }
                case 26: { // i-тая вершина точка сочленения
                    state = 24; // Был ли найден нужный ребенок
                    break;
                }
                case 27: { // i-тая вершина не является точкой сочленения
                    state = 24; // Был ли найден нужный ребенок
                    break;
                }
                case 28: { // Инкремент i
                    state = 14; // Была ли i-тая вершина посещена (окончание)
                    break;
                }
                case 29: { // Инициализация i
                    state = 12; // Обход посещенных вершин
                    break;
                }
                case 30: { // Обход ребер.
                    if (stack.popBoolean()) {
                        state = 37; // Инкремент i
                    } else {
                        state = 29; // Инициализация i
                    }
                    break;
                }
                case 31: { // Инициализация j
                    state = 30; // Обход ребер.
                    break;
                }
                case 32: { // Обход ребер.
                    if (stack.popBoolean()) {
                        state = 36; // Инкремент j
                    } else {
                        state = 31; // Инициализация j
                    }
                    break;
                }
                case 33: { // Есть ли ребро
                    state = 32; // Обход ребер.
                    break;
                }
                case 34: { // Есть ли ребро (окончание)
                    if (stack.popBoolean()) {
                        state = 35; // Меняем статус ребра
                    } else {
                        state = 33; // Есть ли ребро
                    }
                    break;
                }
                case 35: { // Меняем статус ребра
                    state = 33; // Есть ли ребро
                    break;
                }
                case 36: { // Инкремент j
                    state = 34; // Есть ли ребро (окончание)
                    break;
                }
                case 37: { // Инкремент i
                    state = 32; // Обход ребер.
                    break;
                }
                case 38: { // Конец данного автомата
                    state = 30; // Обход ребер.
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 38; // Конец данного автомата
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
                case 1: { // Инициализация i
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.FAPInitI1"); 
                    break;
                }
                case 9: { // Корень является точкой сочленения
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isRootArticulationPoint"); 
                    args = new Object[]{new Integer(d.root)}; 
                    break;
                }
                case 10: { // Корень не является точкой сочленения
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isNotRootArticulationPoint"); 
                    args = new Object[]{new Integer(d.root)}; 
                    break;
                }
                case 15: { // Инициализация j
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.FAPInitJ"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i)}; 
                    break;
                }
                case 21: { // Изменяем флаг
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isGreater"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i), new Integer(d.findArticulationPoints_j), new Integer(d.dfnumber[d.findArticulationPoints_i]), new Integer(d.low[d.findArticulationPoints_j])}; 
                    break;
                }
                case 22: { // Выводим комментарий
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isNotGreater"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i), new Integer(d.findArticulationPoints_j), new Integer(d.dfnumber[d.findArticulationPoints_i]), new Integer(d.low[d.findArticulationPoints_j])}; 
                    break;
                }
                case 26: { // i-тая вершина точка сочленения
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isArticulationPoint"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i)}; 
                    break;
                }
                case 27: { // i-тая вершина не является точкой сочленения
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.isNotArticulationPoint"); 
                    args = new Object[]{new Integer(d.findArticulationPoints_i)}; 
                    break;
                }
                case 38: { // Конец данного автомата
                    comment = ArticulationPoints.this.getComment("findArticulationPoints.AllArticulationPointsFound"); 
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
                case 1: { // Инициализация i
                    				d.visualizer.updateVertex(-1);
                    break;
                }
                case 9: { // Корень является точкой сочленения
                    						d.visualizer.updateVertex(-1);
                    break;
                }
                case 10: { // Корень не является точкой сочленения
                    						d.visualizer.updateVertex(-1);
                    break;
                }
                case 15: { // Инициализация j
                    							d.visualizer.updateVertex(-1);
                    break;
                }
                case 21: { // Изменяем флаг
                    												d.visualizer.updateVertex(d.findArticulationPoints_j);
                    break;
                }
                case 22: { // Выводим комментарий
                    												d.visualizer.updateVertex(d.findArticulationPoints_j);
                    break;
                }
                case 26: { // i-тая вершина точка сочленения
                    									d.visualizer.updateVertex(-1);
                    break;
                }
                case 27: { // i-тая вершина не является точкой сочленения
                    									d.visualizer.updateVertex(-1);
                    break;
                }
                case 38: { // Конец данного автомата
                    				d.visualizer.updateVertex(-1);
                    break;
                }
            }
        }
    }

    /**
      * Ищет мосты.
      */
    private final class findBridges extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 22;

        /**
          * Конструктор.
          */
        public findBridges() {
            super( 
                "findBridges", 
                0, // Номер начального состояния 
                22, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация i", 
                    "Обход вершин", 
                    "Инициализация j", 
                    "Обход вершин", 
                    "Существует ли ребро", 
                    "Существует ли ребро (окончание)", 
                    "Рассмотрим ребро", 
                    "Сравниваем low у вершин ребра", 
                    "Сравниваем low у вершин ребра (окончание)", 
                    "Ребро не является мостом", 
                    "Low вершин не равны", 
                    "Сравниваем low и dfnum у вершин ребра", 
                    "Сравниваем low и dfnum у вершин ребра (окончание)", 
                    "Сравниваем low и dfnum у вершин ребра", 
                    "Сравниваем low и dfnum у вершин ребра (окончание)", 
                    "Это ребро является мостом.", 
                    "Это ребро является мостом.", 
                    "Ребро не является мостом.", 
                    "Инкремент j", 
                    "Инкремент i", 
                    "Конец данного автомата", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    1, // Инициализация i 
                    -1, // Обход вершин 
                    -1, // Инициализация j 
                    -1, // Обход вершин 
                    -1, // Существует ли ребро 
                    -1, // Существует ли ребро (окончание) 
                    0, // Рассмотрим ребро 
                    -1, // Сравниваем low у вершин ребра 
                    -1, // Сравниваем low у вершин ребра (окончание) 
                    0, // Ребро не является мостом 
                    0, // Low вершин не равны 
                    -1, // Сравниваем low и dfnum у вершин ребра 
                    -1, // Сравниваем low и dfnum у вершин ребра (окончание) 
                    -1, // Сравниваем low и dfnum у вершин ребра 
                    -1, // Сравниваем low и dfnum у вершин ребра (окончание) 
                    0, // Это ребро является мостом. 
                    0, // Это ребро является мостом. 
                    0, // Ребро не является мостом. 
                    -1, // Инкремент j 
                    -1, // Инкремент i 
                    0, // Конец данного автомата 
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
                    state = 1; // Инициализация i
                    break;
                }
                case 1: { // Инициализация i
                    stack.pushBoolean(false); 
                    state = 2; // Обход вершин
                    break;
                }
                case 2: { // Обход вершин
                    if (d.findBridges_i < d.numberOfVertices) {
                        state = 3; // Инициализация j
                    } else {
                        state = 21; // Конец данного автомата
                    }
                    break;
                }
                case 3: { // Инициализация j
                    stack.pushBoolean(false); 
                    state = 4; // Обход вершин
                    break;
                }
                case 4: { // Обход вершин
                    if (d.findBridges_j < d.numberOfVertices) {
                        state = 5; // Существует ли ребро
                    } else {
                        state = 20; // Инкремент i
                    }
                    break;
                }
                case 5: { // Существует ли ребро
                    if (d.aMatrix[d.findBridges_i][d.findBridges_j] != 0) {
                        state = 7; // Рассмотрим ребро
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // Существует ли ребро (окончание)
                    }
                    break;
                }
                case 6: { // Существует ли ребро (окончание)
                    state = 19; // Инкремент j
                    break;
                }
                case 7: { // Рассмотрим ребро
                    state = 8; // Сравниваем low у вершин ребра
                    break;
                }
                case 8: { // Сравниваем low у вершин ребра
                    if (d.low[d.findBridges_i] == d.low[d.findBridges_j]) {
                        state = 10; // Ребро не является мостом
                    } else {
                        state = 11; // Low вершин не равны
                    }
                    break;
                }
                case 9: { // Сравниваем low у вершин ребра (окончание)
                    stack.pushBoolean(true); 
                    state = 6; // Существует ли ребро (окончание)
                    break;
                }
                case 10: { // Ребро не является мостом
                    stack.pushBoolean(true); 
                    state = 9; // Сравниваем low у вершин ребра (окончание)
                    break;
                }
                case 11: { // Low вершин не равны
                    state = 12; // Сравниваем low и dfnum у вершин ребра
                    break;
                }
                case 12: { // Сравниваем low и dfnum у вершин ребра
                    if (d.low[d.findBridges_i] == d.dfnumber[d.findBridges_i] || d.low[d.findBridges_j] == d.dfnumber[d.findBridges_j]) {
                        state = 14; // Сравниваем low и dfnum у вершин ребра
                    } else {
                        state = 18; // Ребро не является мостом.
                    }
                    break;
                }
                case 13: { // Сравниваем low и dfnum у вершин ребра (окончание)
                    stack.pushBoolean(false); 
                    state = 9; // Сравниваем low у вершин ребра (окончание)
                    break;
                }
                case 14: { // Сравниваем low и dfnum у вершин ребра
                    if (d.low[d.findBridges_i] == d.dfnumber[d.findBridges_i]) {
                        state = 16; // Это ребро является мостом.
                    } else {
                        state = 17; // Это ребро является мостом.
                    }
                    break;
                }
                case 15: { // Сравниваем low и dfnum у вершин ребра (окончание)
                    stack.pushBoolean(true); 
                    state = 13; // Сравниваем low и dfnum у вершин ребра (окончание)
                    break;
                }
                case 16: { // Это ребро является мостом.
                    stack.pushBoolean(true); 
                    state = 15; // Сравниваем low и dfnum у вершин ребра (окончание)
                    break;
                }
                case 17: { // Это ребро является мостом.
                    stack.pushBoolean(false); 
                    state = 15; // Сравниваем low и dfnum у вершин ребра (окончание)
                    break;
                }
                case 18: { // Ребро не является мостом.
                    stack.pushBoolean(false); 
                    state = 13; // Сравниваем low и dfnum у вершин ребра (окончание)
                    break;
                }
                case 19: { // Инкремент j
                    stack.pushBoolean(true); 
                    state = 4; // Обход вершин
                    break;
                }
                case 20: { // Инкремент i
                    stack.pushBoolean(true); 
                    state = 2; // Обход вершин
                    break;
                }
                case 21: { // Конец данного автомата
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация i
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
                case 2: { // Обход вершин
                    break;
                }
                case 3: { // Инициализация j
                    startSection();
                    storeField(d, "findBridges_j");
                    					d.findBridges_j = d.findBridges_i + 1;
                    break;
                }
                case 4: { // Обход вершин
                    break;
                }
                case 5: { // Существует ли ребро
                    break;
                }
                case 6: { // Существует ли ребро (окончание)
                    break;
                }
                case 7: { // Рассмотрим ребро
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
                case 8: { // Сравниваем low у вершин ребра
                    break;
                }
                case 9: { // Сравниваем low у вершин ребра (окончание)
                    break;
                }
                case 10: { // Ребро не является мостом
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
                case 11: { // Low вершин не равны
                    startSection();
                    storeField(d, "findBridges_activeVertexInLow1");
                    										d.findBridges_activeVertexInLow1 = d.findBridges_i;
                    storeField(d, "findBridges_activeVertexInLow2");
                    										d.findBridges_activeVertexInLow2 = d.findBridges_j;
                    break;
                }
                case 12: { // Сравниваем low и dfnum у вершин ребра
                    break;
                }
                case 13: { // Сравниваем low и dfnum у вершин ребра (окончание)
                    break;
                }
                case 14: { // Сравниваем low и dfnum у вершин ребра
                    break;
                }
                case 15: { // Сравниваем low и dfnum у вершин ребра (окончание)
                    break;
                }
                case 16: { // Это ребро является мостом.
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
                case 17: { // Это ребро является мостом.
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
                case 18: { // Ребро не является мостом.
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
                case 19: { // Инкремент j
                    startSection();
                    storeField(d, "findBridges_j");
                    						d.findBridges_j = d.findBridges_j + 1;
                    break;
                }
                case 20: { // Инкремент i
                    startSection();
                    storeField(d, "findBridges_i");
                    					d.findBridges_i = d.findBridges_i + 1;
                    break;
                }
                case 21: { // Конец данного автомата
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Инициализация i
                    restoreSection();
                    break;
                }
                case 2: { // Обход вершин
                    break;
                }
                case 3: { // Инициализация j
                    restoreSection();
                    break;
                }
                case 4: { // Обход вершин
                    break;
                }
                case 5: { // Существует ли ребро
                    break;
                }
                case 6: { // Существует ли ребро (окончание)
                    break;
                }
                case 7: { // Рассмотрим ребро
                    restoreSection();
                    break;
                }
                case 8: { // Сравниваем low у вершин ребра
                    break;
                }
                case 9: { // Сравниваем low у вершин ребра (окончание)
                    break;
                }
                case 10: { // Ребро не является мостом
                    restoreSection();
                    break;
                }
                case 11: { // Low вершин не равны
                    restoreSection();
                    break;
                }
                case 12: { // Сравниваем low и dfnum у вершин ребра
                    break;
                }
                case 13: { // Сравниваем low и dfnum у вершин ребра (окончание)
                    break;
                }
                case 14: { // Сравниваем low и dfnum у вершин ребра
                    break;
                }
                case 15: { // Сравниваем low и dfnum у вершин ребра (окончание)
                    break;
                }
                case 16: { // Это ребро является мостом.
                    restoreSection();
                    break;
                }
                case 17: { // Это ребро является мостом.
                    restoreSection();
                    break;
                }
                case 18: { // Ребро не является мостом.
                    restoreSection();
                    break;
                }
                case 19: { // Инкремент j
                    restoreSection();
                    break;
                }
                case 20: { // Инкремент i
                    restoreSection();
                    break;
                }
                case 21: { // Конец данного автомата
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация i
                    state = START_STATE; 
                    break;
                }
                case 2: { // Обход вершин
                    if (stack.popBoolean()) {
                        state = 20; // Инкремент i
                    } else {
                        state = 1; // Инициализация i
                    }
                    break;
                }
                case 3: { // Инициализация j
                    state = 2; // Обход вершин
                    break;
                }
                case 4: { // Обход вершин
                    if (stack.popBoolean()) {
                        state = 19; // Инкремент j
                    } else {
                        state = 3; // Инициализация j
                    }
                    break;
                }
                case 5: { // Существует ли ребро
                    state = 4; // Обход вершин
                    break;
                }
                case 6: { // Существует ли ребро (окончание)
                    if (stack.popBoolean()) {
                        state = 9; // Сравниваем low у вершин ребра (окончание)
                    } else {
                        state = 5; // Существует ли ребро
                    }
                    break;
                }
                case 7: { // Рассмотрим ребро
                    state = 5; // Существует ли ребро
                    break;
                }
                case 8: { // Сравниваем low у вершин ребра
                    state = 7; // Рассмотрим ребро
                    break;
                }
                case 9: { // Сравниваем low у вершин ребра (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // Ребро не является мостом
                    } else {
                        state = 13; // Сравниваем low и dfnum у вершин ребра (окончание)
                    }
                    break;
                }
                case 10: { // Ребро не является мостом
                    state = 8; // Сравниваем low у вершин ребра
                    break;
                }
                case 11: { // Low вершин не равны
                    state = 8; // Сравниваем low у вершин ребра
                    break;
                }
                case 12: { // Сравниваем low и dfnum у вершин ребра
                    state = 11; // Low вершин не равны
                    break;
                }
                case 13: { // Сравниваем low и dfnum у вершин ребра (окончание)
                    if (stack.popBoolean()) {
                        state = 15; // Сравниваем low и dfnum у вершин ребра (окончание)
                    } else {
                        state = 18; // Ребро не является мостом.
                    }
                    break;
                }
                case 14: { // Сравниваем low и dfnum у вершин ребра
                    state = 12; // Сравниваем low и dfnum у вершин ребра
                    break;
                }
                case 15: { // Сравниваем low и dfnum у вершин ребра (окончание)
                    if (stack.popBoolean()) {
                        state = 16; // Это ребро является мостом.
                    } else {
                        state = 17; // Это ребро является мостом.
                    }
                    break;
                }
                case 16: { // Это ребро является мостом.
                    state = 14; // Сравниваем low и dfnum у вершин ребра
                    break;
                }
                case 17: { // Это ребро является мостом.
                    state = 14; // Сравниваем low и dfnum у вершин ребра
                    break;
                }
                case 18: { // Ребро не является мостом.
                    state = 12; // Сравниваем low и dfnum у вершин ребра
                    break;
                }
                case 19: { // Инкремент j
                    state = 6; // Существует ли ребро (окончание)
                    break;
                }
                case 20: { // Инкремент i
                    state = 4; // Обход вершин
                    break;
                }
                case 21: { // Конец данного автомата
                    state = 2; // Обход вершин
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 21; // Конец данного автомата
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
                case 1: { // Инициализация i
                    comment = ArticulationPoints.this.getComment("findBridges.FBInitI"); 
                    break;
                }
                case 7: { // Рассмотрим ребро
                    comment = ArticulationPoints.this.getComment("findBridges.LookAtEdge"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.findBridges_j)}; 
                    break;
                }
                case 10: { // Ребро не является мостом
                    comment = ArticulationPoints.this.getComment("findBridges.NotFoundBridge1"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.findBridges_j), new Integer(d.low[d.findBridges_i])}; 
                    break;
                }
                case 11: { // Low вершин не равны
                    comment = ArticulationPoints.this.getComment("findBridges.LowNotEqual"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.findBridges_j), new Integer(d.low[d.findBridges_i]), new Integer(d.low[d.findBridges_j])}; 
                    break;
                }
                case 16: { // Это ребро является мостом.
                    comment = ArticulationPoints.this.getComment("findBridges.FoundBridge1"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.low[d.findBridges_i]), new Integer(d.findBridges_j)}; 
                    break;
                }
                case 17: { // Это ребро является мостом.
                    comment = ArticulationPoints.this.getComment("findBridges.FoundBridge2"); 
                    args = new Object[]{new Integer(d.findBridges_j), new Integer(d.low[d.findBridges_j]), new Integer(d.findBridges_i)}; 
                    break;
                }
                case 18: { // Ребро не является мостом.
                    comment = ArticulationPoints.this.getComment("findBridges.NotFoundBridge2"); 
                    args = new Object[]{new Integer(d.findBridges_i), new Integer(d.findBridges_j)}; 
                    break;
                }
                case 21: { // Конец данного автомата
                    comment = ArticulationPoints.this.getComment("findBridges.AllBridgesFound"); 
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
                case 1: { // Инициализация i
                    				d.visualizer.updateVertex(-1);
                    break;
                }
                case 7: { // Рассмотрим ребро
                    								d.visualizer.updateVertex(-1);
                    break;
                }
                case 10: { // Ребро не является мостом
                    										d.visualizer.updateVertex(-1);
                    break;
                }
                case 11: { // Low вершин не равны
                    										d.visualizer.updateVertex(-1);
                    break;
                }
                case 16: { // Это ребро является мостом.
                    														d.visualizer.updateVertex(-1);
                    break;
                }
                case 17: { // Это ребро является мостом.
                    														d.visualizer.updateVertex(-1);
                    break;
                }
                case 18: { // Ребро не является мостом.
                    												d.visualizer.updateVertex(-1);
                    break;
                }
                case 21: { // Конец данного автомата
                    				d.visualizer.updateVertex(-1);
                    break;
                }
            }
        }
    }

    /**
      * Ищет точки сочленения, мосты, компоненты двусвязности.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 11;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                11, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация i", 
                    "Обход непосещенных вершин.", 
                    "Посещалась ли вершина", 
                    "Посещалась ли вершина (окончание)", 
                    "Инициализация переменных", 
                    "Обходит граф в глубину (автомат)", 
                    "Находим low для каждой вершины. (автомат)", 
                    "Ищет точки сочленения. (автомат)", 
                    "Инкремент i", 
                    "Ищет мосты (автомат)", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация i 
                    -1, // Обход непосещенных вершин. 
                    -1, // Посещалась ли вершина 
                    -1, // Посещалась ли вершина (окончание) 
                    -1, // Инициализация переменных 
                    CALL_AUTO_LEVEL, // Обходит граф в глубину (автомат) 
                    CALL_AUTO_LEVEL, // Находим low для каждой вершины. (автомат) 
                    CALL_AUTO_LEVEL, // Ищет точки сочленения. (автомат) 
                    -1, // Инкремент i 
                    CALL_AUTO_LEVEL, // Ищет мосты (автомат) 
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
                    state = 1; // Инициализация i
                    break;
                }
                case 1: { // Инициализация i
                    stack.pushBoolean(false); 
                    state = 2; // Обход непосещенных вершин.
                    break;
                }
                case 2: { // Обход непосещенных вершин.
                    if (d.Main_i < d.numberOfVertices) {
                        state = 3; // Посещалась ли вершина
                    } else {
                        state = 10; // Ищет мосты (автомат)
                    }
                    break;
                }
                case 3: { // Посещалась ли вершина
                    if (d.articulationPoints[d.Main_i] == 0) {
                        state = 5; // Инициализация переменных
                    } else {
                        stack.pushBoolean(false); 
                        state = 4; // Посещалась ли вершина (окончание)
                    }
                    break;
                }
                case 4: { // Посещалась ли вершина (окончание)
                    state = 9; // Инкремент i
                    break;
                }
                case 5: { // Инициализация переменных
                    state = 6; // Обходит граф в глубину (автомат)
                    break;
                }
                case 6: { // Обходит граф в глубину (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 7; // Находим low для каждой вершины. (автомат)
                    }
                    break;
                }
                case 7: { // Находим low для каждой вершины. (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // Ищет точки сочленения. (автомат)
                    }
                    break;
                }
                case 8: { // Ищет точки сочленения. (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 4; // Посещалась ли вершина (окончание)
                    }
                    break;
                }
                case 9: { // Инкремент i
                    stack.pushBoolean(true); 
                    state = 2; // Обход непосещенных вершин.
                    break;
                }
                case 10: { // Ищет мосты (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = END_STATE; 
                    }
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация i
                    startSection();
                    storeField(d, "Main_i");
                    				d.Main_i = 0;
                    break;
                }
                case 2: { // Обход непосещенных вершин.
                    break;
                }
                case 3: { // Посещалась ли вершина
                    break;
                }
                case 4: { // Посещалась ли вершина (окончание)
                    break;
                }
                case 5: { // Инициализация переменных
                    startSection();
                    storeField(d, "root");
                    							d.root = d.Main_i;
                    break;
                }
                case 6: { // Обходит граф в глубину (автомат)
                    if (child == null) {
                        child = new DFS(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 7: { // Находим low для каждой вершины. (автомат)
                    if (child == null) {
                        child = new findLow(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // Ищет точки сочленения. (автомат)
                    if (child == null) {
                        child = new findArticulationPoints(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 9: { // Инкремент i
                    startSection();
                    storeField(d, "Main_i");
                    					d.Main_i = d.Main_i + 1;
                    break;
                }
                case 10: { // Ищет мосты (автомат)
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Инициализация i
                    restoreSection();
                    break;
                }
                case 2: { // Обход непосещенных вершин.
                    break;
                }
                case 3: { // Посещалась ли вершина
                    break;
                }
                case 4: { // Посещалась ли вершина (окончание)
                    break;
                }
                case 5: { // Инициализация переменных
                    restoreSection();
                    break;
                }
                case 6: { // Обходит граф в глубину (автомат)
                    if (child == null) {
                        child = new DFS(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 7: { // Находим low для каждой вершины. (автомат)
                    if (child == null) {
                        child = new findLow(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // Ищет точки сочленения. (автомат)
                    if (child == null) {
                        child = new findArticulationPoints(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 9: { // Инкремент i
                    restoreSection();
                    break;
                }
                case 10: { // Ищет мосты (автомат)
                    if (child == null) {
                        child = new findBridges(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация i
                    state = START_STATE; 
                    break;
                }
                case 2: { // Обход непосещенных вершин.
                    if (stack.popBoolean()) {
                        state = 9; // Инкремент i
                    } else {
                        state = 1; // Инициализация i
                    }
                    break;
                }
                case 3: { // Посещалась ли вершина
                    state = 2; // Обход непосещенных вершин.
                    break;
                }
                case 4: { // Посещалась ли вершина (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // Ищет точки сочленения. (автомат)
                    } else {
                        state = 3; // Посещалась ли вершина
                    }
                    break;
                }
                case 5: { // Инициализация переменных
                    state = 3; // Посещалась ли вершина
                    break;
                }
                case 6: { // Обходит граф в глубину (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 5; // Инициализация переменных
                    }
                    break;
                }
                case 7: { // Находим low для каждой вершины. (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // Обходит граф в глубину (автомат)
                    }
                    break;
                }
                case 8: { // Ищет точки сочленения. (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 7; // Находим low для каждой вершины. (автомат)
                    }
                    break;
                }
                case 9: { // Инкремент i
                    state = 4; // Посещалась ли вершина (окончание)
                    break;
                }
                case 10: { // Ищет мосты (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 2; // Обход непосещенных вершин.
                    }
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 10; // Ищет мосты (автомат)
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
                case START_STATE: { // Начальное состояние
                    comment = ArticulationPoints.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 6: { // Обходит граф в глубину (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // Находим low для каждой вершины. (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 8: { // Ищет точки сочленения. (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // Ищет мосты (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = ArticulationPoints.this.getComment("Main.END_STATE"); 
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
                case START_STATE: { // Начальное состояние
                    				d.visualizer.updateVertex(-1);
                    break;
                }
                case 6: { // Обходит граф в глубину (автомат)
                    child.drawState(); 
                    break;
                }
                case 7: { // Находим low для каждой вершины. (автомат)
                    child.drawState(); 
                    break;
                }
                case 8: { // Ищет точки сочленения. (автомат)
                    child.drawState(); 
                    break;
                }
                case 10: { // Ищет мосты (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    				d.visualizer.updateVertex(-1);
                    break;
                }
            }
        }
    }
}
