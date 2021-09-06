package bbs;

public class CmtVO extends BbsVO {

	@Override
	public String toString() {
		return "작성자 : " + this.getName() + "\n작성일 : " + this.getDate() + "\n내용 : \n" + this.getContent() + "\n";
	}

	
}
