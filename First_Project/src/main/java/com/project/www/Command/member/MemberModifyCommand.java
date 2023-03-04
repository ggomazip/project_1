package com.project.www.Command.member;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.project.www.Command.Command;
import com.project.www.dao.MemberDao;
import com.project.www.dto.MemberDto;

public class MemberModifyCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String oldId = (String) session.getAttribute("id");
		String oldPw = (String) session.getAttribute("pw");
		// request.getSession().getServletContext().getRealPath("/");
		// String path = application.getRealPath("img");
		request.setCharacterEncoding("utf-8");
		MemberDao dao = MemberDao.getDao();
		MemberDto dto = new MemberDto();

		String path = "C:/work/web/First_Project/src/main/webapp/profile_img";
		String email = null;
		String id = null;
		String pw = null;
		String file = null;
		String deleteImg = null;
		String oriFile = "";
		String fileSrc = "";
		int size = 1024 * 1024 * 10; // 10M - 최대 사이즈

		try {
			MultipartRequest multi = new MultipartRequest(request, path, size, "utf-8", new DefaultFileRenamePolicy());

			email = multi.getParameter("email");
			id = multi.getParameter("id");
			pw = multi.getParameter("pw");
			deleteImg = multi.getParameter("deleteImg");

			// System.out.println("email " + email); //디버깅용
			// System.out.println("id " + id); //디버깅용
			// System.out.println("pw " + pw); //디버깅용
			System.out.println(deleteImg);

			Enumeration<String> files = multi.getFileNames(); // 폼 요소 중 input 태그 속성이 file로 된 파라미터의 이름들을 반환
																// upload 된 파일이 없으면 비어있는 Enumeration을 반환
			while (files.hasMoreElements()) {
				String str = files.nextElement();
				file = multi.getFilesystemName(str); // 사용자가 지정해서 서버에 실제로 업로드된 파일명 반환
														// 파일명이 중복되는 경우 변경된 파일명 반환
				oriFile = multi.getOriginalFileName(str); // 사용자가 업로드한 실제 파일명을 반환.
															// 이때의 파일명은 파일 중복을 고려한 파일명 변경 전의 이름을 말한다.
				fileSrc = path.substring(path.lastIndexOf('/') + 1) + "/" + file;
				// System.out.println("저장된 경로 " + path + " 저장된 이름 " + file + " 원본이름 " +
				// oriFile); //디버깅용.
				// System.out.println("파일 경로 " + fileSrc); //디버깅용.
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (deleteImg.equals("deleteImg")) {
			file="profile.png";
			fileSrc="profile_img/profile.png";
		}

		boolean flag = false;
		if (oldPw.equals(pw)) {
			dto.setId(id);
			if (file != null) {
				if (oldId.equals(id)) {
					dto.setProfileImgName(file);
					dto.setProfileImgPath(path);
					dto.setProfileImgSrc(fileSrc);
					session.setAttribute("profileImgSrc", fileSrc);
					dao.modifyDaoOnlyProfileImg(dto, oldId);
				} else {
				dto.setProfileImgName(file);
				dto.setProfileImgPath(path);
				dto.setProfileImgSrc(fileSrc);
				session.setAttribute("profileImgSrc", fileSrc);
				dao.modifyDaoWithProfileImg(dto, oldId);
			}
			} else {
				if (oldId.equals(id)) {
					// 프로필 사진 인풋 없고, 아이디 변동 없을 경유 그냥 흘려보냄.
					System.out.println("유지");
				}else {
					dao.modifyDao(dto, oldId);
				}
			}
			flag = true;
			session.setAttribute("id", id);
			request.setAttribute("viewPage", "member_userProfile.mem");
			request.setAttribute("flag", flag);
		} else {
			request.setAttribute("error", "기존 비밀번호가 일치하지 않습니다.");
			request.setAttribute("viewPage", "error.jsp");
			request.setAttribute("flag", flag);
		}
	}

}
