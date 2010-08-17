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
using System.Diagnostics;
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
            OfficeDocument officeDocument=null;
            object filedocxtoOpen = file.FullName;
            object missing = Type.Missing;
            Word.Document doc = application.Documents.Open(ref filedocxtoOpen, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
            officeDocument = new Word2007OfficeDocument(doc);            
            return officeDocument;
        }
        protected override OfficeDocument Open(System.IO.FileInfo file,String contentid,String rep)
        {
            object missing = Type.Missing;
            object format = Word.WdSaveFormat.wdFormatDocument;
            OfficeDocument officeDocument = null;
            object filedocxtoOpen = file.FullName;
            if (file.Exists)
            {   
                try
                {
                    Word.Document doc = application.Documents.Open(ref filedocxtoOpen, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
                    officeDocument = new Word2007OfficeDocument(doc);
                }
                catch (Exception e)
                {
                    String path = file.FullName.Replace(".doc", ".html");
                    FileInfo fHTML = new FileInfo(path);
                    if (fHTML.Exists)
                    {
                        filedocxtoOpen = path;
                        Word.Document doc = application.Documents.Open(ref filedocxtoOpen, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
                        filedocxtoOpen = file.FullName;
                        doc.SaveAs(ref filedocxtoOpen, ref format, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
                        doc.ActiveWindow.View.Type = Word.WdViewType.wdPrintView;
                        officeDocument = new Word2007OfficeDocument(doc);
                    }
                }
            }
            else
            {
                String path = file.FullName.Replace(".doc", ".html");
                FileInfo fHTML = new FileInfo(path);
                if (fHTML.Exists)
                {
                    filedocxtoOpen = path;
                    Word.Document doc = application.Documents.Open(ref filedocxtoOpen, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
                    filedocxtoOpen = file.FullName;
                    doc.SaveAs(ref filedocxtoOpen, ref format, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
                    doc.ActiveWindow.View.Type = Word.WdViewType.wdPrintView;
                    officeDocument = new Word2007OfficeDocument(doc);
                }
            }
            if (officeDocument != null)
            {
                officeDocument.SaveContentProperties(contentid, rep);
                if (MenuListener != null)
                {
                    MenuListener.DocumentPublished();
                }
            }
            return officeDocument;
        }
    }
}
