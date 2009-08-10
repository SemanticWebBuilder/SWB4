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
    partial class FormConfigurationProxy
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormConfigurationProxy));
            this.checkBoxUsesServerProxy = new System.Windows.Forms.CheckBox();
            this.groupBoxConfProxy = new System.Windows.Forms.GroupBox();
            this.textBoxServerPort = new System.Windows.Forms.TextBox();
            this.textBoxServerProxy = new System.Windows.Forms.TextBox();
            this.labelServerPort = new System.Windows.Forms.Label();
            this.labelServerProxy = new System.Windows.Forms.Label();
            this.buttonAccept = new System.Windows.Forms.Button();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.groupBoxConfProxy.SuspendLayout();
            this.SuspendLayout();
            // 
            // checkBoxUsesServerProxy
            // 
            this.checkBoxUsesServerProxy.AutoSize = true;
            this.checkBoxUsesServerProxy.Location = new System.Drawing.Point(13, 13);
            this.checkBoxUsesServerProxy.Name = "checkBoxUsesServerProxy";
            this.checkBoxUsesServerProxy.Size = new System.Drawing.Size(116, 17);
            this.checkBoxUsesServerProxy.TabIndex = 0;
            this.checkBoxUsesServerProxy.Text = "Usa Servidor Proxy";
            this.checkBoxUsesServerProxy.UseVisualStyleBackColor = true;
            this.checkBoxUsesServerProxy.CheckedChanged += new System.EventHandler(this.checkBoxUsesServerProxy_CheckedChanged);
            // 
            // groupBoxConfProxy
            // 
            this.groupBoxConfProxy.Controls.Add(this.textBoxServerPort);
            this.groupBoxConfProxy.Controls.Add(this.textBoxServerProxy);
            this.groupBoxConfProxy.Controls.Add(this.labelServerPort);
            this.groupBoxConfProxy.Controls.Add(this.labelServerProxy);
            this.groupBoxConfProxy.Location = new System.Drawing.Point(13, 37);
            this.groupBoxConfProxy.Name = "groupBoxConfProxy";
            this.groupBoxConfProxy.Size = new System.Drawing.Size(329, 103);
            this.groupBoxConfProxy.TabIndex = 1;
            this.groupBoxConfProxy.TabStop = false;
            this.groupBoxConfProxy.Text = "Configuración";
            // 
            // textBoxServerPort
            // 
            this.textBoxServerPort.Enabled = false;
            this.textBoxServerPort.Location = new System.Drawing.Point(243, 44);
            this.textBoxServerPort.MaxLength = 4;
            this.textBoxServerPort.Name = "textBoxServerPort";
            this.textBoxServerPort.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.textBoxServerPort.Size = new System.Drawing.Size(69, 20);
            this.textBoxServerPort.TabIndex = 3;
            this.textBoxServerPort.Text = "8080";
            // 
            // textBoxServerProxy
            // 
            this.textBoxServerProxy.Enabled = false;
            this.textBoxServerProxy.Location = new System.Drawing.Point(116, 13);
            this.textBoxServerProxy.MaxLength = 15;
            this.textBoxServerProxy.Name = "textBoxServerProxy";
            this.textBoxServerProxy.Size = new System.Drawing.Size(196, 20);
            this.textBoxServerProxy.TabIndex = 2;
            // 
            // labelServerPort
            // 
            this.labelServerPort.AutoSize = true;
            this.labelServerPort.Location = new System.Drawing.Point(10, 47);
            this.labelServerPort.Name = "labelServerPort";
            this.labelServerPort.Size = new System.Drawing.Size(41, 13);
            this.labelServerPort.TabIndex = 1;
            this.labelServerPort.Text = "Puerto:";
            // 
            // labelServerProxy
            // 
            this.labelServerProxy.AutoSize = true;
            this.labelServerProxy.Location = new System.Drawing.Point(7, 20);
            this.labelServerProxy.Name = "labelServerProxy";
            this.labelServerProxy.Size = new System.Drawing.Size(78, 13);
            this.labelServerProxy.TabIndex = 0;
            this.labelServerProxy.Text = "Servidor Proxy:";
            // 
            // buttonAccept
            // 
            this.buttonAccept.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.buttonAccept.Location = new System.Drawing.Point(186, 165);
            this.buttonAccept.Name = "buttonAccept";
            this.buttonAccept.Size = new System.Drawing.Size(75, 23);
            this.buttonAccept.TabIndex = 2;
            this.buttonAccept.Text = "Aceptar";
            this.buttonAccept.UseVisualStyleBackColor = true;
            this.buttonAccept.Click += new System.EventHandler(this.buttonAccept_Click);
            // 
            // buttonCancel
            // 
            this.buttonCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.buttonCancel.Location = new System.Drawing.Point(267, 165);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 3;
            this.buttonCancel.Text = "Cancelar";
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // FormConfigurationProxy
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(349, 200);
            this.Controls.Add(this.buttonCancel);
            this.Controls.Add(this.buttonAccept);
            this.Controls.Add(this.groupBoxConfProxy);
            this.Controls.Add(this.checkBoxUsesServerProxy);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FormConfigurationProxy";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Configuración de Servidor Proxy";
            this.groupBoxConfProxy.ResumeLayout(false);
            this.groupBoxConfProxy.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.CheckBox checkBoxUsesServerProxy;
        private System.Windows.Forms.GroupBox groupBoxConfProxy;
        private System.Windows.Forms.TextBox textBoxServerPort;
        private System.Windows.Forms.TextBox textBoxServerProxy;
        private System.Windows.Forms.Label labelServerPort;
        private System.Windows.Forms.Label labelServerProxy;
        private System.Windows.Forms.Button buttonAccept;
        private System.Windows.Forms.Button buttonCancel;
    }
}