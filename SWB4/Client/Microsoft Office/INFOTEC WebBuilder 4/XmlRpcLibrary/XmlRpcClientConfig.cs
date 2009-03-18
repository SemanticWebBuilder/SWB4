using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace XmlRpcLibrary
{
    internal sealed class XmlRpcClientConfig
    {        
        public Uri ServerUri { get; set; }
        public String UserName { get; set; }
        public String Password { get; set; }
        public Uri ProxyServer { get; set; }
        public int ProxyPort { get; set; }
        public XmlRpcClientConfig(Uri serverUri)
        {
            this.ServerUri = serverUri;
        }
        /*public XmlRpcClientConfig(Uri serverUri, Uri proxyServer,int proxyPort) : this(serverUri)
        {
            this.ProxyServer = proxyServer;
            this.ProxyPort = proxyPort;
        }*/
        /*public XmlRpcClientConfig(Uri serverUri, Uri proxyServer, int proxyPort, String userName,String password) : this(serverUri,proxyServer,proxyPort)            
        {
            this.UserName = userName;
            this.Password = password;
        }*/
        /*public XmlRpcClientConfig(Uri serverUri, String userName, String password)
            : this(serverUri)
        {
            this.UserName = userName;
            this.Password = password;
        }*/
        public bool HasUserName
        {
            get
            {
                return this.UserName!=null && this.Password!=null;
            }
        }
        public bool UsesProxy
        {
            get
            {
                return this.ProxyServer != null && this.ProxyPort != 0;
            }
        }
    }
}
