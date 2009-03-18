using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;
namespace WBOffice4
{
    [Serializable]
    public class WBOfficeErrorException : WBException
    {
        public WBOfficeErrorException()
            : base()
        {

        }  
        protected WBOfficeErrorException(SerializationInfo info,StreamingContext context) : base(info,context)
        {

        }
        public WBOfficeErrorException(string message)
            : base(message)
        {

        }        
        public WBOfficeErrorException(string message,Exception innerException)
            : base(message,innerException)
        {
            
        }        
    }
}
