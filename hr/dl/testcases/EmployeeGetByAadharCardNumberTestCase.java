import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeGetByAadharCardNumberTestCase
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage:java path_to_jar_file;. EmployeeGetByAadharCardNumberTestCase [aadharcard_num]...");
return;
}
String aadharCardNumber=gg[0];
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDTO=employeeDAO.getByAadharCardNumber(aadharCardNumber);
System.out.println("EmployeeId:"+employeeDTO.getEmployeeId());
System.out.println("Name:"+employeeDTO.getName());
System.out.println("Designation code:"+employeeDTO.getDesignationCode());
System.out.println("Date of birth:"+simpleDateFormat.format(employeeDTO.getDateOfBirth()));
System.out.println("Gender:"+employeeDTO.getGender());
System.out.println("Is Indian:"+employeeDTO.getIsIndian());
System.out.println("Basic salary:"+employeeDTO.getBasicSalary().toPlainString());
System.out.println("Pan Number:"+employeeDTO.getPANNumber());
System.out.println("Aadharcard Number:"+employeeDTO.getAadharCardNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class EmployeeGetByAadharCardNumberTestCase Ends.