using System;
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
using System.Net;


namespace WBOffice4
{

    //public delegate void ActiveMenuForDocumentPublished();
    public abstract class OfficeApplication
    {
        public static readonly String iso8601dateFormat = "{0:dd/MM/yyyy HH:mm:ss}";
        private static UserInfo userInfo;
        private static Uri webAddress;
        private static IMenuListener _MenuListener;
        private static IOfficeDocument officedocument;
        private static IOfficeApplication officeApplication;
        private static double version = 0.1;
        protected OfficeApplication()
        {
           
        }        
        internal static IOfficeDocument OfficeDocumentProxy
        {
            get
            {
                if (officedocument == null)
                {
                    if (OfficeApplicationProxy.IsValidVersion(version))
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
                    if (!officeApplication.IsValidVersion(version))
                    {
                        throw new WBAlertException("La versión entre el publicador y el servidor no son compatibles");
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
                    OfficeApplicationProxy.ChangePassword(form.NewPassword);
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
                    SwbEventLog.Log(fe);
                }
                catch (ApplicationException ap)
                {
                    SwbEventLog.Log(ap);
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
                    OfficeApplicationProxy.IsValidVersion(version);
                    if (MenuListener != null)
                    {
                        MenuListener.LogOn();
                    }
                    //RtlAwareMessageBox.Show("Su sesión ha sido iniciada", "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                catch (HttpException e)
                {
                    if (e.Code == HttpStatusCode.NotFound)
                    {
                        RtlAwareMessageBox.Show(null, "El sitio al que desea conectarse, indica que no tiene habilitada la función de publicación de contenidos", "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error);
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
                    RtlAwareMessageBox.Show(null, e.Message, "Iniciar sessión", MessageBoxButtons.OK, MessageBoxIcon.Error);
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
            AboutBox frmAbout = new AboutBox();
            frmAbout.ShowDialog();
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
