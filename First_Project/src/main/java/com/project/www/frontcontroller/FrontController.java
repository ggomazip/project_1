package com.project.www.frontcontroller;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.www.Command.Command;
import com.project.www.Command.board.DeleteCommand;
import com.project.www.Command.board.GetGoodHitCntCommand;
import com.project.www.Command.board.ListCommand;
import com.project.www.Command.board.MainListCommand;
import com.project.www.Command.board.MinusGoodHitCommand;
import com.project.www.Command.board.ModifyCommand;
import com.project.www.Command.board.ModifyUpdateCommand;
import com.project.www.Command.board.MoreListCommand;
import com.project.www.Command.board.PlusGoodHitCommand;
import com.project.www.Command.board.ReplyCntGetCommand;
import com.project.www.Command.board.ReplyCommand;
import com.project.www.Command.board.ReplyDeleteCommand;
import com.project.www.Command.board.ReplyViewCommand;
import com.project.www.Command.board.SearchCommand;
import com.project.www.Command.board.SearchListCommand;
import com.project.www.Command.board.UpdateHitCommand;
import com.project.www.Command.board.ViewCommand;
import com.project.www.Command.board.WriteCommand;
import com.project.www.Command.member.MemberViewCommand;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionDo(request, response);
	}

	protected void actionDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String commandName = request.getServletPath();
		String viewPage = "";
		boolean flag = false;
		boolean pass = true;
		Command command = null;
		if (commandName.equals("/BoardList.do")) { // ?????? ??? ????????? ????????????
			command = new ListCommand();
			command.excute(request, response);
			viewPage = "BoardList.jsp";
		} else if (commandName.equals("/BoardListMore.do")) { // ????????? ?????????
			command = new MoreListCommand();
			command.excute(request, response);
			
			//out.write(request.getAttribute("result") + "");
			pass= false;
		} else if (commandName.equals("/BoardMain.do")) { // ???????????? ????????? ????????? ????????????
			command = new MainListCommand();
			command.excute(request, response);
			viewPage = "BoardMain.jsp";
		} else if (commandName.equals("/BoardSearchList.do")) { // ??? ?????? 
			command = new SearchListCommand();
			command.excute(request, response);
			viewPage="BoardSearch.jsp";
		} else if (commandName.equals("/BoardSearch.do")) { // ???????????? ?????? ?????? ???????????? 
			command = new SearchCommand();
			command.excute(request, response);
			viewPage="BoardList.jsp";
		} else if (commandName.equals("/BoardView.do")) { // ????????? ?????? ????????? ?????????
			HttpSession session = request.getSession();
			command = new UpdateHitCommand();
			command.excute(request, response);
			viewPage = "BoardViewAfterUpdateHit.do?no="+request.getParameter("no");
			flag = true;
		} else if (commandName.equals("/BoardViewAfterUpdateHit.do")) { // ?????????
			command = new ViewCommand();
			command.excute(request, response);
			viewPage = "BoardView.jsp";
		} else if (commandName.equals("/BoardWriteView.do")) { // ????????? ????????? ??????
			command = new MemberViewCommand();
			command.excute(request, response);
			viewPage = "BoardWrite.jsp";
		} else if (commandName.equals("/BoardWrite.do")) { // ?????????
			command = new WriteCommand();
			command.excute(request, response);
			viewPage = "BoardList.do";
			flag = true;
		}  else if (commandName.equals("/BoardModify.do")) {	// ????????? ?????????
			command = new ModifyCommand();
			command.excute(request, response);
			viewPage = "BoardModify.jsp";
		}else if (commandName.equals("/BoardModifyUpdate.do")) { //????????? ??????
			command = new ModifyUpdateCommand();
			command.excute(request, response);
			viewPage = "BoardViewAfterUpdateHit.do?no="+request.getParameter("no");
			flag = true;
		}else if (commandName.equals("/BoardReplyView.do")) {	//reply ????????? ??????
			command = new ReplyViewCommand();
			command.excute(request, response);
			pass= false;
		}else if (commandName.equals("/BoardWriteReply.do")) { // reply ??????
			command = new ReplyCommand();
			command.excute(request, response);
			out.write("1");
			pass= false;
		}else if (commandName.equals("/BoardDeleteReply.do")) { // reply ??????
			command = new ReplyDeleteCommand();
			command.excute(request, response);
			out.write("1");
			pass= false;
		}else if (commandName.equals("/BoardGetReplyCnt.do")) { // reply ?????? ????????????
			command = new ReplyCntGetCommand();
			command.excute(request, response);
			out.write(request.getAttribute("replyCnt")+"");
			pass= false;
		}else if (commandName.equals("/BoardPlusGoodHit.do")) { // ????????? ?????????
			command = new PlusGoodHitCommand();
			command.excute(request, response);
			out.write("1");
			pass = false;
		}else if (commandName.equals("/BoardMinusGoodHit.do")) { // ????????? ????????????
			command = new MinusGoodHitCommand();
			command.excute(request, response);
			out.write("1");
			pass = false;
		}else if (commandName.equals("/BoardGetGoodHitCnt.do")) { // ????????? ?????? ????????????
			command = new GetGoodHitCntCommand();
			command.excute(request, response);
			out.write(request.getAttribute("goodHitCnt")+"");
			pass= false;
		}else if (commandName.equals("/BoardDelete.do")) { // ??? ??????
			command = new DeleteCommand();
			command.excute(request, response);
			viewPage = "member_userProfile.mem";
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
