package dto;

public class FilesDTO {

	private int id;
	private String oriName;
	private String sysName;
	private int parent_seq;
	
		//상황에 따라
		//저장 경로
		//파일 사이즈
		//업로드 된 날짜 등등.
	
	
	public FilesDTO() {}
	
	public FilesDTO(int id, String oriName, String sysName, int parent_seq) {
		super();
		this.id = id;
		this.oriName = oriName;
		this.sysName = sysName;
		this.parent_seq = parent_seq;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriName() {
		return oriName;
	}

	public void setOriName(String oriName) {
		this.oriName = oriName;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public int getParent_seq() {
		return parent_seq;
	}

	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}
	
	
	
	
	
	
	
}
