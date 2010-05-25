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
using Excel = Microsoft.Office.Interop.Excel;
using Office = Microsoft.Office.Core;
namespace WB4Office2003Library
{
    public class Excel2003OfficeDocument : OfficeDocument
    {
        private static object missing = Type.Missing;
        private static object nullObjStr = String.Empty;
        private static object objtrue = true;

        public const String DocumentDefaultExtension = ".xls";
        public const String FilterDescription = "Libro de Excel (*.xls)|*.xls";
        public const String HtmlExtension = ".html";
        Excel.Workbook workbook;
        public Excel2003OfficeDocument(Excel.Workbook workbook)
        {
            this.workbook = workbook;
        }


        public override System.IO.FileInfo FilePath
        {
            get
            {
                return new FileInfo(workbook.FullName);
            }
        }
        protected override void CleanContentProperties()
        {
            Office.DocumentProperties docProperties = (Office.DocumentProperties)workbook.CustomDocumentProperties;
            foreach (Office.DocumentProperty prop in docProperties)
            {
                if (prop.Name.Equals(CONTENT_ID_NAME, StringComparison.InvariantCulture))
                {
                    prop.Delete();
                    break;
                }
            }
            docProperties = (Office.DocumentProperties)workbook.CustomDocumentProperties;
            foreach (Office.DocumentProperty prop in docProperties)
            {
                if (prop.Name.Equals(REPOSITORY_ID_NAME, StringComparison.InvariantCulture))
                {
                    prop.Delete();
                    break;
                }
            }
            workbook.Save();

        }

        public override Dictionary<string, string> CustomProperties
        {
            get
            {
                Dictionary<string, string> properties = new Dictionary<string, string>();
                Office.DocumentProperties excelProperties = (Office.DocumentProperties)this.workbook.CustomDocumentProperties;
                foreach (Office.DocumentProperty property in excelProperties)
                {
                    properties.Add(property.Name, property.Value.ToString());
                }
                return properties;
            }
        }

        protected override DocumentType DocumentType
        {
            get
            {
                return DocumentType.Excel;
            }
        }
        
        protected override void SaveCustomProperties(Dictionary<string, string> properties)
        {
            try
            {
                Office.DocumentProperties docProperties = (Office.DocumentProperties)workbook.CustomDocumentProperties;

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
                workbook.Save();
            }
            catch (Exception e)
            {
                throw new WBOfficeErrorException("No puede obtener las propiedades del documento", e);
            }
        }

        protected override System.IO.FileInfo SaveAsHtml(System.IO.DirectoryInfo dir)
        {
            Excel.Application application = this.workbook.Application;
            FileInfo xlsX = new FileInfo(workbook.FullName);
            FileInfo HTMLFile = new FileInfo(dir.FullName + Separator + this.FilePath.Name.Replace(xlsX.Extension, HtmlExtension));
            object fileName = HTMLFile.FullName;
            dir.Create();
            if (HTMLFile.Exists)
            {
                HTMLFile.Delete();
            }
            workbook.SaveAs(fileName, Excel.XlFileFormat.xlHtml, missing, missing, missing, missing, Excel.XlSaveAsAccessMode.xlNoChange, missing, missing, missing, missing, missing);
            workbook.Close(objtrue, missing, missing);
            workbook = (Excel.Workbook)application.Workbooks.Open(xlsX.FullName, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing);
            return HTMLFile;
        }

        public override void Save()
        {
            try
            {
                if (!this.workbook.Saved)
                {
                    this.workbook.Save();
                }
            }
            catch (Exception e)
            {
                throw new WBOfficeErrorException("El documento no se pudo guardar debido a un error reportado por el Office " + workbook.Application.Version, e);
            }
        }
        private static ICollection<FileInfo> GetLinkSources(Excel.Workbook workbook)
        {
            List<FileInfo> attachments = new List<FileInfo>();
            Array links = (Array)workbook.LinkSources(Excel.XlLink.xlExcelLinks);
            if (links != null)
            {
                foreach (String link in links)
                {
                    FileInfo f = new FileInfo(link);
                    attachments.Add(f);                    
                }
            }
            return attachments;
        }        
        private ICollection<FileInfo> GetHyperLinks(Excel.Hyperlink link)
        {
            List<FileInfo> attachments = new List<FileInfo>();
            if (link.Address != null)
            {
                FileInfo file = UriToFile(link.Address);
                if (file != null)
                {
                    attachments.Add(file);
                }
            }
            return attachments;
        }
        private ICollection<FileInfo> GetHyperLinksFromWorkbook()
        {
            List<FileInfo> attachments = new List<FileInfo>();
            foreach (Excel.Worksheet worksheet in workbook.Worksheets)
            {
                foreach (Excel.Hyperlink link in worksheet.Hyperlinks)
                {
                    attachments.AddRange(GetHyperLinks(link));
                }
            }
            return attachments;
        }
        protected override ICollection<FileInfo> Attachments
        {
            get
            {
                List<FileInfo> attachments = new List<FileInfo>();
                attachments.AddRange(GetLinkSources(workbook));
                attachments.AddRange(GetHyperLinksFromWorkbook());
                return attachments;
            }
        }

        protected override string ApplicationVersion
        {
            get
            {
                return workbook.Application.Version;
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
            //htmlFile.MoveTo(htmlFile.Directory.FullName + "/frame.html");
        }
        protected override bool IsReadOnly
        {
            get
            {
                return workbook.ReadOnly;
            }
        }
        protected override bool IsNew
        {
            get
            {
                if (String.IsNullOrEmpty(workbook.Path))
                {
                    return true;
                }
                return false;
            }
        }
        protected override void Save(FileInfo file)
        {
            object missing = Type.Missing;
            if (file.Exists)
            {
                file.Delete();
            }
            workbook.SaveAs(file.FullName, Excel.XlFileFormat.xlExcel7, missing, missing, missing, missing, Excel.XlSaveAsAccessMode.xlNoChange, missing, missing, missing, missing, missing);
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
            get {
                return DocumentDefaultExtension;
            }
        }
        public override void InsertLink(String path,String titulo)
        {
            object missing = Type.Missing;
            Excel.Range selection = (Excel.Range)workbook.Application.Selection;
            selection.Hyperlinks.Add(missing, path, missing, missing, titulo);  
        }
        public override string[] Links
        {
            get
            {
                HashSet<String> links = new HashSet<String>();
                for (int i = 1; i <= this.workbook.Sheets.Count; i++)
                {
                    Excel.Worksheet sheet = (Excel.Worksheet)this.workbook.Sheets[i];
                    for (int j = 1; j <= sheet.Hyperlinks.Count; j++)
                    {
                        Excel.Hyperlink link = (Excel.Hyperlink)sheet.Hyperlinks[j];
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
                for (int i = 1; i <= this.workbook.Sheets.Count; i++)
                {
                    Excel.Worksheet sheet = (Excel.Worksheet)this.workbook.Sheets[i];
                    images += sheet.Shapes.Count;
                }
                return images;
            }
        }

    }
}
