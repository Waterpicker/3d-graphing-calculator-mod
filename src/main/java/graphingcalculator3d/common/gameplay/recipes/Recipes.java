package graphingcalculator3d.common.gameplay.recipes;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.blocks.GCBlocks;
import graphingcalculator3d.common.gameplay.items.GCItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class Recipes
{
	public static void loadRecipes(Consumer<IFinishedRecipe> consumer) {
        registerShaped(consumer, GCBlocks.block_gc_cartesian, Blocks.STONE, "cartesian");

		registerShaped(consumer, GCBlocks.block_gc_spherical,  Items.SNOWBALL, "spherical");
		
		registerShaped(consumer, GCBlocks.block_gc_cylindrical, Items.BUCKET, "cylindrical");
		
		registerShaped(consumer, GCBlocks.block_gc_parabolic_cylindrical, Items.BOWL, "parabolic_cylindrical");
		
		registerShaped(consumer, GCBlocks.block_gc_bipolar_cylindrical, Items.GOLD_NUGGET, "bipolar_cylindrical");
		
		
		registerShaped(consumer, GCBlocks.block_gc_oblate_spheroidal, Items.SLIME_BALL, "oblate_spheroidal");
		
		registerShaped(consumer, GCBlocks.block_gc_toroidal, Items.CLAY_BALL, "toroidal");
		
		registerShaped(consumer, GCBlocks.block_gc_conical, Items.CARROT, "conical");

		registerShaped(consumer, GCBlocks.block_gc_6_sphere, Items.ENDER_PEARL, "6_sphere");

		registerShaped(consumer, GCBlocks.block_gc_prolate_spheroidal, Items.MAGMA_CREAM, "prolate_spheroidal");
		
		registerShaped(consumer, GCBlocks.block_gc_parabolic, Blocks.CAULDRON, "parabolic");
		
		ShapedRecipeBuilder.shapedRecipe(GCItems.item_memory_card.get())
                .patternLine("GII")
                .patternLine("INI")
                .patternLine("RNR")
                .key('I', Items.IRON_INGOT)
                .key('G', Blocks.GLASS)
                .key('R', Items.REDSTONE)
                .key('N', Items.GOLD_NUGGET)
                .build(consumer, GraphingCalculator3D.id("memory_card"));

		register(consumer, GCBlocks.block_gc_spherical_phi, GCBlocks.block_gc_spherical, "spherical_phi");
		register(consumer, GCBlocks.block_gc_spherical_theta, GCBlocks.block_gc_spherical_phi, "spherical_theta");
		register(consumer, GCBlocks.block_gc_spherical, GCBlocks.block_gc_spherical_theta, "spherical_shapeless");
		register(consumer, GCBlocks.block_gc_cylindrical_h, GCBlocks.block_gc_cylindrical, "cylindrical_h");
		register(consumer, GCBlocks.block_gc_cylindrical_theta, GCBlocks.block_gc_cylindrical_h, "cylindrical_theta");
		register(consumer, GCBlocks.block_gc_cylindrical, GCBlocks.block_gc_cylindrical_theta, "cylindrical_shapeless");
		register(consumer, GCBlocks.block_gc_parabolic_cylindrical_sigma, GCBlocks.block_gc_parabolic_cylindrical, "parabolic_cylindrical_sigma");
		register(consumer, GCBlocks.block_gc_parabolic_cylindrical_tau, GCBlocks.block_gc_parabolic_cylindrical_sigma, "parabolic_cylindrical_tau");
		register(consumer, GCBlocks.block_gc_parabolic_cylindrical, GCBlocks.block_gc_parabolic_cylindrical_tau, "parabolic_cylindrical_shapeless");
		register(consumer, GCBlocks.block_gc_parabolic_tau, GCBlocks.block_gc_parabolic, "parabolic_tau");
		register(consumer, GCBlocks.block_gc_parabolic_phi, GCBlocks.block_gc_parabolic_tau, "parabolic_phi");
		register(consumer, GCBlocks.block_gc_parabolic, GCBlocks.block_gc_parabolic_phi, "parabolic_shapeless");
		register(consumer, GCBlocks.block_gc_bipolar_cylindrical_sigma, GCBlocks.block_gc_bipolar_cylindrical, "bipolar_cylindrical_sigma");
		register(consumer, GCBlocks.block_gc_bipolar_cylindrical_tau, GCBlocks.block_gc_bipolar_cylindrical_sigma, "bipolar_cylindrical_tau");
		register(consumer, GCBlocks.block_gc_bipolar_cylindrical, GCBlocks.block_gc_bipolar_cylindrical_tau, "bipolar_cylindrical_shapeless");
		register(consumer, GCItems.item_memory_card, GCItems.item_memory_card, "memory_card_reset");
		register(consumer, GCBlocks.block_gc_oblate_spheroidal_nu, GCBlocks.block_gc_oblate_spheroidal, "oblate_spheroidal_nu");
		register(consumer, GCBlocks.block_gc_oblate_spheroidal_phi, GCBlocks.block_gc_oblate_spheroidal_nu, "oblate_spheroidal_varphi");
		register(consumer, GCBlocks.block_gc_oblate_spheroidal, GCBlocks.block_gc_oblate_spheroidal_phi, "oblate_spheroidal_shapeless");
		register(consumer, GCBlocks.block_gc_prolate_spheroidal_nu, GCBlocks.block_gc_prolate_spheroidal, "prolate_spheroidal_nu");
		register(consumer, GCBlocks.block_gc_prolate_spheroidal_phi, GCBlocks.block_gc_prolate_spheroidal_nu, "prolate_spheroidal_varphi");
		register(consumer, GCBlocks.block_gc_prolate_spheroidal, GCBlocks.block_gc_prolate_spheroidal_phi, "prolate_spheroidal_shapeless");
		register(consumer, GCBlocks.block_gc_toroidal_phi, GCBlocks.block_gc_toroidal, "toroidal_phi");
		register(consumer, GCBlocks.block_gc_toroidal_tau, GCBlocks.block_gc_toroidal_phi, "toroidal_tau");
		register(consumer, GCBlocks.block_gc_toroidal, GCBlocks.block_gc_toroidal_tau, "toroidal_shapeless");
		register(consumer, GCBlocks.block_gc_conical_mu, GCBlocks.block_gc_conical, "conical_mu");
		register(consumer, GCBlocks.block_gc_conical_nu, GCBlocks.block_gc_conical_mu, "conical_nu");
		register(consumer, GCBlocks.block_gc_conical, GCBlocks.block_gc_conical_nu, "conical_shapeless");
		register(consumer, GCBlocks.block_gc_6_sphere_v, GCBlocks.block_gc_6_sphere, "6_sphere_v");

		register(consumer, GCBlocks.block_gc_6_sphere_w, GCBlocks.block_gc_6_sphere_v, "6_sphere_w");
		register(consumer, GCBlocks.block_gc_6_sphere, GCBlocks.block_gc_6_sphere_w, "6_sphere_shapeless");
	}

    private static void registerShaped(Consumer<IFinishedRecipe> consumer, RegistryObject<Block> block, IItemProvider item, String name) {
        ShapedRecipeBuilder.shapedRecipe(block.get())
                .patternLine("IGI")
                .patternLine("IVI")
                .patternLine("ORO")
                .key('I', Items.IRON_INGOT)
                .key('G', Blocks.GLASS)
                .key('R', Blocks.REDSTONE_LAMP)
                .key('O', Blocks.OBSIDIAN)
                .key('V', item)
                .build(consumer, GraphingCalculator3D.id("recipe_gc_" + name));
    }

    private static void register(Consumer<IFinishedRecipe> consumer, RegistryObject<? extends IItemProvider> block, RegistryObject<? extends IItemProvider> item, String name) {
        ShapelessRecipeBuilder.shapelessRecipe(block.get())
                .addIngredient(item.get())
                .build(consumer, GraphingCalculator3D.id("recipe_gc_" + name));
    }
}
