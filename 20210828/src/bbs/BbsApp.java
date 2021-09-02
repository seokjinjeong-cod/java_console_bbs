package bbs;

import java.util.Scanner;

public class BbsApp {
	static Scanner sc = new Scanner(System.in);
	static BbsDAO bbsDao = new BbsDAO();

	public static void main(String[] args) {

		int ls = 0; // 0:�α��ξ��ѻ���	 	1:ȸ���α��λ��� 		2:�����ڷα��λ���
		int selectMenu;
		while(true) {
			try {
				if(ls == 0) {
					System.out.println("-------------------------------------------------------------------------------------------------------------");
				} else {		//�α����������� ���� ��ܿ� ���̵�ǥ��
					System.out.println(bbsDao.userId + "--------------------------------------------------------------------------------------------------------");
				}
				System.out.println("0.�α���   1.�۾���   2.��ã��   3.�ۼ���   4.�ۻ���(������) 5.����ü ��ȸ   6.ȸ������   7.�α׾ƿ�  8.ȸ������(������) 9.ȸ�������ȸ(������)  10.����");
				System.out.println("-------------------------------------------------------------------------------------------------------------");
				selectMenu = sc.nextInt();
				sc.nextLine();
				if(selectMenu < MENU.LOGIN || selectMenu > MENU.EXIT)
					throw new MenuException(selectMenu);
				switch(selectMenu) {
				case MENU.LOGIN : 
					if(ls == 1 || ls == 2) {
						System.out.println("�̹� �α��λ��� �Դϴ�."); 
						break;
					} else {
						ls = loginBbs(); break;
					}
				case MENU.INSERT:
					if(ls == 1 || ls == 2) {
						insertBbs(); break;
					} else {
						System.out.println("�α����� ���ּ���.");
						break;
					}
				case MENU.SEARCH:
					searchBbs(); break;
				case MENU.UPDATE:
					if(ls == 1 || ls == 2) {
					updateBbs(); break;
					} else {
						System.out.println("�α��� ���ּ���.");
						break;
					}
				case MENU.DELETE:
					if(ls == 2) {
						deleteBbsMember(); break;
					} else {
						System.out.println("������ �����ϴ�.");
						System.out.println("�����ڷ� �α��� ���ּ���.");
						break;
					}
				case MENU.SEARCHALL:
					searchAllBbs(); break;
				case MENU.JOIN:
					joinBbs(); break;
				case MENU.LOGOUT:
					if(ls == 1 || ls == 2) {
						ls = 0;
						System.out.println("�α׾ƿ��Ͽ����ϴ�.");
						break;
					} else {
						System.out.println("�α����� �Ǿ����� �ʽ��ϴ�.");
						break;
					}
				case MENU.DELETEMEMBER:
					if(ls == 2) {
						deleteBbsMember(); break;
					} else {
						System.out.println("������ �����ϴ�.");
						System.out.println("�����ڷ� �α��� ���ּ���.");
						break;
					}
				case MENU.MEMBERLIST:
//					onlyAdmin(ls); break;
					if(ls == 2) {
						selectMemberList(); break;
					} else {
						System.out.println("������ �����ϴ�.");
						System.out.println("�����ڷ� �α��� ���ּ���.");
						break;
					}
				case MENU.EXIT:
					System.out.println("����");
					return;
				}
			} catch (MenuException e) {
				e.showWrongMessage();
			}
		}
		
		
	}
	
	//�����ڱ��Ѱ��
//	static void onlyAdmin(int ls) {
//		if(ls == 2) {
//			deleteBbsMember();
//		} else {
//			System.out.println("������ �����ϴ�."); 
//			System.out.println("�����ڷ� �α��� ���ּ���.");
//		}
//	}
	
	static void insertBbs() {
		BbsVO bbs = new BbsVO();
		System.out.println("-----------------------------�۾���-----------------------------");
//		System.out.print("�ۼ��� : ");
//		String name = sc.nextLine();
		System.out.print("���� : ");
		String title = sc.nextLine();
//		System.out.println("���� : ");
		String content = ScannerUtil.readMultiLine();
//		bbs.setName(name);
		bbs.setTitle(title);
		bbs.setContent(content);
		bbsDao.insert(bbs);
	}
	
	static void searchBbs() {
		System.out.println("-----------------------------��ã��-----------------------------");
		System.out.println("�ۼ��ڷ� �� ã��");
		System.out.print("�ۼ��� : ");
		String name = sc.nextLine();
		
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
		
//		System.out.println("����");
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
		System.out.println("-----------------------------�ۻ���-----------------------------");
		System.out.print("������ ���� ���� : ");
		String title = sc.nextLine();
		bbsDao.delete(title);
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
}
