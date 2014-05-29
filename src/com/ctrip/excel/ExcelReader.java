package com.ctrip.excel;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ctrip.doc.ServiceXml;
import com.ctrip.model.ServiceModel;

public class ExcelReader {
	public static XSSFWorkbook xwb = null;
	public ExcelReader(){}
	public ExcelReader(XSSFWorkbook xwb)
	{
		this.xwb = xwb;
		ExcelRemark.initStatic(xwb);
	}
	/**
	 * 按服务号生成单个服务的xml
	 * @param serviceCode
	 */
	public void getSingelXml(String serviceCode)
	{
		XSSFSheet sheet = xwb.getSheet("Overview");
		SheetMessage sheetMessage = new SheetMessage();
		sheetMessage.serviceModel = new SheetOverview().sheetReader(sheet,serviceCode);
		if(sheetMessage.serviceModel == null) return;
		sheet = xwb.getSheet(sheetMessage.serviceModel.getServiceCode());
		sheetMessage.sheetReader(sheet);
		ServiceXml serviceXml = new ServiceXml(sheetMessage.serviceModel);
		serviceXml.getDoc();
	}
	/**
	 * 生成最新版本涉及变更的所有服务的xml
	 */
	public void getNewestXml()
	{
		XSSFSheet sheet = xwb.getSheet("Overview");
		SheetOverview sheetOverview = new SheetOverview();
		sheetOverview.sheetReader(sheet);
	}
	
	public static void getSingelXml(XSSFSheet sheet,ServiceModel serviceModel)
	{
		System.out.println();
		System.out.println("====="+serviceModel.getServiceCode()+"=======================================");
		SheetMessage sheetMessage = new SheetMessage();
		sheetMessage.serviceModel = serviceModel;
		if(sheetMessage.serviceModel == null) return;
		sheetMessage.sheetReader(sheet);
		ServiceXml serviceXml = new ServiceXml(sheetMessage.serviceModel);
		serviceXml.getDoc();
	}
}
