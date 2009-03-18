using System;
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
        public string contentID=null;
        public string reporitoryID = null;
        protected OfficeDocument()
        {
            
        }
        /// <summary>
        /// Verify if the content is published or not, and if it exists or not. 
        /// </summary>
        protected bool SetupDocument()
        {            
            bool setupDocument=false;
            if (this.CustomProperties.ContainsKey(CONTENT_ID_NAME) && this.CustomProperties.ContainsKey(REPOSITORY_ID_NAME))
            {
                contentID = this.CustomProperties[CONTENT_ID_NAME];
                reporitoryID = this.CustomProperties[REPOSITORY_ID_NAME];
            }
            try
            {
                contentID = OfficeApplication.SetupDocument(reporitoryID, contentID);
                setupDocument=true;
            }            
            catch (HttpException e)
            {
                if(e.Code==HttpStatusCode.NotFound)
                {
                    RtlAwareMessageBox.Show(null,"El sitio al que desea conectarse, indica que no tiene habilitada la función de publicación de contenidos", "Verificación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                else if (e.Code == HttpStatusCode.Forbidden)
                {
                    RtlAwareMessageBox.Show(null, "Su clave o contraseña es incorrecta", "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                else
                {
                    RtlAwareMessageBox.Show(null,e.Message,"Verificación de contenido",MessageBoxButtons.OK,MessageBoxIcon.Error,MessageBoxDefaultButton.Button1);
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


        public abstract void InsertLink(String path,String titulo);

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
                FileInfo farchivo = new FileInfo(uri.LocalPath);
                return farchivo;
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
                
                if (this.contentID == null || reporitoryID==null)
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
                attachment.CopyTo(destFileName, true);
            }
        }
        private void addFiles(Crc32 crc32,ZipOutputStream zip, DirectoryInfo parent,bool includeDir)
        {
            foreach (FileInfo file in parent.GetFiles())
            {
                FileStream fileStream = file.OpenRead();
                byte[] buffer = new byte[fileStream.Length];

                ZipEntry entry;
                if (includeDir)
                {
                    entry = new ZipEntry(parent.Name+"/"+file.Name);
                }
                else
                {
                    entry = new ZipEntry(file.Name);
                }
                
                entry.DateTime = file.LastWriteTime;
                entry.Size = file.Length;                
                fileStream.Read(buffer,0,buffer.Length);                
                fileStream.Close();
                crc32.Reset();
                crc32.Update(buffer);
                entry.Crc = crc32.Value;                
                zip.PutNextEntry(entry);                
                zip.Write(buffer, 0, buffer.Length);
                zip.CloseEntry();
            }
            foreach (DirectoryInfo dir in parent.GetDirectories())
            {                
                addFiles(crc32,zip, dir,true);
            }
        }
        internal FileInfo CreateZipFile()
        {
            String guid = Guid.NewGuid().ToString().Replace('-', '_');
            DirectoryInfo temporalFile = new DirectoryInfo(this.FilePath.Directory + Separator + guid);
            CopyAttachmentsToDirectory(temporalFile);
            SaveHtmlPrepareAndGetFiles(guid);                   
            FileInfo zipFile = new FileInfo(this.FilePath.Directory + Separator + guid + ".zip");
            if (zipFile.Exists)
            {
                zipFile.Delete();
            }
            ZipOutputStream zip = new ZipOutputStream(File.Create(zipFile.FullName));
            Crc32 crc32 = new Crc32();
            zip.SetLevel(6);
            addFiles(crc32,zip, temporalFile,false);
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
            if (Uri.IsWellFormedUriString(uri, UriKind.Relative))
            {
                Uri filepath = new Uri(new Uri(this.FilePath.DirectoryName + Separator), new Uri(uri, UriKind.Relative));
                file = UriToFile(filepath);
            }
            else if (Uri.IsWellFormedUriString(uri, UriKind.Absolute))
            {
                file = UriToFile(new Uri(uri));
            }            
            return file;
        }
        #endregion

        /// <param name="obj">Clase <see cref="T:System.Object"/> que se va a comparar con la clase <see cref="T:System.Object"/> actual.</param>
        /// <returns>Es true si el objeto <see cref="T:System.Object"/> especificado es igual al objeto <see cref="T:System.Object"/> actual; en caso contrario, es false.</returns>
        public override bool Equals(object obj)
        {
            OfficeDocument officeDocument = obj as OfficeDocument;
            if (obj!=null)
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
            if (OfficeApplication.TryLogOn() && SetupDocument() && IsPublished)
            {
                FormPublishcontentToPage frm = new FormPublishcontentToPage(this);
                frm.ShowDialog();
            }
        }
        public void Publish(String title,String description)
        {
            if (OfficeApplication.TryLogOn() && SetupDocument() && IsPublished)
            {
                FormPublishcontentToPage frm = new FormPublishcontentToPage(this,title,description);
                frm.ShowDialog();
            }
        }
        public void Delete()
        {
            if (IsPublished)
            {
                if (OfficeApplication.TryLogOn() && SetupDocument())
                {
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
                            SwbEventLog.Log(e);
                        }
                        catch (XmlRpcException e)
                        {
                            RtlAwareMessageBox.Show(e.Message, "Borrar contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            SwbEventLog.Log(e);
                        }
                        catch (WebException e)
                        {
                            RtlAwareMessageBox.Show(e.Message, "Borrar contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            SwbEventLog.Log(e);
                        }
                    }
                }
            }
        }
        public void SaveToSite()
        {
            if (IsReadOnly)
            {
                RtlAwareMessageBox.Show("El documento es de sólo lectura, por lo tanto no puede ser publicado", "Publicación de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            if (OfficeApplication.TryLogOn() && SetupDocument())
            {
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
                else if (!IsNew && this.IsPublished && OfficeApplication.OfficeDocumentProxy.exists(this.reporitoryID,this.contentID))
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
        internal static IOfficeDocument OfficeDocumentProxy
        {
            get
            {
                return OfficeApplication.OfficeDocumentProxy;
            }

        }
        internal void SaveContentProperties(string contentId, String repositoryName)
        {
            this.contentID = contentId;
            Dictionary<String, String> values = new Dictionary<string, string>();
            values[CONTENT_ID_NAME]=contentId.ToString(CultureInfo.InvariantCulture);
            values[REPOSITORY_ID_NAME]= repositoryName.ToString(CultureInfo.InvariantCulture);
            SaveCustomProperties(values);
        }
        public void showContentInformation()
        {
            if (OfficeApplication.TryLogOn() && SetupDocument())
            {
                FormContentInformation formContentInformation = new FormContentInformation(reporitoryID, contentID,this);
                formContentInformation.ShowDialog();
            }
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
                }
                if (OfficeApplication.MenuListener != null)
                {
                    OfficeApplication.MenuListener.NoDocumentPublished();
                }
            }
        }
        private void UpdateContent()
        {
            if (OfficeApplication.TryLogOn() && SetupDocument())
            {
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

        
    }
}
