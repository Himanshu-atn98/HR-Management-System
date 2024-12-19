import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationCodeExistsTestCase
{
public static void main(String gg[])
{
if(gg.length!=1) 
{
System.out.println("Usage:java path_to_jar_file;. DesignationCodeExistsTestCase [code]...");
return;
}
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
System.out.println(code+" exists:"+designationDAO.codeExists(code));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class DesignationCodeExistsTestCase Ends.