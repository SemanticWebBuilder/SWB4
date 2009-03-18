using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class CalendarInfo
    {
        public String id;
        public String xml;
        public bool active;
        public String title;

        public override bool Equals(Object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (GetType() != obj.GetType())
            {
                return false;
            }
            CalendarInfo other = (CalendarInfo)obj;
            if ((this.id == null) ? (other.id != null) : !this.id.Equals(other.id))
            {
                return false;
            }
            return true;
        }


        public override int GetHashCode()
        {
            int hash = 7;
            hash = 89 * hash + (this.id != null ? this.id.GetHashCode() : 0);
            return hash;
        }


        public override String ToString()
        {
            return title.ToString();
        }
    }
}
