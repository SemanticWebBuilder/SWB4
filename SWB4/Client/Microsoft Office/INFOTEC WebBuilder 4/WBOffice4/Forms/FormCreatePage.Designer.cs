namespace WBOffice4.Forms
{
    partial class FormCreatePage
    {
        /// <summary>
        /// Variable del diseñador requerida.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén utilizando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben eliminar; false en caso contrario, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido del método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormCreatePage));
            this.SuspendLayout();
            // 
            // panelStep
            // 
            this.panelStep.BackColor = System.Drawing.SystemColors.Control;
            this.panelStep.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panelStep.Location = new System.Drawing.Point(0, 66);
            this.panelStep.Size = new System.Drawing.Size(488, 249);
            // 
            // FormCreatePage
            // 
            this.ClientSize = new System.Drawing.Size(488, 355);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "FormCreatePage";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Asistente para crear una página web";
            this.VisibleLogo = true;
            this.LoadSteps += new System.EventHandler(this.FormCreatePage_LoadSteps);
            this.ResumeLayout(false);

        }

        #endregion
    }
}
