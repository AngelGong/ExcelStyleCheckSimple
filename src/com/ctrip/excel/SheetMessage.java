package com.ctrip.excel;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.ctrip.main.Config;
import com.ctrip.model.ClassModel;
import com.ctrip.model.ItemModel;
import com.ctrip.model.ServiceModel;

public class SheetMessage extends SheetBase {
	public SheetMessage()
	{
		serviceModel = new ServiceModel();
	}
	
	public ServiceModel serviceModel = null;
	private int nameIndex = 0;
	private int typeIndex = 0;
	private int metadataIndex = 0;
	private int lengthIndex = 0;
	private int requireIndex = 0;
	private int rangeIndex = 0;
	private int versionIndex = 0;
	private int remarkIndex = 0;
	
	public ServiceModel getServiceModel() {
		return serviceModel;
	}

	public void setServiceModel(ServiceModel serviceModel) {
		this.serviceModel = serviceModel;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public int getTypeIndex() {
		return typeIndex;
	}

	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}

	public int getMetadataIndex() {
		return metadataIndex;
	}

	public void setMetadataIndex(int metadataIndex) {
		this.metadataIndex = metadataIndex;
	}

	public int getLengthIndex() {
		return lengthIndex;
	}

	public void setLengthIndex(int lengthIndex) {
		this.lengthIndex = lengthIndex;
	}

	public int getRequireIndex() {
		return requireIndex;
	}

	public void setRequireIndex(int requireIndex) {
		this.requireIndex = requireIndex;
	}

	public int getRangeIndex() {
		return rangeIndex;
	}

	public void setRangeIndex(int rangeIndex) {
		this.rangeIndex = rangeIndex;
	}

	public int getVersionIndex() {
		return versionIndex;
	}

	public void setVersionIndex(int versionIndex) {
		this.versionIndex = versionIndex;
	}

	public int getRemarkIndex() {
		return remarkIndex;
	}

	public void setRemarkIndex(int remarkIndex) {
		this.remarkIndex = remarkIndex;
	}

	@Override	
	public void sheetReader(XSSFSheet sheet)
	{
		super.sheetReader(sheet);
		ExcelCheck.serviceInfoCheck(this.serviceModel);
		int firstRowNumber = sheet.getFirstRowNum();
		XSSFRow row = sheet.getRow(firstRowNumber);
		initIndex(row);
		
		ClassModel classModel = null;
		int index = 0;
		if("".equals(sheet.getRow(firstRowNumber+1).getCell(0).toString()))
		{
			ExcelCheck.isClassNameExist(classModel);
			classModel = new ClassModel();
			classModel.setType("Request");
			serviceModel.setReqModel(classModel);
		}
		for(int i = firstRowNumber+1;;i++)
		{
			row = sheet.getRow(i);
			if(row == null || row.getCell(0) == null) //读完最后一行的处理
			{
				endSetting(classModel);
				break;
			}
			if(!("".equals(row.getCell(0).toString())))
			{	
				String temp = row.getCell(0).toString();
				index = 0;
				ExcelCheck.isRequestOrResponse(temp);
				classModel = new ClassModel();
				if(serviceModel.getReqModel() == null)
				{
					classModel.setType("Request");
					serviceModel.setReqModel(classModel);
				}
				else
				{
					classModel.setType("Response");
					serviceModel.setResModel(classModel);
				}
				classModel.setName(temp);
			}
			
			ItemModel temp = getItem(row,index,nameIndex);
			if("Ignore".equals(temp.getMetadata()))
			{
				continue;
			}
			else if("".equals(temp.getName()))
			{
				endSetting(classModel);
				break;
			}
			else{
				classModel.getItemList().add(temp);
				if(temp.isModel())
				{
					i = getModel(i,temp,sheet,nameIndex);
				}
			}
			index++;
		}
		ExcelCheck.serviceCheck(serviceModel);
	}
	
	private void endSetting(ClassModel classModel)
	{
		if("Request".equals(classModel.getType()))
				System.out.println("服务没有Response，请确认！");
		else serviceModel.setResModel(classModel);
	}
	
	private ItemModel getItem(XSSFRow row,int index,int nameIndex)
	{
		ItemModel itemModel = new ItemModel();
		try{
			if(row == null) return new ItemModel();
			Cell cell = null;
			
			cell = row.getCell(nameIndex);	//名称
			itemModel.setName(cell.toString().trim());	
			itemModel.setIndex("" + index);		//index
			cell = row.getCell(lengthIndex);	//长度
			if(cell != null)
			{
				int length = (int)cell.getNumericCellValue();
				itemModel.setLength(length == 0? "":""+length);
			}
			cell = row.getCell(requireIndex);
			if(cell != null)
			{
				String required = cell.toString();
				itemModel.setRequire("Y".equals(required)?"True":"False");
			}
			else itemModel.setRequire("False");
			cell = row.getCell(typeIndex);
			if(cell != null)
				itemModel.setType(cell.toString());
			cell = row.getCell(metadataIndex);
			if(cell != null)
				itemModel.setMetadata(cell.toString());
			else System.out.println("Error：Metadata不可以为空");
			cell = row.getCell(rangeIndex);
			if(cell != null)
				itemModel.setFormat(cell.toString().replaceAll("\\n", ";"));
			cell = row.getCell(remarkIndex);
			if(cell != null)
				itemModel.setDescription(cell.toString().replaceAll("\\n", ""));
			cell = row.getCell(versionIndex);
			if(cell != null)
				itemModel.setVersion(""+(int)cell.getNumericCellValue());
			String str = ExcelCheck.itemModelCheck(itemModel);
		}catch(Exception e)
		{
			System.out.println("RowNumber:"+(row.getRowNum()+1)+" 读取字段"+itemModel.getName()+"时抛出了异常，请检查字段类型和属性信息");
		}
		return itemModel;
	}
	
	private int getModel(int rowNumber,ItemModel itemModel,XSSFSheet sheet,int nameIndex)
	{
		int modelNameIndex = nameIndex + 1;
		XSSFRow row;
		int index = 0;
		for(++rowNumber;;rowNumber++)
		{
			row = sheet.getRow(rowNumber);
			ItemModel temp = getItem(row,index,modelNameIndex);
			if("Ignore".equals(temp.getMetadata()))
			{
				continue;
			}
			else if("".equals(temp.getName()))
			{
				ExcelCheck.itemModelFieldCheck(itemModel);
				return rowNumber-1;
			}
			else{
				itemModel.getItemList().add(temp);
				if(temp.isModel())
				{
					rowNumber = getModel(rowNumber,temp,sheet,modelNameIndex);
				}
			}
			index++;
		}
	}
	
	private void initIndex(XSSFRow row)
	{
		Iterator<Cell> iterator = row.cellIterator();
		while(iterator.hasNext())
		{
			Cell temp = iterator.next();
			if(Config.variableName.equals(temp.toString().trim()))
			{
				nameIndex = temp.getColumnIndex();
			}
			else if(Config.variableLength.equals(temp.toString().trim()))
			{
				lengthIndex = temp.getColumnIndex();
			}
			else if(Config.variableRange.equals(temp.toString().trim()))
			{
				rangeIndex = temp.getColumnIndex();
			}
			else if(Config.variableMetadata.equals(temp.toString().trim())||"MetaData".equals(temp.toString().trim()))
			{
				metadataIndex = temp.getColumnIndex();
			}
			else if(Config.variableRemark.equals(temp.toString().trim()))
			{
				remarkIndex = temp.getColumnIndex();
			}
			else if(Config.variableType.equals(temp.toString().trim()))
			{
				typeIndex = temp.getColumnIndex();
			}
			else if(Config.variableVersion.equals(temp.toString().trim()))
			{
				versionIndex = temp.getColumnIndex();
			}
			else if(Config.variableRequire.equals(temp.toString().trim()))
			{
				requireIndex = temp.getColumnIndex();
			}
		}
		//检查报文详情Sheet文档格式是否合法
		ExcelCheck.messageColIndex(this);
	}
}
