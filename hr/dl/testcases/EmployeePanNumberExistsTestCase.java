import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
public class EmployeePanNumberExistsTestCase
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage:java path_to_jar_file;. EmployeePanNumberExistsTestCase [pan_num]...");
return;
}
String panNumber=gg[0];
try
{    
System.out.println("Employee pan number:"+panNumber+" exists:"+new EmployeeDAO().panNumberExists(panNumber));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class EmployeePanNumberExistsTestCase Ends.