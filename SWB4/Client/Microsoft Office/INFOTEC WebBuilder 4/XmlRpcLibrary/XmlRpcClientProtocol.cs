using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.ComponentModel;
using System.Reflection;
using System.Diagnostics;
namespace XmlRpcLibrary
{
    public class XmlRpcClientProtocol : Component,  IXmlRpcProxy
    {
        public Uri WebAddress { get; set; }
        public Uri ProxyServer { get; set; }
        public int ProxyPort { get; set; }
        public HashSet<Part> ResponseParts { get; set; }
        public NetworkCredential Credentials { get; set; }
        ICollection<Attachment> _attachments = new List<Attachment>();
        public XmlRpcClientProtocol()
        {

        }
        public ICollection<Attachment> Attachments 
        {
            get
            {
                return _attachments;
            }            
        }        
        public object Invoke(MethodInfo mi,params object[] parameters)
        {
            try
            {
                XmlRpcClientConfig config = new XmlRpcClientConfig(this.WebAddress);
                config.ProxyServer = this.ProxyServer;
                config.ProxyPort = this.ProxyPort;
                if (this.Credentials != null)
                {
                    config.Password = this.Credentials.Password;
                    config.UserName = this.Credentials.UserName;
                }
                string rpcMethodName = GetRpcMethodName(mi);
                XmlRpcClient client = new XmlRpcClient(config);
                Type type = client.GetType();
                object[] args = new object[4];
                args[0] = rpcMethodName;
                args[1] = parameters;
                args[2] = Attachments;
                this.ResponseParts = new HashSet<Part>();                
                args[3] = this.ResponseParts;
                System.Reflection.MethodInfo executeMethod = type.GetMethod("Execute");
                if (mi.ReturnType.Name != "Void")
                {
                    executeMethod = executeMethod.MakeGenericMethod(mi.ReturnType);
                }
                else
                {
                    
                    Type[] types={typeof(string)};
                    executeMethod = executeMethod.MakeGenericMethod(types);
                }
                object ObjToReturn = executeMethod.Invoke(client, args);
                this.ResponseParts = (HashSet<Part>)args[3];                
                return ObjToReturn;
            }
            catch (TargetInvocationException ex)
            {
                Debug.WriteLine(ex.StackTrace);
                throw ex.GetBaseException();
            }
            catch (XmlRpcException webex)
            {
                Debug.WriteLine(webex.StackTrace);
                throw webex;
            }
            catch (WebException webex)
            {
                Debug.WriteLine(webex.StackTrace);
                throw webex;
            }
            catch (NullReferenceException webex)
            {
                Debug.WriteLine(webex.StackTrace);
                throw webex;
            }   
            finally
            {
                this.Attachments.Clear();
            }
        }  
        private static string GetRpcMethodName(MethodInfo mi)
        {
            string rpcMethod;
            Attribute attr = Attribute.GetCustomAttribute(mi, typeof(XmlRpcMethodAttribute));
            if (attr == null)
            {
                throw new XmlRpcException("missing method attribute");
            }
            XmlRpcMethodAttribute xrmAttr = attr as XmlRpcMethodAttribute;
            rpcMethod = xrmAttr.Method;
            if (String.IsNullOrEmpty(rpcMethod))
            {
                rpcMethod = mi.Name;
            }
            return rpcMethod;
        }
    }
}
