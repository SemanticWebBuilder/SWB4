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
    internal partial class SelectVersionToOpen : TSWizards.BaseInteriorStep
    {
        public static readonly String VERSION = "VERSION";        
        String type;
        public SelectVersionToOpen(String type)
        {           
            InitializeComponent();
            this.type = type;
            
        }

        private void SelectVersionToOpen_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            string contentID = ((ContentInfo)this.Wizard.Data[Search.CONTENT]).id;
            string repository = this.Wizard.Data[Search.REPOSITORY_ID].ToString();
            this.listView1.Items.Clear();
            foreach (VersionInfo version in OfficeApplication.OfficeDocumentProxy.getVersions(repository, contentID))
            {
                ListViewItem item = new ListViewItem(version.nameOfVersion);
                item.Tag = version;
                String date = String.Format(OfficeApplication.iso8601dateFormat,version.created);
                item.SubItems.Add(date);
                item.SubItems.Add(version.user);
                this.listView1.Items.Add(item);
            }
        }

        private void SelectVersionToOpen_ValidateStep(object sender, CancelEventArgs e)
        {
            if (this.listView1.SelectedIndices.Count == 0)
            {
                MessageBox.Show(this, "¡Debe indicar una versión a abrir", "Selección de Versión", MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
            }
            else
            {
                this.Wizard.Data[VERSION] = this.listView1.SelectedItems[0].Tag;
            }
        }

        private void toolStripButtonViewContent_Click(object sender, EventArgs e)
        {
            if (this.listView1.SelectedItems.Count > 0)
            {
                string repository = this.Wizard.Data[Search.REPOSITORY_ID].ToString();
                VersionInfo version = (VersionInfo)this.listView1.SelectedItems[0].Tag;
                String name = null;
                try
                {                    
                    name = OfficeApplication.OfficeDocumentProxy.createPreview(repository, version.contentId, version.nameOfVersion,type);
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
                    Uri url = new Uri(urlproxy + "?contentId=" + version.contentId + "&versionName=" + version.nameOfVersion + "&repositoryName=" + repository + "&name=" + name + "&type=" + type);
                    String title = OfficeApplication.OfficeDocumentProxy.getTitle(repository, version.contentId);
                    FormPreview formPreview = new FormPreview(url,false,title);
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
    }
}
