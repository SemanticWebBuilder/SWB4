using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    [XmlRoot("value")]
    public sealed class FloatValue : Value
    {
        [XmlElement("float")]
        public float Value { get; set; }
        public FloatValue()
        {
        }
        public FloatValue(float value)
        {
            this.Value = value;
        }
    }
}
