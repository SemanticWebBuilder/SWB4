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
    internal partial class FormSaveContent : TSWizards.BaseWizard
    {
        private OfficeDocument document;
        
        public FormSaveContent(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;        
        }        
        private void FormPublishContent_LoadSteps(object sender, EventArgs e)
        {            
            this.AddStep(new SelectCategory(document));
            this.AddStep(new TitleAndDescription(document,true));
            this.AddStep(new ContentProperties(document));
            this.AddStep(new SummaryNew(document));
            
        }
    }
}
