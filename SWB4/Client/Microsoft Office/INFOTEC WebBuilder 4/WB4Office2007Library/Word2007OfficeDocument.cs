using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Diagnostics;
using WBOffice4;
using Word = Microsoft.Office.Interop.Word;
using Office = Microsoft.Office.Core;

namespace WB4Office2007Library
{
    public class Word2007OfficeDocument : OfficeDocument
    {
        private static object missing = Type.Missing;
        private static object objformatHTML = Word.WdSaveFormat.wdFormatHTML;
        private static object objtrue = true;
        private static object objfalse = false;
        private static object nullObjStr = String.Empty;
        private Word.Application application;

        public const String HtmlExtension = ".html";
        public const String DocumentDefaultExtension = ".docx";
        public const String Office2003Extension = ".doc";
        public const String FilterDescription = "Documento de Word(*.docx)|*.docx";
        private Word.Document document;
        public Word2007OfficeDocument(Word.Document document)
        {
            this.document = (Word.Document)document;
            this.application = document.Application;
        }

        public override System.IO.FileInfo FilePath
        {
            get
            {
                return new FileInfo(document.FullName);
            }
        }
        protected override bool IsReadOnly
        {
            get
            {
                return document.ReadOnly;
            }
        }
        public override Dictionary<string, string> CustomProperties
        {
            get
            {
                Dictionary<String, String> properties = new Dictionary<string, string>();
                Office.DocumentProperties docProperties = (Office.DocumentProperties)document.CustomDocumentProperties;
                foreach (Office.DocumentProperty prop in docProperties)
                {
                    properties.Add(prop.Name, prop.Value.ToString());
                }
                return properties;
            }
        }
        protected override DocumentType DocumentType
        {
            get
            {
                return DocumentType.Word;
            }
        }
        protected override void CleanContentProperties()
        {
            Office.DocumentProperties docProperties = (Office.DocumentProperties)document.CustomDocumentProperties;            
            foreach (Office.DocumentProperty prop in docProperties)
            {
                if (prop.Name.Equals(CONTENT_ID_NAME, StringComparison.InvariantCulture))
                {
                    prop.Delete();
                    break;
                }
            }
            docProperties = (Office.DocumentProperties)document.CustomDocumentProperties;
            foreach (Office.DocumentProperty prop in docProperties)
            {
                if (prop.Name.Equals(REPOSITORY_ID_NAME, StringComparison.InvariantCulture))
                {
                    prop.Delete();
                    break;
                }
            }
            document.Saved = false;
            document.Save();
        }
        protected override void SaveCustomProperties(Dictionary<string, string> properties)
        {
            try
            {                
                Office.DocumentProperties docProperties = (Office.DocumentProperties)document.CustomDocumentProperties;

                foreach (String key in properties.Keys)
                {
                    foreach (Office.DocumentProperty prop in docProperties)
                    {
                        if (prop.Name.Equals(key, StringComparison.InvariantCulture))
                        {
                            prop.Delete();
                            break;
                        }
                    }
                }
                foreach (String key in properties.Keys)
                {                    
                    docProperties.Add(key, false, Office.MsoDocProperties.msoPropertyTypeString, properties[key], nullObjStr);
                }
                document.Saved = false;
                document.Save();
            }
            catch (Exception e)
            {
                throw new WBOfficeErrorException("No puede obtener las propiedades del documento", e);
            }

        }

        protected override FileInfo SaveAsHtml(System.IO.DirectoryInfo dir)
        {
            object file = document.FullName;
            FileInfo fileDoc = new FileInfo(document.FullName);
            if (!fileDoc.Extension.Equals(Office2003Extension, StringComparison.OrdinalIgnoreCase))
            {
                SaveAs(dir, SaveDocument.Office2003);
            }
            ((Word.DocumentClass)document).Close(ref objtrue, ref missing, ref missing);
            document = (Word.DocumentClass)application.Documents.Open(ref file, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
            object filedocxtoOpen = this.FilePath.FullName;
            FileInfo HTMLFile = new FileInfo(dir.FullName + Separator + this.FilePath.Name.Replace(this.FilePath.Extension, HtmlExtension));
            object fileName = HTMLFile.FullName;
            dir.Create();
            if (HTMLFile.Exists)
            {
                HTMLFile.Delete();
            }
            document.SaveAs(ref fileName, ref objformatHTML, ref missing, ref missing, ref objfalse, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
            ((Word.DocumentClass)document).Close(ref objtrue, ref missing, ref missing);
            document = (Word.DocumentClass)application.Documents.Open(ref filedocxtoOpen, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
            return HTMLFile;
        }
        public override void Save()
        {
            try
            {                
                if (!document.Saved)
                {
                    document.Save();
                }
            }
            catch (Exception e)
            {
                throw new WBOfficeErrorException("El documento no se pudo guardar debido a un error reportado por el Office " + this.application.Version, e);
            }
        }

        private ICollection<FileInfo> GetInlineShapesLinks()
        {
            List<FileInfo> attachments = new List<FileInfo>();
            foreach (Word.InlineShape shape in document.InlineShapes)
            {
                if (shape.LinkFormat != null)
                {
                    FileInfo file = UriToFile(shape.LinkFormat.SourceFullName);
                    if (file != null)
                    {
                        attachments.Add(file);
                    }
                }
            }
            return attachments;
        }
        private ICollection<FileInfo> GetHyperLinks()
        {
            List<FileInfo> attachments = new List<FileInfo>();
            foreach (Word.Hyperlink link in document.Hyperlinks)
            {
                FileInfo file = UriToFile(link.Address);
                if (file != null)
                {
                    attachments.Add(file);
                }
            }
            return attachments;
        }
        protected override ICollection<FileInfo> Attachments
        {
            get
            {
                List<FileInfo> attachments = new List<FileInfo>();
                attachments.AddRange(this.GetInlineShapesLinks());
                attachments.AddRange(this.GetHyperLinks());
                return attachments;
            }
        }

        protected override string ApplicationVersion
        {
            get
            {
                return this.application.Version;
            }
        }

        protected override FileInfo SaveAs(DirectoryInfo dir, SaveDocument format)
        {
            switch (format)
            {
                case SaveDocument.HtmlAll:
                case SaveDocument.HtmlIE:
                    return this.SaveAsHtml(dir);
                case SaveDocument.Office2003:
                    String nameDocFile = this.FilePath.Name.Replace(this.FilePath.Extension, Office2003Extension);
                    FileInfo docFile = new FileInfo(dir.FullName + Separator + nameDocFile);
                    this.SaveOffice2003(docFile);
                    return docFile;
                default:
                    String nameDocxFile = this.FilePath.Name.Replace(this.FilePath.Extension, DocumentDefaultExtension);
                    FileInfo docXFile = new FileInfo(dir.FullName + Separator + nameDocxFile);
                    this.Save(docXFile);
                    return docXFile;
            }

        }

        protected override void PrepareHtmlFileToSend(FileInfo htmlFile)
        {
            
        }

        protected override bool IsNew
        {
            get
            {
                if (String.IsNullOrEmpty(document.Path))
                {
                    return true;
                }
                return false;
            }
        }
        private void SaveOffice2003(FileInfo file)
        {
            DirectoryInfo dir = file.Directory;
            dir.Create();
            object fileNameDOC = file.FullName;
            object objformatDoc = Word.WdSaveFormat.wdFormatDocument;
            if (file.Exists)
            {
                file.Delete();
            }
            document.SaveAs(ref fileNameDOC, ref objformatDoc, ref missing, ref missing, ref objfalse, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
        }
        protected override void Save(FileInfo file)
        {
            DirectoryInfo dir = file.Directory;
            dir.Create();
            object fileNameDOC = file.FullName;
            object objformatDoc = Word.WdSaveFormat.wdFormatXMLDocument;
            if (file.Exists)
            {
                file.Delete();
            }
            document.SaveAs(ref fileNameDOC, ref objformatDoc, ref missing, ref missing, ref objfalse, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
        }
        protected override string DocumentFilter
        {
            get
            {
                return FilterDescription;
            }
        }

        public override string DefaultExtension
        {
            get
            {
                return DocumentDefaultExtension;
            }
        }
        public override string PublicationExtension
        {
            get { return Office2003Extension; }
        }
        public override void InsertLink(String path, String titulo)
        {
            object missing = Type.Missing;
            object text = titulo;
            object address = path;            
            this.document.Hyperlinks.Add(this.document.Application.Selection.Range, ref address, ref missing, ref missing, ref text, ref missing);
        }
    }
}
