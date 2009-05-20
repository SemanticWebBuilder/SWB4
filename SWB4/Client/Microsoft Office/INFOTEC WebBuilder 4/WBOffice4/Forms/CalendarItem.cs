using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public class CalendarItem :  ListViewItem
    {
        private CalendarInfo info;
        public CalendarItem(CalendarInfo info)
        {
            this.info = info;
            this.Text = info.title;
            this.SubItems.Add(info.active.ToString());
        }
        public CalendarInfo CalendarInfo
        {
            get
            {
                return info;
            }
        }
    }
}
