import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
public class EmployeeGetCountByDesignationCodeTestCase
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage:java path_to_jar_file;. EmployeeGetCountByDesignationCodeTestCase [designation_code]...");
return;
}
int designationCode=Integer.parseInt(gg[0]);
try
{    
System.out.println("No. of employees whoes designation code is:"+designationCode+" is: "+new EmployeeDAO().getCountByDesignationCode(designationCode));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class EmployeeGetCountByDesignationCodeTestCase Ends.