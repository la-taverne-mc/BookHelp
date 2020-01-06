package fr.neolithic.bookhelp;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class Commands implements TabExecutor {
    private Main main;
    
    public Commands(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            switch (command.getName().toLowerCase()) {
                case "help":
                    if (main.helpBooks.containsKey(player.getLocation().getWorld().getName())) {
                        player.openBook(main.helpBooks.get(player.getLocation().getWorld().getName()));
                    }
                    else {
                        player.sendMessage("§4[§cBook Help§4] §eDésolé mais ce monde possède pas d'aides. Notifie le staff pour qu'on puisse en rajouter.");
                        return false;
                    }

                    return true;

                case "helpset":
                    if (player.getInventory().getItemInMainHand().getType() != Material.WRITTEN_BOOK) {
                        player.sendMessage("§4[§cBook Help§4] §eVeuillez prendre un livre écrit en main.");
                        return false;
                    }

                    try {
                        if (Bukkit.getWorld(args[0]) == null) {
                            player.sendMessage("§4[§cBook Help§4] §6" + args[0] + " §en'est pas un nom de monde valide.");
                            return false;
                        }

                        player.sendMessage("§4[§cBook Help§4] §eLe livre d'aide a été défini pour " + args[0]);
                        
                        if (main.helpBooks.containsKey(args[0])) {
                            main.helpBooks.replace(args[0], player.getInventory().getItemInMainHand());
                        }
                        else {
                            main.helpBooks.put(args[0], player.getInventory().getItemInMainHand());
                        }
                    }
                    catch (IndexOutOfBoundsException e) {
                        player.sendMessage("§4[§cBook Help§4] §eLe livre d'aide a été défini pour " + player.getLocation().getWorld().getName());

                        if (main.helpBooks.containsKey(player.getLocation().getWorld().getName())) {
                            main.helpBooks.replace(player.getLocation().getWorld().getName(), player.getInventory().getItemInMainHand());
                        } else {
                            main.helpBooks.put(player.getLocation().getWorld().getName(), player.getInventory().getItemInMainHand());
                        }
                    }

                    return true;

                default:
                    return false;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        
        return null;
    }
}