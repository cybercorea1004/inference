/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : ChatAPIController.java
 * 2. Package : kr.co.qwave.inference.rest
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 1:24:45
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.rest;

import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.jdbc.Connection;

import kr.co.qwave.inference.common.util.KoreanJasoUtil;
import kr.co.qwave.inference.rest.service.ChatAPIService;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.rest
*   |_ ChatAPIController.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 1:24:45
* @Version : 1.0
*/
@RestController
public class ChatAPIController implements BeanFactoryAware {

	BeanFactory context;
	
	@Autowired
	KoreanJasoUtil koreanJasoUtil;
	
	@Autowired
	ChatAPIService chatAPIService;
	
	@Autowired
	DataSource dataSource;
	
	/**
	* <PRE> 질문 요청에 대해 답변을 보낸다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param reqMap
	* @return 
	*/
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject message(@RequestBody JSONObject reqMap) {
		
		try{
			java.sql.Connection con = dataSource.getConnection();
			System.out.println(con);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		JSONObject returnObj = new JSONObject();
		String user_key = "";
		String type = "";
		String content = "";
		
		user_key = reqMap.get("user_key") + "";
		type = reqMap.get("type") + "";
		content = reqMap.get("content") + "";
		
		returnObj.put("message", koreanJasoUtil.hangulToJaso("신용천은바보다.asdf"));
		
		returnObj = chatAPIService.getRequestInference(content);
		return returnObj;
	}

	@Override
	public void setBeanFactory(BeanFactory context) throws BeansException {
		this.context = context;
	}
}
