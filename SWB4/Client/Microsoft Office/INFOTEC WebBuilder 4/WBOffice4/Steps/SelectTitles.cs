using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace WBOffice4.Steps
{
    public partial class SelectTitles : TSWizards.BaseInteriorStep
    {
        public SelectTitles()
        {
            InitializeComponent();
        }

        private void listView1_AfterLabelEdit(object sender, LabelEditEventArgs e)
        {
            
        }
    }
}
