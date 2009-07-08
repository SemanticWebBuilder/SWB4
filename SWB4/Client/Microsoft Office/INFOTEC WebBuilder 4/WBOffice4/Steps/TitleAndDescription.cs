using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Diagnostics;
using System.Text;
using System.IO;
using System.Windows.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Utils;
using XmlRpcLibrary;

namespace WBOffice4.Steps
{
    internal sealed partial class TitleAndDescription : TSWizards.BaseInteriorStep
    {
        public static readonly String NODE_TYPE = "NODE_TYPE";
        public static readonly String TITLE = "TITLE";
        public static readonly String DESCRIPTION = "DESCRIPTION";
        public static readonly String CONTENT_ID = "CONTENT_ID";
        OfficeDocument document;
        private bool showType;
        public TitleAndDescription(OfficeDocument document, bool showType)
        {
            InitializeComponent();
            this.document = document;
            this.showType = showType;
            if (!showType)
            {
                this.ComboBoxType.Visible = false;
                this.labelType.Visible = false;
            }
        }

        private void New1_ValidateStep(object sender, CancelEventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxTitle.Text))
            {
                MessageBox.Show(this, "¡De indicar el título!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxTitle.Focus();
                e.Cancel = true;
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxDescription.Text))
            {
                MessageBox.Show(this, "¡De indicar la descripción!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxDescription.Focus();
                e.Cancel = true;
                return;
            }
            if (showType)
            {                
                this.Wizard.Data[TITLE] = this.textBoxTitle.Text;
                this.Wizard.Data[DESCRIPTION] = this.textBoxDescription.Text;
                this.Wizard.Data[NODE_TYPE] = this.ComboBoxType.SelectedItem;
                FileInfo zipFile = null;
                String repositoryName = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
                String title = this.Wizard.Data[TitleAndDescription.TITLE].ToString();
                String description = this.Wizard.Data[TitleAndDescription.DESCRIPTION].ToString();
                ContentType contentType = (ContentType)this.Wizard.Data[TitleAndDescription.NODE_TYPE]; String categoryID = this.Wizard.Data[SelectCategory.CATEGORY_ID].ToString();
                PropertyInfo[] props = OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName, contentType.id);
                if (props == null || props.Length == 0)
                {
                    Object obj = TypeFactory.getObject(props, contentType.title);
                    String[] values = TypeFactory.getValues(props, obj);
                    try
                    {
                        OfficeApplication.OfficeDocumentProxy.validateContentValues(repositoryName, props, values, contentType.id);
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
                        String contentID = openOfficeDocument.save(title, description, repositoryName, categoryID, document.DocumentType.ToString().ToUpper(), contentType.id, name, props, values);
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
                        this.Wizard.Close();
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
            else
            {
                this.Wizard.Data[TITLE] = this.textBoxTitle.Text;
                this.Wizard.Data[DESCRIPTION] = this.textBoxDescription.Text;
            }

        }

        private void TitleAndDescription_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            this.textBoxTitle.Focus();
            if (showType)
            {
                ComboBoxType.Items.Clear();
                try
                {
                    String repository = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
                    foreach (ContentType type in OfficeApplication.OfficeApplicationProxy.getContentTypes(repository))
                    {
                        this.ComboBoxType.Items.Add(type);
                    }
                    if (this.ComboBoxType.Items.Count > 0)
                    {
                        this.ComboBoxType.SelectedIndex = 0;
                    }
                }
                catch (Exception ue)
                {
                    Debug.WriteLine(ue.StackTrace);
                }
                ContentType contentType=(ContentType)this.ComboBoxType.SelectedItem;
                String repositoryName = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
                PropertyInfo[] props = OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName, contentType.id);
                if (props == null || props.Length == 0)
                {
                    this.Wizard.changeToFinish();
                }
                else
                {
                    this.Wizard.changeToNext();
                }

            }
        }
    }
}
