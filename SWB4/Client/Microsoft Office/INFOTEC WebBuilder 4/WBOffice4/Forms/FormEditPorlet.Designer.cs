namespace WBOffice4.Forms
{
    partial class FormEditPorlet
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormEditPorlet));
            this.panel1 = new System.Windows.Forms.Panel();
            this.buttonOK = new System.Windows.Forms.Button();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.tabControlProperties = new System.Windows.Forms.TabControl();
            this.tabPageInformation = new System.Windows.Forms.TabPage();
            this.comboBoxVersiones = new System.Windows.Forms.ComboBox();
            this.label6 = new System.Windows.Forms.Label();
            this.checkBoxActive = new System.Windows.Forms.CheckBox();
            this.label4 = new System.Windows.Forms.Label();
            this.labelPage = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.labelSite = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.textBoxDescription = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.textBoxTitle = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.tabPageProperties = new System.Windows.Forms.TabPage();
            this.tabPageCalendar = new System.Windows.Forms.TabPage();
            this.listViewCalendar = new System.Windows.Forms.ListView();
            this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader2 = new System.Windows.Forms.ColumnHeader();
            this.toolStripCalendar = new System.Windows.Forms.ToolStrip();
            this.toolStripButtonAdd = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonEdit = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButton2 = new System.Windows.Forms.ToolStripButton();
            this.panel1.SuspendLayout();
            this.tabControlProperties.SuspendLayout();
            this.tabPageInformation.SuspendLayout();
            this.tabPageCalendar.SuspendLayout();
            this.toolStripCalendar.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.buttonOK);
            this.panel1.Controls.Add(this.buttonCancel);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 244);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(419, 42);
            this.panel1.TabIndex = 1;
            // 
            // buttonOK
            // 
            this.buttonOK.Location = new System.Drawing.Point(251, 9);
            this.buttonOK.Name = "buttonOK";
            this.buttonOK.Size = new System.Drawing.Size(75, 23);
            this.buttonOK.TabIndex = 1;
            this.buttonOK.Text = "Actualizar";
            this.buttonOK.UseVisualStyleBackColor = true;
            this.buttonOK.Click += new System.EventHandler(this.buttonOK_Click);
            // 
            // buttonCancel
            // 
            this.buttonCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.buttonCancel.Location = new System.Drawing.Point(332, 9);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 0;
            this.buttonCancel.Text = "Cerrar";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // tabControlProperties
            // 
            this.tabControlProperties.Controls.Add(this.tabPageInformation);
            this.tabControlProperties.Controls.Add(this.tabPageProperties);
            this.tabControlProperties.Controls.Add(this.tabPageCalendar);
            this.tabControlProperties.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControlProperties.Location = new System.Drawing.Point(0, 0);
            this.tabControlProperties.Name = "tabControlProperties";
            this.tabControlProperties.SelectedIndex = 0;
            this.tabControlProperties.Size = new System.Drawing.Size(419, 244);
            this.tabControlProperties.TabIndex = 2;
            // 
            // tabPageInformation
            // 
            this.tabPageInformation.Controls.Add(this.comboBoxVersiones);
            this.tabPageInformation.Controls.Add(this.label6);
            this.tabPageInformation.Controls.Add(this.checkBoxActive);
            this.tabPageInformation.Controls.Add(this.label4);
            this.tabPageInformation.Controls.Add(this.labelPage);
            this.tabPageInformation.Controls.Add(this.label5);
            this.tabPageInformation.Controls.Add(this.labelSite);
            this.tabPageInformation.Controls.Add(this.label3);
            this.tabPageInformation.Controls.Add(this.textBoxDescription);
            this.tabPageInformation.Controls.Add(this.label2);
            this.tabPageInformation.Controls.Add(this.textBoxTitle);
            this.tabPageInformation.Controls.Add(this.label1);
            this.tabPageInformation.Location = new System.Drawing.Point(4, 22);
            this.tabPageInformation.Name = "tabPageInformation";
            this.tabPageInformation.Padding = new System.Windows.Forms.Padding(3);
            this.tabPageInformation.Size = new System.Drawing.Size(411, 218);
            this.tabPageInformation.TabIndex = 0;
            this.tabPageInformation.Text = "Información";
            this.tabPageInformation.UseVisualStyleBackColor = true;
            // 
            // comboBoxVersiones
            // 
            this.comboBoxVersiones.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxVersiones.FormattingEnabled = true;
            this.comboBoxVersiones.Location = new System.Drawing.Point(131, 185);
            this.comboBoxVersiones.Name = "comboBoxVersiones";
            this.comboBoxVersiones.Size = new System.Drawing.Size(272, 21);
            this.comboBoxVersiones.TabIndex = 11;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(8, 188);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(91, 13);
            this.label6.TabIndex = 10;
            this.label6.Text = "Versión a mostrar:";
            // 
            // checkBoxActive
            // 
            this.checkBoxActive.AutoSize = true;
            this.checkBoxActive.Location = new System.Drawing.Point(131, 156);
            this.checkBoxActive.Name = "checkBoxActive";
            this.checkBoxActive.Size = new System.Drawing.Size(15, 14);
            this.checkBoxActive.TabIndex = 9;
            this.checkBoxActive.UseVisualStyleBackColor = true;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(8, 156);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(40, 13);
            this.label4.TabIndex = 8;
            this.label4.Text = "Activo:";
            // 
            // labelPage
            // 
            this.labelPage.AutoSize = true;
            this.labelPage.Location = new System.Drawing.Point(128, 131);
            this.labelPage.Name = "labelPage";
            this.labelPage.Size = new System.Drawing.Size(78, 13);
            this.labelPage.TabIndex = 7;
            this.labelPage.Text = "Sitio de prueba";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(8, 131);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(43, 13);
            this.label5.TabIndex = 6;
            this.label5.Text = "Página:";
            // 
            // labelSite
            // 
            this.labelSite.AutoSize = true;
            this.labelSite.Location = new System.Drawing.Point(128, 105);
            this.labelSite.Name = "labelSite";
            this.labelSite.Size = new System.Drawing.Size(81, 13);
            this.labelSite.TabIndex = 5;
            this.labelSite.Text = "Sitio de prueba ";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(8, 105);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(30, 13);
            this.label3.TabIndex = 4;
            this.label3.Text = "Sitio:";
            // 
            // textBoxDescription
            // 
            this.textBoxDescription.Location = new System.Drawing.Point(131, 43);
            this.textBoxDescription.Multiline = true;
            this.textBoxDescription.Name = "textBoxDescription";
            this.textBoxDescription.Size = new System.Drawing.Size(272, 49);
            this.textBoxDescription.TabIndex = 3;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(8, 43);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(66, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Descripción:";
            // 
            // textBoxTitle
            // 
            this.textBoxTitle.Location = new System.Drawing.Point(131, 13);
            this.textBoxTitle.Name = "textBoxTitle";
            this.textBoxTitle.Size = new System.Drawing.Size(272, 20);
            this.textBoxTitle.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(8, 16);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(38, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Título:";
            // 
            // tabPageProperties
            // 
            this.tabPageProperties.Location = new System.Drawing.Point(4, 22);
            this.tabPageProperties.Name = "tabPageProperties";
            this.tabPageProperties.Padding = new System.Windows.Forms.Padding(3);
            this.tabPageProperties.Size = new System.Drawing.Size(411, 260);
            this.tabPageProperties.TabIndex = 1;
            this.tabPageProperties.Text = "Propiedades de publicación";
            this.tabPageProperties.UseVisualStyleBackColor = true;
            // 
            // tabPageCalendar
            // 
            this.tabPageCalendar.Controls.Add(this.listViewCalendar);
            this.tabPageCalendar.Controls.Add(this.toolStripCalendar);
            this.tabPageCalendar.Location = new System.Drawing.Point(4, 22);
            this.tabPageCalendar.Name = "tabPageCalendar";
            this.tabPageCalendar.Size = new System.Drawing.Size(411, 260);
            this.tabPageCalendar.TabIndex = 2;
            this.tabPageCalendar.Text = "Calendarización";
            this.tabPageCalendar.UseVisualStyleBackColor = true;
            // 
            // listViewCalendar
            // 
            this.listViewCalendar.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2});
            this.listViewCalendar.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listViewCalendar.Location = new System.Drawing.Point(0, 25);
            this.listViewCalendar.Name = "listViewCalendar";
            this.listViewCalendar.Size = new System.Drawing.Size(411, 235);
            this.listViewCalendar.TabIndex = 1;
            this.listViewCalendar.UseCompatibleStateImageBehavior = false;
            this.listViewCalendar.View = System.Windows.Forms.View.Details;
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "Título";
            this.columnHeader1.Width = 150;
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "Activo";
            this.columnHeader2.Width = 150;
            // 
            // toolStripCalendar
            // 
            this.toolStripCalendar.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStripCalendar.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButtonAdd,
            this.toolStripSeparator1,
            this.toolStripButtonEdit,
            this.toolStripSeparator2,
            this.toolStripButton2});
            this.toolStripCalendar.Location = new System.Drawing.Point(0, 0);
            this.toolStripCalendar.Name = "toolStripCalendar";
            this.toolStripCalendar.Size = new System.Drawing.Size(411, 25);
            this.toolStripCalendar.TabIndex = 0;
            this.toolStripCalendar.Text = "toolStrip1";
            // 
            // toolStripButtonAdd
            // 
            this.toolStripButtonAdd.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonAdd.Image = global::WBOffice4.Properties.Resources.add;
            this.toolStripButtonAdd.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonAdd.Name = "toolStripButtonAdd";
            this.toolStripButtonAdd.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonAdd.ToolTipText = "Agregar una calendarizacin para la publicación actual";
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonEdit
            // 
            this.toolStripButtonEdit.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonEdit.Enabled = false;
            this.toolStripButtonEdit.Image = global::WBOffice4.Properties.Resources.edit;
            this.toolStripButtonEdit.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonEdit.Name = "toolStripButtonEdit";
            this.toolStripButtonEdit.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonEdit.ToolTipText = "Editar la calendarización seleccionada";
            // 
            // toolStripSeparator2
            // 
            this.toolStripSeparator2.Name = "toolStripSeparator2";
            this.toolStripSeparator2.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButton2
            // 
            this.toolStripButton2.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButton2.Enabled = false;
            this.toolStripButton2.Image = global::WBOffice4.Properties.Resources.delete;
            this.toolStripButton2.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton2.Name = "toolStripButton2";
            this.toolStripButton2.Size = new System.Drawing.Size(23, 22);
            this.toolStripButton2.ToolTipText = "Eliminar la calendarización seleccionada";
            // 
            // FormEditPorlet
            // 
            this.AcceptButton = this.buttonOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.buttonCancel;
            this.ClientSize = new System.Drawing.Size(419, 286);
            this.Controls.Add(this.tabControlProperties);
            this.Controls.Add(this.panel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FormEditPorlet";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Editar Propiedades";
            this.panel1.ResumeLayout(false);
            this.tabControlProperties.ResumeLayout(false);
            this.tabPageInformation.ResumeLayout(false);
            this.tabPageInformation.PerformLayout();
            this.tabPageCalendar.ResumeLayout(false);
            this.tabPageCalendar.PerformLayout();
            this.toolStripCalendar.ResumeLayout(false);
            this.toolStripCalendar.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button buttonOK;
        private System.Windows.Forms.Button buttonCancel;
        private System.Windows.Forms.TabControl tabControlProperties;
        private System.Windows.Forms.TabPage tabPageInformation;
        private System.Windows.Forms.ComboBox comboBoxVersiones;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.CheckBox checkBoxActive;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label labelPage;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label labelSite;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox textBoxDescription;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBoxTitle;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TabPage tabPageProperties;
        private System.Windows.Forms.TabPage tabPageCalendar;
        private System.Windows.Forms.ListView listViewCalendar;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ToolStrip toolStripCalendar;
        private System.Windows.Forms.ToolStripButton toolStripButtonAdd;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripButton toolStripButtonEdit;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
        private System.Windows.Forms.ToolStripButton toolStripButton2;
    }
}