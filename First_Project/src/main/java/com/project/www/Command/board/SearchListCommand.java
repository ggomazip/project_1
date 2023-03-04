package com.project.www.Command.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dao.MemberDao;
import com.project.www.dto.HashTagDto;
import com.project.www.dto.MemberDto;

public class SearchListCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		BoardDao dao = BoardDao.getDao();
		MemberDao memDao = MemberDao.getDao();
		ArrayList<HashTagDto> hashTagSearchList = new ArrayList<HashTagDto>();
		ArrayList<MemberDto> idSearchList = new ArrayList<MemberDto>();

		int listSize = 5;
		int offset = 0;

		String search = request.getParameter("search");
		request.setAttribute("search", search);
		if (search==null || search=="") {
			request.setAttribute("id_listSize", 0);
			request.setAttribute("hashTag_listSize", 0);
			return;
		}
		
		if (search.charAt(0)=='@' || search.charAt(0)=='#' ) {
			String tap = search.substring(0, 1);
			request.setAttribute("tap", tap);
			search=search.substring(1);
		}
		
		search = dao.changeSearchText(search); // 쿼리문 like 에서 _ 언더바 앞에 escape 문자인 ! 넣어주는 메소드
		String hashTag =search; 
		
		idSearchList = memDao.getIdSearchList(search);
		request.setAttribute("id_list", idSearchList);
		request.setAttribute("id_listSize", idSearchList.size());
		
		hashTagSearchList = dao.getHashTagList(hashTag);
		request.setAttribute("hashTag_list", hashTagSearchList);
		request.setAttribute("hashTag_listSize", hashTagSearchList.size());
	}

}
