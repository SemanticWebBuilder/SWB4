﻿using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using WBOffice4.Forms;
using WBOffice4;
namespace WB4OfficeTest
{
    /// <summary>
    /// Descripción resumida de FormDialogTest
    /// </summary>
    [TestClass]
    public class FormDialogTest
    {
        public FormDialogTest()
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
        public void FormLoginTest()
        {
            FormLogin login = new FormLogin();
            login.ShowDialog();
        }

        [TestMethod]
        public void FormLoginTestWidthUri()
        {
            URIConfigurationList target = new URIConfigurationList();
            string login = "demo";
            Uri address = new Uri("http://www.infotec.com.mx");
            target.Add(login, address);
            FormLogin frmLogin = new FormLogin();
            frmLogin.ShowDialog();
        }
    }
}
