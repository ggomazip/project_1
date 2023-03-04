package com.project.www.Command.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dto.BoardDto;

public class WriteCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.getSession().getServletContext().getRealPath("/");
		//String path = application.getRealPath("img");
		request.setCharacterEncoding("utf-8");
		BoardDao dao = BoardDao.getDao();
		BoardDto dto = new BoardDto();
		HttpSession session = request.getSession();
		
		String path = "C:/work/web/First_Project/src/main/webapp/board_img";
		String contents ="";
		String contentsText ="";
		String hashTagCombination ="";
		String file = "";
		String oriFile = "";
		String fileSrc ="";
		int size = 1024 * 1024 * 10; //10M - 최대 사이즈
		
		try{
			MultipartRequest multi = new MultipartRequest(request, path, size, "utf-8", new DefaultFileRenamePolicy());
			
			contentsText = multi.getParameter("contents");
			
			System.out.println("내용 " + contents); //디버깅용
			
			Enumeration<String> files = multi.getFileNames();		// 폼 요소 중 input 태그 속성이 file로 된 파라미터의 이름들을 반환
															// upload 된 파일이 없으면 비어있는 Enumeration을 반환
			while(files.hasMoreElements())
			{
				String str = files.nextElement();
				file = multi.getFilesystemName(str);			// 사용자가 지정해서 서버에 실제로 업로드된 파일명 반환
																// 파일명이 중복되는 경우 변경된 파일명 반환
				oriFile = multi.getOriginalFileName(str);		// 사용자가 업로드한 실제 파일명을 반환.
																// 이때의 파일명은 파일 중복을 고려한 파일명 변경 전의 이름을 말한다.
				fileSrc = path.substring(path.lastIndexOf('/')+1)+"/"+file;
				System.out.println("저장된 경로 " + path + " 저장된 이름 " + file + " 원본이름 " + oriFile); //디버깅용.
				System.out.println("파일 경로 " + fileSrc); //디버깅용.
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<String> list = new ArrayList<String>();
		list =  dao.addLinkToContents(contentsText);
		contents =	list.get(0);
		hashTagCombination = list.get(1); //
		if (hashTagCombination!=".#") {
			String[] hashTagArr = hashTagCombination.split("#");
			for (int i = 1; i < hashTagArr.length; i++) { // i=0 일때는 ""이므로 빼준다.
				dao.insertIntoHashTagTable(hashTagArr[i]);
				System.out.println(hashTagArr[i]);
			}
		}
		
		dto.setId((String)session.getAttribute("id"));
		dto.setContents(contents);
		dto.setContentsText(contentsText);
		dto.setHashTagCombination(hashTagCombination);
		dto.setFilePath(path);
		dto.setFileName(file);
		dto.setFileSrc(fileSrc);
		dao.writeDao(dto);
	}
}
