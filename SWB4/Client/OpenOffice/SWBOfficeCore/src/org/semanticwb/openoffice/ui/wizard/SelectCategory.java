/*
 * SelectCategory.java
 *
 * Created on 3 de junio de 2008, 11:03 AM
 */
package org.semanticwb.openoffice.ui.wizard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.RepositoryInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.ui.dialogs.DialogAddCategory;
import org.semanticwb.openoffice.ui.icons.ImageLoader;

/**
 *
 * @author  victor.lorenzana
 */
public class SelectCategory extends WizardPage
{

    public static Map map = null;
    public static final String CATEGORY_ID = "categoryID";
    public static final String REPOSITORY_ID = "repositoryID";
    private String repositoryID;

    /** Creates new form SelectCategory */
    public SelectCategory()
    {
        initComponents();
        this.getWizardDataMap().remove(CATEGORY_ID);
        this.getWizardDataMap().remove(REPOSITORY_ID);
        loadTree();

    }

    public SelectCategory(String repositoryID)
    {
        initComponents();
        this.repositoryID = repositoryID;
        this.getWizardDataMap().remove(CATEGORY_ID);
        this.getWizardDataMap().remove(REPOSITORY_ID);
        loadTree();
    }

    private void addCategory(RepositoryInfo repository, CategoryNode parent)
    {
        try
        {
            for (CategoryInfo category : OfficeApplication.getOfficeApplicationProxy().getCategories(repository.name, parent.getID()))
            {
                CategoryNode categoryNode = new CategoryNode(category.UDDI, category.title, category.description, repository);
                parent.add(categoryNode);
                if (category.childs > 0)
                {
                    categoryNode.add(new DefaultMutableTreeNode(""));
                }            
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    private void addCategory(RepositoryNode parent)
    {
        try
        {
            for (CategoryInfo category : OfficeApplication.getOfficeApplicationProxy().getCategories(parent.getName()))
            {
                CategoryNode categoryNode = new CategoryNode(category.UDDI, category.title, category.description, parent.repositoryInfo);
                parent.add(categoryNode);
                if (category.childs > 0)
                {
                    categoryNode.add(new DefaultMutableTreeNode(""));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    private void addRepositories(RepositoryNode root)
    {
        try
        {
            for (RepositoryInfo repository : OfficeApplication.getOfficeApplicationProxy().getRepositories())
            {
                boolean showRepository = false;
                if (this.repositoryID == null || repositoryID.equals(repository.name))
                {
                    showRepository = true;
                }
                if (showRepository)
                {
                    RepositoryNode repositoryNode = new RepositoryNode(repository);
                    root.add(repositoryNode);                    
                    addCategory(repositoryNode);
                    jTreeCategory.expandPath(new TreePath(repositoryNode.getPath()));
                }
            }
        }
        catch (Exception wbe)
        {
            String message = wbe.getLocalizedMessage();
            if (wbe.getCause() != null)
            {
                Throwable cause = wbe.getCause();
                message += "\r\n" + cause.getMessage();
            }
            JOptionPane.showMessageDialog(this, message, getDescription(), JOptionPane.OK_OPTION);
            this.setProblem(message);
            return;
        }
    }

    private void loadTree()
    {
        this.jTreeCategory.setCellRenderer(new TreeRender());
        this.jTreeCategory.setEditable(false);
        RepositoryNode root = new RepositoryRootNode();
        DefaultTreeModel model = new DefaultTreeModel(root);
        model.setRoot(root);
        this.jTreeCategory.setModel(model);
        addRepositories(root);        
        jTreeCategory.setRootVisible(false);
        jTreeCategory.expandPath(new TreePath(root.getPath()));

    }

    public static String getDescription()
    {
        return "Ubicación del contenido";
    }

    @Override
    public WizardPanelNavResult allowNext(String arg, Map map, Wizard wizard)
    {
        WizardPanelNavResult result = WizardPanelNavResult.PROCEED;
        if (this.getWizardDataMap().get(CATEGORY_ID) == null || this.getWizardDataMap().get(REPOSITORY_ID) == null)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "¡Debe seleccionar una categoria!", getDescription(), JOptionPane.ERROR_MESSAGE);
            this.jTreeCategory.requestFocus();
            result = WizardPanelNavResult.REMAIN_ON_PAGE;
        }
        RepositoryInfo rep = (RepositoryInfo) this.getWizardDataMap().get(REPOSITORY_ID);
        if (rep.exclusive)
        {
            int res = JOptionPane.showConfirmDialog(this, "¡El repositorio selecionado sólo puede publicar contenidos en el sitio " + rep.siteInfo.title + "!\r\n¿Desea continuar?", getDescription(), JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.NO_OPTION)
            {
                result = WizardPanelNavResult.REMAIN_ON_PAGE;
            }

        }
        else
        {
            int res = JOptionPane.showConfirmDialog(this, "¡El repositorio selecionado permite publicar en cualquier sitio!\r\n¿Desea continuar?", getDescription(), JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.NO_OPTION)
            {
                result = WizardPanelNavResult.REMAIN_ON_PAGE;
            }
        }
        SelectCategory.map = map;
        return result;
    }

    @Override
    public WizardPanelNavResult allowFinish(String arg, Map map, Wizard wizard)
    {
        return this.allowNext(arg, map, wizard);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonAddCategory = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButtonDeletCategory = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeCategory = new javax.swing.JTree();

        setPreferredSize(new java.awt.Dimension(500, 322));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButtonAddCategory.setBackground(new java.awt.Color(255, 255, 255));
        jButtonAddCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/close.png"))); // NOI18N
        jButtonAddCategory.setToolTipText("Agregar categoria");
        jButtonAddCategory.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jButtonAddCategory.setEnabled(false);
        jButtonAddCategory.setFocusable(false);
        jButtonAddCategory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAddCategory.setOpaque(false);
        jButtonAddCategory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddCategoryActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAddCategory);
        jToolBar1.add(jSeparator1);

        jButtonDeletCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/delete.png"))); // NOI18N
        jButtonDeletCategory.setToolTipText("Borrar categoria");
        jButtonDeletCategory.setEnabled(false);
        jButtonDeletCategory.setFocusable(false);
        jButtonDeletCategory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDeletCategory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDeletCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeletCategoryActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonDeletCategory);

        jPanel1.add(jToolBar1);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        jTreeCategory.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeCategoryValueChanged(evt);
            }
        });
        jTreeCategory.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                jTreeCategoryTreeWillExpand(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeCategory);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void jButtonAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddCategoryActionPerformed
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    Object selected = this.jTreeCategory.getLastSelectedPathComponent();
    if (selected != null && selected instanceof CategoryNode)
    {
        CategoryNode categoryNode = (CategoryNode) selected;
        DialogAddCategory addCategory = new DialogAddCategory(categoryNode.getRepository().name, categoryNode.getID());
        addCategory.setVisible(true);
        if (!addCategory.isCancel())
        {
            categoryNode.removeAllChildren();
            addCategory(categoryNode.getRepository(), categoryNode);
        }
    }
    if (selected != null && selected instanceof RepositoryNode)
    {
        RepositoryNode repositoryNode = (RepositoryNode) selected;
        DialogAddCategory addCategory = new DialogAddCategory(repositoryNode.getName());
        addCategory.setVisible(true);
        if (!addCategory.isCancel())
        {
            repositoryNode.removeAllChildren();
            addCategory(repositoryNode);
        }
    }
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}//GEN-LAST:event_jButtonAddCategoryActionPerformed

private void jTreeCategoryValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeCategoryValueChanged
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    Object selected = this.jTreeCategory.getLastSelectedPathComponent();
    this.getWizardDataMap().remove(CATEGORY_ID);
    this.getWizardDataMap().remove(REPOSITORY_ID);
    if (selected != null && selected instanceof RepositoryNode)
    {
        if (selected instanceof RepositoryRootNode)
        {
            this.jButtonAddCategory.setEnabled(false);
        }
        else
        {

            RepositoryNode rep = (RepositoryNode) selected;
            if (rep.getChildCount() == 0)
            {
                JOptionPane.showMessageDialog(this, "¡No existen categorias en este repositorio!\r\nDebe crear una para poder publicar el contenido", getDescription(), JOptionPane.ERROR_MESSAGE);
            }
            this.jButtonAddCategory.setEnabled(true);
        }
    }
    else
    {
        this.jButtonAddCategory.setEnabled(true);
    }
    this.jButtonDeletCategory.setEnabled(false);
    if (selected != null && selected instanceof CategoryNode)
    {
        CategoryNode categoryNode = (CategoryNode) selected;
        String categoryId = categoryNode.getID();

        RepositoryInfo repository = categoryNode.getRepository();
        this.getWizardDataMap().put(CATEGORY_ID, categoryId);
        this.getWizardDataMap().put(REPOSITORY_ID, repository);
        try
        {
            if (OfficeApplication.getOfficeApplicationProxy().canDeleteCategory(repository.name, categoryId))
            {
                this.jButtonDeletCategory.setEnabled(true);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}//GEN-LAST:event_jTreeCategoryValueChanged

private void jButtonDeletCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeletCategoryActionPerformed
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    String categoryId = (String) this.getWizardDataMap().get(CATEGORY_ID);
    RepositoryInfo repository = (RepositoryInfo) this.getWizardDataMap().get(REPOSITORY_ID);
    try
    {
        if (OfficeApplication.getOfficeApplicationProxy().canDeleteCategory(repository.name, categoryId))
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTreeCategory.getLastSelectedPathComponent();
            if (node == null)
            {
                return;
            }
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (node.getParent());
            if (parentNode == null)
            {
                return;//-- remove node --
            }
            parentNode.remove(node);
            ((DefaultTreeModel) jTreeCategory.getModel()).reload(parentNode);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No se puede borrar la categoria por que tiene contenidos", getDescription(), JOptionPane.ERROR | JOptionPane.YES_NO_OPTION);
        }
    }
    catch (Exception e)
    {
        e.printStackTrace(System.out);
    }
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}//GEN-LAST:event_jButtonDeletCategoryActionPerformed

private void jTreeCategoryTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException//GEN-FIRST:event_jTreeCategoryTreeWillExpand
{//GEN-HEADEREND:event_jTreeCategoryTreeWillExpand
    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();
    if (treeNode instanceof CategoryNode && treeNode.getChildCount() == 1 && !(treeNode.getChildAt(0) instanceof CategoryNode))
    {
        CategoryNode categoryNode = (CategoryNode) treeNode;
        categoryNode.removeAllChildren();
        addCategory(categoryNode.getRepository(), categoryNode);
    }
}//GEN-LAST:event_jTreeCategoryTreeWillExpand

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddCategory;
    private javax.swing.JButton jButtonDeletCategory;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTreeCategory;
    // End of variables declaration//GEN-END:variables
}

interface ToolTipTreeNode
{

    public String getToolTipText();
}

class RepositoryRootNode extends RepositoryNode
{

    public RepositoryRootNode()
    {
        super(new RepositoryInfo("Repositorios"));
        repositoryInfo.exclusive = false;
        component.setText(repositoryInfo.name);
        component.setOpaque(true);
    }

    @Override
    public boolean isRoot()
    {
        return true;
    }

    @Override
    public String getToolTipText()
    {
        return "Este no es un repositorio";
    }
}

class RepositoryNode extends DefaultMutableTreeNode implements ToolTipTreeNode
{

    protected JLabel component = new JLabel();
    protected RepositoryInfo repositoryInfo;

    public RepositoryNode(RepositoryInfo repositoryInfo)
    {
        this.repositoryInfo = repositoryInfo;
        component.setText(repositoryInfo.name);
        component.setOpaque(true);
        component.setToolTipText(this.getToolTipText());
    }

    @Override
    public boolean isRoot()
    {
        return false;
    }

    public String getToolTipText()
    {
        if (repositoryInfo.exclusive)
        {
            return "Repositorio exclusivo para el sitio " + repositoryInfo.siteInfo.title;
        }
        else
        {
            return "Repositorio compartido, puede publicar en cualquier sitio";
        }
    }

    public String getName()
    {
        return this.repositoryInfo.name;
    }

    @Override
    public String toString()
    {

        return repositoryInfo.name;
    }

    @Override
    public int hashCode()
    {
        return repositoryInfo.name.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if (obj instanceof RepositoryNode)
        {
            equals = ((RepositoryNode) obj).repositoryInfo.name.equals(this.repositoryInfo.name);
        }
        return equals;
    }

    public JLabel getComponent()
    {
        return component;
    }
}

class CategoryNode extends DefaultMutableTreeNode implements ToolTipTreeNode
{

    private JLabel component = new JLabel();
    private String id;
    private String title;
    private String description;
    private RepositoryInfo repository;

    public CategoryNode(String id, String title, String description, RepositoryInfo repository)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.repository = repository;
        component.setText(title);
        component.setToolTipText(description);
        component.setOpaque(true);
    }

    @Override
    public boolean isRoot()
    {
        return false;
    }

    public RepositoryInfo getRepository()
    {
        return this.repository;
    }

    public String getToolTipText()
    {
        return description;
    }

    @Override
    public String toString()
    {
        this.hashCode();
        return title;
    }

    @Override
    public int hashCode()
    {
        return this.title.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if (obj instanceof CategoryNode)
        {
            equals = ((CategoryNode) obj).title.equals(this.title);
        }
        return equals;
    }

    public String getID()
    {
        return id;
    }

    public String getDescription()
    {
        return this.description;
    }

    public JLabel getComponent()
    {
        return component;
    }
}

class TreeRender extends JPanel implements TreeCellRenderer
{

    public Component getTreeCellRendererComponent(JTree tree, Object object, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        Component component = this;
        tree.setToolTipText("");
        if (object instanceof CategoryNode)
        {
            component = ((CategoryNode) object).getComponent();
            if (hasFocus)
            {
                tree.setToolTipText(((CategoryNode) object).getDescription());
            }
        }
        if (object instanceof RepositoryNode)
        {
            component = ((RepositoryNode) object).getComponent();
        //component.setFont(tree.getFont());
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
                /*URL imageURL=getClass().getResource("/org/semanticwb/openoffice/ui/icons/close.png");
                System.out.println(imageURL.toString());
                javax.swing.ImageIcon icon=new javax.swing.ImageIcon(imageURL);*/
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
