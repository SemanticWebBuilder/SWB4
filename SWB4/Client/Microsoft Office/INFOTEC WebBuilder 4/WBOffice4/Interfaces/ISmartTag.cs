using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XmlRpcLibrary;

namespace WBOffice4.Interfaces
{
    public interface ISmartTag : IXmlRpcProxy
    {

        [XmlRpcMethod("OfficeSmartTag.isSmartTag")]
        bool isSmartTag(String text);

        [XmlRpcMethod("OfficeSmartTag.search")]
        ObjecInfo[] search(String text);


        [XmlRpcMethod("OfficeSmartTag.getTokens")]
        String[] getTokens(String text);
    }
}
