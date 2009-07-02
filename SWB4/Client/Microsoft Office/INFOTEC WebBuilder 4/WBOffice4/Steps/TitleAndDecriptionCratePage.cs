using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace WBOffice4.Steps
{
    public partial class TitleAndDecriptionCratePage : TSWizards.BaseInteriorStep
    {
        public static readonly String TITLE = "TITLE";
        public static readonly String DESCRIPTION = "DESCRIPTION";
        public TitleAndDecriptionCratePage()
        {
            InitializeComponent();
        }

        private void TitleAndDecriptionCratePage_ValidateStep(object sender, CancelEventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxTitle.Text))
            {
                MessageBox.Show(this, "¡De indicar el título!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxTitle.Focus();
                e.Cancel = true;
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxDescription.Text))
            {
                MessageBox.Show(this, "¡De indicar la descripción!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxDescription.Focus();
                e.Cancel = true;
                return;
            }
            this.Wizard.Data[TITLE] = this.textBoxTitle.Text;
            this.Wizard.Data[DESCRIPTION] = this.textBoxDescription.Text;
        }
    }
}
