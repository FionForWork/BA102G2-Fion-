package com.tempcont.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TempContDAO implements TempContDAO_Interface {

	private static final String UPLOAD_IMG_SQL = "insert into tempcont(tcont_no,temp_no,upload_date,img,tcont_desc) "
			+ "values(ltrim(To_char(tempcont_sq.nextval,'0009')),?,CURRENT_TIMESTAMP,?,?)";
	private static final String UPLOAD_VDO_SQL = "insert into tempcont(tcont_no,temp_no,upload_date,vdo,tcont_desc) "
			+ "values(ltrim(To_char(tempcont_sq.nextval,'0009')),?,CURRENT_TIMESTAMP,?,?)";
	private static final String DELETE_SQL = "delete from tempcont where tcont_no = ?";
	private static final String UPDATE_SQL = "update tempcont set temp_no=?,img=?,vdo=?,upload_date=?,tcont_desc=? where tcont_no = ?";
	private static final String FIND_BY_PK = "select * from tempcont where tcont_no = ?";
	private static final String FIND_ALL_BY_TEMP_NO = "select * from tempcont where temp_no = ?";
	private static final String FIND_ALL = "select * from tempcont";

	private static DataSource ds = null;

	static {
		Context ctx;
		try {
			ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ModelDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String uploadImg(TempContVO tempcont) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] cols = { "tcont_no" };
		String tcont_no = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(UPLOAD_IMG_SQL, cols);
			pstmt.setString(1, tempcont.getTemp_no());
			pstmt.setBytes(2, tempcont.getImg());
			pstmt.setString(3, tempcont.getTcont_desc());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			while (rs.next()) {
				tcont_no = rs.getString(1);
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tcont_no;
	}

	@Override
	public String uploadVdo(TempContVO tempcont) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] cols = { "tcont_no" };
		String tcont_no = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(UPLOAD_VDO_SQL, cols);
			pstmt.setString(1, tempcont.getTemp_no());
			pstmt.setBytes(2, tempcont.getVdo());
			pstmt.setString(3, tempcont.getTcont_desc());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			while (rs.next()) {
				tcont_no = rs.getString(1);
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tcont_no;
	}

	@Override
	public void deleteTempCont(String tcont_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(DELETE_SQL);
			pstmt.setString(1, tcont_no);
			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void updateTempCont(TempContVO tempcont) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(UPDATE_SQL);
			pstmt.setString(1, tempcont.getTemp_no());
			pstmt.setBytes(2, tempcont.getImg());
			pstmt.setBytes(3, tempcont.getVdo());
			pstmt.setTimestamp(4, tempcont.getUpload_date());
			pstmt.setString(5, tempcont.getTcont_desc());
			pstmt.setString(6, tempcont.getTcont_no());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public TempContVO findTempContByPK(String tcont_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TempContVO tempcont = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, tcont_no);
			rs = pstmt.executeQuery();
			rs.next();
			tempcont = new TempContVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3), rs.getBytes(4),
					rs.getBytes(5), rs.getString(6));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return tempcont;
	}

	@Override
	public List<TempContVO> findTempContsByTempNo(String temp_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TempContVO tempcont = null;
		List<TempContVO> tempcontList = new ArrayList<>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(FIND_ALL_BY_TEMP_NO);
			pstmt.setString(1, temp_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tempcont = new TempContVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3), rs.getBytes(4),
						rs.getBytes(5), rs.getString(6));
				tempcontList.add(tempcont);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return tempcontList;
	}

	@Override
	public List<TempContVO> findAll() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		TempContVO tempcont = null;
		List<TempContVO> tempcontList = new ArrayList<>();
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(FIND_ALL);
			while (rs.next()) {
				tempcont = new TempContVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3), rs.getBytes(4),
						rs.getBytes(3), rs.getString(6));
				tempcontList.add(tempcont);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return tempcontList;
	}
}
