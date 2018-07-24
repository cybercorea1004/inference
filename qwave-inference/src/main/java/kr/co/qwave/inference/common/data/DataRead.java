/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : DataRead.java
 * 2. Package : kr.co.qwave.inference.common.data
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 2:02:08
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.qwave.inference.QwaveInferenceApplication;
import kr.co.qwave.inference.common.info.KeyWordInfo;
import kr.co.qwave.inference.common.info.QuestionDataInfo;
import kr.co.qwave.inference.common.util.ExcelUtil;
import kr.co.qwave.inference.common.util.KoreanJasoUtil;
import kr.co.qwave.inference.common.util.StringUtils;

/**
 * <pre>
 * 간략 : .
 * 상세 : .
 * kr.co.qwave.inference.common.data
 *   |_ DataRead.java
 * </pre>
 * 
 * @Company : (주)퀀텀웨이브
 * @Author  : sky
 * @Date    : 2018. 7. 19. 오후 2:02:08
 * @Version : 1.0
 */
@Component
public class DataRead {
	private static final Logger logger = LoggerFactory.getLogger(DataRead.class);
	@Autowired
	KoreanJasoUtil koreanJasoUtil;
	@Autowired
	ExcelUtil excelUtil;
	@Autowired
	StringUtils stringUtils;

	/**
	 * <PRE> 키워드 데이터를 로드한다.
	 * 간략 : .
	 * 상세 : .
	 * <PRE>
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public KeyWordInfo readKeywordData(String filePath) throws IOException {
		HashMap<String, String> similarStr = new HashMap<String, String>();
		HashMap<String, ArrayList<String>> similarStr_grp = new HashMap<String, ArrayList<String>>();
		KeyWordInfo keyWordInfo = new KeyWordInfo();

		XSSFWorkbook workbook = excelUtil.getXSSWorkBook(filePath);
		int sheetCount = workbook.getNumberOfSheets();

		for(int s = 0 ; s < sheetCount ; s ++){
			XSSFSheet sheet = workbook.getSheetAt(s);
			int rowCnt = sheet.getPhysicalNumberOfRows(); // row 개수
			for(int i = 0; i < rowCnt; i++) {
				String topKeyword = "";
				JSONArray testArray = new JSONArray();
				XSSFRow row = sheet.getRow(i); // row data	
				int cells = row.getPhysicalNumberOfCells();
				for(int k=2 ; k<cells ;k++){
					XSSFCell cell = row.getCell(k);
					String value = excelUtil.getCellValue(cell);
					if(!stringUtils.isNumeric(value)) {
						if(k == 2) {
							topKeyword = value;	
						}
						value = value.replaceAll(" ", "");
						String valueJaso = koreanJasoUtil.hangulToJaso(value);
						String topKeywordJaso = koreanJasoUtil.hangulToJaso(topKeyword);
						testArray.add(valueJaso);
						similarStr.put(valueJaso, topKeywordJaso);
					}

					if(k == (cells-1)) {
						if(!topKeyword.equals("")) {
							String topKeywordJaso = koreanJasoUtil.hangulToJaso(topKeyword);
							similarStr_grp.put(topKeywordJaso, testArray);
						}
					}

				}
			}
		}
		keyWordInfo.setSimilarStr(similarStr);
		keyWordInfo.setSimilarStr_grp(similarStr_grp);
		return keyWordInfo;
	}
	/**
	 * <PRE> 일반질문 및 유사 질문을 로드한다.
	 * 간략 : .
	 * 상세 : .
	 * <PRE>
	 * @return
	 * @throws IOException 
	 */
	public QuestionDataInfo readQuestionData(String filePath) throws IOException {
		ArrayList<String> dicString = new ArrayList<String>(); //일반 질문
		ArrayList<String> shortDicString = new ArrayList<String>(); //단문
		HashMap<String, String> shortMap = new HashMap<String, String>();
		HashMap<String, String> newMap = new HashMap<String, String>();
		HashMap<String, ArrayList<String>> responseMap = new HashMap<String, ArrayList<String>>();//답변 리스트

		QuestionDataInfo questionDataInfo = new QuestionDataInfo();
		XSSFWorkbook workbook = excelUtil.getXSSWorkBook(filePath);
		int sheetCount = workbook.getNumberOfSheets();
		for(int s = 0 ; s < sheetCount ; s ++){ //1년에 192 // 182만 4천우
			XSSFSheet sheet = workbook.getSheetAt(s);
			int rowCnt = sheet.getPhysicalNumberOfRows(); // row 개수
			for(int i = 0; i < rowCnt; i++) {
				XSSFRow row = sheet.getRow(i); // row data	
				int celllNum = row.getPhysicalNumberOfCells();
				String sQuestion = "";
				String cQuestion = "";
				ArrayList<String> resDataArray = new ArrayList<String>();
				String qid = "";
				for(int c = 0 ; c < celllNum ; c++) {
					XSSFCell cell = row.getCell(c);		
					String value = excelUtil.getCellValue(cell);
					
					if(value.equals("false")) {
						break;
					}
					switch(c) {
					case 0 : //질문 아이디
						qid = value;
						break;
					case 1 : //유사 질문
						sQuestion = value;
						if(value.replaceAll(" ", "").length() < 9) {
							shortDicString.add(value);
						}
						dicString.add(value);
						break;
					case 2 : //대표질문
						cQuestion = value;
						if(sQuestion.replaceAll(" ", "").length() < 9) {
							shortMap.put(sQuestion, cQuestion);
						}
						newMap.put(sQuestion, cQuestion);
						break;
					case 3 : //최초 답변
						ArrayList<String> resArray = new ArrayList<String>();
						resArray.add(value);
						responseMap.put(cQuestion,resArray);
						
//						System.out.println(cQuestion);
						break;
					default : //나머지 답변
						responseMap.get(cQuestion).add(value);
						break;
					}
				}
			}
		}
		questionDataInfo.setSubDataList(dicString);
		questionDataInfo.setSubShortDataList(shortDicString);
		questionDataInfo.setNewMap(newMap);
		questionDataInfo.setResponseMap(responseMap);
		questionDataInfo.setShortMap(shortMap);

		return questionDataInfo;
	}

	/**
	 * <PRE> Entity List를 필요한 모양으로 가공한다.
	 * 간략 : .
	 * 상세 : .
	 * <PRE>
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public Multimap<String, String[]> readEntityData(String filePath) throws IOException {
		Multimap<String, String[]> entityMap = ArrayListMultimap.create();
		XSSFWorkbook workbook = excelUtil.getXSSWorkBook(filePath);
		int sheetCount = workbook.getNumberOfSheets();
		for(int s = 0 ; s < sheetCount ; s ++){
			XSSFSheet sheet = workbook.getSheetAt(s);
			String gEntityName = sheet.getSheetName();
			int rowCnt = sheet.getPhysicalNumberOfRows(); // row 개수
			String cEntityName = "";
			String sEntityName = "";
			for(int i = 0; i < rowCnt; i++) {
				XSSFRow row = sheet.getRow(i); // row data	
				int cells = row.getPhysicalNumberOfCells();

				for(int c=0 ; c<cells ;c++){
					XSSFCell cell = row.getCell(c);		
					String value = excelUtil.getCellValue(cell);
					if(value.equals("false")) {
						break;
					}
					switch (c){
					case 0 : // 서브 엔티티
						sEntityName = value;
						break;
					case 1 : // 엔티티 대표명
						cEntityName = value;
						break;
					default :
						break;
					}
				}
				String [] cellInfo = {gEntityName, cEntityName};
				entityMap.put(sEntityName, cellInfo);			
			}
		}
		return entityMap;
	}

	/**
	 * <PRE> 조사 정보를 리스트에 담는다.
	 * 간략 : .
	 * 상세 : .
	 * <PRE>
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<String> readJosaData(String filePath) throws IOException {
		ArrayList<String> josaList = new ArrayList<String>();
		XSSFWorkbook workbook = excelUtil.getXSSWorkBook(filePath);
		int sheetCount = workbook.getNumberOfSheets();
		for(int s = 0 ; s < sheetCount ; s ++){
			XSSFSheet sheet = workbook.getSheetAt(s);
			int rowCnt = sheet.getPhysicalNumberOfRows();
			for(int i = 0; i < rowCnt; i++) {
				XSSFRow row = sheet.getRow(i); // row data	
				int cells = row.getPhysicalNumberOfCells();
				for(int c= 0 ; c < cells ; c++) {
					XSSFCell cell = row.getCell(c);
					String value = excelUtil.getCellValue(cell);
					if(value.equals("false")) {
						break;
					}
					josaList.add(koreanJasoUtil.hangulToJaso(value));
				}
			}
		}
		return josaList;
	}
	/**
	 * <PRE> DataBase 정보를 메모리에 담는다.
	 * 간략 : .
	 * 상세 : .
	 * <PRE>
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public JSONObject readDBData(String filePath) throws IOException {
		JSONObject dataObj = new JSONObject();
		XSSFWorkbook workbook = excelUtil.getXSSWorkBook(filePath);
		int sheetCount = workbook.getNumberOfSheets();
		for(int s = 0 ; s < sheetCount ; s ++){
			XSSFSheet sheet = workbook.getSheetAt(s);
			String depth1 = sheet.getSheetName();
			JSONObject dataObj2 = new JSONObject();
			int rowCnt = sheet.getPhysicalNumberOfRows(); // row 개수
			ArrayList<String> keyNames = new ArrayList<String>();
			for(int i = 0; i < rowCnt; i++) {
				String topKeyword = "";
				XSSFRow row = sheet.getRow(i); // row data	
				int cells = row.getPhysicalNumberOfCells();
				JSONObject realObj = new JSONObject();
				for(int c=0 ; c<cells ; c++){
					XSSFCell cell = row.getCell(c);
					String value = excelUtil.getCellValue(cell);
					switch(i) {
					case 0 :
						keyNames.add(value);
						break;
					default :
						switch(c) {
						case 0 :
							topKeyword = value;
							realObj.put(keyNames.get(c), value);
						default :
							realObj.put(keyNames.get(c), value);
							break;
						}
						break;
					}
				}
				if(i != 0) {
					dataObj2.put(topKeyword, realObj);
				}
			}
			dataObj.put(depth1, dataObj2);
		}
		return dataObj;
	}
	

}
