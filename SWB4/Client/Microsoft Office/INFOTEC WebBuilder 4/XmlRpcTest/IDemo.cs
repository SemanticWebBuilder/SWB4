using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XmlRpcLibrary;
namespace XmlRpcTest
{
    public interface IDemo : IXmlRpcProxy
    {
        [XmlRpcMethod("Demo.add")]
        String Add(int a, double myDouble, String c, DateTime d, bool e);
        [XmlRpcMethod("Demo.add")]
        String Add(int a, double myDouble, String c, DateTime d, int e);
    }
}
