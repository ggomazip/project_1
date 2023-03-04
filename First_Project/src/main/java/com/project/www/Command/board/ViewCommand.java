package com.project.www.Command.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dao.MemberDao;
import com.project.www.dto.BoardDto;

public class ViewCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		BoardDao dao = BoardDao.getDao();
		int no = Integer.parseInt(request.getParameter("no"));
		String id = (String)session.getAttribute("id");
		BoardDto dto = dao.getView(id, no);
		request.setAttribute("view",dto);
	}

}
