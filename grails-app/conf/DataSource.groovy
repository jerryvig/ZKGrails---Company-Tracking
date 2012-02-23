dataSource {
    pooled = true
    dbCreate = "update"
    driverClassName = "oracle.jdbc.OracleDriver"
    url = "jdbc:oracle:thin:morningstar/uptime5@localhost:1521:XE"
    username = "morningstar"
    password = "uptime5"
    dialect = org.hibernate.dialect.Oracle10gDialect
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
