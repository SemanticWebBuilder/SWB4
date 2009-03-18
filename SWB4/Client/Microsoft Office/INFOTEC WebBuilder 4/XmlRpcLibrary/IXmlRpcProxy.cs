using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
namespace XmlRpcLibrary
{
    public interface IXmlRpcProxy
    {
        Uri WebAddress { get; set; }
        NetworkCredential Credentials { get; set; }
        ICollection<Attachment> Attachments { get; }
        Uri ProxyServer { get; set; }
        int ProxyPort { get; set; }
        HashSet<Part> ResponseParts { get; set; }
    }
}
