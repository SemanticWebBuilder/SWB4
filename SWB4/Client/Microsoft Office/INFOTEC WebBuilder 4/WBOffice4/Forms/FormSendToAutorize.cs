using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public partial class FormSendToAutorize : Form
    {
        public PFlow pflow;
        public FormSendToAutorize(ResourceInfo info)
        {
            InitializeComponent();
            foreach(PFlow flow in OfficeApplication.OfficeDocumentProxy.getFlows(info))
            {
                this.comboBoxFlujo.Items.Add(flow);
            }
            if (this.comboBoxFlujo.Items.Count > 0)
            {
                this.comboBoxFlujo.SelectedIndex = 0;
            }
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void buttonAccept_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(this.textBoxMessage.Text))
            {
                MessageBox.Show(this, "¡Debe indicar el mensaje!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxMessage.Focus();
                return;
            }
            pflow=(PFlow)this.comboBoxFlujo.SelectedItem;
            this.DialogResult = DialogResult.OK;
            this.Close();
        }
    }
}
