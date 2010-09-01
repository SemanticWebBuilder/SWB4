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
using System.Drawing;
namespace WBOffice4.Steps
{
    public class SelectSiteCreatePage: SelectSite
    {
        private String title, description;
        private OfficeDocument document;
        public SelectSiteCreatePage(OfficeDocument document) : base(false)
        {
            this.document = document;
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(SelectSite_ValidateStep);
            selectWebPage.AddNode += new WBOffice4.Controls.NodeEvent(selectWebPage_onAddNode);
        }

        void selectWebPage_onAddNode(TreeNode node)
        {            
            if (node is WebPageTreeNode)
            {
                WebPageInfo page = ((WebPageTreeNode)node).WebPageInfo;
                if (!OfficeApplication.OfficeApplicationProxy.canCreatePage(page))
                {
                    node.ForeColor = Color.Gray;
                }         
            }
        }
        public SelectSiteCreatePage(String title, String description,OfficeDocument document)
            : base()
        {
            this.title = title;
            this.description = description;
            this.document = document;
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(SelectSite_ValidateStep);
        }
        
        private void SelectSite_ValidateStep(object sender, CancelEventArgs e)
        {
            if (selectWebPage.SelectedWebPage==null)
            {
                MessageBox.Show(this, "¡Debe indicar una página web!", "Seleccionar página web", MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
                return;
            }
            else
            {
                WebPageInfo parent = selectWebPage.SelectedWebPage.WebPageInfo;
                if (OfficeApplication.OfficeApplicationProxy.canCreatePage(parent))
                {
                    this.Wizard.Data[SelectSiteCreatePage.WEB_PAGE] = parent;
                    return;
                }
                else
                {
                    MessageBox.Show(this, "¡No tiene permisos para crear páginas web!", "Seleccionar página web", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    e.Cancel = true;
                    return;
                }
                
            }
        }                
    }
}
