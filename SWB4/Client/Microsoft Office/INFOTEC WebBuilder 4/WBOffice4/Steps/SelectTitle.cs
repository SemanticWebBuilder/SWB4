using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace WBOffice4.Steps
{
    public partial class SelectTitle : TSWizards.BaseInteriorStep
    {
        public static readonly String TITLE = "TITLE";
        public SelectTitle()
        {
            InitializeComponent();
        }

        private void SelectTitle_ValidateStep(object sender, CancelEventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxTitle.Text.Trim()))
            {
                MessageBox.Show(this, "¡Debe indicar el título!", "Título", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxTitle.Focus();
                e.Cancel = true;
            }
            else
            {
                this.Wizard.Data[TITLE] = this.textBoxTitle.Text.Trim();
            }
        }
    }
}
