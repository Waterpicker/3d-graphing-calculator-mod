package graphingcalculator3d.common.util.events.register;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.tile.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class TileEntities {
    public static final TileEntityType<TileGCCartesian> GC_CARTESIAN = register("gc_cartesian", () -> new TileGCCartesian());
    public static final TileEntityType<TileGCSpherical> GC_SPHERICAL = register("gc_spherical", () -> new TileGCSpherical());
    public static final TileEntityType<TileGCSphericalTheta> GC_SPHERICAL_THETA = register("gc_spherical_theta", () -> new TileGCSphericalTheta());
    public static final TileEntityType<TileGCSphericalPhi> GC_SPHERICAL_PHI = register("gc_spherical_phi", () -> new TileGCSphericalPhi());
    public static final TileEntityType<TileGCCylindrical> GC_CYLINDRICAL = register("gc_cylindrical", () -> new TileGCCylindrical());
    public static final TileEntityType<TileGCCylindricalH> GC_CYLINDRICAL_H = register("gc_cylindrical_h", () -> new TileGCCylindricalH());
    public static final TileEntityType<TileGCCylindricalTheta> GC_CYLINDRICAL_THETA = register("gc_cylindrical_theta", () -> new TileGCCylindricalTheta());
    public static final TileEntityType<TileGCParabolicCylindrical> GC_PARABOLIC_CYLINDRICAL = register("gc_parabolic_cylindrical", () -> new TileGCParabolicCylindrical());
    public static final TileEntityType<TileGCParabolicCylindricalSigma> GC_PARABOLIC_CYLINDRICAL_SIGMA = register("gc_parabolic_cylindrical_sigma", () -> new TileGCParabolicCylindricalSigma());
    public static final TileEntityType<TileGCParabolicCylindricalTau> GC_PARABOLIC_CYLINDRICAL_TAU = register("gc_parabolic_cylindrical_tau", () -> new TileGCParabolicCylindricalTau());
    public static final TileEntityType<TileGCParabolic> GC_PARABOLIC = register("gc_parabolic", () -> new TileGCParabolic());
    public static final TileEntityType<TileGCParabolicTau> GC_PARABOLIC_TAU = register("gc_parabolic_tau", () -> new TileGCParabolicTau());
    public static final TileEntityType<TileGCParabolicPhi> GC_PARABOLIC_PHI = register("gc_parabolic_phi", () -> new TileGCParabolicPhi());
    public static final TileEntityType<TileGCBipolarCylindrical> GC_BIPOLAR_CYLINDRICAL = register("gc_bipolar_cylindrical", () -> new TileGCBipolarCylindrical());
    public static final TileEntityType<TileGCBipolarCylindricalSigma> GC_BIPOLAR_CYLINDRICAL_SIGMA = register("gc_bipolar_cylindrical_sigma", () -> new TileGCBipolarCylindricalSigma());
    public static final TileEntityType<TileGCBipolarCylindricalTau> GC_BIPOLAR_CYLINDRICAL_TAU = register("gc_bipolar_cylindrical_tau", () -> new TileGCBipolarCylindricalTau());
    public static final TileEntityType<TileGCOblateSpheroidal> GC_OBLATE_SPHEROIDAL = register("gc_oblate_spheroidal", () -> new TileGCOblateSpheroidal());
    public static final TileEntityType<TileGCOblateSpheroidalNu> GC_OBLATE_SPHEROIDAL_NU = register("gc_oblate_spheroidal_nu", () -> new TileGCOblateSpheroidalNu());
    public static final TileEntityType<TileGCOblateSpheroidalPhi> GC_OBLATE_SPHEROIDAL_PHI = register("gc_oblate_spheroidal_phi", () -> new TileGCOblateSpheroidalPhi());
    public static final TileEntityType<TileGCProlateSpheroidal> GC_PROLATE_SPHEROIDAL = register("gc_prolate_spheroidal", () -> new TileGCProlateSpheroidal());
    public static final TileEntityType<TileGCProlateSpheroidalNu> GC_PROLATE_SPHEROIDAL_NU = register("gc_prolate_spheroidal_nu", () -> new TileGCProlateSpheroidalNu());
    public static final TileEntityType<TileGCProlateSpheroidalPhi> GC_PROLATE_SPHEROIDAL_PHI = register("gc_prolate_spheroidal_phi", () -> new TileGCProlateSpheroidalPhi());
    public static final TileEntityType<TileGCToroidal> GC_TOROIDAL = register("gc_toroidal", () -> new TileGCToroidal());
    public static final TileEntityType<TileGCToroidalPhi> GC_TOROIDAL_PHI = register("gc_toroidal_phi", () -> new TileGCToroidalPhi());
    public static final TileEntityType<TileGCToroidalTau> GC_TOROIDAL_TAU = register("gc_toroidal_tau", () -> new TileGCToroidalTau());
    public static final TileEntityType<TileGCConical> GC_CONICAL = register("gc_conical", () -> new TileGCConical());
    public static final TileEntityType<TileGCConicalMu> GC_CONICAL_MU = register("gc_conical_mu", () -> new TileGCConicalMu());
    public static final TileEntityType<TileGCConicalNu> GC_CONICAL_NU = register("gc_conical_nu", () -> new TileGCConicalNu());
    public static final TileEntityType<TileGC6Sphere> GC_6_SPHERE = register("gc_6_sphere", () -> new TileGC6Sphere());
    public static final TileEntityType<TileGC6SphereV> GC_6_SPHERE_V = register("gc_6_sphere_v", () -> new TileGC6SphereV());
    public static final TileEntityType<TileGC6SphereW> GC_6_SPHERE_W = register("gc_6_sphere_w", () -> new TileGC6SphereW());
    public static final TileEntityType<TileGCEllipticCylindrical> GC_ELLIPTIC_CYLINDRICAL = register("gc_elliptic_cylindrical", () -> new TileGCEllipticCylindrical());
    public static final TileEntityType<TileGCEllipticCylindricalMu> GC_ELLIPTIC_CYLINDRICAL_MU = register("gc_elliptic_cylindrical_mu", () -> new TileGCEllipticCylindricalMu());
    public static final TileEntityType<TileGCEllipticCylindricalNu> GC_ELLIPTIC_CYLINDRICAL_NU = register("gc_elliptic_cylindrical_nu", () -> new TileGCEllipticCylindricalNu());

    private static <T extends TileGCBase> TileEntityType<T> register(String name, Supplier<T> supplier) {
        return (TileEntityType<T>) new TileEntityType<>(supplier, null).setRegistryName(GraphingCalculator3D.id(name));
    }

	public static void register(RegistryEvent.Register<TileEntityType<?>> event) {
        IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();


        registry.register(GC_CARTESIAN);
        registry.register(GC_SPHERICAL);
        registry.register(GC_SPHERICAL_THETA);
        registry.register(GC_SPHERICAL_PHI);
        registry.register(GC_CYLINDRICAL);
        registry.register(GC_CYLINDRICAL_H);
        registry.register(GC_CYLINDRICAL_THETA);
        registry.register(GC_PARABOLIC_CYLINDRICAL);
        registry.register(GC_PARABOLIC_CYLINDRICAL_SIGMA);
        registry.register(GC_PARABOLIC_CYLINDRICAL_TAU);
        registry.register(GC_PARABOLIC);
        registry.register(GC_PARABOLIC_TAU);
        registry.register(GC_PARABOLIC_PHI);
        registry.register(GC_BIPOLAR_CYLINDRICAL);
        registry.register(GC_BIPOLAR_CYLINDRICAL_SIGMA);
        registry.register(GC_BIPOLAR_CYLINDRICAL_TAU);
        registry.register(GC_OBLATE_SPHEROIDAL);
        registry.register(GC_OBLATE_SPHEROIDAL_NU);
        registry.register(GC_OBLATE_SPHEROIDAL_PHI);
        registry.register(GC_PROLATE_SPHEROIDAL);
        registry.register(GC_PROLATE_SPHEROIDAL_NU);
        registry.register(GC_PROLATE_SPHEROIDAL_PHI);
        registry.register(GC_TOROIDAL);
        registry.register(GC_TOROIDAL_PHI);
        registry.register(GC_TOROIDAL_TAU);
        registry.register(GC_CONICAL);
        registry.register(GC_CONICAL_MU);
        registry.register(GC_CONICAL_NU);
        registry.register(GC_6_SPHERE);
        registry.register(GC_6_SPHERE_V);
        registry.register(GC_6_SPHERE_W);
        registry.register(GC_ELLIPTIC_CYLINDRICAL);
        registry.register(GC_ELLIPTIC_CYLINDRICAL_MU);
        registry.register(GC_ELLIPTIC_CYLINDRICAL_NU);

	}
}
