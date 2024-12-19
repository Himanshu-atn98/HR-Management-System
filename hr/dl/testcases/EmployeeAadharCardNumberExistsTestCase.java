import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeAadharCardNumberExistsTestCase
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage:java path_to_jar_file;. EmployeeAadharCardNumberExistsTestCase [aadhar_card_num]...");
return;
}
String aadharCardNumber=gg[0];
try
{    
System.out.println("Employee aadharcard number:"+aadharCardNumber+" exists:"+new EmployeeDAO().aadharCardNumberExists(aadharCardNumber));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}//main() Ends.
}//class EmployeeAadharCardNumberExistsTestCase Ends.