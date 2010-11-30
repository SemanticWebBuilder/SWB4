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
 
﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Reflection;
namespace WBOffice4.Forms
{
    internal partial class FormLogin : Form
    {
        URIConfigurationList uRIConfigurationList = new URIConfigurationList();
        public Uri WebAddress { get; set; }
        public String User { get; set; }
        public String Password { get; set; }

        public FormLogin()
        {
            InitializeComponent();
            this.comboBoxAddresses.Items.Clear();
            Uri[] addresses=this.uRIConfigurationList.Addresses;
            Array.Reverse(addresses);
            foreach (Uri uri in addresses)
            {
                this.comboBoxAddresses.Items.Add(uri);
            }
            if (this.comboBoxAddresses.Items.Count > 0)
            {
                this.comboBoxAddresses.SelectedIndex = 0;
            }
            Array.Reverse(addresses);

        }

        private void buttonAceptar_Click(object sender, EventArgs e)
        {
            this.Cursor = Cursors.WaitCursor;
            if (!Uri.IsWellFormedUriString(this.comboBoxAddresses.Text, UriKind.Absolute))
            {
                RtlAwareMessageBox.Show(this, "La dirección web no está bien escrita", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.comboBoxAddresses.Focus();                
                return;
            }
            Uri uri = new Uri(this.comboBoxAddresses.Text, UriKind.Absolute);
            if (!(uri.Scheme == Uri.UriSchemeNetTcp || uri.Scheme == Uri.UriSchemeHttp || uri.Scheme == Uri.UriSchemeHttps))
            {
                RtlAwareMessageBox.Show(this, "La dirección web debe ser una IP, Http ó Https", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.comboBoxAddresses.Focus();
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxLogin.Text.Trim()))
            {
                RtlAwareMessageBox.Show(this, "Falta indicar la clave de acceso", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxLogin.Focus();
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxPassword.Text.Trim()))
            {
                RtlAwareMessageBox.Show(this, "Falta indicar la contraseña de acceso", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxPassword.Focus();
                return;
            }
            //uRIConfigurationList.Add(this.textBoxLogin.Text, uri);
            User = this.textBoxLogin.Text;
            Password = this.textBoxPassword.Text;
            WebAddress = uri;
            this.DialogResult = DialogResult.OK;
            this.Close();

        }
        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }


        private void comboBoxAddresses_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.comboBoxAddresses.Text))
            {
                string uri = this.comboBoxAddresses.Text;
                String login = uRIConfigurationList.GetLogin(new Uri(uri));
                if (!String.IsNullOrEmpty(login))
                {
                    this.buttonDelete.Enabled = true;
                    this.textBoxLogin.Text = login;
                }
            }
        }

        private void buttonAdvanced_Click(object sender, EventArgs e)
        {
            FormConfigurationProxy form = new FormConfigurationProxy();
            form.ShowDialog();
        }

        private void FormLogin_Load(object sender, EventArgs e)
        {
            char[] sep = { ',', '=' };
            string[] data = Assembly.GetExecutingAssembly().FullName.Split(sep);
            String version = "";
            int i = 0;
            foreach (String s in data)
            {
                if (s.IndexOf("Version") != -1)
                {
                    version = data[i + 1];
                }
                i++;
            }
            //this.labelAssembly.Text += ""+version+","+Assembly.GetExecutingAssembly().ManifestModule.Name;
            this.labelAssembly.Text += "" + version;
            
        }

        private void FormLogin_Activated(object sender, EventArgs e)
        {            
            
            if (String.IsNullOrEmpty(this.textBoxPassword.Text.Trim()))
            {
                textBoxPassword.Focus();
            }
            if (String.IsNullOrEmpty(this.textBoxLogin.Text.Trim()))
            {
                textBoxLogin.Focus();
            }
            if (comboBoxAddresses.Items.Count == 0 || String.IsNullOrEmpty(comboBoxAddresses.Text))
            {
                comboBoxAddresses.Focus();
            }
        }

        

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.comboBoxAddresses.Text))
            {
                string uri = this.comboBoxAddresses.Text;
                String login = uRIConfigurationList.GetLogin(new Uri(uri));
                if (!String.IsNullOrEmpty(login))
                {
                    uRIConfigurationList.remove(new Uri(uri));
                }
            }
        }
    }
}
