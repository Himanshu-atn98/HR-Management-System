package com.thinking.machines.hr.bl.exceptions;
import java.util.*;
public class BLException extends Exception
{
private Map<String,String> exceptions;
private String genericException;
public BLException()
{
genericException=null;
exceptions=new HashMap<>();
}
//setGenericException()
public void setGenericException(String genericException)
{
this.genericException=genericException;
}
//getGenericException()
public String getGenericException()
{
if(this.genericException==null) return "";
return this.genericException;
}
//addException()
public void addException(String property,String exception)
{
this.exceptions.put(property,exception);
}
//getException()
public String getException(String property)
{
return this.exceptions.get(property);
}
//removeException()
public void removeException(String property)
{
this.exceptions.remove(property);
}
//getExceptionCount()
public int getExceptionCount()
{
if(this.genericException!=null) return this.exceptions.size()+1;
return this.exceptions.size();
}
// hasGenericException()
public boolean hasGenericException()
{
return this.genericException!=null;
}
//has Exception() ---> WHEN THERE IS A PROPERTY SPECIFY TO CHECK hasException()
public boolean hasException(String property)
{
return this.exceptions.containsKey(property);
}
//hasException() ---> WHEN THERE IS NO PROPERTY SPECIFY TO CHECK hasException()
public boolean hasException()
{
return this.getExceptionCount()>0;
}
//getProperties()
public List<String> getProperties()
{
List<String> properties=new ArrayList<>();
this.exceptions.forEach((k,v)->{
properties.add(k);
});
return properties;
}
//getMessage() --->Basically call/invoke to get the generic exception.
public String getMessage()
{
if(this.genericException==null) return "";
return this.genericException;
}
}// class BLException Ends.
