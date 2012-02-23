package zk.testing

class RevenueDatum {

    String tickerSymbol
    String period
    Double revenue
    Double revenueGrowth

    static constraints = {
    }

    static mapping = {
      table 'CBOE_REVENUE_GROWTH'
      tickerSymbol column: 'TICKER_SYMBOL'
      period column: 'PERIOD'
      revenue column: 'REVENUE'
      revenueGrowth column: 'REV_GROWTH'
      id column: 'IDX'
    }
}
