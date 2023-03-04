package com.project.www.Command.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;

public class DeleteCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = BoardDao.getDao();
		int no = Integer.parseInt(request.getParameter("no"));
		//1. 게시물에 들어있는 해시태그들 해시태그 테이블에서 삭제.
		String pastHashTagCombination = dao.getHashTagCombinationFromBoard(no);
		
		if (pastHashTagCombination!=".#") { // 기존 글내용에 포함되어 있는 해시태그 기록 삭제.
			String[] hashTagArr = pastHashTagCombination.split("#");
			for (int i = 1; i < hashTagArr.length; i++) {
				dao.deleteFromHashTagTable(hashTagArr[i]);
			}
		}
		//2. 게시물 좋아요 기록 삭제.
		dao.deleteWIthGoodTable(no);
		//3. 게시물 댓글 기록 삭제.
		dao.deleteWIthReplyTable(no);
		//4. 글삭제.
		dao.deleteDao(no);
	}

}
