package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dto.ReplyDTO;


@WebServlet("*.reply")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO dao = BoardDAO.getInstance();
		
		request.setCharacterEncoding("UTF-8");
		String ipAddress = request.getRemoteAddr();
        System.out.println("접속한 IP 주소: " + ipAddress);
		String cmd = request.getRequestURI();
		System.out.println(cmd);
		
		try {
			
			if(cmd.equals("/register.reply")) {
				String writer = (String) request.getSession().getAttribute("loginID");
				String contents = request.getParameter("replyText");
				int seq = Integer.parseInt(request.getParameter("seq"));
				
				ReplyDTO dto = new ReplyDTO(0, writer, contents, null, seq);

				int rs = dao.replyInsert(dto);

				List<ReplyDTO> replyList = dao.selectByReply(seq);
				
				request.setAttribute("replyList", replyList);
				response.sendRedirect("/detail.board?seq="+seq);
				
				
				
			
			}
			
			else if(cmd.equals("/delete.reply")) {
				
				int seq = Integer.parseInt(request.getParameter("seq"));
				System.out.println(seq);
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				
				
				dao.deleteByReply(replyId);
				
				//System.out.println(seq);
				response.sendRedirect("/detail.board?seq="+seq);
				
				
			}
				
				
			else if (cmd.equals("/update.reply")) {
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				String writer = (String) request.getSession().getAttribute("loginID");
				String contents = request.getParameter("contents");
				int seq = Integer.parseInt(request.getParameter("seq"));
				
				ReplyDTO rdto = new ReplyDTO(replyId, writer, contents,null,seq);

				dao.updateByReplyList(rdto);

				response.sendRedirect("/detail.board?seq="+seq);
			}
			
			
			
			
			
			
			
			
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
