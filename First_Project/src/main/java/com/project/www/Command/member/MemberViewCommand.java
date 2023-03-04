package com.project.www.Command.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.MemberDao;
import com.project.www.dto.MemberDto;

public class MemberViewCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	MemberDao dao = MemberDao.getDao();
	MemberDto dto = new MemberDto();
	HttpSession session = request.getSession();
	String id = (String)session.getAttribute("id");
	dto = dao.view(id);
	request.setAttribute("dto", dto);
	}

}
