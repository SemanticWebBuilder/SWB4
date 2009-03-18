using System;
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

        
    }
}
