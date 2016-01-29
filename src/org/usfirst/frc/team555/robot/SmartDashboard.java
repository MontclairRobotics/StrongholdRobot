package org.usfirst.frc.team555.robot;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import edu.wpi.first.wpilibj.Sendable;

@SuppressWarnings("static-access")
public class SmartDashboard implements Runnable {
	
	private ConcurrentHashMap<String, Object> values;
	private ConcurrentHashMap<String, Double> numQueue;
	private ConcurrentHashMap<String, String> stringQueue;
	private ConcurrentHashMap<String, Sendable> dataQueue;
	
	private int runs = 0;
	private int gets = 0;
	
	private boolean running = true;
	
	private edu.wpi.first.wpilibj.smartdashboard.SmartDashboard dashboard;
	
	public SmartDashboard() {
		values = new ConcurrentHashMap<String, Object>(16, 0.75f, 3);
		numQueue = new ConcurrentHashMap<String, Double>(16, 0.75f, 3);
		dataQueue = new ConcurrentHashMap<String, Sendable>(16, 0.75f, 3);
		stringQueue = new ConcurrentHashMap<String, String>(16, 0.75f, 3);
	}
	
	
	@Override
	public void run() {
		while(running) {
			runs++;
			dashboard.putNumber("values", values.size());
			dashboard.putNumber("numQueue", numQueue.size());
			dashboard.putNumber("dataQueue", dataQueue.size());
			dashboard.putNumber("runs", runs);
			
			for(Entry<String, Sendable> entry : dataQueue.entrySet()) {
				dashboard.putNumber("first", 1);
				dashboard.putData(entry.getKey(), entry.getValue());
				dataQueue.remove(entry.getKey());
			}
			
			for(Entry<String, Double> entry : numQueue.entrySet()) {
				dashboard.putNumber("second", 1);
				dashboard.putNumber(entry.getKey(), entry.getValue());
				numQueue.remove(entry.getKey());
			}
			
			for(Entry<String, String> entry : stringQueue.entrySet()) {
				dashboard.putString(entry.getKey(), entry.getValue());
				stringQueue.remove(entry.getKey());
			}
			
			for(String key : values.keySet()) {
				dashboard.putString("gets", key);
				values.put(key, dashboard.getData(key));
				gets++;
			}
			try {
				dashboard.putNumber("beforesleep", 1);
				Thread.sleep(100);
				dashboard.putNumber("aftersleep", 1);
			} catch (InterruptedException e) {
				dashboard.putString("ERROR", e.getMessage());
			}
		}
	}
	
	public Sendable getData(String key) {
		Sendable value = (Sendable)values.get(key);
		if(value == null) {
			value = dashboard.getData(key);
			values.put(key, value);
		}
		return value;
	}
	
	public double getNumber(String key) {
		Double value = (Double)values.get(key);
		if(value == null) {
			value = dashboard.getNumber(key);
			values.put(key, value);
		}
		return value;
	}
	
	public String getString(String key) {
		String value = (String)values.get(key);
		if(value == null) {
			value = dashboard.getString(key);
			values.put(key, value);
		}
		return value;
	}
	
	public void putData(String key, Sendable value) {
		dataQueue.put(key, value);
		values.put(key, value);
	}
	
	public void putNumber(String key, double value) {
		numQueue.put(key, (Double)value);
		values.put(key, (Double)value);
	}
	
	public void putString(String key, String s) {
		stringQueue.put(key, s);
		values.put(key, s);
	}
	
	public void shutdown() {
		running = false;
	}
	
}
