package servlets;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("*.files")
public class FilesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf8");
		String cmd = request.getRequestURI();
		
		if(cmd.equals("/download.files")) {
			
			//권한 확인하기
			//기록 남기기
			// 등등....
			
			//다운로드에 필요한 정보 취합.
			String fileName = request.getParameter("filename");
			String oriName = request.getParameter("oriname");
			String path = request.getServletContext().getRealPath("upload");
			
			//다운로드할 대상 파일의 내용을 byte[]에 로딩하기 위해 저장소 준비
			File target = new File(path + "/" + fileName);
			byte[] fileContents = new byte[(int)target.length()];
			
			//오리지널 파일 이름 인코딩 처리
			//크롬은 파일 다운로드시 이름을 ISO-8859-1로 처리해버림
			//브라우저별로 인코딩 처리가 다르지만 우린 크롬버전으로 설정.
			oriName = new String(oriName.getBytes("utf8"),"ISO-8859-1");
			
			
			//리스폰스가 디폴트로 가지고 있는 동작들을 리셋시킴
			response.reset();
			
			//response에 보내는 값은 소스코드가 아닌 파일 다운로드이다. 라는 정보를 탑재.
			response.setHeader("Content-Disposition", "attachment; filename="+oriName);
			
			try(DataInputStream dis = new DataInputStream(new FileInputStream(target));
					ServletOutputStream sos = response.getOutputStream();){
				//파일 내용을 Ram으로 전부 로딩
			dis.readFully(fileContents); 
			
			// Response의 Stream을 통해 byte[]을 출력
			sos.write(fileContents);
			sos.flush();
			}
		
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
