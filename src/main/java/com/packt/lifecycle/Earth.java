package com.packt.lifecycle;

public class Earth implements Planet {

	private final String name;
	
	public Earth(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
