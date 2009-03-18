using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4
{
    internal class UserInfo
    {
        private String password, login;
        private Dictionary<string, string> cookies = new Dictionary<string, string>();        


        public UserInfo(String login,String password)
        {
            this.login = login;
            this.password = password;
        }        
        
        public String Login
        {
            get
            {
                return this.login;
            }
        }
        public String Password
        {
            get
            {
                return this.password;
            }
        }
        public Dictionary<string, string> Cookies
        {
            get
            {
                return cookies;
            }
            set
            {
                cookies = value;
            }
        }        
        public void ChangePassword(String newPassword)
        {            
            this.password = newPassword;            
        }
    }
}
