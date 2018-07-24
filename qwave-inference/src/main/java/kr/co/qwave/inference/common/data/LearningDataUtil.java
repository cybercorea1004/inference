/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : LearningDataUtil.java
 * 2. Package : kr.co.qwave.inference.common.data
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 5:20:21
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Multimap;

import kr.co.qwave.inference.common.info.KeyWordInfo;
import kr.co.qwave.inference.common.info.QuestionDataInfo;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.data
*   |_ LearningDataUtil.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 5:20:21
* @Version : 1.0
*/
@Component
public class LearningDataUtil {
	
	@Autowired
	LearningDataSet learningDataSet;
	
	/**
	* <PRE> 키워드 데이터를 메모리에 실제 담는다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param keywordInfo 
	*/
	public void setKeyWordData(KeyWordInfo keywordInfo) {
		learningDataSet.setSimilarStr(keywordInfo.getSimilarStr());
		learningDataSet.setSimilarStr_grp(keywordInfo.getSimilarStr_grp());
	}
	
	/**
	* <PRE> 질문 데이터를 메모리에 실제 담는다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param readDataInfo 
	*/
	public void setQuestionData(QuestionDataInfo readDataInfo) {
		learningDataSet.setDicString(readDataInfo.getSubDataList());
		learningDataSet.setDicShortString(readDataInfo.getSubShortDataList());
		learningDataSet.setOrgTopRequestMap(readDataInfo.getNewMap()); //대표질문 데이터
		learningDataSet.setShortRequestMap(readDataInfo.getShortMap()); // 단문 리스트(대표질문) 데이터
		learningDataSet.setResponseMap(readDataInfo.getResponseMap()); //답변 데이터
	}
	
	/**
	* <PRE>Entity 데이터를 메모리에 실제 담는다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param entityMap 
	*/
	public void setEntityData(Multimap<String, String[]> entityMap) {
		learningDataSet.setEntityMap(entityMap);
	}
	
	/**
	* <PRE> 조사 데이터를 메모리에 실제 담는다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param josaList 
	*/
	public void setJosaListData(ArrayList<String> josaList) {
		learningDataSet.setJosaList(josaList);
	}
	
	/**
	* <PRE> 조회용 데이터를 JSON 형태로 가공하여 메모리에 담는다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param dataObj 
	*/
	public void setDBdata(JSONObject dataObj) {
		learningDataSet.setDataObj(dataObj);
	}
	

}
