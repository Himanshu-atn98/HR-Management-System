package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.math.*;
import java.io.*;
import java.text.*;
import com.thinking.machines.enums.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
public final static String FILE_NAME="employee.data";

//add()
public void add(EmployeeDTOInterface employeeDTO) throws DAOException  
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code:"+designationCode);
DesignationDAOInterface designationDAO=new DesignationDAO();
if(designationDAO.codeExists(designationCode)==false) throw new DAOException("Invalid designation code:"+designationCode); 
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender not to set Male/Female");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw  new DAOException("Basic salary is null");
if(basicSalary.signum()<0) throw new DAOException("Basic salary is negative:"+basicSalary.toPlainString());
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("Pan Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of pan number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadharcard Number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of aadhar card number is zero");
try
{
File file= new File(FILE_NAME);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
int lastGeneratedEmployeeId=10000000;
int recordCount=0;
String lastGeneratedEmployeeIdString="";
String recordCountString="";
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
String fPanNumber="";
String fAadharCardNumber="";
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int x=1;x<=7;x++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberExists==false && fPanNumber.equalsIgnoreCase(panNumber))
{
panNumberExists=true;
}
if(aadharCardNumberExists==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberExists=true;
}
if(panNumberExists && aadharCardNumberExists) break;
}

if(panNumberExists && aadharCardNumberExists) 
{
randomAccessFile.close();
throw new DAOException("Pan Number:("+panNumber+"( and AadharCard Number:"+aadharCardNumber+") exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("Pan Number:("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadharcard Number:("+aadharCardNumber+") exists");
}
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
employeeId="A"+(lastGeneratedEmployeeId+1);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(String.valueOf(designationCode)+"\n");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedEmployeeId++;
recordCount++;
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

//update()
public void update(EmployeeDTOInterface employeeDTO) throws DAOException  
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("Employee Id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employee Id is zero");
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0)  throw new DAOException("Invalid designation code:"+designationCode);
if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Invalid designation code:"+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender not set to Male/Female");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic salary is null");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("Pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of pan number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadharcard number is zero");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of aadharcard number is zero");

try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employeeId:"+employeeId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid employeeId:"+employeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeId;
String fPanNumber;
String fAadharCardNumber;
boolean employeeIdFound=false;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
String panNumberFoundAgainstEmployeeId="";
String aadharCardNumberFoundAgainstEmployeeId="";
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(int x=1;x<=6;x++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
}
if(panNumberFound==false && fPanNumber.equalsIgnoreCase(panNumber))
{
panNumberFound=true;
panNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(aadharCardNumberFound==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberFound=true;
aadharCardNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(employeeIdFound && panNumberFound && aadharCardNumberFound) break;
}

if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("Invalid employeeId:"+employeeId);
}
boolean panNumberExists=false;
if(panNumberFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
panNumberExists=true;
}
boolean aadharCardNumberExists=false;
if(aadharCardNumberFound && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
aadharCardNumberExists=true;
}

if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Pan number ("+panNumber+") and Aadharcard number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("Pan number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadharcard number ("+aadharCardNumber+") exists");
}
 
randomAccessFile.seek(foundAt);
for(int x=1;x<=9;x++) randomAccessFile.readLine();
File tmpFile=new File("tmpFile.data");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}

randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");

tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

//delete()
public void delete(String employeeId) throws DAOException 
{
if(employeeId==null) throw new DAOException("Employee Id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employee Id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employeeId:"+employeeId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid employeeId:"+employeeId);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
String recordCountString=""; 
String fEmployeeId;
boolean employeeIdFound=false;
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(int x=1;x<=8;x++) randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
break;
}
}
if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("Invalid employeeId:"+employeeId);
}
randomAccessFile.seek(foundAt);
for(int x=1;x<=9;x++) randomAccessFile.readLine();
File tmpFile=new File("tmpFile.data");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}

randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
recordCount--;
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

//getAll()
public Set<EmployeeDTOInterface>getAll() throws DAOException 
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine().trim()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing.
}
employeeDTO.setGender(randomAccessFile.readLine().charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

//getByDesignationCode()
public Set<EmployeeDTOInterface>getByDesignationCode(int designationCode) throws DAOException 
{
if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Invalid designation code:"+designationCode);
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid designation code:"+designationCode);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid designation code:"+designationCode);
}
String fEmployeeId;
String fName;
int fDesignationCode;
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine().trim());
if(fDesignationCode!=designationCode)
{
for(int x=1;x<=6;x++) randomAccessFile.readLine();
continue;
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing.
}
employeeDTO.setGender(randomAccessFile.readLine().charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

//getByEmployeeId()
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException 
{
if(employeeId==null) throw new DAOException("EmployeeId is null");
if(employeeId.trim().length()==0) throw new DAOException("Length of employeeId is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employeeId:"+employeeId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid employeeId:"+employeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing.
}
employeeDTO.setGender((randomAccessFile.readLine().charAt(0))=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
for(int x=1;x<=8;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("Invalid employeeId:"+employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

//getByPANNumber()
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("Invalid pan number:"+panNumber);
if(panNumber.trim().length()==0) throw new DAOException("Invalid pan number:"+panNumber);
try
{
File file =new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid pan number:"+panNumber);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid pan number:"+panNumber);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid pan number:"+panNumber);
}
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
//do nothing.
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber))
{
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender(fGender=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
return employeeDTO;
}
}
throw new DAOException("Invalid pan number:"+panNumber); 
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}

//getByAadharCardNumber()
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException 
{
if(aadharCardNumber==null) throw new DAOException("Invalid aadharcard number:"+aadharCardNumber);
if(aadharCardNumber.trim().length()==0) throw new DAOException("Invalid aadharcard number:"+aadharCardNumber);
try
{
File file =new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid aadharcard number:"+aadharCardNumber);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid aadharcard number:"+aadharCardNumber);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid aadharcard number:"+aadharCardNumber);
}
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
//do nothing.
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender(fGender=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
return employeeDTO;
}
}
throw new DAOException("Invalid aadharcard number:"+aadharCardNumber); 
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
} 
}

//isDesignationAlloted()
public boolean isDesignationAlloted(int designationCode) throws DAOException 
{
if(designationCode<=0) throw new DAOException("Invalid designation code:"+designationCode);
if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Invalid designation code:"+designationCode);
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
randomAccessFile.readLine();
int fDesignationCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode)
{
randomAccessFile.close();
return true;
}
for(int x=1;x<=6;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
} 
}

//employeeIdExists()
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) return false;
if(employeeId.trim().length()==0) return false;
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
randomAccessFile.readLine();
String fEmployeeId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
randomAccessFile.close();
return true;
}
for(int x=1;x<=8;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

//panNumberExists()
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
if(panNumber.trim().length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile =new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fPanNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int x=1;x<=7;x++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber))
{
randomAccessFile.close();
return true;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

//aadharCardNumberExists()
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
if(aadharCardNumber.trim().length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile =new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fAadharCardNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int x=1;x<=8;x++) randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

//getCount()
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
} 
}

//getCountByDesignationCode()
public int getCountByDesignationCode(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code:"+designationCode);
if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Invalid designation code:"+designationCode);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int count=0;
int fDesignationCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode)
{
count++;
}
for(int x=1;x<=6;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return count;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
} 
}

}