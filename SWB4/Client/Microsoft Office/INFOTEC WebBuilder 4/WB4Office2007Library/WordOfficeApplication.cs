using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using WBOffice4;
using Word = Microsoft.Office.Interop.Word;
using Office = Microsoft.Office.Core;
namespace WB4Office2007Library
{
    public class WordOfficeApplication : OfficeApplication
    {     
        Word.Application application;
        public WordOfficeApplication(Word.Application application)
        {
            this.application = application;
            this.application.DocumentOpen += new Microsoft.Office.Interop.Word.ApplicationEvents4_DocumentOpenEventHandler(ApplicationDocumentOpen);
            this.application.DocumentChange += new Microsoft.Office.Interop.Word.ApplicationEvents4_DocumentChangeEventHandler(ApplicationDocumentChange);                        
            this.application.DocumentBeforeClose+=new Microsoft.Office.Interop.Word.ApplicationEvents4_DocumentBeforeCloseEventHandler(ApplicationDocumentBeforeClose);
        }
        private void ApplicationDocumentBeforeClose(Microsoft.Office.Interop.Word.Document document,ref bool cancel)
        {
            if(document.Application.Documents.Count==1)
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
            OfficeDocument officeDocument = new Word2007OfficeDocument(document);
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
        private void ApplicationDocumentChange()
        {
            this.ActivateDocument(this.application.ActiveDocument);
        }
        private void ApplicationDocumentOpen(Microsoft.Office.Interop.Word.Document document)
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
                    documents.Add(new Word2007OfficeDocument(document));
                }
                return documents;
            }

        }
        protected override OfficeDocument Open(System.IO.FileInfo file)
        {
            object filedocxtoOpen = file.FullName;            
            object missing = Type.Missing;            
            Word.Document doc=application.Documents.Open(ref filedocxtoOpen, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
            return new Word2007OfficeDocument(doc);            
        }
    }
}
