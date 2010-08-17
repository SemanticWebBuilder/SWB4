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
using System.Linq;
using System.Text;
using System.IO;
using System.Diagnostics;
using System.Globalization;
using System.Net;
using System.Windows.Forms;
using WBOffice4.Forms;
using WBOffice4.Interfaces;
using XmlRpcLibrary;
using System.Threading;
using ICSharpCode.SharpZipLib.Zip;
using ICSharpCode.SharpZipLib.Checksums;

namespace WBOffice4
{
    public abstract class OfficeDocument
    {
        byte[] emptyzip = new byte[]{80,75,5,6,0,0,0,0,0, 
                  0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        public const String CONTENT_ID_NAME = "CONTENT_ID_NAME";
        public const String REPOSITORY_ID_NAME = "REPOSITORY_ID_NAME";
        public string contentID = null;
        public string reporitoryID = null;
        protected OfficeDocument()
        {

        }
        /// <summary>
        /// Verify if the content is published or not, and if it exists or not. 
        /// </summary>
        protected bool SetupDocument()
        {
            bool setupDocument = false;
            if (this.CustomProperties.ContainsKey(CONTENT_ID_NAME) && this.CustomProperties.ContainsKey(REPOSITORY_ID_NAME))
            {
                contentID = this.CustomProperties[CONTENT_ID_NAME];
                reporitoryID = this.CustomProperties[REPOSITORY_ID_NAME];
            }
            try
            {
                contentID = OfficeApplication.SetupDocument(reporitoryID, contentID);
                setupDocument = true;
            }
            catch (HttpException e)
            {
                if (e.Code == HttpStatusCode.NotFound)
                {
                    RtlAwareMessageBox.Show(null, "El sitio al que desea conectarse, indica que no tiene habilitada la función de publicación de contenidos", "Verificación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                else if (e.Code == HttpStatusCode.Forbidden)
                {
                    RtlAwareMessageBox.Show(null, "Su clave o contraseña es incorrecta", "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                else
                {
                    RtlAwareMessageBox.Show(null, e.Message, "Verificación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error, MessageBoxDefaultButton.Button1);
                }
                OfficeApplication.LogOff();
            }
            catch (WebException e)
            {
                if (e.Status == WebExceptionStatus.ConnectFailure)
                {
                    RtlAwareMessageBox.Show(null, "El sitio web con el que intenta trabajar, se encuentra apagado o no se puede acceder al mismo.", "Verificación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                else
                {
                    RtlAwareMessageBox.Show(null, e.Message, "Verificación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                OfficeApplication.LogOff();
            }
            catch (Exception e)
            {
                OfficeApplication.WriteError(e);
                RtlAwareMessageBox.Show(null, e.Message, "Verificación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                OfficeApplication.LogOff();
            }
            return setupDocument;
        }

        protected static readonly string Separator = @"\";
        #region Abstract methods and properties
        /// <summary>
        /// Gets the Application version
        /// </summary> 
        protected abstract string ApplicationVersion
        {
            get;
        }
        /// <summary>
        /// Gets the Links of the document
        /// </summary> 
        public abstract String[] Links
        {
            get;
        }

        public abstract String SelectedText
        {
            get;
        }

        /// <summary>
        /// Gets the Images of document
        /// </summary> 
        public abstract int Images
        {
            get;
        }
        /// <summary>
        /// Gets the path of the fisical document, if the document is nos saved returns a null
        /// </summary>
        public abstract FileInfo FilePath
        {
            get;
        }
        /// <summary>
        /// Gets true if the document has not been saved before, false otherwise
        /// </summary>
        protected abstract bool IsNew
        {
            get;
        }

        /// <summary>
        /// Gets true if the document is read only, false otherwise
        /// </summary>
        protected abstract bool IsReadOnly
        {
            get;
        }

        /// <summary>
        /// Gets the custom properties of the document
        /// </summary>
        public abstract Dictionary<String, String> CustomProperties
        {
            get;
        }
        /// <summary>
        /// Gets the Filter by default to save the document
        /// </summary>
        protected abstract String DocumentFilter
        {
            get;
        }
        /// <summary>
        /// Gets the default extension to use when the document is saved
        /// </summary>
        public abstract String DefaultExtension
        {
            get;
        }
        public abstract String PublicationExtension
        {
            get;
        }
        /// <summary>
        /// Returns the type of document (Excel,PPT or Word)
        /// </summary>
        protected internal abstract DocumentType DocumentType
        {
            get;
        }
        /// <summary>
        /// Returns all the attachments in the document
        /// </summary>
        protected abstract ICollection<FileInfo> Attachments
        {
            get;
        }
        /// <summary>
        /// Save the custom proprties
        /// </summary>
        /// <param name="properties">Properties to save</param>
        protected abstract void SaveCustomProperties(Dictionary<String, String> properties);

        protected void InsertLinkToDocumentRepository()
        {
            FormInsertLinkToDocuentRepository frm = new FormInsertLinkToDocuentRepository(this);
            frm.ShowDialog();
        }

        protected abstract void CleanContentProperties();


        /// <summary>
        /// Save the document as Html format
        /// </summary>
        /// <param name="dir">Directory to save the html file</param>
        /// <returns>Archivo HTML almacenado</returns>
        protected abstract FileInfo SaveAsHtml(DirectoryInfo dir);
        /// <summary>
        /// 
        /// </summary>
        /// <param name="htmlFile"></param>
        protected abstract void PrepareHtmlFileToSend(FileInfo htmlFile);
        /// <summary>
        /// Save the document in a specific format
        /// </summary>
        /// <param name="dir">Directory to save</param>
        /// <param name="format">Format to use</param>
        /// <returns></returns>
        protected abstract FileInfo SaveAs(DirectoryInfo dir, SaveDocument format);


        public abstract void InsertLink(String path, String titulo);

        /// <summary>
        /// Save the document
        /// </summary>
        public abstract void Save();
        /// <summary>
        /// Save the document in the default format, with an especific file path
        /// </summary>
        /// <param name="file"></param>
        protected abstract void Save(FileInfo file);
        #endregion
        #region Static methods

        /// <summary>
        /// Coverts the uri string path to FileInfo
        /// </summary>
        /// <param name="uri">uri to convert, it can be relative or absulute</param>
        /// <returns>FileInfo width the path of the file, null is the uri is not a local path, for example: http://www.infotec.com.mx</returns>                           
        private static FileInfo UriToFile(Uri uri)
        {
            if (uri.IsFile)
            {
                try
                {
                    FileInfo farchivo = new FileInfo(uri.LocalPath);
                    return farchivo;
                }
                catch (Exception e)
                {
                    OfficeApplication.WriteError(e);                    
                }
            }
            return null;

        }
        #endregion
        #region Implemented methods
        /// <summary>
        /// Returns all the files that was not found in the local disk
        /// </summary>
        /// <value>A collection o files that was not found in the local disk</value>
        protected ICollection<FileInfo> MissedAttachments
        {
            get
            {
                HashSet<FileInfo> attachments = new HashSet<FileInfo>();
                foreach (FileInfo file in Attachments)
                {
                    if (!file.Exists)
                    {
                        attachments.Add(file);
                    }
                }
                return attachments;
            }
        }

       

        /// <summary>
        /// Returns all the files that was found in the local disk
        /// </summary>
        /// <value>List of files that exist in the local disk</value>
        internal ICollection<FileInfo> NotMissingAttachments
        {
            get
            {
                HashSet<FileInfo> attachments = new HashSet<FileInfo>();
                foreach (FileInfo file in Attachments)
                {
                    if (file.Exists)
                    {
                        attachments.Add(file);
                    }
                }
                return attachments;
            }
        }
        private FileInfo SaveOnGuidDirectoryAsHtml(string guid)
        {
            DirectoryInfo dir = new DirectoryInfo(this.FilePath.Directory + Separator + guid);
            return this.SaveAsHtml(dir);
        }
        private void SaveHtmlPrepareAndGetFiles(String guid)
        {
            FileInfo file = SaveOnGuidDirectoryAsHtml(guid);
            PrepareHtmlFileToSend(file);
        }
        /// <summary>
        /// If the document is new, and has not been saved before
        /// </summary>
        /// <value>True If the document is new, false otherwise</value>
        public bool IsPublished
        {
            get
            {

                if (this.contentID == null || reporitoryID == null)
                {
                    if (this.CustomProperties.ContainsKey(CONTENT_ID_NAME) && this.CustomProperties.ContainsKey(REPOSITORY_ID_NAME))
                    {
                        contentID = this.CustomProperties[CONTENT_ID_NAME];
                        reporitoryID = this.CustomProperties[REPOSITORY_ID_NAME];
                    }
                    if (this.contentID == null || reporitoryID == null)
                    {
                        return false;
                    }
                    return true;

                }
                else
                {
                    return true;
                }
            }
        }
        private void CopyAttachmentsToDirectory(DirectoryInfo temporalFile)
        {
            foreach (FileInfo attachment in this.NotMissingAttachments)
            {
                string destFileName = temporalFile.FullName + @"\" + attachment.Name;
                if (!temporalFile.Exists)
                {
                    temporalFile.Create();
                }
                attachment.CopyTo(destFileName, true);
            }
        }
        private void addFiles(Crc32 crc32, ZipOutputStream zip, DirectoryInfo parent, bool includeDir)
        {
            foreach (FileInfo file in parent.GetFiles())
            {
                FileStream fileStream = file.Open(FileMode.Open, FileAccess.ReadWrite, FileShare.ReadWrite);
                byte[] buffer = new byte[fileStream.Length];

                ZipEntry entry;
                if (includeDir)
                {
                    entry = new ZipEntry(parent.Name + "/" + file.Name);
                }
                else
                {
                    entry = new ZipEntry(file.Name);
                }

                entry.DateTime = file.LastWriteTime;
                entry.Size = file.Length;
                fileStream.Read(buffer, 0, buffer.Length);
                fileStream.Close();
                crc32.Reset();
                crc32.Update(buffer);
                //entry.Crc = crc32.Value;                
                zip.PutNextEntry(entry);
                zip.Write(buffer, 0, buffer.Length);
                zip.CloseEntry();
            }
            foreach (DirectoryInfo dir in parent.GetDirectories())
            {
                addFiles(crc32, zip, dir, true);
            }
        }
        internal FileInfo CreateZipFile()
        {
            String guid = Guid.NewGuid().ToString().Replace('-', '_');
            DirectoryInfo temporalFile = new DirectoryInfo(this.FilePath.Directory + Separator + guid);
            CopyAttachmentsToDirectory(temporalFile);
            SaveHtmlPrepareAndGetFiles(guid);
            if (this.FilePath.Exists)
            {
                String newpath = temporalFile + "/" + FilePath.Name;
                newpath=newpath.Replace('/','\\');
                try
                {
                    this.FilePath.CopyTo(newpath);
                }
                catch (Exception ue)
                {
                    Debug.WriteLine(ue.Message);
                    Debug.WriteLine(ue.StackTrace);
                    
                }
            }
            FileInfo zipFile = new FileInfo(this.FilePath.Directory + Separator + guid + ".zip");
            if (zipFile.Exists)
            {
                zipFile.Delete();
            }
            ZipOutputStream zip = new ZipOutputStream(File.Create(zipFile.FullName));
            Crc32 crc32 = new Crc32();
            zip.SetLevel(9);
            addFiles(crc32, zip, temporalFile, false);
            zip.Finish();
            zip.Close();
            temporalFile.Delete(true);
            zipFile.Refresh();
            return zipFile;
        }
        /// <summary>
        /// Coverts the uri string path to FileInfo
        /// </summary>
        /// <param name="uri">uri to convert, it can be relative or absulute</param>
        /// <returns>FileInfo width the path of the file, null is the uri is not a local path, for example: http://www.infotec.com.mx</returns>
        protected FileInfo UriToFile(string uri)
        {
            FileInfo file=null;
            if (uri.StartsWith("..") || uri.StartsWith("."))
            {
                Uri filepath = new Uri(new Uri(this.FilePath.DirectoryName + Separator), new Uri(uri, UriKind.Relative));
                file = UriToFile(filepath);
            }
            else
            {
                if (Uri.IsWellFormedUriString(uri, UriKind.Relative))
                {
                    Uri filepath = new Uri(new Uri(this.FilePath.DirectoryName + Separator), new Uri(uri, UriKind.Relative));
                    file = UriToFile(filepath);
                }
                else if (Uri.IsWellFormedUriString(uri, UriKind.Absolute))
                {
                    file = UriToFile(new Uri(uri));
                }
                else
                {
                    try
                    {
                        file = new FileInfo(uri);
                    }
                    catch (Exception e)
                    {
                        Debug.WriteLine(e.Message);
                        file = null;
                    }
                }
            }
            return file;
        }
        #endregion

        /// <param name="obj">Clase <see cref="T:System.Object"/> que se va a comparar con la clase <see cref="T:System.Object"/> actual.</param>
        /// <returns>Es true si el objeto <see cref="T:System.Object"/> especificado es igual al objeto <see cref="T:System.Object"/> actual; en caso contrario, es false.</returns>
        public override bool Equals(object obj)
        {
            OfficeDocument officeDocument = obj as OfficeDocument;
            if (obj != null)
            {
                return officeDocument.FilePath.FullName.ToUpperInvariant().Equals(this.FilePath.FullName.ToUpperInvariant());
            }
            return false;
        }

        /// <returns>Código hash para la clase <see cref="T:System.Object"/> actual.</returns>
        public override int GetHashCode()
        {
            return this.FilePath.FullName.ToUpperInvariant().GetHashCode();
        }

        /// <returns>Una clase <see cref="T:System.String"/> que representa la clase <see cref="T:System.Object"/> actual.</returns>
        public override string ToString()
        {
            return this.FilePath.FullName;
        }
        public void Publish()
        {
            try
            {
                if (OfficeApplication.TryLogOn() && SetupDocument() && IsPublished)
                {
                    WebSiteInfo[] sites=OfficeApplication.OfficeApplicationProxy.getSites();
                    if (sites == null || sites.Length == 0)
                    {
                        MessageBox.Show("¡No hay sitios web creados!", "Publicación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        return;
                    }
                    FormPublishcontentToPage frm = new FormPublishcontentToPage(this);
                    frm.ShowDialog();
                }
            }
            catch (Exception e)
            {
                OfficeApplication.WriteError(e);
            }
        }
        public void Publish(String title, String description)
        {
            try
            {
                if (OfficeApplication.TryLogOn() && SetupDocument() && IsPublished)
                {
                    WebSiteInfo[] sites = OfficeApplication.OfficeApplicationProxy.getSites();
                    if (sites == null || sites.Length == 0)
                    {
                        MessageBox.Show("¡No hay sitios web creados!", "Publicación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        return;
                    }
                    FormPublishcontentToPage frm = new FormPublishcontentToPage(this, title, description);
                    frm.ShowDialog();
                }
            }
            catch (Exception e)
            {
                OfficeApplication.WriteError(e);
            }

        }
        public void Delete()
        {
            if (IsPublished)
            {
                if (OfficeApplication.TryLogOn() && SetupDocument())
                {
                    bool canModify = OfficeDocument.OfficeDocumentProxy.canModify(reporitoryID, contentID);
                    if (!canModify)
                    {
                        MessageBox.Show("¡No tiene permisos para modificar o borrar este contenido, debe ser el propietario o un super usuario quien lo puede realizar!", "Borrar contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        return;
                    }
                    DialogResult result = RtlAwareMessageBox.Show("¿Desea borrar el contenido del sitio web?", "Borrar contenido", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                    if (result == DialogResult.Yes)
                    {
                        try
                        {
                            OfficeDocumentProxy.delete(this.reporitoryID, this.contentID);
                            this.CleanContentProperties();
                            MessageBox.Show("¡Se ha borrado el contenido!", "Borrado de contenido", MessageBoxButtons.OK, MessageBoxIcon.Information);
                            if (OfficeApplication.MenuListener != null)
                            {
                                OfficeApplication.MenuListener.NoDocumentPublished();
                            }
                        }
                        catch (HttpException e)
                        {
                            RtlAwareMessageBox.Show(e.Message, "Borrar contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            OfficeApplication.WriteError(e);
                        }
                        catch (XmlRpcException e)
                        {
                            RtlAwareMessageBox.Show(e.Message, "Borrar contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            OfficeApplication.WriteError(e);
                        }
                        catch (WebException e)
                        {
                            RtlAwareMessageBox.Show(e.Message, "Borrar contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            OfficeApplication.WriteError(e);
                        }
                    }
                }
            }
        }
        private bool isOldVersion()
        {
            if (this.CustomProperties.ContainsKey("content") && this.CustomProperties.ContainsKey("topicid") && this.CustomProperties.ContainsKey("topicmap"))
            {
                String contentid = this.CustomProperties["content"];
                String topicid = this.CustomProperties["topicid"];
                String topicmap = this.CustomProperties["topicmap"];
                if (contentid == null || topicmap == null || topicid == null)
                {
                    return false;
                }
                if (contentid.Equals("") || topicmap.Equals("") || topicid.Equals(""))
                {
                    return false;
                }
                return true;
            }
            return false;
        }
        private bool ValidaNombre(FileInfo archivo)
        {
            String ext = archivo.Extension;
            int pos = archivo.Name.LastIndexOf(ext);
            String nombre = archivo.Name.Substring(0, pos);
            if (nombre.Length > 40)
            {
                MessageBox.Show("El nombre del archivo es mayor de 40 caracteres","Validación de nombre de archivo",MessageBoxButtons.OK,MessageBoxIcon.Error);
                return false;
            }
            char[] letras = nombre.ToCharArray();
            for (int i = 0; i < letras.Length; i++)
            {
                char letra = letras[i];
                if (Char.IsPunctuation(letra))
                {
                    MessageBox.Show("El nombre del archivo tiene caracteres de puntuación" + ": " + letra + "", "Validación de nombre de archivo", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return false;

                }
                else if (Char.IsWhiteSpace(letra))
                {
                    MessageBox.Show("El nombre del archivo tiene espacios", "Validación de nombre de archivo", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return false;

                }
                else if (!Char.IsLetterOrDigit(letra))
                {
                    MessageBox.Show("El nombre del archivo tiene caracteres no válidos" + ": " + letra + "", "Validación de nombre de archivo", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return false;
                }
                else if (letra > 123)
                {
                    MessageBox.Show("El nombre del archivo tiene caracteres no válidos" + ": " + letra + "", "Validación de nombre de archivo", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return false;
                }
            }            
            return true;
        }
        private Boolean ValidaFiles()
        {
            foreach (FileInfo file in this.MissedAttachments)
            {
                DialogResultEx valueres = MessageBoxWB4.Show("Publicación de contenido","El archivo incrustado " + file.FullName + " no se encontró,¿Desea continuar?" );
                if (valueres == DialogResultEx.CANCEL)
                {
                    return false;
                }
                if (valueres == DialogResultEx.YESALL)
                {
                    return true;
                }
            }
            return true;
        }
        public void SaveToSite()
        {
            
            try
            {
                if (IsReadOnly)
                {
                    RtlAwareMessageBox.Show("El documento es de sólo lectura, por lo tanto no puede ser publicado", "Publicación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                if (OfficeApplication.TryLogOn() && SetupDocument())
                {
                    if (!ValidaNombre(this.FilePath))
                    {
                        return;
                    }
                    if (!ValidaFiles())
                    {
                        return;
                    }
                    if (isOldVersion())
                    {
                        DialogResult res=RtlAwareMessageBox.Show("El documento esta publicado en una versión anterior, ¿Desea que se verifique si existe en el sitio actual?", "Publicación de contenido", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);
                        if (res == DialogResult.Yes)
                        {
                            String contentid = this.CustomProperties["content"];
                            String topicid = this.CustomProperties["topicid"];
                            String topicmap = this.CustomProperties["topicmap"];
                            ContentInfo info = OfficeApplication.OfficeDocumentProxy.existContentOldVersion(contentid, topicmap, topicid);
                            if(info!=null)
                            {
                                res=RtlAwareMessageBox.Show("El documento se encuentra en el sitio, ¿Desea convertir el documento a versión 4?", "Publicación de contenido", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);
                                if (res == DialogResult.Yes)
                                {
                                    CleanContentProperties();
                                    SaveContentProperties(info.id,info.respositoryName);
                                    this.Save();
                                    RtlAwareMessageBox.Show("¡El documento se ha convertido a versión 4, puede continuar!", "Publicación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Information);
                                }
                                if (res == DialogResult.Cancel)
                                {
                                    return;
                                }
                            }
                            else
                            {
                                res = RtlAwareMessageBox.Show("El documento no existe en el sitio actual, por lo cuál no se puede convertir, ¿Desea continuar?", "Publicación de contenido", MessageBoxButtons.YesNo, MessageBoxIcon.Information);
                                if (res == DialogResult.No)
                                {
                                    return;
                                }

                            }
                        }
                        if (res == DialogResult.Cancel)
                        {
                            return;
                        }
                    }
                    if (IsNew)
                    {
                        SaveFileDialog dialog = new SaveFileDialog();
                        dialog.Title = "Guardar Documento";
                        dialog.CreatePrompt = true;
                        dialog.CheckFileExists = false;
                        dialog.CheckPathExists = true;
                        dialog.OverwritePrompt = true;
                        dialog.AddExtension = true;
                        dialog.DefaultExt = DefaultExtension;
                        dialog.Filter = DocumentFilter;
                        DialogResult result = dialog.ShowDialog();
                        if (result != DialogResult.OK)
                        {
                            return;
                        }
                        FileInfo file = new FileInfo(dialog.FileName);
                        if (file.Exists)
                        {
                            file.Delete();
                        }
                        Save(file);
                        new FormSaveContent(this).ShowDialog();
                    }
                    else if (!IsNew && this.IsPublished && OfficeApplication.OfficeDocumentProxy.exists(this.reporitoryID, this.contentID))
                    {
                        // update the content
                        Save();
                        UpdateContent();
                    }
                    else
                    {
                        Save();
                        new FormSaveContent(this).ShowDialog();
                    }
                }
            }
            catch (Exception e)
            {
                OfficeApplication.WriteError(e);
            }
        }
        internal static IOfficeDocument OfficeDocumentProxy
        {
            get
            {
                return OfficeApplication.OfficeDocumentProxy;
            }

        }
        public void SaveContentProperties(string contentId, String repositoryName)
        {
            this.contentID = contentId;
            this.reporitoryID = repositoryName;
            Dictionary<String, String> values = new Dictionary<string, string>();
            values[CONTENT_ID_NAME] = contentId.ToString(CultureInfo.InvariantCulture);
            values[REPOSITORY_ID_NAME] = repositoryName.ToString(CultureInfo.InvariantCulture);
            SaveCustomProperties(values);
        }
        public void showContentInformation()
        {
            if (OfficeApplication.TryLogOn() && SetupDocument())
            {
                FormContentInformation formContentInformation = new FormContentInformation(reporitoryID, contentID, this);
                formContentInformation.ShowDialog();
            }
        }
        public void showDocumentDetail()
        {
            FrmDetalleDoc formContentInformation = new FrmDetalleDoc(this);
            formContentInformation.ShowDialog();            
        }  
        public void DeleteAsociation()
        {

            if (IsPublished)
            {
                DialogResult res = MessageBox.Show("¿Desea borra la asociación del contenido?", "Borrar asociación de contenido", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (res == DialogResult.Yes)
                {
                    CleanContentProperties();
                    MessageBox.Show("La asociación del contenido se ha borrado", "Borrar asociación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    if (OfficeApplication.MenuListener != null)
                    {
                        OfficeApplication.MenuListener.NoDocumentPublished();
                    }
                }

            }
        }
        private void UpdateContent()
        {
            if (OfficeApplication.TryLogOn() && SetupDocument())
            {
                String contentID = this.CustomProperties[CONTENT_ID_NAME];
                String reporitoryID = this.CustomProperties[REPOSITORY_ID_NAME];
                bool canModify=OfficeDocument.OfficeDocumentProxy.canModify(reporitoryID, contentID);
                if (!canModify)
                {
                    MessageBox.Show("¡No tiene permisos para modificar o borrar este contenido, debe ser el propietario o un super usuario quien lo puede realizar!", "Actualizar contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                FormUpdateContent frm = new FormUpdateContent(this);
                frm.ShowDialog();
            }
        }

        public void InsertLink()
        {
            if (OfficeApplication.TryLogOn())
            {
                FormAddLink formAddLink = new FormAddLink(this);
                formAddLink.ShowDialog();
            }
        }

        public void InsertLinkToDoc()
        {
            if (OfficeApplication.TryLogOn())
            {
                FormInsertLinkToDocuentRepository formAddLink = new FormInsertLinkToDocuentRepository(this);
                formAddLink.ShowDialog();
            }
        }


    }
}
