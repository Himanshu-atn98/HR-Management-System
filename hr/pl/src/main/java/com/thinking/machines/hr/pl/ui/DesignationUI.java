package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JTable designationTable;
private JLabel titleLabel;
private JLabel searchLabel;
private JLabel searchErrorLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JScrollPane scrollPane;
private DesignationModel designationModel;
private Container container;
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;
private ImageIcon clearTextFieldIcon;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon deleteIcon;
private ImageIcon cancelIcon;
private ImageIcon pdfIcon;
private ImageIcon saveIcon;
private ImageIcon updateIcon;
public DesignationUI()
{
initComponents();
setAppearance();
addListeners();
setViewMode();
designationPanel.setViewMode();
}

//initComponents()
private void initComponents()
{
logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo_icon.png"));
clearTextFieldIcon=new ImageIcon(this.getClass().getResource("/icons/clearTextField_icon.png"));
addIcon=new ImageIcon(this.getClass().getResource("/icons/add_icon.png"));
editIcon=new ImageIcon(this.getClass().getResource("/icons/edit_icon.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icons/back_icon.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icons/remove_icon.png"));
pdfIcon=new ImageIcon(this.getClass().getResource("/icons/pdf_icon.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/icons/save_icon.png"));
updateIcon=new ImageIcon(this.getClass().getResource("/icons/update_icon.png"));
setIconImage(logoIcon.getImage());
designationModel=new DesignationModel();
titleLabel=new JLabel("Designation");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
//clearSearchTextFieldButton=new JButton("x");
clearSearchTextFieldButton=new JButton(clearTextFieldIcon);
searchErrorLabel=new JLabel("");
designationTable=new JTable(designationModel);
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
designationPanel=new DesignationPanel();
}

//setAppearance()
private void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
titleLabel.setFont(titleFont);
Font captionFont=new Font("Verdana",Font.BOLD,16);
searchLabel.setFont(captionFont);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
searchTextField.setFont(dataFont);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
designationTable.setFont(dataFont);
designationTable.setRowHeight(35);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(400);
JTableHeader header=designationTable.getTableHeader();
header.setFont(columnHeaderFont);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
container.setLayout(null);
int lm=0;  //lm->leftmargine
int tm=0;  //tm->topmargine
titleLabel.setBounds(lm+10,tm+10,200,40);
searchErrorLabel.setBounds(lm+10+100+5+400+10-75,tm+10+10+20,100,20);
searchLabel.setBounds(lm+10,tm+10+40+10,100,30);
searchTextField.setBounds(lm+10+100+5,tm+10+40+10,400,30);
clearSearchTextFieldButton.setBounds(lm+10+100+5+400+10,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,565,300);
designationPanel.setBounds(lm+10,tm+10+40+10+30+10+300+10,565,200);
container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(designationPanel);
//For closing the window frame
setDefaultCloseOperation(EXIT_ON_CLOSE);
int width=600;
int height=660;
setSize(width,height);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation(((d.width)/2-(width/2)),((d.height)/2-(height/2)));
}
//addListeners();
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
designationTable.getSelectionModel().addListSelectionListener(this);
}
//valueChanged()
public void valueChanged(ListSelectionEvent ev)
{
int selectedRowIndex=designationTable.getSelectedRow();
try
{
DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();
}
}
//changedUpdate()
public void changedUpdate(DocumentEvent ev)
{
searchDesignation();
}
//removeUpdate()
public void removeUpdate(DocumentEvent ev)
{
searchDesignation();
}

//insertUpdate()
public void insertUpdate(DocumentEvent ev)
{
searchDesignation();
}
//searchDesignation()
private void searchDesignation()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not found");
return;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}
//OuterClass-> setViewMode()
private void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
designationTable.setEnabled(true);
}
}
//OuterClass-> setAddMode()
private void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
//OuterClass-> setEditMode()
private void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
//OuterClass-> setDeleteMode()
private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
//OuterClass-> setExportToPDFMode()
private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
//Inner class starts
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JPanel buttonPanel;
private DesignationInterface designation;
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
initComponents();
setAppearance();
addListeners();
}
//initComponents()
private void initComponents()
{
titleCaptionLabel=new JLabel("Designation");
designation=null;
titleLabel=new JLabel("");
titleTextField=new JTextField();
//clearTitleTextFieldButton=new JButton("x");
clearTitleTextFieldButton=new JButton(clearTextFieldIcon);
buttonPanel=new JPanel();
/*
addButton=new JButton("A");
editButton=new JButton("E");
cancelButton=new JButton("C");
deleteButton=new JButton("D");
exportToPDFButton=new JButton("E");
*/
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
}
//setAppearance()
private void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm=0;
int tm=0;
titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+10+5+110,tm+20,400,30);
titleTextField.setBounds(lm+10+5+110,tm+20,350,30);
clearTitleTextFieldButton.setBounds(lm+10+5+110+350+5,tm+20,30,30);
buttonPanel.setBounds(50,tm+20+30+30,465,75);
buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
addButton.setBounds(70,12,50,50);
editButton.setBounds(70+50+20,12,50,50);
cancelButton.setBounds(70+50+20+50+20,12,50,50);
deleteButton.setBounds(70+50+20+50+20+50+20,12,50,50);
exportToPDFButton.setBounds(70+50+20+50+20+50+20+50+20,12,50,50);
buttonPanel.setLayout(null);
buttonPanel.add(addButton);
buttonPanel.add(editButton);
buttonPanel.add(cancelButton);
buttonPanel.add(deleteButton);
buttonPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleLabel);
add(titleTextField);
add(clearTitleTextFieldButton);
add(buttonPanel);
}
//addDesignation()
private boolean addDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required");
this.titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setTitle(title);
int rowIndex=0;
try
{
designationModel.add(d);
rowIndex=designationModel.indexOfDesignation(d);
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException())
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
this.titleTextField.requestFocus();
return false;
}
//updateDesignation()
private boolean updateDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designaton required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try
{
designationModel.update(d);
int rowIndex=0;
rowIndex=designationModel.indexOfDesignation(d);
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
titleTextField.requestFocus();
return false;
}
//removeDesignation()
private void removeDesignation()
{
try
{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+"?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" deleted");
this.clearDesignation();
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
}

//addListeners()
private void addListeners()
{
this.clearTitleTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
titleTextField.setText("");
titleTextField.requestFocus();
}
});
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setAddMode();
}
else
{
if(addDesignation()) setViewMode();
}
}
});
this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setEditMode();
}
else
{
if(updateDesignation()) setViewMode();
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});
this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setDeleteMode();
}
});
this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showSaveDialog(DesignationUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
try
{
File selectedFile=jfc.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
File file=new File(pdfFile);
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect path:"+file.getAbsolutePath());
return;
}
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data exported to:"+file.getAbsolutePath());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
}
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}
});
}
//InnerClass-> setViewMode()
private void setViewMode()
{
DesignationUI.this.setViewMode();
//this.addButton.setText("A");
this.addButton.setIcon(addIcon);
//this.editButton.setText("E");
this.editButton.setIcon(editIcon);
this.titleLabel.setVisible(true);
this.titleTextField.setVisible(false);
clearTitleTextFieldButton.setVisible(false);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
if(designationModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
//InnerClass-> setAddMode()
private void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleLabel.setVisible(false);
this.titleTextField.setText("");
this.titleTextField.setVisible(true);
this.titleTextField.requestFocus();
clearTitleTextFieldButton.setVisible(true);
//this.addButton.setText("S");
this.addButton.setIcon(saveIcon);
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
//InnerClass-> setEditMode()
private void setEditMode()
{
if(designationTable.getSelectedRow()<0||designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setText(this.designation.getTitle());
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
clearTitleTextFieldButton.setVisible(true);
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
//this.editButton.setText("U");
this.editButton.setIcon(updateIcon);
}
//InnerClass-> setDeleteMode()
private void setDeleteMode()
{
if(designationTable.getSelectedRow()<0||designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to delete");
return ;
}
DesignationUI.this.setDeleteMode();
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false); 
removeDesignation();
DesignationUI.this.setViewMode();
this.setViewMode();
}
//InnerClass-> setExportToPDFMode()
private void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false); 
}
//setDesigntion()
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(designation.getTitle());
}
//clearDesignation()
public void clearDesignation()
{
this.designation=null;
titleLabel.setText("");
}
}//Inner class Ends.
}//class Ends.