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
import java.awt.Cursor;
/**
 * Formulario que muestra las secciones para egenrar filtros en WB3.0.
 * @author Victor Lorenzana
 */
public class EditFilter extends javax.swing.JApplet {

    public static final Color colorPath=new Color(6,102,153);
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
    @Override
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
                e.printStackTrace(System.err);
            }
        }        
        initComponents();
        //this.setJMenuBar(this.jMenuBar1);
        jsess=this.getParameter(PRM_JSESS);
        cgiPath=this.getParameter(PRM_CGIPATH);
        topicmap=this.getParameter("tm");
        this.id=this.getParameter("idfilter");
        url_script=this.getParameter("location");
        try {
            url=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),cgiPath);
        }catch(Exception e) {
            e.printStackTrace();
        }        
        jTree.setCellRenderer(new CheckRenderer());        
        jTree.addMouseListener(new CheckListener()); 
        
        jTreeMenus.setCellRenderer(new CheckRenderer());        
        jTreeMenus.addMouseListener(new CheckListener()); 
        
        jTreeElements.setCellRenderer(new CheckRenderer());        
        jTreeElements.addMouseListener(new CheckListener());
        
        jTreeDirs.setCellRenderer(new CheckRenderer());
        jTreeDirs.addMouseListener(new CheckListener());
        loadMenus();
        loadElements();
        loadDirectories();
        
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>initTreeFilter</cmd><id>"+this.id +"</id><tm>"+topicmap+"</tm></req>";
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
                            e.printStackTrace();
                        }
                    }
                }
            }
            Iterator nodes=res.getNodesbyName("node");
            while(nodes.hasNext())
            {
                WBTreeNode node=(WBTreeNode)nodes.next();

                ImageIcon icon=null;
                if(node.getAttribute("icon")!=null && !node.getAttribute("icon").equals(""))
                {
                    icon=(ImageIcon)this.icons.get(node.getAttribute("icon"));
                }
                //(ImageIcon)this.icons.get(node.getAttribute("icon"));
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
                        e.printStackTrace();
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

    private void loadDirectories(Directory odir)
    {
        odir.removeAllChildren();
        String path=odir.getDirectory();
        try
        {
            path=WBXMLParser.encode(path, "UTF-8");
        }catch(Exception e){}
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
        
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getDirectories</cmd><path>"+ path +"</path></req>";
        
        String respxml=this.getData(xml);
        
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode enode=parser.parse(respxml);
        try
        {
            if(enode.getFirstNode()!=null && enode.getFirstNode().getFirstNode()!=null)
            {
                WBTreeNode dir=enode.getFirstNode().getFirstNode();
                if(dir.getName().equals("dir"))
                {
                    Iterator it=dir.getNodes().iterator();
                    while(it.hasNext())
                    {
                        dir=(WBTreeNode)it.next();
                        if(dir!=null && dir.getName().equals("dir"))
                        {
                            Directory child=new Directory(dir.getAttribute("name"),dir.getAttribute("path"));
                            odir.add(child);
                            if(odir.getChecked())
                            {
                                child.setChecked(false);
                                child.setEnabled(false);
                            }
                            if(!odir.isEnabled())
                            {
                                child.setEnabled(false);
                            }
                            if(dir.getAttribute("hasChild").equals("true"))
                            {
                                child.add(new DefaultMutableTreeNode(""));
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){}
    }
    private void loadDirectories(WBTreeNode dir,Directory root)
    {
        Iterator it=dir.getNodes().iterator();
        while(it.hasNext())
        {
            WBTreeNode enode=(WBTreeNode)it.next();
            if(enode.getName().equals("dir"))
            {
                Directory child=new Directory(enode.getAttribute("name"),enode.getAttribute("path"));
                root.add(child);
                if(root.getChecked())
                {
                    child.setChecked(false);
                    child.setEnabled(false);
                }
                if(!root.isEnabled())
                {
                    child.setEnabled(false);
                } 
                if(enode.getAttribute("hasChild").equals("true"))
                {
                    child.add(new DefaultMutableTreeNode(""));
                }
            }
        }


    }
    private void loadDirectories()
    {
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getDirectories</cmd></req>";
        String respxml=getData(xml);        
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode enode=parser.parse(respxml);
        if(enode.getFirstNode()!=null && enode.getFirstNode().getFirstNode()!=null)
        {
            WBTreeNode dir=enode.getFirstNode().getFirstNode();
            if(dir.getName().equals("dir"))
            {
                Directory root=new Directory(dir.getAttribute("name"),dir.getAttribute("path"));
                jTreeDirs.setModel(new DefaultTreeModel(root));                                
                loadDirectories(dir,root);
                this.jTreeDirs.expandRow(0);
            }
        }
    }
    /*private void loadDirectories(Directory odir)
    {
        odir.removeAllChildren();
        String path=odir.getDirectory();
        try
        {
            path=WBXMLParser.encode(path, "UTF-8");
        }catch(Exception e){}
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getDirectories</cmd><path>"+ path +"</path></req>";
        String respxml=this.getData(xml);
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode enode=parser.parse(respxml);
        try
        {
            if(enode.getFirstNode()!=null && enode.getFirstNode().getFirstNode()!=null)
            {
                WBTreeNode dir=enode.getFirstNode().getFirstNode();
                if(dir.getName().equals("dir"))
                {
                    Iterator it=dir.getNodes().iterator();
                    while(it.hasNext())
                    {
                        dir=(WBTreeNode)it.next();
                        if(dir!=null && dir.getName().equals("dir"))
                        {
                            Directory child=new Directory(dir.getAttribute("name"),dir.getAttribute("path"));
                            odir.add(child);
                            if(dir.getAttribute("hasChild").equals("true"))
                            {
                                child.add(new DefaultMutableTreeNode(""));
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){}
    }*/
    /*private void loadDirectories(WBTreeNode dir,Directory root)
    {
        Iterator it=dir.getNodes().iterator();
        while(it.hasNext())
        {
            WBTreeNode enode=(WBTreeNode)it.next();
            if(enode.getName().equals("dir"))
            {
                Directory child=new Directory(enode.getAttribute("name"),enode.getAttribute("path"));
                root.add(child);
                if(enode.getAttribute("hasChild").equals("true"))
                {
                    child.add(new DefaultMutableTreeNode(""));
                }
            }
        }


    }*/
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
                    editable=Boolean.valueOf(etopic.getAttribute("canModify")).booleanValue();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
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
                        editable=Boolean.valueOf(etopic.getAttribute("canModify")).booleanValue();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
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
                    editable=Boolean.valueOf(etopic.getAttribute("canModify")).booleanValue();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
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
                        editable=Boolean.valueOf(etopic.getAttribute("canModify")).booleanValue();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
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
           String stopicmap=enode.getAttribute("topicmap");
           int childs=this.jTreeMenus.getModel().getChildCount(root);
           for(int ichild=0;ichild<childs;ichild++)
           {                               
               if(this.jTreeMenus.getModel().getChild(root, ichild) instanceof Topic)
               {                                   
                    Topic topic=(Topic)this.jTreeMenus.getModel().getChild(root, ichild);
                    if(topic.getTopicMapID().equals(stopicmap) && enode.getAttribute("id").equals(topic.getID()))
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

    private void loadFilterDirs(Directory root,WBTreeNode enode)
    {      
       int childs=this.jTreeDirs.getModel().getChildCount(root);
       for(int ichild=0;ichild<childs;ichild++)
       {
           if(this.jTreeDirs.getModel().getChild(root, ichild) instanceof Directory)
           {
                Directory dir=(Directory)this.jTreeDirs.getModel().getChild(root, ichild);
                if(dir.path!=null && enode.getAttribute("path").equals(dir.path))
                {
                    dir.setChecked(true);
                    this.jTreeDirs.expandPath(new TreePath(dir.getPath()));
                    this.jTreeDirs.updateUI();
                }
                else
                {
                    if(dir.path!=null && enode.getAttribute("path").startsWith(dir.path))
                    {
                        this.jTreeDirs.expandPath(new TreePath(dir.getPath()));
                        this.jTreeDirs.updateUI();
                    }
                    loadFilterDirs(dir, enode);
                }
           }
       }
       
    }
    private void loadFilterDirs(WBTreeNode efilter)
    {
        WBTreeNode dirs=efilter.getNodebyName("dirs");
        if(dirs!=null)
        {
            Iterator it=dirs.getNodesbyName("dir");
            while(it.hasNext())
            {
               WBTreeNode enode=(WBTreeNode)it.next();
               String path=enode.getAttribute("path");
               Object objroot=this.jTreeDirs.getModel().getRoot();
               if(objroot instanceof Directory)
               {
                   Directory dir=(Directory)objroot;
                   if(dir.path.equals(path))
                   {
                       dir.setChecked(true);
                       break;
                   }
               }
               int childs=this.jTreeDirs.getModel().getChildCount(objroot);
               for(int ichild=0;ichild<childs;ichild++)
               {
                   if(this.jTreeDirs.getModel().getChild(objroot, ichild) instanceof Directory)
                   {
                        Directory dir=(Directory)this.jTreeDirs.getModel().getChild(objroot, ichild);
                        if(dir.path!=null && enode.getAttribute("path").equals(dir.path))
                        {
                            dir.setChecked(true);
                            this.jTreeDirs.expandPath(new TreePath(dir.getPath()));
                            this.jTreeDirs.updateUI();
                        }
                        else
                        {
                            if(dir.path!=null && enode.getAttribute("path").startsWith(dir.path))
                            {
                                this.jTreeDirs.expandPath(new TreePath(dir.getPath()));
                                this.jTreeDirs.updateUI();
                            }
                            loadFilterDirs(dir, enode);
                        }
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
                   String stopicmap=enode.getAttribute("topicmap");
                   Object objroot=this.jTreeMenus.getModel().getRoot();                           
                   if(objroot instanceof Topic)
                   {
                        Topic topic=(Topic)objroot;
                        if(topic.getTopicMapID().equals(stopicmap) && enode.getAttribute("id").equals(topic.getID()))
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
                            if(topic.getTopicMapID().equals(stopicmap) && enode.getAttribute("id").equals(topic.getID()))
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
           String stopicmap=enode.getAttribute("topicmap");
           int childs=this.jTreeElements.getModel().getChildCount(root);
           for(int ichild=0;ichild<childs;ichild++)
           {                               
               if(this.jTreeElements.getModel().getChild(root, ichild) instanceof Topic)
               {                                   
                    Topic topic=(Topic)this.jTreeElements.getModel().getChild(root, ichild);                                        
                    if(topic.getTopicMapID().equals(stopicmap) && enode.getAttribute("id").equals(topic.getID()))
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
               if(reload!=null && reload.startsWith("getTopic"))
               {
                   String stopicmap=enode.getAttribute("topicmap");
                   Object objroot=this.jTreeElements.getModel().getRoot();                           
                   if(objroot instanceof Topic)
                   {
                        Topic topic=(Topic)objroot;
                        if(topic.getTopicMapID().equals(stopicmap) && enode.getAttribute("id").equals(topic.getID()))
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
                            if(topic.getTopicMapID().equals(stopicmap) && enode.getAttribute("id").equals(topic.getID()))
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
                    loadFilterElements(efilter);
                    loadFilterMenu(efilter);
                    loadFilterDirs(efilter);
                    WBTreeNode sites=efilter.getNodebyName("sites");
                    if(sites!=null)
                    {
                        Iterator it=sites.getNodesbyName("node");
                        while(it.hasNext())
                        {
                           WBTreeNode enode=(WBTreeNode)it.next();
                           String reload=enode.getAttribute("reload");
                           
                           if(reload!=null && reload.startsWith("getSemanticObject"))
                           {
                               String stopicmap=enode.getAttribute("topicmap");
                               Object objroot=this.jTree.getModel().getRoot();                           
                               int childs=this.jTree.getModel().getChildCount(objroot);
                               for(int ichild=0;ichild<childs;ichild++)
                               {                               
                                   if(this.jTree.getModel().getChild(objroot, ichild) instanceof TopicMap)
                                   {                                   
                                        TopicMap map=(TopicMap)this.jTree.getModel().getChild(objroot, ichild);
                                        if(map.getID().equals(stopicmap))
                                        {
                                            this.jTree.expandPath(new TreePath(map.getPath()));                                            
                                            if(enode.getAttribute("reload")!=null && !enode.getAttribute("reload").equals(""))
                                            {
                                                StringTokenizer st=new StringTokenizer(enode.getAttribute("reload"),".");
                                                if(st.nextToken().equals("getSemanticObject"))
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
                                                                    tp.setLabelParent(tp, colorPath);
                                                                    this.jTree.expandPath(new TreePath(tp.getPath()));                           
                                                                    root=tp;                                                                   
                                                                    break;
                                                                }                                                                
                                                            }
                                                        }                                                        
                                                    }
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
                    e.printStackTrace();
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
                    e.printStackTrace();
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
                    topic.add(new DefaultMutableTreeNode());
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
                    e.printStackTrace();
                }
            }
            
            topic.setEditable(editable);
            tm.add(topic);
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
                    topic.add(new DefaultMutableTreeNode());
                }                
            }
            fillTreeTopic(ochild,topicmap,topic);
        }
    }
    public String getData(String xml)
    {
        StringBuilder ret=new StringBuilder();
        try {           
            
            URLConnection urlconn=url.openConnection();
            urlconn.setUseCaches(false);
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
        }catch(Exception e){e.printStackTrace();}
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
                e.printStackTrace();
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree = new javax.swing.JTree();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeMenus = new javax.swing.JTree();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTreeElements = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTreeDirs = new javax.swing.JTree();

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 30));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/filters/images/save.gif"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTree.setRowHeight(24);
        jTree.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener()
        {
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException
            {
                jTreeTreeWillExpand(evt);
            }
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException
            {
            }
        });
        jTree.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                jTreeMouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(jTree);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("applets/filters/EditFilter",locale); // NOI18N
        jTabbedPane1.addTab(bundle.getString("filtro_sitios"), jPanel3); // NOI18N

        jPanel5.setLayout(new java.awt.BorderLayout());

        jTreeMenus.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                jTreeMenusMouseMoved(evt);
            }
        });
        jScrollPane2.setViewportView(jTreeMenus);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("filtro_menus"), jPanel5); // NOI18N

        jPanel6.setLayout(new java.awt.BorderLayout());

        jTreeElements.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                jTreeElementsMouseMoved(evt);
            }
        });
        jScrollPane3.setViewportView(jTreeElements);

        jPanel6.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("filtro_system"), jPanel6); // NOI18N

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTreeDirs.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener()
        {
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException
            {
                jTreeDirsTreeWillExpand(evt);
            }
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException
            {
            }
        });
        jTreeDirs.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
        {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt)
            {
                jTreeDirsValueChanged(evt);
            }
        });
        jTreeDirs.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                jTreeDirsMouseMoved(evt);
            }
        });
        jScrollPane4.setViewportView(jTreeDirs);

        jPanel2.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Documentos del servidor", jPanel2);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTreeElementsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeElementsMouseMoved
        this.jTreeElements.setSelectionPath(this.jTreeElements.getPathForLocation(evt.getX(), evt.getY()));
    }//GEN-LAST:event_jTreeElementsMouseMoved

    private void jTreeMenusMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeMenusMouseMoved
        this.jTreeMenus.setSelectionPath(this.jTreeMenus.getPathForLocation(evt.getX(), evt.getY()));
    }//GEN-LAST:event_jTreeMenusMouseMoved

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode exml=parser.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        WBTreeNode ereq=exml.addNode();
        ereq.setName("req");
        WBTreeNode cmd=ereq.addNode();
        cmd.setName("cmd");
        cmd.setText("update");
        WBTreeNode efilter=ereq.addNode();
        efilter.setName("filter");
        //efilter.addAttribute("name",this.jTextFieldName.getText());
        efilter.addAttribute("topicmap",topicmap);        
        if(this.id!=null)
        {
            efilter.addAttribute("id",this.id);        
        }
        //WBTreeNode edescription=efilter.addNode();
        //edescription.setName("description");
        //edescription.setText(this.jTextAreaDescription.getText());
        
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
               //etm.addAttribute("icon",root.getIconName());
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
                               //etm.addAttribute("icon",topic.getIconName());
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
        
        model=this.jTreeDirs.getModel();
        if(model.getRoot() instanceof Directory)
        {
            WBTreeNode eDirs=efilter.addNode();
            eDirs.setName("dirs");
            Directory root=(Directory)model.getRoot();
            if(root.getChecked())
            {
               WBTreeNode etm=eDirs.addNode();
               etm.setName("dir");
               etm.addAttribute("path",root.path);

            }
            else
            {
                for(int i=0;i<root.getChildCount();i++)
                {
                    TreeNode child=root.getChildAt(i);
                    if(child instanceof Directory)
                    {
                        Directory topic=(Directory)child;
                        if(topic.getChecked())
                        {

                           boolean existe=false;
                           Iterator it=eDirs.getNodesbyName("dir");
                           while(it.hasNext())
                           {
                               WBTreeNode node=(WBTreeNode)it.next();
                               if(node.getAttribute("dir")!=null && topic.path!=null && node.getAttribute("dir").equals(topic.path))
                               {
                                   existe=true;
                               }
                           }
                           if(!existe)
                           {
                               WBTreeNode etm=eDirs.addNode();
                               etm.setName("dir");
                               etm.addAttribute("path",topic.path);
                           }
                        }
                        else
                        {
                            evaluateDirs(topic,eDirs);
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
               //etm.addAttribute("icon",root.getIconName());
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
                               //etm.addAttribute("icon",topic.getIconName());
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
               //etm.addAttribute("icon",root.getIconName());
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
                                       //etp.addAttribute("icon",topic.getIconName());
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
        String oid="";
        if(node instanceof Topic)
        {
            Topic tp=(Topic)node;
            oid=tp.getID();
        }
        else if(node instanceof TopicMap)
        {
            TopicMap map=(TopicMap)node;
            oid=map.getID();
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
                return path+oid;
            }
            else if(node.getParent() instanceof TopicMap)
            {
                TopicMap map=(TopicMap)node.getParent();
                String path=getPath(map);
                if(!path.equals(""))
                {
                    path+="|";
                }
                return path+oid;
            }
        }
        return oid;
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
                       //etp.addAttribute("icon",topic.getIconName());
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
                               //etp.addAttribute("icon",topic.getIconName());
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
    void evaluateDirs(Directory root,WBTreeNode efilter)
    {
        for(int i=0;i<root.getChildCount();i++)
        {
            TreeNode child=root.getChildAt(i);
            if(child instanceof Directory)
            {
                Directory topic=(Directory)child;
                if(topic.getChecked())
                {
                   boolean existe=false;
                   Iterator it=efilter.getNodesbyName("dir");
                   while(it.hasNext())
                   {
                       WBTreeNode node=(WBTreeNode)it.next();
                       if(node.getAttribute("path")!=null && topic.path!=null && node.getAttribute("path").startsWith(topic.path))
                       {
                           existe=true;
                       }
                   }
                   if(!existe)
                   {
                       WBTreeNode etp=efilter.addNode();
                       etp.setName("dir");
                       etp.addAttribute("path",topic.path);
                   }
                }
                else
                {
                    evaluateDirs(topic,efilter);
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
                       //etp.addAttribute("icon",topic.getIconName());
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
                               //etp.addAttribute("icon",topic.getIconName());
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

    private void jTreeDirsValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_jTreeDirsValueChanged
    {//GEN-HEADEREND:event_jTreeDirsValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTreeDirsValueChanged

    private void jTreeDirsTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException//GEN-FIRST:event_jTreeDirsTreeWillExpand
    {//GEN-HEADEREND:event_jTreeDirsTreeWillExpand
        if(evt.getPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir=(Directory)evt.getPath().getLastPathComponent();
            if(dir.getChildCount()==1 && !(dir.getChildAt(0) instanceof Directory))
            {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                dir.remove(0);
                loadDirectories(dir);
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_jTreeDirsTreeWillExpand

    private void jTreeDirsMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTreeDirsMouseMoved
    {//GEN-HEADEREND:event_jTreeDirsMouseMoved
        this.jTreeDirs.setSelectionPath(this.jTreeDirs.getPathForLocation(evt.getX(), evt.getY()));
    }//GEN-LAST:event_jTreeDirsMouseMoved
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree;
    private javax.swing.JTree jTreeDirs;
    private javax.swing.JTree jTreeElements;
    private javax.swing.JTree jTreeMenus;
    // End of variables declaration//GEN-END:variables
    
}
