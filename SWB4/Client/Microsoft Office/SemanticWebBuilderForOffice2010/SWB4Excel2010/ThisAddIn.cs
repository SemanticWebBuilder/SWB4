using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Linq;
using Excel = Microsoft.Office.Interop.Excel;
using Office = Microsoft.Office.Core;
using Microsoft.Office.Tools.Excel;
using WB4Office2010Library;
using WBOffice4;
namespace SWB4Excel2010
{
    public partial class ThisAddIn
    {
        public OfficeApplication officeApplication;

        private void ThisAddIn_Startup(object sender, System.EventArgs e)
        {
            officeApplication = new ExcelApplication(this.Application);
            OfficeApplication.MenuListener = Globals.Ribbons.RibbonMenu;

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
