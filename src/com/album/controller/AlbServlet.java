package com.album.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.album.model.AlbumDAO;
import com.album.model.AlbumVO;

@WebServlet("/AlbServlet")
public class AlbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		if("create_Album".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			String name = request.getParameter("name").trim();
			String mem_no = request.getParameter("mem_no");
			if(name == null){
				name = new java.sql.Date(new java.util.Date().getTime()).toString();
			}
			AlbumVO alb = new AlbumVO();
			alb.setMem_no(mem_no);
			alb.setName(name);
			////////////////////////////////////////////////////////////////////
			AlbumDAO albDAO = new AlbumDAO();
			String alb_no = albDAO.createAlbum(alb);
		}
		
		
	}

}
