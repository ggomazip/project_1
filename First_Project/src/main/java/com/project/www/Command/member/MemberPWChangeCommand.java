package com.project.www.Command.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.MemberDao;
import com.project.www.dto.MemberDto;

public class MemberPWChangeCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("id");
		String sessionPw = (String) session.getAttribute("pw");
		String pastPw = request.getParameter("pastPw");
		String newPw = request.getParameter("pw");
		request.setCharacterEncoding("utf-8");
		MemberDao dao = MemberDao.getDao();
		MemberDto dto = new MemberDto();
		boolean flag = false;
		if (sessionPw.equals(pastPw)) {
			flag=true;
			session.setAttribute("pw", newPw);
			request.setAttribute("viewPage", "member_userProfile.mem");
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("error", "기존 비밀번호가 일치하지 않습니다.");
			request.setAttribute("viewPage", "error.jsp");
			request.setAttribute("flag", flag);
		}
	}

}
