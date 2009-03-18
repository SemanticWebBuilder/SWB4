using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Reflection;
namespace WBOffice4.Forms
{
    internal partial class FormLogin : Form
    {
        URIConfigurationList uRIConfigurationList = new URIConfigurationList();
        public Uri WebAddress { get; set; }
        public String User { get; set; }
        public String Password { get; set; }

        public FormLogin()
        {
            InitializeComponent();
            this.comboBoxAddresses.Items.Clear();
            foreach (Uri uri in uRIConfigurationList.Addresses)
            {
                this.comboBoxAddresses.Items.Add(uri);
            }
            if (this.comboBoxAddresses.Items.Count > 0)
            {
                this.comboBoxAddresses.SelectedIndex = 0;
            }

        }

        private void buttonAceptar_Click(object sender, EventArgs e)
        {
            if (!Uri.IsWellFormedUriString(this.comboBoxAddresses.Text, UriKind.Absolute))
            {
                RtlAwareMessageBox.Show(this, "La dirección web no está bien escrita", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.comboBoxAddresses.Focus();                
                return;
            }
            Uri uri = new Uri(this.comboBoxAddresses.Text, UriKind.Absolute);
            if (!(uri.Scheme == Uri.UriSchemeNetTcp || uri.Scheme == Uri.UriSchemeHttp || uri.Scheme == Uri.UriSchemeHttps))
            {
                RtlAwareMessageBox.Show(this, "La dirección web debe ser una IP, Http ó Https", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.comboBoxAddresses.Focus();
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxLogin.Text.Trim()))
            {
                RtlAwareMessageBox.Show(this, "Falta indicar la clave de acceso", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxLogin.Focus();
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxPassword.Text.Trim()))
            {
                RtlAwareMessageBox.Show(this, "Falta indicar la contraseña de acceso", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxPassword.Focus();
                return;
            }
            uRIConfigurationList.Add(this.textBoxLogin.Text, uri);
            User = this.textBoxLogin.Text;
            Password = this.textBoxPassword.Text;
            WebAddress = uri;
            this.DialogResult = DialogResult.OK;
            this.Close();

        }
        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }


        private void comboBoxAddresses_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.comboBoxAddresses.Text))
            {
                string uri = this.comboBoxAddresses.Text;
                String login = uRIConfigurationList.GetLogin(new Uri(uri));
                if (!String.IsNullOrEmpty(login))
                {
                    this.buttonDelete.Enabled = true;
                    this.textBoxLogin.Text = login;
                }
            }
        }

        private void buttonAdvanced_Click(object sender, EventArgs e)
        {
            FormConfigurationProxy form = new FormConfigurationProxy();
            form.ShowDialog();
        }

        private void FormLogin_Load(object sender, EventArgs e)
        {
            char[] sep = { ',', '=' };
            string[] data = Assembly.GetExecutingAssembly().FullName.Split(sep);
            String version = "";
            int i = 0;
            foreach (String s in data)
            {
                if (s.IndexOf("Version") != -1)
                {
                    version = data[i + 1];
                }
                i++;
            }
            //this.labelAssembly.Text += ""+version+","+Assembly.GetExecutingAssembly().ManifestModule.Name;
            this.labelAssembly.Text += "" + version;
            
        }

        private void FormLogin_Activated(object sender, EventArgs e)
        {
            if (comboBoxAddresses.Items.Count == 0)
            {
                comboBoxAddresses.Focus();
            }
            if (String.IsNullOrEmpty(this.textBoxLogin.Text.Trim()))
            {
                textBoxLogin.Focus();
            }
            if (String.IsNullOrEmpty(this.textBoxPassword.Text.Trim()))
            {
                textBoxPassword.Focus();
            }
        }

        

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.comboBoxAddresses.Text))
            {
                string uri = this.comboBoxAddresses.Text;
                String login = uRIConfigurationList.GetLogin(new Uri(uri));
                if (!String.IsNullOrEmpty(login))
                {
                    uRIConfigurationList.remove(new Uri(uri));
                }
            }
        }
    }
}
