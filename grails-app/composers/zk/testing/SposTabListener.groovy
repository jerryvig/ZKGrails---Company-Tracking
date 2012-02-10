package zk.testing

import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.Event
import org.zkoss.zkgrails.*
import org.zkoss.zul.Listitem
import org.zkoss.zul.Listcell
import org.zkoss.zul.Listhead
import org.zkoss.zul.Listheader
import groovy.sql.Sql

public class SposTabListener implements EventListener {
      def sposQuery
      def sqlTextBox
      def columnListBox
      def sql = Sql.newInstance("jdbc:oracle:thin:morningstar/uptime5@localhost:1521:XE","morningstar","uptime5","oracle.jdbc.OracleDriver")

      public SposTabListener( def _sposQuery, def _sqlTextBox, _colListBox ) {
         sposQuery = _sposQuery
         sqlTextBox = _sqlTextBox
         columnListBox = _colListBox
      }

      public void onEvent( Event event ) {
         sqlTextBox.setValue( sposQuery )

         while ( columnListBox.getFirstChild() != null ) {
          columnListBox.removeChild( columnListBox.getFirstChild() )
         }

         Listheader lh1 = new Listheader("Column")
         Listheader lh2 = new Listheader("Description")
         lh1.setStyle("font-weight:bold;")
         lh2.setStyle("font-weight:bold;")
         Listhead listHead = new Listhead()
         listHead.appendChild( lh1 )
         listHead.appendChild( lh2 )
         columnListBox.appendChild( listHead )

         sql.eachRow( "SELECT * FROM nasdaq_ipos_desc" ) {
          Listitem myItem = new Listitem()
          Listcell cell1 = new Listcell( it.column_name )
          Listcell cell2 = new Listcell( it.description )
          cell1.setStyle("font-weight:bold;")
          cell2.setStyle("font-weight:bold;")
          myItem.appendChild( cell1 )
          myItem.appendChild( cell2 )
          columnListBox.appendChild( myItem )
        }
      }
}
