using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class SemanticRepository
    {
        public String name;
        public String resid;
        public String pageid;
        public String uri;
        public override String ToString()
        {
            return name;
        }
    }
}
