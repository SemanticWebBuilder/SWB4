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
 
﻿namespace WB4PPTOffice2007
{
    partial class RibbonMenu
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

        #region Component Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.infowb = new Microsoft.Office.Tools.Ribbon.RibbonTab();
            this.groupSession = new Microsoft.Office.Tools.Ribbon.RibbonGroup();
            this.buttonInit = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonCloseSession = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.group1 = new Microsoft.Office.Tools.Ribbon.RibbonGroup();
            this.buttonSave = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonPublish = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonOpen = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.separator3 = new Microsoft.Office.Tools.Ribbon.RibbonSeparator();
            this.buttonInformation = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonCleanPropeties = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonShowDocumentDetail = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonViewRep = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.separator2 = new Microsoft.Office.Tools.Ribbon.RibbonSeparator();
            this.buttonDelete = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.groupTools = new Microsoft.Office.Tools.Ribbon.RibbonGroup();
            this.buttonCreatePage = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonChangePassword = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonAddLink = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonDocsToAuthorize = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.groupHelp = new Microsoft.Office.Tools.Ribbon.RibbonGroup();
            this.buttonHelp = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.buttonAbout = new Microsoft.Office.Tools.Ribbon.RibbonButton();
            this.infowb.SuspendLayout();
            this.groupSession.SuspendLayout();
            this.group1.SuspendLayout();
            this.groupTools.SuspendLayout();
            this.groupHelp.SuspendLayout();
            this.SuspendLayout();
            // 
            // infowb
            // 
            this.infowb.ControlId.ControlIdType = Microsoft.Office.Tools.Ribbon.RibbonControlIdType.Office;
            this.infowb.Groups.Add(this.groupSession);
            this.infowb.Groups.Add(this.group1);
            this.infowb.Groups.Add(this.groupTools);
            this.infowb.Groups.Add(this.groupHelp);
            this.infowb.Label = "SemanticWebBuilder";
            this.infowb.Name = "infowb";
            // 
            // groupSession
            // 
            this.groupSession.Items.Add(this.buttonInit);
            this.groupSession.Items.Add(this.buttonCloseSession);
            this.groupSession.Label = "Sesión";
            this.groupSession.Name = "groupSession";
            // 
            // buttonInit
            // 
            this.buttonInit.ControlSize = Microsoft.Office.Core.RibbonControlSize.RibbonControlSizeLarge;
            this.buttonInit.Image = global::WB4PPTOffice2007.Properties.Resources.icon_llavea;
            this.buttonInit.Label = "Iniciar sesión";
            this.buttonInit.Name = "buttonInit";
            this.buttonInit.ShowImage = true;
            this.buttonInit.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonInit_Click);
            // 
            // buttonCloseSession
            // 
            this.buttonCloseSession.ControlSize = Microsoft.Office.Core.RibbonControlSize.RibbonControlSizeLarge;
            this.buttonCloseSession.Enabled = false;
            this.buttonCloseSession.Image = global::WB4PPTOffice2007.Properties.Resources.icon_candadoa;
            this.buttonCloseSession.Label = "Cerrar sesión";
            this.buttonCloseSession.Name = "buttonCloseSession";
            this.buttonCloseSession.ShowImage = true;
            this.buttonCloseSession.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonCloseSession_Click);
            // 
            // group1
            // 
            this.group1.Items.Add(this.buttonSave);
            this.group1.Items.Add(this.buttonPublish);
            this.group1.Items.Add(this.buttonOpen);
            this.group1.Items.Add(this.separator3);
            this.group1.Items.Add(this.buttonInformation);
            this.group1.Items.Add(this.buttonCleanPropeties);
            this.group1.Items.Add(this.buttonShowDocumentDetail);
            this.group1.Items.Add(this.buttonViewRep);
            this.group1.Items.Add(this.separator2);
            this.group1.Items.Add(this.buttonDelete);
            this.group1.Label = "Contenido";
            this.group1.Name = "group1";
            // 
            // buttonSave
            // 
            this.buttonSave.ControlSize = Microsoft.Office.Core.RibbonControlSize.RibbonControlSizeLarge;
            this.buttonSave.Image = global::WB4PPTOffice2007.Properties.Resources.icon_guardar32;
            this.buttonSave.Label = "Guardar";
            this.buttonSave.Name = "buttonSave";
            this.buttonSave.ShowImage = true;
            this.buttonSave.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonSave_Click);
            // 
            // buttonPublish
            // 
            this.buttonPublish.ControlSize = Microsoft.Office.Core.RibbonControlSize.RibbonControlSizeLarge;
            this.buttonPublish.Image = global::WB4PPTOffice2007.Properties.Resources.icon_enviar32;
            this.buttonPublish.Label = "Publicar";
            this.buttonPublish.Name = "buttonPublish";
            this.buttonPublish.ShowImage = true;
            this.buttonPublish.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonPublish_Click);
            // 
            // buttonOpen
            // 
            this.buttonOpen.ControlSize = Microsoft.Office.Core.RibbonControlSize.RibbonControlSizeLarge;
            this.buttonOpen.Image = global::WB4PPTOffice2007.Properties.Resources.open;
            this.buttonOpen.Label = "Abrir";
            this.buttonOpen.Name = "buttonOpen";
            this.buttonOpen.ShowImage = true;
            this.buttonOpen.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonOpen_Click);
            // 
            // separator3
            // 
            this.separator3.Name = "separator3";
            // 
            // buttonInformation
            // 
            this.buttonInformation.Image = global::WB4PPTOffice2007.Properties.Resources.icon_inf32;
            this.buttonInformation.Label = "Información del contenido";
            this.buttonInformation.Name = "buttonInformation";
            this.buttonInformation.ShowImage = true;
            this.buttonInformation.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonInformation_Click);
            // 
            // buttonCleanPropeties
            // 
            this.buttonCleanPropeties.Image = global::WB4PPTOffice2007.Properties.Resources.icon_eliminar32;
            this.buttonCleanPropeties.Label = "Borrar asociación";
            this.buttonCleanPropeties.Name = "buttonCleanPropeties";
            this.buttonCleanPropeties.ShowImage = true;
            this.buttonCleanPropeties.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonCleanPropeties_Click);
            // 
            // buttonShowDocumentDetail
            // 
            this.buttonShowDocumentDetail.Label = "Detalle de documento";
            this.buttonShowDocumentDetail.Name = "buttonShowDocumentDetail";
            this.buttonShowDocumentDetail.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonShowDocumentDetail_Click);
            // 
            // buttonViewRep
            // 
            this.buttonViewRep.Label = "Ver repositorio de contenidos";
            this.buttonViewRep.Name = "buttonViewRep";
            this.buttonViewRep.ShowImage = true;
            this.buttonViewRep.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonViewRep_Click);
            // 
            // separator2
            // 
            this.separator2.Name = "separator2";
            // 
            // buttonDelete
            // 
            this.buttonDelete.ControlSize = Microsoft.Office.Core.RibbonControlSize.RibbonControlSizeLarge;
            this.buttonDelete.Image = global::WB4PPTOffice2007.Properties.Resources.icon_eliminar32;
            this.buttonDelete.Label = "Borrar Contenido";
            this.buttonDelete.Name = "buttonDelete";
            this.buttonDelete.ShowImage = true;
            this.buttonDelete.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonDelete_Click);
            // 
            // groupTools
            // 
            this.groupTools.Items.Add(this.buttonCreatePage);
            this.groupTools.Items.Add(this.buttonChangePassword);
            this.groupTools.Items.Add(this.buttonAddLink);
            this.groupTools.Items.Add(this.buttonDocsToAuthorize);
            this.groupTools.Label = "Herramientas";
            this.groupTools.Name = "groupTools";
            // 
            // buttonCreatePage
            // 
            this.buttonCreatePage.ControlSize = Microsoft.Office.Core.RibbonControlSize.RibbonControlSizeLarge;
            this.buttonCreatePage.Image = global::WB4PPTOffice2007.Properties.Resources.icon_agregarpag32;
            this.buttonCreatePage.Label = "Crear Página";
            this.buttonCreatePage.Name = "buttonCreatePage";
            this.buttonCreatePage.ShowImage = true;
            this.buttonCreatePage.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonCreatePage_Click);
            // 
            // buttonChangePassword
            // 
            this.buttonChangePassword.Image = global::WB4PPTOffice2007.Properties.Resources.icon_pass32;
            this.buttonChangePassword.Label = "Cambiar contraseña";
            this.buttonChangePassword.Name = "buttonChangePassword";
            this.buttonChangePassword.ShowImage = true;
            this.buttonChangePassword.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonChangePassword_Click);
            // 
            // buttonAddLink
            // 
            this.buttonAddLink.Image = global::WB4PPTOffice2007.Properties.Resources.icon_liga32;
            this.buttonAddLink.Label = "Agregar liga de página";
            this.buttonAddLink.Name = "buttonAddLink";
            this.buttonAddLink.ShowImage = true;
            this.buttonAddLink.Visible = false;
            this.buttonAddLink.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonAddLink_Click);
            // 
            // buttonDocsToAuthorize
            // 
            this.buttonDocsToAuthorize.Image = global::WB4PPTOffice2007.Properties.Resources.icon_docauto;
            this.buttonDocsToAuthorize.Label = "Documentos por autorizar";
            this.buttonDocsToAuthorize.Name = "buttonDocsToAuthorize";
            this.buttonDocsToAuthorize.ShowImage = true;
            this.buttonDocsToAuthorize.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonDocsToAuthorize_Click);
            // 
            // groupHelp
            // 
            this.groupHelp.Items.Add(this.buttonHelp);
            this.groupHelp.Items.Add(this.buttonAbout);
            this.groupHelp.Label = "Ayuda";
            this.groupHelp.Name = "groupHelp";
            // 
            // buttonHelp
            // 
            this.buttonHelp.ControlSize = Microsoft.Office.Core.RibbonControlSize.RibbonControlSizeLarge;
            this.buttonHelp.Image = global::WB4PPTOffice2007.Properties.Resources.icon_ayuda32;
            this.buttonHelp.Label = "Ayuda";
            this.buttonHelp.Name = "buttonHelp";
            this.buttonHelp.ShowImage = true;
            this.buttonHelp.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonHelp_Click);
            // 
            // buttonAbout
            // 
            this.buttonAbout.Label = "Acerca de ...";
            this.buttonAbout.Name = "buttonAbout";
            this.buttonAbout.Click += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonControlEventArgs>(this.buttonAbout_Click);
            // 
            // RibbonMenu
            // 
            this.Name = "RibbonMenu";
            this.RibbonType = "Microsoft.PowerPoint.Presentation";
            this.Tabs.Add(this.infowb);
            this.Load += new System.EventHandler<Microsoft.Office.Tools.Ribbon.RibbonUIEventArgs>(this.RibbonMenu_Load);
            this.infowb.ResumeLayout(false);
            this.infowb.PerformLayout();
            this.groupSession.ResumeLayout(false);
            this.groupSession.PerformLayout();
            this.group1.ResumeLayout(false);
            this.group1.PerformLayout();
            this.groupTools.ResumeLayout(false);
            this.groupTools.PerformLayout();
            this.groupHelp.ResumeLayout(false);
            this.groupHelp.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        internal Microsoft.Office.Tools.Ribbon.RibbonTab infowb;
        internal Microsoft.Office.Tools.Ribbon.RibbonGroup groupSession;
        internal Microsoft.Office.Tools.Ribbon.RibbonGroup group1;
        internal Microsoft.Office.Tools.Ribbon.RibbonGroup groupTools;
        internal Microsoft.Office.Tools.Ribbon.RibbonGroup groupHelp;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonCloseSession;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonPublish;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonOpen;
        internal Microsoft.Office.Tools.Ribbon.RibbonSeparator separator2;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonDelete;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonCreatePage;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonChangePassword;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonHelp;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonAbout;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonAddLink;
        internal Microsoft.Office.Tools.Ribbon.RibbonSeparator separator3;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonInformation;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonCleanPropeties;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonInit;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonSave;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonDocsToAuthorize;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonShowDocumentDetail;
        internal Microsoft.Office.Tools.Ribbon.RibbonButton buttonViewRep;
    }

    partial class ThisRibbonCollection : Microsoft.Office.Tools.Ribbon.RibbonReadOnlyCollection
    {
        internal RibbonMenu RibbonMenu
        {
            get { return this.GetRibbon<RibbonMenu>(); }
        }
    }
}
