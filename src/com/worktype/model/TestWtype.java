package com.worktype.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TestWtype {
	public static void main(String[] args) {
		WTypeVO wtype = null;
		WTypeDAO dao = new WTypeDAO();
		
		// insert
//			wtype = new WTypeVO();
//			wtype.setName("冷豔風格");
//			String wtype_no = dao.insertWorkType(wtype);
//			System.out.println(wtype_no);
		
	
	//update
	
//		wtype = new WTypeVO();
//		wtype.setWtype_no("0002");
//		wtype.setName("異國風");
//		dao.updateWorkType(wtype);
	
	// find by pk
	
//	wtype = dao.findWorkTypeByPK("0002");
//	System.out.println(wtype.getName());
	
	
	// find all
//	List<WTypeVO> wtypeList = dao.findAll();
//	for(int i = 0; i < wtypeList.size(); i++){
//		wtype = wtypeList.get(i);
//		System.out.println(wtype.getName());
//
//	}
//	
//	// delete
//	
	dao.deleteWType("0002");
	}

}
