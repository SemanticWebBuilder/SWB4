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
 * EditFilter.java
 *
 * Created on 23 de noviembre de 2004, 12:30 PM
 */

package applets.filters;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.Color;

import applets.commons.*;
/**
 * Formulario que muestra las secciones para egenrar filtros en WB3.0.
 * @author Victor Lorenzana
 */
public class EditFilter extends javax.swing.JApplet {
    
    private final String PRM_JSESS="jsess";
    private final String PRM_CGIPATH="cgipath";
    
    private String cgiPath="/gtw.jsp";
    private String jsess="",repository;                    //session del usuario
    private URL url=null;
    private String topicmap;
    private String id;
    String url_script;
    HashMap icons=new HashMap();
    
    HashSet reloads=new HashSet();
    Locale locale;
    /** Initializes the applet EditFilter */
    public void init() {
        locale=Locale.getDefault();
        if(this.getParameter("locale")!=null && !this.getParameter("locale").equals(""))
        {
            try
            {
                
                locale=new Locale(this.getParameter("locale"));                
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
            }
        }        
        initComponents();
        this.setJMenuBar(this.jMenuBar1);
        jsess=this.getParameter(PRM_JSESS);
        cgiPath=this.getParameter(PRM_CGIPATH);
        topicmap=this.getParameter("tm");
        this.id=this.getParameter("idfilter");
        url_script=this.getParameter("location");
        try {
            url=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),cgiPath);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }        
        jTree.setCellRenderer(new CheckRenderer());        
        jTree.addMouseListener(new CheckListener()); 
        
        jTreeMenus.setCellRenderer(new CheckRenderer());        
        jTreeMenus.addMouseListener(new CheckListener()); 
        
        jTreeElements.setCellRenderer(new CheckRenderer());        
        jTreeElements.addMouseListener(new CheckListener()); 
        
        loadMenus();
        loadElements();
        
        
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>initTreeFilter</cmd></req>";
        String resp=this.getData(xml);                                        
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode nodexml=parser.parse(resp);
        if(nodexml.getFirstNode()!=null)
        {
            WBTreeNode res=nodexml.getFirstNode();
            WBTreeNode config=res.getNodebyName("config");            
            if(config!=null)
            {
                WBTreeNode icons=config.getFirstNode();                
                if(icons!=null)
                {
                    Iterator itIcons=icons.getNodesbyName("icon");
                    while(itIcons.hasNext())
                    {
                        WBTreeNode icon=(WBTreeNode)itIcons.next();                        
                        try
                        {
                            
                            String path="/applets/filters/"+icon.getAttribute("path");                        
                            ImageIcon oicon=new javax.swing.ImageIcon(getClass().getResource(path));
                            this.icons.put(icon.getAttribute("id"),oicon);
                        }
                        catch(Exception e)
                        {
                            System.out.println("icon not found: "+icon.getAttribute("path"));
                            e.printStackTrace(System.out);                            
                        }
                    }
                }
            }
            Iterator nodes=res.getNodesbyName("node");
            while(nodes.hasNext())
            {
                WBTreeNode node=(WBTreeNode)nodes.next();                
                ImageIcon icon=(ImageIcon)this.icons.get(node.getAttribute("icon"));                
                String name=node.getAttribute("name");
                TopicMap root=new TopicMap(node.getAttribute("icon"),node.getAttribute("id"),name,node.getAttribute("reload"),icon);
                boolean editable=true;            
                if(node.getAttribute("access")!=null)
                {
                    try
                    {
                        if(Integer.parseInt(node.getAttribute("access"))==2)
                        {
                            editable=true;
                        }
                        else
                        {
                            editable=false;
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace(System.out);
                    }
                }
                root.setEditable(editable);
                this.jTree.setModel(new DefaultTreeModel(root));                
                fillTreeTopicMap(node,root);
                this.jTree.updateUI();
            }
        }
        loadFilter();
    }
    private void loadElements(Topic root,WBTreeNode eroot)
    {
        Iterator topics=eroot.getNodesbyName("topic");
        while(topics.hasNext())
        {
            WBTreeNode etopic=(WBTreeNode)topics.next(); 
            boolean editable=true;
            if(etopic.getAttribute("canModify")!=null)
            {
                try
                {
                    editable=new Boolean(etopic.getAttribute("canModify")).booleanValue();
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                }
            }
            Topic child=new Topic("", etopic.getAttribute("topicmap"), etopic.getAttribute("id"), etopic.getAttribute("name"),  etopic.getAttribute("reload"), null);                        
            child.setEditable(editable);
            root.add(child);             
            loadElements(child,etopic);
            findTopic(this.jTree,child);
        }
    }
    private void loadElements()
    {
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getElements</cmd></req>";
        String resp=this.getData(xml);                
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode nodexml=parser.parse(resp);
        if(nodexml.getFirstNode()!=null)
        {
            WBTreeNode res=nodexml.getFirstNode();
            Iterator topics=res.getNodesbyName("topic");
            while(topics.hasNext())
            {
                WBTreeNode etopic=(WBTreeNode)topics.next();
                boolean editable=true;
                if(etopic.getAttribute("canModify")!=null)
                {
                    try
                    {
                        editable=new Boolean(etopic.getAttribute("canModify")).booleanValue();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace(System.out);
                    }
                }
                Topic root=new Topic("", etopic.getAttribute("topicmap"), etopic.getAttribute("id"), etopic.getAttribute("name"),  etopic.getAttribute("reload"), null);                
                root.setEditable(editable);
                this.jTreeElements.setModel(new DefaultTreeModel(root));  
                loadElements(root,etopic);
                findTopic(this.jTree,root);
                this.jTreeElements.updateUI();
            }
        }
    }
    private void loadMenus(Topic root,WBTreeNode eroot)
    {
        Iterator topics=eroot.getNodesbyName("topic");
        while(topics.hasNext())
        {
            WBTreeNode etopic=(WBTreeNode)topics.next();
            boolean editable=true;
            if(etopic.getAttribute("canModify")!=null)
            {
                try
                {
                    editable=new Boolean(etopic.getAttribute("canModify")).booleanValue();
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                }
            }
            Topic child=new Topic("", etopic.getAttribute("topicmap"), etopic.getAttribute("id"), etopic.getAttribute("name"), etopic.getAttribute("reload"), null);                        
            child.setEditable(editable);
            root.add(child);              
            loadMenus(child,etopic);
            findTopic(this.jTree,child);
        }
    }
    private void loadMenus()
    {
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getMenus</cmd></req>";
        String resp=this.getData(xml);                
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode nodexml=parser.parse(resp);
        if(nodexml.getFirstNode()!=null)
        {
            WBTreeNode res=nodexml.getFirstNode();
            Iterator topics=res.getNodesbyName("topic");
            while(topics.hasNext())
            {
                WBTreeNode etopic=(WBTreeNode)topics.next();
                boolean editable=true;
                if(etopic.getAttribute("canModify")!=null)
                {
                    try
                    {
                        editable=new Boolean(etopic.getAttribute("canModify")).booleanValue();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace(System.out);
                    }
                }
                Topic root=new Topic("", etopic.getAttribute("topicmap"), etopic.getAttribute("id"), etopic.getAttribute("name"),  etopic.getAttribute("reload"), null);                
                root.setEditable(editable);
                this.jTreeMenus.setModel(new DefaultTreeModel(root));  
                loadMenus(root,etopic);
                findTopic(this.jTree,root);
                this.jTreeMenus.updateUI();
            }
        }
    }
    private void loadFilterMenu(Topic root,WBTreeNode enode)
    {           
       String reload=enode.getAttribute("reload");                        
       if(reload.startsWith("getTopic"))
       {
           String topicmap=enode.getAttribute("topicmap");                                      
           int childs=this.jTreeMenus.getModel().getChildCount(root);
           for(int ichild=0;ichild<childs;ichild++)
           {                               
               if(this.jTreeMenus.getModel().getChild(root, ichild) instanceof Topic)
               {                                   
                    Topic topic=(Topic)this.jTreeMenus.getModel().getChild(root, ichild);
                    if(topic.getTopicMapID().equals(topicmap) && enode.getAttribute("id").equals(topic.getID()))
                    {
                        topic.setChecked(true);
                        this.jTreeMenus.expandPath(new TreePath(topic.getPath()));
                        this.jTreeMenus.updateUI();
                    }
                    else
                    {
                        loadFilterMenu(topic, enode);
                    }
               }
           }
       }        
    }
    private void loadFilterMenu(WBTreeNode efilter)
    {
        WBTreeNode menus=efilter.getNodebyName("menus");
        if(menus!=null)
        {
            Iterator it=menus.getNodesbyName("node");
            while(it.hasNext())
            {
               WBTreeNode enode=(WBTreeNode)it.next();
               String reload=enode.getAttribute("reload");                                       
               if(reload.startsWith("getTopic"))
               {
                   String topicmap=enode.getAttribute("topicmap");                           
                   Object objroot=this.jTreeMenus.getModel().getRoot();                           
                   if(objroot instanceof Topic)
                   {
                        Topic topic=(Topic)objroot;
                        if(topic.getTopicMapID().equals(topicmap) && enode.getAttribute("id").equals(topic.getID()))
                        {
                            topic.setChecked(true);
                            this.jTreeMenus.expandPath(new TreePath(topic.getPath()));
                            this.jTreeMenus.updateUI();
                            return;
                        }
                   }
                   int childs=this.jTreeMenus.getModel().getChildCount(objroot);
                   for(int ichild=0;ichild<childs;ichild++)
                   {                               
                       if(this.jTreeMenus.getModel().getChild(objroot, ichild) instanceof Topic)
                       {                                   
                            Topic topic=(Topic)this.jTreeMenus.getModel().getChild(objroot, ichild);
                            if(topic.getTopicMapID().equals(topicmap) && enode.getAttribute("id").equals(topic.getID()))
                            {
                                topic.setChecked(true);
                                this.jTreeMenus.expandPath(new TreePath(topic.getPath())); 
                                this.jTreeMenus.updateUI();
                            }
                            else
                            {
                                loadFilterMenu(topic, enode);
                            }
                       }
                   }
               }
            }    
        }
    }
    private void loadFilterElements(Topic root,WBTreeNode enode)
    {      
       
       String reload=enode.getAttribute("reload");                        
       if(reload.startsWith("getTopic"))
       {
           String topicmap=enode.getAttribute("topicmap");                                      
           int childs=this.jTreeElements.getModel().getChildCount(root);
           for(int ichild=0;ichild<childs;ichild++)
           {                               
               if(this.jTreeElements.getModel().getChild(root, ichild) instanceof Topic)
               {                                   
                    Topic topic=(Topic)this.jTreeElements.getModel().getChild(root, ichild);                                        
                    if(topic.getTopicMapID().equals(topicmap) && enode.getAttribute("id").equals(topic.getID()))
                    {                        
                        topic.setChecked(true);
                        this.jTreeElements.expandPath(new TreePath(topic.getPath()));
                        this.jTreeElements.updateUI();
                    }
                    else
                    {
                        loadFilterElements(topic, enode);
                    }
               }
           }
       }        
    }
    private void loadFilterElements(WBTreeNode efilter)
    {
        WBTreeNode elements=efilter.getNodebyName("elements");
        if(elements!=null)
        {
            Iterator it=elements.getNodesbyName("node");
            while(it.hasNext())
            {
               WBTreeNode enode=(WBTreeNode)it.next();
               String reload=enode.getAttribute("reload");                        
               if(reload.startsWith("getTopic"))
               {
                   String topicmap=enode.getAttribute("topicmap");                           
                   Object objroot=this.jTreeElements.getModel().getRoot();                           
                   if(objroot instanceof Topic)
                   {
                        Topic topic=(Topic)objroot;
                        if(topic.getTopicMapID().equals(topicmap) && enode.getAttribute("id").equals(topic.getID()))
                        {
                            topic.setChecked(true);
                            this.jTreeElements.expandPath(new TreePath(topic.getPath()));
                            this.jTreeElements.updateUI();
                            return;
                        }
                   }
                   int childs=this.jTreeElements.getModel().getChildCount(objroot);
                   for(int ichild=0;ichild<childs;ichild++)
                   {                               
                       if(this.jTreeElements.getModel().getChild(objroot, ichild) instanceof Topic)
                       {                                   
                            Topic topic=(Topic)this.jTreeElements.getModel().getChild(objroot, ichild);
                            if(topic.getTopicMapID().equals(topicmap) && enode.getAttribute("id").equals(topic.getID()))
                            {
                                topic.setChecked(true);
                                this.jTreeElements.expandPath(new TreePath(topic.getPath()));
                                this.jTreeElements.updateUI();
                            }
                            else
                            {
                                loadFilterElements(topic, enode);
                            }
                       }
                   }
               }
            }   
        }
    }
    private void loadFilter()
    {
        if(id!=null)
        {
            String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getFilter</cmd><id>"+ this.id +"</id></req>";
            String resp=this.getData(xml);                
            WBXMLParser parser=new WBXMLParser();
            WBTreeNode nodexml=parser.parse(resp);
            WBTreeNode eresp=nodexml.getFirstNode();
            if(eresp!=null)
            {
                WBTreeNode efilter=eresp.getNodebyName("filter");
                if(efilter!=null)
                {
                    this.jTextFieldName.setText(efilter.getAttribute("name"));
                    WBTreeNode edesc=efilter.getNodebyName("description");
                    if(edesc!=null)
                    {
                        this.jTextAreaDescription.setText(edesc.getFirstNode().getText());
                    }
                    loadFilterElements(efilter);
                    loadFilterMenu(efilter);
                    
                    WBTreeNode sites=efilter.getNodebyName("sites");
                    if(sites!=null)
                    {
                        Iterator it=sites.getNodesbyName("node");
                        while(it.hasNext())
                        {
                           WBTreeNode enode=(WBTreeNode)it.next();
                           String reload=enode.getAttribute("reload");                        
                           if(reload.startsWith("getTopic"))
                           {
                               String topicmap=enode.getAttribute("topicmap");                           
                               Object objroot=this.jTree.getModel().getRoot();                           
                               int childs=this.jTree.getModel().getChildCount(objroot);
                               for(int ichild=0;ichild<childs;ichild++)
                               {                               
                                   if(this.jTree.getModel().getChild(objroot, ichild) instanceof TopicMap)
                                   {                                   
                                        TopicMap map=(TopicMap)this.jTree.getModel().getChild(objroot, ichild);
                                        if(map.getID().equals(topicmap))
                                        {
                                            this.jTree.expandPath(new TreePath(map.getPath()));                           
                                            if(enode.getAttribute("reload")!=null && !enode.getAttribute("reload").equals(""))
                                            {
                                                StringTokenizer st=new StringTokenizer(enode.getAttribute("reload"),".");
                                                if(st.nextToken().equals("getTopic"))
                                                {
                                                    String path=enode.getAttribute("path");
                                                    StringTokenizer stpath=new StringTokenizer(path,"|");                                                    
                                                    Object root=map;                                                     
                                                    while(stpath.hasMoreElements())
                                                    {
                                                        String topicid=stpath.nextToken();                                                        
                                                        int ichilds=this.jTree.getModel().getChildCount(root);
                                                        for(int i=0;i<ichilds;i++)
                                                        {
                                                            if(this.jTree.getModel().getChild(root,i) instanceof Topic)
                                                            {                                                                
                                                                Topic tp=(Topic)this.jTree.getModel().getChild(root,i);
                                                                if(tp.getID().equalsIgnoreCase(topicid))
                                                                {
                                                                    tp.setLabelParent(tp, new Color(81,137,42));
                                                                    this.jTree.expandPath(new TreePath(tp.getPath()));                           
                                                                    root=tp;                                                                   
                                                                    break;
                                                                }                                                                
                                                            }
                                                        }
                                                        
                                                    }
                                                    /*Root shortcuts=null;
                                                    int l=this.jTree.getModel().getChildCount(map);
                                                    for(int i=0;i<l;i++)
                                                    {
                                                        if(this.jTree.getModel().getChild(map, i) instanceof Root)
                                                        {
                                                            shortcuts=(Root)this.jTree.getModel().getChild(map, i);
                                                            break;
                                                        }
                                                    }
                                                    String path="/applets/filters/images/f_general.gif";
                                                    ImageIcon oicon=new javax.swing.ImageIcon(getClass().getResource(path));
                                                    if(shortcuts==null)
                                                    {
                                                        shortcuts=new Root(java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("sections_permiss"),oicon);
                                                        map.add(shortcuts);
                                                    }                                           
                                                    Topic nodetopic=new Topic(enode.getAttribute("icon"), enode.getAttribute("topicmap"),  enode.getAttribute("id"), "",enode.getAttribute("reload"),oicon);
                                                    shortcuts.add(nodetopic);
                                                    this.reLoadTopic(topicmap,nodetopic);*/
                                                }
                                            }
                                        }
                                   }
                               }

                           }
                        }
                        it=sites.getNodesbyName("node");
                        while(it.hasNext())
                        {
                           WBTreeNode enode=(WBTreeNode)it.next();
                           String reload=enode.getAttribute("reload");                       
                           if(!CheckNode(reload))
                           {
                               reloads.add(reload);
                           }                            
                        }
                    }
                }
            }
        }
    }
    boolean CheckNode(String reload)
    {
        Object root=this.jTree.getModel().getRoot();
        if(root instanceof TopicMap)
        {
            TopicMap map=(TopicMap)root;
            if(map.getRealoadPath()!=null && map.getRealoadPath().equals(reload))
            {
                map.setChecked(true);                    
                    this.jTree.expandPath(new TreePath(map.getPath()));
                    return true;
            }
        }
        int childs=this.jTree.getModel().getChildCount(root);
        for(int i=0;i<childs;i++)
        {
            if(this.jTree.getModel().getChild(root,i) instanceof TopicMap)
            {
                TopicMap map=(TopicMap)this.jTree.getModel().getChild(root,i);
                if(map.getRealoadPath()!=null && map.getRealoadPath().equals(reload))
                {
                    map.setChecked(true);                    
                    this.jTree.expandPath(new TreePath(map.getPath()));
                    return true;
                }
                else
                {
                    CheckNode(map, reload);
                }
            }
        }
        return false;
    }
    boolean CheckNode(Topic root,String reload)
    {
        int childs=this.jTree.getModel().getChildCount(root);
        for(int i=0;i<childs;i++)
        {
            if(this.jTree.getModel().getChild(root,i) instanceof Topic)
            {
                Topic topic=(Topic)this.jTree.getModel().getChild(root,i);                
                if(topic.getRealoadPath()!=null && topic.getRealoadPath().equals(reload))
                {
                    topic.setChecked(true);
                    this.jTree.expandPath(new TreePath(topic.getPath()));
                    return true;
                }
                else
                {
                    CheckNode(topic, reload);
                }
            }
        }
        return false;
    }
    boolean CheckNode(TopicMap root,String reload)
    {
        int childs=this.jTree.getModel().getChildCount(root);
        for(int i=0;i<childs;i++)
        {
            if(this.jTree.getModel().getChild(root,i) instanceof Topic)
            {
                Topic topic=(Topic)this.jTree.getModel().getChild(root,i);
                if(topic.getRealoadPath()!=null && topic.getRealoadPath().equals(reload))
                {
                    topic.setChecked(true);
                    this.jTree.expandPath(new TreePath(topic.getPath()));
                    return true;
                }
                else
                {
                    CheckNode(topic, reload);
                }
            }
        }
        return false;
    }
    void fillTreeTopicMap(WBTreeNode node,TopicMap root)
    {
        
        Iterator nodes=node.getNodesbyName("node");        
        while(nodes.hasNext())
        {
            WBTreeNode ochild=(WBTreeNode)nodes.next();        
            TopicMap tm=new TopicMap(ochild.getAttribute("icon"),ochild.getAttribute("id"),ochild.getAttribute("name"),ochild.getAttribute("reload"),(ImageIcon)this.icons.get(ochild.getAttribute("icon")));            
            boolean editable=true;            
            if(ochild.getAttribute("access")!=null)
            {
                try
                {
                    if(Integer.parseInt(ochild.getAttribute("access"))==2)
                    {
                        editable=true;
                    }
                    else
                    {
                        editable=false;
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                }
            }
            tm.setEditable(editable);
            root.add(tm);
            fillTreeTopic(ochild,ochild.getAttribute("id"),tm);
        }
    }
    
    void fillTreeTopic(WBTreeNode node,String topicmap,Topic tp)
    {        
        Iterator nodes=node.getNodesbyName("node");
        while(nodes.hasNext())
        {
            WBTreeNode ochild=(WBTreeNode)nodes.next();           
            Topic topic=new Topic(ochild.getAttribute("icon"),topicmap,ochild.getAttribute("id"),ochild.getAttribute("name"),ochild.getAttribute("reload"),(ImageIcon)this.icons.get(ochild.getAttribute("icon")));                        
            boolean editable=true;            
            if(ochild.getAttribute("access")!=null)
            {
                try
                {
                    if(Integer.parseInt(ochild.getAttribute("access"))==2)
                    {
                        editable=true;
                    }
                    else
                    {
                        editable=false;
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                }
            }
            topic.setEditable(editable);
            tp.add(topic);
            this.findTopic(this.jTree,topic);            
            if(topic.getParent()!=null)
            {
                if(topic.getParent() instanceof Topic)
                {
                    Topic parent=(Topic)topic.getParent();
                    if(parent.getChecked())
                    {
                        topic.setChecked(false);
                        topic.setEnabled(false);
                    }    
                    if(!parent.isEnabled())
                    {                        
                        topic.setEnabled(false);
                    }                    
                }
                else if(topic.getParent() instanceof TopicMap)
                {
                    TopicMap parent=(TopicMap)topic.getParent();
                    if(parent.getChecked())
                    {
                        topic.setChecked(false);
                        topic.setEnabled(false);
                    }                          
                }
            }
            
            Iterator it=reloads.iterator();
            while(it.hasNext())
            {
               String reload=(String)it.next();
               if(reload!=null && ochild.getAttribute("reload")!=null && ochild.getAttribute("reload").equals(reload))
               {
                   this.jTree.expandPath(new TreePath(topic.getPath()));
                   topic.setChecked(true);
               }               
            }
            
            WBTreeNode evt=ochild.getNodebyName("events");
            if(evt!=null)
            {
                WBTreeNode willexpand=evt.getNodebyName("willExpand");
                if(willexpand!=null)
                {
                    topic.setWillExpand(true);
                }                
            }
            fillTreeTopic(ochild,topicmap,topic);            
        }
    }
    void fillTreeTopic(WBTreeNode node,String topicmap,TopicMap tm)
    {
        Iterator nodes=node.getNodesbyName("node");
        while(nodes.hasNext())
        {
            WBTreeNode ochild=(WBTreeNode)nodes.next();
            
            Topic topic=new Topic(ochild.getAttribute("icon"),topicmap,ochild.getAttribute("id"),ochild.getAttribute("name"),ochild.getAttribute("reload"),(ImageIcon)this.icons.get(ochild.getAttribute("icon")));            
            boolean editable=true;
            if(ochild.getAttribute("access")!=null)
            {
                try
                {
                    if(Integer.parseInt(ochild.getAttribute("access"))==2)
                    {
                        editable=true;
                    }
                    else
                    {
                        editable=false;
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                }
            }
            topic.setEditable(editable);
            tm.add(topic);
            this.findTopic(this.jTree,topic);
            /*this.findTopic(this.jTreeElements,topic);
            this.findTopic(this.jTreeMenus,topic);*/
            if(topic.getParent()!=null)
            {
                if(topic.getParent() instanceof Topic)
                {
                    Topic parent=(Topic)topic.getParent();
                    if(parent.getChecked())
                    {
                        topic.setChecked(false);
                        topic.setEnabled(false);
                    }
                    if(!parent.isEnabled())
                    {                       
                        
                        topic.setEnabled(false);
                    }   
                   
                }
                else if(topic.getParent() instanceof TopicMap)
                {
                    TopicMap parent=(TopicMap)topic.getParent();
                    if(parent.getChecked())
                    {
                        topic.setChecked(false);
                        topic.setEnabled(false);
                    }
                }
            }
            
            Iterator it=reloads.iterator();
            while(it.hasNext())
            {
               String reload=(String)it.next();
               if(reload!=null && ochild.getAttribute("reload")!=null && ochild.getAttribute("reload").equals(reload))
               {
                   this.jTree.expandPath(new TreePath(topic.getPath()));
                   topic.setChecked(true);
               }               
            }
            WBTreeNode evt=ochild.getNodebyName("events");
            if(evt!=null)
            {
                WBTreeNode willexpand=evt.getNodebyName("willExpand");
                if(willexpand!=null)
                {
                    topic.setWillExpand(true);
                }                
            }
            fillTreeTopic(ochild,topicmap,topic);            
        }
    }
    public String getData(String xml)
    {
        StringBuffer ret=new StringBuffer();
        try {           
            
            URLConnection urlconn=url.openConnection();
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            urlconn.setRequestProperty("Content-Type","application/xml");
            urlconn.setDoOutput(true);
            PrintWriter pout = new PrintWriter(urlconn.getOutputStream());
            pout.println(xml);
            pout.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                ret.append(inputLine);
                ret.append("\n");
            }
            in.close();
        }catch(Exception e){System.out.println("Error to open service..."+e);}
        return ret.toString();
    }
    private void findTopic(JTree jTree,Object root,Topic topic)
    {         
        int childs=jTree.getModel().getChildCount(root);
        if(root instanceof Topic)
        {
            Topic node=(Topic)root;
            node.addTopicListener(topic);
            topic.addTopicListener(node);
        }
        for(int i=0;i<childs;i++)
        {
            Object ochild=jTree.getModel().getChild(root, i);
            if(ochild instanceof Topic)
            {
                Topic tp=(Topic)ochild;
                tp.addTopicListener(topic);
                topic.addTopicListener(tp);                
            }
            findTopic(jTree,ochild,topic);
        }
    }
    private void findTopic(JTree jTree,Topic topic)
    {
        Object root=jTree.getModel().getRoot();
        int childs=jTree.getModel().getChildCount(root);
        if(root instanceof Topic)
        {
            Topic node=(Topic)root;
            node.addTopicListener(topic);
            topic.addTopicListener(node);
        }
        for(int i=0;i<childs;i++)
        {
            Object ochild=jTree.getModel().getChild(root, i);            
            findTopic(jTree,ochild,topic);
        }
    }
    public void addNode(String topicmap,WBTreeNode ochild,DefaultMutableTreeNode tp,boolean checked,boolean expand)
    {        
        if(tp==null || ochild==null)
            return;
                
        Topic topic=new Topic(ochild.getAttribute("icon"),topicmap,ochild.getAttribute("id"),ochild.getAttribute("name"),ochild.getAttribute("reload"),(ImageIcon)this.icons.get(ochild.getAttribute("icon")));                    
        boolean editable=true;
        if(ochild.getAttribute("access")!=null)
        {
            try
            {
                if(Integer.parseInt(ochild.getAttribute("access"))==2)
                {
                    editable=true;
                }
                else
                {
                    editable=false;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
            }
        }
        topic.setEditable(editable);
        if(expand)
        {
            TreeNode parent=topic.getParent();
            while(parent!=null)
            {                
                if(parent instanceof DefaultMutableTreeNode)                    
                {
                    DefaultMutableTreeNode nodep=(DefaultMutableTreeNode)parent;
                    jTree.expandPath(new TreePath(nodep.getPath()));                    
                }
                parent=topic.getParent();
            }
            jTree.expandPath(new TreePath(topic.getPath()));
        }
        topic.setChecked(checked);
        tp.add(topic);
        
        this.findTopic(this.jTree,topic);        
        
        if(topic.getParent()!=null)
        {
            if(topic.getParent() instanceof Topic)
            {
                Topic parent=(Topic)topic.getParent();
                if(parent.getChecked())
                {
                    topic.setChecked(false);
                    topic.setEnabled(false);
                }
                if(!parent.isEnabled())
                {                    
                    topic.setEnabled(false);
                }   
                
            }
            else if(topic.getParent() instanceof TopicMap)
            {
                TopicMap parent=(TopicMap)topic.getParent();
                if(parent.getChecked())
                {
                    topic.setChecked(false);
                    topic.setEnabled(false);
                }
            }
        }

        Iterator it=reloads.iterator();
        while(it.hasNext())
        {
           String reload=(String)it.next();
           if(reload!=null && ochild.getAttribute("reload")!=null && ochild.getAttribute("reload").equals(reload))
           {
               this.jTree.expandPath(new TreePath(topic.getPath()));
               topic.setChecked(true);
           }               
        }

        WBTreeNode evt=ochild.getNodebyName("events");
        if(evt!=null)
        {
            WBTreeNode willexpand=evt.getNodebyName("willExpand");
            if(willexpand!=null)
            {
                topic.setWillExpand(true);
            }                
        }
        fillTreeTopic(ochild,topicmap,topic);          
    }
    public void reLoadTopic(String topicmap,Topic topic)
    {
        if(topic.getParent()!=null)
        {            
            if(topic.getParent() instanceof DefaultMutableTreeNode)
            {               
                String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+ topic.getRealoadPath() +"</cmd></req>";
                String resp=this.getData(xml);                  
                WBXMLParser parser=new WBXMLParser();
                WBTreeNode exml=parser.parse(resp);
                if(exml.getFirstNode()!=null)
                {
                    WBTreeNode res=exml.getFirstNode();
                    if(res!=null)
                    {
                        WBTreeNode etopic=res.getNodebyName("node");
                        if(etopic!=null)
                        {
                            DefaultMutableTreeNode tp=(DefaultMutableTreeNode)topic.getParent();                
                            tp.remove(topic);
                            addNode(topicmap,etopic, tp,true,true);
                        }
                        else
                        {
                            System.out.println("error : node:"+ topic.getID() +" reload: "+topic.getRealoadPath()+" was not found");
                        }
                    }
                }
            }
        }
    }
    public void loadTopic(Topic topic)
    {
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+ topic.getRealoadPath() +"</cmd></req>";
        String resp=this.getData(xml);                  
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode exml=parser.parse(resp);
        if(exml.getFirstNode()!=null)
        {
            WBTreeNode res=exml.getFirstNode();
            if(res!=null)
            {
                WBTreeNode etopic=res.getNodebyName("node");
                this.fillTreeTopic(etopic,topic.getTopicMapID(),topic);                
            }
        }
    }
    
    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemVerFiltros = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree = new javax.swing.JTree();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeMenus = new javax.swing.JTree();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTreeElements = new javax.swing.JTree();

        jMenu1.setText(java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("Herramientas"));
        jMenuItemVerFiltros.setText(java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("ver_filtros"));
        jMenuItemVerFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVerFiltrosActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItemVerFiltros);

        jMenuBar1.add(jMenu1);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 30));
        jToolBar1.setFloatable(false);
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/filters/images/save.gif")));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jToolBar1.add(jButton1);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(null);

        jLabel1.setText(java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("nombre"));
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 10, 70, 20);

        jPanel2.add(jTextFieldName);
        jTextFieldName.setBounds(90, 10, 300, 20);

        jLabel2.setText(java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("descripcion"));
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 40, 80, 16);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jTextAreaDescription.setLineWrap(true);
        jScrollPane4.setViewportView(jTextAreaDescription);

        jPanel4.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4);
        jPanel4.setBounds(90, 40, 300, 110);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("Propiedades"), jPanel2);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTree.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTreeMouseMoved(evt);
            }
        });
        jTree.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                jTreeTreeWillExpand(evt);
            }
        });

        jScrollPane1.setViewportView(jTree);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("filtro_sitios"), jPanel3);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jTreeMenus.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTreeMenusMouseMoved(evt);
            }
        });

        jScrollPane2.setViewportView(jTreeMenus);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("filtro_menus"), jPanel5);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jTreeElements.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTreeElementsMouseMoved(evt);
            }
        });

        jScrollPane3.setViewportView(jTreeElements);

        jPanel6.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("filtro_system"), jPanel6);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void jTreeElementsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeElementsMouseMoved
        this.jTreeElements.setSelectionPath(this.jTreeElements.getPathForLocation(evt.getX(), evt.getY()));
    }//GEN-LAST:event_jTreeElementsMouseMoved

    private void jTreeMenusMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeMenusMouseMoved
        this.jTreeMenus.setSelectionPath(this.jTreeMenus.getPathForLocation(evt.getX(), evt.getY()));
    }//GEN-LAST:event_jTreeMenusMouseMoved

    private void jMenuItemVerFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVerFiltrosActionPerformed
        //JSObject win = (JSObject) JSObject.getWindow(this);
        //String[] params=new String[0];
        //params[0]=eid.getFirstNode().getText();
        //win.call("doView", params);        
        try
        {
            URL url=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),url_script);            
            this.getAppletContext().showDocument(url,"work");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
    }//GEN-LAST:event_jMenuItemVerFiltrosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(this.jTextFieldName.getText().equals(""))
        {
            this.jTextFieldName.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("indicar_nombre"),java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("title"),JOptionPane.ERROR_MESSAGE);
            return;                
        }
        if(this.jTextAreaDescription.getText().equals(""))
        {
            this.jTextAreaDescription.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("indicar_description"),java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("title"),JOptionPane.ERROR_MESSAGE);
            return;   
        }
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode exml=parser.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        WBTreeNode ereq=exml.addNode();
        ereq.setName("req");
        WBTreeNode cmd=ereq.addNode();
        cmd.setName("cmd");
        cmd.setText("update");
        WBTreeNode efilter=ereq.addNode();
        efilter.setName("filter");
        efilter.addAttribute("name",this.jTextFieldName.getText());
        efilter.addAttribute("topicmap",topicmap);        
        if(this.id!=null)
        {
            efilter.addAttribute("id",this.id);        
        }
        WBTreeNode edescription=efilter.addNode();
        edescription.setName("description");
        edescription.setText(this.jTextAreaDescription.getText());        
        
        TreeModel model=this.jTreeElements.getModel();
        if(model.getRoot() instanceof Topic)
        {
            WBTreeNode eElements=efilter.addNode();
            eElements.setName("elements");
            Topic root=(Topic)model.getRoot();
            if(root.getChecked())
            {
               WBTreeNode etm=eElements.addNode();
               etm.setName("node");
               etm.addAttribute("id",root.getID());                       
               etm.addAttribute("icon",root.getIconName());
               etm.addAttribute("topicmap",root.getTopicMapID());
               etm.addAttribute("reload",root.getRealoadPath());
               String path=getPath(root);               
               etm.addAttribute("path",path);
               reloads.add(root.getRealoadPath());
            }
            else
            {
                for(int i=0;i<root.getChildCount();i++)
                {
                    TreeNode child=root.getChildAt(i);
                    if(child instanceof Topic)
                    {
                        Topic topic=(Topic)child;
                        if(topic.getChecked())
                        {

                           boolean existe=false;
                           Iterator it=eElements.getNodesbyName("node");
                           while(it.hasNext())
                           {
                               WBTreeNode node=(WBTreeNode)it.next();
                               if(node.getAttribute("reload")!=null && topic.getRealoadPath()!=null && node.getAttribute("reload").equals(topic.getRealoadPath()))
                               {
                                   existe=true;
                               }
                           }
                           if(!existe)
                           {
                               WBTreeNode etm=eElements.addNode();
                               etm.setName("node");
                               etm.addAttribute("id",topic.getID());                       
                               etm.addAttribute("icon",topic.getIconName());
                               etm.addAttribute("topicmap",topic.getTopicMapID());
                               etm.addAttribute("reload",topic.getRealoadPath());
                               String path=getPath(topic);               
                               etm.addAttribute("path",path);
                               reloads.add(topic.getRealoadPath());
                           }
                        }
                        else
                        {
                            evaluateTopics(topic,eElements);
                        }
                    }
                }
            }
        }
        
        model=this.jTreeMenus.getModel();
        if(model.getRoot() instanceof Topic)
        {
            WBTreeNode eMenus=efilter.addNode();
            eMenus.setName("menus");
            Topic root=(Topic)model.getRoot();
            if(root.getChecked())
            {
               WBTreeNode etm=eMenus.addNode();
               etm.setName("node");
               etm.addAttribute("id",root.getID());                       
               etm.addAttribute("icon",root.getIconName());
               etm.addAttribute("topicmap",root.getTopicMapID());
               etm.addAttribute("reload",root.getRealoadPath());
               String path=getPath(root);               
               etm.addAttribute("path",path);
               reloads.add(root.getRealoadPath());
            }
            else
            {
                for(int i=0;i<root.getChildCount();i++)
                {
                    TreeNode child=root.getChildAt(i);
                    if(child instanceof Topic)
                    {
                        Topic topic=(Topic)child;
                        if(topic.getChecked())
                        {

                           boolean existe=false;
                           Iterator it=eMenus.getNodesbyName("node");
                           while(it.hasNext())
                           {
                               WBTreeNode node=(WBTreeNode)it.next();
                               if(node.getAttribute("reload")!=null && topic.getRealoadPath()!=null && node.getAttribute("reload").equals(topic.getRealoadPath()))
                               {
                                   existe=true;
                               }
                           }
                           if(!existe)
                           {
                               WBTreeNode etm=eMenus.addNode();
                               etm.setName("node");
                               etm.addAttribute("id",topic.getID());                       
                               etm.addAttribute("icon",topic.getIconName());
                               etm.addAttribute("topicmap",topic.getTopicMapID());
                               etm.addAttribute("reload",topic.getRealoadPath());
                               String path=getPath(topic);               
                               etm.addAttribute("path",path);
                               reloads.add(topic.getRealoadPath());
                           }
                        }
                        else
                        {
                            evaluateTopics(topic,eMenus);
                        }
                    }
                }
            }
        }
        model=this.jTree.getModel();
        reloads=new HashSet();
        
        if(model.getRoot() instanceof TopicMap)
        {
            WBTreeNode eSites=efilter.addNode();
            eSites.setName("sites");
            TopicMap root=(TopicMap)model.getRoot();
            if(root.getChecked())
            {
               WBTreeNode etm=eSites.addNode();
               etm.setName("node");
               etm.addAttribute("id",root.getID());                       
               etm.addAttribute("icon",root.getIconName());
               etm.addAttribute("topicmap",root.getID());
               etm.addAttribute("reload",root.getRealoadPath());
               String path=getPath(root);               
               etm.addAttribute("path",path);
               reloads.add(root.getRealoadPath());
            }
            else
            {
                for(int i=0;i<root.getChildCount();i++)
                {
                    TreeNode child=root.getChildAt(i);
                    if(child instanceof TopicMap)
                    {
                        TopicMap map=(TopicMap)child;
                        if(map.getChecked())
                        {

                           boolean existe=false;
                           Iterator it=eSites.getNodesbyName("node");
                           while(it.hasNext())
                           {
                               WBTreeNode node=(WBTreeNode)it.next();
                               if(node.getAttribute("reload")!=null && map.getRealoadPath()!=null && node.getAttribute("reload").equals(map.getRealoadPath()))
                               {
                                   existe=true;
                               }
                           }
                           if(!existe)
                           {
                               WBTreeNode etm=eSites.addNode();
                               etm.setName("node");
                               etm.addAttribute("id",map.getID());                       
                               etm.addAttribute("icon",map.getIconName());
                               etm.addAttribute("topicmap",map.getID());
                               etm.addAttribute("reload",map.getRealoadPath());
                               String path=getPath(map);               
                               etm.addAttribute("path",path);
                               reloads.add(map.getRealoadPath());
                           }
                        }
                        else
                        {
                            evaluateTopics(map,eSites);
                        }
                    }
                    else if(child instanceof Root)
                    {
                        Root rootnode=(Root)child;
                        int childs=rootnode.getChildCount();
                        for(int ichild=0;ichild<childs;ichild++)
                        {
                            if(rootnode.getChildAt(ichild) instanceof Topic)
                            {
                                Topic topic=(Topic)rootnode.getChildAt(ichild);
                                if(topic.getChecked())
                                {
                                    boolean existe=false;
                                   Iterator it=efilter.getNodesbyName("node");
                                   while(it.hasNext())
                                   {
                                       WBTreeNode node=(WBTreeNode)it.next();
                                       if(node.getAttribute("reload")!=null && topic.getRealoadPath()!=null && node.getAttribute("reload").equals(topic.getRealoadPath()))
                                       {
                                           existe=true;
                                       }
                                   }
                                   if(!existe)
                                   {
                                       WBTreeNode etp=efilter.addNode();
                                       etp.setName("node");
                                       etp.addAttribute("id",topic.getID());
                                       etp.addAttribute("icon",topic.getIconName());
                                       etp.addAttribute("topicmap",topic.getTopicMapID());
                                       etp.addAttribute("reload",topic.getRealoadPath());                   
                                       String path=getPath(topic);               
                                       etp.addAttribute("path",path);
                                       reloads.add(topic.getRealoadPath());
                                   }
                                }
                                else
                                {
                                    evaluateTopics(topic,efilter);
                                }
                            }
                        }
                    }
                }
            }
        }
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ereq.getXML();                
        String resp=this.getData(xml);        
        parser= new WBXMLParser();
        WBTreeNode exmlresp=parser.parse(resp);
        WBTreeNode respnode=exmlresp.getFirstNode();
        if(respnode!=null)
        {
            WBTreeNode efilternode=respnode.getNodebyName("filter");
            if(efilternode!=null)
            {
                this.id=efilternode.getFirstNode().getText();               
                JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("save"),java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("title"),JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else
            {
                WBTreeNode err=respnode.getNodebyName("err");
                if(err!=null)
                {
                    JOptionPane.showMessageDialog(this,err.getFirstNode().getText(),java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("title"),JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                else
                {
                    JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("err1"),java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("title"),JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("err1"),java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale).getString("title"),JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed
    public String getPath(DefaultMutableTreeNode node)
    {         
        String id="";
        if(node instanceof Topic)
        {
            Topic tp=(Topic)node;
            id=tp.getID();
        }
        else if(node instanceof TopicMap)
        {
            TopicMap map=(TopicMap)node;
            id=map.getID();
        }
        else
        {
            return "";
        }
        
        if(node.getParent()!=null)
        {
            if(node.getParent() instanceof Topic)
            {
                Topic tp=(Topic)node.getParent();
                String path=getPath(tp);
                if(!path.equals(""))
                {
                    path+="|";
                }
                return path+id;
            }
            else if(node.getParent() instanceof TopicMap)
            {
                TopicMap map=(TopicMap)node.getParent();
                String path=getPath(map);
                if(!path.equals(""))
                {
                    path+="|";
                }
                return path+id;
            }
        }
        return id;
    }
    void evaluateTopics(Topic root,WBTreeNode efilter)
    {
        for(int i=0;i<root.getChildCount();i++)
        {
            TreeNode child=root.getChildAt(i);
            if(child instanceof Topic)
            {
                Topic topic=(Topic)child;
                if(topic.getChecked())
                {
                   boolean existe=false;
                   Iterator it=efilter.getNodesbyName("node");
                   while(it.hasNext())
                   {
                       WBTreeNode node=(WBTreeNode)it.next();
                       if(node.getAttribute("reload")!=null && topic.getRealoadPath()!=null && node.getAttribute("reload").equals(topic.getRealoadPath()))
                       {
                           existe=true;
                       }
                   }
                   if(!existe)
                   {
                       WBTreeNode etp=efilter.addNode();
                       etp.setName("node");
                       etp.addAttribute("id",topic.getID());
                       etp.addAttribute("icon",topic.getIconName());
                       etp.addAttribute("topicmap",topic.getTopicMapID());
                       etp.addAttribute("reload",topic.getRealoadPath());     
                       String path=getPath(topic);
                       etp.addAttribute("path",path);     
                       reloads.add(topic.getRealoadPath());
                   }
                }
                else
                {
                    evaluateTopics(topic,efilter);
                }
            }
            else if(child instanceof Root)
            {
                Root rootnode=(Root)child;
                int childs=rootnode.getChildCount();
                for(int ichild=0;ichild<childs;ichild++)
                {
                    if(rootnode.getChildAt(ichild) instanceof Topic)
                    {
                        Topic topic=(Topic)rootnode.getChildAt(ichild);
                        if(topic.getChecked())
                        {
                           boolean existe=false;
                           Iterator it=efilter.getNodesbyName("node");
                           while(it.hasNext())
                           {
                               WBTreeNode node=(WBTreeNode)it.next();
                               if(node.getAttribute("reload")!=null && topic.getRealoadPath()!=null && node.getAttribute("reload").equals(topic.getRealoadPath()))
                               {
                                   existe=true;
                               }
                           }
                           if(!existe)
                           {
                               WBTreeNode etp=efilter.addNode();
                               etp.setName("node");
                               etp.addAttribute("id",topic.getID());
                               etp.addAttribute("icon",topic.getIconName());
                               etp.addAttribute("topicmap",topic.getTopicMapID());
                               etp.addAttribute("reload",topic.getRealoadPath());                   
                               String path=getPath(topic);
                               etp.addAttribute("path",topic.getRealoadPath());                   
                               reloads.add(topic.getRealoadPath());
                           }
                        }
                        else
                        {
                            evaluateTopics(topic,efilter);
                        }
                    }
                }
            }
        }
    }
    void evaluateTopics(TopicMap root,WBTreeNode efilter)
    {
        for(int i=0;i<root.getChildCount();i++)
        {
            TreeNode child=root.getChildAt(i);
            if(child instanceof Topic)
            {
                Topic topic=(Topic)child;
                if(topic.getChecked())
                {
                   boolean existe=false;
                   Iterator it=efilter.getNodesbyName("node");
                   while(it.hasNext())
                   {
                       WBTreeNode node=(WBTreeNode)it.next();
                       if(node.getAttribute("reload")!=null && topic.getRealoadPath()!=null && node.getAttribute("reload").equals(topic.getRealoadPath()))
                       {
                           existe=true;
                       }
                   }
                   if(!existe)
                   {
                       WBTreeNode etp=efilter.addNode();
                       etp.setName("node");
                       etp.addAttribute("id",topic.getID());
                       etp.addAttribute("icon",topic.getIconName());
                       etp.addAttribute("topicmap",topic.getTopicMapID());
                       etp.addAttribute("reload",topic.getRealoadPath()); 
                       String path=getPath(topic);
                       etp.addAttribute("path",path); 
                       reloads.add(topic.getRealoadPath());
                   }
                }
                else
                {
                    evaluateTopics(topic,efilter);
                }
            }
            else if(child instanceof Root)
            {
                Root rootnode=(Root)child;
                int childs=rootnode.getChildCount();
                for(int ichild=0;ichild<childs;ichild++)
                {
                    if(rootnode.getChildAt(ichild) instanceof Topic)
                    {
                        Topic topic=(Topic)rootnode.getChildAt(ichild);
                        if(topic.getChecked())
                        {
                           boolean existe=false;
                           Iterator it=efilter.getNodesbyName("node");
                           while(it.hasNext())
                           {
                               WBTreeNode node=(WBTreeNode)it.next();
                               if(node.getAttribute("reload")!=null && topic.getRealoadPath()!=null && node.getAttribute("reload").equals(topic.getRealoadPath()))
                               {
                                   existe=true;
                               }
                           }
                           if(!existe)
                           {
                               WBTreeNode etp=efilter.addNode();
                               etp.setName("node");
                               etp.addAttribute("id",topic.getID());
                               etp.addAttribute("icon",topic.getIconName());
                               etp.addAttribute("topicmap",topic.getTopicMapID());
                               etp.addAttribute("reload",topic.getRealoadPath());                   
                               String path=getPath(topic);
                               etp.addAttribute("path",path);                   
                               reloads.add(topic.getRealoadPath());
                           }
                        }
                        else
                        {
                            evaluateTopics(topic,efilter);
                        }
                    }
                }
            }
        }
    }
    private void jTreeTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {//GEN-FIRST:event_jTreeTreeWillExpand
        if(evt.getPath().getLastPathComponent() instanceof Topic)
        {
            Topic topic=(Topic)evt.getPath().getLastPathComponent();
            if(topic.getWillExpand())
            {
                topic.removeAllChildren();  
                loadTopic(topic);
            }
            topic.setWillExpand(false);
        }
    }//GEN-LAST:event_jTreeTreeWillExpand

    private void jTreeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeMouseMoved
        this.jTree.setSelectionPath(this.jTree.getPathForLocation(evt.getX(), evt.getY()));
    }//GEN-LAST:event_jTreeMouseMoved
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemVerFiltros;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree;
    private javax.swing.JTree jTreeElements;
    private javax.swing.JTree jTreeMenus;
    // End of variables declaration//GEN-END:variables
    
}
