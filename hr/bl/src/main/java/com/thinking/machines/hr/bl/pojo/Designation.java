package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
public class Designation implements DesignationInterface
{
private int code;
private String title;
public Designation()
{
code=0;
title=null;
}
public void setCode(int code)
{
this.code=code;
}
public void setTitle(String title)
{
this.title=title;
}
public int getCode()
{
return this.code;
}
public String getTitle()
{
return this.title;
}
//equals()
public boolean equals(Object object)
{
if(!(object instanceof DesignationInterface)) return false;
DesignationInterface designation=(DesignationInterface) object;
return this.code==designation.getCode();
}
//compareTo()
public int compareTo(DesignationInterface designation)
{
return this.code-designation.getCode();
}
//hashCode()
public int hashCode()
{
return this.code;
}
}