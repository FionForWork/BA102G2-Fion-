package test;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.album.model.AlbumService;
import com.album.model.AlbumVO;
import com.content.model.ContentService;
import com.content.model.ContentVO;
import com.tempcont.model.TempContService;
import com.tempcont.model.TempContVO;

public class ShowPictureServletDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		String file = request.getQueryString();
		String pk = null;
		if(file.startsWith("alb_no")){
			pk = request.getParameter("alb_no");
			System.out.println(pk);
			AlbumService albSvc = new AlbumService();
			AlbumVO alb = albSvc.getOneAlbum(pk);
			byte[] cover = alb.getCover();
			out.write(cover);
			out.close(); 
			return;
		}
		if(file.startsWith("cont_no")){
			pk = request.getParameter("cont_no");
			byte[] cont =null;
			System.out.println(pk);
			ContentService contSvc = new ContentService();
			ContentVO contVO = contSvc.getOneContent(pk);
			if(contVO.getImg() == null){
				cont = contVO.getVdo();
			}else{
				cont = contVO.getImg();
			}
			out.write(cont);
			out.close(); 
			return;
		}
		if(file.startsWith("tcont_no")){
			pk = request.getParameter("tcont_no");
			byte[] tempcont =null;
			System.out.println(pk);
			TempContService contSvc = new TempContService();
			TempContVO tempcontVO = contSvc.getOneTempCont(pk);
			if(tempcontVO.getImg() == null){
				tempcont = tempcontVO.getVdo();
			}else{
				tempcont = tempcontVO.getImg();
			}
			out.write(tempcont);
			out.close(); 
			return;
		}
		
	}
}
