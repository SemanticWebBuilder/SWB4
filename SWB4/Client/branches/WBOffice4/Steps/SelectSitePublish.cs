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
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.ComponentModel;
using WBOffice4.Interfaces;
using WBOffice4.Forms;
using System.Drawing;

namespace WBOffice4.Steps
{
    public class SelectSitePublish : SelectSite
    {
        private DocumentType type;
        private OfficeDocument document;
        private String m_title, m_description;
        public SelectSitePublish(OfficeDocument document, WebSiteInfo webSiteInfo)
            : base(webSiteInfo)
        {
            this.document = document;
            type = document.DocumentType;
            this.ValidateStep+=new System.ComponentModel.CancelEventHandler(SelectSitePublish_ValidateStep);
            selectWebPage.AddNode += new WBOffice4.Controls.NodeEvent(selectWebPage_onAddNode);
        }

        void selectWebPage_onAddNode(TreeNode node)
        {
            if (node is WebPageTreeNode)
            {
                WebPageInfo page = ((WebPageTreeNode)node).WebPageInfo;
                if (!OfficeApplication.OfficeDocumentProxy.canPublishToResourceContent(type.ToString(), page))
                {
                    node.ForeColor = Color.Gray;
                }
            }
        }
        public SelectSitePublish(String title, String description, OfficeDocument document, WebSiteInfo webSiteInfo)
            : base(webSiteInfo)
        {
            this.m_title = title;
            this.m_description = description;
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(SelectSitePublish_ValidateStep);
            this.document = document;
        }
        
        protected virtual void SelectSitePublish_ValidateStep(object sender, CancelEventArgs e)
        {
            if (selectWebPage.SelectedWebPage!=null)
            {
                WebPageInfo webpage = selectWebPage.SelectedWebPage.WebPageInfo;
                if (!OfficeApplication.OfficeDocumentProxy.canPublishToResourceContent(type.ToString(),webpage))
                {
                    MessageBox.Show(this, "No tiene permisos para publicar en esta página", this.Wizard.Title, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    e.Cancel = true;
                    return;
                }
                this.Wizard.Data[WEB_PAGE] = webpage;
                String version = this.Wizard.Data[SelectVersionToOpen.VERSION].ToString();
                string repositoryName = this.Wizard.Data[SelectVersionToPublish.REPOSITORY_ID_NAME].ToString();
                string contentID = this.Wizard.Data[SelectVersionToPublish.CONTENT_ID_NAME].ToString();
                if (m_title == null || m_description == null)
                {
                    m_title = this.Wizard.Data[TitleAndDescription.TITLE].ToString();
                    m_description = this.Wizard.Data[TitleAndDescription.DESCRIPTION].ToString();
                }                
                PropertyInfo[] properties = OfficeApplication.OfficeDocumentProxy.getResourceProperties(document.reporitoryID, document.contentID);
                String[] values = null;
                if (properties == null || properties.Length == 0)
                {
                    values = new String[0];
                }
                else
                {
                    values = (String[])this.Wizard.Data[ViewProperties.VIEW_PROPERTIES_VALUES];
                }
                ResourceInfo portletInfo = OfficeApplication.OfficeDocumentProxy.publishToResourceContent(repositoryName, contentID, version, m_title, m_description, webpage,properties,values);
                if (OfficeApplication.OfficeDocumentProxy.needsSendToPublish(portletInfo))
                {
                    DialogResult res = MessageBox.Show(this, "Para activar el contenido, se necesita que sea autorizado primero\r\n¿Desea enviar el contenido a autorizar?", this.Wizard.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                    if (res == DialogResult.Yes)
                    {
                        FormSendToAutorize formSendToAutorize = new FormSendToAutorize(portletInfo);
                        formSendToAutorize.ShowDialog();
                        if (formSendToAutorize.DialogResult == DialogResult.OK)
                        {
                            OfficeApplication.OfficeDocumentProxy.sendToAuthorize(portletInfo, formSendToAutorize.pflow, formSendToAutorize.textBoxMessage.Text);
                        }
                    }
                    else
                    {
                        MessageBox.Show(this, "Para activar el contenido deberá enviar primero esta publicación a autorizar", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                }
                else
                {
                    DialogResult res = MessageBox.Show(this, "¿Desea activar el contenido?", this.Wizard.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                    if (res == DialogResult.Yes)
                    {
                        OfficeApplication.OfficeDocumentProxy.activateResource(portletInfo, true);
                        MessageBox.Show(this, "Se ha publicado correctamente el contenido en la página web", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                }
                
                this.Wizard.Close();
            }
            else
            {
                MessageBox.Show(this, "¡Debe indicar una página web", "Seleccionar página web", MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
            }
        }
    }
}
