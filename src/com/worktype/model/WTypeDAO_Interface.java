package com.worktype.model;

import java.util.List;

public interface WTypeDAO_Interface {

	
	public String insertWType(WTypeVO wtype);
	public void deleteWType(String wtype_no);
	public void updateWType(WTypeVO wotype);
	public WTypeVO findWTypeByPK(String wtype_no);
	public List<WTypeVO> findAll();
}
