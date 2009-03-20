using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WBOffice4.Forms
{
    public partial class FormChangePassword : Form
    {
        
        public FormChangePassword()
        {
            InitializeComponent();
        }
        public String NewPassword
        {
            get
            {
                return this.textBoxNewPassword.Text;
            }
        }
        private void buttonAccept_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxNewPassword.Text.Trim()))
            {
                RtlAwareMessageBox.Show(this, "¡Indique la nueva contraseña!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxNewPassword.Focus();
                return;
            }
            if (this.textBoxNewPassword.Text.Trim() != this.textBoxConfirmNewPassword.Text.Trim())
            {
                RtlAwareMessageBox.Show(this, "La contraseña de acceso no coincide con la confirmación de la contraseña de acceso", this.Text, MessageBoxButtons.OK,MessageBoxIcon.Error);
                this.textBoxNewPassword.Focus();
                return;
            }
            OfficeApplication.OfficeApplicationProxy.changePassword(this.textBoxNewPassword.Text.Trim());
            OfficeApplication.OfficeApplicationProxy.Credentials.Password = this.textBoxNewPassword.Text.Trim();
            OfficeApplication.OfficeDocumentProxy.Credentials.Password = this.textBoxNewPassword.Text.Trim();
            this.Close();
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
