package com.project.www.Command.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dao.MemberDao;
import com.project.www.dto.BoardDto;
import com.project.www.dto.MemberDto;

public class MoreListCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		BoardDao dao = BoardDao.getDao();		
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		
		String userId = request.getParameter("userId");
		String id = (String)session.getAttribute("id");
		
		int listSizeOfPage = 0;
		int offSet =0;
		String addStr = request.getParameter("add");
		String offSetStr = request.getParameter("offSet");
		String from = request.getParameter("from");
		
		System.out.println(offSetStr);
		if (addStr!=null&&offSetStr!=null) {
			listSizeOfPage += Integer.parseInt(addStr);
			offSet += Integer.parseInt(offSetStr);
			System.out.println("리스트 사이즈:"+listSizeOfPage);
			System.out.println("오프셋"+offSet);
		}
		if (from.equals("main_feed")) { // 메인피드에서 더보기 (나의 팔로우 게시글 더보기)
			MemberDao memDao = MemberDao.getDao();
			ArrayList<String> followlist = new ArrayList<String>();
			String whereSql = "where ";
			followlist = memDao.getFollow(id);
			for (String follow : followlist) {
				whereSql += "id='" + follow + "' or ";
			}
			whereSql += "id='" + id + "'";
			
			list = dao.getMainList(id, whereSql, listSizeOfPage, offSet);
		}else if(from.equals("userProfile")){ // 유저 프로필 페이지에서 더보기
			String whereSql = "where id='"+userId+"' ";
			list = dao.getMainList(id, whereSql, listSizeOfPage, offSet);
		}else {
			String hashTag= request.getParameter("hashTag");
			if (hashTag!=null) { // 해시태그 검색으로 글어간 리스트에서 더보기
				hashTag = dao.changeSearchText(hashTag);
				list = dao.getHashTagSearchList(id, hashTag, listSizeOfPage, offSet);
			}else { // 전체 리스트 보기 화면에서 더보기
				list = dao.getBoardList(id, listSizeOfPage, offSet);
			}
		}
		
		JSONArray jArray = new JSONArray();
		for (BoardDto boardDto : list) {
			JSONObject obj = new JSONObject();
			obj.put("no", boardDto.getNo());
			obj.put("id", boardDto.getId());
			obj.put("contents", boardDto.getContents());
			obj.put("fileSrc", boardDto.getFileSrc());
			obj.put("goodHit", boardDto.getGoodHit());
			obj.put("profileImgSrc", boardDto.getProfileImgSrc());
			obj.put("isFollow", boardDto.getIsFollow());
			obj.put("isGoodHit", boardDto.getIsGoodHit());
			obj.put("replyCnt", boardDto.getReplyCnt());
			jArray.add(obj);
			//System.out.println("여기?2"+obj.toJSONString());
		}
		out.write(jArray.toJSONString());
		out.flush();
		out.close();
		//System.out.println(jArray);
	}
}
