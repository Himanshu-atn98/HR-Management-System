package com.thinking.machines.hr.bl.interfaces.pojo;
import java.io.*;
public interface DesignationInterface extends Comparable<DesignationInterface>,Serializable
{
public void setCode(int code);
public int getCode();
public void setTitle(String title);
public String getTitle();
}