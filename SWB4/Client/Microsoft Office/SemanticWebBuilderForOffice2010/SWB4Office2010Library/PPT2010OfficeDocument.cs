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
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
using Office = Microsoft.Office.Core;
using System.Windows.Forms;
namespace WB4Office2010Library
{
    public class PowerPoint2010OfficeDocument : OfficeDocument
    {
        public const String HtmlExtension = ".html";
        public const String DocumentDefaultExtension = ".pptx";
        public const String Office2003Extension = ".ppt";
        private static object nullObjStr = String.Empty;
        public const String FilterDescription = "Presentación de PowerPoint (*.pptx)|*.pptx";
        private PowerPoint.Presentation presentation;
        private PowerPoint.Application application;
        public PowerPoint2010OfficeDocument(PowerPoint.Presentation presentation)
        {
            this.presentation = presentation;
            this.application = this.presentation.Application;
        }
        protected override string ApplicationVersion
        {
            get
            {
                return presentation.Application.Version;
            }
        }
        protected override bool IsReadOnly
        {
            get
            {
                return presentation.ReadOnly == Office.MsoTriState.msoTrue;
            }
        }
        public override System.IO.FileInfo FilePath
        {
            get
            {
                /*if (this.IsNew)
                {
                    throw new WBAlertException("El documento debe ser almacenado primero");
                }*/
                return new FileInfo(presentation.FullName);
            }
        }

        public override Dictionary<string, string> CustomProperties
        {
            get
            {
                Dictionary<String, String> properties = new Dictionary<string, string>();
                Office.DocumentProperties docProperties = (Office.DocumentProperties)presentation.CustomDocumentProperties;
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
                return DocumentType.PPT;
            }
        }
        protected override void CleanContentProperties()
        {
            Office.DocumentProperties docProperties = (Office.DocumentProperties)presentation.CustomDocumentProperties;
            foreach (Office.DocumentProperty prop in docProperties)
            {
                if (prop.Name.Equals(CONTENT_ID_NAME, StringComparison.InvariantCulture))
                {
                    prop.Delete();
                    break;
                }
            }
            docProperties = (Office.DocumentProperties)presentation.CustomDocumentProperties;
            foreach (Office.DocumentProperty prop in docProperties)
            {
                if (prop.Name.Equals(REPOSITORY_ID_NAME, StringComparison.InvariantCulture))
                {
                    prop.Delete();
                    break;
                }
            }
            presentation.Save();
        }
        protected override void SaveCustomProperties(Dictionary<string, string> properties)
        {
            try
            {
                Office.DocumentProperties docProperties = (Office.DocumentProperties)presentation.CustomDocumentProperties;

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
                presentation.Save();
            }
            catch (Exception e)
            {
                throw new WBOfficeErrorException("No puede obtener las propiedades del documento", e);
            }
            
        }

        public override void Save()
        {
            try
            {
                if (presentation.Saved != Office.MsoTriState.msoTrue)
                {
                    presentation.Save();
                }
            }
            catch (Exception e)
            {
                throw new WBOfficeErrorException("El documento no se pudo guardar debido a un error reportado por el Office " + presentation.Application.Version, e);
            }
        }
        
        private ICollection<FileInfo> GetHiperlinks(PowerPoint.Slide slide)
        {
            List<FileInfo> attachments = new List<FileInfo>();
            foreach (PowerPoint.Hyperlink link in slide.Hyperlinks)
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
                foreach (PowerPoint.Slide slide in presentation.Slides)
                {
                    attachments.AddRange(GetHiperlinks(slide));
                }
                return attachments;
            }
        }

        protected override FileInfo SaveAs(DirectoryInfo dir, SaveDocument format)
        {
            String basepath = dir.FullName + Separator;
            FileInfo fileToReturn = new FileInfo(basepath + this.FilePath.Name.Replace(this.FilePath.Extension, HtmlExtension));
            switch (format)
            {
                case SaveDocument.HtmlIE:
                    this.SaveAsHtml(fileToReturn);
                    return fileToReturn;
                case SaveDocument.HtmlAll:
                    SaveAsHtmlAll(fileToReturn);
                    return fileToReturn;
                case SaveDocument.Office2003:
                    fileToReturn = new FileInfo(basepath + this.FilePath.Name.Replace(this.FilePath.Extension, Office2003Extension));
                    this.SaveOffice2003(fileToReturn);
                    return fileToReturn;
                default:
                    fileToReturn = new FileInfo(basepath + this.FilePath.Name.Replace(this.FilePath.Extension, DocumentDefaultExtension));
                    this.Save(fileToReturn);
                    return fileToReturn;
            }
        }
        protected override FileInfo SaveAsHtml(DirectoryInfo dir)
        {
            SaveAs(dir, SaveDocument.Office2003);
            FileInfo docX = new FileInfo(presentation.FullName);
            FileInfo HTMLFile = new FileInfo(dir.FullName + Separator + this.FilePath.Name.Replace(docX.Extension, HtmlExtension));
            return this.SaveAsHtml(HTMLFile);
        }
        public FileInfo SaveAsHtmlAll(FileInfo htmlFile)
        {
            FileInfo docX = new FileInfo(presentation.FullName);
            htmlFile.Directory.Create();
            if (htmlFile.Exists)
            {
                htmlFile.Delete();
            }
            presentation.SaveAs(htmlFile.FullName, PowerPoint.PpSaveAsFileType.ppSaveAsHTMLDual, Office.MsoTriState.msoFalse);
            presentation.Close();
            presentation = (PowerPoint.Presentation)application.Presentations.Open(docX.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
            return htmlFile;
        }

        public FileInfo SaveAsHtml(FileInfo htmlFile)
        {
            FileInfo docX = new FileInfo(presentation.FullName);
            htmlFile.Directory.Create();
            if (htmlFile.Exists)
            {
                htmlFile.Delete();
            }
            presentation.WebOptions.AllowPNG = Office.MsoTriState.msoTrue;
            presentation.WebOptions.OrganizeInFolder = Office.MsoTriState.msoTrue;
            presentation.WebOptions.RelyOnVML = Office.MsoTriState.msoFalse;            
            presentation.WebOptions.UseLongFileNames = Office.MsoTriState.msoTrue;
            presentation.WebOptions.IncludeNavigation = Office.MsoTriState.msoTrue;
            presentation.WebOptions.TargetBrowser = Office.MsoTargetBrowser.msoTargetBrowserIE6;
            presentation.WebOptions.HTMLVersion = PowerPoint.PpHTMLVersion.ppHTMLDual;
            presentation.WebOptions.ResizeGraphics = Office.MsoTriState.msoTrue;
            presentation.WebOptions.Encoding = Office.MsoEncoding.msoEncodingISO88591Latin1;
            presentation.SaveAs(htmlFile.FullName, PowerPoint.PpSaveAsFileType.ppSaveAsHTMLDual, Office.MsoTriState.msoFalse);            
            presentation.Close();
            presentation = (PowerPoint.Presentation)application.Presentations.Open(docX.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
            return htmlFile;
        }
        protected override void Save(FileInfo file)
        {
            FileInfo docX = new FileInfo(presentation.FullName);
            DirectoryInfo dir = file.Directory;
            dir.Create();
            if (file.Exists)
            {
                file.Delete();
            }
            presentation.SaveAs(file.FullName, PowerPoint.PpSaveAsFileType.ppSaveAsOpenXMLPresentation, Office.MsoTriState.msoFalse);
            presentation.Close();
            presentation = (PowerPoint.Presentation)application.Presentations.Open(file.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
        }
        public void SaveOffice2003(FileInfo file)
        {
            FileInfo docX = new FileInfo(presentation.FullName);
            DirectoryInfo dir = file.Directory;
            dir.Create();
            if (file.Exists)
            {
                file.Delete();
            }
            presentation.SaveAs(file.FullName, PowerPoint.PpSaveAsFileType.ppSaveAsPresentation, Office.MsoTriState.msoFalse);
            presentation.Close();
            presentation = (PowerPoint.Presentation)application.Presentations.Open(docX.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
        }

        protected override void PrepareHtmlFileToSend(FileInfo htmlFile)
        {            
            String script="<script>\r\n<!--\r\nvar ver = 0, appVer = navigator.appVersion, msie = appVer.indexOf( \"MSIE \" )\r\n" +
            "var msieWin31 = (appVer.indexOf( \"Windows 3.1\" ) >= 0), isMac = (appVer.indexOf(\"Macintosh\") >= 0)\r\n" +
            "if( msie >= 0 )\r\nver = parseFloat( appVer.substring( msie+5, appVer.indexOf ( \";\", msie ) ) )\r\n" +
            "else\r\nver = parseInt( appVer )\r\n" +
            //"if( !isMac && ver >= 4 && msie >= 0 )\r\nwindow.location.replace( \"frame.html\"+document.location.hash )\r\n" +
            "if( !isMac && ver >= 4 && msie >= 0 )\r\ni=0;\r\n" +
            "else if( ver >= 3 ) {\r\nvar path = \"v3_document.html\"\r\n" +
            "if ( !msieWin31 && ( ( msie >= 0 && ver >= 3.02 ) || ( msie < 0 && ver >= 3 ) ) )\r\n" +
            "window.location.replace( path )\r\nelse\r\n" +
            "window.location.href = path\r\n}\r\n//-->\r\n</script>";
            int pos = htmlFile.Name.LastIndexOf(htmlFile.Extension);
            String name = htmlFile.Name;
            if (pos > 0)
            {
                name = htmlFile.Name.Substring(0, pos);
            }
            String path = htmlFile.Directory.FullName + "/" + name + this.presentation.WebOptions.FolderSuffix+"/frame.html";
            FileInfo framehtml = new FileInfo(path);
            if (framehtml.Exists)
            {
                StreamReader fin = framehtml.OpenText();
                String html = fin.ReadToEnd();
                fin.Close();
                pos = html.IndexOf("</head>");
                if (pos > 0)
                {
                    String newhtml = html.Substring(0, pos);
                    newhtml += script;
                    newhtml += html.Substring(pos);
                    html = newhtml;
                }
                FileStream fout = framehtml.OpenWrite();
                byte[] bcont = System.Text.Encoding.Default.GetBytes(html);
                fout.Write(bcont, 0, bcont.Length);
                fout.Close();
            }
            

        }

        protected override bool IsNew
        {
            get
            {
                if (string.IsNullOrEmpty(presentation.Path))
                {
                    return true;
                }
                return false;
            }
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
            /*this.presentation.Slides.Range(1).Hyperlinks.
            PowerPoint.Selection selection=presentation.Application.ActiveWindow.Selection;
            selection.TextRange.ActionSettings[PowerPoint.PpMouseActivation.ppMouseClick].Action = PowerPoint.PpActionType.ppActionHyperlink;
            PowerPoint.Hyperlink hyperlink = selection.TextRange.ActionSettings[PowerPoint.PpMouseActivation.ppMouseClick].Hyperlink;
            hyperlink.Address = path;            
            hyperlink.TextToDisplay = titulo;*/
        }

        public override string[] Links
        {
            get
            {
                HashSet<String> links = new HashSet<String>();
                for (int i = 1; i <= this.presentation.Slides.Count; i++)
                {
                    PowerPoint.Slide slide = (PowerPoint.Slide)this.presentation.Slides[i];
                    for (int j = 1; j <= slide.Hyperlinks.Count; j++)
                    {
                        PowerPoint.Hyperlink link = (PowerPoint.Hyperlink)slide.Hyperlinks[j];
                        if (link.Address != null)
                        {
                            links.Add(link.Address);
                        }
                    }
                }
                return links.ToArray();
            }
        }
        public override String SelectedText
        {
            get
            {
                return null;
            }
        }

        public override int Images
        {
            get
            {
                int images = 0;
                for (int i = 1; i <= this.presentation.Slides.Count; i++)
                {
                    PowerPoint.Slide slide = (PowerPoint.Slide)this.presentation.Slides[i];
                    for (int j = 1; j <= slide.Shapes.Count; j++)
                    {
                        PowerPoint.Shape shape = (PowerPoint.Shape)slide.Shapes[j];
                        if (shape.Type == Microsoft.Office.Core.MsoShapeType.msoPicture)
                        {
                            images++;
                        }                        
                        else if (shape.PlaceholderFormat.Type==PowerPoint.PpPlaceholderType.ppPlaceholderObject)
                        {
                            images++;
                        }
                        else if (shape.PlaceholderFormat.Type == PowerPoint.PpPlaceholderType.ppPlaceholderPicture)
                        {
                            images++;
                        }
                        else if (shape.PlaceholderFormat.Type == PowerPoint.PpPlaceholderType.ppPlaceholderOrgChart)
                        {
                            images++;
                        }
                        if (shape.PlaceholderFormat.Type == PowerPoint.PpPlaceholderType.ppPlaceholderChart)
                        {
                            images++;
                        }
                    }
                }
                return images;
            }
        }
    }
}
