package ru.ifmo.vizi.quick_sort;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class QuickSort extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public QuickSort(Locale locale) {
        super("ru.ifmo.vizi.quick_sort.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Массив для сортировки.
          */
        public int[] array = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * Массив левых границ.
          */
        public int[] leftBorders = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * Массив правых границ.
          */
        public int[] rightBorders = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * Глубина рекурсии.
          */
        public int depth = 0;

        /**
          * Барьер.
          */
        public int bar = 0;

        /**
          * Экземпляр апплета.
          */
        public QuickSortVisualizer visualizer = null;

        /**
          * Текущая левая граница (Процедура QSort).
          */
        public int QSort_left;

        /**
          * Текущая правая граница (Процедура QSort).
          */
        public int QSort_right;

        /**
          * Барьер (Процедура QSort).
          */
        public int QSort_m;

        /**
          * Локальная переменная (Процедура QSort).
          */
        public int QSort_i;

        /**
          * Локальная переменная (Процедура QSort).
          */
        public int QSort_j;

        /**
          * Локальная переменная (Процедура QSort).
          */
        public int QSort_tmp;

        public String toString() {
            		        StringBuffer s=new StringBuffer();
            			    return s.toString();
        }
    }

    /**
      * Главный автомат.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 3;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                3, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Запуск автомата сортировки массива", 
                    "Сортировка (автомат)", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Запуск автомата сортировки массива 
                    CALL_AUTO_LEVEL, // Сортировка (автомат) 
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
                    state = 1; // Запуск автомата сортировки массива
                    break;
                }
                case 1: { // Запуск автомата сортировки массива
                    state = 2; // Сортировка (автомат)
                    break;
                }
                case 2: { // Сортировка (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = END_STATE; 
                    }
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Запуск автомата сортировки массива
                    startSection();
                    storeField(d, "depth");
                    					d.depth = 0;
                    storeArray(d.leftBorders, d.depth);
                    					d.leftBorders[d.depth] = 0;
                    storeArray(d.rightBorders, d.depth);
                    					d.rightBorders[d.depth] = d.array.length - 1;
                    storeField(d, "depth");
                    					d.depth = d.depth + 1;
                    break;
                }
                case 2: { // Сортировка (автомат)
                    if (child == null) {
                        child = new QSort(); 
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
                case 1: { // Запуск автомата сортировки массива
                    restoreSection();
                    break;
                }
                case 2: { // Сортировка (автомат)
                    if (child == null) {
                        child = new QSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Запуск автомата сортировки массива
                    state = START_STATE; 
                    break;
                }
                case 2: { // Сортировка (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // Запуск автомата сортировки массива
                    }
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Сортировка (автомат)
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
                    comment = QuickSort.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 2: { // Сортировка (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = QuickSort.this.getComment("Main.END_STATE"); 
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
                    					d.visualizer.updateArray(0, d.array.length - 1);
                    					d.visualizer.updateBorders();
                    					d.visualizer.updateBar();
                    break;
                }
                case 1: { // Запуск автомата сортировки массива
                    					d.visualizer.updateArray(0, d.array.length - 1);
                    					d.visualizer.updateBorders();
                    break;
                }
                case 2: { // Сортировка (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    					d.visualizer.updateArray(0, d.array.length - 1);
                    					d.visualizer.updateBorders();
                    break;
                }
            }
        }
    }

    /**
      * Сортировка.
      */
    private final class QSort extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 36;

        /**
          * Конструктор.
          */
        public QSort() {
            super( 
                "QSort", 
                0, // Номер начального состояния 
                36, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация локальных переменных", 
                    "Выбор барьера", 
                    "Собощение о разделении", 
                    "Разделение элементов сортируемой части", 
                    "Поиск элемента большего или равного барьеру", 
                    "Увеличиваем i", 
                    "Найден первый элемент для обмена", 
                    "Поиск элемента меньшего или равного барьеру", 
                    "Уменьшаем j", 
                    "Найден второй элемент для обмена", 
                    "Условие смены мест элементов", 
                    "Условие смены мест элементов (окончание)", 
                    "Сообщение об обмене элементов", 
                    "Обмен переменных", 
                    "Сдвиг указателей", 
                    "Элементы не надо менять местами", 
                    "Условие для рекурсивного вызова для левой части", 
                    "Условие для рекурсивного вызова для левой части (окончание)", 
                    "Рекурсивный вызов левой части", 
                    "Сортировка (автомат)", 
                    "Восстановление переменных после рекурсии", 
                    "Проверка один элемент в левой части или она пуста", 
                    "Проверка один элемент в левой части или она пуста (окончание)", 
                    "Левая часть состоит из одного элемента", 
                    "Левая часть пуста", 
                    "Условие для рекурсивного вызова для правой части", 
                    "Условие для рекурсивного вызова для правой части (окончание)", 
                    "Рекурсивный вызов для правой части", 
                    "Сортировка (автомат)", 
                    "Восстановление переменных после рекурсии", 
                    "Проверка один элемент в правой части или она пуста", 
                    "Проверка один элемент в правой части или она пуста (окончание)", 
                    "Правая часть состоит из одного элемента", 
                    "Правая часть пуста", 
                    "Достаём из стэка последние границы", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    1, // Инициализация локальных переменных 
                    1, // Выбор барьера 
                    1, // Собощение о разделении 
                    -1, // Разделение элементов сортируемой части 
                    -1, // Поиск элемента большего или равного барьеру 
                    0, // Увеличиваем i 
                    0, // Найден первый элемент для обмена 
                    -1, // Поиск элемента меньшего или равного барьеру 
                    0, // Уменьшаем j 
                    0, // Найден второй элемент для обмена 
                    -1, // Условие смены мест элементов 
                    -1, // Условие смены мест элементов (окончание) 
                    0, // Сообщение об обмене элементов 
                    -1, // Обмен переменных 
                    0, // Сдвиг указателей 
                    0, // Элементы не надо менять местами 
                    -1, // Условие для рекурсивного вызова для левой части 
                    -1, // Условие для рекурсивного вызова для левой части (окончание) 
                    1, // Рекурсивный вызов левой части 
                    CALL_AUTO_LEVEL, // Сортировка (автомат) 
                    -1, // Восстановление переменных после рекурсии 
                    -1, // Проверка один элемент в левой части или она пуста 
                    -1, // Проверка один элемент в левой части или она пуста (окончание) 
                    1, // Левая часть состоит из одного элемента 
                    1, // Левая часть пуста 
                    -1, // Условие для рекурсивного вызова для правой части 
                    -1, // Условие для рекурсивного вызова для правой части (окончание) 
                    1, // Рекурсивный вызов для правой части 
                    CALL_AUTO_LEVEL, // Сортировка (автомат) 
                    -1, // Восстановление переменных после рекурсии 
                    -1, // Проверка один элемент в правой части или она пуста 
                    -1, // Проверка один элемент в правой части или она пуста (окончание) 
                    1, // Правая часть состоит из одного элемента 
                    1, // Правая часть пуста 
                    0, // Достаём из стэка последние границы 
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
                    state = 1; // Инициализация локальных переменных
                    break;
                }
                case 1: { // Инициализация локальных переменных
                    state = 2; // Выбор барьера
                    break;
                }
                case 2: { // Выбор барьера
                    state = 3; // Собощение о разделении
                    break;
                }
                case 3: { // Собощение о разделении
                    stack.pushBoolean(false); 
                    state = 4; // Разделение элементов сортируемой части
                    break;
                }
                case 4: { // Разделение элементов сортируемой части
                    if (d.QSort_i <= d.QSort_j) {
                        stack.pushBoolean(false); 
                        state = 5; // Поиск элемента большего или равного барьеру
                    } else {
                        state = 17; // Условие для рекурсивного вызова для левой части
                    }
                    break;
                }
                case 5: { // Поиск элемента большего или равного барьеру
                    if (d.array[d.QSort_i] < d.QSort_m) {
                        state = 6; // Увеличиваем i
                    } else {
                        state = 7; // Найден первый элемент для обмена
                    }
                    break;
                }
                case 6: { // Увеличиваем i
                    stack.pushBoolean(true); 
                    state = 5; // Поиск элемента большего или равного барьеру
                    break;
                }
                case 7: { // Найден первый элемент для обмена
                    stack.pushBoolean(false); 
                    state = 8; // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 8: { // Поиск элемента меньшего или равного барьеру
                    if (d.array[d.QSort_j] > d.QSort_m) {
                        state = 9; // Уменьшаем j
                    } else {
                        state = 10; // Найден второй элемент для обмена
                    }
                    break;
                }
                case 9: { // Уменьшаем j
                    stack.pushBoolean(true); 
                    state = 8; // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 10: { // Найден второй элемент для обмена
                    state = 11; // Условие смены мест элементов
                    break;
                }
                case 11: { // Условие смены мест элементов
                    if (d.QSort_i <= d.QSort_j) {
                        state = 13; // Сообщение об обмене элементов
                    } else {
                        state = 16; // Элементы не надо менять местами
                    }
                    break;
                }
                case 12: { // Условие смены мест элементов (окончание)
                    stack.pushBoolean(true); 
                    state = 4; // Разделение элементов сортируемой части
                    break;
                }
                case 13: { // Сообщение об обмене элементов
                    state = 14; // Обмен переменных
                    break;
                }
                case 14: { // Обмен переменных
                    state = 15; // Сдвиг указателей
                    break;
                }
                case 15: { // Сдвиг указателей
                    stack.pushBoolean(true); 
                    state = 12; // Условие смены мест элементов (окончание)
                    break;
                }
                case 16: { // Элементы не надо менять местами
                    stack.pushBoolean(false); 
                    state = 12; // Условие смены мест элементов (окончание)
                    break;
                }
                case 17: { // Условие для рекурсивного вызова для левой части
                    if (d.QSort_left < d.QSort_j) {
                        state = 19; // Рекурсивный вызов левой части
                    } else {
                        state = 22; // Проверка один элемент в левой части или она пуста
                    }
                    break;
                }
                case 18: { // Условие для рекурсивного вызова для левой части (окончание)
                    state = 26; // Условие для рекурсивного вызова для правой части
                    break;
                }
                case 19: { // Рекурсивный вызов левой части
                    state = 20; // Сортировка (автомат)
                    break;
                }
                case 20: { // Сортировка (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 21; // Восстановление переменных после рекурсии
                    }
                    break;
                }
                case 21: { // Восстановление переменных после рекурсии
                    stack.pushBoolean(true); 
                    state = 18; // Условие для рекурсивного вызова для левой части (окончание)
                    break;
                }
                case 22: { // Проверка один элемент в левой части или она пуста
                    if (d.QSort_left == d.QSort_j) {
                        state = 24; // Левая часть состоит из одного элемента
                    } else {
                        state = 25; // Левая часть пуста
                    }
                    break;
                }
                case 23: { // Проверка один элемент в левой части или она пуста (окончание)
                    stack.pushBoolean(false); 
                    state = 18; // Условие для рекурсивного вызова для левой части (окончание)
                    break;
                }
                case 24: { // Левая часть состоит из одного элемента
                    stack.pushBoolean(true); 
                    state = 23; // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 25: { // Левая часть пуста
                    stack.pushBoolean(false); 
                    state = 23; // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 26: { // Условие для рекурсивного вызова для правой части
                    if (d.QSort_i < d.QSort_right) {
                        state = 28; // Рекурсивный вызов для правой части
                    } else {
                        state = 31; // Проверка один элемент в правой части или она пуста
                    }
                    break;
                }
                case 27: { // Условие для рекурсивного вызова для правой части (окончание)
                    state = 35; // Достаём из стэка последние границы
                    break;
                }
                case 28: { // Рекурсивный вызов для правой части
                    state = 29; // Сортировка (автомат)
                    break;
                }
                case 29: { // Сортировка (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 30; // Восстановление переменных после рекурсии
                    }
                    break;
                }
                case 30: { // Восстановление переменных после рекурсии
                    stack.pushBoolean(true); 
                    state = 27; // Условие для рекурсивного вызова для правой части (окончание)
                    break;
                }
                case 31: { // Проверка один элемент в правой части или она пуста
                    if (d.QSort_right == d.QSort_i) {
                        state = 33; // Правая часть состоит из одного элемента
                    } else {
                        state = 34; // Правая часть пуста
                    }
                    break;
                }
                case 32: { // Проверка один элемент в правой части или она пуста (окончание)
                    stack.pushBoolean(false); 
                    state = 27; // Условие для рекурсивного вызова для правой части (окончание)
                    break;
                }
                case 33: { // Правая часть состоит из одного элемента
                    stack.pushBoolean(true); 
                    state = 32; // Проверка один элемент в правой части или она пуста (окончание)
                    break;
                }
                case 34: { // Правая часть пуста
                    stack.pushBoolean(false); 
                    state = 32; // Проверка один элемент в правой части или она пуста (окончание)
                    break;
                }
                case 35: { // Достаём из стэка последние границы
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация локальных переменных
                    startSection();
                    storeField(d, "QSort_left");
                    d.QSort_left = d.leftBorders[d.depth - 1];
                    storeField(d, "QSort_i");
                    d.QSort_i = d.QSort_left;
                    storeField(d, "QSort_right");
                    d.QSort_right = d.rightBorders[d.depth - 1];
                    storeField(d, "QSort_j");
                    d.QSort_j = d.QSort_right;
                    break;
                }
                case 2: { // Выбор барьера
                    startSection();
                    storeField(d, "QSort_m");
                    d.QSort_m = d.array[(d.QSort_left + d.QSort_right) / 2];
                    break;
                }
                case 3: { // Собощение о разделении
                    startSection();
                    break;
                }
                case 4: { // Разделение элементов сортируемой части
                    break;
                }
                case 5: { // Поиск элемента большего или равного барьеру
                    break;
                }
                case 6: { // Увеличиваем i
                    startSection();
                    storeField(d, "QSort_i");
                    						d.QSort_i = d.QSort_i + 1;
                    break;
                }
                case 7: { // Найден первый элемент для обмена
                    startSection();
                    break;
                }
                case 8: { // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 9: { // Уменьшаем j
                    startSection();
                    storeField(d, "QSort_j");
                    d.QSort_j = d.QSort_j - 1;
                    break;
                }
                case 10: { // Найден второй элемент для обмена
                    startSection();
                    break;
                }
                case 11: { // Условие смены мест элементов
                    break;
                }
                case 12: { // Условие смены мест элементов (окончание)
                    break;
                }
                case 13: { // Сообщение об обмене элементов
                    startSection();
                    break;
                }
                case 14: { // Обмен переменных
                    startSection();
                    storeField(d, "QSort_tmp");
                    d.QSort_tmp = d.array[d.QSort_i];
                    storeArray(d.array, d.QSort_i);
                    d.array[d.QSort_i] = d.array[d.QSort_j];
                    storeArray(d.array, d.QSort_j);
                    d.array[d.QSort_j] = d.QSort_tmp;
                    break;
                }
                case 15: { // Сдвиг указателей
                    startSection();
                    storeField(d, "QSort_i");
                    d.QSort_i = d.QSort_i + 1;
                    storeField(d, "QSort_j");
                    d.QSort_j = d.QSort_j - 1;
                    break;
                }
                case 16: { // Элементы не надо менять местами
                    startSection();
                    break;
                }
                case 17: { // Условие для рекурсивного вызова для левой части
                    break;
                }
                case 18: { // Условие для рекурсивного вызова для левой части (окончание)
                    break;
                }
                case 19: { // Рекурсивный вызов левой части
                    startSection();
                    storeArray(d.leftBorders, d.depth);
                    d.leftBorders[d.depth] = d.QSort_left;
                    storeArray(d.rightBorders, d.depth);
                    d.rightBorders[d.depth] = d.QSort_j;
                    storeField(d, "depth");
                    d.depth = d.depth + 1;
                    break;
                }
                case 20: { // Сортировка (автомат)
                    if (child == null) {
                        child = new QSort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 21: { // Восстановление переменных после рекурсии
                    startSection();
                    storeField(d, "QSort_left");
                    d.QSort_left = d.leftBorders[d.depth - 1];
                    storeField(d, "QSort_right");
                    d.QSort_right = d.rightBorders[d.depth - 1];
                    break;
                }
                case 22: { // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 23: { // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 24: { // Левая часть состоит из одного элемента
                    startSection();
                    break;
                }
                case 25: { // Левая часть пуста
                    startSection();
                    break;
                }
                case 26: { // Условие для рекурсивного вызова для правой части
                    break;
                }
                case 27: { // Условие для рекурсивного вызова для правой части (окончание)
                    break;
                }
                case 28: { // Рекурсивный вызов для правой части
                    startSection();
                    storeArray(d.leftBorders, d.depth);
                    d.leftBorders[d.depth] = d.QSort_i;
                    storeArray(d.rightBorders, d.depth);
                    d.rightBorders[d.depth] = d.QSort_right;
                    storeField(d, "depth");
                    d.depth = d.depth + 1;
                    break;
                }
                case 29: { // Сортировка (автомат)
                    if (child == null) {
                        child = new QSort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 30: { // Восстановление переменных после рекурсии
                    startSection();
                    storeField(d, "QSort_left");
                    d.QSort_left = d.leftBorders[d.depth - 1];
                    storeField(d, "QSort_right");
                    d.QSort_right = d.rightBorders[d.depth - 1];
                    break;
                }
                case 31: { // Проверка один элемент в правой части или она пуста
                    break;
                }
                case 32: { // Проверка один элемент в правой части или она пуста (окончание)
                    break;
                }
                case 33: { // Правая часть состоит из одного элемента
                    startSection();
                    break;
                }
                case 34: { // Правая часть пуста
                    startSection();
                    break;
                }
                case 35: { // Достаём из стэка последние границы
                    startSection();
                    storeField(d, "depth");
                    				d.depth = d.depth - 1;
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
                case 1: { // Инициализация локальных переменных
                    restoreSection();
                    break;
                }
                case 2: { // Выбор барьера
                    restoreSection();
                    break;
                }
                case 3: { // Собощение о разделении
                    restoreSection();
                    break;
                }
                case 4: { // Разделение элементов сортируемой части
                    break;
                }
                case 5: { // Поиск элемента большего или равного барьеру
                    break;
                }
                case 6: { // Увеличиваем i
                    restoreSection();
                    break;
                }
                case 7: { // Найден первый элемент для обмена
                    restoreSection();
                    break;
                }
                case 8: { // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 9: { // Уменьшаем j
                    restoreSection();
                    break;
                }
                case 10: { // Найден второй элемент для обмена
                    restoreSection();
                    break;
                }
                case 11: { // Условие смены мест элементов
                    break;
                }
                case 12: { // Условие смены мест элементов (окончание)
                    break;
                }
                case 13: { // Сообщение об обмене элементов
                    restoreSection();
                    break;
                }
                case 14: { // Обмен переменных
                    restoreSection();
                    break;
                }
                case 15: { // Сдвиг указателей
                    restoreSection();
                    break;
                }
                case 16: { // Элементы не надо менять местами
                    restoreSection();
                    break;
                }
                case 17: { // Условие для рекурсивного вызова для левой части
                    break;
                }
                case 18: { // Условие для рекурсивного вызова для левой части (окончание)
                    break;
                }
                case 19: { // Рекурсивный вызов левой части
                    restoreSection();
                    break;
                }
                case 20: { // Сортировка (автомат)
                    if (child == null) {
                        child = new QSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 21: { // Восстановление переменных после рекурсии
                    restoreSection();
                    break;
                }
                case 22: { // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 23: { // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 24: { // Левая часть состоит из одного элемента
                    restoreSection();
                    break;
                }
                case 25: { // Левая часть пуста
                    restoreSection();
                    break;
                }
                case 26: { // Условие для рекурсивного вызова для правой части
                    break;
                }
                case 27: { // Условие для рекурсивного вызова для правой части (окончание)
                    break;
                }
                case 28: { // Рекурсивный вызов для правой части
                    restoreSection();
                    break;
                }
                case 29: { // Сортировка (автомат)
                    if (child == null) {
                        child = new QSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 30: { // Восстановление переменных после рекурсии
                    restoreSection();
                    break;
                }
                case 31: { // Проверка один элемент в правой части или она пуста
                    break;
                }
                case 32: { // Проверка один элемент в правой части или она пуста (окончание)
                    break;
                }
                case 33: { // Правая часть состоит из одного элемента
                    restoreSection();
                    break;
                }
                case 34: { // Правая часть пуста
                    restoreSection();
                    break;
                }
                case 35: { // Достаём из стэка последние границы
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация локальных переменных
                    state = START_STATE; 
                    break;
                }
                case 2: { // Выбор барьера
                    state = 1; // Инициализация локальных переменных
                    break;
                }
                case 3: { // Собощение о разделении
                    state = 2; // Выбор барьера
                    break;
                }
                case 4: { // Разделение элементов сортируемой части
                    if (stack.popBoolean()) {
                        state = 12; // Условие смены мест элементов (окончание)
                    } else {
                        state = 3; // Собощение о разделении
                    }
                    break;
                }
                case 5: { // Поиск элемента большего или равного барьеру
                    if (stack.popBoolean()) {
                        state = 6; // Увеличиваем i
                    } else {
                        state = 4; // Разделение элементов сортируемой части
                    }
                    break;
                }
                case 6: { // Увеличиваем i
                    state = 5; // Поиск элемента большего или равного барьеру
                    break;
                }
                case 7: { // Найден первый элемент для обмена
                    state = 5; // Поиск элемента большего или равного барьеру
                    break;
                }
                case 8: { // Поиск элемента меньшего или равного барьеру
                    if (stack.popBoolean()) {
                        state = 9; // Уменьшаем j
                    } else {
                        state = 7; // Найден первый элемент для обмена
                    }
                    break;
                }
                case 9: { // Уменьшаем j
                    state = 8; // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 10: { // Найден второй элемент для обмена
                    state = 8; // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 11: { // Условие смены мест элементов
                    state = 10; // Найден второй элемент для обмена
                    break;
                }
                case 12: { // Условие смены мест элементов (окончание)
                    if (stack.popBoolean()) {
                        state = 15; // Сдвиг указателей
                    } else {
                        state = 16; // Элементы не надо менять местами
                    }
                    break;
                }
                case 13: { // Сообщение об обмене элементов
                    state = 11; // Условие смены мест элементов
                    break;
                }
                case 14: { // Обмен переменных
                    state = 13; // Сообщение об обмене элементов
                    break;
                }
                case 15: { // Сдвиг указателей
                    state = 14; // Обмен переменных
                    break;
                }
                case 16: { // Элементы не надо менять местами
                    state = 11; // Условие смены мест элементов
                    break;
                }
                case 17: { // Условие для рекурсивного вызова для левой части
                    state = 4; // Разделение элементов сортируемой части
                    break;
                }
                case 18: { // Условие для рекурсивного вызова для левой части (окончание)
                    if (stack.popBoolean()) {
                        state = 21; // Восстановление переменных после рекурсии
                    } else {
                        state = 23; // Проверка один элемент в левой части или она пуста (окончание)
                    }
                    break;
                }
                case 19: { // Рекурсивный вызов левой части
                    state = 17; // Условие для рекурсивного вызова для левой части
                    break;
                }
                case 20: { // Сортировка (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // Рекурсивный вызов левой части
                    }
                    break;
                }
                case 21: { // Восстановление переменных после рекурсии
                    state = 20; // Сортировка (автомат)
                    break;
                }
                case 22: { // Проверка один элемент в левой части или она пуста
                    state = 17; // Условие для рекурсивного вызова для левой части
                    break;
                }
                case 23: { // Проверка один элемент в левой части или она пуста (окончание)
                    if (stack.popBoolean()) {
                        state = 24; // Левая часть состоит из одного элемента
                    } else {
                        state = 25; // Левая часть пуста
                    }
                    break;
                }
                case 24: { // Левая часть состоит из одного элемента
                    state = 22; // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 25: { // Левая часть пуста
                    state = 22; // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 26: { // Условие для рекурсивного вызова для правой части
                    state = 18; // Условие для рекурсивного вызова для левой части (окончание)
                    break;
                }
                case 27: { // Условие для рекурсивного вызова для правой части (окончание)
                    if (stack.popBoolean()) {
                        state = 30; // Восстановление переменных после рекурсии
                    } else {
                        state = 32; // Проверка один элемент в правой части или она пуста (окончание)
                    }
                    break;
                }
                case 28: { // Рекурсивный вызов для правой части
                    state = 26; // Условие для рекурсивного вызова для правой части
                    break;
                }
                case 29: { // Сортировка (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 28; // Рекурсивный вызов для правой части
                    }
                    break;
                }
                case 30: { // Восстановление переменных после рекурсии
                    state = 29; // Сортировка (автомат)
                    break;
                }
                case 31: { // Проверка один элемент в правой части или она пуста
                    state = 26; // Условие для рекурсивного вызова для правой части
                    break;
                }
                case 32: { // Проверка один элемент в правой части или она пуста (окончание)
                    if (stack.popBoolean()) {
                        state = 33; // Правая часть состоит из одного элемента
                    } else {
                        state = 34; // Правая часть пуста
                    }
                    break;
                }
                case 33: { // Правая часть состоит из одного элемента
                    state = 31; // Проверка один элемент в правой части или она пуста
                    break;
                }
                case 34: { // Правая часть пуста
                    state = 31; // Проверка один элемент в правой части или она пуста
                    break;
                }
                case 35: { // Достаём из стэка последние границы
                    state = 27; // Условие для рекурсивного вызова для правой части (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 35; // Достаём из стэка последние границы
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
                case 1: { // Инициализация локальных переменных
                    comment = QuickSort.this.getComment("QSort.SetVariables"); 
                    args = new Object[]{new Integer(d.QSort_left + 1), new Integer(d.QSort_right + 1)}; 
                    break;
                }
                case 2: { // Выбор барьера
                    comment = QuickSort.this.getComment("QSort.ChooseBarrier"); 
                    break;
                }
                case 3: { // Собощение о разделении
                    comment = QuickSort.this.getComment("QSort.partitionMessage"); 
                    break;
                }
                case 6: { // Увеличиваем i
                    comment = QuickSort.this.getComment("QSort.incrementI"); 
                    args = new Object[]{new Integer(d.array[d.QSort_i - 1]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 7: { // Найден первый элемент для обмена
                    comment = QuickSort.this.getComment("QSort.firstElementFound"); 
                    args = new Object[]{new Integer(d.array[d.QSort_i]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 9: { // Уменьшаем j
                    comment = QuickSort.this.getComment("QSort.decrementJ"); 
                    args = new Object[]{new Integer(d.array[d.QSort_j + 1]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 10: { // Найден второй элемент для обмена
                    comment = QuickSort.this.getComment("QSort.secondElementFound"); 
                    args = new Object[]{new Integer(d.array[d.QSort_j]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 13: { // Сообщение об обмене элементов
                    comment = QuickSort.this.getComment("QSort.elementsSwapComment"); 
                    break;
                }
                case 15: { // Сдвиг указателей
                    comment = QuickSort.this.getComment("QSort.shiftPointers"); 
                    break;
                }
                case 16: { // Элементы не надо менять местами
                    comment = QuickSort.this.getComment("QSort.notElementsSwap"); 
                    args = new Object[]{new Integer(d.array[d.QSort_j]),                                        new Integer(d.array[d.QSort_i])}; 
                    break;
                }
                case 19: { // Рекурсивный вызов левой части
                    comment = QuickSort.this.getComment("QSort.launchLeft"); 
                    args = new Object[]{new Integer(d.QSort_left + 1), new Integer(d.QSort_j + 1)}; 
                    break;
                }
                case 20: { // Сортировка (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 24: { // Левая часть состоит из одного элемента
                    comment = QuickSort.this.getComment("QSort.leftContainsOneElement"); 
                    break;
                }
                case 25: { // Левая часть пуста
                    comment = QuickSort.this.getComment("QSort.leftContainsZeroElements"); 
                    break;
                }
                case 28: { // Рекурсивный вызов для правой части
                    comment = QuickSort.this.getComment("QSort.launchRight"); 
                    args = new Object[]{new Integer(d.QSort_i + 1), new Integer(d.QSort_right + 1)}; 
                    break;
                }
                case 29: { // Сортировка (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 33: { // Правая часть состоит из одного элемента
                    comment = QuickSort.this.getComment("QSort.rightContainsOneElement"); 
                    break;
                }
                case 34: { // Правая часть пуста
                    comment = QuickSort.this.getComment("QSort.rightContainsZeroElements"); 
                    break;
                }
                case 35: { // Достаём из стэка последние границы
                    comment = QuickSort.this.getComment("QSort.popBorders"); 
                    args = new Object[]{new Integer(d.QSort_left + 1), new Integer(d.QSort_right + 1)}; 
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
                case 1: { // Инициализация локальных переменных
                    					d.visualizer.updateArray(d.QSort_left, d.QSort_right);
                    					d.visualizer.updateBar();
                    					d.visualizer.updateBorders();
                    break;
                }
                case 2: { // Выбор барьера
                    				d.visualizer.updateBar();
                    				d.visualizer.updateBorders();
                    break;
                }
                case 3: { // Собощение о разделении
                    				d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 1, 1);
                    				d.visualizer.updateBorders();
                    break;
                }
                case 6: { // Увеличиваем i
                    						d.visualizer.updatePointers(d.QSort_i - 1, d.QSort_j, 1, 1);
                    						d.visualizer.updateBorders();
                    break;
                }
                case 7: { // Найден первый элемент для обмена
                    					d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 2, 1);
                    					d.visualizer.updateBorders();
                    break;
                }
                case 9: { // Уменьшаем j
                    						d.visualizer.updatePointers(d.QSort_i, d.QSort_j + 1, 2, 1);
                    						d.visualizer.updateBorders();
                    break;
                }
                case 10: { // Найден второй элемент для обмена
                    					d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 2, 2);
                    					d.visualizer.updateBorders();
                    break;
                }
                case 15: { // Сдвиг указателей
                    							d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 1, 1);
                    							d.visualizer.updateBorders();
                    break;
                }
                case 16: { // Элементы не надо менять местами
                    							d.visualizer.updatePointers(d.QSort_i, d.QSort_j, 1, 1);
                    							d.visualizer.updateBorders();
                    break;
                }
                case 19: { // Рекурсивный вызов левой части
                    						d.visualizer.updateArray(d.QSort_left, d.QSort_right);
                    						d.visualizer.updateBorders();
                    break;
                }
                case 20: { // Сортировка (автомат)
                    child.drawState(); 
                    break;
                }
                case 24: { // Левая часть состоит из одного элемента
                    								d.visualizer.updateBorders();
                    break;
                }
                case 25: { // Левая часть пуста
                    								d.visualizer.updateBorders();
                    break;
                }
                case 28: { // Рекурсивный вызов для правой части
                    						d.visualizer.updateArray(d.QSort_left, d.QSort_right);
                    						d.visualizer.updateBorders();
                    break;
                }
                case 29: { // Сортировка (автомат)
                    child.drawState(); 
                    break;
                }
                case 33: { // Правая часть состоит из одного элемента
                    								d.visualizer.updateBorders();
                    break;
                }
                case 34: { // Правая часть пуста
                    								d.visualizer.updateBorders();
                    break;
                }
                case 35: { // Достаём из стэка последние границы
                    				d.visualizer.updateBar();
                    				d.visualizer.updateBorders();
                    break;
                }
            }
        }
    }
}
