/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : ChatAPIService.java
 * 2. Package : kr.co.qwave.inference.rest.service
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 5:59:15
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.rest.service;

import org.json.simple.JSONObject;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.rest.service
*   |_ ChatAPIService.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 5:59:15
* @Version : 1.0
*/

public interface ChatAPIService {

	/**
	* <PRE> 체팅 질문에 답변한다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param requestMsg
	* @return 
	*/
	public JSONObject getRequestInference(String requestMsg);
}
