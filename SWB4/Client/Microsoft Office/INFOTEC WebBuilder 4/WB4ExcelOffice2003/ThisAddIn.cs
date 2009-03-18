using System;
using System.Windows.Forms;
using WB4Office2003Library;
using Microsoft.VisualStudio.Tools.Applications.Runtime;
using Excel = Microsoft.Office.Interop.Excel;
using Office = Microsoft.Office.Core;

namespace WB4ExcelOffice2003
{
    public partial class ThisAddIn
    {
        ExcelApplication officeApplication;
        Menu menu;
        private void ThisAddIn_Startup(object sender, System.EventArgs e)
        {
            #region VSTO generated code                        
            menu = new Menu(this.Application);
            officeApplication = new ExcelApplication(this.Application);
            ExcelApplication.MenuListener = menu;            
            #endregion

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
