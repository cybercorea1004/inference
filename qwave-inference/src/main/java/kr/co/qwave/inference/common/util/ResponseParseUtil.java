/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : ResponseParseUtil.java
 * 2. Package : kr.co.qwave.inference.common.util
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 20. 오전 9:48:05
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 20. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.qwave.inference.common.info.ResponseInfo;
import kr.co.qwave.inference.common.info.ResponseResultInfo;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.util
*   |_ ResponseParseUtil.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 20. 오전 9:48:05
* @Version : 1.0
*/
@Component
public class ResponseParseUtil {
	
	@Autowired
	JSONUtil jSONUtil;
	
	@Autowired
	NumberUtil numberUtil;

	/**
	* <PRE> 답변 데이터 리스트를 분석 데이터를 리턴한다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param responseData
	* @return 
	*/
	public ResponseResultInfo getResponseParseData(ResponseInfo responseInfo) {
		ArrayList<String> responseData = responseInfo.getResponseStr();
		HashMap<String, ArrayList<String>> entityMap = responseInfo.getEntityMap();
		boolean isFirst = true;
		JSONObject optionObj = new JSONObject();
		optionObj = jSONUtil.parseStringToJSONObject(responseData.get(0));
		String type = optionObj.get("type")+"";	
//		responseData.remove(0);
		String params [] = {"head","body","end","url","no"};
		JSONObject matchObj = new JSONObject();
//		System.out.println("4  ===> " + type);
		switch(type) {
		case "option" : // 조건식에 맞는 데이터 반환
			JSONObject allObj = new JSONObject();
			boolean isMatch = false;
			
			String matchStr = "";
			for(int i = 0 ; i < responseData.size() ; i++) {
				String str = responseData.get(i);
//			for(String str : responseData) {
//				System.out.println("5  ===> " + str);
				JSONObject answerObj = jSONUtil.parseStringToJSONObject(str);

				String option = answerObj.get("option")+"";
				String head = answerObj.get("head")+"";
				String end = answerObj.get("end")+"";
				String body = answerObj.get("body")+"";
				String url = answerObj.get("url")+"";
				String no = answerObj.get("no")+"";
				
				String options [] = option.split(",");
				if(option.equals("ALL")) {
					for(String ekey : entityMap.keySet()) {
						for(String param : params) {
							String paramValue = (answerObj.get(param)+"").replaceAll("#" + ekey + "#", entityMap.get(ekey).get(0));
							answerObj.put(param, paramValue);
						}
					}
					allObj = answerObj;
					continue;
				}
				int matchCount = options.length;
				for(String opt : options ) {
					for(String ekey : entityMap.keySet()) {
						if(!opt.equals(ekey)) {
							break;
						}else {
							for(String param : params) {
								String paramValue = (answerObj.get(param)+"").replaceAll("#" + opt + "#", entityMap.get(opt).get(0));
								answerObj.put(param, paramValue);
							}
							matchCount--;
						}
					}
				}
				if(matchCount == 0) { //전체가 매칭됨
					isMatch = true;
				}
				if(isMatch) {	
					option = "," + option + ",";
					if(option.indexOf(matchStr) != -1) {
						matchObj = answerObj;
						matchStr = option;
					}					
				}
			}
			if(!isMatch) {
				matchObj = allObj;
				matchStr = "ALL";
			}
			
			break;
		case "random" : // 데이터들을 Random 하게 리턴한다.
			String str = numberUtil.randomItem(responseData);
			JSONObject answerObj = jSONUtil.parseStringToJSONObject(str);
			for(String ekey : entityMap.keySet()) {
				for(String param : params) {
					String paramValue = (answerObj.get(param)+"").replaceAll("#" + ekey + "#", entityMap.get(ekey).get(0));
					answerObj.put(param, paramValue);
				}
			}
			matchObj = answerObj;
			break;
		case "text" : // 일반 데이터로 첫번째 데이터를 바로 리턴한다.
			String str3 = responseData.get(0);
			JSONObject answerObj2 = jSONUtil.parseStringToJSONObject(str3);
			for(String ekey : entityMap.keySet()) {
				for(String param : params) {
					String paramValue = (answerObj2.get(param)+"").replaceAll("#" + ekey + "#", entityMap.get(ekey).get(0));
					answerObj2.put(param, paramValue);
				}
			}
			matchObj = answerObj2;
		}	
//		System.out.println("=================================");
//		System.out.println(jSONUtil.printJsonPretty(matchObj));
		ResponseResultInfo responseResultInfo = new ResponseResultInfo();
		responseResultInfo.setMessage(jSONUtil.printJsonPretty(matchObj));
		return responseResultInfo;
	}
	
	
}
/*
{
"head": "",
"end": "",
"type": "MEM/JSONObject",
"body": "문의하신 #센터명#의 지원 가능한 언어는 $keys=3.0$ 입니다.",
"url": "$.센터정보.#센터명#[?(@.#언어# == 3.0)]",
"option": "센터명,언어"
},
{
"head": "외국어 지원 가능한 센터의 위치는 아래와 같습니다. \n",
"end": "",
"type": "MEM/JSONArray",
"body": "$센터명$ : $주소$\n",
"url": "$.센터정보.[*][?(@.지역 in [#지역#])][?(@.#언어# == 3.0)]",
"option": "지역,언어"
},
{
"head": "해당되는 센터의 위치는 아래와 같습니다. \n",
"end": "",
"type": "MEM/JSONArray",
"body": "$센터명$ : $주소$\n",
"url": "$.센터정보.[*][?(@.#언어# == 3.0)]",
"option": "언어"
},
{
"head": "해당되는 센터의 위치는 아래와 같습니다. \n",
"end": "",
"type": "MEM/JSONArray",
"body": "$센터명$ : $keys=3.0$\n",
"url": "$.센터정보.[*]",
"option": "ALL"
}
*/