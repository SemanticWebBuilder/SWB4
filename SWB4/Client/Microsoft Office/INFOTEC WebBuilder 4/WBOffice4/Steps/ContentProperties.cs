using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.IO;
using System.Windows.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Utils;
using XmlRpcLibrary;

namespace WBOffice4.Steps
{
    public partial class ContentProperties : TSWizards.BaseInteriorStep
    {
        private OfficeDocument document;
        private Object obj;
        private PropertyGrid grid = new PropertyGrid();
        public static readonly String CONTENT_PROPERTIES = "CONTENT_PROPERTIES";
        public ContentProperties(OfficeDocument document)
        {
            InitializeComponent();
            grid.ToolbarVisible = false;
            grid.Dock = DockStyle.Fill;
            this.Controls.Add(grid);
            this.document=document;
            
        }

        private void ContentProperties_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            if (obj == null)
            {
                String repositoryName = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
                ContentType type = ((ContentType)this.Wizard.Data[TitleAndDescription.NODE_TYPE]);
                PropertyInfo[] props = OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName, type.id);
                obj = TypeFactory.getObject(props, type.title);
            }
            this.grid.SelectedObject = obj;
        }

        private void ContentProperties_ValidateStep(object sender, CancelEventArgs e)
        {
            FileInfo zipFile = null;
            String title = this.Wizard.Data[TitleAndDescription.TITLE].ToString();
            String description = this.Wizard.Data[TitleAndDescription.DESCRIPTION].ToString();
            String categoryID = this.Wizard.Data[SelectCategory.CATEGORY_ID].ToString();
            String repositoryName = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
            ContentType contentType = (ContentType)this.Wizard.Data[TitleAndDescription.NODE_TYPE];
            PropertyInfo[] properties = OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName, contentType.id);
            String[] values = TypeFactory.getValues(properties, obj);
            try
            {
                OfficeApplication.OfficeDocumentProxy.validateContentValues(repositoryName,properties, values, contentType.id);
            }
            catch (Exception ue)
            {
                MessageBox.Show(this, ue.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            this.Wizard.SetProgressBarInit(3, 1, "Preparando documento para publicar...");
            try
            {
                zipFile = document.CreateZipFile();
                this.Wizard.SetProgressBarInit(3, 2, "Publicando Documento...");
                IOfficeDocument openOfficeDocument = OfficeDocument.OfficeDocumentProxy;
                openOfficeDocument.Attachments.Add(new Attachment(zipFile, zipFile.Name));                
                String name = document.FilePath.Name.Replace(document.DefaultExtension, document.PublicationExtension);                
                String contentID = openOfficeDocument.save(title, description, repositoryName, categoryID, document.DocumentType.ToString().ToUpper(), contentType.id, name, properties, values);
                this.Wizard.Data[TitleAndDescription.CONTENT_ID] = contentID;        


                document.SaveContentProperties(contentID, repositoryName);
                DialogResult res = MessageBox.Show(this, "¿Desea publicar el contenido en una página web?", this.Wizard.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                this.Wizard.SetProgressBarEnd();
                if (OfficeApplication.MenuListener != null)
                {
                    OfficeApplication.MenuListener.DocumentPublished();
                }
                if (res == DialogResult.Yes)
                {
                    document.Publish(title, description);
                }
            }
            finally
            {
                if (zipFile != null && zipFile.Exists)
                {
                    zipFile.Delete();
                }
            }
        }
    }
}
