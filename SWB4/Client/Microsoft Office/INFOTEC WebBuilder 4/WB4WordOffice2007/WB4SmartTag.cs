using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Office.Tools.Word;
using System.Diagnostics;
using System.Windows.Forms;
using Microsoft.Office.Interop.SmartTag;
using WBOffice4.Interfaces;
using WBOffice4.Proxy;
using System.Resources;
using System.IO;
using System.Reflection;
namespace WB4WordOffice2007
{
    public class WB4SmartTag : Microsoft.Office.Tools.Word.SmartTag
    {
        private static String[] norelevant;
        public WB4SmartTag()
            : base("http://www.semanticwb.org/smarttags#demo", "Smart tags for SWB4")
        {
            //Terms.Add("Empresa");
            Microsoft.Office.Tools.Word.Action action1 = new Microsoft.Office.Tools.Word.Action("Objetos encontrados");
            action1.Click += new ActionClickEventHandler(showInfo_click);
            Actions = new  Microsoft.Office.Tools.Word.Action[] { action1 };
            List<String> values = new List<string>();
            ResourceManager rs = new ResourceManager(typeof(Resource));
            String data =(String)rs.GetObject("nonrelevant_es");
            StringReader sr = new StringReader(data);
            String word=sr.ReadLine();
            
            while (word != null)
            {
                word = word.Trim();
                if (!word.StartsWith("#"))
                {
                    values.Add(word);
                }
                word = sr.ReadLine();
            }
            norelevant = values.ToArray();

        }
        public void showInfo_click(Object obj, ActionEventArgs args)
        {
            
            //String token=args.Properties.get_Read("token");
            String token = args.Text;
            if (token != null && token!="")
            {
                FormInformation frm = new FormInformation(SmartTagProxy.SmartTag.search(token));
                frm.Show();
            }
        }
        protected override void Recognize(string text, Microsoft.Office.Interop.SmartTag.ISmartTagRecognizerSite site, Microsoft.Office.Interop.SmartTag.ISmartTagTokenList tokenList)
        {
            /*int pos = text.IndexOf((char)65532);
            if (pos != -1)
            {
                text = text.Substring(0, pos);
            }
            if (text==null || text == "")
            {
                return;
            }
            if (isToken(text))
            {
                String[] tokens = SmartTagProxy.SmartTag.getTokens(text);
                ISmartTagProperties propertyBag = site.GetNewPropertyBag();
                foreach(String stoken in tokens)
                {
                    int startIndex = text.IndexOf(stoken);
                    int endIndex = startIndex + stoken.Length;
                    Terms.Add(stoken);                    
                    propertyBag.Write("token", stoken);
                    this.PersistTag(startIndex, endIndex, propertyBag);                                        
                }
            }*/

            ISmartTagProperties propertyBag = site.GetNewPropertyBag();
            for (int i = 1; i <= tokenList.Count; i++)
            {
                ISmartTagToken token = tokenList.get_Item(i);
                if (isToken(token.Text))
                {
                    string stoken = token.Text;
                    int startIndex = text.IndexOf(stoken);
                    int endIndex = startIndex + stoken.Length;

                    propertyBag.Write("token", stoken);
                    this.Terms.Add(stoken);
                }
            }        
            
        }
        private bool isRelevantWord(String token)
        {
            bool isRelevantWord = true;
            if (token.Length == 1)
            {
                return false;
            }
            foreach (String prep in norelevant)
            {
                if (prep.Equals(token,StringComparison.InvariantCultureIgnoreCase)) {
                    return false;
                }
            }
            return isRelevantWord;
        }
        private bool isToken(String text)
        {
            if (text!=null && text.Trim() == "")
            {
                return false;
            }
            text = text.Trim();
            if(isRelevantWord(text))
            {
                return SmartTagProxy.SmartTag.isSmartTag(text);
            }
            return false;
        }
        private bool hasToken(String text)
        {            
            if(text=="")
            {
                return false;
            }
            return SmartTagProxy.SmartTag.isSmartTag(text);            
        }
    }
}
