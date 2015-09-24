package com.returnsoft.callcenter.eao;

import java.util.Date;
import java.util.List;

import com.returnsoft.callcenter.entity.Call;
import com.returnsoft.callcenter.exception.EaoException;

public interface CallEao {
	
	public void add(Call call) throws EaoException;
	
	public Call update(Call call) throws EaoException;
	
	public Call findByUniqueId(Double uniqueId) throws EaoException;
	
	public List<Call> findByReport(Date start, Date end) throws EaoException;

}
