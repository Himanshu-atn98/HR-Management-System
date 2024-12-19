package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;

//IMPORTING DL PACKAGES.
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;

public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManager designationManager=null;

//DesignationManager()
private DesignationManager() throws BLException
{
populateDataStructures();
}
//getDesignationManager()
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null)
{
designationManager=new DesignationManager();
}
return designationManager;
}
//populateDataStructures()
private void populateDataStructures() throws BLException
{
this.codeWiseDesignationsMap=new HashMap<>();
this.titleWiseDesignationsMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignationsSet;
DesignationDAOInterface designationDAO=new DesignationDAO();
dlDesignationsSet=designationDAO.getAll();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation:dlDesignationsSet)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationsMap.put(new Integer(designation.getCode()),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//populateDataStructures() Ends

//addDesignation()
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0)
{
blException.addException("code","Code should be zero");
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blException.addException("title","Title required");
}
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
blException.addException("title","Designation:"+title+" exists");
}
}
if(blException.hasException()) throw blException;
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
designation.setCode(code);
// AT THIS POINT WE ARE MAKING THE CLONE OF THE PL LAYER OBJECT TO ADD IN OUR DS AT BL
Designation dsDesignation=new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(new Integer(code),dsDesignation);
titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//addDesignation() Ends.

//updateDesignation()
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code =designation.getCode();
String title =designation.getTitle();
if(code<=0 || codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","Invalid code:"+code);
throw blException;
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blException.addException("title","Title required");
}
if(title.length()>0)
{
DesignationInterface d=titleWiseDesignationsMap.get(title);
if(d!=null && d.getCode()!=code) blException.addException("title","Designation:"+title+" exists");
}
if(blException.hasException()) throw blException;
try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
DesignationDTOInterface dlDesignation=new DesignationDTO();
dlDesignation.setCode(code);
dlDesignation.setTitle(title);
DesignationDAO designationDAO=new DesignationDAO();
designationDAO.update(dlDesignation);
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle());
designationsSet.remove(dsDesignation);
dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(code,dsDesignation);
titleWiseDesignationsMap.put(title,dsDesignation);
designationsSet.add(dsDesignation);

}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//update() Ends.

//removeDesignation()
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0 || codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","Invalid code");
throw blException;
} 
try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
DesignationDAO designationDAO=new DesignationDAO();
designationDAO.delete(code);
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//removeDesignation() Ends.

//getDesignationByCode()
public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0 || codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","Invalid code");
throw blException;
}
DesignationInterface designation=codeWiseDesignationsMap.get(code);
if(designation==null)
{
blException.addException("code","Invalid code");
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}//getDesignaionByCode() Ends.


//getDsDesignationByCode() 
//It's only for internal use. So it's with Default Access Specifier
//It returns actual object of Designation from our DS at BL to our DS at BL.

DesignationInterface getDsDesignationByCode(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0 || codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","Invalid code");
throw blException;
}
DesignationInterface designation=codeWiseDesignationsMap.get(code);
if(designation==null)
{
blException.addException("code","Invalid code");
throw blException;
}
return designation;
}//getDsesignaionByCode() Ends.

//getDesignationByTitle()
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException=new BLException();
if(title==null)
{
blException.addException("title","Title required");
throw blException;
}
title=title.trim();
if(title.length()==0) 
{
blException.addException("title","Title required");
throw blException;
}
DesignationInterface designation=titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
blException.addException("title","Invalid title");
throw blException;
}
 DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}//getDesignationByTitle() Ends.

//getDesignationCount()
public int getDesignationCount() throws BLException
{
return designationsSet.size();
}

//getDesignations()
public Set<DesignationInterface> getDesignations() throws BLException
{
Set<DesignationInterface> designations=new TreeSet<>();
designationsSet.forEach((designation)->{
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
designations.add(d);
});
return designations;
}//getDesignations() Ends.



//designationCodeExists()
public boolean designationCodeExists(int code) throws BLException
{
return codeWiseDesignationsMap.containsKey(code);
}

//designationTitleExists()
public boolean designationTitleExists(String title) throws BLException
{
BLException blException=new BLException();
if(title==null)
{
blException.addException("title","Title required");
throw blException;
}
title=title.trim();
if(title.length()==0) 
{
blException.addException("title","Title required");
throw blException;
}
return titleWiseDesignationsMap.containsKey(title.toUpperCase()); 
}

}//Class DesignationManager Ends.
