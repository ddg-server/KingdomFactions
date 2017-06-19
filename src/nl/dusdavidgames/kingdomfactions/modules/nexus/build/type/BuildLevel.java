package nl.dusdavidgames.kingdomfactions.modules.nexus.build.type;

import lombok.Getter;

public enum BuildLevel {
	LEVEL_0(0, "0"),
	LEVEL_1(1, "I"),
	LEVEL_2(2, "II"),
	LEVEL_3(3, "III"),
	LEVEL_4(4, "IV"),
	LEVEL_5(5, "V"),
	LEVEL_6(6, "VI"),
	LEVEL_7(7, "VII"),
	LEVEL_8(8, "VIII");
	
	private @Getter int level;
	private @Getter String roman;
	 BuildLevel(int level, String roman) {
		this.level = level;
		this.roman = roman;
	}
	 
	 
	 public static int getLevel(BuildLevel b) {
		 return b.getLevel();
	 }
	 
	 public static BuildLevel getLevel(int i) {
		 switch(i) {
		 case 0:
			 return LEVEL_0;
		 case 1:
			 return LEVEL_1;
		 case 2:
			 return LEVEL_2;
		 case 3:
			 return LEVEL_3;
		 case 4: 
			 return LEVEL_4;
		 case 5:
			 return LEVEL_5;
		 case 6:
			 return LEVEL_6;
		 case 7: 
			 return LEVEL_7;
		 case 8:
			 return LEVEL_8;
			 }
		return LEVEL_0;
	 }
	 
	 
	 public BuildLevel next() {
	     if(getLevel(this) >= 8) {
	    	 return null;
	     }
	     int i = getLevel(this);
	     i++;
	     return getLevel(i);
	 }
	 
	 public boolean hasNext() {
		 return next() != null;
	 }
}
