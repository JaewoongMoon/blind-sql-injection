/**
 * @ GroupButtonUtils.java
 */
package util;

import java.awt.Component;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * <pre>
 * util
 * GroupButtonUtils.java
 * </pre>
 *
 * @brief :
 *        https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup
 *        https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
 * @author : Jae-Woong Moon(mjw8585@gmail.com)
 * @Date : 2017/09/05
 */
public class SwingUtils {

	public static String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				// System.out.println("selected button : " + button.getText());
				return button.getText();
			}
		}
		return null;
	}
	
	public static void resizeColumnWidthPercentage(JTable table, float[] columnWidthPercentage){
		int tableWidth = table.getWidth();
		TableColumnModel columnModel = table.getColumnModel();
		for (int i=0; i < columnModel.getColumnCount(); i++){
			TableColumn column = columnModel.getColumn(i);
			int pWidth = Math.round(columnWidthPercentage[i] * tableWidth);
			column.setPreferredWidth(pWidth);
		}
	}

	public static void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 15; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			if (width > 300){
				width = 300;
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}
	
	public static void resizeColumnWidth2(JTable table){
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		 
		for (int column = 0; column < table.getColumnCount(); column++)
		{
		    TableColumn tableColumn = table.getColumnModel().getColumn(column);
		    int preferredWidth = tableColumn.getMinWidth();
		    int maxWidth = tableColumn.getMaxWidth();
		 
		    for (int row = 0; row < table.getRowCount(); row++)
		    {
		        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		        Component c = table.prepareRenderer(cellRenderer, row, column);
		        int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
		        preferredWidth = Math.max(preferredWidth, width);
		 
		        //  We've exceeded the maximum width, no need to check other rows
		 
		        if (preferredWidth >= maxWidth)
		        {
		            preferredWidth = maxWidth;
		            break;
		        }
		    }
		 
		    tableColumn.setPreferredWidth( preferredWidth );
		}
	}
}
