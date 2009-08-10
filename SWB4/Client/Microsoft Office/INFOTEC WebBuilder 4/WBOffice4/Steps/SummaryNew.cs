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
using System.IO;
using WBOffice4.Interfaces;
using WBOffice4.Forms;
namespace WBOffice4.Steps
{
    internal partial class SummaryNew : TSWizards.BaseInteriorStep
    {
        OfficeDocument document;
        public SummaryNew(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;

        }
        public void loadVersions(String contentId, String repositoryName)
        {
            try
            {

                int rows = listViewVersions.Items.Count;
                for (int i = 1; i <= rows; i++)
                {
                    listViewVersions.Items.RemoveAt(0);
                }
                IOfficeDocument document = OfficeApplication.OfficeDocumentProxy;
                foreach (VersionInfo versionInfo in document.getVersions(repositoryName, contentId))
                {

                    String date = String.Format(OfficeApplication.iso8601dateFormat, versionInfo.created);
                    ListViewItem item = new ListViewItem(versionInfo.nameOfVersion);
                    item.SubItems.Add(date);
                    item.SubItems.Add(versionInfo.user);
                    listViewVersions.Items.Add(item);
                }
            }
            catch
            {
            }

        }

        private void SummaryNew_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            string contentId = this.Wizard.Data[TitleAndDescription.CONTENT_ID].ToString();
            string rep = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
            loadVersions(contentId, rep);
            this.Wizard.BackEnabled = false;
        }
    }
}
