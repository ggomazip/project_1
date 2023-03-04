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
import com.project.www.dto.MemberDto;

public class MemberUserProfileCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		BoardDao dao = BoardDao.getDao();
		MemberDao memDao = MemberDao.getDao();
		MemberDto dto = new MemberDto();
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		String userId = request.getParameter("userId");
		String myId = (String)session.getAttribute("id");
		if (userId==null) {
			userId=myId;
		}
		
		int listSizeOfPage = 12;
		int offSet =0;
		list = dao.getList(userId ,listSizeOfPage, offSet);
		request.setAttribute("list", list);
		
		int posting = dao.postingNum(userId);
		request.setAttribute("posting", posting);
		
		int follower = memDao.followerCnt(userId);
		int follow = memDao.followCnt(userId);
		request.setAttribute("follower", follower);
		request.setAttribute("follow", follow);
		
		dto = memDao.view(userId);
		//System.out.println(dto.getProfileImgSrc()); 디버깅
		request.setAttribute("view", dto);
		
		boolean isFollow = memDao.isFollow(myId, userId);
		request.setAttribute("isFollow", isFollow);
		request.setAttribute("listSize", list.size());
	}
}
