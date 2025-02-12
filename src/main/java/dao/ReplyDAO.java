//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;
//
//import dto.BoardDTO;
//import dto.ReplyDTO;
//
//public class ReplyDAO {
//	
//	public static ReplyDAO instance;
//	
//	public synchronized static ReplyDAO getInstance() {// 2. 싱글톤 패턴
//		if (instance == null) {
//			instance = new ReplyDAO();
//		}
//		return instance; 
//	}
//
//	private Connection getConnection() throws Exception {
//		// Tomcat 서버의 DBCP를 사용하여 커넥션을 가져오는 코드(DB 연결 생성)
//		Context ctx = new InitialContext(); // [1] ctx -> tomcat 전체
//		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/orcl"); // [2] java:comp/env -> Resource 태그로 만들어진
//																			// 인스턴스 영역
//		// /jdbc/orcl -> 우리가 정한 이름을 가진 인스턴스
//		return ds.getConnection();
//	}
//	
//	public int replyInsert(ReplyDTO dto)throws Exception {
//		String sql = "insert into reply (id ,writer, contents, write_date, parent_seq) values (reply_seq.nextval,?,?,sysdate,?)";
//		try(Connection con = this.getConnection();
//				PreparedStatement pstat = con.prepareStatement(sql);
//				){
//			pstat.setString(1, dto.getWriter());
//			pstat.setString(2, dto.getContents());
//			pstat.setInt(3, dto.getParentSeq());
//			
//			
//			System.out.println(dto.getWriter());
//			System.out.println(dto.getContents());
//			System.out.println(dto.getParentSeq());
//			//pstat.setDate(9, new java.sql.Date(System.currentTimeMillis())); // current date (SYSDATE)
//			
//			int result = pstat.executeUpdate();
//			return result;
//		}
//		
//		
//		
//	}
//	public List<ReplyDTO> selectByReply() throws Exception {
//		String sql = "select * from reply ORDER BY write_date";
//		try (Connection con = this.getConnection();
//				PreparedStatement pstat = con.prepareStatement(sql);
//				ResultSet rs = pstat.executeQuery();) {
//
//			List<ReplyDTO> replyList = new ArrayList<>();
//			while (rs.next()) {
//				int id = rs.getInt("id");
//				String writer = rs.getString("writer");
//				String contents = rs.getString("contents");
//				Timestamp writeDate = rs.getTimestamp("write_date");
//				int parentSeq = rs.getInt("parent_seq");
//				
//
//				ReplyDTO dto = new ReplyDTO(id, writer, contents ,writeDate, parentSeq);
//				replyList.add(dto);
//			}
//			return replyList;
//		}
//	}
//	
//	
//	
//	
//}
