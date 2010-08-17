using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WB4WordOffice2007
{
    public partial class FormInformation : Form
    {
        private ObjecInfo[] objects;
        public FormInformation(ObjecInfo[] objects)
        {
            InitializeComponent();
            if (objects != null)
            {
                foreach (ObjecInfo obj in objects)
                {
                    this.listBox1.Items.Add(obj);
                }
            }
        }

        private void salirToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

             
        

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listBox1.SelectedItems.Count ==1)
            {
                this.copiarToolStripMenuItem.Enabled = false;
                this.listView1.Items.Clear();
                WBOffice4.Interfaces.ObjecInfo obj = (ObjecInfo)listBox1.SelectedItem;
                foreach(PropertyObjectInfo prop in obj.properties)
                {
                    if (prop.title != null && prop.title.Trim() != "" && prop.value!=null && prop.value.Trim()!="")
                    {
                        WBOffice4.Controls.PropertyItem item = new WBOffice4.Controls.PropertyItem(prop);
                        this.listView1.Items.Add(item);
                    }
                }
            }
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.copiarToolStripMenuItem.Enabled=false;
            this.envíarCorreoToolStripMenuItem.Enabled=false;
            this.verPáginaWebToolStripMenuItem.Enabled = false;
            if (this.listView1.SelectedItems.Count > 0)
            {
                this.copiarToolStripMenuItem.Enabled = true;
                WBOffice4.Controls.PropertyItem prop = (WBOffice4.Controls.PropertyItem)this.listView1.SelectedItems[0];
                if (prop.isURL)
                {
                    this.verPáginaWebToolStripMenuItem.Enabled = true;
                }
                if (prop.isEmail)
                {
                    this.envíarCorreoToolStripMenuItem.Enabled = true;
                }
            }
        }

        private void copiarToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (this.listView1.SelectedItems.Count > 0)
            {
                WBOffice4.Controls.PropertyItem prop = (WBOffice4.Controls.PropertyItem)this.listView1.SelectedItems[0];
                System.Windows.Forms.Clipboard.SetDataObject(prop.PropertyObjectInfo.value);
            }
        }

        private void FormInformation_Deactivate(object sender, EventArgs e)
        {
            this.Close();
        }

        private void envíarCorreoToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (this.listView1.SelectedItems.Count > 0)
            {
                this.copiarToolStripMenuItem.Enabled = true;
                WBOffice4.Controls.PropertyItem prop = (WBOffice4.Controls.PropertyItem)this.listView1.SelectedItems[0];
                if (prop.isURL)
                {
                    System.Diagnostics.Process.Start(prop.PropertyObjectInfo.value);
                }
            }
        }

        private void verPáginaWebToolStripMenuItem_Click_1(object sender, EventArgs e)
        {
            if (this.listView1.SelectedItems.Count > 0)
            {
                this.copiarToolStripMenuItem.Enabled = true;
                WBOffice4.Controls.PropertyItem prop = (WBOffice4.Controls.PropertyItem)this.listView1.SelectedItems[0];
                if (prop.isURL)
                {
                    System.Diagnostics.Process.Start("explorer", prop.PropertyObjectInfo.value);
                }
            }
            
        }
    }
}
