using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Office.Tools.Ribbon;
using WBOffice4;
using WB4Office2007Library;
namespace WB4WordOffice2007
{
    public partial class RibbonMenuWord : OfficeRibbon, IMenuListener
    {
        private WordOfficeApplication application;
        public RibbonMenuWord()
        {
            InitializeComponent();
        }

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
        private void RibbonMenuWord_Load(object sender, RibbonUIEventArgs e)
        {
            application = new WordOfficeApplication(Globals.ThisAddIn.Application);
            OfficeApplication.MenuListener = this;
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

        private void buttonAbout_Click(object sender, RibbonControlEventArgs e)
        {
            WordOfficeApplication.ShowAbout();
        }

        private void buttonSave_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveDocument != null)
            {
                Word2007OfficeDocument document = new Word2007OfficeDocument(Globals.ThisAddIn.Application.ActiveDocument);
                document.SaveToSite();
            }
        }

        private void buttonOpen_Click(object sender, RibbonControlEventArgs e)
        {
            application.Open(DocumentType.Word);
        }

        private void buttonCloseSession_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.CloseSession();
        }

        private void buttonDelete_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveDocument != null)
            {
                Word2007OfficeDocument document = new Word2007OfficeDocument(Globals.ThisAddIn.Application.ActiveDocument);
                document.Delete();
            }
        }

        private void buttonChangePassword_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ChangePassword();
        }

        private void buttonCreatePage_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.CreatePage();
        }

        private void buttonInit_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.InitSession();
        }

        private void buttonPublish_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveDocument != null)
            {
                Word2007OfficeDocument document = new Word2007OfficeDocument(Globals.ThisAddIn.Application.ActiveDocument);
                document.Publish();
            }
        }

        private void buttonCleanPropeties_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveDocument != null)
            {
                Word2007OfficeDocument document = new Word2007OfficeDocument(Globals.ThisAddIn.Application.ActiveDocument);
                document.DeleteAsociation();
            }
        }

        private void buttonInformation_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveDocument != null)
            {
                Word2007OfficeDocument document = new Word2007OfficeDocument(Globals.ThisAddIn.Application.ActiveDocument);
                document.showContentInformation();
            }
        }

        private void buttonAddLink_Click(object sender, RibbonControlEventArgs e)
        {
            if (Globals.ThisAddIn.Application.ActiveDocument != null)
            {
                Word2007OfficeDocument document = new Word2007OfficeDocument(Globals.ThisAddIn.Application.ActiveDocument);
                document.InsertLink();
            }
        }

        private void buttonHelp_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ShowHelp();
        }

        private void buttonDocsToAuthorize_Click(object sender, RibbonControlEventArgs e)
        {
            OfficeApplication.ShowDocumentsToAuthorize();
        }
    }
}
