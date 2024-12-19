import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationTitleExistsTestCase
{
public static void main(String gg[])
{
if(gg.length!=1) 
{
System.out.println("Usage:java path_to_jar_file;. DesignationCodeExistsTestCase [title]...");
return;
}
String title=gg[0];
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
System.out.println(title+" exists:"+designationDAO.titleExists(title));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class DesignationTitleExistsTestCase Ends.