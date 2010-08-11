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
 * Directory.java
 *
 * Created on 11 de noviembre de 2004, 11:31 AM
 */

package applets.ftp;

import javax.swing.tree.*;
import javax.swing.*;
import java.awt.*;
/**
 * Clase que representa un directorio existente en el servidor.
 * @author Victor Lorenzana
 */
public class Directory extends DefaultMutableTreeNode {
    
    /** Creates a new instance of Directory */
    String name, path;
    JLabel component=new JLabel();
    boolean selected=false;
    public Directory(String name,String path) {
        this.name=name;
        this.path=path;
        component.setText(name);        
        component.setOpaque(true);
    }
    public void setSelected(boolean selected)
    {
        this.selected=selected;
       /* if(selected)
        {
            this.component.setBackground(new Color(204,204,204));
        }
        else
        {
            this.component.setBackground(Color.white);
        }
        this.component.updateUI();*/
    }
    public void setName(String name)
    {
        this.name=name;  
        this.getComponent().setText(this.name);
        this.getComponent().updateUI();
    }
    public void setDirectory(String path)
    {
        this.path=path;        
    }
    public boolean isSelected()
    {
        return selected;
    }
    public JLabel getComponent()
    {
        return component;
    }
    public String getName()
    {
        return name;
    }
    public String getDirectory()
    {
        return path;
    }
    public void rename(String name)
    {
        this.name=name;
    }
    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Directory other = (Directory) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
}
