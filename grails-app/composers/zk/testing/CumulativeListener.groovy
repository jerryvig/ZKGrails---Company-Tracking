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

public class CumulativeListener extends ColumnListener { 

  def CumulativeListener( def _tableRows ) {
    tableRows = _tableRows
    sortDirection = "DESC"
    query = "SELECT * FROM (SELECT * FROM quantcast_report ORDER BY cumulative_growth "+sortDirection+") WHERE rownum<251"
  }

  public void onEvent( Event event ) {
    clearTableRows()
 
    query = "SELECT * FROM (SELECT * FROM quantcast_report ORDER BY cumulative_growth "+sortDirection+") WHERE rownum<251"

    sql.eachRow( query ) {   
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
       tableRows.appendChild( nextRow )
    }

    resetSortDirection()
  }
}