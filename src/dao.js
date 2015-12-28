/**
 * 
 */
var ioc = {
	config : {
		type : "com.tohours.imo.util.XmlProxy",
		fields : {
			path : "/hibernate.cfg.xml"
		}
	},
    dataSource : {
        type :"com.alibaba.druid.pool.DruidDataSource",
        events : {
            depose :"close"
        },
        fields : {
            driverClassName : {java :"$config.get('hibernate.connection.driver_class')"},
            url             : {java :"$config.get('hibernate.connection.url')"},
            username        : {java :"$config.get('hibernate.connection.username')"},
            password        : {java :"$config.get('hibernate.connection.password')"},
            initialSize     : 10,
            maxActive       : 100,
            testOnReturn    : true,
            //validationQueryTimeout : 5,
            validationQuery : "select 1"
        }
    },
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [ {
			refer : "dataSource"
		} ]
	}
};