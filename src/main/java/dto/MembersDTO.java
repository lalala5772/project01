package dto;

public class MembersDTO {

	private String id;
	private String pw;
	private String pw2;
	private String name;
	private String email;
	private String phone;
	private String postcode;
	private String addr1;
	private String addr2;
	private String signDate;
	
	
	public MembersDTO() {}
	
	public MembersDTO(String id, String name, String email, String phone, String postcode,
			String addr1, String addr2) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.postcode = postcode;
		this.addr1 = addr1;
		this.addr2 = addr2;
		
		
	}
	
	public MembersDTO(String id, String pw, String name, String email, String phone, String postcode,
			String addr1, String addr2, String signDate) {
		super();
		this.id = id;
		this.pw = pw;
		
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.postcode = postcode;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.signDate = signDate;
	}
	
	
	
	
	
	
	public MembersDTO(String id, String pw, String pw2, String name, String email, String phone, String postcode,
			String addr1, String addr2, String signDate) {
		super();
		this.id = id;
		this.pw = pw;
		this.pw2 = pw2;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.postcode = postcode;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.signDate = signDate;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getPw2() {
		return pw2;
	}
	public void setPw2(String pw2) {
		this.pw2 = pw2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	
	
	
	
}
