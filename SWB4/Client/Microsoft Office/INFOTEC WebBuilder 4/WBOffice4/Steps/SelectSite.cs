using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Steps
{
    public partial class SelectSite : TSWizards.BaseInteriorStep
    {
        public static readonly String WEB_PAGE = "WEB_PAGE";
        protected String title;
        protected String description;
        public SelectSite()
        {
            InitializeComponent();            
            foreach (WebSiteInfo site in OfficeApplication.OfficeApplicationProxy.getSites())
            {
                TreeNode siteNode = new TreeNode(site.title, 1, 1);
                siteNode.Tag = site;
                treeView1.Nodes.Add(siteNode);
                addHomePage(siteNode, site);
            }
        }
        public SelectSite(String title,String description)
        {

            InitializeComponent();
            this.title = title;
            this.description = description;
            foreach (WebSiteInfo site in OfficeApplication.OfficeApplicationProxy.getSites())
            {
                TreeNode siteNode=new TreeNode(site.title,1,1);
                siteNode.Tag = site;
                treeView1.Nodes.Add(siteNode);
                addHomePage(siteNode,site);
            }
        }
        private void addHomePage(TreeNode siteNode, WebSiteInfo site)
        {
            WebPageInfo home=OfficeApplication.OfficeApplicationProxy.getHomePage(site);
            TreeNode homeNode = new TreeNode(home.title,1,1);
            homeNode.Tag = home;
            homeNode.ToolTipText = home.description;
            siteNode.Nodes.Add(homeNode);
            if (home.childs > 0)
            {
                TreeNode dummy = new TreeNode("");
                homeNode.Nodes.Add(dummy);
            }
            //addChilds(homeNode, home);
        }
        private void addWebPage(TreeNode pageNode, WebPageInfo page)
        {
            foreach (WebPageInfo childPage in OfficeApplication.OfficeApplicationProxy.getPages(page))
            {
                TreeNode childPageNode = new TreeNode(childPage.title,1,1);
                childPageNode.Tag = childPage;
                childPageNode.ToolTipText = childPage.description;
                pageNode.Nodes.Add(childPageNode);                
                if (childPage.childs > 0)
                {
                    TreeNode dummy = new TreeNode("");
                    childPageNode.Nodes.Add(dummy);
                }
            }
        }

        

        private void treeView1_BeforeExpand(object sender, TreeViewCancelEventArgs e)
        {
            if (e.Node.Nodes.Count == 1 && e.Node.Nodes[0].Text == "" && e.Node.Nodes[0].Tag==null)
            {
                e.Node.Nodes.Clear();
                addWebPage(e.Node, (WebPageInfo)e.Node.Tag);
            }
        }

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            this.toolStripButtonAddPage.Enabled = false;            
            if (e.Node.Tag!=null && e.Node.Tag is WebPageInfo)
            {
                this.toolStripButtonAddPage.Enabled = true;                
            }
        }

        private void toolStripButtonAddPage_Click(object sender, EventArgs e)
        {
            if (this.treeView1.SelectedNode!=null && this.treeView1.SelectedNode.Tag != null && this.treeView1.SelectedNode.Tag is WebPageInfo)
            {
                bool expanded = this.treeView1.SelectedNode.IsExpanded;
                OfficeApplication.CreatePage();
                this.treeView1.SelectedNode.Nodes.Clear();
                addWebPage(this.treeView1.SelectedNode, (WebPageInfo)this.treeView1.SelectedNode.Tag);
                if (expanded)
                {
                    this.treeView1.SelectedNode.Expand();
                }
            }
        }
    }
}
