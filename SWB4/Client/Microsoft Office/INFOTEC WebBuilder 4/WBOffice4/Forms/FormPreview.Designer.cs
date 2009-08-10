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
    partial class FormPreview
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormPreview));
            this.panel1 = new System.Windows.Forms.Panel();
            this.textBoxWebAddress = new System.Windows.Forms.TextBox();
            this.labelWebAddress = new System.Windows.Forms.Label();
            this.buttonViewBrowser = new System.Windows.Forms.Button();
            this.buttonClose = new System.Windows.Forms.Button();
            this.webBrowser1 = new System.Windows.Forms.WebBrowser();
            this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.textBoxWebAddress);
            this.panel1.Controls.Add(this.labelWebAddress);
            this.panel1.Controls.Add(this.buttonViewBrowser);
            this.panel1.Controls.Add(this.buttonClose);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 376);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(494, 42);
            this.panel1.TabIndex = 0;
            // 
            // textBoxWebAddress
            // 
            this.textBoxWebAddress.Location = new System.Drawing.Point(88, 12);
            this.textBoxWebAddress.Name = "textBoxWebAddress";
            this.textBoxWebAddress.ReadOnly = true;
            this.textBoxWebAddress.Size = new System.Drawing.Size(199, 20);
            this.textBoxWebAddress.TabIndex = 3;
            // 
            // labelWebAddress
            // 
            this.labelWebAddress.AutoSize = true;
            this.labelWebAddress.Location = new System.Drawing.Point(3, 12);
            this.labelWebAddress.Name = "labelWebAddress";
            this.labelWebAddress.Size = new System.Drawing.Size(78, 13);
            this.labelWebAddress.TabIndex = 2;
            this.labelWebAddress.Text = "Dirección web:";
            // 
            // buttonViewBrowser
            // 
            this.buttonViewBrowser.Location = new System.Drawing.Point(293, 7);
            this.buttonViewBrowser.Name = "buttonViewBrowser";
            this.buttonViewBrowser.Size = new System.Drawing.Size(108, 23);
            this.buttonViewBrowser.TabIndex = 1;
            this.buttonViewBrowser.Text = "Ver en navegador";
            this.buttonViewBrowser.UseVisualStyleBackColor = true;
            this.buttonViewBrowser.Click += new System.EventHandler(this.buttonViewBrowser_Click);
            // 
            // buttonClose
            // 
            this.buttonClose.Location = new System.Drawing.Point(407, 7);
            this.buttonClose.Name = "buttonClose";
            this.buttonClose.Size = new System.Drawing.Size(75, 23);
            this.buttonClose.TabIndex = 0;
            this.buttonClose.Text = "Cerrar";
            this.buttonClose.UseVisualStyleBackColor = true;
            this.buttonClose.Click += new System.EventHandler(this.buttonClose_Click);
            // 
            // webBrowser1
            // 
            this.webBrowser1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.webBrowser1.Location = new System.Drawing.Point(0, 0);
            this.webBrowser1.MinimumSize = new System.Drawing.Size(20, 20);
            this.webBrowser1.Name = "webBrowser1";
            this.webBrowser1.Size = new System.Drawing.Size(494, 376);
            this.webBrowser1.TabIndex = 1;
            this.toolTip1.SetToolTip(this.webBrowser1, "Esta vista no es navegable");
            // 
            // FormPreview
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(494, 418);
            this.Controls.Add(this.webBrowser1);
            this.Controls.Add(this.panel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FormPreview";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Ver página";
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox textBoxWebAddress;
        private System.Windows.Forms.Label labelWebAddress;
        private System.Windows.Forms.Button buttonViewBrowser;
        private System.Windows.Forms.Button buttonClose;
        private System.Windows.Forms.WebBrowser webBrowser1;
        private System.Windows.Forms.ToolTip toolTip1;
    }
}