using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Controls;
namespace WBOffice4.Steps
{
    public class WebSiteTreeNode : TreeNode
    {
        public event NodeEvent AddNode;
        private WebSiteInfo webSiteInfo;
        public WebSiteTreeNode(WebSiteInfo webSiteInfo)
        {
            this.webSiteInfo = webSiteInfo;
            this.Text = webSiteInfo.title;
            this.Tag = webSiteInfo;
            this.SelectedImageIndex = 1;
            this.ImageIndex = 1;
            TreeNode dummy = new TreeNode("");
            Nodes.Add(dummy);
        }
        public void addHomePage()
        {
            this.Nodes.Clear();
            WebPageInfo home = OfficeApplication.OfficeApplicationProxy.getHomePage(webSiteInfo);
            HomeWebPageTreeNode homeNode = new HomeWebPageTreeNode(home);
            this.Nodes.Add(homeNode);
            if (AddNode != null)
            {
                AddNode(homeNode);
            }
            homeNode.onAddNode += new NodeEvent(homeNode_onAddNode);
        }

        void homeNode_onAddNode(TreeNode node)
        {
            if (AddNode != null)
            {
                AddNode(node);
            }
        }
        public WebSiteInfo WebSiteInfo
        {
            get
            {
                return webSiteInfo;
            }
        }
    }
}
