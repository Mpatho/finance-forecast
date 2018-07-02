package psybergate.grad2018.javafnds.finance.controller;

import java.util.Map;

public interface ForecastController {

	String save(Map<String, String[]> request, Map<String, Object> response);

	String delete(Map<String, String[]> request, Map<String, Object> response);

	String forecast(Map<String, String[]> request, Map<String, Object> response);

}