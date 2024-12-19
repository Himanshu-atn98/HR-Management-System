//Location->c:\java\java_projects\...
package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.io.*;
public class DesignationDAO implements DesignationDAOInterface
{
private final static String FILE_NAME="designation.data";

//add()
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null");
String title=designationDTO.getTitle();
if(title==null) throw new DAOException("Designation is null");
title=title.trim();
if(title.length()==0) throw new DAOException("Length of designation is zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
int lastGeneratedCode=0;
int recordCount=0;
String lastGeneratedCodeString=""; //Empty String
String recordCountString="";  //Empty String
if(randomAccessFile.length()==0)
{
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" "; //space
recordCountString="0";
while(recordCountString.length()<10) recordCountString+=" "; //space
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
}
else
{
lastGeneratedCodeString=randomAccessFile.readLine().trim();
recordCountString=randomAccessFile.readLine().trim();
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString);
recordCount=Integer.parseInt(recordCountString);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title))
{
randomAccessFile.close();
throw new DAOException("Designation:"+title+" exists");
}
}
int code=lastGeneratedCode+1;
randomAccessFile.writeBytes(String.valueOf(code));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(title);
randomAccessFile.writeBytes("\n");
designationDTO.setCode(code);
lastGeneratedCode++;
recordCount++;
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
recordCountString=String.valueOf(recordCount);
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" "; //space
while(recordCountString.length()<10) recordCountString+=" "; //space
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

// update()
public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null.");
int code=designationDTO.getCode();
if(code<=0) throw new DAOException("Invalid code:"+code);
String title=designationDTO.getTitle();
if(title==null) throw new DAOException("Designation is null.");
title=title.trim();
if(title.length()==0) throw new DAOException("Length of designations is zero.");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid code:"+code);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code:"+code);
}
//FIRSTLY WE WILL CHECK THE CODE EXISTSTANCE HERE

randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code:"+code);
}
int fCode;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
if(fCode==code)
{
found=true;
break;
}
randomAccessFile.readLine();
}
if(found==false)
{
randomAccessFile.close();
throw new DAOException("Invalid code:"+code);
}
//NOW CHECK THE GIVEN TITLE EXISTSTANCE AGAINST THE OTHER CODE IN FILE
// FOR UNIQUENESS AND TO AVOID DUPLICACY

randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.readLine();
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode!=code && fTitle.equalsIgnoreCase(title))
{
randomAccessFile.close();
throw new DAOException("Title:"+title+" exists");
}
}
randomAccessFile.seek(0);
//NOW WE WILL CREATE TEMPRAORY FILE

File tmpFile=new File("tmpFile.txt");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(code)+"\n");
tmpRandomAccessFile.writeBytes(title+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode)+"\n");
tmpRandomAccessFile.writeBytes(fTitle+"\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

// delete() 
public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code:"+code);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid code:"+code);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code:"+code);
}
//FIRSTLY WE WILL CHECK THE CODE EXISTSTANCE HERE

randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code:"+code);
}
int fCode;
String fTitle="";
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code)
{
found=true;
break;
}
}
if(found==false)
{
randomAccessFile.close();
throw new DAOException("Invalid code:"+code);
}
if(new EmployeeDAO().isDesignationAlloted(code))
{
randomAccessFile.close();
throw new DAOException("Employee exists against designation code:"+code+" with designation:"+fTitle);
}
randomAccessFile.seek(0);
//NOW WE WILL CREATE TEMPRAORY FILE

File tmpFile=new File("tmpFile.txt");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode!=code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode)+"\n");
tmpRandomAccessFile.writeBytes(fTitle+"\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
tmpRandomAccessFile.readLine();
String recordCountString=String.valueOf(recordCount-1);
while(recordCountString.length()<10) recordCountString+=" ";
randomAccessFile.writeBytes(recordCountString+"\n");
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

// getAll()
public Set<DesignationDTOInterface> getAll() throws DAOException
{
Set<DesignationDTOInterface> designations=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return designations; //at this point we are returning empty Set.
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return designations; //at this point we are returning empty Set.
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
return designations; //at this point we are returning empty Set.
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
designations.add(designationDTO);
}
randomAccessFile.close();
return designations;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

// getByCode()
public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code:"+code);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid code:"+code);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid code:"+code);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid code:"+code);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code)
{
randomAccessFile.close();
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid code:"+code);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

// getByTitle()
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null || title.trim().length()==0) throw new DAOException("Invalid title:"+title);
title=title.trim();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid title:"+title);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid title:"+title);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid title:"+title);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title))
{
randomAccessFile.close();
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid title:"+title);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

// codeExists()
public boolean codeExists(int code) throws DAOException
{
if(code<=0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
return false;
}
int fCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
if(fCode==code)
{
randomAccessFile.close();
return true;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
return false;
}

// titleExists()
public boolean titleExists(String title) throws DAOException
{
if(title==null || title.trim().length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
return false;
}
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
return false;
}

// getCount()
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount;
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

}//class DesignationDAO Ends.
