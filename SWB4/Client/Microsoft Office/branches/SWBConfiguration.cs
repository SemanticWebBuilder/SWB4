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
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.IO;
namespace WBOffice4
{
    internal sealed class SWBConfiguration
    {
        private bool _UsesProxy;
        private string _ProxyServer;
        private string _ProxyPort;
        private string _Language;
        public bool UsesProxy
        {
            get
            {
                return _UsesProxy;
            }
            set
            {
                _UsesProxy = value;
                Save();
            }
        }
        public string ProxyServer
        {
            get
            {
                return _ProxyServer;
            }
            set
            {
                _ProxyServer = value;
                Save();
            }
        }
        public string ProxyPort 
        { 
            get
            {
                return _ProxyPort;
            }
            set
            { 
                _ProxyPort=value;
                Save();
            }
        }
        public string Language 
        { 
            get
            {
                return _Language;
            }
            set
            {
                _Language = value;
                Save();
            }
        }
        private FileInfo config;
        public FileInfo Path
        {
            get
            {
                return config;
            }
        }
        public SWBConfiguration()
        {            
            FileInfo assemblyLocation = new FileInfo(this.GetType().Assembly.Location);
            config = new FileInfo(assemblyLocation.DirectoryName + @"\configwb.xml");
            Load();
        }
        private void Load()
        {
            if (config.Exists)
            {
                XmlDocument document = new XmlDocument();
                document.Load(config.FullName);
                foreach (XmlElement proxyServer in document.GetElementsByTagName("ProxyServer"))
                {
                    ProxyServer = proxyServer.InnerText;
                }
                foreach (XmlElement proxyPort in document.GetElementsByTagName("ProxyPort"))
                {
                    ProxyPort = proxyPort.InnerText;
                }
                foreach (XmlElement useProxy in document.GetElementsByTagName("UsesProxy"))
                {
                    UsesProxy = bool.Parse(useProxy.InnerText);
                }
                foreach (XmlElement language in document.GetElementsByTagName("Language"))
                {
                    Language = language.InnerText;
                }
            }
            else
            {
                UsesProxy = false;
                ProxyServer = "";
                ProxyPort = "";
                Language = "";
            }
        }
        private void Save()
        {            
            XmlDocument document = new XmlDocument();
            XmlElement configuration=document.CreateElement("Configuration");
            document.AppendChild(configuration);

            XmlElement proxyServer = document.CreateElement("ProxyServer");
            proxyServer.AppendChild(document.CreateTextNode(ProxyServer));
            configuration.AppendChild(proxyServer);
            XmlElement proxyPort = document.CreateElement("ProxyPort");
            proxyPort.AppendChild(document.CreateTextNode(ProxyPort));
            configuration.AppendChild(proxyPort);
            XmlElement usesProxy = document.CreateElement("UsesProxy");
            usesProxy.AppendChild(document.CreateTextNode(UsesProxy.ToString()));
            configuration.AppendChild(usesProxy);
            XmlElement language = document.CreateElement("Language");
            language.AppendChild(document.CreateTextNode(Language));
            configuration.AppendChild(language);
            document.Save(config.FullName);            
        }
    }
}
