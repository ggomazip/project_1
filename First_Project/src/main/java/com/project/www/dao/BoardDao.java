package com.project.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.project.www.dto.BoardDto;
import com.project.www.dto.HashTagDto;
import com.project.www.dto.ReplyDto;

public class BoardDao {
	private static BoardDao dao = new BoardDao();
	private final String CONNECTION_POOL_RESOURCE_NAME = "jdbc/first_project";
	private final String TABLE_NAME = "board";
	private DataSource datasource = null;

	private BoardDao() {
		try {
			Context context = new InitialContext();
			datasource = (DataSource) context.lookup("java:comp/env/" + CONNECTION_POOL_RESOURCE_NAME);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static BoardDao getDao() {
		return dao;
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close(PreparedStatement pstmt, Connection conn) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getListNum(String sqlOption) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(no) from " + TABLE_NAME + " " + sqlOption;
		int num = 0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = Integer.parseInt(rs.getString("count(no)"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return num;
	}

	public int postingNum(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(no) from " + TABLE_NAME + " where id=?";
		int num = 0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = Integer.parseInt(rs.getString("count(no)"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return num;
	}

	public ArrayList<BoardDto> getList(String userId, int listSize, int offSet) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		String sql = "select * from " + TABLE_NAME + " where id='" + userId + "' order by no desc limit " + listSize
				+ " offset " + offSet;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDto dto = new BoardDto();
				MemberDao memDao = MemberDao.getDao();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setContents(rs.getString("contents"));
				dto.setContentsTextLengh(rs.getString("contentsText").length());
				dto.setWtime(rs.getString("wtime"));
				dto.setFilePath(rs.getString("filePath"));
				dto.setFileName(rs.getString("fileName"));
				dto.setFileSrc(rs.getString("fileSrc"));
				dto.setHit(rs.getInt("hit"));
				dto.setGoodHit(rs.getInt("goodHit"));
				dto.setBadHit(rs.getInt("badHit"));
				dto.setReplyCnt(countReply(dto.getNo()));
				dto.setProfileImgSrc(memDao.getProfileImgSrc(rs.getString("id")));
				dto.setIsGoodHit(isAlreadyGoodHit(rs.getInt("no"),userId));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return list;
	}

	public ArrayList<BoardDto> getBoardList(String id, int listSize, int offSet) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		String sql = "select * from " + TABLE_NAME + " order by no desc limit " + listSize + " offset " + offSet;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDto dto = new BoardDto();
				MemberDao memDao = MemberDao.getDao();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setContents(rs.getString("contents"));
				dto.setContentsTextLengh(rs.getString("contentsText").length());
				dto.setWtime(rs.getString("wtime"));
				dto.setFilePath(rs.getString("filePath"));
				dto.setFileName(rs.getString("fileName"));
				dto.setFileSrc(rs.getString("fileSrc"));
				dto.setHit(rs.getInt("hit"));
				dto.setGoodHit(rs.getInt("goodHit"));
				dto.setBadHit(rs.getInt("badHit"));
				dto.setReplyCnt(countReply(dto.getNo()));
				dto.setProfileImgSrc(memDao.getProfileImgSrc(rs.getString("id")));
				dto.setIsGoodHit(isAlreadyGoodHit(rs.getInt("no"),id));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return list;
	}
	

	public ArrayList<HashTagDto> getHashTagList(String hashTag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<HashTagDto> list = new ArrayList<HashTagDto>();
		String sql = "select * from hashTag where hashTag like '%" + hashTag + "%' escape '!' order by hashTag asc ";
		try	{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HashTagDto dto = new HashTagDto();
				dto.setHashTag(rs.getString("hashTag"));
				dto.setCount(rs.getInt("count"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return list;
	}

	public ArrayList<BoardDto> getHashTagSearchList(String id, String hashTag, int listSize, int offSet) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		System.out.println("dao 내부" + hashTag);
			String sql = "select * from " + TABLE_NAME + " where hashTagCombination like '%#"+hashTag+"#%' escape '!' order by no desc limit " + listSize + " offset " + offSet;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDto dto = new BoardDto();
				MemberDao memDao = MemberDao.getDao();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setContents(rs.getString("contents"));
				dto.setContentsTextLengh(rs.getString("contentsText").length());
				dto.setWtime(rs.getString("wtime"));
				dto.setFilePath(rs.getString("filePath"));
				dto.setFileName(rs.getString("fileName"));
				dto.setFileSrc(rs.getString("fileSrc"));
				dto.setHit(rs.getInt("hit"));
				dto.setGoodHit(rs.getInt("goodHit"));
				dto.setBadHit(rs.getInt("badHit"));
				dto.setReplyCnt(countReply(dto.getNo()));
				dto.setProfileImgSrc(memDao.getProfileImgSrc(rs.getString("id")));
				dto.setIsGoodHit(isAlreadyGoodHit(rs.getInt("no"), id));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return list;
	}

	public ArrayList<BoardDto> getMainList(String id, String whereSql, int limit, int offset) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		String sql = "select * from " + TABLE_NAME + " " + whereSql + "order by no desc limit " + limit + " offset "
				+ offset;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDto dto = new BoardDto();
				MemberDao memDao = MemberDao.getDao();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setContents(rs.getString("contents"));
				dto.setContentsTextLengh(rs.getString("contentsText").length());
				dto.setWtime(rs.getString("wtime"));
				dto.setFilePath(rs.getString("filePath"));
				dto.setFileName(rs.getString("fileName"));
				dto.setFileSrc(rs.getString("fileSrc"));
				dto.setHit(rs.getInt("hit"));
				dto.setGoodHit(rs.getInt("goodHit"));
				dto.setBadHit(rs.getInt("badHit"));
				dto.setReplyCnt(countReply(dto.getNo()));
				dto.setProfileImgSrc(memDao.getProfileImgSrc(rs.getString("id")));
				dto.setIsFollow(memDao.isFollow(id, rs.getString("id")));
				dto.setIsGoodHit(isAlreadyGoodHit(rs.getInt("no"), id));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return list;
	}

	public BoardDto getView(String id, int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto dto = new BoardDto();
		String sql = "select * from " + TABLE_NAME + " where no=" + no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				MemberDao memDao = MemberDao.getDao();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setContents(rs.getString("contents"));
				dto.setContentsText(rs.getString("contentsText"));
				dto.setContentsTextLengh(rs.getString("contentsText").length());
				dto.setWtime(rs.getString("wtime"));
				dto.setFilePath(rs.getString("filePath"));
				dto.setFileName(rs.getString("fileName"));
				dto.setFileSrc(rs.getString("fileSrc"));
				dto.setHit(rs.getInt("hit"));
				dto.setGoodHit(rs.getInt("goodHit"));
				dto.setBadHit(rs.getInt("badHit"));
				dto.setReplyCnt(countReply(dto.getNo()));
				dto.setProfileImgSrc(memDao.getProfileImgSrc(rs.getString("id")));
				dto.setIsFollow(memDao.isFollow(id, rs.getString("id")));
				dto.setIsGoodHit(isAlreadyGoodHit(rs.getInt("no"), id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return dto;
	}

	public void updateHit(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update " + TABLE_NAME + " set hit=hit+1 where no=" + no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}	

	public void updateId(String newId, String oldId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update " + TABLE_NAME + " set id=? where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newId);
			pstmt.setString(2, oldId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}

	public int getMaxNo(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int groupNum = 0;
		String getMaxNoSql = "select max(no) from " + TABLE_NAME + " where id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(getMaxNoSql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				groupNum = rs.getInt("max(no)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return groupNum;
	}

	public void updateStepNum(int groupNum, int stepNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update " + TABLE_NAME + " set stepNum=stepNum+1 where groupNum=? and stepNum>?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			pstmt.setInt(2, stepNum);
			int result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}

	public void writeDao(BoardDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into " + TABLE_NAME
				+ "(id, contents, contentsText, filePath, fileName, fileSrc, hashTagCombination) values(?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getContents());
			pstmt.setString(3, dto.getContentsText());
			pstmt.setString(4, dto.getFilePath());
			pstmt.setString(5, dto.getFileName());
			pstmt.setString(6, dto.getFileSrc());
			pstmt.setString(7, dto.getHashTagCombination());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	

	public void modifyDao(BoardDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update " + TABLE_NAME + " set contents=?, contentsText=? where no=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getContents());
			pstmt.setString(2, dto.getContentsText());
			pstmt.setInt(3, dto.getNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}

	/*
	 * public void replyDao(BoardDto dto) { 나중에 뎃글용으로 수정. Connection conn = null;
	 * PreparedStatement pstmt = null; int result = 0; String sql =
	 * "insert into mvcboard(name, title, contents, groupNum, stepNum, indentNum) values(?, ?, ?, ?, ?, ?)"
	 * ; try { conn = getConnection(); pstmt= conn.prepareStatement(sql);
	 * pstmt.setString(1, dto.getName()); pstmt.setString(2, dto.getTitle());
	 * pstmt.setString(3, dto.getContents()); pstmt.setInt(4, dto.getGroupNum());
	 * pstmt.setInt(5, (dto.getStepNum()+1)); pstmt.setInt(6,
	 * (dto.getIndentNum()+1)); updateStepNum(dto.getGroupNum(), dto.getStepNum());
	 * result = pstmt.executeUpdate(); } catch (SQLException e) {
	 * e.printStackTrace(); }finally { close(pstmt, conn); } }
	 */

	public void deleteDao(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from " + TABLE_NAME + " where no=" + no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	public void deleteWIthReplyTable(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from reply where no=" + no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	public void deleteWIthGoodTable(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from good where no=" + no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	public void deleteWIthBadTable(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from bad where no=" + no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	
	public ArrayList<String> addLinkToContents(String contentsText) {
		ArrayList<String> list = new ArrayList<String>();
		String contents = "";
		String hashTagCombination = ".#";
		int hash_idx = 0;
		int sub_start = 0;
		int idx_end = 0;
		int isContainHashTag = contentsText.indexOf('#');
		if (isContainHashTag < 0) {
			contents = contentsText;
		} else {
			while (hash_idx > -1 || idx_end<contentsText.length()) {
				hash_idx = contentsText.indexOf('#', idx_end);
				if (hash_idx < 0) {
					contents += contentsText.substring(sub_start, contentsText.length());
					break;
				}
				idx_end = contentsText.indexOf('#', hash_idx + 1);
				contents += contentsText.substring(sub_start, hash_idx);
				if (idx_end < 0) { // 뒤에 #이 더 없는 경우
					idx_end = contentsText.length();
				}
				if (idx_end - hash_idx > 1) {
					String hashTag_text = "";
					for (int i = hash_idx + 1; i < idx_end; i++) { // hash_idx+1 로 설정해야 정상동작!!!
						char testCh = contentsText.charAt(i);
						boolean flag = (testCh >= 1 && testCh <= 47) || (testCh >= 58 && testCh <= 64)
								|| (testCh >= 91 && testCh <= 94) || testCh == 96 || (testCh >= 123 && testCh <= 127);
						sub_start = i;
						if (flag) {
							break; // 해시태그안에 쓸 수 없는 문자가 들어있는 경우
						} else {
							hashTag_text += testCh;
							sub_start++;
						}
					}
					if (hashTag_text.length() > 0) {
						contents += "<a class='hashTag' href='BoardSearch.do?hashTag=" + hashTag_text + "'>#"
								+ hashTag_text + "</a>";
						if (hashTagCombination.contains("#" + hashTag_text + "#")) {
							// 해시태그 중복인지 확인 후 중복이면 추가 x
						} else {
							hashTagCombination += hashTag_text.toLowerCase() + "#";
						}
					} else {
						contents += "#";
					}
				} else {
					sub_start = idx_end;
					contents += "#";
					// System.out.println("디버깅 "+contents);
				}
			}
			hashTagCombination = hashTagCombination.substring(0, hashTagCombination.length());
		}
		list.add(contents);
		list.add(hashTagCombination);
		System.out.println(contents);
		System.out.println(hashTagCombination);
		return list;
	}

	public String changeSearchText(String search) {
		String newSearch = "";
		int idx = 0;
		int sub_start = 0;
		int idx_end = 0;
		int isContainUnderbar = search.indexOf('_');
		if (isContainUnderbar < 0) {
			newSearch = search;
		} else {
			while (idx > -1 || idx_end<search.length()) {
				idx = search.indexOf('_', idx_end);
				if (idx < 0) {
					newSearch += search.substring(sub_start, search.length());
					break;
				}
				idx_end = search.indexOf('_', idx + 1);
				newSearch += search.substring(sub_start, idx);
				if (idx_end < 0) { // 뒤에 _이 더 없는 경우
					idx_end = search.length();
				}
				newSearch += "!"+search.substring(idx,idx_end);
				sub_start=idx_end;
			}
		}
		return newSearch;
	}
	
	
	public int checkHashTagTable(String hashTag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select count from hashTag where hashTag=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hashTag);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
		return result;
	}

	public void insertIntoHashTagTable(String hashTag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		int checkHashtagCount = checkHashTagTable(hashTag);
		String sql = "";
		if (checkHashtagCount < 1) {
			sql = "insert into hashTag(hashTag,count) values(?,1)";
		} else {
			updateFromHashTagTable(hashTag);
			return;
		}
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hashTag);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}

	public void updateFromHashTagTable(String hashTag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update hashTag set count = count+1 where hashTag = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hashTag);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}

	public void deleteFromHashTagTable(String hashTag) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		int checkHashtagCount = checkHashTagTable(hashTag);
		String sql = "";
		if (checkHashtagCount > 1) {
			sql = "update hashTag set count = count-1 where hashTag = ?";
		} else {
			sql = "delete from hashtag where hashTag = ?";
		}
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hashTag);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}

	public void updateHashTagCombinationToBoardTable(String hashTagCombination, int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update board set hashTagCombination = ? where no=" + no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hashTagCombination);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}

	public String getHashTagCombinationFromBoard(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		String sql = "select hashTagCombination from board where no=" + no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getString("hashTagCombination");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
		return result;
	}
	
	public void plusGoodHit(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update board set goodHit = goodHit+1 where no="+ no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	
	public void minusGoodHit(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update board set goodHit = goodHit-1 where no="+ no;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	public void insertGoodHitId(int no, String goodHitId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into good(no, id) values(?, ?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, goodHitId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	public void deleteGoodHitId(int no, String goodHitId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from good where no=? and id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, goodHitId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	
	public boolean isAlreadyGoodHit(int no, String goodHitId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		String sql = "select * from good where no=? and id=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, goodHitId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}return result;
	}
	
	public int countGoodHit(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select count(id) from good where no=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("count(id)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}return result;
	}
	public void insertReply(int no, String id, String reply) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into reply(no, id, reply) values(?, ?, ?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, id);
			pstmt.setString(3, reply);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	public ArrayList<ReplyDto> getReplyList(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDao memDao = MemberDao.getDao();
		ArrayList<ReplyDto> list = new ArrayList<ReplyDto>();
		String sql = "select * from reply where no=? order by num asc";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ReplyDto dto = new ReplyDto();
				dto.setNum(rs.getInt("num"));
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setReply(rs.getString("reply"));
				dto.setProfileImgSrc(memDao.getProfileImgSrc(dto.getId()));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return list;
	}
	public void deleteReply(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from reply where num=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	public int countReply(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select count(num) from reply where no=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("count(num)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}return result;
	}
}
