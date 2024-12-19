import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationGetByCodeTestCase
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage:java path_to_jar_file;. DesignationGetByCodeTestCase [code]...");
return;
}
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
DesignationDTOInterface designationDTO;
designationDTO=designationDAO.getByCode(code);
System.out.printf("Code:%d, Title %s\n",designationDTO.getCode(),designationDTO.getTitle());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class DesignationGetByCodeTestCase Ends.