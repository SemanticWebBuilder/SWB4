using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Steps;
namespace WBOffice4.Controls
{
    public delegate void NodeEvent(TreeNode node);    
    public partial class SelectWebPage : UserControl
    {
        private WebPageTreeNode selectedWebPage;
        public event NodeEvent AddNode;
        public event NodeEvent ClickNode;
        public SelectWebPage()
        {
            InitializeComponent();
            foreach (WebSiteInfo site in OfficeApplication.OfficeApplicationProxy.getSites())
            {
                WebSiteTreeNode siteNode = new WebSiteTreeNode(site);
                this.treeView1.Nodes.Add(siteNode);
                siteNode.AddNode += new NodeEvent(siteNode_onAddNode);                
            }
        }
        public SelectWebPage(WebSiteInfo webSiteInfo)
        {
            InitializeComponent();
            if (webSiteInfo != null)
            {
                foreach (WebSiteInfo site in OfficeApplication.OfficeApplicationProxy.getSites())
                {
                    if (webSiteInfo.id.Equals(site.id))
                    {
                        WebSiteTreeNode siteNode = new WebSiteTreeNode(site);
                        this.treeView1.Nodes.Add(siteNode);
                        siteNode.AddNode += new NodeEvent(siteNode_onAddNode);                        
                    }
                }
            }
            else
            {
                foreach (WebSiteInfo site in OfficeApplication.OfficeApplicationProxy.getSites())
                {                   
                    WebSiteTreeNode siteNode = new WebSiteTreeNode(site);
                    this.treeView1.Nodes.Add(siteNode);
                    siteNode.AddNode += new NodeEvent(siteNode_onAddNode);                    
                }
            }
        }

        void siteNode_onAddNode(TreeNode node)
        {
            if (AddNode != null)
            {
                AddNode(node);
            }
        }
        private void treeView1_BeforeExpand(object sender, TreeViewCancelEventArgs e)
        {
            if (e.Node.Nodes.Count == 1 && !(e.Node.Nodes[0] is HomeWebPageTreeNode) && e.Node is WebSiteTreeNode)
            {
                WebSiteTreeNode site = e.Node as WebSiteTreeNode;
                try
                {
                    site.addHomePage();
                }
                catch { }
                finally { this.Cursor = Cursors.Default; }
            }
            else if (e.Node.Nodes.Count == 1 && !(e.Node.Nodes[0] is WebPageTreeNode)  && e.Node is WebPageTreeNode)
            {
                WebPageTreeNode node = e.Node as WebPageTreeNode;
                this.Cursor = Cursors.WaitCursor;
                try
                {
                    node.loadChilds();
                }
                catch { }
                finally { this.Cursor = Cursors.Default; }
            }
        }

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            selectedWebPage = null;
            if (e.Node is TreeNode)
            {
                if (e.Node is WebPageTreeNode)
                {
                    selectedWebPage = e.Node as WebPageTreeNode;                    
                }
                // fire event
                if (ClickNode != null)
                {
                    ClickNode(e.Node as TreeNode);
                }
            }
        }
        public WebPageTreeNode SelectedWebPage
        {
            get
            {
                return selectedWebPage;
            }
        }
    }
}
