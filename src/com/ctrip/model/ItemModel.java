package com.ctrip.model;

import java.util.ArrayList;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class ItemModel {
	/*
	 * description="注释" type="类型" name="字段名" metadata="元数据名" require="是否为必传" length="长度" index="索引"
	 */
	private String description = "";
	private String type = "";
	private String name = "";
	private String metadata = "";
	private String require = "";
	private String index = "";
	private String length = "";
	private String format = "";
	private String valueIndex = "";
	private boolean isBasicModel = false;
	private String version = "";
	
	private ArrayList<ItemModel> itemList = new ArrayList<ItemModel>();

	public ItemModel() {
	
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getMetadata() {
		return metadata;
	}

	public String getRequire() {
		return require;
	}

	public String getIndex() {
		return index;
	}

	public void setDescription(String description) {
		this.description = description.trim();
	}

	public void setType(String type) {
		this.type = type.trim();
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata.trim();
	}

	public void setRequire(String require) {
		this.require = require.trim();
	}

	public void setIndex(String index) {
		this.index = index.trim();
	}

	public String getLength() {
		return length.trim();
	}

	public void setLength(String length) {
		this.length = length.trim();
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	

	public String getValueIndex() {
		return valueIndex;
	}

	public void setValueIndex(String valueIndex) {
		this.valueIndex = valueIndex;
	}

	public ArrayList<ItemModel> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<ItemModel> itemList) {
		this.itemList = itemList;
	}

	public boolean isBasicModel() {
		return isBasicModel;
	}

	public void setBasicModel(boolean isBasicModel) {
		this.isBasicModel = isBasicModel;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isModel()
	{
		return "List".equals(this.getMetadata()) || "Class".equals(this.getMetadata())|| "NullableClass".equals(this.getMetadata());
	}
	
	public String getFieldName()
	{
		if("List".equals(metadata)){
			if(name.endsWith("List")){
				return name;
			}
			else{
				return name+"List";
			}
		}
		else if("NullableClass".equals(metadata)||"Class".equals(metadata)){
			return name+"Model";
		}
		else{
			return name;
		}
	}
	
	public String getModelName()
	{
		if(this.isModel())
			return this.getType()+"Model";
		else return this.getName();
	}
	
	public Element getDocElement()
	{
		DOMElement element = new DOMElement("item");
		element.addAttribute("index", this.getIndex());
		element.addAttribute("name", this.getName());
		element.addAttribute("length", this.getLength());
		element.addAttribute("require", this.getRequire());
		element.addAttribute("type", this.getType());
		element.addAttribute("metadata", this.getMetadata());
		element.addAttribute("description", this.getDescription());
		element.addAttribute("format", this.getFormat());
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
