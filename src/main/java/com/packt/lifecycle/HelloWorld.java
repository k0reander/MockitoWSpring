package com.packt.lifecycle;

public class HelloWorld {
	
	private Planet planet;

	public String getMessage() {
		return "Hello from planet " + planet.getName();
	}
	
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

}
