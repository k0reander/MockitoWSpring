package com.packt.aop;

public class MessageWriter implements IMessageWriter {

	@Override
	public void writeMessage() {
		System.out.print("World");
	}
}
