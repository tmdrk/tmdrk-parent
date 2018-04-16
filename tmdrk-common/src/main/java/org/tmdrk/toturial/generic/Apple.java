package org.tmdrk.toturial.generic;

import org.tmdrk.toturial.entity.User;

public class Apple<T extends User,E> extends Fruit<User>{
	@Override
	public User generate() {
		
		return null;
	}
}
