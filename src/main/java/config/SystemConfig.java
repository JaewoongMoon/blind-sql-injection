package config;

/**
 * @author Jae-Woong Moon(mjw8585@gmail.com)
 * @brief
 * @Date 2018. 3. 20.
 */
public class SystemConfig {

	private int lengthUntil = 50; // search length until
	private int countUntil = 65; // search count until
	private String proxyAddress;
	private String portNum;

	public String getProxyAddress() {
		return proxyAddress;
	}

	public void setProxyAddress(String proxyAddress) {
		this.proxyAddress = proxyAddress;
	}

	public String getPortNum() {
		return portNum;
	}

	public void setPortNum(String portNum) {
		this.portNum = portNum;
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
