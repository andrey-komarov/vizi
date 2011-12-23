package ru.ifmo.vizi.quick_sort;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Stack;

public final class QuickSortVisualizer 
    extends Base 
    implements AdjustmentListener
{
    private final QuickSort auto;
    private final QuickSort.Data data;
	private final AdjustablePanel elements;
    private final ShapeStyle[] styleSet1;
    private final ShapeStyle[] styleSet2;
    private SaveLoadDialog saveLoadDialog;
	private final Stack cells;
	private final Stack leftBord;
	private final Stack rightBord;
	private final Stack index;
	private final int maxValue;
	private final String maxValueString;
	private final Rect rectBar;
	private final Rect rectBord;
	private final String barMessage;
	private final String bordersMessage;
	private int barWidth;
	private int barHeight;
		
	public QuickSortVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new QuickSort(locale);
        data = auto.d;
        data.visualizer = this;
		cells = new Stack();
		leftBord = new Stack();
		rightBord = new Stack();
		index = new Stack();
		maxValue = config.getInteger("max-value");
        maxValueString = config.getParameter("max-value-string", Integer.toString(maxValue));
		
		
		styleSet1 = ShapeStyle.loadStyleSet(config, "array");
		styleSet2 = ShapeStyle.loadStyleSet(config, "indexes");
		barMessage = config.getParameter("bar-message");
		rectBar = new Rect(
                new ShapeStyle[]{new ShapeStyle(config, "bar-style", styleSet1[0])},
                "Барьер = 0"
        );
		rectBar.adjustFontSize();
		
		bordersMessage = config.getParameter("borders-message");
		rectBord = new Rect(
                new ShapeStyle[]{new ShapeStyle(config, "borders-style", styleSet1[0])},
                bordersMessage
        );
        rectBord.adjustFontSize();
		
        elements = new AdjustablePanel(config, "elements");
        elements.addAdjustmentListener(this);
	
        setArraySize(elements.getValue());
		randomize();
		
		createInterface(auto);
	}
	
	public Component createControlsPane() {
        Container panel = new Panel(new BorderLayout());

        panel.add(new AutoControlsPane(config, auto, forefather, false), BorderLayout.CENTER);

        Panel bottomPanel = new Panel();
        bottomPanel.add(new HintedButton(config, "button-random"){
            protected void click() {
                randomize();
            }
        });
		
		if (config.getBoolean("button-ShowSaveLoad")) { 
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();
                    int[] a = auto.d.array;
                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("ArrayLengthComment"), 
                            new Integer(elements.getMinimum()),
                            new Integer(elements.getMaximum())
                        )
                    ).append(" */\n");

                    buffer.append("ArrayLength = ").append(a.length).append("\n");

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("ElementsComment"), 
                            new Integer(0),
                            new Integer(maxValue)
                        )
                    ).append(" */\n");

                    buffer.append("Elements = ");
                    for (int i = 0; i < a.length; i++) {
                        buffer.append(a[i]).append(" ");
                    }

                    buffer.append("\n/* ").append(
                        config.getParameter("StepComment")
                    ).append(" */\n");
                    buffer.append("Step = ").append(auto.getStep());
                    saveLoadDialog.show(buffer.toString());
                }
            });
        }
		
        bottomPanel.add(elements);
        panel.add(bottomPanel, BorderLayout.SOUTH);
	
		saveLoadDialog = new SaveLoadDialog(config, forefather) {
            public boolean load(String text) throws Exception {
                SmartTokenizer tokenizer = new SmartTokenizer(text, config);
                tokenizer.expect("ArrayLength");
                tokenizer.expect("=");
                int[] a = new int[tokenizer.nextInt(
                    elements.getMinimum(), 
                    elements.getMaximum()
                )];

                tokenizer.expect("Elements");
                tokenizer.expect("=");
                for (int i = 0; i < a.length; i++) {
                    a[i] = tokenizer.nextInt(0, maxValue);
                }

                tokenizer.expect("Step");
                tokenizer.expect("=");
                int step = tokenizer.nextInt();
                tokenizer.expectEOF();

                setArraySize(a.length);
                auto.d.array = a;                
                auto.getController().rewind(step);

                return true;
            }
        };
		
        return panel;
    }
	
	private void randomize() {
        for (int i = 0; i < data.array.length; i++) {
            data.array[i] = (int) (Math.random() * maxValue) + 1;
        }
        auto.getController().doRestart();
    }
	
	private void adjustArraySize() {
        int size = auto.d.array.length;
        while (cells.size() < size) {
            Rect rect = new Rect(styleSet1);
            cells.push(rect);
            clientPane.add(rect);
			Rect rect2 = new Rect(styleSet2);
			rect2.setMessage(Integer.toString(cells.size()));
			rect2.setStyle(5);
			index.push(rect2);
            clientPane.add(rect2);
        }
        while (cells.size() > size) {
            clientPane.remove((Component) cells.pop());
			clientPane.remove((Component) index.pop());
        }
        clientPane.doLayout();
    }
	
	private void setArraySize(int size) {
        auto.d.array = new int[size];
        elements.setValue(data.array.length);
        adjustArraySize();
    }
	
	public void adjustmentValueChanged(AdjustmentEvent event) {
        if (event.getSource() == elements) {
            setArraySize(event.getValue());
            randomize();
        }
    }
	
	public void updateArray(int left, int right) {
		
        for (int i = 0; i < data.array.length; i++) {
            Rect rect = (Rect) cells.elementAt(i);
            rect.setMessage(Integer.toString(data.array[i]));
			if (i < left || i > right)
				rect.setStyle(3);
			else
				rect.setStyle(0);
        }
		
        update(true);
    }
	
	public void updateBorders() {
		while (leftBord.size() > 0){
			clientPane.remove((Component) leftBord.pop());
			clientPane.remove((Component) rightBord.pop());
		}

        for (int i = 0; i < data.depth; i++) {
			Rect rect = new Rect(styleSet1);
			rect.setMessage(Integer.toString(data.leftBorders[i] + 1));
			rect.setStyle(i == data.depth - 1 ? 1 : 0);
			leftBord.push(rect);
            clientPane.add(rect);
			Rect rect2 = new Rect(styleSet1);
			rect2.setMessage(Integer.toString(data.rightBorders[i] + 1));
			rect2.setStyle(i == data.depth - 1 ? 1 : 0);
			rightBord.push(rect2);
            clientPane.add(rect2);
        }
		clientPane.doLayout();
        update(true);
    }
	
	public void updateBar(){ 
		rectBar.setMessage(I18n.message(barMessage, new Integer(data.QSort_m)));
        rectBar.adjustFontSize();
				
        update(true);
    }
	
	public void updatePointers(int first, int second, int style1, int style2){
		for (int i = data.QSort_left; i <= data.QSort_right; i++) {
            Rect rect = (Rect) cells.elementAt(i);
            rect.setMessage(Integer.toString(data.array[i]));
            if (i == first)
				rect.setStyle(style1);
			else if (i == second)
				rect.setStyle(style2);
			else
				rect.setStyle(0);
        }
		
		update(true);
	}
	
	
	protected void layoutClientPane(int clientWidth, int clientHeight) {
        int n = cells.size();
		int width = Math.min(clientWidth / (n + 1), clientHeight / 5);
		Rectangle mb = rectBar.getBounds();
		rectBar.setBounds(clientWidth / 2 - width, width / 5, width * 2, width / 2);
		rectBar.adjustFontSize(I18n.message(barMessage, new Integer(maxValue)));
		clientPane.add(rectBar);
		mb = rectBar.getBounds();
        
		int height = Math.min(width, (clientHeight - width / 2 - mb.height) * 10 / 13);
		int y = mb.y + mb.height + height / 10;
        int x = (clientWidth - width * n) / 2;
		
		for (int i = 0; i < n; i++) {
            Rect rect = (Rect) cells.elementAt(i);
            rect.setBounds(x + i * width, y + width, width + 1, height + 1);
            rect.adjustFontSize(maxValueString);
			Rect rect2 = (Rect) index.elementAt(i);
            rect2.setBounds(x + i * width, y, width + 1, height + 1);
            rect2.adjustFontSize(maxValueString);
        }
		
		rectBord.setBounds(clientWidth / 2 - 2 * width, y + 2 * width, width * 4, width);
		rectBord.adjustFontSize(I18n.message(bordersMessage, new Integer(maxValue)));
		clientPane.add(rectBord);
		
		for (int i = 0; i < data.depth; i++) {
            Rect rect = (Rect) leftBord.elementAt(i);
            rect.setBounds(x + i * (width / 2), y + 3 * width, width / 2, height / 2);
            rect.adjustFontSize(maxValueString);
			Rect rect2 = (Rect) rightBord.elementAt(i);
            rect2.setBounds(x + i * (width / 2), y + 7 * width / 2, width / 2, height / 2);
            rect2.adjustFontSize(maxValueString);
        }
    }
}