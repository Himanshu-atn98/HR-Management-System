import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.pl.model.*;
class DesignationModelTestCase extends JFrame
{
private JTable table;
private DesignationModel designationModel;
private Container container;
private JScrollPane jsp;
DesignationModelTestCase()
{
designationModel=new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
//container.add(table);
container.add(jsp);
//For closing the window frame
setDefaultCloseOperation(EXIT_ON_CLOSE);
int width=500;
int height=400;
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setSize(width,height);
setVisible(true);
}
public static void main(String gg[])
{
DesignationModelTestCase dmtc=new DesignationModelTestCase();
}
}