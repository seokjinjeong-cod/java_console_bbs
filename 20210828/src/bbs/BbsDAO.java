package bbs;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BbsDAO extends DAO {
	
	String userId = null;		//로그인한 회원이 누구인지 저장하는 변수
	int bbsNo = 0;				//댓글달 글번호 저장하는 변수

	// 게시글 등록
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
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	// 게시글 검색
	void search(String word) {
		List<BbsVO> list = new ArrayList<>();
		boolean isValid = false;
		try {
			String sql = "select m.*,(select count(*) from cmt where no = m.no) cmt_cnt "
					+ "from memo m where name like '%'||?||'%' "
					+ "or title like '%'||?||'%' or content like '%'||?||'%'";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, word);
			pstmt.setString(2, word);
			pstmt.setString(3, word);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				isValid = true;
				BbsVO bbs = new BbsVO();
				bbs.setNo(rs.getInt("no"));
				bbs.setName(rs.getString("name"));
				bbs.setDate(rs.getString("rdate"));
				bbs.setTitle(rs.getString("title"));
				bbs.setContent(rs.getString("content"));
				bbs.setCmt_cnt(rs.getInt("cmt_cnt"));
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	//게시글 정보 가져오기 getbbs
	BbsVO getBbs(int no) {
		BbsVO bbs = null;
		try {
			String sql = "select * from memo where no = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bbs = new BbsVO();
				bbs.setName(rs.getString("name"));
				bbs.setTitle(rs.getString("title"));
				bbs.setContent(rs.getString("content"));
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return bbs;
	}
	
	// 게시글 수정(작성자 본인만)
	void update(String reTitle, String content, int no) {
		try {
			String sql1 = "select name, title from memo where no = ?";
			String sql2 = "update memo set title = ?, content = ? where no = ?";
			connect();
			
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			BbsVO bbs = new BbsVO();
			while(rs.next()) {
				bbs.setName(rs.getString("name"));
			}
			
			if(userId.equals(bbs.getName())) {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, reTitle);
				pstmt.setString(2, content);
				pstmt.setInt(3, no);
				pstmt.executeUpdate();
				System.out.println("수정되었습니다.");
				
			} else {
				System.out.println("작성자만 수정할 수 있습니다.");
			}
				
			pstmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("존재하지 않는 글입니다.");
		} finally {
			disconnect();
		}

	}

	// 게시글 삭제(작성자 본인만)
	void delete(int no) {
		try {
			String sql1 = "select name, title from memo where no = ?";
			String sql2 = "delete memo where no = ?";
			connect();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			BbsVO bbs = new BbsVO();
			while(rs.next()) {
				bbs.setName(rs.getString("name"));
			}
			
			if(userId.equals(bbs.getName())) {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, no);
				pstmt.executeUpdate();
				System.out.println("삭제되었습니다.");
				
			} else {
				System.out.println("작성자만 삭제할 수 있습니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	// 게시글 삭제(모든게시글 삭제가능(관리자))
	void adminDelete(int no) {
		try {
			String sql = "delete memo where no = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) {
				System.out.println("삭제되었습니다.");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 게시글 전체조회
	void searchAll() {
		List<BbsVO> list = new ArrayList<>();
		try {
			String sql = "select m.*,(select count(*) from cmt where no = m.no) cmt_cnt from memo m order by rdate";
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BbsVO bbs = new BbsVO();
				bbs.setNo(rs.getInt("no"));
				bbs.setName(rs.getString("name"));
				bbs.setDate(rs.getString("rdate"));
				bbs.setTitle(rs.getString("title"));
				bbs.setContent(rs.getString("content"));
				bbs.setCmt_cnt(rs.getInt("cmt_cnt"));

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
		} finally {
			disconnect();
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
			System.out.println("이미 존재하는 아이디입니다.");
		} finally {
			disconnect();
		}
	}

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
		} finally {
			disconnect();
		}
		return loginResult;
	}

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
		} finally {
			disconnect();
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
		} finally {
			disconnect();
		}
	}
	
	
	//댓글(로그인 상태에서만)
	void comment(int no, BbsVO bbs) {
		try {
			String sql1 = "select * from memo where no = ?";
			String sql2 = "insert into cmt values(?, ?, TO_CHAR(SYSDATE, 'yyyy.mm.dd hh24:mi'), ?)";
			connect();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bbs.setNo(rs.getInt("no"));
			}
			
			if(no == bbs.getNo()) {
				pstmt = conn.prepareCall(sql2);
				pstmt.setInt(1, no);
				pstmt.setString(2, userId);
				pstmt.setString(3, bbs.getContent());
				int cnt = pstmt.executeUpdate();
				if(cnt > 0) {
					System.out.println("댓글이 추가되었습니다.");
				} else {
					System.out.println("내용을 입력하세요.");
				}
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
	}
	
	//글상세정보 보기(댓글도같이 보기)
	void details(int no) {
		try {
			String sql = "select * from memo where no = ?";
			connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bbsNo = no;
				BbsVO bbs = new BbsVO();
				bbs.setNo(rs.getInt("no"));
				bbs.setName(rs.getString("name"));
				bbs.setDate(rs.getString("rdate"));
				bbs.setTitle(rs.getString("title"));
				bbs.setContent(rs.getString("content"));
				
				System.out.println(bbs);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	// 댓글 보기
	void showCmt(int no) {
		List<CmtVO> list = new ArrayList<CmtVO>();
		try {
			String sql = "select * from cmt where no = ?";
			connect();

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CmtVO cmt = new CmtVO();
				cmt.setNo(rs.getInt("no"));
				cmt.setName(rs.getString("name"));
				cmt.setDate(rs.getString("rdate"));
				cmt.setContent(rs.getString("cmt"));
				
				list.add(cmt);
			}
			
			for(CmtVO cmt : list) {
				System.out.println(cmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
}