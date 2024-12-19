import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
public class DesignationManagerAddTestCase 
{
public static void main(String gg[])
{
if(gg.length!=2 && gg.length!=1)
{
System.out.println("Usage:DesignationManagerAddTestCase path_to_jar_file [code=0,title]");
return;
}
int code;
String title;
if(gg.length==1) 
{
title=gg[0];
code=0;
}
else 
{
code=Integer.parseInt(gg[0]);
title=gg[1];
}
DesignationInterface designation=new Designation();
designation.setCode(code);
designation.setTitle(title);
DesignationManagerInterface designationManager;
try
{
designationManager=DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
System.out.println("Designation added with code as:"+designation.getCode());
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
}//main()Ends.
}//class Ends.