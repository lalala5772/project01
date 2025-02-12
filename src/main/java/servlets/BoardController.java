package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import commons.Statics;
import dao.BoardDAO;
import dao.FilesDAO;
import dto.BoardDTO;
import dto.FilesDTO;
import dto.ReplyDTO;

@WebServlet("*.board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String ipAddress = request.getRemoteAddr();
		System.out.println("접속한 IP 주소: " + ipAddress);
		String cmd = request.getRequestURI();
		
		BoardDAO dao = BoardDAO.getInstance();
		FilesDAO fdao = FilesDAO.getInstance();
		FilesDTO fdto = new FilesDTO();
		
		System.out.println(cmd);

		try {
			// 게시판 목록
			if (cmd.equals("/list.board")) {
//				List<BoardDTO> list = dao.selectBylist();
//				request.setAttribute("list", list);

				// 현재페이지
				String scpage = (String) (request.getParameter("cpage"));
				// 현재 페이지 유효성 검사.
				if (scpage == null) {
					scpage = "1";
				}

				int cpage = Integer.parseInt(scpage);

				int recordTotalCount = dao.getRecordTotalCount();

				// 전체 글의 개수를 알았고, 한 페이지에 10개씩 보여줄것이라면, 전체 페이지의 개수를 구할수 있음.
				int pageTotalCount = 0;

				// int형은 소수점이 안나오기때문에 나머지가 있을 경우 페이지를 한개 더 늘리고
				if (recordTotalCount % Statics.recordCountPerPage > 0) {

					pageTotalCount = recordTotalCount / Statics.recordCountPerPage + 1;
				} // 나머지 없을 경우 그대로.
				else {
					pageTotalCount = recordTotalCount / Statics.recordCountPerPage;
				}

				if (cpage < 1) {
					cpage = 1;
				} 
				else if (cpage > pageTotalCount) {
					cpage = pageTotalCount;
				}

				
				request.getSession().setAttribute("last_cpage", cpage);
				
				// cpage == 1 -> 1번~10번까지 가져오기
				// cpage == 2 -> 11번~20번까지 가져오기
				// cpage == 3 -> 21번~30번까지 가져오기.....

				// 네이게이션 시작번호
				int start = cpage * Statics.recordCountPerPage - (Statics.recordCountPerPage - 1);
				// 네이게이션 끝번호
				int end = cpage * Statics.recordCountPerPage;

				
				
				List<BoardDTO> list = dao.selectFromTo(start, end);

				int startNavi = (cpage - 1) / Statics.naviCountPerPage * Statics.naviCountPerPage + 1;
				int endNavi = startNavi + Statics.naviCountPerPage - 1;

				if(endNavi > pageTotalCount) {
					endNavi = pageTotalCount;
				}//endNavi 값은 페이지 전체 개수보다 클수없음!
				
				// 이전
				boolean needPrev = true;
				// 다음
				boolean needNext = true;

				if (startNavi == 1) {
					needPrev = false;
				} else if (endNavi == pageTotalCount) {
					needNext = false;
				}

				request.setAttribute("list", list);
				request.setAttribute("cpage", cpage);
				request.setAttribute("startNavi", startNavi);
				request.setAttribute("endNavi", endNavi);
				request.setAttribute("needPrev", needPrev);
				request.setAttribute("needNext", needNext);

				// String navi = dao.getPageNavi(cpage);
				// request.setAttribute("navi", navi);

				request.getRequestDispatcher("/board/list.jsp").forward(request, response);
				
			}

			else if (cmd.equals("/toWrite.board")) {

				response.sendRedirect("/board/write.jsp");
			}

			else if (cmd.equals("/add.board")) {
				//업로드 파일 10mb로 제한.
				int maxSize = 1024 * 1024 * 10;
				String savePath = request.getServletContext().getRealPath("upload");
				File filePath = new File(savePath);
				
				if(!filePath.exists()) {
					filePath.mkdir();
				}
				System.out.println(savePath);
				
				
				//(request 객체, 파일업로드 경로,  파일업로드 사이즈 제한, 인코딩처리, 이름 중복시 이름 변경 규칙)
				MultipartRequest multi = new MultipartRequest(request, savePath, maxSize , "utf8", new DefaultFileRenamePolicy());
				
				
//				String title = request.getParameter("title");
//				
//				String contents = request.getParameter("contents");
//				
//				
//				String writer = (String) request.getSession().getAttribute("loginID");
				
				
				//사용자가 직접 저장한 파일 명을 반환
				//String originalFileName1=multi.getOriginalFileName("upload1");    
			    //기존에 업로드한 파일들 중에 이름이 겹칠 경우 뒤에 번호가 붙는데, 그렇게 변환된 파일 이름을 반환
			    //String fileName1 = multi.getFilesystemName("upload1");   
			    //서버상에 업로드된 파일에 대한 파일 객체를 반환한다. 
				//File file1= multi.getFile("file");
				int seq = new BoardDAO().getNextVal();
				
				String title = multi.getParameter("title");
				String contents = multi.getParameter("contents");
				String writer = (String) request.getSession().getAttribute("loginID");
				
				BoardDTO dto = new BoardDTO(seq, writer, title, contents, null);
				int rs = dao.insert(dto);
				
				Enumeration<String> fileNames =  multi.getFileNames();
				
				//file1, file2
				while(fileNames.hasMoreElements()) {
					
					String name = fileNames.nextElement();
					String oriName = multi.getOriginalFileName(name);
					
					
					if(oriName == null) {continue;}
					
					String sysName = multi.getFilesystemName(name);
					
					System.out.println(oriName + "으로 넘어온 파일의 이름 : "+ sysName);
					
					fdto = new FilesDTO(0,oriName,sysName,seq);
					
					fdao.fileInsert(fdto);
				}
				
//				String oriName = multi.getOriginalFileName("file");
//				String sysName = multi.getFilesystemName("file");
				
				
				
				
//				if(oriName != null) {
//				fdto = new FilesDTO(0,oriName,sysName,seq);
//				fdao.fileInsert(fdto);
//				response.sendRedirect("/list.board?cpage=1");
//				
//				}
//				else {
//					
//				}
				
				
				
				response.sendRedirect("/list.board?cpage=1");

			}

			else if (cmd.equals("/detail.board")) {

				int seq = Integer.parseInt(request.getParameter("seq"));

				String id = (String) request.getSession().getAttribute("loginID");

				BoardDTO dto = dao.detailBylist2(seq);
				
				
				String writer = dao.detailByWriter(seq);

				dao.incrementViewCount(seq);

				List<ReplyDTO> replyList = dao.selectByReply(seq);

				List<FilesDTO> list = fdao.selectByFile(seq);
				System.out.println("파일 목록 크기: " + list.size());
				request.setAttribute("list", list);
				
				request.setAttribute("replyList", replyList);

				request.setAttribute("dto", dto);

				request.setAttribute("id", id);

				request.setAttribute("writer", writer);

				request.getRequestDispatcher("/board/detail.jsp").forward(request, response);
				
			}

			else if (cmd.equals("/delete.board")) {

				int seq = Integer.parseInt(request.getParameter("seq"));

				dao.deleteByContents(seq);

				response.sendRedirect("/list.board?cpage=1");
			}

			else if (cmd.equals("/update.board")) {
				int seq = Integer.parseInt(request.getParameter("seq"));
				String title = request.getParameter("title");
				String writer = (String) request.getSession().getAttribute("loginID");
				String contents = request.getParameter("contents");

				BoardDTO dto = new BoardDTO(seq, writer, title, contents);

				dao.updateBylist(dto);

				response.sendRedirect("/list.board?cpage=1");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}