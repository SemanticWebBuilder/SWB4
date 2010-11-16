using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Linq;
using Word = Microsoft.Office.Interop.Word;
using Office = Microsoft.Office.Core;
using Microsoft.Office.Tools.Word;
using WB4Office2010Library;
namespace SWB4Word2010
{
    public partial class ThisAddIn
    {
        private WordOfficeApplication officeApplication;
        
        private void ThisAddIn_Startup(object sender, System.EventArgs e)
        {
            officeApplication = new WordOfficeApplication(this.Application);
            WBOffice4.OfficeApplication.MenuListener = Globals.Ribbons.RibbonMenuWord;

        }

        private void ThisAddIn_Shutdown(object sender, System.EventArgs e)
        {
        }

        #region VSTO generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InternalStartup()
        {
            this.Startup += new System.EventHandler(ThisAddIn_Startup);
            this.Shutdown += new System.EventHandler(ThisAddIn_Shutdown);
        }
        
        #endregion
    }
}
