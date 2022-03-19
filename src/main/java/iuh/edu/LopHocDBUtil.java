package iuh.edu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class LopHocDBUtil {
	
private DataSource dataSource;
	
	public LopHocDBUtil(DataSource theDataSource) {
		dataSource= theDataSource;
	}
	
	public List<LopHoc> getLops() throws SQLException{
		List<LopHoc> lops= new ArrayList<LopHoc>();
		Connection myConn= null;
		Statement myStatement= null;
		ResultSet myResultSet= null;
		
		try {
			myConn= dataSource.getConnection();
			
			String sql="select * from lophoc";
			myStatement= myConn.createStatement();
			
			myResultSet= myStatement.executeQuery(sql);
			
			while (myResultSet.next()) {
				String maLop= myResultSet.getString("maLop");
				String tenLop= myResultSet.getString("tenLop");
				String dayNha= myResultSet.getString("dayNha");
				
				LopHoc tempLop= new LopHoc(maLop, tenLop, dayNha);
				
				lops.add(tempLop);
				
			}
			return lops;
		} finally {
			close(myConn,myStatement,myResultSet);
		}
		
	}
	
	public void addLop(LopHoc theLop) throws SQLException {
		Connection myConnection= null;
		PreparedStatement myStmt= null;
		try {
			myConnection= dataSource.getConnection();
			
			String sql="insert into lophoc (maLop, tenLop, dayNha) values(?,?,?)";
			myStmt=myConnection.prepareStatement(sql);
			
			myStmt.setString(1, theLop.getMaLop());
			myStmt.setString(2, theLop.getTenLop());
			myStmt.setString(3, theLop.getDayNha());
			
			myStmt.execute();
		} finally {
			close(myConnection, myStmt, null);
		}
	}
	
	public List<Student> getSVLops(String theLopId) throws Exception{
		List<Student> svs= new ArrayList<Student>();
		Connection myConn= null;
		PreparedStatement myStatement= null;
		ResultSet myResultSet= null;
		
		try {
			myConn= dataSource.getConnection();
			
			String sql="select * from student where lophoc=?";
			myStatement= myConn.prepareStatement(sql);
			myStatement.setString(1, theLopId);
			myResultSet=myStatement.executeQuery();
			while (myResultSet.next()) {
				int ma= myResultSet.getInt("id");
				String firstName= myResultSet.getString("first_name");
				String lastName= myResultSet.getString("last_name");
				String email= myResultSet.getString("email");
				String lophoc = myResultSet.getString("lophoc");
				svs.add(new Student(ma, firstName, lastName, email, lophoc));
			
			}
			return svs;
		} finally {
			close(myConn,myStatement,myResultSet);
		}
		
	}
	public LopHoc getLop(String theLopId) throws Exception {
		LopHoc thLop=null;
		Connection myConnection= null;
		PreparedStatement myStmt= null;
		ResultSet myResultSet=null;
		try {
			myConnection=dataSource.getConnection();
			String sql="select * from lophoc"
					+ " where maLop=?";
			myStmt= myConnection.prepareStatement(sql);
			myStmt.setString(1, theLopId);
			myResultSet= myStmt.executeQuery();
			if(myResultSet.next()) {
				String ma= myResultSet.getString("maLop");
				String ten= myResultSet.getString("tenLop");
				String nha= myResultSet.getString("dayNha");
				thLop= new LopHoc(ma, ten, nha);
				
			}else {
				throw new Exception("Could not find student id: "+theLopId);
				
			}
			return thLop;
		} finally {
			close(myConnection, myStmt, myResultSet);
		}
	}
	
	public void updateLop(LopHoc theLop) throws SQLException{
		Connection myConnection= null;
		PreparedStatement myStmt= null;
		try {
			myConnection= dataSource.getConnection();
			
			String sql="update lophoc set tenLop=?, dayNha=? where maLop=?";
			myStmt=myConnection.prepareStatement(sql);
			
			myStmt.setString(1, theLop.getTenLop());
			myStmt.setString(2, theLop.getDayNha());
			myStmt.setString(3, theLop.getMaLop());
			
			myStmt.execute();
		} finally {
			close(myConnection, myStmt, null);
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
