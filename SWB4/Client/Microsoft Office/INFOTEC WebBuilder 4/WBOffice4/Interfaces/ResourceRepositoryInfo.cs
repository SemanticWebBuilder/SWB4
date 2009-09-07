using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class ResourceRepositoryInfo
    {
        public String id;
        public String repository;
        public String version;
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
            ResourceRepositoryInfo other = (ResourceRepositoryInfo)obj;
            if ((this.id == null) ? (other.id != null) : !this.id.Equals(other.id))
            {
                return false;
            }
            return true;
        }


        public override int GetHashCode()
        {
            int hash = 7;
            hash = 23 * hash + (this.id != null ? this.id.GetHashCode() : 0);
            return hash;
        }
    }
}
