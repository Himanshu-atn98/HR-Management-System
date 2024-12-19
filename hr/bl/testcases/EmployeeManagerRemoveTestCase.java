import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
public class EmployeeManagerRemoveTestCase
{
public static void main(String gg[])
{
if(gg[0].length()!=1)
{
System.out.println("Usage:EmployeeManagerRemoveTestCase,path_to_jar_file [employeeId]...");
return;
}
String employeeId=gg[0].trim();
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.removeEmployee(employeeId);
System.out.println("Employee deleted");
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