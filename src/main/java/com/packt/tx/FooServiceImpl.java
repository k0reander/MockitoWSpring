package com.packt.tx;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class FooServiceImpl implements FooService {

	@Override
	public Foo getFoo(String fooName) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional(readOnly = false)
	public void insertFoo(Foo foo) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateFoo(Foo foo) {
		throw new UnsupportedOperationException();
	}

}
