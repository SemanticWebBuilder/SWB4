using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Controls;
namespace WBOffice4.Steps
{
    public  class WebPageTreeNode : TreeNode
    {
        public event NodeEvent onAddNode;
        private WebPageInfo webPageInfo;
        public WebPageTreeNode(WebPageInfo webPageInfo)
        {
            this.webPageInfo = webPageInfo;
            this.Text = webPageInfo.title;
            this.Tag = webPageInfo;            
            if (webPageInfo.active)
            {
                this.ImageIndex = 2;
                this.SelectedImageIndex = 2;            
            }
            else
            {
                this.ImageIndex = 4;
                this.SelectedImageIndex = 4;            
            }            
            this.Tag = webPageInfo;
            this.ToolTipText = webPageInfo.description;            
            if (webPageInfo.childs > 0)
            {
                TreeNode dummy = new TreeNode("");
                Nodes.Add(dummy);
            }            
        }
        public void loadChilds()
        {
            if (this.Nodes.Count == 1 && !(this.Nodes[0] is WebPageTreeNode))
            {
                ReLoadChilds();
            }
        }
        public void ReLoadChilds()
        {            
            this.Nodes.Clear();
            foreach (WebPageInfo childPage in OfficeApplication.OfficeApplicationProxy.getPages(webPageInfo))
            {
                WebPageTreeNode childTreeNode = new WebPageTreeNode(childPage);
                this.Nodes.Add(childTreeNode);
                if (onAddNode != null)
                {
                    onAddNode(childTreeNode);
                }
                childTreeNode.onAddNode += new NodeEvent(childTreeNode_onAddNode);
            }            
        }

        void childTreeNode_onAddNode(TreeNode childTreeNode)
        {
            if (onAddNode != null)
            {
                onAddNode(childTreeNode);
            }
        }
        public WebPageInfo WebPageInfo
        {
            get
            {
                return webPageInfo;
            }
        }
    }
}
