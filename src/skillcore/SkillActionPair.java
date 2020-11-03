package skillcore;

import org.bukkit.Material;

public class SkillActionPair {

	Skill skill;
	Material itemType;
	SkillAction skillAction;
	public SkillActionPair(SkillAction sk,Material m,Skill s) {
		skillAction = sk;
		itemType = m;
		skill = s;
		
	}
}
