package com.project.www.frontcontroller;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.project.www.Command.Command;
import com.project.www.Command.board.GetGoodHitCntCommand;
import com.project.www.Command.member.CheckIdCommand;
import com.project.www.Command.member.GetFollowerCntCommand;
import com.project.www.Command.member.CheckEmailCommand;
import com.project.www.Command.member.LoginCommand;
import com.project.www.Command.member.LogoutCommand;
import com.project.www.Command.member.MemberFollowCommand;
import com.project.www.Command.member.MemberFollowerOrFollowListCommand;
import com.project.www.Command.member.MemberModifyCommand;
import com.project.www.Command.member.MemberPWChangeCommand;
import com.project.www.Command.member.MemberUnfollowCommand;
import com.project.www.Command.member.MemberUserProfileCommand;
import com.project.www.Command.member.MemberViewCommand;
import com.project.www.Command.member.RegisterCommand;

/**
 * Servlet implementation class memberFrontcontroller
 */
@WebServlet("*.mem")
public class memberFrontcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public memberFrontcontroller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionMem(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionMem(request, response);
	}

	protected void actionMem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String commandName = request.getServletPath();
		String viewPage = "";
		boolean flag = false;
		boolean pass = true;
		Command command = null;
		if (commandName.equals("/member_login.mem")) { // ?????? ?????????
			command = new LoginCommand();
			command.excute(request, response);
			viewPage = (String) request.getAttribute("viewPage");
			flag = (boolean) request.getAttribute("flag");
		} else if (commandName.equals("/member_userProfile.mem")) { // ??????????????????
			command = new MemberUserProfileCommand();
			command.excute(request, response);
			viewPage = "member_userProfile.jsp";
		} else if (commandName.equals("/member_followerList.mem")) { // ?????????,????????? ????????? ?????????
			command = new MemberFollowerOrFollowListCommand();
			command.excute(request, response);
			viewPage = "member_followerOrFollowList.jsp";
		} else if (commandName.equals("/member_follow.mem")) { // ?????? ?????????
			command = new MemberFollowCommand();
			command.excute(request, response);
			out.write(request.getAttribute("result") + "");
			pass= false;
		} else if (commandName.equals("/member_unfollow.mem")) { // ?????? ????????????
			command = new MemberUnfollowCommand();
			command.excute(request, response);
			out.write(request.getAttribute("result") + "");
			pass= false;
		} else if (commandName.equals("/member_register.mem")) { // ?????? ??????
			command = new RegisterCommand();
			command.excute(request, response);
			viewPage = "member_userProfile.mem";
			flag = true;
		} else if (commandName.equals("/MemberGetFollowerCnt.mem")) { // ????????? ?????? ????????????
			command = new GetFollowerCntCommand();
			command.excute(request, response);
			out.write(request.getAttribute("followerCnt")+"");
			pass= false;
		} else if (commandName.equals("/member_idcheck.mem")) { // ?????? ????????? ??????
			command = new CheckIdCommand();
			command.excute(request, response);
			//viewPage = "IdCheckOK.jsp";
			//System.out.println("result " + request.getAttribute("result")); //?????????
			out.write(request.getAttribute("result") + "");
			pass= false;
		} else if (commandName.equals("/member_emailcheck.mem")) { // ?????? ????????? ??????
			command = new CheckEmailCommand();
			command.excute(request, response);
			//viewPage = "emailCheckOK.jsp";
			//System.out.println("result " + request.getAttribute("result")); //?????????
			out.write(request.getAttribute("result") + "");
			pass= false;
		} else if (commandName.equals("/member_modify.mem")) { // ?????? ???????????? ????????? ??????
			command = new MemberViewCommand();
			command.excute(request, response);
			viewPage = "member_modify.jsp";
		} else if (commandName.equals("/member_modifyUpdate.mem")) { // ?????? ???????????? ??????
			command = new MemberModifyCommand();
			command.excute(request, response);
			viewPage = (String) request.getAttribute("viewPage");
			flag = (boolean) request.getAttribute("flag");
		} else if (commandName.equals("/member_pwChange.mem")) { // ?????? ???????????? ?????? ??????
			command = new MemberPWChangeCommand();
			command.excute(request, response);
			viewPage = (String) request.getAttribute("viewPage");
			flag = (boolean) request.getAttribute("flag");
		} else if (commandName.equals("/member_logout.mem")) { // ?????? ????????????
			command = new LogoutCommand();
			command.excute(request, response);
			viewPage = "member_login.jsp";
			flag = true;
		}
		if (pass) {
			if (flag) {
				response.sendRedirect(viewPage);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(viewPage);
				rd.forward(request, response);
			}
		}
	}

}
