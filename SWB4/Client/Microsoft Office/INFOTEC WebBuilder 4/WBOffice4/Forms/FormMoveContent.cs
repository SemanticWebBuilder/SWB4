using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Steps;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public partial class FormMoveContent : TSWizards.BaseWizard
    {
        private ResourceInfo resourceInfo;
        public FormMoveContent(ResourceInfo resourceInfo)
        {
            InitializeComponent();
            this.resourceInfo = resourceInfo;
        }

        private void FormMoveContent_LoadSteps(object sender, EventArgs e)
        {
            this.AddStep(new SelectSiteMoveContent(resourceInfo));
        }
    }
}
