package bbs;

public class MenuException extends Exception {

	int selectMenu;
	
	public MenuException(int selectMenu) {
		this.selectMenu = selectMenu;
	}
	
	public void showWrongMessage() {
		System.out.println("���� �޴���ȣ�Դϴ�.\n�ٽ� �Է��ϼ���.");
	}
}
