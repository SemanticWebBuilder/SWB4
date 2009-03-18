namespace WBOffice4.Steps
{
    partial class TitleAndDescription
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
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.textBoxTitle = new System.Windows.Forms.TextBox();
            this.textBoxDescription = new System.Windows.Forms.TextBox();
            this.labelType = new System.Windows.Forms.Label();
            this.ComboBoxType = new System.Windows.Forms.ComboBox();
            this.SuspendLayout();
            // 
            // Description
            // 
            this.Description.Text = "Indique el título y la descripción";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(38, 10);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(38, 13);
            this.label1.TabIndex = 1;
            this.label1.Text = "Título:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(38, 32);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(66, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Descripción:";
            // 
            // textBoxTitle
            // 
            this.textBoxTitle.Location = new System.Drawing.Point(154, 3);
            this.textBoxTitle.MaxLength = 255;
            this.textBoxTitle.Name = "textBoxTitle";
            this.textBoxTitle.Size = new System.Drawing.Size(268, 20);
            this.textBoxTitle.TabIndex = 3;
            // 
            // textBoxDescription
            // 
            this.textBoxDescription.Location = new System.Drawing.Point(154, 29);
            this.textBoxDescription.MaxLength = 255;
            this.textBoxDescription.Multiline = true;
            this.textBoxDescription.Name = "textBoxDescription";
            this.textBoxDescription.Size = new System.Drawing.Size(268, 130);
            this.textBoxDescription.TabIndex = 4;
            // 
            // labelType
            // 
            this.labelType.AutoSize = true;
            this.labelType.Location = new System.Drawing.Point(38, 173);
            this.labelType.Name = "labelType";
            this.labelType.Size = new System.Drawing.Size(97, 13);
            this.labelType.TabIndex = 5;
            this.labelType.Text = "Tipo de Contenido:";
            // 
            // ComboBoxType
            // 
            this.ComboBoxType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.ComboBoxType.FormattingEnabled = true;
            this.ComboBoxType.Location = new System.Drawing.Point(154, 165);
            this.ComboBoxType.Name = "ComboBoxType";
            this.ComboBoxType.Size = new System.Drawing.Size(268, 21);
            this.ComboBoxType.TabIndex = 6;
            // 
            // TitleAndDescription
            // 
            this.Controls.Add(this.textBoxTitle);
            this.Controls.Add(this.ComboBoxType);
            this.Controls.Add(this.textBoxDescription);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.labelType);
            this.Name = "TitleAndDescription";
            this.NextStep = "Step2";
            this.StepDescription = "Indique el título y la descripción";
            this.StepTitle = "Título y Descripción";
            this.ShowStep += new TSWizards.ShowStepEventHandler(this.TitleAndDescription_ShowStep);
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(this.New1_ValidateStep);
            this.Controls.SetChildIndex(this.labelType, 0);
            this.Controls.SetChildIndex(this.label1, 0);
            this.Controls.SetChildIndex(this.label2, 0);
            this.Controls.SetChildIndex(this.textBoxDescription, 0);
            this.Controls.SetChildIndex(this.ComboBoxType, 0);
            this.Controls.SetChildIndex(this.textBoxTitle, 0);
            this.Controls.SetChildIndex(this.Description, 0);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBoxTitle;
        private System.Windows.Forms.TextBox textBoxDescription;
        private System.Windows.Forms.Label labelType;
        private System.Windows.Forms.ComboBox ComboBoxType;
    }
}
