package me.pixelperfect.vitalregister.tabcompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainTabComplete implements TabCompleter {

    List<String> arguments0 = new ArrayList<String>();
    List<String> arguments1 = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (arguments0.isEmpty()) {
            arguments0.add("data.yml"); // arguments.add("next");
        }

        List<String> result1 = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arguments0) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result1.add(a);
                }
            }
            return result1;
        }

        if (arguments1.isEmpty()) {
            arguments1.add("enable"); arguments1.add("disable"); arguments1.add("reload");
        }

        List<String> result2 = new ArrayList<String>();
        if (args.length == 2) {
            for (String a : arguments1) {
                if (a.toLowerCase().startsWith(args[1].toLowerCase())) {
                    result2.add(a);
                }
            }
            return result2;
        }
        return null;
    }
}
