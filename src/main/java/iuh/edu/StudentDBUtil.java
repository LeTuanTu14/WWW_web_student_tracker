package iuh.edu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	
	private DataSource dataSource;
	
	public StudentDBUtil(DataSource theDataSource) {
		dataSource= theDataSource;
	}
	
	public List<Student> getStudents() throws SQLException{
		List<Student> students= new ArrayList<Student>();
		Connection myConn= null;
		Statement myStatement= null;
		ResultSet myResultSet= null;
		
		try {
			myConn= dataSource.getConnection();
			
			String sql="select * from student order by last_name";
			myStatement= myConn.createStatement();
			
			myResultSet= myStatement.executeQuery(sql);
			
			while (myResultSet.next()) {
				int id= myResultSet.getInt("id");
				String firstName= myResultSet.getString("first_name");
				String lastName= myResultSet.getString("last_name");
				String email= myResultSet.getString("email");
				
				Student tempStudent= new Student(id, firstName, lastName, email);
				
				students.add(tempStudent);
				
			}
			return students;
		} finally {
			close(myConn,myStatement,myResultSet);
		}
		
	}
	
	public void addStudent(Student theStudent) throws SQLException {
		Connection myConnection= null;
		PreparedStatement myStmt= null;
		try {
			myConnection= dataSource.getConnection();
			
			String sql="insert into student (first_name, last_name, email) values(?,?,?)";
			myStmt=myConnection.prepareStatement(sql);
			
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			
			myStmt.execute();
		} finally {
			close(myConnection, myStmt, null);
		}
	}
	
	
	public void updateStudent(Student theStudent) throws SQLException{
		Connection myConnection= null;
		PreparedStatement myStmt= null;
		try {
			myConnection= dataSource.getConnection();
			
			String sql="update student set first_name=?, last_name=?, email=? where id=?";
			myStmt=myConnection.prepareStatement(sql);
			
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			
			myStmt.execute();
		} finally {
			close(myConnection, myStmt, null);
		}
	}
	
	public void deleteStudent(String theStudentId) throws SQLException {
		Connection myConnection= null;
		PreparedStatement myStmt= null;
		try {
			int id=Integer.parseInt(theStudentId);
			myConnection= dataSource.getConnection();
			
			String sql="delete from student where id=?";
			myStmt=myConnection.prepareStatement(sql);
			
			myStmt.setInt(1, id);
			
			myStmt.execute();
		} finally {
			close(myConnection, myStmt, null);
		}
	}
	
	public Student getStudent(String theStudentId) throws Exception {
		Student thStudent=null;
		Connection myConnection= null;
		PreparedStatement myStmt= null;
		ResultSet myResultSet=null;
		int StudentId;
		try {
			StudentId= Integer.parseInt(theStudentId);
			myConnection=dataSource.getConnection();
			String sql="select * from student where id=?";
			myStmt= myConnection.prepareStatement(sql);
			myStmt.setInt(1, StudentId);
			myResultSet= myStmt.executeQuery();
			if(myResultSet.next()) {
				String firstName= myResultSet.getString("first_name");
				String lastName= myResultSet.getString("last_name");
				String email= myResultSet.getString("email");
				thStudent= new Student(StudentId, firstName, lastName, email);
				
			}else {
				throw new Exception("Could not find student id: "+StudentId);
				
			}
			return thStudent;
		} finally {
			close(myConnection, myStmt, myResultSet);
		}
	}
	
	private void close(Connection myConnection, Statement myStatement, ResultSet myResultSet) {
		try {
			if(myResultSet != null)
				myResultSet.close();
			if(myStatement !=null)
				myStatement.close();
			if(myConnection !=null)
				myConnection.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	

}
