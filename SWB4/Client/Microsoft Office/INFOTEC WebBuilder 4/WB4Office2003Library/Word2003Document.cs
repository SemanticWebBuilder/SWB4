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
using WBOffice4;
using WB4Office2003Library;
using Word = Microsoft.Office.Interop.Word;
using Office = Microsoft.Office.Core;
using System.Xml;
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
                if (link.Address != null)
                {
                    FileInfo file = UriToFile(link.Address);
                    if (file != null)
                    {
                        attachments.Add(file);
                    }
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
            if (!(this.document.Application.Selection.Range.Text == null || this.document.Application.Selection.Range.Text == ""))            
            {
                text = this.document.Application.Selection.Range.Text;
            }
            this.document.Hyperlinks.Add(this.document.Application.Selection.Range, ref address, ref missing, ref missing, ref text, ref missing);
        }
        private ICollection<FileInfo> GetFlashLinks()
        {
            List<FileInfo> attachments = new List<FileInfo>();
            /*String xml = this.document.WordOpenXML;
            XmlDocument docopenXml = new XmlDocument();
            docopenXml.LoadXml(xml);
            XmlNamespaceManager manager = new XmlNamespaceManager(docopenXml.NameTable);
            string prefix = manager.LookupPrefix("http://schemas.microsoft.com/office/2006/activeX");
            String snamespace = manager.LookupNamespace("ax");
            if (prefix == null)
            {
                prefix = "ax";
                manager.AddNamespace(prefix, "http://schemas.microsoft.com/office/2006/activeX");
            }
            prefix = manager.LookupPrefix("http://schemas.microsoft.com/office/2006/xmlPackage");
            if (prefix == null)
            {
                prefix = "pkg";
                manager.AddNamespace(prefix, "http://schemas.microsoft.com/office/2006/xmlPackage");
            }

            XmlNodeList nodes = docopenXml.SelectNodes("//pkg:xmlData/ax:ocx/ax:ocxPr", manager);
            foreach (XmlNode node in nodes)
            {
                if (node is XmlElement)
                {
                    XmlElement ocxPr = (XmlElement)node;
                    String nameatt = ocxPr.GetAttribute("name", "http://schemas.microsoft.com/office/2006/activeX");
                    if (nameatt != null && (nameatt.ToLower().Equals("src") || nameatt.ToLower().Equals("movie")))
                    {
                        String value = ocxPr.GetAttribute("value", "http://schemas.microsoft.com/office/2006/activeX");
                        if (value != null && value.ToLower().EndsWith(".swf"))
                        {
                            String archivo = value;
                            System.Uri basepath = new System.Uri(this.document.Path + "\\");
                            System.Uri filepath = new System.Uri(basepath, archivo);
                            if (filepath.IsFile)
                            {
                                FileInfo farchivo = new FileInfo(filepath.LocalPath);
                                if (farchivo.Extension.IndexOf(".") != -1)
                                {
                                    if (!attachments.Contains(farchivo))
                                    {

                                        attachments.Add(farchivo);
                                    }
                                }
                            }
                        }
                    }
                }
            }*/

            return attachments;
        }
        public override string[] Links
        {
            get
            {
                HashSet<String> links = new HashSet<String>();
                foreach (Word.Hyperlink link in document.Hyperlinks)
                {
                    if (link.Address != null)
                    {
                        links.Add(link.Address);
                    }
                }
                foreach (FileInfo file in GetFlashLinks())
                {
                    links.Add(file.FullName);
                }
                foreach (FileInfo file in GetInlineShapesLinks())
                {
                    links.Add(file.FullName);
                }
                return links.ToArray();
            }
        }
        public override String SelectedText
        {
            get
            {
                return this.document.Application.Selection.Text;
            }
        }
        public override int Images
        {
            get 
            {
                return document.InlineShapes.Count + document.Shapes.Count; 
            }
        }
    }
}
