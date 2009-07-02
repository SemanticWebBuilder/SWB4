using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WBOffice4.Steps
{
    public class LanguageListItem : ListViewItem
    {
        private String id, title;
        public LanguageListItem(String id,String title)
        {
            this.Text = title;
            this.id = id;
        }
        public String ID
        {
            get
            {
                return id;
            }
        }
    }
}
