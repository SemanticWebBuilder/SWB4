using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WBOffice4;
using Word = Microsoft.Office.Interop.Word;
using Office = Microsoft.Office.Core;
namespace WB4WordOffice2003
{
    internal class Menu : IMenuListener
    {
        private Word.Application application;
        public Menu(Word.Application application)
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
