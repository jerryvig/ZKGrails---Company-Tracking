package zk.testing

import java.text.DecimalFormat
import org.zkoss.zkgrails.*
import org.zkoss.zul.Row
import org.zkoss.zul.Label
import groovy.sql.Sql

class DividendsComposer extends GrailsComposer {
    def dividendRows
    def cboeWeeklyRows
    def sql = Sql.newInstance("jdbc:oracle:thin:morningstar/uptime5@localhost:1521:XE","morningstar","uptime5","oracle.jdbc.OracleDriver")
    def dividendQuery = "SELECT * FROM (SELECT * FROM dividend_yields ORDER BY dividend_yield DESC) WHERE rownum<251"
    def pctFormatter = new DecimalFormat("#,###.00%");

    def afterCompose = { window ->
      // initialize components here
      initDividendRows()
      initCBOEWeeklyRows()
    }

    def initDividendRows() {
      sql.eachRow( dividendQuery ) {
        def cells = []
        cells.add( new Label( it.ticker_symbol ) )
        cells.add( new Label( Double.toString(it.sum_dividends) ) )
        cells.add( new Label( Double.toString(it.close) ) )
        cells.add( new Label( pctFormatter.format(it.dividend_yield) ) )

        def nextRow = new Row()
        cells.each { cell ->
          cell.setStyle("font-weight:bold;")
          nextRow.appendChild( cell )
        }
        dividendRows.appendChild( nextRow )
      }
    }

    def initCBOEWeeklyRows() {
       def stockList = Stock.list()
       stockList.each {
         def cells = []
         cells.add( new Label( it.tickerSymbol ) )
         cells.add( new Label( pctFormatter.format(it.avgReturn) ) )
         cells.add( new Label( Double.toString(it.sharpeRatio) ) )
         //cells.add( new Label( pctFormatter.format(it.avgRevenueGrowth) ) )
         // cells.add( new Label( Double.toString(it.sharpeRevenueGrowth) ) )

         def nextRow = new Row()
         cells.each { cell ->
           cell.setStyle("font-weight:bold;")
           nextRow.appendChild( cell )
         }
         cboeWeeklyRows.appendChild( nextRow )
       }
    }
}
