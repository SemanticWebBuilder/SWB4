using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;
namespace XmlRpcLibrary
{
    [Serializable]
    public class XmlRpcException : Exception
    {
        public XmlRpcException()           
        {

        }
        public XmlRpcException(String message)
            : base(message)
        {

        }
        protected XmlRpcException(SerializationInfo info,StreamingContext context)
            : base(info, context)
        {

        }
        public XmlRpcException(String message,Exception cause)
            : base(message,cause)
        {

        }        
    }
}
