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

public class MainListCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");

		BoardDao dao = BoardDao.getDao();
		MemberDao memDao = MemberDao.getDao();

		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		ArrayList<String> followlist = new ArrayList<String>();
		String whereSql = "where ";
		followlist = memDao.getFollow(id);
		for (String follow : followlist) {
			whereSql += "id='" + follow + "' or ";
		}
		whereSql += "id='" + id + "'";
		list = dao.getMainList(id, whereSql, 5, 0); // 최초 0부터 5개 게시물만 출력
		request.setAttribute("list", list);
	}

}
