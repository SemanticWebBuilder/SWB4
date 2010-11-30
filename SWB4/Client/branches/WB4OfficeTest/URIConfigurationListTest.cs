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
 
﻿using WBOffice4;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;

namespace WB4OfficeTest
{
    
    
    /// <summary>
    ///Se trata de una clase de prueba para URIConfigurationListTest y se pretende que
    ///contenga todas las pruebas unitarias URIConfigurationListTest.
    ///</summary>
    [TestClass()]
    public class URIConfigurationListTest
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
        ///Una prueba de Addresses
        ///</summary>
        [TestMethod()]
        [Ignore]
        public void AddressesTest()
        {
            URIConfigurationList target = new URIConfigurationList(); // TODO: Inicializar en un valor adecuado
            Uri[] actual;
            actual = target.Addresses;
            Assert.Inconclusive("Compruebe la exactitud de este método de prueba.");
        }

        /// <summary>
        ///Una prueba de Save
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WBOffice4.dll")]        
        public void SaveTest()
        {
            URIConfigurationList_Accessor target = new URIConfigurationList_Accessor(); 
            target.Save();
            Assert.AreEqual(target.Addresses.Length, 0);
        }

        /// <summary>
        ///Una prueba de Load
        ///</summary>
        [TestMethod()]
        [DeploymentItem("WBOffice4.dll")]
        public void LoadTest()
        {
            URIConfigurationList_Accessor target = new URIConfigurationList_Accessor(); // TODO: Inicializar en un valor adecuado
            target.Load();
            Assert.AreEqual(target.Addresses.Length, 0);
        }

        /// <summary>
        ///Una prueba de GetLogin
        ///</summary>
        [TestMethod()]        
        public void GetLoginTest()
        {
            URIConfigurationList target = new URIConfigurationList(); 
            Uri uri = new Uri("http://www.infotec.com.mx"); ; 
            string expected = "demo"; 
            string actual;
            actual = target.GetLogin(uri);
            Assert.AreEqual(expected, actual);            
        }

        /// <summary>
        ///Una prueba de Add
        ///</summary>
        [TestMethod()]        
        public void AddTest()
        {
            URIConfigurationList target = new URIConfigurationList();
            for (int i = 0; i < 50; i++)
            {
                string login = "demo"+i;
                Uri address = new Uri("http://www.infotec.com.mx/"+i);
                target.Add(login, address);                
            }
            Assert.AreEqual(target.Addresses.Length, 20);
        }

        /// <summary>
        ///Una prueba de Constructor URIConfigurationList
        ///</summary>
        [TestMethod()]
        [Ignore]
        public void URIConfigurationListConstructorTest()
        {
            URIConfigurationList target = new URIConfigurationList();            
        }
    }
}
