using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Steps;
namespace WBOffice4.Forms
{
    internal partial class FormOpenContent : TSWizards.BaseWizard
    {
        private OfficeApplication application;
        private DocumentType documentType;
        public FormOpenContent(OfficeApplication application, DocumentType documentType)
        {
            InitializeComponent();
            this.application = application;
            this.documentType = documentType;
        }
        

        private void FormOpenContent_LoadSteps(object sender, EventArgs e)
        {
            this.AddStep(new Search(documentType));
            this.AddStep(new SelectVersionToOpen());
            this.AddStep(new SelectDirectory(application));
        }
    }
}
