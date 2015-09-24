package com.returnsoft.callcenter.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.returnsoft.callcenter.enumeration.SessionTypeEnum;

@Converter(autoApply=true)
public class SessionTypeConverter implements AttributeConverter<SessionTypeEnum, Short>{

	@Override
	public Short convertToDatabaseColumn(SessionTypeEnum attribute) {
		return (attribute==null) ? null : attribute.getId();
	}

	@Override
	public SessionTypeEnum convertToEntityAttribute(Short dbData) {
			return (dbData==null) ? null : SessionTypeEnum.findById(dbData);
		
		
	}
	
	

}
