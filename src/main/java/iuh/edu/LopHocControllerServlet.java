package iuh.edu;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class LopHocControllerServlet
 */
@WebServlet("/LopHocControllerServlet")
public class LopHocControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LopHocDBUtil lopHocDBUtil;
	
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;
    
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			lopHocDBUtil= new LopHocDBUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	   
    /**
     * @throws ServletException 
     * @see HttpServlet#HttpServlet()
     */
    public LopHocControllerServlet() throws ServletException {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String theCommand= request.getParameter("command");
			
			if(theCommand == null)
				theCommand= "LIST";
			switch (theCommand) {
			case "LIST": listLops(request, response);
			break;
			case "ADD": addLop(request, response);
			break;
			case "LOAD": loadLop(request, response);
			break;
			case "UPDATE": updateLop(request, response);
			break;
			case "Student": listSVs(request, response);
			break;
			default:
				listLops(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void listLops(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<LopHoc> lops= lopHocDBUtil.getLops();
		
		request.setAttribute("lop_list", lops);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("/list-lops.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addLop(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String ma= request.getParameter("ma_lop");
		String ten= request.getParameter("ten_lop");
		String nha= request.getParameter("day_nha");
		
		LopHoc thLop= new LopHoc(ma, ten, nha);
		
		lopHocDBUtil.addLop(thLop);
		listLops(request, response);
	}
	
	private void loadLop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id= request.getParameter("ma_lop");
		
		LopHoc thLop= lopHocDBUtil.getLop(id);
		
		request.setAttribute("the_lop", thLop);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("/update-lop-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void updateLop(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String ma= request.getParameter("ma_lop");
		
		String ten= request.getParameter("ten_lop");
		String nha= request.getParameter("day_nha");
		
		LopHoc thLop= new LopHoc(ma, ten, nha);
		
		lopHocDBUtil.updateLop(thLop);
		listLops(request, response);
	}
	
	
	private void listSVs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ma= request.getParameter("ma_lop");
		List<Student> lops= lopHocDBUtil.getSVLops(ma);
		
		request.setAttribute("student_list", lops);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("/dsSVTheoLop.jsp");
		dispatcher.forward(request, response);
	}

}
