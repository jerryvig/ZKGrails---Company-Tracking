package zk.testing

import java.text.SimpleDateFormat
import java.text.DecimalFormat
import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.Event
import org.zkoss.zkgrails.*
import groovy.sql.Sql
import org.zkoss.zul.Label
import org.zkoss.zul.A
import org.zkoss.zul.Row

public class ColumnListener implements EventListener {
  def sortDirection;  
  def tableRows;
  def query;
  def sql = Sql.newInstance("jdbc:oracle:thin:morningstar/uptime5@localhost:1521:XE","morningstar","uptime5");  
  def dateFormatter = new SimpleDateFormat("MMM-yy")
  def integerFormatter = new DecimalFormat("#,###")
  def pctFormatter = new DecimalFormat("#,###.00%")

  /* def ColumnListener( def _tableRows ) {
    tableRows = _tableRows
  } */

  public void clearTableRows() {
    while ( tableRows.getFirstChild() != null ) {
      tableRows.removeChild( tableRows.getFirstChild() )
    }
  }

  public void resetSortDirection() {
    if ( sortDirection.equals("DESC") ) {
      sortDirection = "ASC"
    }
    else {
      sortDirection = "DESC"
    }
  }

  public void onEvent( Event event ) {}
    
}