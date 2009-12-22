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
 
﻿using WB4Office2007Library;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using PowerPoint=Microsoft.Office.Interop.PowerPoint;
using Office = Microsoft.Office.Core;
using System.IO;
using WBOffice4;
namespace TestProjectOffice2007
{
    
    
    /// <summary>
    ///This is a test class for PPTApplicationTest and is intended
    ///to contain all PPTApplicationTest Unit Tests
    ///</summary>
    [TestClass()]
    public class PowerPointApplicationTest
    {
        PowerPoint.Application application;
        private FileInfo demoDoc = new FileInfo(@"c:\temp\demo.ppt");

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
            application = new PowerPoint.ApplicationClass();
            application.Visible = Office.MsoTriState.msoTrue;
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


        /// <summary>
        ///A test for presentationOpen
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void PresentationOpenTest()
        {

            PptApplication_Accessor target = new PptApplication_Accessor(this.application);
            PowerPoint.Presentation presentation = this.application.Presentations.Open(this.demoDoc.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
            target.PresentationOpen(presentation);
            presentation.Close();
        }

        /// <summary>
        ///A test for presentationNew
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void PresentationNewTest()
        {
            PptApplication_Accessor target = new PptApplication_Accessor(this.application);
            PowerPoint.Presentation presentation = this.application.Presentations.Open(this.demoDoc.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
            PowerPoint.DocumentWindow window = this.application.ActiveWindow; 
            target.PresentationNew(presentation, window);            
            presentation.Close();
        }

        /// <summary>
        ///A test for presentationClose
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void PresentationCloseTest()
        {
            PptApplication_Accessor target = new PptApplication_Accessor(this.application);
            PowerPoint.Presentation presentation = this.application.Presentations.Open(this.demoDoc.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
            target.PresentationClose(presentation);
            presentation.Close();
        }

        /// <summary>
        ///A test for Open
        ///</summary>
        [TestMethod()]
        public void OpenTest()
        {            
            PptApplication_Accessor target = new PptApplication_Accessor(this.application); 
            FileInfo file = this.demoDoc;             
            OfficeDocument actual;
            actual = target.Open(file);
            Assert.AreEqual(actual.FilePath.FullName.ToUpperInvariant(), file.FullName.ToUpperInvariant());
        }

        /// <summary>
        ///A test for ActivateDocument
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void ActivateDocumentTest()
        {
            PptApplication_Accessor target = new PptApplication_Accessor(this.application);
            PowerPoint.Presentation presentation = this.application.Presentations.Open(this.demoDoc.FullName, Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse, Office.MsoTriState.msoTrue);
            target.ActivateDocument(presentation);
            presentation.Close();
        }

        /// <summary>
        ///A test for PPTApplication Constructor
        ///</summary>
        [TestMethod()]
        public void PowerPointApplicationConstructorTest()
        {            
            PptApplication_Accessor target = new PptApplication_Accessor(this.application);
            OfficeDocument document=target.Open(this.demoDoc);
            Assert.AreEqual(document.FilePath.FullName.ToUpperInvariant(), demoDoc.FullName.ToUpperInvariant());
        }
    }
}
