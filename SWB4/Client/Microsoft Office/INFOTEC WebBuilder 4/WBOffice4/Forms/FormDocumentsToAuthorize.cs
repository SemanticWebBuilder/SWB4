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
    public partial class FormDocumentsToAuthorize : Form
    {
        public FormDocumentsToAuthorize()
        {
            InitializeComponent();
            loadSites();
        }
        private void loadSites()
        {
            foreach (WebSiteInfo site in OfficeApplication.OfficeApplicationProxy.getSites())
            {
                this.comboBoxSites.Items.Add(site);
            }
            if (this.comboBoxSites.Items.Count < 0)
            {
                this.comboBoxSites.SelectedIndex = 0;
            }
        }
        private void loadContents()
        {
            this.listViewFlows.Items.Clear();
            if(this.comboBoxSites.SelectedItem is WebSiteInfo)
            {
                WebSiteInfo site=(WebSiteInfo)this.comboBoxSites.SelectedItem;
                if (this.radioButtonAll.Checked)
                {
                    foreach (FlowContentInformation info in OfficeApplication.OfficeApplicationProxy.getAllContents(site))
                    {
                        FlowItem item = new FlowItem(info);
                        this.listViewFlows.Items.Add(item);
                    }
                }
                else if (this.radioButtonMyDocuments.Checked)
                {
                    foreach (FlowContentInformation info in OfficeApplication.OfficeApplicationProxy.getMyContents(site))
                    {
                        FlowItem item = new FlowItem(info);
                        this.listViewFlows.Items.Add(item);
                    }
                }
                else
                {
                    foreach (FlowContentInformation info in OfficeApplication.OfficeApplicationProxy.getContentsForAuthorize(site))
                    {
                        FlowItem item = new FlowItem(info);
                        this.listViewFlows.Items.Add(item);
                    }
                }
            }
        }


        private void buttonCancel_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.OK;
            this.Close();
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.toolStripButtonSee.Enabled = false;
            this.toolStripButtonAuthorize.Enabled = false;
            this.toolStripButtonReject.Enabled = false;
            if (this.listViewFlows.SelectedItems.Count > 0)
            {
                ResourceInfo info = ((FlowItem)this.listViewFlows.SelectedItems[0]).FlowContentInformation.resourceInfo;
                if (OfficeApplication.OfficeApplicationProxy.isReviewer(info))
                {
                    this.toolStripButtonAuthorize.Enabled = true;
                    this.toolStripButtonReject.Enabled = true;
                }
                this.toolStripButtonSee.Enabled = true;                
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            loadContents();
        }

        private void toolStripButtonAuthorize_Click(object sender, EventArgs e)
        {
            if (this.listViewFlows.SelectedItems.Count > 0)
            {
                ResourceInfo info = ((FlowItem)this.listViewFlows.SelectedItems[0]).FlowContentInformation.resourceInfo;
                FormAuthorize formAuthorize = new FormAuthorize("Autorizar");
                formAuthorize.ShowDialog();
                if (formAuthorize.DialogResult == DialogResult.OK)
                {
                    OfficeApplication.OfficeApplicationProxy.authorize(info, formAuthorize.textBox1.Text);
                    loadContents();
                }
            }
        }

        private void toolStripButtonReject_Click(object sender, EventArgs e)
        {
            if (this.listViewFlows.SelectedItems.Count > 0)
            {
                ResourceInfo info = ((FlowItem)this.listViewFlows.SelectedItems[0]).FlowContentInformation.resourceInfo;
                FormAuthorize formAuthorize = new FormAuthorize("Recharzar");
                formAuthorize.ShowDialog();
                if (formAuthorize.DialogResult == DialogResult.OK)
                {
                    OfficeApplication.OfficeApplicationProxy.reject(info, formAuthorize.textBox1.Text);
                    loadContents();
                }
            }
        }
    }
}
