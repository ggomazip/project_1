package com.project.www.Command.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.www.Command.Command;
import com.project.www.dao.MemberDao;
import com.project.www.dto.MemberDto;

public class CheckEmailCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
		char[] emailArray = email.toCharArray();
		MemberDao dao = MemberDao.getDao();
		boolean flag =true;
		int result = 0;
		int count =0;
		boolean orderCheck=false;
		orderCheck = (email.indexOf('.')-email.indexOf('@'))>1	&& email.indexOf('@')>0
				&& email.indexOf('.')>0	&& (email.length()-email.indexOf('.'))==4 && (email.lastIndexOf('_')-email.indexOf('@'))<1; 
		if (email==null||email.length()==0) {
			result=2;
		} else if(email.length()<7){
			result=0;
		}
		else {
			for (int i = 0; i < emailArray.length; i++) {
				flag = (emailArray[i] >= 'a') && (emailArray[i] <= 'z') 
						|| (emailArray[i] >= 'A') && (emailArray[i] <= 'Z') 
						|| (emailArray[i] >= '0') && (emailArray[i] <= '9')
						|| (emailArray[i] == '_') || (emailArray[i] == '.') || (emailArray[i] == '@');
				if (!flag) {
					break;
				}
				if((emailArray[i] == '.') || (emailArray[i] == '@')) {
					count++;
				}
			}
			if (flag&&count==2&&orderCheck) {
				result = dao.checkEmail(email);
			}else {
				result = 0;
			}
		}
		request.setAttribute("result", result);
		//request.setAttribute("email", email);
	}

}
