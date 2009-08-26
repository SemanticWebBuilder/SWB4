using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class LanguageInfo
    {
        public String id;
        public String title;


        public override String ToString()
        {
            return title;
        }


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
            LanguageInfo other = (LanguageInfo)obj;
            if ((this.id == null) ? (other.id != null) : !this.id.Equals(other.id))
            {
                return false;
            }
            return true;
        }


        public override int GetHashCode()
        {
            int hash = 7;
            hash = 17 * hash + (this.id != null ? this.id.GetHashCode() : 0);
            return hash;
        }
    }
}
