using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using System.Windows.Forms;
using WBOffice4.Interfaces;

namespace WBOffice4.Steps
{
    public class SelectSiteMoveContent : SelectSite
    {
        ResourceInfo resourceInfo;
        public SelectSiteMoveContent(ResourceInfo resourceInfo)
            : base()
        {
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(SelectSite_ValidateStep);
            this.resourceInfo = resourceInfo;
        }        
        private void SelectSite_ValidateStep(object sender, CancelEventArgs e)
        {
            if (this.treeView1.SelectedNode != null && this.treeView1.SelectedNode.Tag != null && this.treeView1.SelectedNode.Tag is WebPageInfo)
            {
                WebPageInfo webpage = this.treeView1.SelectedNode.Tag as WebPageInfo;
                this.Wizard.Data[WEB_PAGE] = webpage;
                OfficeApplication.OfficeDocumentProxy.changeResourceOfWebPage(this.resourceInfo, webpage);                
                this.Wizard.Close();
            }
            else
            {
                MessageBox.Show(this, "¡Debe indicar una página web", "Seleccionar página web", MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
            }

        }
    }
}
