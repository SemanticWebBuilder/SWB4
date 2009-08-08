namespace WBOffice4.Steps
{
    partial class ViewProperties
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.propertyEditor1 = new Editor.PropertyEditor();
            
            this.SuspendLayout();
            // 
            // Description
            // 
            this.Description.Text = "Indique las propiedades de presentación";
            // 
            // propertyEditor1
            // 
            this.propertyEditor1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.propertyEditor1.Location = new System.Drawing.Point(0, 0);
            this.propertyEditor1.Name = "propertyEditor1";
            this.propertyEditor1.Properties = null;
            this.propertyEditor1.Size = new System.Drawing.Size(472, 236);
            this.propertyEditor1.TabIndex = 1;
            // 
            // ViewProperties
            // 
            this.Controls.Add(this.propertyEditor1);
            this.Name = "ViewProperties";
            this.StepDescription = "Indique las propiedades de presentación";
            this.StepTitle = "Propiedades de presentación";
            this.ShowStep += new TSWizards.ShowStepEventHandler(this.ViewProperties_ShowStep);
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(this.ViewProperties_ValidateStep);
            this.Controls.SetChildIndex(this.propertyEditor1, 0);
            this.Controls.SetChildIndex(this.Description, 0);
            this.ResumeLayout(false);

        }

        #endregion

        private Editor.PropertyEditor propertyEditor1;
    }
}
