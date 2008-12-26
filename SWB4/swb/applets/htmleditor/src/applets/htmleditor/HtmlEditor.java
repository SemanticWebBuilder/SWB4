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
 * HtmlEditor.java
 *
 * Created on 20 de septiembre de 2004, 06:02 PM
 */

package applets.htmleditor;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class HtmlEditor extends javax.swing.JApplet
{
    
    private final String backgroundParam = "background";
    private final String foregroundParam = "foreground";
    private final String backgroundSelectionParam = "backgroundSelection";
    private final String foregroundSelectionParam = "foregroundSelection";
    
    private Vector vFonts=null;
    
    private String oldTab="html";
    
    
    /** Initializes the applet HtmlEditor */
    public void init()
    {
        loadFonts();
        initComponents();
        cmbStyle.setVisible(false);
        cmbSize.setVisible(false);
    }
    
    private void loadFonts()
    {
        //Fonts
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        vFonts = new Vector(fonts.length - 5);
        for(int i = 0; i < fonts.length; i++)
        {
            if(!fonts[i].equals("Dialog") && !fonts[i].equals("DialogInput") && !fonts[i].equals("Monospaced") && !fonts[i].equals("SansSerif") && !fonts[i].equals("Serif"))
            {
                    vFonts.add(fonts[i]);
            }
        }        
    }
    
    private void usePageParams()
    {
        WBHTMLEditoKit htmlkit = new WBHTMLEditoKit();
        htmlEditor.setEditorKit(htmlkit);

        final String defaultBackground = "979fc3";
        final String defaultForeground = "000000";
        final String defaultBackgroundSelection = "666699";
        final String defaultForegroundSelection = "ffffff";
        String backgroundValue;
        String foregroundValue;
        String backgroundSelectionValue;
        String foregroundSelectionValue;

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
        
        /*
        this.jButton3.setEnabled(false);
        this.jButton4.setEnabled(false);
        this.jButton5.setEnabled(false);
        this.jButton6.setEnabled(false);
        this.jButton1.setEnabled(false);        
        this.jButton2.setEnabled(false);
        */
         
        //cursores
        //curdrag = getToolkit().createCustomCursor(getImage(getCodeBase(), "images/drag.gif"), new Point( 16, 8 ), "drop");
        //curdrag2 = getToolkit().createCustomCursor(getImage(getCodeBase(), "images/drag3.gif"), new Point( 15, 15 ), "drop2");
        

        try
        {
            URL url=new URL(this.getCodeBase(),this.getParameter("document"));
            System.out.println("load url:"+url);
            htmlEditor.setPage(url);
            htmlPreview.getDocument().putProperty(Document.StreamDescriptionProperty,url);
/*            
            //********** parser
            HTMLEditorKit.Parser parser = htmlkit.getParser();
            HTMLEditorKit.ParserCallback callback = new WBParserCallback(new OutputStreamWriter(System.out));
            InputStream in = url.openStream();
            InputStreamReader r = new InputStreamReader(in);
            parser.parse(r, callback, true);
            //*********
*/
        }catch(Exception e)
        {System.out.println(e);}

    }
    
    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        htmlEditor = new javax.swing.JEditorPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textEditor = new WBTextEditorPane();
        jToolBar2 = new javax.swing.JToolBar();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        htmlPreview = new javax.swing.JEditorPane();
        jToolBar1 = new javax.swing.JToolBar();
        cmbStyle = new javax.swing.JComboBox();
        cmbFont = new JComboBox(vFonts);
        cmbSize = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        mnuEdit = new javax.swing.JMenu();
        mnuUndo = new javax.swing.JMenuItem();
        mnuRedo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        mnuCut = new javax.swing.JMenuItem();
        mnuCopy = new javax.swing.JMenuItem();
        mnuPaste = new javax.swing.JMenuItem();
        mnuDelete = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        mnuFind = new javax.swing.JMenuItem();
        mnuReplace = new javax.swing.JMenuItem();
        mnuGoto = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        mnuSelectAll = new javax.swing.JMenuItem();
        mnuSelectParagraph = new javax.swing.JMenuItem();
        mnuSelectLine = new javax.swing.JMenuItem();
        mnuSelectWord = new javax.swing.JMenuItem();

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        htmlEditor.setContentType("text/html");
        jScrollPane1.setViewportView(htmlEditor);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("HTML", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setViewportView(textEditor);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jToolBar2.setBorder(new javax.swing.border.EtchedBorder());
        jToolBar2.setFloatable(false);
        jToolBar2.setPreferredSize(new java.awt.Dimension(4, 20));
        jPanel2.add(jToolBar2, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addTab("Text", jPanel2);

        jPanel4.setLayout(new java.awt.BorderLayout());

        htmlPreview.setEditable(false);
        htmlPreview.setContentType("text/html");
        jScrollPane3.setViewportView(htmlPreview);

        jPanel4.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Preview", jPanel4);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        usePageParams();
        jToolBar1.setBorder(new javax.swing.border.EtchedBorder());
        jToolBar1.add(cmbStyle);

        cmbFont.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cmbFontActionPerformed(evt);
            }
        });

        jToolBar1.add(cmbFont);

        jToolBar1.add(cmbSize);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/b_guardar.gif")));
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

        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/b_cancelar.gif")));
        jButton4.setBorderPainted(false);
        jButton4.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton4.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton4.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton4.setPreferredSize(new java.awt.Dimension(25, 25));
        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/b_nuevo.gif")));
        jButton5.setBorderPainted(false);
        jButton5.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton5.setPreferredSize(new java.awt.Dimension(25, 25));
        jToolBar1.add(jButton5);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/b_editar.gif")));
        jButton1.setBorderPainted(false);
        jButton1.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton1.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(25, 25));
        jToolBar1.add(jButton1);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/b_borrar.gif")));
        jButton6.setBorderPainted(false);
        jButton6.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton6.setPreferredSize(new java.awt.Dimension(25, 25));
        jToolBar1.add(jButton6);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/b_abrir.gif")));
        jButton2.setBorderPainted(false);
        jButton2.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton2.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton2.setPreferredSize(new java.awt.Dimension(25, 25));
        jToolBar1.add(jButton2);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        jPanel3.add(jLabel2);

        jToolBar1.add(jPanel3);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        mnuEdit.setMnemonic('E');
        mnuEdit.setText("Edit");
        mnuUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        mnuUndo.setText("Undo");
        mnuUndo.setToolTipText("Undo");
        mnuEdit.add(mnuUndo);

        mnuRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        mnuRedo.setText("Redo");
        mnuEdit.add(mnuRedo);

        mnuEdit.add(jSeparator1);

        mnuCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mnuCut.setText("Cut");
        mnuEdit.add(mnuCut);

        mnuCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnuCopy.setText("Copy");
        mnuEdit.add(mnuCopy);

        mnuPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        mnuPaste.setText("Paste");
        mnuEdit.add(mnuPaste);

        mnuDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        mnuDelete.setText("Delete");
        mnuEdit.add(mnuDelete);

        mnuEdit.add(jSeparator2);

        mnuFind.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_FIND, 0));
        mnuFind.setText("Find...");
        mnuEdit.add(mnuFind);

        mnuReplace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        mnuReplace.setText("Replace...");
        mnuEdit.add(mnuReplace);

        mnuGoto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        mnuGoto.setText("Goto...");
        mnuEdit.add(mnuGoto);

        mnuEdit.add(jSeparator3);

        mnuSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        mnuSelectAll.setText("Select All");
        mnuEdit.add(mnuSelectAll);

        mnuSelectParagraph.setText("Select Paragraph");
        mnuEdit.add(mnuSelectParagraph);

        mnuSelectLine.setText("Select Line");
        mnuEdit.add(mnuSelectLine);

        mnuSelectWord.setText("Select Word");
        mnuSelectWord.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mnuSelectWordActionPerformed(evt);
            }
        });

        mnuEdit.add(mnuSelectWord);

        jMenuBar2.add(mnuEdit);

        setJMenuBar(jMenuBar2);

    }//GEN-END:initComponents

    private void mnuSelectWordActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuSelectWordActionPerformed
    {//GEN-HEADEREND:event_mnuSelectWordActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_mnuSelectWordActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        htmlEditor.setEditable(!htmlEditor.isEditable());
        htmlEditor.updateUI();
        // Add your handling code here:
        //System.out.println(textEditor.getSize());
        //int pos=textEditor.getCaretPosition();
        System.out.println("Editable:"+htmlEditor.isEditable());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cmbFontActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cmbFontActionPerformed
    {//GEN-HEADEREND:event_cmbFontActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_cmbFontActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jTabbedPane1StateChanged
    {//GEN-HEADEREND:event_jTabbedPane1StateChanged
        // Add your handling code here:
        System.out.println("jTabbedPane1StateChanged:"+evt);
        try
        {
            if(jTabbedPane1.getSelectedComponent()==jPanel2 || jTabbedPane1.getSelectedComponent()==jPanel4)
            {
                if(oldTab.equals("html"))
                {
                    //textEditor.setLogicalStyle(STYLE_TEXT);
                    textEditor.setText(htmlEditor.getText());
                }
                oldTab="text";
            }else if(jTabbedPane1.getSelectedComponent()==jPanel1 || jTabbedPane1.getSelectedComponent()==jPanel4)
            {
                if(oldTab.equals("text"))
                {
                    htmlEditor.setText(textEditor.getText());
                }
                oldTab="html";
            }
            if(jTabbedPane1.getSelectedComponent()==jPanel4)
            {
                if(oldTab.equals("html"))
                {
                    htmlPreview.setText(htmlEditor.getText());
                    textEditor.setText(htmlEditor.getText());
                }else
                {
                    htmlPreview.setText(textEditor.getText());
                    htmlEditor.setText(textEditor.getText());
                }
                oldTab="preview";
            }
        }catch(Exception e){e.printStackTrace(System.out);}
    }//GEN-LAST:event_jTabbedPane1StateChanged
    
    private Color stringToColor(String paramValue)
    {
        int red = (Integer.decode("0x" + paramValue.substring(0,2))).intValue();
        int green = (Integer.decode("0x" + paramValue.substring(2,4))).intValue();
        int blue = (Integer.decode("0x" + paramValue.substring(4,6))).intValue();
        return new Color(red,green,blue);
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbFont;
    private javax.swing.JComboBox cmbSize;
    private javax.swing.JComboBox cmbStyle;
    private javax.swing.JEditorPane htmlEditor;
    private javax.swing.JEditorPane htmlPreview;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenuItem mnuCopy;
    private javax.swing.JMenuItem mnuCut;
    private javax.swing.JMenuItem mnuDelete;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JMenuItem mnuFind;
    private javax.swing.JMenuItem mnuGoto;
    private javax.swing.JMenuItem mnuPaste;
    private javax.swing.JMenuItem mnuRedo;
    private javax.swing.JMenuItem mnuReplace;
    private javax.swing.JMenuItem mnuSelectAll;
    private javax.swing.JMenuItem mnuSelectLine;
    private javax.swing.JMenuItem mnuSelectParagraph;
    private javax.swing.JMenuItem mnuSelectWord;
    private javax.swing.JMenuItem mnuUndo;
    private javax.swing.JTextPane textEditor;
    // End of variables declaration//GEN-END:variables
    
}
/*
 *Default
  Monospaced 11 Plain
  0,0,0
  255,255,255

Selected Text
  255,255,255
  192,192,192

Character Reference
  178,0,0
  255,255,255

Html Special Syntax
  0,124,0
  255,255,255

SGML-type Comment
  128,128,128
  255,255,255

Matching Bracket
  255,255,255
  255,50,210

Highlighted by Search
  0,0,0
  255,255,128

Attribute of a tag
  0,124,0
  255,255,255

Html Comment
  128,128,128
  255,255,255

Search Wrapeed
  255,255,255
  255,0,0

Html Tag
  0,0,255
  255,255,255

Attribute Value
  153,0,107
  255,255,255

Incremental Search
  0,0,0
  255,107,138

Sgml Declaration
  191,146,33
  255,255,255
*/