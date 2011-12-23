package ru.ifmo.vizi.min_cost_max_flow;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class MinCostMaxFlow extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public MinCostMaxFlow(Locale locale) {
        super("ru.ifmo.vizi.min_cost_max_flow.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Количество вершин.
          */
        public int n = 6;

        /**
          * Количество ребер.
          */
        public int m = 10;

        /**
          * Источник.
          */
        public int source = 0;

        /**
          * Сток.
          */
        public int sink = 1;

        /**
          * Матрица смежности графа.
          */
        public Edge[][] edge = new Edge[n][n];

        /**
          * Матрица смежности для обратных ребер графа.
          */
        public Edge[][] reverseEdge = new Edge[n][n];

        /**
          * Массив потенциалов.
          */
        public long[] phi = new long[n];

        /**
          * Расстояния от источника.
          */
        public long[] dist = new long[n];

        /**
          * Использовали ли мы данную вершину.
          */
        public boolean[] used = new boolean[n];

        /**
          * Массив для востановления кратчайшего пути.
          */
        public Edge[] prev = new Edge[n];

        /**
          * Текущяя стоимость потока.
          */
        public long cost = 0;

        /**
          * Максимальный дополняющий поток.
          */
        public long maxFlow = 0;

        /**
          * Надо ли отображать обратные ребра и дополнительную информацию в вершинах.
          */
        public boolean extendedDraw = false;

        /**
          * Что выделять (0 - ничего, 1 - расстояния, 2 - потенциалы).
          */
        public int highlight = 0;

        /**
          * Экзэмпляр апплета.
          */
        public MinCostMaxFlowVisualizer visualizer = null;

        /**
          * Переменная для использования в цикле (Процедура dijkstra).
          */
        public int dijkstra_i;

        /**
          * Переменная для использования в цикле (Процедура dijkstra).
          */
        public int dijkstra_j;

        /**
          * Текущая вершина в дейкстре (Процедура dijkstra).
          */
        public int dijkstra_u;

        /**
          * Текущая вершина в предыдущем пути (Процедура dijkstra).
          */
        public int dijkstra_cur;

        /**
          * переменная цикла (Процедура updatePotentials).
          */
        public int updatePotentials_i;

        /**
          * Текущая вершина (Процедура recoverPath).
          */
        public int recoverPath_cur;

        /**
          * текущая вершина (Процедура letPassFlow).
          */
        public int letPassFlow_cur;

        /**
          * стоимость дополняющего потока (Процедура letPassFlow).
          */
        public long letPassFlow_curCost;

        public String toString() {
            return "";
        }
    }

    /**
      * Вычисляет расстояния от источника до вершин.
      */
    private final class dijkstra extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 34;

        /**
          * Конструктор.
          */
        public dijkstra() {
            super( 
                "dijkstra", 
                0, // Номер начального состояния 
                34, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "инициализируем cur", 
                    "убираем выделение с пути", 
                    "убираем выделение с текущего ребра", 
                    "обнуляем i", 
                    "Заполняет расстояния бесконечностями", 
                    "шаг цикла", 
                    "обнуляем i2", 
                    "Еще ничего не использовали", 
                    "шаг цикла", 
                    "Делаем источник источником для Дейкстры и инициализируем i", 
                    "Внешний цикл дейкстры", 
                    "инициализируем u и j", 
                    "выбираем виршину", 
                    "выбираем наиболее близкую неиспользованную вершину", 
                    "выбираем наиболее близкую неиспользованную вершину (окончание)", 
                    "обновляем u", 
                    "инкремент", 
                    "Есть ли вершина, которую можно использовать", 
                    "Есть ли вершина, которую можно использовать (окончание)", 
                    "инициализируем j", 
                    "цикл по ребрам, инцидентным u", 
                    "надо ли обновлять вершину на конце ребра", 
                    "надо ли обновлять вершину на конце ребра (окончание)", 
                    "обновляем расстояние до вершины и добавляем ребро в массив для восстановления", 
                    "следующее ребро", 
                    "инициализируем j", 
                    "цикл по обратным ребрам, инцидентным u", 
                    "надо ли обновлять вершину на конце ребра", 
                    "надо ли обновлять вершину на конце ребра (окончание)", 
                    "обновляем расстояние до вершины и добавляем ребро в массив для восстановления", 
                    "следующее ребро", 
                    "мы использовали вершину u", 
                    "инкремент", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // инициализируем cur 
                    -1, // убираем выделение с пути 
                    -1, // убираем выделение с текущего ребра 
                    -1, // обнуляем i 
                    -1, // Заполняет расстояния бесконечностями 
                    -1, // шаг цикла 
                    -1, // обнуляем i2 
                    -1, // Еще ничего не использовали 
                    -1, // шаг цикла 
                    -1, // Делаем источник источником для Дейкстры и инициализируем i 
                    -1, // Внешний цикл дейкстры 
                    -1, // инициализируем u и j 
                    -1, // выбираем виршину 
                    -1, // выбираем наиболее близкую неиспользованную вершину 
                    -1, // выбираем наиболее близкую неиспользованную вершину (окончание) 
                    -1, // обновляем u 
                    -1, // инкремент 
                    -1, // Есть ли вершина, которую можно использовать 
                    -1, // Есть ли вершина, которую можно использовать (окончание) 
                    -1, // инициализируем j 
                    -1, // цикл по ребрам, инцидентным u 
                    -1, // надо ли обновлять вершину на конце ребра 
                    -1, // надо ли обновлять вершину на конце ребра (окончание) 
                    -1, // обновляем расстояние до вершины и добавляем ребро в массив для восстановления 
                    -1, // следующее ребро 
                    -1, // инициализируем j 
                    -1, // цикл по обратным ребрам, инцидентным u 
                    -1, // надо ли обновлять вершину на конце ребра 
                    -1, // надо ли обновлять вершину на конце ребра (окончание) 
                    -1, // обновляем расстояние до вершины и добавляем ребро в массив для восстановления 
                    -1, // следующее ребро 
                    -1, // мы использовали вершину u 
                    -1, // инкремент 
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
                    state = 1; // инициализируем cur
                    break;
                }
                case 1: { // инициализируем cur
                    stack.pushBoolean(false); 
                    state = 2; // убираем выделение с пути
                    break;
                }
                case 2: { // убираем выделение с пути
                    if ((d.prev[d.dijkstra_cur] != null) && (d.dijkstra_cur != d.source)) {
                        state = 3; // убираем выделение с текущего ребра
                    } else {
                        state = 4; // обнуляем i
                    }
                    break;
                }
                case 3: { // убираем выделение с текущего ребра
                    stack.pushBoolean(true); 
                    state = 2; // убираем выделение с пути
                    break;
                }
                case 4: { // обнуляем i
                    stack.pushBoolean(false); 
                    state = 5; // Заполняет расстояния бесконечностями
                    break;
                }
                case 5: { // Заполняет расстояния бесконечностями
                    if (d.dijkstra_i < d.n) {
                        state = 6; // шаг цикла
                    } else {
                        state = 7; // обнуляем i2
                    }
                    break;
                }
                case 6: { // шаг цикла
                    stack.pushBoolean(true); 
                    state = 5; // Заполняет расстояния бесконечностями
                    break;
                }
                case 7: { // обнуляем i2
                    stack.pushBoolean(false); 
                    state = 8; // Еще ничего не использовали
                    break;
                }
                case 8: { // Еще ничего не использовали
                    if (d.dijkstra_i < d.n) {
                        state = 9; // шаг цикла
                    } else {
                        state = 10; // Делаем источник источником для Дейкстры и инициализируем i
                    }
                    break;
                }
                case 9: { // шаг цикла
                    stack.pushBoolean(true); 
                    state = 8; // Еще ничего не использовали
                    break;
                }
                case 10: { // Делаем источник источником для Дейкстры и инициализируем i
                    stack.pushBoolean(false); 
                    state = 11; // Внешний цикл дейкстры
                    break;
                }
                case 11: { // Внешний цикл дейкстры
                    if (d.dijkstra_i < d.n) {
                        state = 12; // инициализируем u и j
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 12: { // инициализируем u и j
                    stack.pushBoolean(false); 
                    state = 13; // выбираем виршину
                    break;
                }
                case 13: { // выбираем виршину
                    if (d.dijkstra_j < d.n) {
                        state = 14; // выбираем наиболее близкую неиспользованную вершину
                    } else {
                        state = 18; // Есть ли вершина, которую можно использовать
                    }
                    break;
                }
                case 14: { // выбираем наиболее близкую неиспользованную вершину
                    if ((!d.used[d.dijkstra_j]) && (d.dist[d.dijkstra_j] >= 0) && ( ( d.dijkstra_u == -1) || ( d.dist[d.dijkstra_j] < d.dist[d.dijkstra_u] ) )) {
                        state = 16; // обновляем u
                    } else {
                        stack.pushBoolean(false); 
                        state = 15; // выбираем наиболее близкую неиспользованную вершину (окончание)
                    }
                    break;
                }
                case 15: { // выбираем наиболее близкую неиспользованную вершину (окончание)
                    state = 17; // инкремент
                    break;
                }
                case 16: { // обновляем u
                    stack.pushBoolean(true); 
                    state = 15; // выбираем наиболее близкую неиспользованную вершину (окончание)
                    break;
                }
                case 17: { // инкремент
                    stack.pushBoolean(true); 
                    state = 13; // выбираем виршину
                    break;
                }
                case 18: { // Есть ли вершина, которую можно использовать
                    if (d.dijkstra_u != -1) {
                        state = 20; // инициализируем j
                    } else {
                        stack.pushBoolean(false); 
                        state = 19; // Есть ли вершина, которую можно использовать (окончание)
                    }
                    break;
                }
                case 19: { // Есть ли вершина, которую можно использовать (окончание)
                    state = 33; // инкремент
                    break;
                }
                case 20: { // инициализируем j
                    stack.pushBoolean(false); 
                    state = 21; // цикл по ребрам, инцидентным u
                    break;
                }
                case 21: { // цикл по ребрам, инцидентным u
                    if (d.dijkstra_j < d.n) {
                        state = 22; // надо ли обновлять вершину на конце ребра
                    } else {
                        state = 26; // инициализируем j
                    }
                    break;
                }
                case 22: { // надо ли обновлять вершину на конце ребра
                    if ((d.edge[d.dijkstra_u][d.dijkstra_j] != null ) && ( d.edge[d.dijkstra_u][d.dijkstra_j].capacity - d.edge[d.dijkstra_u][d.dijkstra_j].flow > 0 ) && ( (d.dist[d.dijkstra_j] == -1 ) || (d.dist[d.dijkstra_u] + d.edge[d.dijkstra_u][d.dijkstra_j].cost - d.phi[d.dijkstra_j] + d.phi[d.dijkstra_u] < d.dist[d.dijkstra_j] ) )) {
                        state = 24; // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    } else {
                        stack.pushBoolean(false); 
                        state = 23; // надо ли обновлять вершину на конце ребра (окончание)
                    }
                    break;
                }
                case 23: { // надо ли обновлять вершину на конце ребра (окончание)
                    state = 25; // следующее ребро
                    break;
                }
                case 24: { // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    stack.pushBoolean(true); 
                    state = 23; // надо ли обновлять вершину на конце ребра (окончание)
                    break;
                }
                case 25: { // следующее ребро
                    stack.pushBoolean(true); 
                    state = 21; // цикл по ребрам, инцидентным u
                    break;
                }
                case 26: { // инициализируем j
                    stack.pushBoolean(false); 
                    state = 27; // цикл по обратным ребрам, инцидентным u
                    break;
                }
                case 27: { // цикл по обратным ребрам, инцидентным u
                    if (d.dijkstra_j < d.n) {
                        state = 28; // надо ли обновлять вершину на конце ребра
                    } else {
                        state = 32; // мы использовали вершину u
                    }
                    break;
                }
                case 28: { // надо ли обновлять вершину на конце ребра
                    if ((d.reverseEdge[d.dijkstra_u][d.dijkstra_j] != null ) && ( d.reverseEdge[d.dijkstra_u][d.dijkstra_j].capacity - d.reverseEdge[d.dijkstra_u][d.dijkstra_j].flow > 0 ) && ( (d.dist[d.dijkstra_j] == -1 ) || (d.dist[d.dijkstra_u] + d.reverseEdge[d.dijkstra_u][d.dijkstra_j].cost - d.phi[d.dijkstra_j] + d.phi[d.dijkstra_u] < d.dist[d.dijkstra_j] ) )) {
                        state = 30; // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    } else {
                        stack.pushBoolean(false); 
                        state = 29; // надо ли обновлять вершину на конце ребра (окончание)
                    }
                    break;
                }
                case 29: { // надо ли обновлять вершину на конце ребра (окончание)
                    state = 31; // следующее ребро
                    break;
                }
                case 30: { // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    stack.pushBoolean(true); 
                    state = 29; // надо ли обновлять вершину на конце ребра (окончание)
                    break;
                }
                case 31: { // следующее ребро
                    stack.pushBoolean(true); 
                    state = 27; // цикл по обратным ребрам, инцидентным u
                    break;
                }
                case 32: { // мы использовали вершину u
                    stack.pushBoolean(true); 
                    state = 19; // Есть ли вершина, которую можно использовать (окончание)
                    break;
                }
                case 33: { // инкремент
                    stack.pushBoolean(true); 
                    state = 11; // Внешний цикл дейкстры
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // инициализируем cur
                    startSection();
                    storeField(d, "dijkstra_cur");
                    d.dijkstra_cur = d.sink;
                    break;
                }
                case 2: { // убираем выделение с пути
                    break;
                }
                case 3: { // убираем выделение с текущего ребра
                    startSection();
                    storeField(d.prev[d.dijkstra_cur], "highlighted");
                    d.prev[d.dijkstra_cur].highlighted = false;
                    storeField(d, "dijkstra_cur");
                    d.dijkstra_cur = d.prev[d.dijkstra_cur].u;
                    break;
                }
                case 4: { // обнуляем i
                    startSection();
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = 0;
                    break;
                }
                case 5: { // Заполняет расстояния бесконечностями
                    break;
                }
                case 6: { // шаг цикла
                    startSection();
                    storeArray(d.dist, d.dijkstra_i);
                    d.dist[d.dijkstra_i] = -1;
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = d.dijkstra_i + 1;
                    break;
                }
                case 7: { // обнуляем i2
                    startSection();
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = 0;
                    break;
                }
                case 8: { // Еще ничего не использовали
                    break;
                }
                case 9: { // шаг цикла
                    startSection();
                    storeArray(d.used, d.dijkstra_i);
                    d.used[d.dijkstra_i] = false;
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = d.dijkstra_i + 1;
                    break;
                }
                case 10: { // Делаем источник источником для Дейкстры и инициализируем i
                    startSection();
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = 0;
                    d.dist[d.source] = 0;
                    d.prev[d.source] = null;
                    break;
                }
                case 11: { // Внешний цикл дейкстры
                    break;
                }
                case 12: { // инициализируем u и j
                    startSection();
                    storeField(d, "dijkstra_u");
                    d.dijkstra_u = -1;
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = 0;
                    break;
                }
                case 13: { // выбираем виршину
                    break;
                }
                case 14: { // выбираем наиболее близкую неиспользованную вершину
                    break;
                }
                case 15: { // выбираем наиболее близкую неиспользованную вершину (окончание)
                    break;
                }
                case 16: { // обновляем u
                    startSection();
                    storeField(d, "dijkstra_u");
                    d.dijkstra_u = d.dijkstra_j;
                    break;
                }
                case 17: { // инкремент
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = d.dijkstra_j + 1;
                    break;
                }
                case 18: { // Есть ли вершина, которую можно использовать
                    break;
                }
                case 19: { // Есть ли вершина, которую можно использовать (окончание)
                    break;
                }
                case 20: { // инициализируем j
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = 0;
                    break;
                }
                case 21: { // цикл по ребрам, инцидентным u
                    break;
                }
                case 22: { // надо ли обновлять вершину на конце ребра
                    break;
                }
                case 23: { // надо ли обновлять вершину на конце ребра (окончание)
                    break;
                }
                case 24: { // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    startSection();
                    storeArray(d.dist, d.dijkstra_j);
                    d.dist[d.dijkstra_j] = d.dist[d.dijkstra_u] + d.edge[d.dijkstra_u][d.dijkstra_j].cost - d.phi[d.dijkstra_j] + d.phi[d.dijkstra_u];
                    storeArray(d.prev, d.dijkstra_j);
                    d.prev[d.dijkstra_j] = d.edge[d.dijkstra_u][d.dijkstra_j];
                    break;
                }
                case 25: { // следующее ребро
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = d.dijkstra_j + 1;
                    break;
                }
                case 26: { // инициализируем j
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = 0;
                    break;
                }
                case 27: { // цикл по обратным ребрам, инцидентным u
                    break;
                }
                case 28: { // надо ли обновлять вершину на конце ребра
                    break;
                }
                case 29: { // надо ли обновлять вершину на конце ребра (окончание)
                    break;
                }
                case 30: { // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    startSection();
                    storeArray(d.dist, d.dijkstra_j);
                    d.dist[d.dijkstra_j] = d.dist[d.dijkstra_u] + d.reverseEdge[d.dijkstra_u][d.dijkstra_j].cost - d.phi[d.dijkstra_j] + d.phi[d.dijkstra_u];
                    storeArray(d.prev, d.dijkstra_j);
                    d.prev[d.dijkstra_j] = d.reverseEdge[d.dijkstra_u][d.dijkstra_j];
                    break;
                }
                case 31: { // следующее ребро
                    startSection();
                    storeField(d, "dijkstra_j");
                    d.dijkstra_j = d.dijkstra_j + 1;
                    break;
                }
                case 32: { // мы использовали вершину u
                    startSection();
                    storeArray(d.used, d.dijkstra_u);
                    d.used[d.dijkstra_u] = true;
                    break;
                }
                case 33: { // инкремент
                    startSection();
                    storeField(d, "dijkstra_i");
                    d.dijkstra_i = d.dijkstra_i + 1;
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
                case 1: { // инициализируем cur
                    restoreSection();
                    break;
                }
                case 2: { // убираем выделение с пути
                    break;
                }
                case 3: { // убираем выделение с текущего ребра
                    restoreSection();
                    break;
                }
                case 4: { // обнуляем i
                    restoreSection();
                    break;
                }
                case 5: { // Заполняет расстояния бесконечностями
                    break;
                }
                case 6: { // шаг цикла
                    restoreSection();
                    break;
                }
                case 7: { // обнуляем i2
                    restoreSection();
                    break;
                }
                case 8: { // Еще ничего не использовали
                    break;
                }
                case 9: { // шаг цикла
                    restoreSection();
                    break;
                }
                case 10: { // Делаем источник источником для Дейкстры и инициализируем i
                    restoreSection();
                    break;
                }
                case 11: { // Внешний цикл дейкстры
                    break;
                }
                case 12: { // инициализируем u и j
                    restoreSection();
                    break;
                }
                case 13: { // выбираем виршину
                    break;
                }
                case 14: { // выбираем наиболее близкую неиспользованную вершину
                    break;
                }
                case 15: { // выбираем наиболее близкую неиспользованную вершину (окончание)
                    break;
                }
                case 16: { // обновляем u
                    restoreSection();
                    break;
                }
                case 17: { // инкремент
                    restoreSection();
                    break;
                }
                case 18: { // Есть ли вершина, которую можно использовать
                    break;
                }
                case 19: { // Есть ли вершина, которую можно использовать (окончание)
                    break;
                }
                case 20: { // инициализируем j
                    restoreSection();
                    break;
                }
                case 21: { // цикл по ребрам, инцидентным u
                    break;
                }
                case 22: { // надо ли обновлять вершину на конце ребра
                    break;
                }
                case 23: { // надо ли обновлять вершину на конце ребра (окончание)
                    break;
                }
                case 24: { // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    restoreSection();
                    break;
                }
                case 25: { // следующее ребро
                    restoreSection();
                    break;
                }
                case 26: { // инициализируем j
                    restoreSection();
                    break;
                }
                case 27: { // цикл по обратным ребрам, инцидентным u
                    break;
                }
                case 28: { // надо ли обновлять вершину на конце ребра
                    break;
                }
                case 29: { // надо ли обновлять вершину на конце ребра (окончание)
                    break;
                }
                case 30: { // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    restoreSection();
                    break;
                }
                case 31: { // следующее ребро
                    restoreSection();
                    break;
                }
                case 32: { // мы использовали вершину u
                    restoreSection();
                    break;
                }
                case 33: { // инкремент
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // инициализируем cur
                    state = START_STATE; 
                    break;
                }
                case 2: { // убираем выделение с пути
                    if (stack.popBoolean()) {
                        state = 3; // убираем выделение с текущего ребра
                    } else {
                        state = 1; // инициализируем cur
                    }
                    break;
                }
                case 3: { // убираем выделение с текущего ребра
                    state = 2; // убираем выделение с пути
                    break;
                }
                case 4: { // обнуляем i
                    state = 2; // убираем выделение с пути
                    break;
                }
                case 5: { // Заполняет расстояния бесконечностями
                    if (stack.popBoolean()) {
                        state = 6; // шаг цикла
                    } else {
                        state = 4; // обнуляем i
                    }
                    break;
                }
                case 6: { // шаг цикла
                    state = 5; // Заполняет расстояния бесконечностями
                    break;
                }
                case 7: { // обнуляем i2
                    state = 5; // Заполняет расстояния бесконечностями
                    break;
                }
                case 8: { // Еще ничего не использовали
                    if (stack.popBoolean()) {
                        state = 9; // шаг цикла
                    } else {
                        state = 7; // обнуляем i2
                    }
                    break;
                }
                case 9: { // шаг цикла
                    state = 8; // Еще ничего не использовали
                    break;
                }
                case 10: { // Делаем источник источником для Дейкстры и инициализируем i
                    state = 8; // Еще ничего не использовали
                    break;
                }
                case 11: { // Внешний цикл дейкстры
                    if (stack.popBoolean()) {
                        state = 33; // инкремент
                    } else {
                        state = 10; // Делаем источник источником для Дейкстры и инициализируем i
                    }
                    break;
                }
                case 12: { // инициализируем u и j
                    state = 11; // Внешний цикл дейкстры
                    break;
                }
                case 13: { // выбираем виршину
                    if (stack.popBoolean()) {
                        state = 17; // инкремент
                    } else {
                        state = 12; // инициализируем u и j
                    }
                    break;
                }
                case 14: { // выбираем наиболее близкую неиспользованную вершину
                    state = 13; // выбираем виршину
                    break;
                }
                case 15: { // выбираем наиболее близкую неиспользованную вершину (окончание)
                    if (stack.popBoolean()) {
                        state = 16; // обновляем u
                    } else {
                        state = 14; // выбираем наиболее близкую неиспользованную вершину
                    }
                    break;
                }
                case 16: { // обновляем u
                    state = 14; // выбираем наиболее близкую неиспользованную вершину
                    break;
                }
                case 17: { // инкремент
                    state = 15; // выбираем наиболее близкую неиспользованную вершину (окончание)
                    break;
                }
                case 18: { // Есть ли вершина, которую можно использовать
                    state = 13; // выбираем виршину
                    break;
                }
                case 19: { // Есть ли вершина, которую можно использовать (окончание)
                    if (stack.popBoolean()) {
                        state = 32; // мы использовали вершину u
                    } else {
                        state = 18; // Есть ли вершина, которую можно использовать
                    }
                    break;
                }
                case 20: { // инициализируем j
                    state = 18; // Есть ли вершина, которую можно использовать
                    break;
                }
                case 21: { // цикл по ребрам, инцидентным u
                    if (stack.popBoolean()) {
                        state = 25; // следующее ребро
                    } else {
                        state = 20; // инициализируем j
                    }
                    break;
                }
                case 22: { // надо ли обновлять вершину на конце ребра
                    state = 21; // цикл по ребрам, инцидентным u
                    break;
                }
                case 23: { // надо ли обновлять вершину на конце ребра (окончание)
                    if (stack.popBoolean()) {
                        state = 24; // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    } else {
                        state = 22; // надо ли обновлять вершину на конце ребра
                    }
                    break;
                }
                case 24: { // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    state = 22; // надо ли обновлять вершину на конце ребра
                    break;
                }
                case 25: { // следующее ребро
                    state = 23; // надо ли обновлять вершину на конце ребра (окончание)
                    break;
                }
                case 26: { // инициализируем j
                    state = 21; // цикл по ребрам, инцидентным u
                    break;
                }
                case 27: { // цикл по обратным ребрам, инцидентным u
                    if (stack.popBoolean()) {
                        state = 31; // следующее ребро
                    } else {
                        state = 26; // инициализируем j
                    }
                    break;
                }
                case 28: { // надо ли обновлять вершину на конце ребра
                    state = 27; // цикл по обратным ребрам, инцидентным u
                    break;
                }
                case 29: { // надо ли обновлять вершину на конце ребра (окончание)
                    if (stack.popBoolean()) {
                        state = 30; // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    } else {
                        state = 28; // надо ли обновлять вершину на конце ребра
                    }
                    break;
                }
                case 30: { // обновляем расстояние до вершины и добавляем ребро в массив для восстановления
                    state = 28; // надо ли обновлять вершину на конце ребра
                    break;
                }
                case 31: { // следующее ребро
                    state = 29; // надо ли обновлять вершину на конце ребра (окончание)
                    break;
                }
                case 32: { // мы использовали вершину u
                    state = 27; // цикл по обратным ребрам, инцидентным u
                    break;
                }
                case 33: { // инкремент
                    state = 19; // Есть ли вершина, которую можно использовать (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 11; // Внешний цикл дейкстры
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
      * Обновление потенциалов.
      */
    private final class updatePotentials extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 5;

        /**
          * Конструктор.
          */
        public updatePotentials() {
            super( 
                "updatePotentials", 
                0, // Номер начального состояния 
                5, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "инициализируем i", 
                    "цикл для обновления потенциалов", 
                    "обновляем потенциал и переходим к следующей вершине", 
                    "потенциалы обновлены", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // инициализируем i 
                    -1, // цикл для обновления потенциалов 
                    -1, // обновляем потенциал и переходим к следующей вершине 
                    0, // потенциалы обновлены 
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
                    state = 1; // инициализируем i
                    break;
                }
                case 1: { // инициализируем i
                    stack.pushBoolean(false); 
                    state = 2; // цикл для обновления потенциалов
                    break;
                }
                case 2: { // цикл для обновления потенциалов
                    if (d.updatePotentials_i < d.n) {
                        state = 3; // обновляем потенциал и переходим к следующей вершине
                    } else {
                        state = 4; // потенциалы обновлены
                    }
                    break;
                }
                case 3: { // обновляем потенциал и переходим к следующей вершине
                    stack.pushBoolean(true); 
                    state = 2; // цикл для обновления потенциалов
                    break;
                }
                case 4: { // потенциалы обновлены
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // инициализируем i
                    startSection();
                    storeField(d, "updatePotentials_i");
                    d.updatePotentials_i = 0;
                    break;
                }
                case 2: { // цикл для обновления потенциалов
                    break;
                }
                case 3: { // обновляем потенциал и переходим к следующей вершине
                    startSection();
                    storeArray(d.phi, d.updatePotentials_i);
                    d.phi[d.updatePotentials_i] = d.dist[d.updatePotentials_i];
                    storeField(d, "updatePotentials_i");
                    d.updatePotentials_i = d.updatePotentials_i + 1;
                    break;
                }
                case 4: { // потенциалы обновлены
                    startSection();
                    storeField(d, "highlight");
                    d.highlight = 2;
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
                case 1: { // инициализируем i
                    restoreSection();
                    break;
                }
                case 2: { // цикл для обновления потенциалов
                    break;
                }
                case 3: { // обновляем потенциал и переходим к следующей вершине
                    restoreSection();
                    break;
                }
                case 4: { // потенциалы обновлены
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // инициализируем i
                    state = START_STATE; 
                    break;
                }
                case 2: { // цикл для обновления потенциалов
                    if (stack.popBoolean()) {
                        state = 3; // обновляем потенциал и переходим к следующей вершине
                    } else {
                        state = 1; // инициализируем i
                    }
                    break;
                }
                case 3: { // обновляем потенциал и переходим к следующей вершине
                    state = 2; // цикл для обновления потенциалов
                    break;
                }
                case 4: { // потенциалы обновлены
                    state = 2; // цикл для обновления потенциалов
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 4; // потенциалы обновлены
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
                case 4: { // потенциалы обновлены
                    comment = MinCostMaxFlow.this.getComment("updatePotentials.potentialUpdated"); 
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
                case 4: { // потенциалы обновлены
                    d.visualizer.drawGraph();
                    break;
                }
            }
        }
    }

    /**
      * Находим максимальный дополняющий поток для кратчайшего пути.
      */
    private final class recoverPath extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 9;

        /**
          * Конструктор.
          */
        public recoverPath() {
            super( 
                "recoverPath", 
                0, // Номер начального состояния 
                9, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "инициализируем текущую вершину стоком и максимальный дополняющий поток последним ребром", 
                    "находим максимальный дополняющий поток", 
                    "выделяем ребро пути", 
                    "надо ли обновлять поток", 
                    "надо ли обновлять поток (окончание)", 
                    "обновляем поток", 
                    "переходим к предыдущей вершине в пути", 
                    "путь восстановлен", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // инициализируем текущую вершину стоком и максимальный дополняющий поток последним ребром 
                    -1, // находим максимальный дополняющий поток 
                    -1, // выделяем ребро пути 
                    -1, // надо ли обновлять поток 
                    -1, // надо ли обновлять поток (окончание) 
                    -1, // обновляем поток 
                    -1, // переходим к предыдущей вершине в пути 
                    0, // путь восстановлен 
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
                    state = 1; // инициализируем текущую вершину стоком и максимальный дополняющий поток последним ребром
                    break;
                }
                case 1: { // инициализируем текущую вершину стоком и максимальный дополняющий поток последним ребром
                    stack.pushBoolean(false); 
                    state = 2; // находим максимальный дополняющий поток
                    break;
                }
                case 2: { // находим максимальный дополняющий поток
                    if (d.recoverPath_cur != d.source) {
                        state = 3; // выделяем ребро пути
                    } else {
                        state = 8; // путь восстановлен
                    }
                    break;
                }
                case 3: { // выделяем ребро пути
                    state = 4; // надо ли обновлять поток
                    break;
                }
                case 4: { // надо ли обновлять поток
                    if (d.maxFlow > d.prev[d.recoverPath_cur].capacity - d.prev[d.recoverPath_cur].flow) {
                        state = 6; // обновляем поток
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // надо ли обновлять поток (окончание)
                    }
                    break;
                }
                case 5: { // надо ли обновлять поток (окончание)
                    state = 7; // переходим к предыдущей вершине в пути
                    break;
                }
                case 6: { // обновляем поток
                    stack.pushBoolean(true); 
                    state = 5; // надо ли обновлять поток (окончание)
                    break;
                }
                case 7: { // переходим к предыдущей вершине в пути
                    stack.pushBoolean(true); 
                    state = 2; // находим максимальный дополняющий поток
                    break;
                }
                case 8: { // путь восстановлен
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // инициализируем текущую вершину стоком и максимальный дополняющий поток последним ребром
                    startSection();
                    storeField(d, "recoverPath_cur");
                    d.recoverPath_cur = d.sink;
                    storeField(d, "maxFlow");
                    d.maxFlow = d.prev[d.recoverPath_cur].capacity - d.prev[d.recoverPath_cur].flow;
                    break;
                }
                case 2: { // находим максимальный дополняющий поток
                    break;
                }
                case 3: { // выделяем ребро пути
                    startSection();
                    storeField(d.prev[d.recoverPath_cur], "highlighted");
                    d.prev[d.recoverPath_cur].highlighted = true;
                    break;
                }
                case 4: { // надо ли обновлять поток
                    break;
                }
                case 5: { // надо ли обновлять поток (окончание)
                    break;
                }
                case 6: { // обновляем поток
                    startSection();
                    storeField(d, "maxFlow");
                    d.maxFlow = d.prev[d.recoverPath_cur].capacity - d.prev[d.recoverPath_cur].flow;
                    break;
                }
                case 7: { // переходим к предыдущей вершине в пути
                    startSection();
                    storeField(d, "recoverPath_cur");
                    d.recoverPath_cur = d.prev[d.recoverPath_cur].u;
                    break;
                }
                case 8: { // путь восстановлен
                    startSection();
                    storeField(d, "highlight");
                    d.highlight = 0;
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
                case 1: { // инициализируем текущую вершину стоком и максимальный дополняющий поток последним ребром
                    restoreSection();
                    break;
                }
                case 2: { // находим максимальный дополняющий поток
                    break;
                }
                case 3: { // выделяем ребро пути
                    restoreSection();
                    break;
                }
                case 4: { // надо ли обновлять поток
                    break;
                }
                case 5: { // надо ли обновлять поток (окончание)
                    break;
                }
                case 6: { // обновляем поток
                    restoreSection();
                    break;
                }
                case 7: { // переходим к предыдущей вершине в пути
                    restoreSection();
                    break;
                }
                case 8: { // путь восстановлен
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // инициализируем текущую вершину стоком и максимальный дополняющий поток последним ребром
                    state = START_STATE; 
                    break;
                }
                case 2: { // находим максимальный дополняющий поток
                    if (stack.popBoolean()) {
                        state = 7; // переходим к предыдущей вершине в пути
                    } else {
                        state = 1; // инициализируем текущую вершину стоком и максимальный дополняющий поток последним ребром
                    }
                    break;
                }
                case 3: { // выделяем ребро пути
                    state = 2; // находим максимальный дополняющий поток
                    break;
                }
                case 4: { // надо ли обновлять поток
                    state = 3; // выделяем ребро пути
                    break;
                }
                case 5: { // надо ли обновлять поток (окончание)
                    if (stack.popBoolean()) {
                        state = 6; // обновляем поток
                    } else {
                        state = 4; // надо ли обновлять поток
                    }
                    break;
                }
                case 6: { // обновляем поток
                    state = 4; // надо ли обновлять поток
                    break;
                }
                case 7: { // переходим к предыдущей вершине в пути
                    state = 5; // надо ли обновлять поток (окончание)
                    break;
                }
                case 8: { // путь восстановлен
                    state = 2; // находим максимальный дополняющий поток
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 8; // путь восстановлен
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
                case 8: { // путь восстановлен
                    comment = MinCostMaxFlow.this.getComment("recoverPath.pathRecovered"); 
                    args = new Object[]{new Long(d.maxFlow)}; 
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
                case 8: { // путь восстановлен
                    d.visualizer.drawGraph();
                    break;
                }
            }
        }
    }

    /**
      * пропускаем поток и обновляем стоимость.
      */
    private final class letPassFlow extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 9;

        /**
          * Конструктор.
          */
        public letPassFlow() {
            super( 
                "letPassFlow", 
                0, // Номер начального состояния 
                9, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "инициализируем начальную вершину стоком и текущую стоимость нулем", 
                    "цикл для обновления потока и стоимости", 
                    "обратное ли это ребро", 
                    "обратное ли это ребро (окончание)", 
                    "обновляем ребро, обратное текущему", 
                    "обновляем ребро, обратное текущему", 
                    "обновляем поток и стоимость", 
                    "пропустили поток", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // инициализируем начальную вершину стоком и текущую стоимость нулем 
                    -1, // цикл для обновления потока и стоимости 
                    -1, // обратное ли это ребро 
                    -1, // обратное ли это ребро (окончание) 
                    -1, // обновляем ребро, обратное текущему 
                    -1, // обновляем ребро, обратное текущему 
                    -1, // обновляем поток и стоимость 
                    1, // пропустили поток 
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
                    state = 1; // инициализируем начальную вершину стоком и текущую стоимость нулем
                    break;
                }
                case 1: { // инициализируем начальную вершину стоком и текущую стоимость нулем
                    stack.pushBoolean(false); 
                    state = 2; // цикл для обновления потока и стоимости
                    break;
                }
                case 2: { // цикл для обновления потока и стоимости
                    if (d.letPassFlow_cur != d.source) {
                        state = 3; // обратное ли это ребро
                    } else {
                        state = 8; // пропустили поток
                    }
                    break;
                }
                case 3: { // обратное ли это ребро
                    if (d.prev[d.letPassFlow_cur].reversed) {
                        state = 5; // обновляем ребро, обратное текущему
                    } else {
                        state = 6; // обновляем ребро, обратное текущему
                    }
                    break;
                }
                case 4: { // обратное ли это ребро (окончание)
                    state = 7; // обновляем поток и стоимость
                    break;
                }
                case 5: { // обновляем ребро, обратное текущему
                    stack.pushBoolean(true); 
                    state = 4; // обратное ли это ребро (окончание)
                    break;
                }
                case 6: { // обновляем ребро, обратное текущему
                    stack.pushBoolean(false); 
                    state = 4; // обратное ли это ребро (окончание)
                    break;
                }
                case 7: { // обновляем поток и стоимость
                    stack.pushBoolean(true); 
                    state = 2; // цикл для обновления потока и стоимости
                    break;
                }
                case 8: { // пропустили поток
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // инициализируем начальную вершину стоком и текущую стоимость нулем
                    startSection();
                    storeField(d, "letPassFlow_cur");
                    d.letPassFlow_cur = d.sink;
                    storeField(d, "letPassFlow_curCost");
                    d.letPassFlow_curCost = 0;
                    break;
                }
                case 2: { // цикл для обновления потока и стоимости
                    break;
                }
                case 3: { // обратное ли это ребро
                    break;
                }
                case 4: { // обратное ли это ребро (окончание)
                    break;
                }
                case 5: { // обновляем ребро, обратное текущему
                    startSection();
                    storeField(d.edge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u], "flow");
                    d.edge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u].flow = d.edge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u].flow - d.maxFlow; 
                    break;
                }
                case 6: { // обновляем ребро, обратное текущему
                    startSection();
                    storeField(d.reverseEdge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u], "flow");
                    d.reverseEdge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u].flow = d.reverseEdge[d.letPassFlow_cur][d.prev[d.letPassFlow_cur].u].flow - d.maxFlow; 
                    break;
                }
                case 7: { // обновляем поток и стоимость
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
                case 8: { // пропустили поток
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
                case 1: { // инициализируем начальную вершину стоком и текущую стоимость нулем
                    restoreSection();
                    break;
                }
                case 2: { // цикл для обновления потока и стоимости
                    break;
                }
                case 3: { // обратное ли это ребро
                    break;
                }
                case 4: { // обратное ли это ребро (окончание)
                    break;
                }
                case 5: { // обновляем ребро, обратное текущему
                    restoreSection();
                    break;
                }
                case 6: { // обновляем ребро, обратное текущему
                    restoreSection();
                    break;
                }
                case 7: { // обновляем поток и стоимость
                    restoreSection();
                    break;
                }
                case 8: { // пропустили поток
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // инициализируем начальную вершину стоком и текущую стоимость нулем
                    state = START_STATE; 
                    break;
                }
                case 2: { // цикл для обновления потока и стоимости
                    if (stack.popBoolean()) {
                        state = 7; // обновляем поток и стоимость
                    } else {
                        state = 1; // инициализируем начальную вершину стоком и текущую стоимость нулем
                    }
                    break;
                }
                case 3: { // обратное ли это ребро
                    state = 2; // цикл для обновления потока и стоимости
                    break;
                }
                case 4: { // обратное ли это ребро (окончание)
                    if (stack.popBoolean()) {
                        state = 5; // обновляем ребро, обратное текущему
                    } else {
                        state = 6; // обновляем ребро, обратное текущему
                    }
                    break;
                }
                case 5: { // обновляем ребро, обратное текущему
                    state = 3; // обратное ли это ребро
                    break;
                }
                case 6: { // обновляем ребро, обратное текущему
                    state = 3; // обратное ли это ребро
                    break;
                }
                case 7: { // обновляем поток и стоимость
                    state = 4; // обратное ли это ребро (окончание)
                    break;
                }
                case 8: { // пропустили поток
                    state = 2; // цикл для обновления потока и стоимости
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 8; // пропустили поток
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
                case 8: { // пропустили поток
                    comment = MinCostMaxFlow.this.getComment("letPassFlow.flowPassed"); 
                    args = new Object[]{new Long(d.letPassFlow_curCost)}; 
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
                case 8: { // пропустили поток
                    d.visualizer.drawGraph();
                    break;
                }
            }
        }
    }

    /**
      * Находит максимальный поток минимальной стоимости.
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
                    "Инициализация", 
                    "Вычисляет расстояния от источника до вершин (автомат)", 
                    "Главный цикл", 
                    "существет путь", 
                    "Обновление потенциалов (автомат)", 
                    "Находим максимальный дополняющий поток для кратчайшего пути (автомат)", 
                    "пропускаем поток и обновляем стоимость (автомат)", 
                    "Вычисляет расстояния от источника до вершин (автомат)", 
                    "путь до стока отсутствует", 
                    "убираем расширенное рисование", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    0, // Инициализация 
                    CALL_AUTO_LEVEL, // Вычисляет расстояния от источника до вершин (автомат) 
                    -1, // Главный цикл 
                    0, // существет путь 
                    CALL_AUTO_LEVEL, // Обновление потенциалов (автомат) 
                    CALL_AUTO_LEVEL, // Находим максимальный дополняющий поток для кратчайшего пути (автомат) 
                    CALL_AUTO_LEVEL, // пропускаем поток и обновляем стоимость (автомат) 
                    CALL_AUTO_LEVEL, // Вычисляет расстояния от источника до вершин (автомат) 
                    0, // путь до стока отсутствует 
                    -1, // убираем расширенное рисование 
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
                    state = 1; // Инициализация
                    break;
                }
                case 1: { // Инициализация
                    state = 2; // Вычисляет расстояния от источника до вершин (автомат)
                    break;
                }
                case 2: { // Вычисляет расстояния от источника до вершин (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 3; // Главный цикл
                    }
                    break;
                }
                case 3: { // Главный цикл
                    if (d.dist[d.sink] != -1) {
                        state = 4; // существет путь
                    } else {
                        state = 9; // путь до стока отсутствует
                    }
                    break;
                }
                case 4: { // существет путь
                    state = 5; // Обновление потенциалов (автомат)
                    break;
                }
                case 5: { // Обновление потенциалов (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 6; // Находим максимальный дополняющий поток для кратчайшего пути (автомат)
                    }
                    break;
                }
                case 6: { // Находим максимальный дополняющий поток для кратчайшего пути (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 7; // пропускаем поток и обновляем стоимость (автомат)
                    }
                    break;
                }
                case 7: { // пропускаем поток и обновляем стоимость (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // Вычисляет расстояния от источника до вершин (автомат)
                    }
                    break;
                }
                case 8: { // Вычисляет расстояния от источника до вершин (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 3; // Главный цикл
                    }
                    break;
                }
                case 9: { // путь до стока отсутствует
                    state = 10; // убираем расширенное рисование
                    break;
                }
                case 10: { // убираем расширенное рисование
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация
                    startSection();
                    storeField(d, "extendedDraw");
                    d.extendedDraw = true;
                    break;
                }
                case 2: { // Вычисляет расстояния от источника до вершин (автомат)
                    if (child == null) {
                        child = new dijkstra(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 3: { // Главный цикл
                    break;
                }
                case 4: { // существет путь
                    startSection();
                    storeField(d, "highlight");
                    d.highlight = 1;
                    break;
                }
                case 5: { // Обновление потенциалов (автомат)
                    if (child == null) {
                        child = new updatePotentials(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 6: { // Находим максимальный дополняющий поток для кратчайшего пути (автомат)
                    if (child == null) {
                        child = new recoverPath(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 7: { // пропускаем поток и обновляем стоимость (автомат)
                    if (child == null) {
                        child = new letPassFlow(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // Вычисляет расстояния от источника до вершин (автомат)
                    if (child == null) {
                        child = new dijkstra(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 9: { // путь до стока отсутствует
                    startSection();
                    storeField(d, "highlight");
                    d.highlight = 1;
                    break;
                }
                case 10: { // убираем расширенное рисование
                    startSection();
                    storeField(d, "extendedDraw");
                    d.extendedDraw = false;
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
                case 1: { // Инициализация
                    restoreSection();
                    break;
                }
                case 2: { // Вычисляет расстояния от источника до вершин (автомат)
                    if (child == null) {
                        child = new dijkstra(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 3: { // Главный цикл
                    break;
                }
                case 4: { // существет путь
                    restoreSection();
                    break;
                }
                case 5: { // Обновление потенциалов (автомат)
                    if (child == null) {
                        child = new updatePotentials(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 6: { // Находим максимальный дополняющий поток для кратчайшего пути (автомат)
                    if (child == null) {
                        child = new recoverPath(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 7: { // пропускаем поток и обновляем стоимость (автомат)
                    if (child == null) {
                        child = new letPassFlow(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // Вычисляет расстояния от источника до вершин (автомат)
                    if (child == null) {
                        child = new dijkstra(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 9: { // путь до стока отсутствует
                    restoreSection();
                    break;
                }
                case 10: { // убираем расширенное рисование
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация
                    state = START_STATE; 
                    break;
                }
                case 2: { // Вычисляет расстояния от источника до вершин (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // Инициализация
                    }
                    break;
                }
                case 3: { // Главный цикл
                    if (stack.popBoolean()) {
                        state = 8; // Вычисляет расстояния от источника до вершин (автомат)
                    } else {
                        state = 2; // Вычисляет расстояния от источника до вершин (автомат)
                    }
                    break;
                }
                case 4: { // существет путь
                    state = 3; // Главный цикл
                    break;
                }
                case 5: { // Обновление потенциалов (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 4; // существет путь
                    }
                    break;
                }
                case 6: { // Находим максимальный дополняющий поток для кратчайшего пути (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 5; // Обновление потенциалов (автомат)
                    }
                    break;
                }
                case 7: { // пропускаем поток и обновляем стоимость (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // Находим максимальный дополняющий поток для кратчайшего пути (автомат)
                    }
                    break;
                }
                case 8: { // Вычисляет расстояния от источника до вершин (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 7; // пропускаем поток и обновляем стоимость (автомат)
                    }
                    break;
                }
                case 9: { // путь до стока отсутствует
                    state = 3; // Главный цикл
                    break;
                }
                case 10: { // убираем расширенное рисование
                    state = 9; // путь до стока отсутствует
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 10; // убираем расширенное рисование
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
                    comment = MinCostMaxFlow.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // Инициализация
                    comment = MinCostMaxFlow.this.getComment("Main.Initialization"); 
                    break;
                }
                case 2: { // Вычисляет расстояния от источника до вершин (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 4: { // существет путь
                    comment = MinCostMaxFlow.this.getComment("Main.pathFinded"); 
                    break;
                }
                case 5: { // Обновление потенциалов (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 6: { // Находим максимальный дополняющий поток для кратчайшего пути (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // пропускаем поток и обновляем стоимость (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 8: { // Вычисляет расстояния от источника до вершин (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 9: { // путь до стока отсутствует
                    comment = MinCostMaxFlow.this.getComment("Main.pathNotFinded"); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = MinCostMaxFlow.this.getComment("Main.END_STATE"); 
                    args = new Object[]{new Long(d.cost)}; 
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
                    d.visualizer.drawGraph();
                    break;
                }
                case 1: { // Инициализация
                    d.visualizer.drawGraph();
                    break;
                }
                case 2: { // Вычисляет расстояния от источника до вершин (автомат)
                    child.drawState(); 
                    break;
                }
                case 4: { // существет путь
                    d.visualizer.drawGraph();
                    break;
                }
                case 5: { // Обновление потенциалов (автомат)
                    child.drawState(); 
                    break;
                }
                case 6: { // Находим максимальный дополняющий поток для кратчайшего пути (автомат)
                    child.drawState(); 
                    break;
                }
                case 7: { // пропускаем поток и обновляем стоимость (автомат)
                    child.drawState(); 
                    break;
                }
                case 8: { // Вычисляет расстояния от источника до вершин (автомат)
                    child.drawState(); 
                    break;
                }
                case 9: { // путь до стока отсутствует
                    d.visualizer.drawGraph();
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.drawGraph();
                    break;
                }
            }
        }
    }
}
