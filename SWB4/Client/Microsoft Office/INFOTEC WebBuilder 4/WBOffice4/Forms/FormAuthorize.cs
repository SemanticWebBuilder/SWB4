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
    public partial class FormAuthorize : Form
    {
        public FormAuthorize(String title)
        {
            InitializeComponent();
            this.Text = title;
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void buttonok_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
