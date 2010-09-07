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
 * Search.java
 *
 * Created on 9 de diciembre de 2008, 12:01 PM
 */
package org.semanticwb.openoffice.ui.wizard;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.ContentInfo;
import org.semanticwb.office.interfaces.ContentType;
import org.semanticwb.office.interfaces.RepositoryInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.DocumentType;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.components.WebPage;
import org.semanticwb.openoffice.ui.dialogs.DialogSelectWebPageToOpen;

/**
 *
 * @author  victor.lorenzana
 */
public class Search extends WizardPage
{
    private WebPageInfo selectedWebPageInfo;
    public static final String CONTENT = "CONTENT";
    public static final String WORKSPACE = "WORKSPACE";
    public static Map map;
    private DocumentType officeType;

    /** Creates new form Search */
    public Search(DocumentType officeType)
    {
        initComponents();
        this.officeType = officeType;
        this.jComboBoxCategory.removeAllItems();
        this.jComboBoxType.removeAllItems();
        this.jComboBoxRepositorio.removeAllItems();
        try
        {
            for (RepositoryInfo rep : OfficeApplication.getOfficeApplicationProxy().getRepositories())
            {
                this.jComboBoxRepositorio.addItem(rep);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void search(String repositoryName, String title, String description, String type, String category)
    {
        DefaultTableModel model = (DefaultTableModel) this.jTableContents.getModel();
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            model.removeRow(0);
        }
        try
        {

            ContentInfo[] contens=null;
            if(this.selectedWebPageInfo==null)
            {
                contens = OfficeApplication.getOfficeApplicationProxy().search(repositoryName, title, description, category, type, officeType.toString());
            }
            else
            {
                contens = OfficeApplication.getOfficeApplicationProxy().search(repositoryName, title, description, category, type, officeType.toString(),this.selectedWebPageInfo);
                
            }
            if (contens.length == 0)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("¡NO_SE_ENCONTRARÓN_COINCIDENCIAS_PARA_LA_BUSQUEDA!"), Search.getDescription(), JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("¡SE_ENCONTRARÓN_") + " "+contens.length +" "+ java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("_COINCIDENCIAS_PARA_LA_BUSQUEDA!"), Search.getDescription(), JOptionPane.INFORMATION_MESSAGE);
            }
            for (ContentInfo info : contens)
            {
                String date = OfficeApplication.iso8601dateFormat.format(info.created);
                Object[] values =
                {
                    info, info.descripcion, date, info.categoryTitle
                };
                model.addRow(values);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getDescription()
    {
        return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("BÚSQUEDA_DE_CONTENIDO");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextFieldTitle = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDescription = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxCategory = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableContents = new javax.swing.JTable();
        jButtonView = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxRepositorio = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jTextWebPage = new javax.swing.JTextField();
        jButtonSelectWebPage = new javax.swing.JButton();
        jButtonSelectWebPageDelete = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search"); // NOI18N
        jLabel1.setText(bundle.getString("TÍTULO:")); // NOI18N

        jTextFieldTitle.setToolTipText(bundle.getString("DEBE_INDICAR_UNA_PALABRA_O_TÍTULO_COMPLETO_A_BUSCAR")); // NOI18N

        jLabel2.setText(bundle.getString("DESCRIPCIÓN:")); // NOI18N

        jTextFieldDescription.setToolTipText(bundle.getString("DEBE_INDICAR_UNA_PALABRA_O_DESCRIPCIÓN_COMPLETA_A_BUSCAR")); // NOI18N

        jButtonSearch.setText(bundle.getString("BUSCAR")); // NOI18N
        jButtonSearch.setToolTipText(bundle.getString("SELECCIONE_ESTA_OPCIÓN_PARA_BUSCAR_CONTENIDOS_QUE_COINCIDAN_CON_LOS_CRITERIOS_DE_BÚSQUEDA")); // NOI18N
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jLabel3.setText(bundle.getString("CATEGORIA:")); // NOI18N

        jComboBoxCategory.setToolTipText(bundle.getString("SELECCIONE_UNA_CATEGORIA_PARA_BUSCAR_EL_CONTENIDO")); // NOI18N

        jTableContents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Título", "Descripción", "Fecha de creación", "Categoria"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableContents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableContents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableContentsMouseClicked(evt);
            }
        });
        jTableContents.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableContentsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableContents);

        jButtonView.setText(bundle.getString("VER_CONTENIDO")); // NOI18N
        jButtonView.setEnabled(false);

        jLabel4.setText(bundle.getString("REPOSITORIO:")); // NOI18N

        jComboBoxRepositorio.setToolTipText(bundle.getString("SELECCIONE_UN_REPOSITORIO_DE_CONTENIDOS")); // NOI18N
        jComboBoxRepositorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRepositorioActionPerformed(evt);
            }
        });

        jLabel5.setText(bundle.getString("TIPO_DE_CONTENIDO:")); // NOI18N

        jLabel6.setText(bundle.getString("PAGINA_WEB:")); // NOI18N

        jTextWebPage.setEditable(false);
        jTextWebPage.setToolTipText(bundle.getString("DEBE_INDICAR_UNA_PALABRA_O_DESCRIPCIÓN_COMPLETA_A_BUSCAR")); // NOI18N

        jButtonSelectWebPage.setText("...");
        jButtonSelectWebPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectWebPageActionPerformed(evt);
            }
        });

        jButtonSelectWebPageDelete.setText("X");
        jButtonSelectWebPageDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectWebPageDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldDescription, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                            .addComponent(jComboBoxRepositorio, javax.swing.GroupLayout.Alignment.TRAILING, 0, 358, Short.MAX_VALUE)
                            .addComponent(jComboBoxCategory, javax.swing.GroupLayout.Alignment.TRAILING, 0, 358, Short.MAX_VALUE)
                            .addComponent(jTextFieldTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                            .addComponent(jComboBoxType, javax.swing.GroupLayout.Alignment.TRAILING, 0, 358, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jTextWebPage, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonSelectWebPage)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSelectWebPageDelete))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonView)
                                .addGap(50, 50, 50)
                                .addComponent(jButtonSearch)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxRepositorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxType)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextWebPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jButtonSelectWebPageDelete)
                    .addComponent(jButtonSelectWebPage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonView)
                    .addComponent(jButtonSearch))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
    if (this.jComboBoxRepositorio.getSelectedItem() == null)
    {
        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("¡DEBE_INDICAR_UN_REPOSITORIO!"), Search.getDescription(), JOptionPane.ERROR_MESSAGE);
        this.jComboBoxRepositorio.requestFocus();
        return;
    }
    if (this.jComboBoxCategory.getSelectedItem() == null)
    {
        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("¡DEBE_INDICAR_UNA_CATEGORIA!"), Search.getDescription(), JOptionPane.ERROR_MESSAGE);
        this.jComboBoxCategory.requestFocus();
        return;
    }
    if (this.jComboBoxType.getSelectedItem() == null)
    {
        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("¡DEBE_INDICAR_UN_TIPO_DE_CONTENIDO!"), Search.getDescription(), JOptionPane.ERROR_MESSAGE);
        this.jComboBoxType.requestFocus();
        return;
    }
    CategoryInfo category = (CategoryInfo) this.jComboBoxCategory.getSelectedItem();
    String title = "*";
    String description = "*";
    if (!this.jTextFieldTitle.getText().isEmpty())
    {
        title = this.jTextFieldTitle.getText();
    }
    if (!this.jTextFieldDescription.getText().isEmpty())
    {
        description = this.jTextFieldDescription.getText();
    }
    ContentType type = (ContentType) jComboBoxType.getSelectedItem();
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    search(this.jComboBoxRepositorio.getSelectedItem().toString(), title, description, type.id, category.UDDI);
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

}//GEN-LAST:event_jButtonSearchActionPerformed

private void jComboBoxRepositorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRepositorioActionPerformed
    this.jComboBoxCategory.removeAllItems();
    if (this.jComboBoxRepositorio.getSelectedItem() != null)
    {
        String rep = this.jComboBoxRepositorio.getSelectedItem().toString();
        try
        {
            selectedWebPageInfo=null;
            this.jTextWebPage.setText("");
            for (ContentType type : OfficeApplication.getOfficeApplicationProxy().getContentTypes(rep))
            {
                this.jComboBoxType.addItem(type);
            }
            for (CategoryInfo cat : OfficeApplication.getOfficeApplicationProxy().getAllCategories(rep))
            {
                this.jComboBoxCategory.addItem(cat);
            }
            CategoryInfo all = new CategoryInfo();
            all.UDDI = "*";
            all.title = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("TODAS_LAS_CATEGORIAS");
            all.description = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("TODAS_LAS_CATEGORIAS_DEL_REPOSITORIO");
            this.jComboBoxCategory.addItem(all);
            this.jComboBoxCategory.setSelectedItem(all);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}//GEN-LAST:event_jComboBoxRepositorioActionPerformed

private void jTableContentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableContentsMouseClicked
    this.jButtonView.setEnabled(false);
    if (jTableContents.getSelectedRow() != -1)
    {
        this.jButtonView.setEnabled(true);
    }
}//GEN-LAST:event_jTableContentsMouseClicked

private void jTableContentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableContentsKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN)
    {
        jTableContentsMouseClicked(null);
    }
}//GEN-LAST:event_jTableContentsKeyPressed

private void jButtonSelectWebPageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSelectWebPageActionPerformed
{//GEN-HEADEREND:event_jButtonSelectWebPageActionPerformed
    String siteid=null;
    if(this.jComboBoxRepositorio.getSelectedItem()!=null)
    {
        RepositoryInfo rep=(RepositoryInfo)this.jComboBoxRepositorio.getSelectedItem();
        if(rep.siteInfo!=null && rep.siteInfo.id!=null)
        {
            siteid=rep.siteInfo.id;
        }
        try
        {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            DialogSelectWebPageToOpen dialogSelectWebPageToOpen=new DialogSelectWebPageToOpen(siteid);
            dialogSelectWebPageToOpen.setVisible(true);
            if(!dialogSelectWebPageToOpen.isCancel())
            {
                WebPage selected=dialogSelectWebPageToOpen.getSelectedWebPage();
                if(selected!=null)
                {
                    this.jTextWebPage.setText(selected.getWebPageInfo().title);
                    selectedWebPageInfo=selected.getWebPageInfo();
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}//GEN-LAST:event_jButtonSelectWebPageActionPerformed

private void jButtonSelectWebPageDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSelectWebPageDeleteActionPerformed
{//GEN-HEADEREND:event_jButtonSelectWebPageDeleteActionPerformed
    this.selectedWebPageInfo=null;
    this.jTextWebPage.setText("");
}//GEN-LAST:event_jButtonSelectWebPageDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonSelectWebPage;
    private javax.swing.JButton jButtonSelectWebPageDelete;
    private javax.swing.JButton jButtonView;
    private javax.swing.JComboBox jComboBoxCategory;
    private javax.swing.JComboBox jComboBoxRepositorio;
    private javax.swing.JComboBox jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableContents;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldTitle;
    private javax.swing.JTextField jTextWebPage;
    // End of variables declaration//GEN-END:variables

    @Override
    public WizardPanelNavResult allowNext(String arg, Map map, Wizard wizard)
    {
        WizardPanelNavResult result = WizardPanelNavResult.PROCEED;
        if (this.jTableContents.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/Search").getString("!DEBE_INDICAR_UN_CONTENIDO!"), Search.getDescription(), JOptionPane.ERROR_MESSAGE);
            this.jTableContents.requestFocus();
            result = WizardPanelNavResult.REMAIN_ON_PAGE;
        }
        else
        {
            Object selection = this.jTableContents.getModel().getValueAt(this.jTableContents.getSelectedRow(), 0);
            map.put(CONTENT, selection);
            map.put(WORKSPACE, this.jComboBoxRepositorio.getSelectedItem().toString());
            Search.map = map;
        }
        return result;
    }

    private void addCategory(String repository, CategoryInfo category)
    {
        if (category.childs > 0)
        {
            try
            {
                for (CategoryInfo cat : OfficeApplication.getOfficeApplicationProxy().getCategories(repository, category.UDDI))
                {
                    this.jComboBoxCategory.addItem(cat);
                    addCategory(repository, cat);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
