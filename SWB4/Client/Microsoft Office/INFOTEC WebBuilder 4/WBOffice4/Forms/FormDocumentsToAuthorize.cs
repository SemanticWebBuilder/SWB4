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
    public partial class FormDocumentsToAuthorize : Form
    {
        public FormDocumentsToAuthorize()
        {
            InitializeComponent();
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.toolStripButtonSee.Enabled = false;
            this.toolStripButtonAuthorize.Enabled = false;
            this.toolStripButtonReject.Enabled = false;
            if (this.listView1.SelectedItems.Count > 0)
            {
                this.toolStripButtonSee.Enabled = true;
                this.toolStripButtonAuthorize.Enabled = true;
                this.toolStripButtonReject.Enabled = true;
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            
        }

        private void toolStripButtonAuthorize_Click(object sender, EventArgs e)
        {
            FormAuthorize formAuthorize = new FormAuthorize("Autorizar");
            formAuthorize.ShowDialog();
        }

        private void toolStripButtonReject_Click(object sender, EventArgs e)
        {
            FormAuthorize formAuthorize = new FormAuthorize("Recharzar");
            formAuthorize.ShowDialog();
        }
    }
}
