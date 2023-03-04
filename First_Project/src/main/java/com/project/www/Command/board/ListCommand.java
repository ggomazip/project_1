package com.project.www.Command.board;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dto.BoardDto;

public class ListCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		BoardDao dao = BoardDao.getDao();		
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		
		
		int listSizeOfPage = 15;
		int offSet =0;
		String addStr = request.getParameter("add");
		String offSetStr = request.getParameter("listSize");
		if (addStr!=null&&offSetStr!=null) {
			listSizeOfPage += Integer.parseInt(addStr);
			offSet += Integer.parseInt(offSetStr);
		}
		
		list = dao.getBoardList(id, listSizeOfPage, offSet);
		request.setAttribute("list", list);
		System.out.println(request.getParameter("search"));
		ArrayList<Integer> idx = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			idx.add(i);
		}
		request.setAttribute("idx", idx);
	}

}
