/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : WordUtil.java
 * 2. Package : kr.co.qwave.inference.common.util
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 6:11:13
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.util;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.util
*   |_ WordUtil.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 6:11:13
* @Version : 1.0
*/
@Component
public class WordUtil {

	/** String Data를 한단어씩 해서 단어로 분리 리스트 리턴
	 * @param strTxt
	 * @return
	 */
	public  ArrayList<String> getDistWordList(String strTxt){
		KoreanJasoUtil koreanJasoUtil = new KoreanJasoUtil();
		ArrayList<String> wordArray = new ArrayList<String>();

		String strTxtTemp = strTxt.replaceAll(" ", "");
//		System.out.println("strTxtTemp :: " + strTxtTemp);
		for(int i = 0 ; i < strTxtTemp.length() ; i ++) {
			for(int j = i ; j < strTxtTemp.length()-1  ; j++) {
				String a = strTxtTemp.substring(i,j+2);
//				System.out.println("a :: " + a);
				if(!a.equals("")) {
					wordArray.add(koreanJasoUtil.hangulToJaso(a));	
				}
			}
		}	
//		System.out.println("wordArray :: " + wordArray);
		return wordArray;
	}
}
