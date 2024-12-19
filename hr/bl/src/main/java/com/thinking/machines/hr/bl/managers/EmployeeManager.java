package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
//IMPORTING DL PACKAGES.
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;

public class EmployeeManager implements EmployeeManagerInterface
{

private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private static EmployeeManager employeeManager=null;

//EmployeeManager()
private EmployeeManager() throws BLException
{
populateDataStructures();
}

//getEmployeeManager()
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null)
{
employeeManager=new EmployeeManager();
}
return employeeManager;
}
//populateDataStructures()
private void populateDataStructures() throws BLException
{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try
{
Set<EmployeeDTOInterface> dlEmployeesSet;
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
dlEmployeesSet=employeeDAO.getAll();
EmployeeInterface employee;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:dlEmployeesSet)
{
employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
/*
DesignationInterface dsDesignation=((DesignationManager)designationManager).getDsDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(dsDesignation);
*/
//Instead of this we can write one line code...
employee.setDesignation(((DesignationManager)designationManager).getDsDesignationByCode(dlEmployee.getDesignationCode()));
//employee.setDateOfBirth(dlEmployee.getDateOfBirth());
employee.setDateOfBirth((Date)dlEmployee.getDateOfBirth().clone());
employee.setGender(dlEmployee.getGender()=='M'?(GENDER.MALE):(GENDER.FEMALE));
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber()); 
this.employeeIdWiseEmployeesMap.put(dlEmployee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(dlEmployee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(dlEmployee.getAadharCardNumber(),employee);
this.employeesSet.add(employee);
ets=this.designationCodeWiseEmployeesMap.get(dlEmployee.getDesignationCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
//Updating the the designation code wise map
designationCodeWiseEmployeesMap.put(dlEmployee.getDesignationCode(),ets);
}
else
{
ets.add(employee);
}
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//populateDataStructures() Ends


// addEmployee()
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("Employee required");
throw blException;
}
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
Boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
 String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
//VALIDATIONS APPLY FOR THE DATA OF EMPLOYEE
if(employeeId!=null && employeeId.trim().length()>0)
{
blException.addException("employeeId","EmployeeId should be nill/empty");
}
if(name==null)
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required");
}
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
int designationCode=0;
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid designation");
}
}
if(dateOfBirth==null) blException.addException("dateOfBirth","Date of birth required");
if(gender==' ') blException.addException("gender","Gender required");
//No need of validation for isIndian
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1) blException.addException("basicSalary","Basic salary can not be negative");
}
if(panNumber==null)
{
blException.addException("panNumber","PAN number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("panNumber","PAN number required");
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadharcard number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addException("aadharCardNumer","Aadharcard number required");
}
//Checking Duplicacy of PAN number and Aadharcard Number
if(panNumber!=null && panNumber.length()>0)
{
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase())==true)
{
blException.addException("panNumber","PAN number:"+panNumber+" exists");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase())==true)
{
blException.addException("aadharCardNumber","Aadharcard number:"+aadharCardNumber+" exists");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
//dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dlEmployee.setGender(gender=='M'?(GENDER.MALE):(GENDER.FEMALE));
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.add(dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDsDesignationByCode(designation.getCode()));
//dsEmployee.setDateOfBirth(dateOfBirth);
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?(GENDER.MALE):(GENDER.FEMALE));
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
employeesSet.add(dsEmployee);
designationCode=dsEmployee.getDesignation().getCode();
Set<EmployeeInterface> ets=designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(designationCode,ets);
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//addEmployee() Ends.

	// updateEmployee()
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("Employee required");
throw blException;
}
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
Boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
//VALIDATIONS APPLY FOR THE DATA OF EMPLOYEE
if(employeeId==null)
{
blException.addException("employeeId","EmployeeId required");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) 
{
blException.addException("employeeId","EmployeeId required");
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid employee Id:"+employeeId);
throw blException;
}
}
}
if(name==null)
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required");
}
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
int designationCode=0;
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid designation");
}
}
if(dateOfBirth==null) blException.addException("dateOfBirth","Date of birth required");
if(gender==' ') blException.addException("gender","Gender required");
//No need of validation for isIndian
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1) blException.addException("basicSalary","Basic salary can not be negative");
}
if(panNumber==null)
{
blException.addException("panNumber","PAN number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("panNumber","PAN number required");
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadharcard number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addException("aadharCardNumer","Aadharcard number required");
}
//Checking Duplicacy of PAN number and Aadharcard Number
if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface emp=panNumberWiseEmployeesMap.get(panNumber);
if(emp!=null && employeeId.equalsIgnoreCase(emp.getEmployeeId())==false)
{
blException.addException("panNumber","PAN number:"+panNumber+" exists");
} 
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface emp=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber);
if(emp!=null && employeeId.equalsIgnoreCase(emp.getEmployeeId())==false)
{
blException.addException("aadharCardNumber","Aadharcard number:"+aadharCardNumber+" exists");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPanNumber=dsEmployee.getPANNumber();
String oldAdharCardNumber=dsEmployee.getAadharCardNumber();
int oldDesignationCode=dsEmployee.getDesignation().getCode();
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
//dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dlEmployee.setGender(gender=='M'?(GENDER.MALE):(GENDER.FEMALE));
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.update(dlEmployee);
//Now UPDATING THE DS AT BL
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDsDesignationByCode(designation.getCode()));
//dsEmployee.setDateOfBirth(dateOfBirth);
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?(GENDER.MALE):(GENDER.FEMALE));
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(dsEmployee.getEmployeeId().toUpperCase());
panNumberWiseEmployeesMap.remove(oldPanNumber.toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(oldAdharCardNumber.toUpperCase());
employeesSet.add(dsEmployee);
employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(oldDesignationCode);
ets.remove(dsEmployee);
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//updateEmployee() Ends.

// removeEmployee()
public void removeEmployee(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null)
{
blException.addException("employeeId","EmployeeId required");
throw blException;
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) 
{
blException.addException("employeeId","EmployeeId required");
throw blException;
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid employee Id:"+employeeId);
throw blException;
}
}
}
if(blException.hasException())
{
throw blException;
}
try
{
EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.delete(employeeId);
//Now REMOVING/DELETING THE DS AT BL
employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(dsEmployee.getEmployeeId().toUpperCase());
panNumberWiseEmployeesMap.remove(dsEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(dsEmployee.getAadharCardNumber().toUpperCase());
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
ets.remove(dsEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//removeEmployee() Ends.

// getEmployeeByEmployeeId()
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.trim().toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("employeeId","Invalid employeeId:"+employeeId);
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
//employee.setDateOfBirth(dsEmployee.getDateOfBirth());
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender(dsEmployee.getGender()=='M'?(GENDER.MALE):(GENDER.FEMALE));
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}
// getEmployeeByPANNumber()
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
EmployeeInterface dsEmployee=panNumberWiseEmployeesMap.get(panNumber.trim().toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("panNumber","Invalid PAN Number:"+panNumber);
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
//employee.setDateOfBirth(dsEmployee.getDateOfBirth());
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender(dsEmployee.getGender()=='M'?(GENDER.MALE):(GENDER.FEMALE));
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}

// getEmployeeByAadharCardNumber()
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
EmployeeInterface dsEmployee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.trim().toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("aadharCardNumber","Invalid Aadhar card number:"+aadharCardNumber);
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
//employee.setDateOfBirth(dsEmployee.getDateOfBirth());
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender(dsEmployee.getGender()=='M'?(GENDER.MALE):(GENDER.FEMALE));
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
 
}

// getEmployeeCount()
public int getEmployeeCount() throws BLException
{
return employeesSet.size(); 
}

// employeeIdExists()
public boolean employeeIdExists(String employeeId) // throws BLException --->Because the method is not going to raise any kind of exception.
{
return employeeIdWiseEmployeesMap.containsKey(employeeId.trim().toUpperCase());
}

// employeePANNumberExists()
public boolean employeePANNumberExists(String panNumber) // throws BLException --->Because the method is not going to raise any kind of exception.
{
return panNumberWiseEmployeesMap.containsKey(panNumber.trim().toUpperCase());
}

// employeeAadharCardNumberExists()
public boolean employeeAadharCardNumberExists(String aadharCardNumber) // throws BLException --->Because the method is not going to raise any kind of exception.
{
return aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.trim().toUpperCase());
}

// getEmployees()
public Set<EmployeeInterface> getEmployees() throws BLException
{
Set<EmployeeInterface> employees=new TreeSet<>();
EmployeeInterface employee;
DesignationInterface designation;
for(EmployeeInterface dsEmployee:employeesSet)
{
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
//employee.setDateOfBirth(dsEmployee.getDateOfBirth());
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender(dsEmployee.getGender()=='M'?(GENDER.MALE):(GENDER.FEMALE));
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}

// isDesignationAlloted()
public boolean isDesignationAlloted(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}

// getEmployeesByDesignationCode()
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designationCode<=0 || designationManager.designationCodeExists(designationCode)==false)
{
BLException blException=new BLException();
blException.addException("designationCode","Invalid designation code:"+designationCode);
throw blException;
}
Set<EmployeeInterface> dsEmployeesSet;
dsEmployeesSet=designationCodeWiseEmployeesMap.get(designationCode);
Set<EmployeeInterface> ets=new TreeSet<>();
if(dsEmployeesSet==null) 
{
return ets;
}
EmployeeInterface employee;
for(EmployeeInterface dsEmployee:dsEmployeesSet)
{
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?(GENDER.MALE):(GENDER.FEMALE));
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
ets.add(employee);
}
return ets; 
}

// getEmployeeCountByDesignationCode()
public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
{
Set<EmployeeInterface> ets;
ets=designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null) return 0;
return ets.size(); 
}

}//Class EmployeeManager Ends.
