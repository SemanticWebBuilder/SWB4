using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{    
    public sealed class ArrayElement
    {
        private ArrayList _values = new ArrayList();
        public ArrayElement()
        {

        }
        public ArrayElement(ArrayList values)
        {
            _values = values;
        }

        [XmlArray("data")]
        [XmlArrayItem("value", Type = typeof(Value))]
        public ArrayList Values
        {
            get
            {
                return _values;
            }            
        }  
    }
}
