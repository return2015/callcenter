package com.returnsoft.callcenter.eao;

import com.returnsoft.callcenter.entity.Queue;
import com.returnsoft.callcenter.exception.EaoException;

public interface QueueEao {
	
	public Queue findByName(String name) throws EaoException;
	

}
