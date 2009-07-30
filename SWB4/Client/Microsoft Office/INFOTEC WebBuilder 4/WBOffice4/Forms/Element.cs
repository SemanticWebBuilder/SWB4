using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public class ElementListView : ListViewItem
    {
        ElementInfo info;
        public ElementListView(ElementInfo info)
        {
            this.info = info;
            this.Text = info.title;
            if (this.SubItems.Count == 1)
            {
                this.SubItems[0].Text = info.type;
            }
            if (this.SubItems.Count == 2)
            {
                if (info.active)
                {
                    this.SubItems[0].Text = "Sí";
                }
                else
                {
                    this.SubItems[0].Text = "No";
                }
                this.SubItems[1].Text = info.type;
            }
        }
        public ElementInfo ElementInfo
        {
            get
            {
                return info;
            }
        }
    }
}
