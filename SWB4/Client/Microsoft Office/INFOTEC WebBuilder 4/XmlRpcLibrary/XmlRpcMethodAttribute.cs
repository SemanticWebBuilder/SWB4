using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace XmlRpcLibrary
{
    [AttributeUsage(AttributeTargets.Method)]
    public sealed class XmlRpcMethodAttribute : Attribute
    {
        public XmlRpcMethodAttribute()
        {
        }

        public XmlRpcMethodAttribute(string method)
        {
            this.method = method;
        }

        public string Method
        {
            get
            { return method; }
        }

        public override string ToString()
        {
            string value = "Method : " + method;
            return value;
        }        
        private string method = "";
        
    }
}
