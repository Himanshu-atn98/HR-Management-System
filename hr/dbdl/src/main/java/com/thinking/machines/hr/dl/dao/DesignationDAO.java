//Location->c:\java\java_projects\...
package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.io.*;
import java.sql.*;
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
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation:"+title+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into designation (title) values(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
designationDTO.setCode(code);
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
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
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code:"+code+" does not exist.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from designation where title=? AND code!=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation:"+title+" exist.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

// delete() 
public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code:"+code);
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code:"+code+" does not exits.");
} 
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

// getAll()
public Set<DesignationDTOInterface> getAll() throws DAOException
{
Set<DesignationDTOInterface> designations=new TreeSet<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from designation");
DesignationDTOInterface designationDTO;
while(resultSet.next())
{
designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());
designations.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close();
return designations; 
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

// getByCode()
public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code:"+code);
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code:"+code+" does not exits.");
} 
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(resultSet.getString("title").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

// getByTitle()
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null || title.trim().length()==0) throw new DAOException("Invalid title:"+title);
title=title.trim();
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery(); 
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation:"+title+" does not exist.");
}
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(title);
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

// codeExists()
public boolean codeExists(int code) throws DAOException
{
if(code<=0) return false;
boolean exist=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery(); 
exist=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}
return exist; 
}

// titleExists()
public boolean titleExists(String title) throws DAOException
{
if(title==null || title.trim().length()==0) return false;
boolean exist=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select title from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery(); 
exist=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}
return exist; 
}

// getCount()
public int getCount() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(*) as count_all from designation");
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
int count=resultSet.getInt("count_all");
resultSet.close();
preparedStatement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

}//class DesignationDAO Ends.
