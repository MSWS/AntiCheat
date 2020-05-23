package xyz.msws.anticheat.actions;

import org.apache.commons.lang.StringUtils;
import org.bukkit.OfflinePlayer;

import xyz.msws.anticheat.NOPE;
import xyz.msws.anticheat.checks.Check;

public class VLActionCheck extends AbstractConditionalAction {

	private int value;
	private Compare comparer;

	public VLActionCheck(NOPE plugin, String data) {
		super(plugin);
		String symb = "";
		int firstNumber = "vl".length();
		for (; firstNumber < data.length(); firstNumber++) {
			String c = data.charAt(firstNumber) + "";
			if (StringUtils.isNumeric(c))
				break;
			symb += c;
		}
		comparer = Compare.fromString(symb);
		this.value = Integer.parseInt(data.substring(firstNumber));
	}

	@Override
	public boolean getValue(OfflinePlayer player, Check check) {
		return comparer.check(plugin.getCPlayer(player).getVL(check.getCategory()), value);
	}

}
