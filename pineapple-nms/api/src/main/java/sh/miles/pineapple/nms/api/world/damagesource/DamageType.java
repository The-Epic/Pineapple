package sh.miles.pineapple.nms.api.world.damagesource;

import org.bukkit.NamespacedKey;
import sh.miles.pineapple.collection.registry.RegistryKey;
import sh.miles.pineapple.nms.api.registry.PineappleRegistry;

public interface DamageType extends RegistryKey {
    DamageType IN_FIRE = get("in_fire");
    DamageType LIGHTNING_BOLT = get("lightning_bolt");
    DamageType ON_FIRE = get("on_fire");
    DamageType LAVA = get("lava");
    DamageType HOT_FLOOR = get("hot_floor");
    DamageType IN_WALL = get("in_wall");
    DamageType CRAMMING = get("cramming");
    DamageType DROWN = get("drown");
    DamageType STARVE = get("starve");
    DamageType CACTUS = get("cactus");
    DamageType FALL = get("fall");
    DamageType FLY_INTO_WALL = get("fly_into_wall");
    DamageType FELL_OUT_OF_WORLD = get("out_of_world");
    DamageType GENERIC = get("generic");
    DamageType MAGIC = get("magic");
    DamageType WITHER = get("wither");
    DamageType DRAGON_BREATH = get("dragon_breath");
    DamageType DRY_OUT = get("dry_out");
    DamageType SWEET_BERRY_BUSH = get("sweet_berry_bush");
    DamageType FREEZE = get("freeze");
    DamageType STALAGMITE = get("stalagmite");
    DamageType FALLING_BLOCK = get("falling_block");
    DamageType FALLING_ANVIL = get("falling_anvil");
    DamageType FALLING_STALACTITE = get("falling_stalactite");
    DamageType STING = get("sting");
    DamageType MOB_ATTACK = get("mob_attack");
    DamageType MOB_ATTACK_NO_AGGRO = get("mob_attack_no_aggro");
    DamageType PLAYER_ATTACK = get("player_attack");
    DamageType ARROW = get("arrow");
    DamageType TRIDENT = get("trident");
    DamageType MOB_PROJECTILE = get("mob_projectile");
    DamageType FIREWORKS = get("fireworks");
    DamageType FIREBALL = get("fireball");
    DamageType UNATTRIBUTED_FIREBALL = get("unattributed_fireball");
    DamageType WITHER_SKULL = get("wither_skull");
    DamageType THROWN = get("thrown");
    DamageType INDIRECT_MAGIC = get("indirect_magic");
    DamageType THORNS = get("thorns");
    DamageType EXPLOSION = get("explosion");
    DamageType PLAYER_EXPLOSION = get("player_explosion");
    DamageType SONIC_BOOM = get("sonic_boom");
    DamageType BAD_RESPAWN_POINT = get("bad_respawn_point");
    DamageType OUTSIDE_BORDER = get("outside_border");
    DamageType GENERIC_KILL = get("generic_kill");

    /**
     * @return exhaustion?
     * <p>
     * Note if anyone knows what this is please PR proper documentation
     */
    float exhaustion();

    /**
     * Gets the proper effect for this DamageType
     *
     * @return the effect
     */
    DamageEffect effect();

    private static DamageType get(String id) {
        return (DamageType) PineappleRegistry.DAMAGE_TYPE.getOrNull(NamespacedKey.minecraft(id).toString());
    }
}
