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
namespace WB4Office2010Library
{
    public class Excel2010OfficeDocument : OfficeDocument
    {
        private static object missing = Type.Missing;
        private static object objtrue = true;
        private static object nullObjStr = String.Empty;
        public const String HtmlExtension = ".html";
        public const String DocumentDefaultExtension = ".xlsx";
        public const String Office2003Extension = ".xls";
        public const String FilterDescription = "Libro de Excel (*.xlsx)|*.xlsx";
        private Excel.Workbook workbook;
        public Excel2010OfficeDocument(Excel.Workbook workbook)
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
        protected override bool IsReadOnly
        {
            get
            {
                return workbook.ReadOnly;
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
            workbook.Saved = false;
            try
            {
                workbook.Save();
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.Message);
            }
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
            workbook.WebOptions.AllowPNG = true;
            workbook.WebOptions.Encoding = Office.MsoEncoding.msoEncodingISO88591Latin1;
            workbook.WebOptions.OrganizeInFolder = true;
            workbook.WebOptions.RelyOnCSS = true;
            workbook.WebOptions.RelyOnVML = false;            
            workbook.WebOptions.TargetBrowser = Office.MsoTargetBrowser.msoTargetBrowserV3;            
            workbook.WebOptions.UseLongFileNames = true;            
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
        private ICollection<FileInfo> GetLinks()
        {
            List<FileInfo> attachments = new List<FileInfo>();

            Array links = (Array)workbook.LinkSources(Excel.XlLink.xlExcelLinks);
            if (links != null)
            {
                foreach (String link in links)
                {
                    attachments.Add(new FileInfo(link));
                }
            }
            return attachments;
        }
        private FileInfo GetLinkFile(string archivo)
        {
            System.Uri filepath = new System.Uri(this.FilePath.DirectoryName + Separator + archivo);
            return new FileInfo(filepath.LocalPath);
        }
        private bool isFile(string archivo)
        {
            System.Uri filepath = new System.Uri(this.FilePath.DirectoryName + Separator + archivo);
            if (filepath.IsFile)
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        private ICollection<FileInfo> GetHyperLinksWorkSheet(Excel.Worksheet worksheet)
        {
            List<FileInfo> attachments = new List<FileInfo>();
            Excel.Hyperlinks links = worksheet.Hyperlinks;
            foreach (Excel.Hyperlink link in links)
            {
                if (link.Address != null)
                {
                    String archivo = link.Address;
                    if (archivo != null && isFile(archivo))
                    {
                        attachments.Add(this.GetLinkFile(archivo));
                    }
                }
            }
            return attachments;
        }
        private ICollection<FileInfo> GetHyperLinks()
        {
            List<FileInfo> attachments = new List<FileInfo>();
            foreach (Excel.Worksheet worksheet in workbook.Worksheets)
            {
                attachments.AddRange(GetHyperLinksWorkSheet(worksheet));
            }
            return attachments;
        }
        protected override ICollection<FileInfo> Attachments
        {
            get
            {
                List<FileInfo> attachments = new List<FileInfo>();
                attachments.AddRange(this.GetLinks());
                attachments.AddRange(this.GetHyperLinks());
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
            else if (format == SaveDocument.Office2003)
            {
                String nameDocFile = this.FilePath.Name.Replace(this.FilePath.Extension, Office2003Extension);
                FileInfo docFile = new FileInfo(dir.FullName + Separator + nameDocFile);
                this.SaveOffice2003(docFile);
                return docFile;
            }
            else
            {
                String nameDocxFile = this.FilePath.Name.Replace(this.FilePath.Extension, DocumentDefaultExtension);
                FileInfo docXFile = new FileInfo(dir.FullName + Separator + nameDocxFile);
                this.Save(docXFile);
                return docXFile;
            }
        }

        protected override void PrepareHtmlFileToSend(FileInfo htmlFile)
        {
            //htmlFile.MoveTo(htmlFile.Directory.FullName + "/frame.html");
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
        public void SaveOffice2003(FileInfo file)
        {
            DirectoryInfo dir = file.Directory;
            dir.Create();
            if (file.Exists)
            {
                file.Delete();
            }
            workbook.SaveAs(file.FullName, Excel.XlFileFormat.xlExcel8, missing, missing, missing, missing, Excel.XlSaveAsAccessMode.xlNoChange, missing, missing, missing, missing, missing);
        }
        protected override void Save(FileInfo file)
        {
            DirectoryInfo dir = file.Directory;
            dir.Create();
            if (file.Exists)
            {
                file.Delete();
            }
            workbook.SaveAs(file.FullName, Excel.XlFileFormat.xlOpenXMLWorkbook, missing, missing, missing, missing, Excel.XlSaveAsAccessMode.xlNoChange, missing, missing, missing, missing, missing);
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
            Excel.Range selection=(Excel.Range)workbook.Application.Selection;            
            selection.Hyperlinks.Add(missing, path, missing, missing, titulo);            
        }


        public override string[] Links
        {            
            get
            {
                HashSet<String> links=new HashSet<String>();
                for(int i=1;i<=this.workbook.Sheets.Count;i++)
                {
                    Excel.Worksheet sheet=(Excel.Worksheet)this.workbook.Sheets[i];
                    for(int j=1;j<=sheet.Hyperlinks.Count;j++)
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
