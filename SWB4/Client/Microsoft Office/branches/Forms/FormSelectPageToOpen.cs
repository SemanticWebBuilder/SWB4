using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Steps;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public partial class FormSelectPageToOpen : TSWizards.BaseWizard
    {
        private SelectSiteToOpen selectSiteToOpen;
        private DocumentType type;
        private WebSiteInfo siteInfo;
        public FormSelectPageToOpen(DocumentType type, WebSiteInfo siteInfo)
        {
            InitializeComponent();
            this.type = type;
            this.siteInfo=siteInfo;
            this.cancel.Enabled = true;
        }
        public FormSelectPageToOpen(DocumentType type) : this(type,null)
        {
            
        }

        private void FormSelectPageToOpen_LoadSteps(object sender, EventArgs e)
        {
            selectSiteToOpen = new SelectSiteToOpen(type, siteInfo);
            this.AddStep(selectSiteToOpen);
            this.cancel.Enabled = true;
        }
        public WebPageInfo WebPage
        {
            get
            {
                return (WebPageInfo)this.Data[SelectSite.WEB_PAGE];
            }
        }

        private void FormSelectPageToOpen_Activated(object sender, EventArgs e)
        {
            this.cancel.Enabled = true;
        }
    }
   
}
