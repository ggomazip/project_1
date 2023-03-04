package com.project.www.Command.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dto.BoardDto;

public class ReplyDeleteCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession(); 
		BoardDao dao = BoardDao.getDao();
		String id = (String)session.getAttribute("id");
		String numStr = request.getParameter("num");
		int num = Integer.parseInt(numStr);
		System.out.println(num);
		dao.deleteReply(num);
		}
}
