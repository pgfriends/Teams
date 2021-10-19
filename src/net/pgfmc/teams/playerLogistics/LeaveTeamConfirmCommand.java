package net.pgfmc.teams.playerLogistics;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.pgfmc.pgfessentials.playerdataAPI.PlayerData;
import net.pgfmc.teams.inventories.LeaderLeaveInventory;
import net.pgfmc.teams.inventories.TeamInventory;
import net.pgfmc.teams.teamscore.Team;

/*
Written by CrimsonDart

-----------------------------------

the sender leaves their current team (unless if they dont have a team)

-----------------------------------
 */

public class LeaveTeamConfirmCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("You must be in game to run this command.");
			return true;
		}
		
		Player p = (Player) sender;
		
		PlayerData playerData = PlayerData.getPlayerData(p);
		if (playerData.getData("team") != null) {
			
			Team team = playerData.getData("team");
			
			if (team.getLeader().getUniqueId().equals(p.getUniqueId())) {
				p.openInventory(new LeaderLeaveInventory(team, p, TeamInventory.removePlayer(p, team.getMembers())).getInventory());
			} else {
				String name = team.getName();
				
				if (((Team) playerData.getData("team")).removePlayer(p)) {
					p.sendMessage("�dYou have left �a�l" + name + "�r�d.");
				} else {
					p.sendMessage("�cYou can't leave �a�l" + name + "�r�c!");
					p.sendMessage("�cCheck if you have any Pending Requests.");
				}
			}
			
			return true;
		} else {
			p.sendMessage("�cYou aren't in a team!");
			return true;
		}
	}
}