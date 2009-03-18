using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Diagnostics;
using System.Text;
using System.IO;

using System.Windows.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Forms;
namespace WBOffice4.Steps
{
    internal sealed partial class TitleAndDescription : TSWizards.BaseInteriorStep
    {
        public static readonly String NODE_TYPE = "NODE_TYPE";
        public static readonly String TITLE = "TITLE";
        public static readonly String DESCRIPTION = "DESCRIPTION";
        public static readonly String CONTENT_ID = "CONTENT_ID";
        OfficeDocument document;
        private bool showType;
        public TitleAndDescription(OfficeDocument document,bool showType)
        {
            InitializeComponent();
            this.document = document;
            this.showType = showType;
            if (!showType)
            {
                this.ComboBoxType.Visible = false;
                this.labelType.Visible = false;
            }
        }

        private void New1_ValidateStep(object sender, CancelEventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxTitle.Text))
            {
                MessageBox.Show(this, "¡De indicar el título!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxTitle.Focus();
                e.Cancel = true;
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxDescription.Text))
            {
                MessageBox.Show(this, "¡De indicar la descripción!", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxDescription.Focus();
                e.Cancel = true;
                return;
            }
            if (showType)
            {                
                this.Wizard.Data[TITLE] = this.textBoxTitle.Text;
                this.Wizard.Data[DESCRIPTION] = this.textBoxDescription.Text;
                this.Wizard.Data[NODE_TYPE] = this.ComboBoxType.SelectedItem;              
                
            }
            else
            {
                this.Wizard.Data[TITLE] = this.textBoxTitle.Text;
                this.Wizard.Data[DESCRIPTION] = this.textBoxDescription.Text;
            }
            
        }

        private void TitleAndDescription_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            this.textBoxTitle.Focus();
            if (showType)
            {
                ComboBoxType.Items.Clear();
                try
                {
                    String repository = this.Wizard.Data[SelectCategory.REPOSITORY_ID].ToString();
                    foreach (ContentType type in OfficeApplication.OfficeApplicationProxy.getContentTypes(repository))
                    {
                        this.ComboBoxType.Items.Add(type);
                    }
                    if (this.ComboBoxType.Items.Count > 0)
                    {
                        this.ComboBoxType.SelectedIndex = 0;
                    }
                }
                catch(Exception ue)
                {
                    Debug.WriteLine(ue.StackTrace);
                }
            }
        }
    }
}
