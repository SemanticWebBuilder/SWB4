using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
using ICSharpCode.SharpZipLib.Zip;
using ICSharpCode.SharpZipLib.Checksums;
using XmlRpcLibrary;
using System.Diagnostics;
using WBOffice4.Interfaces;

namespace WBOffice4.Forms
{
    public partial class FormUpdateContent : Form
    {
        OfficeDocument document;
        public FormUpdateContent(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;
            loadVersions();
        }
        private void loadVersions()
        {
            this.listViewVersions.Items.Clear();
            foreach (VersionInfo version in OfficeApplication.OfficeDocumentProxy.getVersions(document.reporitoryID, document.contentID))
            {
                ListViewItem versionItem = new ListViewItem(version.nameOfVersion);
                String date = String.Format(OfficeApplication.iso8601dateFormat, version.created);
                versionItem.SubItems.Add(date);
                versionItem.SubItems.Add(version.user);
                String published = "No";
                if (version.published)
                {
                    published = "Sí";
                }
                versionItem.SubItems.Add(published);
                this.listViewVersions.Items.Add(versionItem);
                versionItem.Tag = version;
            }
        }


        private void buttonClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            int limit = OfficeApplication.OfficeApplicationProxy.getLimitOfVersions();
            if (limit > 0)
            {
                int versions = OfficeApplication.OfficeDocumentProxy.getNumberOfVersions(this.document.reporitoryID, this.document.contentID);
                if (OfficeApplication.OfficeDocumentProxy.allVersionsArePublished(this.document.reporitoryID, this.document.contentID))
                {
                    if (versions >= limit)
                    {
                        DialogResult resp = MessageBox.Show(this, "¡El limite máximo de versiones es de " + limit + "!\r\nPuede publicar este contenido, debido a que tiene todas las versiones publicadas, pero excederá del límite de versiones\r\n¿Desea continuar?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                        if (resp == DialogResult.No)
                        {
                            return;
                        }
                    }
                }
                else
                {
                    if (versions >= limit)
                    {
                        MessageBox.Show(this, "¡El limite máximo de versiones es de " + limit + "!\r\nSi desea crear una nueva version, debe borrar alguna de las existentes, que no este publicada.", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                        return;
                    }
                }
            }
            this.progressBarSave.Maximum = 1;
            FileInfo zipFile = document.CreateZipFile();
            this.progressBarSave.Value = 1;
            OfficeDocument.OfficeDocumentProxy.Attachments.Add(new Attachment(zipFile, zipFile.Name));
            this.Cursor = Cursors.WaitCursor;
            try
            {

                String name = document.FilePath.Name.Replace(document.DefaultExtension, document.PublicationExtension);
                OfficeDocument.OfficeDocumentProxy.updateContent(document.reporitoryID, document.contentID, name);
                MessageBox.Show(this, "¡Version actualizada!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                loadVersions();
                this.buttonUpdate.Enabled = false;
            }
            catch
            {
                throw;
            }
            finally
            {
                this.Cursor = Cursors.Default;
                zipFile.Delete();
            }
        }

        private void listViewVersions_SelectedIndexChanged(object sender, EventArgs e)
        {            
            this.toolStripButtonDelete.Enabled = false;
            this.toolStripButtonView.Enabled = false;
            if (this.listViewVersions.SelectedItems.Count > 0)
            {
                this.toolStripButtonDelete.Enabled = true;
                this.toolStripButtonView.Enabled = true;
            }
        }

        private void toolStripButtonView_Click(object sender, EventArgs e)
        {
            if (this.listViewVersions.SelectedItems.Count > 0)
            {
                String name = null;
                try
                {
                    String version = this.listViewVersions.SelectedItems[0].Text;
                    name = OfficeApplication.OfficeDocumentProxy.createPreview(this.document.reporitoryID, this.document.contentID, version);
                    String urlproxy = OfficeApplication.OfficeDocumentProxy.WebAddress.ToString();
                    Uri url = new Uri(urlproxy + "?contentId=" + this.document.contentID + "&versionName=" + version + "&repositoryName=" + this.document.reporitoryID + "&name=" + name);
                    String title = OfficeApplication.OfficeDocumentProxy.getTitle(this.document.reporitoryID, this.document.contentID);
                    FormPreview dialogPreview = new FormPreview(url,false,title);
                    dialogPreview.ShowDialog(this);
                }
                catch (Exception ue)
                {
                    Debug.WriteLine(ue.StackTrace);
                }
                finally
                {
                    if (name != null)
                    {
                        try
                        {
                            OfficeApplication.OfficeDocumentProxy.deletePreview(name);
                        }
                        catch (Exception ue)
                        {
                            Debug.WriteLine(ue.StackTrace);
                        }
                    }
                }
            }
        }

        private void toolStripButtonDelete_Click(object sender, EventArgs e)
        {
            if (this.listViewVersions.SelectedItems.Count>0)
            {
                String versionInfo = this.listViewVersions.SelectedItems[0].Text;
                bool published = ((VersionInfo)this.listViewVersions.SelectedItems[0].Tag).published;
                if (published)
                {
                    MessageBox.Show(this, "¡No se puede borrar una versión que ha sido publicada.!\r\nDebe borrar primero la publicación del contenido.", "Borrado de versión de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                try
                {
                    DialogResult res = MessageBox.Show(this, "¿Desea borrar la versión " + versionInfo + "?", "Borrado de versión de contenido", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                    if (res == DialogResult.Yes)
                    {
                        try
                        {
                            this.Cursor = Cursors.WaitCursor;
                            OfficeApplication.OfficeDocumentProxy.deleteVersionOfContent(this.document.reporitoryID, this.document.contentID, versionInfo);
                            loadVersions();
                        }
                        catch (Exception ue)
                        {
                            Debug.WriteLine(ue.StackTrace);
                        }
                        finally
                        {
                            this.Cursor = Cursors.Default;
                        }
                    }
                }
                catch (Exception ue)
                {
                    Debug.WriteLine(ue.StackTrace);
                }
            }
        }
    }
}
