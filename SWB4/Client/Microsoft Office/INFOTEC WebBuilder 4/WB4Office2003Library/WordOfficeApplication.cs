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
        protected override OfficeDocument Open(System.IO.FileInfo file,String contentid,String rep)
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
            Word2003OfficeDocument officeDocument=new Word2003OfficeDocument(doc);
            officeDocument.SaveContentProperties(contentid, rep);
            return officeDocument;
        }
    }
}
