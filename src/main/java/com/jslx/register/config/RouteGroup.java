package com.jslx.register.config;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RouteGroup {
	@XmlAttribute
	String type;
	@XmlAttribute
	String pkge;
	
	@XmlElement(name = "route")
	List<RouteItem> routeItems = Lists.newArrayList();

	/*void addRoutegroup(RouteItem routeItem) {
		this.routeItems.add(routeItem);
	}*/
}
