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
 * WBTreeNode.java
 *
 * Created on 8 de agosto de 2002, 16:48
 */
package applets.commons;

import java.util.*;
/**
  * Nodo del XML obtenido del parser.
 *
 * XML node obtained from parser.
 *
 * @author  Administrador
 * @version 
 */
public class WBTreeNode {

    private WBTreeNode parent;                  //padre
    private String text=null;
    private String name="noname";
    private String prefix=null;
    private Vector child=null; 
    private int type=0;
    
    private Hashtable attributes=null;
    private Hashtable preAtt=null;
    
    public static int XMLTAG=0;
    public static int TAG=1;
    public static int TEXT=2;
    
    /** Creates new WBTreeNode */
    public WBTreeNode() {
        child=new Vector();
        attributes=new Hashtable();
        preAtt=new Hashtable();
    }
    
    /** Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getName() {
        return name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(java.lang.String name) {
        String p=null;
        if(name.indexOf(":")>-1)
        {
            p=name.substring(0,name.indexOf(":"));
            name=name.substring(name.indexOf(":")+1);
        }
        this.name=name;
        setPrefix(p);
    }
    
    /** Getter for property text.
     * @return Value of property text.
     */
    public java.lang.String getText() {
        return text;
    }
    
    /** Setter for property text.
     * @param text New value of property text.
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }
    
    /** Getter for property parent.
     * @return Value of property parent.
     */
    public WBTreeNode getParent() {
        return parent;
    }
    
    /** Setter for property parent.
     * @param parent New value of property parent.
     */
    public void setParent(WBTreeNode parent) {
        this.parent = parent;
    }
    
    public void addNode(WBTreeNode node)
    {
        child.add(node);
        node.setParent(this);
    }
    
    public void removeNode(WBTreeNode node)
    {
        child.remove(node);
        node.setParent(null);
    }

    public WBTreeNode addNode()
    {
        WBTreeNode temp=new WBTreeNode();
        addNode(temp);
        return temp;
    }

    /** Getter for property type.
     * @return Value of property type.
     */
    public int getType() {
        return type;
    }
    
    /** Setter for property type.
     * @param type New value of property type.
     */
    public void setType(int type) {
        this.type = type;
    }
    
    /** Getter for property child.
     * @return Value of property child.
     */
    public Vector getNodes() {
        return child;
    }

    /** Getter for property child.
     * @return Value of property child.
     */
    public WBTreeNode getNode(int index) {
        return (WBTreeNode)child.get(index);
    }

    /** Getter for property child.
     * @return Value of property child.
     */
    public WBTreeNode getFirstNode() {
        return (WBTreeNode)child.firstElement();
    }

    /** Getter for property child.
     * @return Value of property child.
     */
    public int getNodesSize() {
        return child.size();
    }
    
    public WBTreeNode findNode(String name)
    {
        WBTreeNode ret=null;
        for(int x=0;x<child.size();x++)
        {
            WBTreeNode aux=getNode(x);
            if(aux.getName().equals(name))
            {
                ret=aux;
                break;
            }else
            {
                ret=aux.findNode(name);
                if(ret!=null)break;
            }
        }        
        return ret;
    }
    
    public Iterator getNodesbyName(String name)
    {
        return getNodesbyNameNS(name,null);
    }
    
    public Iterator getNodesbyNameNS(String name, String ns)
    {
        ArrayList ret=new ArrayList();
        for(int x=0;x<child.size();x++)
        {
            if(getNode(x).getName().equals(name) && (ns==null || ns.equals(getNode(x).getNameSapace())))
            {
                ret.add(getNode(x));
            }
        }
        return ret.iterator();
    }    
    
    public WBTreeNode getNodebyName(String name)
    {
        return getNodebyNameNS(name,null);
    }
    
    public WBTreeNode getNodebyNameNS(String name, String ns)
    {
        for(int x=0;x<child.size();x++)
        {
            if(getNode(x).getName().equals(name) && (ns==null || ns.equals(getNode(x).getNameSapace())))
            {
                return getNode(x);
            }
        }        
        return null;
    }    
    
    public void addAttribute(String name, String value)
    {
        //System.out.println("name:("+name+") value:("+value+")");
        if(value.startsWith("\"") && value.endsWith("\""))
        {
            value=value.substring(1,value.length()-1);
        }
        
        
        String p=null;
        if(name.indexOf(":")>-1)
        {
            p=name.substring(0,name.indexOf(":"));
            name=name.substring(name.indexOf(":")+1);
            
            Hashtable pmap=(Hashtable)preAtt.get(p);
            if(pmap==null)
            {
                pmap=new Hashtable();
                preAtt.put(p,pmap);
            }
            pmap.put(name, value);
        }    
        
        attributes.put(name,value);
    }
    
    public String getAttribute(String name)
    {
        return (String)attributes.get(name);
    }
    
    public String toString()
    {
        String name=getAttribute("name");
        if(name!=null)return name;
        return getName();
    }
    
    public String getXML()
    {
        StringBuffer buf=new StringBuffer();
        try
        {
            if(getName()!=null)
            {
                
                String name=this.name;
                name=WBXMLParser.replaceStrChars(name);
                buf.append("<"+ name);
                Enumeration enum1=this.attributes.keys();
                while(enum1.hasMoreElements())
                {
                    String key=(String)enum1.nextElement();
                    //key=WBXMLParser.encode(key,"UTF-8");
                    String value=(String)this.attributes.get(key);
                    //value=WBXMLParser.encode(value,"UTF-8");
                    value=WBXMLParser.replaceStrChars(value);
                    buf.append(" "+key+"=\""+value+"\"");
                }
                buf.append(">");
                
                if(getText()==null)
                {
                    for(int i=0;i<this.child.size();i++)
                    {
                        WBTreeNode node=(WBTreeNode)this.child.get(i);
                        buf.append(node.getXML());
                    }
                }
                else
                {
                    String text=this.getText();
                    //text=WBXMLParser.encode(text,"UTF-8");
                    text=WBXMLParser.replaceStrChars(text);
                    buf.append(text);
                }
                buf.append("</"+ this.name+">");
            }
            else
            {
                if(this.getText()!=null)
                {
                    String text=this.getText();
                    //text=WBXMLParser.encode(text,"UTF-8");
                    text=WBXMLParser.replaceStrChars(text);
                    buf.append(text);
                }
                else
                {
                    for(int i=0;i<this.child.size();i++)
                    {
                        WBTreeNode node=(WBTreeNode)this.child.get(i);
                        buf.append(node.getXML());
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return buf.toString();
    }
    
    /**
     * Getter for property prefix.
     * @return Value of property prefix.
     */
    public java.lang.String getPrefix()
    {
        return prefix;
    }
    
    /**
     * Setter for property prefix.
     * @param prefix New value of property prefix.
     */
    public void setPrefix(java.lang.String prefix)
    {
        this.prefix = prefix;
    }
    
    public String getPrefixAttribute(String prefix, String name)
    {
        String ret=null;
        Hashtable pmap=(Hashtable)preAtt.get(prefix);
        if(pmap!=null)
        {
            ret=(String)pmap.get(name);
        }
        return ret;
    }
    
    /**
     * Getter for property ns.
     * @return Value of property ns.
     */
    String getPrefixNameSapace(String prefix)
    {
        String xmlns=getPrefixAttribute("xmlns",prefix);
        if(xmlns!=null)
        {
            return xmlns;
        }else
        {
            if(getParent()!=null)
            {
                return getParent().getPrefixNameSapace(prefix);
            }else
            {
                return null;
            }
        }        
    }
    
    /**
     * Getter for property ns.
     * @return Value of property ns.
     */
    public java.lang.String getNameSapace()
    {
        if(prefix==null)
        {
            String xmlns=(String)attributes.get("xmlns");
            if(xmlns!=null)
            {
                return xmlns;
            }else
            {
                if(getParent()!=null)
                {
                    return getParent().getNameSapace();
                }else
                {
                    return null;
                }
            }
        }else
        {
            String xmlns=getPrefixAttribute("xmlns",prefix);
            if(xmlns!=null)
            {
                return xmlns;
            }else
            {
                if(getParent()!=null)
                {
                    return getParent().getPrefixNameSapace(prefix);
                }else
                {
                    return null;
                }
            }
        }
    }
    
}
