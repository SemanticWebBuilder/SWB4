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
 
﻿using System.IO;
using System.Globalization;
using WB4Office2007Library;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Word=Microsoft.Office.Interop.Word;
using WBOffice4;

namespace TestProjectOffice2007
{
    
    
    /// <summary>
    ///This is a test class for WordOfficeApplicationTest and is intended
    ///to contain all WordOfficeApplicationTest Unit Tests
    ///</summary>
    [TestClass()]
    public class WordOfficeApplicationTest
    {

        private Word.Application application;
        private FileInfo demoDoc= new FileInfo(@"c:\temp\demo.doc");
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
        }
        //
        //Use TestCleanup to run code after each test has run
        [TestCleanup()]
        public void MyTestCleanup()
        {
            object missing = System.Type.Missing;
            object objfalse = false;
            foreach (Word.Document document in application.Documents)
            {
                ((Word.DocumentClass)document).Close(ref objfalse, ref missing, ref missing);
            }
            ((Word.ApplicationClass)application).Quit(ref objfalse, ref missing, ref missing);
        }
        //
        #endregion


        /// <summary>
        ///A test for Open
        ///</summary>
        [TestMethod()]
        public void OpenTest()
        {

            WordOfficeApplication_Accessor target = new WordOfficeApplication_Accessor(this.application);                        
            OfficeDocument actual;
            actual = target.Open(demoDoc);            
            Assert.AreEqual(demoDoc.FullName.ToUpperInvariant(), actual.FilePath.FullName.ToUpperInvariant());            
        }

        /// <summary>
        ///A test for application_DocumentBeforeClose
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void ApplicationDocumentBeforeCloseTest()
        {            
            WordOfficeApplication_Accessor target = new WordOfficeApplication_Accessor(this.application);
            target.Open(demoDoc);
            Word.Document document = this.application.ActiveDocument;
            bool cancel = false;             
            target.ApplicationDocumentBeforeClose(document, ref cancel);            
        }

        /// <summary>
        ///A test for app_DocumentOpen
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void ApplicationDocumentOpenTest()
        {            
            WordOfficeApplication_Accessor target = new WordOfficeApplication_Accessor(this.application);            
            target.Open(demoDoc);
            Word.Document document = this.application.ActiveDocument;
            target.ApplicationDocumentOpen(document);            
        }

        /// <summary>
        ///A test for app_DocumentChange
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void ApplicationDocumentChangeTest()
        {            
            WordOfficeApplication_Accessor target = new WordOfficeApplication_Accessor(this.application);
            target.Open(demoDoc);
            target.ApplicationDocumentChange();            
        }

        /// <summary>
        ///A test for ActivateDocument
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void ActivateDocumentTest()
        {            
            WordOfficeApplication_Accessor target = new WordOfficeApplication_Accessor(this.application);
            target.Open(demoDoc);
            Word.Document document = this.application.ActiveDocument;
            target.ActivateDocument(document);            
        }

        /// <summary>
        ///A test for WordOfficeApplication Constructor
        ///</summary>
        [TestMethod()]
        public void WordOfficeApplicationConstructorTest()
        {            
            WordOfficeApplication_Accessor target = new WordOfficeApplication_Accessor(this.application);
            OfficeDocument document = target.Open(this.demoDoc);
            Assert.AreEqual(document.FilePath.FullName.ToUpperInvariant(), demoDoc.FullName.ToUpperInvariant());
        }
    }
}
