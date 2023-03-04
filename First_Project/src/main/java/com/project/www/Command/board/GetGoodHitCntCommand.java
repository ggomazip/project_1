package com.project.www.Command.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;

public class GetGoodHitCntCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = BoardDao.getDao();
		String noStr = request.getParameter("no");
		int no = Integer.parseInt(noStr);
		int goodHitCnt = dao.countGoodHit(no);
		System.out.println("좋아요 수 디버깅");
		System.out.println("좋아요 수:"+goodHitCnt);
		request.setAttribute("goodHitCnt", goodHitCnt);
	}

}
