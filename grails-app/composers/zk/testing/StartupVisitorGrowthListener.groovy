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

public class StartupVisitorGrowthListener extends ColumnListener { 
  def fullDateFormatter = new SimpleDateFormat("MM/dd/yyyy")

  def StartupVisitorGrowthListener( def _tableRows ) {
    tableRows = _tableRows
    sortDirection = "DESC"
    query = "SELECT * FROM (SELECT * FROM second_market_fact_table WHERE ( state='CA' AND visitor_growth IS NOT NULL ) ORDER BY visitor_growth "+sortDirection+") where rownum<251"
  }

  public void onEvent( Event event ) {
    clearTableRows()
 
    query = "SELECT * FROM (SELECT * FROM second_market_fact_table WHERE ( state='CA' AND visitor_growth IS NOT NULL ) ORDER BY visitor_growth "+sortDirection+") where rownum<251"

    sql.eachRow( query ) {   
       def cellList = []
       cellList.add( new Label( it.company_name ) )
       cellList.add( new Label( it.city ) )
       cellList.add( new Label( it.state ) )
       def secondMktA = new A( it.second_market_url )
       secondMktA.setHref( it.second_market_url )
       secondMktA.setTarget( "_blank" )
       cellList.add( secondMktA )
       def companyA = new A( it.company_url )
       companyA.setHref( it.company_url )
       companyA.setTarget("_blank")
       cellList.add( companyA )
       cellList.add( new Label( dateFormatter.format(it.last_funding_date) ) )
       cellList.add( new Label( integerFormatter.format(it.last_funding_amount) ) )
       if ( it.unique_visitors != null ) {
          cellList.add( new Label( integerFormatter.format(it.unique_visitors) ) )
       } else { cellList.add( new Label() ) }
       if ( it.visitor_growth != null ) {
         def visitorGrowthA = new A( pctFormatter.format(it.visitor_growth) )
         visitorGrowthA.setTarget("_blank")
         if ( it.company_url != null ) {
           visitorGrowthA.setHref("http://siteanalytics.compete.com/" + it.company_url.replace("http://","").replace("https://","") )
         } else { visitorGrowthA.setHref("") }
         cellList.add( visitorGrowthA )
       } else { cellList.add( new Label() ) }

       def nextRow = new Row()
       cellList.each { cell ->
         cell.setStyle("font-weight:bold;")
         nextRow.appendChild( cell )
       }
       tableRows.appendChild( nextRow )
    }

    resetSortDirection()
  }
}