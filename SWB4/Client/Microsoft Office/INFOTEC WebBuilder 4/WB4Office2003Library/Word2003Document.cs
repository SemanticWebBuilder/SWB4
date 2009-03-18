using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Diagnostics;
using WBOffice4;
using WB4Office2003Library;
using Word = Microsoft.Office.Interop.Word;
using Office = Microsoft.Office.Core;

namespace WB4Office2003Library
{
    public class Word2003OfficeDocument : OfficeDocument
    {        
        private static object missing = Type.Missing;
        private static object objformatHTML = Word.WdSaveFormat.wdFormatHTML;
        private static object objtrue = true;
        private static object objfalse = false;
        private static object nullObjStr = String.Empty;
                

        public const String DocumentDefaultExtension = ".doc";
        public const String FilterDescription = "Documento de Word(*.doc)|*.doc";
        public const String HtmlExtension = ".html";
        private Word.Document document;
        private Word.Application application;

        public Word2003OfficeDocument(Word.Document document)
        {
            this.document = (Word.Document)document;
            this.application=document.Application;
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
            document.Save();

        }
        protected override void SaveCustomProperties(Dictionary<string, string> properties)
        {
            try
            {
                Office.DocumentProperties docProperties = (Office.DocumentProperties)document.CustomDocumentProperties;
                foreach (String key in properties.Keys)
                {
                    docProperties[key].Delete();
                    docProperties.Add(key, false, Office.MsoDocProperties.msoPropertyTypeString, properties[key], nullObjStr);
                }
                this.Save();
            }
            catch (Exception e)
            {
                throw new WBOfficeErrorException("No puede obtener las propiedades del documento", e);
            }
        }

        protected override FileInfo SaveAsHtml(DirectoryInfo dir)
        {
            object file = document.FullName;            
            SaveAs(dir, SaveDocument.Office2003);
            ((Word.DocumentClass)document).Close(ref objtrue, ref missing, ref missing);
            document = (Word.DocumentClass)application.Documents.Open(ref file, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);

            object filedocxtoOpen = document.FullName;            
            FileInfo HTMLFile = new FileInfo(dir.FullName + Separator + this.FilePath.Name.Replace(this.FilePath.Extension, HtmlExtension));
            object HtmlFileName = HTMLFile.FullName;                        
            dir.Create();
            if (HTMLFile.Exists)
            {
                HTMLFile.Delete();
            }
            document.SaveAs(ref HtmlFileName, ref objformatHTML, ref missing, ref missing, ref objfalse, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
            ((Word.DocumentClass)document).Close(ref objtrue, ref missing, ref missing);
            document = application.Documents.Open(ref filedocxtoOpen, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
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
                throw new WBOfficeErrorException("El documento no se pudo guardar debido a un error reportado por el Office " + document.Application.Version, e);
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

                System.Collections.Generic.List<FileInfo> attachments = new List<FileInfo>();
                attachments.AddRange(this.GetInlineShapesLinks());
                attachments.AddRange(this.GetHyperLinks());                
                return attachments;
            }
        }

        protected override string ApplicationVersion
        {
            get
            {
                return document.Application.Version;
            }
        }

        protected override FileInfo SaveAs(DirectoryInfo dir, SaveDocument format)
        {
            if (format == SaveDocument.HtmlAll || format == SaveDocument.HtmlIE)
            {
                return this.SaveAsHtml(dir);
            }
            else
            {
                throw new NotImplementedException();
            }

        }

        protected override void PrepareHtmlFileToSend(FileInfo htmlFile)
        {
            throw new NotImplementedException();
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

        protected override void Save(FileInfo file)
        {
            object fileNameDOC = file.FullName;
            object missing = Type.Missing;
            object objformatDoc = Word.WdSaveFormat.wdFormatDocument;
            object objfalse = false;
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
            get { return DocumentDefaultExtension; }
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
