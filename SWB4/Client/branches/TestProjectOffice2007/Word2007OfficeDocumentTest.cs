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
using Word=Microsoft.Office.Interop.Word;
using System.IO;
using System.Collections.Generic;
using WBOffice4;

namespace TestProjectOffice2007
{
    
    
    /// <summary>
    ///This is a test class for Word2007OfficeDocumentTest and is intended
    ///to contain all Word2007OfficeDocumentTest Unit Tests
    ///</summary>
    [TestClass()]
    public class Word2007OfficeDocumentTest
    {

        private Word.Application application;
        WordOfficeApplication officeApplication;
        private FileInfo demoDoc = new FileInfo(@"c:\temp\demo.doc");                
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
            application = new Word.ApplicationClass();
            officeApplication = new WordOfficeApplication(this.application);            
        }
        //
        //Use TestCleanup to run code after each test has run
        [TestCleanup()]
        public void MyTestCleanup()
        {
            object missing = System.Type.Missing;
            object objfalse = false;
            ((Word.ApplicationClass)application).Quit(ref objfalse, ref missing, ref missing);
        }
        //
        #endregion


        /// <summary>
        ///A test for isNew
        ///</summary>
        [TestMethod()]        
        public void IsNewTest()
        {
            object missing=System.Type.Missing;
            this.application.Documents.Add(ref missing, ref missing, ref missing, ref missing);
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(this.application.ActiveDocument); 
            bool actual;
            actual = target.IsNew;
            bool expected = true;
            Assert.AreEqual(actual, expected);            
        }

        /// <summary>
        ///A test for isNew
        ///</summary>
        [TestMethod()]
        public void isNotReadOnlyTest()
        {
            object missing = System.Type.Missing;
            this.application.Documents.Add(ref missing, ref missing, ref missing, ref missing);
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(this.application.ActiveDocument);
            bool actual;
            actual = target.IsReadOnly;
            bool expected = false;
            Assert.AreEqual(actual, expected);
        }
        /// <summary>
        ///A test for isNew
        ///</summary>
        [TestMethod()]
        public void isReadOnlyTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            bool actual;
            actual = target.IsReadOnly;
            bool expected = true;
            Assert.AreEqual(actual, expected);
        }
        private Word.Document Open(FileInfo file)
        {
            object filedocxtoOpen = file.FullName;
            object missing = Type.Missing;
            Word.Document doc = application.Documents.Open(ref filedocxtoOpen, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
            return doc;
        }

        /// <summary>
        ///A test for isNew
        ///</summary>
        [TestMethod()]
        public void IsNotNewTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            bool actual;
            actual = target.IsNew;
            bool expected = false;
            Assert.AreEqual(actual, expected);
        }

        /// <summary>
        ///A test for DocumentFilter
        ///</summary>
        [TestMethod()]
        public void DocumentFilterTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            string actual;
            actual = target.DocumentFilter;
            string expected = "Documento de Word(*.docx)|*.docx";
            Assert.AreEqual(actual, expected);            
        }

        /// <summary>
        ///A test for DefaultExtension
        ///</summary>
        [TestMethod()]
        public void DefaultExtensionTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            string actual;
            actual = target.DefaultExtension;
            string expected = ".docx";
            Assert.AreEqual(actual, expected);
        }

        /// <summary>
        ///A test for SaveCustomProperties
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void SaveCustomPropertiesTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            Dictionary<string, string> properties = new Dictionary<string,string>(); 
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
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            DirectoryInfo dir = new DirectoryInfo(@"c:\temp\tempdemo\");
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
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            DirectoryInfo dir = new DirectoryInfo(@"c:\temp\tempdemo\");
            SaveDocument format = SaveDocument.HtmlAll;
            FileInfo expected = new FileInfo(dir.FullName + "\\demo.html"); ; 
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
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            DirectoryInfo dir = new DirectoryInfo(@"c:\temp\tempdemo\");
            SaveDocument format = SaveDocument.HtmlIE;
            FileInfo expected = new FileInfo(dir.FullName + "\\demo.html"); ; 
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
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            DirectoryInfo dir = new DirectoryInfo(@"c:\temp\tempdemo\");
            SaveDocument format = SaveDocument.Office2003;
            FileInfo expected = new FileInfo(dir.FullName + "\\demo.doc"); 
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
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            DirectoryInfo dir = new DirectoryInfo(@"c:\temp\tempdemo\");
            SaveDocument format = SaveDocument.OOXml2007;
            FileInfo expected = new FileInfo(dir.FullName + "\\demo.docx"); 
            FileInfo actual;
            actual = target.SaveAs(dir, format);
            Assert.AreEqual(expected.FullName.ToUpperInvariant(), actual.FullName.ToUpperInvariant());
            Assert.IsTrue(actual.Exists);
        }

        /// <summary>
        ///A test for Save
        ///</summary>
        [TestMethod()]
        public void SaveTestWithoutFile()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            target.Save();            
        }

        /// <summary>
        ///A test for Save
        ///</summary>
        [TestMethod()]
        public void SaveTestWithFile()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            FileInfo file = new FileInfo("c:\\temp\\demodemodemo\\demo"+target.DefaultExtension);
            target.Save(file);
            Assert.AreEqual(file.FullName.ToUpperInvariant(), target.FilePath.FullName.ToUpperInvariant());
            Assert.IsTrue(file.Exists);
            
        }

        

        /// <summary>
        ///A test for GetLocalPath
        ///</summary>
        [TestMethod()]
        public void GetLocalPathTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            FileInfo actual;
            actual = target.FilePath;
            Assert.AreEqual(this.demoDoc.FullName.ToUpperInvariant(), actual.FullName.ToUpperInvariant());
            Assert.IsTrue(actual.Exists);
        }

        /// <summary>
        ///A test for GetDocumentType
        ///</summary>
        [TestMethod()]
        public void DocumentTypeTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            DocumentType expected = DocumentType.Word;
            DocumentType actual;
            actual = target.DocumentType;
            Assert.AreEqual(expected, actual);            
        }

        /// <summary>
        ///A test for GetCustomProperties
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void GetCustomPropertiesTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            Dictionary<string, string> expected = new Dictionary<string,string>(); // TODO: Initialize to an appropriate value
            Dictionary<string, string> actual;
            actual = target.CustomProperties;
            Assert.AreEqual(expected.Count, actual.Count);            
        }

        /// <summary>
        ///A test for GetAttachments
        ///</summary>
        [TestMethod()]
        public void GetAttachmentsTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            ICollection<FileInfo> actual;
            actual = target.Attachments;
            Assert.AreEqual( actual.Count,1);            
        }

        /// <summary>
        ///A test for GetApplicationVersion
        ///</summary>
        [TestMethod()]
        public void GetApplicationVersionTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            string expected = "12.0"; 
            string actual;
            actual = target.ApplicationVersion;
            Assert.AreEqual(expected, actual);            
        }

        /// <summary>
        ///A test for Word2007OfficeDocument Constructor
        ///</summary>
        [TestMethod()]
        public void Word2007OfficeDocumentConstructorTest()
        {
            Word2007OfficeDocument_Accessor target = new Word2007OfficeDocument_Accessor(Open(this.demoDoc));            
            Word2007OfficeDocument_Accessor actual = new Word2007OfficeDocument_Accessor(this.application.ActiveDocument);
            Assert.AreEqual(actual.FilePath.FullName.ToUpperInvariant(), target.FilePath.FullName.ToUpperInvariant());
        }
        /// <summary>
        ///A test for GetApplicationVersion
        ///</summary>
        [TestMethod()]
        public void PublishTest()
        {
            Word2007OfficeDocument target = new Word2007OfficeDocument(Open(this.demoDoc));    
            target.SaveToSite();
        }
        
    }
}
