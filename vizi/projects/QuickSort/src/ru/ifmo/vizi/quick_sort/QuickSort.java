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
        public int[] a = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * Массив левых границ (для отображения).
          */
        public int[] globL = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * Массив правых границ (для отображения).
          */
        public int[] globR = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * Массив правы.
          */
        public int[] globI = new int[]{1, 2, 3, 4, 5, 6, 7 ,8};

        /**
          * Глубина рекурсии.
          */
        public int ptr = 0;

        /**
          * Экземпляр апплета.
          */
        public QuickSortVisualizer visualizer = null;

        /**
          * Барьер.
          */
        public int barrier = 0;

        /**
          * Левая граница (Процедура QSort).
          */
        public int QSort_l;

        /**
          * Правая граница (Процедура QSort).
          */
        public int QSort_r;

        /**
          * Барьер (Процедура QSort).
          */
        public int QSort_m;

        /**
          * Временная переменная (Процедура QSort).
          */
        public int QSort_c;

        /**
          * Переменная цикла (Процедура QSort).
          */
        public int QSort_i;

        /**
          * Переменная цикла (Процедура QSort).
          */
        public int QSort_j;

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
                    storeField(d, "ptr");
                    d.ptr = 0;
                    storeArray(d.globL, d.ptr);
                    d.globL[d.ptr] = 0;
                    storeArray(d.globR, d.ptr);
                    d.globR[d.ptr] = d.a.length - 1;
                    storeField(d, "ptr");
                    d.ptr = d.ptr + 1;
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
                    d.visualizer.updateArrow(-666, -666, false);
                    d.visualizer.updateMainArray(-666, -666, -666, -666, 0, 0, 0, 5);
                    d.visualizer.updateStack(0, 0, 1);
                    break;
                }
                case 2: { // Сортировка (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.updateArrow(-666, -666, false);                
                    d.visualizer.updateMainArray(-666, -666, -666, -666, 3, 0, 0, 5);
                    d.visualizer.updateStack(d.ptr, 0, 1);
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
        private final int END_STATE = 37;

        /**
          * Конструктор.
          */
        public QSort() {
            super( 
                "QSort", 
                0, // Номер начального состояния 
                37, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Восстанавливание переменных после вызова", 
                    "Выбор барьера", 
                    "Собощение о разделении", 
                    "Разделение элементов сортируемой части", 
                    "Поиск элемента большего или равного барьеру", 
                    "Увеличиваем i", 
                    "Элемент 1 для смены найден", 
                    "Поиск элемента меньшего или равного барьеру", 
                    "Уменьшаем j", 
                    "Элемент 2 для смены найден", 
                    "Условие смены мест элементов", 
                    "Условие смены мест элементов (окончание)", 
                    "Смена элементов местами (сообщение)", 
                    "Смена элементов местами", 
                    "Сдвиг указателей", 
                    "Элементы не надо менять местами", 
                    "Условие для рекурсивного вызова для левой части", 
                    "Условие для рекурсивного вызова для левой части (окончание)", 
                    "Рекурсивный вызов для левой части", 
                    "Сортировка (автомат)", 
                    "Восстановление переменных после рекурсии", 
                    "Проверка один элемент в левой части или она пуста", 
                    "Проверка один элемент в левой части или она пуста (окончание)", 
                    "leftNotRec", 
                    "leftNotRec", 
                    "Условие для рекурсивного вызова для правой части", 
                    "Условие для рекурсивного вызова для правой части (окончание)", 
                    "Рекурсивный вызов для правой части", 
                    "Сортировка (автомат)", 
                    "Восстановление переменных после рекурсии", 
                    "Проверка один элемент в левой части или она пуста", 
                    "Проверка один элемент в левой части или она пуста (окончание)", 
                    "rightNotRecOne", 
                    "rightNotRecEmpty", 
                    "Вытаскиваем из стэка последние границы", 
                    "Восстановление уровня рекурсии", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    1, // Восстанавливание переменных после вызова 
                    1, // Выбор барьера 
                    1, // Собощение о разделении 
                    -1, // Разделение элементов сортируемой части 
                    -1, // Поиск элемента большего или равного барьеру 
                    0, // Увеличиваем i 
                    0, // Элемент 1 для смены найден 
                    -1, // Поиск элемента меньшего или равного барьеру 
                    0, // Уменьшаем j 
                    0, // Элемент 2 для смены найден 
                    -1, // Условие смены мест элементов 
                    -1, // Условие смены мест элементов (окончание) 
                    0, // Смена элементов местами (сообщение) 
                    -1, // Смена элементов местами 
                    0, // Сдвиг указателей 
                    0, // Элементы не надо менять местами 
                    -1, // Условие для рекурсивного вызова для левой части 
                    -1, // Условие для рекурсивного вызова для левой части (окончание) 
                    1, // Рекурсивный вызов для левой части 
                    CALL_AUTO_LEVEL, // Сортировка (автомат) 
                    -1, // Восстановление переменных после рекурсии 
                    -1, // Проверка один элемент в левой части или она пуста 
                    -1, // Проверка один элемент в левой части или она пуста (окончание) 
                    1, // leftNotRec 
                    1, // leftNotRec 
                    -1, // Условие для рекурсивного вызова для правой части 
                    -1, // Условие для рекурсивного вызова для правой части (окончание) 
                    1, // Рекурсивный вызов для правой части 
                    CALL_AUTO_LEVEL, // Сортировка (автомат) 
                    -1, // Восстановление переменных после рекурсии 
                    -1, // Проверка один элемент в левой части или она пуста 
                    -1, // Проверка один элемент в левой части или она пуста (окончание) 
                    1, // rightNotRecOne 
                    1, // rightNotRecEmpty 
                    0, // Вытаскиваем из стэка последние границы 
                    -1, // Восстановление уровня рекурсии 
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
                    state = 1; // Восстанавливание переменных после вызова
                    break;
                }
                case 1: { // Восстанавливание переменных после вызова
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
                    if (d.a[d.QSort_i] < d.QSort_m) {
                        state = 6; // Увеличиваем i
                    } else {
                        state = 7; // Элемент 1 для смены найден
                    }
                    break;
                }
                case 6: { // Увеличиваем i
                    stack.pushBoolean(true); 
                    state = 5; // Поиск элемента большего или равного барьеру
                    break;
                }
                case 7: { // Элемент 1 для смены найден
                    stack.pushBoolean(false); 
                    state = 8; // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 8: { // Поиск элемента меньшего или равного барьеру
                    if (d.a[d.QSort_j] > d.QSort_m) {
                        state = 9; // Уменьшаем j
                    } else {
                        state = 10; // Элемент 2 для смены найден
                    }
                    break;
                }
                case 9: { // Уменьшаем j
                    stack.pushBoolean(true); 
                    state = 8; // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 10: { // Элемент 2 для смены найден
                    state = 11; // Условие смены мест элементов
                    break;
                }
                case 11: { // Условие смены мест элементов
                    if (d.QSort_i <= d.QSort_j) {
                        state = 13; // Смена элементов местами (сообщение)
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
                case 13: { // Смена элементов местами (сообщение)
                    state = 14; // Смена элементов местами
                    break;
                }
                case 14: { // Смена элементов местами
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
                    if (d.QSort_l < d.QSort_j) {
                        state = 19; // Рекурсивный вызов для левой части
                    } else {
                        state = 22; // Проверка один элемент в левой части или она пуста
                    }
                    break;
                }
                case 18: { // Условие для рекурсивного вызова для левой части (окончание)
                    state = 26; // Условие для рекурсивного вызова для правой части
                    break;
                }
                case 19: { // Рекурсивный вызов для левой части
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
                    if (d.QSort_l == d.QSort_j) {
                        state = 24; // leftNotRec
                    } else {
                        state = 25; // leftNotRec
                    }
                    break;
                }
                case 23: { // Проверка один элемент в левой части или она пуста (окончание)
                    stack.pushBoolean(false); 
                    state = 18; // Условие для рекурсивного вызова для левой части (окончание)
                    break;
                }
                case 24: { // leftNotRec
                    stack.pushBoolean(true); 
                    state = 23; // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 25: { // leftNotRec
                    stack.pushBoolean(false); 
                    state = 23; // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 26: { // Условие для рекурсивного вызова для правой части
                    if (d.QSort_i < d.QSort_r) {
                        state = 28; // Рекурсивный вызов для правой части
                    } else {
                        state = 31; // Проверка один элемент в левой части или она пуста
                    }
                    break;
                }
                case 27: { // Условие для рекурсивного вызова для правой части (окончание)
                    state = 35; // Вытаскиваем из стэка последние границы
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
                case 31: { // Проверка один элемент в левой части или она пуста
                    if (d.QSort_r == d.QSort_i) {
                        state = 33; // rightNotRecOne
                    } else {
                        state = 34; // rightNotRecEmpty
                    }
                    break;
                }
                case 32: { // Проверка один элемент в левой части или она пуста (окончание)
                    stack.pushBoolean(false); 
                    state = 27; // Условие для рекурсивного вызова для правой части (окончание)
                    break;
                }
                case 33: { // rightNotRecOne
                    stack.pushBoolean(true); 
                    state = 32; // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 34: { // rightNotRecEmpty
                    stack.pushBoolean(false); 
                    state = 32; // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 35: { // Вытаскиваем из стэка последние границы
                    state = 36; // Восстановление уровня рекурсии
                    break;
                }
                case 36: { // Восстановление уровня рекурсии
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Восстанавливание переменных после вызова
                    startSection();
                    storeField(d, "QSort_l");
                    d.QSort_l = d.globL[d.ptr - 1];
                    storeField(d, "QSort_i");
                    d.QSort_i = d.QSort_l;
                    storeField(d, "QSort_r");
                    d.QSort_r = d.globR[d.ptr - 1];
                    storeField(d, "QSort_j");
                    d.QSort_j = d.QSort_r;
                    break;
                }
                case 2: { // Выбор барьера
                    startSection();
                    storeField(d, "QSort_m");
                    d.QSort_m = d.a[d.QSort_l];
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
                case 7: { // Элемент 1 для смены найден
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
                case 10: { // Элемент 2 для смены найден
                    startSection();
                    break;
                }
                case 11: { // Условие смены мест элементов
                    break;
                }
                case 12: { // Условие смены мест элементов (окончание)
                    break;
                }
                case 13: { // Смена элементов местами (сообщение)
                    startSection();
                    break;
                }
                case 14: { // Смена элементов местами
                    startSection();
                    storeField(d, "QSort_c");
                    d.QSort_c = d.a[d.QSort_i];
                    storeArray(d.a, d.QSort_i);
                    d.a[d.QSort_i] = d.a[d.QSort_j];
                    storeArray(d.a, d.QSort_j);
                    d.a[d.QSort_j] = d.QSort_c;
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
                case 19: { // Рекурсивный вызов для левой части
                    startSection();
                    storeArray(d.globL, d.ptr);
                    d.globL[d.ptr] = d.QSort_l;
                    storeArray(d.globR, d.ptr);
                    d.globR[d.ptr] = d.QSort_j;
                    storeArray(d.globI, d.ptr - 1);
                    d.globI[d.ptr - 1] = d.QSort_i;
                    storeField(d, "ptr");
                    d.ptr = d.ptr + 1;
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
                    storeField(d, "QSort_l");
                    d.QSort_l = d.globL[d.ptr - 1];
                    storeField(d, "QSort_r");
                    d.QSort_r = d.globR[d.ptr - 1];
                    storeField(d, "QSort_i");
                    d.QSort_i = d.globI[d.ptr - 1];
                    break;
                }
                case 22: { // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 23: { // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 24: { // leftNotRec
                    startSection();
                    break;
                }
                case 25: { // leftNotRec
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
                    storeArray(d.globL, d.ptr);
                    d.globL[d.ptr] = d.QSort_i;
                    storeArray(d.globR, d.ptr);
                    d.globR[d.ptr] = d.QSort_r;
                    storeField(d, "ptr");
                    d.ptr = d.ptr + 1;
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
                    storeField(d, "QSort_l");
                    d.QSort_l = d.globL[d.ptr - 1];
                    storeField(d, "QSort_r");
                    d.QSort_r = d.globR[d.ptr - 1];
                    break;
                }
                case 31: { // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 32: { // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 33: { // rightNotRecOne
                    startSection();
                    break;
                }
                case 34: { // rightNotRecEmpty
                    startSection();
                    break;
                }
                case 35: { // Вытаскиваем из стэка последние границы
                    startSection();
                    break;
                }
                case 36: { // Восстановление уровня рекурсии
                    startSection();
                    storeField(d, "ptr");
                    d.ptr = d.ptr - 1;
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
                case 1: { // Восстанавливание переменных после вызова
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
                case 7: { // Элемент 1 для смены найден
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
                case 10: { // Элемент 2 для смены найден
                    restoreSection();
                    break;
                }
                case 11: { // Условие смены мест элементов
                    break;
                }
                case 12: { // Условие смены мест элементов (окончание)
                    break;
                }
                case 13: { // Смена элементов местами (сообщение)
                    restoreSection();
                    break;
                }
                case 14: { // Смена элементов местами
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
                case 19: { // Рекурсивный вызов для левой части
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
                case 24: { // leftNotRec
                    restoreSection();
                    break;
                }
                case 25: { // leftNotRec
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
                case 31: { // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 32: { // Проверка один элемент в левой части или она пуста (окончание)
                    break;
                }
                case 33: { // rightNotRecOne
                    restoreSection();
                    break;
                }
                case 34: { // rightNotRecEmpty
                    restoreSection();
                    break;
                }
                case 35: { // Вытаскиваем из стэка последние границы
                    restoreSection();
                    break;
                }
                case 36: { // Восстановление уровня рекурсии
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Восстанавливание переменных после вызова
                    state = START_STATE; 
                    break;
                }
                case 2: { // Выбор барьера
                    state = 1; // Восстанавливание переменных после вызова
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
                case 7: { // Элемент 1 для смены найден
                    state = 5; // Поиск элемента большего или равного барьеру
                    break;
                }
                case 8: { // Поиск элемента меньшего или равного барьеру
                    if (stack.popBoolean()) {
                        state = 9; // Уменьшаем j
                    } else {
                        state = 7; // Элемент 1 для смены найден
                    }
                    break;
                }
                case 9: { // Уменьшаем j
                    state = 8; // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 10: { // Элемент 2 для смены найден
                    state = 8; // Поиск элемента меньшего или равного барьеру
                    break;
                }
                case 11: { // Условие смены мест элементов
                    state = 10; // Элемент 2 для смены найден
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
                case 13: { // Смена элементов местами (сообщение)
                    state = 11; // Условие смены мест элементов
                    break;
                }
                case 14: { // Смена элементов местами
                    state = 13; // Смена элементов местами (сообщение)
                    break;
                }
                case 15: { // Сдвиг указателей
                    state = 14; // Смена элементов местами
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
                case 19: { // Рекурсивный вызов для левой части
                    state = 17; // Условие для рекурсивного вызова для левой части
                    break;
                }
                case 20: { // Сортировка (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // Рекурсивный вызов для левой части
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
                        state = 24; // leftNotRec
                    } else {
                        state = 25; // leftNotRec
                    }
                    break;
                }
                case 24: { // leftNotRec
                    state = 22; // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 25: { // leftNotRec
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
                        state = 32; // Проверка один элемент в левой части или она пуста (окончание)
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
                case 31: { // Проверка один элемент в левой части или она пуста
                    state = 26; // Условие для рекурсивного вызова для правой части
                    break;
                }
                case 32: { // Проверка один элемент в левой части или она пуста (окончание)
                    if (stack.popBoolean()) {
                        state = 33; // rightNotRecOne
                    } else {
                        state = 34; // rightNotRecEmpty
                    }
                    break;
                }
                case 33: { // rightNotRecOne
                    state = 31; // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 34: { // rightNotRecEmpty
                    state = 31; // Проверка один элемент в левой части или она пуста
                    break;
                }
                case 35: { // Вытаскиваем из стэка последние границы
                    state = 27; // Условие для рекурсивного вызова для правой части (окончание)
                    break;
                }
                case 36: { // Восстановление уровня рекурсии
                    state = 35; // Вытаскиваем из стэка последние границы
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 36; // Восстановление уровня рекурсии
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
                case 1: { // Восстанавливание переменных после вызова
                    comment = QuickSort.this.getComment("QSort.SetVars"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_r + 1)}; 
                    break;
                }
                case 2: { // Выбор барьера
                    comment = QuickSort.this.getComment("QSort.ChBarrier"); 
                    break;
                }
                case 3: { // Собощение о разделении
                    comment = QuickSort.this.getComment("QSort.partitionMessage"); 
                    break;
                }
                case 6: { // Увеличиваем i
                    comment = QuickSort.this.getComment("QSort.iInc"); 
                    args = new Object[]{new Integer(d.a[d.QSort_i - 1]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 7: { // Элемент 1 для смены найден
                    comment = QuickSort.this.getComment("QSort.element1found"); 
                    args = new Object[]{new Integer(d.a[d.QSort_i]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 9: { // Уменьшаем j
                    comment = QuickSort.this.getComment("QSort.jDec"); 
                    args = new Object[]{new Integer(d.a[d.QSort_j + 1]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 10: { // Элемент 2 для смены найден
                    comment = QuickSort.this.getComment("QSort.element2found"); 
                    args = new Object[]{new Integer(d.a[d.QSort_j]), new Integer(d.QSort_m)}; 
                    break;
                }
                case 13: { // Смена элементов местами (сообщение)
                    comment = QuickSort.this.getComment("QSort.elementsSwapComment"); 
                    break;
                }
                case 15: { // Сдвиг указателей
                    comment = QuickSort.this.getComment("QSort.elementsNext"); 
                    break;
                }
                case 16: { // Элементы не надо менять местами
                    comment = QuickSort.this.getComment("QSort.notElementsSwap"); 
                    args = new Object[]{new Integer(d.a[d.QSort_j]),                                        new Integer(d.a[d.QSort_i])}; 
                    break;
                }
                case 19: { // Рекурсивный вызов для левой части
                    comment = QuickSort.this.getComment("QSort.leftRec"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_j + 1)}; 
                    break;
                }
                case 20: { // Сортировка (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 24: { // leftNotRec
                    comment = QuickSort.this.getComment("QSort.leftNotRecOne"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_j + 1)}; 
                    break;
                }
                case 25: { // leftNotRec
                    comment = QuickSort.this.getComment("QSort.leftNotRecNone"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_j + 1)}; 
                    break;
                }
                case 28: { // Рекурсивный вызов для правой части
                    comment = QuickSort.this.getComment("QSort.rightRec"); 
                    args = new Object[]{new Integer(d.QSort_i + 1), new Integer(d.QSort_r + 1)}; 
                    break;
                }
                case 29: { // Сортировка (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 33: { // rightNotRecOne
                    comment = QuickSort.this.getComment("QSort.rightNotRecOne"); 
                    args = new Object[]{new Integer(d.QSort_i + 1), new Integer(d.QSort_r + 1)}; 
                    break;
                }
                case 34: { // rightNotRecEmpty
                    comment = QuickSort.this.getComment("QSort.rightNotRecEmpty"); 
                    args = new Object[]{new Integer(d.QSort_i + 1), new Integer(d.QSort_r + 1)}; 
                    break;
                }
                case 35: { // Вытаскиваем из стэка последние границы
                    comment = QuickSort.this.getComment("QSort.popFromStack"); 
                    args = new Object[]{new Integer(d.QSort_l + 1), new Integer(d.QSort_r + 1)}; 
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
                case 1: { // Восстанавливание переменных после вызова
                    				d.visualizer.updateArrow(-666, -666, false);
                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, -666, 0, 1, 0, 5);
                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 2: { // Выбор барьера
                    				d.visualizer.updateArrow(-666, -666, false);
                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_l, 0, 1, 2, 5);
                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 3: { // Собощение о разделении
                    				d.visualizer.updateArrow(-666, -666, false);
                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_l, 0, 1, 2, 5);
                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 6: { // Увеличиваем i
                    						d.visualizer.updateArrow(-666, -666, false);
                                            d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_j, d.QSort_i - 1,
                                                                         0, 1, 2, 5);
                                            d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 7: { // Элемент 1 для смены найден
                    					d.visualizer.updateArrow(-666, -666, false);
                                        d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_j, d.QSort_i,
                                                                     0, 1, 2, 5);
                                        d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 9: { // Уменьшаем j
                    						d.visualizer.updateArrow(-666, -666, false);
                                            d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j + 1,
                                                                         0, 1, 2, 5);
                                            d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 10: { // Элемент 2 для смены найден
                    					d.visualizer.updateArrow(-666, -666, false);
                                        d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j,
                                                                     0, 1, 2, 5);
                                        d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 13: { // Смена элементов местами (сообщение)
                    d.visualizer.updateArrow(d.QSort_i + 1, d.QSort_j + 1, true);
                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j,
                                                 0, 1, 5, 5);
                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 15: { // Сдвиг указателей
                    d.visualizer.updateArrow(-666, -666, false);
                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j,
                                                 0, 1, 5, 5);
                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 16: { // Элементы не надо менять местами
                    d.visualizer.updateArrow(-666, -666, false);
                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, d.QSort_i, d.QSort_j,
                                                 0, 1, 5, 5);
                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 19: { // Рекурсивный вызов для левой части
                    						d.visualizer.updateArrow(-666, -666, false);
                                            d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_j, 0, 1, 2, 5);
                                            d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 20: { // Сортировка (автомат)
                    child.drawState(); 
                    break;
                }
                case 24: { // leftNotRec
                    								d.visualizer.updateArrow(-666, -666, false);
                                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_j, 
                                                                                 0, 1, 2, 5);
                                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 25: { // leftNotRec
                    								d.visualizer.updateArrow(-666, -666, false);
                                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_j, 
                                                                                 0, 1, 2, 5);
                                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 28: { // Рекурсивный вызов для правой части
                    						d.visualizer.updateArrow(-666, -666, false);
                                            d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_i, 0, 1, 2, 5);
                                            d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 29: { // Сортировка (автомат)
                    child.drawState(); 
                    break;
                }
                case 33: { // rightNotRecOne
                    								d.visualizer.updateArrow(-666, -666, false);
                                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_i,
                                                                                 0, 1, 2, 5);
                                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 34: { // rightNotRecEmpty
                    								d.visualizer.updateArrow(-666, -666, false);
                                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, d.QSort_i,
                                                                                 0, 1, 2, 5);
                                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
                case 35: { // Вытаскиваем из стэка последние границы
                    				d.visualizer.updateArrow(-666, -666, false);
                                    d.visualizer.updateMainArray(d.QSort_l, d.QSort_r, -666, -666, 0, 1, 2, 5);
                                    d.visualizer.updateStack(d.ptr, 0, 1);
                    break;
                }
            }
        }
    }
}
