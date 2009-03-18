using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;
namespace WBOffice4
{
    [Serializable]
    public abstract class WBException : Exception,ISerializable
    {
        protected const string MHelpLink = "http://www.webbuilder.org.mx";
        protected WBException()
            : base()
        {
            
        }
        protected WBException(string message)
            : base(message)
        {
            
        }
        protected WBException(SerializationInfo info, StreamingContext context) 
            : base(info,context)
        {
            
        }
        protected WBException(string message, Exception innerException)
            : base(message,innerException)
        {
            
        }
        public override string HelpLink
        {
            get
            {
                return MHelpLink;
            }
            set
            {
                base.HelpLink = value;
            }
        }
    }
}
