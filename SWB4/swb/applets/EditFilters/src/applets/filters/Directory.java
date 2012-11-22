/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gesti?n de procesos de negocio mediante el uso de 
* tecnolog?a sem?ntica, que permite el modelado, configuraci?n, ejecuci?n y monitoreo de los procesos de negocio
* de una organizaci?n, as? como el desarrollo de componentes y aplicaciones orientadas a la gesti?n de procesos.
* 
* Mediante el uso de tecnolog?a sem?ntica, SemanticWebBuilder Process puede generar contextos de informaci?n
* alrededor de alg?n tema de inter?s o bien integrar informaci?n y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la informaci?n se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creaci?n original del Fondo de 
* Informaci?n y Documentaci?n para la Industria INFOTEC.
* 
* INFOTEC pone a su disposici?n la herramienta SemanticWebBuilder Process a trav?s de su licenciamiento abierto 
* al p?blico (?open source?), en virtud del cual, usted podr? usarlo en las mismas condiciones con que INFOTEC 
* lo ha dise?ado y puesto a su disposici?n; aprender de ?l; distribuirlo a terceros; acceder a su c?digo fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los t?rminos y 
* condiciones de la LICENCIA ABIERTA AL P?BLICO que otorga INFOTEC para la utilizaci?n de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garant?a sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni impl?cita ni 
* expl?cita, siendo usted completamente responsable de la utilizaci?n que le d? y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposici?n la
* siguiente direcci?n electr?nica: 
*  http://www.semanticwebbuilder.org.mx
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
        if(!path.startsWith("/"))
        {
            int pos=path.indexOf("/");
            if(pos!=-1)
                path=path.substring(pos);
            else
            {
                path="/";
            }
        }
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
