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
	 * ��鱨��Sheet���Ƿ�ȱ���ֶ�������Ϣ
	 * @param sheet
	 */
	public static void messageColIndex(SheetMessage sheet)
	{
		if(sheet.getLengthIndex() == 0)
		{
			System.out.println("# # û���ҵ�������,��ȷ���б�ͷΪ��"+Config.variableLength);
		}
		if(sheet.getNameIndex() == 0)
		{
			System.out.println("# # û���ҵ�������,��ȷ���б�ͷΪ��"+Config.variableName);
		}
		if(sheet.getRequireIndex() == 0)
		{
			System.out.println("# # û���ҵ�Require��,��ȷ���б�ͷΪ��"+Config.variableRequire);
		}
		if(sheet.getTypeIndex() == 0)
		{
			System.out.println("# # û���ҵ�������,��ȷ���б�ͷΪ��"+Config.variableType);
		}
		if(sheet.getMetadataIndex() == 0)
		{
			System.out.println("# # û���ҵ�Medadata��,��ȷ���б�ͷΪ��"+Config.variableMetadata);
		}
		if(sheet.getRangeIndex() == 0)
		{
			System.out.println("# # û���ҵ�Range��,��ȷ���б�ͷΪ��"+Config.variableRange);
		}
		if(sheet.getVersionIndex() == 0)
		{
			System.out.println("# # û���ҵ�Version��,��ȷ���б�ͷΪ��"+Config.variableVersion);
		}
		if(sheet.getRemarkIndex() == 0)
		{
			System.out.println("# # û���ҵ�Version�У�"+Config.variableRemark);
		}
		System.out.println();
	}

	/**
	 * Overvie�з�����Ϣ�Ƿ�ȱ�ټ��
	 */
	public static void overviewColIndex()
	{
		if(SheetOverview.serviceCodeColIndex == 0)
		{
			System.out.println("# # Overview���Ҳ�������Ŷ�Ӧ��,��ȷ���б�ͷΪ��"+Config.ServiceCode);
		}
		if(SheetOverview.serviceDescColIndex == 0)
		{
			System.out.println("# # Overview���Ҳ�������������Ӧ��,��ȷ���б�ͷΪ��"+Config.ServiceDescription);
		}
		if(SheetOverview.serviceNameColIndex == 0)
		{
			System.out.println("# # Overview���Ҳ����������ƶ�Ӧ��,��ȷ���б�ͷΪ��"+Config.ServiceName);
		}
		if(SheetOverview.serviceVersionIndex == 0)
		{
			System.out.println("# # Overview���Ҳ����������ƶ�Ӧ��,��ȷ���б�ͷΪ��"+Config.version);
		}
		if(SheetOverview.serviceOperColIndex == 0)
		{
			System.out.println("# # Overview���Ҳ����������ƶ�Ӧ��,��ȷ���б�ͷΪ��"+Config.operation);
		}
	}
	/**
	 * ������Ϣ�Ƿ�ȫ����
	 * @param serviceModel
	 */
	public static void serviceInfoCheck(ServiceModel serviceModel)
	{
		if("".equals(serviceModel.getServiceCode()))
		{
			System.out.println("# # Overview��û���ҵ������Ӧ�����");
		}
		if("".equals(serviceModel.getClassName()))
		{
			System.out.println("# # Overview��û���ҵ������Ӧ����");
		}
		if("".equals(serviceModel.getDescription()))
		{
			System.out.println("# # Overview��û���ҵ������Ӧ��������");
		}
		System.out.println();
	}

	/**
	 * Request/Response��ȷ�Լ��
	 * @param classModel
	 */
	public static void classNameCheck(ClassModel classModel)
	{
		if("Request".equals(classModel.getType()) && !classModel.getName().endsWith("Request"))
		{
			System.out.println("# # ����Request���ƴ���û����Request��β");
		}
		if("Response".equals(classModel.getType()) && !classModel.getName().endsWith("Response"))
		{
			System.out.println("# # ����Response���ƴ���û����Response��β");
		}
		if(classModel.getItemList().size() == 0)
		{
			System.out.println("# # ����Request��Response����û���ֶ�");
		}
	}

	public static void serviceCheck(ServiceModel serviceModel)
	{
		if(serviceModel.getReqModel() == null)
			System.out.println("# # ����Excel��ʽ�����ɵ�XMLû��Request������");
		else classNameCheck(serviceModel.getReqModel());
		if(serviceModel.getResModel() == null)
			System.out.println("# # ����Excel��ʽ�����ɵ�XMLû��Response������");
		else classNameCheck(serviceModel.getResModel());
		if(serviceModel.getReqModel() != null && serviceModel.getResModel() != null)
		{
			String requestName = serviceModel.getReqModel().getName();
			String reqServiceName = requestName.substring(0, requestName.length()-7);
			String responseName = serviceModel.getResModel().getName();
			String resServiceName = responseName.substring(0, responseName.length() - 8);
			if(!reqServiceName.equals(resServiceName))
			{
				System.out.println("# # ����Request���ƣ�"+requestName);
				System.out.println("# # ����Response���ƣ�"+responseName+"�������Ʋ�һ��");
			}
		}
	}
	/**
	 * ������Request��Response������Excel��ʽ�Ƿ���ȷ�������Request��Response�������Ƿ���ȷ�Ľ�β��
	 * @param className
	 */
	public static void isRequestOrResponse(String className)
	{
		if(!(className.endsWith("Request") || className.endsWith("Response")))
		{
			System.out.println("# # ����Request��Response������û����ȷ�Ľ�β");
		}
		if("Request".equals(className) || "Response".equals(className))
		{
			System.out.println("# # ����Request��Response�����Ʋ���ȫ����");
		}
	}
	/**
	 * �����������û����д���˴����ᱨ��
	 * @param classModel
	 * @return
	 */
	public static boolean isClassNameExist(ClassModel classModel)
	{
		if(classModel == null)
		{
			System.out.println("# # ����Request����û����д");
			return false;
		}
		return true;
	}

	/**
	 * ��Model�У�û���ֶε�������
	 * @param itemModel
	 */
	public static void itemModelFieldCheck(ItemModel itemModel)
	{
		if(itemModel.getItemList().size() == 0)
		{
			System.out.println("# "+itemModel.getName()+"��һ��Model���ͣ�����û���ֶ�");
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
				return log("# "+itemModel.getName()+"��"+itemModel.getType()+"���͵�ö��(Enum Or EnumB)��formatû����д");
			
//			else
//				System.out.println("# "+itemModel.getName()+"��"+itemModel.getType()+"���͵�ö��(Enum Or EnumB)������ȷ��Format��ʽ�Ƿ���ȷ->"+itemModel.getFormat());
		}
		if("".equals(itemModel.getDescription()))
		{
			return log("# "+itemModel.getName()+"û���ֶ�˵������");
		}
		if(("Default".equals(itemModel.getMetadata()) || "Decimal".equals(itemModel.getMetadata()))&& "".equals(itemModel.getLength()))
		{
			return log("# "+itemModel.getName()+":Metadata����Ϊ"+itemModel.getMetadata()+"���Ȳ�����Ϊ��");
		}
		if("Default".equals(itemModel.getMetadata()) && "".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":Metadata����Ϊ"+itemModel.getMetadata()+"Type����Ϊ��");
		}
		if("string".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":type������ȷӦ����String");
		}
		if("int".equals(itemModel.getType())||"Int".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":type������ȷӦ����Int32");
		}
		if("bool".equals(itemModel.getType())||"boolean".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":type������ȷӦ����Boolean");
		}
		if("datetime".equals(itemModel.getType())||"Datetime".equals(itemModel.getType())||"dateTime".equals(itemModel.getType()))
		{
			return log("# "+itemModel.getName()+":type������ȷӦ����DateTime");
		}
		if("Class".equals(itemModel.getMetadata()) || "NullableClass".equals(itemModel.getMetadata())||"List".equals(itemModel.getMetadata()) || "Enum".equals(itemModel.getMetadata()) || "EnumB".equals(itemModel.getMetadata()) )
		{
			if("".equals(itemModel.getType()))
				return log("# "+itemModel.getName()+":Metadata����Ϊ"+itemModel.getMetadata()+"Type������Ϊ��");
		}
		return null;
	}
	
	private static String log(String value)
	{
		System.out.println(value);
		return value;
	}
}
