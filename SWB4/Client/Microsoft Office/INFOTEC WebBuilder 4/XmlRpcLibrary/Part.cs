using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace XmlRpcLibrary
{
    public class Part
    {
        private byte[] content;
        private String name;
        private String fileName;
        public Part(byte[] content, String name, String fileName)
        {
            this.content = content;
            this.name = name;
            this.fileName = fileName;
        }
        public String getFileName()
        {
            return fileName;
        }
        public String getName()
        {
            return name;
        }
        public byte[] getContent()
        {
            return content;
        }


    }
}
