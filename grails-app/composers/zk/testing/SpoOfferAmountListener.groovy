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

public class SpoOfferAmountListener extends ColumnListener { 
  def fullDateFormatter = new SimpleDateFormat("MM/dd/yyyy")

  def SpoOfferAmountListener( def _tableRows ) {
    tableRows = _tableRows
    sortDirection = "DESC"
    query = "SELECT * FROM (SELECT type, company_name, company_url, ticker_symbol, offer_amount, date_priced, nasdaq_profile_url, city, state FROM nasdaq_spos WHERE state='CA' ORDER BY offer_amount "+sortDirection+") where rownum<251"
  }

  public void onEvent( Event event ) {
    clearTableRows()
 
    query = "SELECT * FROM (SELECT type, company_name, company_url, ticker_symbol, offer_amount, date_priced, nasdaq_profile_url, city, state FROM nasdaq_spos WHERE state='CA' ORDER BY offer_amount "+sortDirection+") where rownum<251"

    sql.eachRow( query ) {   
       def cellList = []
       cellList.add( new Label(it.type) )
       def companyA = new A( it.company_name )
       companyA.setHref( "http://" + it.company_url )
       companyA.setTarget("_blank")
       cellList.add( companyA )
       cellList.add( new Label( it.ticker_symbol ) )
       cellList.add( new Label( integerFormatter.format(it.offer_amount) ) )
       cellList.add( new Label( fullDateFormatter.format(it.date_priced) ) )
       def nasdaqA = new A( it.nasdaq_profile_url )
       nasdaqA.setHref( it.nasdaq_profile_url )
       nasdaqA.setTarget("_blank")
       cellList.add( nasdaqA )
       cellList.add( new Label( it.city ) )
       cellList.add( new Label( it.state ) )

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