/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : QuestionDataInfo.java
 * 2. Package : kr.co.qwave.inference.common.info
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 3:26:22
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.info;

import java.util.ArrayList;
import java.util.HashMap;

import com.jaunt.util.MultiMap;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.info
*   |_ QuestionDataInfo.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 3:26:22
* @Version : 1.0
*/

public class QuestionDataInfo {
	private ArrayList<String> subDataList = new ArrayList<String>();
	private HashMap<String, String> newMap;
	private HashMap<String, ArrayList<String>> responseMap;
	private MultiMap<String, String> responseMultiMap;
	private HashMap<String, String> shortMap; 
	private ArrayList<String> subShortDataList = new ArrayList<String>();
	
	public ArrayList<String> getSubDataList() {
		return subDataList;
	}
	public void setSubDataList(ArrayList<String> subDataList) {
		this.subDataList = subDataList;
	}
	public HashMap<String, String> getNewMap() {
		return newMap;
	}
	public void setNewMap(HashMap<String, String> newMap) {
		this.newMap = newMap;
	}
	public HashMap<String, ArrayList<String>> getResponseMap() {
		return responseMap;
	}
	public void setResponseMap(HashMap<String, ArrayList<String>> responseMap) {
		this.responseMap = responseMap;
	}
	public MultiMap<String, String> getResponseMultiMap() {
		return responseMultiMap;
	}
	public void setResponseMultiMap(MultiMap<String, String> responseMultiMap) {
		this.responseMultiMap = responseMultiMap;
	}
	public HashMap<String, String> getShortMap() {
		return shortMap;
	}
	public void setShortMap(HashMap<String, String> shortMap) {
		this.shortMap = shortMap;
	}
	public ArrayList<String> getSubShortDataList() {
		return subShortDataList;
	}
	public void setSubShortDataList(ArrayList<String> subShortDataList) {
		this.subShortDataList = subShortDataList;
	}
}
