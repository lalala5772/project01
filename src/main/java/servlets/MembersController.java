package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.MembersDAO;
import dto.MembersDTO;

@WebServlet("*.members")
public class MembersController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// 한글 인코딩 설정

		String cmd = request.getRequestURI(); // 현재 요청된 URI를 가져오는 것
		System.out.println("클라이언트 요청 : " + cmd);
		String ipAddress = request.getRemoteAddr();
        System.out.println("접속한 IP 주소: " + ipAddress);
		// DAO를 통해 데이터베이스에 저장
		MembersDAO dao = MembersDAO.getInstance();

		try {
			if (cmd.equals("/add.members")) {
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				String postcode = request.getParameter("postcode");
				String address1 = request.getParameter("addr1");
				String address2 = request.getParameter("addr2");

				MembersDTO dto = new MembersDTO(id, pw, name, email, phone, postcode, address1, address2, null);
				int rs = dao.insert(dto);
				response.sendRedirect("/index.jsp");
			} 
			
			else if (cmd.equals("/idCheck.members")) {
				Gson g = new Gson();
				
				String id = request.getParameter("id");
				System.out.println(id);

				// DB에서 아이디가 존재하는지 안하는지 체크하는 코드
				
				response.getWriter().append(g.toJson(dao.isExistID(id)));
				

			}
			// 가입
			else if (cmd.equals("/signUp.members")) {
				
					
				
				response.sendRedirect("/members/signUp.jsp");
				
			} 
			
			else if (cmd.equals("/login.members")) {
				
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");

				// DB로부터 아이디 패스워드가 일치하는 지 확인
				// boolean result = true;
				boolean result = dao.login(id, pw);
				
				if (result) {
					System.out.println("로그인 성공");
					request.getSession().setAttribute("loginID", id);
				} 
				
				else {
					System.out.println("로그인 실패");
				}

				response.sendRedirect("/index.jsp");

				// HTTP 프로토콜 = Stateless 프로토콜
				// 최초의 로그인 처리 기법 : Cookie
				// 로그인 성공 시점에 임의의 내용을 클라이언트에게 전송함
				// 현재는 인증 기능 보다는 부가 기능으로 사용되는 추세

				// 새로운 인증 방법
				// 1. Request - 요청 한번에 대한 단발성 기억 변수로 로그인에 부적합
				// 2. Application ( ServletContext ) - 서버측 메모리에 보관되는 Static 한 Map 구조
				// request.getServletContext().setAttribute("key", "value");
				// ServletContext는 모든 사용자의 공용 저장소로 한명만 로그인해도 모든 사용자가 로그인되는 문제가 발생하므로 부적합
				// 3. Session - 서버측 메모리에 보관되는 Static한 Map 구조
				// request.setAttribute("request", "data1");
				// request.getSession().setAttribute("session", "data2");
				// request.getServletContext().setAttribute("context", "data3");

				// request.getRequestDispatcher("/index.jsp").forward(request, response);

			} 
			
			else if (cmd.equals("/signOut.members")) { // 로그아웃
				request.getSession().invalidate();
				response.sendRedirect("/index.jsp");// 현재 접속자의 키로 사용중인 MAP
			} 
			
			else if (cmd.equals("/membersOut.members")) {// 탈퇴
				String id = (String)request.getSession().getAttribute("loginID");
				int result = dao.deleteAccount(id);

				if (result > 0) {
					System.out.println("탈퇴 성공");
					request.getSession().invalidate();// 세션에서 정보 삭제
				}

				response.sendRedirect("/index.jsp");
			} 
			
			else if (cmd.equals("/myPage.members")) {

				String loginID = (String)request.getSession().getAttribute("loginID");
				MembersDTO dto = dao.selectAll(loginID);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("/members/myPage.jsp").forward(request, response);
			}
			// 수정
			else if (cmd.equals("/modify.members")) {
				String id = (String)request.getSession().getAttribute("loginID");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				String postcode = request.getParameter("postcode");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				
				MembersDTO dto = new MembersDTO(id,name,email,phone,postcode,address1,address2);
				int result = dao.updateBylist(dto);
				
				response.sendRedirect("/myPage.members");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/error.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
