using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using WBOffice4;
using WB4Office2003Library;
using Word = Microsoft.Office.Interop.Word;
using Office = Microsoft.Office.Core;

namespace WB4Office2003Library
{
    public class WordOfficeApplication : OfficeApplication
    {
        public const String HtmlExtension = ".html";
        Word.Application application;
        public WordOfficeApplication(Word.Application application)
        {
            this.application = application;
            this.application.DocumentOpen += new Microsoft.Office.Interop.Word.ApplicationEvents4_DocumentOpenEventHandler(app_DocumentOpen);
            this.application.DocumentChange += new Microsoft.Office.Interop.Word.ApplicationEvents4_DocumentChangeEventHandler(app_DocumentChange);
            this.application.DocumentBeforeClose += new Microsoft.Office.Interop.Word.ApplicationEvents4_DocumentBeforeCloseEventHandler(application_DocumentBeforeClose);
        }
        private void application_DocumentBeforeClose(Microsoft.Office.Interop.Word.Document document, ref bool cancel)
        {
            if (document.Application.Documents.Count == 1)
            {
                // Es el último
                if (MenuListener != null)
                {
                    OfficeApplication.MenuListener.NoDocumentsActive();
                }
            }
            else
            {
                if (MenuListener != null)
                {
                    OfficeApplication.MenuListener.DocumentsActive();
                }
            }
        }
        private void ActivateDocument(Microsoft.Office.Interop.Word.Document document)
        {
            OfficeDocument officeDocument = new Word2003OfficeDocument(document);
            if (officeDocument.IsPublished)
            {
                if (MenuListener != null)
                {
                    OfficeApplication.MenuListener.DocumentPublished();
                }
            }
            else
            {
                if (MenuListener != null)
                {
                    OfficeApplication.MenuListener.NoDocumentPublished();
                }
            }
        }
        private void app_DocumentChange()
        {
            this.ActivateDocument(this.application.ActiveDocument);
        }
        private void app_DocumentOpen(Microsoft.Office.Interop.Word.Document document)
        {
            this.ActivateDocument(document);
        }
        protected override string Version
        {
            get
            {
                return application.Version;
            }
        }
        protected override ICollection<OfficeDocument> Documents
        {
            get
            {
                List<OfficeDocument> documents = new List<OfficeDocument>();
                foreach (Word.Document document in this.application.Documents)
                {
                    documents.Add(new Word2003OfficeDocument(document));
                }
                return documents;
            }

        }
        protected override OfficeDocument Open(System.IO.FileInfo file)
        {
            object filedocxtoOpen = file.FullName;
            if (file.Extension.Equals(HtmlExtension, StringComparison.CurrentCultureIgnoreCase) || file.Extension.Equals(".htm", StringComparison.CurrentCultureIgnoreCase))
            {
                FileInfo docFile = new FileInfo(file.FullName.Replace(file.Extension, ".doc"));
                if (docFile.Exists)
                {
                    filedocxtoOpen = docFile.FullName;
                }
            }
            object missing = Type.Missing;
            Word.Document doc = application.Documents.Open(ref filedocxtoOpen, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
            return new Word2003OfficeDocument(doc);
        }
    }
}
