using System;
using System.Net;
using System.Diagnostics;
using WBOffice4.Interfaces;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using XmlRpcLibrary;
namespace TestProjectOffice2007
{
    
    
    /// <summary>
    ///Se trata de una clase de prueba para IOfficeApplicationTest y se pretende que
    ///contenga todas las pruebas unitarias IOfficeApplicationTest.
    ///</summary>
    [TestClass()]
    public class IOfficeApplicationTest
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
        ///Una prueba de IsValidVersion
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void IsValidVersionTest()
        {
            IOfficeApplication target = CreateIOfficeApplication(); // TODO: Inicializar en un valor adecuado
            double version = 0F; // TODO: Inicializar en un valor adecuado
            bool expected = false; // TODO: Inicializar en un valor adecuado
            bool actual;
            actual = target.IsValidVersion(version);
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("Compruebe la exactitud de este método de prueba.");
        }

        /// <summary>
        ///Una prueba de ExistPage
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void ExistPageTest()
        {
            IOfficeApplication target = CreateIOfficeApplication(); // TODO: Inicializar en un valor adecuado
            string id = string.Empty; // TODO: Inicializar en un valor adecuado
            bool expected = false; // TODO: Inicializar en un valor adecuado
            bool actual;
            WebSiteInfo site = new WebSiteInfo();
            WebPageInfo page = new WebPageInfo();
            actual = target.existsPage(site,page,id);
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("Compruebe la exactitud de este método de prueba.");
        }

        /// <summary>
        ///Una prueba de CreatePage
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void CreatePageTest()
        {
            IOfficeApplication target = CreateIOfficeApplication(); // TODO: Inicializar en un valor adecuado
            string title = string.Empty; // TODO: Inicializar en un valor adecuado
            string id = string.Empty; // TODO: Inicializar en un valor adecuado
            string description = string.Empty; // TODO: Inicializar en un valor adecuado
            //target.CreatePage(title, id, description);
            Assert.Inconclusive("Un método que no devuelve ningún valor no se puede comprobar.");
        }

        internal virtual IOfficeApplication CreateIOfficeApplication()
        {
            // TODO: Crear instancia de una clase concreta apropiada.
            IOfficeApplication target = XmlRpcProxyFactory.Create<IOfficeApplication>();
            target.WebAddress = new Uri("http://localhost:8084/TestRPC/GatewayOffice");
            target.Credentials = new NetworkCredential("v", "v");
            return target;
        }

        /// <summary>
        ///Una prueba de ChangePassword
        ///</summary>
        [TestMethod()]
        [Ignore()]
        public void ChangePasswordTest()
        {
            IOfficeApplication target = CreateIOfficeApplication(); // TODO: Inicializar en un valor adecuado
            string newPassword = string.Empty; // TODO: Inicializar en un valor adecuado
            target.ChangePassword(newPassword);
            Assert.Inconclusive("Un método que no devuelve ningún valor no se puede comprobar.");
        }

        /// <summary>
        ///Una prueba de GetRepositories
        ///</summary>
        [TestMethod()]
        public void GetRepositoriesTest()
        {
            IOfficeApplication target = CreateIOfficeApplication();
            RepositoryInfo[] repositories = target.getRepositories();
            foreach (RepositoryInfo repName in repositories)
            {
                Debug.WriteLine(repName);
            }
            Assert.AreEqual(repositories.Length, 1);            
        }

        /// <summary>
        ///Una prueba de CreateCategory
        ///</summary>
        [TestMethod()]
        public void CreateCategoryTest()
        {
            IOfficeApplication target = CreateIOfficeApplication();
            RepositoryInfo[] repositories = target.getRepositories();
            String UUDI=target.createCategory(repositories[0].name, "Deportes", "Description");                                   
            Assert.IsFalse(string.IsNullOrEmpty(UUDI));
        }

        /// <summary>
        ///Una prueba de GetCategories
        ///</summary>
        [TestMethod()]
        public void GetCategoriesTest()
        {
            IOfficeApplication target = CreateIOfficeApplication();
            RepositoryInfo[] repositories = target.getRepositories();
            CategoryInfo[] categories= target.getCategories(repositories[0].name);
            foreach (CategoryInfo category in categories)
            {
                Debug.WriteLine(category.title);
            }
            Assert.IsTrue(categories.Length > 0);
        }

    }
}
