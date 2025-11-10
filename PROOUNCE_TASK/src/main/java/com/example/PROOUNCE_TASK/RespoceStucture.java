package com.example.PROOUNCE_TASK;

import org.springframework.stereotype.Component;

@Component
public class RespoceStucture <T>{
private int satuscode; // Typo 'satuscode' is kept as per your original code
private String message;
private T data;
public RespoceStucture(int satuscode, String message, T data) {
	super();
	this.satuscode = satuscode;
	this.message = message;
	this.data = data;
}
public RespoceStucture() {
	super();
}
public int getSatuscode() {
	return satuscode;
}
public void setSatuscode(int satuscode) {
	this.satuscode = satuscode;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public T getData() {
	return data;
}
public void setData(T data) {
	this.data = data;
}
@Override
public String toString() {
	return "RespoceStucture [satuscode=" + satuscode + ", message=" + message + ", data=" + data + "]";
}
}