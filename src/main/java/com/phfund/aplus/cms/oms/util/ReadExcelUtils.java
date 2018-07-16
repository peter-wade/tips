package com.phfund.aplus.cms.oms.util; 
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


  
/** 
 * 读取Excel 
 *  
 * @author zhuxiaolong 
 */  
public class ReadExcelUtils {  
    private Logger logger = LoggerFactory.getLogger(ReadExcelUtils.class);  
    //错误信息接收器  
    private String errorMsg;  
    private Sheet sheet;  
    private Row row; 
    
    //读取excel文件
    public List<List<Object>> ReadExcelUtils(MultipartFile mFile,String number) {  

        String fileName = mFile.getOriginalFilename();//获取文件名  
        List<List<Object>>  excelData = null;
        try {  
            if (!validateExcel(fileName)) {// 验证文件名是否合格  
                return null ;  
            }  
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本  
            if (isExcel2007(fileName)) {  
                isExcel2003 = false;  
            }  
            logger.info("----------------读取Excel的内容------------");
            excelData = createExcel(mFile.getInputStream(), isExcel2003, number);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return excelData;  
    }  
      
    /** 
     * 根据excel里面的内容读取文件信息 
     * @param is 输入流 
     * @param isExcel2003 excel是2003还是2007版本 
     * @return 
     * @throws IOException 
     */ 
    public List<List<Object>> createExcel(InputStream is, boolean isExcel2003,String number) {
    	List<List<Object>> excelData = null;
        try{  
            Workbook wb = null;  
            if (isExcel2003) {// 当excel是2003时,创建excel2003  
                wb = new HSSFWorkbook(is);  
            } else {// 当excel是2007时,创建excel2007  
                wb = new XSSFWorkbook(is);  
            }  
            excelData = readExcelContent(wb,number);// 读取Excel里面数据的信息  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return excelData;  
  
    }  
	/** 
     * 读取Excel数据内容 
     *  
     * @param InputStream 
     * @return Map 包含单元格数据内容的Map对象 
     * @author zhuxiaolong 
     */  
    public List<List<Object>> readExcelContent(Workbook wb,String number) {    	
    	List<List<Object>> carryList = new ArrayList<List<Object>>();
        sheet = wb.getSheetAt(0);  
        // 得到总行数  
        int rowNum = sheet.getLastRowNum();  
        row = sheet.getRow(0);  
        int colNum = row.getPhysicalNumberOfCells();  
        // 正文内容应该从第二行开始,第一行为表头的标题  
        // 默认从第二行开始读正文数据
        int no = 1;
            for (int i = no; i <= rowNum; i++) {  
                row = sheet.getRow(i);  
                if(null == row) {
                	continue;
                }
                int j = 0;  
                List<Object> cellList = new ArrayList<Object>();
                while (j < colNum) { 
                	cellList.add(getCellFormatValue(row.getCell(j)));
                    j++;  
                }  
                carryList.add(cellList);
            }
  
        return carryList;  
    }  
  
    /** 
     *  
     * 根据Cell类型设置数据 
     *  
     * @param cell 
     * @return 
     * @author zhuxiaolong 
     */  
    private Object getCellFormatValue(Cell cell) {  
        Object cellvalue = "";  
        if (cell != null) {  
            // 判断当前Cell的Type  
            switch (cell.getCellType()) {  
            case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC  
            case Cell.CELL_TYPE_FORMULA: {  
                // 判断当前的cell是否为Date  
                if (DateUtil.isCellDateFormatted(cell)) {  
                    // 如果是Date类型则，转化为Data格式  
                    // data格式是带时分秒的：2013-7-10 0:00:00  
                    // cellvalue = cell.getDateCellValue().toLocaleString();  
                    // data格式是不带带时分秒的：2013-7-10  
                    Date date = cell.getDateCellValue();  
                    cellvalue = date;  
                } else {// 如果是纯数字  
  
                    // 取得当前Cell的数值  
                    cellvalue = String.valueOf(cell.getNumericCellValue());  
                }  
                break;  
            }  
            case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING  
                // 取得当前的Cell字符串  
                cellvalue = cell.getRichStringCellValue().getString();  
                break;  
            default:// 默认的Cell值  
                cellvalue = "";  
            }  
        } else {  
            cellvalue = "";  
        }  
        return cellvalue;  
    }  

    /** 
     * 验证EXCEL文件 
     *  
     * @param filePath 
     * @return 
     */  
    public boolean validateExcel(String filePath) {  
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {  
            errorMsg = "文件名不是excel格式";  
            return false;  
        }  
        return true;  
    }  
      
    // @描述：是否是2003的excel，返回true是2003   
    public static boolean isExcel2003(String filePath)  {    
         return filePath.matches("^.+\\.(?i)(xls)$");    
     }    
     
    //@描述：是否是2007的excel，返回true是2007   
    public static boolean isExcel2007(String filePath)  {    
         return filePath.matches("^.+\\.(?i)(xlsx)$");    
     }    
}  