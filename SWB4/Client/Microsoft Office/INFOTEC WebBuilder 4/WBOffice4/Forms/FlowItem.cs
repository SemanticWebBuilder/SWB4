using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public class FlowItem : ListViewItem
    {
        private FlowContentInformation info;
        public FlowItem(FlowContentInformation info)
        {
            this.Text = info.title;
            this.SubItems.Add(info.resourceInfo.page.title);
            this.SubItems.Add(info.step);
            this.SubItems.Add(info.resourceInfo.version);
        }
        public FlowContentInformation FlowContentInformation
        {
            get
            {
                return info;
            }
        }
    }
}
