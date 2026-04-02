package graphingcalculator3d.common.gameplay.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.items.GCItems;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.events.register.TileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.GenericEvent;

public class GCBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();

    private static final Block.Properties CALCULATOR_PROPERTIES = Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6000);
	
	public static final BlockGC block_gc_cartesian = registerCalculator("gc_cartesian", TileEntities.GC_CARTESIAN);
    public static final BlockGC block_gc_spherical = registerCalculator("gc_spherical", TileEntities.GC_SPHERICAL);
    public static final BlockGC block_gc_spherical_theta = registerCalculator("gc_spherical_theta", TileEntities.GC_SPHERICAL_THETA);
    public static final BlockGC block_gc_spherical_phi = registerCalculator("gc_spherical_phi", TileEntities.GC_SPHERICAL_PHI);
    public static final BlockGC block_gc_cylindrical = registerCalculator("gc_cylindrical", TileEntities.GC_CYLINDRICAL);
    public static final BlockGC block_gc_cylindrical_h = registerCalculator("gc_cylindrical_h", TileEntities.GC_CYLINDRICAL_H);
    public static final BlockGC block_gc_cylindrical_theta = registerCalculator("gc_cylindrical_theta", TileEntities.GC_CYLINDRICAL_THETA);
    public static final BlockGC block_gc_parabolic_cylindrical = registerCalculator("gc_parabolic_cylindrical", TileEntities.GC_PARABOLIC_CYLINDRICAL);
    public static final BlockGC block_gc_parabolic_cylindrical_sigma = registerCalculator("gc_parabolic_cylindrical_sigma", TileEntities.GC_PARABOLIC_CYLINDRICAL_SIGMA);
    public static final BlockGC block_gc_parabolic_cylindrical_tau = registerCalculator("gc_parabolic_cylindrical_tau", TileEntities.GC_PARABOLIC_CYLINDRICAL_TAU);
    public static final BlockGC block_gc_parabolic = registerCalculator("gc_parabolic", TileEntities.GC_PARABOLIC);
    public static final BlockGC block_gc_parabolic_tau = registerCalculator("gc_parabolic_tau", TileEntities.GC_PARABOLIC_TAU);
    public static final BlockGC block_gc_parabolic_phi = registerCalculator("gc_parabolic_phi", TileEntities.GC_PARABOLIC_PHI);
    public static final BlockGC block_gc_bipolar_cylindrical = registerCalculator("gc_bipolar_cylindrical", TileEntities.GC_BIPOLAR_CYLINDRICAL);
    public static final BlockGC block_gc_bipolar_cylindrical_sigma = registerCalculator("gc_bipolar_cylindrical_sigma", TileEntities.GC_BIPOLAR_CYLINDRICAL_SIGMA);
    public static final BlockGC block_gc_bipolar_cylindrical_tau = registerCalculator("gc_bipolar_cylindrical_tau", TileEntities.GC_BIPOLAR_CYLINDRICAL_TAU);
    public static final BlockGC block_gc_oblate_spheroidal = registerCalculator("gc_oblate_spheroidal", TileEntities.GC_OBLATE_SPHEROIDAL);
    public static final BlockGC block_gc_oblate_spheroidal_nu = registerCalculator("gc_oblate_spheroidal_nu", TileEntities.GC_OBLATE_SPHEROIDAL_NU);
    public static final BlockGC block_gc_oblate_spheroidal_phi = registerCalculator("gc_oblate_spheroidal_phi", TileEntities.GC_OBLATE_SPHEROIDAL_PHI);
    public static final BlockGC block_gc_prolate_spheroidal = registerCalculator("gc_prolate_spheroidal", TileEntities.GC_PROLATE_SPHEROIDAL);
    public static final BlockGC block_gc_prolate_spheroidal_nu = registerCalculator("gc_prolate_spheroidal_nu", TileEntities.GC_PROLATE_SPHEROIDAL_NU);
    public static final BlockGC block_gc_prolate_spheroidal_phi = registerCalculator("gc_prolate_spheroidal_phi", TileEntities.GC_PROLATE_SPHEROIDAL_PHI);
    public static final BlockGC block_gc_toroidal = registerCalculator("gc_toroidal", TileEntities.GC_TOROIDAL);
    public static final BlockGC block_gc_toroidal_phi = registerCalculator("gc_toroidal_phi", TileEntities.GC_TOROIDAL_PHI);
    public static final BlockGC block_gc_toroidal_tau = registerCalculator("gc_toroidal_tau", TileEntities.GC_TOROIDAL_TAU);
    public static final BlockGC block_gc_conical = registerCalculator("gc_conical", TileEntities.GC_CONICAL);
    public static final BlockGC block_gc_conical_mu = registerCalculator("gc_conical_mu", TileEntities.GC_CONICAL_MU);
    public static final BlockGC block_gc_conical_nu = registerCalculator("gc_conical_nu", TileEntities.GC_CONICAL_NU);
    public static final BlockGC block_gc_6_sphere = registerCalculator("gc_6_sphere", TileEntities.GC_6_SPHERE);
    public static final BlockGC block_gc_6_sphere_v = registerCalculator("gc_6_sphere_v", TileEntities.GC_6_SPHERE_V);
    public static final BlockGC block_gc_6_sphere_w = registerCalculator("gc_6_sphere_w", TileEntities.GC_6_SPHERE_W);

    public static final Block block_mesh_flat = null;
    public static final Block block_mesh_grid = null;

    private static BlockGC registerCalculator(String name, TileEntityType<? extends TileGCBase> type) {
        return register(name, properties -> new BlockGC(type, properties), CALCULATOR_PROPERTIES);
    }

    public static <T extends Block> T register(String name, Function<Block.Properties, T> function, Block.Properties properties) {
        T block = function.apply(properties);
        block.setRegistryName(GraphingCalculator3D.id(name));
        GCItems.registerBlock(name, block);

        BLOCKS.add(block);
        return block;
    }

    public static void register(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
    }
}