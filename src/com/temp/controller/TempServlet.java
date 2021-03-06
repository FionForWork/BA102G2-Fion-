package com.temp.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.album.model.AlbumService;
import com.album.model.AlbumVO;
import com.content.model.ContentService;
import com.content.model.ContentVO;
import com.temp.model.TempService;
import com.temp.model.TempVO;
import com.tempcont.model.TempContService;
import com.tempcont.model.TempContVO;

@MultipartConfig(fileSizeThreshold = 10 * 1024 * 1024, maxFileSize = 5 * 10 * 1021 * 1024, maxRequestSize = 5 * 5 * 10
* 1024 * 1024)
public class TempServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		/*********   查詢單本成品內容       *********/
		if ("getOne_For_Display".equals(action)) {
			System.out.println("!!!!!!!!1");
			String temp_no = request.getParameter("temp_no");
			
			System.out.println(temp_no);
			request.setAttribute("temp_no",temp_no);
			String url ="/Front_end/Album/ListAllTempConts.jsp";
			request.getRequestDispatcher(url).forward(request, response);
			return;
		}
		
		/*********   刪除成品與成品內容       *********/
		if("delete_Album".equals(action)){
			TempService tempSvc = new TempService();
			TempContService tcontSvc = new TempContService();
			
			String temp_no = request.getParameter("temp_no");
			System.out.println("temp_no"+temp_no);
			List<TempContVO> tempConts = tcontSvc.getAllByTempNo(temp_no);
			for(TempContVO tcont : tempConts){
				tcontSvc.deleteTempCont(tcont.getTcont_no());
			}
			
			tempSvc.deleteTemp(temp_no);
			
			/*********   回到成品列表       *********/
			String url = "/Front_end/Album/ListAllTemps.jsp";
			request.getRequestDispatcher(url).forward(request, response);
			return;
		}
		
		/*********   建立成品與成品內容       *********/
		if ("create_Temp".equals(action)) {
			Map<String,String> errorMsgs = new Hashtable<String,String>();
			request.setAttribute("errorMsgs", errorMsgs);		
			HttpSession session = request.getSession();
		
			Collection<Part> parts = request.getParts();
			System.out.println("part size"+parts.size());
			forloop:
			for(Part part: parts){		
				System.out.println("!!!!!!");
				errorMsgs.put("file","(請選擇照片或影片)" );
				if(getFileNameFromPart(part) != null && part.getContentType() != null){
					System.out.println("~~~~");
					errorMsgs.remove("file");
					break forloop;
				}
			}
			// ===== 檢查作品名稱 ===== //
			String name = request.getParameter("name").trim();
			if (name.length() == 0) {
				errorMsgs.put("name", "(請輸入作品名稱)");
			}
			
			String mem_no = request.getParameter("mem_no");
			
			// ===== 檢查可挑選張數 ===== //
			Integer available = null;
			try{
				
				available = new Integer(request.getParameter("available").trim());
				if(available > parts.size()){
					errorMsgs.put("available_number", "(照片不夠選阿!!!)");
				}
				
			}catch(NumberFormatException e){
				errorMsgs.put("available_empty", "(請輸入可挑選數量)");
			}catch(NullPointerException e){
				errorMsgs.put("available_empty", "(請輸入可挑選張數)");
			}
			
			
			// ===== 檢查拍攝日期是否為空值 ===== //
			Timestamp create_date = null;
			try{
				create_date = covertToTimestamp(request.getParameter("create_date"));
			}catch(NullPointerException e){
				errorMsgs.put("create_date", "(請輸入拍攝日期)");
			}
			
			String status = "未挑選";
			String com_no = (String) session.getAttribute("com_no");

			ServletContext context = getServletContext();
			TempVO temp = new TempVO();
			temp.setCom_no(com_no);
			temp.setMem_no(mem_no);
			temp.setAvailable(available);
			temp.setCreate_date(create_date);
			temp.setName(name);
			
			if(!errorMsgs.isEmpty()){
				request.setAttribute("temp", temp);
				RequestDispatcher failureView = request
						.getRequestDispatcher("/Front_end/Temp/create_temp.jsp");
				failureView.forward(request, response);
				return; 
			}
			
			/*********  新增成品    *********/
			TempService tempSvc = new TempService();
			temp = tempSvc.addTemp(com_no, mem_no, name, create_date, available, status);
			String temp_no = null;
			
			/*********  新增成品內容     *********/
			
			TempContService tempcontSvc = new TempContService();
			TempContVO tempcont = null;
			System.out.println("partsize::::"+parts.size());
			for (Part part : parts) {
				if (getFileNameFromPart(part) != null && part.getContentType() != null) {
					String filename = getFileNameFromPart(part);
					byte[] file = toByteArray(part.getInputStream());
					temp_no = temp.getTemp_no();
					if (isImgFile(context.getMimeType(filename))) {
						tempcontSvc.addTempCont(temp_no, new Timestamp(System.currentTimeMillis()), file, null);
					} else {
						tempcontSvc.addTempCont(temp_no, new Timestamp(System.currentTimeMillis()), null, file);
					}
				}
			}
			
			/*********   導到成品內容       *********/
			request.setAttribute("temp_no", temp_no);
			String url = "/Front_end/Temp/ListAllTempConts.jsp";
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
	private Timestamp covertToTimestamp(String date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date original_date = null;
		try {
			original_date = formatter.parse(date+" 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(date);
		Timestamp create_date = new Timestamp(original_date.getTime());
		return create_date;
	}
}
