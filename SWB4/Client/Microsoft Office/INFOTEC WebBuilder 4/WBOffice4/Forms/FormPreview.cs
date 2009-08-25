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

namespace WBOffice4.Forms
{
    public partial class FormPreview : Form
    {
        private Uri uri;
        public FormPreview(Uri uri,String title)
        {
            InitializeComponent();            
            this.uri = uri;
            this.textBoxWebAddress.Text = uri.ToString();
            this.webBrowser1.Navigate(uri, null, null, "Authorization: Basic "+GetUserPassWordEncoded());
            this.webBrowser1.AllowNavigation = false;
            this.Text = title;
        }
        public FormPreview(Uri uri,Boolean showWebAddress,String title) : this(uri,title)
        {
            this.textBoxWebAddress.Visible = showWebAddress;
            this.labelWebAddress.Visible = showWebAddress;
            this.buttonViewBrowser.Visible = showWebAddress;
        }

        private String GetUserPassWordEncoded()
        {
            String userPassword = OfficeApplication.OfficeDocumentProxy.Credentials.UserName + ":" + OfficeApplication.OfficeDocumentProxy.Credentials.Password;
            String encoded =EncodeTo64(userPassword);
            return encoded;
        }
        private static string EncodeTo64(string toEncode)
        {

            byte[] toEncodeAsBytes

                  = System.Text.ASCIIEncoding.ASCII.GetBytes(toEncode);

            string returnValue

                  = System.Convert.ToBase64String(toEncodeAsBytes);

            return returnValue;

        }
        private void buttonClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }
        public static void showInBrowser(Uri uri)
        {
            System.Diagnostics.Process.Start("explorer.exe",uri.ToString());
        }

        private void buttonViewBrowser_Click(object sender, EventArgs e)
        {
            showInBrowser(this.uri);
        }

    }
}
