package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
//packages for pdf generation
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.io.image.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;
private String[] columnTitles;
private DesignationManagerInterface designationManager;
private Set<DesignationInterface> blDesignations;
public DesignationModel()
{
this.populateDataStructures();
}
//populateDataStructures()
private void populateDataStructures()
{
this.columnTitles=new String[2];
this.columnTitles[0]="S.No.";
this.columnTitles[1]="Designation";
try
{
designationManager=DesignationManager.getDesignationManager();
blDesignations=designationManager.getDesignations();
this.designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
this.designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}catch(BLException blException)
{
//donedone
}
}

//methods for the JTable
//getRowCount()
public int getRowCount()
{
return this.designations.size();
}
//getColumnCount()
public int getColumnCount()
{
return this.columnTitles.length;
}
//getColumnName()
public String getColumnName(int columnIndex)
{
return this.columnTitles[columnIndex];
}
//getValueAT()
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return this.designations.get(rowIndex).getTitle();
}
//getColumnClass()
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class;  // return Class.forName("java.lang.Integer");
return String.class;
}
//isCellEditable()
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
//Application Specific Methods
//add()
public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
//indexOfDesignation()
public int indexOfDesignation(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid designation:"+designation.getTitle());
throw blException;
}
//indexOfTitle()
public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{
Iterator<DesignationInterface>iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase())) return index;
}
else
{
if(d.getTitle().equalsIgnoreCase(title)) return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid title:"+title);
throw blException;
}
//update()
public void update(DesignationInterface designation) throws BLException
{
designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
//remove
public void remove(int code) throws BLException
{
designationManager.removeDesignation(code);
Iterator<DesignationInterface>iterator=this.designations.iterator();
int index=0;
while(iterator.hasNext())
{
if(iterator.next().getCode()==code) break;
index++;
}
if(index==this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid designation code:"+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}
//getDesignationAt()
public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0||index>=this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index:"+index);
throw blException;
}
return this.designations.get(index);
}
//exportToPDF()
public void exportToPDF(File file) throws BLException
{
try
{
if(file.exists()) file.delete();
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);
Image logoImage=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo1.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logoImage);
Paragraph companyNamePara=new Paragraph();
companyNamePara.add("LCK Corporation");
PdfFont companyNameFont =PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);
Paragraph reportTitlePara=new Paragraph("List of designations");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(15);
PdfFont columnTitleFont =PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont pageNumberFont =PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont =PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
//done done
Paragraph columnTitle1=new Paragraph("S.No.");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(14);
Paragraph columnTitle2=new Paragraph("Designation");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(14);
Paragraph pageNumberParagraph;
Paragraph dataParagraph; 
float topTableColumnWidth[]={1,5};
float dataTableColumnWidth[]={1,5};

int pageSize=5;
boolean newPage=true;
Table topTable;
Table pageNumberTable;
Table dataTable=null;
Cell  cell;
int numberOfPages=this.designations.size()/pageSize;
if((this.designations.size()%pageSize)!=0)
{
numberOfPages++;
}
DesignationInterface designation;
int serialNumber=0;
int pageNumber=0;
int x=0;
while(x<this.designations.size())
{
if(newPage==true)
{
//Create new header
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidth));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyNamePara);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
document.add(topTable);
pageNumberParagraph=new Paragraph("page:"+pageNumber+"/"+numberOfPages);
pageNumberParagraph.setFont(pageNumberFont);
pageNumberParagraph.setFontSize(13);
pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
document.add(pageNumberTable);
//done done
dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidth));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);
newPage=false;
}//if(newPage==true) Ends.
designation=this.designations.get(x);
//Add row to table
serialNumber++;
cell=new Cell();
dataParagraph=new Paragraph(String.valueOf(serialNumber));
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);
cell=new Cell();
dataParagraph=new Paragraph(designation.getTitle());
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
dataTable.addCell(cell);
x++;
if((serialNumber%pageSize==0) ||(x==this.designations.size()))
{
//Create Footer
document.add(dataTable);
document.add(new Paragraph("Software by: LCK Corporation"));
if(x<this.designations.size())
{
//Adding new page to document
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}// While() Loop Ends.
document.close();
}catch(Exception exception)
{
BLException blException;
blException=new BLException();
blException.setGenericException(exception.getMessage());
throw blException;
}
}
} //class Ends.