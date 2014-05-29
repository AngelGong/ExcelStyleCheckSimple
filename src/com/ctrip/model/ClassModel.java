package com.ctrip.model;

import java.util.ArrayList;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class ClassModel {

	private String version = "";
	private String type = "";
	private String name = "";

	private ArrayList<ItemModel> itemList = new ArrayList<ItemModel>();
	
	public ClassModel() {

	}

	public ArrayList<ItemModel> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<ItemModel> itemList) {
		this.itemList = itemList;
	}

	public String getVersion() {
		return version;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setVersion(String version) {
		this.version = version.trim();
	}

	public void setType(String type) {
		this.type = type.trim();
	}

	public void setName(String name) {
		this.name = name.trim();
	}
	
	public Element getDocElement()
	{
		DOMElement element = new DOMElement(this.getType());
		element.addAttribute("name", this.getName());
		element.addAttribute("type", this.getType());
		element.addAttribute("version", this.getVersion());
		if(this.getItemList().size() != 0)
		{
			for(ItemModel item:this.getItemList())
			{
				element.add(item.getDocElement());
			}
		}
		return element;
	}

}
