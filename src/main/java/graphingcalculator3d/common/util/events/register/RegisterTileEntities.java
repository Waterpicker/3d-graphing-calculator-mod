package graphingcalculator3d.common.util.events.register;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.tile.TileGC6Sphere;
import graphingcalculator3d.common.gameplay.tile.TileGC6SphereV;
import graphingcalculator3d.common.gameplay.tile.TileGC6SphereW;
import graphingcalculator3d.common.gameplay.tile.TileGCBipolarCylindrical;
import graphingcalculator3d.common.gameplay.tile.TileGCBipolarCylindricalSigma;
import graphingcalculator3d.common.gameplay.tile.TileGCBipolarCylindricalTau;
import graphingcalculator3d.common.gameplay.tile.TileGCCartesian;
import graphingcalculator3d.common.gameplay.tile.TileGCConical;
import graphingcalculator3d.common.gameplay.tile.TileGCConicalMu;
import graphingcalculator3d.common.gameplay.tile.TileGCConicalNu;
import graphingcalculator3d.common.gameplay.tile.TileGCCylindrical;
import graphingcalculator3d.common.gameplay.tile.TileGCCylindricalH;
import graphingcalculator3d.common.gameplay.tile.TileGCCylindricalTheta;
import graphingcalculator3d.common.gameplay.tile.TileGCEllipticCylindrical;
import graphingcalculator3d.common.gameplay.tile.TileGCEllipticCylindricalMu;
import graphingcalculator3d.common.gameplay.tile.TileGCEllipticCylindricalNu;
import graphingcalculator3d.common.gameplay.tile.TileGCOblateSpheroidal;
import graphingcalculator3d.common.gameplay.tile.TileGCOblateSpheroidalNu;
import graphingcalculator3d.common.gameplay.tile.TileGCOblateSpheroidalPhi;
import graphingcalculator3d.common.gameplay.tile.TileGCParabolic;
import graphingcalculator3d.common.gameplay.tile.TileGCParabolicCylindrical;
import graphingcalculator3d.common.gameplay.tile.TileGCParabolicCylindricalSigma;
import graphingcalculator3d.common.gameplay.tile.TileGCParabolicCylindricalTau;
import graphingcalculator3d.common.gameplay.tile.TileGCParabolicTau;
import graphingcalculator3d.common.gameplay.tile.TileGCParabolicPhi;
import graphingcalculator3d.common.gameplay.tile.TileGCProlateSpheroidal;
import graphingcalculator3d.common.gameplay.tile.TileGCProlateSpheroidalNu;
import graphingcalculator3d.common.gameplay.tile.TileGCProlateSpheroidalPhi;
import graphingcalculator3d.common.gameplay.tile.TileGCSpherical;
import graphingcalculator3d.common.gameplay.tile.TileGCSphericalPhi;
import graphingcalculator3d.common.gameplay.tile.TileGCSphericalTheta;
import graphingcalculator3d.common.gameplay.tile.TileGCToroidal;
import graphingcalculator3d.common.gameplay.tile.TileGCToroidalPhi;
import graphingcalculator3d.common.gameplay.tile.TileGCToroidalTau;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class RegisterTileEntities {
    public static final TileEntityType<TileGCCartesian> GC_CARTESIAN = new TileEntityType<>()             GameRegistry.registerTileEntity(TileGCCartesian.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_cartesian"));
    public static final TileEntityType<TileGCSpherical> GC_SPHERICAL = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCSpherical.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_spherical"));
    public static final TileEntityType<TileGCSphericalTheta> GC_SPHERICAL_THETA = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCSphericalTheta.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_spherical_theta"));
    public static final TileEntityType<TileGCSphericalPhi> GC_SPHERICAL_PHI = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCSphericalPhi.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_spherical_phi"));
    public static final TileEntityType<TileGCCylindrical> GC_CYLINDRICAL = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCCylindrical.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_cylindrical"));
    public static final TileEntityType<TileGCCylindricalH> GC_CYLINDRICAL_H = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCCylindricalH.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_cylindrical_h"));
    public static final TileEntityType<TileGCCylindricalTheta> GC_CYLINDRICAL_THETA = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCCylindricalTheta.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_cylindrical_theta"));
    public static final TileEntityType<TileGCParabolicCylindrical> GC_PARABOLIC_CYLINDRICAL = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCParabolicCylindrical.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_parabolic_cylindrical"));
    public static final TileEntityType<TileGCParabolicCylindricalSigma> GC_PARABOLIC_CYLINDRICAL_SIGMA = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCParabolicCylindricalSigma.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_parabolic_cylindrical_sigma"));
    public static final TileEntityType<TileGCParabolicCylindricalTau> GC_PARABOLIC_CYLINDRICAL_TAU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCParabolicCylindricalTau.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_parabolic_cylindrical_tau"));
    public static final TileEntityType<TileGCParabolic> GC_PARABOLIC = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCParabolic.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_parabolic"));
    public static final TileEntityType<TileGCParabolicTau> GC_PARABOLIC_TAU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCParabolicTau.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_parabolic_tau"));
    public static final TileEntityType<TileGCParabolicPhi> GC_PARABOLIC_PHI = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCParabolicPhi.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_parabolic_phi"));
    public static final TileEntityType<TileGCBipolarCylindrical> GC_BIPOLAR_CYLINDRICAL = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCBipolarCylindrical.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_bipolar_cylindrical"));
    public static final TileEntityType<TileGCBipolarCylindricalSigma> GC_BIPOLAR_CYLINDRICAL_SIGMA = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCBipolarCylindricalSigma.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_bipolar_cylindrical_sigma"));
    public static final TileEntityType<TileGCBipolarCylindricalTau> GC_BIPOLAR_CYLINDRICAL_TAU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCBipolarCylindricalTau.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_bipolar_cylindrical_tau"));
    public static final TileEntityType<TileGCOblateSpheroidal> GC_OBLATE_SPHEROIDAL = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCOblateSpheroidal.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_oblate_spheroidal"));
    public static final TileEntityType<TileGCOblateSpheroidalNu> GC_OBLATE_SPHEROIDAL_NU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCOblateSpheroidalNu.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_oblate_spheroidal_nu"));
    public static final TileEntityType<TileGCOblateSpheroidalPhi> GC_OBLATE_SPHEROIDAL_PHI = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCOblateSpheroidalPhi.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_oblate_spheroidal_phi"));
    public static final TileEntityType<TileGCProlateSpheroidal> GC_PROLATE_SPHEROIDAL = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCProlateSpheroidal.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_prolate_spheroidal"));
    public static final TileEntityType<TileGCProlateSpheroidalNu> GC_PROLATE_SPHEROIDAL_NU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCProlateSpheroidalNu.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_prolate_spheroidal_nu"));
    public static final TileEntityType<TileGCProlateSpheroidalPhi> GC_PROLATE_SPHEROIDAL_PHI = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCProlateSpheroidalPhi.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_prolate_spheroidal_phi"));
    public static final TileEntityType<TileGCToroidal> GC_TOROIDAL = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCToroidal.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_toroidal"));
    public static final TileEntityType<TileGCToroidalPhi> GC_TOROIDAL_PHI = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCToroidalPhi.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_toroidal_phi"));
    public static final TileEntityType<TileGCToroidalTau> GC_TOROIDAL_TAU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCToroidalTau.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_toroidal_tau"));
    public static final TileEntityType<TileGCConical> GC_CONICAL = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCConical.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_conical"));
    public static final TileEntityType<TileGCConicalMu> GC_CONICAL_MU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCConicalMu.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_conical_mu"));
    public static final TileEntityType<TileGCConicalNu> GC_CONICAL_NU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCConicalNu.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_conical_nu"));
    public static final TileEntityType<TileGC6Sphere> GC_6_SPHERE = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGC6Sphere.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_6_sphere"));
    public static final TileEntityType<TileGC6SphereV> GC_6_SPHERE_V = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGC6SphereV.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_6_sphere_v"));
    public static final TileEntityType<TileGC6SphereW> GC_6_SPHERE_W = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGC6SphereW.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_6_sphere_w"));
    public static final TileEntityType<TileGCEllipticCylindrical> GC_ELLIPTIC_CYLINDRICAL = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCEllipticCylindrical.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_elliptic_cylindrical"));
    public static final TileEntityType<TileGCEllipticCylindricalMu> GC_ELLIPTIC_CYLINDRICAL_MU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCEllipticCylindricalMu.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_elliptic_cylindrical_mu"));
    public static final TileEntityType<TileGCEllipticCylindricalNu> GC_ELLIPTIC_CYLINDRICAL_NU = new TileEntityType<>()         GameRegistry.registerTileEntity(TileGCEllipticCylindricalNu.class, new ResourceLocation(GraphingCalculator3D.MODID + ":tile_gc_elliptic_cylindrical_nu"));

    @SubscribeEvent
	public static void register(RegistryEvent.Register<TileEntityType<?>> event) {
        IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();

        registry.

	}
}
