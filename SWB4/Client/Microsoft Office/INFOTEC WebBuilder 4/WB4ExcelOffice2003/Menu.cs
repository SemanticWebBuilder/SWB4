using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WBOffice4;
using Excel = Microsoft.Office.Interop.Excel;
using Office = Microsoft.Office.Core;
namespace WB4ExcelOffice2003
{
    internal class Menu : IMenuListener
    {
        private Excel.Application application;
        public Menu(Excel.Application application)
        {
            this.application = application;
        }
        #region MenuListener Members

        public void NoDocumentsActive()
        {
            throw new NotImplementedException();
        }

        public void DocumentsActive()
        {
            throw new NotImplementedException();
        }

        public void NoDocumentPublished()
        {
            throw new NotImplementedException();
        }

        public void DocumentPublished()
        {
            throw new NotImplementedException();
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
