/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : EntityUtil.java
 * 2. Package : kr.co.qwave.inference.common.util
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 6:06:21
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Multimap;

import kr.co.qwave.inference.common.data.LearningDataSet;
import kr.co.qwave.inference.common.info.EntityInfo;
import kr.co.qwave.inference.common.info.EntityParseDataInfo;


/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.util
*   |_ EntityUtil.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 6:06:21
* @Version : 1.0
*/
@Component
public class EntityParseUtil {
	@Autowired
	LearningDataSet learningDataSet;
	
	/**
	* <PRE>등록된 엔티티명과 일치하는 명식적인 데이터를 로딩한다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param strTxt
	* @return 
	*/
	public  EntityParseDataInfo getEntityList(String strTxt){
		strTxt = strTxt.replaceAll(" ", "");
		EntityParseDataInfo entityParseDataInfo = new EntityParseDataInfo();
		HashMap<String,ArrayList<String>> entityMap = new HashMap<String, ArrayList<String>>();
		HashMap<String,ArrayList<String>> entityMapOrg = new HashMap<String, ArrayList<String>>();
		Multimap<String, String[]> entityMapList = learningDataSet.getEntityMap();
		
		for(int i = 0 ; i < strTxt.length() ; i ++) {
			for(int j = i ; j < strTxt.length()-1  ; j++) {
				String a = strTxt.substring(i,j+2);
				if(!a.equals("")) {
					if(entityMapList.containsKey(a)) {
						Collection<String[]> values = entityMapList.get(a);
						for(String[] b : values) {
							if(entityMap.containsKey(b[0])) {
								ArrayList<String> bs = entityMapOrg.get(b[0]);
								ArrayList<String> as = entityMap.get(b[0]);
								ArrayList<String> aArray = new ArrayList<String>();
								
								if(as.indexOf(a) != -1) {
									continue;
								}else {
									aArray.addAll(as);
									for(String a_ : as) {
										if(a.indexOf(a_) != -1) {
											as.remove(a_);
											break;
										}
									}
								}
								
								if(bs.indexOf(b[1]) == -1) {
									bs.add(b[1]);	
								}
								entityMap.get(b[0]).add(a);
							}else {
								ArrayList<String> as = new ArrayList<String>();
								ArrayList<String> bs = new ArrayList<String>();
								as.add(a);
								bs.add(b[1]);
								entityMap.put(b[0], as);
								entityMapOrg.put(b[0], bs);
							}
						}
						
					}
				}
			}
		}
		Set<String> keysx = entityMap.keySet();
		for (String keyprint : keysx) {
			ArrayList<String> as = entityMap.get(keyprint);
			for(String a : as) {
//				strTxt = strTxt.replaceAll(a, keyprint);
				strTxt = strTxt.replaceAll(a, "");
			}
			as = entityMap.get(keyprint);
			
		}
		entityParseDataInfo.setEntityMap(entityMapOrg);
		entityParseDataInfo.setParseTxt(strTxt);
		return entityParseDataInfo;
	}
	
	/**
	* <PRE> 엔티티의 레프트에 있는 숫자를 가져온다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param txtStr
	* @param dist
	* @return 
	*/
	public  EntityInfo getEntityLeftValue(String txtStr, String dist[]) {
		EntityInfo entityInfo = new EntityInfo();
		
		String returnValue = "";
		for(String unit : dist) {
			int strIdx = txtStr.indexOf(unit);
			if(strIdx != -1) {
				txtStr = txtStr.substring(0,txtStr.lastIndexOf(unit) + unit.length());				
				String [] splitTemp = txtStr.split(unit);
				boolean isFirst = true;
				
				for(String splitStr : splitTemp) {
					String unitStrTemp = splitStr.replaceAll(" " , "");
					if(unitStrTemp.startsWith(",")) {
						unitStrTemp = unitStrTemp.substring(1);
					}
					String returnValue_ = "";
					char strArray [] = unitStrTemp.toCharArray();
					int idx2 = strArray.length -1;
					for(int i = idx2 ; i > -1 ; i--) {
						if(Character.isDigit(strArray[i])){
							returnValue_ = strArray[i]+"" + returnValue_;
						}else{
							if(strArray[i] != ',') {
								break;
							}else {
								returnValue_ = strArray[i]+"" + returnValue_;
							}
						}					
					}
					
					if(isFirst) {
						returnValue = returnValue + returnValue_;
					}else {
						returnValue = returnValue +","+ returnValue_;
					}
					isFirst = false;
				}
				
			}
		}
		entityInfo.setGroupName(dist[0]);
		entityInfo.setEntityValue(returnValue);
		entityInfo.setStrTxt(txtStr);

		return entityInfo;
	}

}
