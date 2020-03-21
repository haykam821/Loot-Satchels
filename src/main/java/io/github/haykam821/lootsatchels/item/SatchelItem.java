package io.github.haykam821.lootsatchels.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SatchelItem extends Item {
	public SatchelItem(Item.Settings settings) {
		super(settings);
	}

	public List<ItemStack> getContents(List<ItemStack> contents, World world, PlayerEntity player, Hand hand) {
		return contents;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack handStack = player.getStackInHand(hand);

		List<ItemStack> contents = this.getContents(new ArrayList<ItemStack>(1), world, player, hand);

		if (contents.size() > 0) {
			contents.forEach(player::giveItemStack);

			// Consume satchel if not in creative mode
			if (!player.abilities.creativeMode) {
				handStack.decrement(1);
			}

			player.incrementStat(Stats.USED.getOrCreateStat(this));
			return TypedActionResult.consume(handStack);
		}

		return TypedActionResult.fail(handStack);
	}
}
