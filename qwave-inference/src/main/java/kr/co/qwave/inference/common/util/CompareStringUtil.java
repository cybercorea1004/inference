/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : CompareStringUtil.java
 * 2. Package : kr.co.qwave.inference.common.util
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 6:17:52
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Multimap;

import kr.co.qwave.inference.common.data.LearningDataSet;
import kr.co.qwave.inference.common.info.CompareStringInfo;


/**
 * <pre>
 * 간략 : .
 * 상세 : .
 * kr.co.qwave.inference.common.util
 *   |_ CompareStringUtil.java
 * </pre>
 * 
 * @Company : (주)퀀텀웨이브
 * @Author  : sky
 * @Date    : 2018. 7. 19. 오후 6:17:52
 * @Version : 1.0
 */
@Component
public class CompareStringUtil {
	@Autowired
	LearningDataSet learningDataSet;

	public CompareStringInfo compareWord(String strTxtJaso, Object dicObj, ArrayList<String> wordArray) {
		int inputStringSize = strTxtJaso.trim().length();
		int dicStringSize = 0;
		int standardSize = 0;
		boolean isCompareStandardInputStr = false;
		
		ArrayList<String> josaList = learningDataSet.getJosaList();

//		System.out.println(dicObj);
		Map.Entry e = (Map.Entry) dicObj;
		String dicString = (String) e.getKey();
//		System.out.println("dicString ::" + dicString);
		JSONObject obj = (JSONObject) e.getValue();		
		String dicJaso = (obj.get("dicJaso")+"").replaceAll(" ", "");
		dicStringSize = dicJaso.length();
		standardSize = dicStringSize;

		ArrayList<String> dicWordArray = (ArrayList<String>) obj.get("word");
//		for(String s : dicWordArray) {
//			System.out.println(dicWordArray);
//		}
		Multimap<String, String> matchWordMap = (Multimap<String, String>) obj.get("mg");
		isCompareStandardInputStr = false;

		if(inputStringSize > dicStringSize) {
			standardSize = inputStringSize;
			isCompareStandardInputStr = true;
		}

		BigDecimal standardSizeDecimal = new BigDecimal(standardSize);

		HashMap<String, BigDecimal> tempMap = new HashMap<String, BigDecimal>();

		for(String word : wordArray) {
			String key = "";
			for(int i = 0 ; i < dicWordArray.size() ; i ++) {
				String dicWord = dicWordArray.get(i);
				BigDecimal calcLast = new BigDecimal(0);

				BigDecimal rlstBic = getDistance(word,dicWord);
				
				//System.out.println("1 ==> " + word + " :: " + dicWord);
				if(rlstBic.compareTo(new BigDecimal(0.85)) == 1) {
//					System.out.println("2 ==> " + word + " :: " + dicWord  + "(" + rlstBic + ")");
					if(isCompareStandardInputStr) {//입력 받은 문구가 더큼
						BigDecimal sliceLength = new BigDecimal(word.length());
						BigDecimal sliceLength_ = sliceLength.divide(standardSizeDecimal, 5, BigDecimal.ROUND_UP);
						calcLast = sliceLength_.multiply(rlstBic);
						key = word;
					}else{ //비교대상 스트링의 길이가 더 큼
						Collection<String> matchObjs = matchWordMap.get(dicWord);
						BigDecimal compareDecimal = new BigDecimal(0);
						for (String matchStr : matchObjs) {
							BigDecimal sliceLength = new BigDecimal(matchStr.length());
							BigDecimal sliceLength_ = sliceLength.divide(standardSizeDecimal, 5, BigDecimal.ROUND_UP);
							calcLast = sliceLength_.multiply(rlstBic);
							if(calcLast.compareTo(compareDecimal) == 1) {
								key = matchStr;
								compareDecimal = calcLast;
							}
						}
						calcLast = compareDecimal;
					}

					boolean isInput = true;
					boolean isReplace = false;
					if(tempMap.isEmpty()) {
						tempMap.put(key, calcLast);
					}else {
						BigDecimal bigT = new BigDecimal(0);
						String keyt = "";

						Set<String> keySet = tempMap.keySet();
						Iterator<String> itr = keySet.iterator();
						while(itr.hasNext()) {
							keyt = itr.next();
							bigT = (BigDecimal) tempMap.get(keyt);	

							if(keyt.indexOf(key) != -1) {
								isInput = false;
								break;
							}else {
								if(key.indexOf(keyt) != -1) {
									isReplace = true;
									break;
								}
							}
						}

						if(isInput) {
							tempMap.put(key, calcLast);
						}
						if(isReplace) {
							tempMap.remove(keyt);
						}
					}
				}else {
//					System.out.println("1 ==> " + word + " :: " + dicWord+ "(" + rlstBic + ")");
				}
			}

		}
		BigDecimal lastRate = new BigDecimal(0);
		Set<String> keySet = tempMap.keySet();
		Iterator<String> itr = keySet.iterator();
		KoreanJasoUtil koreanJasoUtil = new KoreanJasoUtil();
		String dicStringJamo = strTxtJaso;

		while(itr.hasNext()) {
			String keyt = itr.next();
			BigDecimal lastValue = (BigDecimal) tempMap.get(keyt);	
			lastRate = lastRate.add(lastValue);
			dicStringJamo = dicStringJamo.replaceAll(keyt, "||");
		}

		String [] splitDic = dicStringJamo.split("[||]");
		for(String sd : splitDic) {
			if(!sd.equals("")) {
				if(josaList.indexOf(sd) != -1) {
					BigDecimal sliceLength = new BigDecimal(sd.length());
					BigDecimal sliceLength_ = sliceLength.divide(standardSizeDecimal, 5, BigDecimal.ROUND_UP);
					lastRate = lastRate.add(sliceLength_);
				}
			}
		}


		return new CompareStringInfo(dicString, lastRate.doubleValue());
	}

	/** 두단어의 거리를 비교 리턴한다.
	 * @param inputWord
	 * @param checkWord
	 * @return
	 */
	public   BigDecimal  getDistance(String inputWord, String checkWord) {
		int[][] wordMartix;
		int tSize = inputWord.length();
		if(inputWord.length() < checkWord.length()) {
			tSize = checkWord.length();
		}
		wordMartix = new int[inputWord.length() + 1][checkWord.length() + 1];

		for (int i = 0; i <= inputWord.length(); i++) {
			wordMartix[i][0] = i;
		}

		for (int j = 0; j <= checkWord.length(); j++) {
			wordMartix[0][j] = j;
		}

		for (int i = 1; i < wordMartix.length; i++) {
			for (int j = 1; j < wordMartix[i].length; j++) {
				if (inputWord.charAt(i - 1) == checkWord.charAt(j - 1)) {
					wordMartix[i][j] = wordMartix[i - 1][j - 1];
				} else {
					int minimum = Integer.MAX_VALUE;
					if ((wordMartix[i - 1][j]) + 1 < minimum) {
						minimum = (wordMartix[i - 1][j]) + 1;
					}

					if ((wordMartix[i][j - 1]) + 1 < minimum) {
						minimum = (wordMartix[i][j - 1]) + 1;
					}

					if ((wordMartix[i - 1][j - 1]) + 1 < minimum) {
						minimum = (wordMartix[i - 1][j - 1]) + 1;
					}

					wordMartix[i][j] = minimum;
				}
			}
		}
		tSize = inputWord.length() + checkWord.length();

		BigDecimal bic1 = new BigDecimal(tSize);
		BigDecimal bic2 = new BigDecimal(wordMartix[inputWord.length()][checkWord.length()]);
		BigDecimal gap = bic1.subtract(bic2.multiply(new BigDecimal(2))).divide(bic1, 5, BigDecimal.ROUND_UP);
		if(gap.doubleValue() < 0) {
			gap = new BigDecimal(0);
		}
		return gap;
	}
}
