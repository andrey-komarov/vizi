package ru.ifmo.vizi.base.auto;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Locale;

/**
 * ������� ����� ��� �������� � �������������� ����������.
 *
 * @author Georgiy Korneev
 * @version $Id: BaseAutoReverseAutomata.java,v 1.2 2006/05/16 10:15:47 geo Exp $
 */
public abstract class BaseAutoReverseAutomata extends BaseAutomataWithListener {
    /**
     * ������� ����� ������� � �������������� ����������.
     * ����� �������������� ������� ������ ���� ������������������ �������
     * {@link #init}.
     *
     * @param commentFile ��� ����� � �������������.
     * @param locale ������ ��� ������������.
     */
    public BaseAutoReverseAutomata(String commentFile, Locale locale) {
        super(commentFile, locale);
    }

    /**
     * �������� ������ ������������.
     */
    protected void startSection() {
        stack.push(null);
    }

    /**
     * ��������������� ������������ � ������.
     */
    protected void restoreSection() {
        Object o = stack.pop();
        while (o != null) {
            if (o instanceof StoredField) {
                ((StoredField) o).restore();
            } else if (o instanceof StoredElement) {
                ((StoredElement) o).restore();
            } else {
                System.err.println("Stack corrupted");
            }
            o = stack.pop();
        }
    }
    /**
     * ���������� ���� �������.
     * @param object ������.
     * @param field ��� ����.
     */
    protected void storeField(Object object, String field) {
        stack.push(new StoredField(object, field));
    }

    /**
     * ��������� ������� �������.
     * @param array ������.
     * @param index ������ ��������.
     */
    protected void storeArray(Object array, int index) {
        stack.push(new StoredElement(array, index));
    }

    /**
     * ����������� �������� ����.
     */
    private final static class StoredField {
        /**
         * ������, ���� �������� ���������.
         */
        private final Object object;

        /**
         * ����, ������� ���������.
         */
        private final Field field;

        /**
         * ����������� ��������.
         */
        private final Object value;

        /**
         * ��������� ����� �������� ����.
         * @param object ������, ���� �������� ������� ���������.
         * @param field ��� ������������ ����.
         */
        private StoredField(Object object, String field) {
            this.object = object;
            try {
                this.field = object.getClass().getField(field);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Fail: Field not found " + object.getClass().getName() + " " + field);
            }
            try {
                this.value = this.field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Fail: Illegal access " + object.getClass().getName() + " " + field);
            }
        }

        /**
         * ��������������� ����������� ����.
         */
        private void restore() {
            try {
                field.set(object, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Fail: Illegal access " + object + " " + field);
            }
        }
    }

    /**
     * ����������� ������� �������.
     */
    private final static class StoredElement {
        /**
         * ������, ������� �������� ��������.
         */
        private final Object array;

        /**
         * ������ ������������ �������� � �������.
         */
        private final int index;

        /**
         * ����������� ��������.
         */
        private final Object value;

        /**
         * ��������� ����� ������� �������.
         * @param array ������, ������� �������� ������� ���������.
         * @param index ������ ������������ ��������.
         */
        private StoredElement(Object array, int index) {
            this.array = array;
            this.index = index;
            value = Array.get(array, index);
        }

        /**
         * ��������������� ���������� ��������.
         */
        private void restore() {
            Array.set(array, index, value);
        }
    }
}
