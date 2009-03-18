using WB4Office2007Library;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Excel=Microsoft.Office.Interop.Excel;
using System.IO;
using WBOffice4;

namespace TestProjectOffice2007
{
    
    
    /// <summary>
    ///This is a test class for ExcelApplicationTest and is intended
    ///to contain all ExcelApplicationTest Unit Tests
    ///</summary>
    [TestClass()]
    public class ExcelApplicationTest
    {
        private static object missing = System.Type.Missing;
        private static object objtrue = true;

        Excel.Application application;
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
        }
        //
        //Use TestCleanup to run code after each test has run
        [TestCleanup()]
        public void MyTestCleanup()
        {
            foreach (Excel.Workbook workbook in application.Workbooks)
            {
                workbook.Close(objtrue, missing, missing);
            }
            application.Quit();
        }
        //
        #endregion


        /// <summary>
        ///A test for Open
        ///</summary>
        [TestMethod()]
        public void OpenTest()
        {            
            ExcelApplication_Accessor target = new ExcelApplication_Accessor(this.application); 
            FileInfo file = this.demoDoc; 
            OfficeDocument actual;
            actual = target.Open(file);
            Assert.AreEqual(actual.FilePath.FullName.ToUpperInvariant(), file.FullName.ToUpperInvariant());            
        }

        /// <summary>
        ///A test for application_WorkbookOpen
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void ApplicationWorkbookOpenTest()
        {
            ExcelApplication_Accessor target = new ExcelApplication_Accessor(this.application);
            Excel.Workbook workbook = this.application.Workbooks.Add(System.Type.Missing);
            target.ApplicationWorkbookOpen(workbook);            
        }

        /// <summary>
        ///A test for application_WorkbookBeforeClose
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void ApplicationWorkbookBeforeCloseTest()
        {            
            ExcelApplication_Accessor target = new ExcelApplication_Accessor(this.application);
            Excel.Workbook workbook = this.application.Workbooks.Add(System.Type.Missing);
            bool cancel = false; // TODO: Initialize to an appropriate value            
            target.ApplicationWorkbookBeforeClose(workbook, ref cancel);
            
        }

        /// <summary>
        ///A test for ActivateDocument
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WB4Office2007Library.dll")]
        public void ActivateDocumentTest()
        {            
            ExcelApplication_Accessor target = new ExcelApplication_Accessor(this.application);
            Excel.Workbook workbook = this.application.Workbooks.Add(System.Type.Missing);
            target.ActivateDocument(workbook);            
        }

        /// <summary>
        ///A test for ExcelApplication Constructor
        ///</summary>
        [TestMethod()]
        public void ExcelApplicationConstructorTest()
        {            
            ExcelApplication_Accessor target = new ExcelApplication_Accessor(application);
            OfficeDocument document = target.Open(this.demoDoc);
            Assert.AreEqual(document.FilePath.FullName.ToUpperInvariant(), demoDoc.FullName.ToUpperInvariant());
        }
    }
}
