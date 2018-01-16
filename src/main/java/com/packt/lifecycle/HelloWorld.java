package com.packt.lifecycle;

import org.springframework.beans.factory.annotation.Autowired;

public class HelloWorld {
	
	@Autowired
	private Planet planet;

	public String getMessage() {
		return "Hello from planet " + planet.getName();
	}
	
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

}
