package ru.ifmo.vizi.base.auto;

/**
 *  онечный автомат.
 *
 * @author  Georgiy Korneev
 * @version $Id: Automata.java,v 1.2 2006/05/16 10:15:47 geo Exp $
 */
public interface Automata {
    /**
     * ѕереходит в начальное состо€ние.
     */
    public void toStart();

    /**
     * ѕереходит в конечное состо€ние.
     */
    public void toEnd();

    /**
     * ѕровер€ет, находитс€ ли автомат в начальном состо€нии.
     *
     * @return находитс€ ли автомат в начальном состо€нии.
     */
    public boolean isAtStart();

    /**
     * ѕровер€ет находитс€ ли автомат в конечном состо€нии.
     *
     * @return находитс€ ли автомат в конечном состо€нии.
     */
    public boolean isAtEnd();

    /**
     * ¬озвращает номер текущего шага.
     *
     * @return номер текущего шага.
     */
    public int getStep();

    /**
     * ƒелает шаг вперед.
     *
     * @param level уровень состо€ни€ в котором остановитс€.
     */
    public void stepForward(int level);

    /**
     * ƒелает шаг назад.
     *
     * @param level уровень состо€ни€ в котором остановитс€.
     */
    public void stepBackward(int level);

    /**
     * ¬озвращает комментарий к текущему состо€нию.
     *
     * @return комментарий к текущему состо€нию.
     */
    public String getComment();

    /**
     * ќтображает текущее состо€ние.
     */
    public void drawState();

    /**
     * —троковое представление состо€ни€ автомата.
     *
     * @param s буфер дл€ состо€ни€.
     * @return буфер <code>s</code>.
     */
    public StringBuffer toString(StringBuffer s);
}