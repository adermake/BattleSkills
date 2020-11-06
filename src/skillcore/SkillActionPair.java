package skillcore;

import org.bukkit.Material;

public class SkillActionPair {

	public Skill skill;
	public Material itemType;
	public SkillAction skillAction;
	public SkillActionPair(SkillAction sk,Material m,Skill s) {
		skillAction = sk;
		itemType = m;
		skill = s;
		
	}
}
