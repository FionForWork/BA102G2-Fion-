package com.content.model;

import java.util.List;

public interface ContentDAO_Interface {

	public String uploadImg(ContentVO content);
	public String uploadVdo(ContentVO content);
	public void deleteContent(String cont_no);
	public void updateContent(ContentVO content);
	public ContentVO findContentByPK(String cont_no);
	public List<ContentVO> findContentsByAlbNo(String alb_no);
	public List<ContentVO> findAll();
}
