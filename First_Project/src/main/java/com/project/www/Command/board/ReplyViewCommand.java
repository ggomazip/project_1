package com.project.www.Command.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.project.www.Command.Command;
import com.project.www.dao.BoardDao;
import com.project.www.dto.BoardDto;
import com.project.www.dto.ReplyDto;

public class ReplyViewCommand implements Command {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		int no = Integer.parseInt(request.getParameter("no"));
		String id = (String)session.getAttribute("id");
		ArrayList<ReplyDto> list = new ArrayList<ReplyDto>();
		BoardDao dao = BoardDao.getDao();
		list = dao.getReplyList(no);
		JSONArray jArray = new JSONArray();
		for (ReplyDto dto : list) {
			JSONObject obj = new JSONObject();
			obj.put("num", dto.getNum());
			obj.put("no", dto.getNo());
			obj.put("id", dto.getId());
			obj.put("reply", dto.getReply());
			obj.put("profileImgSrc", dto.getProfileImgSrc());
			jArray.add(obj);
		}
		out.write(jArray.toJSONString());
		out.flush();
		out.close();
	}
}
