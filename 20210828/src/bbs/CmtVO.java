package bbs;

public class CmtVO extends BbsVO {

	@Override
	public String toString() {
		return "�ۼ��� : " + this.getName() + "\n�ۼ��� : " + this.getDate() + "\n���� : \n" + this.getContent() + "\n";
	}

	
}
