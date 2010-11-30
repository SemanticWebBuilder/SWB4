using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class ObjecInfo
    {
        public String uri;
        public String name;
        public PropertyObjectInfo[] properties;
        public override String ToString()
        {
            return name;
        }
    }
}
