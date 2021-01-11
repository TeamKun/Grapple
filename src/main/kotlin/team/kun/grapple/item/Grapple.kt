package team.kun.grapple.item

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.meta.Damageable
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.Vector
import team.kun.grapple.ext.playSound

class Grapple : Item(), Craftable {
    override val name = "グラップル"
    override val description = listOf("右クリックでフックを射出する", "もう一度右クリックで移動できる", "手に持っていると落下ダメージを半減する")
    override val itemStack = ItemStack(Material.FISHING_ROD)

    override fun getRecipe(plugin: JavaPlugin): ShapedRecipe? {
        val key = getKey(plugin) ?: return null
        return ShapedRecipe(key, toItemStack(plugin)).apply {
            shape("  I", " IS", "I S")
            setIngredient('I', Material.IRON_INGOT)
            setIngredient('S', Material.STRING)
        }
    }

    fun execute(player: Player, target: Entity) {
        val hookLocation = target.location

        if (player.fireTicks <= 0 && isValidLocation(hookLocation)) {
            val playerLocation = player.location
            playerLocation.playSound(Sound.ENTITY_ZOMBIE_INFECT, 0.5f, 1.8f)
            player.velocity = Vector(hookLocation.x - playerLocation.x, hookLocation.y - playerLocation.y, hookLocation.z - playerLocation.z).multiply(0.2)
            target.remove()
            val itemMeta = itemStack.itemMeta
            if (itemMeta is Damageable) {
                itemMeta.damage += 1
                if (itemStack.type.maxDurability < itemMeta.damage) {
                    player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f)
                    itemStack.amount -= 1
                }
            }
        }
    }

    private fun isValidLocation(location: Location): Boolean {
        return isValidBlock(location.block) || isValidBlock(location.block.getRelative(BlockFace.UP)) || isValidBlock(location.block.getRelative(BlockFace.DOWN))
    }

    private fun isValidBlock(block: Block): Boolean {
        return !(block.type == Material.AIR || block.type == Material.WATER || block.type == Material.WATER)
    }
}