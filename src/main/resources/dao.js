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

		type : "javax.sql.DataSource",

		args : [

		/** jndi name */

		{java :"$config.get('connection.datasource')"} ],

		factory : "com.tohours.imo.util.DataSourceFactory#born"

	},

	dao : {

		type : "org.nutz.dao.impl.NutDao",

		args : [ {

			refer : "dataSource"

		} ]

	}

};