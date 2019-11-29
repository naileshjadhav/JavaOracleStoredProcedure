package local.nil.apps;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import local.nil.dto.EmployeeDTO;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

/**
 * Call oracle stored procedure!
 *
 */
public class App {

	private static Logger logger = Logger.getLogger(App.class);
	private static DBConnection dbConnection = new DBConnection();

	public static void main(String[] args) throws SQLException {

		EmployeeDTO dto = new EmployeeDTO();
		dto.setCity("Pune");
		dto.setCountry("India");
		dto.setEmpid(2121);
		dto.setName("ssss");
		dto.setRole("SSE");
		// Insert or update employee records to db
		insertEmpCallable(dto);
		// Get employee details based on employeeId
		getEmpcallCallable(dto.getEmpid());
		// Get all employees based on role
		List<EmployeeDTO> dtos = getEmployeesByRole(dto.getRole());
		dtos.forEach(e -> logger.info("EmpId: " + e.getEmpid() + ", name:" + e.getName()));
		// Using struct
		insertUsingStruct(dto);
	}

	private static void insertUsingStruct(EmployeeDTO dto) {
		try (Connection conn = dbConnection.getOracleConnection()) {
			Object[] value = { dto.getEmpid(), dto.getName(), dto.getRole(), dto.getCity(), dto.getCountry() };
			OracleCallableStatement stmt = (OracleCallableStatement) conn
					.prepareCall("{call insEmployeeObjStruct(?,?)}");
			stmt.setObject(1, conn.createStruct("EMPLOYEE_OBJ", value));
			// register the OUT parameter before calling the stored procedure
			stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			stmt.executeUpdate();
			String result = stmt.getString(2);
			if (result.equalsIgnoreCase(Boolean.TRUE.toString())) {
				logger.info("Employee data save success using struct::" + result);
			}

		} catch (SQLException ex) {
			logger.error("Caught SQLException.." + ex);
		} catch (Exception ex) {
			logger.error("Caught Exception.." + ex);
		}
	}

	private static List<EmployeeDTO> getEmployeesByRole(String role) {

		List<EmployeeDTO> dtos = new ArrayList<>();
		try (Connection connection = dbConnection.getOracleConnection();
				CallableStatement stmt = connection.prepareCall("{call getEmployeesByRole(?,?)}")) {
			stmt.setString(1, role);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			boolean result = stmt.execute();
			logger.info("Get employee by role result ::" + result);
			try (ResultSet rs = (ResultSet) stmt.getObject(2);) {
				while (rs.next()) {
					EmployeeDTO dto = new EmployeeDTO();
					dto.setEmpid(rs.getInt("empId"));
					dto.setName(rs.getString("name"));
					dto.setCity(rs.getString("city"));
					dto.setCountry(rs.getString("country"));
					dtos.add(dto);
				}
			}

		} catch (SQLException e) {
			logger.error("SQlExecption at get employee.." + e);
		} catch (Exception ex) {
			logger.error("Caught Exception.." + ex);
		}
		return dtos;

	}

	private static void getEmpcallCallable(Integer empid) {

		try (Connection connection = dbConnection.getOracleConnection();
				CallableStatement stmt = connection.prepareCall("{call getEmployee(?,?,?,?,?)}")) {
			stmt.setInt(1, empid);
			stmt.registerOutParameter(2, Types.VARCHAR);
			stmt.registerOutParameter(3, Types.VARCHAR);
			stmt.registerOutParameter(4, Types.VARCHAR);
			stmt.registerOutParameter(5, Types.VARCHAR);
			stmt.execute();
			String name = stmt.getString(2);
			String role = stmt.getString(3);
			String city = stmt.getString(4);
			String country = stmt.getString(5);
			logger.info("Employee name: " + name + ", city:" + city + ", country:" + country + ", role:" + role);
		} catch (SQLException e) {
			logger.error("SQlExecption at get employee.." + e);
		} catch (Exception ex) {
			logger.error("Caught Exception.." + ex);
		}
	}

	private static void insertEmpCallable(EmployeeDTO dto) {

		try (Connection conn = dbConnection.getOracleConnection();
				CallableStatement stmt = conn.prepareCall("{call emp_package.insertEmployee1(?,?,?,?,?,?)}");) {
			int id = dto.getEmpid();
			stmt.setInt(1, id);
			String name = dto.getName();
			stmt.setString(2, name);
			String role = dto.getRole();
			stmt.setString(3, role);
			String city = dto.getCity();
			stmt.setString(4, city);
			String country = dto.getCountry();
			stmt.setString(5, country);

			// register the OUT parameter before calling the stored procedure
			stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			stmt.executeUpdate();
			String result = stmt.getString(6);
			if (result.equalsIgnoreCase(Boolean.TRUE.toString())) {
				logger.info("Employee data save success::" + result);
			}

		} catch (SQLException ex) {
			logger.error("Caught SQLException.." + ex);
		} catch (Exception ex) {
			logger.error("Caught Exception.." + ex);
		}
	}
}
