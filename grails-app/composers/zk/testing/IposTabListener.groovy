package zk.testing

import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.Event
import org.zkoss.zkgrails.*
import org.zkoss.zk.ui.util.Clients

public class IposTabListener implements EventListener {
      def iposQuery     
      def sqlTextBox

      public IposTabListener( def _iposQuery, def _sqlTextBox ) {
         iposQuery = _iposQuery
         sqlTextBox = _sqlTextBox
      }

      public void onEvent( Event event ) {
         sqlTextBox.setValue( iposQuery )
      }
}
