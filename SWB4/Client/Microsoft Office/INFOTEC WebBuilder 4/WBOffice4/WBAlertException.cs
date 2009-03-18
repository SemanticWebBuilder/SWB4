using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;
namespace WBOffice4
{
    [Serializable]
    public class WBAlertException : WBException
    {
        public WBAlertException()
            : base()
        {

        }
        public WBAlertException(string message)
            : base(message)
        {

        }
        protected WBAlertException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {

        }
        public WBAlertException(string message,Exception cause)
            : base(message, cause)
        {

        }
    }
}
