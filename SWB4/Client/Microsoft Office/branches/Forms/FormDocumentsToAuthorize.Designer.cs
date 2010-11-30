/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.webbuilder.org.mx 
**/ 
 
﻿namespace WBOffice4.Forms
{
    partial class FormDocumentsToAuthorize
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormDocumentsToAuthorize));
            this.panel1 = new System.Windows.Forms.Panel();
            this.buttonOk = new System.Windows.Forms.Button();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.panel2 = new System.Windows.Forms.Panel();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.toolStripButtonSee = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonAuthorize = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonReject = new System.Windows.Forms.ToolStripButton();
            this.panel3 = new System.Windows.Forms.Panel();
            this.radioButtonForAuthorize = new System.Windows.Forms.RadioButton();
            this.radioButtonMyDocuments = new System.Windows.Forms.RadioButton();
            this.radioButtonAll = new System.Windows.Forms.RadioButton();
            this.comboBoxSites = new System.Windows.Forms.ComboBox();
            this.listViewFlows = new System.Windows.Forms.ListView();
            this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader2 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader3 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader4 = new System.Windows.Forms.ColumnHeader();
            this.panel1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.toolStrip1.SuspendLayout();
            this.panel3.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.buttonOk);
            this.panel1.Controls.Add(this.buttonCancel);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 256);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(477, 40);
            this.panel1.TabIndex = 0;
            // 
            // buttonOk
            // 
            this.buttonOk.Location = new System.Drawing.Point(309, 5);
            this.buttonOk.Name = "buttonOk";
            this.buttonOk.Size = new System.Drawing.Size(75, 23);
            this.buttonOk.TabIndex = 1;
            this.buttonOk.Text = "Aceptar";
            this.buttonOk.UseVisualStyleBackColor = true;
            this.buttonOk.Click += new System.EventHandler(this.buttonOk_Click);
            // 
            // buttonCancel
            // 
            this.buttonCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.buttonCancel.Location = new System.Drawing.Point(390, 5);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 0;
            this.buttonCancel.Text = "Cerrar";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.toolStrip1);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(477, 26);
            this.panel2.TabIndex = 1;
            // 
            // toolStrip1
            // 
            this.toolStrip1.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButtonSee,
            this.toolStripSeparator1,
            this.toolStripButtonAuthorize,
            this.toolStripSeparator2,
            this.toolStripButtonReject});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(477, 25);
            this.toolStrip1.TabIndex = 0;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // toolStripButtonSee
            // 
            this.toolStripButtonSee.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonSee.Enabled = false;
            this.toolStripButtonSee.Image = global::WBOffice4.Properties.Resources.see;
            this.toolStripButtonSee.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonSee.Name = "toolStripButtonSee";
            this.toolStripButtonSee.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonSee.Text = "toolStripButton1";
            this.toolStripButtonSee.Click += new System.EventHandler(this.toolStripButtonSee_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonAuthorize
            // 
            this.toolStripButtonAuthorize.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonAuthorize.Enabled = false;
            this.toolStripButtonAuthorize.Image = global::WBOffice4.Properties.Resources.icono_autorizar;
            this.toolStripButtonAuthorize.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonAuthorize.Name = "toolStripButtonAuthorize";
            this.toolStripButtonAuthorize.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonAuthorize.Text = "toolStripButton2";
            this.toolStripButtonAuthorize.Click += new System.EventHandler(this.toolStripButtonAuthorize_Click);
            // 
            // toolStripSeparator2
            // 
            this.toolStripSeparator2.Name = "toolStripSeparator2";
            this.toolStripSeparator2.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonReject
            // 
            this.toolStripButtonReject.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonReject.Enabled = false;
            this.toolStripButtonReject.Image = global::WBOffice4.Properties.Resources.icon_rechazar;
            this.toolStripButtonReject.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonReject.Name = "toolStripButtonReject";
            this.toolStripButtonReject.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonReject.Text = "toolStripButton3";
            this.toolStripButtonReject.Click += new System.EventHandler(this.toolStripButtonReject_Click);
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.radioButtonForAuthorize);
            this.panel3.Controls.Add(this.radioButtonMyDocuments);
            this.panel3.Controls.Add(this.radioButtonAll);
            this.panel3.Controls.Add(this.comboBoxSites);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel3.Location = new System.Drawing.Point(0, 26);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(477, 36);
            this.panel3.TabIndex = 3;
            // 
            // radioButtonForAuthorize
            // 
            this.radioButtonForAuthorize.AutoSize = true;
            this.radioButtonForAuthorize.Location = new System.Drawing.Point(363, 13);
            this.radioButtonForAuthorize.Name = "radioButtonForAuthorize";
            this.radioButtonForAuthorize.Size = new System.Drawing.Size(84, 17);
            this.radioButtonForAuthorize.TabIndex = 3;
            this.radioButtonForAuthorize.Text = "Por autorizar";
            this.radioButtonForAuthorize.UseVisualStyleBackColor = true;
            this.radioButtonForAuthorize.CheckedChanged += new System.EventHandler(this.radioButtonForAuthorize_CheckedChanged);
            // 
            // radioButtonMyDocuments
            // 
            this.radioButtonMyDocuments.AutoSize = true;
            this.radioButtonMyDocuments.Checked = true;
            this.radioButtonMyDocuments.Location = new System.Drawing.Point(255, 13);
            this.radioButtonMyDocuments.Name = "radioButtonMyDocuments";
            this.radioButtonMyDocuments.Size = new System.Drawing.Size(102, 17);
            this.radioButtonMyDocuments.TabIndex = 2;
            this.radioButtonMyDocuments.TabStop = true;
            this.radioButtonMyDocuments.Text = "Mis documentos";
            this.radioButtonMyDocuments.UseVisualStyleBackColor = true;
            this.radioButtonMyDocuments.CheckedChanged += new System.EventHandler(this.radioButtonMyDocuments_CheckedChanged);
            // 
            // radioButtonAll
            // 
            this.radioButtonAll.AutoSize = true;
            this.radioButtonAll.Location = new System.Drawing.Point(193, 13);
            this.radioButtonAll.Name = "radioButtonAll";
            this.radioButtonAll.Size = new System.Drawing.Size(55, 17);
            this.radioButtonAll.TabIndex = 1;
            this.radioButtonAll.Text = "Todos";
            this.radioButtonAll.UseVisualStyleBackColor = true;
            this.radioButtonAll.CheckedChanged += new System.EventHandler(this.radioButtonAll_CheckedChanged);
            // 
            // comboBoxSites
            // 
            this.comboBoxSites.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxSites.FormattingEnabled = true;
            this.comboBoxSites.Location = new System.Drawing.Point(3, 9);
            this.comboBoxSites.Name = "comboBoxSites";
            this.comboBoxSites.Size = new System.Drawing.Size(183, 21);
            this.comboBoxSites.TabIndex = 0;
            this.comboBoxSites.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
            // 
            // listViewFlows
            // 
            this.listViewFlows.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2,
            this.columnHeader3,
            this.columnHeader4});
            this.listViewFlows.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listViewFlows.FullRowSelect = true;
            this.listViewFlows.Location = new System.Drawing.Point(0, 62);
            this.listViewFlows.MultiSelect = false;
            this.listViewFlows.Name = "listViewFlows";
            this.listViewFlows.Size = new System.Drawing.Size(477, 194);
            this.listViewFlows.TabIndex = 4;
            this.listViewFlows.UseCompatibleStateImageBehavior = false;
            this.listViewFlows.View = System.Windows.Forms.View.Details;
            this.listViewFlows.SelectedIndexChanged += new System.EventHandler(this.listView1_SelectedIndexChanged);
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "Título";
            this.columnHeader1.Width = 120;
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "Página";
            this.columnHeader2.Width = 120;
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Paso";
            this.columnHeader3.Width = 120;
            // 
            // columnHeader4
            // 
            this.columnHeader4.Text = "Version";
            this.columnHeader4.Width = 100;
            // 
            // FormDocumentsToAuthorize
            // 
            this.AcceptButton = this.buttonOk;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.buttonCancel;
            this.ClientSize = new System.Drawing.Size(477, 296);
            this.Controls.Add(this.listViewFlows);
            this.Controls.Add(this.panel3);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FormDocumentsToAuthorize";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Documentos por autorizar";
            this.panel1.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.panel3.ResumeLayout(false);
            this.panel3.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripButton toolStripButtonSee;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripButton toolStripButtonAuthorize;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
        private System.Windows.Forms.ToolStripButton toolStripButtonReject;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.RadioButton radioButtonForAuthorize;
        private System.Windows.Forms.RadioButton radioButtonMyDocuments;
        private System.Windows.Forms.RadioButton radioButtonAll;
        private System.Windows.Forms.ComboBox comboBoxSites;
        private System.Windows.Forms.ListView listViewFlows;
        private System.Windows.Forms.Button buttonOk;
        private System.Windows.Forms.Button buttonCancel;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.ColumnHeader columnHeader4;
    }
}