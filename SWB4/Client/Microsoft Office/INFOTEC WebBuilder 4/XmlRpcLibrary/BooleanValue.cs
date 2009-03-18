using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    [XmlRoot("value")]
    public sealed class BooleanValue : Value
    {
        [XmlElement("boolean")]
        public int Value { get; set; }
        public BooleanValue()
        {
        }
        public BooleanValue(bool value)
        {
            this.Value = value?1:0;
        }
    }
}
