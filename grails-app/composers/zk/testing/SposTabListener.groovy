package zk.testing

import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.Event
import org.zkoss.zkgrails.*
import org.zkoss.zul.Listitem
import org.zkoss.zul.Listcell
import org.zkoss.zul.Listhead
import org.zkoss.zul.Listheader
import groovy.sql.Sql

public class SposTabListener extends TabListener {
      def descQuery = "SELECT * FROM nasdaq_ipos_desc"

      public SposTabListener( def _query, def _sqlTextBox, def _colListBox ) {
        super( _query, _sqlTextBox, _colListBox )
      }
      
      public void onEvent( Event event ) {
         sqlTextBox.setValue( query )
         clearDescTable()
         renderDescTable( descQuery )
      }
}
