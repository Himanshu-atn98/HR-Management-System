import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeIsDesignationAllotedTestCase
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage:java path_to_jar_file;. EmployeeGetByCodeTestCase [code]...");
return;
}
int designationCode=Integer.parseInt(gg[0]);
try
{    
System.out.println("Employee against the designation code:"+designationCode+" exists:"+new EmployeeDAO().isDesignationAlloted(designationCode));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class EmployeeIsDesignationAllotedTestCase Ends.