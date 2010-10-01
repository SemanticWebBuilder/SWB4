/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 


/*
 * jTableFileModel.java
 *
 * Created on 11 de noviembre de 2004, 10:55 AM
 */

package applets.ftp;

import java.awt.Color;
import java.text.DecimalFormat;
import javax.swing.table.*;
import java.util.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 * Clase que representa el modelo de datos necesarios para mostrarlos en una tabla
 * dentro del applet.
 * @author Victor Lorenzana
 */


public class jTableFileModel extends AbstractTableModel{
    
    /** Creates a new instance of jTableFileModel */
    private String[] columnNames =new String[3];
    ArrayList<File> files=new ArrayList<File>();
    ArrayList<FileListener> listeners=new ArrayList<FileListener>();
    JTable table;
    Locale locale;
    SizeFileComparator sizeFileComparator=new SizeFileComparator();
    DateTimeFileComparator dateTimeFileComparator=new DateTimeFileComparator();
    FileNameCompartor fileNameCompartor=new FileNameCompartor();
    public jTableFileModel(JTable table,Locale locale) {
       this.table=table;
       this.locale=locale;
       columnNames[0]=java.util.ResourceBundle.getBundle("applets/ftp/jTableFileModel",locale).getString("name");
       columnNames[1]=java.util.ResourceBundle.getBundle("applets/ftp/jTableFileModel",locale).getString("size");
       columnNames[2]=java.util.ResourceBundle.getBundle("applets/ftp/jTableFileModel",locale).getString("lastupdate");
       
    }
    public void addFileListener(FileListener listener)
    {
        listeners.add(listener);
    }
    public void clear()
    {
        this.files.clear();
        firemodificatedlisteners();
    }
    @Override
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }
    public int getColumnCount() {
        return this.columnNames.length;
    }
    private void firemodificatedlisteners()
    {
        Iterator it=listeners.iterator();
        while(it.hasNext())
        {
            FileListener fl=(FileListener)it.next();
            fl.Modificated();
        }
    }
    public int getRowCount() {
        return files.size();
    }
    @Override
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }
    @Override
    public Class getColumnClass(int col)
    {       
        switch(col)
        {
            case 0:                
                return JLabel.class;                   
            case 1:
                return JLabel.class;
            case 2:
                return JLabel.class;
            default:
                return null;
        }   
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {        
        File file=(File)files.toArray()[rowIndex];
        switch(columnIndex)
        {
            case 0:
                return file.getLabel();        
            case 1:
                
                return file.getSizeLabel();
            case 2:
                return file.getDateLabel();
            default:
                return null;
        }    
    }
    public File getFile(int index)
    {   
        return (File)this.files.toArray()[index];
    }
    public void reorderByName()
    {
        fileNameCompartor.toogle();
        Collections.sort(this.files,fileNameCompartor);
    }
    public void reorderBySize()
    {
        sizeFileComparator.toogle();
        Collections.sort(this.files,sizeFileComparator);
    }
    public void reorderByDate()
    {
        dateTimeFileComparator.toogle();
        Collections.sort(this.files,dateTimeFileComparator);
    }
    public void addFile(File file)
    {        
        this.files.add(file); 
        table.updateUI();
        firemodificatedlisteners();
    }
    public void removeFile(File file)
    {               
        this.files.remove(file);
        table.updateUI();
        firemodificatedlisteners();
    }
    
    
}
