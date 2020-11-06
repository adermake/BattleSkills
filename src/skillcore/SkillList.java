package skillcore;

import java.util.ArrayList;

import skill.Acrobat;
import skill.Authority;
import skill.Explorer;
import skill.Fletcher;
import skill.Fly;
import skill.Hellbringer;
import skill.Hyperscreamer;
import skill.Illusionist;
import skill.MobManipulator;
import skill.Psychic;
import skill.Quickbuilder;
import skill.Shadow;
import skill.Shapeshifter;
import skill.Teleporter;
import skill.Tracker;
import skill.Transporter;
import skill.Waterwalker;
import skill.XRay;
import skill.Yeeter;
import utils.MathUtils;

public class SkillList {

	public static ArrayList<Skill> skillList = new ArrayList<Skill>();
	
	
	
	public static void fillList() {
		skillList.add(new Acrobat());
		skillList.add(new Authority());		
		skillList.add(new Explorer());
		skillList.add(new Fletcher());
		skillList.add(new Fly());
		skillList.add(new Hellbringer());
		skillList.add(new Hyperscreamer());
		skillList.add(new Illusionist());
		skillList.add(new MobManipulator());
		skillList.add(new Psychic());
		skillList.add(new Quickbuilder());
		skillList.add(new Shadow());
		skillList.add(new Shapeshifter());
		skillList.add(new Teleporter());
		skillList.add(new Tracker());
		skillList.add(new Transporter());
		skillList.add(new Waterwalker());
		skillList.add(new XRay());
		skillList.add(new Yeeter());
	}
	
	
	public static Skill getRandomEntry() {
		int r = MathUtils.randInt(0, skillList.size()-1);
		
		return skillList.get(r);
	}
	
}
