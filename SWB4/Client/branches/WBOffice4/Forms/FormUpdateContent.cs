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
using System.IO;
using ICSharpCode.SharpZipLib.Zip;
using ICSharpCode.SharpZipLib.Checksums;
using XmlRpcLibrary;
using System.Diagnostics;
using WBOffice4.Interfaces;

namespace WBOffice4.Forms
{
    public partial class FormUpdateContent : Form
    {
        private static readonly String NL = "\r\n";
        OfficeDocument document;
        public FormUpdateContent(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;
            loadVersions();
        }
        private void loadVersions()
        {
            this.listViewVersions.Items.Clear();
            foreach (VersionInfo version in OfficeApplication.OfficeDocumentProxy.getVersions(document.reporitoryID, document.contentID))
            {
                ListViewItem versionItem = new ListViewItem(version.nameOfVersion);
                String date = String.Format(OfficeApplication.iso8601dateFormat, version.created);
                versionItem.SubItems.Add(date);
                versionItem.SubItems.Add(version.user);
                String published = "No";
                if (version.published)
                {
                    published = "Sí";
                }
                versionItem.SubItems.Add(published);
                this.listViewVersions.Items.Add(versionItem);
                versionItem.Tag = version;
            }
        }


        private void buttonClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            int limit = OfficeApplication.OfficeApplicationProxy.getLimitOfVersions();
            if (limit > 0)
            {
                int versions = OfficeApplication.OfficeDocumentProxy.getNumberOfVersions(this.document.reporitoryID, this.document.contentID);
                if (OfficeApplication.OfficeDocumentProxy.allVersionsArePublished(this.document.reporitoryID, this.document.contentID))
                {
                    if (versions >= limit)
                    {
                        DialogResult resp = MessageBox.Show(this, "¡El limite máximo de versiones es de " + limit + "!\r\nPuede publicar este contenido, debido a que tiene todas las versiones publicadas, pero excederá del límite de versiones\r\n¿Desea continuar?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                        if (resp == DialogResult.No)
                        {
                            return;
                        }
                    }
                }
                else
                {
                    if (versions >= limit)
                    {
                        MessageBox.Show(this, "¡El limite máximo de versiones es de " + limit + "!\r\nSi desea crear una nueva version, debe borrar alguna de las existentes, que no este publicada.", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                        return;
                    }
                }
            }
            
            Dictionary<ResourceInfo, Dictionary<PFlow, String>> flows = new Dictionary<ResourceInfo, Dictionary<PFlow, String>>();
            ResourceInfo[] resources = OfficeApplication.OfficeDocumentProxy.listResources(document.reporitoryID, document.contentID);
            if (resources != null && resources.Length > 0)
            {
                bool showMessage = false;
                foreach (ResourceInfo resourceInfo in resources)
                {
                    if (OfficeApplication.OfficeDocumentProxy.isInFlow(resourceInfo) && resourceInfo.version.EndsWith("*"))
                    {
                        DialogResult res = MessageBox.Show(this, "La publicación de este contenido en la página" + " " + resourceInfo.title + " " + "esta en trámite" + NL + "Si continua se perderá este proceso de autorización, ¿Desea continuar?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                        if (res == DialogResult.No)
                        {
                            return;
                        }
                        PFlow[] aflows = OfficeApplication.OfficeDocumentProxy.getFlows(resourceInfo);
                        PFlow flowtoSend = null;
                        String msg = null;

                        FormSendToAutorize dialogSelectFlow = new FormSendToAutorize(resourceInfo);
                        dialogSelectFlow.Text = dialogSelectFlow.Text + " "+"para página" + " " + resourceInfo.title;
                        dialogSelectFlow.ShowDialog(this);
                        if (dialogSelectFlow.DialogResult == DialogResult.Cancel)
                        {
                            return;
                        }


                        flowtoSend = dialogSelectFlow.pflow;
                        msg = dialogSelectFlow.textBoxMessage.Text;
                        if (aflows.Length == 1)
                        {
                            flowtoSend = aflows[0];
                        }
                        if (flowtoSend != null && msg != null)
                        {
                            flows.Add(resourceInfo, new Dictionary<PFlow, String>());
                            flows[resourceInfo].Add(flowtoSend, msg);
                        }
                        break;
                    }
                }

                foreach (ResourceInfo resourceInfo in resources)
                {
                    PFlow[] pflows = OfficeApplication.OfficeDocumentProxy.getFlows(resourceInfo);
                    if (pflows != null && pflows.Length >= 1)
                    {
                        if (resourceInfo.version.EndsWith("*") && !flows.ContainsKey(resourceInfo))
                        {
                            showMessage = true;
                        }                        
                    }
                }
                if (showMessage)
                {
                    DialogResult res = MessageBox.Show(this, "Se encontró que algunas páginas requieren autorización para publicar este contenido, ¿Desea continuar?", this.Text,MessageBoxButtons.YesNo,MessageBoxIcon.Question);
                    if (res == DialogResult.No)
                    {
                        return;
                    }
                }
                foreach (ResourceInfo resourceInfo in resources)
                {
                    PFlow[] aflows = OfficeApplication.OfficeDocumentProxy.getFlows(resourceInfo);
                    if (aflows != null && aflows.Length >= 1)
                    {
                        // solo avisa de las que va a actualizar
                        if (resourceInfo.version.EndsWith("*") && !flows.ContainsKey(resourceInfo))
                        {
                            PFlow flowtoSend = null;
                            String msg = null;
                            FormSendToAutorize dialogSelectFlow = new FormSendToAutorize(resourceInfo);
                            dialogSelectFlow.Text = dialogSelectFlow.Text + " " + "para página" + " " + resourceInfo.title;
                            dialogSelectFlow.ShowDialog(this);
                            if (dialogSelectFlow.DialogResult == DialogResult.Cancel)
                            {
                                return;
                            }


                            flowtoSend = dialogSelectFlow.pflow;
                            msg = dialogSelectFlow.textBoxMessage.Text;
                            if (aflows.Length == 1)
                            {
                                flowtoSend = aflows[0];
                            }
                            if (flowtoSend != null && msg != null)
                            {
                                flows.Add(resourceInfo, new Dictionary<PFlow, String>());
                                flows[resourceInfo].Add(flowtoSend, msg);
                            }
                        }
                    }
                }
            }
            ResourceInfo[] resourcestoSend = flows.Keys.ToArray<ResourceInfo>();
            String[] messages = new String[flows.Count];
            PFlow[] flowsToSend = new PFlow[flows.Count];
            int i = 0;
            foreach (ResourceInfo res in resourcestoSend)
            {
                flowsToSend[i] = flows[res].Keys.ElementAt(0);
                messages[i] = flows[res][flowsToSend[i]];
                i++;
            }

            this.progressBarSave.Maximum = 1;
            FileInfo zipFile = document.CreateZipFile();
            this.progressBarSave.Value = 1;
            OfficeDocument.OfficeDocumentProxy.Attachments.Add(new Attachment(zipFile, zipFile.Name));
            this.Cursor = Cursors.WaitCursor;
            try
            {

                String name = document.FilePath.Name.Replace(document.DefaultExtension, document.PublicationExtension);
                OfficeDocument.OfficeDocumentProxy.updateContent(document.reporitoryID, document.contentID, name, resourcestoSend, flowsToSend, messages);
                MessageBox.Show(this, "¡Version actualizada!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                loadVersions();
                this.buttonUpdate.Enabled = false;
            }
            catch
            {
                throw;
            }
            finally
            {
                this.Cursor = Cursors.Default;
                zipFile.Delete();
            }
        }

        private void listViewVersions_SelectedIndexChanged(object sender, EventArgs e)
        {            
            this.toolStripButtonDelete.Enabled = false;
            this.toolStripButtonView.Enabled = false;
            if (this.listViewVersions.SelectedItems.Count > 0)
            {
                this.toolStripButtonDelete.Enabled = true;
                this.toolStripButtonView.Enabled = true;
            }
        }

        private void toolStripButtonView_Click(object sender, EventArgs e)
        {
            if (this.listViewVersions.SelectedItems.Count > 0)
            {
                String name = null;
                try
                {
                    String version = this.listViewVersions.SelectedItems[0].Text;
                    String type = document.DocumentType.ToString().ToLower();
                    name = OfficeApplication.OfficeDocumentProxy.createPreview(this.document.reporitoryID, this.document.contentID, version,type);
                    String urlproxy = OfficeApplication.OfficeDocumentProxy.WebAddress.ToString();
                    if (!urlproxy.EndsWith("/gtw"))
                    {
                        if (!urlproxy.EndsWith("/"))
                        {
                            urlproxy += "/";
                        }
                        if (!urlproxy.EndsWith("gtw"))
                        {
                            urlproxy += "gtw";
                        }
                    }
                    Uri url = new Uri(urlproxy + "?contentId=" + this.document.contentID + "&versionName=" + version + "&repositoryName=" + this.document.reporitoryID + "&name=" + name + "&type=" + type);
                    String title = OfficeApplication.OfficeDocumentProxy.getTitle(this.document.reporitoryID, this.document.contentID);
                    FormPreview dialogPreview = new FormPreview(url,false,title);
                    dialogPreview.ShowDialog(this);
                }
                catch (Exception ue)
                {
                    OfficeApplication.WriteError(ue);
                }
                finally
                {
                    if (name != null)
                    {
                        try
                        {
                            OfficeApplication.OfficeDocumentProxy.deletePreview(name);
                        }
                        catch (Exception ue)
                        {
                            OfficeApplication.WriteError(ue);
                        }
                    }
                }
            }
        }

        private void toolStripButtonDelete_Click(object sender, EventArgs e)
        {
            if (this.listViewVersions.SelectedItems.Count>0)
            {
                String versionInfo = this.listViewVersions.SelectedItems[0].Text;
                bool published = ((VersionInfo)this.listViewVersions.SelectedItems[0].Tag).published;
                if (published)
                {
                    MessageBox.Show(this, "¡No se puede borrar una versión que ha sido publicada.!\r\nDebe borrar primero la publicación del contenido.", "Borrado de versión de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                try
                {
                    DialogResult res = MessageBox.Show(this, "¿Desea borrar la versión " + versionInfo + "?", "Borrado de versión de contenido", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                    if (res == DialogResult.Yes)
                    {
                        try
                        {
                            this.Cursor = Cursors.WaitCursor;
                            OfficeApplication.OfficeDocumentProxy.deleteVersionOfContent(this.document.reporitoryID, this.document.contentID, versionInfo);
                            loadVersions();
                        }
                        catch (Exception ue)
                        {
                            OfficeApplication.WriteError(ue);
                        }
                        finally
                        {
                            this.Cursor = Cursors.Default;
                        }
                    }
                }
                catch (Exception ue)
                {
                    OfficeApplication.WriteError(ue);
                }
            }
        }
    }
}
