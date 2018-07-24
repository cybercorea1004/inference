/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : CompareStringInfo.java
 * 2. Package : kr.co.qwave.inference.common.info
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 6:15:50
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.info;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.info
*   |_ CompareStringInfo.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 6:15:50
* @Version : 1.0
*/

public class CompareStringInfo {
	
	private String key = "";
	private double rate = 0.00;
	
	public CompareStringInfo() {

	}
	
	public CompareStringInfo(String key, double rate) {
		this.key = key;
		this.rate = rate;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
}
