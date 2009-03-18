using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    [XmlRoot("value")]
    public sealed class StringValue : Value
    {
        [XmlElement("string")]
        public string Value { get; set; }
        public StringValue()
        {
        }
        public StringValue(string value)
        {
            this.Value = value;
        }
    }
}
