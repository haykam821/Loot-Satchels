package io.github.haykam821.lootsatchels;

import io.github.haykam821.lootsatchels.item.LootSatchelItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	public static final Item LOOT_SATCHEL = new LootSatchelItem(new Item.Settings().maxCount(1));

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("lootsatchels", "loot_satchel"), LOOT_SATCHEL);
	}
}