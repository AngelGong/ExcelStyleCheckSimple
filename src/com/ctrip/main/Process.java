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
		//��jar����ʱ���ߴ˽�������
		jarMain();
	}

	private static void customMain()
	{
		Config.xmlPath = "xml/";
		File file = new File(Config.fileName);
		if(!file.exists())
		{
			System.out.println("�Ҳ����ļ�����ȷ��������ļ�·���Ƿ���ȷ");
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
		Scanner in = new Scanner(System.in);    //Scanner��
		System.out.println("������Ҫ���ɷ���İ汾�ţ�");
		Config.currentVersion = in.nextLine();
		while(true)
		{
			System.out.println("������Excel�ļ�����·��ȫ���ƣ�");
			String str = in.nextLine();
			str.replaceAll("\\\\", "/");
			if(!str.endsWith(".xlsx"))
			{
				System.out.println("�������׺Ϊ.xlsx��Excel�ļ�");
				continue;
			}
			Config.fileName = str;
			file = new File(Config.fileName);
			if(!file.exists())
			{
				System.out.println("�Ҳ����ļ�����ȷ��������ļ�·���Ƿ���ȷ");
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
//				System.out.println("������XML��"+Config.xmlPath+"�ļ����У���鿴��");
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
