dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
//    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
}

// environment specific settings
environments {
    development {
        /*dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }*/
        dataSource {
            pooled = true
            dbCreate = "update"
            //url = "jdbc:mysql://localhost/films"
            url = "jdbc:mysql://localhost:3306/filmsdb"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "root"
            password = "caracol"
        }

    }
    test {
        def credentials = [
                hostname:System.getenv("OPENSHIFT_MYSQL_DB_HOST"),
                port:System.getenv("OPENSHIFT_MYSQL_DB_PORT"),
                username:System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"),
                password:System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD"),
                name:"APPLICATION_NAME"
        ]

        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://${credentials.hostname}:${credentials.port}/filmsdb"
            username = credentials.username
            password = credentials.password

            driverClassName = "com.mysql.jdbc.Driver"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect

            properties {
                maxActive = -1
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=false
                validationQuery="SELECT 1"
                jdbcInterceptors="ConnectionState"
            }
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=false
               validationQuery="SELECT 1"
               jdbcInterceptors="ConnectionState"
            }
        }
    }
}
