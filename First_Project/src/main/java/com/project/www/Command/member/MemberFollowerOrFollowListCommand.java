package com.project.www.Command.member;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.dao.MemberDao;
import com.project.www.dto.MemberDto;

public class MemberFollowerOrFollowListCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		MemberDao dao = MemberDao.getDao();
		ArrayList<String> followerList = new ArrayList<String>();
		ArrayList<String> followList = new ArrayList<String>();
		ArrayList<MemberDto> list = new ArrayList<MemberDto>();
		String listOffer = request.getParameter("listOffer");
		String userId = request.getParameter("userId");
		String sessionId = (String) session.getAttribute("id");

		if (listOffer.equals("follower")) {
			followerList = dao.getFollower(userId);
			for (String follower : followerList) {
				MemberDto dto = new MemberDto();
				dto.setProfileImgSrc(dao.getProfileImgSrc(follower));
				dto.setId(follower);
				if (sessionId.equals(follower)) {
					dto.setIsFollow(null);
				}else {
					dto.setIsFollow(dao.isFollow(sessionId, follower));
				}
				list.add(dto);
			}
		} else {
			followList = dao.getFollow(userId);
			for (String follow : followList) {
				MemberDto dto = new MemberDto();
				dto.setProfileImgSrc(dao.getProfileImgSrc(follow));
				dto.setId(follow);
				if (sessionId.equals(follow)) {
					dto.setIsFollow(null);
				}else {
					dto.setIsFollow(dao.isFollow(sessionId, follow));
				}
				list.add(dto);
			}
			request.setAttribute("follow", "follow");
		}

		request.setAttribute("list", list);
	}

}
