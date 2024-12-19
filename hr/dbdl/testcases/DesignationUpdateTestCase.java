import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationUpdateTestCase
{
public static void main(String gg[])
{
if(gg.length!=2)
{
System.out.println("Usage:java path_to_jar_file;. DesignationUpdateTestCase [code,title]...");
return;
}
int code=Integer.parseInt(gg[0]);
String title=gg[1];
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
System.out.println(title+" updated against the code:"+code);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class DesignationUpdateTestCase Ends.