import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerGetEmployeesByDesignationCodeTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
Set<EmployeeInterface> employeesSet;
employeesSet=employeeManager.getEmployeesByDesignationCode(designationCode);
System.out.println("********Data against designation code:"+designationCode+"********");
for(EmployeeInterface employee:employeesSet)
{
System.out.println("Employee Id:"+employee.getEmployeeId());
System.out.println("Name:"+employee.getName());
DesignationInterface designation=employee.getDesignation();
System.out.println("Designation:"+designation.getTitle()+", with code:"+designation.getCode());

System.out.println("Date of birth:"+simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println("Gender:"+employee.getGender());
System.out.println("IsIndian:"+employee.getIsIndian());
System.out.println("Basic salary:"+employee.getBasicSalary().toPlainString());
System.out.println("Pan number:"+employee.getPANNumber());
System.out.println("Aadhar card number:"+employee.getAadharCardNumber());
System.out.println("*************************************************************************"); 
}
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}//main() Ends.
}//class Ends.