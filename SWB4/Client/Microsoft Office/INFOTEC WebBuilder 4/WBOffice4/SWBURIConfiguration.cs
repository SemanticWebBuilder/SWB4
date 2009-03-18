using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4
{
    internal class SWBURIConfiguration
    {
        public string Login { get; set; }
        public Uri Uri { get; set; }
        public DateTime LastUsed { get; set; }

        public SWBURIConfiguration(string login, Uri uri)
        {            
            this.Login = login;
            this.Uri = uri;
            this.LastUsed = DateTime.Now;
        }
        public SWBURIConfiguration(string login, Uri uri, DateTime dateTime)
        {
            this.Login = login;
            this.Uri = uri;
            this.LastUsed = dateTime;
        } 
        public override bool Equals(object obj)
        {
            SWBURIConfiguration o = obj as SWBURIConfiguration;
            if (o != null)
            {
                return o.Uri == Uri;
            }            
            return false;            
        }
        public override int GetHashCode()
        {            
            return Uri.GetHashCode();
        }
        public override String ToString()
        {
            return Uri.ToString();
        }
        

    }
}
