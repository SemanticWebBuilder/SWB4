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
        private Object obj;
        private PropertyGrid grid = new PropertyGrid();
        public static readonly String VIEW_PROPERTIES = "VIEW_PROPERTIES";
        public static readonly String VIEW_PROPERTIES_VALUES = "VIEW_PROPERTIES_VALUES";
        public ViewProperties()
        {
            InitializeComponent();
            grid.ToolbarVisible = false;
            grid.Dock = DockStyle.Fill;
            this.Controls.Add(grid);
        }

        private void ViewProperties_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            if (obj == null)
            {
                string repositoryName = this.Wizard.Data[SelectVersionToPublish.REPOSITORY_ID_NAME].ToString();
                string contentID = this.Wizard.Data[SelectVersionToPublish.CONTENT_ID_NAME].ToString();
                PropertyInfo[] properties = OfficeApplication.OfficeDocumentProxy.getPortletProperties(repositoryName, contentID);
                obj = TypeFactory.getObject(properties, "Propiedades de presentación");
            }
            grid.SelectedObject = obj;
            
        }

        private void ViewProperties_ValidateStep(object sender, CancelEventArgs e)
        {
            string repositoryName = this.Wizard.Data[SelectVersionToPublish.REPOSITORY_ID_NAME].ToString();
            string contentID = this.Wizard.Data[SelectVersionToPublish.CONTENT_ID_NAME].ToString();
            PropertyInfo[] properties=OfficeApplication.OfficeDocumentProxy.getPortletProperties(repositoryName, contentID);
            Object[] values=TypeFactory.getValues(properties, obj);
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
