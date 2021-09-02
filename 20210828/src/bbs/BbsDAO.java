package bbs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BbsDAO extends DAO {

	// ���
	void insert(BbsVO bbs) {
		String sql = "insert into memo\r\n"
				+ "values(BBS_NUM.nextval, ?, ?, TO_CHAR(SYSDATE, 'yyyy.mm.dd hh24:mi'), ?)";
		connect();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bbs.getName());
			pstmt.setString(2, bbs.getTitle());
			pstmt.setString(3, bbs.getContent());
			int cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				System.out.println("���� ��ϵǾ����ϴ�.");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("�̹� �����ϴ� �����Դϴ�.\n ������ �������ּ���.");
		}

	}

	// �˻�
	void search(String name) {
		List<BbsVO> list = new ArrayList<>();
		boolean isValid = false;
		try {
			String sql = "select * from memo where name = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				isValid = true;
				BbsVO bbs = new BbsVO();
				bbs.setName(rs.getString("name"));
				bbs.setTitle(rs.getString("title"));
				bbs.setContent(rs.getString("content"));
				list.add(bbs);
//				System.out.println(list);
//				System.out.println("-----------------�˻� ���-----------------");
//				System.out.println("�ۼ��� : " + bbs.getName());
//				System.out.println("���� : " + bbs.getName());
//				System.out.println("����\n" + bbs.getName());
//				System.out.println("----------------------------------------");
			}

			for (BbsVO bbs : list) {
				System.out.println(bbs);
			}

			if (isValid == false) {
				System.out.println("���������ʴ� ���Դϴ�.");
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("�߸� �Է��Ͽ����ϴ�.");
		}

	}

	//getbbs
	BbsVO getBbs(String title) {
		BbsVO bbs = null;
		try {
			String sql = "select * from memo where title = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bbs = new BbsVO();
				bbs.setName(rs.getString("name"));
				bbs.setTitle(rs.getString("title"));
				bbs.setContent(rs.getString("content"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bbs;
	}
	
	// ����
	void update(String reTitle, String content, String title) {
		try {
			String sql = "update memo set title = ?, content = ? where title = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reTitle);
			pstmt.setString(2, content);
			pstmt.setString(3, title);
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) {
				System.out.println("�����Ǿ����ϴ�.");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// ����(������)
	void delete(String title) {
		try {
			String sql = "delete memo where title = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) {
				System.out.println("�����Ǿ����ϴ�.");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ��ü��ȸ
	void searchAll() {
		List<BbsVO> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM memo ORDER BY RDATE";
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BbsVO bbs = new BbsVO();
				bbs.setName(rs.getString("name"));
				bbs.setTitle(rs.getString("title"));
				bbs.setContent(rs.getString("content"));

				list.add(bbs);
			}

			for (BbsVO bbs : list) {
				System.out.println(bbs);
			}
			stmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ȸ������
	void join(UserVO user) {
		try {
			String sql = "insert into userlist\r\n" + "values(?, ?, ?, ?, ?)";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setInt(2, user.getBirth());
			pstmt.setString(3, user.getPhone());
			pstmt.setString(4, user.getId());
			pstmt.setString(5, user.getPassword());
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) {
				System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	// �α���
//	boolean login(String password, String id) {
//		boolean isValid = false;
//		try {
//			String sql = "select * from userlist where id = ?";
//			connect();
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, id);
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				if (password.equals(rs.getString("password")) && id.equals(rs.getString("id"))) {
//					isValid = true;
//					System.out.println("�α��� �Ͽ����ϴ�.");
//				}
//			}
//			if (isValid == false) {
//				System.out.println("���̵� �Ǵ� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
//			}
//			rs.close();
//			pstmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return isValid;
//	}
	
	// �α��� 		
	int login(String password, String id) {
		int loginResult = 0;
		try {
			String sql = "select * from userlist where id = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if(password.equals("admin") && id.equals("admin")) {
					loginResult = 2;
					System.out.println("�����ڷ� �α����Ͽ����ϴ�.");
				}
				else if (password.equals(rs.getString("password")) && id.equals(rs.getString("id"))) {
					loginResult = 1;
					System.out.println("�α��� �Ͽ����ϴ�.");
				} 
			}
			if (loginResult == 0) {
				System.out.println("���̵� �Ǵ� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginResult;
	}

//	// ������ �α���
//	boolean adminLogin(String password, String id) {
//		boolean isValid = false;
//		try {
//			String sql = "select * from userlist where id = ?";
//			connect();
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, id);
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				if (password.equals("admin") && id.equals("admin")) {
//					isValid = true;
//					System.out.println("�����ڷ� �α����Ͽ����ϴ�. ");
//				}
//			}
//			if (isValid == false) {
//				System.out.println("���̵� �Ǵ� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
//			}
//			rs.close();
//			pstmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return isValid;
//	}
	
	//ȸ������(������)
	void deleteMember(String id) {
		try {
			String sql = "delete userlist where id = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int cnt = pstmt.executeUpdate();
			if(cnt > 0) {
				System.out.println("ȸ���� �����Ǿ����ϴ�.");
			} else {
				System.out.println("�������� �ʴ� ȸ���Դϴ�.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//ȸ�� ��� ��ȸ(������)
	void showAllBbsMember() {
		List<UserVO> memberList = new ArrayList<>();
		try {
			String sql = "select * from userlist";
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				UserVO user = new UserVO();
				user.setName(rs.getString("name"));
				user.setBirth(rs.getInt("birth"));
				user.setPhone(rs.getString("phone"));
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				
				memberList.add(user);
			}
			
			for(UserVO us : memberList) {
				System.out.println(us);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
