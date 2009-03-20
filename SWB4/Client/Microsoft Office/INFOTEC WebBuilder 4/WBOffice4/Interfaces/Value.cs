using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
   public class Value {
    public String key;
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
        Value other = (Value) obj;
        if ((this.key == null) ? (other.key != null) : !this.key.Equals(other.key))
        {
            return false;
        }
        return true;
    }

    
    public override int GetHashCode()
    {
        int hash = 7;
        hash = 67 * hash + (this.key != null ? this.key.GetHashCode() : 0);
        return hash;
    }

    
    public override String ToString()
    {
        return title;
    }

}
}
