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
            if (info.active)
            {
                this.SubItems.Add("Si");
            }
            else
            {
                this.SubItems.Add("No");
            }
        }
        public CalendarInfo CalendarInfo
        {
            get
            {
                return info;
            }
        }
        public bool Active
        {
            get
            {
                return info.active;
            }
            set
            {
                info.active = value;
                if (info.active)
                {
                    this.SubItems[1].Text="Si";
                }
                else
                {
                    this.SubItems[1].Text = "No";
                }
            }
        }
    }
}
