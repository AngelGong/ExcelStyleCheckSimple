package com.ctrip.excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class SheetBase {
	public void sheetReader(XSSFSheet sheet)
	{
		ExcelRemark.currentSheet = sheet;
	}
}
