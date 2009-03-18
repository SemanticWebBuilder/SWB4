using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    [XmlRoot("value")]
    public sealed class DoubleValue : Value
    {
        [XmlElement("double")]
        public double Value { get; set; }
        public DoubleValue()
        {
        }
        public DoubleValue(double value)
        {
            this.Value = value;
        }
    }

}
