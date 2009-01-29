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
 * Topic.java
 *
 * Created on 7 de enero de 2005, 06:36 PM
 */

package applets.filterSection;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.*;
/**
 * Clase que representa un topic dentro del árbol de secciones, este nodo es
 * representado como un checkbox.
 * @author Victor Lorenzana
 */
public class Topic extends DefaultMutableTreeNode implements SelectableNode{
    
    /** Creates a new instance of Topic */
    HashSet listeners=new HashSet();
    boolean selected;
    boolean enabled=true;
    boolean willExpand=false;
    TristateCheckBox chk=new TristateCheckBox();
    JPanel panel=new JPanel();
    JLabel label=new JLabel();
    String id,topicmap;    
    String realoadPath;
    String iconName;
    public Topic(String iconName,String topicmap,String id,String text,String realoadPath,ImageIcon img) {
        panel.setOpaque(false);
        this.label.setBackground(Color.WHITE);
        label.setOpaque(false);
        chk.setOpaque(false);
        //panel.setOpaque(true);
        label.setText(text);
        chk.setText("");
        chk.setState(TristateCheckBox.NOT_SELECTED);
        this.id=id;            
        panel.add(chk);
        panel.add(label);        
        if(img!=null)
            label.setIcon(img);
        this.topicmap=topicmap;
        this.realoadPath=realoadPath;
        this.iconName=iconName;
    }
    public JLabel getLabel()
    {
        return this.label;
    }
    public void addTopicListener(Topic topic)
    {
        if(topic!=this && topic.getRealoadPath()!=null && this.getRealoadPath()!=null && this.getRealoadPath().equals(topic.getRealoadPath()))
        {
            if(topic.getChecked().equals(TristateCheckBox.SELECTED) || topic.getChecked().equals(TristateCheckBox.DONT_CARE))
            {
                this.setCheckedTopic(TristateCheckBox.NOT_SELECTED);
            }
            this.listeners.add(topic);
        }
    }
    public String getIconName()
    {
        return iconName;
    }
    public String getRealoadPath()
    {
        return realoadPath;
    }
    public boolean getWillExpand()
    {
        return this.willExpand;
    }
    public void setWillExpand(boolean willExpand)
    {
        this.willExpand=willExpand;
    }
    public String getTopicMapID()
    {
        return topicmap;
    }
    public String getID()
    {
        return id;
    }
    public TristateCheckBox getCheckBox() {
        return chk;
    }
    
    public TristateCheckBox.State getChecked() {
        return chk.getState();
    }
    
    public java.awt.Component getComponent() {
        return panel;
    }
    public void setLabelParent(SelectableNode lb,Color color)
    {
        
        lb.getLabel().setForeground(color);
        lb.getLabel().updateUI();
        if(lb.getParent() instanceof SelectableNode)
        {
            SelectableNode parent=(SelectableNode)lb.getParent();
            parent.getLabel().setForeground(color);
            parent.getLabel().updateUI();
            setLabelParent(parent,color);
        } 
    }
    public void nextChecked()
    {
        chk.nextState();
        setChecked(chk.getState());
    }
    public void setChecked(TristateCheckBox.State state) {
        this.chk.setState(state);
        if(chk.getState()==TristateCheckBox.SELECTED || chk.getState()==TristateCheckBox.DONT_CARE)
        {
            setLabelParent(this,new Color(81,137,42));     
        }
        if(chk.getState()==TristateCheckBox.DONT_CARE)
        {
                 
            for(int i=0;i<this.getChildCount();i++)
            {
                if(this.getChildAt(i) instanceof Topic)
                {
                    Topic topic=(Topic)this.getChildAt(i);
                    topic.setChecked(TristateCheckBox.NOT_SELECTED);
                    topic.setEnabled(false);
                }
            }
             
        }
        else
        {
                     
            for(int i=0;i<this.getChildCount();i++)
            {
                if(this.getChildAt(i) instanceof Topic)
                {
                    Topic topic=(Topic)this.getChildAt(i);  
                    topic.setEnabled(true);
                }
            }
            setLabelParent(this,Color.BLACK);  
        }
        Iterator topics=this.listeners.iterator();
        while(topics.hasNext())
        {
            Topic topic=(Topic)topics.next();
            topic.setCheckedTopic(state);
        }
    }
    public void setCheckedTopic(TristateCheckBox.State state)
    {
        
        this.chk.setState(state);
        if(this.chk.getState()==TristateCheckBox.SELECTED || this.chk.getState()==TristateCheckBox.DONT_CARE)
        {
            setLabelParent(this,new Color(81,137,42));  
        }
        if(this.chk.getState()==TristateCheckBox.DONT_CARE)
        {
                    
            for(int i=0;i<this.getChildCount();i++)
            {
                if(this.getChildAt(i) instanceof Topic)
                {
                    Topic topic=(Topic)this.getChildAt(i);
                    topic.setCheckedTopic(TristateCheckBox.NOT_SELECTED);
                    topic.setEnabled(false);
                }
            }
            
        }
        else
        {
                     
            for(int i=0;i<this.getChildCount();i++)
            {
                if(this.getChildAt(i) instanceof Topic)
                {
                    Topic topic=(Topic)this.getChildAt(i);  
                    topic.setEnabled(true);
                }
            }
            setLabelParent(this,Color.BLACK);  
        }       
    }
    public void setEnabledTopic(boolean enabled)
    {
        this.enabled=enabled;
        this.panel.setEnabled(enabled);
        this.chk.setEnabled(enabled);
        this.label.setEnabled(enabled);
        for(int i=0;i<this.getChildCount();i++)
        {
            if(this.getChildAt(i) instanceof Topic)
            {
                Topic topic=(Topic)this.getChildAt(i);  
                topic.setEnabled(enabled);
            }
        }        
    }
    public void setEnabled(boolean enabled)
    {
        this.enabled=enabled;
        this.panel.setEnabled(enabled);
        this.chk.setEnabled(enabled);
        this.label.setEnabled(enabled);
        for(int i=0;i<this.getChildCount();i++)
        {
            if(this.getChildAt(i) instanceof Topic)
            {
                Topic topic=(Topic)this.getChildAt(i);  
                topic.setEnabled(enabled);
            }
        }
        Iterator it=this.listeners.iterator();
        while(it.hasNext())
        {
            Topic topic=(Topic)it.next();
            topic.setEnabledTopic(enabled);
        }
    }
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setSelected(boolean selected)
    {        
        this.selected=selected;
    }
    
    public boolean isSelected() {
        return selected;
    }
}
