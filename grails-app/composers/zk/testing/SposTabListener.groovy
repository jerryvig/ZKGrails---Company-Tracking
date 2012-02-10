package zk.testing

import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.Event
import org.zkoss.zkgrails.*
import org.zkoss.zk.ui.util.Clients

public class SposTabListener implements EventListener {
      def sposQuery
      def sqlTextBox

      public SposTabListener( def _sposQuery, def _sqlTextBox ) {
         sposQuery = _sposQuery
         sqlTextBox = _sqlTextBox
      }

      public void onEvent( Event event ) {
         sqlTextBox.setValue( sposQuery )
      }
}
