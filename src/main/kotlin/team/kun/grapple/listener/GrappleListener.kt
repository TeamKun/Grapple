package team.kun.grapple.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.plugin.java.JavaPlugin
import team.kun.grapple.item.Grapple

class GrappleListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onGrapple(event: PlayerFishEvent) {
        if (Grapple().equal(event.player.inventory.itemInMainHand, plugin) ||
                Grapple().equal(event.player.inventory.itemInOffHand, plugin)) {
            when (event.state) {
                PlayerFishEvent.State.FISHING,
                PlayerFishEvent.State.CAUGHT_FISH,
                PlayerFishEvent.State.CAUGHT_ENTITY,
                PlayerFishEvent.State.FAILED_ATTEMPT -> {
                    return
                }
                else -> {
                    Grapple().execute(event.player, event.hook)
                }
            }
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (event.cause == EntityDamageEvent.DamageCause.FALL && entity is Player) {
            if (Grapple().equal(entity.inventory.itemInMainHand, plugin) ||
                    Grapple().equal(entity.inventory.itemInOffHand, plugin)) {
                event.damage /= 2.0
            }
        }
    }
}


