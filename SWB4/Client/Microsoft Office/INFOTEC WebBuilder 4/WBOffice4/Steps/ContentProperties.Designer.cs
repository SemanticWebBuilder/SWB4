namespace WBOffice4.Steps
{
    partial class ContentProperties
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
            this.SuspendLayout();
            // 
            // Description
            // 
            this.Description.Text = "Propiedes del contenido";
            // 
            // ContentProperties
            // 
            this.Name = "ContentProperties";
            this.StepDescription = "Indique las propiedes del contenido";
            this.StepTitle = "Propiedes del contenido";
            this.ShowStep += new TSWizards.ShowStepEventHandler(this.ContentProperties_ShowStep);
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(this.ContentProperties_ValidateStep);
            this.ResumeLayout(false);

        }

        #endregion
    }
}
