package io.github.haykam821.lootsatchels.item;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class LootSatchelItem extends SatchelItem {
	private static final Logger LOGGER = LogManager.getLogger("Loot Satchels");

	public LootSatchelItem(Item.Settings settings) {
		super(settings);
	}

	public List<ItemStack> getContents(List<ItemStack> contents, World world, PlayerEntity player, Hand hand) {
		ItemStack handStack = player.getStackInHand(hand);
		CompoundTag tag = handStack.getTag();

		if (tag != null && tag.contains("LootTables", 9) && !world.isClient) {
			ListTag lootTableTag = tag.getList("LootTables", 8);

			LootManager lootManager = world.getServer().getLootManager();

			Iterator<Tag> iterator = lootTableTag.iterator();
			while (iterator.hasNext()) {
				String lootTableID = iterator.next().asString();
				LootTable lootTable = lootManager.getSupplier(new Identifier(lootTableID));

				if (lootTable == null) {
					LOGGER.error("Invalid loot table: {}", lootTableID);
					continue;
				} else {
					LootContext.Builder builder = new LootContext.Builder((ServerWorld) world);

					builder.setRandom(world.getRandom());
					builder.put(LootContextParameters.POSITION, player.getBlockPos());
					builder.put(LootContextParameters.THIS_ENTITY, player);

					LootContext context = builder.build(LootContextTypes.GIFT);
					contents.addAll(lootTable.getDrops(context));
				}
			}
		}

		return contents;
	}
}