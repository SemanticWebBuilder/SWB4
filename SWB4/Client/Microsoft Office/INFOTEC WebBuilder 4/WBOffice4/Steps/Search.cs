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
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Forms;
namespace WBOffice4.Steps
{
    internal partial class Search : TSWizards.BaseInteriorStep
    {
        public static readonly String CONTENT = "CONTENT";
        public static readonly String REPOSITORY_ID = "REPOSITORY_ID";
        private DocumentType documentType;
        public Search(DocumentType documentType)
        {
            InitializeComponent();
            this.comboBoxRepositories.Items.Clear();
            RepositoryInfo repall = new RepositoryInfo();
            repall.exclusive = false;
            repall.name = "Todos";
            repall = null;
            foreach (RepositoryInfo rep in OfficeApplication.OfficeApplicationProxy.getRepositories())
            {
                this.comboBoxRepositories.Items.Add(rep);
            }

            this.documentType = documentType;
            if (this.comboBoxRepositories.Items.Count > 0)
            {
                this.comboBoxRepositories.SelectedIndex = 0;
            }
        }

        private void buttonSearch_Click(object sender, EventArgs e)
        {
            if (this.comboBoxRepositories.SelectedItem != null && this.comboBoxRepositories.SelectedItem != null && comboBoxType.SelectedItem != null)
            {
                String rep = this.comboBoxRepositories.SelectedItem.ToString();
                String category = ((CategoryInfo)this.comboBoxCategories.SelectedItem).UDDI;
                String type = ((ContentType)this.comboBoxType.SelectedItem).id;
                this.listView1.Items.Clear();
                this.Cursor = Cursors.WaitCursor;
                WebPageInfo page = null;
                if (this.textBox_WebPage.Tag != null && this.textBox_WebPage.Tag is WebPageInfo)
                {
                    page = this.textBox_WebPage.Tag as WebPageInfo;
                }
                ContentInfo[] contents=null;
                if (page == null)
                {
                    contents = OfficeApplication.OfficeApplicationProxy.search(rep, this.textBoxTitle.Text, this.textBoxDescription.Text, category, type, this.documentType.ToString().ToUpper());
                }
                else
                {
                    contents = OfficeApplication.OfficeApplicationProxy.search(rep, this.textBoxTitle.Text, this.textBoxDescription.Text, category, type, this.documentType.ToString().ToUpper(), page);
                }                
                this.Cursor = Cursors.Default;
                if (contents.Length == 0)
                {
                    MessageBox.Show(this, "No se encontraron contenidos coincidentes", "Búsqueda de contenido", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                else
                {
                    MessageBox.Show(this, "Se encontraron " + contents.Length + " contenidos coincidentes", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                foreach (ContentInfo content in contents)
                {
                    ListViewItem item = new ListViewItem(content.title);
                    item.Tag = content;
                    item.SubItems.Add(content.descripcion);
                    String date=String.Format(OfficeApplication.iso8601dateFormat,content.created);
                    item.SubItems.Add(date);
                    item.SubItems.Add(content.categoryTitle);
                    this.listView1.Items.Add(item);
                }
                this.tabControl1.SelectTab(1);
            }
        }

        private void comboBoxRepositories_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.comboBoxRepositories.SelectedItem != null)
            {
                String rep = this.comboBoxRepositories.SelectedItem.ToString();
                this.textBox_WebPage.Text = "";
                this.textBox_WebPage.Tag = null;
                this.comboBoxCategories.Items.Clear();
                CategoryInfo all=new CategoryInfo();
                all.UDDI = "*";
                all.title = "Todas";
                ContentType allContentType = new ContentType();
                allContentType.id = "*";
                allContentType.title = "Todas";
                this.comboBoxCategories.Items.Add(all);
                foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getAllCategories(rep))
                {
                    this.comboBoxCategories.Items.Add(category);                    
                }
                if (this.comboBoxCategories.Items.Count > 0)
                {
                    this.comboBoxCategories.SelectedIndex = 0;
                }
                this.comboBoxType.Items.Clear();
                this.comboBoxType.Items.Add(allContentType);
                foreach(ContentType type in OfficeApplication.OfficeApplicationProxy.getContentTypes(rep))
                {
                    this.comboBoxType.Items.Add(type);
                }
                if (this.comboBoxType.Items.Count > 0)
                {
                    this.comboBoxType.SelectedIndex = 0;
                }
            }

        }
        
        private void Search_ValidateStep(object sender, CancelEventArgs e)
        {
            if (this.listView1.SelectedItems.Count == 0)
            {
                MessageBox.Show(this, "¡Debe seleccionar un contenido a abrir!", "Apertura de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.tabControl1.SelectTab(0);
                e.Cancel = true;
            }
            else
            {
                this.Wizard.Data[CONTENT] = this.listView1.SelectedItems[0].Tag;
                this.Wizard.Data[REPOSITORY_ID] = this.comboBoxRepositories.SelectedItem.ToString();
            }
        }

        private void Search_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            this.textBoxTitle.Focus();
        }

        private void buttonSelectWebPage_Click(object sender, EventArgs e)
        {
            if (this.comboBoxRepositories.SelectedItem != null)
            {
                RepositoryInfo rep = (RepositoryInfo)this.comboBoxRepositories.SelectedItem;
                if (rep.siteInfo != null)
                {
                    WebSiteInfo webSiteInfo = new WebSiteInfo();
                    webSiteInfo.id = rep.siteInfo.id;
                    webSiteInfo.title = rep.siteInfo.title;
                    FormSelectPageToOpen frm = new FormSelectPageToOpen(documentType, webSiteInfo);
                    DialogResult result = frm.ShowDialog(this);
                    if (result == DialogResult.OK && frm.WebPage != null)
                    {
                        this.textBox_WebPage.Text = frm.WebPage.title;
                        this.textBox_WebPage.Tag = frm.WebPage;
                    }
                }
                else
                {
                    FormSelectPageToOpen frm = new FormSelectPageToOpen(documentType);
                    DialogResult result = frm.ShowDialog(this);
                    if (result == DialogResult.OK && frm.WebPage != null)
                    {
                        this.textBox_WebPage.Text = frm.WebPage.title;
                        this.textBox_WebPage.Tag = frm.WebPage;
                    }
                }
            }
        }
    }
}
