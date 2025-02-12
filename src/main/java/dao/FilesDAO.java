package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.BoardDTO;
import dto.FilesDTO;

public class FilesDAO {

	public static FilesDAO instance; // 1. 싱글톤 패턴

	public synchronized static FilesDAO getInstance() {// 2. 싱글톤 패턴
		if (instance == null) {
			instance = new FilesDAO();
		}
		return instance;
	}

	private Connection getConnection() throws Exception {
		// Tomcat 서버의 DBCP를 사용하여 커넥션을 가져오는 코드(DB 연결 생성)
		Context ctx = new InitialContext(); // [1] ctx -> tomcat 전체
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/orcl"); // [2] java:comp/env -> Resource 태그로 만들어진
																			// 인스턴스 영역
		// /jdbc/orcl -> 우리가 정한 이름을 가진 인스턴스
		return ds.getConnection();
	}

	public int fileInsert(FilesDTO fdto) throws Exception {
		String sql = "insert into files (id ,oriname, sysname, parent_seq) values (files_seq.nextval, ?, ?, ?)";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
			pstat.setString(1, fdto.getOriName());
			pstat.setString(2, fdto.getSysName());
			pstat.setInt(3, fdto.getParent_seq());

			int result = pstat.executeUpdate();
			return result;
		}
	}

	public List<FilesDTO> selectByFile(int seq) throws Exception {
		String sql = "select * from files where parent_seq = ?";
		FilesDTO fdto = null;
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {

			pstat.setInt(1, seq);
			List<FilesDTO> list = new ArrayList<>();
			try (ResultSet rs = pstat.executeQuery();) {
				if (rs.next()) {
					int id = rs.getInt("id");
					String oriName = rs.getString("oriname");
					String sysName = rs.getString("sysname");
					int parent_seq = seq;

					fdto = new FilesDTO(id, oriName, sysName, parent_seq);
					list.add(fdto);
					
				}
				return list;
			}
			
		}
		
	}

	
	
	
	
}
