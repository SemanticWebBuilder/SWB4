using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WBOffice4.Interfaces;
using System.Windows.Forms;
using System.ComponentModel;
namespace WBOffice4.Steps
{
    public class SelectSiteToOpen : SelectSite
    {
        private DocumentType type;
        private WebSiteInfo siteInfo;
        public SelectSiteToOpen(DocumentType type, WebSiteInfo siteInfo)
            : base(false, siteInfo)
        {
            this.type = type;
            this.siteInfo = siteInfo;
            this.ValidateStep+=new CancelEventHandler(SelectSiteToOpen_ValidateStep);
        }
        private void SelectSiteToOpen_ValidateStep(object sender, CancelEventArgs e)
        {
            if (selectWebPage.SelectedWebPage!=null)
            {
                WebPageInfo webpage = selectWebPage.SelectedWebPage.WebPageInfo;
                if (!OfficeApplication.OfficeDocumentProxy.canPublishToResourceContent(type.ToString(), webpage))
                {
                    MessageBox.Show(this, "No tiene permisos para abrir contenidos en esta página", this.Wizard.Title, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    e.Cancel = true;
                    return;
                }
                this.Wizard.Data[WEB_PAGE] = webpage;
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
