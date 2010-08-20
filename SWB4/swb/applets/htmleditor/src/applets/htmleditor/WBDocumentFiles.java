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
 * WBDocumentFiles.java
 *
 * Created on 14 de noviembre de 2004, 08:28 PM
 */

package applets.htmleditor;

import java.net.URL;
import java.net.URLConnection;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.StringWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.applet.*;
import javax.swing.tree.*;

import applets.commons.*;
/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBDocumentFiles extends javax.swing.JDialog
{
    
    private URL gateway=null;
    private URL upload=null;
    private String topicMap=null;
    private String id=null;
    private String version=null;
    private String type=null;
    
    
    private String jsess=null;                    //session del usuario
    private Frame parent=null; 
    private ImagePreview preview=null;
    
    private int lastx;
    private int lasty;
    
    private WBDocFilesTreeRender renderer=null;
    private DefaultMutableTreeNode root=null;
    private DefaultTreeModel model=null;
    private WBTreeNode events=null;
    
    private String docPath=null;
    
    private boolean vdebug=true;
    
    //private File curDir=null;    
    
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
    
    private Locale locale=getLocale();    
    
    private java.awt.event.ActionListener actList=new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            menuActionPerformed(evt);
        }
    };
    

    
    /** Creates new form WBDocumentFiles */
    public WBDocumentFiles(java.awt.Frame parent, boolean modal, Locale locale)
    {
        super(parent, modal);
        initComponents();
        this.parent=parent;
        setTitle("Document Files");
        this.locale=locale;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        java.awt.GridBagConstraints gridBagConstraints;

        menu = new javax.swing.JPopupMenu();
        jToolBar1 = new javax.swing.JToolBar();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        _owidth = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        _oheight = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        _size = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        _scalex = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        _scaley = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        _scaleLock = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        _width = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        _height = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        _border = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        _date = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
        });

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(650, 300));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("files")));
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

        jScrollPane1.setViewportView(jTree1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton3.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("add"));
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel3.add(jButton3);

        jButton4.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("delete"));
        jButton4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel3.add(jButton4);

        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(jPanel2);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel4.setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("properties")));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("owidth"));
        jLabel18.setPreferredSize(new java.awt.Dimension(70, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel18, gridBagConstraints);

        _owidth.setFont(new java.awt.Font("Dialog", 0, 12));
        _owidth.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 6, 2);
        jPanel4.add(_owidth, gridBagConstraints);

        jLabel4.setText("pt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel4, gridBagConstraints);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("oheight"));
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel19, gridBagConstraints);

        _oheight.setFont(new java.awt.Font("Dialog", 0, 12));
        _oheight.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 6, 2);
        jPanel4.add(_oheight, gridBagConstraints);

        jLabel6.setText("pt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel6, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("size"));
        jLabel3.setPreferredSize(new java.awt.Dimension(70, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel3, gridBagConstraints);

        _size.setFont(new java.awt.Font("Dialog", 0, 12));
        _size.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 6, 2);
        jPanel4.add(_size, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("scaleX"));
        jLabel7.setPreferredSize(new java.awt.Dimension(70, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel7, gridBagConstraints);

        _scalex.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        _scalex.setText("100");
        _scalex.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                _scalexFocusLost(evt);
            }
        });
        _scalex.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                _scalexPropertyChange(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(_scalex, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("scaleY"));
        jLabel8.setPreferredSize(new java.awt.Dimension(70, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel8, gridBagConstraints);

        _scaley.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        _scaley.setText("100");
        _scaley.setEnabled(false);
        _scaley.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                _scaleyFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -3;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(_scaley, gridBagConstraints);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("lockScale"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jLabel17, gridBagConstraints);

        _scaleLock.setSelected(true);
        _scaleLock.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                _scaleLockItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(_scaleLock, gridBagConstraints);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("width"));
        jLabel9.setPreferredSize(new java.awt.Dimension(70, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel9, gridBagConstraints);

        _width.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        _width.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                _widthFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -3;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(_width, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("height"));
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel10, gridBagConstraints);

        _height.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        _height.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                _heightActionPerformed(evt);
            }
        });
        _height.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                _heightFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -3;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(_height, gridBagConstraints);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("border"));
        jLabel11.setPreferredSize(new java.awt.Dimension(70, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel11, gridBagConstraints);

        _border.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        _border.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -3;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(_border, gridBagConstraints);

        jLabel12.setText("%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel12, gridBagConstraints);

        jLabel13.setText("%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel13, gridBagConstraints);

        jLabel14.setText("pt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel14, gridBagConstraints);

        jLabel15.setText("pt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel15, gridBagConstraints);

        jLabel16.setText("pt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel16, gridBagConstraints);

        jLabel2.setText("B");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel4.add(jLabel2, gridBagConstraints);

        jPanel6.add(jPanel4, java.awt.BorderLayout.EAST);

        jScrollPane2.setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("preview")));
        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("date"));
        jPanel5.add(jLabel5);

        _date.setFont(new java.awt.Font("Dialog", 1, 10));
        _date.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel5.add(_date);

        jPanel6.add(jPanel5, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(jPanel6);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("accept"));
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton1);

        jButton2.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/WBDocumentFiles",locale).getString("cancel"));
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        // Add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton4ActionPerformed
    {//GEN-HEADEREND:event_jButton4ActionPerformed
        // Add your handling code here:
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
                    if(key.getKeyCode()==java.awt.event.KeyEvent.VK_DELETE)
                    {
                        executeAction(select, option);
                    }
                }
            }
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        // Add your handling code here:
        addFile();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void _heightActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__heightActionPerformed
    {//GEN-HEADEREND:event__heightActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event__heightActionPerformed

    private void _heightFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__heightFocusLost
    {//GEN-HEADEREND:event__heightFocusLost
        // Add your handling code here:
        if(preview!=null)
        {
            preview.setImageHeight(Integer.parseInt(_height.getText()));
            preview.repaint();
            _scalex.setText(""+preview.getScaleX());
            _scalex.updateUI();
            _scaley.setText(""+preview.getScaleY());
            _scaley.updateUI();
        }             
    }//GEN-LAST:event__heightFocusLost

    private void _widthFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__widthFocusLost
    {//GEN-HEADEREND:event__widthFocusLost
        // Add your handling code here:
        if(preview!=null)
        {
            preview.setImageWidth(Integer.parseInt(_width.getText()));
            preview.repaint();
            _scalex.setText(""+preview.getScaleX());
            _scalex.updateUI();
            _scaley.setText(""+preview.getScaleY());
            _scaley.updateUI();
        }          
    }//GEN-LAST:event__widthFocusLost

    private void _scaleyFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__scaleyFocusLost
    {//GEN-HEADEREND:event__scaleyFocusLost
        // Add your handling code here:
        if(preview!=null)
        {
            preview.setScaleY(Integer.parseInt(_scaley.getText()));
            preview.repaint();
            _width.setText(""+preview.getWidth());
            _height.setText(""+preview.getHeight());
        }  
    }//GEN-LAST:event__scaleyFocusLost

    private void _scaleLockItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event__scaleLockItemStateChanged
    {//GEN-HEADEREND:event__scaleLockItemStateChanged
        // Add your handling code here:
        if(_scaleLock.isSelected())
        {
            _scaley.disable();
            _scaley.updateUI();
            preview.setScaleY(Integer.parseInt(_scalex.getText()));
            preview.updateUI();
        }else
        {
            _scaley.enable();
            _scaley.updateUI();
            preview.setScaleY(Integer.parseInt(_scaley.getText()));
            preview.updateUI();
        }
    }//GEN-LAST:event__scaleLockItemStateChanged

    private void _scalexFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__scalexFocusLost
    {//GEN-HEADEREND:event__scalexFocusLost
        // Add your handling code here:
        if(preview!=null)
        {
            preview.setScaleX(Integer.parseInt(_scalex.getText()));
            if(_scaleLock.isSelected())
            {
                //_scaley.enable();
                _scaley.setText(_scalex.getText());
                //_scaley.disable();
                _scaley.updateUI();
                preview.setScaleY(Integer.parseInt(_scalex.getText()));
            }
            preview.repaint();
            _width.setText(""+preview.getWidth());
            _height.setText(""+preview.getHeight());
        }        
    }//GEN-LAST:event__scalexFocusLost

    private void _scalexPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event__scalexPropertyChange
    {//GEN-HEADEREND:event__scalexPropertyChange
        // Add your handling code here:
    }//GEN-LAST:event__scalexPropertyChange

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
                showFile(node, anode);
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        // Add your handling code here:
        preview=null;
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    
    
    public void addFile()
    {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);        
        fc.setCurrentDirectory(TemplateEditor.curDir);
        //int returnVal = fc.showSaveDialog(this);
        int returnVal = fc.showOpenDialog(this);
        TemplateEditor.curDir = fc.getCurrentDirectory();
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File files[] = fc.getSelectedFiles();
         
            String path="";
            DefaultMutableTreeNode select=(DefaultMutableTreeNode)model.getRoot();
            if(jTree1.getSelectionPath()!=null)
            {
                select=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
                WBTreeNode node=(WBTreeNode)select.getUserObject();
                if(node.getAttribute("type").equals("FILE"))
                {
                    select=(DefaultMutableTreeNode)select.getParent();
                    node=(WBTreeNode)select.getUserObject();
                }
                path=node.getAttribute(ATT_ID);
            }
            if(path.length()>0)path+="/";
            
            for(int f=0;f<files.length;f++)
            {
                try
                {
                    FUpload up=new FUpload(parent,false,jsess,upload);
                    up.setTopicMap(topicMap);
                    up.setId(id);
                    up.setVersion(version);
                    up.setType(type);
                    //up.show();
                    up.setVisible(true);
                    up.sendFile(path+files[f].getName(), files[f]);

                    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+"getFile."+WBXMLParser.encode(path+files[f].getName(),"UTF8")+"</cmd></req>";
                    String data=getData(xml);
                    WBTreeNode wbnode=(new WBXMLParser()).parse(data);
                    DefaultMutableTreeNode aux=new DefaultMutableTreeNode(wbnode.findNode(ND_NODE));
                    model.insertNodeInto(aux,select,select.getChildCount());
                    jTree1.setSelectionPath(new javax.swing.tree.TreePath(aux.getPath()));
                    jTree1.scrollPathToVisible(new javax.swing.tree.TreePath(aux.getPath()));
                }catch(Exception e){e.printStackTrace();}
                debug("Saving: " + files[f].getName());
            }
            
        } else {
            debug("Save command cancelled by user");
        }        
    }
    
    public void removeFile(String action)
    {
        try
        {
            String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>"+WBXMLParser.encode(action,"UTF8")+"</cmd></req>";
            String data=getData(xml);
            if(data.indexOf("error")==-1)
            {
                executeAction(ACC_REMOVE);                
            }else
            {
                JOptionPane.showConfirmDialog(this,"Error removing file...","WebBuilder",JOptionPane.OK_OPTION,JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){JOptionPane.showConfirmDialog(this,e,"WebBuilder",JOptionPane.OK_OPTION,JOptionPane.ERROR_MESSAGE);}
    }
    /*@Override
    public void setVisible(boolean visible)
    {
        if(visible)
            init();
        super.setVisible(visible);
    }*/
    
    public void init()
    {
        renderer=new WBDocFilesTreeRender();
        jTree1.setCellEditor(new WBTreeCellEditor(jTree1,renderer,null));
        jTree1.setCellRenderer(renderer);
        
        preview = new ImagePreview();
        preview.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(preview);        
        
        //DefaultTreeModel
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>initFiles</cmd></req>";
        String data=getData(xml);
        //System.out.println("data:"+data);
        WBTreeNode wbnode=new WBXMLParser().parse(data);
        //System.out.println("wbnode:"+wbnode);
        fillTree(jTree1,wbnode);
    }    
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt)//GEN-FIRST:event_closeDialog
    {
        preview=null;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    private String getData(String xml)
    {
        debug("getData:"+xml);
        StringBuffer ret=new StringBuffer();
        try
        {
            //URL gurl=new URL(this.url,cgi);
            URLConnection urlconn=gateway.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            urlconn.setRequestProperty("TM", topicMap);
            urlconn.setRequestProperty("ID", id);
            urlconn.setRequestProperty("VER", version);
            urlconn.setRequestProperty("TYPE", type);
            urlconn.setDoOutput(true);
            java.io.OutputStream outp=urlconn.getOutputStream();
            PrintWriter pout = new PrintWriter(outp);
            pout.println(xml);
            pout.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                ret.append(inputLine);
                ret.append("\n");
                debug(inputLine);
            }
            in.close();
        }catch(Exception e){System.out.println("Error to open service..."+e);}
        return ret.toString();
    }  
    
    public void showFile(DefaultMutableTreeNode node, WBTreeNode anode)
    {
        String type=anode.getAttribute("type");
        if("FILE".equals(type))
        {
            preview.setFileSize(anode.getAttribute("size"));
            preview.setFileDate(anode.getAttribute("date"));
            _size.setText(preview.getFileSize());
            _date.setText(preview.getFileDate());
        }
        String path=anode.getAttribute("path");
        try
        {
            URL url=new URL(gateway,path);
            
            Image img=parent.getToolkit().getImage(url);
            try
            {
                MediaTracker mt=new MediaTracker(this);
                mt.addImage(img,0);
                mt.waitForAll();
            }catch(InterruptedException e){e.printStackTrace(System.out);}
            preview.setImage(img);
            preview.setFilePath(anode.getAttribute("id"));
            
            _owidth.setText(""+preview.getOrigImageWidth());
            _oheight.setText(""+preview.getOrigImageHeight());
            _scalex.setText(""+preview.getScaleX());
            _scaley.setText(""+preview.getScaleY());
            _width.setText(""+preview.getWidth());
            _height.setText(""+preview.getHeight());
            debug("url:"+url);
            jButton1.setEnabled(true);
        }catch(Exception e){
            jButton1.setEnabled(false);
            System.out.println(e);
        }
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
                menu.show(getContentPane(),this.lastx+jTree1.getX()+5,this.lasty+jTree1.getY()+25);
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
    
    public void treeChanged(javax.swing.event.TreeModelEvent evt,String action){
        debug("treeChanged:"+action+" - "+evt);
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

                }else if(action.equals("treeNodesRemoved"))
                {
                    
                }
            }
        }
        
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
        WBTreeNode config=node.findNode(ND_CONFIG);
        docPath=config.getAttribute("docPath");

        WBTreeNode icons=node.findNode(ND_ICONS);
        Iterator it=icons.getNodes().iterator();
        while(it.hasNext())
        {
            WBTreeNode inode=(WBTreeNode)it.next();
            try
            {
                Image img=parent.getToolkit().getImage(getClass().getResource(inode.getAttribute(ATT_PATH)));
                MediaTracker mt=new MediaTracker(this);
                mt.addImage(img,0);
                mt.waitForAll();
                Image buf=img;
                String txt=inode.getAttribute(ATT_TEXT);
                renderer.getIcons().put(inode.getAttribute(ATT_ID), new ImageIcon(buf));
            }catch(InterruptedException e){e.printStackTrace(System.out);}
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
    
    private void menuActionPerformed(java.awt.event.ActionEvent evt)
    {
        // Add your handling code here:
        debug("menuActionPerformed:"+evt.getActionCommand());
        String id=evt.getActionCommand();
        DefaultMutableTreeNode select=(DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
        WBTreeNode node=(WBTreeNode)select.getUserObject();
        
        WBTreeNode menu=node.getNodebyName(ND_MENU);
        Iterator it=menu.getNodes().iterator();
        while(it.hasNext())
        {
            WBTreeNode option=(WBTreeNode)it.next();
            if(id.equals(option.getAttribute(ATT_ID)))
            {
                executeAction(select, option);
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
    
    private void selectNode(DefaultMutableTreeNode node)
    {
        TreePath path=new TreePath(node.getPath());
        jTree1.setSelectionPath(path);
        //if(!jTree1.isVisible(path))
        jTree1.scrollPathToVisible(path);
        //jTree1.updateUI();
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
            }else if(action.startsWith("removeFile"))
            {
                removeFile(action);
            }else if(action.startsWith("addFile"))
            {
                addFile();
/*                
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
 */
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
                    String xmlfin=WBXMLParser.encode(xml,"UTF8");
                    String data=getData(xmlfin);
                }catch(Exception e){System.out.println(e);}
            }else if(action.startsWith(ACC_REMOVE))
            {
                executeAction(action);
            }
        }
        
    }    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        try
        {
            WBDocumentFiles files=new WBDocumentFiles(new javax.swing.JFrame(), true, new Locale("es"));
            files.setGateway(new URL("http://localhost/wb/WBAdmin/WBAd_utl_HTMLEditor/_rid/1/_mto/3/_mod/gateway"));
            files.setUpload(new URL("http://localhost/wb/WBAdmin/WBAd_utl_HTMLEditor/_rid/1/_mto/3/_mod/upload"));
            files.setId("2");
            files.setTopicMap("Test");
            files.setVersion("1");
            //files.show();
            files.setVisible(true);
            //System.out.println("FIN:");
        }catch(Exception e){System.out.println(e);}
    }
    
    

    
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
    
    /**
     * Getter for property jsess.
     * @return Value of property jsess.
     */
    public java.lang.String getJSession()
    {
        return jsess;
    }
    
    /**
     * Setter for property jsess.
     * @param jsess New value of property jsess.
     */
    public void setJSession(java.lang.String jsess)
    {
        this.jsess = jsess;
    }
    
    /**
     * Getter for property gateway.
     * @return Value of property gateway.
     */
    public java.net.URL getGateway()
    {
        return gateway;
    }
    
    /**
     * Setter for property gateway.
     * @param gateway New value of property gateway.
     */
    public void setGateway(java.net.URL gateway)
    {
        this.gateway = gateway;
    }
    
    /**
     * Getter for property upload.
     * @return Value of property upload.
     */
    public java.net.URL getUpload()
    {
        return upload;
    }
    
    /**
     * Setter for property upload.
     * @param gateway New value of property upload.
     */
    public void setUpload(java.net.URL upload)
    {
        this.upload = upload;
    }    
    
    public ImagePreview getSelectedImage()
    {
        return preview;
    }
    
    /**
     * Getter for property topicMap.
     * @return Value of property topicMap.
     */
    public java.lang.String getTopicMap()
    {
        return topicMap;
    }
    
    /**
     * Setter for property topicMap.
     * @param topicMap New value of property topicMap.
     */
    public void setTopicMap(java.lang.String topicMap)
    {
        this.topicMap = topicMap;
    }
    
    /**
     * Getter for property templateId.
     * @return Value of property templateId.
     */
    public java.lang.String getId()
    {
        return id;
    }
    
    /**
     * Setter for property templateId.
     * @param templateId New value of property templateId.
     */
    public void setId(java.lang.String id)
    {
        this.id = id;
    }
    
    /**
     * Getter for property version.
     * @return Value of property version.
     */
    public java.lang.String getVersion()
    {
        return version;
    }
    
    /**
     * Setter for property version.
     * @param version New value of property version.
     */
    public void setVersion(java.lang.String version)
    {
        this.version = version;
    }
    
    /**
     * Getter for property type.
     * @return Value of property type.
     */
    public java.lang.String getType()
    {
        return type;
    }
    
    /**
     * Setter for property type.
     * @param type New value of property type.
     */
    public void setType(java.lang.String type)
    {
        this.type = type;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField _border;
    private javax.swing.JLabel _date;
    private javax.swing.JTextField _height;
    private javax.swing.JLabel _oheight;
    private javax.swing.JLabel _owidth;
    private javax.swing.JCheckBox _scaleLock;
    private javax.swing.JTextField _scalex;
    private javax.swing.JTextField _scaley;
    private javax.swing.JLabel _size;
    private javax.swing.JTextField _width;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    private javax.swing.JPopupMenu menu;
    // End of variables declaration//GEN-END:variables
    
}
