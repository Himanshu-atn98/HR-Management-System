import com.thinking.machines.hr.dl.exceptions.*; 
import com.thinking.machines.hr.dl.interfaces.dao.*; 
import com.thinking.machines.hr.dl.dao.*;
public class DesignationDeleteTestCase
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage:java path_to_jar_file;. DesignationUpdateTestCase [code]...");
return;
}
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.delete(code);
System.out.println("Designation against the code:"+code+" deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class DesignationDeleteTestCase Ends.