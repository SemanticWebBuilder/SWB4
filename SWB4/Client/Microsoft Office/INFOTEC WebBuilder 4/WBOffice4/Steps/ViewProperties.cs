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
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Utils;
namespace WBOffice4.Steps
{
    public partial class ViewProperties : TSWizards.BaseInteriorStep
    {
        
        public static readonly String VIEW_PROPERTIES = "VIEW_PROPERTIES";
        public static readonly String VIEW_PROPERTIES_VALUES = "VIEW_PROPERTIES_VALUES";
        public ViewProperties()
        {
            InitializeComponent();            
        }

        private void ViewProperties_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            
                string repositoryName = this.Wizard.Data[SelectVersionToPublish.REPOSITORY_ID_NAME].ToString();
                string contentID = this.Wizard.Data[SelectVersionToPublish.CONTENT_ID_NAME].ToString();
                PropertyInfo[] properties = OfficeApplication.OfficeDocumentProxy.getResourceProperties(repositoryName, contentID);
                this.propertyEditor1.Properties = properties;
            
        }

        private void ViewProperties_ValidateStep(object sender, CancelEventArgs e)
        {
            string repositoryName = this.Wizard.Data[SelectVersionToPublish.REPOSITORY_ID_NAME].ToString();
            string contentID = this.Wizard.Data[SelectVersionToPublish.CONTENT_ID_NAME].ToString();
            PropertyInfo[] properties=OfficeApplication.OfficeDocumentProxy.getResourceProperties(repositoryName, contentID);            
            Object[] values = this.propertyEditor1.Values;
            try
            {
                OfficeApplication.OfficeDocumentProxy.validateViewValues(repositoryName, contentID, properties, values);
            }
            catch (Exception ue)
            {
                MessageBox.Show(this, ue.Message, this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                e.Cancel = true;
                return;
            }
            this.Wizard.Data[VIEW_PROPERTIES] = properties;
            this.Wizard.Data[VIEW_PROPERTIES_VALUES] = values;
        }
    }
}
