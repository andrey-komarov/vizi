package ru.ifmo.vizi.base.auto;

/**
 * �������� �������.
 *
 * @author  Georgiy Korneev
 * @version $Id: Automata.java,v 1.2 2006/05/16 10:15:47 geo Exp $
 */
public interface Automata {
    /**
     * ��������� � ��������� ���������.
     */
    public void toStart();

    /**
     * ��������� � �������� ���������.
     */
    public void toEnd();

    /**
     * ���������, ��������� �� ������� � ��������� ���������.
     *
     * @return ��������� �� ������� � ��������� ���������.
     */
    public boolean isAtStart();

    /**
     * ��������� ��������� �� ������� � �������� ���������.
     *
     * @return ��������� �� ������� � �������� ���������.
     */
    public boolean isAtEnd();

    /**
     * ���������� ����� �������� ����.
     *
     * @return ����� �������� ����.
     */
    public int getStep();

    /**
     * ������ ��� ������.
     *
     * @param level ������� ��������� � ������� �����������.
     */
    public void stepForward(int level);

    /**
     * ������ ��� �����.
     *
     * @param level ������� ��������� � ������� �����������.
     */
    public void stepBackward(int level);

    /**
     * ���������� ����������� � �������� ���������.
     *
     * @return ����������� � �������� ���������.
     */
    public String getComment();

    /**
     * ���������� ������� ���������.
     */
    public void drawState();

    /**
     * ��������� ������������� ��������� ��������.
     *
     * @param s ����� ��� ���������.
     * @return ����� <code>s</code>.
     */
    public StringBuffer toString(StringBuffer s);
}