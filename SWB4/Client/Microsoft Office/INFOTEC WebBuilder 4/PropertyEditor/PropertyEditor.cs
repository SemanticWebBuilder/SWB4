using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace PropertyEditor
{
    public partial class PropertyEditor : UserControl
    {
        PropertyInfo[] properties;
        Object[] values;
        public PropertyEditor()
        {
            InitializeComponent();
        }
        public PropertyEditor(PropertyInfo[] properties)
        {
            InitializeComponent();
        }
        public PropertyInfo[] Properties
        {
            get
            {
                return properties;
            }
            set
            {
                this.properties = value;
                drawProperties();
            }
        }
        private void drawProperties()
        {
            //this.paneProperties.Controls.Clear();
            if (this.properties != null)
            {

                int i = 0;
                foreach (PropertyInfo prop in this.properties)
                {
                    Panel plabelspace = new Panel();
                    plabelspace.Height = 10;
                    plabelspace.Dock = DockStyle.Top;


                    Panel plabel = new Panel();                    
                    plabel.Height = 20;
                    plabel.Dock = DockStyle.Top;
                    //plabel.BorderStyle = BorderStyle.FixedSingle;


                    Control box = getControl(prop);
                    box.TabIndex = i;
                    box.Height = 20;
                    box.Dock = DockStyle.Fill;                    
                    box.BackColor = Color.White;
                    plabel.Controls.Add(box);

                    Label label2 = new Label();
                    label2.Width = 20;
                    label2.AutoSize = false;
                    label2.Dock = DockStyle.Left;                    
                    plabel.Controls.Add(label2);
                    
                                        
                    Label label = new Label();
                    toolTip1.SetToolTip(label, prop.title);
                    //label.BorderStyle = BorderStyle.FixedSingle;
                    label.Height = 20;
                    label.Width = 200;
                    label.TextAlign = ContentAlignment.MiddleLeft;
                    label.Dock = DockStyle.Left;
                    label.AutoSize = false;                    
                    label.Text = prop.title;
                    label.AutoEllipsis = true;
                    plabel.Controls.Add(label);

                    


                    
                    i++;                    
                    this.paneProperties.Controls.Add(plabel);
                    this.paneProperties.Controls.Add(plabelspace);                    
                }
            }
        }
        private Control getControl(PropertyInfo prop)
        {
            if (prop.type != null)
            {
                if (prop.type.Equals("boolean", StringComparison.InvariantCultureIgnoreCase))
                {
                    CheckBox CheckBox=new CheckBox();
                    CheckBox.CheckAlign = ContentAlignment.MiddleCenter;
                    //CheckBox.FlatStyle = FlatStyle.Flat;
                    return CheckBox;
                }
                if (prop.type.Equals("string", StringComparison.InvariantCultureIgnoreCase))
                {
                    if (prop.values == null)
                    {
                        TextBox TextBox=new TextBox();
                        //TextBox.BorderStyle = BorderStyle.None;                        
                    }
                    else
                    {
                        ComboBox ComboBox=new ComboBox();
                        //ComboBox.FlatStyle = FlatStyle.Flat;
                        ComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
                        foreach (Object value in prop.values)
                        {
                            ComboBox.Items.Add(new KeyValue(value.ToString()));
                        }
                        if (ComboBox.Items.Count > 0)
                        {
                            ComboBox.SelectedIndex = 0;
                        }
                        return ComboBox;
                    }
                    
                }
                if (prop.type.Equals("integer", StringComparison.InvariantCultureIgnoreCase))
                {
                    return new NumericUpDown();
                }
            }
            return new Label();
        }

        private void paneProperties_Resize(object sender, EventArgs e)
        {
            //this.drawProperties();
        }
    }
    internal class KeyValue
    {
        private String m_key,m_value;
        public KeyValue(String key, String value)
        {
            this.m_key = key;
            this.m_value = value;
        }
        public KeyValue(String key_value)
        {
            String[] values=key_value.Split(new char[] { ':' });
            if (values.Length == 2)
            {
                m_key = values[0];
                m_value = values[1];
            }
        }
        public String Key
        {
            get
            {
                return m_key;
            }
        }
        public String Value
        {
            get
            {
                return m_value;
            }
        }
        public override string ToString()
        {
            return m_value.ToString();
        }
    }
}
