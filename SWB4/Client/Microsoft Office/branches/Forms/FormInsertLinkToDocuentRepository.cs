using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public partial class FormInsertLinkToDocuentRepository : Form
    {
        private OfficeDocument officeDocument;
        public FormInsertLinkToDocuentRepository(OfficeDocument officeDocument)
        {
            InitializeComponent();
            this.officeDocument = officeDocument;
            this.comboBoxSite.Items.Clear();
            foreach (WebSiteInfo website in OfficeApplication.OfficeApplicationProxy.getSites())
            {
                this.comboBoxSite.Items.Add(website);
            }
            if (comboBoxSite.Items.Count > 0)
            {
                comboBoxSite.SelectedIndex = 0;
            }
        }

        private void comboBoxSite_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.comboBoxSite.SelectedItem != null)
            {
                WebSiteInfo site = (WebSiteInfo)this.comboBoxSite.SelectedItem;
                this.comboBoxRepository.Items.Clear();
                SiteInfo siteinfo = new SiteInfo();
                siteinfo.id = site.id;
                foreach (SemanticRepository sr in OfficeApplication.OfficeApplicationProxy.getSemanticRepositories(siteinfo))
                {
                    this.comboBoxRepository.Items.Add(sr);
                }
                if (comboBoxRepository.Items.Count > 0)
                {
                    comboBoxRepository.SelectedIndex = 0;
                }
            }
        }

        private void comboBoxRepository_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.comboBoxRepository.SelectedItem != null)
            {
                this.treeView1.Nodes.Clear();
                SemanticRepository sr = (SemanticRepository)this.comboBoxRepository.SelectedItem;
                WebSiteInfo site = (WebSiteInfo)this.comboBoxSite.SelectedItem;                
                SiteInfo siteinfo = new SiteInfo();
                siteinfo.id = site.id;
                SemanticRepositoryNode root = new SemanticRepositoryNode(sr);
                this.treeView1.Nodes.Add(root);
                foreach(SemanticFolderRepository sf in OfficeApplication.OfficeApplicationProxy.getSemanticFolderRepositories(siteinfo,sr))
                {
                    root.Nodes.Add(new SemanticFolderNode(sf));
                }
            }
        }

        private void treeView1_BeforeExpand(object sender, TreeViewCancelEventArgs e)
        {
            if (e.Node.Nodes.Count == 1 && e.Node is SemanticFolderNode && !(e.Node.Nodes[0] is SemanticFolderNode))
            {
                SemanticFolderNode fnode = (SemanticFolderNode)e.Node;
                e.Node.Nodes.Clear();
                SemanticRepository sr = (SemanticRepository)this.comboBoxRepository.SelectedItem;
                WebSiteInfo site = (WebSiteInfo)this.comboBoxSite.SelectedItem;                
                SiteInfo siteinfo = new SiteInfo();
                siteinfo.id = site.id;
                foreach (SemanticFolderRepository sf in OfficeApplication.OfficeApplicationProxy.getSemanticFolderRepositories(siteinfo, sr,fnode.SemanticFolderRepository))
                {
                    fnode.Nodes.Add(new SemanticFolderNode(sf));
                }
            }
        }

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            listView1.Items.Clear();
            if (e.Node is SemanticFolderNode)
            {
                SemanticFolderNode sfnode = (SemanticFolderNode)e.Node;
                WebSiteInfo site = (WebSiteInfo)this.comboBoxSite.SelectedItem;                
                SiteInfo siteinfo = new SiteInfo();
                siteinfo.id = site.id;
                SemanticRepository sr = (SemanticRepository)this.comboBoxRepository.SelectedItem;
                foreach(SemanticFileRepository semanticfile in OfficeApplication.OfficeApplicationProxy.getSemanticFileRepositories(siteinfo, sr,sfnode.SemanticFolderRepository))
                {
                    listView1.Items.Add(new SemanticFileItem(semanticfile));                       
                    
                }
            }
            if (e.Node is SemanticRepositoryNode)
            {                
                WebSiteInfo site = (WebSiteInfo)this.comboBoxSite.SelectedItem;                
                SiteInfo siteinfo = new SiteInfo();
                siteinfo.id = site.id;
                SemanticRepository sr = (SemanticRepository)this.comboBoxRepository.SelectedItem;
                foreach (SemanticFileRepository semanticfile in OfficeApplication.OfficeApplicationProxy.getSemanticFileRepositories(siteinfo, sr))
                {
                    listView1.Items.Add(new SemanticFileItem(semanticfile));

                }
            }
        }
        private void buttonInsert_Click(object sender, EventArgs e)
        {
            if (this.listView1.SelectedItems.Count == 0 || this.treeView1.SelectedNode == null || this.comboBoxSite.SelectedItem==null)
            {
                MessageBox.Show(this, "¡Debe indicar un archivo!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }            
            WebSiteInfo site = (WebSiteInfo)this.comboBoxSite.SelectedItem;            
            SemanticRepository repository = (SemanticRepository)this.comboBoxRepository.SelectedItem;
            SemanticFileRepository file = ((SemanticFileItem)this.listView1.SelectedItems[0]).SemanticFileRepository;
            String title = file.title;
            if (officeDocument.SelectedText != null && officeDocument.SelectedText != "")
            {
                title = officeDocument.SelectedText;
            }
            String path = "wbrelpath://"+ site.id +"/"+ repository.pageid +"/_rid/"+ repository.resid +"/_mto/3/_act/inline/_mod/getFile/_wst/maximized/"+ file.uuid +"/"+file.name;
            officeDocument.InsertLink(path, title);
            //http://localhost:8080/swb/es/demo/home/_rid/45/_mto/3/_act/inline/_mod/getFile/_wst/maximized/33a923a1-8e02-4123-9cb4-86901fe62ec1/Asley%20005.jpg
        }
    }
}
