package com.project.www.Command.member;

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

public class MemberUnfollowCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		
		BoardDao dao = BoardDao.getDao();
		MemberDao memDao = MemberDao.getDao();
		
		String targetId = request.getParameter("user_id");
		String id = (String)session.getAttribute("id");
		
		memDao.removeFollow(id, targetId);
		memDao.removeFollower(targetId, id);
		
		/*boolean isFollow = memDao.isFollow(id, targetId);
		request.setAttribute("isFollow", isFollow);
		
		String main_feed = request.getParameter("main_feed");
		if (main_feed!=null) {
			ArrayList<BoardDto> list = new ArrayList<BoardDto>();
			ArrayList<String> followlist = new ArrayList<String>();
			String whereSql = "where ";
			followlist = memDao.getFollow(id);
			for (String follow : followlist) {
				whereSql += "id='" + follow + "' or ";
			}
			whereSql += "id='" + id + "'";
			list = dao.getMainList(whereSql, 10, 0);
			request.setAttribute("list", list);
		}*/
	}
}
