using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;
namespace WBOffice4
{
    internal class WBConfiguration
    {
        HashSet<Uri> url = new HashSet<Uri>();
        String login;
        public WBConfiguration()
        {
            
            
        }        
        public String Login
        {
            get
            {
                return login;
            }
            set
            {
                login = value;
            }
        }
        public void AddURI(Uri uri)
        {            
            this.url.Add(uri);
        }                
    }
}
