package com.returnsoft.callcenter.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.returnsoft.callcenter.enumeration.CallEventTypeEnum;

@Converter(autoApply=true)
public class CallEventTypeConverter implements AttributeConverter<CallEventTypeEnum, Short> {

	@Override
	public Short convertToDatabaseColumn(CallEventTypeEnum attribute) {
		return (attribute==null) ? null : attribute.getId();
	}

	@Override
	public CallEventTypeEnum convertToEntityAttribute(Short dbData) {
		return (dbData==null) ? null : CallEventTypeEnum.findById(dbData);
	}

}
