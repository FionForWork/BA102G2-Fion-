package com.content.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.content.model.ContentService;

public class ContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		if("delete_Content".equals(action)){
			
			
			return;
		}
		if("insert_Content".equals(action)){
			String alb_no = request.getParameter("alb_no");
			ContentService contSvc = new ContentService();
			ServletContext context = request.getServletContext();
			Collection<Part> parts = request.getParts();
			System.out.println("partsize::::"+parts.size());
			for (Part part : parts) {
				if (getFileNameFromPart(part) != null && part.getContentType() != null) {
					String filename = getFileNameFromPart(part);
					byte[] file = toByteArray(part.getInputStream());
					if (isImgFile(context.getMimeType(filename))) {
						contSvc.addContent(alb_no, new Timestamp(System.currentTimeMillis()), file, null);
					} else {
						contSvc.addContent(alb_no, new Timestamp(System.currentTimeMillis()), null, file);
					}
				}
			}
			request.setAttribute("alb_no", alb_no);
			String url = "/Front_end/Album/ListAllContents.jsp";
			request.getRequestDispatcher(url).forward(request, response);
			return;
			
		}
		
	}
	private String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header: " + header);
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
	private byte[] toByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int length = 0;
		byte[] bytes = new byte[1024];
		while ((length = is.read(bytes)) != -1) {
			baos.write(bytes, 0, length);
		}
		baos.close();
		return baos.toByteArray();
	}

	private boolean isImgFile(String mimetype) {
		return mimetype != null && mimetype.startsWith("image");
	}


}
