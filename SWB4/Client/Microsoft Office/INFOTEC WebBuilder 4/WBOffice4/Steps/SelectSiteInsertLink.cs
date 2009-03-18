using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Steps
{
    public class SelectSiteInsertLink : SelectSite
    {
        OfficeDocument document;
        public SelectSiteInsertLink(OfficeDocument document)
            : base()
        {
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(SelectSite_ValidateStep);
            this.document = document;
        }        
        private void SelectSite_ValidateStep(object sender, CancelEventArgs e)
        {
            if (!(this.treeView1.SelectedNode != null && this.treeView1.SelectedNode.Tag != null && this.treeView1.SelectedNode.Tag is WebPageInfo))
            {
                MessageBox.Show(this, "¡Debe indicar una página web", "Seleccionar página web", MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
            }
            else
            {
                WebPageInfo webpage = this.treeView1.SelectedNode.Tag as WebPageInfo;
                Uri address=OfficeApplication.OfficeApplicationProxy.WebAddress;
                String host;
                if (address.Port == 80)
                {
                    host = address.Host;
                }
                else
                {
                    host = address.Host + ":" + address.Port;
                }
                Uri uri = new Uri(address.Scheme + "://" + host + webpage.url);
                String text = this.Wizard.Data[SelectTitle.TITLE].ToString();
                document.InsertLink(uri.ToString(), text);
            }

        }
    }
}
