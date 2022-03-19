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
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDBUtil studentDBUtil;
	
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			studentDBUtil= new StudentDBUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentControllerServlet() {
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
			case "LIST": listStudents(request, response);
			break;
			case "ADD": addStudent(request, response);
			break;
			case "LOAD": loadStudent(request, response);
			break;
			case "UPDATE": updateStudent(request, response);
			break;
			case "DELETE": deleteStudent(request, response);
			break;
			default:
				listStudents(request, response);
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
	
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Student> students= studentDBUtil.getStudents();
		
		request.setAttribute("student_list", students);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String fname= request.getParameter("first_name");
		String lname= request.getParameter("last_name");
		String email= request.getParameter("email");
		
		Student thStudent= new Student(fname, lname, email);
		
		studentDBUtil.addStudent(thStudent);
		listStudents(request, response);
	}
	
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id= request.getParameter("studentId");
		
		Student thStudent= studentDBUtil.getStudent(id);
		
		request.setAttribute("the_student", thStudent);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id= Integer.parseInt(request.getParameter("studentId"));
		
		String fname= request.getParameter("first_name");
		String lname= request.getParameter("last_name");
		String email= request.getParameter("email");
		
		Student thStudent= new Student(id, fname, lname, email);
		
		studentDBUtil.updateStudent(thStudent);
		listStudents(request, response);
	}
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id= request.getParameter("studentId");
		
//		Student thStudent= studentDBUtil.getStudent(id);
		
		studentDBUtil.deleteStudent(id);
		listStudents(request, response);
	}


}
