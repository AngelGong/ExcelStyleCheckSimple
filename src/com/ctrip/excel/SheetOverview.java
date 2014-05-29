package com.ctrip.excel;

import java.text.DecimalFormat;
import java.util.Iterator;

import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ctrip.doc.ServiceXml;
import com.ctrip.main.Config;
import com.ctrip.model.ServiceModel;

public class SheetOverview extends SheetBase {
	public static int serviceCodeColIndex = 0;
	public static int serviceNameColIndex = 0;
	public static int serviceDescColIndex = 0;
	public static int serviceVersionIndex = 0;
	public static int serviceOperColIndex = 0;
	@Override
	public void sheetReader(XSSFSheet sheet)
	{
		super.sheetReader(sheet);
		int firstRowNumber = sheet.getFirstRowNum();
		XSSFRow firstRow = sheet.getRow(firstRowNumber);
		initIndex(firstRow);
		Row codeRow = null;
		ServiceModel serviceModel = null;
		for(Row row:sheet)
		{
			if(row == null)	break;
			if(row.getRowNum() == firstRowNumber) continue;
			String tempCode = getServiceCode(row.getCell(serviceCodeColIndex));
			if("".equals(tempCode))	break;
			else if("0".equals(tempCode))
			{
				if(Config.currentVersion.equals(row.getCell(serviceVersionIndex).toString()))
				{
					getMessageXml(codeRow,serviceModel,row,ExcelReader.xwb);
					super.sheetReader(sheet);
				}
			}
			else
			{
				codeRow = row;
				serviceModel = new ServiceModel();
				serviceModel.setServiceCode(tempCode);
				serviceModel.setClassName(row.getCell(serviceNameColIndex).toString());
				serviceModel.setDescription(row.getCell(serviceDescColIndex).toString().replaceAll("\n", ""));
				serviceModel.setType("Service");
				if(Config.currentVersion.equals(row.getCell(serviceVersionIndex).toString()))
				{
					getMessageXml(codeRow,serviceModel,row,ExcelReader.xwb);
					super.sheetReader(sheet);
				}
			}
		}
	}
	
	private void getMessageXml(Row codeRow,ServiceModel serviceModel,Row row,XSSFWorkbook xwb)
	{
		XSSFSheet sheet = xwb.getSheet(serviceModel.getServiceCode());
		if(sheet == null)
		{
			String tempStr = "Excel文件中没有找到名称为\""+serviceModel.getServiceCode()+"\"的Sheet";
			System.out.println(tempStr);
//			ExcelRemark.cellRemark(codeRow.getCell(serviceCodeColIndex), tempStr, 2, 2);
			return;
		}
		ExcelReader.getSingelXml(sheet,serviceModel);
	}
	
	public ServiceModel sheetReader(XSSFSheet sheet,String serviceCode)
	{
		int firstRowNumber = sheet.getFirstRowNum();
		XSSFRow row = sheet.getRow(firstRowNumber);
		initIndex(row);
		ServiceModel serviceModel = null;
		for(int i = firstRowNumber+1; i < sheet.getLastRowNum();i++)
		{
			row = sheet.getRow(i);
			String tempCode = getServiceCode(row.getCell(serviceCodeColIndex));
			if(serviceCode.equals(tempCode))
			{
				serviceModel = new ServiceModel();
				serviceModel.setServiceCode(tempCode);
				serviceModel.setClassName(row.getCell(serviceNameColIndex).toString());
				serviceModel.setDescription(row.getCell(serviceDescColIndex).toString().replaceAll("\n", ""));
				serviceModel.setType("Service");
				return serviceModel;
			}
		}
		System.out.println("没有找到服务号对应服务:"+serviceCode);
		return null;
	}

	public String getServiceCode(Cell cell)
	{
		String serviceCode = "";
		if(cell == null)
			return serviceCode;
		try {
			DecimalFormat df = new DecimalFormat("0");  
			serviceCode = df.format(cell.getNumericCellValue());
		} catch (Exception e) {
			System.out.println("请检查"+cell.toString()+"单元格的格式是否为常规");
//			ExcelRemark.cellRemark(cell, "请检查"+cell.toString()+"单元格的格式是否为常规", 2, 2);
			return "0";
		}
		return serviceCode;
	}
	
	private void initIndex(XSSFRow row)
	{
		Iterator<Cell> iterator = row.cellIterator();
		while(iterator.hasNext())
		{
			Cell temp = iterator.next();
			if(Config.ServiceCode.equals(temp.toString().trim()))
			{
				//服务号列
				serviceCodeColIndex = temp.getColumnIndex();
			}
			if(Config.version.equals(temp.toString().trim()))
			{
				//版本列
				serviceVersionIndex = temp.getColumnIndex();
			}
			if(Config.operation.equals(temp.toString().trim()))
			{
				//操作列
				serviceOperColIndex = temp.getColumnIndex();
			}
			if(Config.ServiceDescription.equals(temp.toString().trim()))
			{
				//服务描述
				serviceDescColIndex = temp.getColumnIndex();
			}
			if(Config.ServiceName.equals(temp.toString().trim()))
			{
				//服务名称列
				serviceNameColIndex = temp.getColumnIndex();
			}
		}
		//检查Overview文档格式是否合法
		ExcelCheck.overviewColIndex();
	}
}
