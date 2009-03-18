using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Office.Tools.Ribbon;
using WB4Office2007Library;
using WBOffice4;
namespace WB4PPTOffice2007
{
    public partial class RibbonMenu : OfficeRibbon,IMenuListener
    {
        PptApplication application;
        public RibbonMenu()
        {            
            InitializeComponent();
        }

        private void RibbonMenu_Load(object sender, RibbonUIEventArgs e)
        {
            application = new PptApplication(Globals.ThisAddIn.Application);
            OfficeApplication.MenuListener = this;
        }

        #region MenuListener Members

        public void NoDocumentsActive()
        {
            this.buttonOpen.Visible = true;
            this.buttonPublish.Visible = false;
            this.buttonCleanPropeties.Visible = false;
            this.buttonSave.Visible = false;
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

        #endregion

        #region MenuListener Members


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

        #endregion

        private void buttonAbout_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ShowAbout();
        }

        private void buttonCloseSession_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.CloseSession();                
        }

        private void buttonChangePassword_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ChangePassword();
        }

        private void buttonOpen_Click(object sender, RibbonControlEventArgs e)
        {
            application.Open(DocumentType.PPT);
        }

        private void buttonSave_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActivePresentation != null)
            {
                PowerPoint2007OfficeDocument document = new PowerPoint2007OfficeDocument(Globals.ThisAddIn.Application.ActivePresentation);
                document.SaveToSite();
            }
        }

        private void buttonPublish_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActivePresentation != null)
            {
                PowerPoint2007OfficeDocument document = new PowerPoint2007OfficeDocument(Globals.ThisAddIn.Application.ActivePresentation);
                document.Publish();
            }
        }

        private void buttonInformation_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActivePresentation != null)
            {
                PowerPoint2007OfficeDocument document = new PowerPoint2007OfficeDocument(Globals.ThisAddIn.Application.ActivePresentation);
                document.showContentInformation();
            }
        }

        private void buttonCleanPropeties_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActivePresentation != null)
            {
                PowerPoint2007OfficeDocument document = new PowerPoint2007OfficeDocument(Globals.ThisAddIn.Application.ActivePresentation);
                document.DeleteAsociation();
            }
        }

        private void buttonDelete_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActivePresentation != null)
            {
                PowerPoint2007OfficeDocument document = new PowerPoint2007OfficeDocument(Globals.ThisAddIn.Application.ActivePresentation);
                document.Delete();
            }
        }

        private void buttonCreatePage_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.CreatePage();
        }

        private void buttonAddLink_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActivePresentation != null)
            {
                PowerPoint2007OfficeDocument document = new PowerPoint2007OfficeDocument(Globals.ThisAddIn.Application.ActivePresentation);
                document.InsertLink();
            }
        }

        private void buttonHelp_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ShowHelp();
        }

        private void buttonInit_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.InitSession();
        }

        private void buttonDocsToAuthorize_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ShowDocumentsToAuthorize();
        }
    }
}
