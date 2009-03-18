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
    public partial class FormCreatePage : TSWizards.BaseWizard
    {
        public FormCreatePage()
        {
            InitializeComponent();
        }

        private void FormCreatePage_LoadSteps(object sender, EventArgs e)
        {
            this.AddStep(new SelectSiteCreatePage());
            this.AddStep(new SelectTitles());
        }
    }
}
