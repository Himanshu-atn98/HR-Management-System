import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
public class DesignationManagerUpdateTestCase 
{
public static void main(String gg[])
{
if(gg.length!=2 && gg.length!=1)
{
System.out.println("Usage:DesignationManagerAddTestCase path_to_jar_file [code=0,title]");
return;
}
int code=Integer.parseInt(gg[0]);
String title=gg[1].trim();
DesignationInterface designation=new Designation();
designation.setCode(code);
designation.setTitle(title);
try
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
System.out.println("Designation updated");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}
