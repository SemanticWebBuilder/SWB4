using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Diagnostics;
namespace WBOffice4.Utils
{
    internal sealed class FileUtil
    {
        /// <summary>
        /// Delete a Directory
        /// </summary>
        /// <param name="dir"></param>
        /// <exception cref="DirectoryNotFoundException">If the especific directory does not exist</exception>
        public static void DeleteTemporalDirectory(DirectoryInfo dir)
        {
            try
            {
                dir.Delete(true);
            }
            catch (DirectoryNotFoundException e)
            {
                SwbEventLog.Log(e, EventLogEntryType.Warning);
            }
        }
        public static ICollection<FileInfo> GetFiles(DirectoryInfo dir)
        {
            List<FileInfo> attachments = new List<FileInfo>();
            attachments.AddRange(dir.GetFiles());
            foreach (DirectoryInfo dirInfo in dir.GetDirectories())
            {
                attachments.AddRange(GetFiles(dirInfo));
            }
            return attachments;
        }
    }
}
