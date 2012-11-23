/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/




/*
 * WBTopicMap.java
 *
 * Created on 8 de agosto de 2002, 18:07
 */

package applets.mapsadm;

import java.util.*;

import applets.commons.*;

/**
 *
 * @author  Administrador
 * @version 
 */
public class WBTopic {

    private WBTreeNode node;
    private String id;
    private String name=null;
    private boolean havechild=false;
    private int active=0;
    private int accesslevel=0;
    private boolean cheched=true;
    private WBTopic parent=null;
    private WBTopicMap topicMap=null;
    private boolean virtual=false;
    private String link=null;
    private Object treeNode=null;
    
    /** Creates new WBTopicMap */
    public WBTopic(WBTopicMap topicMap, WBTreeNode node) {
        this.topicMap=topicMap;
        this.node=node;
        init();
    }
    
    private void init()
    {
        for(int x=0;x<node.getNodesSize();x++)
        {
            String name=node.getNode(x).getName();
            //System.out.println("Tag:"+name);
            if(name.equals("id"))
            {
                id=node.getNode(x).getFirstNode().getText();
            }
            else if(name.equals("name"))
            {
                //System.out.println("Value:"+node.getNode(x).getAttribute("value"));
                String scope=node.getNode(x).getAttribute("scope");
                if(this.name==null)
                {   
                    if(scope!=null && scope.equals(topicMap.getLang()))
                        this.name=node.getNode(x).getAttribute("value");
                }
            }
            else if(name.equals("child"))
            {
                havechild=true;
            }
            else if(name.equals("active"))
            {
                active=Integer.parseInt(node.getNode(x).getFirstNode().getText());
            }
            else if(name.equals("havechild"))
            {
                cheched=false;
                String hvchild=node.getNode(x).getFirstNode().getText();
                if(hvchild.equals("true"))havechild=true;
            }
            else if(name.equals("linked"))
            {
                link=node.getNode(x).getFirstNode().getText();
                virtual=true;
                havechild=false;
            }
        }
        if(name==null)
        {
            Iterator it=node.getNodesbyName("name");
            if(it.hasNext())
            {
                WBTreeNode node=(WBTreeNode)it.next();
                name=node.getAttribute("value");
            }            
        }
    }
    
    public String toString()
    {
        String ret="Topic";
        if(name!=null)ret=name;
        /*
        Iterator it=getDataNode().getNodesbyName("name");
        if(it.hasNext())
        {
            WBTreeNode node=(WBTreeNode)it.next();
            ret=node.getAttribute("value");
        }
        */
        return ret;
    }
    
    public void refreshName()
    {
        String first=null;
        name=null;
        Iterator it=getDataNode().getNodesbyName("name");
        if(it.hasNext())
        {
            WBTreeNode node=(WBTreeNode)it.next();
            String scope=node.getAttribute("scope");
            if(this.name==null)
            {   
                if(scope!=null && scope.equals(topicMap.getLang()))
                    this.name=node.getAttribute("value");
            }
            if(first==null)first=node.getAttribute("value");
        }
        if(name==null)
        {
            name=first;
        }
    }

    /** Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getName() {
        return name;
    }
    
    /** Getter for property id.
     * @return Value of property id.
     */
    public java.lang.String getId() {
        return id;
    }
    
    /** Getter for property havechild.
     * @return Value of property havechild.
     */
    public boolean haveChild() {
        return havechild;
    }
    
    public Enumeration getChild()
    {
        Vector ret=new Vector();
        for(int x=0;x<node.getNodesSize();x++)
        {
            String name=node.getNode(x).getName();
            if(name.equals("child"))
            {
                WBTreeNode childnode=node.getNode(x);
                for(int y=0;y<childnode.getNodesSize();y++)
                {
                    ret.add(childnode.getNode(y));
                }
            }
        }
        return ret.elements();
    }
    
    /** Getter for property cheched.
     * @return Value of property cheched.
     */
    public boolean isCheched() {
        return cheched;
    }
    
    /** Setter for property cheched.
     * @param cheched New value of property cheched.
     */
    public void setCheched(boolean cheched) {
        this.cheched = cheched;
    }
    
    /** Getter for property node.
     * @return Value of property node.
     */
    public WBTreeNode getDataNode() {
        return node;
    }
    
    /** Getter for property changed.
     * @return Value of property changed.
     */
    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        if(virtual)
        {
           havechild=false; 
        }
        this.virtual=virtual;
    }
    
    /** Getter for property active.
     * @return Value of property active.
     */
    public int getActive() {
        return active;
    }
    
    /** Setter for property active.
     * @param active New value of property active.
     */
    public void setActive(int active) {
        this.active = active;
    }
    
    /** Getter for property level.
     * @return Value of property level.
     */
    public int getAccessLevel() {
        if(virtual)return 0;
        return accesslevel;
    }
    
    /** Setter for property level.
     * @param level New value of property level.
     */
    public void setAccessLevel(int level) {
        this.accesslevel = level;
    }
    
    /** Getter for property parent.
     * @return Value of property parent.
     */
    public WBTopic getParent() {
        return parent;
    }
    
    /** Setter for property parent.
     * @param parent New value of property parent.
     */
    public void setParent(WBTopic parent) {
        this.parent = parent;
    }
    
    /** Getter for property topicMap.
     * @return Value of property topicMap.
     */
    public WBTopicMap getTopicMap()
    {
        return topicMap;
    }
    
    /** Setter for property topicMap.
     * @param topicMap New value of property topicMap.
     */
    public void setTopicMap(WBTopicMap topicMap)
    {
        this.topicMap = topicMap;
    }
    
    /** Getter for property link.
     * @return Value of property link.
     */
    public java.lang.String getLink()
    {
        return link;
    }
    
    /** Setter for property link.
     * @param link New value of property link.
     */
    public void setLink(java.lang.String link)
    {
        this.link = link;
    }
    
    /** Getter for property treeNode.
     * @return Value of property treeNode.
     *
     */
    public java.lang.Object getTreeNode()
    {
        return treeNode;
    }
    
    /** Setter for property treeNode.
     * @param treeNode New value of property treeNode.
     *
     */
    public void setTreeNode(java.lang.Object treeNode)
    {
        this.treeNode = treeNode;
    }
    
}
