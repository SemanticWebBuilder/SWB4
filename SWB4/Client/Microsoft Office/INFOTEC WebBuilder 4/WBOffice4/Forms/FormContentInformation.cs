using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Controls;
using WBOffice4.Interfaces;
using WBOffice4.Utils;

namespace WBOffice4.Forms
{
    public partial class FormContentInformation : Form
    {
        private Object obj;
        private PropertyGrid grid = new PropertyGrid();
        //private listViewPages listViewPages;
        private String repositoryName, contentID;
        private OfficeDocument document;
        private ContentType contentType;
        public FormContentInformation(String repositoryName, String contentID, OfficeDocument document)
        {
            InitializeComponent();
            this.repositoryName = repositoryName;
            this.contentID = contentID;
            this.document = document;
            

            this.textBoxTitle.Text = OfficeApplication.OfficeDocumentProxy.getTitle(repositoryName, contentID);
            this.textBoxDescription.Text = OfficeApplication.OfficeDocumentProxy.getDescription(repositoryName, contentID);
            DateTime date = OfficeApplication.OfficeDocumentProxy.getLastUpdate(repositoryName, contentID);
            this.labelLasUpdate.Text = date.ToShortDateString() + " " + date.ToShortTimeString();
            this.comboBoxVersiones.Items.Clear();
            loadCategories();
            CategoryInfo actualCategory = OfficeApplication.OfficeDocumentProxy.getCategoryInfo(repositoryName, contentID);
            this.comboBoxVersiones.SelectedItem = actualCategory;
            loadVersions();
            loadPorlets();
            loadProperties();            
           
        }
        private void loadProperties()
        {
            grid.ToolbarVisible = false;
            grid.Dock = DockStyle.Fill;            
            String type = OfficeApplication.OfficeDocumentProxy.getNameOfContent(repositoryName, contentID);

            foreach (ContentType contentType in OfficeApplication.OfficeApplicationProxy.getContentTypes(repositoryName))
            {
                if (contentType.id == type)
                {
                    this.contentType = contentType;
                    PropertyInfo[] props = OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName, contentType.id);
                    obj = TypeFactory.getObject(props, contentType.title);
                    foreach (PropertyInfo prop in props)
                    {
                        String value=OfficeApplication.OfficeDocumentProxy.getContentProperty(prop, repositoryName, contentID);
                        TypeFactory.setValue(prop, obj, value);
                    }
                    this.grid.SelectedObject = obj;
                }
            }
            tabPageProperties.Controls.Add(grid);
        }
        private void loadPorlets()
        {
            listViewPages.Items.Clear();
            foreach (PortletInfo portletInfo in OfficeApplication.OfficeDocumentProxy.listPortlets(repositoryName, contentID))
            {
                VersionInfo selected = new VersionInfo();
                selected.nameOfVersion = portletInfo.version;
                ListViewItem item = new ListViewItem(portletInfo.title);
                item.SubItems.Add(portletInfo.page.title);
                item.SubItems.Add(portletInfo.page.site.title);
                item.SubItems.Add(portletInfo.active.ToString());
                String version = portletInfo.version;
                if (portletInfo.version == "")
                {
                    version = "Mostrar la última version";
                }
                item.SubItems.Add(version);
                item.Tag = portletInfo;
                listViewPages.Items.Add(item);
            }
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
                this.listViewVersions.Items.Add(versionItem);
                versionItem.Tag = version;
            }
        }
        public void loadCategories()
        {

            this.comboBoxVersiones.Items.Clear();
            foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getAllCategories(repositoryName))
            {
                this.comboBoxVersiones.Items.Add(category);
            }
        }
        private void buttonClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void toolStripButtonEdit_Click(object sender, EventArgs e)
        {
            if (this.listViewPages.SelectedItems.Count > 0 && this.listViewPages.SelectedItems[0].Tag is PortletInfo)
            {
                PortletInfo portletInfo = (PortletInfo)this.listViewPages.SelectedItems[0].Tag;
                FormEditPorlet formEditPorlet = new FormEditPorlet(portletInfo, repositoryName, contentID);
                formEditPorlet.ShowDialog();
            }
        }

        private void listViewVersions_SelectedIndexChanged(object sender, EventArgs e)
        {
            toolStripButtonViewVersion.Enabled = true;
            toolStripButtonDeleteVersion.Enabled = true;
        }

        private void toolStripButtonViewVersion_Click(object sender, EventArgs e)
        {
            if (this.listViewVersions.SelectedItems.Count > 0 && this.listViewVersions.SelectedItems[0].Tag is VersionInfo)
            {
                VersionInfo version = (VersionInfo)this.listViewVersions.SelectedItems[0].Tag;
                String name = null;
                try
                {
                    name = OfficeApplication.OfficeDocumentProxy.createPreview(repositoryName, version.contentId, version.nameOfVersion);
                    String urlproxy = OfficeApplication.OfficeDocumentProxy.WebAddress.ToString();
                    Uri url = new Uri(urlproxy + "?contentId=" + version.contentId + "&versionName=" + version.nameOfVersion + "&repositoryName=" + repositoryName + "&name=" + name);
                    String title = OfficeApplication.OfficeDocumentProxy.getTitle(repositoryName, version.contentId);
                    FormPreview formPreview = new FormPreview(url, false,title);

                    formPreview.ShowDialog(this);
                }
                finally
                {
                    if (name != null)
                    {
                        OfficeApplication.OfficeDocumentProxy.deletePreview(name);
                    }
                }
            }
        }

        private void toolStripButtonSave_Click(object sender, EventArgs e)
        {
            document.SaveToSite();
        }

        private void toolStripButtonSeePage_Click(object sender, EventArgs e)
        {
            if (this.listViewPages.SelectedItems.Count > 0 && this.listViewPages.SelectedItems[0].Tag is PortletInfo)
            {
                PortletInfo portletInfo = (PortletInfo)this.listViewPages.SelectedItems[0].Tag;
                Uri uri = OfficeApplication.OfficeDocumentProxy.WebAddress;
                Uri url = new Uri(uri.Scheme + "://" + uri.Host + ":" + uri.Port + portletInfo.page.url);
                FormPreview preview = new FormPreview(url,portletInfo.title);
                preview.ShowDialog(this);
            }
        }

        private void toolStripButtonDeletePage_Click(object sender, EventArgs e)
        {
            if (this.listViewPages.SelectedItems.Count > 0 && this.listViewPages.SelectedItems[0].Tag is PortletInfo)
            {
                PortletInfo portletInfo = (PortletInfo)this.listViewPages.SelectedItems[0].Tag;
                OfficeApplication.OfficeDocumentProxy.deletePortlet(portletInfo);
            }
        }

        private void listViewPages_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.toolStripButtonEdit.Enabled = false;
            this.toolStripButtonSeePage.Enabled = false;
            this.toolStripButtonDeletePage.Enabled = false;
            if (this.listViewPages.SelectedItems.Count > 0)
            {
                this.toolStripButtonEdit.Enabled = true;
                this.toolStripButtonSeePage.Enabled = true;
                this.toolStripButtonDeletePage.Enabled = true;
            }
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxTitle.Text.Trim()))
            {
                MessageBox.Show(this, "¡Debe indicar un título de contenido!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxTitle.Focus();
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxDescription.Text.Trim()))
            {
                MessageBox.Show(this, "¡Debe indicar una descripción de contenido!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxTitle.Focus();
                return;
            }
            PropertyInfo[] props = null;
            String[] values = null;
            if (obj != null && contentType!=null)
            {
                props=OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName,contentType.id);
                values=TypeFactory.getValues(props, obj);
                try
                {
                    OfficeApplication.OfficeDocumentProxy.validateContentValues(repositoryName, props, values, this.contentType.id);
                }
                catch (Exception ue)
                {
                    MessageBox.Show(this, ue.Message, this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    tabPageProperties.Focus();
                    return;
                }
            }
            try
            {
                this.Cursor = Cursors.WaitCursor;
                String oldTitle = OfficeApplication.OfficeDocumentProxy.getTitle(repositoryName, contentID);
                if (!oldTitle.Equals(textBoxTitle.Text))
                {
                    OfficeApplication.OfficeDocumentProxy.setTitle(repositoryName, contentID, this.textBoxTitle.Text);
                }
                String oldDescription = OfficeApplication.OfficeDocumentProxy.getDescription(repositoryName, contentID);
                if (!oldDescription.Equals(this.textBoxDescription.Text))
                {
                    OfficeApplication.OfficeDocumentProxy.setDescription(repositoryName, contentID, this.textBoxDescription.Text);
                }
                if (props != null && values != null)
                {
                    OfficeApplication.OfficeDocumentProxy.setContentProperties(repositoryName, contentID, props, values);
                }
                CategoryInfo oldCategory = OfficeApplication.OfficeDocumentProxy.getCategoryInfo(repositoryName, contentID);
                CategoryInfo newCategory = (CategoryInfo)this.comboBoxVersiones.SelectedItem;
                if (!oldCategory.Equals(newCategory))
                {
                    OfficeApplication.OfficeDocumentProxy.changeCategory(repositoryName, contentID, newCategory.UDDI);
                }
                DateTime date = OfficeApplication.OfficeDocumentProxy.getLastUpdate(repositoryName, contentID);
                this.labelLasUpdate.Text = date.ToShortDateString() + " " + date.ToShortTimeString();
                loadPorlets();
                loadVersions();
                MessageBox.Show(this, "¡Se han realizado correctamente los cambios!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        private void toolStripButtonDeleteVersion_Click(object sender, EventArgs e)
        {
            if (this.listViewVersions.SelectedItems.Count > 0)
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

        private void toolStripButtonPublish_Click(object sender, EventArgs e)
        {
            document.Publish();
        }
        
    }
}

