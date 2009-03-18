using System;
using System.Windows.Forms;
using Microsoft.VisualStudio.Tools.Applications.Runtime;
using WB4Office2003Library;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
using Office = Microsoft.Office.Core;

namespace WB4PPTOffice2003
{
    public partial class ThisAddIn
    {
        Menu menu;
        PptApplication officeApplication;
        private void ThisAddIn_Startup(object sender, System.EventArgs e)
        {
            menu = new Menu(this.Application);
            officeApplication = new PptApplication(this.Application);
            PptApplication.MenuListener = menu;

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
