package skillcore;

import java.util.ArrayList;

import skill.Explorer;
import skill.Fly;
import skill.Psychic;
import skill.XRay;
import utils.MathUtils;

public class SkillList {

	public static ArrayList<Skill> skillList = new ArrayList<Skill>();
	
	
	
	public static void fillList() {
		skillList.add(new Explorer());
		skillList.add(new Fly());
		skillList.add(new Psychic());
		skillList.add(new XRay());
	}
	
	
	public static Skill getRandomEntry() {
		int r = MathUtils.randInt(0, skillList.size()-1);
		
		return skillList.get(r);
	}
	
}
