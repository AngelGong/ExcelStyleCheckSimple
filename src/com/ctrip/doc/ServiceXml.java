package com.ctrip.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ctrip.main.Config;
import com.ctrip.model.ServiceModel;
import com.ctrip.model.SubPathEnum;

public class ServiceXml {
	private ServiceModel serviceModel = null;
	public ServiceXml()	{	}
	public ServiceXml(ServiceModel serviceModel)
	{
		this.serviceModel = serviceModel;
	}
	
	public void getDoc()
	{
		Document document = DocumentHelper.createDocument();
		document.add(this.serviceModel.getDocElement());
		String subPath = SubPathEnum.getSubPath(this.serviceModel.getServiceCode());
		File file = new File(Config.xmlPath);
		if(!file.exists()) file.mkdir();
		subPath = Config.xmlPath+subPath;
		file = new File(subPath);
		if(!file.exists()) file.mkdir();
		OutputStreamWriter outputStream = null;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			outputStream = new OutputStreamWriter(new FileOutputStream(new File(subPath+"/"+this.serviceModel.getServiceCode()+".xml")));
			XMLWriter writer = new XMLWriter(outputStream,format);
			writer.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
