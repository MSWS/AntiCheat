package xyz.msws.anticheat.checks.movement;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import xyz.msws.anticheat.NOPE;
import xyz.msws.anticheat.checks.Check;
import xyz.msws.anticheat.checks.CheckType;
import xyz.msws.anticheat.data.CPlayer;

/**
 * Checks if a player's Y differences are too similar
 * 
 * @author imodm
 * 
 *         TODO rewrite - buggy while a player is jumping on lilypads doesn't do
 *         average, merely counts up until max size, very unintentional and
 *         poorly written
 *
 * @deprecated
 */
public class Jesus2 implements Check, Listener {

	private NOPE plugin;

	@Override
	public CheckType getType() {
		return CheckType.MOVEMENT;
	}

	@Override
	public void register(NOPE plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		CPlayer cp = plugin.getCPlayer(player);

		if (player.getLocation().getBlock().getType() != Material.AIR)
			return;

		if (player.isFlying() || player.isInsideVehicle())
			return;

		if (!player.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid())
			return;

		if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.WATER) // Should be
																										// stationary
																										// water
			return;

		if (event.getTo().getY() != event.getFrom().getY())
			return;

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				Block b = player.getLocation().clone().add(x, -.09375, z).getBlock();
				if (b.getType().isSolid() || b.getType() == Material.LILY_PAD)
					return;
			}
		}

		cp.flagHack(this, 30);
	}

	@Override
	public String getCategory() {
		return "Jesus";
	}

	@Override
	public String getDebugName() {
		return getCategory() + "#2";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}