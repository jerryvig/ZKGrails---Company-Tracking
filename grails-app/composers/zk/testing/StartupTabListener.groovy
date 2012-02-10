package zk.testing

import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.Event
import org.zkoss.zkgrails.*
import org.zkoss.zul.Listitem
import org.zkoss.zul.Listcell
import org.zkoss.zul.Listhead
import org.zkoss.zul.Listheader
import groovy.sql.Sql

public class StartupTabListener implements EventListener {
      def startupQuery
      def sqlTextBox
      def columnListBox
      def sql = Sql.newInstance("jdbc:oracle:thin:morningstar/uptime5@localhost:1521:XE","morningstar","uptime5","oracle.jdbc.OracleDriver")

      public StartupTabListener( def _startupQuery, def _sqlTextBox, def _colListBox ) {
         startupQuery = _startupQuery
         sqlTextBox = _sqlTextBox
         columnListBox = _colListBox
      }

      public void onEvent( Event event ) {
        sqlTextBox.setValue( startupQuery )

        while ( columnListBox.getFirstChild() != null ) {
           columnListBox.removeChild( columnListBox.getFirstChild() )
        }
 
        Listheader lh1 = new Listheader("Column")
        Listheader lh2 = new Listheader("Description")
        lh1.setStyle("font-weight:bold;")
        lh2.setStyle("font-weight:bold;")
        Listhead lh = new Listhead()
        lh.appendChild( lh1 )
        lh.appendChild( lh2 )
        columnListBox.appendChild( lh )

        sql.eachRow( "SELECT * FROM second_market_fact_desc" ) {
         def myListitem = new Listitem()
         def cell1 = new Listcell( it.column_name )
         cell1.setStyle("font-weight:bold;")
         def cell2 = new Listcell( it.description )
         cell2.setStyle("font-weight:bold;")
         myListitem.appendChild( cell1 )
         myListitem.appendChild( cell2 )
         columnListBox.appendChild( myListitem )
       }
      }
}
