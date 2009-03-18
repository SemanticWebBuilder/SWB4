using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;
using WBOffice4.Interfaces;
using WBOffice4.Forms;
namespace WBOffice4.Steps
{
    internal partial class SummaryNew : TSWizards.BaseInteriorStep
    {
        OfficeDocument document;
        public SummaryNew(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;

        }
        public void loadVersions(String contentId, String repositoryName)
        {
            try
            {

                int rows = listViewVersions.Items.Count;
                for (int i = 1; i <= rows; i++)
                {
                    listViewVersions.Items.RemoveAt(0);
                }
                IOfficeDocument document = OfficeApplication.OfficeDocumentProxy;
                foreach (VersionInfo versionInfo in document.getVersions(repositoryName, contentId))
                {

                    String date = String.Format(OfficeApplication.iso8601dateFormat, versionInfo.created);
                    ListViewItem item = new ListViewItem(versionInfo.nameOfVersion);
                    item.SubItems.Add(date);
                    item.SubItems.Add(versionInfo.user);
                    listViewVersions.Items.Add(item);
                }
            }
            catch
            {
            }

        }

        private void SummaryNew_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            string contentId = this.Wizard.Data[TitleAndDescription.CONTENT_ID].ToString();
            string rep = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
            loadVersions(contentId, rep);
            this.Wizard.BackEnabled = false;
        }
    }
}
