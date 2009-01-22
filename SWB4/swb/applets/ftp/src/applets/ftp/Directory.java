/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


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
    public String toString()
    {
        return name;
    }
    
}
