package ru.otus.hw13.security.acls.service;

import java.util.UUID;

class TestObject {

	private final String id = UUID.randomUUID().toString();

	String getId() {
		return this.id;
	}
}
