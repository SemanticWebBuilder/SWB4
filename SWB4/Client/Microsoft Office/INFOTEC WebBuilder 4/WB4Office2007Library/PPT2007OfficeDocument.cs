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

namespace WB4Office2007Library
{
    public class PowerPoint2007OfficeDocument : OfficeDocument
    {
        public const String HtmlExtension = ".html";
        public const String DocumentDefaultExtension = ".pptx";
        public const String Office2003Extension = ".ppt";
        private static object nullObjStr = String.Empty;
        public const String FilterDescription = "Presentación de PowerPoint (*.pptx)|*.pptx";
        private PowerPoint.Presentation presentation;
        private PowerPoint.Application application;
        public PowerPoint2007OfficeDocument(PowerPoint.Presentation presentation)
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
            presentation.SaveAs(htmlFile.FullName, PowerPoint.PpSaveAsFileType.ppSaveAsHTML, Office.MsoTriState.msoFalse);
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
                for (int i = 0; i < this.presentation.Slides.Count; i++)
                {
                    PowerPoint.Slide slide = (PowerPoint.Slide)this.presentation.Slides[i];
                    for (int j = 0; j < slide.Hyperlinks.Count; j++)
                    {
                        PowerPoint.Hyperlink link = (PowerPoint.Hyperlink)slide.Hyperlinks[j];
                        links.Add(link.Address);
                    }
                }
                return links.ToArray();
            }
        }

        public override int Images
        {
            get
            {
                int images = 0;
                for (int i = 0; i < this.presentation.Slides.Count; i++)
                {
                    PowerPoint.Slide slide = (PowerPoint.Slide)this.presentation.Slides[i];
                    images += slide.Shapes.Count;
                }
                return images;
            }
        }
    }
}
