package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import commons.Statics;
import dto.BoardDTO;
import dto.MembersDTO;
import dto.ReplyDTO;

public class BoardDAO {

	
	public static BoardDAO instance; // 1. 싱글톤 패턴

	public synchronized static BoardDAO getInstance() {// 2. 싱글톤 패턴
		if (instance == null) {
			instance = new BoardDAO();
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
	
	public int getNextVal()throws Exception {
		String sql = "select board_seq.nextval from dual";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			rs.next();
			return rs.getInt(1);
		}
	}
	
	public int insert(BoardDTO dto) throws Exception {
		String sql = "insert into board (seq ,writer, title, contents, write_date, view_count) values (?,?,?,?,sysdate,?)";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			
			pstat.setInt(1, dto.getSeq());
			pstat.setString(2, dto.getWriter());
			pstat.setString(3, dto.getTitle());
			pstat.setString(4, dto.getContents());
			pstat.setInt(5, dto.getViewCount());
			
			
			System.out.println(dto.getWriter());
			System.out.println(dto.getTitle());
			//pstat.setDate(9, new java.sql.Date(System.currentTimeMillis())); // current date (SYSDATE)
			
			int result = pstat.executeUpdate();
			return result;
		}
	}
	
	public List<BoardDTO> selectBylist() throws Exception {
		String sql = "select * from board ORDER BY write_date DESC";
		try (Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();) {

			List<BoardDTO> list = new ArrayList<>();
			while (rs.next()) {
				int seq = rs.getInt("seq");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				Timestamp writeDate = rs.getTimestamp("write_date");
				int viewCount = rs.getInt("view_count");
				

				BoardDTO dto = new BoardDTO(seq, writer,title,writeDate,viewCount);
				list.add(dto);
			}
			return list;
		}
	}
	
	
	public List<BoardDTO> selectFromTo(int start, int end)throws Exception{
		String sql = "select * from (select board.*, row_number() over(order by seq desc) rnum from board) where rnum between ? and ?";
		try (Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			
			try(ResultSet rs = pstat.executeQuery();){
				List<BoardDTO> list = new ArrayList<>();
				while (rs.next()) {
					int seq = rs.getInt("seq");
					String title = rs.getString("title");
					String writer = rs.getString("writer");
					Timestamp writeDate = rs.getTimestamp("write_date");
					int viewCount = rs.getInt("view_count");
					

					BoardDTO dto = new BoardDTO(seq, writer,title,writeDate,viewCount);
					list.add(dto);
				}
				return list;
			}
			
		}
		
	}
	
	
	
	public List<BoardDTO> detailBylist(int seq) throws Exception {
		String sql = "select * from board where seq=?";
		try (Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				) {
			pstat.setInt(1, seq);
			try(ResultSet rs = pstat.executeQuery();) {
				List<BoardDTO> list = new ArrayList<>();
				while (rs.next()) {
					
					String title = rs.getString("title");
					String writer = rs.getString("writer");
					Timestamp writeDate = rs.getTimestamp("write_date");
					String contents = rs.getString("contents");
					

					BoardDTO dto = new BoardDTO(seq,writer,title,contents,writeDate);
					list.add(dto);
	         }
				return list;
			}
			
		}
	}
	
	public BoardDTO detailBylist2(int seq) throws Exception {
		String sql = "select * from board where seq=?";
		try (Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				) {
			pstat.setInt(1, seq);
			try(ResultSet rs = pstat.executeQuery();) {
				BoardDTO dto = new BoardDTO();
				while (rs.next()) {
					dto.setSeq(seq);
					dto.setTitle(rs.getString("title"));
					dto.setWriter(rs.getString("writer"));
					dto.setContents(rs.getString("contents"));
					dto.setWriteDate(rs.getTimestamp("write_date"));
					dto.setViewCount(rs.getInt("view_count"));
	         }
				return dto;
			}
			
		}
		
		
	}
	
	
	public String detailByWriter(int seq) throws Exception {
		String sql = "select * from board where seq=?";
		try (Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				) {
			pstat.setInt(1, seq);
			String writer = null;
			try(ResultSet rs = pstat.executeQuery();) {
				while (rs.next()) {
					writer = rs.getString("writer");
				}
				return writer;
			}
			
		}
		
	}
	public int deleteByContents(int seq) throws Exception{
		String sql = "delete from board where seq = ?";
		try (Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				) {
			pstat.setInt(1, seq);
			return pstat.executeUpdate();
			
			
		}

	}
	public int updateBylist(BoardDTO dto)throws Exception {
		String sql = "update board set title = ?, contents = ?, write_date = sysdate where seq = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContents());
			pstat.setInt(3, dto.getSeq());
			

			int result = pstat.executeUpdate();

			return result;
			
			
		}
	}
	public int incrementViewCount(int seq)throws Exception {
	    String sql = "UPDATE board SET view_count = view_count + 1 WHERE seq = ?";
	    try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
	    	pstat.setInt(1, seq);
	    	int result = pstat.executeUpdate();
	    	
	    	return result;
	    	}
	    
	    }
	public int getRecordTotalCount()throws Exception {
		String sql = "select count(*) from board";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);
		}
	}
	
	
//	public String getPageNavi(int currentPage)throws Exception {
//		
//		// 전체 글의 개수가 몇개인가?
//		
//		int recordTotalCount = this.getRecordTotalCount(); //DB에서 데이터 개수를 조회해 와야 함.
//			
//		// 한 페이지당 몇개씩 보여줄것인가?
//		int recordCountPerPage = Statics.recordCountPerPage; //한 페이지 당 10개씩 보여줄 것이다.
//		
//		//현재 페이지에 노출 될 네이비게이터 개수는 몇개로 할 것 인가.
//		int naviCountPerPage = Statics.naviCountPerPage;
//		
//		//전체 글의 개수를 알았고, 한 페이지에 10개씩 보여줄것이라면, 전체 페이지의 개수를 구할수 있음.
//		int pageTotalCount = 0; 
//		
//		// int형은 소수점이 안나오기때문에 나머지가 있을 경우 페이지를 한개 더 늘리고
//		if(recordTotalCount % recordCountPerPage > 0) {
//			
//			pageTotalCount = recordTotalCount/recordCountPerPage +1;
//		} //나머지 없을 경우 그대로.
//		else {
//			pageTotalCount = recordTotalCount/recordCountPerPage;
//		}
//		// 총 페이지 개수 확인!
//		//-------------------------
//		
//		//현재 보고 있는 페이지
//		//int currentPage = 10;
//		// 만약을 대비해서.
//		if(currentPage < 1) {
//			currentPage = 1;
//		}
//		else if(currentPage > pageTotalCount) {
//			currentPage = pageTotalCount;
//		}
//		//------------------------
//		//내비게이터 시작 번호
//		int startNavi = (((currentPage-1)/naviCountPerPage) * naviCountPerPage)+1;
//		//내비게이터 끝 번호.
//		int endNavi = startNavi + naviCountPerPage-1;
//		
//		
//		if(endNavi > pageTotalCount) {
//			endNavi = pageTotalCount;
//		}//endNavi 값은 페이지 전체 개수보다 클수없음!
//		
////		//이전
////		boolean needPrev = true;
////		//다음
////		boolean needNext = true;
////		
////		if(startNavi == 1) {
////			needPrev = false;
////		}
////		else if(endNavi == pageTotalCount) {
////			needNext = false;
////		}
//		
//		
//		//문자열 붙이기
//		StringBuilder navi = new StringBuilder();
////		//이전 붙이기
////		if(needPrev) {
////			navi.append("<a href='/list.board?cpage="+(startNavi-1)+"'> <이전</a> &nbsp;");
////		}
////		//내비게이션 넣기
////		for(int i = startNavi; i<=endNavi;i++) {
////			navi.append("<a href='/list.board?cpage="+i+"'>"+ i + "</a> &nbsp;");
////		}
////		//다음 붙이기
////		if(needNext) {
////			navi.append("<a href='/list.board?cpage="+(endNavi+1)+"'> 다음></a>");
////		}
//		
//		
//		System.out.println(navi);
//		
//		
//		return navi.toString();
//	}
	
	public int replyInsert(ReplyDTO dto)throws Exception {
		String sql = "insert into reply (id ,writer, contents, write_date, parent_seq) values (reply_seq.nextval,?,?,sysdate,?)";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getWriter());
			pstat.setString(2, dto.getContents());
			pstat.setInt(3, dto.getParentSeq());
			
			
			System.out.println(dto.getWriter());
			System.out.println(dto.getContents());
			System.out.println(dto.getParentSeq());
			//pstat.setDate(9, new java.sql.Date(System.currentTimeMillis())); // current date (SYSDATE)
			
			int result = pstat.executeUpdate();
			return result;
		}
	}
	
	
	public List<ReplyDTO> selectByReply(int seq) throws Exception {
		String sql = "SELECT * FROM reply WHERE parent_seq IN (SELECT seq FROM board where seq=?)";
		try (Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				) {
			pstat.setInt(1, seq);
			ResultSet rs = pstat.executeQuery();
			List<ReplyDTO> replyList = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String writer = rs.getString("writer");
				String contents = rs.getString("contents");
				Timestamp writeDate = rs.getTimestamp("write_date");
				int parentSeq = rs.getInt("parent_seq");
				

				ReplyDTO dto = new ReplyDTO(id, writer, contents ,writeDate, parentSeq);
				replyList.add(dto);
			}
			return replyList;
		}
	}
	
	public int deleteByReply(int id) throws Exception{
		String sql = "delete from reply where id = ?";
		try (Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				) {
			pstat.setInt(1, id);
			return pstat.executeUpdate();
			
			
		}

	}
	public int updateByReplyList(ReplyDTO rdto)throws Exception {
		String sql = "update reply set contents = ?, write_date = sysdate where id = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			
			pstat.setString(1, rdto.getContents());
			pstat.setInt(2, rdto.getId());
			

			int result = pstat.executeUpdate();

			return result;
			
			
		}
	}
	
		
//	public static void main(String[] args)throws Exception {
//		String sql = "insert into board values(board_seq.nextval,?,?,?,sysdate,0)";
//		try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","exam","exam");
//			PreparedStatement pstat = con.prepareStatement(sql);){
//				
//			for(int i = 1; i <= 144; i++) {
//				pstat.setString(1,"writer"+i);
//				pstat.setString(2,"title"+i);
//				pstat.setString(3,"contents"+i);
//				pstat.executeUpdate();
//				
//				Thread.sleep(300);
//				System.out.println(i+1+" 번째 데이터 입력");
//			}
//		}
//	}

}
