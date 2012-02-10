package zk.testing

import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.Event
import org.zkoss.zkgrails.*
import org.zkoss.zk.ui.util.Clients
import org.zkoss.zul.Listitem
import org.zkoss.zul.Listcell
import org.zkoss.zul.Listhead
import org.zkoss.zul.Listheader
import groovy.sql.Sql

public class IposTabListener implements EventListener {
      def iposQuery     
      def sqlTextBox
      def columnListBox
      def sql = Sql.newInstance("jdbc:oracle:thin:morningstar/uptime5@localhost:1521:XE","morningstar","uptime5","oracle.jdbc.OracleDriver")

      public IposTabListener( def _iposQuery, def _sqlTextBox, def _colListBox ) {
         iposQuery = _iposQuery
         sqlTextBox = _sqlTextBox
         columnListBox = _colListBox
      }

      public void onEvent( Event event ) {
        sqlTextBox.setValue( iposQuery )
        
        while ( columnListBox.getFirstChild() != null ) {
          columnListBox.removeChild( columnListBox.getFirstChild() )
        }

        def lh1 = new Listheader("Column")
        def lh2 = new Listheader("Description")
        lh1.setStyle("font-weight:bold;")
        lh2.setStyle("font-weight:bold;")
        def listHead = new Listhead()
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
