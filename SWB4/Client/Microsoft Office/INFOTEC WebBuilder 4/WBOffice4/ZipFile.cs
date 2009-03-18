using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace WBOffice4
{
    /*internal class ZipFile
    {
        FileInfo zipFile;
        DirectoryInfo temporalFile;
        public ZipFile(FileInfo zipFile, DirectoryInfo temporalFile)
        {
            this.zipFile = zipFile;
            this.temporalFile = temporalFile;
        }
        public void DoCreateZipFile()
        {
            Shell32.ShellClass sc = new Shell32.ShellClass();
            Shell32.Folder SrcFlder = sc.NameSpace(temporalFile.FullName);
            Shell32.Folder DestFlder = sc.NameSpace(zipFile.FullName);
            Shell32.FolderItems items = SrcFlder.Items();
            DestFlder.CopyHere(items, 20);
            System.Threading.Thread.Sleep(1000);
        }
    }*/
}
