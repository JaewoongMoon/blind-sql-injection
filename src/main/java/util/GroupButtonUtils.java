/**
 * @ GroupButtonUtils.java
 */
package util;

import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;


/**
 * <pre>
 * util
 * GroupButtonUtils.java 
 * </pre>
 *
 * @brief	: https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/09/05
 */
public class GroupButtonUtils {

	 public static String getSelectedButtonText(ButtonGroup buttonGroup) {
	        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
	            AbstractButton button = buttons.nextElement();

	            if (button.isSelected()) {
	            	//System.out.println("selected button : " + button.getText());
	                return button.getText();
	            }
	        }
	        return null;
	    }
}
