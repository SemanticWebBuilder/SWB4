using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WBOffice4.Forms
{
    public partial class FormPreview : Form
    {
        private Uri uri;
        public FormPreview(Uri uri,String title)
        {
            InitializeComponent();            
            this.uri = uri;
            this.textBoxWebAddress.Text = uri.ToString();
            this.webBrowser1.Navigate(uri, null, null, "Authorization: Basic "+GetUserPassWordEncoded());
            this.webBrowser1.AllowNavigation = false;
            this.Text = title;
        }
        public FormPreview(Uri uri,Boolean showWebAddress,String title) : this(uri,title)
        {
            this.textBoxWebAddress.Visible = showWebAddress;
            this.labelWebAddress.Visible = showWebAddress;
            this.buttonViewBrowser.Visible = showWebAddress;
        }

        private String GetUserPassWordEncoded()
        {
            String userPassword = OfficeApplication.OfficeDocumentProxy.Credentials.UserName + ":" + OfficeApplication.OfficeDocumentProxy.Credentials.Password;
            String encoded =EncodeTo64(userPassword);
            return encoded;
        }
        private static string EncodeTo64(string toEncode)
        {

            byte[] toEncodeAsBytes

                  = System.Text.ASCIIEncoding.ASCII.GetBytes(toEncode);

            string returnValue

                  = System.Convert.ToBase64String(toEncodeAsBytes);

            return returnValue;

        }
        private void buttonClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }
        public static void showInBrowser(Uri uri)
        {
            System.Diagnostics.Process.Start("explorer.exe",uri.ToString());
        }

        private void buttonViewBrowser_Click(object sender, EventArgs e)
        {
            showInBrowser(this.uri);
        }

    }
}
