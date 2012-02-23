package zk.testing

import java.sql.Date

class EODDatum {
    Date eodDate 
    String tickerSymbol
    Double close
    Double adjClose
    Double dayChange

    static constraints = {
    }

    static mapping = {
      table 'CBOE_WEEKLIES_RETURNS'
      eodDate column: 'EOD_DATE'
      tickerSymbol column: 'TICKER_SYMBOL'
      close column: 'CLOSE'
      adjClose column: 'ADJ_CLOSE'
      dayChange column: 'DAY_CHANGE'
      id column: 'IDX'
    }
}
