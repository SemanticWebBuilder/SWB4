using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace XmlRpcLibrary
{
    public sealed class Attachment
    {
        private FileInfo file;
        private string name;
        public Attachment(FileInfo file, string name)
        {
            this.file = file;
            this.name = name;
        }
        public FileInfo File
        {
            get
            {
                return file;
            }
        }
        public string Name
        {
            get
            {
                return name;
            }
        }

        
    }
}
