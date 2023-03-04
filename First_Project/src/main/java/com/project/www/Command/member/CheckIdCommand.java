package com.project.www.Command.member;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.MemberDao;
import com.project.www.dto.MemberDto;

public class CheckIdCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String sessionId = (String)session.getAttribute("id");
		MemberDao dao = MemberDao.getDao();
		boolean flag =true;
		int result = 0;
		String id = request.getParameter("id");
		if (id==null||id.length()==0) {
			result=2;
		} else if(id.length()<4){
			result=0;
		} else if(sessionId!=null&&sessionId.equals(id)) {
			result=7;
		} else {
			char[] idArray = id.toCharArray();
			for (int i = 0; i < idArray.length; i++) {
				flag = (idArray[i] >= 'a') && (idArray[i] <= 'z') 
						|| (idArray[i] >= 'A') && (idArray[i] <= 'Z') 
						|| (idArray[i] >= '0') && (idArray[i] <= '9') || (idArray[i] == '_');
				if (!flag) {
					break;
				}
			}
			if (flag) {
				result = dao.checkId(id);
			}else {
				result = 0;
			}
		}
		
		
		request.setAttribute("result", result);
		//request.setAttribute("id", id);
	}
}
