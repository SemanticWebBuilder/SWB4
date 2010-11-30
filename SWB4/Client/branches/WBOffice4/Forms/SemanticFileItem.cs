using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WBOffice4.Interfaces;
using System.Windows.Forms;
namespace WBOffice4.Forms
{
    public class SemanticFileItem : ListViewItem
    {
        private SemanticFileRepository semanticFileRepository;
        public SemanticFileItem(SemanticFileRepository semanticFileRepository)
        {
            this.semanticFileRepository = semanticFileRepository;
            this.SubItems[0].Text = semanticFileRepository.title;
            this.SubItems.Add(semanticFileRepository.date.ToString("dd/MM/yyyy HH:mm:ss"));            
        }
        public SemanticFileRepository SemanticFileRepository
        {
            get
            {
                return semanticFileRepository;
            }
        }
    }
}
