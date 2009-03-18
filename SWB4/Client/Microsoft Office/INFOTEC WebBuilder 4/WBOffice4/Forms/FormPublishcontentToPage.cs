using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Steps;
namespace WBOffice4.Forms
{
    public partial class FormPublishcontentToPage : TSWizards.BaseWizard
    {
        private OfficeDocument document;
        private String title,description;
        public FormPublishcontentToPage(OfficeDocument document)
        {
            InitializeComponent();
            this.document = document;
        }
        public FormPublishcontentToPage(OfficeDocument document,String title,String description)
        {
            InitializeComponent();
            this.document = document;
            this.title = title;
            this.description = description;
        }

        private void FormPublishcontentToPage_LoadSteps(object sender, EventArgs e)
        {

            if (title == null || description == null)
            {
                this.AddStep(new TitleAndDescription(document,false));
                this.AddStep(new SelectVersionToPublish(document));
                this.AddStep(new ViewProperties());
                this.AddStep(new SelectSitePublish());
                
            }
            else
            {                
                this.AddStep(new SelectVersionToPublish(document));
                this.AddStep(new ViewProperties());
                this.AddStep(new SelectSitePublish(title, description));
                
            }
            
        }
    }
}
