package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.MembersDTO;

public class MembersDAO {
	public static MembersDAO instance; // 1. 싱글톤 패턴

	public synchronized static MembersDAO getInstance() {// 2. 싱글톤 패턴
		if (instance == null) {
			instance = new MembersDAO();
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
	
	public int insert(MembersDTO dto) throws Exception {
		String sql = "insert into members (id, pw, name, email, phone, postcode, address1, address2) values (?, ?, ?, ?, ?, ?, ?, ?)";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getPw());
			pstat.setString(3, dto.getName());
			pstat.setString(4, dto.getEmail());
			pstat.setString(5, dto.getPhone());
			pstat.setString(6, dto.getPostcode());
			pstat.setString(7, dto.getAddr1());
			pstat.setString(8, dto.getAddr2());
			//pstat.setDate(9, new java.sql.Date(System.currentTimeMillis())); // current date (SYSDATE)
			
			int result = pstat.executeUpdate();
			return result;
		}
	}
	
	public boolean isExistID(String id) throws Exception {//ID 중복체크
		String sql = "SELECT * FROM members where id = ?";
	      try(Connection con = this.getConnection();
	            PreparedStatement pstat = con.prepareStatement(sql);) {
	         
	         pstat.setString(1, id); 
	         try(ResultSet rs = pstat.executeQuery();) {
	            return rs.next();
	         }
	      }
	}
	
	public boolean login(String id, String pw) throws Exception { //로그인
		String sql = "select * from members where id=? and pw=?"; //3. DBMS select

		try(Connection con = this.getConnection(); 
				PreparedStatement pstat= con.prepareStatement(sql);
				){
				pstat.setString(1, id);
				pstat.setString(2, pw);
				try(ResultSet rs = pstat.executeQuery();){
					return rs.next();
				}
		}
	}
	
	public int deleteAccount(String id) throws Exception { //탈퇴
		String sql = "delete from members where id = ?" ;  
		try(Connection con = this.getConnection(); 
			PreparedStatement pstat= con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			int result = pstat.executeUpdate();
			return result;
		}
	}
	
	
	public MembersDTO selectAll(String id) throws Exception {// 내 정보 출력
	      String sql = "select id, name, email, phone, postcode, address1, address2, signup_date from members where id=?";
	      MembersDTO dto = null;
	      try (Connection con = this.getConnection(); 
	    		  PreparedStatement pstat = con.prepareStatement(sql);) {

	         pstat.setString(1, id);

	         try (ResultSet rs = pstat.executeQuery();) {
	            if (rs.next()) {
	               id = rs.getString("id");
	               String name = rs.getString("name");
	               String email = rs.getString("email");
	               String phone = rs.getString("phone");
	               String postcode = rs.getString("postcode");
	               String address1 = rs.getString("address1");
	               String address2 = rs.getString("address2");
	               

	               dto = new MembersDTO(id, name, email, phone, postcode, address1, address2);
	            }
	            return dto;
	         }
	      }
	   }
	
	
	
	public int updateBylist(MembersDTO dto)throws Exception {
		String sql = "update members set email = ?, phone = ?, postcode = ?, address1 = ?, address2 = ? where id = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getEmail());
			pstat.setString(2, dto.getPhone());
			pstat.setString(3, dto.getPostcode());
			pstat.setString(4, dto.getAddr1());
			pstat.setString(5, dto.getAddr2());
			pstat.setString(6, dto.getId());

			int result = pstat.executeUpdate();

			return result;
			
			
		}
	}
	
}
