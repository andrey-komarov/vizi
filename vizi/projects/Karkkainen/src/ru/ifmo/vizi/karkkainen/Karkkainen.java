package ru.ifmo.vizi.karkkainen;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class Karkkainen extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public Karkkainen(Locale locale) {
        super("ru.ifmo.vizi.karkkainen.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экземпляр апплета.
          */
        public KarkkainenVisualizer visualizer = null;

        /**
          * Начальная строка.
          */
        public char[] s = new char[]{'a'};

        /**
          * Стиль начальной строки.
          */
        public int[] sSt = new int[0];

        /**
          * Стиль индексов начальной строки.
          */
        public int[] sNumSt = new int[3];

        /**
          * Рекурсивно построенные строки.
          */
        public int[][] recStr = new int[0][0];

        /**
          * Стиль рекурсивно построенных строк.
          */
        public int[][] recStrSt = new int[0][0];

        /**
          * Стиль индексов рекурсивно построенных строк.
          */
        public int[][] recStrNumSt = new int[0][0];

        /**
          * Стиль троек.
          */
        public int[][][] tripplesSt = new int[0][0][0];

        /**
          * Номера троек.
          */
        public int[][][] tripplesNum = new int[0][0][0];

        /**
          * Стиль номеров троек.
          */
        public int[][][] tripplesNumSt = new int[0][0][0];

        /**
          * Recursive built array 1 style.
          */
        public int[][] recS1St = new int[0][0];

        /**
          * Суффиксные массивы.
          */
        public int[][] suffArr = new int[0][0];

        /**
          * Стиль суффиксных массивов.
          */
        public int[][] suffArrSt = new int[0][0];

        /**
          * Второй стиль суффиксного массива.
          */
        public int[][] suffArrSt2 = new int[0][0];

        /**
          * Стиль суффиксного массива для последней строки.
          */
        public int[][] lastSuffArrSt = new int[0][0];

        /**
          * Обратная к суффиксному масиву перестановка.
          */
        public int[][][] invSuffArr = new int[0][0][0];

        /**
          * Стиль обратной к суффиксному массиву перестановки.
          */
        public int[][][] invSuffArrSt = new int[0][0][0];

        /**
          * Пары.
          */
        public int[][][] pairs = new int[0][0][0];

        /**
          * Стиль пар.
          */
        public int[][][] pairsSt = new int[0][0][0];

        /**
          * Номера пар.
          */
        public int[][][] pairsNum = new int[0][0][0];

        /**
          * Стиль номеров пар.
          */
        public int[][][] pairsNumSt = new int[0][0][0];

        /**
          * Стиль индексов обратной к суффиксному массиву перестановки.
          */
        public int[][][] invSuffArrNumSt = new int[0][0][0];

        /**
          * Номера элементов типа 2 и 3 в строке.
          */
        public int[][][] trp23Num = new int[0][0][0];

        /**
          * Стиль номеров элементов типа 2 и 3.
          */
        public int[][][] trp23NumSt = new int[0][0][0];

        /**
          * Отсортированный список суффиксов типа 2 и 3.
          */
        public int[][][] suff23List = new int[0][0][0];

        /**
          * Стиль отсортированного списка суффиксов типа 2 и 3.
          */
        public int[][][] suff23ListSt = new int[0][0][0];

        /**
          * Второй стиль отсортированного списка суффиксов типа 2 и 3.
          */
        public int[][][] suff23ListSt2 = new int[0][0][0];

        /**
          * Стиль индексов отсортированного списка суффиксов типа 2 и 3.
          */
        public int[][][] suff23ListNumSt = new int[0][0][0];

        /**
          * Отсортированный список суффиксов типа 0.
          */
        public int[][][] suff0List = new int[0][0][0];

        /**
          * Стиль отсортированного списка суффиксов типа 0.
          */
        public int[][][] suff0ListSt = new int[0][0][0];

        /**
          * Клон стиля отсортированного списка суффиксов типа 0.
          */
        public int[][][] suff0ListSt2 = new int[0][0][0];

        /**
          * Стиль индексов отсортированного списка суффиксов типа 0.
          */
        public int[][][] suff0ListNumSt = new int[0][0][0];

        /**
          * Номера суффиксов типа 2 и 3.
          */
        public int[][] suff23Num = new int[0][0];

        /**
          * Переменная цикла - глубина рекурсии (Процедура Main).
          */
        public int Main_i;

        /**
          * Переменная цикла (Процедура Main).
          */
        public int Main_j;

        /**
          * Переменная цикла - указатель в левом суффиксном массиве (Процедура Main).
          */
        public int Main_l;

        /**
          * Переменная цикла - указатель в правом суффиксном массиве (Процедура Main).
          */
        public int Main_r;

        /**
          * Временная переменная (Процедура Main).
          */
        public int Main_tl;

        /**
          * Временная переменная (Процедура Main).
          */
        public int Main_tr;

        /**
          * Переменная цикла - глубина, на которую мы ушли при сравнении суффиксов (Процедура Main).
          */
        public int Main_sh;

        /**
          * Переменная цикла (Процедура Main).
          */
        public boolean Main_goOn;

        public String toString() {
            StringBuffer s = new StringBuffer();
            return s.toString();
        }
    }

    /**
      * Строит суффиксный массив.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 59;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                59, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Перенумерация", 
                    "Главный цикл рекурсии", 
                    "Текущая строка", 
                    "Проверка на то, что все символы различны", 
                    "Проверка на то, что все символы различны (окончание)", 
                    "Дополнение нулем", 
                    "Дополнение нулем (окончание)", 
                    "Комментарий", 
                    "Цкл", 
                    "Построение троек", 
                    "Сортировка троек", 
                    "Цикл по тройкам типа 1", 
                    "Выписываем номера троек типа 1", 
                    "Цикл по тройкам типа 2", 
                    "Выписываем номера троек типа 2", 
                    "Комментарий", 
                    "Цикл", 
                    "Непосредственное построение суффиксного массива", 
                    "Цикл", 
                    "Начало шага рекурсии", 
                    "???", 
                    "Комментарий", 
                    "Цикл построения обратной перестановки", 
                    "???", 
                    "Комментарий", 
                    "Цикл", 
                    "Шаг цикла", 
                    "Скрываем вспомогательные массивы", 
                    "Комментарий", 
                    "Цикл построения пар", 
                    "Выписываем очередную пару", 
                    "Сортировка пар", 
                    "Комментарий", 
                    "Цикл", 
                    "???", 
                    "???", 
                    "???", 
                    "??? (окончание)", 
                    "???", 
                    "Цикл", 
                    "???", 
                    "Цикл", 
                    "???", 
                    "Сравнение символов", 
                    "Сравнение символов (окончание)", 
                    "Проверка на принадлежность к типам 1-2", 
                    "Проверка на принадлежность к типам 1-2 (окончание)", 
                    "Проверка, не кончилавсь ли строка", 
                    "Проверка, не кончилавсь ли строка (окончание)", 
                    "???", 
                    "Сравнение номеров суффиксов", 
                    "Сравнение номеров суффиксов (окончание)", 
                    "???", 
                    "???", 
                    "Сравнение символов", 
                    "Дописываем остаток", 
                    "Дописываем остаток", 
                    "Сокрытие ненужных массивов", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    0, // Перенумерация 
                    -1, // Главный цикл рекурсии 
                    0, // Текущая строка 
                    0, // Проверка на то, что все символы различны 
                    -1, // Проверка на то, что все символы различны (окончание) 
                    0, // Дополнение нулем 
                    -1, // Дополнение нулем (окончание) 
                    0, // Комментарий 
                    -2, // Цкл 
                    0, // Построение троек 
                    0, // Сортировка троек 
                    -2, // Цикл по тройкам типа 1 
                    0, // Выписываем номера троек типа 1 
                    -2, // Цикл по тройкам типа 2 
                    0, // Выписываем номера троек типа 2 
                    0, // Комментарий 
                    -1, // Цикл 
                    0, // Непосредственное построение суффиксного массива 
                    -1, // Цикл 
                    0, // Начало шага рекурсии 
                    0, // ??? 
                    0, // Комментарий 
                    -1, // Цикл построения обратной перестановки 
                    0, // ??? 
                    0, // Комментарий 
                    -1, // Цикл 
                    0, // Шаг цикла 
                    0, // Скрываем вспомогательные массивы 
                    0, // Комментарий 
                    -1, // Цикл построения пар 
                    0, // Выписываем очередную пару 
                    0, // Сортировка пар 
                    0, // Комментарий 
                    -1, // Цикл 
                    0, // ??? 
                    0, // ??? 
                    0, // ??? 
                    -1, // ??? (окончание) 
                    0, // ??? 
                    -1, // Цикл 
                    0, // ??? 
                    -2, // Цикл 
                    0, // ??? 
                    0, // Сравнение символов 
                    -1, // Сравнение символов (окончание) 
                    0, // Проверка на принадлежность к типам 1-2 
                    -1, // Проверка на принадлежность к типам 1-2 (окончание) 
                    0, // Проверка, не кончилавсь ли строка 
                    -1, // Проверка, не кончилавсь ли строка (окончание) 
                    0, // ??? 
                    0, // Сравнение номеров суффиксов 
                    -1, // Сравнение номеров суффиксов (окончание) 
                    0, // ??? 
                    0, // ??? 
                    0, // Сравнение символов 
                    -1, // Дописываем остаток 
                    0, // Дописываем остаток 
                    0, // Сокрытие ненужных массивов 
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
                    state = 1; // Перенумерация
                    break;
                }
                case 1: { // Перенумерация
                    stack.pushBoolean(false); 
                    state = 2; // Главный цикл рекурсии
                    break;
                }
                case 2: { // Главный цикл рекурсии
                    if (d.Main_i < d.recStr.length) {
                        state = 3; // Текущая строка
                    } else {
                        stack.pushBoolean(false); 
                        state = 17; // Цикл
                    }
                    break;
                }
                case 3: { // Текущая строка
                    state = 4; // Проверка на то, что все символы различны
                    break;
                }
                case 4: { // Проверка на то, что все символы различны
                    if (d.Main_i < d.recStr.length) {
                        state = 6; // Дополнение нулем
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // Проверка на то, что все символы различны (окончание)
                    }
                    break;
                }
                case 5: { // Проверка на то, что все символы различны (окончание)
                    stack.pushBoolean(true); 
                    state = 2; // Главный цикл рекурсии
                    break;
                }
                case 6: { // Дополнение нулем
                    if (d.recStr[d.Main_i - 1][d.recStr[d.Main_i - 1].length - 1] == 0) {
                        state = 8; // Комментарий
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // Дополнение нулем (окончание)
                    }
                    break;
                }
                case 7: { // Дополнение нулем (окончание)
                    stack.pushBoolean(false); 
                    state = 9; // Цкл
                    break;
                }
                case 8: { // Комментарий
                    stack.pushBoolean(true); 
                    state = 7; // Дополнение нулем (окончание)
                    break;
                }
                case 9: { // Цкл
                    if (d.Main_j < d.tripplesSt[d.Main_i - 1].length) {
                        state = 10; // Построение троек
                    } else {
                        state = 11; // Сортировка троек
                    }
                    break;
                }
                case 10: { // Построение троек
                    stack.pushBoolean(true); 
                    state = 9; // Цкл
                    break;
                }
                case 11: { // Сортировка троек
                    stack.pushBoolean(false); 
                    state = 12; // Цикл по тройкам типа 1
                    break;
                }
                case 12: { // Цикл по тройкам типа 1
                    if (d.Main_j < (d.tripplesNumSt[d.Main_i - 1].length + 1) / 2) {
                        state = 13; // Выписываем номера троек типа 1
                    } else {
                        stack.pushBoolean(false); 
                        state = 14; // Цикл по тройкам типа 2
                    }
                    break;
                }
                case 13: { // Выписываем номера троек типа 1
                    stack.pushBoolean(true); 
                    state = 12; // Цикл по тройкам типа 1
                    break;
                }
                case 14: { // Цикл по тройкам типа 2
                    if (d.Main_j < d.tripplesNumSt[d.Main_i - 1].length) {
                        state = 15; // Выписываем номера троек типа 2
                    } else {
                        state = 16; // Комментарий
                    }
                    break;
                }
                case 15: { // Выписываем номера троек типа 2
                    stack.pushBoolean(true); 
                    state = 14; // Цикл по тройкам типа 2
                    break;
                }
                case 16: { // Комментарий
                    stack.pushBoolean(true); 
                    state = 5; // Проверка на то, что все символы различны (окончание)
                    break;
                }
                case 17: { // Цикл
                    if (d.Main_j < d.lastSuffArrSt.length) {
                        state = 18; // Непосредственное построение суффиксного массива
                    } else {
                        stack.pushBoolean(false); 
                        state = 19; // Цикл
                    }
                    break;
                }
                case 18: { // Непосредственное построение суффиксного массива
                    stack.pushBoolean(true); 
                    state = 17; // Цикл
                    break;
                }
                case 19: { // Цикл
                    if (d.Main_i > 1) {
                        state = 20; // Начало шага рекурсии
                    } else {
                        state = 58; // Сокрытие ненужных массивов
                    }
                    break;
                }
                case 20: { // Начало шага рекурсии
                    state = 21; // ???
                    break;
                }
                case 21: { // ???
                    state = 22; // Комментарий
                    break;
                }
                case 22: { // Комментарий
                    stack.pushBoolean(false); 
                    state = 23; // Цикл построения обратной перестановки
                    break;
                }
                case 23: { // Цикл построения обратной перестановки
                    if (d.Main_j < d.invSuffArr[d.Main_i].length) {
                        state = 24; // ???
                    } else {
                        state = 25; // Комментарий
                    }
                    break;
                }
                case 24: { // ???
                    stack.pushBoolean(true); 
                    state = 23; // Цикл построения обратной перестановки
                    break;
                }
                case 25: { // Комментарий
                    stack.pushBoolean(false); 
                    state = 26; // Цикл
                    break;
                }
                case 26: { // Цикл
                    if (d.Main_j < d.trp23Num[d.Main_i].length) {
                        state = 27; // Шаг цикла
                    } else {
                        state = 28; // Скрываем вспомогательные массивы
                    }
                    break;
                }
                case 27: { // Шаг цикла
                    stack.pushBoolean(true); 
                    state = 26; // Цикл
                    break;
                }
                case 28: { // Скрываем вспомогательные массивы
                    state = 29; // Комментарий
                    break;
                }
                case 29: { // Комментарий
                    stack.pushBoolean(false); 
                    state = 30; // Цикл построения пар
                    break;
                }
                case 30: { // Цикл построения пар
                    if (d.Main_j < d.pairs[d.Main_i].length) {
                        state = 31; // Выписываем очередную пару
                    } else {
                        state = 32; // Сортировка пар
                    }
                    break;
                }
                case 31: { // Выписываем очередную пару
                    stack.pushBoolean(true); 
                    state = 30; // Цикл построения пар
                    break;
                }
                case 32: { // Сортировка пар
                    state = 33; // Комментарий
                    break;
                }
                case 33: { // Комментарий
                    stack.pushBoolean(false); 
                    state = 34; // Цикл
                    break;
                }
                case 34: { // Цикл
                    if (d.Main_j < d.suff0List[d.Main_i].length) {
                        state = 35; // ???
                    } else {
                        state = 36; // ???
                    }
                    break;
                }
                case 35: { // ???
                    stack.pushBoolean(true); 
                    state = 34; // Цикл
                    break;
                }
                case 36: { // ???
                    state = 37; // ???
                    break;
                }
                case 37: { // ???
                    if (d.recStr[d.Main_i - 1][d.recStr[d.Main_i - 1].length - 1] == 0) {
                        state = 39; // ???
                    } else {
                        stack.pushBoolean(false); 
                        state = 38; // ??? (окончание)
                    }
                    break;
                }
                case 38: { // ??? (окончание)
                    stack.pushBoolean(false); 
                    state = 40; // Цикл
                    break;
                }
                case 39: { // ???
                    stack.pushBoolean(true); 
                    state = 38; // ??? (окончание)
                    break;
                }
                case 40: { // Цикл
                    if (d.Main_l < d.suff0List[d.Main_i].length && d.Main_r < d.suff23List[d.Main_i].length) {
                        state = 41; // ???
                    } else {
                        stack.pushBoolean(false); 
                        state = 56; // Дописываем остаток
                    }
                    break;
                }
                case 41: { // ???
                    stack.pushBoolean(false); 
                    state = 42; // Цикл
                    break;
                }
                case 42: { // Цикл
                    if (d.Main_goOn == true) {
                        state = 43; // ???
                    } else {
                        stack.pushBoolean(true); 
                        state = 40; // Цикл
                    }
                    break;
                }
                case 43: { // ???
                    state = 44; // Сравнение символов
                    break;
                }
                case 44: { // Сравнение символов
                    if (d.recStr[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] == d.recStr[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        state = 46; // Проверка на принадлежность к типам 1-2
                    } else {
                        state = 55; // Сравнение символов
                    }
                    break;
                }
                case 45: { // Сравнение символов (окончание)
                    stack.pushBoolean(true); 
                    state = 42; // Цикл
                    break;
                }
                case 46: { // Проверка на принадлежность к типам 1-2
                    if ((d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh) % 3 == 0 || (d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh) % 3 == 0) {
                        state = 48; // Проверка, не кончилавсь ли строка
                    } else {
                        state = 51; // Сравнение номеров суффиксов
                    }
                    break;
                }
                case 47: { // Проверка на принадлежность к типам 1-2 (окончание)
                    stack.pushBoolean(true); 
                    state = 45; // Сравнение символов (окончание)
                    break;
                }
                case 48: { // Проверка, не кончилавсь ли строка
                    if (d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh + 1 == d.recStr[d.Main_i - 1].length || d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh + 1 == d.recStr[d.Main_i - 1].length) {
                        state = 50; // ???
                    } else {
                        stack.pushBoolean(false); 
                        state = 49; // Проверка, не кончилавсь ли строка (окончание)
                    }
                    break;
                }
                case 49: { // Проверка, не кончилавсь ли строка (окончание)
                    stack.pushBoolean(true); 
                    state = 47; // Проверка на принадлежность к типам 1-2 (окончание)
                    break;
                }
                case 50: { // ???
                    stack.pushBoolean(true); 
                    state = 49; // Проверка, не кончилавсь ли строка (окончание)
                    break;
                }
                case 51: { // Сравнение номеров суффиксов
                    if (d.suff23Num[d.Main_i][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] < d.suff23Num[d.Main_i][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        state = 53; // ???
                    } else {
                        state = 54; // ???
                    }
                    break;
                }
                case 52: { // Сравнение номеров суффиксов (окончание)
                    stack.pushBoolean(false); 
                    state = 47; // Проверка на принадлежность к типам 1-2 (окончание)
                    break;
                }
                case 53: { // ???
                    stack.pushBoolean(true); 
                    state = 52; // Сравнение номеров суффиксов (окончание)
                    break;
                }
                case 54: { // ???
                    stack.pushBoolean(false); 
                    state = 52; // Сравнение номеров суффиксов (окончание)
                    break;
                }
                case 55: { // Сравнение символов
                    stack.pushBoolean(false); 
                    state = 45; // Сравнение символов (окончание)
                    break;
                }
                case 56: { // Дописываем остаток
                    if (d.Main_l < d.suff0List[d.Main_i].length || d.Main_r < d.suff23List[d.Main_i].length) {
                        state = 57; // Дописываем остаток
                    } else {
                        stack.pushBoolean(true); 
                        state = 19; // Цикл
                    }
                    break;
                }
                case 57: { // Дописываем остаток
                    stack.pushBoolean(true); 
                    state = 56; // Дописываем остаток
                    break;
                }
                case 58: { // Сокрытие ненужных массивов
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Перенумерация
                    startSection();
                     storeField(d, "Main_i");
                     d.Main_i = 0;
                     for (int ti = 0; ti < d.recStrSt[d.Main_i].length; ti++) {
                         storeArray(d.recStrSt[d.Main_i], ti);
                         d.recStrSt[d.Main_i][ti] = 1 + ti % 3;
                         storeArray(d.recStrNumSt[d.Main_i], ti);
                         d.recStrNumSt[d.Main_i][ti] = 1;
                     }
                     
                     if (d.recStr[d.Main_i][d.recStr[d.Main_i].length - 1] == 0) {
                         storeArray(d.recStrSt[d.Main_i], d.recStr[d.Main_i].length - 1);
                         d.recStrSt[d.Main_i][d.recStr[d.Main_i].length - 1] = -1;
                         storeArray(d.recStrNumSt[d.Main_i], d.recStr[d.Main_i].length - 1);
                         d.recStrNumSt[d.Main_i][d.recStr[d.Main_i].length - 1] = -1;
                    }
                    break;
                }
                case 2: { // Главный цикл рекурсии
                    break;
                }
                case 3: { // Текущая строка
                    startSection();
                    if (d.Main_i > 0) {
                        for (int tj = 0; tj < d.tripplesSt[d.Main_i - 1].length; tj++) {
                            for (int tk = 0; tk < d.tripplesSt[d.Main_i - 1][tj].length; tk++) {
                                storeArray(d.tripplesSt[d.Main_i - 1][tj], tk);
                                d.tripplesSt[d.Main_i - 1][tj][tk] = -1;
                            }                            
                        }
                        for (int ti = 0; ti < d.tripplesNumSt[d.Main_i - 1].length; ti++) {
                            for (int tk = 0; tk < d.tripplesNumSt[d.Main_i - 1][ti].length; tk++) {
                                storeArray(d.tripplesNumSt[d.Main_i - 1][ti], tk);
                                d.tripplesNumSt[d.Main_i - 1][ti][tk] = -1;   
                            }
                        }
                        for (int ti = 0; ti < d.recS1St[d.Main_i].length; ti++) {
                            storeArray(d.recS1St[d.Main_i], ti);
                            d.recS1St[d.Main_i][ti] = -1;       
                        }                        
                    }
                    for (int ti = 0; ti < d.recStrSt[d.Main_i].length; ti++) {
                        storeArray(d.recStrSt[d.Main_i], ti);
                        d.recStrSt[d.Main_i][ti] = 1 + ti % 3;    
                        storeArray(d.recStrNumSt[d.Main_i], ti);
                        d.recStrNumSt[d.Main_i][ti] = 1;
                    }                    
                    
                    if (d.recStr[d.Main_i][d.recStr[d.Main_i].length - 1] == 0) {
                        storeArray(d.recStrSt[d.Main_i], d.recStr[d.Main_i].length - 1);
                        d.recStrSt[d.Main_i][d.recStr[d.Main_i].length - 1] = -1;
                        storeArray(d.recStrNumSt[d.Main_i], d.recStr[d.Main_i].length - 1);
                        d.recStrNumSt[d.Main_i][d.recStr[d.Main_i].length - 1] = -1;
                    }
                    storeField(d, "Main_i");
                    d.Main_i = d.Main_i + 1;
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    break;
                }
                case 4: { // Проверка на то, что все символы различны
                    break;
                }
                case 5: { // Проверка на то, что все символы различны (окончание)
                    break;
                }
                case 6: { // Дополнение нулем
                    break;
                }
                case 7: { // Дополнение нулем (окончание)
                    break;
                }
                case 8: { // Комментарий
                    startSection();
                    storeArray(d.recStrSt[d.Main_i - 1], d.recStrSt[d.Main_i - 1].length - 1);
                    d.recStrSt[d.Main_i - 1][d.recStrSt[d.Main_i - 1].length - 1] = 1 + (d.recStrSt[d.Main_i - 1].length - 1) % 3;
                    storeArray(d.recStrNumSt[d.Main_i - 1], d.recStrSt[d.Main_i - 1].length - 1);
                    d.recStrNumSt[d.Main_i - 1][d.recStrSt[d.Main_i - 1].length - 1] = 1;    
                    break;
                }
                case 9: { // Цкл
                    break;
                }
                case 10: { // Построение троек
                    startSection();
                    int col = 2 + (d.Main_j % 2);
                    for (int ti = 0; ti < d.tripplesSt[d.Main_i - 1][d.Main_j].length; ti++) {
                        storeArray(d.tripplesSt[d.Main_i - 1][d.Main_j], ti);
                        d.tripplesSt[d.Main_i - 1][d.Main_j][ti] = col;        
                    }
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    break;
                }
                case 11: { // Сортировка троек
                    startSection();
                    for (int ti = 0; ti < d.tripplesNumSt[d.Main_i - 1].length; ti++) {
                        storeArray(d.tripplesNumSt[d.Main_i - 1][ti], 0);
                        d.tripplesNumSt[d.Main_i - 1][ti][0] = 2 + (ti % 2);
                    }
                    d.Main_j = 0;                                                         
                    break;
                }
                case 12: { // Цикл по тройкам типа 1
                    break;
                }
                case 13: { // Выписываем номера троек типа 1
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.tripplesNumSt[d.Main_i - 1][(d.Main_j - 1) * 2], 0);
                        d.tripplesNumSt[d.Main_i - 1][(d.Main_j - 1) * 2][0] = 2;
                        storeArray(d.recS1St[d.Main_i], d.Main_j - 1);
                        d.recS1St[d.Main_i][d.Main_j - 1] = 2;
                    }
                    storeArray(d.tripplesNumSt[d.Main_i - 1][d.Main_j * 2], 0);
                    d.tripplesNumSt[d.Main_i - 1][d.Main_j * 2][0] = 4;
                    
                    
                    storeArray(d.recS1St[d.Main_i], d.Main_j);
                    d.recS1St[d.Main_i][d.Main_j] = 4;
                    
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    break;
                }
                case 14: { // Цикл по тройкам типа 2
                    break;
                }
                case 15: { // Выписываем номера троек типа 2
                    startSection();
                    if (d.Main_j > (d.tripplesNumSt[d.Main_i - 1].length + 1) / 2) {
                        storeArray(d.tripplesNumSt[d.Main_i - 1][(d.Main_j - 1 - ((d.tripplesNumSt[d.Main_i - 1].length + 1) / 2)) * 2 + 1], 0);
                        d.tripplesNumSt[d.Main_i - 1][(d.Main_j - 1 - ((d.tripplesNumSt[d.Main_i - 1].length + 1) / 2)) * 2 + 1][0] = 3;
                        storeArray(d.recS1St[d.Main_i], d.Main_j - 1);
                        d.recS1St[d.Main_i][d.Main_j - 1] = 3;
                    } else {
                        storeArray(d.tripplesNumSt[d.Main_i - 1][2 * d.Main_j - 2], 0);
                        d.tripplesNumSt[d.Main_i - 1][2 * d.Main_j - 2][0] = 2;
                        storeArray(d.recS1St[d.Main_i], d.Main_j - 1);
                        d.recS1St[d.Main_i][d.Main_j - 1] = 2;
                    }
                    storeArray(d.tripplesNumSt[d.Main_i - 1][(d.Main_j - ((d.tripplesNumSt[d.Main_i - 1].length + 1) / 2)) * 2 + 1], 0);
                    d.tripplesNumSt[d.Main_i - 1][(d.Main_j - ((d.tripplesNumSt[d.Main_i - 1].length + 1) / 2)) * 2 + 1][0] = 4;
                    
                    storeArray(d.recS1St[d.Main_i], d.Main_j);
                    d.recS1St[d.Main_i][d.Main_j] = 4;
                    
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    break;
                }
                case 16: { // Комментарий
                    startSection();
                    storeArray(d.recS1St[d.Main_i], d.Main_j - 1);
                    d.recS1St[d.Main_i][d.Main_j - 1] = 3;
                    storeArray(d.tripplesNumSt[d.Main_i - 1][d.tripplesNumSt[d.Main_i - 1].length - 1 - (d.tripplesNumSt[d.Main_i - 1].length % 2)], 0);
                    d.tripplesNumSt[d.Main_i - 1][d.tripplesNumSt[d.Main_i - 1].length - 1 - (d.tripplesNumSt[d.Main_i - 1].length % 2)][0] = 3;
                    break;
                }
                case 17: { // Цикл
                    break;
                }
                case 18: { // Непосредственное построение суффиксного массива
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.recStrSt[d.Main_i - 1], d.Main_j - 1);
                        d.recStrSt[d.Main_i - 1][d.Main_j - 1] = 1 + (d.Main_j % 3);
                        storeArray(d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j - 1] - 1], 0);
                        d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j - 1] - 1][0] = 1;
                    }
                    storeArray(d.recStrSt[d.Main_i - 1], d.Main_j);
                    d.recStrSt[d.Main_i - 1][d.Main_j] = 4;
                    storeArray(d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j] - 1], 0);
                    d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j] - 1][0] = 4;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 19: { // Цикл
                    break;
                }
                case 20: { // Начало шага рекурсии
                    startSection();
                    if (d.Main_i == d.recStr.length) {
                        storeArray(d.recStrSt[d.Main_i - 1], d.Main_j - 1);
                        d.recStrSt[d.Main_i - 1][d.Main_j - 1] = 1 + (d.Main_j % 3);
                        storeArray(d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j - 1] - 1], 0);
                        d.lastSuffArrSt[d.recStr[d.recStr.length - 1][d.Main_j - 1] - 1][0] = 1;    
                    }
                    if (d.Main_i < d.suff0ListSt.length) {
                        storeArray(d.suff0ListSt2[d.Main_i][d.suff0ListSt2[d.Main_i].length - 1], 0);
                        d.suff0ListSt2[d.Main_i][d.suff0ListSt2[d.Main_i].length - 1][0] = 1;
                        storeArray(d.suff23ListSt2[d.Main_i][d.suff23ListSt2[d.Main_i].length - 1], 0);
                        d.suff23ListSt2[d.Main_i][d.suff23ListSt2[d.Main_i].length - 1][0] = 1;
                    }
                    break;
                }
                case 21: { // ???
                    startSection();
                    for (int ti = 0; ti < d.recStrSt[d.Main_i - 1].length; ti++) {
                        storeArray(d.recStrSt[d.Main_i - 1], ti);
                        d.recStrSt[d.Main_i - 1][ti] = -1;    
                        storeArray(d.recStrNumSt[d.Main_i - 1], ti);
                        d.recStrNumSt[d.Main_i - 1][ti] = -1;
                    }
                    if (d.Main_i < d.suff0ListSt2.length) {
                        for (int ti = 0; ti < d.suff0ListSt2[d.Main_i].length; ti++) {
                            storeArray(d.suff0ListSt2[d.Main_i][ti], 0);
                            d.suff0ListSt2[d.Main_i][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.suff23ListSt2[d.Main_i].length; ti++) {
                            storeArray(d.suff23ListSt2[d.Main_i][ti], 0);
                            d.suff23ListSt2[d.Main_i][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.trp23NumSt[d.Main_i].length; ti++) {
                            storeArray(d.trp23NumSt[d.Main_i][ti], 0);
                            d.trp23NumSt[d.Main_i][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.suffArrSt[d.Main_i - 1].length; ti++) {
                            storeArray(d.suffArrSt2[d.Main_i - 1], ti);
                            d.suffArrSt2[d.Main_i - 1][ti] = -1;
                        }
                    }
                    if (d.Main_i < d.suffArr.length) {                        
                        storeArray(d.suffArrSt[d.Main_i], 0);
                        d.suffArrSt[d.Main_i][0] = -1;                        
                    } else {
                        for (int ti = 0; ti < d.lastSuffArrSt.length; ti++) {
                            storeArray(d.lastSuffArrSt[ti], 0);
                            d.lastSuffArrSt[ti][0] = -1;
                        }
                    }
                                     
                    storeField(d, "Main_i");
                    d.Main_i = d.Main_i - 1;
                    for (int ti = 0; ti < d.suffArrSt[d.Main_i].length; ti++) {
                        storeArray(d.suffArrSt[d.Main_i], ti);
                        d.suffArrSt[d.Main_i][ti] = 1;
                    }                   
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    break;
                }
                case 22: { // Комментарий
                    startSection();
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    for (int ti = 0; ti < d.invSuffArrSt[d.Main_i].length; ti++) {
                        storeArray(d.invSuffArrNumSt[d.Main_i][ti], 0);
                        d.invSuffArrNumSt[d.Main_i][ti][0] = 1;
                    }
                    break;
                }
                case 23: { // Цикл построения обратной перестановки
                    break;
                }
                case 24: { // ???
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.suffArrSt[d.Main_i], d.Main_j - 1);
                        d.suffArrSt[d.Main_i][d.Main_j - 1] = 1;
                        storeArray(d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j - 1]], 0);
                        d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j - 1]][0] = 1;
                    }
                    storeArray(d.suffArrSt[d.Main_i], d.Main_j);
                    d.suffArrSt[d.Main_i][d.Main_j] = 4;
                    storeArray(d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j]], 0);
                    d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j]][0] = 4;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 25: { // Комментарий
                    startSection();
                    storeArray(d.suffArrSt[d.Main_i], d.Main_j - 1);
                    d.suffArrSt[d.Main_i][d.Main_j - 1] = 1;        
                    storeArray(d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j - 1]], 0);
                    d.invSuffArrSt[d.Main_i][d.suffArr[d.Main_i][d.Main_j - 1]][0] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    for (int ti = 0; ti < d.suff23ListNumSt[d.Main_i].length; ti++) {
                        storeArray(d.suff23ListNumSt[d.Main_i][ti], 0);
                        d.suff23ListNumSt[d.Main_i][ti][0] = 1;
                    }
                    break;
                }
                case 26: { // Цикл
                    break;
                }
                case 27: { // Шаг цикла
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.invSuffArrSt[d.Main_i][d.Main_j - 1], 0);
                        d.invSuffArrSt[d.Main_i][d.Main_j - 1][0] = 1;
                        storeArray(d.trp23NumSt[d.Main_i][d.Main_j - 1], 0);
                        d.trp23NumSt[d.Main_i][d.Main_j - 1][0] = 1;
                        storeArray(d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]], 0);
                        d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]][0] = 1;
                    }
                    storeArray(d.invSuffArrSt[d.Main_i][d.Main_j], 0);
                    d.invSuffArrSt[d.Main_i][d.Main_j][0] = 4;
                    storeArray(d.trp23NumSt[d.Main_i][d.Main_j], 0);
                    d.trp23NumSt[d.Main_i][d.Main_j][0] = 4;
                    storeArray(d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j][0]], 0);
                    d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j][0]][0] = 4;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 28: { // Скрываем вспомогательные массивы
                    startSection();
                    storeArray(d.invSuffArrSt[d.Main_i][d.Main_j - 1], 0);
                    d.invSuffArrSt[d.Main_i][d.Main_j - 1][0] = 1;       
                    storeArray(d.trp23NumSt[d.Main_i][d.Main_j - 1], 0);
                    d.trp23NumSt[d.Main_i][d.Main_j - 1][0] = 1;
                    storeArray(d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]], 0);
                    d.suff23ListSt[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]][0] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    for (int ti = 0; ti < d.suff23List[d.Main_i].length; ti++) {
                        storeArray(d.suff23ListSt[d.Main_i][ti], 0);
                        d.suff23ListSt[d.Main_i][ti][0] = -1;
                        storeArray(d.suff23ListSt2[d.Main_i][ti], 0);
                        d.suff23ListSt2[d.Main_i][ti][0] = 1;
                        storeArray(d.suff23ListNumSt[d.Main_i][ti], 0);
                        d.suff23ListNumSt[d.Main_i][ti][0] = -1;
                        storeArray(d.invSuffArrSt[d.Main_i][ti], 0);
                        d.invSuffArrSt[d.Main_i][ti][0] = -1;
                        storeArray(d.invSuffArrNumSt[d.Main_i][ti], 0);
                        d.invSuffArrNumSt[d.Main_i][ti][0] = -1;
                        storeArray(d.suffArrSt[d.Main_i], ti);
                        d.suffArrSt[d.Main_i][ti] = -1;
                    }
                    break;
                }
                case 29: { // Комментарий
                    startSection();
                    break;
                }
                case 30: { // Цикл построения пар
                    break;
                }
                case 31: { // Выписываем очередную пару
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.recStrSt[d.Main_i - 1], 3 * (d.Main_j - 1));
                        d.recStrSt[d.Main_i - 1][3 * (d.Main_j - 1)] = 1;
                        storeArray(d.trp23NumSt[d.Main_i][d.Main_j - 1], 0);
                        d.trp23NumSt[d.Main_i][d.Main_j - 1][0] = 1;
                    }
                    storeArray(d.recStrSt[d.Main_i - 1], 3 * d.Main_j);
                    d.recStrSt[d.Main_i - 1][3 * d.Main_j] = 4;
                    storeArray(d.trp23NumSt[d.Main_i][d.Main_j], 0);
                    d.trp23NumSt[d.Main_i][d.Main_j][0] = 4;
                    for (int ti = 0; ti < d.pairsSt[d.Main_i][d.Main_j].length; ti++) {
                        storeArray(d.pairsSt[d.Main_i][d.Main_j], ti);
                        d.pairsSt[d.Main_i][d.Main_j][ti] = 1;
                    }
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 32: { // Сортировка пар
                    startSection();
                    storeArray(d.recStrSt[d.Main_i - 1], 3 * (d.Main_j - 1));
                    d.recStrSt[d.Main_i - 1][3 * (d.Main_j - 1)] = 1;
                    storeArray(d.trp23NumSt[d.Main_i][d.Main_j - 1], 0);
                    d.trp23NumSt[d.Main_i][d.Main_j - 1][0] = 1;         
                    for (int ti = 0; ti < d.pairsNumSt[d.Main_i].length; ti++) {
                        storeArray(d.pairsNumSt[d.Main_i][ti], 0);
                        d.pairsNumSt[d.Main_i][ti][0] = 1;
                    }
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    break;
                }
                case 33: { // Комментарий
                    startSection();
                    for (int ti = 0; ti < d.suff0ListNumSt[d.Main_i].length; ti++) {
                        storeArray(d.suff0ListNumSt[d.Main_i][ti], 0);
                        d.suff0ListNumSt[d.Main_i][ti][0] = 1;
                    }
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    break;
                }
                case 34: { // Цикл
                    break;
                }
                case 35: { // ???
                    startSection();
                    if (d.Main_j > 0) {
                        storeArray(d.pairsSt[d.Main_i][d.Main_j - 1], 0);
                        d.pairsSt[d.Main_i][d.Main_j - 1][0] = 1;
                        storeArray(d.pairsSt[d.Main_i][d.Main_j - 1], 1);
                        d.pairsSt[d.Main_i][d.Main_j - 1][1] = 1;
                        storeArray(d.pairsNumSt[d.Main_i][d.Main_j - 1], 0);
                        d.pairsNumSt[d.Main_i][d.Main_j - 1][0] = 1;
                        storeArray(d.recStrSt[d.Main_i - 1], 3 * (d.Main_j - 1));
                        d.recStrSt[d.Main_i - 1][3 * (d.Main_j - 1)] = 1;
                        storeArray(d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][d.Main_j - 1][0]], 0);
                        d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][d.Main_j - 1][0]][0] = 1;
                    }
                    storeArray(d.pairsSt[d.Main_i][d.Main_j], 0);
                    d.pairsSt[d.Main_i][d.Main_j][0] = 4;
                    storeArray(d.pairsSt[d.Main_i][d.Main_j], 1);
                    d.pairsSt[d.Main_i][d.Main_j][1] = 4;
                    storeArray(d.pairsNumSt[d.Main_i][d.Main_j], 0);
                    d.pairsNumSt[d.Main_i][d.Main_j][0] = 4;
                    storeArray(d.recStrSt[d.Main_i - 1], 3 * (d.Main_j));
                    d.recStrSt[d.Main_i - 1][3 * (d.Main_j)] = 4;
                    storeArray(d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][d.Main_j][0]], 0);
                    d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][d.Main_j][0]][0] = 4;                        
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;    
                    break;
                }
                case 36: { // ???
                    startSection();
                    int pj = d.pairs[d.Main_i].length;
                    storeArray(d.pairsSt[d.Main_i][pj - 1], 0);
                    d.pairsSt[d.Main_i][pj - 1][0] = 1;
                    storeArray(d.pairsSt[d.Main_i][pj - 1], 1);
                    d.pairsSt[d.Main_i][pj - 1][1] = 1;
                    storeArray(d.pairsNumSt[d.Main_i][pj - 1], 0);
                    d.pairsNumSt[d.Main_i][pj - 1][0] = 1;
                    storeArray(d.recStrSt[d.Main_i - 1], 3 * (pj - 1));
                    d.recStrSt[d.Main_i - 1][3 * (pj - 1)] = 1;
                    storeArray(d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][pj - 1][0]], 0);
                    d.suff0ListSt[d.Main_i][d.pairsNum[d.Main_i][pj - 1][0]][0] = 1;      
                    
                    for (int ti = 0; ti < d.pairsNum[d.Main_i].length; ti++) {
                        storeArray(d.pairsNumSt[d.Main_i][ti], 0);
                        d.pairsNumSt[d.Main_i][ti][0] = -1;
                        for (int tj = 0; tj < d.pairsSt[d.Main_i][ti].length; tj++) {
                            storeArray(d.pairsSt[d.Main_i][ti], tj);
                            d.pairsSt[d.Main_i][ti][tj] = -1;
                        }
                    }
                    
                    for (int ti = 0; ti < d.suff0List[d.Main_i].length; ti++) {
                        storeArray(d.suff0ListSt[d.Main_i][ti], 0);
                        d.suff0ListSt[d.Main_i][ti][0] = -1;
                        storeArray(d.suff0ListSt2[d.Main_i][ti], 0);
                        d.suff0ListSt2[d.Main_i][ti][0] = 1;
                        storeArray(d.suff0ListNumSt[d.Main_i][ti], 0);
                        d.suff0ListNumSt[d.Main_i][ti][0] = -1;
                    }
                    
                    storeField(d, "Main_j");
                    d.Main_j = 0;
                    storeField(d, "Main_l");
                    d.Main_l = 0;
                    storeField(d, "Main_r");
                    d.Main_r = 0;
                    storeArray(d.suff0ListSt2[d.Main_i][d.Main_l], 0);
                    d.suff0ListSt2[d.Main_i][d.Main_l][0] = 4;
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l][0]);
                    d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0]] = 4;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0]);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0]] = 5;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = true;
                    storeField(d, "Main_sh");
                    d.Main_sh = -1;
                    break;
                }
                case 37: { // ???
                    break;
                }
                case 38: { // ??? (окончание)
                    break;
                }
                case 39: { // ???
                    startSection();
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0]);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0]] = 1 + (d.suff23List[d.Main_i][d.Main_r][0] % 3);
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 1;
                    storeField(d, "Main_r");
                    d.Main_r = d.Main_r + 1;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0]);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0]] = 5;
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    break;
                }
                case 40: { // Цикл
                    break;
                }
                case 41: { // ???
                    startSection();
                    for (int ti = 0; ti < d.recStrSt[d.Main_i - 1].length; ti++) {
                         storeArray(d.recStrSt[d.Main_i - 1], ti);
                         d.recStrSt[d.Main_i - 1][ti] = 1 + (ti % 3);
                    }
                        
                    if (d.Main_l > 0) {
                        storeArray(d.suff0ListSt2[d.Main_i][d.Main_l - 1], 0);
                        d.suff0ListSt2[d.Main_i][d.Main_l - 1][0] = 1;           
                    }       
                    
                    if (d.Main_r > 0) {
                        storeArray(d.suff23ListSt2[d.Main_i][d.Main_r - 1], 0);
                        d.suff23ListSt2[d.Main_i][d.Main_r - 1][0] = 1;           
                    }
                    
                    storeArray(d.suff0ListSt2[d.Main_i][d.Main_l], 0);
                    d.suff0ListSt2[d.Main_i][d.Main_l][0] = 4;
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l][0]);
                    d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0]] = 4;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0]);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0]] = 5;
                    storeField(d, "Main_sh");
                    d.Main_sh = -1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = true;
                    break;
                }
                case 42: { // Цикл
                    break;
                }
                case 43: { // ???
                    startSection();
                    for (int ti = 0; ti < d.recStrSt[d.Main_i - 1].length; ti++) {
                        storeArray(d.recStrSt[d.Main_i - 1], ti);
                        d.recStrSt[d.Main_i - 1][ti] = 1 + (ti % 3);
                    }
                    storeField(d, "Main_sh");
                    d.Main_sh = d.Main_sh + 1;
                    
                    if (d.Main_l > 0) {
                        storeArray(d.suff0ListSt2[d.Main_i][d.Main_l - 1], 0);
                        d.suff0ListSt2[d.Main_i][d.Main_l - 1][0] = 1;           
                    }       
                    
                    if (d.Main_r > 0) {
                        storeArray(d.suff23ListSt2[d.Main_i][d.Main_r - 1], 0);
                        d.suff23ListSt2[d.Main_i][d.Main_r - 1][0] = 1;           
                    }
                    
                    storeArray(d.suff0ListSt2[d.Main_i][d.Main_l], 0);
                    d.suff0ListSt2[d.Main_i][d.Main_l][0] = 4;       
                    storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                    d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh);
                    d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] = 4;
                    storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh);
                    d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh] = 5;
                    
                    storeField(d, "Main_goOn");
                    d.Main_goOn = true;
                    break;
                }
                case 44: { // Сравнение символов
                    break;
                }
                case 45: { // Сравнение символов (окончание)
                    break;
                }
                case 46: { // Проверка на принадлежность к типам 1-2
                    break;
                }
                case 47: { // Проверка на принадлежность к типам 1-2 (окончание)
                    break;
                }
                case 48: { // Проверка, не кончилавсь ли строка
                    break;
                }
                case 49: { // Проверка, не кончилавсь ли строка (окончание)
                    break;
                }
                case 50: { // ???
                    startSection();
                    if (d.suff0List[d.Main_i][d.Main_l][0] + 1 == d.recStr[d.Main_i - 1].length) {
                        storeField(d, "Main_r");
                        d.Main_r = d.Main_r + 1;
                    } else {
                        storeField(d, "Main_l");
                        d.Main_l = d.Main_l + 1;
                    }
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = false;
                    break;
                }
                case 51: { // Сравнение номеров суффиксов
                    break;
                }
                case 52: { // Сравнение номеров суффиксов (окончание)
                    break;
                }
                case 53: { // ???
                    startSection();
                    storeField(d, "Main_l");
                    d.Main_l = d.Main_l + 1;
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = false;
                    break;
                }
                case 54: { // ???
                    startSection();
                    storeField(d, "Main_r");
                    d.Main_r = d.Main_r + 1;
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = false;
                    break;
                }
                case 55: { // Сравнение символов
                    startSection();
                    storeField(d, "Main_tl");
                    d.Main_tl = d.Main_l;
                    storeField(d, "Main_tr");
                    d.Main_tr = d.Main_r;
                    if (d.recStr[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] < d.recStr[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        storeField(d, "Main_l");
                        d.Main_l = d.Main_l + 1;
                    } else {
                        storeField(d, "Main_r");
                        d.Main_r = d.Main_r + 1;
                    }
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;
                    storeField(d, "Main_goOn");
                    d.Main_goOn = false;
                    break;
                }
                case 56: { // Дописываем остаток
                    break;
                }
                case 57: { // Дописываем остаток
                    startSection();
                    if (d.Main_l < d.suff0List[d.Main_i].length) {
                         storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh);
                         d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] = 1;
                    }
                    
                    if (d.Main_l - 1 < d.suff0List[d.Main_i].length && d.Main_l > 0) { 
                        storeArray(d.suff0ListSt2[d.Main_i][d.Main_l - 1], 0);
                        d.suff0ListSt2[d.Main_i][d.Main_l - 1][0] = 1;
                        storeArray(d.recStrSt[d.Main_i - 1], d.suff0List[d.Main_i][d.Main_l - 1][0] + d.Main_sh);
                        d.recStrSt[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l - 1][0] + d.Main_sh] = 1;
                    }
                    
                    if (d.Main_r < d.suff23List[d.Main_i].length) {
                        storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh);
                        d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh] = 1 + ((d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh) % 3); 
                    }
                    if (d.Main_r - 1 < d.suff23List[d.Main_i].length && d.Main_r > 0) {
                        storeArray(d.suff23ListSt2[d.Main_i][d.Main_r - 1], 0);
                        d.suff23ListSt2[d.Main_i][d.Main_r - 1][0] = 1;
                        storeArray(d.recStrSt[d.Main_i - 1], d.suff23List[d.Main_i][d.Main_r - 1][0] + d.Main_sh);
                        d.recStrSt[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r - 1][0] + d.Main_sh] = 1 + ((d.suff23List[d.Main_i][d.Main_r - 1][0] + d.Main_sh) % 3);
                    } 
                    
                    if (d.Main_l < d.suff0List[d.Main_i].length) { 
                        storeArray(d.suff0ListSt2[d.Main_i][d.Main_l], 0);
                        d.suff0ListSt2[d.Main_i][d.Main_l][0] = 4;
                    }
                    if (d.Main_r < d.suff23List[d.Main_i].length) {
                        storeArray(d.suff23ListSt2[d.Main_i][d.Main_r], 0);
                        d.suff23ListSt2[d.Main_i][d.Main_r][0] = 5;
                    }
                    
                    storeArray(d.suffArrSt2[d.Main_i - 1], d.Main_j);
                    d.suffArrSt2[d.Main_i - 1][d.Main_j] = 1;
                    storeField(d, "Main_l");
                    d.Main_l = d.Main_l + 1;
                    storeField(d, "Main_r");
                    d.Main_r = d.Main_r + 1;
                    storeField(d, "Main_j");
                    d.Main_j = d.Main_j + 1;                                                                               
                    break;
                }
                case 58: { // Сокрытие ненужных массивов
                    startSection();
                    for (int ti = 0; ti < d.recStrSt[0].length; ti++) {
                        storeArray(d.recStrSt[0], ti);
                        d.recStrSt[0][ti] = -1;
                        storeArray(d.recStrNumSt[0], ti);
                        d.recStrNumSt[0][ti] = -1;
                    }
                    if (d.suff0ListSt2.length > 1) {
                        for (int ti = 0; ti < d.suff0ListSt2[1].length; ti++) {
                            storeArray(d.suff0ListSt2[1][ti], 0);
                            d.suff0ListSt2[1][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.suff23ListSt2[1].length; ti++) {
                            storeArray(d.suff23ListSt2[1][ti], 0);
                            d.suff23ListSt2[1][ti][0] = -1;
                        }
                        for (int ti = 0; ti < d.trp23NumSt[1].length; ti++) {
                            storeArray(d.trp23NumSt[1][ti], 0);
                            d.trp23NumSt[1][ti][0] = -1;
                        }
                    }
                    for (int ti = 0; ti < d.suffArrSt[0].length; ti++) {
                        storeArray(d.suffArrSt2[0], ti);
                        d.suffArrSt2[0][ti] = -1;
                    }
                    for (int ti = 0; ti < d.lastSuffArrSt.length; ti++) {
                        storeArray(d.lastSuffArrSt[ti], 0);
                        d.lastSuffArrSt[ti][0] = -1;
                    }
                    for (int ti = 0; ti < d.suffArrSt[0].length; ti++) {
                        storeArray(d.suffArrSt[0], ti);
                        d.suffArrSt[0][ti] = 1;
                    }
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
                case 1: { // Перенумерация
                    restoreSection();
                    break;
                }
                case 2: { // Главный цикл рекурсии
                    break;
                }
                case 3: { // Текущая строка
                    restoreSection();
                    break;
                }
                case 4: { // Проверка на то, что все символы различны
                    break;
                }
                case 5: { // Проверка на то, что все символы различны (окончание)
                    break;
                }
                case 6: { // Дополнение нулем
                    break;
                }
                case 7: { // Дополнение нулем (окончание)
                    break;
                }
                case 8: { // Комментарий
                    restoreSection();
                    break;
                }
                case 9: { // Цкл
                    break;
                }
                case 10: { // Построение троек
                    restoreSection();
                    break;
                }
                case 11: { // Сортировка троек
                    restoreSection();
                    break;
                }
                case 12: { // Цикл по тройкам типа 1
                    break;
                }
                case 13: { // Выписываем номера троек типа 1
                    restoreSection();
                    break;
                }
                case 14: { // Цикл по тройкам типа 2
                    break;
                }
                case 15: { // Выписываем номера троек типа 2
                    restoreSection();
                    break;
                }
                case 16: { // Комментарий
                    restoreSection();
                    break;
                }
                case 17: { // Цикл
                    break;
                }
                case 18: { // Непосредственное построение суффиксного массива
                    restoreSection();
                    break;
                }
                case 19: { // Цикл
                    break;
                }
                case 20: { // Начало шага рекурсии
                    restoreSection();
                    break;
                }
                case 21: { // ???
                    restoreSection();
                    break;
                }
                case 22: { // Комментарий
                    restoreSection();
                    break;
                }
                case 23: { // Цикл построения обратной перестановки
                    break;
                }
                case 24: { // ???
                    restoreSection();
                    break;
                }
                case 25: { // Комментарий
                    restoreSection();
                    break;
                }
                case 26: { // Цикл
                    break;
                }
                case 27: { // Шаг цикла
                    restoreSection();
                    break;
                }
                case 28: { // Скрываем вспомогательные массивы
                    restoreSection();
                    break;
                }
                case 29: { // Комментарий
                    restoreSection();
                    break;
                }
                case 30: { // Цикл построения пар
                    break;
                }
                case 31: { // Выписываем очередную пару
                    restoreSection();
                    break;
                }
                case 32: { // Сортировка пар
                    restoreSection();
                    break;
                }
                case 33: { // Комментарий
                    restoreSection();
                    break;
                }
                case 34: { // Цикл
                    break;
                }
                case 35: { // ???
                    restoreSection();
                    break;
                }
                case 36: { // ???
                    restoreSection();
                    break;
                }
                case 37: { // ???
                    break;
                }
                case 38: { // ??? (окончание)
                    break;
                }
                case 39: { // ???
                    restoreSection();
                    break;
                }
                case 40: { // Цикл
                    break;
                }
                case 41: { // ???
                    restoreSection();
                    break;
                }
                case 42: { // Цикл
                    break;
                }
                case 43: { // ???
                    restoreSection();
                    break;
                }
                case 44: { // Сравнение символов
                    break;
                }
                case 45: { // Сравнение символов (окончание)
                    break;
                }
                case 46: { // Проверка на принадлежность к типам 1-2
                    break;
                }
                case 47: { // Проверка на принадлежность к типам 1-2 (окончание)
                    break;
                }
                case 48: { // Проверка, не кончилавсь ли строка
                    break;
                }
                case 49: { // Проверка, не кончилавсь ли строка (окончание)
                    break;
                }
                case 50: { // ???
                    restoreSection();
                    break;
                }
                case 51: { // Сравнение номеров суффиксов
                    break;
                }
                case 52: { // Сравнение номеров суффиксов (окончание)
                    break;
                }
                case 53: { // ???
                    restoreSection();
                    break;
                }
                case 54: { // ???
                    restoreSection();
                    break;
                }
                case 55: { // Сравнение символов
                    restoreSection();
                    break;
                }
                case 56: { // Дописываем остаток
                    break;
                }
                case 57: { // Дописываем остаток
                    restoreSection();
                    break;
                }
                case 58: { // Сокрытие ненужных массивов
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Перенумерация
                    state = START_STATE; 
                    break;
                }
                case 2: { // Главный цикл рекурсии
                    if (stack.popBoolean()) {
                        state = 5; // Проверка на то, что все символы различны (окончание)
                    } else {
                        state = 1; // Перенумерация
                    }
                    break;
                }
                case 3: { // Текущая строка
                    state = 2; // Главный цикл рекурсии
                    break;
                }
                case 4: { // Проверка на то, что все символы различны
                    state = 3; // Текущая строка
                    break;
                }
                case 5: { // Проверка на то, что все символы различны (окончание)
                    if (stack.popBoolean()) {
                        state = 16; // Комментарий
                    } else {
                        state = 4; // Проверка на то, что все символы различны
                    }
                    break;
                }
                case 6: { // Дополнение нулем
                    state = 4; // Проверка на то, что все символы различны
                    break;
                }
                case 7: { // Дополнение нулем (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // Комментарий
                    } else {
                        state = 6; // Дополнение нулем
                    }
                    break;
                }
                case 8: { // Комментарий
                    state = 6; // Дополнение нулем
                    break;
                }
                case 9: { // Цкл
                    if (stack.popBoolean()) {
                        state = 10; // Построение троек
                    } else {
                        state = 7; // Дополнение нулем (окончание)
                    }
                    break;
                }
                case 10: { // Построение троек
                    state = 9; // Цкл
                    break;
                }
                case 11: { // Сортировка троек
                    state = 9; // Цкл
                    break;
                }
                case 12: { // Цикл по тройкам типа 1
                    if (stack.popBoolean()) {
                        state = 13; // Выписываем номера троек типа 1
                    } else {
                        state = 11; // Сортировка троек
                    }
                    break;
                }
                case 13: { // Выписываем номера троек типа 1
                    state = 12; // Цикл по тройкам типа 1
                    break;
                }
                case 14: { // Цикл по тройкам типа 2
                    if (stack.popBoolean()) {
                        state = 15; // Выписываем номера троек типа 2
                    } else {
                        state = 12; // Цикл по тройкам типа 1
                    }
                    break;
                }
                case 15: { // Выписываем номера троек типа 2
                    state = 14; // Цикл по тройкам типа 2
                    break;
                }
                case 16: { // Комментарий
                    state = 14; // Цикл по тройкам типа 2
                    break;
                }
                case 17: { // Цикл
                    if (stack.popBoolean()) {
                        state = 18; // Непосредственное построение суффиксного массива
                    } else {
                        state = 2; // Главный цикл рекурсии
                    }
                    break;
                }
                case 18: { // Непосредственное построение суффиксного массива
                    state = 17; // Цикл
                    break;
                }
                case 19: { // Цикл
                    if (stack.popBoolean()) {
                        state = 56; // Дописываем остаток
                    } else {
                        state = 17; // Цикл
                    }
                    break;
                }
                case 20: { // Начало шага рекурсии
                    state = 19; // Цикл
                    break;
                }
                case 21: { // ???
                    state = 20; // Начало шага рекурсии
                    break;
                }
                case 22: { // Комментарий
                    state = 21; // ???
                    break;
                }
                case 23: { // Цикл построения обратной перестановки
                    if (stack.popBoolean()) {
                        state = 24; // ???
                    } else {
                        state = 22; // Комментарий
                    }
                    break;
                }
                case 24: { // ???
                    state = 23; // Цикл построения обратной перестановки
                    break;
                }
                case 25: { // Комментарий
                    state = 23; // Цикл построения обратной перестановки
                    break;
                }
                case 26: { // Цикл
                    if (stack.popBoolean()) {
                        state = 27; // Шаг цикла
                    } else {
                        state = 25; // Комментарий
                    }
                    break;
                }
                case 27: { // Шаг цикла
                    state = 26; // Цикл
                    break;
                }
                case 28: { // Скрываем вспомогательные массивы
                    state = 26; // Цикл
                    break;
                }
                case 29: { // Комментарий
                    state = 28; // Скрываем вспомогательные массивы
                    break;
                }
                case 30: { // Цикл построения пар
                    if (stack.popBoolean()) {
                        state = 31; // Выписываем очередную пару
                    } else {
                        state = 29; // Комментарий
                    }
                    break;
                }
                case 31: { // Выписываем очередную пару
                    state = 30; // Цикл построения пар
                    break;
                }
                case 32: { // Сортировка пар
                    state = 30; // Цикл построения пар
                    break;
                }
                case 33: { // Комментарий
                    state = 32; // Сортировка пар
                    break;
                }
                case 34: { // Цикл
                    if (stack.popBoolean()) {
                        state = 35; // ???
                    } else {
                        state = 33; // Комментарий
                    }
                    break;
                }
                case 35: { // ???
                    state = 34; // Цикл
                    break;
                }
                case 36: { // ???
                    state = 34; // Цикл
                    break;
                }
                case 37: { // ???
                    state = 36; // ???
                    break;
                }
                case 38: { // ??? (окончание)
                    if (stack.popBoolean()) {
                        state = 39; // ???
                    } else {
                        state = 37; // ???
                    }
                    break;
                }
                case 39: { // ???
                    state = 37; // ???
                    break;
                }
                case 40: { // Цикл
                    if (stack.popBoolean()) {
                        state = 42; // Цикл
                    } else {
                        state = 38; // ??? (окончание)
                    }
                    break;
                }
                case 41: { // ???
                    state = 40; // Цикл
                    break;
                }
                case 42: { // Цикл
                    if (stack.popBoolean()) {
                        state = 45; // Сравнение символов (окончание)
                    } else {
                        state = 41; // ???
                    }
                    break;
                }
                case 43: { // ???
                    state = 42; // Цикл
                    break;
                }
                case 44: { // Сравнение символов
                    state = 43; // ???
                    break;
                }
                case 45: { // Сравнение символов (окончание)
                    if (stack.popBoolean()) {
                        state = 47; // Проверка на принадлежность к типам 1-2 (окончание)
                    } else {
                        state = 55; // Сравнение символов
                    }
                    break;
                }
                case 46: { // Проверка на принадлежность к типам 1-2
                    state = 44; // Сравнение символов
                    break;
                }
                case 47: { // Проверка на принадлежность к типам 1-2 (окончание)
                    if (stack.popBoolean()) {
                        state = 49; // Проверка, не кончилавсь ли строка (окончание)
                    } else {
                        state = 52; // Сравнение номеров суффиксов (окончание)
                    }
                    break;
                }
                case 48: { // Проверка, не кончилавсь ли строка
                    state = 46; // Проверка на принадлежность к типам 1-2
                    break;
                }
                case 49: { // Проверка, не кончилавсь ли строка (окончание)
                    if (stack.popBoolean()) {
                        state = 50; // ???
                    } else {
                        state = 48; // Проверка, не кончилавсь ли строка
                    }
                    break;
                }
                case 50: { // ???
                    state = 48; // Проверка, не кончилавсь ли строка
                    break;
                }
                case 51: { // Сравнение номеров суффиксов
                    state = 46; // Проверка на принадлежность к типам 1-2
                    break;
                }
                case 52: { // Сравнение номеров суффиксов (окончание)
                    if (stack.popBoolean()) {
                        state = 53; // ???
                    } else {
                        state = 54; // ???
                    }
                    break;
                }
                case 53: { // ???
                    state = 51; // Сравнение номеров суффиксов
                    break;
                }
                case 54: { // ???
                    state = 51; // Сравнение номеров суффиксов
                    break;
                }
                case 55: { // Сравнение символов
                    state = 44; // Сравнение символов
                    break;
                }
                case 56: { // Дописываем остаток
                    if (stack.popBoolean()) {
                        state = 57; // Дописываем остаток
                    } else {
                        state = 40; // Цикл
                    }
                    break;
                }
                case 57: { // Дописываем остаток
                    state = 56; // Дописываем остаток
                    break;
                }
                case 58: { // Сокрытие ненужных массивов
                    state = 19; // Цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 58; // Сокрытие ненужных массивов
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
                    comment = Karkkainen.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // Перенумерация
                    comment = Karkkainen.this.getComment("Main.Renumerating"); 
                    break;
                }
                case 3: { // Текущая строка
                    comment = Karkkainen.this.getComment("Main.step1"); 
                    break;
                }
                case 4: { // Проверка на то, что все символы различны
                    if (d.Main_i < d.recStr.length) {
                        comment = Karkkainen.this.getComment("Main.if0.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if0.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 6: { // Дополнение нулем
                    if (d.recStr[d.Main_i - 1][d.recStr[d.Main_i - 1].length - 1] == 0) {
                        comment = Karkkainen.this.getComment("Main.if1.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if1.false"); 
                    }
                    args = new Object[]{new Integer(d.recStr[d.Main_i - 1].length % 3)}; 
                    break;
                }
                case 8: { // Комментарий
                    comment = Karkkainen.this.getComment("Main.step2"); 
                    break;
                }
                case 10: { // Построение троек
                    comment = Karkkainen.this.getComment("Main.bla"); 
                    break;
                }
                case 11: { // Сортировка троек
                    comment = Karkkainen.this.getComment("Main.I"); 
                    break;
                }
                case 13: { // Выписываем номера троек типа 1
                    comment = Karkkainen.this.getComment("Main.step5"); 
                    break;
                }
                case 15: { // Выписываем номера троек типа 2
                    comment = Karkkainen.this.getComment("Main.step6"); 
                    break;
                }
                case 16: { // Комментарий
                    comment = Karkkainen.this.getComment("Main.Idi"); 
                    break;
                }
                case 18: { // Непосредственное построение суффиксного массива
                    comment = Karkkainen.this.getComment("Main.step12"); 
                    args = new Object[]{new Integer(d.recStr[d.Main_i - 1][d.Main_j - 1]), new Integer(d.Main_j - 1), new Integer(d.recStr[d.Main_i - 1][d.Main_j - 1] - 1)}; 
                    break;
                }
                case 20: { // Начало шага рекурсии
                    comment = Karkkainen.this.getComment("Main.step14"); 
                    args = new Object[]{}; 
                    break;
                }
                case 21: { // ???
                    comment = Karkkainen.this.getComment("Main.step13"); 
                    args = new Object[]{}; 
                    break;
                }
                case 22: { // Комментарий
                    comment = Karkkainen.this.getComment("Main.step23"); 
                    args = new Object[]{}; 
                    break;
                }
                case 24: { // ???
                    comment = Karkkainen.this.getComment("Main.step19"); 
                    args = new Object[]{new Integer(d.Main_j - 1), new Integer(d.suffArr[d.Main_i][d.Main_j - 1])}; 
                    break;
                }
                case 25: { // Комментарий
                    comment = Karkkainen.this.getComment("Main.step21"); 
                    args = new Object[]{}; 
                    break;
                }
                case 27: { // Шаг цикла
                    comment = Karkkainen.this.getComment("Main.step29"); 
                    args = new Object[]{new Integer(d.Main_j - 1), new Integer(d.suff23List[d.Main_i][d.invSuffArr[d.Main_i][d.Main_j - 1][0]][0])}; 
                    break;
                }
                case 28: { // Скрываем вспомогательные массивы
                    comment = Karkkainen.this.getComment("Main.step37364"); 
                    break;
                }
                case 29: { // Комментарий
                    comment = Karkkainen.this.getComment("Main.step31"); 
                    break;
                }
                case 31: { // Выписываем очередную пару
                    comment = Karkkainen.this.getComment("Main.step39"); 
                    args = new Object[]{}; 
                    break;
                }
                case 32: { // Сортировка пар
                    comment = Karkkainen.this.getComment("Main.step41"); 
                    break;
                }
                case 33: { // Комментарий
                    comment = Karkkainen.this.getComment("Main.step61"); 
                    break;
                }
                case 35: { // ???
                    comment = Karkkainen.this.getComment("Main.step49"); 
                    args = new Object[]{new Integer(3 * (d.Main_j - 1)), new Integer(d.pairsNumSt[d.Main_i][d.Main_j - 1][0])}; 
                    break;
                }
                case 36: { // ???
                    comment = Karkkainen.this.getComment("Main.step71"); 
                    args = new Object[]{}; 
                    break;
                }
                case 37: { // ???
                    if (d.recStr[d.Main_i - 1][d.recStr[d.Main_i - 1].length - 1] == 0) {
                        comment = Karkkainen.this.getComment("Main.if6.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if6.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 39: { // ???
                    comment = Karkkainen.this.getComment("Main.step911"); 
                    break;
                }
                case 41: { // ???
                    comment = Karkkainen.this.getComment("Main.step267"); 
                    break;
                }
                case 43: { // ???
                    comment = Karkkainen.this.getComment("Main.step2327"); 
                    break;
                }
                case 44: { // Сравнение символов
                    if (d.recStr[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] == d.recStr[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        comment = Karkkainen.this.getComment("Main.if90.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if90.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 46: { // Проверка на принадлежность к типам 1-2
                    if ((d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh) % 3 == 0 || (d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh) % 3 == 0) {
                        comment = Karkkainen.this.getComment("Main.if66.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if66.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 48: { // Проверка, не кончилавсь ли строка
                    if (d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh + 1 == d.recStr[d.Main_i - 1].length || d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh + 1 == d.recStr[d.Main_i - 1].length) {
                        comment = Karkkainen.this.getComment("Main.if316.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if316.false"); 
                    }
                    args = new Object[]{}; 
                    break;
                }
                case 50: { // ???
                    comment = Karkkainen.this.getComment("Main.step924"); 
                    break;
                }
                case 51: { // Сравнение номеров суффиксов
                    if (d.suff23Num[d.Main_i][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh] < d.suff23Num[d.Main_i][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh]) {
                        comment = Karkkainen.this.getComment("Main.if346.true"); 
                    } else {
                        comment = Karkkainen.this.getComment("Main.if346.false"); 
                    }
                    args = new Object[]{new Integer(d.suff23Num[d.Main_i][d.suff0List[d.Main_i][d.Main_l][0] + d.Main_sh]), new Integer(d.suff23Num[d.Main_i][d.suff23List[d.Main_i][d.Main_r][0] + d.Main_sh])}; 
                    break;
                }
                case 53: { // ???
                    comment = Karkkainen.this.getComment("Main.step7624"); 
                    break;
                }
                case 54: { // ???
                    comment = Karkkainen.this.getComment("Main.step7224"); 
                    break;
                }
                case 55: { // Сравнение символов
                    comment = Karkkainen.this.getComment("Main.step766"); 
                    args = new Object[]{new Integer(d.recStr[d.Main_i - 1][d.suff0List[d.Main_i][d.Main_tl][0] + d.Main_sh]), new Integer(d.recStr[d.Main_i - 1][d.suff23List[d.Main_i][d.Main_tr][0] + d.Main_sh])}; 
                    break;
                }
                case 57: { // Дописываем остаток
                    comment = Karkkainen.this.getComment("Main.step32415"); 
                    args = new Object[]{}; 
                    break;
                }
                case 58: { // Сокрытие ненужных массивов
                    comment = Karkkainen.this.getComment("Main.step15"); 
                    args = new Object[]{}; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = Karkkainen.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.draw();
                    break;
                }
                case 1: { // Перенумерация
                    d.visualizer.draw();
                    break;
                }
                case 3: { // Текущая строка
                    d.visualizer.draw();
                    break;
                }
                case 4: { // Проверка на то, что все символы различны
                    d.visualizer.draw();
                    break;
                }
                case 6: { // Дополнение нулем
                    d.visualizer.draw();
                    break;
                }
                case 8: { // Комментарий
                    d.visualizer.draw();
                    break;
                }
                case 10: { // Построение троек
                    d.visualizer.draw();
                    break;
                }
                case 11: { // Сортировка троек
                    d.visualizer.draw();                                                
                    break;
                }
                case 13: { // Выписываем номера троек типа 1
                    d.visualizer.draw();
                    break;
                }
                case 15: { // Выписываем номера троек типа 2
                    d.visualizer.draw();
                    break;
                }
                case 16: { // Комментарий
                    d.visualizer.draw();
                    break;
                }
                case 18: { // Непосредственное построение суффиксного массива
                    d.visualizer.draw();
                    break;
                }
                case 20: { // Начало шага рекурсии
                    d.visualizer.draw();
                    break;
                }
                case 21: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 22: { // Комментарий
                    d.visualizer.draw();
                    break;
                }
                case 24: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 25: { // Комментарий
                    d.visualizer.draw();
                    break;
                }
                case 27: { // Шаг цикла
                    d.visualizer.draw();
                    break;
                }
                case 28: { // Скрываем вспомогательные массивы
                    d.visualizer.draw();
                    break;
                }
                case 29: { // Комментарий
                    d.visualizer.draw();
                    break;
                }
                case 31: { // Выписываем очередную пару
                    d.visualizer.draw();
                    break;
                }
                case 32: { // Сортировка пар
                    d.visualizer.draw();
                    break;
                }
                case 33: { // Комментарий
                    d.visualizer.draw();
                    break;
                }
                case 35: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 36: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 37: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 39: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 41: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 43: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 44: { // Сравнение символов
                    d.visualizer.draw();
                    break;
                }
                case 46: { // Проверка на принадлежность к типам 1-2
                    d.visualizer.draw();
                    break;
                }
                case 48: { // Проверка, не кончилавсь ли строка
                    d.visualizer.draw();
                    break;
                }
                case 50: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 51: { // Сравнение номеров суффиксов
                    d.visualizer.draw();
                    break;
                }
                case 53: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 54: { // ???
                    d.visualizer.draw();
                    break;
                }
                case 55: { // Сравнение символов
                    d.visualizer.draw();
                    break;
                }
                case 57: { // Дописываем остаток
                    d.visualizer.draw();                                                                     
                    break;
                }
                case 58: { // Сокрытие ненужных массивов
                    d.visualizer.draw();
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.draw();
                    break;
                }
            }
        }
    }
}
