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
    public partial class FormAddLink : TSWizards.BaseWizard
    {
        OfficeDocument document;
        public FormAddLink(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;
        }

        private void FormAddLink_LoadSteps(object sender, EventArgs e)
        {
            this.AddStep(new SelectTitle());
            this.AddStep(new SelectSiteInsertLink(document));
        }
    }
}
