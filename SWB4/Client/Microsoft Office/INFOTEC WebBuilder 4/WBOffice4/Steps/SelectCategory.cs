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
*  http://www.webbuilder.org.mx 
**/ 
 
﻿using System;
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
        private bool showDefaultRepository;
        public SelectCategory(OfficeDocument document,bool showDefaultRepository)
        {
            InitializeComponent();
            this.document = document;
            this.showDefaultRepository = showDefaultRepository;
        }

        private void New2_ValidateStep(object sender, CancelEventArgs e)
        {
            if (this.Wizard.Data[CATEGORY_ID] == null || this.Wizard.Data[REPOSITORY_ID] == null)
            {
                MessageBox.Show(this, "¡Debe indicar una categoria!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
                return;
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
                    if (!repository.name.Equals("defaultWorkspace@swb"))
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
                    else
                    {
                        if (showDefaultRepository)
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
                }
            }
            catch(Exception e)
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
                foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getCategories(parentcategoryNode.Repository.name,parentcategoryNode.Category.UDDI))
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

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            if (treeView1.SelectedNode is CategoryNode || treeView1.SelectedNode is RepositoryNode)
            {
                FormAddCategory formAddCategory;                
                if (treeView1.SelectedNode is CategoryNode) // CategoryNode
                {
                    CategoryNode categoryNode=(CategoryNode)treeView1.SelectedNode;
                    formAddCategory = new FormAddCategory(categoryNode.Repository.name,categoryNode.Category.UDDI);                    
                    DialogResult res = formAddCategory.ShowDialog();
                    if (res == DialogResult.OK)
                    {
                        treeView1.SelectedNode.Nodes.Clear();
                        addCategory(categoryNode);
                    }
                }
                else
                {
                    RepositoryNode repositoryNode = (RepositoryNode)treeView1.SelectedNode;
                    formAddCategory = new FormAddCategory(repositoryNode.Repository.name);                    
                    DialogResult res = formAddCategory.ShowDialog();
                    if (res == DialogResult.OK)
                    {
                        treeView1.SelectedNode.Nodes.Clear();
                        addCategoryToRepository(repositoryNode);
                    }

                }               

            }
        }

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            this.Wizard.Data[CATEGORY_ID] = null;
            this.Wizard.Data[REPOSITORY_ID] = null;
            this.toolStripButtonDeleteCategory.Enabled = false;
            this.toolStripButtonAddCategory.Enabled = true;
            if (treeView1.SelectedNode is CategoryNode || treeView1.SelectedNode is RepositoryNode)
            {
                if (treeView1.SelectedNode is CategoryNode)
                {
                    CategoryNode cat = (CategoryNode)treeView1.SelectedNode;
                    if (!OfficeApplication.OfficeApplicationProxy.canCreateCategory(cat.Repository.name, cat.Category.UDDI))
                    {
                        this.toolStripButtonAddCategory.Enabled = false;
                        return;
                    }
                    if (!OfficeApplication.OfficeApplicationProxy.canRemoveCategory(cat.Repository.name, cat.Category.UDDI))
                    {
                        this.toolStripButtonDeleteCategory.Enabled = false;
                        return;
                    }
                    this.Wizard.Data[CATEGORY_ID] = ((CategoryNode)treeView1.SelectedNode).Category.UDDI;
                    this.Wizard.Data[REPOSITORY_ID] = ((CategoryNode)treeView1.SelectedNode).Repository;
                    this.Cursor = Cursors.WaitCursor;
                    try
                    {
                        if (OfficeApplication.OfficeApplicationProxy.canDeleteCategory(this.Wizard.Data[REPOSITORY_ID].ToString(), this.Wizard.Data[CATEGORY_ID].ToString()))
                        {
                            this.toolStripButtonDeleteCategory.Enabled = true;
                        }
                    }
                    catch(Exception ue)
                    {
                        Debug.WriteLine(ue.StackTrace);
                    }
                    this.Cursor = Cursors.Default;
                }
                else
                {
                    if (treeView1.SelectedNode is RepositoryNode)
                    {
                        RepositoryNode rep = (RepositoryNode)treeView1.SelectedNode;
                        if (!OfficeApplication.OfficeApplicationProxy.canCreateCategory(rep.Text))
                        {
                            this.toolStripButtonAddCategory.Enabled = false;
                            return;
                        }
                    }
                    if (e.Node.Nodes.Count == 0)
                    {
                        MessageBox.Show(this, "¡No existen categorias en este repositorio!"+"\r\n"+"Debe crear una para poder publicar el contenido", "Agregar Categoria", MessageBoxButtons.OK, MessageBoxIcon.Information);
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
            if (e.Node is CategoryNode && e.Node.Nodes.Count==1 && e.Node.Nodes[0] is DummyNode)
            {
                this.Cursor = Cursors.WaitCursor;
                CategoryNode parent = (CategoryNode)e.Node;
                e.Node.Nodes.Clear();
                addCategory(parent);
                this.Cursor = Cursors.Default;
            }
        }
    }
}
