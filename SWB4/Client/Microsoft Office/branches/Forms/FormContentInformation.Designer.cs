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
    partial class FormContentInformation
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormContentInformation));
            this.panel1 = new System.Windows.Forms.Panel();
            this.buttonUpdate = new System.Windows.Forms.Button();
            this.buttonClose = new System.Windows.Forms.Button();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPageInformation = new System.Windows.Forms.TabPage();
            this.comboBoxVersiones = new System.Windows.Forms.ComboBox();
            this.label5 = new System.Windows.Forms.Label();
            this.labelLasUpdate = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.textBoxDescription = new System.Windows.Forms.TextBox();
            this.textBoxTitle = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.tabPagePublish = new System.Windows.Forms.TabPage();
            this.listViewPages = new System.Windows.Forms.ListView();
            this.columnHeader4 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader5 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader6 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader9 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader10 = new System.Windows.Forms.ColumnHeader();
            this.toolStripPublication = new System.Windows.Forms.ToolStrip();
            this.toolStripButtonEdit = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonPublish = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator3 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonSeePage = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator4 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonDeletePage = new System.Windows.Forms.ToolStripButton();
            this.tabPageVersions = new System.Windows.Forms.TabPage();
            this.listViewVersions = new System.Windows.Forms.ListView();
            this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader2 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader3 = new System.Windows.Forms.ColumnHeader();
            this.toolStripVersions = new System.Windows.Forms.ToolStrip();
            this.toolStripButtonSave = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonViewVersion = new System.Windows.Forms.ToolStripButton();
            this.toolStripButtonDeleteVersion = new System.Windows.Forms.ToolStripButton();
            this.tabPageProperties = new System.Windows.Forms.TabPage();
            this.propertyEditor1 = new Editor.PropertyEditor();
            this.panel1.SuspendLayout();
            this.tabControl1.SuspendLayout();
            this.tabPageInformation.SuspendLayout();
            this.tabPagePublish.SuspendLayout();
            this.toolStripPublication.SuspendLayout();
            this.tabPageVersions.SuspendLayout();
            this.toolStripVersions.SuspendLayout();
            this.tabPageProperties.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.buttonUpdate);
            this.panel1.Controls.Add(this.buttonClose);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 237);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(494, 31);
            this.panel1.TabIndex = 0;
            // 
            // buttonUpdate
            // 
            this.buttonUpdate.Location = new System.Drawing.Point(326, 6);
            this.buttonUpdate.Name = "buttonUpdate";
            this.buttonUpdate.Size = new System.Drawing.Size(75, 23);
            this.buttonUpdate.TabIndex = 1;
            this.buttonUpdate.Text = "Actualizar";
            this.buttonUpdate.UseVisualStyleBackColor = true;
            this.buttonUpdate.Click += new System.EventHandler(this.buttonUpdate_Click);
            // 
            // buttonClose
            // 
            this.buttonClose.Location = new System.Drawing.Point(407, 5);
            this.buttonClose.Name = "buttonClose";
            this.buttonClose.Size = new System.Drawing.Size(75, 23);
            this.buttonClose.TabIndex = 0;
            this.buttonClose.Text = "Cerrar";
            this.buttonClose.UseVisualStyleBackColor = true;
            this.buttonClose.Click += new System.EventHandler(this.buttonClose_Click);
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPageInformation);
            this.tabControl1.Controls.Add(this.tabPagePublish);
            this.tabControl1.Controls.Add(this.tabPageVersions);
            this.tabControl1.Controls.Add(this.tabPageProperties);
            this.tabControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl1.Location = new System.Drawing.Point(0, 0);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(494, 237);
            this.tabControl1.TabIndex = 1;
            // 
            // tabPageInformation
            // 
            this.tabPageInformation.Controls.Add(this.comboBoxVersiones);
            this.tabPageInformation.Controls.Add(this.label5);
            this.tabPageInformation.Controls.Add(this.labelLasUpdate);
            this.tabPageInformation.Controls.Add(this.label3);
            this.tabPageInformation.Controls.Add(this.textBoxDescription);
            this.tabPageInformation.Controls.Add(this.textBoxTitle);
            this.tabPageInformation.Controls.Add(this.label2);
            this.tabPageInformation.Controls.Add(this.label1);
            this.tabPageInformation.Location = new System.Drawing.Point(4, 22);
            this.tabPageInformation.Name = "tabPageInformation";
            this.tabPageInformation.Padding = new System.Windows.Forms.Padding(3);
            this.tabPageInformation.Size = new System.Drawing.Size(486, 211);
            this.tabPageInformation.TabIndex = 0;
            this.tabPageInformation.Text = "Información del contenido";
            this.tabPageInformation.UseVisualStyleBackColor = true;
            // 
            // comboBoxVersiones
            // 
            this.comboBoxVersiones.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxVersiones.FormattingEnabled = true;
            this.comboBoxVersiones.Location = new System.Drawing.Point(145, 168);
            this.comboBoxVersiones.Name = "comboBoxVersiones";
            this.comboBoxVersiones.Size = new System.Drawing.Size(283, 21);
            this.comboBoxVersiones.TabIndex = 7;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(11, 171);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(55, 13);
            this.label5.TabIndex = 6;
            this.label5.Text = "Categoria:";
            // 
            // labelLasUpdate
            // 
            this.labelLasUpdate.AutoSize = true;
            this.labelLasUpdate.Location = new System.Drawing.Point(142, 143);
            this.labelLasUpdate.Name = "labelLasUpdate";
            this.labelLasUpdate.Size = new System.Drawing.Size(71, 13);
            this.labelLasUpdate.TabIndex = 5;
            this.labelLasUpdate.Text = "No disponible";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(11, 143);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(120, 13);
            this.label3.TabIndex = 4;
            this.label3.Text = "Fecha de actualización:";
            // 
            // textBoxDescription
            // 
            this.textBoxDescription.Location = new System.Drawing.Point(145, 50);
            this.textBoxDescription.Multiline = true;
            this.textBoxDescription.Name = "textBoxDescription";
            this.textBoxDescription.Size = new System.Drawing.Size(283, 80);
            this.textBoxDescription.TabIndex = 3;
            // 
            // textBoxTitle
            // 
            this.textBoxTitle.Location = new System.Drawing.Point(145, 10);
            this.textBoxTitle.Name = "textBoxTitle";
            this.textBoxTitle.Size = new System.Drawing.Size(283, 20);
            this.textBoxTitle.TabIndex = 2;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(11, 50);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(66, 13);
            this.label2.TabIndex = 1;
            this.label2.Text = "Descripción:";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(11, 18);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(38, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Título:";
            // 
            // tabPagePublish
            // 
            this.tabPagePublish.Controls.Add(this.listViewPages);
            this.tabPagePublish.Controls.Add(this.toolStripPublication);
            this.tabPagePublish.Location = new System.Drawing.Point(4, 22);
            this.tabPagePublish.Name = "tabPagePublish";
            this.tabPagePublish.Padding = new System.Windows.Forms.Padding(3);
            this.tabPagePublish.Size = new System.Drawing.Size(486, 211);
            this.tabPagePublish.TabIndex = 1;
            this.tabPagePublish.Text = "Información de publicación";
            this.tabPagePublish.UseVisualStyleBackColor = true;
            // 
            // listViewPages
            // 
            this.listViewPages.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader4,
            this.columnHeader5,
            this.columnHeader6,
            this.columnHeader9,
            this.columnHeader10});
            this.listViewPages.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listViewPages.FullRowSelect = true;
            this.listViewPages.Location = new System.Drawing.Point(3, 28);
            this.listViewPages.Name = "listViewPages";
            this.listViewPages.Size = new System.Drawing.Size(480, 180);
            this.listViewPages.TabIndex = 1;
            this.listViewPages.UseCompatibleStateImageBehavior = false;
            this.listViewPages.View = System.Windows.Forms.View.Details;
            this.listViewPages.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.listViewPages_MouseDoubleClick);
            this.listViewPages.SelectedIndexChanged += new System.EventHandler(this.listViewPages_SelectedIndexChanged);
            this.listViewPages.KeyUp += new System.Windows.Forms.KeyEventHandler(this.listViewPages_KeyUp);
            // 
            // columnHeader4
            // 
            this.columnHeader4.Text = "Título";
            this.columnHeader4.Width = 110;
            // 
            // columnHeader5
            // 
            this.columnHeader5.Text = "Sitio";
            this.columnHeader5.Width = 110;
            // 
            // columnHeader6
            // 
            this.columnHeader6.Text = "Página";
            this.columnHeader6.Width = 110;
            // 
            // columnHeader9
            // 
            this.columnHeader9.Text = "Activo";
            // 
            // columnHeader10
            // 
            this.columnHeader10.Text = "Versión";
            this.columnHeader10.Width = 70;
            // 
            // toolStripPublication
            // 
            this.toolStripPublication.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStripPublication.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButtonEdit,
            this.toolStripSeparator1,
            this.toolStripButtonPublish,
            this.toolStripSeparator3,
            this.toolStripButtonSeePage,
            this.toolStripSeparator4,
            this.toolStripButtonDeletePage});
            this.toolStripPublication.Location = new System.Drawing.Point(3, 3);
            this.toolStripPublication.Name = "toolStripPublication";
            this.toolStripPublication.RenderMode = System.Windows.Forms.ToolStripRenderMode.Professional;
            this.toolStripPublication.Size = new System.Drawing.Size(480, 25);
            this.toolStripPublication.TabIndex = 0;
            this.toolStripPublication.Text = "toolStrip1";
            // 
            // toolStripButtonEdit
            // 
            this.toolStripButtonEdit.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonEdit.Enabled = false;
            this.toolStripButtonEdit.Image = global::WBOffice4.Properties.Resources.edit;
            this.toolStripButtonEdit.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonEdit.Name = "toolStripButtonEdit";
            this.toolStripButtonEdit.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonEdit.Text = "Editar Propiedades";
            this.toolStripButtonEdit.Click += new System.EventHandler(this.toolStripButtonEdit_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonPublish
            // 
            this.toolStripButtonPublish.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonPublish.Image = global::WBOffice4.Properties.Resources.envpaga;
            this.toolStripButtonPublish.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonPublish.Name = "toolStripButtonPublish";
            this.toolStripButtonPublish.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonPublish.Text = "toolStripButton2";
            this.toolStripButtonPublish.ToolTipText = "Publicar contenido";
            this.toolStripButtonPublish.Click += new System.EventHandler(this.toolStripButtonPublish_Click);
            // 
            // toolStripSeparator3
            // 
            this.toolStripSeparator3.Name = "toolStripSeparator3";
            this.toolStripSeparator3.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonSeePage
            // 
            this.toolStripButtonSeePage.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonSeePage.Enabled = false;
            this.toolStripButtonSeePage.Image = global::WBOffice4.Properties.Resources.see;
            this.toolStripButtonSeePage.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonSeePage.Name = "toolStripButtonSeePage";
            this.toolStripButtonSeePage.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonSeePage.Text = "toolStripButton5";
            this.toolStripButtonSeePage.ToolTipText = "Ver página";
            this.toolStripButtonSeePage.Click += new System.EventHandler(this.toolStripButtonSeePage_Click);
            // 
            // toolStripSeparator4
            // 
            this.toolStripSeparator4.Name = "toolStripSeparator4";
            this.toolStripSeparator4.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonDeletePage
            // 
            this.toolStripButtonDeletePage.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonDeletePage.Enabled = false;
            this.toolStripButtonDeletePage.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButtonDeletePage.Image")));
            this.toolStripButtonDeletePage.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonDeletePage.Name = "toolStripButtonDeletePage";
            this.toolStripButtonDeletePage.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonDeletePage.Text = "toolStripButton6";
            this.toolStripButtonDeletePage.ToolTipText = "Borrar publicación";
            this.toolStripButtonDeletePage.Click += new System.EventHandler(this.toolStripButtonDeletePage_Click);
            // 
            // tabPageVersions
            // 
            this.tabPageVersions.Controls.Add(this.listViewVersions);
            this.tabPageVersions.Controls.Add(this.toolStripVersions);
            this.tabPageVersions.Location = new System.Drawing.Point(4, 22);
            this.tabPageVersions.Name = "tabPageVersions";
            this.tabPageVersions.Size = new System.Drawing.Size(486, 211);
            this.tabPageVersions.TabIndex = 2;
            this.tabPageVersions.Text = "Versiones del contenido";
            this.tabPageVersions.UseVisualStyleBackColor = true;
            // 
            // listViewVersions
            // 
            this.listViewVersions.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2,
            this.columnHeader3});
            this.listViewVersions.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listViewVersions.FullRowSelect = true;
            this.listViewVersions.Location = new System.Drawing.Point(0, 25);
            this.listViewVersions.Name = "listViewVersions";
            this.listViewVersions.Size = new System.Drawing.Size(486, 186);
            this.listViewVersions.TabIndex = 1;
            this.listViewVersions.UseCompatibleStateImageBehavior = false;
            this.listViewVersions.View = System.Windows.Forms.View.Details;
            this.listViewVersions.SelectedIndexChanged += new System.EventHandler(this.listViewVersions_SelectedIndexChanged);
            this.listViewVersions.KeyUp += new System.Windows.Forms.KeyEventHandler(this.listViewVersions_KeyUp);
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "Version";
            this.columnHeader1.Width = 120;
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "Fecha de creación";
            this.columnHeader2.Width = 150;
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Creador";
            this.columnHeader3.Width = 150;
            // 
            // toolStripVersions
            // 
            this.toolStripVersions.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStripVersions.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButtonSave,
            this.toolStripSeparator2,
            this.toolStripButtonViewVersion,
            this.toolStripButtonDeleteVersion});
            this.toolStripVersions.Location = new System.Drawing.Point(0, 0);
            this.toolStripVersions.Name = "toolStripVersions";
            this.toolStripVersions.RenderMode = System.Windows.Forms.ToolStripRenderMode.Professional;
            this.toolStripVersions.Size = new System.Drawing.Size(486, 25);
            this.toolStripVersions.TabIndex = 0;
            this.toolStripVersions.Text = "toolStrip2";
            // 
            // toolStripButtonSave
            // 
            this.toolStripButtonSave.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonSave.Image = global::WBOffice4.Properties.Resources.save;
            this.toolStripButtonSave.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonSave.Name = "toolStripButtonSave";
            this.toolStripButtonSave.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonSave.Text = "toolStripButton3";
            this.toolStripButtonSave.Click += new System.EventHandler(this.toolStripButtonSave_Click);
            // 
            // toolStripSeparator2
            // 
            this.toolStripSeparator2.Name = "toolStripSeparator2";
            this.toolStripSeparator2.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonViewVersion
            // 
            this.toolStripButtonViewVersion.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonViewVersion.Enabled = false;
            this.toolStripButtonViewVersion.Image = global::WBOffice4.Properties.Resources.see;
            this.toolStripButtonViewVersion.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonViewVersion.Name = "toolStripButtonViewVersion";
            this.toolStripButtonViewVersion.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonViewVersion.Text = "toolStripButton4";
            this.toolStripButtonViewVersion.Click += new System.EventHandler(this.toolStripButtonViewVersion_Click);
            // 
            // toolStripButtonDeleteVersion
            // 
            this.toolStripButtonDeleteVersion.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonDeleteVersion.Enabled = false;
            this.toolStripButtonDeleteVersion.Image = global::WBOffice4.Properties.Resources.delete;
            this.toolStripButtonDeleteVersion.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonDeleteVersion.Name = "toolStripButtonDeleteVersion";
            this.toolStripButtonDeleteVersion.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonDeleteVersion.Text = "toolStripButton1";
            this.toolStripButtonDeleteVersion.Click += new System.EventHandler(this.toolStripButtonDeleteVersion_Click);
            // 
            // tabPageProperties
            // 
            this.tabPageProperties.Controls.Add(this.propertyEditor1);
            this.tabPageProperties.Location = new System.Drawing.Point(4, 22);
            this.tabPageProperties.Name = "tabPageProperties";
            this.tabPageProperties.Size = new System.Drawing.Size(486, 211);
            this.tabPageProperties.TabIndex = 3;
            this.tabPageProperties.Text = "Propiedades";
            this.tabPageProperties.UseVisualStyleBackColor = true;
            // 
            // propertyEditor1
            // 
            this.propertyEditor1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.propertyEditor1.Location = new System.Drawing.Point(0, 0);
            this.propertyEditor1.Name = "propertyEditor1";
            this.propertyEditor1.Properties = null;
            this.propertyEditor1.Size = new System.Drawing.Size(486, 211);
            this.propertyEditor1.TabIndex = 0;
            this.propertyEditor1.Values = new string[0];
            // 
            // FormContentInformation
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(494, 268);
            this.Controls.Add(this.tabControl1);
            this.Controls.Add(this.panel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FormContentInformation";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Información del contenido";
            this.panel1.ResumeLayout(false);
            this.tabControl1.ResumeLayout(false);
            this.tabPageInformation.ResumeLayout(false);
            this.tabPageInformation.PerformLayout();
            this.tabPagePublish.ResumeLayout(false);
            this.tabPagePublish.PerformLayout();
            this.toolStripPublication.ResumeLayout(false);
            this.toolStripPublication.PerformLayout();
            this.tabPageVersions.ResumeLayout(false);
            this.tabPageVersions.PerformLayout();
            this.toolStripVersions.ResumeLayout(false);
            this.toolStripVersions.PerformLayout();
            this.tabPageProperties.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button buttonClose;
        private System.Windows.Forms.Button buttonUpdate;
        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPageInformation;
        private System.Windows.Forms.TabPage tabPagePublish;
        private System.Windows.Forms.TabPage tabPageVersions;
        private System.Windows.Forms.TextBox textBoxDescription;
        private System.Windows.Forms.TextBox textBoxTitle;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox comboBoxVersiones;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label labelLasUpdate;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.ToolStrip toolStripVersions;
        private System.Windows.Forms.ToolStripButton toolStripButtonSave;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
        private System.Windows.Forms.ToolStripButton toolStripButtonViewVersion;
        private System.Windows.Forms.ToolStrip toolStripPublication;
        private System.Windows.Forms.ToolStripButton toolStripButtonEdit;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripButton toolStripButtonPublish;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator3;
        private System.Windows.Forms.ToolStripButton toolStripButtonSeePage;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator4;
        private System.Windows.Forms.ToolStripButton toolStripButtonDeletePage;
        private System.Windows.Forms.ListView listViewVersions;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.ListView listViewPages;
        private System.Windows.Forms.ColumnHeader columnHeader4;
        private System.Windows.Forms.ColumnHeader columnHeader5;
        private System.Windows.Forms.ColumnHeader columnHeader6;
        private System.Windows.Forms.ColumnHeader columnHeader9;
        private System.Windows.Forms.ColumnHeader columnHeader10;
        private System.Windows.Forms.ToolStripButton toolStripButtonDeleteVersion;
        private System.Windows.Forms.TabPage tabPageProperties;
        private Editor.PropertyEditor propertyEditor1;
    }
}