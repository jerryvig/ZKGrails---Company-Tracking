package zk.testing

import java.sql.Date

class SecondMarketFacts {
    String companyName
    String city
    String state
    String secondMarketUrl
    String companyUrl
    Date lastFundingDate
    Double lastFundingAmount
    Date maxMonth
    Date minMonth
    Integer uniqueVisitors
    Double visitorGrowth

    static mapping = {
      table 'SECOND_MARKET_FACT_TABLE'
      companyName column: 'COMPANY_NAME'
      city column: 'CITY'
      state column: 'STATE'
      secondMarketUrl column: 'SECOND_MARKET_URL'
      companyUrl column: 'COMPANY_URL'
      lastFundingDate column: 'LAST_FUNDING_DATE'
      lastFundingAmount column: 'LAST_FUNDING_AMOUNT'
      maxMonth column: 'MAX_MONTH'
      minMonth column: 'MIN_MONTH'
      uniqueVisitors column: 'UNIQUE_VISITORS'
      visitorGrowth column: 'VISITOR_GROWTH'
    }

    static constraints = {
    }
}
