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
using System.Diagnostics;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public partial class FormAddElement : Form
    {
        private ElementInfo rule;
        private SiteInfo siteInfo;
        private ResourceInfo resourceInfo;
        public FormAddElement(SiteInfo siteInfo, ResourceInfo resourceInfo)
        {
            InitializeComponent();
            this.siteInfo = siteInfo;
            this.resourceInfo = resourceInfo;
            loadRules();
        }
        public ElementInfo ElementInfo
        {
            get
            {
                return rule;
            }
        }
        private void loadRules()
        {


            try
            {
                ElementInfo[] rules = OfficeApplication.OfficeDocumentProxy.getElementsOfResource(resourceInfo);
                this.Cursor = Cursors.WaitCursor;
                foreach (ElementInfo info in OfficeApplication.OfficeApplicationProxy.getElementsToAdd(siteInfo))
                {
                    bool exists = false;
                    foreach (ElementInfo ruleinResource in rules)
                    {
                        if (ruleinResource.Equals(info))
                        {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists)
                    {
                        listView1.Items.Add(new ElementListView(info));
                    }
                }
            }
            catch (Exception e)
            {
                OfficeApplication.WriteError(e);
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        private void buttonClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void buttonAdd_Click(object sender, EventArgs e)
        {
            if (this.listView1.SelectedIndices.Count == 0)
            {
                MessageBox.Show(this, "¡Debe indicar una regla / rol ó grupo de usuarios!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            this.rule = ((ElementListView)this.listView1.SelectedItems[0]).ElementInfo;
            this.DialogResult = DialogResult.OK;
            this.Close();
        }

        private void listView1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            if (this.listView1.SelectedIndices.Count > 0)
            {
                buttonAdd_Click(null, null);
            }
        }
    }
}
