package com.comtra.model;

import java.util.List;

public interface ComTraDAO_Interface {

	public String insertComTra(ComTraVO comTra);
	public void deleteComTra(String comTra_no);
	public void updateComTra(ComTraVO comTra);
	public ComTraVO findComTraByPK(String comTra_no);
	public ComTraVO findComTraByMemNo(String mem_no);
	public List<ComTraVO> findAll();
}
