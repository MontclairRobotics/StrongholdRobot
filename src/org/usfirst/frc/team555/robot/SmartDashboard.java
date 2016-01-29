package org.usfirst.frc.team555.robot;

import java.util.Map.Entry;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import edu.wpi.first.wpilibj.Sendable;

public class SmartDashboard extends TimerTask {
	
	private ConcurrentHashMap<String, Object> values;
	private ConcurrentHashMap<String, Double> numQueue;
	private ConcurrentHashMap<String, Sendable> dataQueue;
	
	public SmartDashboard() {
		values = new ConcurrentHashMap<String, Object>(16, 0.75f, 3);
		numQueue = new ConcurrentHashMap<String, Double>(16, 0.75f, 3);
		dataQueue = new ConcurrentHashMap<String, Sendable>(16, 0.75f, 3);
	}
	
	@Override
	public void run() {
		for(Entry<String, Sendable> entry : dataQueue.entrySet()) {
			edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putData(entry.getKey(), entry.getValue());
			dataQueue.remove(entry.getKey());
		}
		
		for(Entry<String, Double> entry : numQueue.entrySet()) {
			edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putNumber(entry.getKey(), entry.getValue());
			numQueue.remove(entry.getKey());
		}
		
		for(String key : values.keySet()) {
			values.put(key, edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.getData(key));
		}
	}
	
	public Sendable getData(String key) {
		Sendable value = (Sendable)values.get(key);
		if(value == null) {
			value = edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.getData(key);
			values.put(key, value);
		}
		return value;
	}
	
	public double getNumber(String key) {
		Double value = (Double)values.get(key);
		if(value == null) {
			value = edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.getNumber(key);
			values.put(key, value);
		} else {
			value = (Double)values.get(key);
		}
		return value;
	}
	
	public void putData(String key, Sendable value) {
		dataQueue.put(key, value);
		values.put(key, value);
	}
	
	public void putNumber(String key, double value) {
		numQueue.put(key, value);
		values.put(key, value);
	}
	
}
