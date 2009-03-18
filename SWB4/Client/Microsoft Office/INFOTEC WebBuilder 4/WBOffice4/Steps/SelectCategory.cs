using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using System.IO;
using System.Diagnostics;
using XmlRpcLibrary;
using WBOffice4.Interfaces;
using WBOffice4.Forms;

namespace WBOffice4.Steps
{
    internal sealed partial class SelectCategory : TSWizards.BaseInteriorStep
    {
        public static readonly String CATEGORY_ID = "CATEGORY_ID";
        public static readonly String REPOSITORY_ID = "REPOSITORY_ID";
        private OfficeDocument document;
        public SelectCategory(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;
        }

        private void New2_ValidateStep(object sender, CancelEventArgs e)
        {
            if (this.Wizard.Data[CATEGORY_ID] == null || this.Wizard.Data[REPOSITORY_ID] == null)
            {
                MessageBox.Show(this, "¡Debe indicar una categoria!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
            }
            RepositoryInfo rep = (RepositoryInfo)this.Wizard.Data[REPOSITORY_ID];
            if (rep.exclusive)
            {
                DialogResult res = MessageBox.Show(this, "¡El repositorio selecionado sólo puede publicar contenidos en el sitio " + rep.siteInfo.title + "!\r\n¿Desea continuar?", this.Wizard.Text,MessageBoxButtons.YesNo,MessageBoxIcon.Question);
                if (res == DialogResult.No)
                {
                    e.Cancel = true;
                }

            }
            else
            {
                DialogResult res = MessageBox.Show(this, "¡El repositorio selecionado permite publicar en cualquier sitio!\r\n¿Desea continuar?", this.Wizard.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (res == DialogResult.No)
                {
                    e.Cancel = true;
                }
            }
        }
        private void loadTree()
        {
            this.treeView1.Nodes.Clear();
            TreeNode root = this.treeView1.Nodes.Add("Repositorios", "Repositorios",0,1);

            try
            {
                foreach (RepositoryInfo repository in OfficeApplication.OfficeApplicationProxy.getRepositories())
                {
                    TreeNode repositoryNode = root.Nodes.Add(repository.name);
                    repositoryNode.Tag = repository;
                    repositoryNode.ImageIndex = 0;
                    repositoryNode.SelectedImageIndex = 1;
                    foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getCategories(repository.name))
                    {
                        TreeNode categoryNode = repositoryNode.Nodes.Add(category.UDDI, category.title,0,1);
                        categoryNode.ToolTipText = category.description;
                        categoryNode.Tag = repository;
                        if (category.childs > 0)
                        {
                            TreeNode dummyNode = categoryNode.Nodes.Add("");                            
                        }                        
                    }
                }
            }
            catch(Exception e)
            {
                Debug.WriteLine(e.Message);
            }
            if (this.treeView1.Nodes.Count > 0)
            {
                this.treeView1.Nodes[0].Expand();
            }

        }
        private void addCategory(RepositoryInfo repository, TreeNode parent)
        {
            try
            {
                foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getCategories(repository.name, parent.Name))
                {
                    TreeNode categoryNode = parent.Nodes.Add(category.UDDI, category.title,0,1);
                    categoryNode.ToolTipText = category.description;
                    categoryNode.Tag = repository;                    
                    if (category.childs > 0)
                    {
                        TreeNode dummyNode = categoryNode.Nodes.Add("");                        
                    }
                }
            }
            catch
            {
            }
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            if (treeView1.SelectedNode != null && treeView1.SelectedNode.Name != null)
            {
                FormAddCategory formAddCategory;
                RepositoryInfo repository;
                if (treeView1.SelectedNode.Tag != null) // CategoryNode
                {
                    formAddCategory = new FormAddCategory(treeView1.SelectedNode.Tag.ToString(), treeView1.SelectedNode.Name);
                    repository = (RepositoryInfo)treeView1.SelectedNode.Tag;
                }
                else // RepositoryNode
                {
                    formAddCategory = new FormAddCategory(treeView1.SelectedNode.Text);
                    repository = (RepositoryInfo)treeView1.SelectedNode.Tag;

                }
                DialogResult res = formAddCategory.ShowDialog();
                if (res == DialogResult.OK)
                {
                    treeView1.SelectedNode.Nodes.Clear();
                    addCategory(repository, treeView1.SelectedNode);
                }

            }
        }

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            this.Wizard.Data[CATEGORY_ID] = null;
            this.Wizard.Data[REPOSITORY_ID] = null;
            this.toolStripButtonDeleteCategory.Enabled = false;
            this.toolStripButtonAddCategory.Enabled = true;
            if (e.Node.Name != null) //The node is diferent to Repositories
            {
                if (e.Node.Tag != null) // Node Category
                {   
                    this.Wizard.Data[CATEGORY_ID] = e.Node.Name;
                    this.Wizard.Data[REPOSITORY_ID] = e.Node.Tag;
                    this.Cursor = Cursors.WaitCursor;
                    try
                    {
                        if (OfficeApplication.OfficeApplicationProxy.canDeleteCategory(this.Wizard.Data[REPOSITORY_ID].ToString(), this.Wizard.Data[CATEGORY_ID].ToString()))
                        {
                            this.toolStripButtonDeleteCategory.Enabled = true;
                        }
                    }
                    catch { }
                    this.Cursor = Cursors.Default;
                }
                else // Node Repository
                {
                    if (e.Node.Nodes.Count == 0)
                    {
                        MessageBox.Show(this, "¡No existen categorias en este repositorio!\r\nDebe crear una para poder publicar el contenido", "Agregar Categoria", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                }
                this.toolStripButtonAddCategory.Enabled = true;
            }
        }

        private void toolStripButtonDeleteCategory_Click(object sender, EventArgs e)
        {
            this.Cursor = Cursors.WaitCursor;
            String categoryId = (String)this.Wizard.Data[CATEGORY_ID];
            RepositoryInfo repository = (RepositoryInfo)this.Wizard.Data[REPOSITORY_ID];
            if (treeView1.SelectedNode != null)
            {
                try
                {
                    if (OfficeApplication.OfficeApplicationProxy.deleteCategory(repository.name, categoryId))
                    {
                        TreeNode parent = treeView1.SelectedNode.Parent;
                        treeView1.SelectedNode.Remove();
                        treeView1.SelectedNode = parent;
                    }
                    else
                    {
                        MessageBox.Show(this, "No se puede borrar la categoria por que tiene contenidos", "Borrado de categoria", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
                catch
                {
                }
            }
            this.Cursor = Cursors.Default;
        }

        private void SelectCategory_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            loadTree();
        }

        private void treeView1_BeforeExpand(object sender, TreeViewCancelEventArgs e)
        {
            if (e.Node.Nodes.Count == 1 && e.Node.Nodes[0].Text == "" && e.Node.Nodes[0].Tag==null)
            {
                this.Cursor = Cursors.WaitCursor;
                RepositoryInfo rep = (RepositoryInfo)e.Node.Tag;
                TreeNode parent = e.Node;
                e.Node.Nodes.Clear();                
                addCategory(rep, e.Node);
                this.Cursor = Cursors.Default;
            }
        }
    }
}
