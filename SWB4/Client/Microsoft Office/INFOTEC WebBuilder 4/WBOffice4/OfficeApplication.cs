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
using System.IO;
using WBOffice4.Steps;
using WBOffice4.Forms;
using System.Windows.Forms;
using XmlRpcLibrary;
using WBOffice4.Interfaces;
using System.Globalization;
using System.Diagnostics;
using System.Net;
using WBOffice4.Utils;
using System.Threading;
namespace WBOffice4
{

    
    public abstract class OfficeApplication
    {
        
        public static readonly String iso8601dateFormat = "{0:dd/MM/yyyy HH:mm:ss}";
        public static readonly TraceEventLogListener listener = new TraceEventLogListener();
        private static UserInfo userInfo;
        private static Uri webAddress;
        private static IMenuListener _MenuListener;
        private static IOfficeDocument officedocument;
        private static IOfficeApplication officeApplication;
        public static readonly double m_version = 4.100;
        static OfficeApplication()
        {            
            try
            {
                Thread.CurrentThread.CurrentUICulture = new CultureInfo("es");
            }
            catch (Exception e)
            {
                OfficeApplication.WriteError(e);
                //MessageBox.Show(e.Message + "\r\n" + e.StackTrace);
            }
            Debug.Listeners.Add(listener);
            Trace.Listeners.Add(listener);
        }
        protected OfficeApplication()
        {
                      
        }
        public static void WriteError(Exception e)
        {            
            listener.WriteError(e);
        }
        internal static IOfficeDocument OfficeDocumentProxy
        {
            get
            {
                if (officedocument == null)
                {
                    if (OfficeApplicationProxy.isValidVersion(m_version))
                    {
                        if (_MenuListener != null)
                        {
                            _MenuListener.LogOn();
                        }
                        officedocument = XmlRpcProxyFactory.Create<IOfficeDocument>();
                        officedocument.WebAddress = GetWebAddress();
                        SWBConfiguration configuration = new SWBConfiguration();
                        officedocument.Credentials = new NetworkCredential(userInfo.Login, userInfo.Password);
                        if (configuration.UsesProxy)
                        {
                            officedocument.ProxyPort = int.Parse(configuration.ProxyPort,CultureInfo.InvariantCulture);
                            officedocument.ProxyServer = new Uri(configuration.ProxyServer);
                        }
                    }
                }
                return officedocument;
            }

        }
        internal static IOfficeApplication OfficeApplicationProxy
        {
            get
            {
                if (officeApplication == null)
                {
                    officeApplication = XmlRpcProxyFactory.Create<IOfficeApplication>();
                    officeApplication.WebAddress = GetWebAddress();
                    SWBConfiguration configuration = new SWBConfiguration();
                    officeApplication.Credentials = new NetworkCredential(userInfo.Login, userInfo.Password);
                    if (configuration.UsesProxy)
                    {
                        officeApplication.ProxyPort = int.Parse(configuration.ProxyPort,CultureInfo.InvariantCulture);
                        officeApplication.ProxyServer = new Uri(configuration.ProxyServer);
                    }
                    if (!officeApplication.isValidVersion(m_version))
                    {
                        throw new WBAlertException("La versión entre el publicador y el servidor no son compatibles");
                    }
                    else
                    {
                        RtlAwareMessageBox.Show("Su sesión ha sido iniciada", "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                }
                return officeApplication;
            }
        }
        public static IMenuListener MenuListener
        {
            get
            {
                return _MenuListener;
            }
            set
            {
                _MenuListener = value;
            }
        }       

        /// <summary>
        /// Gets all the documents opened in the application
        /// </summary>
        protected internal abstract ICollection<OfficeDocument> Documents
        {
            get;
            
        }
        protected internal abstract string Version
        {
            get;
        }

        protected internal abstract OfficeDocument Open(FileInfo file);

        protected internal abstract OfficeDocument Open(FileInfo file,String contentid,String rep);

        /// <param name="obj">Clase <see cref="T:System.Object"/> que se va a comparar con la clase <see cref="T:System.Object"/> actual.</param>
        /// <returns>Es true si el objeto <see cref="T:System.Object"/> especificado es igual al objeto <see cref="T:System.Object"/> actual; en caso contrario, es false.</returns>
        public override bool Equals(object obj)
        {
            OfficeApplication officeApplication=obj as OfficeApplication;
            if (officeApplication!=null)
            {                
                return this.Version.Equals(officeApplication.Version);
            }
            return false;
        }

        /// <returns>Código hash para la clase <see cref="T:System.Object"/> actual.</returns>
        public override int GetHashCode()
        {
            return this.Version.GetHashCode();
        }

        /// <returns>Una clase <see cref="T:System.String"/> que representa la clase <see cref="T:System.Object"/> actual.</returns>
        public override string ToString()
        {
            return this.Version;
        }
        public void Open(DocumentType documentType)
        {
            if (OfficeApplication.TryLogOn())
            {
                FormOpenContent frm = new FormOpenContent(this, documentType);
                frm.ShowDialog();
                frm.open();
            }
        }
        public static void ChangePassword()
        {
            if (TryLogOn())
            {
                FormChangePassword form = new FormChangePassword();
                DialogResult result=form.ShowDialog();
                if (result == DialogResult.OK)
                {
                    OfficeApplicationProxy.changePassword(form.NewPassword);
                    userInfo.ChangePassword(form.NewPassword);
                }
            }
        }
        internal static void LogOff()
        {
            officeApplication = null;
            officedocument = null;
            userInfo = null;
            webAddress = null;            
        }
        public static void CloseSession()
        {
            userInfo = null;
            webAddress = null;
            if (MenuListener != null)
            {
                MenuListener.LogOff();
                LogOff();
            }

        }
        internal protected static string SetupDocument(String repositoryName,String contentId)
        {            
            if (!String.IsNullOrEmpty(contentId) && OfficeApplication.TryLogOn())
            {
                IOfficeDocument document=OfficeApplication.OfficeDocumentProxy;
                try
                {
                    if (document.exists(repositoryName,contentId))
                    {
                        return contentId;
                    }
                    else
                    {
                        RtlAwareMessageBox.Show("El contenido parace haberse publicado en un sitio web.\r\nAl sitio donde se está intentando conectar, indica que este contenido no existe.\r\nSi desea continuar se perderá esta información, de lo contrario, cierre este documento.", "Verificación de contenido en sitio web", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    }
                }
                catch (FormatException fe)
                {
                    OfficeApplication.WriteError(fe);
                }
                catch (ApplicationException ap)
                {
                    OfficeApplication.WriteError(ap);
                }                
            }
            return null;
        }        
        internal static Uri GetWebAddress()
        {            
            if (webAddress == null)
            {
                if (TryLogOn())
                {
                    return webAddress;
                }
                else
                {
                    throw new WBAlertException("The user can be logged");
                }
            }
            return webAddress;             
        }
        public static void InitSession()
        {
            TryLogOn();            
        }
        internal static bool TryLogOn()
        {
            bool tryLogOn = false;
            if (userInfo == null || webAddress == null)
            {
                LogOn();
                if (userInfo == null || webAddress == null)
                {
                    LogOff();
                    tryLogOn= false;
                }
                else
                {
                    MenuListener.LogOn();
                    tryLogOn=true;
                }
            }
            else
            {
                tryLogOn=true;
            }
            if (tryLogOn)
            {
                try
                {
                    OfficeApplicationProxy.isValidVersion(m_version);
                    if (MenuListener != null)
                    {
                        MenuListener.LogOn();
                    }
                    URIConfigurationList uRIConfigurationList = new URIConfigurationList();
                    String uri=OfficeApplicationProxy.WebAddress.ToString();
                    if (uri.EndsWith("gtw"))
                    {
                        uri = uri.Substring(0, uri.Length - 3);
                    }
                    uRIConfigurationList.Add(OfficeApplicationProxy.Credentials.UserName, new Uri(uri));
                    MenuListener.LogOn();                    
                }
                catch (HttpException e)
                {
                    MenuListener.LogOff();
                    tryLogOn = false;
                    if (e.Code == HttpStatusCode.NotFound)
                    {                        
                        RtlAwareMessageBox.Show(null, "No se puede conectar a la dirección web, o la dirección es incorrecta o no se tiene habilitada la publicación de contenidos", "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        
                    }
                    else if (e.Code == HttpStatusCode.Forbidden)
                    {
                        RtlAwareMessageBox.Show(null, "Su clave o contraseña es incorrecta", "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error);                        
                    }
                    else
                    {
                        RtlAwareMessageBox.Show(null, e.Message, "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error, MessageBoxDefaultButton.Button1);
                        
                    }
                    OfficeApplication.LogOff();
                }
                catch (WebException e)
                {
                    MenuListener.LogOff();
                    tryLogOn = false;
                    if (e.Status == WebExceptionStatus.ConnectFailure)
                    {
                        RtlAwareMessageBox.Show(null, "El sitio web con el que intenta trabajar, se encuentra apagado o no se puede acceder al mismo.", "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error);                        
                    }
                    else
                    {
                        RtlAwareMessageBox.Show(null, e.Message, "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error);                        
                    }
                    OfficeApplication.LogOff();
                }               
                catch (Exception e)
                {
                    OfficeApplication.WriteError(e);
                    MenuListener.LogOff();
                    RtlAwareMessageBox.Show(null, e.Message, "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    tryLogOn = false;
                    OfficeApplication.LogOff();
                }                
            }
            return tryLogOn;
        }
        private static void LogOn()
        {
            FormLogin login = new FormLogin();
            DialogResult result=login.ShowDialog();
            if (result == DialogResult.OK)
            {
                userInfo = new UserInfo(login.User, login.Password);
                webAddress = login.WebAddress;
            }
            else
            {
                LogOff();
            }
        }
        public static void ShowHelp()
        {
            try
            {
                System.Diagnostics.Process.Start("explorer.exe", "\"http://www.semanticwebbuilder.org.mx/OS/wb/COSWB/documentation\"");

            }
            catch (Exception err)
            {
                OfficeApplication.WriteError(err);                
            }

        }
        public static void ShowAbout()
        {
            AboutBox frmAbout = new AboutBox();
            frmAbout.ShowDialog();
        }
        public static void CreatePage()
        {
            if (TryLogOn())
            {
                FormCreatePage form = new FormCreatePage();
                form.ShowDialog();                
            }
        }
        public static void ShowDocumentsToAuthorize()
        {
            if (TryLogOn())
            {
                FormDocumentsToAuthorize form = new FormDocumentsToAuthorize();
                form.ShowDialog();
            }
        }
        
    }
}
