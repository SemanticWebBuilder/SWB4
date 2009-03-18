using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace WBOffice4.Interfaces
{
    
    public class VersionInfo
    {
    
        public String contentId;
    
        public String nameOfVersion;
    
        public DateTime created;
    
        public String user;

        public bool published;

        public override String ToString()
        {
            if (nameOfVersion == "*")
            {
                return "Mostrar la última version";
            }
            else
            {
                return nameOfVersion;

            }
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
            VersionInfo other = (VersionInfo)obj;
            if ((this.nameOfVersion == null) ? (other.nameOfVersion != null) : !this.nameOfVersion.Equals(other.nameOfVersion))
            {
                return false;
            }
            return true;
        }


        public override int GetHashCode()
        {
            int hash = 7;
            hash = 89 * hash + (this.nameOfVersion != null ? this.nameOfVersion.GetHashCode() : 0);
            return hash;
        }
    }
}
