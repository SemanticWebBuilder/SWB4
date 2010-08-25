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

package applets.filters;

import java.awt.Color;
import javax.swing.tree.*;
import javax.swing.*;
/**
 * Clase que representa un directorio existente en el servidor.
 * @author Victor Lorenzana
 */
public class Directory extends DefaultMutableTreeNode implements SelectableNode {
    
    /** Creates a new instance of Directory */
    String name, path;
    boolean enabled=true;
    JCheckBox component=new JCheckBox();
    boolean editable=true;
    boolean selected=false;
    JPanel panel=new JPanel();
    JLabel label=new JLabel();
    boolean checked=false;
    public Directory(String name,String path) {
        this.name=name;
        this.label.setBackground(Color.WHITE);
        this.component.setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);
        label.setOpaque(false);
        component.setOpaque(false);
        this.path=path;
        panel.add(component);
        panel.add(label);
        component.setText("");
        component.setOpaque(true);
        label.setText(name);
    }
    public void setSelected(boolean selected)
    {
        this.selected=selected;
        if(!this.editable)
        {
            this.panel.remove(this.component);
        }
    }
    public void setDirectory(String path)
    {
        this.path=path;        
    }
    public boolean isSelected()
    {
        return selected;
    }
    public java.awt.Component getComponent()
    {
        return panel;
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

    public JCheckBox getCheckBox()
    {
        return component;
    }

    public boolean getChecked()
    {
        return checked;
    }
    public boolean isEnabled()
    {
        return enabled;
    }
    public void setEnabled(boolean enabled)
    {
        this.enabled=enabled;
        this.component.setEnabled(enabled);
        this.component.setEnabled(enabled);
        this.label.setEnabled(enabled);
        for(int i=0;i<this.getChildCount();i++)
        {
            if(this.getChildAt(i) instanceof Directory)
            {
                Directory topic=(Directory)this.getChildAt(i);
                topic.setEnabled(enabled);
            }
        }        
    }
    public void setChecked(boolean checked)
    {
        this.checked=checked;
        component.setSelected(checked);
        if(checked)
        {

            for(int i=0;i<this.getChildCount();i++)
            {
                if(this.getChildAt(i) instanceof Directory)
                {
                    Directory directory=(Directory)this.getChildAt(i);
                    directory.setChecked(false);
                    directory.setEnabled(false);
                }
            }
             setLabelParent(this,EditFilter.colorPath);
        }
        else
        {

            for(int i=0;i<this.getChildCount();i++)
            {
                if(this.getChildAt(i) instanceof Directory)
                {
                    Directory topic=(Directory)this.getChildAt(i);
                    topic.setEnabled(true);
                }
            }
            setLabelParent(this,Color.BLACK);
        }
    }

    public void setLabelParent(SelectableNode lb,Color color)
    {

        lb.getLabel().setForeground(color);
        lb.getLabel().updateUI();
        if(lb.getParent() instanceof SelectableNode)
        {
            SelectableNode oparent=(SelectableNode)lb.getParent();
            oparent.getLabel().setForeground(color);
            oparent.getLabel().updateUI();
            setLabelParent(oparent,color);
        }
    }

    public JLabel getLabel()
    {
        return label;
    }

    public boolean isEditable()
    {
        return editable;
    }
    public void setEditable(boolean editable)
    {
        this.editable=editable;
    }
    
}
