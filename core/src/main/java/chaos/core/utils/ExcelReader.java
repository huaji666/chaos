package chaos.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取Excel 97~2003 xls格式 /2007~ xlsx格式
 * @author		ZhangLiKun
 * @mail		likun_zhang@yeah.net
 * @date		2013-5-11
 */
public class ExcelReader {

	/**
	 * 创建工作簿对象
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @date	2013-5-11
	 */
	public static final Workbook createWb(String filePath) throws IOException {
		if(StringUtils.isBlank(filePath)) {
			throw new IllegalArgumentException("参数错误!!!") ;
		}
		if(filePath.trim().toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(new FileInputStream(filePath)) ;
		} else if(filePath.trim().toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(new FileInputStream(filePath)) ;
		} else {
			throw new IllegalArgumentException("不支持除：xls/xlsx以外的文件格式!!!") ;
		}
	}
	
	public static final Sheet getSheet(Workbook wb , String sheetName) {
		return wb.getSheet(sheetName) ;
	}
	
	public static final Sheet getSheet(Workbook wb ,int index) {
		return wb.getSheetAt(index) ;
	}
	
	public static final List<Object[]> listFromSheet(Sheet sheet) {
		
		int rowTotal = sheet.getPhysicalNumberOfRows() ;
//		Debug.printf("{}共有{}行记录！" ,sheet.getSheetName() ,rowTotal) ;
		
		List<Object[]> list = new ArrayList<Object[]>() ;
		for(int r = sheet.getFirstRowNum() ; r <= sheet.getLastRowNum() ; r ++) {
			Row row = sheet.getRow(r) ;
			if(row == null)continue ;
			// 不能用row.getPhysicalNumberOfCells()，可能会有空cell导致索引溢出
			// 使用row.getLastCellNum()至少可以保证索引不溢出，但会有很多Null值，如果使用集合的话，就不说了
			Object[] cells = new Object[row.getLastCellNum()] ;	
			for(int c = row.getFirstCellNum() ; c <= row.getLastCellNum() ; c++) {
				Cell cell = row.getCell(c) ;
				if(cell == null)continue ;
				cells[c] = getValueFromCell(cell) ;
			}
			list.add(cells) ;
		}
		
		return list ;
	}
	
	
	/**
	 * 获取单元格内文本信息
	 * @param cell
	 * @return
	 * @date	2013-5-8
	 */
	public static final String getValueFromCell(Cell cell) {
		if(cell == null) {
//			printf("Cell is null !!!") ;
			return null ;
		}
//		assert cell==null:"Cell is null !!!";
		String value = null ;
		switch(cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC :	// 数字
				if(HSSFDateUtil.isCellDateFormatted(cell)) {		// 如果是日期类型
					// TODO: 2016-10-31 日期处理
//					value = new SimpleDateFormat(DatePattern.LOCALE_ZH_DATE.getValue()).format(cell.getDateCellValue()) ;
				} else 	value = String.valueOf(cell.getNumericCellValue()) ;
				break ;
			case Cell.CELL_TYPE_STRING:		// 字符串
				value = cell.getStringCellValue() ;
				break ;
			case Cell.CELL_TYPE_FORMULA:	// 公式
				// 用数字方式获取公式结果，根据值判断是否为日期类型
				double numericValue = cell.getNumericCellValue() ;
				if(HSSFDateUtil.isValidExcelDate(numericValue)) {	// 如果是日期类型
					// TODO: 2016-10-31 日期处理
//					value = new SimpleDateFormat(DatePattern.LOCALE_ZH_DATE.getValue()).format(cell.getDateCellValue()) ;
				} else 	value = String.valueOf(numericValue) ;
				break ;
			case Cell.CELL_TYPE_BLANK:				// 空白
//				value = ExcelConstants.EMPTY_CELL_VALUE ;
				// TODO: 2016-10-31 空白
//				value = ExcelConstants.EMPTY_CELL_VALUE ;
				break ;
			case Cell.CELL_TYPE_BOOLEAN:			// Boolean
				value = String.valueOf(cell.getBooleanCellValue()) ;
				break ;
			case Cell.CELL_TYPE_ERROR:				// Error，返回错误码
				value = String.valueOf(cell.getErrorCellValue()) ;
				break ;
			default:value = StringUtils.EMPTY ;break ;
		}
		// 使用[]记录坐标
		return value + "["+cell.getRowIndex()+","+cell.getColumnIndex()+"]" ;
	}	
	
}