package ru.ifmo.vizi.base.auto;

import java.util.*;

/**
 * ������� ����� ��� �������� � ������������� �������.
 *
 * @author Georgiy Korneev
 * @version $Id: BaseAutomataWithListener.java,v 1.7 2006/05/16 10:15:47 geo Exp $
 */
public abstract class BaseAutomataWithListener implements AutomataWithListener {
    /** 
     * ������� ���� call-auto.
     */
    public final static int CALL_AUTO_LEVEL = -10000;

    /**
     * �������.
     */
    private Automata automata;

    /**
     * ������ ������.
     */
    private Object data;

    /**
     * ���������� �������.
     */
    private final Vector listeners;

    /**
     * ���� ���������.
     */
    protected final AutoStack stack;

    /**
     * ����� �������� ����.
     */
    protected int step;

    /**
     * �����������.
     */
    private ResourceBundle bundle;

    /**
     * ���������� ��������.
     */
    private final AutomataController controller;

    /**
     * ������� ����� ������� � ������������� �������.
     * ����� �������������� ������� ������ ���� ������������������ �������
     * {@link #init}.
     *
     * @param commentFile ��� ����� � �������������.
     * @param locale ������ ��� ������������.
     */
    public BaseAutomataWithListener(String commentFile, Locale locale) {
        try {
            bundle = ResourceBundle.getBundle(commentFile, locale);
        } catch (MissingResourceException e) {
            try {
                bundle = ResourceBundle.getBundle(commentFile, Locale.US);
            } catch (MissingResourceException ex) {
                System.err.println("Cannot find bundle " + commentFile);
                bundle = null;
            }
        }

        listeners = new Vector();
        stack = new AutoStack();
        controller = new AutomataController(this);
    }

    /**
     * �������������� �������.
     *
     * @param automata �������.
     * @param data ������ ��� ��������.
     */
    protected void init(Automata automata, Object data) {
        this.automata = automata;
        this.data = data;
    }

    /**
     * ��������� � ��������� ���������.
     */
    public void toStart() {
        step = 0;
        automata.toStart();
        fireStateChanged();
    }

    /**
     * ��������� � �������� ���������.
     */
    public void toEnd() {
        automata.toEnd();
        fireStateChanged();
    }

    /**
     * ���������, ��������� �� ������� � ��������� ���������.
     *
     * @return ��������� �� ������� � ��������� ���������.
     */
    public boolean isAtStart() {
        return automata.isAtStart();
    }

    /**
     * ��������� ��������� �� ������� � �������� ���������.
     *
     * @return ��������� �� ������� � �������� ���������.
     */
    public boolean isAtEnd() {
        return automata.isAtEnd();
    }

    /**
     * ������ ��� ������.
     *
     * @param level ������� ��������� � ������� �����������.
     */
    public void stepForward(int level) {
        automata.stepForward(level);
        fireStateChanged();
    }

    /**
     * ������ ��� �����.
     *
     * @param level ������� ��������� � ������� �����������.
     */
    public void stepBackward(int level) {
        automata.stepBackward(level);
        fireStateChanged();
    }

    /**
     * ���������� ����� �������� ����.
     *
     * @return ����� �������� ����.
     */
    public int getStep() {
        return automata.getStep();
    }

    /**
     * ���������� ����������� � �������� ���������.
     *
     * @return ����������� � �������� ���������.
     */
    public String getComment() {
        return automata.getComment();
    }

    /**
     * ���������� ����������� �� ��������������.
     *
     * @param id ������������� �����������.
     * @return �����������.
     */
    protected String getComment(String id) {
        if (bundle == null) {
            return "";
        }
        try {
            return bundle.getString(id);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * ���������� ������� ���������.
     */
    public void drawState() {
        automata.drawState();
    }

    /**
     * ��������� ���������� �������.
     *
     * @param listener ���������� �������.
     */
    public void addListener(AutomataListener listener) {
        if (listener != null) {
            synchronized (listeners) {
                if (!listeners.contains(listener)) {
                    listeners.addElement(listener);
                }
            }
        }
    }

    /**
     * ������� ���������� �������.
     *
     * @param listener ���������� �������.
     */
    public void removeListener(AutomataListener listener) {
        if (listener != null) {
            synchronized (listeners) {
                if (listeners.contains(listener)) {
                    listeners.removeElement(listener);
                }
            }
        }
    }

    /**
     * �������� ����������� �������.
     */
    public void fireStateChanged() {
        synchronized (listeners) {
            for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
                AutomataListener listener = (AutomataListener) e.nextElement();
                listener.stateChanged();
            }
        }
    }

    /**
     * �������� ���������� ��������.
     *
     * @return ���������� ��������.
     */
    public AutomataController getController() {
        return controller;
    }

    /**
     * ��������� ������������� ��������� ��������.
     *
     * @return ��������� ������������� ��������� ��������.
     */
    public String toString() {
        return toString(new StringBuffer()).toString();
    }

    /**
     * ��������� ������������� ��������� ��������.
     *
     * @param s ����� ��� ���������.
     *
     * @return ����� <code>s</code>.
     */
    public StringBuffer toString(StringBuffer s) {
        s.append("Step: ").append(getStep()).append("\n");
        automata.toString(s);
        s.append("\n");
        s.append("Stack (").append(stack.size()).append(") ").append(stack).append("\n");
        s.append("--------------------\n");
        s.append(automata.getComment()).append("\n\n");
        s.append(data);
        return s;
    }

    /**
     * ������� ����� ��� ��������.
     *
     * @author Georgiy Korneev
     * @version $Id: BaseAutomataWithListener.java,v 1.7 2006/05/16 10:15:47 geo Exp $
     */
    public abstract class BaseAutomata implements Automata {
        /**
         * �������� ��������.
         */
        private final String name;

        /**
         * ��������� ��������� ��������.
         */
        private final int startState;

        /**
         * �������� ��������� ��������.
         */
        private final int endState;

        /**
         * �������� ���������.
         */
        private final String[] descriptions;

        /**
         * ������ ���������.
         */
        private final int[] levels;

        /**
          * ������� ��������� ��������.
          */
        protected int state;

        /**
          * ������� ��������� �������.
          */
        protected Automata child;

        /**
         * ������� ����� �������.
         * 
         * @param name �������� ��������.
         * @param startState ��������� ��������� ��������.
         * @param endState �������� ��������� ��������.
         * @param descriptions ������� ��������� ��������.
         * @param levels ������ ��������� ��������.
         */
        protected BaseAutomata(String name, int startState, int endState, 
                String[] descriptions, int[] levels) 
        {
            this.name = name;
            this.startState = startState;
            this.endState = endState;
            this.descriptions = descriptions;
            this.levels = levels;
        }

        /**
         * ��������� � ��������� ���������.
         */
        public void toStart() {
            state = startState; 
            child = null; 
        }

        /**
         * ��������� � �������� ���������.
         */
        public void toEnd() {
            state = endState; 
            child = null; 
        }

        /**
         * ���������, ��������� �� ������� � ��������� ���������.
         *
         * @return ��������� �� ������� � ��������� ���������.
         */
        public boolean isAtStart() {
            return state == startState; 
        }

        /**
         * ��������� ��������� �� ������� � �������� ���������.
         *
         * @return ��������� �� ������� � �������� ���������.
         */
        public boolean isAtEnd() {
            return state == endState; 
        }

        /**
         * ���������� ����� �������� ����.
         *
         * @return ����� �������� ����.
         */
        public int getStep() {
            return step;
        }

        /**
         * ������ ��� ������.
         *
         * @param level ������� ��������� � ������� �����������.
         */
        public void stepForward(int level) {
            do {
                step++;
                doStepForward(level);
            } while (!isInteresting(level));
        }

        /**
         * ������ ��� �����.
         *
         * @param level ������� ��������� � ������� �����������.
         */
        public void stepBackward(int level) {
            do {
                doStepBackward(level);
                step--; 
            } while (!isInteresting(level));
        }

        /**
          * ��������� �� ������� ���������.
          */
        private boolean isInteresting(int level) {
            if (levels[state] != CALL_AUTO_LEVEL) {
                return (levels[state] >= level);
            } else {
                return child != null && !child.isAtEnd();
            }
        }

        /**
         * ���������� ����������� � �������� ���������.
         *
         * @return ����������� � �������� ���������.
         */
        public abstract String getComment();

        /**
         * ���������� ������� ���������.
         */
        public abstract void drawState();

        /**
         * ��������� ������������� ��������� ��������.
         *
         * @param s ����� ��� ���������.
         * @return ����� <code>s</code>.
         */
        public StringBuffer toString(StringBuffer s) {
            s.append(name).append(' ').append(state).append(' '); 
            s.append('('); 
            s.append(descriptions[state]); 
            s.append(")\n"); 
            if (child != null && !child.isAtStart() && !child.isAtEnd()) {
                child.toString(s); 
            }
            return s; 
        }

        /**
         * ������ ���� ��� �������� ������.
         *
         * @param level ������� ��������� � ������� �����������.
         */
        protected abstract void doStepForward(int level);

        /**
         * ������ ���� ��� �������� �����.
         *
         * @param level ������� ��������� � ������� �����������.
         */
        protected abstract void doStepBackward(int level);

    }
}
