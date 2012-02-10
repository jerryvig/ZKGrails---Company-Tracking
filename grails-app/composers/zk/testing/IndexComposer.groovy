package zk.testing

import org.zkoss.zkgrails.*
import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import org.zkoss.zul.A
import groovy.sql.Sql
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class IndexComposer extends GrailsComposer {
    def startupRows
    def ipoRows
    def spoRows
    def sqlTextBox
    def startupsTab
    def iposTab
    def sposTab
    def sql = Sql.newInstance("jdbc:oracle:thin:morningstar/uptime5@localhost:1521:XE","morningstar","uptime5","oracle.jdbc.OracleDriver")
    def dateFormatter = new SimpleDateFormat("MMM-yy")
    def integerFormatter = new DecimalFormat("#,###")
    def pctFormatter = new DecimalFormat("#,###.00%")
    
    def startupQuery = "SELECT * FROM (SELECT * FROM second_market_fact_table WHERE ( state='CA' ) ORDER BY last_funding_date DESC, last_funding_amount DESC) WHERE rownum<251"
    def iposQuery = "SELECT * FROM nasdaq_ipos WHERE date_priced>='01-JUL-11' ORDER BY date_priced DESC, company_name ASC"
    def sposQuery = "SELECT * FROM nasdaq_spos WHERE date_priced>='01-JUL-11' ORDER BY date_priced DESC, company_name ASC"

    def afterCompose = { window ->
      // initialize components here
      initStartupRows()
      initIpoRows()
      initSpoRows()
      sqlTextBox.setValue( startupQuery )
      addEventListeners()
    }

    def initStartupRows() {
      sql.eachRow( startupQuery ) { 
         def myRow = new Row()
         def cellList = []         
         cellList.add( new Label( it.company_name ) )
         cellList.add( new Label( it.city ) )
         cellList.add( new Label( it.state ) )
         def secondMktA = new A( it.second_market_url )
         secondMktA.setHref( it.second_market_url )
         secondMktA.setTarget("_blank")
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
           cellList.add( new Label( pctFormatter.format(it.visitor_growth) ) )
         } else { cellList.add( new Label() ) }

         cellList.each { cell ->
           cell.setStyle("font-weight:bold;")
           myRow.appendChild( cell )
         }
        
         startupRows.appendChild( myRow )
      }
    }

    def initIpoRows() {
      sql.eachRow( iposQuery ) {
        def myRow = new Row()
        def cellList = []
        cellList.add( new Label( it.type ) )
        cellList.add( new Label( it.company_name ) )
        cellList.add( new Label( it.ticker_symbol ) )
        cellList.add( new Label( it.market ) )
        cellList.add( new Label( it.price ) )        
        if ( it.shares != null ) {
           cellList.add( new Label( integerFormatter.format( Integer.parseInt(it.shares) ) ) )
        } else {
           cellList.add( new Label() )
        }
        cellList.add( new Label( integerFormatter.format(it.offer_amount) ) )
        cellList.add( new Label( dateFormatter.format(it.date_priced) ) )

        cellList.each { cell ->
           cell.setStyle("font-weight:bold;")
           myRow.appendChild( cell )
        }

        ipoRows.appendChild( myRow ) 
      }
    }

    def initSpoRows() {
      sql.eachRow( sposQuery ) {
        def myRow = new Row()
        def cellList = []
        cellList.add( new Label( it.type ) )
        cellList.add( new Label( it.company_name ) )
        cellList.add( new Label( it.ticker_symbol ) )
        cellList.add( new Label( it.market ) )
        cellList.add( new Label( it.price ) )
        if ( it.shares != null ) {
          cellList.add( new Label( integerFormatter.format( Integer.parseInt(it.shares) ) ) )
        } else { cellList.add( new Label() ) }
        cellList.add( new Label( integerFormatter.format(it.offer_amount) ) )
        cellList.add( new Label( dateFormatter.format(it.date_priced) ) )

        cellList.each { cell ->
          cell.setStyle("font-weight:bold;")
          myRow.appendChild( cell ); }

         spoRows.appendChild( myRow )
      }
    }

    def addEventListeners () {
       startupsTab.addEventListener("onSelect",new StartupTabListener( startupQuery, sqlTextBox ))
       iposTab.addEventListener("onSelect",new IposTabListener( iposQuery, sqlTextBox ))
       sposTab.addEventListener("onSelect",new SposTabListener( sposQuery, sqlTextBox ))
    }
}
