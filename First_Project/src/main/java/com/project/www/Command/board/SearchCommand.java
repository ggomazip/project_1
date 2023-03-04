package com.project.www.Command.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dao.MemberDao;
import com.project.www.dto.BoardDto;
import com.project.www.dto.MemberDto;

public class SearchCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		BoardDao dao = BoardDao.getDao();
		ArrayList<BoardDto> hashTagSearchList = new ArrayList<BoardDto>();
		
		int listSize = 5;
		int offset = 0;
		
		String hashTag = request.getParameter("hashTag");
		request.setAttribute("hashTag", hashTag);

		System.out.println("커맨드 내부"+hashTag);
		hashTag = dao.changeSearchText(hashTag);
		hashTagSearchList = dao.getHashTagSearchList(id, hashTag, listSize, offset);
		request.setAttribute("listSize", hashTagSearchList.size());
		request.setAttribute("list", hashTagSearchList);
		ArrayList<Integer> idx = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			idx.add(i);
		}
		request.setAttribute("idx", idx);
	}
}
