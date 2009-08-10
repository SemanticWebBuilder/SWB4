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
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public partial class FormAddCategory : Form
    {
        private String repository, categoryid;
        public FormAddCategory(String repository, String categoryid)
        {
            InitializeComponent();
            this.repository = repository;
            this.categoryid = categoryid;
            this.Text = "Agregar categoria a repositorio " + repository;
        }
        public FormAddCategory(String repository)
        {
            InitializeComponent();
            this.repository = repository;            
            this.Text = "Agregar categoria a repositorio " + repository;
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void buttonOK_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(textBoxName.Text))
            {
                MessageBox.Show(this, "¡Debe indicar el nombre de la categoria!", "Error al capturar el nombre de la categoria", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxName.Focus();
                return;
            }
            if (String.IsNullOrEmpty(textBoxDescription.Text))
            {
                MessageBox.Show(this, "¡Debe indicar la descripción de la categoria!", "Error al capturar descripción de la categoria", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxDescription.Focus();
                return;
            }
            this.Cursor = Cursors.WaitCursor;
            bool existe = false;
            try
            {
                foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getCategories(repository))
                {
                    if (category.title.Equals(textBoxName.Text, StringComparison.InvariantCultureIgnoreCase))
                    {
                        existe = true;
                        break;
                    }
                }
            }
            catch
            {
            }
            if (existe)
            {
                this.Cursor = Cursors.Default;
                MessageBox.Show(this, "¡Ya existe una categoria con ese nombre!", "Error al capturar nombre", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxName.Focus();
                return;
            }
            else
            {
                if (categoryid != null)
                {
                    try
                    {
                        OfficeApplication.OfficeApplicationProxy.createCategory(repository, categoryid, this.textBoxName.Text, textBoxDescription.Text);
                    }
                    catch
                    {
                    }
                }
                else
                {
                    try
                    {
                        OfficeApplication.OfficeApplicationProxy.createCategory(repository, this.textBoxName.Text, textBoxDescription.Text);
                    }
                    catch
                    {
                    }
                }

            }

            this.Cursor = Cursors.Default;

            this.DialogResult = DialogResult.OK;
            this.Close();
        }
    }
}
