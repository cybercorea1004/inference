/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : ResponseResultInfo.java
 * 2. Package : kr.co.qwave.inference.common.info
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 20. 오전 9:48:55
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 20. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.info;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.info
*   |_ ResponseResultInfo.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 20. 오전 9:48:55
* @Version : 1.0
*/

public class ResponseResultInfo {

	private String type = "";
	private String message = "";
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
