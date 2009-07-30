using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class ElementInfo
    {
        public String id;
        public String title;
        public bool active;
        public String type;


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
            ElementInfo other = (ElementInfo)obj;
            if ((this.id == null) ? (other.id != null) : !this.id.Equals(other.id))
            {
                return false;
            }
            if ((this.type == null) ? (other.type != null) : !this.type.Equals(other.type))
            {
                return false;
            }
            return true;
        }


        public override int GetHashCode()
        {
            int hash = 3;
            hash = 97 * hash + (this.id != null ? this.id.GetHashCode() : 0);
            hash = 97 * hash + (this.type != null ? this.type.GetHashCode() : 0);
            return hash;
        }



        public override String ToString()
        {
            return title.ToString();
        }
    }
}
