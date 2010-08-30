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
using Word = Microsoft.Office.Interop.Word;
using Office = Microsoft.Office.Core;
using System.Xml;
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
            try
            {
                
                foreach (Word.InlineShape shape in document.InlineShapes)
                {
                    bool isLinkShape = shape.Type == Word.WdInlineShapeType.wdInlineShapeLinkedOLEObject || shape.Type == Word.WdInlineShapeType.wdInlineShapeLinkedPicture || shape.Type == Word.WdInlineShapeType.wdInlineShapeLinkedPictureHorizontalLine;
                    if (isLinkShape && shape.LinkFormat != null && shape.LinkFormat.Type != Word.WdLinkType.wdLinkTypePicture)
                    {
                        FileInfo file = UriToFile(shape.LinkFormat.SourceFullName);
                        if (file != null)
                        {
                            attachments.Add(file);
                        }
                    }
                }
                foreach (Word.Shape shape in document.Shapes)
                {
                    if (shape.Hyperlink != null)
                    {
                        Word.Hyperlink link = shape.Hyperlink;
                        if (link.Address != null)
                        {
                            FileInfo file = UriToFile(link.Address);
                            if (file != null)
                            {
                                attachments.Add(file);
                            }
                        }
                    }
                }

                foreach (Word.InlineShape shape in document.InlineShapes)
                {
                    if (shape.Hyperlink != null)
                    {
                        Word.Hyperlink link = shape.Hyperlink;
                        if (link.Address != null)
                        {
                            FileInfo file = UriToFile(link.Address);
                            if (file != null)
                            {
                                attachments.Add(file);
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                try
                {
                    WBOffice4.OfficeApplication.WriteError(e);
                }
                catch { }
            }
            return attachments;
        }
        private ICollection<FileInfo> GetFlashLinks()
        {
            List<FileInfo> attachments = new List<FileInfo>();
            String xml = this.document.WordOpenXML;
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
                List<FileInfo> attachments = new List<FileInfo>();
                attachments.AddRange(this.GetInlineShapesLinks());
                attachments.AddRange(this.GetHyperLinks());
                attachments.AddRange(this.GetFlashLinks());
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
            if (!(this.document.Application.Selection.Range.Text == null || this.document.Application.Selection.Range.Text == ""))            
            {
                text = this.document.Application.Selection.Range.Text;
                
            }
            this.document.Hyperlinks.Add(this.document.Application.Selection.Range, ref address, ref missing, ref missing, ref text, ref missing);
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
                foreach(FileInfo file in GetFlashLinks())
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
