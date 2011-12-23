package ru.ifmo.vizi.base.auto;

/**
 * ������� �������������� ��������� �������.
 *
 * @author Georgiy Korneev
 * @version $Id: AutomataWithListener.java,v 1.2 2004/06/07 13:58:33 geo Exp $
 */
public interface AutomataWithListener extends Automata {
    /**
     * ��������� ���������� �������.
     *
     * @param listener ���������� �������.
     */
    public void addListener(AutomataListener listener);

    /**
     * ������� ���������� �������.
     *
     * @param listener ���������� �������.
     */
    public void removeListener(AutomataListener listener);

    /**
     * �������� ���������� ��������.
     *
     * @return ���������� ��������.
     */
    public AutomataController getController();
}
