import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerEmployeeIdExistsTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0].trim();
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
System.out.println("Employee Id:"+employeeId+" Exists:"+employeeManager.employeeIdExists(employeeId));
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