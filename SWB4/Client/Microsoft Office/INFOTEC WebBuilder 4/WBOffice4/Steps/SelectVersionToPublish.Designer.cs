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
 
﻿namespace WBOffice4.Steps
{
    partial class SelectVersionToPublish
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
            this.panel1 = new System.Windows.Forms.Panel();
            this.radioButtonOneVersion = new System.Windows.Forms.RadioButton();
            this.radioButtonLastVersion = new System.Windows.Forms.RadioButton();
            this.listViewVersions = new System.Windows.Forms.ListView();
            this.columnHeader5 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader6 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader7 = new System.Windows.Forms.ColumnHeader();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // Description
            // 
            this.Description.Text = "Seleccione la versión a publicar";
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.radioButtonOneVersion);
            this.panel1.Controls.Add(this.radioButtonLastVersion);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(472, 37);
            this.panel1.TabIndex = 1;
            // 
            // radioButtonOneVersion
            // 
            this.radioButtonOneVersion.AutoSize = true;
            this.radioButtonOneVersion.Location = new System.Drawing.Point(240, 14);
            this.radioButtonOneVersion.Name = "radioButtonOneVersion";
            this.radioButtonOneVersion.Size = new System.Drawing.Size(118, 17);
            this.radioButtonOneVersion.TabIndex = 1;
            this.radioButtonOneVersion.Text = "Publica una versión";
            this.radioButtonOneVersion.UseVisualStyleBackColor = true;
            this.radioButtonOneVersion.CheckedChanged += new System.EventHandler(this.radioButtonOneVersion_CheckedChanged);
            // 
            // radioButtonLastVersion
            // 
            this.radioButtonLastVersion.AutoSize = true;
            this.radioButtonLastVersion.Checked = true;
            this.radioButtonLastVersion.Location = new System.Drawing.Point(80, 14);
            this.radioButtonLastVersion.Name = "radioButtonLastVersion";
            this.radioButtonLastVersion.Size = new System.Drawing.Size(138, 17);
            this.radioButtonLastVersion.TabIndex = 0;
            this.radioButtonLastVersion.TabStop = true;
            this.radioButtonLastVersion.Text = "Publica la última versión";
            this.radioButtonLastVersion.UseVisualStyleBackColor = true;
            this.radioButtonLastVersion.CheckedChanged += new System.EventHandler(this.radioButtonLastVersion_CheckedChanged);
            // 
            // listViewVersions
            // 
            this.listViewVersions.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader5,
            this.columnHeader6,
            this.columnHeader7});
            this.listViewVersions.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listViewVersions.Enabled = false;
            this.listViewVersions.FullRowSelect = true;
            this.listViewVersions.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
            this.listViewVersions.Location = new System.Drawing.Point(0, 37);
            this.listViewVersions.MultiSelect = false;
            this.listViewVersions.Name = "listViewVersions";
            this.listViewVersions.Size = new System.Drawing.Size(472, 199);
            this.listViewVersions.TabIndex = 2;
            this.listViewVersions.UseCompatibleStateImageBehavior = false;
            this.listViewVersions.View = System.Windows.Forms.View.Details;
            // 
            // columnHeader5
            // 
            this.columnHeader5.Text = "Versión";
            this.columnHeader5.Width = 100;
            // 
            // columnHeader6
            // 
            this.columnHeader6.Text = "Fecha de creación";
            this.columnHeader6.Width = 170;
            // 
            // columnHeader7
            // 
            this.columnHeader7.Text = "Creador";
            this.columnHeader7.Width = 150;
            // 
            // SelectVersionToPublish
            // 
            this.Controls.Add(this.listViewVersions);
            this.Controls.Add(this.panel1);
            this.Name = "SelectVersionToPublish";
            this.StepDescription = "Seleccione la versión a publicar";
            this.StepTitle = "Seleccione la versión a publicar";
            this.ShowStep += new TSWizards.ShowStepEventHandler(this.SelectVersionToPublish_ShowStep);
            this.ValidateStep += new System.ComponentModel.CancelEventHandler(this.SelectVersionToPublish_ValidateStep);
            this.Controls.SetChildIndex(this.panel1, 0);
            this.Controls.SetChildIndex(this.Description, 0);
            this.Controls.SetChildIndex(this.listViewVersions, 0);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.RadioButton radioButtonOneVersion;
        private System.Windows.Forms.RadioButton radioButtonLastVersion;
        private System.Windows.Forms.ListView listViewVersions;
        private System.Windows.Forms.ColumnHeader columnHeader5;
        private System.Windows.Forms.ColumnHeader columnHeader6;
        private System.Windows.Forms.ColumnHeader columnHeader7;
    }
}
