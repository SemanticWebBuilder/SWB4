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
 * FilterSection.java
 *
 * Created on 10 de enero de 2005, 09:57 AM
 */
package applets.filterSection;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.net.*;
import java.util.*;

import applets.commons.*;
import javax.swing.event.TreeExpansionEvent;

/**
 * Formulario que muestra el ?rbol de secciones de WebBuilder para hacer filtros de
 * secciones.
 * @author Victor Lorenzana
 */
public class FilterSection extends javax.swing.JApplet
{

    private final String PRM_JSESS = "jsess";
    private final String PRM_CGIPATH = "cgipath";
    private String cgiPath = "/gtw.jsp";
    private String jsess = "", repository;                    //session del usuario
    private URL url = null;
    private String topicmap;
    private String id;
    HashMap icons = new HashMap();
    boolean isGlobal = false;
    Locale locale;
    ArrayList topicmaps;
    HashSet reloads = new HashSet();

    /** Initializes the applet FilterSection */
    public void init()
    {
        locale = Locale.getDefault();
        if (this.getParameter("locale") != null && !this.getParameter("locale").equals(""))
        {
            try
            {

                locale = new Locale(this.getParameter("locale"));
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        }
        initComponents();
        jsess = this.getParameter(PRM_JSESS);
        cgiPath = this.getParameter(PRM_CGIPATH);
        topicmap = this.getParameter("tm");
        if (this.getParameter("maps") != null)
        {
            topicmaps = new ArrayList();
            StringTokenizer st = new StringTokenizer(this.getParameter("maps"), "|");
            while (st.hasMoreTokens())
            {
                this.topicmaps.add(st.nextToken());
            }
        }
        this.id = this.getParameter("idresource");
        this.isGlobal = Boolean.valueOf(this.getParameter("isGlobalTM")).booleanValue();
        try
        {
            if(cgiPath.startsWith("http"))
            {
                url = new URL(cgiPath);
            }else
            {
                url = new URL(getCodeBase().getProtocol(), getCodeBase().getHost(), getCodeBase().getPort(), cgiPath);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        jTree1.setCellRenderer(new CheckRenderer());
        jTree1.addMouseListener(new CheckListener());
/*        
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>initTree</cmd></req>";
        String resp = this.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode nodexml = parser.parse(resp);
        if (nodexml.getNodesSize()>0 && nodexml.getFirstNode() != null)
        {
            WBTreeNode res = nodexml.getFirstNode();
            WBTreeNode config = res.getNodebyName("config");
            if (config != null)
            {
                WBTreeNode icons = config.getFirstNode();
                if (icons != null)
                {
                    Iterator itIcons = icons.getNodesbyName("icon");
                    while (itIcons.hasNext())
                    {
                        WBTreeNode icon = (WBTreeNode) itIcons.next();
                        String path = "/applets/filterSection/" + icon.getAttribute("path");
                        if (getClass().getResource(path) != null)
                        {
                            ImageIcon oicon = new javax.swing.ImageIcon(getClass().getResource(path));
                            this.icons.put(icon.getAttribute("id"), oicon);
                        }
                        else
                        {
                            System.out.println("Image not found:" + path);
                        }
                    }
                }
            }
            Iterator nodes = res.getNodesbyName("node");
            while (nodes.hasNext())
            {
                WBTreeNode node = (WBTreeNode) nodes.next();
                ImageIcon icon = (ImageIcon) this.icons.get(node.getAttribute("icon"));
                String name = node.getAttribute("name");
                TopicMap root = new TopicMap(node.getAttribute("icon"), node.getAttribute("id"), name, node.getAttribute("reload"), icon);
                this.jTree1.setModel(new DefaultTreeModel(root));
                fillTreeTopicMap(node, root);
                this.jTree1.updateUI();
            }
        }
*/       
        loadFilter();
    }

    public void addNode(String topicmap, WBTreeNode ochild, DefaultMutableTreeNode tp, boolean checked, boolean expand, boolean childs)
    {
        if (tp == null || ochild == null)
        {
            return;
        }

        Topic topic = new Topic(ochild.getAttribute("icon"), topicmap, ochild.getAttribute("id"), ochild.getAttribute("name"), ochild.getAttribute("reload"), (ImageIcon) this.icons.get(ochild.getAttribute("icon")));
        if (expand)
        {
            TreeNode parent = topic.getParent();
            while (parent != null)
            {
                if (parent instanceof DefaultMutableTreeNode)
                {
                    DefaultMutableTreeNode nodep = (DefaultMutableTreeNode) parent;
                    jTree1.expandPath(new TreePath(nodep.getPath()));
                }
                parent = topic.getParent();
            }
            jTree1.expandPath(new TreePath(topic.getPath()));
        }
        if (checked)
        {
            if (childs)
            {
                topic.setChecked(TristateCheckBox.DONT_CARE);
            }
            else
            {
                topic.setChecked(TristateCheckBox.SELECTED);
            }
        }
        else
        {
            topic.setChecked(TristateCheckBox.NOT_SELECTED);
        }
        tp.add(topic);

        this.findTopic(this.jTree1, topic);

        if (topic.getParent() != null)
        {
            if (topic.getParent() instanceof Topic)
            {
                Topic parent = (Topic) topic.getParent();
                if (parent.getChecked() == TristateCheckBox.DONT_CARE)
                {
                    topic.setChecked(TristateCheckBox.NOT_SELECTED);
                    topic.setEnabled(false);
                }
                if (!parent.isEnabled())
                {
                    topic.setEnabled(false);
                }

            }
            else if (topic.getParent() instanceof TopicMap)
            {
                TopicMap parent = (TopicMap) topic.getParent();
                if (parent.getChecked() == TristateCheckBox.DONT_CARE)
                {
                    topic.setChecked(TristateCheckBox.NOT_SELECTED);
                    topic.setEnabled(false);
                }
            }
        }

        Iterator it = reloads.iterator();
        while (it.hasNext())
        {
            WBTreeNode enode = (WBTreeNode) it.next();
            String reload = enode.getAttribute("reload");
            if (reload != null && ochild.getAttribute("reload") != null && ochild.getAttribute("reload").equals(reload))
            {
                this.jTree1.expandPath(new TreePath(topic.getPath()));
                if (enode.getAttribute("childs").equals("false"))
                {
                    topic.setChecked(TristateCheckBox.SELECTED);
                }
                else
                {
                    topic.setChecked(TristateCheckBox.DONT_CARE);
                }
            }
        }

        WBTreeNode evt = ochild.getNodebyName("events");
        if (evt != null)
        {
            WBTreeNode willexpand = evt.getNodebyName("willExpand");
            if (willexpand != null)
            {
                topic.setWillExpand(true);
            }
        }
        fillTreeTopic(ochild, topicmap, topic);
    }

    public void reLoadTopic(String topicmap, Topic topic)
    {
        if (topic.getParent() != null)
        {
            if (topic.getParent() instanceof DefaultMutableTreeNode)
            {
                String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>" + topic.getRealoadPath() + "</cmd></req>";
                String resp = this.getData(xml);
                WBXMLParser parser = new WBXMLParser();
                WBTreeNode exml = parser.parse(resp);
                if (exml.getFirstNode() != null)
                {
                    WBTreeNode res = exml.getFirstNode();
                    if (res != null)
                    {
                        WBTreeNode etopic = res.getNodebyName("node");
                        if (etopic != null)
                        {
                            DefaultMutableTreeNode tp = (DefaultMutableTreeNode) topic.getParent();
                            tp.remove(topic);
                            addNode(topicmap, etopic, tp, true, true, false);
                        }
                        else
                        {
                            System.out.println("error : node:" + topic.getID() + " reload: " + topic.getRealoadPath() + " was not found");
                        }
                    }
                }
            }
        }
    }

    boolean CheckNode(String reload, boolean bchilds)
    {
        Object root = this.jTree1.getModel().getRoot();
        if (root instanceof TopicMap)
        {
            TopicMap map = (TopicMap) root;
            if (map.getRealoadPath() != null && map.getRealoadPath().equals(reload))
            {
                map.setChecked(TristateCheckBox.SELECTED);
                this.jTree1.expandPath(new TreePath(map.getPath()));
                return true;
            }
        }
        int childs = this.jTree1.getModel().getChildCount(root);
        for (int i = 0; i < childs; i++)
        {
            if (this.jTree1.getModel().getChild(root, i) instanceof TopicMap)
            {
                TopicMap map = (TopicMap) this.jTree1.getModel().getChild(root, i);
                if (map.getRealoadPath() != null && map.getRealoadPath().equals(reload))
                {
                    map.setChecked(TristateCheckBox.SELECTED);
                    this.jTree1.expandPath(new TreePath(map.getPath()));
                    return true;
                }
                else
                {
                    CheckNode(map, reload, bchilds);
                }
            }
        }
        return false;
    }

    boolean CheckNode(Topic root, String reload, boolean bchilds)
    {
        int childs = this.jTree1.getModel().getChildCount(root);
        for (int i = 0; i < childs; i++)
        {
            if (this.jTree1.getModel().getChild(root, i) instanceof Topic)
            {
                Topic topic = (Topic) this.jTree1.getModel().getChild(root, i);
                if (topic.getRealoadPath() != null && topic.getRealoadPath().equals(reload))
                {
                    if (bchilds)
                    {
                        topic.setChecked(TristateCheckBox.DONT_CARE);
                    }
                    else
                    {
                        topic.setChecked(TristateCheckBox.SELECTED);
                    }
                    this.jTree1.expandPath(new TreePath(topic.getPath()));
                    return true;
                }
                else
                {
                    CheckNode(topic, reload, bchilds);
                }
            }
        }
        return false;
    }

    boolean CheckNode(TopicMap root, String reload, boolean bchilds)
    {
        int childs = this.jTree1.getModel().getChildCount(root);
        for (int i = 0; i < childs; i++)
        {
            if (this.jTree1.getModel().getChild(root, i) instanceof Topic)
            {
                Topic topic = (Topic) this.jTree1.getModel().getChild(root, i);
                if (topic.getRealoadPath() != null && topic.getRealoadPath().equals(reload))
                {
                    if (bchilds)
                    {
                        topic.setChecked(TristateCheckBox.DONT_CARE);
                    }
                    else
                    {
                        topic.setChecked(TristateCheckBox.SELECTED);
                    }
                    this.jTree1.expandPath(new TreePath(topic.getPath()));
                    return true;
                }
                else
                {
                    CheckNode(topic, reload, bchilds);
                }
            }
        }
        return false;
    }

    private void loadFilter()
    {
        reloads = new HashSet();
        DefaultTreeModel model = (DefaultTreeModel) this.jTree1.getModel();

        if (model.getRoot() != null)
        {
            Object objroot = model.getRoot();

            if (objroot instanceof DefaultMutableTreeNode)
            {
                ((DefaultMutableTreeNode) objroot).removeAllChildren();
                String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>initTree</cmd></req>";
                String resp = this.getData(xml);
                WBXMLParser parser = new WBXMLParser();
                WBTreeNode nodexml = parser.parse(resp);
                if (nodexml != null && nodexml.getNodesSize() > 0 && nodexml.getFirstNode() != null)
                {
                    WBTreeNode res = nodexml.getFirstNode();
                    WBTreeNode config = res.getNodebyName("config");
                    if (config != null)
                    {
                        WBTreeNode icons = config.getFirstNode();
                        if (icons != null)
                        {
                            Iterator itIcons = icons.getNodesbyName("icon");
                            while (itIcons.hasNext())
                            {
                                WBTreeNode icon = (WBTreeNode) itIcons.next();
                                String path = "/applets/filterSection/" + icon.getAttribute("path");
                                if (getClass().getResource(path) != null)
                                {
                                    ImageIcon oicon = new javax.swing.ImageIcon(getClass().getResource(path));
                                    this.icons.put(icon.getAttribute("id"), oicon);
                                }
                                else
                                {
                                    System.out.println("Image not found:" + path);
                                }
                            }
                        }
                    }
                    Iterator nodes = res.getNodesbyName("node");
                    while (nodes.hasNext())
                    {
                        WBTreeNode node = (WBTreeNode) nodes.next();
                        ImageIcon icon = (ImageIcon) this.icons.get(node.getAttribute("icon"));
                        String name = node.getAttribute("name");
                        TopicMap root = new TopicMap(node.getAttribute("icon"), node.getAttribute("id"), name, node.getAttribute("reload"), icon);
                        this.jTree1.setModel(new DefaultTreeModel(root));
                        fillTreeTopicMap(node, root);
                        this.jTree1.updateUI();
                    }
                }
                this.jTree1.updateUI();
            }
        }
        if (id != null)
        {

            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getFilter</cmd><id>" + this.id + "</id><tm>" + topicmap + "</tm></req>";
            String resp = this.getData(xml);
            
            WBXMLParser parser = new WBXMLParser();
            WBTreeNode nodexml = parser.parse(resp);
            WBTreeNode eresp = nodexml.getFirstNode();
            if (eresp != null)
            {
                WBTreeNode efilter = eresp.getNodebyName("filter");
                if (efilter != null)
                {
                    Iterator it = efilter.getNodesbyName("topicmap");
                    while (it.hasNext())
                    {
                        WBTreeNode etopicmap = (WBTreeNode) it.next();

                        String _negative = etopicmap.getAttribute("negative");
                        boolean negative = false;
                        if (_negative != null && !"".equals(_negative))
                        {
                            try
                            {
                                negative = Boolean.parseBoolean(_negative);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        jCheckBoxNegative.setSelected(negative);

                        String topicmapid = etopicmap.getAttribute("id");
                        Object objroot = this.jTree1.getModel().getRoot();
                        int childs = this.jTree1.getModel().getChildCount(objroot);
                        for (int ichild = 0; ichild < childs; ichild++)
                        {

                            if (this.jTree1.getModel().getChild(objroot, ichild) instanceof TopicMap)
                            {

                                TopicMap map = (TopicMap) this.jTree1.getModel().getChild(objroot, ichild);
                                this.jTree1.expandPath(new TreePath(map.getPath()));
                                if (map.getID().equals(topicmapid))
                                {
                                    Iterator ittopics = etopicmap.getNodesbyName("topic");
                                    while (ittopics.hasNext())
                                    {
                                        WBTreeNode enode = (WBTreeNode) ittopics.next();
                                        String reload = "getTopic." + topicmapid + "." + enode.getAttribute("id");
                                        enode.addAttribute("reload", reload);
                                        if (reload != null && !reload.equals(""))
                                        {                                            
                                            StringTokenizer st = new StringTokenizer(reload, ".");
                                            if (st.nextToken().equals("getTopic"))
                                            {
                                                Root shortcuts = null;
                                                int l = this.jTree1.getModel().getChildCount(map);
                                                for (int i = 0; i < l; i++)
                                                {
                                                    if (this.jTree1.getModel().getChild(map, i) instanceof Root)
                                                    {
                                                        shortcuts = (Root) this.jTree1.getModel().getChild(map, i);
                                                        break;
                                                    }
                                                }
                                                String path = "/applets/filterSection/images/f_general.gif";
                                                ImageIcon oicon = new javax.swing.ImageIcon(getClass().getResource(path));
                                                if (shortcuts == null)
                                                {
                                                    shortcuts = new Root(java.util.ResourceBundle.getBundle("applets/filterSection/FilterSection", locale).getString("sections_permiss"), oicon);
                                                    map.add(shortcuts);
                                                }
                                                
                                                Topic nodetopic = new Topic(enode.getAttribute("icon"), enode.getAttribute("topicmap"), enode.getAttribute("id"), "", enode.getAttribute("reload"), oicon);
                                                shortcuts.add(nodetopic);
                                                
                                                this.reLoadTopic(topicmap, nodetopic);
                                                
                                                boolean bchilds = Boolean.valueOf(enode.getAttribute("childs")).booleanValue();
                                                if (!CheckNode(reload, bchilds))
                                                {
                                                    reloads.add(enode);
                                                }
                                                
                                                this.jTree1.expandPath(new TreePath(shortcuts.getPath()));
                                                this.jTree1.updateUI();

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

    void fillTreeTopicMap(WBTreeNode node, TopicMap root)
    {

        Iterator nodes = node.getNodesbyName("node");
        while (nodes.hasNext())
        {
            WBTreeNode ochild = (WBTreeNode) nodes.next();
            if (this.isGlobal || this.topicmap.equalsIgnoreCase(ochild.getAttribute("id")))
            {
                if (this.isGlobal && this.topicmaps != null && this.topicmaps.size() > 0)
                {
                    for (int i = 0; i < this.topicmaps.size(); i++)
                    {
                        String topicmaptemp = (String) this.topicmaps.get(i);
                        if (topicmaptemp.equalsIgnoreCase(ochild.getAttribute("id")))
                        {
                            TopicMap tm = new TopicMap(ochild.getAttribute("icon"), ochild.getAttribute("id"), ochild.getAttribute("name"), ochild.getAttribute("reload"), (ImageIcon) this.icons.get(ochild.getAttribute("icon")));
                            root.add(tm);
                            fillTreeTopic(ochild, ochild.getAttribute("id"), tm);
                            break;
                        }
                    }
                }
                else
                {
                    TopicMap tm = new TopicMap(ochild.getAttribute("icon"), ochild.getAttribute("id"), ochild.getAttribute("name"), ochild.getAttribute("reload"), (ImageIcon) this.icons.get(ochild.getAttribute("icon")));
                    root.add(tm);
                    fillTreeTopic(ochild, ochild.getAttribute("id"), tm);
                }
            }
        }
    }

    private void findTopic(JTree jTree, Topic topic)
    {
        Object root = jTree.getModel().getRoot();
        int childs = jTree.getModel().getChildCount(root);
        if (root instanceof Topic)
        {
            Topic node = (Topic) root;
            node.addTopicListener(topic);
            topic.addTopicListener(node);
        }
        for (int i = 0; i < childs; i++)
        {
            Object ochild = jTree.getModel().getChild(root, i);
            findTopic(jTree, ochild, topic);
        }
    }

    private void findTopic(JTree jTree, Object root, Topic topic)
    {
        int childs = jTree.getModel().getChildCount(root);
        if (root instanceof Topic)
        {
            Topic node = (Topic) root;
            node.addTopicListener(topic);
            topic.addTopicListener(node);
        }
        for (int i = 0; i < childs; i++)
        {
            Object ochild = jTree.getModel().getChild(root, i);
            if (ochild instanceof Topic)
            {
                Topic tp = (Topic) ochild;
                tp.addTopicListener(topic);
                topic.addTopicListener(tp);
            }
            findTopic(jTree, ochild, topic);
        }
    }

    void fillTreeTopic(WBTreeNode node, String topicmap, Topic tp)
    {
        Iterator nodes = node.getNodesbyName("node");
        while (nodes.hasNext())
        {
            WBTreeNode ochild = (WBTreeNode) nodes.next();
            Topic topic = new Topic(ochild.getAttribute("icon"), topicmap, ochild.getAttribute("id"), ochild.getAttribute("name"), ochild.getAttribute("reload"), (ImageIcon) this.icons.get(ochild.getAttribute("icon")));
            topic.setChecked(TristateCheckBox.NOT_SELECTED);
            tp.add(topic);
            this.findTopic(this.jTree1, topic);
            if (topic.getParent() != null)
            {
                if (topic.getParent() instanceof Topic)
                {
                    Topic parent = (Topic) topic.getParent();
                    if (parent.getChecked() == TristateCheckBox.DONT_CARE)
                    {
                        topic.setChecked(TristateCheckBox.NOT_SELECTED);
                        topic.setEnabled(false);
                    }
                    if (!parent.isEnabled())
                    {
                        topic.setEnabled(false);
                    }
                }
                else if (topic.getParent() instanceof TopicMap)
                {
                    TopicMap parent = (TopicMap) topic.getParent();
                    if (parent.getChecked() == TristateCheckBox.DONT_CARE)
                    {
                        topic.setChecked(TristateCheckBox.NOT_SELECTED);
                        topic.setEnabled(false);
                    }
                }
            }

            Iterator it = reloads.iterator();
            while (it.hasNext())
            {
                WBTreeNode enode = (WBTreeNode) it.next();
                String reload = enode.getAttribute("reload");
                if (reload != null && ochild.getAttribute("reload") != null && ochild.getAttribute("reload").equals(reload))
                {
                    this.jTree1.expandPath(new TreePath(topic.getPath()));
                    if (enode.getAttribute("childs").equals("false"))
                    {
                        topic.setChecked(TristateCheckBox.SELECTED);
                    }
                    else
                    {
                        topic.setChecked(TristateCheckBox.DONT_CARE);
                    }
                }
            }

            WBTreeNode evt = ochild.getNodebyName("events");
            if (evt != null)
            {
                WBTreeNode willexpand = evt.getNodebyName("willExpand");
                if (willexpand != null)
                {
                    topic.setWillExpand(true);
                }
            }
            fillTreeTopic(ochild, topicmap, topic);
        }
    }

    void fillTreeTopic(WBTreeNode node, String topicmap, TopicMap tm)
    {
        Iterator nodes = node.getNodesbyName("node");
        while (nodes.hasNext())
        {
            WBTreeNode ochild = (WBTreeNode) nodes.next();

            Topic topic = new Topic(ochild.getAttribute("icon"), topicmap, ochild.getAttribute("id"), ochild.getAttribute("name"), ochild.getAttribute("reload"), (ImageIcon) this.icons.get(ochild.getAttribute("icon")));
            topic.setChecked(TristateCheckBox.NOT_SELECTED);
            tm.add(topic);
            this.findTopic(this.jTree1, topic);
            if (topic.getParent() != null)
            {
                if (topic.getParent() instanceof Topic)
                {
                    Topic parent = (Topic) topic.getParent();
                    if (parent.getChecked() == TristateCheckBox.SELECTED || parent.getChecked() == TristateCheckBox.DONT_CARE)
                    {
                        topic.setChecked(TristateCheckBox.NOT_SELECTED);
                        topic.setEnabled(false);
                    }
                    if (!parent.isEnabled())
                    {

                        topic.setEnabled(false);
                    }

                }
                else if (topic.getParent() instanceof TopicMap)
                {
                    TopicMap parent = (TopicMap) topic.getParent();
                    if (parent.getChecked() == TristateCheckBox.SELECTED || parent.getChecked() == TristateCheckBox.DONT_CARE)
                    {
                        topic.setChecked(TristateCheckBox.NOT_SELECTED);
                        topic.setEnabled(false);
                    }
                }
            }

            Iterator it = reloads.iterator();
            while (it.hasNext())
            {
                WBTreeNode enode = (WBTreeNode) it.next();
                String reload = enode.getAttribute("reload");
                if (reload != null && ochild.getAttribute("reload") != null && ochild.getAttribute("reload").equals(reload))
                {
                    this.jTree1.expandPath(new TreePath(topic.getPath()));
                    if (enode.getAttribute("childs").equals("false"))
                    {
                        topic.setChecked(TristateCheckBox.SELECTED);
                    }
                    else
                    {
                        topic.setChecked(TristateCheckBox.DONT_CARE);
                    }
                }
            }
            WBTreeNode evt = ochild.getNodebyName("events");
            if (evt != null)
            {
                WBTreeNode willexpand = evt.getNodebyName("willExpand");
                if (willexpand != null)
                {
                    topic.setWillExpand(true);
                }
            }
            fillTreeTopic(ochild, topicmap, topic);
        }
    }

    public String getData(String xml)
    {
        StringBuffer ret = new StringBuffer();
        try
        {
            //System.out.println("URL:"+url);
            URLConnection urlconn = url.openConnection();
            urlconn.setUseCaches(false);
            if (jsess != null)
            {
                urlconn.setRequestProperty("Cookie", "JSESSIONID=" + jsess);
            }
            urlconn.setRequestProperty("Content-Type", "application/xml");
            urlconn.setDoOutput(true);
            PrintWriter pout = new PrintWriter(urlconn.getOutputStream());
            pout.println(xml);
            pout.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                ret.append(inputLine);
                ret.append("\n");
            }
            in.close();
        }
        catch (Exception e)
        {
            System.out.println("Error to open service...");
            e.printStackTrace();
        }
        
        //System.out.println("getData:"+xml);
        //System.out.println("ret:"+ret.toString());
        
        return ret.toString();
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jCheckBoxNegative = new javax.swing.JCheckBox();

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 30));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/filterSection/images/save.gif"))); // NOI18N
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

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTree1.setRowHeight(24);
        jTree1.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener()
        {
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException
            {
                jTree1TreeWillExpand(evt);
            }
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException
            {
            }
        });
        jTree1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                jTree1MouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("applets/filterSection/FilterSection"); // NOI18N
        jCheckBoxNegative.setText(bundle.getString("negative")); // NOI18N
        jPanel3.add(jCheckBoxNegative);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode exml = parser.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        WBTreeNode ereq = exml.addNode();
        ereq.setName("req");
        WBTreeNode id = ereq.addNode();
        id.setName("id");
        id.setText(this.id);
        WBTreeNode tm = ereq.addNode();
        tm.setName("tm");
        tm.setText(this.topicmap);
        WBTreeNode cmd = ereq.addNode();
        cmd.setName("cmd");
        cmd.setText("update");
        WBTreeNode efilter = ereq.addNode();
        efilter.setName("filter");


        Object root = this.jTree1.getModel().getRoot();
        int ichilds = this.jTree1.getModel().getChildCount(root);
        for (int i = 0; i < ichilds; i++)
        {
            Object child = this.jTree1.getModel().getChild(root, i);
            WBTreeNode etopicmap = null;
            if (child instanceof TopicMap)
            {

                etopicmap = new WBTreeNode();
                etopicmap.setName("topicmap");
                if (this.jCheckBoxNegative.isSelected())
                {
                    etopicmap.addAttribute("negative", "true");
                }
                else
                {
                    etopicmap.addAttribute("negative", "false");
                }
                TopicMap map = (TopicMap) child;
                etopicmap.addAttribute("id", map.getID());
                saveFilters(etopicmap, child);
                if (etopicmap.getNodes().size() > 0)
                {
                    efilter.addNode(etopicmap);
                }
                

            }

        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + ereq.getXML();
        String resp = this.getData(xml);
        parser = new WBXMLParser();
        WBTreeNode exmlresp = parser.parse(resp);
        WBTreeNode efilternode = exmlresp.getNodebyName("filter");
        if (efilternode != null)
        {
            this.id = efilternode.getFirstNode().getText();
            loadFilter();
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/filterSection/FilterSection", locale).getString("save"), java.util.ResourceBundle.getBundle("applets/filterSection/FilterSection", locale).getString("title"), JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        else
        {
            WBTreeNode err = efilternode.getNodebyName("err");
            if (err != null)
            {
                JOptionPane.showMessageDialog(this, err.getFirstNode().getText(), java.util.ResourceBundle.getBundle("applets/filterSection/FilterSection", locale).getString("title"), JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else
            {
                loadFilter();
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/filterSection/FilterSection", locale).getString("err1"), java.util.ResourceBundle.getBundle("applets/filterSection/FilterSection", locale).getString("title"), JOptionPane.ERROR_MESSAGE);

                return;
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed
    private boolean existTopic(WBTreeNode map, String id)
    {
        boolean existTopic = false;
        String search = "id=\"" + id + "\"";
        String xml = map.getXML();
        int pos = xml.indexOf(search);
        if (pos != -1)
        {
            return true;
        }
        return existTopic;
    }

    public void saveFilters(WBTreeNode etopicmap, Object root)
    {
        int ichilds = this.jTree1.getModel().getChildCount(root);
        Root root_ = null;
        for (int i = 0; i < ichilds; i++)
        {
            Object child = this.jTree1.getModel().getChild(root, i);


            if (child instanceof Root)
            {

                root_ = (Root) child;
                this.jTree1.expandPath(new TreePath(root_.getPath()));
                this.jTree1.updateUI();
                String text = root_.getJLabel().getText();
                String labelTo = java.util.ResourceBundle.getBundle("applets/filterSection/FilterSection", locale).getString("sections_permiss");
                if (labelTo.equalsIgnoreCase(text))
                {
                    int childs = this.jTree1.getModel().getChildCount(root_);

                    for (int j = 0; j < childs; j++)
                    {
                        Object node = this.jTree1.getModel().getChild(root_, j);

                        if (node instanceof Topic)
                        {
                            Topic topic = (Topic) node;
                            this.jTree1.expandPath(new TreePath(topic.getPath()));
                            this.jTree1.updateUI();
                            if (topic.getChecked() == TristateCheckBox.SELECTED || topic.getChecked() == TristateCheckBox.DONT_CARE)
                            {
                                WBTreeNode etopic = new WBTreeNode();
                                if (!existTopic(etopicmap, topic.getID()))
                                {
                                    etopic.setName("topic");
                                    etopic.addAttribute("id", topic.getID());
                                    if (topic.getChecked() == TristateCheckBox.DONT_CARE)
                                    {
                                        etopic.addAttribute("childs", "true");
                                    }
                                    else
                                    {
                                        etopic.addAttribute("childs", "false");
                                    }
                                    etopicmap.addNode(etopic);
                                }
                            }

                            saveFilters(etopicmap, topic);
                        }
                    }
                }
            }
            if (child instanceof Topic)
            {
                Topic topic = (Topic) child;

                if (topic.getChecked() == TristateCheckBox.SELECTED || topic.getChecked() == TristateCheckBox.DONT_CARE)
                {
                    List<TreeNode> nodes = Arrays.asList(topic.getPath());
                    if (nodes.contains(root_))
                    {
                        this.jTree1.expandPath(new TreePath(topic.getPath()));
                    }
                    WBTreeNode etopic = new WBTreeNode();
                    if (!existTopic(etopicmap, topic.getID()))
                    {
                        etopic.setName("topic");
                        etopic.addAttribute("id", topic.getID());
                        if (topic.getChecked() == TristateCheckBox.DONT_CARE)
                        {
                            etopic.addAttribute("childs", "true");
                        }
                        else
                        {
                            etopic.addAttribute("childs", "false");
                        }
                        etopicmap.addNode(etopic);
                    }
                    if (topic.getChecked() == TristateCheckBox.DONT_CARE)
                    {
                        continue;
                    }
                }
                saveFilters(etopicmap, child);
            }
        }
    }

    public void loadTopic(Topic topic)
    {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>" + topic.getRealoadPath() + "</cmd></req>";
        String resp = this.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode exml = parser.parse(resp);
        if (exml.getFirstNode() != null)
        {
            WBTreeNode res = exml.getFirstNode();
            if (res != null)
            {
                WBTreeNode etopic = res.getNodebyName("node");
                this.fillTreeTopic(etopic, topic.getTopicMapID(), topic);
            }
        }
    }
    private void jTree1TreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {//GEN-FIRST:event_jTree1TreeWillExpand
        if (evt.getPath().getLastPathComponent() instanceof Topic)
        {
            Topic topic = (Topic) evt.getPath().getLastPathComponent();
            if (topic.getWillExpand())
            {
                topic.removeAllChildren();
                loadTopic(topic);
            }
            topic.setWillExpand(false);
        }
    }//GEN-LAST:event_jTree1TreeWillExpand

    private void jTree1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseMoved
        this.jTree1.setSelectionPath(this.jTree1.getPathForLocation(evt.getX(), evt.getY()));
    }//GEN-LAST:event_jTree1MouseMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBoxNegative;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
