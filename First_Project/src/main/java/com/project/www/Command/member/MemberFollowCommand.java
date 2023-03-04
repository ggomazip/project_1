package com.project.www.Command.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.MemberDao;

public class MemberFollowCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		MemberDao dao = MemberDao.getDao();
		String targetId = request.getParameter("user_id");
		String id = (String)session.getAttribute("id");
		dao.addFollow(id, targetId);
		dao.addFollower(targetId, id);
		boolean isFollow = dao.isFollow(id, targetId);
		request.setAttribute("isFollow", isFollow);
	}
}
