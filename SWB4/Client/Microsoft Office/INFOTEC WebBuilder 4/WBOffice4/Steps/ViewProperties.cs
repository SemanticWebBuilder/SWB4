using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Utils;
namespace WBOffice4.Steps
{
    public partial class ViewProperties : TSWizards.BaseInteriorStep
    {
        
        public static readonly String VIEW_PROPERTIES = "VIEW_PROPERTIES";
        public static readonly String VIEW_PROPERTIES_VALUES = "VIEW_PROPERTIES_VALUES";
        public ViewProperties()
        {
            InitializeComponent();            
        }

        private void ViewProperties_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            
                string repositoryName = this.Wizard.Data[SelectVersionToPublish.REPOSITORY_ID_NAME].ToString();
                string contentID = this.Wizard.Data[SelectVersionToPublish.CONTENT_ID_NAME].ToString();
                PropertyInfo[] properties = OfficeApplication.OfficeDocumentProxy.getResourceProperties(repositoryName, contentID);
                this.propertyEditor1.Properties = properties;
            
        }

        private void ViewProperties_ValidateStep(object sender, CancelEventArgs e)
        {
            string repositoryName = this.Wizard.Data[SelectVersionToPublish.REPOSITORY_ID_NAME].ToString();
            string contentID = this.Wizard.Data[SelectVersionToPublish.CONTENT_ID_NAME].ToString();
            PropertyInfo[] properties=OfficeApplication.OfficeDocumentProxy.getResourceProperties(repositoryName, contentID);            
            Object[] values = this.propertyEditor1.Values;
            try
            {
                OfficeApplication.OfficeDocumentProxy.validateViewValues(repositoryName, contentID, properties, values);
            }
            catch (Exception ue)
            {
                MessageBox.Show(this, ue.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
                return;
            }
            this.Wizard.Data[VIEW_PROPERTIES] = properties;
            this.Wizard.Data[VIEW_PROPERTIES_VALUES] = values;
        }
    }
}
