package test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.album.model.AlbumJDBCDAO;
import com.album.model.AlbumService;
import com.album.model.AlbumVO;
import com.content.model.ContentJDBCDAO;
import com.content.model.ContentService;
import com.content.model.ContentVO;

public class InsertAlbumAndPhotoToDB {

	public static void main(String[] args) {

		int numberOfPic = 33;
		List<String> names = new ArrayList<String>();
		names.add("愛在夕陽裡");
		names.add("Follow Me!");
		names.add("My Wedding Day~");
		String[] mems = { "1001", "1002", "1003" };
		AlbumJDBCDAO albSvc;
		AlbumVO alb = null;
		ContentVO cont = null;
		ContentJDBCDAO contSvc;
		
		
		for (int i = 0; i < names.size(); i++) {
			System.out.println("1111111111111");
			albSvc = new AlbumJDBCDAO();
			String alb_no = null;
			for (int j = 1; j <= numberOfPic; j++) {
				System.out.println("222222222222222222");
				String path = "C:\\Users\\mac\\Dropbox\\test\\" + j + ".jpg";
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				ByteArrayOutputStream baos = null;
				
				try {
					fis = new FileInputStream(path);
					bis = new BufferedInputStream(fis);
					baos = new ByteArrayOutputStream();
					byte[] pics = new byte[1024];
					int length = 0;
					while ((length = bis.read(pics)) != -1) {
						baos.write(pics, 0, length);
					}
					if (j == 1) {
						System.out.println("33333333333333333");
						alb = new AlbumVO();
						alb.setMem_no(mems[i]);
						alb.setName(names.get(i));
						alb.setCreate_date(new Timestamp(System.currentTimeMillis()));
						alb.setCover(baos.toByteArray());
						alb_no = albSvc.createAlbum(alb);
						System.out.println("alb_no---------------------"+alb_no);
					}
					
					
					contSvc = new ContentJDBCDAO();
					cont = new ContentVO();
					System.out.println("========"+alb_no);
					cont.setAlb_no(alb_no);
					cont.setUpload_date(new Timestamp(System.currentTimeMillis()));
					cont.setImg(baos.toByteArray());
					contSvc.insertContent(cont);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (baos != null) {
						try {
							baos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
