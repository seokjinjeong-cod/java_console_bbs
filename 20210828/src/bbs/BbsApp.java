package bbs;
/*
 * 비회원 메뉴 : 0.로그인   1.글찾기(검색한 글자가 작성자, 내용, 제목에 포함된 게시글 조회) 2.글전체 조회   3.회원가입   4.종료
 * 회원 메뉴 : 0.글쓰기   1.글찾기   2.글수정(글번호로 작성자 본인만 수정가능) 3.글삭제(글번호로 본인만 삭제가능)   4.글전체 조회   5.글상세정보(게시글 + 댓글 조회, 댓글쓰기)  6.로그아웃(비회원 메뉴로 돌아감)  7.종료
 * 관리자 메뉴 : 0.글쓰기   1.글찾기   2.글수정   3.글삭제(관리자는 모든글 삭제 가능) 4.글전체 조회   5.글상세정보   6.회원삭제(아이디로 회원 삭제 (관리자 권한))  7.회원목록(회원 리스트 조회 (관리자 권한)) 8.로그아웃  9.종료
 */
import java.util.Scanner;

public class BbsApp {
	static Scanner sc = new Scanner(System.in);
	static BbsDAO bbsDao = new BbsDAO();

	public static void main(String[] args) {

		int ls = 0; // 0:로그인안한상태	 	1:회원로그인상태 		2:관리자로그인상태
		int selectMenu;
		while(true) {
			if(ls != 0) {
				System.out.println("-------------------");
				System.out.println("접속중인 회원 : " + bbsDao.userId);
			}
			if(ls == 0) {			//로그인 안했을때 메뉴
				try {
	//				System.out.println("---------------------------------------------------------------------------------------------------------------------");
	//				System.out.println("0.로그인   1.글쓰기   2.글찾기   3.글수정   4.글삭제(관리자) 5.글전체 조회   6.회원가입   7.로그아웃  8.회원삭제(관리자) 9.회원목록조회(관리자)  10.종료");
	//				System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("------------------------------------------");
					System.out.println("0.로그인   1.글찾기   2.글전체 조회   3.회원가입   4.종료");
					System.out.println("------------------------------------------");
					System.out.print("메뉴 선택>> ");
					selectMenu = sc.nextInt();
					sc.nextLine();
					if(selectMenu < MENU.LOGIN || selectMenu > MENU.EXIT)
						throw new MenuException(selectMenu);
					switch(selectMenu) {
					case MENU.LOGIN : 
							ls = loginBbs(); break;
					case MENU.SEARCH:
						searchBbs(); break;
					case MENU.SEARCHALL:
						searchAllBbs(); break;
					case MENU.JOIN:
						joinBbs(); break;
					case MENU.EXIT:
						System.out.println("종료");
						return;
					}
				} catch (MenuException e) {
					e.showWrongMessage();
				}
			}
			else if(ls == 1) {			//회원로그인 했을때 메뉴
				try {
					System.out.println("-------------------------------------------------------------------------");
					System.out.println("0.글쓰기   1.글찾기   2.글수정   3.글삭제   4.글전체 조회   5.글상세정보  6.로그아웃   7.종료");
					System.out.println("-------------------------------------------------------------------------");
					System.out.print("메뉴 선택>> ");
					selectMenu = sc.nextInt();
					sc.nextLine();
					if(selectMenu < MemberMENU.INSERT || selectMenu > MemberMENU.EXIT)
						throw new MenuException(selectMenu);
					switch(selectMenu) {
					case MemberMENU.INSERT:
						insertBbs(); break;
					case MemberMENU.SEARCH:
						searchBbs(); break;
					case MemberMENU.UPDATE:
						updateBbs(); break;
					case MemberMENU.DELETE:
						deleteBbs(ls); break;
					case MemberMENU.SEARCHALL:
						searchAllBbs(); break;
					case MemberMENU.DETAILS:
						detailsBbs();
						System.out.println("----------------------");
						System.out.println("1.댓글쓰기   2.메뉴로 나가기");
						System.out.println("----------------------");
						int m = sc.nextInt();
						if(m == 1) {
							addComment();
						} else if(m == 2){
							break;
						}
						break;
					case MemberMENU.LOGOUT:
						ls = 0;
						System.out.println("로그아웃 하였습니다.");
						break;
					case MemberMENU.EXIT:
						System.out.println("종료");
						return;
					}
				} catch (MenuException e) {
					e.showWrongMessage();
				}
			}
			else {			//관리자 로그인 했을때 메뉴
				try {
					System.out.println("---------------------------------------------------------------------------------");
					System.out.println("0.글쓰기   1.글찾기   2.글수정   3.글삭제   4.글전체 조회   5.글상세정보   6.회원삭제   7.회원목록   8.로그아웃  9.종료");
					System.out.println("---------------------------------------------------------------------------------");
					System.out.print("메뉴 선택>> ");
					selectMenu = sc.nextInt();
					sc.nextLine();
					if(selectMenu < AdminMENU.INSERT || selectMenu > AdminMENU.EXIT)
						throw new MenuException(selectMenu);
					switch(selectMenu) {
					case AdminMENU.INSERT:
							insertBbs(); break;
					case AdminMENU.SEARCH:
						searchBbs(); break;
					case AdminMENU.UPDATE:
						updateBbs(); break;
					case AdminMENU.DELETE:
						deleteBbs(ls); break;
					case AdminMENU.SEARCHALL:
						searchAllBbs(); break;
					case MemberMENU.DETAILS:
						detailsBbs();
						System.out.println("----------------------");
						System.out.println("1.댓글쓰기   2.메뉴로 나가기");
						System.out.println("----------------------");
						int m = sc.nextInt();
						if(m == 1) {
							addComment();
						} else if(m == 2){
							break;
						}
						break;
					case AdminMENU.DELETEMEMBER:
						deleteBbsMember(); break;
					case AdminMENU.MEMBERLIST:
						selectMemberList(); break;
					case AdminMENU.LOGOUT:
						ls = 0;
						System.out.println("로그아웃 하였습니다.");
						break;
					case AdminMENU.EXIT:
						System.out.println("종료");
						return;
					}
				} catch (MenuException e) {
					e.showWrongMessage();
				}
			}
		}
		
		
	}
	
	static void insertBbs() {
		BbsVO bbs = new BbsVO();
		System.out.println("-----------------------------글쓰기-----------------------------");
		System.out.print("제목 : ");
		String title = sc.nextLine();
		String content = ScannerUtil.readMultiLine();
		bbs.setTitle(title);
		bbs.setContent(content);
		bbsDao.insert(bbs);
	}
	
	static void searchBbs() {
		System.out.println("-----------------------------글찾기-----------------------------");
		System.out.println("검색 : ");
		String name = sc.nextLine();
		System.out.println("-----------------------------글목록-----------------------------");
		
		bbsDao.search(name);
	}
	
	static void updateBbs() {
		System.out.println("-----------------------------글수정-----------------------------");
		System.out.print("수정할 글 번호 : ");
		int no = sc.nextInt();
		sc.nextLine();

		BbsVO bbs = bbsDao.getBbs(no);
		System.out.print("제목 : ");
		String reTitle = sc.nextLine();
		if(!reTitle.equals("")) {
			bbs.setTitle(reTitle);
		} else {
			reTitle = bbsDao.getBbs(no).getTitle();
		}
		
		String content = ScannerUtil.readMultiLine();
		if(!content.equals("")) {
			bbs.setContent(content);
		} else {
			content = bbsDao.getBbs(no).getContent();
		}
		
		bbsDao.update(reTitle, content, no);
	}
	
	static void deleteBbs(int ls) {
		System.out.println("-----------------------------글삭제-----------------------------");
		System.out.print("삭제할 글의 번호 : ");
		int no = sc.nextInt();
		sc.nextLine();
		if(ls == 1) {
			bbsDao.delete(no);
		} else {
			bbsDao.adminDelete(no);
		}
	}
	
	static void searchAllBbs() {
		System.out.println("---------------------------글 전체 목록---------------------------");
		bbsDao.searchAll();
	}
	
	static void joinBbs() {
		System.out.println("---------------------------회원 가입---------------------------");
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
	
	static int loginBbs() {
	System.out.println("-----------------------------로그인-----------------------------");
	System.out.print("아이디 : ");
	String id = sc.nextLine();
	System.out.print("비밀번호 : ");
	String password = sc.nextLine();
	int loginResult = bbsDao.login(password, id);
	
	return loginResult;
	}
	
	
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
	
	static void addComment() {
		System.out.println("-----------------------------댓글 쓰기-----------------------------");
		String content = ScannerUtil.readMultiLine();
		BbsVO bbs = new BbsVO();
		bbs.setContent(content);
		bbsDao.comment(bbsDao.bbsNo, bbs);
		
	}
	
	static void detailsBbs() {
		System.out.println("-----------------------------글상세정보-----------------------------");
		System.out.println("글번호 : ");
		int no = sc.nextInt();
		bbsDao.details(no);
		System.out.println("--------------------------------댓글-------------------------------");
		bbsDao.showCmt(no);
	}
}