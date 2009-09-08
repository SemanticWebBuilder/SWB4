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
using System.IO;
using System.Xml;
using System.Text;
using System.Globalization;

namespace WBOffice4
{
    internal sealed class URIConfigurationList
    {
        private Stack<SWBURIConfiguration> configurations = new Stack<SWBURIConfiguration>(20);
        private FileInfo config;
        public URIConfigurationList()
        {
            String path = System.Environment.GetFolderPath(Environment.SpecialFolder.Personal);
            config = new FileInfo(path + @"\listuri.xml");
            Load();            
        }
        public FileInfo Path
        {
            get
            {
                return config;
            }
        }
        public void remove(Uri uri)
        {
            List<SWBURIConfiguration> values=new List<SWBURIConfiguration>(configurations.ToArray<SWBURIConfiguration>());
            foreach (SWBURIConfiguration conf in values)
            {
                if (conf.Uri.Equals(uri))
                {
                    values.Remove(conf);
                    break;
                }
            }
            configurations = new Stack<SWBURIConfiguration>(20);
            foreach (SWBURIConfiguration conf in values)
            {

                configurations.Push(conf);
            }
            this.Save();
            
        }
        public Uri[] Addresses
        {
            get
            {
                IOrderedEnumerable<SWBURIConfiguration> configurationsOrdened=this.configurations.OrderBy(p => p.LastUsed);
                List<Uri> lista = new List<Uri>();
                foreach (SWBURIConfiguration configuration in configurationsOrdened)
                {
                    lista.Add(configuration.Uri);
                }
                return lista.ToArray<Uri>();                
            }
        }
        
        public string GetLogin(Uri uri)
        {
            try
            {
                Dictionary<Uri, SWBURIConfiguration> dictionary = this.configurations.ToDictionary(p=>p.Uri);
                if (dictionary[uri] != null)
                {
                    return dictionary[uri].Login;
                }
                return null;
            }
            catch (KeyNotFoundException knfe)
            {
                OfficeApplication.WriteError(knfe);
                return "";
            }

        }
        public void Add(String login, Uri address)
        {
            SWBURIConfiguration sWBURIConfiguration = new SWBURIConfiguration(login, address);
            if (this.configurations.Contains(sWBURIConfiguration))
            {
                Dictionary<Uri, SWBURIConfiguration> dictionary = this.configurations.ToDictionary(p => p.Uri);
                if (dictionary[address] != null)
                {
                    dictionary[address].LastUsed = DateTime.Now;
                }
            }
            else
            {
                configurations.Push(sWBURIConfiguration);
            }
            if (configurations.Count > 20)
            {
                configurations.Pop();
            }
            this.Save();
        }
        private void Load()
        {
            if (config.Exists)
            {                
                XmlDocument document = new XmlDocument();
                document.Load(config.FullName);
                foreach (XmlElement entry in document.GetElementsByTagName("entry"))
                {
                    String uri = entry.Attributes["uri"].Value;
                    string login = entry.InnerText;
                    DateTime dateTime = DateTime.Parse(entry.Attributes["dateTime"].Value,CultureInfo.InvariantCulture);
                    SWBURIConfiguration sWBURIConfiguration = new SWBURIConfiguration(login, new Uri(uri), dateTime);
                    this.configurations.Push(sWBURIConfiguration);
                }                                
            }
        }
        private void Save()
        {           
            XmlDocument document = new XmlDocument();
            XmlElement SWBURIConfigurationElement=document.CreateElement("SWBURIConfiguration");
            document.AppendChild(SWBURIConfigurationElement);
            SWBURIConfiguration[] configurationsOrdened = this.configurations.OrderBy(p => p.LastUsed).ToArray<SWBURIConfiguration>();            
            foreach (SWBURIConfiguration configuration in configurationsOrdened)
            {   
                XmlElement entry = document.CreateElement("entry");
                SWBURIConfigurationElement.AppendChild(entry);

                XmlAttribute uriAttribute=document.CreateAttribute("uri");
                uriAttribute.Value = configuration.Uri.ToString();
                entry.Attributes.Append(uriAttribute);


                XmlAttribute dateTimeAttribute = document.CreateAttribute("dateTime");
                dateTimeAttribute.Value = configuration.LastUsed.ToString(CultureInfo.InvariantCulture);
                entry.Attributes.Append(dateTimeAttribute);


                entry.AppendChild(document.CreateTextNode(configuration.Login));                
                
            }
            document.Save(config.FullName);
            
        }
    }
}
