/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : DataInitializer.java
 * 2. Package : kr.co.qwave.inference.common.init
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 1:58:59
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.init;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Multimap;

import kr.co.qwave.inference.common.data.DataRead;
import kr.co.qwave.inference.common.data.LearningDataSet;
import kr.co.qwave.inference.common.data.LearningDataUtil;
import kr.co.qwave.inference.common.info.KeyWordInfo;
import kr.co.qwave.inference.common.info.QuestionDataInfo;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.init
*   |_ DataInitializer.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 1:58:59
* @Version : 1.0
*/
@Configuration
public class DataInitializer implements ServletContextInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
	@Autowired
	LearningDataSet learningDataSet;
	@Autowired
	DataRead dataRead;
	@Autowired
	LearningDataUtil learningDataUtil;
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		try {
//			System.out.println(System.getProperties().get("data_url"));
			
			String data_url = System.getProperties().get("data_url")+"";
			
			KeyWordInfo keywordInfo = dataRead.readKeywordData(data_url + "/data/ubase_keyword_value_20180705103316.xlsx");
			QuestionDataInfo readDataInfo = dataRead.readQuestionData(data_url + "/data/ubase_일반질문_20180705103345.xlsx");
			Multimap<String, String[]> entityMap = dataRead.readEntityData(data_url + "/data/ubase_엔티티리스트.xlsx");
			ArrayList<String> josaList =dataRead.readJosaData(data_url + "/data/ubase_조사.xlsx");
			JSONObject dataObj = dataRead.readDBData(data_url + "/data/ubase_data_new.xlsx");
			
			learningDataUtil.setKeyWordData(keywordInfo);
			learningDataUtil.setQuestionData(readDataInfo);
			learningDataUtil.setEntityData(entityMap);
			learningDataUtil.setJosaListData(josaList);
			learningDataUtil.setDBdata(dataObj);
			
			learningDataSet.setLearningData();
			
//			logger.info(learningDataSet.getWordGroupStringAll().size()+"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
