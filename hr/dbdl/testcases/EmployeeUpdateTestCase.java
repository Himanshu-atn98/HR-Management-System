import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.io.*;
import java.text.*;
import com.thinking.machines.enums.*;
public class EmployeeUpdateTestCase
{
public static void main(String gg[])
{
if(gg.length!=9)
{
System.out.println("Usage:java path_to_jar_file;. EmployeeUpdateTestCase [employee_id name deisgnation_code date_of_birth gender isIndian? baisc_salary pan_num aadharcard_num]");
return;
}
String employeeId=gg[0];
String name=gg[1];
int designationCode=Integer.parseInt(gg[2]);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth=simpleDateFormat.parse(gg[3]);
}catch(ParseException parseException)
{
//do nothing.
}
char gender=gg[4].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[5].trim()); 
BigDecimal basicSalary=new BigDecimal(gg[6]);
String panNumber=gg[7];
String aadharCardNumber=gg[8];
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M' || gender=='m') employeeDTO.setGender(GENDER.MALE);
if(gender=='F' || gender=='f') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
System.out.println("Employee:"+name+" against employee Id:"+employeeId+" updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main()
}//class EmployeeUpdateTestCase