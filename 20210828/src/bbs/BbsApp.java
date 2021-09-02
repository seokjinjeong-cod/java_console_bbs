package bbs;

import java.util.Scanner;

public class BbsApp {
	static Scanner sc = new Scanner(System.in);
	static BbsDAO bbsDao = new BbsDAO();

	public static void main(String[] args) {

		int ls = 0; // 0:로그인안한상태	 	1:회원로그인상태 		2:관리자로그인상태
		int selectMenu;
		while(true) {
			try {
				if(ls == 0) {
					System.out.println("-------------------------------------------------------------------------------------------------------------");
				} else {		//로그인했을때만 왼쪽 상단에 아이디표시
					System.out.println(bbsDao.userId + "--------------------------------------------------------------------------------------------------------");
				}
				System.out.println("0.로그인   1.글쓰기   2.글찾기   3.글수정   4.글삭제(관리자) 5.글전체 조회   6.회원가입   7.로그아웃  8.회원삭제(관리자) 9.회원목록조회(관리자)  10.종료");
				System.out.println("-------------------------------------------------------------------------------------------------------------");
				selectMenu = sc.nextInt();
				sc.nextLine();
				if(selectMenu < MENU.LOGIN || selectMenu > MENU.EXIT)
					throw new MenuException(selectMenu);
				switch(selectMenu) {
				case MENU.LOGIN : 
					if(ls == 1 || ls == 2) {
						System.out.println("이미 로그인상태 입니다."); 
						break;
					} else {
						ls = loginBbs(); break;
					}
				case MENU.INSERT:
					if(ls == 1 || ls == 2) {
						insertBbs(); break;
					} else {
						System.out.println("로그인을 해주세요.");
						break;
					}
				case MENU.SEARCH:
					searchBbs(); break;
				case MENU.UPDATE:
					if(ls == 1 || ls == 2) {
					updateBbs(); break;
					} else {
						System.out.println("로그인 해주세요.");
						break;
					}
				case MENU.DELETE:
					if(ls == 2) {
						deleteBbsMember(); break;
					} else {
						System.out.println("권한이 없습니다.");
						System.out.println("관리자로 로그인 해주세요.");
						break;
					}
				case MENU.SEARCHALL:
					searchAllBbs(); break;
				case MENU.JOIN:
					joinBbs(); break;
				case MENU.LOGOUT:
					if(ls == 1 || ls == 2) {
						ls = 0;
						System.out.println("로그아웃하였습니다.");
						break;
					} else {
						System.out.println("로그인이 되어있지 않습니다.");
						break;
					}
				case MENU.DELETEMEMBER:
					if(ls == 2) {
						deleteBbsMember(); break;
					} else {
						System.out.println("권한이 없습니다.");
						System.out.println("관리자로 로그인 해주세요.");
						break;
					}
				case MENU.MEMBERLIST:
//					onlyAdmin(ls); break;
					if(ls == 2) {
						selectMemberList(); break;
					} else {
						System.out.println("권한이 없습니다.");
						System.out.println("관리자로 로그인 해주세요.");
						break;
					}
				case MENU.EXIT:
					System.out.println("종료");
					return;
				}
			} catch (MenuException e) {
				e.showWrongMessage();
			}
		}
		
		
	}
	
	//관리자권한경고
//	static void onlyAdmin(int ls) {
//		if(ls == 2) {
//			deleteBbsMember();
//		} else {
//			System.out.println("권한이 없습니다."); 
//			System.out.println("관리자로 로그인 해주세요.");
//		}
//	}
	
	static void insertBbs() {
		BbsVO bbs = new BbsVO();
		System.out.println("-----------------------------글쓰기-----------------------------");
//		System.out.print("작성자 : ");
//		String name = sc.nextLine();
		System.out.print("제목 : ");
		String title = sc.nextLine();
//		System.out.println("내용 : ");
		String content = ScannerUtil.readMultiLine();
//		bbs.setName(name);
		bbs.setTitle(title);
		bbs.setContent(content);
		bbsDao.insert(bbs);
	}
	
	static void searchBbs() {
		System.out.println("-----------------------------글찾기-----------------------------");
		System.out.println("작성자로 글 찾기");
		System.out.print("작성자 : ");
		String name = sc.nextLine();
		
		bbsDao.search(name);
	}
	
	static void updateBbs() {
		System.out.println("-----------------------------글수정-----------------------------");
		System.out.print("수정할 글 제목 : ");
		String title = sc.nextLine();

		BbsVO bbs = bbsDao.getBbs(title);
		System.out.print("제목 : ");
		String reTitle = sc.nextLine();
		if(!reTitle.equals("")) {
			bbs.setTitle(reTitle);
		} else {
			reTitle = bbsDao.getBbs(title).getTitle();
		}
		
//		System.out.println("내용");
//		String content = sc.nextLine();
		String content = ScannerUtil.readMultiLine();
		if(!content.equals("")) {
			bbs.setContent(content);
		} else {
			content = bbsDao.getBbs(title).getContent();
		}
		
		bbsDao.update(reTitle, content, title);
	}
	
	static void deleteBbs() {
		System.out.println("-----------------------------글삭제-----------------------------");
		System.out.print("삭제할 글의 제목 : ");
		String title = sc.nextLine();
		bbsDao.delete(title);
	}
	
	static void searchAllBbs() {
		System.out.println("---------------------------글 전체 목록---------------------------");
		bbsDao.searchAll();
	}
	
	static void joinBbs() {
		UserVO user = new UserVO();
		System.out.print("성명 : ");
		String name  = sc.nextLine();
		System.out.print("생년월일(주민번호 앞6자리) : ");
		int birth = sc.nextInt();
		sc.nextLine();
		System.out.print("휴대폰 번호(숫자 13자리) : ");
		String phone = sc.nextLine();
		System.out.print("아이디 : ");
		String id = sc.nextLine();
		System.out.print("비밀번호 : ");
		String password = sc.nextLine();
		
		user.setName(name);
		user.setBirth(birth);
		user.setPhone(phone);
		user.setId(id);
		user.setPassword(password);
		
		bbsDao.join(user);
	}
	
//	static boolean loginBbs() {
//		System.out.println("-----------------------------로그인-----------------------------");
//		System.out.print("아이디 : ");
//		String id = sc.nextLine();
//		System.out.print("비밀번호 : ");
//		String password = sc.nextLine();
//		boolean loginResult = bbsDao.login(password, id);
//		
//		return loginResult;
//	}
	
	static int loginBbs() {
	System.out.println("-----------------------------로그인-----------------------------");
	System.out.print("아이디 : ");
	String id = sc.nextLine();
	System.out.print("비밀번호 : ");
	String password = sc.nextLine();
	int loginResult = bbsDao.login(password, id);
	
	return loginResult;
}
	
	
	
//	static boolean adminLoginBbs() {
//		System.out.println("---------------------------관리자 로그인---------------------------");
//		System.out.print("아이디 : ");
//		String id = sc.nextLine();
//		System.out.print("비밀번호 : ");
//		String password = sc.nextLine();
//		boolean loginResult = bbsDao.adminLogin(password, id);
//		
//		return loginResult;
//	}
	
	static void deleteBbsMember() {
		System.out.println("-----------------------------회원 삭제-----------------------------");
		System.out.println("삭제할 회원 아이디 : ");
		String id = sc.nextLine();
		bbsDao.deleteMember(id);
	}
	
	static void selectMemberList() {
		System.out.println("-----------------------------회원 목록-----------------------------");
		bbsDao.showAllBbsMember();
	}
}
