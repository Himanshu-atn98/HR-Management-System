import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
public class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
try
{
String name="Mayuri Singh";
DesignationInterface designation=new Designation();
designation.setCode(5);
Date dateOfBirth=new Date();//current date will be set.
char gender='F';
Boolean isIndian=true;
BigDecimal basicSalary=new BigDecimal(150000);
String panNumber="PANXXX1009";
String aadharCardNumber="UIIDXXXX1009";
EmployeeInterface employee=new Employee();
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
//employee.setGender('Z'); --->ERROR: char can not be converted to GENDER
//employee.setGender(GENDER.MALE);
employee.setGender((gender=='M'||gender=='m')?(GENDER.MALE):(GENDER.FEMALE));
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.println("Employee:"+name+" added with employeeId:"+employee.getEmployeeId());
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