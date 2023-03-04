package com.project.www.Command.member;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.MemberDao;
import com.project.www.dto.MemberDto;

public class LoginCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		String inputId = request.getParameter("id");
		String inputPw = request.getParameter("pw");
		boolean flag = false;

		if (request.getParameter("remId") != null) {
			Cookie cookie = new Cookie("remId", inputId);
			cookie.setMaxAge(60 * 60 * 24 * 365);
			response.addCookie(cookie); // 쿠키 추가
		} else {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				String str = cookies[i].getName();

				if (str.equals("remId")) {
					cookies[i].setMaxAge(0); // 0 으로 max age 를 설정해줘서 삭제해준다.
					response.addCookie(cookies[i]);
					break;
				}
			}
		}
		MemberDao dao = MemberDao.getDao();
		MemberDto dto = new MemberDto();
		dto.setId(inputId);
		dto.setPw(inputPw);
		
		if(dao.loginDao(dto)){
			flag = true;
			String profileImgSrc = dao.getProfileImgSrc(inputId);
			String email = dao.getProfileImgSrc(inputId);
			session.setAttribute("profileImgSrc", profileImgSrc);
			session.setAttribute("email", email);
			session.setAttribute("id", inputId);
			session.setAttribute("pw", inputPw);
			request.setAttribute("viewPage", "member_userProfile.mem?userId="+inputId);
			request.setAttribute("flag", flag);
		}else{
			request.setAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
			request.setAttribute("viewPage", "error.jsp");
			request.setAttribute("flag", flag);
			//javax.servlet.RequestDispatcher rd = request.getRequestDispatcher(loginError.jsp);
			//rd.forward(request, response);
		}
	}

}
