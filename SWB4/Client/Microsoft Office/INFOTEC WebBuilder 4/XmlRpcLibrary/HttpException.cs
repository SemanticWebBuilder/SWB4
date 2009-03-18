using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Runtime.Serialization;
using System.Security.Permissions;
namespace XmlRpcLibrary
{
    [Serializable]
    public class HttpException : Exception, ISerializable
    {

        public HttpStatusCode Code { get; set; }
        public String Html { get; set; }
        public HttpException()
        {
            
        }
        [SecurityPermission(SecurityAction.Demand, SerializationFormatter = true)]
        public override void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            if (info == null)
                throw new ArgumentNullException("info");

            info.AddValue("Code", Code);
            info.AddValue("Html", Html);
            base.GetObjectData(info, context);
        }
        public HttpException(String message)
            : base(message)
        {
            
        }
        public HttpException(String message,HttpStatusCode code,String html)
            : base(message)
        {
            this.Code = code;
            this.Html = html;
        }
        protected HttpException(SerializationInfo info,StreamingContext context)
            : base(info, context)
        {

        }
        public HttpException(String message, Exception cause)
            : base(message,cause)
        {

        }  
    }
}
