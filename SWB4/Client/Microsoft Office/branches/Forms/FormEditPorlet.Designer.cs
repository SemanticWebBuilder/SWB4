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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormEditPorlet));
            this.panel1 = new System.Windows.Forms.Panel();
            this.buttonSenttoAuthorize = new System.Windows.Forms.Button();
            this.buttonOK = new System.Windows.Forms.Button();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.tabControlProperties = new System.Windows.Forms.TabControl();
            this.tabPageInformation = new System.Windows.Forms.TabPage();
            this.checkBoxActivePag = new System.Windows.Forms.CheckBox();
            this.buttonMove = new System.Windows.Forms.Button();
            this.dateTimePickerEndDate = new System.Windows.Forms.DateTimePicker();
            this.checkBoxEndDate = new System.Windows.Forms.CheckBox();
            this.label7 = new System.Windows.Forms.Label();
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
            this.propertyEditor1 = new Editor.PropertyEditor();
            this.tabPageCalendar = new System.Windows.Forms.TabPage();
            this.listViewCalendar = new System.Windows.Forms.ListView();
            this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader2 = new System.Windows.Forms.ColumnHeader();
            this.toolStripCalendar = new System.Windows.Forms.ToolStrip();
            this.toolStripButtonAdd = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonDelete = new System.Windows.Forms.ToolStripButton();
            this.tabPageRules = new System.Windows.Forms.TabPage();
            this.listViewRules = new System.Windows.Forms.ListView();
            this.columnHeader3 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader5 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader4 = new System.Windows.Forms.ColumnHeader();
            this.toolStripContainer1 = new System.Windows.Forms.ToolStripContainer();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.toolStripButtonAddRule = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonActivate = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator3 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonDeleteRule = new System.Windows.Forms.ToolStripButton();
            this.tabPageTitles = new System.Windows.Forms.TabPage();
            this.titleEditor1 = new Editor.TitleEditor();
            this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
            this.labelPagAct = new System.Windows.Forms.Label();
            this.panel1.SuspendLayout();
            this.tabControlProperties.SuspendLayout();
            this.tabPageInformation.SuspendLayout();
            this.tabPageProperties.SuspendLayout();
            this.tabPageCalendar.SuspendLayout();
            this.toolStripCalendar.SuspendLayout();
            this.tabPageRules.SuspendLayout();
            this.toolStripContainer1.ContentPanel.SuspendLayout();
            this.toolStripContainer1.SuspendLayout();
            this.toolStrip1.SuspendLayout();
            this.tabPageTitles.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.buttonSenttoAuthorize);
            this.panel1.Controls.Add(this.buttonOK);
            this.panel1.Controls.Add(this.buttonCancel);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 327);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(443, 42);
            this.panel1.TabIndex = 1;
            // 
            // buttonSenttoAuthorize
            // 
            this.buttonSenttoAuthorize.Location = new System.Drawing.Point(88, 7);
            this.buttonSenttoAuthorize.Name = "buttonSenttoAuthorize";
            this.buttonSenttoAuthorize.Size = new System.Drawing.Size(122, 23);
            this.buttonSenttoAuthorize.TabIndex = 2;
            this.buttonSenttoAuthorize.Text = "Enviar a autorización";
            this.buttonSenttoAuthorize.UseVisualStyleBackColor = true;
            this.buttonSenttoAuthorize.Click += new System.EventHandler(this.buttonSenttoAuthorize_Click);
            // 
            // buttonOK
            // 
            this.buttonOK.Location = new System.Drawing.Point(242, 7);
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
            this.buttonCancel.Location = new System.Drawing.Point(332, 6);
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
            this.tabControlProperties.Controls.Add(this.tabPageRules);
            this.tabControlProperties.Controls.Add(this.tabPageTitles);
            this.tabControlProperties.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControlProperties.Location = new System.Drawing.Point(0, 0);
            this.tabControlProperties.Name = "tabControlProperties";
            this.tabControlProperties.SelectedIndex = 0;
            this.tabControlProperties.Size = new System.Drawing.Size(443, 327);
            this.tabControlProperties.TabIndex = 2;
            // 
            // tabPageInformation
            // 
            this.tabPageInformation.Controls.Add(this.labelPagAct);
            this.tabPageInformation.Controls.Add(this.checkBoxActivePag);
            this.tabPageInformation.Controls.Add(this.buttonMove);
            this.tabPageInformation.Controls.Add(this.dateTimePickerEndDate);
            this.tabPageInformation.Controls.Add(this.checkBoxEndDate);
            this.tabPageInformation.Controls.Add(this.label7);
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
            this.tabPageInformation.Size = new System.Drawing.Size(435, 301);
            this.tabPageInformation.TabIndex = 0;
            this.tabPageInformation.Text = "Información";
            this.tabPageInformation.UseVisualStyleBackColor = true;
            // 
            // checkBoxActivePag
            // 
            this.checkBoxActivePag.AutoSize = true;
            this.checkBoxActivePag.Location = new System.Drawing.Point(131, 270);
            this.checkBoxActivePag.Name = "checkBoxActivePag";
            this.checkBoxActivePag.Size = new System.Drawing.Size(15, 14);
            this.checkBoxActivePag.TabIndex = 16;
            this.checkBoxActivePag.UseVisualStyleBackColor = true;
            // 
            // buttonMove
            // 
            this.buttonMove.Location = new System.Drawing.Point(159, 263);
            this.buttonMove.Name = "buttonMove";
            this.buttonMove.Size = new System.Drawing.Size(75, 27);
            this.buttonMove.TabIndex = 15;
            this.buttonMove.Text = "Mover";
            this.buttonMove.UseVisualStyleBackColor = true;
            this.buttonMove.Click += new System.EventHandler(this.buttonMove_Click);
            // 
            // dateTimePickerEndDate
            // 
            this.dateTimePickerEndDate.CustomFormat = "dd/MM/yyyy";
            this.dateTimePickerEndDate.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.dateTimePickerEndDate.Location = new System.Drawing.Point(281, 168);
            this.dateTimePickerEndDate.Name = "dateTimePickerEndDate";
            this.dateTimePickerEndDate.Size = new System.Drawing.Size(122, 20);
            this.dateTimePickerEndDate.TabIndex = 14;
            // 
            // checkBoxEndDate
            // 
            this.checkBoxEndDate.AutoSize = true;
            this.checkBoxEndDate.Location = new System.Drawing.Point(131, 168);
            this.checkBoxEndDate.Name = "checkBoxEndDate";
            this.checkBoxEndDate.Size = new System.Drawing.Size(103, 17);
            this.checkBoxEndDate.TabIndex = 13;
            this.checkBoxEndDate.Text = "Activar Vigencia";
            this.checkBoxEndDate.UseVisualStyleBackColor = true;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(10, 168);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(51, 13);
            this.label7.TabIndex = 12;
            this.label7.Text = "Vigencia:";
            // 
            // comboBoxVersiones
            // 
            this.comboBoxVersiones.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxVersiones.FormattingEnabled = true;
            this.comboBoxVersiones.Location = new System.Drawing.Point(131, 135);
            this.comboBoxVersiones.Name = "comboBoxVersiones";
            this.comboBoxVersiones.Size = new System.Drawing.Size(272, 21);
            this.comboBoxVersiones.TabIndex = 11;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(8, 138);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(91, 13);
            this.label6.TabIndex = 10;
            this.label6.Text = "Versión a mostrar:";
            // 
            // checkBoxActive
            // 
            this.checkBoxActive.AutoSize = true;
            this.checkBoxActive.Location = new System.Drawing.Point(131, 106);
            this.checkBoxActive.Name = "checkBoxActive";
            this.checkBoxActive.Size = new System.Drawing.Size(15, 14);
            this.checkBoxActive.TabIndex = 9;
            this.checkBoxActive.UseVisualStyleBackColor = true;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(8, 106);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(90, 13);
            this.label4.TabIndex = 8;
            this.label4.Text = "Contenido activo:";
            // 
            // labelPage
            // 
            this.labelPage.AutoEllipsis = true;
            this.labelPage.Location = new System.Drawing.Point(128, 232);
            this.labelPage.Name = "labelPage";
            this.labelPage.Size = new System.Drawing.Size(275, 13);
            this.labelPage.TabIndex = 7;
            this.labelPage.Text = "Sitio de prueba";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(8, 232);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(43, 13);
            this.label5.TabIndex = 6;
            this.label5.Text = "Página:";
            // 
            // labelSite
            // 
            this.labelSite.AutoEllipsis = true;
            this.labelSite.Location = new System.Drawing.Point(128, 203);
            this.labelSite.Name = "labelSite";
            this.labelSite.Size = new System.Drawing.Size(275, 13);
            this.labelSite.TabIndex = 5;
            this.labelSite.Text = "Sitio de prueba ";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(8, 203);
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
            this.label1.Size = new System.Drawing.Size(95, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Título por defecto:";
            // 
            // tabPageProperties
            // 
            this.tabPageProperties.Controls.Add(this.propertyEditor1);
            this.tabPageProperties.Location = new System.Drawing.Point(4, 22);
            this.tabPageProperties.Name = "tabPageProperties";
            this.tabPageProperties.Padding = new System.Windows.Forms.Padding(3);
            this.tabPageProperties.Size = new System.Drawing.Size(435, 247);
            this.tabPageProperties.TabIndex = 1;
            this.tabPageProperties.Text = "Propiedades de publicación";
            this.tabPageProperties.UseVisualStyleBackColor = true;
            // 
            // propertyEditor1
            // 
            this.propertyEditor1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.propertyEditor1.Location = new System.Drawing.Point(3, 3);
            this.propertyEditor1.Name = "propertyEditor1";
            this.propertyEditor1.Properties = null;
            this.propertyEditor1.Size = new System.Drawing.Size(429, 241);
            this.propertyEditor1.TabIndex = 0;
            this.propertyEditor1.Values = new string[0];
            // 
            // tabPageCalendar
            // 
            this.tabPageCalendar.Controls.Add(this.listViewCalendar);
            this.tabPageCalendar.Controls.Add(this.toolStripCalendar);
            this.tabPageCalendar.Location = new System.Drawing.Point(4, 22);
            this.tabPageCalendar.Name = "tabPageCalendar";
            this.tabPageCalendar.Size = new System.Drawing.Size(435, 247);
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
            this.listViewCalendar.FullRowSelect = true;
            this.listViewCalendar.Location = new System.Drawing.Point(0, 25);
            this.listViewCalendar.Name = "listViewCalendar";
            this.listViewCalendar.Size = new System.Drawing.Size(435, 222);
            this.listViewCalendar.TabIndex = 1;
            this.listViewCalendar.UseCompatibleStateImageBehavior = false;
            this.listViewCalendar.View = System.Windows.Forms.View.Details;
            this.listViewCalendar.SelectedIndexChanged += new System.EventHandler(this.listViewCalendar_SelectedIndexChanged);
            this.listViewCalendar.KeyUp += new System.Windows.Forms.KeyEventHandler(this.listViewCalendar_KeyUp);
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
            this.toolStripSeparator2,
            this.toolStripButtonDelete});
            this.toolStripCalendar.Location = new System.Drawing.Point(0, 0);
            this.toolStripCalendar.Name = "toolStripCalendar";
            this.toolStripCalendar.Size = new System.Drawing.Size(435, 25);
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
            this.toolStripButtonAdd.Click += new System.EventHandler(this.toolStripButtonAdd_Click);
            // 
            // toolStripSeparator2
            // 
            this.toolStripSeparator2.Name = "toolStripSeparator2";
            this.toolStripSeparator2.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonDelete
            // 
            this.toolStripButtonDelete.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonDelete.Enabled = false;
            this.toolStripButtonDelete.Image = global::WBOffice4.Properties.Resources.delete;
            this.toolStripButtonDelete.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonDelete.Name = "toolStripButtonDelete";
            this.toolStripButtonDelete.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonDelete.ToolTipText = "Eliminar la calendarización seleccionada";
            this.toolStripButtonDelete.Click += new System.EventHandler(this.toolStripButtonDelete_Click);
            // 
            // tabPageRules
            // 
            this.tabPageRules.Controls.Add(this.listViewRules);
            this.tabPageRules.Controls.Add(this.toolStripContainer1);
            this.tabPageRules.Location = new System.Drawing.Point(4, 22);
            this.tabPageRules.Name = "tabPageRules";
            this.tabPageRules.Padding = new System.Windows.Forms.Padding(3);
            this.tabPageRules.Size = new System.Drawing.Size(435, 247);
            this.tabPageRules.TabIndex = 3;
            this.tabPageRules.Text = "Reglas / Roles ó Grupos de Usuarios";
            this.tabPageRules.UseVisualStyleBackColor = true;
            // 
            // listViewRules
            // 
            this.listViewRules.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader3,
            this.columnHeader5,
            this.columnHeader4});
            this.listViewRules.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listViewRules.FullRowSelect = true;
            this.listViewRules.Location = new System.Drawing.Point(3, 30);
            this.listViewRules.Name = "listViewRules";
            this.listViewRules.Size = new System.Drawing.Size(429, 214);
            this.listViewRules.TabIndex = 3;
            this.listViewRules.UseCompatibleStateImageBehavior = false;
            this.listViewRules.View = System.Windows.Forms.View.Details;
            this.listViewRules.SelectedIndexChanged += new System.EventHandler(this.listViewRules_SelectedIndexChanged);
            this.listViewRules.KeyUp += new System.Windows.Forms.KeyEventHandler(this.listView1_KeyUp);
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Título";
            this.columnHeader3.Width = 200;
            // 
            // columnHeader5
            // 
            this.columnHeader5.DisplayIndex = 2;
            this.columnHeader5.Text = "Tipo";
            this.columnHeader5.Width = 120;
            // 
            // columnHeader4
            // 
            this.columnHeader4.DisplayIndex = 1;
            this.columnHeader4.Text = "Activo";
            this.columnHeader4.Width = 100;
            // 
            // toolStripContainer1
            // 
            this.toolStripContainer1.BottomToolStripPanelVisible = false;
            // 
            // toolStripContainer1.ContentPanel
            // 
            this.toolStripContainer1.ContentPanel.Controls.Add(this.toolStrip1);
            this.toolStripContainer1.ContentPanel.Size = new System.Drawing.Size(429, 2);
            this.toolStripContainer1.Dock = System.Windows.Forms.DockStyle.Top;
            this.toolStripContainer1.LeftToolStripPanelVisible = false;
            this.toolStripContainer1.Location = new System.Drawing.Point(3, 3);
            this.toolStripContainer1.Name = "toolStripContainer1";
            this.toolStripContainer1.RightToolStripPanelVisible = false;
            this.toolStripContainer1.Size = new System.Drawing.Size(429, 27);
            this.toolStripContainer1.TabIndex = 0;
            this.toolStripContainer1.Text = "toolStripContainer1";
            // 
            // toolStrip1
            // 
            this.toolStrip1.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButtonAddRule,
            this.toolStripSeparator1,
            this.toolStripButtonActivate,
            this.toolStripSeparator3,
            this.toolStripButtonDeleteRule});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(429, 25);
            this.toolStrip1.TabIndex = 1;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // toolStripButtonAddRule
            // 
            this.toolStripButtonAddRule.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonAddRule.Image = global::WBOffice4.Properties.Resources.add;
            this.toolStripButtonAddRule.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonAddRule.Name = "toolStripButtonAddRule";
            this.toolStripButtonAddRule.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonAddRule.ToolTipText = "Agregar una calendarizacin para la publicación actual";
            this.toolStripButtonAddRule.Click += new System.EventHandler(this.toolStripButtonAddRule_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonActivate
            // 
            this.toolStripButtonActivate.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.toolStripButtonActivate.Enabled = false;
            this.toolStripButtonActivate.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButtonActivate.Image")));
            this.toolStripButtonActivate.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonActivate.Name = "toolStripButtonActivate";
            this.toolStripButtonActivate.Size = new System.Drawing.Size(45, 22);
            this.toolStripButtonActivate.Text = "Activar";
            this.toolStripButtonActivate.ToolTipText = "Activar o desactivar regla, rol o grupo";
            this.toolStripButtonActivate.Click += new System.EventHandler(this.toolStripButtonActivate_Click);
            // 
            // toolStripSeparator3
            // 
            this.toolStripSeparator3.Name = "toolStripSeparator3";
            this.toolStripSeparator3.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonDeleteRule
            // 
            this.toolStripButtonDeleteRule.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButtonDeleteRule.Enabled = false;
            this.toolStripButtonDeleteRule.Image = global::WBOffice4.Properties.Resources.delete;
            this.toolStripButtonDeleteRule.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonDeleteRule.Name = "toolStripButtonDeleteRule";
            this.toolStripButtonDeleteRule.Size = new System.Drawing.Size(23, 22);
            this.toolStripButtonDeleteRule.ToolTipText = "Eliminar la calendarización seleccionada";
            this.toolStripButtonDeleteRule.Click += new System.EventHandler(this.toolStripButtonDeleteRule_Click);
            // 
            // tabPageTitles
            // 
            this.tabPageTitles.Controls.Add(this.titleEditor1);
            this.tabPageTitles.Location = new System.Drawing.Point(4, 22);
            this.tabPageTitles.Name = "tabPageTitles";
            this.tabPageTitles.Size = new System.Drawing.Size(435, 247);
            this.tabPageTitles.TabIndex = 4;
            this.tabPageTitles.Text = "Títulos de la página";
            this.tabPageTitles.UseVisualStyleBackColor = true;
            // 
            // titleEditor1
            // 
            this.titleEditor1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.titleEditor1.Languages = null;
            this.titleEditor1.Location = new System.Drawing.Point(0, 0);
            this.titleEditor1.Name = "titleEditor1";
            this.titleEditor1.Size = new System.Drawing.Size(435, 247);
            this.titleEditor1.TabIndex = 0;
            this.titleEditor1.Titles = new string[0];
            // 
            // labelPagAct
            // 
            this.labelPagAct.AutoSize = true;
            this.labelPagAct.Location = new System.Drawing.Point(10, 270);
            this.labelPagAct.Name = "labelPagAct";
            this.labelPagAct.Size = new System.Drawing.Size(75, 13);
            this.labelPagAct.TabIndex = 17;
            this.labelPagAct.Text = "Página activa:";
            // 
            // FormEditPorlet
            // 
            this.AcceptButton = this.buttonOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.buttonCancel;
            this.ClientSize = new System.Drawing.Size(443, 369);
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
            this.tabPageProperties.ResumeLayout(false);
            this.tabPageCalendar.ResumeLayout(false);
            this.tabPageCalendar.PerformLayout();
            this.toolStripCalendar.ResumeLayout(false);
            this.toolStripCalendar.PerformLayout();
            this.tabPageRules.ResumeLayout(false);
            this.toolStripContainer1.ContentPanel.ResumeLayout(false);
            this.toolStripContainer1.ContentPanel.PerformLayout();
            this.toolStripContainer1.ResumeLayout(false);
            this.toolStripContainer1.PerformLayout();
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.tabPageTitles.ResumeLayout(false);
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
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
        private System.Windows.Forms.ToolStripButton toolStripButtonDelete;
        private System.Windows.Forms.ToolTip toolTip1;
        private System.Windows.Forms.DateTimePicker dateTimePickerEndDate;
        private System.Windows.Forms.CheckBox checkBoxEndDate;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TabPage tabPageRules;
        private System.Windows.Forms.Button buttonMove;
        private System.Windows.Forms.CheckBox checkBoxActivePag;
        private Editor.PropertyEditor propertyEditor1;
        private System.Windows.Forms.Button buttonSenttoAuthorize;
        private System.Windows.Forms.TabPage tabPageTitles;
        private Editor.TitleEditor titleEditor1;
        private System.Windows.Forms.ToolStripContainer toolStripContainer1;
        private System.Windows.Forms.ListView listViewRules;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.ColumnHeader columnHeader4;
        private System.Windows.Forms.ColumnHeader columnHeader5;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripButton toolStripButtonAddRule;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripButton toolStripButtonDeleteRule;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator3;
        private System.Windows.Forms.ToolStripButton toolStripButtonActivate;
        private System.Windows.Forms.Label labelPagAct;
    }
}