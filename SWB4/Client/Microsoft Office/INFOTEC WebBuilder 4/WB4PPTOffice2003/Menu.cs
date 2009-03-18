using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WBOffice4;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
using Office = Microsoft.Office.Core;
namespace WB4PPTOffice2003
{
    internal class Menu : IMenuListener
    {
        private PowerPoint.Application application;
        public Menu(PowerPoint.Application application)
        {
            this.application = application;
        }
        #region MenuListener Members

        public void NoDocumentsActive()
        {
            
        }

        public void DocumentsActive()
        {
            
        }

        public void NoDocumentPublished()
        {
            
        }

        public void DocumentPublished()
        {
            
        }

        public void LogOff()
        {
            /*this.buttonInit.Enabled = true;
            this.buttonCloseSession.Enabled = false;*/
        }

        public void LogOn()
        {
            /*this.buttonInit.Enabled = false;
            this.buttonCloseSession.Enabled = true;*/
        }

        #endregion
    }
}
