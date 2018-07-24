/**
 * 0. Project  : Qwave 쳇봇 개발을 위한 프로토 타입
 *
 * 1. FileName : ExcelUtil.java
 * 2. Package : kr.co.qwave.inference.common.util
 * 3. Comment : 
 * 4. 작성자  : sky
 * 5. 작성일  : 2018. 7. 19. 오후 2:07:46
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    sky : 2018. 7. 19. :            : 신규 개발.
 */

package kr.co.qwave.inference.common.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

/**
* <pre>
* 간략 : .
* 상세 : .
* kr.co.qwave.inference.common.util
*   |_ ExcelUtil.java
* </pre>
* 
* @Company : (주)퀀텀웨이브
* @Author  : sky
* @Date    : 2018. 7. 19. 오후 2:07:46
* @Version : 1.0
*/
@Component
public class ExcelUtil {
	
	/**
	* <PRE> Excel File 을 읽어 workbook 을 반환한다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param filePath
	* @return
	* @throws IOException 
	*/
	public XSSFWorkbook getXSSWorkBook(String filePath) throws IOException {
		FileInputStream file = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(file);		
		return workbook;
	}
	
	
	/**
	* <PRE> Cell 값의 타입에 따라 데이터를 Text로 변환해준다.
	* 간략 : .
	* 상세 : .
	* <PRE>
	* @param cell
	* @return 
	*/
	public String getCellValue(XSSFCell cell) {
		String value = "";
		switch (cell.getCellType()){
		case XSSFCell.CELL_TYPE_FORMULA:
			value=cell.getCellFormula();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			value=cell.getNumericCellValue()+"";
			break;
		case XSSFCell.CELL_TYPE_STRING:
			value=cell.getStringCellValue()+"";
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			value=cell.getBooleanCellValue()+"";
			break;
		case XSSFCell.CELL_TYPE_ERROR:
			value=cell.getErrorCellValue()+"";
			break;
		}
		return value;
	}
	
	
}
