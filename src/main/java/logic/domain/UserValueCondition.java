/**
 * @ UserValueCondition.java
 */
package logic.domain;

/**
 * <pre>
 * logic.domain
 * UserValueCondition.java 
 * </pre>
 *
 * @brief	: User input values
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/17
 */
public class UserValueCondition {

	private int countUntil = 40; // search count until
	private int lengthUntil = 50; // search length until
	private String match;
	
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public int getCountUntil() {
		return countUntil;
	}
	public void setCountUntil(int countUntil) {
		this.countUntil = countUntil;
	}
	public int getLengthUntil() {
		return lengthUntil;
	}
	public void setLengthUntil(int lengthUntil) {
		this.lengthUntil = lengthUntil;
	}
	
	
}
