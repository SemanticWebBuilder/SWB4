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
using Microsoft.Office.Tools.Ribbon;
using WBOffice4;
using WB4Office2007Library;
namespace WB4ExcelOffice2007
{
    public partial class RibbonMenu : OfficeRibbon, IMenuListener
    {
        private ExcelApplication application;
        public RibbonMenu()
        {
            InitializeComponent();
        }
        public void NoDocumentsActive()
        {
            this.buttonOpen.Visible = true;
            this.buttonPublish.Visible = false;
            this.buttonCleanPropeties.Visible = false;
            this.buttonSave.Visible = false;
            this.buttonInformation.Visible = false;            
        }
        public void DocumentsActive()
        {
            this.buttonOpen.Visible = true;
            this.buttonSave.Visible = true;
        }
        public void NoDocumentPublished()
        {
            this.buttonOpen.Visible = true;
            this.buttonInformation.Visible = false;
            this.buttonCleanPropeties.Visible = false;
            this.buttonPublish.Visible = false;
            this.buttonDelete.Visible = false;
            this.buttonSave.Visible = true;
        }
        public void DocumentPublished()
        {
            this.buttonOpen.Visible = true;
            this.buttonInformation.Visible = true;
            this.buttonCleanPropeties.Visible = true;
            this.buttonDelete.Visible = true;
            this.buttonPublish.Visible = true;
            this.buttonSave.Visible = true;
        }
        #region MenuListener Members


        public void LogOff()
        {
            this.buttonInit.Enabled = true;
            this.buttonCloseSession.Enabled = false;
        }

        public void LogOn()
        {
            this.buttonInit.Enabled = false;
            this.buttonCloseSession.Enabled = true;
        }

        #endregion
        private void RibbonMenu_Load(object sender, RibbonUIEventArgs e)
        {
            application = new ExcelApplication(Globals.ThisAddIn.Application);
            OfficeApplication.MenuListener = this;            
        }

      

        private void buttonSave_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveWorkbook != null)
            {
                Excel2007OfficeDocument document = new Excel2007OfficeDocument(Globals.ThisAddIn.Application.ActiveWorkbook);
                document.SaveToSite();
            }
        }

        private void buttonPublish_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveWorkbook != null)
            {
                Excel2007OfficeDocument document = new Excel2007OfficeDocument(Globals.ThisAddIn.Application.ActiveWorkbook);
                document.Publish();
            }
        }

        private void buttonOpen_Click(object sender, RibbonControlEventArgs e)
        {
            application.Open(DocumentType.Excel);
        }

        private void buttonInformation_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveWorkbook != null)
            {
                Excel2007OfficeDocument document = new Excel2007OfficeDocument(Globals.ThisAddIn.Application.ActiveWorkbook);
                document.showContentInformation();
            }
        }

        private void buttonCleanPropeties_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveWorkbook != null)
            {
                Excel2007OfficeDocument document = new Excel2007OfficeDocument(Globals.ThisAddIn.Application.ActiveWorkbook);
                document.DeleteAsociation();
            }
        }

        private void buttonDelete_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveWorkbook != null)
            {
                Excel2007OfficeDocument document = new Excel2007OfficeDocument(Globals.ThisAddIn.Application.ActiveWorkbook);
                document.Delete();
            }
        }

        private void buttonCreatePage_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.CreatePage();
        }

        private void buttonChangePassword_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ChangePassword();
        }

        private void buttonAddLink_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveWorkbook != null)
            {
                Excel2007OfficeDocument document = new Excel2007OfficeDocument(Globals.ThisAddIn.Application.ActiveWorkbook);
                document.InsertLink();
            }
        }

        private void buttonHelp_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ShowHelp();
        }

        private void buttonAbout_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ShowAbout();
        }

        private void buttonInit_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.InitSession();
        }

        private void buttonCloseSession_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.CloseSession();
        }

        private void buttonDocstoAuthorize_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ShowDocumentsToAuthorize();
        }

        private void buttonShowDocumentDetail_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveWorkbook != null)
            {
                Excel2007OfficeDocument document = new Excel2007OfficeDocument(Globals.ThisAddIn.Application.ActiveWorkbook);
                document.showDocumentDetail();
            }
        }

        private void buttonViewRep_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.showRepository(DocumentType.Excel);
        }

        
    }
}
