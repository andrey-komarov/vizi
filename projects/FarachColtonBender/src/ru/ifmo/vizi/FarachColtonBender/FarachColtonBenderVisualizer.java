package ru.ifmo.vizi.FarachColtonBender;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.auto.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;
import ru.ifmo.vizi.base.widgets.Shape;

import ru.ifmo.vizi.FarachColtonBender.widgets.*;

import java.awt.*;
import java.util.Stack;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class FarachColtonBenderVisualizer extends Base implements AdjustmentListener, AutomataListener {
	/**
     * Segments tree automata instance.
	 */
	private final FarachColtonBender auto;

	/**
	 * Segments tree automata data
	 */
    private final FarachColtonBender.Data data;
    
    /**
     * Array shape style set
     */
    private ShapeStyle[] ArrayStyleSet;

    /**
     * Brackets shape style set
     */
    private ShapeStyle[] BracketsStyleSet;

    /**
     * Brackets shape style
     */
    private ShapeStyle[] IndicesStyleSet;

    /**
     * Count elements in the array
     */
    private AdjustablePanel elements;

    /**
     * Left bound of search
     */
    private AdjustablePanel leftBorder;

    /**
     * Right bound of search
     */
    private AdjustablePanel rightBorder;

    /**
     * Cells with array elements.
     * Vector of {@link Rect}.
     */
    private Stack cells;

    /**
     * Lines to show correspondence between parent and his sons
     * Vector of {@link Rect}.
     */
    private Stack lines;

    /**
     * Brackets to select interval of the search
     * Vector of {@link Rect}.
     */
    private Stack brackets;

    /**
     * List of minimums
     * Vector of {@link Rect}.
     */
    private Stack minimums;

    /**
     * Cells with indeces of array's elements
     */
    private Stack indices;

    /**
     * Cells with indices of array's elements (in the edit mode)
     */
    private Stack indices2;

    /**
     * Cells with indices of array's elements
     */
    private Stack indices3;


    /**
     * Textfields to edit the array
     */
    private Stack texts;

    /**
     * Maximal array value
     */
    private int maxValue;

    /**
     * Panel with buttons
     */
    private Panel panelBottom;

    /**
     * Min message tamplate
     */
    private String minMessage;

    /**
     * Rectangle that contains min value
     */
    private Rect rectMin;

    /**
     * Size of array. User define
     */
    private int naturalSize;

    /**
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;

    /**
     * Simbol of Infinity;
     */
    private final String INFINITY_SIMBOL = "\u221E";

    /**
     * Integer value determing infinity
     */
    private int Infinity;

    /**
     * If checked then we should visualize buiding the tree
     */
    private Checkbox checkboxBuilding;

    /**
     * Label of the checkbox
     */
    private String checkboxMessage;

    /**
     * Second big step
     */
    private int secondBigStep;

    /**
     * Turns visualizer to edit mode
     */
    private HintedButton buttonEdit;

    /**
     * Turns visualizer to show mode
     */
    private HintedButton buttonShow;

    /**
     * Control pane
     */
    private AutoControlsPane panelControl;

    /**
     * Shows if visualizer is in edit mode 
     */
    private boolean isInEditMode;

    /**
     * If we need to lock panel
     */
    private final boolean lockPanel = false;

    /**
     * Maximal array value string.
     */
    private final String maxValueString;



    /**
     * Returns state of the checkbox
     */
    public boolean showBuilding() {
    	return checkboxBuilding.getState();
    }


    /**
     * Set up second big step
     */
    public void setSecondBigStep() {
    	secondBigStep = auto.getStep();
    }


    /**
     * Draws necessary number of the brackets and paint it
     *
     * @param leftBorder determines upper left border
     * @param right Border determiens upper right border
     * @param highlight determines brackets which must be highlight
     * @param show determines if we need to show brackets
     */
    public void drawBrackets(int leftBorder, int rightBorder, boolean highlight, boolean show) {
    	// Левая граница, которой мы проходим по уровням до тукущего состояния, отрисовывая скобки
    	int l = data.left, r = data.right;

    	// Текущий уровень
    	int k = 0;

    	myBracket left = null, right = null;

    	// Пока мы ниже текущего уровня
    	while ((l >= leftBorder) && (l <= r) && (show)) {
    		left = (myBracket) brackets.elementAt(2 * k);
    		right = (myBracket) brackets.elementAt(2 * k + 1);

    		left.show();
    		right.show();

    		// Обработанные уровни
    		left.setStyle(0); 
    		right.setStyle(0);

    		// Переход на другой уровень
    		k++;
    		l = (l + 1) / 2;
    		r = (r - 1) / 2;
    	}

    	// Красим текущий уровень
    	if (left != null && highlight && leftBorder <= rightBorder) {
	    	left.setStyle(1);
    		right.setStyle(1);
    	}

    	// Стираем лишние скобки
    	while (2 * k + 1 <= brackets.size()) {
	   		left = (myBracket) brackets.elementAt(2 * k);
    		right = (myBracket) brackets.elementAt(2 * k + 1);

    		left.hide();
    		right.hide();

    		k++;
    	}
    }


    /**
     * Draws necessary number of the cells and paint it
     *
     * @param upBorder determines upper cells whick would be drawn
     * @param left determines current left border
     * @param right determines current right border
     * @param current determines current cell
     * @param currentMin determines current minimum cell
     * @param lastMin determines last element in minium history
     */
    public void drawCells(int upBorder, int left, int right, int current, int currentMin, int lastMin, boolean st) {
    	Rect r;
    	myLine leftLine, rightLine;

    	if (!st) {
    		for (int i = 0; i < data.h.length / 2; i++) {
    			r = (Rect) indices3.elementAt(i);
    			r.hide();
    		}
    		for (int i = 0; i < data.h.length / 2; i++) {
    			r = (Rect) cells.elementAt(i);
    			r.hide();

    			r = (Rect) indices.elementAt(i);
    			r.hide();


    			leftLine = (myLine) lines.elementAt(2 * i);
    			rightLine = (myLine) lines.elementAt(2 * i + 1);

    			leftLine.hide();
    			rightLine.hide();

    		}

    		for (int i = data.h.length / 2; i < data.h.length; i++) {
    			r = (Rect) cells.elementAt(i);
    			r.setStyle(0);
    		}

    		for (int i = data.h.length / 2; i < data.h.length; i++) {
    			r = (Rect) indices.elementAt(i);
                r.show();
    			r.setMessage(Integer.toString(i - data.h.length / 2 + 1));
    		}

    		return;
    	}

   		for (int i = data.h.length / 2; i < data.h.length; i++) {
   			r = (Rect) indices.elementAt(i);
   			r.setMessage(Integer.toString(i));
   		}

   		for (int i = 0; i < data.h.length / 2; i++) {
   			r = (Rect) indices3.elementAt(i);
   			r.show();
   		}

    	for (int i = 1; i < data.h.length / 2; i++) {
    			r = (Rect) cells.elementAt(i);
    			r.show();

    			r = (Rect) indices.elementAt(i);
    			r.show();

    			leftLine = (myLine) lines.elementAt(2 * i);
    			rightLine = (myLine) lines.elementAt(2 * i + 1);

    			leftLine.show();
    			rightLine.show();
    	}

    	// Прячем ту часть дерева, которую еще не построили
    	for (int i = 1; i < upBorder; i++) {
    		r = (Rect) cells.elementAt(i);
			r.setMessage("");
    	}

    	// Показываем ту часть дерева, которую уже построили
    	for (int i = upBorder; i < data.h.length; i++) {
    		r = (Rect) cells.elementAt(i);
			if (data.h[i] <= maxValue)
				r.setMessage(Integer.toString(data.h[i]));
			else
				r.setMessage(INFINITY_SIMBOL);				
    	}

    	// Красим все в "обычный цвет"
    	for (int i = 1; i < data.h.length; i++) {
    		r = (Rect) cells.elementAt(i);
    		r.setStyle(0);
    	}

    	// Выделяем левый и правый элементы
    	if (left <= data.h.length && left <= right) {
    		r = (Rect) cells.elementAt(left);
    		r.setStyle(1);

    		r = (Rect) cells.elementAt(right);
    		r.setStyle(1);
       }

    	// Красим "историю минимумов"
      	for (int i = 0; i < minimums.size(); i++) {
      		Integer k = (Integer) minimums.elementAt(i);
      		if (k.intValue() > lastMin || (current == right && k.intValue() == left)) {
      			r = (Rect) cells.elementAt(k.intValue());
      			r.setStyle(3);
      		}
      	}

       r = (Rect) cells.elementAt(current);
       r.setStyle(1);

       r = (Rect) cells.elementAt(currentMin);
       r.setStyle(2);
    }


    /**
     * Draws label with value of minimum
     *
     * @param show determines visibility of the message
     */
    public void drawMin(boolean show) {
    	if (show) {
    		rectMin.show();
        	if (data.a >= Infinity)
        		rectMin.setMessage(I18n.message(minMessage, INFINITY_SIMBOL));
        	else
    	    	rectMin.setMessage(I18n.message(minMessage, new Integer(data.a)));

        	rectMin.adjustSize();	
        } else {
        	rectMin.hide();
        }
    }


    /**
     * Rewinds algorithm to the specified step.
     *
     * @param step spet of the algorith to rewind to.
     */
    private void rewind(int step) {
        initAuto();

        while (!auto.isAtEnd() && auto.getStep() < step) {
            auto.stepForward(0);
        }
    }

    /**
     * Adjusts cells, brackets, lines, indices
     */
    private void adjustAll() {
        adjustCellsSize();
        adjustBracketsSize();
        adjustLinesSize();
        adjustIndicesSize();
    }

    /**
     * Adjusts textfields size
     */
    private void adjustTextFieldsSize() {
     	while (texts.size() > 0)
        	clientPane.remove((myTextField) texts.pop());
        while (texts.size() <= naturalSize) {
        	myTextField r = new myTextField(Integer.toString(data.h[data.h.length / 2 + texts.size() - 1]));
            texts.push(r);
            if (texts.size() != 1)
                clientPane.add(r);
        }
    }
	
    private void adjustIndices2() {
     	while (indices2.size() > 0)
     		clientPane.remove((Rect) indices2.pop());

     	while (indices2.size() <= naturalSize) {
     		Rect r = new Rect(IndicesStyleSet);
     		r.setMessage(Integer.toString(indices2.size()));
     		indices2.push(r);
     		clientPane.add(r);
     	}
    }

    /**
     * Reverses visualizer to show mode
     */
    private void toShowMode() {
    	if (lockPanel)
	    	panelControl.setEnabled(true);
     	isInEditMode = false;

   		buttonShow.hide();
    	buttonEdit.show();

    	for (int i = 1; i <= naturalSize; i++) {
    		myTextField r = (myTextField) texts.elementAt(i);
            String s = r.getText();
            for (int j = 0; j < s.length(); j++) {
            	if (!(s.charAt(j) <= '9' && s.charAt(j) >= '0')) {
                	s = "0";
                    break;
                }
            }
            
    		int k = Integer.parseInt(s);
    		k = maxValue < k ? maxValue : k;
    		k = 0 > k ? 0 : k;
    			
    		data.h[data.h.length / 2 + i - 1] = k;
    	}
     	clearTexts();
    	adjustAll();

       	initAuto();
       	clientPane.repaint();
       	panelBottom.doLayout();
       	clientPane.doLayout();                
    }


	/**
	 * Reverses visualizer to edit mode
	 */
    private void toEditMode() {
    	if (lockPanel)
	    	panelControl.setEnabled(false);
    	isInEditMode = true;
    	clearAll();
        adjustTextFieldsSize();
        adjustIndices2();
    	buttonEdit.hide();
        buttonShow.show();
        clientPane.repaint();
        panelBottom.doLayout();
        clientPane.doLayout();
     }


    /**
     * This method creates panel with visualizer controls.
     *
     * @return controls pane.
     */
    protected Component createControlsPane() {
        final Panel panel = new Panel(new GridLayout(0, 1));

        // Стандартные элементы управления.
        panelBottom = new Panel();
        panelBottom.add(new HintedButton(config, "button-random"){
            protected void click() {
            	setArraySize(naturalSize);
            }
        });

        buttonEdit = new HintedButton(config, "button-edit"){
            protected void click() {
            	toEditMode();
            }
        };

        buttonShow = new HintedButton(config, "button-show"){
            protected void click() {
            	toShowMode();
            }
        };

        if (config.getBoolean("button-ShowSaveLoad")) {
            panelBottom.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();
                    int[] h = auto.d.h;
                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("ArrayLengthComment"),
                            new Integer(elements.getMinimum()),
                            new Integer(elements.getMaximum())
                        )
                    ).append(" */\n");
                    buffer.append("ArrayLength = ").append(naturalSize).append("\n");

					buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("LeftBorderComment"), 
                            new Integer(1),
                            new Integer(elements.getValue())
                        )
                    ).append(" */\n");
                    buffer.append("LeftBorder = ").append(data.left - data.h.length / 2 + 1).append("\n");

					buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("RightBorderComment"), 
                            new Integer(1),
                            new Integer(elements.getValue())
                        )
                    ).append(" */\n");
                    buffer.append("RightBorder = ").append(data.right - data.h.length / 2 + 1).append("\n");

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("ElementsComment"),
                            new Integer(0),
                            new Integer(maxValue)
                        )
                    ).append(" */\n");

                    buffer.append("Elements = ");
                    for (int i = h.length / 2; i < h.length / 2 + naturalSize; i++) {
                        buffer.append(h[i]).append(" ");
                    }

                    buffer.append("\n/* ").append(
                        config.getParameter("StepComment")
                    ).append(" */\n");
                    buffer.append("Step = ").append(auto.getStep());
                    saveLoadDialog.show(buffer.toString());
                }
            });
        }

        saveLoadDialog = new SaveLoadDialog(config, forefather) {
            public boolean load(String text) throws Exception {
                SmartTokenizer tokenizer = new SmartTokenizer(text, config);
                tokenizer.expect("ArrayLength");
                tokenizer.expect("=");

                elements.setValue(tokenizer.nextInt(elements.getMinimum(), elements.getMaximum()));
                int[] h = data.h;

                tokenizer.expect("LeftBorder");
                tokenizer.expect("=");
                data.left = data.h.length / 2 + tokenizer.nextInt(1, elements.getValue()) - 1;
                leftBorder.setValue(data.left - data.h.length / 2 + 1);

                tokenizer.expect("RightBorder");
                tokenizer.expect("=");
                data.right = data.h.length / 2 + tokenizer.nextInt(data.left - data.h.length / 2 + 1, elements.getValue()) - 1;
                rightBorder.setValue(data.right - data.h.length / 2 + 1);

                tokenizer.expect("Elements");
                tokenizer.expect("=");
                for (int i = h.length / 2; i < h.length / 2 + naturalSize; i++) {
                    h[i] = tokenizer.nextInt(0, maxValue);
                }
                tokenizer.expect("Step");
                tokenizer.expect("=");
                int step = tokenizer.nextInt();
                tokenizer.expectEOF();

                auto.d.h = h;
                rewind(step);

                return true;
            }
        };


        // Изменение количества элементов массива и границ поиска.
        panelBottom.add(checkboxBuilding);

        panelBottom.add(buttonEdit);
        panelBottom.add(buttonShow);
        buttonShow.hide();

        Panel panelUp = new Panel(new BorderLayout());
        panelControl = new AutoControlsPane(config, auto, forefather, true);
        panelUp.add(panelControl, BorderLayout.CENTER);

        Panel panelCenter = new Panel(new BorderLayout());
        Panel panelCenterSmall = new Panel();

        panelCenterSmall.add(elements);        
        panelCenterSmall.add(leftBorder);
        panelCenterSmall.add(rightBorder);

        panelCenter.add(panelCenterSmall, BorderLayout.CENTER);

        panel.add(panelUp);
        panel.add(panelCenter);
        panel.add(panelBottom);

        return panel;
    }


    /**
     * This method build tree
     */
    private void buildTree() {
        for (int i = data.h.length / 2 - 1; i >= 1; i--)
            data.h[i] = data.h[2 * i] < data.h[2 * i + 1] ? data.h[2 * i] : data.h[2 * i + 1];
    }


    /**
     * Randomizes array values.
     */
    private void randomize() {
        for (int i = data.h.length / 2; i < data.h.length / 2 + naturalSize; i++)
            data.h[i] = (int) (Math.random() * maxValue) + 1;

        for (int i = data.h.length / 2 + naturalSize; i < data.h.length; i++)
            data.h[i] = Infinity;

        buildTree();
    }


	/**
	 * This method computes 2 raised to the power p
	 *
	 * @return 2 raised to the power p
	 */
    private int pow(int p) {
    	return (int) Math.pow(2, p);
    }

    /**
     * Removes all component from the applet
     */
    private void clearAll() {
        while (cells.size() > 0)
        	clientPane.remove((Rect) cells.pop());
        while (brackets.size() > 0)
        	clientPane.remove((myBracket) brackets.pop());
        while (lines.size() > 0)
        	clientPane.remove((myLine) lines.pop());
        while (indices.size() > 0)
        	clientPane.remove((Rect) indices.pop());
        while (indices3.size() > 0)
        	clientPane.remove((Rect) indices3.pop());
    }


    /**
     * Removes textfields from the applet
     */
    private void clearTexts() {
    	while (texts.size() > 0) 
    		clientPane.remove((myTextField) texts.pop());
    	while (indices2.size() > 0) 
    		clientPane.remove((Rect) indices2.pop());
    }

    /**
     * Adjusts cells size
     */
    private void adjustCellsSize() {

        // Удаляем, если их слишком много
        while (cells.size() > 0)
        	clientPane.remove((Rect) cells.pop());

		// Добавляем, если нехватает
        while (cells.size() < data.h.length) {
        	Rect r = new Rect(ArrayStyleSet);
        	cells.push(r);

        	clientPane.add(r);
        }

        // Заполняем содержимое
        for (int i = 1; i < data.h.length; i++) {
        	Rect r = (Rect) cells.elementAt(i);
        	if (data.h[i] == Infinity)
        		r.setMessage(INFINITY_SIMBOL);
        	else
	        	r.setMessage("" + data.h[i]);
        }
    }


    /**
     * Adjusts brackets size
     */
    private void adjustBracketsSize() {
    	int l = data.left;
    	int r = data.right;
    	int k = 0;

        // Удаляем, если их слишком много
        while (brackets.size() > 0)
        	clientPane.remove((myBracket) brackets.pop());

    	// Добавляем, если нехватает
        while (brackets.size() < 2 * data.height) {
        	myBracket left = new myBracket(BracketsStyleSet);
        	myBracket right = new myBracket(BracketsStyleSet);

        	brackets.push(left);
        	brackets.push(right);

        	clientPane.add(left);
        	clientPane.add(right);
        }

        // Присоединяем каждую скобку к конкретной ячейке
        while (l <= r) {
			myBracket left = (myBracket) brackets.elementAt(2 * k);
			myBracket right = (myBracket) brackets.elementAt(2 * k + 1);

			left.setNumb(l);
			right.setNumb(r);
        	
        	k++;
        	l = (l + 1) / 2;
        	r = (r - 1) / 2;
        }
    }


    /**
     * Adjusts lines size
     */
    private void adjustLinesSize() {
    	// Добавляем, если нехватает
        while (lines.size() < data.h.length) {
        	myLine l = new myLine(ArrayStyleSet);
        	lines.push(l);

        	clientPane.add(l);
        }

        // Удаляем, если их слишком много
        while (lines.size() > data.h.length) 
        	clientPane.remove((myLine) lines.pop());
    }


    /**
     * Adjusts indices size
     */
    private void adjustIndicesSize() {
    	while (indices3.size() < data.h.length / 2) {
        	Rect r = new Rect(IndicesStyleSet);
        	r.setMessage(Integer.toString(indices3.size() + 1));
        	indices3.push(r);

        	clientPane.add(r);
    	}

    	// Добавляем, если нехватает
        while (indices.size() < data.h.length) {
        	Rect r = new Rect(IndicesStyleSet);
        	indices.push(r);

        	clientPane.add(r);
        }

        // Удаляем, если их слишком много
        while (indices.size() > data.h.length) 
        	clientPane.remove((Rect) indices.pop());

        for (int i = 0; i < data.h.length; i++) {
        	Rect r = (Rect) indices.elementAt(i);
        	r.setMessage("" + i);
        }
    }


    /**
     * Sets new array size.
     *
     * @param size new array size.
     */
    private void setArraySize(int arraySize) {
        data.height = 1;
        while (pow(data.height - 1) < arraySize)
            data.height++;
        data.h = new int[pow(data.height)];

        // Устаанвливаем границы поиска
        data.left = data.h.length / 2 + leftBorder.getValue() - 1;
        data.right = data.h.length / 2 + rightBorder.getValue() - 1;

        randomize();
        if (data.right < data.h.length && data.left < data.h.length) {
	        adjustAll();
        }

        if (isInEditMode)
			toEditMode();
        else
        	if (data.right < data.h.length && data.left < data.h.length)
		        initAuto();
    }


    /**
     * Builds list of item which renews current minimum
     */
    private void getMinimums() {
    	int l = data.left, r = data.right, a = Infinity;

    	while (minimums.size() > 0)
    		minimums.pop();

    	while (l <= r) {
    		if (data.h[l] < a) {
    			minimums.push(new Integer(l));
    			a = data.h[l];
    		}

    		if (data.h[r] < a) {
    			minimums.push(new Integer(r));
    			a = data.h[r];
    		}
    		
    		l = (l + 1) / 2;
    		r = (r - 1) / 2;
    	}
    }


    /**
     * Invoked when client pane shoud be layouted.
     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */
    protected void layoutClientPane(int clientWidth, int clientHeight) {
    
    	// Ширина ячейки
        int width = (int) Math.round(clientWidth / ((naturalSize + 1) * 1.2));

        // Высота ячейки
        int height = Math.round(clientHeight / (data.height * 2 + 2));

        width = Math.min(width, height);
        height = Math.min(width, height);
        int iWidth = (int) Math.round(width * 1.2);

    	if (isInEditMode) {
        	int x = (clientWidth - naturalSize * iWidth) / 2;
        	int y = height / 2 + height * 2 * (data.height - 1);
            
        	for (int i = 1; i <= naturalSize; i++) {
            	myTextField r = (myTextField) texts.elementAt(i);
                r.setSize(width, height);
                r.setBounds(x + (i - 1) * iWidth, y, width, height);
                r.adjustFontSize(maxValueString);

                Rect ind = (Rect) indices2.elementAt(i);
                ind.setSize(width, height);
                ind.setBounds(x + (i - 1) * iWidth, y + height, width, height);
                ind.adjustFontSize();
            }
        	return;
        }



        int i = 1;
        int level = 0;
        while (i < cells.size()) {

        	int n = pow(level);

        	int hole = (pow(data.height - level - 1) - 1) * iWidth;
        	int x = Math.round((clientWidth - (n * iWidth + (n - 1) * hole)) / 2);

            int y = height / 2 + height * 2 * level;

            int wb = (iWidth - width) / 2;

            // Расположение скобок.
            if (brackets.size() / 2 >= data.height - level) {
                myBracket leftBracket = (myBracket) brackets.elementAt(2 * (data.height - level - 1));
                myBracket rightBracket = (myBracket) brackets.elementAt(2 * (data.height - level - 1) + 1);


                int bx = x + (leftBracket.getNumb() - pow(level)) * (iWidth + hole);
                leftBracket.setParams(bx, y - wb, width, wb);

                bx = x + (rightBracket.getNumb() - pow(level)) * (iWidth + hole) + width - wb + 1;
                rightBracket.setParams(bx, y - wb, width, wb);
            }

            // Расположение ячеек и линий
            int j = 0;
            while (j < pow(level)) {
                int cx = x + j * iWidth + j * hole;

                Rect rect = (Rect) cells.elementAt(i);
                rect.setBounds(cx + (iWidth - width) / 2, y, width + 1, height);
                rect.adjustFontSize(maxValueString);                


                rect = (Rect) indices.elementAt(i);
                rect.setBounds(cx + (iWidth - width) / 2, y + height, width + 1, height);
                rect.adjustFontSize(maxValueString);


                Font f = rect.getLook().getTextFont(IndicesStyleSet[0]);
                Font f2 = new Font(f.getName(), f.getStyle(), f.getSize() - 4);
                rect.getLook().setTextFont(f2);


                cx = cx + iWidth / 2;
                myLine l = (myLine) lines.elementAt(i);
                if (i > 1) {
                	if (i % 2 == 0)
                		l.setPoints(cx, y, cx + iWidth / 2 + hole / 2, y - height);
                	else
	               		l.setPoints(cx, y, cx - iWidth / 2 - hole / 2, y - height);
	            }
                j++;
                i++;
            }
            
            level++;
        }


        level--;
        int n = pow(level);

    	int hole = (pow(data.height - level - 1) - 1) * iWidth;
    	int x = Math.round((clientWidth - (n * iWidth + (n - 1) * hole)) / 2);

        int y = height / 2 + height * 2 * level;

        int wb = (iWidth - width) / 2;
        int j = 0;
        while (j < data.h.length / 2) {
            int cx = x + j * iWidth + j * hole;

            Rect rect = (Rect) indices3.elementAt(j);
            rect.setBounds(cx + (iWidth - width) / 2, y + 2 * height, width + 1, height);
            rect.adjustFontSize(maxValueString);
            Font f = rect.getLook().getTextFont(IndicesStyleSet[0]);
            Font f2 = new Font(f.getName(), f.getStyle(), f.getSize() - 4);
            rect.getLook().setTextFont(f2);

            j++;
            i++;
        }
    }


    /**
     * Initializes the auto
     */
    private void initAuto() {
    	if (data.right >= data.h.length || data.left >= data.h.length)
        	return;

    	buildTree();
    	getMinimums();

    	data.left = data.h.length / 2 + leftBorder.getValue() - 1;
    	data.right = data.h.length / 2 + rightBorder.getValue() - 1;

    	data.lowCellsBound = data.h.length / 2;

        adjustAll();

        secondBigStep = Integer.MAX_VALUE;

    	auto.toStart();
    	clientPane.doLayout();
    }


    /**
     * Creates a new FarachColtonBender visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public FarachColtonBenderVisualizer(VisualizerParameters parameters) {
        super(parameters);


        ArrayStyleSet = ShapeStyle.loadStyleSet(config, "array");
        BracketsStyleSet = ShapeStyle.loadStyleSet(config, "brackets");
        IndicesStyleSet = ShapeStyle.loadStyleSet(config, "indices");

        auto = new FarachColtonBender(locale);

        data = auto.d;
        data.visualizer = this;

        maxValue = config.getInteger("max-value");
        maxValueString = config.getParameter("max-value-string", Integer.toString(maxValue));
        Infinity = Integer.MAX_VALUE;

        cells = new Stack();
        lines = new Stack();
        brackets = new Stack();
        indices = new Stack();
        indices3 = new Stack();
        indices2 = new Stack();
        minimums = new Stack();
        texts = new Stack();

        minMessage = config.getParameter("min-message");

        checkboxMessage = config.getParameter("checkbox-message");

        rectMin = new Rect(
                new ShapeStyle[]{new ShapeStyle(config, "min-style", ArrayStyleSet[0])},
                "min"
        );

        clientPane.add(rectMin);
        rectMin.adjustSize();
        rectMin.setLocation(10, 10);

        checkboxBuilding = new Checkbox(checkboxMessage, true);

        leftBorder = new AdjustablePanel(config, "left-border");
        leftBorder.addAdjustmentListener(this);

        rightBorder = new AdjustablePanel(config, "right-border");
        rightBorder.addAdjustmentListener(this);

        elements = new AdjustablePanel(config, "elements");
        elements.addAdjustmentListener(this);

        naturalSize = elements.getValue();
        setArraySize(elements.getValue());

        createInterface(auto);
    }


    /**
     * Sets new element size
     */
    private void setElementsSize() {
        naturalSize = elements.getValue();

        setArraySize(naturalSize);

        leftBorder.setMinimum(1);
        leftBorder.setValue(1);

        rightBorder.setMaximum(naturalSize);
        rightBorder.setValue(naturalSize);
    }

    /**
     * Invoked on adjustment event.
     *
     * @param event event to process.
     */
    public void adjustmentValueChanged(AdjustmentEvent event) {
        if (event.getSource() == elements) {
	        setElementsSize();
		}

        if (event.getSource() == leftBorder) {
            data.left = data.h.length / 2 + leftBorder.getValue() - 1;
            if (!isInEditMode) {
               	if (data.right < data.h.length) {
                	adjustBracketsSize();
                    if (auto.getStep() >= secondBigStep) {
                        initAuto();
                        auto.stepForward(1);
                        auto.stepForward(1);
                    } else {
                        initAuto();
                    }
            	}
            }

            rightBorder.setMinimum(leftBorder.getValue());
        }

        if (event.getSource() == rightBorder) {
            data.right = data.h.length / 2 + rightBorder.getValue() - 1;
            if (!isInEditMode) {
                if (data.left < data.h.length) {
                	adjustBracketsSize();
                    if (auto.getStep() >= secondBigStep) {
                        initAuto();
                        auto.stepForward(1);
                        auto.stepForward(1);
                    } else
                        initAuto();
                }
            }
            leftBorder.setMaximum(rightBorder.getValue());
        }
    }

    public void stateChanged() {
    	if (isInEditMode)
    		toShowMode();
    	super.stateChanged();
    }

    /**
     * Makes small step forward.
     */
    public void doNextStep() {
    	if (!isInEditMode)
	        if (!auto.isAtEnd()) 
    	        auto.stepForward(0);
    }

    /**
     * Makes big step forward.
     */
    public void doNextBigStep() {
    	if (!isInEditMode)
	        if (!auto.isAtEnd()) 
    	        auto.stepForward(1);
    }

    /**
     * Makes small step backward.
     */
    public void doPrevStep() {
    	if (!isInEditMode)
	        if (!auto.isAtStart())
    	        auto.stepBackward(0);
    }

    /**
     * Makes big step backward.
     */
    public void doPrevBigStep() {
    	if (!isInEditMode)
	        if (!auto.isAtStart()) 
    	        auto.stepBackward(1);
    }
}