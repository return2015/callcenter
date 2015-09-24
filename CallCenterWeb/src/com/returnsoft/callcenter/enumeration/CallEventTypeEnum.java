package com.returnsoft.callcenter.enumeration;

public enum CallEventTypeEnum {
	
	ENTERQUEUE((short)1,"En cola"),
	CONNECT((short)2,"En llamada"),
	COMPLETEAGENT((short)3,"Finalizado por agente"),
	COMPLETECALLER((short)4,"Finalizado por cliente"),
	TRANSFER((short)5,"Tranferida"),
	ABANDON((short)6,"Abandonada"),
	HOLD((short)7,"En Hold"),
	UNHOLD((short)8,"UnHold");
	
	private Short id;
	private String name;
	
	private CallEventTypeEnum(Short id, String name){
		this.id=id;
		this.name=name;
	}
	
	public static CallEventTypeEnum findById(short id){
		for(CallEventTypeEnum callEventTypeEnum: CallEventTypeEnum.values()){
			if (callEventTypeEnum.getId()==id) {
				return callEventTypeEnum;
			}
		}
		return null;
	}

	public Short getId() {
		return id;
	}

	

	public String getName() {
		return name;
	}

	
	

}
