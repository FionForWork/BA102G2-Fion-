package com.worktype.model;

import java.io.Serializable;

public class WTypeVO implements Serializable {

	private String wtype_no;
	private String name;
	
	public WTypeVO(){}
	
	public WTypeVO(String wtype_no, String name) {
		super();
		this.wtype_no = wtype_no;
		this.name = name;
	}

	public String getWtype_no() {
		return wtype_no;
	}

	public void setWtype_no(String wtype_no) {
		this.wtype_no = wtype_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
