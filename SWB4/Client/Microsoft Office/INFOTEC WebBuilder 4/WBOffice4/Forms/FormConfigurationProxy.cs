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
    internal partial class FormConfigurationProxy : Form
    {
        SWBConfiguration configuration = new SWBConfiguration();
        public FormConfigurationProxy()
        {
            InitializeComponent();
            this.checkBoxUsesServerProxy.Checked = configuration.UsesProxy;
            this.textBoxServerProxy.Text = configuration.ProxyServer;
            this.textBoxServerPort.Text = configuration.ProxyPort.ToString();
        }

        private void buttonAccept_Click(object sender, EventArgs e)
        {
            if (this.checkBoxUsesServerProxy.Checked)
            {
                int result = 0;
                if (!int.TryParse(this.textBoxServerPort.Text, out result))
                {
                    RtlAwareMessageBox.Show(this, "El puerto debe ser númerico", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    this.textBoxServerPort.Focus();
                    return;
                }
                if (!Uri.IsWellFormedUriString(this.textBoxServerProxy.Text, UriKind.Absolute))
                {
                    RtlAwareMessageBox.Show(this, "El servidor proxy es no válido", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    this.textBoxServerProxy.Focus();
                    return;
                }
                Uri uri = new Uri(this.textBoxServerProxy.Text,UriKind.Absolute);
                if (uri.Scheme == Uri.UriSchemeNetTcp || uri.Scheme == Uri.UriSchemeHttp || uri.Scheme == Uri.UriSchemeHttps)
                {
                    RtlAwareMessageBox.Show(this, "El servidor proxy debe ser una IP, Http ó Https", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    this.textBoxServerProxy.Focus();
                    return;
                }
            }
            configuration.UsesProxy = checkBoxUsesServerProxy.Checked;
            configuration.ProxyServer = textBoxServerProxy.Text;
            configuration.ProxyPort = textBoxServerPort.Text;
            this.Close();
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void checkBoxUsesServerProxy_CheckedChanged(object sender, EventArgs e)
        {
            if (this.checkBoxUsesServerProxy.Checked)
            {
                this.textBoxServerPort.Enabled = true;
                this.textBoxServerProxy.Enabled = true;
            }
            else
            {
                this.textBoxServerPort.Enabled = false;
                this.textBoxServerProxy.Enabled = false;
            }
        }
    }
}
