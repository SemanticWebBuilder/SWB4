using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4
{
    public interface IMenuListener
    {
        void NoDocumentsActive();
        void DocumentsActive();
        void NoDocumentPublished();
        void DocumentPublished();
        void LogOff();
        void LogOn();
    }
}
