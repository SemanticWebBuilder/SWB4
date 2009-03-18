using System;
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
