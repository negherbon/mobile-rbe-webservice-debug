package br.com.pontosistemas.webservice;

import com.the9tcat.hadi.annotation.Column;
import com.the9tcat.hadi.annotation.Table;

@Table(name="Data") 
public class Data {
	
	@Column(name="data")
	public String data;
	
}
