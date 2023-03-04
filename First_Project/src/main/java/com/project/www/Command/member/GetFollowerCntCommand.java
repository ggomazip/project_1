package com.project.www.Command.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dao.MemberDao;

public class GetFollowerCntCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDao dao = MemberDao.getDao();
		String userId = request.getParameter("userId");
		int followerCnt = dao.followerCnt(userId);
		request.setAttribute("followerCnt", followerCnt);

	}

}
