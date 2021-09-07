package bbs;

import java.util.Scanner;

public class BbsApp {
	static Scanner sc = new Scanner(System.in);
	static BbsDAO bbsDao = new BbsDAO();

	public static void main(String[] args) {

		int ls = 0; // 0:�α��ξ��ѻ���	 	1:ȸ���α��λ��� 		2:�����ڷα��λ���
		int selectMenu;
		while(true) {
			if(ls != 0) {
				System.out.println("-------------------");
				System.out.println("�������� ȸ�� : " + bbsDao.userId);
			}
			if(ls == 0) {			//�α��� �������� �޴�
				try {
	//				System.out.println("---------------------------------------------------------------------------------------------------------------------");
	//				System.out.println("0.�α���   1.�۾���   2.��ã��   3.�ۼ���   4.�ۻ���(������) 5.����ü ��ȸ   6.ȸ������   7.�α׾ƿ�  8.ȸ������(������) 9.ȸ�������ȸ(������)  10.����");
	//				System.out.println("---------------------------------------------------------------------------------------------------------------------");
					System.out.println("------------------------------------------");
					System.out.println("0.�α���   1.��ã��   2.����ü ��ȸ   3.ȸ������   4.����");
					System.out.println("------------------------------------------");
					selectMenu = sc.nextInt();
					sc.nextLine();
					if(selectMenu < MENU.LOGIN || selectMenu > MENU.EXIT)
						throw new MenuException(selectMenu);
					switch(selectMenu) {
					case MENU.LOGIN : 
//						if(ls == 1 || ls == 2) {
//							System.out.println("�̹� �α��λ��� �Դϴ�."); 
//							break;
//						} else {
							ls = loginBbs(); break;
//						}
	//				case MENU.INSERT:
	//					if(ls == 1 || ls == 2) {
	//						insertBbs(); break;
	//					} else {
	//						System.out.println("�α����� ���ּ���.");
	//						break;
	//					}
					case MENU.SEARCH:
						searchBbs(); break;
	//				case MENU.UPDATE:
	//					if(ls == 1 || ls == 2) {
	//					updateBbs(); break;
	//					} else {
	//						System.out.println("�α��� ���ּ���.");
	//						break;
	//					}
	//				case MENU.DELETE:
	//					if(ls == 2) {
	//						deleteBbsMember(); break;
	//					} else {
	//						System.out.println("������ �����ϴ�.");
	//						System.out.println("�����ڷ� �α��� ���ּ���.");
	//						break;
	//					}
					case MENU.SEARCHALL:
						searchAllBbs(); break;
					case MENU.JOIN:
						joinBbs(); break;
	//				case MENU.LOGOUT:
	//					if(ls == 1 || ls == 2) {
	//						ls = 0;
	//						System.out.println("�α׾ƿ��Ͽ����ϴ�.");
	//						break;
	//					} else {
	//						System.out.println("�α����� �Ǿ����� �ʽ��ϴ�.");
	//						break;
	//					}
	//				case MENU.DELETEMEMBER:
	//					if(ls == 2) {
	//						deleteBbsMember(); break;
	//					} else {
	//						System.out.println("������ �����ϴ�.");
	//						System.out.println("�����ڷ� �α��� ���ּ���.");
	//						break;
	//					}
	//				case MENU.MEMBERLIST:
	//					if(ls == 2) {
	//						selectMemberList(); break;
	//					} else {
	//						System.out.println("������ �����ϴ�.");
	//						System.out.println("�����ڷ� �α��� ���ּ���.");
	//						break;
	//					}
					case MENU.EXIT:
						System.out.println("����");
						return;
					}
				} catch (MenuException e) {
					e.showWrongMessage();
				}
			}
			else if(ls == 1) {			//ȸ���α��� ������ �޴�
				try {
					System.out.println("-------------------------------------------------------------------------");
					System.out.println("0.�۾���   1.��ã��   2.�ۼ���   3.�ۻ���   4.����ü ��ȸ   5.�ۻ�����  6.�α׾ƿ�   7.����");
					System.out.println("-------------------------------------------------------------------------");
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
						System.out.println("1.��۾���   2.�޴��� ������");
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
						System.out.println("�α׾ƿ� �Ͽ����ϴ�.");
						break;
					case MemberMENU.EXIT:
						System.out.println("����");
						return;
					}
				} catch (MenuException e) {
					e.showWrongMessage();
				}
			}
			else {			//������ �α��� ������ �޴�
				try {
					System.out.println("---------------------------------------------------------------------------------");
					System.out.println("0.�۾���   1.��ã��   2.�ۼ���   3.�ۻ���   4.����ü ��ȸ   5.�ۻ�����   6.ȸ������   7.ȸ�����   8.�α׾ƿ�  9.����");
					System.out.println("---------------------------------------------------------------------------------");
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
						System.out.println("1.��۾���   2.�޴��� ������");
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
						System.out.println("�α׾ƿ� �Ͽ����ϴ�.");
						break;
					case AdminMENU.EXIT:
						System.out.println("����");
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
		System.out.println("-----------------------------�۾���-----------------------------");
		System.out.print("���� : ");
		String title = sc.nextLine();
		String content = ScannerUtil.readMultiLine();
		bbs.setTitle(title);
		bbs.setContent(content);
		bbsDao.insert(bbs);
	}
	
	static void searchBbs() {
		System.out.println("-----------------------------��ã��-----------------------------");
		System.out.println("�˻� : ");
//		System.out.print("�ۼ��� : ");
		String name = sc.nextLine();
		System.out.println("-----------------------------�۸��-----------------------------");
		
		bbsDao.search(name);
	}
	
	static void updateBbs() {
		System.out.println("-----------------------------�ۼ���-----------------------------");
		System.out.print("������ �� ���� : ");
		String title = sc.nextLine();

		BbsVO bbs = bbsDao.getBbs(title);
		System.out.print("���� : ");
		String reTitle = sc.nextLine();
		if(!reTitle.equals("")) {
			bbs.setTitle(reTitle);
		} else {
			reTitle = bbsDao.getBbs(title).getTitle();
		}
		
		String content = ScannerUtil.readMultiLine();
		if(!content.equals("")) {
			bbs.setContent(content);
		} else {
			content = bbsDao.getBbs(title).getContent();
		}
		
		bbsDao.update(reTitle, content, title);
	}
	
	static void deleteBbs(int ls) {
		System.out.println("-----------------------------�ۻ���-----------------------------");
		System.out.print("������ ���� ���� : ");
		String title = sc.nextLine();
		if(ls == 1) {
			bbsDao.delete(title);
		} else {
			bbsDao.adminDelete(title);
		}
	}
	
	static void searchAllBbs() {
		System.out.println("---------------------------�� ��ü ���---------------------------");
		bbsDao.searchAll();
	}
	
	static void joinBbs() {
		UserVO user = new UserVO();
		System.out.print("���� : ");
		String name  = sc.nextLine();
		System.out.print("�������(�ֹι�ȣ ��6�ڸ�) : ");
		int birth = sc.nextInt();
		sc.nextLine();
		System.out.print("�޴��� ��ȣ(���� 13�ڸ�) : ");
		String phone = sc.nextLine();
		System.out.print("���̵� : ");
		String id = sc.nextLine();
		System.out.print("��й�ȣ : ");
		String password = sc.nextLine();
		
		user.setName(name);
		user.setBirth(birth);
		user.setPhone(phone);
		user.setId(id);
		user.setPassword(password);
		
		bbsDao.join(user);
	}
	
//	static boolean loginBbs() {
//		System.out.println("-----------------------------�α���-----------------------------");
//		System.out.print("���̵� : ");
//		String id = sc.nextLine();
//		System.out.print("��й�ȣ : ");
//		String password = sc.nextLine();
//		boolean loginResult = bbsDao.login(password, id);
//		
//		return loginResult;
//	}
	
	static int loginBbs() {
	System.out.println("-----------------------------�α���-----------------------------");
	System.out.print("���̵� : ");
	String id = sc.nextLine();
	System.out.print("��й�ȣ : ");
	String password = sc.nextLine();
	int loginResult = bbsDao.login(password, id);
	
	return loginResult;
}
	
	
	
//	static boolean adminLoginBbs() {
//		System.out.println("---------------------------������ �α���---------------------------");
//		System.out.print("���̵� : ");
//		String id = sc.nextLine();
//		System.out.print("��й�ȣ : ");
//		String password = sc.nextLine();
//		boolean loginResult = bbsDao.adminLogin(password, id);
//		
//		return loginResult;
//	}
	
	static void deleteBbsMember() {
		System.out.println("-----------------------------ȸ�� ����-----------------------------");
		System.out.println("������ ȸ�� ���̵� : ");
		String id = sc.nextLine();
		bbsDao.deleteMember(id);
	}
	
	static void selectMemberList() {
		System.out.println("-----------------------------ȸ�� ���-----------------------------");
		bbsDao.showAllBbsMember();
	}
	
	static void addComment() {
		System.out.println("-----------------------------��� ����-----------------------------");
//		System.out.println("����� �� �۹�ȣ : ");
//		int no = sc.nextInt();
//		sc.nextLine();
		String content = ScannerUtil.readMultiLine();
		BbsVO bbs = new BbsVO();
		bbs.setContent(content);
		bbsDao.comment(bbsDao.bbsNo, bbs);
		
	}
	
	static void detailsBbs() {
		System.out.println("-----------------------------�ۻ�����-----------------------------");
		System.out.println("�۹�ȣ : ");
		int no = sc.nextInt();
		bbsDao.details(no);
		System.out.println("--------------------------------���-------------------------------");
		bbsDao.showCmt(no);
	}
}