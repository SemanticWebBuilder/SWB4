using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class RepositoryInfo
    {
        public RepositoryInfo(String name)
        {
            this.name = name;
        }
        public RepositoryInfo()
        {

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
            RepositoryInfo other = (RepositoryInfo)obj;
            if ((this.name == null) ? (other.name != null) : !this.name.Equals(other.name))
            {
                return false;
            }
            return true;
        }


        public override int GetHashCode()
        {
            int hash = 3;
            hash = 61 * hash + (this.name != null ? this.name.GetHashCode() : 0);
            return hash;
        }
        public String name;
        public bool exclusive;
        public SiteInfo siteInfo;


        public override String ToString()
        {
            return name;
        }
    }
}
