package com.returnsoft.callcenter.enumeration;

public enum SessionTypeEnum {
	
	AVAILABLE((short)1,"Disponible",false),
	BREAK((short)2,"Break",true),
	PAUSE((short)3,"Pausa",true);
	
	private Short id;
	
	private String name;
	
	private Boolean isPaused;
	
	private SessionTypeEnum(Short id, String name, boolean isPaused){
		this.id=id;	
		this.name=name;
		this.isPaused=isPaused;
	}
	
	public static SessionTypeEnum findById(short id){
		for(SessionTypeEnum sessionTypeEnum: SessionTypeEnum.values()){
			if (sessionTypeEnum.getId()==id) {
				return sessionTypeEnum;
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



	public Boolean getIsPaused() {
		return isPaused;
	}

	

	
	
	

}
