package graphingcalculator3d.common.util.events.register;

import com.google.common.collect.Sets;
import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.blocks.GCBlocks;
import graphingcalculator3d.common.gameplay.tile.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TileEntities {
    public static final DeferredRegister<TileEntityType<?>> REGISTER = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, GraphingCalculator3D.MODID);

    private static <T extends TileGCBase> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> supplier, RegistryObject<Block> block) {
        return REGISTER.register(name, () -> new TileEntityType<>(supplier, Sets.newHashSet(block.get()), null));
    }


    public static final RegistryObject<TileEntityType<TileGCBase>> GC_CARTESIAN = register("gc_cartesian", TileGCCartesian::new, GCBlocks.block_gc_cartesian);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_SPHERICAL = register("gc_spherical", TileGCSpherical::new, GCBlocks.block_gc_spherical);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_SPHERICAL_THETA = register("gc_spherical_theta", TileGCSphericalTheta::new, GCBlocks.block_gc_spherical_theta);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_SPHERICAL_PHI = register("gc_spherical_phi", TileGCSphericalPhi::new, GCBlocks.block_gc_spherical_phi);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_CYLINDRICAL = register("gc_cylindrical", TileGCCylindrical::new, GCBlocks.block_gc_cylindrical);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_CYLINDRICAL_H = register("gc_cylindrical_h", TileGCCylindricalH::new, GCBlocks.block_gc_cylindrical_h);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_CYLINDRICAL_THETA = register("gc_cylindrical_theta", TileGCCylindricalTheta::new, GCBlocks.block_gc_cylindrical_theta);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_PARABOLIC_CYLINDRICAL = register("gc_parabolic_cylindrical", TileGCParabolicCylindrical::new, GCBlocks.block_gc_parabolic_cylindrical);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_PARABOLIC_CYLINDRICAL_SIGMA = register("gc_parabolic_cylindrical_sigma", TileGCParabolicCylindricalSigma::new, GCBlocks.block_gc_parabolic_cylindrical_sigma);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_PARABOLIC_CYLINDRICAL_TAU = register("gc_parabolic_cylindrical_tau", TileGCParabolicCylindricalTau::new, GCBlocks.block_gc_parabolic_cylindrical_tau);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_PARABOLIC = register("gc_parabolic", TileGCParabolic::new, GCBlocks.block_gc_parabolic);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_PARABOLIC_TAU = register("gc_parabolic_tau", TileGCParabolicTau::new, GCBlocks.block_gc_parabolic_tau);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_PARABOLIC_PHI = register("gc_parabolic_phi", TileGCParabolicPhi::new, GCBlocks.block_gc_parabolic_phi);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_BIPOLAR_CYLINDRICAL = register("gc_bipolar_cylindrical", TileGCBipolarCylindrical::new, GCBlocks.block_gc_bipolar_cylindrical);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_BIPOLAR_CYLINDRICAL_SIGMA = register("gc_bipolar_cylindrical_sigma", TileGCBipolarCylindricalSigma::new, GCBlocks.block_gc_bipolar_cylindrical_sigma);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_BIPOLAR_CYLINDRICAL_TAU = register("gc_bipolar_cylindrical_tau", TileGCBipolarCylindricalTau::new, GCBlocks.block_gc_bipolar_cylindrical_tau);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_OBLATE_SPHEROIDAL = register("gc_oblate_spheroidal", TileGCOblateSpheroidal::new, GCBlocks.block_gc_oblate_spheroidal);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_OBLATE_SPHEROIDAL_NU = register("gc_oblate_spheroidal_nu", TileGCOblateSpheroidalNu::new, GCBlocks.block_gc_oblate_spheroidal_nu);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_OBLATE_SPHEROIDAL_PHI = register("gc_oblate_spheroidal_phi", TileGCOblateSpheroidalPhi::new, GCBlocks.block_gc_oblate_spheroidal_phi);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_PROLATE_SPHEROIDAL = register("gc_prolate_spheroidal", TileGCProlateSpheroidal::new, GCBlocks.block_gc_prolate_spheroidal);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_PROLATE_SPHEROIDAL_NU = register("gc_prolate_spheroidal_nu", TileGCProlateSpheroidalNu::new, GCBlocks.block_gc_prolate_spheroidal_nu);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_PROLATE_SPHEROIDAL_PHI = register("gc_prolate_spheroidal_phi", TileGCProlateSpheroidalPhi::new, GCBlocks.block_gc_prolate_spheroidal_phi);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_TOROIDAL = register("gc_toroidal", TileGCToroidal::new, GCBlocks.block_gc_toroidal);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_TOROIDAL_PHI = register("gc_toroidal_phi", TileGCToroidalPhi::new, GCBlocks.block_gc_toroidal_phi);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_TOROIDAL_TAU = register("gc_toroidal_tau", TileGCToroidalTau::new, GCBlocks.block_gc_toroidal_tau);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_CONICAL = register("gc_conical", TileGCConical::new, GCBlocks.block_gc_conical);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_CONICAL_MU = register("gc_conical_mu", TileGCConicalMu::new, GCBlocks.block_gc_conical_mu);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_CONICAL_NU = register("gc_conical_nu", TileGCConicalNu::new, GCBlocks.block_gc_conical_nu);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_6_SPHERE = register("gc_6_sphere", TileGC6Sphere::new, GCBlocks.block_gc_6_sphere);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_6_SPHERE_V = register("gc_6_sphere_v", TileGC6SphereV::new, GCBlocks.block_gc_6_sphere_v);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_6_SPHERE_W = register("gc_6_sphere_w", TileGC6SphereW::new, GCBlocks.block_gc_6_sphere_w);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_ELLIPTIC_CYLINDRICAL = register("gc_elliptic_cylindrical", TileGCEllipticCylindrical::new, GCBlocks.block_gc_elliptic_cylindrical);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_ELLIPTIC_CYLINDRICAL_MU = register("gc_elliptic_cylindrical_mu", TileGCEllipticCylindricalMu::new, GCBlocks.block_gc_elliptic_cylindrical_mu);
    public static final RegistryObject<TileEntityType<TileGCBase>> GC_ELLIPTIC_CYLINDRICAL_NU = register("gc_elliptic_cylindrical_nu", TileGCEllipticCylindricalNu::new, GCBlocks.block_gc_elliptic_cylindrical_nu);


	public static void register(IEventBus bus) {
        REGISTER.register(bus);
	}
}
