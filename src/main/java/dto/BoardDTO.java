package dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BoardDTO {

	
	private int seq;
	private String title;
	private String writer;
	private String contents;
	private Timestamp writeDate;
	private int viewCount;
	private String dateWrite;
	
	public BoardDTO() {}
	
	public BoardDTO(String writer, String title, String contents, Timestamp writeDate) {
		super();
		this.writer = writer;
		this.title = title;
		
		this.contents = contents;
		this.writeDate = writeDate;
	}
	
	public BoardDTO(int seq, String writer, String title, Timestamp writeDate, int viewCount) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.title = title;
		
		this.writeDate = writeDate;
		this.viewCount = viewCount;
	}
	
	public BoardDTO(int seq, String writer, String title,String contents ) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		
	}
	
	public BoardDTO(int seq, String writer, String title, String contents, Timestamp writeDate) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.writeDate = writeDate;
		
	}
	public BoardDTO(int seq,  String writer, String title, String contents, Timestamp writeDate, int viewCount) {
		super();
		this.seq = seq;
		this.title = title;
		this.writer = writer;
		this.contents = contents;
		this.writeDate = writeDate;
		this.viewCount = viewCount;
		
	}

	public String getDateWrite() {
        /* 데이터베이스에서 가지고 온 게시글 저장시간 커스텀 후 출력 */
        long con_data = this.writeDate.getTime(); // 데이터베이스에서 가지고온 시간
        long current_time = System.currentTimeMillis(); //현재시간
        long getTime = (current_time - con_data)/1000; // (현재시간 - 데이터베이스에서 가지고온 시간)/1000
        if(getTime < 60) {
            return "방금 전";
        }else if(getTime < 300) {
            return "5분 이내";
        }else if(getTime < 3600) {
            return "1시간 이내";
        }else {
            this.dateWrite = new SimpleDateFormat("YYYY-MM-dd").format(con_data);
            //SimpleDateFormat은 .format에 들어간 데이터를 SimpleDateFormat("YYYY-MM-dd")패천으로 변경한다는 뜻
            return dateWrite;
        }
    }
 
 
    
	
	
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Timestamp getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Timestamp writeDate) {
		this.writeDate = writeDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}



	public void setDateWrite(String dateWrite) {
		this.dateWrite = dateWrite;
	}
	
	
	
	
	
	
	
	
	
}
