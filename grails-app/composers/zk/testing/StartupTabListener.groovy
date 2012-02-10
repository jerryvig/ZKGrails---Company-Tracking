package zk.testing

import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.Event
import org.zkoss.zkgrails.*
import org.zkoss.zk.ui.util.Clients

public class StartupTabListener implements EventListener {
      def startupQuery
      def sqlTextBox

      public StartupTabListener( def _startupQuery, def _sqlTextBox ) {
         startupQuery = _startupQuery
         sqlTextBox = _sqlTextBox
      }

      public void onEvent( Event event ) {
         sqlTextBox.setValue( startupQuery ) 
      }
}
