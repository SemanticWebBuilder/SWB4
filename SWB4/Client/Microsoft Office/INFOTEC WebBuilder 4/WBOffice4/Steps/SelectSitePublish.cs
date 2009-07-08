using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.ComponentModel;
using WBOffice4.Interfaces;
using WBOffice4.Forms;

namespace WBOffice4.Steps
{
    public class SelectSitePublish : SelectSite
    {
        private OfficeDocument document;
        public SelectSitePublish(OfficeDocument document)
            : base()
        {            
            this.ValidateStep+=new System.ComponentModel.CancelEventHandler(SelectSitePublish_ValidateStep);
            this.document = document;
        }        
        public SelectSitePublish(String title, String description,OfficeDocument document)
            : base(title, description)
        {
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(SelectSitePublish_ValidateStep);
            this.document = document;
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
                PropertyInfo[] properties = OfficeApplication.OfficeDocumentProxy.getResourceProperties(document.reporitoryID, document.contentID);
                String[] values = null;
                if (properties == null || properties.Length == 0)
                {
                    values = new String[0];
                }
                else
                {
                    values = (String[])this.Wizard.Data[ViewProperties.VIEW_PROPERTIES_VALUES];
                }
                ResourceInfo portletInfo = OfficeApplication.OfficeDocumentProxy.publishToResourceContent(repositoryName, contentID, version, title, description, webpage,properties,values);
                if (OfficeApplication.OfficeDocumentProxy.needsSendToPublish(portletInfo))
                {
                    DialogResult res = MessageBox.Show(this, "Para activar el contenido, se necesita que sea autorizado primero\r\n¿Desea enviar el contenido a autorizar?", this.Wizard.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                    if (res == DialogResult.Yes)
                    {
                        FormSendToAutorize formSendToAutorize = new FormSendToAutorize(portletInfo);
                        formSendToAutorize.ShowDialog();
                        if (formSendToAutorize.DialogResult == DialogResult.OK)
                        {
                            OfficeApplication.OfficeDocumentProxy.sendToAuthorize(portletInfo, formSendToAutorize.pflow, formSendToAutorize.textBoxMessage.Text);
                        }
                    }
                    else
                    {
                        MessageBox.Show(this, "Para activar el conteneido deberá enviar primero esta publicación a autorizar", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                }
                else
                {
                    DialogResult res = MessageBox.Show(this, "¿Desea activar el contenido?", this.Wizard.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                    if (res == DialogResult.Yes)
                    {
                        OfficeApplication.OfficeDocumentProxy.activateResource(portletInfo, true);
                        MessageBox.Show(this, "Se ha publicado correctamente el contenido en la página web", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                }
                
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
