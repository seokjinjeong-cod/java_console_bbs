package bbs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BbsDAO extends DAO {
	
	String userId = null;		//로그인한 회원이 누구인지 저장하는 변수

	// 등록
	void insert(BbsVO bbs) {
		String sql = "insert into memo\r\n"
				+ "values(BBS_NUM.nextval, ?, ?, TO_CHAR(SYSDATE, 'yyyy.mm.dd hh24:mi'), ?)";
		connect();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, bbs.getTitle());
			pstmt.setString(3, bbs.getContent());
			int cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				System.out.println("글이 등록되었습니다.");
			} 
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("이미 존재하는 제목이거나 잘못 입력하였습니다.\n 제목을 변경해주세요.");
		}

	}

	// 검색
	void search(String name) {
		List<BbsVO> list = new ArrayList<>();
		boolean isValid = false;
		try {
			String sql = "select * from memo where name like '%'||?||'%'";
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
			}

			for (BbsVO bbs : list) {
				System.out.println(bbs);
			}

			if(isValid == true) {
				System.out.println("조회결과 입니다.");
			}
			else if (isValid == false) {
				System.out.println("조회결과가 없습니다.");
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("잘못 입력하였습니다.");
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
	
	// 수정(작성자 본인만)
	void update(String reTitle, String content, String title) {
		try {
			String sql1 = "select name, title from memo where title = ?";
			String sql2 = "update memo set title = ?, content = ? where title = ?";
			connect();
			
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			BbsVO bbs = new BbsVO();
			while(rs.next()) {
				bbs.setName(rs.getString("name"));
			}
			
			if(userId.equals(bbs.getName())) {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, reTitle);
				pstmt.setString(2, content);
				pstmt.setString(3, title);
				System.out.println("수정되었습니다.");
				
			} else {
				System.out.println("작성자만 수정할 수 있습니다.");
			}
				
			pstmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	void delete(String title) {
		try {
			String sql1 = "select name, title from memo where title = ?";
			String sql2 = "delete memo where title = ?";
			connect();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			BbsVO bbs = new BbsVO();
			while(rs.next()) {
				bbs.setName(rs.getString("name"));
			}
			
			if(userId.equals(bbs.getName())) {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, title);
				pstmt.executeUpdate();
				System.out.println("삭제되었습니다.");
				
			} else {
				System.out.println("작성자만 삭제할 수 있습니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 삭제(관리자)
	void adminDelete(String title) {
		try {
			String sql = "delete memo where title = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) {
				System.out.println("삭제되었습니다.");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 전체조회
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

	// 회원가입
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
				System.out.println("회원가입이 완료되었습니다.");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	// 로그인
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
//					System.out.println("로그인 하였습니다.");
//				}
//			}
//			if (isValid == false) {
//				System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
//			}
//			rs.close();
//			pstmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return isValid;
//	}
	
	// 로그인 		
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
					userId = id;
					loginResult = 2;
					System.out.println("관리자로 로그인하였습니다.");
				}
				else if (password.equals(rs.getString("password")) && id.equals(rs.getString("id"))) {
					userId = id;
					loginResult = 1;
					System.out.println("로그인 하였습니다.");
				} 
			}
			if (loginResult == 0) {
				System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginResult;
	}

//	// 관리자 로그인
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
//					System.out.println("관리자로 로그인하였습니다. ");
//				}
//			}
//			if (isValid == false) {
//				System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
//			}
//			rs.close();
//			pstmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return isValid;
//	}
	
	//회원삭제(관리자)
	void deleteMember(String id) {
		try {
			String sql = "delete userlist where id = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int cnt = pstmt.executeUpdate();
			if(cnt > 0) {
				System.out.println("회원이 삭제되었습니다.");
			} else {
				System.out.println("존재하지 않는 회원입니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//회원 목록 조회(관리자)
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
	
	
	void comment() {
		
	}
}
