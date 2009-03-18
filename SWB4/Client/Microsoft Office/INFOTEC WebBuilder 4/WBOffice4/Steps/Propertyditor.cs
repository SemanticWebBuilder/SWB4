using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Windows.Forms.PropertyGridInternal;
namespace WBOffice4.Steps
{
    public partial class Propertyditor : UserControl
    {
        PropertyGrid propertyGrid;
        public Propertyditor()
        {
            InitializeComponent();
            propertyGrid = new PropertyGrid();
            this.components.Add(propertyGrid);
            propertyGrid.Dock = DockStyle.Fill;            

        }
        
    }
    

   
}
