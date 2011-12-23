package ru.ifmo.vizi.SegmentsTree;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class SegmentsTree extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public SegmentsTree(Locale locale) {
        super("ru.ifmo.vizi.SegmentsTree.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экземпляр апплета.
          */
        public SegmentsTreeVisualizer visualizer = null;

        /**
          * Левая граница поиска.
          */
        public int left = 0;

        /**
          * Правая граница поиска.
          */
        public int right = 0;

        /**
          * Массив дла поиска.
          */
        public int[] h = null;

        /**
          * Текущая левая граница поиска.
          */
        public int l = 0;

        /**
          * Текущая правая граница поиска.
          */
        public int r = 0;

        /**
          * Текущий минимум.
          */
        public int a = 0;

        /**
          * Нижняя граница для элементов дерева.
          */
        public int lowCellsBound = 0;

        /**
          * Нижняя граница минимумов.
          */
        public int lowMinBound = 0;

        /**
          * Высота дерева.
          */
        public int height = 0;

        /**
          * Символ, обозначающий текущий минимум.
          */
        public String minim = \u0022\u221E\u0022;

        /**
          * Значок бесконечности.
          */
        public String infinity = \u0022\u221E\u0022;

        /**
          * Левый элемент массива.
          */
        public String tl = \u0022\u221E\u0022;

        /**
          * Правый элемент массива.
          */
        public String tr = \u0022\u221E\u0022;

        public String toString() {
            return "" + d.a;
        }
    }

    /**
      * Ищет минимум в массиве, используя структуру данных - дерево отрезков.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 31;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                31, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация", 
                    "Приступаем к построение дерева отрезков", 
                    "Сдвигаем массив", 
                    "Надо ли визуализировать построение дерева", 
                    "Надо ли визуализировать построение дерева (окончание)", 
                    "Построение дерева", 
                    "Строим новый элемент", 
                    "Заменяем бесконечность", 
                    "Заменяем бесконечность (окончание)", 
                    "В комментарии кладем значок бесконечности", 
                    "В комментарии кладем значение элемента", 
                    "Заменяем бесконечность", 
                    "Заменяем бесконечность (окончание)", 
                    "В комментарии кладем значок бесконечности", 
                    "В комментарии кладем значение элемента", 
                    "Визуализация построения дерева", 
                    "Дерево уже построено", 
                    "Инициализация минимума", 
                    "Сжатие границ поиска", 
                    "Проход по дереву вверх с релаксацией минимума на границах", 
                    "Проверка левой границы на минимальность", 
                    "Проверка левой границы на минимальность (окончание)", 
                    "Обновление минимума", 
                    "Приверка на минимальность", 
                    "Приверка на минимальность (окончание)", 
                    "Обновление минимума", 
                    "проверка", 
                    "проверка (окончание)", 
                    "Сжатие границ поиска", 
                    "Границы поиска зашли друг за друга", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация 
                    0, // Приступаем к построение дерева отрезков 
                    0, // Сдвигаем массив 
                    -1, // Надо ли визуализировать построение дерева 
                    -1, // Надо ли визуализировать построение дерева (окончание) 
                    -1, // Построение дерева 
                    -1, // Строим новый элемент 
                    -1, // Заменяем бесконечность 
                    -1, // Заменяем бесконечность (окончание) 
                    -1, // В комментарии кладем значок бесконечности 
                    -1, // В комментарии кладем значение элемента 
                    -1, // Заменяем бесконечность 
                    -1, // Заменяем бесконечность (окончание) 
                    -1, // В комментарии кладем значок бесконечности 
                    -1, // В комментарии кладем значение элемента 
                    0, // Визуализация построения дерева 
                    0, // Дерево уже построено 
                    1, // Инициализация минимума 
                    1, // Сжатие границ поиска 
                    -1, // Проход по дереву вверх с релаксацией минимума на границах 
                    0, // Проверка левой границы на минимальность 
                    -1, // Проверка левой границы на минимальность (окончание) 
                    0, // Обновление минимума 
                    0, // Приверка на минимальность 
                    -1, // Приверка на минимальность (окончание) 
                    0, // Обновление минимума 
                    -1, // проверка 
                    -1, // проверка (окончание) 
                    0, // Сжатие границ поиска 
                    0, // Границы поиска зашли друг за друга 
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
                    state = 2; // Приступаем к построение дерева отрезков
                    break;
                }
                case 2: { // Приступаем к построение дерева отрезков
                    state = 3; // Сдвигаем массив
                    break;
                }
                case 3: { // Сдвигаем массив
                    state = 4; // Надо ли визуализировать построение дерева
                    break;
                }
                case 4: { // Надо ли визуализировать построение дерева
                    if (d.visualizer.showBuilding()) {
                        stack.pushBoolean(false); 
                        state = 6; // Построение дерева
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // Надо ли визуализировать построение дерева (окончание)
                    }
                    break;
                }
                case 5: { // Надо ли визуализировать построение дерева (окончание)
                    state = 17; // Дерево уже построено
                    break;
                }
                case 6: { // Построение дерева
                    if (d.lowCellsBound >= 2) {
                        state = 7; // Строим новый элемент
                    } else {
                        stack.pushBoolean(true); 
                        state = 5; // Надо ли визуализировать построение дерева (окончание)
                    }
                    break;
                }
                case 7: { // Строим новый элемент
                    state = 8; // Заменяем бесконечность
                    break;
                }
                case 8: { // Заменяем бесконечность
                    if (d.h[2 * d.lowCellsBound] == Integer.MAX_VALUE) {
                        state = 10; // В комментарии кладем значок бесконечности
                    } else {
                        state = 11; // В комментарии кладем значение элемента
                    }
                    break;
                }
                case 9: { // Заменяем бесконечность (окончание)
                    state = 12; // Заменяем бесконечность
                    break;
                }
                case 10: { // В комментарии кладем значок бесконечности
                    stack.pushBoolean(true); 
                    state = 9; // Заменяем бесконечность (окончание)
                    break;
                }
                case 11: { // В комментарии кладем значение элемента
                    stack.pushBoolean(false); 
                    state = 9; // Заменяем бесконечность (окончание)
                    break;
                }
                case 12: { // Заменяем бесконечность
                    if (d.h[2 * d.lowCellsBound + 1] == Integer.MAX_VALUE) {
                        state = 14; // В комментарии кладем значок бесконечности
                    } else {
                        state = 15; // В комментарии кладем значение элемента
                    }
                    break;
                }
                case 13: { // Заменяем бесконечность (окончание)
                    state = 16; // Визуализация построения дерева
                    break;
                }
                case 14: { // В комментарии кладем значок бесконечности
                    stack.pushBoolean(true); 
                    state = 13; // Заменяем бесконечность (окончание)
                    break;
                }
                case 15: { // В комментарии кладем значение элемента
                    stack.pushBoolean(false); 
                    state = 13; // Заменяем бесконечность (окончание)
                    break;
                }
                case 16: { // Визуализация построения дерева
                    stack.pushBoolean(true); 
                    state = 6; // Построение дерева
                    break;
                }
                case 17: { // Дерево уже построено
                    state = 18; // Инициализация минимума
                    break;
                }
                case 18: { // Инициализация минимума
                    state = 19; // Сжатие границ поиска
                    break;
                }
                case 19: { // Сжатие границ поиска
                    stack.pushBoolean(false); 
                    state = 20; // Проход по дереву вверх с релаксацией минимума на границах
                    break;
                }
                case 20: { // Проход по дереву вверх с релаксацией минимума на границах
                    if (d.l <= d.r) {
                        state = 21; // Проверка левой границы на минимальность
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 21: { // Проверка левой границы на минимальность
                    if (d.a > d.h[d.l]) {
                        state = 23; // Обновление минимума
                    } else {
                        stack.pushBoolean(false); 
                        state = 22; // Проверка левой границы на минимальность (окончание)
                    }
                    break;
                }
                case 22: { // Проверка левой границы на минимальность (окончание)
                    state = 24; // Приверка на минимальность
                    break;
                }
                case 23: { // Обновление минимума
                    stack.pushBoolean(true); 
                    state = 22; // Проверка левой границы на минимальность (окончание)
                    break;
                }
                case 24: { // Приверка на минимальность
                    if (d.a > d.h[d.r]) {
                        state = 26; // Обновление минимума
                    } else {
                        stack.pushBoolean(false); 
                        state = 25; // Приверка на минимальность (окончание)
                    }
                    break;
                }
                case 25: { // Приверка на минимальность (окончание)
                    state = 27; // проверка
                    break;
                }
                case 26: { // Обновление минимума
                    stack.pushBoolean(true); 
                    state = 25; // Приверка на минимальность (окончание)
                    break;
                }
                case 27: { // проверка
                    if (((d.l + 1)) / 2 <= ((d.r - 1) / 2)) {
                        state = 29; // Сжатие границ поиска
                    } else {
                        state = 30; // Границы поиска зашли друг за друга
                    }
                    break;
                }
                case 28: { // проверка (окончание)
                    stack.pushBoolean(true); 
                    state = 20; // Проход по дереву вверх с релаксацией минимума на границах
                    break;
                }
                case 29: { // Сжатие границ поиска
                    stack.pushBoolean(true); 
                    state = 28; // проверка (окончание)
                    break;
                }
                case 30: { // Границы поиска зашли друг за друга
                    stack.pushBoolean(false); 
                    state = 28; // проверка (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация
                    startSection();
                    storeField(d, "lowCellsBound");
                    d.lowCellsBound = d.h.length / 2;
                    break;
                }
                case 2: { // Приступаем к построение дерева отрезков
                    startSection();
                    break;
                }
                case 3: { // Сдвигаем массив
                    startSection();
                    break;
                }
                case 4: { // Надо ли визуализировать построение дерева
                    break;
                }
                case 5: { // Надо ли визуализировать построение дерева (окончание)
                    break;
                }
                case 6: { // Построение дерева
                    break;
                }
                case 7: { // Строим новый элемент
                    startSection();
                    storeField(d, "lowCellsBound");
                    	d.lowCellsBound = d.lowCellsBound - 1;
                    break;
                }
                case 8: { // Заменяем бесконечность
                    break;
                }
                case 9: { // Заменяем бесконечность (окончание)
                    break;
                }
                case 10: { // В комментарии кладем значок бесконечности
                    startSection();
                    storeField(d, "tl");
                    	d.tl = d.infinity;
                    break;
                }
                case 11: { // В комментарии кладем значение элемента
                    startSection();
                    storeField(d, "tl");
                    	d.tl = Integer.toString(d.h[2 * d.lowCellsBound]);
                    break;
                }
                case 12: { // Заменяем бесконечность
                    break;
                }
                case 13: { // Заменяем бесконечность (окончание)
                    break;
                }
                case 14: { // В комментарии кладем значок бесконечности
                    startSection();
                    storeField(d, "tr");
                    	d.tr = d.infinity;
                    break;
                }
                case 15: { // В комментарии кладем значение элемента
                    startSection();
                    storeField(d, "tr");
                    	d.tr = Integer.toString(d.h[2 * d.lowCellsBound + 1]);
                    break;
                }
                case 16: { // Визуализация построения дерева
                    startSection();
                    break;
                }
                case 17: { // Дерево уже построено
                    startSection();
                    break;
                }
                case 18: { // Инициализация минимума
                    startSection();
                    storeField(d, "a");
                    d.a = Integer.MAX_VALUE;
                    storeField(d, "minim");
                    d.minim = "\u221E";
                    break;
                }
                case 19: { // Сжатие границ поиска
                    startSection();
                    storeField(d, "l");
                    d.l = d.left;
                    storeField(d, "r");
                    d.r = d.right;
                    break;
                }
                case 20: { // Проход по дереву вверх с релаксацией минимума на границах
                    break;
                }
                case 21: { // Проверка левой границы на минимальность
                    break;
                }
                case 22: { // Проверка левой границы на минимальность (окончание)
                    break;
                }
                case 23: { // Обновление минимума
                    startSection();
                    storeField(d, "a");
                    d.a = d.h[d.l];
                    storeField(d, "minim");
                    d.minim = Integer.toString(d.a);
                    break;
                }
                case 24: { // Приверка на минимальность
                    break;
                }
                case 25: { // Приверка на минимальность (окончание)
                    break;
                }
                case 26: { // Обновление минимума
                    startSection();
                    storeField(d, "a");
                    d.a = d.h[d.r];
                    storeField(d, "minim");
                    d.minim = Integer.toString(d.a);
                    break;
                }
                case 27: { // проверка
                    break;
                }
                case 28: { // проверка (окончание)
                    break;
                }
                case 29: { // Сжатие границ поиска
                    startSection();
                    int next = (d.l + 1) / 2;
                    storeField(d, "l");
                    d.l = next;
                    
                    next = (d.r - 1) / 2;
                    storeField(d, "r");
                    d.r = next;
                    break;
                }
                case 30: { // Границы поиска зашли друг за друга
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
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Инициализация
                    restoreSection();
                    break;
                }
                case 2: { // Приступаем к построение дерева отрезков
                    restoreSection();
                    break;
                }
                case 3: { // Сдвигаем массив
                    restoreSection();
                    break;
                }
                case 4: { // Надо ли визуализировать построение дерева
                    break;
                }
                case 5: { // Надо ли визуализировать построение дерева (окончание)
                    break;
                }
                case 6: { // Построение дерева
                    break;
                }
                case 7: { // Строим новый элемент
                    restoreSection();
                    break;
                }
                case 8: { // Заменяем бесконечность
                    break;
                }
                case 9: { // Заменяем бесконечность (окончание)
                    break;
                }
                case 10: { // В комментарии кладем значок бесконечности
                    restoreSection();
                    break;
                }
                case 11: { // В комментарии кладем значение элемента
                    restoreSection();
                    break;
                }
                case 12: { // Заменяем бесконечность
                    break;
                }
                case 13: { // Заменяем бесконечность (окончание)
                    break;
                }
                case 14: { // В комментарии кладем значок бесконечности
                    restoreSection();
                    break;
                }
                case 15: { // В комментарии кладем значение элемента
                    restoreSection();
                    break;
                }
                case 16: { // Визуализация построения дерева
                    restoreSection();
                    break;
                }
                case 17: { // Дерево уже построено
                    restoreSection();
                    break;
                }
                case 18: { // Инициализация минимума
                    restoreSection();
                    break;
                }
                case 19: { // Сжатие границ поиска
                    restoreSection();
                    break;
                }
                case 20: { // Проход по дереву вверх с релаксацией минимума на границах
                    break;
                }
                case 21: { // Проверка левой границы на минимальность
                    break;
                }
                case 22: { // Проверка левой границы на минимальность (окончание)
                    break;
                }
                case 23: { // Обновление минимума
                    restoreSection();
                    break;
                }
                case 24: { // Приверка на минимальность
                    break;
                }
                case 25: { // Приверка на минимальность (окончание)
                    break;
                }
                case 26: { // Обновление минимума
                    restoreSection();
                    break;
                }
                case 27: { // проверка
                    break;
                }
                case 28: { // проверка (окончание)
                    break;
                }
                case 29: { // Сжатие границ поиска
                    restoreSection();
                    break;
                }
                case 30: { // Границы поиска зашли друг за друга
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
                case 2: { // Приступаем к построение дерева отрезков
                    state = 1; // Инициализация
                    break;
                }
                case 3: { // Сдвигаем массив
                    state = 2; // Приступаем к построение дерева отрезков
                    break;
                }
                case 4: { // Надо ли визуализировать построение дерева
                    state = 3; // Сдвигаем массив
                    break;
                }
                case 5: { // Надо ли визуализировать построение дерева (окончание)
                    if (stack.popBoolean()) {
                        state = 6; // Построение дерева
                    } else {
                        state = 4; // Надо ли визуализировать построение дерева
                    }
                    break;
                }
                case 6: { // Построение дерева
                    if (stack.popBoolean()) {
                        state = 16; // Визуализация построения дерева
                    } else {
                        state = 4; // Надо ли визуализировать построение дерева
                    }
                    break;
                }
                case 7: { // Строим новый элемент
                    state = 6; // Построение дерева
                    break;
                }
                case 8: { // Заменяем бесконечность
                    state = 7; // Строим новый элемент
                    break;
                }
                case 9: { // Заменяем бесконечность (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // В комментарии кладем значок бесконечности
                    } else {
                        state = 11; // В комментарии кладем значение элемента
                    }
                    break;
                }
                case 10: { // В комментарии кладем значок бесконечности
                    state = 8; // Заменяем бесконечность
                    break;
                }
                case 11: { // В комментарии кладем значение элемента
                    state = 8; // Заменяем бесконечность
                    break;
                }
                case 12: { // Заменяем бесконечность
                    state = 9; // Заменяем бесконечность (окончание)
                    break;
                }
                case 13: { // Заменяем бесконечность (окончание)
                    if (stack.popBoolean()) {
                        state = 14; // В комментарии кладем значок бесконечности
                    } else {
                        state = 15; // В комментарии кладем значение элемента
                    }
                    break;
                }
                case 14: { // В комментарии кладем значок бесконечности
                    state = 12; // Заменяем бесконечность
                    break;
                }
                case 15: { // В комментарии кладем значение элемента
                    state = 12; // Заменяем бесконечность
                    break;
                }
                case 16: { // Визуализация построения дерева
                    state = 13; // Заменяем бесконечность (окончание)
                    break;
                }
                case 17: { // Дерево уже построено
                    state = 5; // Надо ли визуализировать построение дерева (окончание)
                    break;
                }
                case 18: { // Инициализация минимума
                    state = 17; // Дерево уже построено
                    break;
                }
                case 19: { // Сжатие границ поиска
                    state = 18; // Инициализация минимума
                    break;
                }
                case 20: { // Проход по дереву вверх с релаксацией минимума на границах
                    if (stack.popBoolean()) {
                        state = 28; // проверка (окончание)
                    } else {
                        state = 19; // Сжатие границ поиска
                    }
                    break;
                }
                case 21: { // Проверка левой границы на минимальность
                    state = 20; // Проход по дереву вверх с релаксацией минимума на границах
                    break;
                }
                case 22: { // Проверка левой границы на минимальность (окончание)
                    if (stack.popBoolean()) {
                        state = 23; // Обновление минимума
                    } else {
                        state = 21; // Проверка левой границы на минимальность
                    }
                    break;
                }
                case 23: { // Обновление минимума
                    state = 21; // Проверка левой границы на минимальность
                    break;
                }
                case 24: { // Приверка на минимальность
                    state = 22; // Проверка левой границы на минимальность (окончание)
                    break;
                }
                case 25: { // Приверка на минимальность (окончание)
                    if (stack.popBoolean()) {
                        state = 26; // Обновление минимума
                    } else {
                        state = 24; // Приверка на минимальность
                    }
                    break;
                }
                case 26: { // Обновление минимума
                    state = 24; // Приверка на минимальность
                    break;
                }
                case 27: { // проверка
                    state = 25; // Приверка на минимальность (окончание)
                    break;
                }
                case 28: { // проверка (окончание)
                    if (stack.popBoolean()) {
                        state = 29; // Сжатие границ поиска
                    } else {
                        state = 30; // Границы поиска зашли друг за друга
                    }
                    break;
                }
                case 29: { // Сжатие границ поиска
                    state = 27; // проверка
                    break;
                }
                case 30: { // Границы поиска зашли друг за друга
                    state = 27; // проверка
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 20; // Проход по дереву вверх с релаксацией минимума на границах
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
                    comment = SegmentsTree.this.getComment("Main.START_STATE"); 
                    args = new Object[]{new Integer(d.left - d.h.length / 2 + 1), new Integer(d.right - d.h.length / 2 + 1)}; 
                    break;
                }
                case 2: { // Приступаем к построение дерева отрезков
                    comment = SegmentsTree.this.getComment("Main.treeBuilding1"); 
                    break;
                }
                case 3: { // Сдвигаем массив
                    comment = SegmentsTree.this.getComment("Main.move"); 
                    args = new Object[]{new Integer(d.h.length / 2 - 1)}; 
                    break;
                }
                case 16: { // Визуализация построения дерева
                    comment = SegmentsTree.this.getComment("Main.tree"); 
                    args = new Object[]{new Integer(2 * d.lowCellsBound), d.tl, new Integer(2 * d.lowCellsBound + 1), d.tr}; 
                    break;
                }
                case 17: { // Дерево уже построено
                    comment = SegmentsTree.this.getComment("Main.treeBuilded"); 
                    break;
                }
                case 18: { // Инициализация минимума
                    comment = SegmentsTree.this.getComment("Main.initialization"); 
                    break;
                }
                case 19: { // Сжатие границ поиска
                    comment = SegmentsTree.this.getComment("Main.firstRise"); 
                    args = new Object[]{new Integer(d.h.length / 2 - 1), new Integer(d.left), new Integer(d.right)}; 
                    break;
                }
                case 21: { // Проверка левой границы на минимальность
                    if (d.a > d.h[d.l]) {
                        comment = SegmentsTree.this.getComment("Main.Cond1.true"); 
                    } else {
                        comment = SegmentsTree.this.getComment("Main.Cond1.false"); 
                    }
                    args = new Object[]{new Integer(d.h[d.l]), d.minim}; 
                    break;
                }
                case 23: { // Обновление минимума
                    comment = SegmentsTree.this.getComment("Main.newMin1"); 
                    args = new Object[]{new Integer(d.a)}; 
                    break;
                }
                case 24: { // Приверка на минимальность
                    if (d.a > d.h[d.r]) {
                        comment = SegmentsTree.this.getComment("Main.Cond2.true"); 
                    } else {
                        comment = SegmentsTree.this.getComment("Main.Cond2.false"); 
                    }
                    args = new Object[]{new Integer(d.h[d.r]), d.minim}; 
                    break;
                }
                case 26: { // Обновление минимума
                    comment = SegmentsTree.this.getComment("Main.newMin2"); 
                    args = new Object[]{new Integer(d.a)}; 
                    break;
                }
                case 29: { // Сжатие границ поиска
                    comment = SegmentsTree.this.getComment("Main.rise2"); 
                    break;
                }
                case 30: { // Границы поиска зашли друг за друга
                    comment = SegmentsTree.this.getComment("Main.rise3"); 
                    args = new Object[]{new Integer((d.l + 1) / 2), new Integer((d.r - 1) / 2)}; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = SegmentsTree.this.getComment("Main.END_STATE"); 
                    args = new Object[]{new Integer(d.a)}; 
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
                    d.visualizer.drawBrackets(0, 0, false, false);
                    d.visualizer.drawCells(d.h.length / 2, 0, 0, 0, 0, d.h.length, false);
                    d.visualizer.drawMin(false);
                    break;
                }
                case 2: { // Приступаем к построение дерева отрезков
                    	d.visualizer.drawBrackets(0, 0, false, false);                
                    	d.visualizer.drawCells(d.h.length / 2, 0, 0, 0, 0, d.h.length, false);
                    	d.visualizer.drawMin(false);
                    break;
                }
                case 3: { // Сдвигаем массив
                    	d.visualizer.drawBrackets(0, 0, false, false);                
                    	d.visualizer.drawCells(d.h.length / 2, 0, 0, 0, 0, d.h.length, true);
                    	d.visualizer.drawMin(false);
                    break;
                }
                case 16: { // Визуализация построения дерева
                    	d.visualizer.drawCells(d.lowCellsBound, 0, 0, 0, 0, d.h.length, true);
                    	d.visualizer.drawBrackets(0, 0, false, false);
                    	d.visualizer.drawMin(false);
                    break;
                }
                case 17: { // Дерево уже построено
                    d.visualizer.drawCells(0, 0, 0, 0, 0, d.h.length, true);
                    d.visualizer.drawBrackets(0, 0, false, false);
                    d.visualizer.drawMin(false);
                    break;
                }
                case 18: { // Инициализация минимума
                    	d.visualizer.drawBrackets(0, 0, false, false);                
                    	d.visualizer.drawCells(0, 0, 0, 0, 0, d.h.length, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 19: { // Сжатие границ поиска
                    	d.visualizer.drawCells(0, d.l, d.r, 0, 0, d.h.length, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                     d.visualizer.setSecondBigStep();
                    break;
                }
                case 21: { // Проверка левой границы на минимальность
                    	d.visualizer.drawCells(0, d.l, d.r, d.l, 0, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 23: { // Обновление минимума
                    	d.visualizer.drawCells(0, d.l, d.r, d.l, d.l, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 24: { // Приверка на минимальность
                    	d.visualizer.drawCells(0, d.l, d.r, d.r, 0, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 26: { // Обновление минимума
                    	d.visualizer.drawCells(0, d.l, d.r, d.r, d.r, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 29: { // Сжатие границ поиска
                    	d.visualizer.drawCells(0, d.l, d.r, 0, 0, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case 30: { // Границы поиска зашли друг за друга
                    	d.visualizer.drawCells(0, d.l, d.r, 0, 0, d.r, true);
                    	d.visualizer.drawBrackets(d.l, d.r, true, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
                case END_STATE: { // Конечное состояние
                    	d.visualizer.drawCells(0, 0, 0, 0, 0, 0, true);
                    	d.visualizer.drawBrackets(d.l, d.r, false, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
            }
        }
    }
}
