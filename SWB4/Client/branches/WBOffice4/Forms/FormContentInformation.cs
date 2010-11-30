/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.webbuilder.org.mx 
**/ 
 
﻿using System;
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
                       
            String type = OfficeApplication.OfficeDocumentProxy.getNameOfContent(repositoryName, contentID);

            foreach (ContentType contentType in OfficeApplication.OfficeApplicationProxy.getContentTypes(repositoryName))
            {
                if (contentType.id == type)
                {
                    this.contentType = contentType;
                    PropertyInfo[] props = OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName, contentType.id);
                    this.propertyEditor1.Properties = props;
                    if (props == null || props.Length == 0)
                    {
                        this.tabControl1.Controls.Remove(this.tabPageProperties);
                    }
                    else
                    {
                        String[] values = new String[props.Length];
                        int i = 0;
                        foreach (PropertyInfo prop in props)
                        {
                            String value = OfficeApplication.OfficeDocumentProxy.getContentProperty(prop, repositoryName, contentID);
                            values[i] = value;
                            i++;
                        }
                        this.propertyEditor1.Values = values;    
                    }
                    

                                    
                }
            }
            
        }
        private void loadPorlets()
        {
            this.toolStripButtonEdit.Enabled = false;
            this.toolStripButtonSeePage.Enabled = false;
            this.toolStripButtonDeletePage.Enabled = false;
            listViewPages.Items.Clear();
            foreach (ResourceInfo portletInfo in OfficeApplication.OfficeDocumentProxy.listResources(repositoryName, contentID))
            {
                VersionInfo selected = new VersionInfo();
                selected.nameOfVersion = portletInfo.version;
                ListViewItem item = new ListViewItem(portletInfo.title);
                item.SubItems.Add(portletInfo.page.site.title);
                item.SubItems.Add(portletInfo.page.title);                
                if (portletInfo.active)
                {
                    item.SubItems.Add("Sí");
                }
                else
                {
                    item.SubItems.Add("No");
                }
                String version = portletInfo.version;
                if (portletInfo.version == "*")
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
            if (this.listViewPages.SelectedItems.Count > 0 && this.listViewPages.SelectedItems[0].Tag is ResourceInfo)
            {
                ResourceInfo portletInfo = (ResourceInfo)this.listViewPages.SelectedItems[0].Tag;
                this.Cursor = Cursors.WaitCursor;
                FormEditPorlet formEditPorlet = new FormEditPorlet(portletInfo, repositoryName, contentID);
                this.Cursor = Cursors.Default;
                formEditPorlet.ShowDialog();
                loadPorlets();
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
                    String type = document.DocumentType.ToString().ToLower();
                    name = OfficeApplication.OfficeDocumentProxy.createPreview(repositoryName, version.contentId, version.nameOfVersion,type);
                    String urlproxy = OfficeApplication.OfficeDocumentProxy.WebAddress.ToString();
                    if (!urlproxy.EndsWith("/gtw"))
                    {
                        if (!urlproxy.EndsWith("/"))
                        {
                            urlproxy += "/";
                        }
                        if (!urlproxy.EndsWith("gtw"))
                        {
                            urlproxy += "gtw";
                        }
                    }
                    Uri url = new Uri(urlproxy + "?contentId=" + version.contentId + "&versionName=" + version.nameOfVersion + "&repositoryName=" + repositoryName + "&name=" + name+"&type="+type);
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
            if (this.listViewPages.SelectedItems.Count > 0 && this.listViewPages.SelectedItems[0].Tag is ResourceInfo)
            {
                ResourceInfo portletInfo = (ResourceInfo)this.listViewPages.SelectedItems[0].Tag;
                Uri uri = OfficeApplication.OfficeDocumentProxy.WebAddress;
                Uri url = new Uri(uri.Scheme + "://" + uri.Host + ":" + uri.Port + portletInfo.page.url);
                FormPreview preview = new FormPreview(url,portletInfo.title);
                preview.ShowDialog(this);
            }
        }

        private void toolStripButtonDeletePage_Click(object sender, EventArgs e)
        {
            if (this.listViewPages.SelectedItems.Count > 0 && this.listViewPages.SelectedItems[0].Tag is ResourceInfo)
            {
                ResourceInfo portletInfo = (ResourceInfo)this.listViewPages.SelectedItems[0].Tag;
                DialogResult res = MessageBox.Show(this, "¿Desea borrar la publicación " + portletInfo.title + "?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (res == DialogResult.Yes)
                {
                    OfficeApplication.OfficeDocumentProxy.deleteResource(portletInfo);
                }
            }
            this.loadPorlets();
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
            if (contentType!=null)
            {
                props=OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName,contentType.id);
                values = this.propertyEditor1.Values;
                try
                {
                    OfficeApplication.OfficeDocumentProxy.validateContentValues(repositoryName, props, values, this.contentType.id);
                }
                catch (Exception ue)
                {
                    OfficeApplication.WriteError(ue);
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
                            OfficeApplication.WriteError(ue);
                        }
                        finally
                        {
                            this.Cursor = Cursors.Default;
                        }
                    }
                }
                catch (Exception ue)
                {
                    OfficeApplication.WriteError(ue);
                }
            }
        }

        private void toolStripButtonPublish_Click(object sender, EventArgs e)
        {
            document.Publish();
            loadPorlets();
        }

        private void listViewPages_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            ListViewItem item=this.listViewPages.GetItemAt(e.X, e.Y);
            if (item != null)
            {
                ResourceInfo portletInfo = (ResourceInfo)item.Tag;
                this.Cursor = Cursors.WaitCursor;
                FormEditPorlet formEditPorlet = new FormEditPorlet(portletInfo, repositoryName, contentID);
                this.Cursor = Cursors.Default;
                formEditPorlet.ShowDialog();
                loadPorlets();
            }
        }

        private void listViewPages_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Delete && this.listViewPages.SelectedItems.Count>0)
            {
                this.toolStripButtonDeletePage_Click(null, null);
            }
        }

        private void listViewVersions_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Delete && this.listViewVersions.SelectedItems.Count > 0)
            {
                this.toolStripButtonDeleteVersion_Click(null, null);
            }
        }
        
    }
}

