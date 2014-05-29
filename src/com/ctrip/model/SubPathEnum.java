package com.ctrip.model;

public enum SubPathEnum{
	InlandFlight("10","flight","104"),
	InternationalFlight("11","intFlight","110"),
	InlandHotel("15","hotel","150"),
	Customer("90","system","950"),
	Other("95","other","950"),
	Train("25","train","251"),
	Basic("30","basic","301"),
	Vacation("20","travel","201"),
	Group("16","hotelGroup","161"),
	Overseas("17","overseas","170"),
	SelfTravel("24","selfTravel","240"),
	AirportInfo("12","airportInfo","120"),
	Payment("31","payment","310"),
	CarProduct("23","carProduct","230");
	private SubPathEnum(String id,String name,String moduleId)
	{
		this.id = id;
		this.name = name;
		this.moduleId = moduleId;
	}
	private String id;
	private String name;
	private String moduleId;
	
	public static String getSubPath(String serviceCode)
	{
		String str = serviceCode.substring(0, 2);
		SubPathEnum[] subPaths = SubPathEnum.values();
		for(SubPathEnum item:subPaths)
		{
			if(str.equals(item.id))
				return item.name;
		}
		return "";
	}
	
	public static String getModule(String serviceCode)
	{
		String str = serviceCode.substring(0, 2);
		SubPathEnum[] subPaths = SubPathEnum.values();
		for(SubPathEnum item:subPaths)
		{
			if(str.equals(item.id))
				return item.moduleId;
		}
		return "";
	}
}