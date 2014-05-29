package com.ctrip.model;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.ctrip.excel.ExcelCheck;

public class ServiceModel {
	/*
	 * sender类中方法的服务信息
	 * 用于模板生成
	 */
	private String serviceCode = "";
	private String className = "";
	private String description = "";
	private String type = "";
	private String InternalVersion = "";

	private ClassModel reqModel = null;
	private ClassModel resModel = null;

	public ServiceModel() {

	}

	public String getServiceCode() {
		return serviceCode;
	}

	public String getClassName() {
		return className;
	}

	public String getDescription() {
		return description;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode.trim();
	}

	public void setClassName(String className) {
		this.className = className.trim();
	}

	public void setDescription(String description) {
		this.description = description.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ClassModel getReqModel() {
		return reqModel;
	}

	public ClassModel getResModel() {
		return resModel;
	}

	public void setReqModel(ClassModel reqModel) {
		this.reqModel = reqModel;
	}

	public void setResModel(ClassModel resModel) {
		this.resModel = resModel;
	}

	public String getInternalVersion() {
		return InternalVersion;
	}

	public void setInternalVersion(String internalVersion) {
		InternalVersion = internalVersion.trim();
	}	
	
	public Element getDocElement()
	{
		DOMElement element = new DOMElement("Service");
		element.addAttribute("name", this.className);
		element.addAttribute("code", this.serviceCode);
		element.addAttribute("type", "Service");
		element.addAttribute("description", this.getDescription());
		if(this.reqModel != null)
		{
			element.add(reqModel.getDocElement());
		}
		if(this.resModel != null)
		{
			element.add(resModel.getDocElement());
		}
		return element;
	}
}
