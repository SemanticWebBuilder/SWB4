using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Controls
{
    public class PropertyItem : ListViewItem
    {
        private PropertyObjectInfo prop;
        public PropertyItem(PropertyObjectInfo prop)
        {
            this.prop = prop;
            this.Text = prop.title;
            this.SubItems.Add(prop.value);
        }
        public PropertyObjectInfo PropertyObjectInfo
        {
            get
            {
                return prop;
            }
        }
        public bool isURL
        {
            get
            {
                if(prop.value==null)
                {
                    return false;
                }
                string pattern = @"https?://([-\w\.]+)+(:\d+)?(/([\w/_\.]*(\?\S+)?)?)?";
                return System.Text.RegularExpressions.Regex.IsMatch(prop.value, pattern);
            }
        }
        public bool isEmail
        {
            get
            {
                if (prop.value == null)
                {
                    return false;
                }
                //string pattern = @"[a-z0-9]!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z][0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
                string pattern = @"^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}" + @"\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\" + @".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$";
                return System.Text.RegularExpressions.Regex.IsMatch(prop.value, pattern);
            }
        }
    }
}
