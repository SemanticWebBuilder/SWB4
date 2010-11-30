using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Steps
{
    public class HomeWebPageTreeNode : WebPageTreeNode
    {
        public HomeWebPageTreeNode(WebPageInfo webPageInfo) : base(webPageInfo)
        {
            if (this.WebPageInfo.active)
            {
                this.ImageIndex = 0;
                this.SelectedImageIndex = 0;
            }
            else
            {
                this.ImageIndex = 3;
                this.SelectedImageIndex = 3;
            }
        }
    }
}
