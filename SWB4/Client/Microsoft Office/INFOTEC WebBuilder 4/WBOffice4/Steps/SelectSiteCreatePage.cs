using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.ComponentModel;
using WBOffice4.Interfaces;
namespace WBOffice4.Steps
{
    public class SelectSiteCreatePage: SelectSite
    {
        public SelectSiteCreatePage() : base()
        {
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(SelectSite_ValidateStep);            
        }
        public SelectSiteCreatePage(String title, String description)
            : base(title, description)
        {
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(SelectSite_ValidateStep);
        }
        
        private void SelectSite_ValidateStep(object sender, CancelEventArgs e)
        {
            if (!(this.treeView1.SelectedNode != null && this.treeView1.SelectedNode.Tag != null && this.treeView1.SelectedNode.Tag is WebPageInfo))
            {
                MessageBox.Show(this, "¡Debe indicar una página web", "Seleccionar página web", MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
            }           
        }
    }
}
