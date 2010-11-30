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
        public static readonly String CONTENT_PROPERTIES = "CONTENT_PROPERTIES";
        public ContentProperties(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;

        }

        private void ContentProperties_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            String repositoryName = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
            ContentType type = ((ContentType)this.Wizard.Data[TitleAndDescription.NODE_TYPE]);
            PropertyInfo[] props = OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName, type.id);
            this.propertyEditor1.Properties = props;
        }

        private void ContentProperties_ValidateStep(object sender, CancelEventArgs e)
        {
            FileInfo zipFile = null;
            String title = this.Wizard.Data[TitleAndDescription.TITLE].ToString();
            String description = this.Wizard.Data[TitleAndDescription.DESCRIPTION].ToString();
            String categoryID = this.Wizard.Data[SelectCategory.CATEGORY_ID].ToString();
            String repositoryName = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
            RepositoryInfo rep = null;
            foreach(RepositoryInfo temp in OfficeApplication.OfficeApplicationProxy.getRepositories())
            {
                if(temp.name.Equals(repositoryName))
                {
                    rep=temp;
                }
            }
            ContentType contentType = (ContentType)this.Wizard.Data[TitleAndDescription.NODE_TYPE];
            PropertyInfo[] properties = OfficeApplication.OfficeDocumentProxy.getContentProperties(repositoryName, contentType.id);
            String[] values = this.propertyEditor1.Values;
            try
            {
                OfficeApplication.OfficeDocumentProxy.validateContentValues(repositoryName, properties, values, contentType.id);
            }
            catch (Exception ue)
            {
                OfficeApplication.WriteError(ue);
                MessageBox.Show(this, ue.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            this.Wizard.SetProgressBarInit(3, 1, "Preparando documento para guardar...");
            try
            {
                zipFile = document.CreateZipFile();
                this.Wizard.SetProgressBarInit(3, 2, "Guardando documento en el sitio...");
                IOfficeDocument openOfficeDocument = OfficeDocument.OfficeDocumentProxy;
                openOfficeDocument.Attachments.Add(new Attachment(zipFile, zipFile.Name));
                String name = document.FilePath.Name.Replace(document.DefaultExtension, document.PublicationExtension);
                String contentID = openOfficeDocument.save(title, description, repositoryName, categoryID, document.DocumentType.ToString().ToUpper(), contentType.id, name, properties, values);
                this.Wizard.Data[TitleAndDescription.CONTENT_ID] = contentID;
                document.SaveContentProperties(contentID, repositoryName);
                this.Wizard.SetProgressBarEnd();
                WebSiteInfo[] sites=OfficeApplication.OfficeApplicationProxy.getSites();
                if (sites != null && sites.Length > 0 && rep!=null && rep.siteInfo!=null)
                {
                    DialogResult res = MessageBox.Show(this, "¿Desea publicar el contenido en una página web?", this.Wizard.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);                                        
                    if (res == DialogResult.Yes)
                    {
                        WebSiteInfo webSiteInfo = new WebSiteInfo();
                        webSiteInfo.id = rep.siteInfo.id;
                        webSiteInfo.title = rep.siteInfo.title;
                        document.Publish(title, description, webSiteInfo);
                    }
                }
                if (OfficeApplication.MenuListener != null)
                {
                    OfficeApplication.MenuListener.DocumentPublished();
                }
            }
            catch (Exception ue)
            {
                OfficeApplication.WriteError(ue);
                MessageBox.Show(this, "Error al tratar de publicar un documento " + ue.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
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
