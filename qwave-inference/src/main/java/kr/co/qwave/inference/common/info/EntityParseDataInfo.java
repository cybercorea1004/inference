/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : EntityParseDataInfo.java
 * 2. Package : kr.co.qwave.inference.common.info
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 6:08:38
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
*   |_ EntityParseDataInfo.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 6:08:38
* @Version : 1.0
*/

public class EntityParseDataInfo {
	
	private HashMap<String,ArrayList<String>> entityMap;
	private String parseTxt;
	
	public HashMap<String, ArrayList<String>> getEntityMap() {
		return entityMap;
	}
	public void setEntityMap(HashMap<String, ArrayList<String>> entityMap) {
		this.entityMap = entityMap;
	}
	public String getParseTxt() {
		return parseTxt;
	}
	public void setParseTxt(String parseTxt) {
		this.parseTxt = parseTxt;
	}
}
