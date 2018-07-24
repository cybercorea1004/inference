/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : InferenceUtil.java
 * 2. Package : kr.co.qwave.inference.common.inference
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 6:04:02
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.inference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;

import kr.co.qwave.inference.common.data.LearningDataSet;
import kr.co.qwave.inference.common.info.CompareStringInfo;
import kr.co.qwave.inference.common.info.EntityInfo;
import kr.co.qwave.inference.common.info.EntityParseDataInfo;
import kr.co.qwave.inference.common.info.ResponseInfo;
import kr.co.qwave.inference.common.info.ResponseResultInfo;
import kr.co.qwave.inference.common.util.CompareStringUtil;
import kr.co.qwave.inference.common.util.EntityParseUtil;
import kr.co.qwave.inference.common.util.JSONUtil;
import kr.co.qwave.inference.common.util.KoreanJasoUtil;
import kr.co.qwave.inference.common.util.ResponseParseUtil;
import kr.co.qwave.inference.common.util.WordUtil;




/**
 * <pre>
 * 간략 : .
 * 상세 : .
 * kr.co.qwave.inference.common.inference
 *   |_ InferenceUtil.java
 * </pre>
 * 
 * @Company : (주)퀀텀웨이브
 * @Author  : sky
 * @Date    : 2018. 7. 19. 오후 6:04:02
 * @Version : 1.0
 */
@Component
public class InferenceUtil {
	@Autowired
	EntityParseUtil entityParseUtil;
	@Autowired
	KoreanJasoUtil koreanJasoUtil;
	@Autowired
	WordUtil wordUtil;
	@Autowired
	LearningDataSet learningDataSet;
	@Autowired
	CompareStringUtil compareStringUtil;
	@Autowired
	ResponseParseUtil responseParseUtil;
	@Autowired
	JSONUtil jSONUtil;

	/**
	 * <PRE> 단문에 대한 검증을 우선시한다.
	 * 간략 : .
	 * 상세 : .
	 * <PRE>
	 * @param strTxt
	 * @return 
	 * @throws ParseException 
	 */
	public JSONArray getShortDataOrderList(String strTxt) throws ParseException{
		EntityParseDataInfo entityParseDataInfo = entityParseUtil.getEntityList(strTxt);
		strTxt = entityParseDataInfo.getParseTxt();
		String strTxtJaso = koreanJasoUtil.hangulToJaso(strTxt);
//		//System.out.println("1 ==> " + strTxtJaso);
		LinkedHashMap<String, JSONObject> similarMap = new LinkedHashMap<String, JSONObject>();
		Multimap<String, String> newMap = ArrayListMultimap.create();
		ArrayList<String> wordArray = wordUtil.getDistWordList(strTxt);		

		HashMap<String, String> similarStr = learningDataSet.getSimilarStr();
		Multimap<String, JSONObject> wordShortGroupStringAll = learningDataSet.getWordShortGroupStringAll();
		HashMap<String, String>  shortRequestMap = learningDataSet.getShortRequestMap();
		HashMap<String, ArrayList<String>> responseMap = learningDataSet.getResponseMap();


		for(String wordJaso : wordArray) {
			if(similarStr.containsKey(wordJaso)) {
				String wordGroup = similarStr.get(wordJaso);
				Collection<JSONObject> objs = wordShortGroupStringAll.get(wordGroup);
				for (JSONObject obj : objs) {
					String dicString = obj.get("dic")+"";
					newMap.put(dicString, wordJaso); 
//					System.out.println(dicString + "::" + wordJaso);
					if(!similarMap.containsKey(dicString)) {
//						System.out.println(dicString);
						String dicStringJaso = koreanJasoUtil.hangulToJaso(dicString);
						obj.put("dicJaso", dicStringJaso);
						similarMap.put(dicString, obj);
//						System.out.println(similarMap);
					}
				}
			}
		}
		Set<String> keysx = newMap.keySet();
//		for(String key : newMap.keySet()) {
//			System.out.println(key + "::" + newMap.get(key));
//		}
		for (String keyprint : keysx) {
			Collection<String> values = newMap.get(keyprint);
			ArrayList<String> arr = new ArrayList<String>();
			for(String a : values) {
//				System.out.println(a);
				if(arr.indexOf(a) == -1) {
					arr.add(a);
				}			
			}		
			similarMap.get(keyprint).put("word", arr);
		}
//		for(String key : similarMap.keySet()) {
//			System.out.println(key + "::" + similarMap.get(key));
//		}
//		System.exit(0);
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<CompareStringInfo>> futures = new ArrayList<Future<CompareStringInfo>>();
		HashMap<String, Double> finalMap = new HashMap<String, Double>();
		for (Object o : similarMap.entrySet()) {
			Callable<CompareStringInfo> callable = new Callable<CompareStringInfo>() {

				public CompareStringInfo call() throws Exception {
					CompareStringInfo compareStringInfo = new CompareStringInfo();
					compareStringInfo = compareStringUtil.compareWord(strTxtJaso, o, wordArray);
					return compareStringInfo;
				}
			};

			futures.add(executor.submit(callable));
		}

		for (Future<CompareStringInfo> future : futures) { // future에 담긴 결과 객체를 받아 List에 담는다. results.add(future.get()); 
			try {
				String key = future.get().getKey();
				finalMap.put(future.get().getKey(), future.get().getRate());

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(String key : finalMap.keySet()) {
			String convertKey = koreanJasoUtil.hangulToJaso(key.replaceAll(" ", ""));	
			BigDecimal rsltBig2 = compareStringUtil.getDistance(strTxtJaso,convertKey);
			if(finalMap.get(key) < rsltBig2.doubleValue()) {
				finalMap.put(key, rsltBig2.doubleValue());
			}
		}
		Map<String, Double> result = finalMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));

		JSONObject  endDataMap = new JSONObject();
		JSONArray jArray = new JSONArray();
		Set<String> resultKeySet = result.keySet();
		int cnt = 0;
		for (String keyprint : resultKeySet) {
			if(cnt == 10) {
				break;
			}
			String topRequest = shortRequestMap.get(keyprint);
			Double value = result.get(keyprint);
			ArrayList<String> responseArray = responseMap.get(topRequest);
//			//System.out.println("responseMap.get(topRequest) :: " + responseMap.get(topRequest).size());
			ResponseInfo resInfo = new ResponseInfo(keyprint, value, responseArray, entityParseDataInfo.getEntityMap());
			ResponseResultInfo responseResultInfo = responseParseUtil.getResponseParseData(resInfo);

			resInfo.setResponseResultInfo(responseResultInfo);
			Gson gson = new Gson();
			JSONParser parser = new JSONParser();
			JSONObject vcsObj  = (JSONObject)parser.parse(gson.toJson(resInfo));
			vcsObj.put("top", topRequest);
//			//System.out.println("vcsObj :: " + jSONUtil.printJsonPretty(vcsObj));
			
			//			String topRequest = shortRequestMap.get(keyprint);
//			endDataMap.put(topRequest, vcsObj);
			jArray.add(vcsObj);
			
			
			cnt++;
			continue;
		}
		//System.out.println("============= short message ============");
//		Set<String> keys = endDataMap.keySet();
//		for(String key : keys) {
//			//System.out.println(key + "::" + endDataMap.get(key));
//		}
//		for(int i = 0 ; i < jArray.size() ; i++) {
//			//System.out.println(jArray.get(i));
//		}
		
		

		return jArray;
	}


	public JSONArray getDataOrderList(String strTxt) throws ParseException{
		Multimap<String, String[]> entityMap = learningDataSet.getEntityMap();
		HashMap<String, String> similarStr = learningDataSet.getSimilarStr();
		Multimap<String, JSONObject> wordGroupStringAll = learningDataSet.getWordGroupStringAll();
		HashMap<String, String> orgTopRequestMap = learningDataSet.getOrgTopRequestMap();
		HashMap<String, ArrayList<String>> responseMap = learningDataSet.getResponseMap();
		Set<String> keySet = similarStr.keySet();

		String [] dist = {"구역"};
		EntityInfo intagDataInfo = entityParseUtil.getEntityLeftValue(strTxt, dist);
		String valueE = intagDataInfo.getEntityValue() + intagDataInfo.getGroupName();
		//		
		EntityParseDataInfo entityParseDataInfo = entityParseUtil.getEntityList(strTxt);

		//System.out.println(entityParseDataInfo.getEntityMap());

		strTxt = entityParseDataInfo.getParseTxt();
		strTxt = strTxt.replaceAll(" ", "");
		KoreanJasoUtil koreanJasoUtil = new KoreanJasoUtil();
		String strTxtJaso = koreanJasoUtil.hangulToJaso(strTxt);
		ArrayList<String> wordArray = wordUtil.getDistWordList(strTxt);
		//		ArrayList<JSONObject> similarArray = new ArrayList<JSONObject>();

		//System.out.println("copare txt : " + strTxt);

		LinkedHashMap<String, JSONObject> similarMap = new LinkedHashMap<String, JSONObject>();
		
		Multimap<String, String> newMap = ArrayListMultimap.create();
		for(String wordJaso : wordArray) {
			Iterator<String> itr  = keySet.iterator();
			while(itr.hasNext()) {
				String mainWord = itr.next();
				String wordGroup = similarStr.get(mainWord);
				BigDecimal rlstBic = compareStringUtil.getDistance(wordJaso,mainWord);
				if( rlstBic.compareTo(new BigDecimal(0.8)) == 1) {
					//System.out.println(wordJaso + "::" + mainWord);
					Collection<JSONObject> objs = wordGroupStringAll.get(wordGroup);
					for (JSONObject obj : objs) {
						String dicString = obj.get("dic")+"";
						newMap.put(dicString, mainWord); 
						//System.out.println(dicString + "::" + mainWord);
						if(!similarMap.containsKey(dicString)) {
							String dicStringJaso = koreanJasoUtil.hangulToJaso(dicString);
							obj.put("dicJaso", dicStringJaso);
							similarMap.put(dicString, obj);
						}
					}
				}
			}
		}

		Set<String> keysx = newMap.keySet();
		for (String keyprint : keysx) {
			Collection<String> values = newMap.get(keyprint);
			ArrayList<String> arr = new ArrayList<String>();
			for(String a : values) {
				if(arr.indexOf(a) == -1) {
					arr.add(a);
				}			
			}		
			if(arr.size() < 2) {
				similarMap.remove(keyprint);
			}else {
				similarMap.get(keyprint).put("word", arr);
			}

		}


		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<CompareStringInfo>> futures = new ArrayList<Future<CompareStringInfo>>();
		HashMap<String, Double> finalMap = new HashMap<String, Double>();
		for (Object o : similarMap.entrySet()) {
			Callable<CompareStringInfo> callable = new Callable<CompareStringInfo>() {

				public CompareStringInfo call() throws Exception {
					CompareStringInfo compareStringInfo = new CompareStringInfo();
					compareStringInfo = compareStringUtil.compareWord(strTxtJaso, o, wordArray);
					return compareStringInfo;
				}
			};

			futures.add(executor.submit(callable));
		}

		for (Future<CompareStringInfo> future : futures) { // future에 담긴 결과 객체를 받아 List에 담는다. results.add(future.get()); 
			try {
				String key = future.get().getKey();
				finalMap.put(future.get().getKey(), future.get().getRate());

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(String key : finalMap.keySet()) {
			String convertKey = koreanJasoUtil.hangulToJaso(key.replaceAll(" ", ""));	
			BigDecimal rsltBig2 = compareStringUtil.getDistance(strTxtJaso,convertKey);
			if(finalMap.get(key) < rsltBig2.doubleValue()) {
				finalMap.put(key, rsltBig2.doubleValue());
			}
		}
		Map<String, Double> result = finalMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));

		JSONObject endDataMap = new JSONObject();
		Set<String> resultKeySet = result.keySet();
		int cnt = 0;
		JSONArray jArray = new JSONArray();
		for (String keyprint : resultKeySet) {
			if(cnt == 10) {
				break;
			}
			Double value = result.get(keyprint);
			String topRequest = orgTopRequestMap.get(keyprint);
			ArrayList<String> responseArray = responseMap.get(topRequest);
			ResponseInfo resInfo = new ResponseInfo(keyprint, value, responseArray, entityParseDataInfo.getEntityMap());
			ResponseResultInfo responseResultInfo = responseParseUtil.getResponseParseData(resInfo);
			resInfo.setResponseResultInfo(responseResultInfo);
			Gson gson = new Gson();
			JSONParser parser = new JSONParser();
			JSONObject vcsObj  = (JSONObject)parser.parse(gson.toJson(resInfo));
			vcsObj.put("top", topRequest);
			//			String topRequest = shortRequestMap.get(keyprint);
//			endDataMap.put(topRequest, vcsObj);
			jArray.add(vcsObj);
			cnt++;

		}
//		//System.out.println("============= long message ============");
//		for(int i = 0 ; i < jArray.size() ; i++) {
//			//System.out.println(jArray.get(i));
//		}
		return jArray;
	}
}
