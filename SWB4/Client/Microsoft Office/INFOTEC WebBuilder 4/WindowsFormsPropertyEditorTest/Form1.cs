using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WindowsFormsPropertyEditorTest
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            int elements = 50;
            PropertyInfo[] props = new PropertyInfo[elements];
            for (int i = 0; i < elements; i++)
            {
                props[i] = new PropertyInfo();
                if (i % 4 == 0)
                {
                    props[i].type = "boolean";
                }
                if (i % 4 == 1)
                {
                    props[i].type = "string";
                }
                if (i % 4 == 2)
                {
                    props[i].type = "integer";
                }
                if (i % 4 == 3)
                {
                    props[i].type = "String";
                    props[i].values =new String[]{"1:A","2:B","3:C"};
                }
                props[i].title = "Propiedad amsms fsdf    fsdfsdf  " + i;
            }
            this.propertyEditor1.Properties = props;
        }
    }
}
