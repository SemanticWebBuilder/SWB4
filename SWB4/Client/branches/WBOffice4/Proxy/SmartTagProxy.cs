using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WBOffice4.Interfaces;
using XmlRpcLibrary;
using System.Globalization;
namespace WBOffice4.Proxy
{
    public class SmartTagProxy
    {
        private static ISmartTag smarttag;
        public static ISmartTag SmartTag
        {
            get
            {
                if (smarttag == null)
                {
                    smarttag=XmlRpcProxyFactory.Create<ISmartTag>();
                    SWBConfiguration configuration = new SWBConfiguration();
                    smarttag.WebAddress = new Uri("http://192.168.5.102:8080/swb/tags");
                    if (configuration.UsesProxy)
                    {
                        smarttag.ProxyPort = int.Parse(configuration.ProxyPort, CultureInfo.InvariantCulture);
                        smarttag.ProxyServer = new Uri(configuration.ProxyServer);
                    }                    
                }
                return smarttag;
            }
        }
    }
}
