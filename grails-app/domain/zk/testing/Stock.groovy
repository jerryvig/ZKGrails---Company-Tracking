package zk.testing

class Stock {
  String tickerSymbol
  //String companyName
 
  //Summary Stats
  Double avgReturn
  Double sharpeRatio
  Double avgRevenueGrowth
  Double sharpeRevenueGrowth
  //static hasOne = [avgRevenueGrowth: Double]
  //static hasOne = [sharpeRevenueGrowth: Double]

  //Time Series objects
  static hasMany = [eodData: EODDatum]
  static hasManyII = [revenueData: RevenueDatum]

  static constraints = {
  }

  static mapping = {
    sort sharpeRatio: 'DESC'
    table 'CBOE_WEEKLIES_SHARPE'
    tickerSymbol column: 'TICKER_SYMBOL'
    avgReturn column: 'AVG_RETURN'
    sharpeRatio column: 'SHARPE_RATIO'
    avgRevenueGrowth joinTable: [name: 'CBOE_SHARPE_REVG', key: 'TICKER_SYMBOL', column: 'AVG_REV_GROWTH', type: "DOUBLE"]
    sharpeRevenueGrowth joinTable: [name: 'CBOE_SHARPE_REVG', key: 'TICKER_SYMBOL', column: 'SHARPE_REV_GROWTH', type: "DOUBLE"]
    eodData joinTable: [name: 'CBOE_WEEKLIES_RETURNS', key: 'TICKER_SYMBOL', column: 'IDX']
    revenueData joinTable: [name: 'CBOE_REVENUE_GROWTH', key: 'TICKER_SYMBOL', column: 'IDX']
  }
}
