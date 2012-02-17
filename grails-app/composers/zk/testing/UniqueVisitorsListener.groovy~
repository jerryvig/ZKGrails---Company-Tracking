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

public class UniqueVisitorsListener implements EventListener {
  def uniqueSort = "DESC"  
  def competeRows;
  def sql = Sql.newInstance("jdbc:oracle:thin:morningstar/uptime5@localhost:1521:XE","morningstar","uptime5")
  def dateFormatter = new SimpleDateFormat("MMM-yy")
  def integerFormatter = new DecimalFormat("#,###")
  def pctFormatter = new DecimalFormat("#,###.00%")

  def UniqueVisitorsListener( def _competeRows ) {
    competeRows = _competeRows
  }

  public void onEvent( Event event ) {
    while ( competeRows.getFirstChild() != null ) {
       competeRows.removeChild( competeRows.getFirstChild() )
    }

    sql.eachRow( "SELECT * FROM (SELECT * FROM quantcast_report ORDER BY unique_visitors "+uniqueSort+") WHERE rownum<251" ) {   
       def cells = []
       def domainA = new A( it.domain )
       domainA.setHref( "http://" + it.domain )
       domainA.setTarget("_blank")
       cells.add( domainA )
       def competeA = new A( dateFormatter.format(it.last_month) )
       competeA.setHref( "http://siteanalytics.compete.com/" + it.domain + "/" )
       competeA.setTarget( "_blank" )
       cells.add( competeA )
       cells.add( new Label( integerFormatter.format(it.unique_visitors) ) )
       cells.add( new Label( pctFormatter.format(it.three_month_change) ) )
       cells.add( new Label( pctFormatter.format(it.cumulative_growth) ) )
       cells.add( new Label( pctFormatter.format(it.sharpe_growth) ) )

       def nextRow = new Row()
       cells.each { cell ->
         cell.setStyle("font-weight:bold;")
         nextRow.appendChild( cell )
       }
       competeRows.appendChild( nextRow )
    }

    if ( uniqueSort.equals("DESC") ) {
      uniqueSort = "ASC"
    }
    else {
      uniqueSort = "DESC"
    }
  }
}