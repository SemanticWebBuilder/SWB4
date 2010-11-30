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
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;
using WBOffice4.Interfaces;

namespace WBOffice4.Forms
{
    public partial class FormDocumentsToAuthorize : Form
    {
        public FormDocumentsToAuthorize()
        {
            InitializeComponent();
            loadSites();
            loadContents();
        }
        private void loadSites()
        {
            foreach (WebSiteInfo site in OfficeApplication.OfficeApplicationProxy.getSites())
            {
                this.comboBoxSites.Items.Add(site);
            }
            if (this.comboBoxSites.Items.Count > 0)
            {
                this.comboBoxSites.SelectedIndex = 0;
            }
        }
        private void loadContents()
        {
            try
            {
                this.Cursor = Cursors.WaitCursor;
                this.toolStripButtonSee.Enabled = false;
                this.toolStripButtonAuthorize.Enabled = false;
                this.toolStripButtonReject.Enabled = false;
                this.listViewFlows.Items.Clear();
                if (this.comboBoxSites.SelectedItem is WebSiteInfo)
                {
                    WebSiteInfo site = (WebSiteInfo)this.comboBoxSites.SelectedItem;
                    if (this.radioButtonAll.Checked)
                    {
                        foreach (FlowContentInformation info in OfficeApplication.OfficeApplicationProxy.getAllContents(site))
                        {
                            FlowItem item = new FlowItem(info);
                            this.listViewFlows.Items.Add(item);
                        }
                    }
                    else if (this.radioButtonMyDocuments.Checked)
                    {
                        foreach (FlowContentInformation info in OfficeApplication.OfficeApplicationProxy.getMyContents(site))
                        {
                            FlowItem item = new FlowItem(info);
                            this.listViewFlows.Items.Add(item);
                        }
                    }
                    else
                    {
                        foreach (FlowContentInformation info in OfficeApplication.OfficeApplicationProxy.getContentsForAuthorize(site))
                        {
                            FlowItem item = new FlowItem(info);
                            this.listViewFlows.Items.Add(item);
                        }
                    }
                }
            }
            catch(Exception e)
            {
                OfficeApplication.WriteError(e);
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }


        private void buttonCancel_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.OK;
            this.Close();
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.toolStripButtonSee.Enabled = false;
            this.toolStripButtonAuthorize.Enabled = false;
            this.toolStripButtonReject.Enabled = false;
            if (this.listViewFlows.SelectedItems.Count > 0)
            {
                ResourceInfo info = ((FlowItem)this.listViewFlows.SelectedItems[0]).FlowContentInformation.resourceInfo;
                if (OfficeApplication.OfficeApplicationProxy.isReviewer(info))
                {
                    this.toolStripButtonAuthorize.Enabled = true;
                    this.toolStripButtonReject.Enabled = true;
                }
                this.toolStripButtonSee.Enabled = true;                
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            loadContents();
        }

        private void toolStripButtonAuthorize_Click(object sender, EventArgs e)
        {
            if (this.listViewFlows.SelectedItems.Count > 0)
            {
                ResourceInfo info = ((FlowItem)this.listViewFlows.SelectedItems[0]).FlowContentInformation.resourceInfo;
                FormAuthorize formAuthorize = new FormAuthorize("Autorizar");
                formAuthorize.ShowDialog();
                if (formAuthorize.DialogResult == DialogResult.OK)
                {
                    OfficeApplication.OfficeApplicationProxy.authorize(info, formAuthorize.textBox1.Text);
                    loadContents();
                }
            }
        }

        private void toolStripButtonReject_Click(object sender, EventArgs e)
        {
            if (this.listViewFlows.SelectedItems.Count > 0)
            {
                ResourceInfo info = ((FlowItem)this.listViewFlows.SelectedItems[0]).FlowContentInformation.resourceInfo;
                FormAuthorize formAuthorize = new FormAuthorize("Recharzar");
                formAuthorize.ShowDialog();
                if (formAuthorize.DialogResult == DialogResult.OK)
                {
                    OfficeApplication.OfficeApplicationProxy.reject(info, formAuthorize.textBox1.Text);
                    loadContents();
                }
            }
        }

        private void toolStripButtonSee_Click(object sender, EventArgs e)
        {
            if (this.listViewFlows.SelectedItems.Count > 0)
            {
                FlowItem item = (FlowItem)this.listViewFlows.SelectedItems[0];
                ResourceInfo resourceinfo = item.FlowContentInformation.resourceInfo;
                String version = resourceinfo.version;
                if (version.Equals("*"))
                {
                    version = resourceinfo.lastversion;
                }
                String name = null;
                try
                {                    
                    name = OfficeApplication.OfficeDocumentProxy.createPreview(resourceinfo.repository, resourceinfo.contentid, version,resourceinfo.type);
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
                    Uri url = new Uri(urlproxy + "?contentId=" + resourceinfo.contentid + "&versionName=" + version + "&repositoryName=" + resourceinfo.repository + "&name=" + name + "&type=" + resourceinfo.type);
                    String title = OfficeApplication.OfficeDocumentProxy.getTitle(resourceinfo.repository, resourceinfo.contentid);
                    FormPreview formPreview = new FormPreview(url, false, title);
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

        private void radioButtonForAuthorize_CheckedChanged(object sender, EventArgs e)
        {
            loadContents();
        }

        private void radioButtonAll_CheckedChanged(object sender, EventArgs e)
        {
            loadContents();
        }

        private void radioButtonMyDocuments_CheckedChanged(object sender, EventArgs e)
        {
            loadContents();
        }
    }
}
