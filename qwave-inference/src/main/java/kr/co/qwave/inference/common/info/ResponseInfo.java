/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : ResponseInfo.java
 * 2. Package : kr.co.qwave.inference.common.info
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 6:05:34
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
*   |_ ResponseInfo.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 6:05:34
* @Version : 1.0
*/

public class ResponseInfo {
	public String subRequest = "";
	public double rate = 0.0;
	public ArrayList<String> responseStr = new ArrayList<String>();
	public HashMap<String, ArrayList<String>> entityMap;
	
	public ResponseResultInfo responseResultInfo = new ResponseResultInfo();
	
	public ResponseInfo() {
		
	}
	
	public ResponseInfo(String subRequest_, double rate_, ArrayList<String> responseStr_, HashMap<String, ArrayList<String>> entityMap_) {
		this.subRequest = subRequest_;
		this.rate = rate_;
		this.responseStr = responseStr_;
		this.entityMap = entityMap_;
	}
	
	public String getSubRequest() {
		return subRequest;
	}
	public void setSubRequest(String subRequest) {
		this.subRequest = subRequest;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public ArrayList<String> getResponseStr() {
		return responseStr;
	}
	public void setResponseStr(ArrayList<String> responseStr) {
		this.responseStr = responseStr;
	}

	public HashMap<String, ArrayList<String>> getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(HashMap<String, ArrayList<String>> entityMap) {
		this.entityMap = entityMap;
	}

	public ResponseResultInfo getResponseResultInfo() {
		return responseResultInfo;
	}

	public void setResponseResultInfo(ResponseResultInfo responseResultInfo) {
		this.responseResultInfo = responseResultInfo;
	}
	
	
}
