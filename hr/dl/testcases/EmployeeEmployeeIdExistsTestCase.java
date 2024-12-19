import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeEmployeeIdExistsTestCase
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage:java path_to_jar_file;. EmployeeGetByCodeTestCase [code]...");
return;
}
String employeeId=gg[0];
try
{    
System.out.println("EmployeeId:"+employeeId+" exists:"+new EmployeeDAO().employeeIdExists(employeeId));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class EmployeeEmployeeIdExistsTestCase Ends.