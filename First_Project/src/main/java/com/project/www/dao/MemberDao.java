package com.project.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.websocket.Session;

import com.project.www.dto.BoardDto;
import com.project.www.dto.MemberDto;

public class MemberDao {
	private static MemberDao dao = new MemberDao();
	private final String CONNECTION_POOL_RESOURCE_NAME ="jdbc/first_project";
	private final String TABLE_NAME = "member";
	private DataSource datasource = null;
	
	private MemberDao() {
		try {
			Context context = new InitialContext();
			datasource = (DataSource)context.lookup("java:comp/env/"+CONNECTION_POOL_RESOURCE_NAME);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static MemberDao getDao() {
		return dao;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}return conn;
	}
	
	public void close(ResultSet rs,PreparedStatement pstmt, Connection conn) {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void close(PreparedStatement pstmt, Connection conn) {
		try {
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean loginDao(MemberDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		String sql = "select id, pw from "+TABLE_NAME+" where id=? and pw=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());			
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = true;
			}else{
				result = false;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return result;
	}
	
	public int checkId(String id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select * from "+TABLE_NAME+" where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result=0;
			}else result=1;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return result;
	}
	
	public int checkEmail(String email){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select * from "+TABLE_NAME+" where email=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result=0;
			}else result=1;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return result;
	}
	
	
	public MemberDto view(String id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDto dto = new MemberDto();
		String sql = "select * from "+TABLE_NAME+" where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setEmail(rs.getString("email"));
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setProfileImgPath(rs.getString("profileImgPath"));
				dto.setProfileImgName(rs.getString("profileImgName"));
				dto.setProfileImgSrc(rs.getString("profileImgSrc"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return dto;
	}
	public String getProfileImgSrc(String id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String profileImgSrc =null;
		String sql = "select profileImgSrc from "+TABLE_NAME+" where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				profileImgSrc = rs.getString("profileImgSrc");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return profileImgSrc;
	}
	public String getEamil(String id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String email =null;
		String sql = "select email from "+TABLE_NAME+" where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				email = rs.getString("email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return email;
	}
	public ArrayList<MemberDto> getIdSearchList(String searchId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberDto> list = new ArrayList<MemberDto>();
		String sql="select id, profileImgSrc from " + TABLE_NAME + " where id like '%"+searchId+"%' order by id asc";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					MemberDto dto = new MemberDto();
					dto.setId(rs.getString("id"));
					dto.setProfileImgSrc(rs.getString("profileImgSrc"));
					list.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return list;
	}
	public int followerCnt(String id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int followerCnt=0;
		String sql = "select count(follower) from follower where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				followerCnt = rs.getInt("count(follower)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return followerCnt;
	}
	
	public int followCnt(String id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int followCnt=0;
		String sql = "select count(follow) from follow where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				followCnt = rs.getInt("count(follow)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return followCnt;
	}
	public boolean isFollow(String id, String targetId){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result=false;
		String sql = "select count(id) from follow where id=? and follow=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, targetId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				 if (rs.getInt("count(id)")>0) {
					result = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return result;
	}
	public ArrayList<String> getFollower(String id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select follower from follower where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("follower"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return list;
	}
	public ArrayList<String> getFollow(String id){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select follow from follow where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("follow"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);
		}return list;
	}	
	
	public void addFollow(String id, String targetId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;		
		String sql = "insert into follow(id, follow) values(?,?)";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, id); //내가  팔로우하는 거니까 여기
			pstmt.setString(2, targetId); // 내가 팔로잉 한 상대 아이디.
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}
	}
	public void addFollower(String targetId, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;		
		String sql = "insert into follower(id, follower) values(?,?)";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, targetId); // 내가 팔로우 하는 상대의 아이디
			pstmt.setString(2, id); // 내가 팔로워니까 여기에 넣음
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}
	}
	public void removeFollow(String id, String targetId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;		
		String sql = "delete from follow where id=? and follow=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, targetId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}
	}
	public void removeFollower(String targetId, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;		
		String sql = "delete from follower where id=? and follower=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, targetId);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}
	}
	
	public int register(MemberDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;		
		String sql = "insert into "+TABLE_NAME+" values(?, ?, ?, ?, ?, ?)";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getPw());
			pstmt.setString(4, dto.getProfileImgPath());
			pstmt.setString(5, dto.getProfileImgName());
			pstmt.setString(6, dto.getProfileImgSrc());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		} return result;
	}
	
	public void modifyIdOfGoodTable(String oldId, String newId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDao boardDao = BoardDao.getDao();
		int result = 0;		
		String sql = "update good set id=? where id=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, newId);
			pstmt.setString(2, oldId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	public void modifyIdOfBadTable(String oldId, String newId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDao boardDao = BoardDao.getDao();
		int result = 0;		
		String sql = "update bad set id=? where id=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, newId);
			pstmt.setString(2, oldId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	public void modifyIdOfReplyTable(String oldId, String newId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDao boardDao = BoardDao.getDao();
		int result = 0;		
		String sql = "update reply set id=? where id=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, newId);
			pstmt.setString(2, oldId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	public void modifyIdOfFollowTable(String oldId, String newId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDao boardDao = BoardDao.getDao();
		int result = 0;		
		String sql = "update follow set id=? where id=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, newId);
			pstmt.setString(2, oldId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	public void modifyFollowOfFollowTable(String oldId, String newId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDao boardDao = BoardDao.getDao();
		int result = 0;		
		String sql = "update follow set follow=? where follow=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, newId);
			pstmt.setString(2, oldId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	public void modifyIdOfFollowerTable(String oldId, String newId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDao boardDao = BoardDao.getDao();
		int result = 0;		
		String sql = "update follower set id=? where id=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, newId);
			pstmt.setString(2, oldId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	
	public void modifyFollowerOfFollowerTable(String oldId, String newId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDao boardDao = BoardDao.getDao();
		int result = 0;		
		String sql = "update follower set follower=? where follower=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, newId);
			pstmt.setString(2, oldId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	
	public void modifyIdOfTables(String oldId, String newId) {
		modifyIdOfGoodTable(oldId, newId);
		//modifyIdOfBadTable(oldId, newId);
		modifyIdOfReplyTable(oldId, newId);
		modifyIdOfFollowTable(oldId, newId);
		modifyFollowOfFollowTable(oldId, newId);
		modifyIdOfFollowerTable(oldId, newId);
		modifyFollowerOfFollowerTable(oldId, newId);
	}
	
	public void modifyDao(MemberDto dto, String oldId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDao boardDao = BoardDao.getDao();
		String newId = dto.getId();
		int result = 0;		
		String sql = "update "+TABLE_NAME+" set id=? where id=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, oldId);
			result = pstmt.executeUpdate();
			boardDao.updateId(newId, oldId);
			modifyIdOfTables(oldId, newId);
			
		} catch (SQLException e ) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	public void modifyDaoWithProfileImg(MemberDto dto, String oldId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDao boardDao = BoardDao.getDao();
		String newId = dto.getId();
		int result = 0;		
		String sql = "update "+TABLE_NAME+" set id=?, profileImgPath=?, profileImgName=?, profileImgSrc=? where id=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getProfileImgPath());
			pstmt.setString(3, dto.getProfileImgName());
			pstmt.setString(4, dto.getProfileImgSrc());
			pstmt.setString(5, oldId);
			result = pstmt.executeUpdate();
			boardDao.updateId(newId, oldId);
			modifyIdOfTables(oldId, newId);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	public void modifyDaoOnlyProfileImg(MemberDto dto, String oldId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;		
		String sql = "update "+TABLE_NAME+" set profileImgPath=?, profileImgName=?, profileImgSrc=? where id=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, dto.getProfileImgPath());
			pstmt.setString(2, dto.getProfileImgName());
			pstmt.setString(3, dto.getProfileImgSrc());
			pstmt.setString(4, oldId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	public void changePw(String newPw, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;		
		String sql = "update "+TABLE_NAME+" set pw=? where id=?";
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, newPw);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}		
	}
	
	public void deleteDao(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;		
		String sql = "delete from "+TABLE_NAME+" where id="+id;
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt, conn);
		}
	}
}

