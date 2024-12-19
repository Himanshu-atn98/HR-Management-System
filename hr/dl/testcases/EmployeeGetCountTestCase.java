import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
public class EmployeeGetCountTestCase
{
public static void main(String gg[])
{
try
{    
System.out.println("Total no. of employees:"+new EmployeeDAO().getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class EmployeeGetCountTestCase Ends.