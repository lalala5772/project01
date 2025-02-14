package dto;

import java.sql.Timestamp;

public class ReplyDTO {

	
	private int id;
	private String writer;
	private String contents;
	private Timestamp writeDate;
	private int parentSeq;
	
	public ReplyDTO() {}

	
	public ReplyDTO(int id, String writer, String contents, Timestamp writeDate, int parentSeq) {
		super();
		this.id = id;
		this.writer = writer;
		this.contents = contents;
		this.writeDate = writeDate;
		this.parentSeq = parentSeq;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public int getParentSeq() {
		return parentSeq;
	}


	public void setParentSeq(int parentSeq) {
		this.parentSeq = parentSeq;
	}
	
	
	
	
}