/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : ChatAPIServiceImpl.java
 * 2. Package : kr.co.qwave.inference.rest.service
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 6:00:07
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.rest.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.qwave.inference.common.inference.InferenceUtil;

/**
 * <pre>
 * 간략 : .
 * 상세 : .
 * kr.co.qwave.inference.rest.service
 *   |_ ChatAPIServiceImpl.java
 * </pre>
 * 
 * @Company : (주)퀀텀웨이브
 * @Author  : sky
 * @Date    : 2018. 7. 19. 오후 6:00:07
 * @Version : 1.0
 */
@Service("chatAPIService")
public class ChatAPIServiceImpl implements ChatAPIService{
	@Autowired
	InferenceUtil inferenceUtil;

	/**
	 * <PRE>
	 * 간략 : .
	 * 상세 : .
	 * <PRE>
	 * @see kr.co.qwave.inference.rest.service.ChatAPIService#getRequestInference(java.lang.String)
	 */
	@Override
	public JSONObject getRequestInference(String requestMsg) {
		System.out.println("request message :: " + requestMsg);
		JSONObject obj = new JSONObject();
		try {
			JSONArray shortArray = inferenceUtil.getShortDataOrderList(requestMsg);
			JSONArray longArray = inferenceUtil.getDataOrderList(requestMsg);

			obj.put("short", shortArray);
			obj.put("long", longArray);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

}
