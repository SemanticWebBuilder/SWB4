using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Steps
{
    public partial class SelectWebPageID : TSWizards.BaseInteriorStep
    {
        
        public SelectWebPageID()
        {
            InitializeComponent();
        }

        private void SelectWebPageID_ValidateStep(object sender, CancelEventArgs e)
        {            
            if (String.IsNullOrEmpty(textBoxID.Text))
            {
                textBoxID.Focus();
                MessageBox.Show(this, "¡Debe indicar un identificador!", this.Wizard.Text,MessageBoxButtons.OK,MessageBoxIcon.Error);
                e.Cancel = true;
                return;
            }
            IOfficeApplication openOfficeApplication = OfficeApplication.OfficeApplicationProxy;
            String title = this.Wizard.Data[TitleAndDescription.TITLE].ToString();
            String description = this.Wizard.Data[TitleAndDescription.DESCRIPTION].ToString();
            WebPageInfo parent=(WebPageInfo)this.Wizard.Data[SelectSiteCreatePage.WEB_PAGE];
            openOfficeApplication.createPage(parent, this.textBoxID.Text, title, description);

        }

        private String getId(String titulo)
        {
            String newtitulo = "";
            char[] caracteres = titulo.ToCharArray();
            for (int i = 0; i < caracteres.Length; i++)
            {
                char c = caracteres[i];
                if (c >= 48 && c <= 57) // 0 - 9
                {
                    newtitulo += c;
                }
                if (c >= 65 && c <= 90) // A - Z
                {
                    newtitulo += c;
                }
                if (c >= 97 && c <= 122) // a - z
                {
                    newtitulo += c;
                }
                if (c == 32) // espacio
                {
                    newtitulo += "_";
                }
                if (c == 241) // ñ
                {
                    newtitulo += "n";
                }
                if (c == 209) // Ñ
                {
                    newtitulo += "N";
                }
                if (c >= 224 && c <= 229)	// a
                {
                    newtitulo += "a";
                }
                if (c >= 232 && c <= 235)	// e
                {
                    newtitulo += "e";
                }
                if (c >= 236 && c <= 239)	// i
                {
                    newtitulo += "i";
                }
                if (c >= 242 && c <= 246)	// o
                {
                    newtitulo += "o";
                }
                if (c >= 249 && c <= 252)	// u
                {
                    newtitulo += "u";
                }
                if (c >= 192 && c <= 197)	// A
                {
                    newtitulo += "A";
                }
                if (c >= 200 && c <= 203)	// E
                {
                    newtitulo += "E";
                }
                if (c >= 204 && c <= 207)	// I
                {
                    newtitulo += "I";
                }
                if (c >= 210 && c <= 214)	// O
                {
                    newtitulo += "O";
                }
                if (c >= 217 && c <= 220)	// U
                {
                    newtitulo += "U";
                }
                else
                {
                    newtitulo += "";
                }
            }
            return newtitulo;
        }

        private void SelectWebPageID_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxID.Text))
            {
                String title = (String)Wizard.Data[TitleAndDecriptionCratePage.TITLE];
                this.textBoxID.Text = getId(title);
            }
        }        

        private void textBoxID_KeyPress(object sender, KeyPressEventArgs e)
        {
            int ichar = e.KeyChar;
            if (ichar == 8)
            {
                return;
            }
            if (ichar >= 65 && ichar <= 90) // A-Z
            {
                return;
            }
            if (ichar >= 48 && ichar <= 57) // 0 - 9
            {
                return;
            }
            if (ichar >= 97 && ichar <= 122) // a - z
            {
                return;
            }
            if (e.KeyChar == '_')
            {
                return;
            }
            e.Handled = true; 

        }

    }
}
