using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;

namespace Editor
{
    partial class PropertyEditor : UserControl
    {
        private PropertyInfo[] properties=null;        
        public PropertyEditor()
        {
            InitializeComponent();
        }
        public String[] Values
        {
            get
            {
                if (properties != null && properties.Length>0)
                {
                    String[] values = new String[properties.Length];
                    int i = 0;
                    foreach (PropertyInfo prop in this.properties)
                    {
                        Panel pcontrol = (Panel)this.paneProperties.Controls[prop.id];
                        Control control = pcontrol.Controls[prop.id];
                        if (control is TextBox)
                        {
                            values[i] = ((TextBox)control).Text;
                        }
                        else if (control is CheckBox)
                        {
                            values[i] = ((CheckBox)control).Checked.ToString();
                        }
                        else if (control is ComboBox)
                        {
                            values[i] = ((Value)((ComboBox)control).SelectedItem).key;
                        }
                        else if (control is NumericUpDown)
                        {
                            values[i] = ((NumericUpDown)control).Value.ToString();
                        }
                        i++;
                    }
                    return values;
                }
                else
                {
                    return new String[0];
                }
            }
            set
            {
                if (this.properties != null)
                {
                    String[] values = value;
                    int i = 0;
                    foreach (PropertyInfo prop in this.properties)
                    {
                        if (values[i] != null)
                        {
                            Panel pcontrol = (Panel)this.paneProperties.Controls[prop.id];
                            Control control = pcontrol.Controls[prop.id];
                            if (control is TextBox)
                            {
                                ((TextBox)control).Text = values[i].ToString();
                            }
                            else if (control is CheckBox)
                            {
                                ((CheckBox)control).Checked = bool.Parse(values[i].ToString());
                            }
                            else if (control is NumericUpDown)
                            {
                                ((NumericUpDown)control).Value = int.Parse(values[i].ToString());
                            }
                            else if (control is ComboBox)
                            {
                                foreach (Value o_value in prop.values)
                                {
                                    if (o_value.key.Equals(values[i]))
                                    {
                                        ((ComboBox)control).SelectedItem = o_value;
                                    }
                                }
                            }
                        }
                        i++;
                    }
                }
            }
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
                DrawProperties();
            }
        }
        private void DrawProperties()
        {            
            if (this.properties != null && this.properties.Length>0)
            {
                this.paneProperties.Controls.Clear();
                Array.Reverse(this.properties);
                int i = 0;
                foreach (PropertyInfo prop in this.properties)
                {
                    Panel plabelspace = new Panel();
                    plabelspace.Height = 10;
                    plabelspace.Dock = DockStyle.Top;


                    Panel plabel = new Panel();
                    plabel.Name = prop.id;
                    plabel.Height = 20;
                    plabel.Dock = DockStyle.Top;

                    Control box = getControl(prop);
                    toolTip1.SetToolTip(box, prop.title);
                    box.Name = prop.id;
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
                Array.Reverse(this.properties);
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
                    return CheckBox;
                }
                if (prop.type.Equals("string", StringComparison.InvariantCultureIgnoreCase))
                {
                    if (prop.values == null)
                    {
                        TextBox TextBox=new TextBox();
                        return TextBox;
                    }
                    else
                    {
                        ComboBox ComboBox=new ComboBox();                        
                        ComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
                        foreach (Value value in prop.values)
                        {
                            ComboBox.Items.Add(value);
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
                    if (prop.values == null)
                    {
                        NumericUpDown NumericUpDown = new NumericUpDown();
                        return NumericUpDown;
                    }
                    else
                    {
                        ComboBox ComboBox = new ComboBox();
                        ComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
                        foreach (Value value in prop.values)
                        {
                            ComboBox.Items.Add(value);
                        }
                        if (ComboBox.Items.Count > 0)
                        {
                            ComboBox.SelectedIndex = 0;
                        }
                        return ComboBox;
                    }                    
                }
            }
            return new Label();
        }

        
    }
    
}
