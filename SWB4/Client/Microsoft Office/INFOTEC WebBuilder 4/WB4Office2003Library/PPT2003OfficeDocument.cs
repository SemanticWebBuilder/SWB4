using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Diagnostics;
using WBOffice4;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
using Office = Microsoft.Office.Core;

namespace WB4Office2003Library
{
    public class PowerPoint2003OfficeDocument : OfficeDocument
    {
        private static object nullObjStr = String.Empty;

        
        public const String DocumentDefaultExtension = ".ppt";
        public const String FilterDescription = "Presentación de Power Point (*.ppt)|*.ppt";
        public const String HtmlExtension = ".html";
        
        private PowerPoint.Presentation presentation;
        private PowerPoint.Application application;
        public PowerPoint2003OfficeDocument(PowerPoint.Presentation presentation)
        {
            this.presentation = presentation;
            this.application = this.presentation.Application;
        }
        protected override string ApplicationVersion
        {
            get
            {
                return this.application.Version;
            }            
        }

        public override System.IO.FileInfo FilePath
        {
            get
            {                
                return new FileInfo(presentation.FullName);
            }
        }
        protected override bool IsReadOnly
        {
            get
            {
                return presentation.ReadOnly == Office.MsoTriState.msoTrue;
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

        protected override System.IO.FileInfo SaveAsHtml(System.IO.DirectoryInfo dir)
        {            
            FileInfo docX = new FileInfo(presentation.FullName);
            if (!(docX.Extension.Equals(DocumentDefaultExtension, StringComparison.CurrentCultureIgnoreCase) || docX.Extension.Equals(".pptx", StringComparison.CurrentCultureIgnoreCase)))
            {
                throw new WBAlertException("El documento debe estar en formato Word 2000/XP/2003/2007 con extensión .doc ó .docx");
            }
            if (!dir.Exists)
            {
                dir.Create();
            }
            FileInfo HTMLFile = new FileInfo(dir.FullName + Separator + this.FilePath.Name.Replace(docX.Extension, HtmlExtension));
            if (HTMLFile.Exists)
            {
                HTMLFile.Delete();
            }
            presentation.SaveAs(HTMLFile.FullName, PowerPoint.PpSaveAsFileType.ppSaveAsHTML, Office.MsoTriState.msoFalse);
            presentation.Close();
            presentation = (PowerPoint.Presentation)application.Presentations.Open(docX.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
            return HTMLFile;
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
                throw new WBOfficeErrorException("El documento no se pudo guardar debido a un error reportado por el Office " + this.application.Version, e);
            }
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

        protected override FileInfo SaveAs(DirectoryInfo dir, SaveDocument format)
        {
            FileInfo docX = new FileInfo(presentation.FullName);
            FileInfo HTMLFile = new FileInfo(dir.FullName + Separator + this.FilePath.Name.Replace(docX.Extension, HtmlExtension));            
            switch (format)
            {
                case SaveDocument.HtmlIE:                    
                    presentation.SaveAs(HTMLFile.FullName, PowerPoint.PpSaveAsFileType.ppSaveAsHTML, Office.MsoTriState.msoFalse);
                    presentation.Close();
                    presentation = (PowerPoint.Presentation)application.Presentations.Open(docX.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
                    return HTMLFile;
                case SaveDocument.HtmlAll:
                    dir.Create();
                    presentation.SaveAs(HTMLFile.FullName, PowerPoint.PpSaveAsFileType.ppSaveAsHTMLDual, Office.MsoTriState.msoFalse);
                    presentation.Close();
                    presentation = (PowerPoint.Presentation)application.Presentations.Open(docX.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
                    return HTMLFile;
                default:
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
                if (String.IsNullOrEmpty(presentation.Path))
                {
                    return true;
                }
                return false;
            }
        }

        protected override void Save(FileInfo file)
        {
            if (file.Exists)
            {
                file.Delete();
            }
            presentation.SaveAs(file.FullName, PowerPoint.PpSaveAsFileType.ppSaveAsPresentation, Office.MsoTriState.msoFalse);
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
            PowerPoint.Selection selection = presentation.Application.ActiveWindow.Selection;
            PowerPoint.Hyperlink hyperlink = selection.TextRange.ActionSettings[Microsoft.Office.Interop.PowerPoint.PpMouseActivation.ppMouseClick].Hyperlink;
            hyperlink.Address = path;
            hyperlink.TextToDisplay = titulo;
        }
    }
}
