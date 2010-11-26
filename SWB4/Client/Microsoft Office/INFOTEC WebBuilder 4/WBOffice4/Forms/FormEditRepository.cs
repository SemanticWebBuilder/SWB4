using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using XmlRpcLibrary;
using WBOffice4.Interfaces;
using WBOffice4.Forms;
using WBOffice4.Steps;
using System.Collections;
namespace WBOffice4.Forms
{

    public partial class FormEditRepository : Form
    {
        private DocumentType documentType;
        public FormEditRepository(DocumentType documentType)
        {
            InitializeComponent();
            this.documentType = documentType;
            loadTree();
        }
        private void loadTree()
        {
            this.treeView1.Nodes.Clear();
            TreeNode root = this.treeView1.Nodes.Add("Repositorios", "Repositorios", 0, 1);

            try
            {
                foreach (RepositoryInfo repository in OfficeApplication.OfficeApplicationProxy.getRepositories())
                {
                    RepositoryNode repositoryNode = new RepositoryNode(repository);
                    root.Nodes.Add(repositoryNode);
                    foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getCategories(repository.name))
                    {
                        CategoryNode categoryNode = new CategoryNode(category, repository);
                        repositoryNode.Nodes.Add(categoryNode);
                        if (category.childs > 0)
                        {
                            TreeNode dummyNode = new DummyNode();
                            categoryNode.Nodes.Add(dummyNode);
                        }
                    }

                }
            }
            catch (Exception e)
            {
                OfficeApplication.WriteError(e);
            }
            if (this.treeView1.Nodes.Count > 0)
            {
                this.treeView1.Nodes[0].Expand();
            }

        }
        private void addCategory(CategoryNode parentcategoryNode)
        {
            try
            {
                foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getCategories(parentcategoryNode.Repository.name, parentcategoryNode.Category.UDDI))
                {
                    CategoryNode categoryNode = new CategoryNode(category, parentcategoryNode.Repository);
                    parentcategoryNode.Nodes.Add(categoryNode);
                    if (category.childs > 0)
                    {
                        TreeNode dummyNode = new DummyNode();
                        categoryNode.Nodes.Add(dummyNode);
                    }
                }
            }
            catch
            {
            }
        }
        private void addCategoryToRepository(RepositoryNode repository)
        {
            try
            {
                foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getCategories(repository.Repository.name))
                {
                    CategoryNode categoryNode = new CategoryNode(category, repository.Repository);
                    repository.Nodes.Add(categoryNode);
                    if (category.childs > 0)
                    {
                        TreeNode dummyNode = new DummyNode();
                        categoryNode.Nodes.Add(dummyNode);
                    }
                }
            }
            catch
            {
            }
        }

        private void treeView1_BeforeExpand(object sender, TreeViewCancelEventArgs e)
        {
            if (e.Node is CategoryNode && e.Node.Nodes.Count == 1 && e.Node.Nodes[0] is DummyNode)
            {
                this.Cursor = Cursors.WaitCursor;
                CategoryNode parent = (CategoryNode)e.Node;
                e.Node.Nodes.Clear();
                addCategory(parent);
                this.Cursor = Cursors.Default;
            }
        }

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            this.toolStripButtonDelete.Enabled = false;
            this.toolStripButtonSee.Enabled = false;
            this.listView1.Items.Clear();
            if (this.treeView1.SelectedNode != null && this.treeView1.SelectedNode is CategoryNode)
            {
                CategoryNode node = this.treeView1.SelectedNode as CategoryNode;
                this.toolStripButtonDelete.Enabled = true;
                this.Cursor = Cursors.WaitCursor;
                try
                {
                    loadContens(node,new ContentTitleComparator());
                }
                finally
                {
                    this.Cursor = Cursors.Default;
                }
            }
        }
        private void loadContens(CategoryNode category,IComparer<ContentInfo> comparer)
        {
            this.listView1.Items.Clear();
            String id = category.Category.UDDI;
            String type = "*";
            ContentInfo[] resp = OfficeApplication.OfficeApplicationProxy.search(category.Repository.name, "", "", category.Category.UDDI, type, documentType.ToString().ToUpper());
            Array.Sort(resp, comparer);
            foreach (ContentInfo content in resp)
            {
                ListViewItem item = new ListViewItem(content.title);
                item.Tag = content;
                item.SubItems.Add(content.descripcion);
                String date = String.Format(OfficeApplication.iso8601dateFormat, content.created);
                item.SubItems.Add(date);                
                this.listView1.Items.Add(item);
            }
        }

        private void buttonClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.toolStripButtonDelete.Enabled = false;
            this.toolStripButtonSee.Enabled = false;
            if (this.listView1.SelectedItems.Count > 0)
            {
                this.toolStripButtonDelete.Enabled = true;
                this.toolStripButtonSee.Enabled = true;
            }
        }

        private void toolStripButtonSee_Click(object sender, EventArgs e)
        {
            if (this.listView1.SelectedItems.Count > 0 && this.treeView1.SelectedNode != null && this.treeView1.SelectedNode is CategoryNode)
            {
                CategoryNode category = this.treeView1.SelectedNode as CategoryNode;
                ListViewItem selected = this.listView1.SelectedItems[0];
                ContentInfo content = selected.Tag as ContentInfo;
                string repository = category.Repository.name;
                VersionInfo[] versions = OfficeDocument.OfficeDocumentProxy.getVersions(category.Repository.name, content.id);
                VersionInfo version = versions[versions.Length - 1]; // last version
                String name = null;
                try
                {
                    name = OfficeApplication.OfficeDocumentProxy.createPreview(repository, version.contentId, version.nameOfVersion, this.documentType.ToString());
                    String urlproxy = OfficeApplication.OfficeDocumentProxy.WebAddress.ToString();
                    if (!urlproxy.EndsWith("/gtw"))
                    {
                        if (!urlproxy.EndsWith("/"))
                        {
                            urlproxy += "/";
                        }
                        if (!urlproxy.EndsWith("gtw"))
                        {
                            urlproxy += "gtw";
                        }
                    }
                    Uri url = new Uri(urlproxy + "?contentId=" + version.contentId + "&versionName=" + version.nameOfVersion + "&repositoryName=" + repository + "&name=" + name + "&type=" + documentType.ToString());
                    String title = OfficeApplication.OfficeDocumentProxy.getTitle(repository, version.contentId);
                    FormPreview formPreview = new FormPreview(url, false, title);
                    formPreview.ShowDialog(this);
                }
                finally
                {
                    if (name != null)
                    {
                        OfficeApplication.OfficeDocumentProxy.deletePreview(name);
                    }
                }
            }

        }

        private void toolStripButtonDelete_Click(object sender, EventArgs e)
        {
            if (this.listView1.SelectedItems.Count > 0 && this.treeView1.SelectedNode != null && this.treeView1.SelectedNode is CategoryNode)
            {
                CategoryNode category = this.treeView1.SelectedNode as CategoryNode;
                ListViewItem selected = this.listView1.SelectedItems[0];
                ContentInfo content = selected.Tag as ContentInfo;
                DialogResult res = MessageBox.Show(this, "¿Desea borrar el contenido " + content.title + "?", "Borrado de contenido", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (res == DialogResult.Yes)
                {
                    try
                    {
                        this.Cursor = Cursors.WaitCursor;
                        ResourceInfo[] resources = OfficeDocument.OfficeDocumentProxy.listResources(category.Repository.name, content.id);
                        if (resources != null && resources.Length > 0)
                        {
                            this.Cursor = Cursors.Default;
                            MessageBox.Show(this, "El contenido no puede ser borrado, ya que esta publicado en por lo menos una página web", "Contenido no puede ser borrado", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            return;
                        }
                        OfficeDocument.OfficeDocumentProxy.delete(category.Repository.name, content.id);
                        selected.Remove();
                        loadContens(category,new ContentTitleComparator());
                        this.toolStripButtonDelete.Enabled = false;
                        this.toolStripButtonSee.Enabled = false;
                        if (this.treeView1.SelectedNode is CategoryNode)
                        {
                            this.toolStripButtonDelete.Enabled = true;
                            if (this.listView1.SelectedItems.Count > 0)
                            {
                                this.toolStripButtonSee.Enabled = true;
                            }
                            else
                            {
                                this.toolStripButtonSee.Enabled = false;
                            }
                        }

                    }
                    finally
                    {
                        this.Cursor = Cursors.Default;
                    }
                }
            }
            if (this.listView1.SelectedItems.Count == 0 && this.treeView1.SelectedNode != null && this.treeView1.SelectedNode is CategoryNode)
            {
                CategoryNode category = this.treeView1.SelectedNode as CategoryNode;
                DialogResult res = MessageBox.Show(this, "¿Desea borrar la categoria " + category.Category.title + "?", "Borrado de categoria", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (res == DialogResult.Yes)
                {
                    this.Cursor = Cursors.WaitCursor;
                    try
                    {
                        if (OfficeApplication.OfficeApplicationProxy.deleteCategory(category.Repository.name, category.Category.UDDI))
                        {
                            TreeNode parent = treeView1.SelectedNode.Parent;
                            treeView1.SelectedNode.Remove();
                            treeView1.SelectedNode = parent;
                        }
                        else
                        {
                            this.Cursor = Cursors.Default;
                            MessageBox.Show(this, "No se puede borrar la categoria por que tiene contenidos", "Borrado de categoria", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }
                    finally
                    {
                        this.Cursor = Cursors.Default;
                    }
                }
            }
        }

        private void listView1_ColumnClick(object sender, ColumnClickEventArgs e)
        {
            if(this.treeView1.SelectedNode!=null && this.treeView1.SelectedNode is CategoryNode && this.listView1.Items.Count>0)
            {
                CategoryNode category=this.treeView1.SelectedNode as CategoryNode;
                try
                {
                    this.Cursor = Cursors.WaitCursor;
                    if (e.Column == 0)
                    {
                        ContentTitleComparator comparator = new ContentTitleComparator();
                        loadContens(category,comparator);
                    }
                    if (e.Column == 2)
                    {
                        ContentDateComparator comparator = new ContentDateComparator();
                        loadContens(category, comparator);
                    }
                }
                finally
                {
                    this.Cursor = Cursors.Default;
                }
            }
            
        }

    }
    class ContentTitleComparator : IComparer<ContentInfo>
    {
        public int Compare(ContentInfo x, ContentInfo y)
        {
            return x.title.CompareTo(y.title);
        }
    }
    class ContentDateComparator : IComparer<ContentInfo>
    {
        public int Compare(ContentInfo x, ContentInfo y)
        {
            return x.created.CompareTo(y.created);
        }
    }
    
}
