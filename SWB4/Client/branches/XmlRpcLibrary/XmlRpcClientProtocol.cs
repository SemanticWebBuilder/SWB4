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
using System.Net;
using System.ComponentModel;
using System.Reflection;
using System.Diagnostics;
using System.Windows.Forms;
using System.Net.Sockets;
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
                XmlRpcTraceEventLogListener.WriteError(ex);
                Exception e=ex.GetBaseException();
                if(e is SocketException)
                {
                    MessageBox.Show("Existe un error de comunicación con el publicador.\r\nPuede que el sistema este inestable debido a esta situación.\r\nCierre la aplicación y vuelva a intentar la operación.\r\nDetalle: " + e.Message, "Error de comunicación", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    XmlRpcTraceEventLogListener.WriteError(e);
                    throw e;
                }
                else
                {
                    throw e;
                }

            }            
            catch (XmlRpcException webex)
            {
                XmlRpcTraceEventLogListener.WriteError(webex);
                throw webex;
            }
            catch (WebException webex)
            {
                XmlRpcTraceEventLogListener.WriteError(webex);
                throw webex;
            }
            
            catch (NullReferenceException webex)
            {
                XmlRpcTraceEventLogListener.WriteError(webex);
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
