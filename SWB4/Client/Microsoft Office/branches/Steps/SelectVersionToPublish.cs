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
namespace WBOffice4.Steps
{
    public partial class SelectVersionToPublish : TSWizards.BaseInteriorStep
    {
        public static readonly String VERSION = "VERSION";
        public static readonly String CONTENT_ID_NAME = "CONTENT_ID_NAME";
        public static readonly String REPOSITORY_ID_NAME = "REPOSITORY_ID_NAME";
        private OfficeDocument document;
        public SelectVersionToPublish(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;
        }

        private void SelectVersionToPublish_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {            
            String contentID = document.CustomProperties[OfficeDocument.CONTENT_ID_NAME];
            string repository = document.CustomProperties[OfficeDocument.REPOSITORY_ID_NAME];
            this.Wizard.Data[CONTENT_ID_NAME]=contentID;
            this.Wizard.Data[REPOSITORY_ID_NAME] = repository;
            this.listViewVersions.Items.Clear();
            foreach (VersionInfo version in OfficeApplication.OfficeDocumentProxy.getVersions(repository, contentID))
            {
                ListViewItem item = new ListViewItem(version.nameOfVersion);
                item.Tag = version;
                String date = String.Format(OfficeApplication.iso8601dateFormat, version.created);
                item.SubItems.Add(date);
                item.SubItems.Add(version.user);
                this.listViewVersions.Items.Add(item);
            }
        }

        private void radioButtonOneVersion_CheckedChanged(object sender, EventArgs e)
        {
            this.listViewVersions.Enabled = true;
        }

        private void radioButtonLastVersion_CheckedChanged(object sender, EventArgs e)
        {
            this.listViewVersions.Enabled = false;
        }

        private void SelectVersionToPublish_ValidateStep(object sender, CancelEventArgs e)
        {
            if (this.radioButtonLastVersion.Checked)
            {
                this.Wizard.Data[VERSION] = "*";
            }
            else
            {
                if (this.listViewVersions.SelectedItems.Count == 1)
                {
                    this.Wizard.Data[VERSION] = listViewVersions.SelectedItems[0].Text;
                }
                else
                {
                    MessageBox.Show(this, "¡Debe indicar una versión a publicar!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    e.Cancel = true;
                }
            }
        }
    }
}
