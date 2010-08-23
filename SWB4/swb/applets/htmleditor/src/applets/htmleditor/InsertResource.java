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
 * InsertResource.java
 *
 * Created on 20 de enero de 2005, 04:37 PM
 */

package applets.htmleditor;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

import applets.commons.*;
/**
 *
 * @author  Victor Lorenzana
 */
public class InsertResource extends javax.swing.JDialog {
    URL gateway;
    String jsess;
    String topicMap;
    int ret=0;
    String tag;
    Locale locale;
    WBTreeNode node;
    /** Creates new form InsertResource */
    public InsertResource(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setSize(500,400);
        setLocation(200,200);                
        locale=Locale.getDefault();
        this.jTextFieldName.setDocument(new FixedNumericDocument(50, false));
    }
    @Override
    public void setLocale(Locale locale)
    {
        this.locale=locale;
    }
    public String getTag()
    {
        return tag;
    }
    public void setTag(String tag)
    {
        this.tag=tag;        
    }    
    public int getResultValue()
    {
        return ret;
    }
    public void setGateway(java.net.URL gateway)
    {
        this.gateway = gateway;
    }
    public void setJSession(java.lang.String jsess)
    {
        this.jsess = jsess;
    }
    public void setTopicMap(java.lang.String topicMap)
    {
        this.topicMap = topicMap;
    }
    public void loadResources()
    {
        Root root=new Root("Tipos de recursos");        
        DefaultTreeModel model=new DefaultTreeModel(root);
        this.jTreeTipoRecurso.setModel(model);
        this.jTreeTipoRecurso.setCellRenderer(new CheckRenderer());
        javax.swing.ImageIcon i=new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/f_resourcetype.gif"));        
        this.jTreeTipoRecurso.setRowHeight((int)(i.getIconHeight()*1.5));
        jTreeTipoRecurso.addMouseListener(new CheckListener()); 
        String respxml=this.getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getResourceTypeCat</cmd><tm>" + topicMap + "</tm></req>");
        
        try
        {
            WBXMLParser parser=new WBXMLParser();
            WBTreeNode nodetree=parser.parse(respxml);
            if(nodetree.getFirstNode()!=null)
            {               
                
                Iterator it=nodetree.getFirstNode().getNodesbyName("resourceType");
                while(it.hasNext())
                {
                    
                    WBTreeNode etype=(WBTreeNode)it.next();
                    ResourceType type=new ResourceType(etype.getAttribute("id"), etype.getAttribute("topicmap"), etype.getAttribute("name"), this.buttonGroup);                                      
                    root.add(type);
                    Iterator itsub=etype.getNodesbyName("subResourceType");
                    while(itsub.hasNext())
                    {
                        WBTreeNode esubtype=(WBTreeNode)itsub.next();
                        SubResourceType sub=new SubResourceType(esubtype.getAttribute("id"), esubtype.getAttribute("topicmap"), esubtype.getAttribute("name"),this.buttonGroup);
                        type.add(sub);
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
        this.jTreeTipoRecurso.expandPath(new TreePath(root));
    }
    
    public void init()
    {
        ret=0;
        loadResources();    
        if(tag!=null)
        {
            try
            {
                WBXMLParser parser=new WBXMLParser();
                node=parser.parse(tag);
                if(node.getName().equalsIgnoreCase("resource"))
                {
                    if(node.getAttribute("name")!=null)
                    {
                        this.jTextFieldName.setText(node.getAttribute("name"));
                    }                                      
                    if(node.getAttribute("type")!=null)
                    {
                        Object root=this.jTreeTipoRecurso.getModel().getRoot();
                        int iTypes=this.jTreeTipoRecurso.getModel().getChildCount(root);
                        for(int i=0;i<iTypes;i++)
                        {
                            Object otype=this.jTreeTipoRecurso.getModel().getChild(root, i);
                            if(otype instanceof ResourceType)
                            {
                                ResourceType resType=(ResourceType)otype;
                                if(resType.getID().equalsIgnoreCase(node.getAttribute("type")))
                                {
                                    resType.setSelected(true);
                                    if(node.getAttribute("stype")!=null)
                                    {
                                        int iSubTypes=this.jTreeTipoRecurso.getModel().getChildCount(resType);
                                        for(int j=0;j<iSubTypes;i++)
                                        {
                                            Object subres=this.jTreeTipoRecurso.getModel().getChild(resType, j);
                                            if(subres instanceof SubResourceType)
                                            {
                                                SubResourceType oresSubType=(SubResourceType)subres;
                                                if(oresSubType.getID().equalsIgnoreCase(node.getAttribute("stype")))
                                                {
                                                    oresSubType.setSelected(true);
                                                }
                                            }
                                        }
                                    }    
                                }
                                
                            }
                        }
                        
                    }
                }
                else
                {
                    this.tag=null;
                }
            }
            catch(Exception e)
            {
                this.tag=null;
            }
        }        
    }
    
    private String getData(String xml)
    {
        StringBuffer ret=new StringBuffer();
        try
        {
            //URL gurl=new URL(this.url,cgi);
            URLConnection urlconn=gateway.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);            
            urlconn.setRequestProperty("TM", this.topicMap);
            urlconn.setRequestProperty("ID", "1");
            urlconn.setRequestProperty("VER", "1");
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
            }
            in.close();
        }catch(Exception e){System.out.println("Error to open service..."+e);}
        
        return ret.toString();
    }  
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        buttonGroup = new javax.swing.ButtonGroup();
        buttonGroupCampaña = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeTipoRecurso = new javax.swing.JTree();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jTextFieldName = new javax.swing.JTextField();

        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(220, 10));
        jButton1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertResource",locale).getString("aceppt"));
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.add(jButton1);

        jButton2.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertResource",locale).getString("cancel"));
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.add(jButton2);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jTreeTipoRecurso.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                jTreeTipoRecursoMouseMoved(evt);
            }
        });

        jScrollPane1.setViewportView(jTreeTipoRecurso);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(0, 20));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/InsertResource",locale).getString("Name_espace"));
        jPanel6.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel6, java.awt.BorderLayout.WEST);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel7.add(jTextFieldName, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents

    private void jTreeTipoRecursoMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeTipoRecursoMouseMoved
        this.jTreeTipoRecurso.setSelectionPath(this.jTreeTipoRecurso.getPathForLocation(evt.getX(), evt.getY()));
    }//GEN-LAST:event_jTreeTipoRecursoMouseMoved

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        if(this.jTextFieldName.getText().trim().equals(""))
//        {
//            this.jTextFieldName.grabFocus();
//            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/InsertResource",locale).getString("indicar_titutlo"),java.util.ResourceBundle.getBundle("applets/htmleditor/InsertResource",locale).getString("title"),JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        boolean selected=false;
        SelectableNode selectableNode=null;
        Object root=this.jTreeTipoRecurso.getModel().getRoot();
        int iTypes=this.jTreeTipoRecurso.getModel().getChildCount(root);
        for(int i=0;i<iTypes;i++)
        {
            Object otype=this.jTreeTipoRecurso.getModel().getChild(root, i);
            if(otype instanceof ResourceType)
            {
                ResourceType resType=(ResourceType)otype;                                
                if(resType.isSelected())
                {
                    selectableNode=resType;
                    selected=true;
                    break;
                }
                int iSubTypes=this.jTreeTipoRecurso.getModel().getChildCount(resType);
                for(int j=0;j<iSubTypes;j++)
                {
                    Object subres=this.jTreeTipoRecurso.getModel().getChild(resType, j);
                    if(subres instanceof SubResourceType)
                    {
                        SubResourceType oSubType=(SubResourceType)subres;
                        if(oSubType.isSelected())
                        {
                            selectableNode=oSubType;
                            selected=true;
                            continue;
                        }
                    }
                }                                
            }
        }
        if(!selected || selectableNode==null)
        {            
            this.jTreeTipoRecurso.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/InsertResource",locale).getString("indicar_tipo_recurso"),java.util.ResourceBundle.getBundle("applets/htmleditor/InsertResource",locale).getString("title"),JOptionPane.ERROR_MESSAGE);            
            return;
        }
        String innerText="";
        if(this.node!=null && this.node.getText()!=null)
        {
            innerText=this.node.getText();
        }

        String nt="";
        if(!this.jTextFieldName.getText().trim().equals(""))
        {
            nt="NAME=\""+ this.jTextFieldName.getText().trim() +"\" ";
        }

        if(selectableNode instanceof ResourceType)
        {
            ResourceType res=(ResourceType)selectableNode;
            if(innerText==null || innerText.length()==0)
            {
                this.tag="<RESOURCE "+nt+"TYPE=\""+ res.getID() +"\"/>";
            }else
            {
                this.tag="<RESOURCE "+nt+"TYPE=\""+ res.getID() +"\">"+innerText+"</Resource>";
            }
            
        }
        else
        {
            SubResourceType subres=(SubResourceType)selectableNode;
            ResourceType res=(ResourceType)subres.getParent();
            if(innerText==null || innerText.length()==0)
            {
                this.tag="<RESOURCE "+nt+"TYPE=\""+ res.getID() +"\" STYPE=\""+subres.getID()+"\"/>";
            }else
            {
                this.tag="<RESOURCE "+nt+"TYPE=\""+ res.getID() +"\" STYPE=\""+subres.getID()+"\">"+innerText +"</Resource>";
            }
        }
        
        ret=2;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        ret=0;
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.ButtonGroup buttonGroupCampaña;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTree jTreeTipoRecurso;
    // End of variables declaration//GEN-END:variables
    
}
