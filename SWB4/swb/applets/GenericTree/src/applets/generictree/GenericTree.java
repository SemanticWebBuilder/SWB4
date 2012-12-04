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
 * GenercTree.java
 *
 * Created on 24 de junio de 2004, 10:03 AM
 */

package applets.generictree;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.datatransfer.*;
import java.awt.dnd.*;

import applets.commons.*;
import java.util.zip.GZIPInputStream;

/**
 * Applet que despliega el Árbol lateral de la aplicación.
 *
 * Applet that shows the side tree of the application.
 *
 * @author Javier Solis Gonzalez
 */
public class GenericTree extends javax.swing.JApplet implements DragGestureListener,DragSourceListener
{
    
    private static int bufferSize=8192;  
    
    private boolean vdebug=false;
    
    private static final String PRM_JSESS="jsess";
    private static final String PRM_CGIPATH="cgipath";

    private static final String ND_RES="res";
    private static final String ATT_ID="id";
    private static final String ATT_NAME="name";
    private static final String ND_CONFIG="config";
    private static final String ND_ICONS="icons";
    private static final String ATT_PATH="path";
    private static final String ATT_TEXT="text";
    private static final String ND_NODE="node";
    private static final String ATT_EDITABLE="editable";
    private static final String ND_MENU="menu";
    private static final String ATT_CONFIRM="confirm";
    private static final String ATT_TARGET="target";
    private static final String ATT_VIEW="view";
    private static final String ATT_VTARGET="vtarget";
    private static final String ATT_RELOAD="reload";
    private static final String ATT_DRAGENABLED="dragEnabled";
    private static final String ATT_DRAGVALUE="dragValue";
    private static final String ATT_ICON="icon";
    private static final String ATT_SHORTCUT="shortCut";
    private static final String ND_OPTION="option";
    private static final String ND_SEPARATOR="separator";
    private static final String ND_EVENTS="events";
    private static final String ND_WILLEXPAND="willExpand";
    private static final String ND_NODECHANGED="nodeChanged";
    private static final String ND_NODEREMOVE="nodeRemoved";
    private static final String ATT_ACTION="action";
    
    private static final String ACC_RELOAD="reload";
    private static final String ACC_SHOWURL="showurl";
    private static final String ACC_REMOVE="remove";
    private static final String ACC_GOTONODE="gotonode";
    private static final String ACC_GOTOPATH="gotopath";
    private static final String ACC_ADDNODE="addnode";
    private static final String ACC_SENDDATA="sendData";
    private static final String ACC_EDITNODE="editnode";
    
    private DefaultMutableTreeNode root=null;
    private DefaultTreeModel model=null;
    
    
    private GenericTreeRender renderer=null;
    private WBTreeCellEditor namecelleditor;
    
    private String cgiPath="/gtw.jsp";
    private String jsess=null;                    //session del usuario
    private URL url=null;
    
    private int lastx;
    private int lasty;
    
    private WBTreeNode events=null;
    
    private java.awt.event.ActionListener actList=new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            menuActionPerformed(evt);
        }
    };
    
    public void stop()
    {
        super.stop();
        //System.out.println("stop");
        
        Iterator it=events.getNodesbyName("unload");
        while(it.hasNext())
        {
            WBTreeNode node=(WBTreeNode)it.next();
            executeAction(null,node);
        }
        
    }
    
    /** Initializes the applet GenercTree */
    public void init()
    {
        renderer=new GenericTreeRender();
        jsess=this.getParameter(PRM_JSESS);
        cgiPath=this.getParameter(PRM_CGIPATH);
        try
        {
            url=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),cgiPath);
        }catch(Exception e){}
        initComponents();
        dragConfig();
        dropConfig();
        initWB();
        //this.getContentPane().add(menu);
        ToolTipManager.sharedInstance().registerComponent(jTree1);      //dar de alta tool tips.
        
        //executeAction("gotonode.topic.curso.curs_58_hijo_111");
    }
    
    private void initWB()
    {
        //DefaultTreeModel
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>initTree</cmd></req>";
        String data=getData(xml);
        //System.out.println("data:"+data);
        WBTreeNode wbnode=new WBXMLParser().parse(data);
        //System.out.println("wbnode:"+wbnode);
        fillTree(jTree1,wbnode);
        //jTree1.updateUI();
    }
    
    private DefaultMutableTreeNode addNode2Model(WBTreeNode node)
    {
        DefaultMutableTreeNode root= new DefaultMutableTreeNode(node);
        for(int x=0;x<node.getNodesSize();x++)
        {
            if(node.getNode(x).getName().equals(ND_NODE))
            {
                DefaultMutableTreeNode child=addNode2Model(node.getNode(x));
                root.add(child);
            }
        }     
        return root;
    }
    
    private void fillTree(JTree jtree, WBTreeNode node)
    {
        //icons
        WBTreeNode icons=node.findNode(ND_ICONS);
        Iterator it=icons.getNodes().iterator();
        while(it.hasNext())
        {
            WBTreeNode inode=(WBTreeNode)it.next();
            Image img=getImage(getClass().getResource(inode.getAttribute(ATT_PATH)));
            try
            {
                MediaTracker mt=new MediaTracker(this);
                mt.addImage(img,0);
                mt.waitForAll();
            }catch(InterruptedException e){e.printStackTrace(System.out);}
            Image buf=img;
            String txt=inode.getAttribute(ATT_TEXT);
            if(txt!=null)
            {
                int w=img.getWidth(this);
                int h=img.getHeight(this);
                buf=this.createImage(w,h);
                Graphics grp=buf.getGraphics();
                //grp.setColor(stringToColor(getParameter(backgroundParam)));
                grp.setColor(jTree1.getBackground());
                grp.fillRect(0,0,w,h);
                grp.drawImage(img,0,0,this);
                
                Rectangle2D rec=grp.getFontMetrics().getStringBounds(txt,grp);
                int fw=(int)rec.getWidth();
                int fh=(int)rec.getHeight();
                int x=(int)((w-fw)/2)+1;
                int y=(int)((h-fh)/2)+10;
                grp.setColor(jTree1.getForeground());
                grp.drawString(txt,x,y);
            }
            if(buf!=null)
            {
                renderer.getIcons().put(inode.getAttribute(ATT_ID), new ImageIcon(buf));
            }
        }
        
        //events
        events=node.findNode(ND_EVENTS);
        
        //tree nodes
        WBTreeNode nodes=node.findNode(ND_NODE);
        root=addNode2Model(nodes);
        model=new DefaultTreeModel(root);
        model.addTreeModelListener(new WBTreeModelListener(this));   //add listener of tree...
        jtree.setModel(model);
    }
    
    public boolean executeAction(String action)
    {
        //System.out.println(action);
        if(action==null)return false;
        if(action.startsWith(ACC_REMOVE))
        {
            if(jTree1.getSelectionPath()!=null)
            {
                DefaultMutableTreeNode dnode=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
                MutableTreeNode parent = (MutableTreeNode)(dnode.getParent());
                if (parent != null) {
                    model.removeNodeFromParent(dnode);
                    return true;
                }
            }
        }else if(action.startsWith(ACC_RELOAD))
        {
            if(jTree1.getSelectionPath()!=null)
            {
                DefaultMutableTreeNode dnode=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
                if(dnode.getUserObject() instanceof WBTreeNode)
                {
                    WBTreeNode node=(WBTreeNode)dnode.getUserObject();
                    String reload=node.getAttribute(ATT_RELOAD);
                    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+reload+"</cmd></req>";
                    String data=getData(xml);
                    //System.out.println("data:"+data);
                    WBTreeNode wbnode=new WBXMLParser().parse(data);
                    //System.out.println("wbnode:"+wbnode);
                    reload(dnode,wbnode);                
                }
            }
        }else if(action.startsWith(ACC_GOTONODE))
        {
            String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getPath."+action.substring(ACC_GOTONODE.length()+1)+"</cmd></req>";
            String data=getData(xml);
            WBTreeNode wbnode=new WBXMLParser().parse(data);
            WBTreeNode res=wbnode.findNode("res");
            if(res!=null && res.getNodesSize()==1)
            {
                String p=res.getFirstNode().getText();
                //System.out.println("path:"+p);
                StringTokenizer st=new StringTokenizer(p,".");
                DefaultMutableTreeNode aux=root;
                boolean found=true;
                while(found && st.hasMoreTokens())
                {
                    found=false;
                    String t=st.nextToken();
                    //System.out.println("t:"+t);
                    int l=aux.getChildCount();
                    for(int x=0;x<l;x++)
                    {
                        DefaultMutableTreeNode aux2=(DefaultMutableTreeNode)aux.getChildAt(x);
                        Object obj=aux2.getUserObject();
                        if(obj instanceof WBTreeNode)
                        {
                            WBTreeNode n=(WBTreeNode)obj;
                            String id=n.getAttribute("id");
                            if(id!=null && id.equals(t))
                            {
                                //WillExpand
                                WBTreeNode events=n.getNodebyName(ND_EVENTS);
                                if(events!=null)
                                {
                                    Iterator it=events.getNodesbyName(ND_WILLEXPAND);
                                    while(it.hasNext())
                                    {
                                        WBTreeNode event=(WBTreeNode)it.next();
                                        executeAction(aux2, event);
                                    }
                                }                                
                                aux=aux2;
                                //System.out.println("aux:"+aux);
                                found=true;
                                break;
                            }
                        }
                    }
                }
                
                jTree1.setSelectionPath(new TreePath(aux.getPath()));
            }
        }else if(action.startsWith(ACC_GOTOPATH))
        {
            StringTokenizer st=new StringTokenizer(action,".");
            st.nextElement();
            DefaultMutableTreeNode aux=root;
            boolean found=true;
            while(found && st.hasMoreTokens())
            {
                found=false;
                String t=st.nextToken();
                //System.out.println("t:"+t);
                int l=aux.getChildCount();
                for(int x=0;x<l;x++)
                {
                    DefaultMutableTreeNode aux2=(DefaultMutableTreeNode)aux.getChildAt(x);
                    Object obj=aux2.getUserObject();
                    if(obj instanceof WBTreeNode)
                    {
                        WBTreeNode n=(WBTreeNode)obj;
                        String id=n.getAttribute("id");
                        if(id!=null && id.equals(t))
                        {
                            //WillExpand
                            WBTreeNode events=n.getNodebyName(ND_EVENTS);
                            if(events!=null)
                            {
                                Iterator it=events.getNodesbyName(ND_WILLEXPAND);
                                while(it.hasNext())
                                {
                                    WBTreeNode event=(WBTreeNode)it.next();
                                    executeAction(aux2, event);
                                }
                            }                                

                            aux=aux2;
                            //System.out.println("aux:"+aux);
                            found=true;
                            break;
                        }
                    }
                }
            }
            
            selectNode(aux);

        }
        return false;
    }    
    
    private void selectNode(DefaultMutableTreeNode node)
    {
        TreePath path=new TreePath(node.getPath());
        jTree1.setSelectionPath(path);
        //if(!jTree1.isVisible(path))
        jTree1.scrollPathToVisible(path);
        //jTree1.updateUI();
    }
    
    private void executeAction(DefaultMutableTreeNode dnode, WBTreeNode node)
    {
        String action=node.getAttribute(ATT_ACTION);
        String target=node.getAttribute(ATT_TARGET);
        String confirm=node.getAttribute(ATT_CONFIRM);
        
        String reload=null;
        if(dnode!=null)
        {
            WBTreeNode onode=(WBTreeNode)dnode.getUserObject();
            reload=onode.getAttribute(ATT_RELOAD);
        }
        
        int r=0;
        if(confirm!=null)
        {
            r=JOptionPane.showConfirmDialog(this,confirm,node.getAttribute(ATT_NAME),JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
        }
        
        if(r==0 && action!=null)
        {
            if(action.equals(ACC_RELOAD))
            {
                String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+reload+"</cmd></req>";
                String data=getData(xml);
                //System.out.println("data:"+data);
                WBTreeNode wbnode=new WBXMLParser().parse(data);
                //System.out.println("wbnode:"+wbnode);
                reload(dnode,wbnode);                
            }else if(action.startsWith(ACC_SHOWURL+"="))
            {
                String surl=action.substring(ACC_SHOWURL.length()+1);
                try
                {
                    URL url=new URL(this.getDocumentBase(),surl);
                    if(target!=null)
                        this.getAppletContext().showDocument(url,target);
                    else   
                        this.getAppletContext().showDocument(url);
                }catch(Exception e){e.printStackTrace(System.out);}
            }else if(action.startsWith(ACC_RELOAD+"="))
            {
                String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+action.substring(ACC_RELOAD.length()+1)+"</cmd></req>";
                String data=getData(xml);
                //System.out.println("data:"+data);
                WBTreeNode wbnode=new WBXMLParser().parse(data);
                //System.out.println("wbnode:"+wbnode);
                reload(dnode,wbnode);
            }else if(action.startsWith(ACC_GOTONODE))
            {
                executeAction(action);
            }else if(action.startsWith(ACC_ADDNODE))
            {
                String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+action.substring(ACC_ADDNODE.length()+1)+"</cmd></req>";
                String data=getData(xml);
                //System.out.println("data:"+data);
                WBTreeNode wbnode=(new WBXMLParser()).parse(data);
                DefaultMutableTreeNode aux=new DefaultMutableTreeNode(wbnode.findNode(ND_NODE));
                model.insertNodeInto(aux,dnode,dnode.getChildCount());
                jTree1.setSelectionPath(new javax.swing.tree.TreePath(aux.getPath()));
                jTree1.scrollPathToVisible(new javax.swing.tree.TreePath(aux.getPath()));
                jTree1.setEditable(true);
                jTree1.startEditingAtPath(new javax.swing.tree.TreePath(aux.getPath()));
            }else if(action.startsWith(ACC_EDITNODE))
            {
                jTree1.setEditable(true);
                jTree1.startEditingAtPath(new javax.swing.tree.TreePath(dnode.getPath()));
            }else if(action.startsWith(ACC_SENDDATA))
            {          
                try
                {
                    String cmd=action.substring(ACC_SENDDATA.length()+1);
                    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+cmd+"</cmd><data>"+WBXMLParser.replaceStrChars(dnode.toString())+"</data></req>";
                    System.out.println("xml:"+xml);
                    String xmlfin=WBXMLParser.encode(xml,"UTF8");
                    System.out.println("xmlfin:"+xmlfin);
                    String data=getData(xmlfin);
                }catch(Exception e){System.out.println(e);}
            }else if(action.startsWith(ACC_REMOVE))
            {
                executeAction(action);
            }
        }
        
    }
    
    public void treeChanged(javax.swing.event.TreeModelEvent evt,String action){
        //System.out.println("treeChanged:"+action+" - "+evt);
        //if(action.equals("treeNodesRemoved")||action.equals("treeNodesChanged"))
        {
            WBTreeCellEditor ce=(WBTreeCellEditor)jTree1.getCellEditor();
            WBTreeNode node=ce.getNode();
            DefaultMutableTreeNode aux=null;
            
            //System.out.println("node:"+node);
            //System.out.println("getSource:"+evt.getChildren());
            
            if(jTree1.getSelectionPath()!=null)
            {
                aux=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
            }
            
            if(node!=null)
            {
                if(action.equals("treeNodesChanged"))
                {
                    node.addAttribute("name", aux.getUserObject().toString());
                    aux.setUserObject(node);
                    jTree1.updateUI();

                    WBTreeNode events=node.getNodebyName(ND_EVENTS);
                    if(events!=null)
                    {
                        Iterator it=events.getNodesbyName(ND_NODECHANGED);
                        while(it.hasNext())
                        {
                            WBTreeNode event=(WBTreeNode)it.next();
                            executeAction(aux, event);
                        }
                    }
                }else if(action.equals("treeNodesRemoved"))
                {
                    WBTreeNode events=node.getNodebyName(ND_EVENTS);
                    if(events!=null)
                    {
                        Iterator it=events.getNodesbyName(ND_NODEREMOVE);
                        while(it.hasNext())
                        {
                            WBTreeNode event=(WBTreeNode)it.next();
                            executeAction(aux, event);
                        }
                    }
                }
            }
        }
        
    }    
    
    private void reload(DefaultMutableTreeNode root,WBTreeNode res)
    {
        WBTreeNode node=res.findNode(ND_NODE);
        root.removeAllChildren();
        root.setUserObject(node);
        for(int x=0;x<node.getNodesSize();x++)
        {
            if(node.getNode(x).getName().equals(ND_NODE))
            {
                DefaultMutableTreeNode child=addNode2Model(node.getNode(x));
                root.add(child);
            }
        }
        model.reload(root);
        //selectNode(root);
        TreePath path=new TreePath(root.getPath());
        jTree1.scrollPathToVisible(path);
    }
    
    private String getData(String xml)
    {
        //System.out.println(xml);
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        //StringBuffer ret=new StringBuffer();
        String ret="";
        try
        {
            //URL gurl=new URL(this.url,cgi);
            URLConnection urlconn=url.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            urlconn.setRequestProperty("Accept-Encoding", "gzip");
            //System.out.println("JSESSIONID="+jsess);
            urlconn.setDoOutput(true);
            PrintWriter pout = new PrintWriter(urlconn.getOutputStream());
            pout.println(xml);
            pout.close();

//            BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null)
//            {
//                ret.append(inputLine);
//                ret.append("\n");
//                debug(inputLine);
//            }
//            in.close();
            String accept=urlconn.getHeaderField("Content-Encoding");
            debug("Content-Encoding:"+accept);
            InputStream in;
            if (accept != null && accept.toLowerCase().indexOf("gzip") != -1)
            {
                in=new GZIPInputStream(urlconn.getInputStream(),bufferSize);
            }else
            {
                in=urlconn.getInputStream();
            }
            
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            byte[] bfile = new byte[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1)
            {
                out.write(bfile, 0, x);
            }
            in.close();
            out.flush();
            out.close();
            ret=new String(out.toByteArray());
        }catch(Exception e){System.out.println("Error to open service..."+e);}
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        return ret;
    }    
    
    public void showMenu()
    {
        DefaultMutableTreeNode dnode=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
        if(dnode.getUserObject() instanceof WBTreeNode)
        {
            WBTreeNode node=(WBTreeNode)dnode.getUserObject();
            WBTreeNode nmenu=node.getNodebyName(ND_MENU);
            if(nmenu!=null)
            {
                menu.removeAll();
                addMenu(nmenu, menu);
                menu.show(getContentPane(),this.lastx+jTree1.getX(),this.lasty+jTree1.getY());
            }
        }
    }
    
   private void addMenu(WBTreeNode node, MenuElement menu)
   {
        Iterator it=node.getNodes().iterator();
        while(it.hasNext())
        {
            WBTreeNode opt=(WBTreeNode)it.next();
            String icon=opt.getAttribute(ATT_ICON);
            String shortcut=opt.getAttribute(ATT_SHORTCUT);
            if(opt.getNodesSize()>0)
            {
                JMenu item=new JMenu(opt.getAttribute(ATT_NAME));
                if(menu instanceof JPopupMenu)((JPopupMenu)menu).add(item);
                else if(menu instanceof JMenu)((JMenu)menu).add(item);
                addMenu(opt,item);
                
                if(icon!=null)item.setIcon((ImageIcon)renderer.getIcons().get(icon));
                //if(shortcut!=null)item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(shortcut));
            }else
            {
                if(opt.getName().equals(ND_SEPARATOR))
                {
                    if(menu instanceof JPopupMenu)((JPopupMenu)menu).addSeparator();
                    else if(menu instanceof JMenu)((JMenu)menu).addSeparator();
                }else if(opt.getName().equals(ND_OPTION))
                {
                    JMenuItem menuItem=new JMenuItem();
                    menuItem.setActionCommand(opt.getAttribute(ATT_ID));
                    menuItem.setLabel(opt.getAttribute(ATT_NAME));
                    if(menu instanceof JPopupMenu)((JPopupMenu)menu).add(menuItem);
                    else if(menu instanceof JMenu)((JMenu)menu).add(menuItem);
                    
                    if(icon!=null)menuItem.setIcon((ImageIcon)renderer.getIcons().get(icon));
                    if(shortcut!=null)menuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(shortcut));
                    menuItem.addActionListener(actList);              
                }                
            }
        }
   }    
    
    
    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        menu = new javax.swing.JPopupMenu();
        tabbed = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        tabbed.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        getContentPane().add(tabbed, java.awt.BorderLayout.CENTER);

        namecelleditor=new WBTreeCellEditor(jTree1,renderer,null,this);
        jTree1.setCellEditor(namecelleditor);
        jTree1.setCellRenderer(renderer);
        jTree1.setEditable(true);
        jTree1.setInvokesStopCellEditing(true);
        jTree1.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                jTree1KeyPressed(evt);
            }
        });
        jTree1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTree1MouseClicked(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
        {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt)
            {
                jTree1ValueChanged(evt);
            }
        });
        jTree1.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener()
        {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException
            {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException
            {
                jTree1TreeWillExpand(evt);
            }
        });

        jScrollPane1.setViewportView(jTree1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void jTree1KeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTree1KeyPressed
    {//GEN-HEADEREND:event_jTree1KeyPressed
        // Add your handling code here:
        //System.out.println("jTree1KeyPressed:"+evt);        
        if(jTree1.getSelectionPath()!=null)
        {
            DefaultMutableTreeNode select=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
            WBTreeNode node=(WBTreeNode)select.getUserObject();

            WBTreeNode menu=node.getNodebyName(ND_MENU);
            Iterator it=menu.getNodes().iterator();
            while(it.hasNext())
            {
                WBTreeNode option=(WBTreeNode)it.next();
                String shortcut=option.getAttribute(ATT_SHORTCUT);
                if(shortcut!=null)
                {
                    KeyStroke key=javax.swing.KeyStroke.getKeyStroke(shortcut);
                    if(key.getKeyCode()==evt.getKeyCode())
                    {
                        executeAction(select, option);
                    }
                }
            }
        }             
    }//GEN-LAST:event_jTree1KeyPressed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_jTree1ValueChanged
    {//GEN-HEADEREND:event_jTree1ValueChanged
        if(jTree1.getSelectionPath()!=null)
        {
            DefaultMutableTreeNode node=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
            if(node!=null && node.getUserObject() instanceof WBTreeNode)
            {
                //System.out.println("jTree1MouseClicked:"+node);
                WBTreeNode anode=(WBTreeNode)node.getUserObject();
                
                String editable=anode.getAttribute(ATT_EDITABLE);
                if(editable!=null && editable.equals("true"))
                {
                    jTree1.setEditable(true);
                }else
                {
                    jTree1.setEditable(false);
                }

                String view=anode.getAttribute(ATT_VIEW);
                String vtarget=anode.getAttribute(ATT_VTARGET);

                if(view!=null)
                {
                    if(view.startsWith(ACC_SHOWURL+"="))
                    {
                        String surl=view.substring(ACC_SHOWURL.length()+1);
                        try
                        {
                            URL url=new URL(this.getDocumentBase(),surl);
                            if(vtarget!=null)
                                this.getAppletContext().showDocument(url,vtarget);
                            else   
                                this.getAppletContext().showDocument(url);
                        }catch(Exception e){e.printStackTrace(System.out);}
                    }
                }
            }
        }
    }//GEN-LAST:event_jTree1ValueChanged

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTree1MouseClicked
    {//GEN-HEADEREND:event_jTree1MouseClicked
        // Add your handling code here:
        lastx=evt.getX();
        lasty=evt.getY();
        
        if(evt.getClickCount()==2 && (evt.getModifiers()&evt.BUTTON1_MASK)>0 && jTree1.getPathForLocation(evt.getX(),evt.getY())!=null)
        {
            DefaultMutableTreeNode node=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
            if(node.getUserObject() instanceof WBTreeNode)
            {
                //System.out.println("jTree1MouseClicked:"+node);
                WBTreeNode anode=(WBTreeNode)node.getUserObject();
                executeAction(node, anode);
            }
        }else if((evt.getModifiers()&evt.BUTTON3_MASK)>0&&jTree1.getPathForLocation(evt.getX(),evt.getY())!=null)
        {
            jTree1.setSelectionPath(jTree1.getPathForLocation(evt.getX(),evt.getY()));
            DefaultMutableTreeNode node=(DefaultMutableTreeNode)jTree1.getPathForLocation(evt.getX(),evt.getY()).getLastPathComponent();

            if(node.getUserObject() instanceof WBTreeNode)
            {
                Runnable doWorkRunnable = new Runnable() {
                    public void run() { 
                        showMenu();
                    }
                };
                SwingUtilities.invokeLater(doWorkRunnable);        
            }
            //jTree1.updateUI();
        }
         
    }//GEN-LAST:event_jTree1MouseClicked

    
    private void menuActionPerformed(java.awt.event.ActionEvent evt)
    {
        // Add your handling code here:
        //System.out.println("menuActionPerformed:"+evt.getActionCommand());
        String id=evt.getActionCommand();
        DefaultMutableTreeNode select=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
        WBTreeNode node=(WBTreeNode)select.getUserObject();
        WBTreeNode menu=node.getNodebyName(ND_MENU);
        executeOption(menu,id,select);
    }
    
    private void executeOption(WBTreeNode menu, String id, DefaultMutableTreeNode select)
    {
        Iterator it=menu.getNodes().iterator();
        while(it.hasNext())
        {
            WBTreeNode option=(WBTreeNode)it.next();
            if(id.equals(option.getAttribute(ATT_ID)))
            {
                executeAction(select, option);
                return;
            }else if(option.getNodesSize()>0)
            {
                executeOption(option,id,select);
            }
        }        
    }
    
    private void jTree1TreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException//GEN-FIRST:event_jTree1TreeWillExpand
    {//GEN-HEADEREND:event_jTree1TreeWillExpand
        //System.out.println("jTree1TreeWillExpand:"+evt);
        DefaultMutableTreeNode dnode=(DefaultMutableTreeNode)evt.getPath().getLastPathComponent();
        if(dnode.getUserObject() instanceof WBTreeNode)
        {
            WBTreeNode node=(WBTreeNode)dnode.getUserObject();
            //System.out.println("jTree1TreeWillExpand:"+node);
            WBTreeNode events=node.getNodebyName(ND_EVENTS);
            if(events!=null)
            {
                Iterator it=events.getNodesbyName(ND_WILLEXPAND);
                while(it.hasNext())
                {
                    WBTreeNode event=(WBTreeNode)it.next();
                    executeAction(dnode, event);
                }
            }
        }
    }//GEN-LAST:event_jTree1TreeWillExpand

    
    private void dragConfig()
    {
        DragSource dragSource = DragSource.getDefaultDragSource();
        dragSource.createDefaultDragGestureRecognizer(jTree1, DnDConstants.ACTION_COPY_OR_MOVE, this); 
    }
    
    private void dropConfig()
    {
        DropTargetListener dropLiten = new DropTargetListener()
        {
            public void dragEnter(DropTargetDragEvent enter)
            {
                debug("drop:"+"dragEnter:"+enter);
            }
            
            public void dragOver(DropTargetDragEvent over)
            {
                debug("drop:"+"dragOver:"+over);
            }
            
            public void dropActionChanged(DropTargetDragEvent action)
            {
                debug("drop:"+"dropActionChanged:"+action);
            }
            
            public void dragExit(DropTargetEvent exit)
            {
                debug("drop:"+"DropTargetEvent:"+exit);
            }
            
            public void drop(DropTargetDropEvent drop)
            {
                debug("drop:"+"DropTargetDropEvent:"+drop);
                try
                {
/*                    
                    Transferable transfer = drop.getTransferable();
                    if(transfer.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                    {
                        drop.acceptDrop(DnDConstants.ACTION_REFERENCE);
                        List file_list = (List)transfer.getTransferData(DataFlavor.javaFileListFlavor);
                        for(int i=0;i<file_list.size();i++)
                        {
                            String file_path = file_list.get(i).toString();
                            
                            File file = new File(file_path);
                            Vector data=new Vector();
                            data.add(file.getName());
                            data.add(""+file.length());
                            String tp=java.util.ResourceBundle.getBundle("locale",locale).getString("file");
                            if(file.isDirectory())
                                tp=java.util.ResourceBundle.getBundle("locale",locale).getString("directory");
                            data.add(tp);
                            data.add(new Date(file.lastModified()));
                            data.add(file);
                            model.addRow(data);
                        }
                        drop.getDropTargetContext().dropComplete(true);
                    }
 */
                }
                catch(Exception e)
                {
                    System.err.println(e);
                }
            }
        };
        DropTarget drop = new DropTarget(jTree1,DnDConstants.ACTION_REFERENCE,dropLiten);
    }
    
    public void dragDropEnd(DragSourceDropEvent dsde)
    {
        debug("Drag:"+"dragDropEnd:"+dsde);
    }    
    
    public void dragEnter(DragSourceDragEvent dsde)
    {
        debug("Drag:"+"dragEnter:"+dsde);
    }    
    
    public void dragExit(DragSourceEvent dse)
    {
        debug("Drag:"+"dragExit:"+dse);
    }
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
        debug("Drag:"+"dragGestureRecognized:"+dge);
        
        //debug("source:"+dge.getComponent());
        
        if(dge.getComponent() instanceof JTree)
        {
            JTree tree=(JTree)dge.getComponent();
            if(tree.getSelectionPath()!=null)
            {
                DefaultMutableTreeNode node=(DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
                if(node.getUserObject() instanceof WBTreeNode)
                {
                    WBTreeNode wbnode=(WBTreeNode)node.getUserObject();
                    if("true".equalsIgnoreCase(wbnode.getAttribute(ATT_DRAGENABLED)))
                    {
                        String value=wbnode.getAttribute(ATT_DRAGVALUE);
                        if(value==null)value=wbnode.getAttribute("id");
                        dge.startDrag(DragSource.DefaultCopyDrop,
                        new java.awt.datatransfer.StringSelection(value)
                        , this);
                    }
                }
            }
        }
    }
    
    public void dragOver(DragSourceDragEvent dsde)
    {
        /*
        debug("Drag:"+"dragOver:",false);
        debug(" Modifiers:"+dsde.getGestureModifiers(),false);
        debug(" ModifiersEx:"+dsde.getGestureModifiersEx(),false);
        debug(" Action:"+dsde.getDropAction(),false);
        debug(" TargAction:"+dsde.getTargetActions(),false);
        debug(" UserAction:"+dsde.getUserAction(),true);
        */
    }
    
    public void dropActionChanged(DragSourceDragEvent dsde)
    {
        debug("Drag:"+"dropActionChanged:"+dsde);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JPopupMenu menu;
    private javax.swing.JTabbedPane tabbed;
    // End of variables declaration//GEN-END:variables

    void debug(String txt)
    {
        debug(txt,true);
    }
    
    void debug(String txt, boolean ret)
    {
        if(vdebug)
        {
            if(ret)
            {
                System.out.println(txt);
            }else
            {
                System.out.print(txt);
            }
        }
    }
}
