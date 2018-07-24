/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : EntityInfo.java
 * 2. Package : kr.co.qwave.inference.common.info
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 20. 오전 9:36:28
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
*   |_ EntityInfo.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 20. 오전 9:36:28
* @Version : 1.0
*/

public class EntityInfo {
	
	private String groupName = ""; //그룹명
	private String entityValue = ""; //entity
	private String strTxt = "" ;// 변경된 문장
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getEntityValue() {
		return entityValue;
	}
	public void setEntityValue(String entityValue) {
		this.entityValue = entityValue;
	}
	public String getStrTxt() {
		return strTxt;
	}
	public void setStrTxt(String strTxt) {
		this.strTxt = strTxt;
	}
}
