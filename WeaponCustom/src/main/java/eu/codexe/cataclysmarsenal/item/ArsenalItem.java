package eu.codexe.cataclysmarsenal.item;

import eu.codexe.cataclysmarsenal.system.CombatSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ArsenalItem extends Item {
    private final WeaponDefinition definition;

    public ArsenalItem(WeaponDefinition definition) {
        super(new Item.Settings().maxCount(1).maxDamage(definition.durability()));
        this.definition = definition;
    }

    public WeaponDefinition definition() {
        return definition;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            CombatSystem.fire(serverWorld, user, stack, definition);
        }
        return TypedActionResult.success(stack, world.isClient);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient && attacker instanceof PlayerEntity player) {
            CombatSystem.meleeAttack(player, target, definition);
        }
        stack.damage(1, attacker, LivingEntity.getSlotForHand(Hand.MAIN_HAND));
        return true;
    }
}