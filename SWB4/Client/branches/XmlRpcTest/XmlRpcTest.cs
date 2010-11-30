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
