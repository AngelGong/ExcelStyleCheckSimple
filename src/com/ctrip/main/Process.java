package com.ctrip.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ctrip.excel.ExcelReader;
import com.ctrip.excel.ExcelRemark;
import com.ctrip.excel.ExcelWriter;
import com.ctrip.excel.SheetMessage;
import com.ctrip.excel.SheetOverview;

public class Process {

	public static void main(String[] args) {
//		customMain();
		//打jar包的时候走此交互流程
		jarMain();
	}

	private static void customMain()
	{
		Config.xmlPath = "xml/";
		File file = new File(Config.fileName);
		if(!file.exists())
		{
			System.out.println("找不到文件，请确认输入的文件路径是否正确");
			return;
		}
		try {
			XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
			ExcelReader excelReader = new ExcelReader(xwb);
			excelReader.getNewestXml();
//			if(ExcelRemark.remarkIndex >0)
//			{
//				ExcelWriter witer = new ExcelWriter();
//				witer.writerExcel(xwb);
//			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void jarMain()
	{
		Config.xmlPath = "D:/temp/";
		File file = null;
		Scanner in = new Scanner(System.in);    //Scanner类
		System.out.println("请输入要生成服务的版本号：");
		Config.currentVersion = in.nextLine();
		while(true)
		{
			System.out.println("请输入Excel文件所在路径全名称：");
			String str = in.nextLine();
			str.replaceAll("\\\\", "/");
			if(!str.endsWith(".xlsx"))
			{
				System.out.println("请输入后缀为.xlsx的Excel文件");
				continue;
			}
			Config.fileName = str;
			file = new File(Config.fileName);
			if(!file.exists())
			{
				System.out.println("找不到文件，请确认输入的文件路径是否正确");
				continue;
			}
			try {
				XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
				ExcelReader excelReader = new ExcelReader(xwb);
				excelReader.getNewestXml();
//				if(ExcelRemark.remarkIndex >0)
//				{
//					ExcelWriter witer = new ExcelWriter();
//					witer.writerExcel(xwb);
//					ExcelRemark.remarkIndex = 0;
//				}
//				System.out.println("产出的XML在"+Config.xmlPath+"文件夹中，请查看！");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
