using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Diagnostics;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public partial class FormAddElement : Form
    {
        private ElementInfo rule;
        private SiteInfo siteInfo;
        private ResourceInfo resourceInfo;
        public FormAddElement(SiteInfo siteInfo, ResourceInfo resourceInfo)
        {
            InitializeComponent();
            this.siteInfo = siteInfo;
            this.resourceInfo = resourceInfo;
            loadRules();
        }
        public ElementInfo ElementInfo
        {
            get
            {
                return rule;
            }
        }
        private void loadRules()
        {


            try
            {
                ElementInfo[] rules = OfficeApplication.OfficeDocumentProxy.getElementsOfResource(resourceInfo);
                this.Cursor = Cursors.WaitCursor;
                foreach (ElementInfo info in OfficeApplication.OfficeApplicationProxy.getElementsToAdd(siteInfo))
                {
                    bool exists = false;
                    foreach (ElementInfo ruleinResource in rules)
                    {
                        if (ruleinResource.Equals(info))
                        {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists)
                    {
                        listView1.Items.Add(new ElementListView(info));
                    }
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.StackTrace);
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        private void buttonClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void buttonAdd_Click(object sender, EventArgs e)
        {
            if (this.listView1.SelectedIndices.Count == 0)
            {
                MessageBox.Show(this, "¡Debe indicar una regla / rol ó grupo de usuarios!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            this.rule = ((ElementListView)this.listView1.SelectedItems[0]).ElementInfo;
            this.DialogResult = DialogResult.OK;
            this.Close();
        }
    }
}
