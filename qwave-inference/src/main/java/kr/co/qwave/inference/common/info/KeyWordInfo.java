/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : KeyWordInfo.java
 * 2. Package : kr.co.qwave.inference.common.info
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 2:04:21
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.info;

import java.util.ArrayList;
import java.util.HashMap;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.info
*   |_ KeyWordInfo.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 2:04:21
* @Version : 1.0
*/

public class KeyWordInfo {
	
	HashMap<String, String> similarStr = new HashMap<String, String>();
	HashMap<String, ArrayList<String>> similarStr_grp = new HashMap<String, ArrayList<String>>();
	public HashMap<String, String> getSimilarStr() {
		return similarStr;
	}
	public void setSimilarStr(HashMap<String, String> similarStr) {
		this.similarStr = similarStr;
	}
	public HashMap<String, ArrayList<String>> getSimilarStr_grp() {
		return similarStr_grp;
	}
	public void setSimilarStr_grp(HashMap<String, ArrayList<String>> similarStr_grp) {
		this.similarStr_grp = similarStr_grp;
	}
}
