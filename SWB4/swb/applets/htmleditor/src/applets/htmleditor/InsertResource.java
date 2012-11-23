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
        StringBuilder ret=new StringBuilder();
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        buttonGroupCampana = new javax.swing.ButtonGroup();
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

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 50));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(220, 10));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("applets/htmleditor/InsertResource",locale); // NOI18N
        jButton1.setText(bundle.getString("aceppt")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setText(bundle.getString("cancel")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jTreeTipoRecurso.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTreeTipoRecursoMouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeTipoRecurso);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel5.setPreferredSize(new java.awt.Dimension(0, 20));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel1.setText(bundle.getString("Name_espace")); // NOI18N
        jPanel6.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel6, java.awt.BorderLayout.WEST);

        jPanel7.setLayout(new java.awt.BorderLayout());
        jPanel7.add(jTextFieldName, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.ButtonGroup buttonGroupCampana;
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
