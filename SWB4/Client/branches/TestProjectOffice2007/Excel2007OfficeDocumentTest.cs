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
using WB4Office2007Library;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Excel = Microsoft.Office.Interop.Excel;
using System.IO;
using System.Collections.Generic;
using WBOffice4;

namespace TestProjectOffice2007
{
    
    
    /// <summary>
    ///This is a test class for Excel2007OfficeDocumentTest and is intended
    ///to contain all Excel2007OfficeDocumentTest Unit Tests
    ///</summary>
    [TestClass()]
    public class Excel2007OfficeDocumentTest
    {
        private static object missing = Type.Missing;
        Excel.Application application;
        ExcelApplication officeApplication;
        private FileInfo demoDoc = new FileInfo(@"c:\temp\demo.xls");
        private TestContext testContextInstance;

        /// <summary>
        ///Gets or sets the test context which provides
        ///information about and functionality for the current test run.
        ///</summary>
        public TestContext TestContext
        {
            get
            {
                return testContextInstance;
            }
            set
            {
                testContextInstance = value;
            }
        }

        #region Additional test attributes
        // 
        //You can use the following additional attributes as you write your tests:
        //
        //Use ClassInitialize to run code before running the first test in the class
        //[ClassInitialize()]
        //public static void MyClassInitialize(TestContext testContext)
        //{
        //}
        //
        //Use ClassCleanup to run code after all tests in a class have run
        //[ClassCleanup()]
        //public static void MyClassCleanup()
        //{
        //}
        //
        //Use TestInitialize to run code before running each test
        [TestInitialize()]
        public void MyTestInitialize()
        {
            application = new Excel.ApplicationClass();
            officeApplication = new ExcelApplication(this.application);
        }
        //
        //Use TestCleanup to run code after each test has run
        [TestCleanup()]
        public void MyTestCleanup()
        {
            application.Quit();
        }
        //
        #endregion

        private Excel.Workbook Open(FileInfo file)
        {
            Excel.Workbook workbook = application.Workbooks.Open(file.FullName, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing, missing);
            return workbook;
        }
        /// <summary>
        ///A test for isNew
        ///</summary>
        [TestMethod()]
        public void IsNoNewTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            bool actual;
            actual = target.IsNew;
            bool expected = false;
            Assert.AreEqual(expected, actual);
        }

        /// <summary>
        ///A test for isNew
        ///</summary>
        [TestMethod()]
        public void IsNewTest()
        {
            Excel.Workbook workbook = this.application.Workbooks.Add(System.Type.Missing);
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(workbook);
            bool actual;
            actual = target.IsNew;
            bool expected = true;
            Assert.AreEqual(expected, actual);
        }

        /// <summary>
        ///A test for DocumentFilter
        ///</summary>
        [TestMethod()]
        public void DocumentFilterTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            string actual;
            actual = target.DocumentFilter;
            string expected = "Libro de Excel (*.xlsx)|*.xlsx";
            Assert.AreEqual(actual, expected);            
        }

        /// <summary>
        ///A test for DefaultExtension
        ///</summary>
        [TestMethod()]
        public void DefaultExtensionTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            string actual = target.DefaultExtension;
            string expected = ".xlsx";
            Assert.AreEqual(expected, actual);
        }

        /// <summary>
        ///A test for SaveCustomProperties
        ///</summary>
        [TestMethod()]        
        public void SaveCustomPropertiesTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            Dictionary<string, string> properties = new Dictionary<string, string>();
            properties.Add("id", "1");
            properties.Add("tp", "2");
            target.SaveCustomProperties(properties);            
        }

        /// <summary>
        ///A test for SaveAsHTML
        ///</summary>
        [TestMethod()]
        public void SaveAsHtmlTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            DirectoryInfo dir = new DirectoryInfo("c:\\temp\\exceltemp\\");
            FileInfo expected = new FileInfo(dir.FullName+"\\demo.html");
            FileInfo actual;
            actual = target.SaveAsHtml(dir);
            Assert.AreEqual(expected.FullName.ToUpperInvariant(), actual.FullName.ToUpperInvariant());
            Assert.IsTrue(actual.Exists);
        }

        /// <summary>
        ///A test for SaveAs
        ///</summary>
        [TestMethod()]
        public void SaveAsTestHtmlAll()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            DirectoryInfo dir = new DirectoryInfo("c:\\temp\\exceltemp\\");
            SaveDocument format = SaveDocument.HtmlAll;
            FileInfo expected = new FileInfo(dir.FullName + "\\demo.html");
            FileInfo actual;
            actual = target.SaveAs(dir, format);
            Assert.AreEqual(expected.FullName.ToUpperInvariant(), actual.FullName.ToUpperInvariant());
            Assert.IsTrue(actual.Exists);
            
        }

        /// <summary>
        ///A test for SaveAs
        ///</summary>
        [TestMethod()]
        public void SaveAsTestHtmlIE()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            DirectoryInfo dir = new DirectoryInfo("c:\\temp\\exceltemp\\");
            SaveDocument format = SaveDocument.HtmlIE;
            FileInfo expected = new FileInfo(dir.FullName + "\\demo.html");
            FileInfo actual;
            actual = target.SaveAs(dir, format);
            Assert.AreEqual(expected.FullName.ToUpperInvariant(), actual.FullName.ToUpperInvariant());
            Assert.IsTrue(actual.Exists);

        }


        /// <summary>
        ///A test for SaveAs
        ///</summary>
        [TestMethod()]
        public void SaveAsTestOffice2003()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            DirectoryInfo dir = new DirectoryInfo("c:\\temp\\exceltemp\\");
            SaveDocument format = SaveDocument.Office2003;
            FileInfo expected = new FileInfo(dir.FullName + "\\demo.xls");
            FileInfo actual;
            actual = target.SaveAs(dir, format);
            Assert.AreEqual(expected.FullName.ToUpperInvariant(), actual.FullName.ToUpperInvariant());
            Assert.IsTrue(actual.Exists);

        }


        /// <summary>
        ///A test for SaveAs
        ///</summary>
        [TestMethod()]
        public void SaveAsTestOOXml2007()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            DirectoryInfo dir = new DirectoryInfo("c:\\temp\\exceltemp\\");
            SaveDocument format = SaveDocument.OOXml2007;
            FileInfo expected = new FileInfo(dir.FullName + "\\demo.xlsx");
            FileInfo actual;
            actual = target.SaveAs(dir, format);
            Assert.AreEqual(expected.FullName.ToUpperInvariant(), actual.FullName.ToUpperInvariant());
            Assert.IsTrue(actual.Exists);

        }

        /// <summary>
        ///A test for Save
        ///</summary>
        [TestMethod()]
        public void SaveTestWithOutFile()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            target.Save();            
        }

        /// <summary>
        ///A test for Save
        ///</summary>
        [TestMethod()]
        public void SaveTestWithFile()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            FileInfo file = new FileInfo("c:\\temp\\deeeeemo\\demo.xlsx"); 
            target.Save(file);
            Assert.IsTrue(file.Exists);
        }
               

        /// <summary>
        ///A test for GetLocalPath
        ///</summary>
        [TestMethod()]
        public void GetLocalPathTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            FileInfo expected = this.demoDoc;
            FileInfo actual;
            actual = target.FilePath;
            Assert.AreEqual(expected.FullName.ToUpperInvariant(), actual.FullName.ToUpperInvariant());            
        }

        /// <summary>
        ///A test for GetDocumentType
        ///</summary>
        [TestMethod()]
        public void DocumentTypeTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            DocumentType expected = DocumentType.Excel;
            DocumentType actual;
            actual = target.DocumentType;
            Assert.AreEqual(expected, actual);
        }

        /// <summary>
        ///A test for GetCustomProperties
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void CustomPropertiesTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            Dictionary<string, string> actual;
            actual = target.CustomProperties;
            Assert.AreEqual(actual.Count, 0);            
        }

        /// <summary>
        ///A test for GetAttachments
        ///</summary>
        [TestMethod()]
        public void GetAttachmentsTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            ICollection<FileInfo> actual;
            actual = target.Attachments;            
            Assert.AreEqual(actual.Count, 1);            
        }

        /// <summary>
        ///A test for GetApplicationVersion
        ///</summary>
        [TestMethod()]
        public void ApplicationVersionTest()
        {
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            string expected = "12.0"; 
            string actual;
            actual = target.ApplicationVersion;
            Assert.AreEqual(expected, actual);            
        }

        /// <summary>
        ///A test for Excel2007OfficeDocument Constructor
        ///</summary>
        [TestMethod()]
        public void Excel2007OfficeDocumentConstructorTest()
        {
            Excel2007OfficeDocument_Accessor actual = new Excel2007OfficeDocument_Accessor(Open(this.demoDoc));
            Excel2007OfficeDocument_Accessor target = new Excel2007OfficeDocument_Accessor(this.application.ActiveWorkbook);
            Assert.AreEqual(actual.FilePath.FullName.ToUpperInvariant(), target.FilePath.FullName.ToUpperInvariant());
        }
    }
}
