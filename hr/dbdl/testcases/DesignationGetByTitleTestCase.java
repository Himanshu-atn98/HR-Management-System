import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationGetByTitleTestCase
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage:java path_to_jar_file;. DesignationGetByCodeTestCase [title]...");
return;
}
String title=gg[0];
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
DesignationDTOInterface designationDTO;
designationDTO=designationDAO.getByTitle(title);
System.out.printf("Code:%d, Title %s\n",designationDTO.getCode(),designationDTO.getTitle());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class DesignationGetByTitleTestCase Ends.