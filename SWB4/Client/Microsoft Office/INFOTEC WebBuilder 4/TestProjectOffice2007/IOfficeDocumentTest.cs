using WBOffice4.Interfaces;
using Microsoft.VisualStudio.TestTools.UnitTesting;
namespace TestProjectOffice2007
{
    
    
    /// <summary>
    ///Se trata de una clase de prueba para IOfficeDocumentTest y se pretende que
    ///contenga todas las pruebas unitarias IOfficeDocumentTest.
    ///</summary>
    [TestClass()]
    public class IOfficeDocumentTest
    {


        private TestContext testContextInstance;

        /// <summary>
        ///Obtiene o establece el contexto de la prueba que proporciona
        ///la información y funcionalidad para la ejecución de pruebas actual.
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

        #region Atributos de prueba adicionales
        // 
        //Puede utilizar los siguientes atributos adicionales mientras escribe sus pruebas:
        //
        //Use ClassInitialize para ejecutar código antes de ejecutar la primera prueba en la clase 
        //[ClassInitialize()]
        //public static void MyClassInitialize(TestContext testContext)
        //{
        //}
        //
        //Use ClassCleanup para ejecutar código después de haber ejecutado todas las pruebas en una clase
        //[ClassCleanup()]
        //public static void MyClassCleanup()
        //{
        //}
        //
        //Use TestInitialize para ejecutar código antes de ejecutar cada prueba
        //[TestInitialize()]
        //public void MyTestInitialize()
        //{
        //}
        //
        //Use TestCleanup para ejecutar código después de que se hayan ejecutado todas las pruebas
        //[TestCleanup()]
        //public void MyTestCleanup()
        //{
        //}
        //
        #endregion


        /// <summary>
        ///Una prueba de UpdateContent
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void UpdateContentTest()
        {
            IOfficeDocument target = CreateIOfficeDocument(); // TODO: Inicializar en un valor adecuado
            string contentId = null; // TODO: Inicializar en un valor adecuado
            string repositoryId = null; // TODO: Inicializar en un valor adecuado
            string expected = null; // TODO: Inicializar en un valor adecuado
            string actual;
            actual = target.updateContent(repositoryId,contentId,"demo.doc");
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("Compruebe la exactitud de este método de prueba.");
        }

        /// <summary>
        ///Una prueba de SetTitle
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void SetTitleTest()
        {
            IOfficeDocument target = CreateIOfficeDocument(); // TODO: Inicializar en un valor adecuado
            string contentId = null; // TODO: Inicializar en un valor adecuado
            string repositoryName = null; // TODO: Inicializar en un valor adecuado
            string title = string.Empty; // TODO: Inicializar en un valor adecuado
            target.setTitle(repositoryName,contentId, title);
            Assert.Inconclusive("Un método que no devuelve ningún valor no se puede comprobar.");
        }

                /// <summary>
        ///Una prueba de SetDescription
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void SetDescriptionTest()
        {
            IOfficeDocument target = CreateIOfficeDocument(); // TODO: Inicializar en un valor adecuado
            string contentId = null; // TODO: Inicializar en un valor adecuado
            string repositoryName = null;
            string description = string.Empty; // TODO: Inicializar en un valor adecuado
            target.setDescription(repositoryName,contentId, description);
            Assert.Inconclusive("Un método que no devuelve ningún valor no se puede comprobar.");
        }

        

        /// <summary>
        ///Una prueba de Publish
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void PublishTest()
        {
            IOfficeDocument target = CreateIOfficeDocument(); // TODO: Inicializar en un valor adecuado
            string title = string.Empty; // TODO: Inicializar en un valor adecuado
            string repositoryName = string.Empty; // TODO: Inicializar en un valor adecuado
            string description = string.Empty; // TODO: Inicializar en un valor adecuado
            string categoryID = string.Empty; // TODO: Inicializar en un valor adecuado
            string type = string.Empty; // TODO: Inicializar en un valor adecuado
            string expected = string.Empty; // TODO: Inicializar en un valor adecuado
            string actual;
            actual = target.save(title, description, repositoryName,categoryID, type,"cm:Content","demo.doc",null,null);
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("Compruebe la exactitud de este método de prueba.");
        }

        

        

        /// <summary>
        ///Una prueba de Exists
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void ExistsTest()
        {
            IOfficeDocument target = CreateIOfficeDocument(); // TODO: Inicializar en un valor adecuado
            string contentId = string.Empty; // TODO: Inicializar en un valor adecuado
            string repositoryName = string.Empty; // TODO: Inicializar en un valor adecuado
            bool expected = false; // TODO: Inicializar en un valor adecuado
            bool actual;
            actual = target.exists(repositoryName,contentId);
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("Compruebe la exactitud de este método de prueba.");
        }

        internal virtual IOfficeDocument CreateIOfficeDocument()
        {
            // TODO: Crear instancia de una clase concreta apropiada.
            IOfficeDocument target = null;
            return target;
        }

        /// <summary>
        ///Una prueba de Delete
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void DeleteTest()
        {
            IOfficeDocument target = CreateIOfficeDocument(); // TODO: Inicializar en un valor adecuado
            string contentId = string.Empty; // TODO: Inicializar en un valor adecuado
            string repositoryName = string.Empty; // TODO: Inicializar en un valor adecuado
            target.delete(repositoryName,contentId);
            Assert.Inconclusive("Un método que no devuelve ningún valor no se puede comprobar.");
        }
    }
}
