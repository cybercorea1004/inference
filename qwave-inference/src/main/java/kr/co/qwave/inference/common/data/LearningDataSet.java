/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : LearningDataSet.java
 * 2. Package : kr.co.qwave.inference.data
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오전 11:41:38
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import kr.co.qwave.inference.QwaveInferenceApplication;
import kr.co.qwave.inference.common.util.KoreanJasoUtil;

/**
 * <pre>
 * 간략 : .
 * 상세 : .
 * kr.co.qwave.inference.common.data
 *   |_ LearningDataSet.java
 * </pre>
 * 
 * @Company : (주)퀀텀웨이브
 * @Author : sky
 * @Date : 2018. 7. 19. 오전 11:41:38
 * @Version : 1.0
 */
@Component
public class LearningDataSet {
	private static final Logger logger = LoggerFactory.getLogger(LearningDataSet.class);
	@Autowired
	KoreanJasoUtil koreanJasoUtil;

	private HashMap<String, String> similarStr = new HashMap<String, String>();
	private HashMap<String, ArrayList<String>> similarStr_grp = new HashMap<String, ArrayList<String>>();
	private Multimap<String, JSONObject> wordGroupStringAll = ArrayListMultimap.create();
//	private Set<String> keySet;
	private HashMap<String, String> orgTopRequestMap;
	private HashMap<String, String> shortRequestMap;
	private HashMap<String, ArrayList<String>> responseMap;
	private Multimap<String, String[]> entityMap = ArrayListMultimap.create();
	private ArrayList<String> josaList = new ArrayList<String>();

	/*
	 * 단문
	 */
	public Multimap<String, JSONObject> wordShortGroupStringAll = ArrayListMultimap.create();
	public ArrayList<String> dicString = new ArrayList<String>();
	public ArrayList<String> dicShortString = new ArrayList<String>();

	public JSONObject dataObj = new JSONObject();

	public HashMap<String, String> getSimilarStr() {
		return similarStr;
	}

	public void setSimilarStr(HashMap<String, String> similarStr) {
		this.similarStr = similarStr;
//		this.keySet = similarStr.keySet();
	}

	public HashMap<String, ArrayList<String>> getSimilarStr_grp() {
		return similarStr_grp;
	}

	public void setSimilarStr_grp(HashMap<String, ArrayList<String>> similarStr_grp) {
		this.similarStr_grp = similarStr_grp;
	}

	public KoreanJasoUtil getKoreanJasoUtil() {
		return koreanJasoUtil;
	}

	public void setKoreanJasoUtil(KoreanJasoUtil koreanJasoUtil) {
		this.koreanJasoUtil = koreanJasoUtil;
	}

	public Multimap<String, JSONObject> getWordGroupStringAll() {
		return wordGroupStringAll;
	}

	public void setWordGroupStringAll(Multimap<String, JSONObject> wordGroupStringAll) {
		this.wordGroupStringAll = wordGroupStringAll;
	}

//	public Set<String> getKeySet() {
//		return keySet;
//	}
//
//	public void setKeySet(Set<String> keySet) {
//		this.keySet = keySet;
//	}

	public HashMap<String, String> getOrgTopRequestMap() {
		return orgTopRequestMap;
	}

	public void setOrgTopRequestMap(HashMap<String, String> orgTopRequestMap) {
		this.orgTopRequestMap = orgTopRequestMap;
	}

	public HashMap<String, String> getShortRequestMap() {
		return shortRequestMap;
	}

	public void setShortRequestMap(HashMap<String, String> shortRequestMap) {
		this.shortRequestMap = shortRequestMap;
	}

	public HashMap<String, ArrayList<String>> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(HashMap<String, ArrayList<String>> responseMap) {
		this.responseMap = responseMap;
	}

	public Multimap<String, String[]> getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(Multimap<String, String[]> entityMap) {
		this.entityMap = entityMap;
	}

	public ArrayList<String> getJosaList() {
		return josaList;
	}

	public void setJosaList(ArrayList<String> josaList) {
		this.josaList = josaList;
	}

	public Multimap<String, JSONObject> getWordShortGroupStringAll() {
		return wordShortGroupStringAll;
	}

	public void setWordShortGroupStringAll(Multimap<String, JSONObject> wordShortGroupStringAll) {
		this.wordShortGroupStringAll = wordShortGroupStringAll;
	}

	public ArrayList<String> getDicString() {
		return dicString;
	}

	public void setDicString(ArrayList<String> dicString) {
		this.dicString = dicString;
	}

	public ArrayList<String> getDicShortString() {
		return dicShortString;
	}

	public void setDicShortString(ArrayList<String> dicShortString) {
		this.dicShortString = dicShortString;
	}

	public JSONObject getDataObj() {
		return dataObj;
	}

	public void setDataObj(JSONObject dataObj) {
		this.dataObj = dataObj;
	}

	/**
	 * <PRE>
	 * 들어온 질문 및 엔티티, 키워드를 조합 추론 가능한 데이터로 가공한다. 간략 : . 상세 : .
	 * 
	 * <PRE>
	 */
	@SuppressWarnings("unchecked")
	public void setLearningData() {
		/*
		 * 짧은 문장(9자 이하)에 대한 러닝을 진행한다.
		 */
		for (String dic : dicShortString) {
			HashSet<String> shortWordArray = new HashSet<String>();
			Multimap<String, String> matchShortWordGroup = ArrayListMultimap.create();
			String[] dicArray = dic.split(" ");
			for (String dicWord : dicArray) {
				String dicWordJaso = koreanJasoUtil.hangulToJaso(dicWord);
				if (similarStr.containsKey(dicWordJaso)) {
					String wordGroup = similarStr.get(dicWordJaso);
					ArrayList<String> wordList = similarStr_grp.get(wordGroup);
					shortWordArray.addAll(wordList);
					for (String word_ : wordList) {
						matchShortWordGroup.put(word_, dicWordJaso);
					}
				}
			}
			ArrayList<String> dataList = new ArrayList<String>();
			dataList.clear();
			dataList.addAll(shortWordArray);
			JSONObject dicObj = new JSONObject();
			dicObj.put("word", dataList);
			dicObj.put("dic", dic);
			dicObj.put("mg", matchShortWordGroup);
			for (String grp : shortWordArray) {
				this.wordShortGroupStringAll.put(grp, dicObj);
			}
		}

		/*
		 * 장문에 대한 데이터를 러닝한다.
		 */
		for (String dic : dicString) {
			Iterator<String> itr = similarStr.keySet().iterator();
			String dicJaso = koreanJasoUtil.hangulToJaso(dic);
			ArrayList<String> wordGroupArray = new ArrayList<String>();
			HashSet<String> wordArray = new HashSet<String>();
			Multimap<String, String> matchWordGroup = ArrayListMultimap.create();
			while (itr.hasNext()) {
				String word = itr.next();
				String wordGroup = similarStr.get(word);
				if (dicJaso.indexOf(word) != -1) {
					if (wordGroupArray.indexOf(wordGroup) == -1) {
						wordGroupArray.add(wordGroup);
					}
					ArrayList<String> wordList = similarStr_grp.get(wordGroup);
					wordArray.addAll(wordList);
					for (String word_ : wordList) {
						matchWordGroup.put(word_, word);
					}
				}
			}
			ArrayList<String> dataList = new ArrayList<String>();
			dataList.clear();
			dataList.addAll(wordArray);
			JSONObject dicObj = new JSONObject();
			dicObj.put("word", dataList);
			dicObj.put("dic", dic);
			dicObj.put("mg", matchWordGroup);
			for (String grp : wordGroupArray) {
				this.wordGroupStringAll.put(grp, dicObj);
			}
		}
	}
}
