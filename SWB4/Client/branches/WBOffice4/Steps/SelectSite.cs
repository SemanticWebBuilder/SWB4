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
using WBOffice4.Controls;
using WBOffice4.Interfaces;
namespace WBOffice4.Steps
{
    public abstract partial class SelectSite : TSWizards.BaseInteriorStep
    {        
        public static readonly String WEB_PAGE = "WEB_PAGE";
        protected SelectWebPage selectWebPage;
        protected bool showCreatePage;
        public SelectSite() : this(true)
        {
        }
        public SelectSite(WebSiteInfo siteInfo)
            : this(true,siteInfo)
        {
            
        }
        public SelectSite(bool showCreatePage) : this(showCreatePage,null)
        {

        }
        public SelectSite(bool showCreatePage, WebSiteInfo siteInfo)
        {
            InitializeComponent();
            this.showCreatePage = showCreatePage;
            if (!showCreatePage)
            {
                this.toolStrip1.Visible = false;
            }
            if (siteInfo == null)
            {
                selectWebPage = new SelectWebPage();
            }
            else
            {
                selectWebPage = new SelectWebPage(siteInfo);
            }
            this.panelSelectWebPage.Controls.Add(selectWebPage);
            selectWebPage.Dock = DockStyle.Fill;
            selectWebPage.ClickNode += new NodeEvent(AfterSelect);
            
        }
        protected virtual void AfterSelect(TreeNode node)
        {
            this.toolStripButtonAddPage.Enabled = false;
            if (showCreatePage && node is WebPageTreeNode)
            {   
                WebPageTreeNode page = node as WebPageTreeNode;
                if (OfficeApplication.OfficeApplicationProxy.canCreatePage(page.WebPageInfo))
                {
                    this.toolStripButtonAddPage.Enabled = true;
                }                
            }
        }

        private void toolStripButtonAddPage_Click(object sender, EventArgs e)
        {
            OfficeApplication.CreatePage();
            if (selectWebPage.SelectedWebPage != null)
            {
                selectWebPage.SelectedWebPage.ReLoadChilds();
                selectWebPage.SelectedWebPage.Expand();
            }            
        }
    }
}
