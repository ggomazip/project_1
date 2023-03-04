package com.project.www.Command.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;

public class UpdateHitCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = BoardDao.getDao();
		int no = Integer.parseInt(request.getParameter("no"));
		dao.updateHit(no);
	}
}
