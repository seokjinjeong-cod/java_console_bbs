package bbs;

public class MenuException extends Exception {

	int selectMenu;
	
	public MenuException(int selectMenu) {
		this.selectMenu = selectMenu;
	}
	
	public void showWrongMessage() {
		System.out.println("없는 메뉴번호입니다.\n다시 입력하세요.");
	}
}
