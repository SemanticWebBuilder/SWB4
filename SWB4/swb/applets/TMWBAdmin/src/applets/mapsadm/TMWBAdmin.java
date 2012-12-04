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
 * TMWBAdmin.java
 *
 * Created on 14 de octubre de 2002, 11:27
 */

package applets.mapsadm;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.applet.AppletContext;

import applets.commons.*;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author  Administrador
 */
public class TMWBAdmin extends javax.swing.JApplet implements TMWBContainer
{
    private static int bufferSize=8192;     
    
    private Hashtable langs=new Hashtable();
    private final String IDTPLANG = "IDM_WB";
    
    private int admtype=1;
    private WBTreeNode admuser;
    
    private DefaultTreeModel namemodel;
    private DefaultMutableTreeNode nameroot;
    private WBNamesRenderer namerenderer;
    private WBTreeCellEditor namecelleditor;
    
    //private String cgi="";                      //cgi del gateway
    private String doc="/wb/";                      //cgi del admin
    private String jsess="";                    //session del usuario
    //private URL gurl=null;                      //url de gateway
    
    private DefaultMutableTreeNode selected=null;
    //private WBTimer timer;
    private int lastx;
    private int lasty;

    private final String backgroundParam = "background";
    private final String foregroundParam = "foreground";
    private final String backgroundSelectionParam = "backgroundSelection";
    private final String foregroundSelectionParam = "foregroundSelection";
    private final String cgiPathParam = "cgipath";
    private String cgiPath = null;
    protected URL url=null;
    
    private TMViewer viewer=null;
    private ModelRelation relations;
    private boolean editing=false;
    private boolean nameschange=false;
    private boolean assochange=false;

    private SelectRelation selectRel;
    private SelectTopic selectTop;
    protected AppObject edit,auxdrag;
    
    protected boolean drag=false;
    protected int dragx,dragy;
    protected Cursor curdrag=new Cursor(Cursor.TEXT_CURSOR);
    protected Cursor curdrag2=new Cursor(Cursor.MOVE_CURSOR);    
    
    WBTopicMap tm=null;
    private Locale locale=Locale.getDefault();
    
    /** Creates new form TMWBAdmin */
    public TMWBAdmin()
    {
    }
    
    public void init() 
    {
        //System.out.println("Hola");
        cgiPath = getParameter(cgiPathParam);
        try
        {
            if(cgiPath!=null)
                url=new URL(getCodeBase(),cgiPath);
                //url=new URL("http://comunidad.infotec.com.mx"+cgiPath);
        }catch(Exception e){}
        //System.out.println(url);
        
        jsess=this.getParameter("jsess");
        
        String loc=this.getParameter("locale");
        if(loc!=null)locale=new Locale(loc);
        
/*        
        getUserAttr();
        root=getTopicMaps();
*/
        nameroot = new DefaultMutableTreeNode(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("names.title"));
        namemodel = new DefaultTreeModel(nameroot);
        initComponents();
        viewer=(TMViewer)jPanel1;
        ToolTipManager.sharedInstance().registerComponent(jTree2);      //dar de alta tool tips.
        this.getContentPane().add(topicMenu);                           //dar de alta popup menu topics
        this.getContentPane().add(nameMenu);                            //dar de alta popup menu nombres
        this.getContentPane().add(TypeMenu);                            //dar de alta popup menu typos
        this.getContentPane().add(RelationMenu);                        //dar de alta popup menu relations
        //timer=new WBTimer(this);                                        //timer retardo de popup
        jSplitPane1.setDividerLocation((int)(0.6f*this.getHeight()));
        namemodel.addTreeModelListener(new WBTreeModelListener(this));   //add listener de names...
        getTopicMaps();
        //jPanel2.hide();
        //jTree2.hide();
    }
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        topicMenu = new java.awt.PopupMenu();
        topicItem1 = new java.awt.MenuItem();
        topicItem8 = new java.awt.MenuItem();
        topicItem0 = new java.awt.MenuItem();
        checkboxMenuItem1 = new java.awt.CheckboxMenuItem();
        topicItem5 = new java.awt.MenuItem();
        topicItem6 = new java.awt.MenuItem();
        topicItem9 = new java.awt.MenuItem();
        topicItem3 = new java.awt.MenuItem();
        menuItem13 = new java.awt.MenuItem();
        topicItem7 = new java.awt.MenuItem();
        topicItem4 = new java.awt.MenuItem();
        nameMenu = new java.awt.PopupMenu();
        menuItem1 = new java.awt.MenuItem();
        menuItem12 = new java.awt.MenuItem();
        menuItem2 = new java.awt.MenuItem();
        menuItem3 = new java.awt.MenuItem();
        menu1 = new java.awt.Menu();
        RelationMenu = new java.awt.PopupMenu();
        menuItem4 = new java.awt.MenuItem();
        menuItem5 = new java.awt.MenuItem();
        menuItem6 = new java.awt.MenuItem();
        menuItem7 = new java.awt.MenuItem();
        menuItem9 = new java.awt.MenuItem();
        TypeMenu = new java.awt.PopupMenu();
        menuItem8 = new java.awt.MenuItem();
        menuItem14 = new java.awt.MenuItem();
        menuItem10 = new java.awt.MenuItem();
        menuItem11 = new java.awt.MenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        maps = new javax.swing.JComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new TMViewer(this, locale);
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        types = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableRelations = new javax.swing.JTable();

        topicMenu.setLabel("PopupMenu");
        topicMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                topicMenuActionPerformed(evt);
            }
        });

        topicItem1.setActionCommand("create");
        topicItem1.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.new"));
        topicMenu.add(topicItem1);

        topicItem8.setActionCommand("edit");
        topicItem8.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.edit"));
        topicItem8.setName("");
        topicMenu.add(topicItem8);

        topicItem0.setActionCommand("open");
        topicItem0.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.open"));
        topicMenu.add(topicItem0);

        checkboxMenuItem1.setActionCommand("status");
        checkboxMenuItem1.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.active"));
        checkboxMenuItem1.setState(true);
        checkboxMenuItem1.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                checkboxMenuItem1ItemStateChanged(evt);
            }
        });

        topicMenu.add(checkboxMenuItem1);

        topicMenu.addSeparator();
        topicItem5.setActionCommand("home");
        topicItem5.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.home"));
        topicItem5.setName("");
        topicMenu.add(topicItem5);

        topicItem6.setActionCommand("goto");
        topicItem6.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.goto"));
        topicMenu.add(topicItem6);

        topicMenu.addSeparator();
        topicItem9.setActionCommand("borrado");
        topicItem9.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.markDelete"));
        topicItem9.setName("");
        topicMenu.add(topicItem9);

        topicItem3.setActionCommand("delete");
        topicItem3.setEnabled(false);
        topicItem3.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.delete"));
        topicMenu.add(topicItem3);

        menuItem13.setActionCommand("deleteR");
        menuItem13.setEnabled(false);
        menuItem13.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.deleteChild"));
        topicMenu.add(menuItem13);

        topicMenu.addSeparator();
        topicItem7.setActionCommand("update");
        topicItem7.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.reload"));
        topicMenu.add(topicItem7);

        topicItem4.setActionCommand("refresh");
        topicItem4.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.refresh"));
        topicMenu.add(topicItem4);

        nameMenu.setLabel("PopupMenu");
        nameMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nameMenuActionPerformed(evt);
            }
        });

        menuItem1.setActionCommand("add");
        menuItem1.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.name.add"));
        nameMenu.add(menuItem1);

        menuItem12.setActionCommand("addsortname");
        menuItem12.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.name.addSort"));
        nameMenu.add(menuItem12);

        nameMenu.addSeparator();
        menuItem2.setActionCommand("edit");
        menuItem2.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.name.edit"));
        nameMenu.add(menuItem2);

        menuItem3.setActionCommand("delete");
        menuItem3.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.name.delete"));
        nameMenu.add(menuItem3);

        nameMenu.addSeparator();
        menu1.setActionCommand("assign");
        menu1.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.name.setLanguage"));
        menu1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menu1ActionPerformed(evt);
            }
        });

        nameMenu.add(menu1);

        RelationMenu.setLabel("PopupMenu");
        RelationMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                RelationMenuActionPerformed(evt);
            }
        });

        menuItem4.setActionCommand("add");
        menuItem4.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.ass.add"));
        RelationMenu.add(menuItem4);

        RelationMenu.addSeparator();
        menuItem5.setActionCommand("edit");
        menuItem5.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.ass.edit"));
        RelationMenu.add(menuItem5);

        menuItem6.setActionCommand("copy");
        menuItem6.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.ass.copy"));
        RelationMenu.add(menuItem6);

        menuItem7.setActionCommand("delete");
        menuItem7.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.ass.delete"));
        RelationMenu.add(menuItem7);

        RelationMenu.addSeparator();
        menuItem9.setActionCommand("clear");
        menuItem9.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.ass.clear"));
        RelationMenu.add(menuItem9);

        TypeMenu.setLabel("PopupMenu");
        TypeMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                TypeMenuActionPerformed(evt);
            }
        });

        menuItem8.setActionCommand("add");
        menuItem8.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.type.add"));
        TypeMenu.add(menuItem8);

        menuItem14.setActionCommand("up");
        menuItem14.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.type.up"));
        TypeMenu.add(menuItem14);

        TypeMenu.addSeparator();
        menuItem10.setActionCommand("delete");
        menuItem10.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.type.delete"));
        TypeMenu.add(menuItem10);

        menuItem11.setActionCommand("clear");
        menuItem11.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.type.clear"));
        TypeMenu.add(menuItem11);

        usePageParams();
        jToolBar1.setToolTipText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("grp.toolbar"));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b_guardar.gif")));
        jButton3.setToolTipText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ico.save"));
        jButton3.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        jButton3.setBorderPainted(false);
        jButton3.setFocusCycleRoot(true);
        jButton3.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton3.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton3.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton3.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton3.setSelected(true);
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButton3MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButton3MouseEntered(evt);
            }
        });

        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b_cancelar.gif")));
        jButton4.setToolTipText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ico.cancel"));
        jButton4.setBorderPainted(false);
        jButton4.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton4.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton4.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton4.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButton4MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButton4MouseEntered(evt);
            }
        });

        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b_nuevo.gif")));
        jButton5.setToolTipText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ico.new"));
        jButton5.setBorderPainted(false);
        jButton5.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton5.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton5.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton5ActionPerformed(evt);
            }
        });
        jButton5.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButton5MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButton5MouseEntered(evt);
            }
        });

        jToolBar1.add(jButton5);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b_editar.gif")));
        jButton1.setToolTipText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ico.edit"));
        jButton1.setBorderPainted(false);
        jButton1.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton1.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButton1MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButton1MouseEntered(evt);
            }
        });

        jToolBar1.add(jButton1);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b_borrar.gif")));
        jButton6.setToolTipText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ico.markDelete"));
        jButton6.setBorderPainted(false);
        jButton6.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton6.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton6.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton6ActionPerformed(evt);
            }
        });
        jButton6.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButton6MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButton6MouseEntered(evt);
            }
        });

        jToolBar1.add(jButton6);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b_abrir.gif")));
        jButton2.setToolTipText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ico.open"));
        jButton2.setBorderPainted(false);
        jButton2.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton2.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton2.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButton2MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButton2MouseEntered(evt);
            }
        });

        jToolBar1.add(jButton2);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("lbl.Map"));
        jPanel3.add(jLabel2);

        maps.setPreferredSize(new java.awt.Dimension(130, 25));
        maps.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mapsActionPerformed(evt);
            }
        });

        jPanel3.add(maps);

        jToolBar1.add(jPanel3);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setToolTipText("");
        jPanel1.setMinimumSize(new java.awt.Dimension(50, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 300));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                jPanel1MouseReleased(evt);
            }
        });
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                jPanel1MouseDragged(evt);
            }
        });

        jSplitPane1.setTopComponent(jPanel1);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.X_AXIS));

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel4.setMinimumSize(new java.awt.Dimension(100, 22));
        jPanel4.setPreferredSize(new java.awt.Dimension(150, 150));
        jLabel1.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel1.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("lbl.names"));
        jPanel4.add(jLabel1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(150, 22));
        jTree2.setToolTipText("");
        namecelleditor=new WBTreeCellEditor(jTree2,namerenderer,null,this);
        jTree2.setCellEditor(namecelleditor);
        jTree2.setCellRenderer(namerenderer);
        jTree2.setEditable(true);
        jTree2.setInvokesStopCellEditing(true);
        jTree2.setModel(namemodel);
        jTree2.setAutoscrolls(true);
        jTree2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTree2MouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(jTree2);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel6.setMinimumSize(new java.awt.Dimension(50, 0));
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 150));
        jLabel3.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel3.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("lbl.parents"));
        jPanel6.add(jLabel3, java.awt.BorderLayout.NORTH);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(100, 22));
        types.setFont(new java.awt.Font("Dialog", 0, 12));
        types.setModel(new DefaultListModel());
        types.setToolTipText("");
        types.setDragEnabled(true);
        types.setMinimumSize(new java.awt.Dimension(100, 0));
        types.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                typesMouseClicked(evt);
            }
        });

        jScrollPane3.setViewportView(types);

        jPanel6.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel6);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel5.setMinimumSize(new java.awt.Dimension(150, 35));
        jPanel5.setPreferredSize(new java.awt.Dimension(500, 150));
        jLabel4.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel4.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("lbl.associations"));
        jPanel5.add(jLabel4, java.awt.BorderLayout.NORTH);

        jScrollPane2.setBorder(null);
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setOpaque(false);
        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jScrollPane2MouseClicked(evt);
            }
        });

        tableRelations.setBorder(new javax.swing.border.CompoundBorder());
        relations = new ModelRelation(new String [] {java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ass.type"),java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ass.role"),java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ass.related"), java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("ass.relatedRole")},0);
        TableSorter sorter = new TableSorter(relations); //ADDED THIS
        tableRelations.setModel(sorter);
        tableRelations.setToolTipText("");
        tableRelations.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tableRelationsMouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(tableRelations);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5);

        jSplitPane1.setBottomComponent(jPanel2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton6ActionPerformed
    {//GEN-HEADEREND:event_jButton6ActionPerformed
        // Add your handling code here:
            frmBorradoTopic(tm,viewer.actual);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        try{
            this.getAppletContext().showDocument(new URL(url,doc+tm.getId()+"/"+viewer.actual.getId()),"_new");        
        }catch(Exception e){System.out.println("Error opening section..."+e.getMessage());}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanel1MouseDragged
    {//GEN-HEADEREND:event_jPanel1MouseDragged
        // Add your handling code here:
        if(auxdrag!=null)
        {
            if(!drag)
            {
                drag=true;
                setCursor(curdrag);
            }
            else if(types.isVisible() || tableRelations.isVisible())
            {
                int dragx=evt.getX()+(int)jPanel1.getLocationOnScreen().getX();
                int dragy=evt.getY()+(int)jPanel1.getLocationOnScreen().getY();
                int typex=(int)jScrollPane3.getLocationOnScreen().getX();
                int typey=(int)jScrollPane3.getLocationOnScreen().getY();
                int typew=(int)jScrollPane3.getWidth();
                int typeh=(int)jScrollPane3.getHeight();
                int relx=(int)jScrollPane2.getLocationOnScreen().getX();
                int rely=(int)jScrollPane2.getLocationOnScreen().getY();
                int relw=(int)jScrollPane2.getWidth();
                int relh=(int)jScrollPane2.getHeight();
                
                if((tableRelations.isVisible()&&dragx>relx&&dragy>rely&&dragx<relx+relw&&dragy<rely+relh) || (types.isVisible()&&dragx>typex&&dragy>typey&&dragx<typex+typew&&dragy<typey+typeh))
                {
                    if(getCursor()!=curdrag)setCursor(curdrag);
                }
                else
                {
                    if(getCursor()!=curdrag2)setCursor(curdrag2);
                }
            }
        }
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanel1MouseReleased
    {//GEN-HEADEREND:event_jPanel1MouseReleased
        // Add your handling code here:
        //System.out.println("mouse release drag:"+drag+" "+auxdrag);
        if(drag)
        {
            drag=false;
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            if(auxdrag!=null)
            {
                
                int dragx=evt.getX()+(int)jPanel1.getLocationOnScreen().getX();
                int dragy=evt.getY()+(int)jPanel1.getLocationOnScreen().getY();
                int typex=(int)jScrollPane3.getLocationOnScreen().getX();
                int typey=(int)jScrollPane3.getLocationOnScreen().getY();
                int typew=(int)jScrollPane3.getWidth();
                int typeh=(int)jScrollPane3.getHeight();
                int relx=(int)jScrollPane2.getLocationOnScreen().getX();
                int rely=(int)jScrollPane2.getLocationOnScreen().getY();
                int relw=(int)jScrollPane2.getWidth();
                int relh=(int)jScrollPane2.getHeight();
                
                //System.out.println("dragx:"+dragx+" dragy:"+dragy);
                //System.out.println("typex:"+typex+" typey:"+typey);
                //System.out.println("relx:"+relx+" rely:"+relx);
                //System.out.println(evt);
                
                if(tableRelations.isVisible()&&dragx>relx&&dragy>rely&&dragx<relx+relw&&dragy<rely+relh)
                {
                    selectRel=new SelectRelation(viewer.obj,viewer.objSort,auxdrag,relations,locale);
                    selectRel.show();
                    setEditing(true,3);
                }

                if(types.isVisible()&&dragx>typex&&dragy>typey&&dragx<typex+typew&&dragy<typey+typeh)
                {
                    //System.out.println(viewer.actualchange+" "+auxdrag+" "+edit);
                    if(edit!=auxdrag && !auxdrag.isChildof(edit))
                    {
                        DefaultListModel m=(DefaultListModel)types.getModel();
                        if(m.indexOf(auxdrag)<0)
                        {
                            m.addElement(auxdrag);
                            setEditing(true,2);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("type.usrmsg.already"),java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("type.usrmsg.title"),JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("type.usrmsg.cyclicError"),java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("type.usrmsg.title"),JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        
    }//GEN-LAST:event_jPanel1MouseReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton5ActionPerformed
    {//GEN-HEADEREND:event_jButton5ActionPerformed
        // Add your handling code here:
        frmCreateTopic(tm,viewer.actual);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton2MouseEntered
    {//GEN-HEADEREND:event_jButton2MouseEntered
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(true);        
    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton2MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton2MouseExited
    {//GEN-HEADEREND:event_jButton2MouseExited
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(false);        
    }//GEN-LAST:event_jButton2MouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        // Add your handling code here:
        setEditing(true,0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton1MouseExited
    {//GEN-HEADEREND:event_jButton1MouseExited
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(false);        
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton1MouseEntered
    {//GEN-HEADEREND:event_jButton1MouseEntered
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(true);        
    }//GEN-LAST:event_jButton1MouseEntered

    private void checkboxMenuItem1ItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_checkboxMenuItem1ItemStateChanged
    {//GEN-HEADEREND:event_checkboxMenuItem1ItemStateChanged
        // Add your handling code here:
        WBTopicMap tm=(WBTopicMap)maps.getSelectedItem();
        if(viewer.auxedit.getActive()==0)
            viewer.auxedit.setActive(1);
        else
            viewer.auxedit.setActive(0);

        String err=changeStatus(tm.getId(),viewer.auxedit);
        if(err!=null)
        {
            JOptionPane.showMessageDialog(this,err,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.status.title"),JOptionPane.ERROR_MESSAGE);
            if(viewer.auxedit.getActive()==0)
                viewer.auxedit.setActive(1);
            else
                viewer.auxedit.setActive(0);
        }
        viewer.auxedit.setOverFontColor();
        //viewer.auxedit.setOverBackColor();
        //viewer.organizeMap();
        //viewer.setActual(viewer.auxedit.getId());
    }//GEN-LAST:event_checkboxMenuItem1ItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        // Add your handling code here:
        String acc="Update";
        AppObject edit=viewer.edit;
        JTree names=jTree2;
/*        
        if(edit==null)
        {
            acc="Add";
            edit=new AppObject(sgmlid.getText());
            initTopic(edit);
            obj.put(edit.id,edit);
        }
*/
        edit.names=new Vector();
        Object root=names.getModel().getRoot();
        int z=names.getModel().getChildCount(root);
        for(int x=0;x<z;x++)
        {
            DefaultMutableTreeNode child=(DefaultMutableTreeNode)names.getModel().getChild(root,x);
            //cambiar a idioma seleccionado
            //System.out.println("name:"+child.toString());
            if(x==0)
            {
                edit.setFontSize(16);
                edit.setName(child.toString());
            }
            
            edit.names.addElement("n:"+child.toString());
            //System.out.println("child:"+child);
            if(child.getUserObject() instanceof ScopedName)
            {
                String scope=((ScopedName)child.getUserObject()).getScope();
                if(scope!=null)edit.names.addElement("s:"+scope);
            }
            int h=names.getModel().getChildCount(child);
            for(int y=0;y<h;y++)
            {
                Object child2=names.getModel().getChild(child,y);
                edit.names.addElement("v:"+child2);
            }
        }

        //********************************* Remover Tipos y relaciones *************************************************
        Vector toRem=new Vector();
        Enumeration en=edit.assoc.elements();
        while(en.hasMoreElements())
        {
            AppAssoc as=(AppAssoc)en.nextElement();
            if(as.type!=2)
            {
                toRem.addElement(as);
            }
        }
        en=toRem.elements();
        while(en.hasMoreElements())
        {
            AppAssoc as=(AppAssoc)en.nextElement();
            //System.out.println("topic:"+as.topic+" type:"+as.type+" idtype:"+as.idtype+" name:"+as.name+" role:"+as.role);
            viewer.removeAss(edit,as.topic);
        }



        //********************************** Agregar Tipos ************************************************
        z=types.getModel().getSize();
        for(int x=0;x<z;x++)
        {
            AppObject to=(AppObject)types.getModel().getElementAt(x);
            edit.assoc.addElement(new AppAssoc(to,0,"","","",""));
            to.assoc.addElement(new AppAssoc(edit,2,"","","",""));
        }

        //********************************** Agregar Relaciones ************************************************
        z=relations.getRowCount();
        for(int x=0;x<z;x++)
        {
            //System.out.println("to:"+relations.getValueAt(x,2)+" tipo:"+relations.getValueAt(x,0));
            AppObject to=(AppObject)relations.getValueAt(x,2);
            AppObject tipo=(AppObject)relations.getValueAt(x,0);
            //System.out.println("to:"+to+" tipo:"+tipo);
            edit.assoc.addElement(new AppAssoc(to,1,tipo.id,tipo.name,((AppObject)relations.getValueAt(x,3)).id,((AppObject)relations.getValueAt(x,1)).id));
            to.assoc.addElement(new AppAssoc(edit,3,tipo.id,tipo.name,((AppObject)relations.getValueAt(x,1)).id,((AppObject)relations.getValueAt(x,3)).id));
        }

        //send update ......
        StringBuffer str=new StringBuffer();
        if(assochange && nameschange)
            str.append("Type:3\n");
        else if(assochange)
            str.append("Type:2\n");
        else 
            str.append("Type:1\n");
        
        if(nameschange||assochange)
        {
            str.append(acc+" Topic\n");
            str.append("i:");
            str.append(edit.getId());
            str.append("\n");
            en=edit.names.elements();
            while(en.hasMoreElements())
            {
                str.append(en.nextElement());; 
                str.append("\n");
            }
        }
        if(assochange)
        {
            Enumeration ass=edit.assoc.elements();
            while(ass.hasMoreElements())
            {
                AppAssoc asoc=(AppAssoc)ass.nextElement();
                if(asoc.type==0||asoc.type==1)
                {
                    str.append("Update Association"+"\n");
                    //System.out.println("i:");
                    str.append("t:"+asoc.idtype+"\n");
                    str.append("n:"+asoc.name+"\n");
                    str.append("r:"+asoc.role+"\n");
                    str.append("p:"+asoc.topic.id+"\n");
                    str.append("r:"+asoc.relrole+"\n");
                    str.append("p:"+edit.id+"\n");
                }
            }
        }
        //System.out.println(str.toString());
        try
        {
            String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><topicmap>"+((WBTopicMap)maps.getSelectedItem()).getId()+"</topicmap><cmd>setTopicMap4Adm</cmd><data>"+WBXMLParser.replaceStrChars(str.toString())+"</data></req>";
            xml=WBXMLParser.encode(xml,"UTF8");
            //System.out.println(xml);
            String ret=getData(xml);
        }catch(Exception e){System.out.println("Error to generate the Topic's XML...");}

        viewer.sortTopics();

        viewer.setActual(edit.getId());
        viewer.organizeMap();
        setEditing(false,0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void mapsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mapsActionPerformed
    {//GEN-HEADEREND:event_mapsActionPerformed
        // Add your handling code here:
        if(evt.getModifiers()>0)
        {
            tm=(WBTopicMap)maps.getSelectedItem();

            setTopicMap(tm.getId(), tm.getHome());
            //else send error al traer mapa
        }
    }//GEN-LAST:event_mapsActionPerformed

    public void setTopicMap(String tmid, String homeid)
    {
        String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><topicmap>"+tmid+"</topicmap><cmd>getTopicMap4Adm</cmd></req>");
        System.out.println("data1:"+data);
        data=getCData(data,"map");
        if(data.length()>0)
        {
            viewer.candraw=false;
            viewer.over=null;
            viewer.getMap(data);
            System.out.println("data2:"+data);
            viewer.setActual(homeid);
            System.out.println("homeid:"+homeid);
            viewer.home=tm.getHome();
            viewer.topicMap=tmid;
            System.out.println("tmid:"+tmid);
            viewer.candraw=true;   
            setEditing(false,0);
            getLanguages(tmid);
            System.out.println(viewer.actual);
        }        
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton4ActionPerformed
    {//GEN-HEADEREND:event_jButton4ActionPerformed
        // Add your handling code here:
        setEditing(false,0);
        mapChange();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void TypeMenuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_TypeMenuActionPerformed
    {//GEN-HEADEREND:event_TypeMenuActionPerformed
        // Add your handling code here:
        if(evt.getActionCommand().equals("add"))
        {
            DefaultListModel list=(DefaultListModel)types.getModel();
            selectTop=new SelectTopic(viewer.objSort,list,edit,this,locale);
            selectTop.show();
        }else if(evt.getActionCommand().equals("up"))
        {
            int ind=types.getSelectedIndex();
            //System.out.println("ind:"+ind);
            Object obj=((DefaultListModel)types.getModel()).remove(ind);
            if(ind>0)ind--;
            ((DefaultListModel)types.getModel()).insertElementAt(obj,ind);
            types.setSelectedIndex(ind);
            setEditing(true,2);
        }        
        else if(evt.getActionCommand().equals("delete"))
        {
            ((DefaultListModel)types.getModel()).remove(types.getSelectedIndex());
            setEditing(true,2);
        }
        else if(evt.getActionCommand().equals("clear"))
        {
            DefaultListModel lm=(DefaultListModel)types.getModel();
            if(lm.size()!=0)
            {
                lm.clear();
                setEditing(true,2);                
            }
        }
    }//GEN-LAST:event_TypeMenuActionPerformed

    private void RelationMenuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_RelationMenuActionPerformed
    {//GEN-HEADEREND:event_RelationMenuActionPerformed
        // Add your handling code here:
        if(evt.getActionCommand().equals("add"))
        {
            selectRel=new SelectRelation(viewer.obj,viewer.objSort,null,relations,locale);
            selectRel.show();
        }
        else if(evt.getActionCommand().equals("edit"))
        {
            selectRel=new SelectRelation(viewer.obj,viewer.objSort,null,relations,locale);
            selectRel.edit(tableRelations.getSelectedRow());
            selectRel.show();
        }
        else if(evt.getActionCommand().equals("copy"))
        {
            int row=tableRelations.getSelectedRow();
            Object[] oaux={relations.getValueAt(row,0),relations.getValueAt(row,1),relations.getValueAt(row,2),relations.getValueAt(row,3)};
            relations.addRow(oaux);
            //selectRel=new SelectRelation(obj,objSort,null,relations);
            //selectRel.edit(tableRelations.getSelectedRow());
            //selectRel.show();
        }
        else if(evt.getActionCommand().equals("delete"))
        {
            relations.removeRow(tableRelations.getSelectedRow());
        }
        else if(evt.getActionCommand().equals("clear"))
        {
            relations.setNumRows(0);
        }
        setEditing(true,3);
    }//GEN-LAST:event_RelationMenuActionPerformed

    private void jButton6MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton6MouseExited
    {//GEN-HEADEREND:event_jButton6MouseExited
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(false);
    }//GEN-LAST:event_jButton6MouseExited

    private void jButton6MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton6MouseEntered
    {//GEN-HEADEREND:event_jButton6MouseEntered
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(true);
    }//GEN-LAST:event_jButton6MouseEntered

    private void jButton5MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton5MouseExited
    {//GEN-HEADEREND:event_jButton5MouseExited
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(false);
    }//GEN-LAST:event_jButton5MouseExited

    private void jButton5MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton5MouseEntered
    {//GEN-HEADEREND:event_jButton5MouseEntered
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(true);
    }//GEN-LAST:event_jButton5MouseEntered

    private void jButton4MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton4MouseExited
    {//GEN-HEADEREND:event_jButton4MouseExited
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(false);
    }//GEN-LAST:event_jButton4MouseExited

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton4MouseEntered
    {//GEN-HEADEREND:event_jButton4MouseEntered
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(true);
    }//GEN-LAST:event_jButton4MouseEntered

    private void tableRelationsMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tableRelationsMouseClicked
    {//GEN-HEADEREND:event_tableRelationsMouseClicked
        // Add your handling code here:
        if(!tableRelations.isEnabled())return;
        
        //System.out.println(evt);
        if((evt.getModifiers()&evt.BUTTON3_MASK)>0)
        {
            if(tableRelations.getSelectedRow()!=-1)
            {
                RelationMenu.getItem(2).enable();
                RelationMenu.getItem(3).enable();
                RelationMenu.getItem(4).enable();
            }else
            {
                RelationMenu.getItem(2).disable();
                RelationMenu.getItem(3).disable();
                RelationMenu.getItem(4).disable();
            }
            //RelationMenu.show(getContentPane(),(int)evt.getComponent().getLocationOnScreen().getX()-(int)this.getLocationOnScreen().getX()+evt.getX(),(int)evt.getComponent().getLocationOnScreen().getY()-(int)this.getLocationOnScreen().getY()+evt.getY());
            RelationMenu.show(evt.getComponent(),evt.getX(),evt.getY());
        }
    }//GEN-LAST:event_tableRelationsMouseClicked

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jScrollPane2MouseClicked
    {//GEN-HEADEREND:event_jScrollPane2MouseClicked
        // Add your handling code here:
        tableRelationsMouseClicked(evt);
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void typesMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_typesMouseClicked
    {//GEN-HEADEREND:event_typesMouseClicked
        if(!types.isEnabled())return;
        
        // Add your handling code here:
        if((evt.getModifiers()&evt.BUTTON3_MASK)>0)
        {
            if(types.getSelectedIndex()>-1)
            {
                TypeMenu.getItem(2).enable();
            }
            else
            {
                TypeMenu.getItem(2).disable();
            }
            //TypeMenu.show(getContentPane(),(int)evt.getComponent().getLocationOnScreen().getX()-(int)this.getLocationOnScreen().getX()+evt.getX(),(int)evt.getComponent().getLocationOnScreen().getY()-(int)this.getLocationOnScreen().getY()+evt.getY());
            TypeMenu.show(evt.getComponent(),evt.getX(),evt.getY());
        }        
    }//GEN-LAST:event_typesMouseClicked

    private void menu1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menu1ActionPerformed
    {//GEN-HEADEREND:event_menu1ActionPerformed
        // Add your handling code here:
        if(langs.containsValue(evt.getActionCommand()))
        {
            DefaultMutableTreeNode selec=(DefaultMutableTreeNode)jTree2.getSelectionPath().getLastPathComponent();
            String scope=null;
            Enumeration en=langs.keys();
            while(en.hasMoreElements())
            {
                String st=(String)en.nextElement();
                if(langs.get(st).equals(evt.getActionCommand()))
                {
                    scope=st;
                }
            }
            
            if(selec.getUserObject() instanceof ScopedName)
            {
                ScopedName sc=(ScopedName)selec.getUserObject();
                sc.setLanguage(evt.getActionCommand());
                sc.setScope(scope);
            }else
            {
                ScopedName sc=new ScopedName((String)selec.getUserObject(),scope,(String)evt.getActionCommand());
                selec.setUserObject(sc);
            }
            //syncNames();
            //WBTopicMap tm=(WBTopicMap)((DefaultMutableTreeNode)selected.getPath()[1]).getUserObject();
            //WBTopic tp=(WBTopic)selected.getUserObject();
            //this.updateTopic(tm.getId(),tp);
            jTree2.updateUI();
            setEditing(true,1);
        }
    }//GEN-LAST:event_menu1ActionPerformed

    private void nameMenuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameMenuActionPerformed
    {//GEN-HEADEREND:event_nameMenuActionPerformed
        // Add your handling code here:
        boolean change=false;
        if(evt.getActionCommand().equals("add"))
        {
            DefaultMutableTreeNode selec=(DefaultMutableTreeNode)jTree2.getSelectionPath().getLastPathComponent();
            DefaultMutableTreeNode aux=new DefaultMutableTreeNode("");
            namemodel.insertNodeInto(aux,selec,selec.getChildCount());
            jTree2.setSelectionPath(new javax.swing.tree.TreePath(aux.getPath()));
            jTree2.scrollPathToVisible(new javax.swing.tree.TreePath(aux.getPath()));
            jTree2.startEditingAtPath(new javax.swing.tree.TreePath(aux.getPath()));
            change=true;
        }
        else if(evt.getActionCommand().equals("edit"))
        {
            if (jTree2.getSelectionPath() != null)
            {
                jTree2.startEditingAtPath(jTree2.getSelectionPath());
            }
            change=true;
        }
        else if(evt.getActionCommand().equals("delete"))
        {
            removeCurrentNode(jTree2,this.namemodel);
            change=true;
        }
        else if(evt.getActionCommand().equals("addsortname"))
        {
            DefaultMutableTreeNode selec=(DefaultMutableTreeNode)jTree2.getSelectionPath().getLastPathComponent();
            DefaultMutableTreeNode aux=new DefaultMutableTreeNode("");
            ScopedName scn=new ScopedName();
            scn.setName("");
            scn.setScope("CNF_WBSortName");
            scn.setLanguage("SortName");
            aux.setUserObject(scn);
            namemodel.insertNodeInto(aux,selec,selec.getChildCount());
            jTree2.setSelectionPath(new javax.swing.tree.TreePath(aux.getPath()));
            jTree2.scrollPathToVisible(new javax.swing.tree.TreePath(aux.getPath()));
            jTree2.startEditingAtPath(new javax.swing.tree.TreePath(aux.getPath()));
            change=true;
        }
        
        if(change)setEditing(true,1);
    }//GEN-LAST:event_nameMenuActionPerformed

    private void topicMenuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_topicMenuActionPerformed
    {//GEN-HEADEREND:event_topicMenuActionPerformed
        // Add your handling code here:
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        //System.out.println(evt);
            
        if(evt.getActionCommand().equals("create"))
        {
            frmCreateTopic(tm,viewer.auxedit);
        }
        if(evt.getActionCommand().equals("edit"))
        {
            setEditing(true,0,viewer.auxedit);            
            viewer.setActual(viewer.auxedit.getId());
        }
        else if(evt.getActionCommand().equals("open"))
        {
            try{
                this.getAppletContext().showDocument(new URL(url,doc+tm.getId()+"/"+viewer.auxedit.getId()),"_new");
            }catch(Exception e){System.out.println("Error opening section..."+e.getMessage());}            
        }
        else if(evt.getActionCommand().equals("delete"))
        {
            frmDeleteTopic(tm,viewer.auxedit);
        }
        else if(evt.getActionCommand().equals("deleteR"))
        {
            frmDeleteTopicR(tm,viewer.auxedit);
        }
        else if(evt.getActionCommand().equals("borrado"))
        {
            frmBorradoTopic(tm,viewer.auxedit);
        }
        else if(evt.getActionCommand().equals("goto"))
        {
            selectTop=new SelectTopic(viewer.objSort,viewer,locale);
            selectTop.show();
        }
        else if(evt.getActionCommand().equals("home"))
        {
            viewer.setActual(viewer.home);
        }
        else if(evt.getActionCommand().equals("refresh"))
        {
            viewer.organizeMap();
            viewer.changes=true;
        }
        else if(evt.getActionCommand().equals("update"))
        {
            String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><topicmap>"+tm.getId()+"</topicmap><cmd>getTopicMap4Adm</cmd></req>");
            data=getCData(data);
            if(data.length()>0)
            {
                viewer.candraw=false;
                String ant=viewer.actual.id;
                viewer.over=null;
                viewer.getMap(data);
                viewer.setActual(ant);
                viewer.candraw=true;
            }            
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_topicMenuActionPerformed

    private void jTree2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTree2MouseClicked
    {//GEN-HEADEREND:event_jTree2MouseClicked
        // Add your handling code here:
        if(!jTree2.isEnabled())return;
        
        lastx=evt.getX();
        lasty=evt.getY();

        if((evt.getModifiers()&evt.BUTTON3_MASK)>0)
        {
            if(jTree2.getSelectionPath()!=null)
            {
                int lev=jTree2.getSelectionPath().getPathCount();
                if(lev==1)
                {
                    nameMenu.getItem(0).setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.name.add"));
                    nameMenu.getItem(0).enable();
                    boolean hasSort=false;
                    for(int x=0;x<nameroot.getChildCount();x++)
                    {
                        DefaultMutableTreeNode aux=(DefaultMutableTreeNode)nameroot.getChildAt(x);
                        Object obj=aux.getUserObject();
                        if(obj instanceof ScopedName) 
                        {
                            ScopedName sn=(ScopedName)obj;
                            String sc=sn.getScope();
                            if(sc!=null && sc.equals("CNF_WBSortName"))
                            {
                                hasSort=true;
                                break;
                            }
                        }
                    }
                    if(hasSort)nameMenu.getItem(1).disable();
                    else nameMenu.getItem(1).enable();
                    nameMenu.getItem(3).disable();
                    nameMenu.getItem(4).disable();
                    nameMenu.getItem(6).disable();
                }
                else if(lev==2)
                {
                    nameMenu.getItem(0).setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.name.addVariant"));

                    DefaultMutableTreeNode aux=(DefaultMutableTreeNode)jTree2.getSelectionPath().getLastPathComponent();
                    if(aux.getUserObject() instanceof ScopedName && ((ScopedName)aux.getUserObject()).getScope()!=null && ((ScopedName)aux.getUserObject()).getScope().equals("CNF_WBSortName"))
                        nameMenu.getItem(0).disable();
                    else
                        nameMenu.getItem(0).enable();
                    nameMenu.getItem(1).disable();
                    nameMenu.getItem(3).enable();
                    nameMenu.getItem(4).enable();
                    nameMenu.getItem(6).enable();
                }
                else if(lev==3)
                {
                    nameMenu.getItem(0).disable();
                    nameMenu.getItem(1).disable();
                    nameMenu.getItem(3).enable();
                    nameMenu.getItem(4).enable();
                    nameMenu.getItem(6).disable();
                }
                //System.out.println(lev);
            }else
            {
                nameMenu.getItem(0).enable();
                nameMenu.getItem(1).enable();
                nameMenu.getItem(3).disable();
                nameMenu.getItem(4).disable();
                nameMenu.getItem(6).disable();
            }
            //nameMenu.show(getContentPane(),(int)evt.getComponent().getLocationOnScreen().getX()-(int)this.getLocationOnScreen().getX()+evt.getX(),(int)evt.getComponent().getLocationOnScreen().getY()-(int)this.getLocationOnScreen().getY()+evt.getY());
            nameMenu.show(evt.getComponent(),evt.getX(),evt.getY());
        }        
    }//GEN-LAST:event_jTree2MouseClicked

    private void jButton3MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton3MouseExited
    {//GEN-HEADEREND:event_jButton3MouseExited
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(false);
    }//GEN-LAST:event_jButton3MouseExited

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton3MouseEntered
    {//GEN-HEADEREND:event_jButton3MouseEntered
        // Add your handling code here:
        ((JButton)evt.getComponent()).setBorderPainted(true);
    }//GEN-LAST:event_jButton3MouseEntered
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.PopupMenu RelationMenu;
    private java.awt.PopupMenu TypeMenu;
    private java.awt.CheckboxMenuItem checkboxMenuItem1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree2;
    private javax.swing.JComboBox maps;
    private java.awt.Menu menu1;
    private java.awt.MenuItem menuItem1;
    private java.awt.MenuItem menuItem10;
    private java.awt.MenuItem menuItem11;
    private java.awt.MenuItem menuItem12;
    private java.awt.MenuItem menuItem13;
    private java.awt.MenuItem menuItem14;
    private java.awt.MenuItem menuItem2;
    private java.awt.MenuItem menuItem3;
    private java.awt.MenuItem menuItem4;
    private java.awt.MenuItem menuItem5;
    private java.awt.MenuItem menuItem6;
    private java.awt.MenuItem menuItem7;
    private java.awt.MenuItem menuItem8;
    private java.awt.MenuItem menuItem9;
    private java.awt.PopupMenu nameMenu;
    private javax.swing.JTable tableRelations;
    private java.awt.MenuItem topicItem0;
    private java.awt.MenuItem topicItem1;
    private java.awt.MenuItem topicItem3;
    private java.awt.MenuItem topicItem4;
    private java.awt.MenuItem topicItem5;
    private java.awt.MenuItem topicItem6;
    private java.awt.MenuItem topicItem7;
    private java.awt.MenuItem topicItem8;
    private java.awt.MenuItem topicItem9;
    private java.awt.PopupMenu topicMenu;
    private javax.swing.JList types;
    // End of variables declaration//GEN-END:variables

    private void usePageParams()
    {
        final String defaultBackground = "979fc3";
        final String defaultForeground = "000000";
        final String defaultBackgroundSelection = "666699";
        final String defaultForegroundSelection = "ffffff";
        String backgroundValue;
        String foregroundValue;
        String backgroundSelectionValue;
        String foregroundSelectionValue;

        //if(getParameter("Home")!=null)viewer.home = getParameter("Home");
        /**
         * Read the <PARAM NAME="label" VALUE="some string">,
         * <PARAM NAME="background" VALUE="rrggbb">,
         * and <PARAM NAME="foreground" VALUE="rrggbb"> tags from
         * the applet's HTML host.
         */
        backgroundValue = getParameter(backgroundParam);
        foregroundValue = getParameter(foregroundParam);
        backgroundSelectionValue = getParameter(backgroundSelectionParam);
        foregroundSelectionValue = getParameter(foregroundSelectionParam);

        if ((backgroundValue == null) || (foregroundValue == null))
        {
            /**
             * There was something wrong with the HTML host tags.
             * Generate default values.
             */
            backgroundValue = defaultBackground;
            foregroundValue = defaultForeground;
            backgroundSelectionValue = defaultBackgroundSelection;
            foregroundSelectionValue = defaultForegroundSelection;
        }

        // /**
        // * Set the applet's string label, background color, and
        // * foreground colors.
        // */
        this.setBackground(stringToColor(backgroundValue));
        this.setForeground(stringToColor(foregroundValue));
        
        
        //Configuracion jTree2

        jTree2.setBackground(stringToColor(backgroundValue));
        jTree2.setForeground(stringToColor(foregroundValue));

        namerenderer=new WBNamesRenderer();
        namerenderer.setBackgroundNonSelectionColor(stringToColor(backgroundValue));
        namerenderer.setBackgroundSelectionColor(stringToColor(backgroundSelectionValue));
        namerenderer.setTextSelectionColor(stringToColor(foregroundSelectionValue));
        namerenderer.setTextNonSelectionColor(stringToColor(foregroundValue));

        try
        {
            MediaTracker mt=new MediaTracker(this);
            Image icon1=getImage(getClass().getResource("images/ico_titulo.gif"));
            Image icon2=getImage(getClass().getResource("images/ico_idioma.gif"));
            namerenderer.setRootIcon(new ImageIcon(icon1));
            namerenderer.setLangIcon(new ImageIcon(icon2));
            mt.addImage(icon1,0);
            mt.addImage(icon2,0);
            mt.waitForAll();
        }catch(Exception e){System.out.println("Error to load icons...");}

        //Configuracion jList1
        types.setBackground(stringToColor(backgroundValue));
        types.setForeground(stringToColor(foregroundValue));
        types.setSelectionBackground(stringToColor(backgroundSelectionValue));
        types.setSelectionForeground(stringToColor(foregroundSelectionValue));

        //Configuracion TableRelations
        tableRelations.setBackground(stringToColor(backgroundValue));
        tableRelations.setForeground(stringToColor(foregroundValue));
        tableRelations.setSelectionBackground(stringToColor(backgroundSelectionValue));
        tableRelations.setSelectionForeground(stringToColor(foregroundSelectionValue));
        tableRelations.setGridColor(stringToColor(foregroundSelectionValue));
        
        //Configuracion jScrollPane2
        jScrollPane2.setBackground(stringToColor(backgroundValue));
        
        this.jButton3.setEnabled(false);
        this.jButton4.setEnabled(false);
        this.jButton5.setEnabled(false);
        this.jButton6.setEnabled(false);
        this.jButton1.setEnabled(false);        
        this.jButton2.setEnabled(false);
        this.jTree2.setEnabled(false);
        this.types.setEnabled(false);
        this.tableRelations.setEnabled(false);
        
        //cursores
        curdrag = getToolkit().createCustomCursor(getImage(getClass().getResource("images/drag.gif")), new Point( 16, 8 ), "drop");
        curdrag2 = getToolkit().createCustomCursor(getImage(getClass().getResource("images/drag3.gif")), new Point( 15, 15 ), "drop2");
        
    }

    private Color stringToColor(String paramValue)
    {
        int red;
        int green;
        int blue;

        red = (Integer.decode("0x" + paramValue.substring(0,2))).intValue();
        green = (Integer.decode("0x" + paramValue.substring(2,4))).intValue();
        blue = (Integer.decode("0x" + paramValue.substring(4,6))).intValue();

        return new Color(red,green,blue);
    }

    
    public void start()
    {
        viewer.start();
    }
    
    public void stop()
    {
        viewer.stop();
    }
    
    public void mapChange()
    {
        
        if(viewer.getActual()==null)return;
        
        //System.out.println("MapChange to:"+viewer.getActual().getName());
        
        if(!editing || (editing && edit!=viewer.edit))
        {
            if(!editing)
            {
                if(viewer.getActual().getBorrado()==1)
                {
                    jButton5.setEnabled(false);
                    jButton1.setEnabled(false);
                    jButton2.setEnabled(false);
                    jTree2.setEnabled(false);
                    types.setEnabled(false);
                    tableRelations.setEnabled(false);
                }else
                {
                    jButton5.setEnabled(true);
                    jButton1.setEnabled(true);
                    jButton2.setEnabled(true);
                    jTree2.setEnabled(true);
                    types.setEnabled(true);
                    tableRelations.setEnabled(true);
                }
            }

            edit=viewer.getActual();
            
            //****************update names...*******************************
            nameroot.removeAllChildren();
            DefaultMutableTreeNode namenode=null;
            ScopedName scn=null;

            Enumeration en=viewer.getActual().names.elements();
            while(en.hasMoreElements())
            {
                String n=(String)en.nextElement();
                if(n.startsWith("n:"))
                {
                    scn=new ScopedName(n.substring(2),null,null);
                    namenode=new DefaultMutableTreeNode(scn);
                    nameroot.add(namenode);
                }
                else if(n.startsWith("s:"))
                {
                    scn.setScope(n.substring(2));
                    scn.setLanguage((String)langs.get(scn.getScope()));
                }
                else if(n.startsWith("v:"))
                {
                    namenode.add(new DefaultMutableTreeNode(n.substring(2)));
                }
                //System.out.println(n);
            }
            namemodel.reload();

            //****************update types...*******************************
            ((DefaultListModel)types.getModel()).clear();
            
            Iterator ass=viewer.getActual().getTypes();
            while(ass.hasNext())
            {
                AppObject topic=(AppObject)ass.next();
                //System.out.println(topic);
                ((DefaultListModel)types.getModel()).addElement(topic);
            }


            //****************update associations...*******************************
            relations.setNumRows(0);
            Enumeration asso=viewer.getActual().assoc.elements();
            while(asso.hasMoreElements())
            {
                AppAssoc asoc=(AppAssoc)asso.nextElement();
                if(asoc.type==1||asoc.type==3)relations.addRow(new Object[]{viewer.obj.get(asoc.idtype),viewer.obj.get(asoc.relrole),asoc.topic,viewer.obj.get(asoc.role)});
            }
        }
        
        
    }
    
    public void namesChanged(javax.swing.event.TreeModelEvent evt,String action){
        if(action.equals("treeNodesRemoved")||action.equals("treeNodesChanged"))
        {
            if(action.equals("treeNodesChanged"))
            {
                WBTreeCellEditor ce=(WBTreeCellEditor)jTree2.getCellEditor();
                if(ce.getScope()!=null)
                {
                    ScopedName scn=ce.getScope();
                    DefaultMutableTreeNode aux=(DefaultMutableTreeNode)jTree2.getSelectionPath().getLastPathComponent();
                    scn.setName(""+aux.getUserObject());
                    aux.setUserObject(scn);
                }
                //System.out.println(action+" "+evt);
            }
            //System.out.println(action+" "+evt);
            setEditing(true,1);
/*            
            syncNames();
            WBTopicMap tm=(WBTopicMap)((DefaultMutableTreeNode)selected.getPath()[1]).getUserObject();
            WBTopic tp=(WBTopic)selected.getUserObject();
            this.updateTopic(tm.getId(),tp);
*/
        }
    }
    
    /** Getter for property topicMenu.
     * @return Value of property topicMenu.
     */
    public java.awt.PopupMenu getTopicMenu()
    {
        return topicMenu;
    }    
    
    /** Setter for property topicMenu.
     * @param topicMenu New value of property topicMenu.
     */
    public void setTopicMenu(java.awt.PopupMenu topicMenu)
    {
        this.topicMenu = topicMenu;
    }

    private String getCData(String data)
    {
        return getCData(data, null);
    }
    
    private String getCData(String data, String tag)
    {
        String ret=null;

        if(tag!=null)
        {
            int s=data.indexOf("<"+tag+">");
            if(s>0)
            {
                data=data.substring(s+tag.length()+2,data.indexOf("</"+tag+">",s));
                ret=data;
            }
        }
        int s=data.indexOf("<![CDATA[");
        if(s>0)
        {
            StringBuffer cdata=new StringBuffer();
            while(s>0)
            {
                int f=data.indexOf("]]>",s);
                //System.out.println("l:"+data.length()+" s:"+s+" f:"+f);
                //System.out.println(data.substring(f-100,f+100));
                cdata.append(data.substring(s+9,f));
                //System.out.println(viewer.actual);
                s=data.indexOf("<![CDATA[", f);
                if(s>0)
                {
                    String aux=data.substring(f+3,s);
                    //String rpl=WBXMLParser.replaceStrTags(aux);
                    //System.out.println(aux+" "+rpl);
                    cdata.append(aux);
                }
            }
            ret=cdata.toString();
        }
        return ret;
    }
    

    private String getData(String xml)
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        //System.out.println("getData:"+xml);
         String ret="";
        try
        {
            URLConnection urlconn=url.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            urlconn.setRequestProperty("Accept-Encoding", "gzip");
            urlconn.setRequestProperty("Content-Type","application/xml");            
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
//                //System.out.println(inputLine);
//            }
//            in.close();
            String accept=urlconn.getHeaderField("Content-Encoding");
            //System.out.println("Content-Encoding:"+accept);
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
        //System.out.println("ret:"+ret.toString());
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        return ret;
    }
/*    
    public void showPopUp()
    {
        WBTopic auxtp=(WBTopic)selected.getUserObject();
        if(auxtp.getAccessLevel()==0)
        {
            topicMenu.getItem(0).disable();
            topicMenu.getItem(2).disable();
            topicMenu.getItem(3).disable();
            topicMenu.getItem(5).disable();
        }
        else 
        {
            int act=auxtp.getActive();
            if(act==1)checkboxMenuItem1.setState(true);
            else checkboxMenuItem1.setState(false);
            topicMenu.getItem(0).enable();
            if((auxtp.getAccessLevel()&2)>0)
                topicMenu.getItem(2).enable();
            else
                topicMenu.getItem(2).disable();
            if((auxtp.getAccessLevel()&8)>0)
                topicMenu.getItem(3).enable();
            else
                topicMenu.getItem(3).disable();
            if((auxtp.getAccessLevel()&4)>0)
                topicMenu.getItem(5).enable();
            else 
                topicMenu.getItem(5).disable();
        }
        topicMenu.show(getContentPane(),this.lastx+jTree1.getX(),this.lasty+jTree1.getY());
    }
*/    
    private void getTopicMaps()
    {
        String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getTopicMaps</cmd></req>");
        WBTreeNode wbnode=new WBXMLParser().parse(data);
        WBTreeNode req=wbnode.getFirstNode();
        
        //DefaultMutableTreeNode root= new DefaultMutableTreeNode("Sitios");
        for(int x=0;x<req.getNodesSize();x++)
        {
            WBTopicMap tm=new WBTopicMap(req.getNode(x));
            
            ((DefaultComboBoxModel)maps.getModel()).addElement(tm);
/*            
            DefaultMutableTreeNode topicmapnode=new  DefaultMutableTreeNode(tm);
            root.add(topicmapnode);
            DefaultMutableTreeNode topic=getTopic(tm.getId(),tm.getHome(),null);
            if(topic!=null)
            {
                topicmapnode.add(topic);
            }
 */
        }
        maps.setSelectedIndex(-1);
        ((DefaultComboBoxModel)maps.getModel()).setSelectedItem(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("map.select"));
        //return root;
    }
/*
    private DefaultMutableTreeNode getTopic(String topicmap,String topic, WBTopic parent)
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        //System.out.println("getTopic("+topicmap+","+topic+")");
        String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><topicmap>"+topicmap+"</topicmap><topicid>"+topic+"</topicid><cmd>2</cmd></req>");
        //System.out.println("data:"+data);
        WBTreeNode wbnode=new WBXMLParser().parse(data);
        if(wbnode==null)return null;
        WBTreeNode req=wbnode.getFirstNode();
        WBTreeNode tp=req.getFirstNode();
        //System.out.println("tipo:"+tp.getName());
        
        WBTopic wbtopic=new WBTopic(tp);
        wbtopic.setParent(parent);
        checkAccess(wbtopic,topicmap);
        //System.out.println("topic:"+wbtopic);
        //System.out.println("topicid:"+wbtopic.getId());
        DefaultMutableTreeNode root= new DefaultMutableTreeNode(wbtopic);
        
        if(wbtopic.haveChild())
        {
            Enumeration en=wbtopic.getChild();
            while(en.hasMoreElements())
            {
                WBTreeNode childtp=(WBTreeNode)en.nextElement();
                WBTopic wbtopicchild=new WBTopic(childtp);
                wbtopicchild.setParent(wbtopic);
                checkAccess(wbtopicchild,topicmap);
                DefaultMutableTreeNode nodechild=new DefaultMutableTreeNode(wbtopicchild);
                root.add(nodechild);
                //System.out.println("child-->:"+wbtopicchild);
                if(wbtopicchild.haveChild())nodechild.add(new  DefaultMutableTreeNode("nochecked"));
                
            }
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        return root;
    }
*/
    private String updateTopic(String topicmap, WBTopic topic)
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        StringBuffer xml=new StringBuffer();
        String xmlfin="";
        try{
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<req>\n");
            xml.append(" <prm>\n");
            xml.append("  <topicid>"+topic.getId()+"</topicid>\n");
            xml.append("  <topicmap>"+topicmap+"</topicmap>\n");
            Iterator it=topic.getDataNode().getNodesbyName("name");
            while(it.hasNext())
            {
                WBTreeNode n=(WBTreeNode)it.next();
                String scope="";
                if(n.getAttribute("scope")!=null)scope=" scope=\""+n.getAttribute("scope")+"\"";
                xml.append("  <name"+scope+" value=\""+WBXMLParser.replaceStrChars(n.getAttribute("value"))+"\">\n");
                Iterator it2=n.getNodesbyName("variant");
                while(it2.hasNext())
                {
                    WBTreeNode v=(WBTreeNode)it2.next();
                    xml.append("   <variant>"+WBXMLParser.replaceStrChars(v.getFirstNode().getText())+"</variant>\n");
                }
                xml.append("  </name>\n");
            }
            xml.append(" </prm>\n");
            xml.append(" <cmd>updateTopic</cmd>\n");
            xml.append("</req>\n");
            xmlfin=WBXMLParser.encode(xml.toString(),"UTF8");
        }catch(Exception e){System.out.println("Error to generate Topic's XML...");}
        //System.out.println(xmlfin);
        String data=getData(xmlfin);
        //System.out.println(data);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if(data.indexOf("<err>")>0)
        {
            return "Error to update Topic...";
        }
        else
            return null;
    }

    private String createTopic(String topicmap,String parent_id, WBTopic topic)
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        StringBuffer xml=new StringBuffer();
        String xmlfin="";
        try{
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<req>\n");
            xml.append(" <prm>\n");
            xml.append("  <topicid>"+parent_id+"</topicid>\n");
            xml.append("  <topicmap>"+topicmap+"</topicmap>\n");
            xml.append("  <id>"+topic.getId()+"</id>\n");
            Iterator it=topic.getDataNode().getNodesbyName("name");
            while(it.hasNext())
            {
                WBTreeNode n=(WBTreeNode)it.next();
                String scope="";
                if(n.getAttribute("scope")!=null)scope=" scope=\""+n.getAttribute("scope")+"\"";
                xml.append("  <name"+scope+" value=\""+WBXMLParser.replaceStrChars(n.getAttribute("value"))+"\">\n");
                Iterator it2=n.getNodesbyName("variant");
                while(it2.hasNext())
                {
                    WBTreeNode v=(WBTreeNode)it2.next();
                    xml.append("   <variant>"+WBXMLParser.replaceStrChars(v.getFirstNode().getText())+"</variant>\n");
                }
                xml.append("  </name>\n");
            }
            xml.append(" </prm>\n");
            xml.append(" <cmd>createTopic</cmd>\n");
            xml.append("</req>\n");
            xmlfin=WBXMLParser.encode(xml.toString(),"UTF8");
        }catch(Exception e){System.out.println("Error to generate Topic's XML...");}
        //System.out.println(xmlfin);
        String data=getData(xmlfin);
        //System.out.println(data);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if(data.indexOf("<err>")>0)
        {
            if(data.indexOf("<id>8</id>")>0)
                return java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.error.exist");
            else
                return java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.error.create");
        }
        else
            return null;
    }

    private String changeStatus(String topicmap, AppObject topic)
    {
        //System.out.println("cambio de estatus... al topico:"+topic.getId());
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        StringBuffer xml=new StringBuffer();
        String xmlfin="";
        try{
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<req>\n");
            xml.append(" <prm>\n");
            xml.append("  <topicid>"+topic.getId()+"</topicid>\n");
            xml.append("  <topicmap>"+topicmap+"</topicmap>\n");
            xml.append("  <active>"+topic.getActive()+"</active>\n");
            xml.append(" </prm>\n");
            xml.append(" <cmd>setStatusTopic</cmd>\n");
            xml.append("</req>\n");
            xmlfin=WBXMLParser.encode(xml.toString(),"UTF8");
        }catch(Exception e){System.out.println("Error to generate Topic's XML...");}
        //System.out.println(xmlfin);
        String data=getData(xmlfin);
        //System.out.println(data);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if(data.indexOf("<err>")>0)
        {
            return java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.error.status");
        }
        else
            return null;
    }
    
    private String borradoTopic(String topicmap, AppObject topic)
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        StringBuffer xml=new StringBuffer();
        String xmlfin="";
        try{
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<req>\n");
            xml.append(" <prm>\n");
            xml.append("  <topicmap>"+topicmap+"</topicmap>\n");
            xml.append("  <topicid>"+topic.getId()+"</topicid>\n");
            if(topic.borrado==1)xml.append("  <back/>\n");
            xml.append(" </prm>\n");
            xml.append(" <cmd>deleteTopic</cmd>\n");
            xml.append("</req>\n");
            xmlfin=WBXMLParser.encode(xml.toString(),"UTF8");
        }catch(Exception e){System.out.println("Error to generate Topic's XML...");}
        //System.out.println(xmlfin);
        String data=getData(xmlfin);
        //System.out.println(data);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if(data.indexOf("<err>")>0)
        {
            if(topic.borrado==1)
                return java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.error.restore");
            else 
                return java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.error.markDeleted");
        }
        else
            return null;
    }    
    
    private String deleteTopic(String topicmap, AppObject topic)
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        StringBuffer xml=new StringBuffer();
        String xmlfin="";
        try{
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<req>\n");
            xml.append(" <topicmap>"+topicmap+"</topicmap>\n");
            xml.append(" <cmd>setTopicMap4Adm</cmd>\n");
            xml.append(" <data>Remove Topic\ni:"+topic.getId()+"</data>\n");
            xml.append("</req>\n");
            xmlfin=WBXMLParser.encode(xml.toString(),"UTF8");
        }catch(Exception e){System.out.println("Error to generate Topic's XML...");}
        //System.out.println(xmlfin);
        String data=getData(xmlfin);
        //System.out.println(data);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if(data.indexOf("<err>")>0)
        {
            return java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.error.delete");
        }
        else
            return null;
    }    
    
    private String deleteTopicR(String topicmap, AppObject topic)
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        StringBuffer xml=new StringBuffer();
        String xmlfin="";
        try{
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<req>\n");
            xml.append(" <topicmap>"+topicmap+"</topicmap>\n");
            xml.append(" <cmd>setTopicMap4Adm</cmd>\n");
            xml.append(" <data>RRemove Topic\ni:"+topic.getId()+"</data>\n");
            xml.append("</req>\n");
            xmlfin=WBXMLParser.encode(xml.toString(),"UTF8");
        }catch(Exception e){System.out.println("Error to generate Topic's XML...");}
        //System.out.println(xmlfin);
        String data=getData(xmlfin);
        //System.out.println(data);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if(data.indexOf("<err>")>0)
        {
            return java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.error.delete");
        }
        else
            return null;
    }    
    
    
/*
    private void getUserAttr()
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>16</cmd></req>");
        //System.out.println(data);
        if(data.indexOf("<err>")>0)return;
        WBTreeNode wbnode=new WBXMLParser().parse(data);
        wbnode=wbnode.getFirstNode().getFirstNode();
        String admtypen=((WBTreeNode)wbnode.getNodesbyName("admtype").next()).getFirstNode().getText();
        if(admtypen.equals("0"))
        {
            this.admtype=0;
        }else
        {
            this.admtype=1;
            String xml=((WBTreeNode)wbnode.getNodesbyName("xml").next()).getFirstNode().getText();
            WBTreeNode xmlnode=new WBXMLParser().parse(xml);
            admuser=xmlnode.getFirstNode();

            //System.out.println("tag:"+admMaps.getName());
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
*/    
    private void getLanguages(String tm)
    {
        langs=new Hashtable();
        menu1.removeAll();
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><topicmap>"+tm+"</topicmap><cmd>getLanguagesList</cmd></req>");
        WBTreeNode wbnode=new WBXMLParser().parse(data);
        wbnode=wbnode.getFirstNode().getFirstNode();
        Iterator it=wbnode.getNodesbyName("Lenguaje");
        while(it.hasNext())
        {
            WBTreeNode lang=(WBTreeNode)it.next();
            String id=((WBTreeNode)lang.getNodesbyName("id").next()).getFirstNode().getText();
            String name=((WBTreeNode)lang.getNodesbyName("name").next()).getFirstNode().getText();
            langs.put(this.IDTPLANG+id,name);
            menu1.add(new MenuItem(name));
            //System.out.println(name);
        }
        
        Enumeration en=langs.keys();
        while(en.hasMoreElements())
        {
            int iconw=22;
            int iconh=15;
            String l=(String)en.nextElement();
            
            try
            {
                Image img=this.createImage(iconw,iconh);
                Graphics grp=img.getGraphics();
                grp.setColor(stringToColor(getParameter(backgroundParam)));
                grp.fillRect(0,0,iconw,iconh);
                
                MediaTracker mt=new MediaTracker(this);
                Image img2=getImage(getClass().getResource("images/ico_idioma.gif"));
                mt.addImage(img2,0);
                mt.waitForAll();
                
                grp.drawImage(img2,0,0,this);
                Rectangle2D rec=grp.getFontMetrics().getStringBounds(l.substring(IDTPLANG.length()),grp);
                grp.setColor(Color.blue);
                grp.drawString(l.substring(this.IDTPLANG.length()),(int)((iconw-rec.getWidth())/2),10);
                ImageIcon icon=new ImageIcon(img);
                namerenderer.getIdmIcons().put(l,icon);
            }catch(Exception e){System.out.println("Error to load icons...");}
        }
        
        //cargar icono de sortname
        try
        {
            MediaTracker mt=new MediaTracker(this);
            Image img=getImage(getClass().getResource("images/ico_sortname.gif"));
            mt.addImage(img,0);
            mt.waitForAll();
            
            ImageIcon icon=new ImageIcon(img);
            namerenderer.getIdmIcons().put("CNF_WBSortName",icon);
            
        }catch(Exception e){System.out.println("Error to load icons...");}
        
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    protected void setEditing(boolean val, int type)
    {
        setEditing(val,type,null);
    }
    
    private void setEditing(boolean val, int type, AppObject toEdit)
    {
        editing=val;
        this.jButton3.setEnabled(val);
        this.jButton4.setEnabled(val);
        this.jButton5.setEnabled(!val);
        this.jButton6.setEnabled(!val);
        this.jButton1.setEnabled(!val);
        this.jButton2.setEnabled(!val);
        if(val)
        {
            if(toEdit==null)
            {
                if(viewer.edit==null)viewer.edit=viewer.actual;
            }else
            {
                if(viewer.edit==null)viewer.edit=toEdit;
            }
            
            if(type==4)
            {
                nameschange=true;
                assochange=true;
            }
            else if(type==1)nameschange=true;
            else if(type==2)assochange=true;
            else if(type==3)assochange=true;
        }
        else 
        {
            viewer.edit=null;
            nameschange=false;
            assochange=false;
        }
    }
    
    public boolean isEditing()
    {
        return editing;
    }

    /** Remove the currently selected node. */
    public void removeCurrentNode(JTree tree, DefaultTreeModel treeModel) {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }
    }        
    
    public void frmCreateTopic(WBTopicMap tm, AppObject auxedit)
    {
        WBCreateTopic frm=new WBCreateTopic(new javax.swing.JFrame(), true,locale,(String)langs.get(tm.getLang()));
        if(tm.getId().length()>4)
            frm.setPrefix(tm.getId().substring(0,4));
        else
            frm.setPrefix(tm.getId());
        //frm.setLang((String)langs.get(tm.getLang()));
        frm.move((int)this.getLocationOnScreen().getX()+(int)this.getWidth()/2,(int)this.getLocationOnScreen().getY()+(int)jPanel1.getHeight()/2);
        boolean fin=false;
        do
        {
            frm.show();
            if(frm.isAccepted())
            {
                StringBuffer xml=new StringBuffer();
                try{
                    xml.append("  <Topic>\n");
                    xml.append("    <id>"+frm.getTopicId()+"</id>\n");
                    xml.append("    <active>0</active>\n");
                    xml.append("    <types>\n");
                    xml.append("      <type>"+auxedit.getId()+"</type>\n");
                    xml.append("    </types>\n");
                    xml.append("    <name value=\""+frm.getTopicName()+"\" scope=\""+tm.getLang()+"\"/>\n");
                    xml.append("  </Topic>\n");
                }catch(Exception e){System.out.println("Error to generate Topic's XML...");}

                //System.out.println(xml);

                WBTreeNode tpnode=new WBXMLParser().parse(xml.toString());
                //System.out.println(tpnode.getName()+" "+tpnode.getNodesSize());
                WBTopic topic=new WBTopic(tm,tpnode.getFirstNode());
                String err=createTopic(tm.getId(),auxedit.getId(),topic);
                if(err==null)
                {
                    fin=true;

                    AppObject edit=new AppObject(topic.getId());
                    edit.name=frm.getTopicName();
                    edit.names=new Vector();
                    edit.names.addElement("n:"+frm.getTopicName());
                    edit.names.addElement("s:"+tm.getLang());
                    edit.active=0;

                    viewer.initTopic(edit);
                    viewer.obj.put(edit.id,edit);

                    AppObject to=auxedit;
                    edit.assoc.addElement(new AppAssoc(to,0,"","","",""));
                    to.assoc.addElement(new AppAssoc(edit,2,"","","",""));                        
                    viewer.setActual(edit.getId());
                    viewer.sortTopics();
                }else
                {
                    JOptionPane.showMessageDialog(this,err,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.title.create"),JOptionPane.ERROR_MESSAGE);
                }
            }
            else fin=true;
        }while(!fin);
        frm.dispose();
    }
    
    public void frmBorradoTopic(WBTopicMap tm, AppObject auxedit)
    {
        int r=JOptionPane.OK_OPTION;
        if(auxedit.borrado==0)
            r=JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.markDeleted"),java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.title.delete"),JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(r==JOptionPane.OK_OPTION)
        {
            String err=borradoTopic(tm.getId(),auxedit);
            if(err!=null)
            {
                JOptionPane.showMessageDialog(this,err,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.title.markDeleted"),JOptionPane.ERROR_MESSAGE);
            }else
            {
                if(auxedit.borrado==1)
                    auxedit.borrado=0;
                else 
                {
                    auxedit.borrado=1;  
                    auxedit.active=0;  
                }
                //removeCurrentNode(jTree1,this.treemodel);
            }
        }
    }

    public void frmDeleteTopic(WBTopicMap tm, AppObject auxedit)
    {
        int r=JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.delete"),java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.title.delete"),JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(r==JOptionPane.OK_OPTION)
        {
            String err=deleteTopic(tm.getId(),auxedit);
            if(err!=null)
            {
                JOptionPane.showMessageDialog(this,err,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.title.delete"),JOptionPane.ERROR_MESSAGE);
            }else
            {
                auxedit.visible=false;
                auxedit.actual=false;
                auxedit.over=false;
                Enumeration en=viewer.obj.elements();
                while(en.hasMoreElements())
                {
                    AppObject auxobj=(AppObject)en.nextElement();
                    Enumeration ass=auxobj.assoc.elements();
                    while(ass.hasMoreElements())
                    {
                        AppAssoc auxass=(AppAssoc)ass.nextElement();
                        if(auxass.topic==auxedit)
                        {
                            auxobj.assoc.remove(auxass);
                        }
                        if(viewer.obj.get(auxass.idtype)==auxedit)
                        {
                            auxobj.assoc.remove(auxass);
                        }
                        if(viewer.obj.get(auxass.role)==auxedit||viewer.obj.get(auxass.relrole)==auxedit)
                        {
                            auxobj.assoc.remove(auxass);
                        }
                    }
                }
                viewer.obj.remove(auxedit.id);
                viewer.sortTopics();
                if(auxedit==viewer.actual)
                {
                    viewer.setActual(viewer.home);
                }
            }
        }
    }
    
    public void frmDeleteTopicR(WBTopicMap tm, AppObject auxedit)
    {
        int r=JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.deleteChild"),java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.title.delete"),JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(r==JOptionPane.OK_OPTION)
        {
            String err=deleteTopicR(tm.getId(),auxedit);
            if(err!=null)
            {
                JOptionPane.showMessageDialog(this,err,java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("topic.usrmsg.title.delete"),JOptionPane.ERROR_MESSAGE);
            }else
            {
                if(auxedit==viewer.actual)
                {
                    viewer.setActual(viewer.home);
                }

                String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><topicmap>"+tm.getId()+"</topicmap><cmd>getTopicMap4Adm</cmd></req>");
                data=getCData(data,"map");
                if(data.length()>0)
                {
                    viewer.candraw=false;
                    String ant=viewer.actual.id;
                    viewer.over=null;
                    viewer.getMap(data);
                    viewer.setActual(ant);
                    viewer.candraw=true;
                }            
            }
        }
    }
    
    /**
     * Getter for property auxdrag.
     * @return Value of property auxdrag.
     */
    public applets.mapsadm.AppObject getAuxDrag()
    {
        return auxdrag;
    }
    
    /**
     * Setter for property auxdrag.
     * @param auxdrag New value of property auxdrag.
     */
    public void setAuxDrag(applets.mapsadm.AppObject auxdrag)
    {
        this.auxdrag = auxdrag;
    }
    
    public void setActual(String id)
    {
        viewer.setActual(id);
    }
    
    /** Getter for property actual.
     * @return Value of property actual.
     */
    public AppObject getActual()
    {
        return viewer.getActual();
    }    
    
}
