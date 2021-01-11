package team.kun.grapple

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.author.Author
import team.kun.grapple.ext.registerListener
import team.kun.grapple.item.RecipeService
import team.kun.grapple.listener.GrappleListener

@Plugin(name = "Grapple", version = "1.0-SNAPSHOT")
@Author("ReyADayer")
@ApiVersion(ApiVersion.Target.v1_15)
class Grapple : JavaPlugin() {
    override fun onEnable() {
        registerListener(GrappleListener(this))

        RecipeService.add(this)
    }

    override fun onDisable() {
        RecipeService.remove(this)
    }
}