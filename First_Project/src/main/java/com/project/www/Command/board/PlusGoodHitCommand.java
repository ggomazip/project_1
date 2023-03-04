package com.project.www.Command.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;
import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;

public class PlusGoodHitCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		BoardDao dao = BoardDao.getDao();
		
		String id = (String)session.getAttribute("id");
		String noStr = request.getParameter("no");
		int no = Integer.parseInt(noStr);
		
		dao.insertGoodHitId(no, id);
		dao.plusGoodHit(no);
	}
}
