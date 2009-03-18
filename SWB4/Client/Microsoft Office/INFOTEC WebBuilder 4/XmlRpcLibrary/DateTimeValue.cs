using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    [XmlRoot("value")]
    public sealed class DateTimeValue : Value
    {
        [XmlElement("dateTime.iso8601")]
        public DateTime Value { get; set; }
        public DateTimeValue()
        {
        }
        public DateTimeValue(DateTime value)
        {
            this.Value = value;
        }
    }
}
