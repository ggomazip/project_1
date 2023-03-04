package com.project.www.Command.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dto.BoardDto;

public class ModifyUpdateCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = BoardDao.getDao();
		BoardDto dto = new BoardDto();
		ArrayList<String> list = new ArrayList<String>();
		int no = Integer.parseInt(request.getParameter("no"));
		String contentsText = request.getParameter("contents");
		String pastHashTagCombination = dao.getHashTagCombinationFromBoard(no);
		
		if (pastHashTagCombination!=".#") { // 기존 글내용에 포함되어 있는 해시태그 기록 삭제.
			String[] hashTagArr = pastHashTagCombination.split("#");
			for (int i = 1; i < hashTagArr.length; i++) {
				dao.deleteFromHashTagTable(hashTagArr[i]);
			}
		}
		
		list = dao.addLinkToContents(contentsText);
		String contents = list.get(0);
		String hashTagCombination = list.get(1);
		
		dto.setNo(no);		
		dto.setContents(contents);
		dto.setContentsText(contentsText);
		dao.modifyDao(dto);
		dao.updateHashTagCombinationToBoardTable(hashTagCombination, no);
		if (hashTagCombination!=".#") {
			String[] hashTagArr = hashTagCombination.split("#");
			for (int i = 1; i < hashTagArr.length; i++) {
				dao.insertIntoHashTagTable(hashTagArr[i]);
				System.out.println(i+" : "+hashTagArr[i]);
			}
		}
	}
}
