using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Steps
{
    public partial class SelectVersionToPublish : TSWizards.BaseInteriorStep
    {
        public static readonly String VERSION = "VERSION";
        public static readonly String CONTENT_ID_NAME = "CONTENT_ID_NAME";
        public static readonly String REPOSITORY_ID_NAME = "REPOSITORY_ID_NAME";
        private OfficeDocument document;
        public SelectVersionToPublish(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;
        }

        private void SelectVersionToPublish_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {            
            String contentID = document.CustomProperties[OfficeDocument.CONTENT_ID_NAME];
            string repository = document.CustomProperties[OfficeDocument.REPOSITORY_ID_NAME];
            this.Wizard.Data[CONTENT_ID_NAME]=contentID;
            this.Wizard.Data[REPOSITORY_ID_NAME] = repository;
            this.listViewVersions.Items.Clear();
            foreach (VersionInfo version in OfficeApplication.OfficeDocumentProxy.getVersions(repository, contentID))
            {
                ListViewItem item = new ListViewItem(version.nameOfVersion);
                item.Tag = version;
                String date = String.Format(OfficeApplication.iso8601dateFormat, version.created);
                item.SubItems.Add(date);
                item.SubItems.Add(version.user);
                this.listViewVersions.Items.Add(item);
            }
        }

        private void radioButtonOneVersion_CheckedChanged(object sender, EventArgs e)
        {
            this.listViewVersions.Enabled = true;
        }

        private void radioButtonLastVersion_CheckedChanged(object sender, EventArgs e)
        {
            this.listViewVersions.Enabled = false;
        }

        private void SelectVersionToPublish_ValidateStep(object sender, CancelEventArgs e)
        {
            if (this.radioButtonLastVersion.Checked)
            {
                this.Wizard.Data[VERSION] = "*";
            }
            else
            {
                if (this.listViewVersions.SelectedItems.Count == 1)
                {
                    this.Wizard.Data[VERSION] = listViewVersions.SelectedItems[0].Text;
                }
                else
                {
                    MessageBox.Show(this, "¡Debe indicar una versión a publicar!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    e.Cancel = true;
                }
            }
        }
    }
}
