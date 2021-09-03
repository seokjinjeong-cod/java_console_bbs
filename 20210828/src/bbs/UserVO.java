package bbs;

public class UserVO {
	
	private String name;
	private int birth;
	private String phone;
	private String id;
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBirth() {
		return birth;
	}
	public void setBirth(int birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "성명 : " + name + "\t생년월일 : " + birth + "\t휴대폰 번호 : " + phone + "\t아이디 : " + id + "\t비밀번호 : "
				+ password;
	}
	
	
}
