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
    public partial class FormChangePassword : Form
    {
        
        public FormChangePassword()
        {
            InitializeComponent();
        }
        public String NewPassword
        {
            get
            {
                return this.textBoxNewPassword.Text;
            }
        }
        private void buttonAccept_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxNewPassword.Text.Trim()))
            {
                RtlAwareMessageBox.Show(this, "¡Indique la nueva contraseña!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxNewPassword.Focus();
                return;
            }
            if (this.textBoxNewPassword.Text.Trim() != this.textBoxConfirmNewPassword.Text.Trim())
            {
                RtlAwareMessageBox.Show(this, "La contraseña de acceso no coincide con la confirmación de la contraseña de acceso", this.Text, MessageBoxButtons.OK,MessageBoxIcon.Error);
                this.textBoxNewPassword.Focus();
                return;
            }
            OfficeApplication.OfficeApplicationProxy.changePassword(this.textBoxNewPassword.Text.Trim());
            OfficeApplication.OfficeApplicationProxy.Credentials.Password = this.textBoxNewPassword.Text.Trim();
            OfficeApplication.OfficeDocumentProxy.Credentials.Password = this.textBoxNewPassword.Text.Trim();
            this.Close();
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
