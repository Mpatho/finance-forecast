package psybergate.grad2018.javafnds.finance.controller;

import java.util.Map;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;

@ManagedBean("Example")
public class Example {

	private static final Logger L = Logger.getLogger(Example.class);

	public String hello(Map<String, String[]> request, Map<String, Object> response) {
		L.debug("Exampe:" + this);
		response.put("name", "PsyTeam");
		return "/WEB-INF/views/hello.jsp";
	}
}
