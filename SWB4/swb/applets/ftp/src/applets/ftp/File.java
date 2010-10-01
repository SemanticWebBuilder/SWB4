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
 * File.java
 *
 * Created on 11 de noviembre de 2004, 10:59 AM
 */

package applets.ftp;

import java.text.DecimalFormat;
import javax.swing.*;
/**
 * Clase que representa un archivo existente en el servidor.
 * @author Victor Lorenzana
 */
public class File {
    
    /** Creates a new instance of File */
    String name,path,size,lastupdate;
    Directory dir;
    JLabel label=new JLabel();
    JLabel labelSize=new JLabel();
    JLabel labelDate=new JLabel();
    public File(Directory dir,String name,String path,String size,String lastupdate)
    {        
        
        this.name=name;
        this.path=path;
        this.size=size;
        this.lastupdate=lastupdate;
        this.dir=dir;
        label.setText(name);
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/file.gif")));
        label.setOpaque(true);
        labelSize.setOpaque(true);
        labelDate.setOpaque(true);
        label.updateUI();
        initSizeLabel();
        initDateLabel();
    }
    private void initDateLabel()
    {
        labelDate.setText(this.getLastUpdate());
        labelDate.setHorizontalAlignment(SwingConstants.RIGHT);
    }
    private void initSizeLabel()
    {
        String size=this.getSize();
                try
                {
                    int isize=Integer.parseInt(size);
                    double sizekb=0;
                    if(isize>0)
                    {
                        sizekb=isize/1024d;
                        if(sizekb==0)
                        {
                            sizekb=1;
                        }
                    }
                    DecimalFormat df=new DecimalFormat("###,###,##0.00");
                    size="("+df.format(isize)+" bytes)      "+df.format(sizekb)+" KB ";
                }
                catch(NumberFormatException nfe)
                {
                    nfe.printStackTrace();
                }
                DecimalFormat df=new DecimalFormat();

                labelSize.setText(size);
                labelSize.setHorizontalAlignment(SwingConstants.RIGHT);
    }
    public Directory getDirectory()
    {
        return dir;
    }
    public JLabel getSizeLabel()
    {
        return labelSize;
    }
    public JLabel getDateLabel()
    {
        return labelDate;
    }
    public JLabel getLabel()
    {
        return label;
    }
    public int hashCode()
    {
        return this.getName().hashCode();
    }
    public void setName(String name)
    {
        this.name=name;
        label.setText(name);
        label.updateUI();
    }
    public void setPath(String path)
    {
        this.path=path;
    }
    public String getName()
    {
        return name;
    }
    public String getPath()
    {
        return path;
    }
    public String getSize()
    {
        return size;
    }
    public String getLastUpdate()
    {
        return lastupdate;
    }
    
}
