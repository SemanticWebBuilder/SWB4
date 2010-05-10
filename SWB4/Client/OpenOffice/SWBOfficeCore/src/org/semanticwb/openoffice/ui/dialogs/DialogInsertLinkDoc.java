/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogInsertLinkDoc.java
 *
 * Created on 3/05/2010, 11:12:04 AM
 */

package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import org.semanticwb.office.interfaces.SemanticFileRepository;
import org.semanticwb.office.interfaces.SemanticFolderRepository;
import org.semanticwb.office.interfaces.SemanticRepository;
import org.semanticwb.office.interfaces.SiteInfo;
import org.semanticwb.office.interfaces.WebSiteInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.OfficeDocument;
import org.semanticwb.openoffice.ui.icons.ImageLoader;

/**
 *
 * @author victor.lorenzana
 */
public class DialogInsertLinkDoc extends javax.swing.JDialog {

    private OfficeDocument officeDocument;
    /** Creates new form DialogInsertLinkDoc */
    public DialogInsertLinkDoc(OfficeDocument officeDocument) {
        super((Frame)null, ModalityType.TOOLKIT_MODAL);
        initComponents();
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.officeDocument=officeDocument;        
        loadSites();
        if(jComboBoxSites.getItemCount()>0)
        {
            jComboBoxSites.setSelectedIndex(0);
            loadRepositories();
        }
        jTreeFolders.setCellRenderer(new TreeRender());
        

    }
    private void loadFolders()
    {
        if(this.jComboBoxRepository.getSelectedIndex()!=-1 && jComboBoxRepository.getSelectedIndex()!=-1)
        {
            WebSiteInfo site=(WebSiteInfo)jComboBoxSites.getSelectedItem();
            SiteInfo info=new SiteInfo();
            info.id=site.id;            
            SemanticRepository sr=(SemanticRepository)jComboBoxRepository.getSelectedItem();
            RepositoryNode root=new RepositoryNode(sr);
            DefaultTreeModel model=new DefaultTreeModel(root);
            jTreeFolders.setModel(model);
            try
            {
                for(SemanticFolderRepository sf : OfficeApplication.getOfficeApplicationProxy().getSemanticFolderRepositories(info, sr))
                {
                    root.add(new RepositoryFolderNode(sf));
                }
                jTreeFolders.expandRow(0);                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        jTreeFolders.updateUI();
    }
    private void loadFolders(RepositoryNode node)
    {
        if(this.jComboBoxRepository.getSelectedIndex()!=-1 && jComboBoxRepository.getSelectedIndex()!=-1)
        {
            WebSiteInfo site=(WebSiteInfo)jComboBoxSites.getSelectedItem();
            SiteInfo info=new SiteInfo();
            info.id=site.id;
            SemanticRepository sr=(SemanticRepository)jComboBoxRepository.getSelectedItem();
            try
            {
                for(SemanticFolderRepository sf : OfficeApplication.getOfficeApplicationProxy().getSemanticFolderRepositories(info, sr))
                {
                    node.add(new RepositoryFolderNode(sf));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void loadRepositories()
    {
        this.jComboBoxRepository.removeAllItems();
        if(this.jComboBoxSites.getSelectedIndex()!=-1)
        {
            WebSiteInfo site=(WebSiteInfo)jComboBoxSites.getSelectedItem();
            SiteInfo info=new SiteInfo();
            info.id=site.id;
            try
            {
                for(SemanticRepository sr : OfficeApplication.getOfficeApplicationProxy().getSemanticRepositories(info))
                {
                    jComboBoxRepository.addItem(sr);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void loadSites()
    {
        this.jComboBoxSites.removeAllItems();
        try
        {
            for(WebSiteInfo site : OfficeApplication.getOfficeApplicationProxy().getSites())
            {
                this.jComboBoxSites.addItem(site);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jButtonInsert = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxSites = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxRepository = new javax.swing.JComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeFolders = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableFiles = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Insertar liga a documento en el repositorio");

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));

        jButtonClose.setText("Cerrar");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonInsert.setText("Insertar");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(292, Short.MAX_VALUE)
                .addComponent(jButtonInsert)
                .addGap(18, 18, 18)
                .addComponent(jButtonClose)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonInsert))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setPreferredSize(new java.awt.Dimension(400, 40));

        jLabel1.setText("Sitio:");

        jComboBoxSites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSitesActionPerformed(evt);
            }
        });

        jLabel2.setText("Repositorio:");

        jComboBoxRepository.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRepositoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxSites, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxRepository, 0, 173, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxSites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxRepository, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTreeFolders.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeFolders.setPreferredSize(new java.awt.Dimension(120, 64));
        jTreeFolders.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeFoldersValueChanged(evt);
            }
        });
        jTreeFolders.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                jTreeFoldersTreeWillExpand(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeFolders);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jTableFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Archivo", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableFiles);

        jSplitPane1.setRightComponent(jScrollPane2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTreeFoldersTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException//GEN-FIRST:event_jTreeFoldersTreeWillExpand
    {//GEN-HEADEREND:event_jTreeFoldersTreeWillExpand
        if(evt.getPath().getLastPathComponent() instanceof RepositoryNode)
        {
            try
            {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                RepositoryNode node=(RepositoryNode)evt.getPath().getLastPathComponent();
                if(node.getChildCount()==1 && !(node.getChildAt(0) instanceof RepositoryNode))
                {
                    node.removeAllChildren();
                    loadFolders(node);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();                
            }
            finally
            {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_jTreeFoldersTreeWillExpand

    private void jTreeFoldersValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_jTreeFoldersValueChanged
    {//GEN-HEADEREND:event_jTreeFoldersValueChanged
        try
        {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            DefaultTableModel model=(DefaultTableModel)jTableFiles.getModel();
            int count=jTableFiles.getModel().getRowCount();
            for(int i=0;i<count;i++)
            {
                model.removeRow(0);
            }
            if(jTreeFolders.getSelectionPath()!=null && jTreeFolders.getSelectionPath().getLastPathComponent()!=null && (jTreeFolders.getSelectionPath().getLastPathComponent() instanceof RepositoryFolderNode || jTreeFolders.getSelectionPath().getLastPathComponent() instanceof RepositoryNode))
            {
                SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                WebSiteInfo site=(WebSiteInfo)jComboBoxSites.getSelectedItem();
                SiteInfo info=new SiteInfo();
                info.id=site.id;
                SemanticRepository sr=(SemanticRepository)jComboBoxRepository.getSelectedItem();
                if(jTreeFolders.getSelectionPath().getLastPathComponent() instanceof RepositoryFolderNode)
                {
                    RepositoryFolderNode sf=(RepositoryFolderNode)jTreeFolders.getSelectionPath().getLastPathComponent();
                    try
                    {


                        for(SemanticFileRepository file : OfficeApplication.getOfficeApplicationProxy().getSemanticFileRepositories(info, sr, sf.getSemanticFolderRepository()))
                        {
                            String date=df.format(file.date);
                            Object[] data={file,date};
                            model.addRow(data);
                        }
                        jTableFiles.setModel(model);
                        jTableFiles.updateUI();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try
                    {
                        for(SemanticFileRepository file : OfficeApplication.getOfficeApplicationProxy().getSemanticFileRepositories(info, sr))
                        {
                            String date=df.format(file.date);
                            Object[] data={file,date};
                            model.addRow(data);
                        }
                        jTableFiles.setModel(model);
                        jTableFiles.updateUI();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();            
        }
        finally
        {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

    }//GEN-LAST:event_jTreeFoldersValueChanged

    private void jComboBoxSitesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxSitesActionPerformed
    {//GEN-HEADEREND:event_jComboBoxSitesActionPerformed
        if(this.jComboBoxSites.getSelectedIndex()!=-1)
        {
            try
            {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                loadRepositories();
            }
            catch(Exception e)
            {
                e.printStackTrace();                
            }
            finally
            {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

        }
    }//GEN-LAST:event_jComboBoxSitesActionPerformed

    private void jComboBoxRepositoryActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxRepositoryActionPerformed
    {//GEN-HEADEREND:event_jComboBoxRepositoryActionPerformed
        if(this.jComboBoxSites.getSelectedIndex()!=-1 && jComboBoxRepository.getSelectedIndex()!=-1)
        {
            try
            {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                loadFolders();
            }
            catch(Exception e)
            {
                e.printStackTrace();             
            }
            finally
            {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_jComboBoxRepositoryActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCloseActionPerformed
    {//GEN-HEADEREND:event_jButtonCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonInsertActionPerformed
    {//GEN-HEADEREND:event_jButtonInsertActionPerformed
        if(jTableFiles.getSelectedRow()==-1)
        {
            JOptionPane.showMessageDialog(this, "¡Debe indicar el archivo en el repositorio!", this.getTitle(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel model=(DefaultTableModel)jTableFiles.getModel();
        SemanticRepository sr=(SemanticRepository)jComboBoxRepository.getSelectedItem();
        WebSiteInfo site=(WebSiteInfo)jComboBoxSites.getSelectedItem();
        SemanticFileRepository file=(SemanticFileRepository)model.getValueAt(jTableFiles.getSelectedRow(), 0);
        String title=file.title;        
        //String url="wbrelpath://"+file.uuid+"/"+file.name;
        String url="docrep://"+ site.id +"/"+ sr.pageid +"/_rid/"+ sr.resid +"/_mto/3/_act/inline/_mod/getFile/_wst/maximized/"+file.uuid+"/"+file.name;
        //http://localhost:8080/swb/es/demo/home/_rid/45/_mto/3/_act/inline/_mod/getFile/_wst/maximized/33a923a1-8e02-4123-9cb4-86901fe62ec1/Asley%20005.jpg
        this.officeDocument.insertLink(url, title);
        

    }//GEN-LAST:event_jButtonInsertActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JComboBox jComboBoxRepository;
    private javax.swing.JComboBox jComboBoxSites;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableFiles;
    private javax.swing.JTree jTreeFolders;
    // End of variables declaration//GEN-END:variables

}

class RepositoryFolderNode extends DefaultMutableTreeNode
{

    protected JLabel component = new JLabel();
    protected SemanticFolderRepository semanticFolderRepository;

    public RepositoryFolderNode(SemanticFolderRepository semanticFolderRepository)
    {
        this.semanticFolderRepository = semanticFolderRepository;
        component.setText(semanticFolderRepository.name);
        component.setOpaque(true);
        component.setToolTipText(this.getToolTipText());
        if(semanticFolderRepository.haschilds)
        {
            this.add(new DefaultMutableTreeNode());
        }
    }
    public SemanticFolderRepository getSemanticFolderRepository()
    {
        return semanticFolderRepository;
    }
    @Override
    public boolean isRoot()
    {
        return true;
    }

    public String getToolTipText()
    {
        return semanticFolderRepository.name;
    }

    public String getName()
    {
        return this.semanticFolderRepository.name;
    }

    @Override
    public String toString()
    {

        return semanticFolderRepository.name;
    }

    @Override
    public int hashCode()
    {
        return semanticFolderRepository.name.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if (obj instanceof RepositoryNode)
        {
            equals = ((RepositoryNode) obj).semanticFolderRepository.name.equals(this.semanticFolderRepository.name);
        }
        return equals;
    }

    public JLabel getComponent()
    {
        return component;
    }
}


class RepositoryNode extends DefaultMutableTreeNode
{

    protected JLabel component = new JLabel();
    protected SemanticRepository semanticFolderRepository;

    public RepositoryNode(SemanticRepository semanticFolderRepository)
    {
        this.semanticFolderRepository = semanticFolderRepository;
        component.setText(semanticFolderRepository.name);
        component.setOpaque(true);
        component.setToolTipText(this.getToolTipText());
    }
    public SemanticRepository getSemanticRepository()
    {
        return semanticFolderRepository;
    }
    @Override
    public boolean isRoot()
    {
        return false;
    }

    public String getToolTipText()
    {
        return semanticFolderRepository.name;
    }

    public String getName()
    {
        return this.semanticFolderRepository.name;
    }

    @Override
    public String toString()
    {

        return semanticFolderRepository.name;
    }

    @Override
    public int hashCode()
    {
        return semanticFolderRepository.name.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if (obj instanceof RepositoryNode)
        {
            equals = ((RepositoryNode) obj).semanticFolderRepository.name.equals(this.semanticFolderRepository.name);
        }
        return equals;
    }

    public JLabel getComponent()
    {
        return component;
    }
}
class TreeRender extends JPanel implements TreeCellRenderer
{

    private static final String EMPTY_STRING = "";

    public Component getTreeCellRendererComponent(JTree tree, Object object, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        Component component = this;
        tree.setToolTipText(EMPTY_STRING);

        if (object instanceof RepositoryNode)
        {
            component = ((RepositoryNode) object).getComponent();

        }
        if (object instanceof RepositoryFolderNode)
        {
            component = ((RepositoryFolderNode) object).getComponent();

        }
        if (component != null && component instanceof JLabel)
        {
            JLabel label = (JLabel) component;

            label.setFont(tree.getFont());
            if (expanded)
            {
                label.setIcon(ImageLoader.images.get("open"));
            }
            else
            {
                label.setIcon(ImageLoader.images.get("close"));
            }
            if (selected)
            {
                label.setBackground(Color.BLUE);
                label.setForeground(Color.WHITE);
            }
            else
            {
                label.setBackground(tree.getBackground());
                label.setForeground(tree.getForeground());
            }
        }
        return component;

    }
}