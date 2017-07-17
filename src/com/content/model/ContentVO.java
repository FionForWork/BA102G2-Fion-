package com.content.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContentVO implements Serializable{

	private String cont_no;
	private String alb_no;
	private Timestamp upload_date;
	private byte[] img;
	private byte[] vdo;
	private String cont_desc;
	
	public ContentVO(){}
	
	
	public ContentVO(String cont_no, String alb_no, Timestamp upload_date, byte[] img, byte[] vdo,String cont_desc) {
		super();
		this.cont_no = cont_no;
		this.alb_no = alb_no;
		this.upload_date = upload_date;
		this.img = img;
		this.vdo = vdo;
		this.cont_desc = cont_desc;
	}


	public String getCont_no() {
		return cont_no;
	}

	public void setCont_no(String cont_no) {
		this.cont_no = cont_no;
	}

	public String getAlb_no() {
		return alb_no;
	}

	public void setAlb_no(String alb_no) {
		this.alb_no = alb_no;
	}

	public Timestamp getUpload_date() {
		return upload_date;
	}

	public void setUpload_date(Timestamp upload_date) {
		this.upload_date = upload_date;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public byte[] getVdo() {
		return vdo;
	}

	public void setVdo(byte[] vdo) {
		this.vdo = vdo;
	}

	public String getCont_desc() {
		return cont_desc;
	}

	public void setCont_desc(String cont_desc) {
		this.cont_desc = cont_desc;
	}
}
