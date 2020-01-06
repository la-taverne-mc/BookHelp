package fr.neolithic.bookhelp;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public HashMap<String, ItemStack> helpBooks = new HashMap<String, ItemStack>();

    private FileManager saveFile;
    private FileConfiguration saveContent;

    @Override
    public void onEnable() {
        Commands commandExecutor = new Commands(this);
        getCommand("help").setExecutor(commandExecutor);
        getCommand("helpset").setExecutor(commandExecutor);

        saveFile = new FileManager(this, "save.yml");
        saveContent = saveFile.getContent();

        for (String key : saveContent.getKeys(false)) {
            helpBooks.put(key, saveContent.getItemStack(key));
        }

        System.out.println("[BookHelp] Successfully Enabled BookHelp v1.0");
    }

    @Override
    public void onDisable() {
        for (Map.Entry<String, ItemStack> entry : helpBooks.entrySet()) {
            saveContent.set(entry.getKey(), entry.getValue());
        }

        saveFile.save();

        helpBooks.clear();

        System.out.println("[BookHelp] Successfully Disabled BookHelp v1.0");
    }
}