package ru.ifmo.vizi.SegmentsTree.widgets;

import java.awt.*;

public class myTextField extends TextField {
	
	/**
	 * Message of the textfield
	 */
	private String message;
	
	/**
	 * Constructor
	 *
	 * @param message - text in the textfield
	 */
	public myTextField(String message) {
		super(message);
		this.message = message;
	}

    /**
     * Adjust shape's font size to show specified message.
     *
     * @param message template message.
     */
    public void adjustFontSize(String message) {
        Dimension size = getSize();
        Font font = getFont();
        int l = 5;
        int r = 1000;
        while (l < r) {
            int m = (l + r + 1) / 2;
            FontMetrics metrics = getFontMetrics(new Font(font.getName(), font.getStyle(), m));
            Dimension minSize = new Dimension(metrics.stringWidth(message) + 1, metrics.getHeight() + 1);
            if (size.width < minSize.width || size.height < minSize.height) {
                r = m - 1;
            } else {
                l = m;
            }
        }
        setFont(new Font(font.getName(), font.getStyle(), l));
    }		
}