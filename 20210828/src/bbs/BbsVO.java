package bbs;

public class BbsVO {
	
	private int no;
	private String name;
	private String title;
	private String content;
	private String date;
	private int cmtCount;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public int getCmtCount() {
		return cmtCount;
	}

	public void setCmtCount(int cmtCount) {
		this.cmtCount = cmtCount;
	}

	@Override
	public String toString() {
		return "작성자 : " + name + "\t글번호 : " + no + "\t댓글수 : " + cmtCount + "\n작성일 : " + date + "\n제목 : " + title + "\n내용 : \n" + content + "\n";
	}
}
