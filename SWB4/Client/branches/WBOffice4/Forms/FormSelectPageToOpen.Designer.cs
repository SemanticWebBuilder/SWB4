namespace WBOffice4.Forms
{
    partial class FormSelectPageToOpen
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
            // panelStep
            // 
            this.panelStep.BackColor = System.Drawing.SystemColors.Control;
            this.panelStep.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panelStep.Location = new System.Drawing.Point(0, 66);
            this.panelStep.Size = new System.Drawing.Size(488, 249);
            // 
            // FormSelectPageToOpen
            // 
            this.ClientSize = new System.Drawing.Size(488, 355);
            this.Name = "FormSelectPageToOpen";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Seleccione Página Web Para buscar contenido";
            this.Title = "Seleccione Página Web Para buscar contenido";
            this.Activated += new System.EventHandler(this.FormSelectPageToOpen_Activated);
            this.LoadSteps += new System.EventHandler(this.FormSelectPageToOpen_LoadSteps);
            this.ResumeLayout(false);

        }

        #endregion
    }
}
