package com.ctrip.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.junit.runner.Request;

import com.ctrip.main.Config;
import com.ctrip.model.ClassModel;
import com.ctrip.model.ItemModel;
import com.ctrip.model.ServiceModel;

public class ExcelCheck {
	/**
	 * 检查报文Sheet中是否缺少字段属性信息
	 * @param sheet
	 */
	public static void messageColIndex(SheetMessage sheet)
	{
		if(sheet.getLengthIndex() == 0)
		{
			System.out.println("# # 没有找到长度列,正确的列标头为："+Config.variableLength);
		}
		if(sheet.getNameIndex() == 0)
		{
			System.out.println("# # 没有找到名称列,正确的列标头为："+Config.variableName);
		}
		if(sheet.getRequireIndex() == 0)
		{
			System.out.println("# # 没有找到Require列,正确的列标头为："+Config.variableRequire);
		}
		if(sheet.getTypeIndex() == 0)
		{
			System.out.println("# # 没有找到类型列,正确的列标头为："+Config.variableType);
		}
		if(sheet.getMetadataIndex() == 0)
		{
			System.out.println("# # 没有找到Medadata列,正确的列标头为："+Config.variableMetadata);
		}
		if(sheet.getRangeIndex() == 0)
		{
			System.out.println("# # 没有找到Range列,正确的列标头为："+Config.variableRange);
		}
		if(sheet.getVersionIndex() == 0)
		{
			System.out.println("# # 没有找到Version列,正确的列标头为："+Config.variableVersion);
		}
		if(sheet.getRemarkIndex() == 0)
		{
			System.out.println("# # 没有找到Version列："+Config.variableRemark);
		}
		System.out.println();
	}

	/**
	 * Overvie中服务信息是否缺少检查
	 */
	public static void overviewColIndex()
	{
		if(SheetOverview.serviceCodeColIndex == 0)
		{
			System.out.println("# # Overview中找不到服务号对应列,正确的列标头为："+Config.ServiceCode);
		}
		if(SheetOverview.serviceDescColIndex == 0)
		{
			System.out.println("# # Overview中找不到服务描述对应列,正确的列标头为："+Config.ServiceDescription);
		}
		if(SheetOverview.serviceNameColIndex == 0)
		{
			System.out.println("# # Overview中找不到服务名称对应列,正确的列标头为："+Config.ServiceName);
		}
		if(SheetOverview.serviceVersionIndex == 0)
		{
			System.out.println("# # Overview中找不到服务名称对应列,正确的列标头为："+Config.version);
		}
		if(SheetOverview.serviceOperColIndex == 0)
		{
			System.out.println("# # Overview中找不到服务名称对应列,正确的列标头为："+Config.operation);
		}
	}
	/**
	 * 服务信息是否全面检查
	 * @param serviceModel
	 */
	public static void serviceInfoCheck(ServiceModel serviceModel)
	{
		if("".equals(serviceModel.getServiceCode()))
		{
			System.out.println("# # Overview中没有找到服务对应服务号");
		}
		if("".equals(serviceModel.getClassName()))
		{
			System.out.println("# # Overview中没有找到服务对应名称");
		}
		if("".equals(serviceModel.getDescription()))
		{
			System.out.println("# # Overview中没有找到服务对应中文名称");
		}
		System.out.println();
	}

	/**
	 * Request/Response正确性检查
	 * @param classModel
	 */
	public static void classNameCheck(ClassModel classModel)
	{
		if("Request".equals(classModel.getType()) && !classModel.getName().endsWith("Request"))
		{
			System.out.println("# # 服务Request名称错误，没有以Request结尾");
		}
		if("Response".equals(classModel.getType()) && !classModel.getName().endsWith("Response"))
		{
			System.out.println("# # 服务Response名称错误，没有以Response结尾");
		}
		if(classModel.getItemList().size() == 0)
		{
			System.out.println("# # 服务Request或Response里面没有字段");
		}
	}

	public static void serviceCheck(ServiceModel serviceModel)
	{
		if(serviceModel.getReqModel() == null)
			System.out.println("# # 请检查Excel格式，生成的XML没有Request报文体");
		else classNameCheck(serviceModel.getReqModel());
		if(serviceModel.getResModel() == null)
			System.out.println("# # 请检查Excel格式，生成的XML没有Response报文体");
		else classNameCheck(serviceModel.getResModel());
		if(serviceModel.getReqModel() != null && serviceModel.getResModel() != null)
		{
			String requestName = serviceModel.getReqModel().getName();
			String reqServiceName = requestName.substring(0, requestName.length()-7);
			String responseName = serviceModel.getResModel().getName();
			String resServiceName = responseName.substring(0, responseName.length() - 8);
			if(!reqServiceName.equals(resServiceName))
			{
				System.out.println("# # 服务Request名称："+requestName);
				System.out.println("# # 服务Response名称："+responseName+"服务名称不一致");
			}
		}
	}
	/**
	 * 检查服务Request和Response名称列Excel格式是否正确，并检查Request和Response类名称是否正确的结尾了
	 * @param className
	 */
	public static void isRequestOrResponse(String className)
	{
		if(!(className.endsWith("Request") || className.endsWith("Response")))
		{
			System.out.println("# # 服务Request或Response的名称没有正确的结尾");
		}
		if("Request".equals(className) || "Response".equals(className))
		{
			System.out.println("# # 服务Request或Response的名称不是全名称");
		}
	}
	/**
	 * 如果请求名称没有填写，此处将会报错
	 * @param classModel
	 * @return
	 */
	public static boolean isClassNameExist(ClassModel classModel)
	{
		if(classModel == null)
		{
			System.out.println("# # 服务Request名称没有填写");
			return false;
		}
		return true;
	}

	/**
	 * 子Model中，没有字段的情况检查
	 * @param itemModel
	 */
	public static void itemModelFieldCheck(ItemModel itemModel)
	{
		if(itemModel.getItemList().size() == 0)
		{
			System.out.println("# "+itemModel.getName()+"是一个Model类型，里面没有字段");
		}
	}
	/**
	 * 
	 * @param itemModel
	 */
	public static String itemModelCheck(ItemModel itemModel)
	{
		boolean isEnumHasValue = ("Enum".equals(itemModel.getMetadata()) || "EnumB".equals(itemModel.getMetadata()));
		if(isEnumHasValue)
		{
			if("".equals(itemModel.getFormat()))
				return log("# "+itemModel.getName()+"是"+itemModel.getType()+"类型的枚举(Enum Or EnumB)，format没有填写");
			
//			else
//				System.out.println("# "+itemModel.getName()+"是"+itemModel.getType()+"类型的枚举(Enum Or EnumB)，请再确认Format格式是否正确->"+itemModel.getFormat());
		}
		if("".equals(itemModel.getDescription()))
		{
			return log("# "+itemModel.getName()+"没有字段说明文字");
		}
		if(("Default".equals(itemModel.getMetadata()) || "Decimal".equals(itemModel.getMetadata()))&& "".equals(itemModel.getLength()))
		{
			return log("# "+itemModel.getName()+":Metadata类型为"+itemModel.getMetadata()+"长度不可以为空");
		}
		if("Default".equals(itemModel.getMetadata()) && "".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":Metadata类型为"+itemModel.getMetadata()+"Type不能为空");
		}
		if("string".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":type错误，正确应该是String");
		}
		if("int".equals(itemModel.getType())||"Int".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":type错误，正确应该是Int32");
		}
		if("bool".equals(itemModel.getType())||"boolean".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":type错误，正确应该是Boolean");
		}
		if("datetime".equals(itemModel.getType())||"Datetime".equals(itemModel.getType())||"dateTime".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":type错误，正确应该是DateTime");
		}
		if("Class".equals(itemModel.getMetadata()) || "NullableClass".equals(itemModel.getMetadata())||"List".equals(itemModel.getMetadata()) || "Enum".equals(itemModel.getMetadata()) || "EnumB".equals(itemModel.getMetadata()) )
		{
			if("".equals(itemModel.getType()))
				return log("# "+itemModel.getName()+":Metadata类型为"+itemModel.getMetadata()+"Type不可以为空");
		}
		return null;
	}
	
	private static String log(String value)
	{
		System.out.println(value);
		return value;
	}
}
