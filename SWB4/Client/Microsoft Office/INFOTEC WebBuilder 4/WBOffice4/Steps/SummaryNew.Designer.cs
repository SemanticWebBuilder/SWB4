namespace WBOffice4.Steps
{
    partial class SummaryNew
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
            this.listViewVersions = new System.Windows.Forms.ListView();
            this.Version = new System.Windows.Forms.ColumnHeader();
            this.date = new System.Windows.Forms.ColumnHeader();
            this.Creator = new System.Windows.Forms.ColumnHeader();
            this.SuspendLayout();
            // 
            // listViewVersions
            // 
            this.listViewVersions.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.Version,
            this.date,
            this.Creator});
            this.listViewVersions.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listViewVersions.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
            this.listViewVersions.Location = new System.Drawing.Point(0, 0);
            this.listViewVersions.MultiSelect = false;
            this.listViewVersions.Name = "listViewVersions";
            this.listViewVersions.Size = new System.Drawing.Size(472, 236);
            this.listViewVersions.TabIndex = 1;
            this.listViewVersions.UseCompatibleStateImageBehavior = false;
            this.listViewVersions.View = System.Windows.Forms.View.Details;
            // 
            // Version
            // 
            this.Version.Text = "Version";
            this.Version.Width = 100;
            // 
            // date
            // 
            this.date.Text = "Fecha de creación";
            this.date.Width = 150;
            // 
            // Creator
            // 
            this.Creator.Text = "Creador";
            this.Creator.Width = 120;
            // 
            // SummaryNew
            // 
            this.Controls.Add(this.listViewVersions);
            this.IsFinished = true;
            this.Name = "SummaryNew";
            this.NextStep = "FINISHED";
            this.ShowStep += new TSWizards.ShowStepEventHandler(this.SummaryNew_ShowStep);
            this.Controls.SetChildIndex(this.Description, 0);
            this.Controls.SetChildIndex(this.listViewVersions, 0);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListView listViewVersions;
        private System.Windows.Forms.ColumnHeader Version;
        private System.Windows.Forms.ColumnHeader date;
        private System.Windows.Forms.ColumnHeader Creator;


    }
}
