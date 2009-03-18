using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.ComponentModel;
using WBOffice4.Interfaces;

namespace WBOffice4.Steps
{
    public class SelectSitePublish : SelectSite
    {        
        public SelectSitePublish() : base()
        {            
            this.ValidateStep+=new System.ComponentModel.CancelEventHandler(SelectSitePublish_ValidateStep);
        }
        public SelectSitePublish(String title, String description) : base(title,description)
        {            
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(SelectSitePublish_ValidateStep);
        }

        private void SelectSitePublish_ValidateStep(object sender, CancelEventArgs e)
        {
            if (this.treeView1.SelectedNode != null && this.treeView1.SelectedNode.Tag != null && this.treeView1.SelectedNode.Tag is WebPageInfo)
            {
                WebPageInfo webpage = this.treeView1.SelectedNode.Tag as WebPageInfo;
                this.Wizard.Data[WEB_PAGE] = webpage;
                String version = this.Wizard.Data[SelectVersionToOpen.VERSION].ToString();
                string repositoryName = this.Wizard.Data[SelectVersionToPublish.REPOSITORY_ID_NAME].ToString();
                string contentID = this.Wizard.Data[SelectVersionToPublish.CONTENT_ID_NAME].ToString();
                if (title == null || description == null)
                {
                    title = this.Wizard.Data[TitleAndDescription.TITLE].ToString();
                    description = this.Wizard.Data[TitleAndDescription.DESCRIPTION].ToString();
                }
                PropertyInfo[] properties = (PropertyInfo[])this.Wizard.Data[ViewProperties.VIEW_PROPERTIES];
                String[] values = (String[])this.Wizard.Data[ViewProperties.VIEW_PROPERTIES_VALUES];
                PortletInfo portletInfo = OfficeApplication.OfficeDocumentProxy.publishToPortletContent(repositoryName, contentID, version, title, description, webpage,properties,values);
                DialogResult res = MessageBox.Show(this, "¿Desea activar el contenido?", this.Wizard.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (res == DialogResult.Yes)
                {
                    OfficeApplication.OfficeDocumentProxy.activatePortlet(portletInfo, true);
                }
                MessageBox.Show(this, "Se ha publicado correctamente el contenido en la página web", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
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
