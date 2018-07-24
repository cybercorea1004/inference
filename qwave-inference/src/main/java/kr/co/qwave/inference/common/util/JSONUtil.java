/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : JSONUtil.java
 * 2. Package : kr.co.qwave.inference.common.util
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 5:57:29
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.util
*   |_ JSONUtil.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 5:57:29
* @Version : 1.0
*/
@Component
public class JSONUtil {

	/**
	 * <PRE> JSONObject를 보기 편한 형태의 String으로 변환한다.
	 * 간략 : .
	 * 상세 : .
	 * <PRE>
	 * @param answerObj 
	 */
	public  String printJsonPretty(JSONObject answerObj){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(answerObj);
		return prettyJson;
	}
	
	/**
	* <PRE> String data 를 JSONObject 변환하여 반환한다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param str
	* @return 
	*/
	public JSONObject parseStringToJSONObject(String str) {
		JSONParser parser = new JSONParser();
		JSONObject obj = new JSONObject();
		try {
			obj = (JSONObject)parser.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}
