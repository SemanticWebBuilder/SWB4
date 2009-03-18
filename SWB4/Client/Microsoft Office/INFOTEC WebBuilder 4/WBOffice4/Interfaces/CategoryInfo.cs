using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class CategoryInfo
    {
        public String UDDI;
        public String title;
        public String description;
        public int childs;
        public override string ToString()
        {
            return title;
        }
        public override int GetHashCode()
        {
            return UDDI.GetHashCode();
        }
        public override bool Equals(Object obj)
        {
            bool equals = false;
            if (obj is CategoryInfo)
            {
                return ((CategoryInfo)obj).UDDI == UDDI;
            }
            return equals;
        }

    }
}