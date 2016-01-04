package com.tohours.imo;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@SetupBy(value = MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={"*js","dao.js",
	   "*anno", "com.tohours.imo",
	   "*tx"})
@Modules(scanPackage = true)
@Ok("json:full")
@Fail("jsp:jsp.500")
public class MainModule {
	// org.nutz.ioc.impl.
}