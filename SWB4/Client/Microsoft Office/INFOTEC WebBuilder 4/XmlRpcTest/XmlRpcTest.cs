using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using XmlRpcLibrary;
namespace XmlRpcTest
{
    /// <summary>
    /// Descripción resumida de XmlRpcTest
    /// </summary>
    [TestClass]
    public class XmlRpcTest
    {
        public XmlRpcTest()
        {
            //
            // TODO: Agregar aquí la lógica del constructor
            //
        }

        private TestContext testContextInstance;

        /// <summary>
        ///Obtiene o establece el contexto de las pruebas que proporciona
        ///información y funcionalidad para la ejecución de pruebas actual.
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
        // Puede usar los siguientes atributos adicionales conforme escribe las pruebas:
        //
        // Use ClassInitialize para ejecutar el código antes de ejecutar la primera prueba en la clase
        // [ClassInitialize()]
        // public static void MyClassInitialize(TestContext testContext) { }
        //
        // Use ClassCleanup para ejecutar el código una vez ejecutadas todas las pruebas en una clase
        // [ClassCleanup()]
        // public static void MyClassCleanup() { }
        //
        // Usar TestInitialize para ejecutar el código antes de ejecutar cada prueba 
        // [TestInitialize()]
        // public void MyTestInitialize() { }
        //
        // Use TestCleanup para ejecutar el código una vez ejecutadas todas las pruebas
        // [TestCleanup()]
        // public void MyTestCleanup() { }
        //
        #endregion

        [TestMethod]
        public void TestExecute()
        {
            IDemo demo=XmlRpcProxyFactory.Create<IDemo>();
            demo.WebAddress = new Uri("http://localhost:8084/TestRPC/GatewayOffice");
            demo.Credentials = new System.Net.NetworkCredential("victor", "Lorenzana");
            String res=demo.Add(4, 3.2, "", DateTime.Now, true);
            Assert.IsFalse(String.IsNullOrEmpty(res));
        }

        [TestMethod]
        public void TestExecuteWithProxy()
        {
            IDemo demo = XmlRpcProxyFactory.Create<IDemo>();
            demo.WebAddress = new Uri("http://localhost:8084/TestRPC/GatewayOffice");
            demo.Credentials = new System.Net.NetworkCredential("victor", "Lorenzana");
            demo.ProxyPort = 8080;
            demo.ProxyServer = new Uri("http://localhost:8084/TestRPC/GatewayOffice");
            String res = demo.Add(4, 3.2, "", DateTime.Now, true);
            Assert.IsFalse(String.IsNullOrEmpty(res));            
        }
        

        [TestMethod]
        [ExpectedException(typeof(HttpException))]
        public void TestExecuteWithOutPassword()
        {
            IDemo demo = XmlRpcProxyFactory.Create<IDemo>();
            demo.WebAddress = new Uri("http://localhost:8084/TestRPC/GatewayOffice");
            String res = demo.Add(4, 3.2, "", DateTime.Now, true);
            Assert.IsNotNull(res);            
        }

        [TestMethod]
        [ExpectedException(typeof(XmlRpcException))]
        public void TestExecuteWithInvalidMethod()
        {

            IDemo demo = XmlRpcProxyFactory.Create<IDemo>();
            demo.WebAddress = new Uri("http://localhost:8084/TestRPC/GatewayOffice");
            demo.Credentials = new System.Net.NetworkCredential("victor", "Lorenzana");
            String res = demo.Add(4, 3.2, "", DateTime.Now, 1);
            Assert.IsNotNull(res);            

        }

        [TestMethod]
        [ExpectedException(typeof(HttpException))]
        public void TestExecuteWithBadPath()
        {
            try
            {
                IDemo demo = XmlRpcProxyFactory.Create<IDemo>();
                
                demo.WebAddress = new Uri("http://localhost:8084/TestRPC/GatewayOffice2");
                demo.Credentials = new System.Net.NetworkCredential("victor", "Lorenzana");
                String res = demo.Add(4, 3.2, "", DateTime.Now, true);
                Assert.IsNotNull(res);            
            }
            catch (UriFormatException e)
            {
                Assert.Fail(e.Message);
            }
        }
    }
}
