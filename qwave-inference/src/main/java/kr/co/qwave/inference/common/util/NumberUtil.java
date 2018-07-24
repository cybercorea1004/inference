/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : NumberUtil.java
 * 2. Package : kr.co.qwave.inference.common.util
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 20. 오후 1:39:45
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 20. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.util;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.util
*   |_ NumberUtil.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 20. 오후 1:39:45
* @Version : 1.0
*/
@Component
public class NumberUtil {
	/**
	* <PRE>
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param smallTalkList
	* @return 
	*/
	public String randomItem(ArrayList<String> jsonList) {
		Random random  = new Random(); 
		String str = jsonList.get(random.nextInt(jsonList.size()))+"";
		return str;
	}
}
