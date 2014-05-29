package com.ctrip.excel;

import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ctrip.main.Config;

public class ExcelWriter {
	public ExcelWriter()
	{
		
	}
	
	public void writerExcel(XSSFWorkbook workbook)
	{
		 FileOutputStream fileOut = null;
         try {
               fileOut = new FileOutputStream(Config.fileName);
               workbook.write(fileOut);
               fileOut.close();
           } catch (Exception e) {
               System.out.println("保存xlsx的时候发生的异常");
               e.printStackTrace();
           }
	}

}
