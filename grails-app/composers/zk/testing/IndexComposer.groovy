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
    def competeRows
    def sqlTextBox
    def startupsTab
    def iposTab
    def sposTab
    def columnListBox
    def uniqueVisitorsColumn
    def threeMonthColumn
    def cumulativeColumn
    def sharpeColumn

    def sql = Sql.newInstance("jdbc:oracle:thin:morningstar/uptime5@localhost:1521:XE","morningstar","uptime5","oracle.jdbc.OracleDriver")
    def dateFormatter = new SimpleDateFormat("MMM-yy")
    def fullDateFormatter = new SimpleDateFormat("MM/dd/yyyy")
    def integerFormatter = new DecimalFormat("#,###")
    def pctFormatter = new DecimalFormat("#,###.00%")
    
    def startupQuery = "SELECT * FROM (SELECT * FROM second_market_fact_table WHERE ( state='CA' ) ORDER BY last_funding_date DESC, last_funding_amount DESC) WHERE rownum<251"
    def iposQuery = "SELECT type, company_name, company_url, ticker_symbol_ii, offer_amount, date_priced, nasdaq_profile_url, city, state FROM nasdaq_ipos WHERE state='CA' ORDER BY date_priced DESC, offer_amount DESC";
    def sposQuery = "SELECT type, company_name, company_url, ticker_symbol, offer_amount, date_priced, nasdaq_profile_url, city, state FROM nasdaq_spos WHERE state='CA' ORDER BY date_priced DESC, offer_amount DESC";
    def competeQuery = "SELECT * FROM (SELECT * FROM quantcast_report ORDER BY three_month_change DESC) WHERE rownum<251"

    def afterCompose = { window ->
      // initialize components here
      initStartupRows()
      initIpoRows()
      initSpoRows()
      initColumnListBox()
      initCompeteWebTrafficRows()
      sqlTextBox.setValue( startupQuery )
      initEventListeners()
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
        def companyA = new A( it.company_name )
        companyA.setHref( "http://" + it.company_url )
        companyA.setTarget("_blank") 
        cellList.add( companyA )
        cellList.add( new Label( it.ticker_symbol_ii ) )
        cellList.add( new Label( integerFormatter.format(it.offer_amount) ) )
        cellList.add( new Label( fullDateFormatter.format(it.date_priced) ) )
        def nasdaqA = new A( it.nasdaq_profile_url )
        nasdaqA.setHref( it.nasdaq_profile_url )
        nasdaqA.setTarget("_blank")
        cellList.add( nasdaqA )
        cellList.add( new Label( it.city ) )
        cellList.add( new Label( it.state ) )

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

        cellList.each { cell ->
          cell.setStyle("font-weight:bold;")
          myRow.appendChild( cell );
        }

         spoRows.appendChild( myRow )
      }
    }

    def initCompeteWebTrafficRows() {
      sql.eachRow( competeQuery ) {
        def nextRow = new Row()
        def cells = []
        def domainA = new A( it.domain )
        domainA.setHref( "http://" + it.domain )
        domainA.setTarget("_blank")
        cells.add( domainA )
        def competeA = new A( dateFormatter.format(it.last_month) )
        competeA.setHref( "http://siteanalytics.compete.com/" + it.domain + "/" )
        competeA.setTarget("_blank")      
        cells.add( competeA )

        cells.add( new Label( integerFormatter.format(it.unique_visitors) ) )
        cells.add( new Label( pctFormatter.format(it.three_month_change) ) )
        cells.add( new Label( pctFormatter.format(it.cumulative_growth) ) )
        cells.add( new Label( pctFormatter.format(it.sharpe_growth) ) )

        cells.each { cell ->
          cell.setStyle("font-weight:bold;")
          nextRow.appendChild( cell )
        }
        competeRows.appendChild( nextRow )
      }
    }

    def initColumnListBox () {
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

    def initEventListeners () {
       startupsTab.addEventListener("onSelect",new StartupTabListener( startupQuery, sqlTextBox, columnListBox ))
       iposTab.addEventListener("onSelect",new IposTabListener( iposQuery, sqlTextBox, columnListBox ))
       sposTab.addEventListener("onSelect",new SposTabListener( sposQuery, sqlTextBox, columnListBox ))
       uniqueVisitorsColumn.addEventListener("onClick",new UniqueVisitorsListener( competeRows ))
       threeMonthColumn.addEventListener("onClick",new ThreeMonthListener( competeRows ))
       cumulativeColumn.addEventListener("onClick",new CumulativeListener( competeRows ))
       sharpeColumn.addEventListener("onClick",new SharpeListener( competeRows ))
    }
}
