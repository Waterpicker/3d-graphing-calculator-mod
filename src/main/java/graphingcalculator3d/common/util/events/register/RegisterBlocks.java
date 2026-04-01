package graphingcalculator3d.common.util.events.register;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.blocks.BlockGC;
import graphingcalculator3d.common.gameplay.blocks.GCBlocks;
import graphingcalculator3d.common.gameplay.blocks.SetupBlock;
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
import graphingcalculator3d.common.gameplay.tile.TileGCParabolicPhi;
import graphingcalculator3d.common.gameplay.tile.TileGCParabolicTau;
import graphingcalculator3d.common.gameplay.tile.TileGCProlateSpheroidal;
import graphingcalculator3d.common.gameplay.tile.TileGCProlateSpheroidalNu;
import graphingcalculator3d.common.gameplay.tile.TileGCProlateSpheroidalPhi;
import graphingcalculator3d.common.gameplay.tile.TileGCSpherical;
import graphingcalculator3d.common.gameplay.tile.TileGCSphericalPhi;
import graphingcalculator3d.common.gameplay.tile.TileGCSphericalTheta;
import graphingcalculator3d.common.gameplay.tile.TileGCToroidal;
import graphingcalculator3d.common.gameplay.tile.TileGCToroidalPhi;
import graphingcalculator3d.common.gameplay.tile.TileGCToroidalTau;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisterBlocks
{
	@SubscribeEvent
	public void registerBlocks(Register<Block> event)
	{
		event.getRegistry().registerAll(SetupBlock.calculator(new BlockGC(ModTileGCCartesian.class, Material.ROCK), "block_gc_cartesian"),
				SetupBlock.calculator(new BlockGC(TileGCSpherical.class, Material.ROCK), "block_gc_spherical"),
				SetupBlock.calculator(new BlockGC(TileGCSphericalPhi.class, Material.ROCK), "block_gc_spherical_phi"),
				SetupBlock.calculator(new BlockGC(TileGCSphericalTheta.class, Material.ROCK), "block_gc_spherical_theta"),
				SetupBlock.calculator(new BlockGC(TileGCCylindrical.class, Material.ROCK), "block_gc_cylindrical"),
				SetupBlock.calculator(new BlockGC(TileGCCylindricalH.class, Material.ROCK), "block_gc_cylindrical_h"),
				SetupBlock.calculator(new BlockGC(TileGCCylindricalTheta.class, Material.ROCK), "block_gc_cylindrical_theta"),
				SetupBlock.calculator(new BlockGC(TileGCParabolicCylindrical.class, Material.ROCK), "block_gc_parabolic_cylindrical"),
				SetupBlock.calculator(new BlockGC(TileGCParabolicCylindricalSigma.class, Material.ROCK), "block_gc_parabolic_cylindrical_sigma"),
				SetupBlock.calculator(new BlockGC(TileGCParabolicCylindricalTau.class, Material.ROCK), "block_gc_parabolic_cylindrical_tau"),
				SetupBlock.calculator(new BlockGC(TileGCParabolic.class, Material.ROCK), "block_gc_parabolic"),
				SetupBlock.calculator(new BlockGC(TileGCParabolicTau.class, Material.ROCK), "block_gc_parabolic_tau"),
				SetupBlock.calculator(new BlockGC(TileGCParabolicPhi.class, Material.ROCK), "block_gc_parabolic_phi"),
				SetupBlock.calculator(new BlockGC(TileGCBipolarCylindrical.class, Material.ROCK), "block_gc_bipolar_cylindrical"),
				SetupBlock.calculator(new BlockGC(TileGCBipolarCylindricalSigma.class, Material.ROCK), "block_gc_bipolar_cylindrical_sigma"),
				SetupBlock.calculator(new BlockGC(TileGCBipolarCylindricalTau.class, Material.ROCK), "block_gc_bipolar_cylindrical_tau"),
				SetupBlock.calculator(new BlockGC(TileGCOblateSpheroidal.class, Material.ROCK), "block_gc_oblate_spheroidal"),
				SetupBlock.calculator(new BlockGC(TileGCOblateSpheroidalNu.class, Material.ROCK), "block_gc_oblate_spheroidal_nu"),
				SetupBlock.calculator(new BlockGC(TileGCOblateSpheroidalPhi.class, Material.ROCK), "block_gc_oblate_spheroidal_phi"),
				SetupBlock.calculator(new BlockGC(TileGCProlateSpheroidal.class, Material.ROCK), "block_gc_prolate_spheroidal"),
				SetupBlock.calculator(new BlockGC(TileGCProlateSpheroidalNu.class, Material.ROCK), "block_gc_prolate_spheroidal_nu"),
				SetupBlock.calculator(new BlockGC(TileGCProlateSpheroidalPhi.class, Material.ROCK), "block_gc_prolate_spheroidal_phi"),
				SetupBlock.calculator(new BlockGC(TileGCToroidal.class, Material.ROCK), "block_gc_toroidal"),
				SetupBlock.calculator(new BlockGC(TileGCToroidalPhi.class, Material.ROCK), "block_gc_toroidal_phi"),
				SetupBlock.calculator(new BlockGC(TileGCToroidalTau.class, Material.ROCK), "block_gc_toroidal_tau"),
				SetupBlock.calculator(new BlockGC(TileGCConical.class, Material.ROCK), "block_gc_conical"),
				SetupBlock.calculator(new BlockGC(TileGCConicalMu.class, Material.ROCK), "block_gc_conical_mu"),
				SetupBlock.calculator(new BlockGC(TileGCConicalNu.class, Material.ROCK), "block_gc_conical_nu"),
				SetupBlock.calculator(new BlockGC(TileGC6Sphere.class, Material.ROCK), "block_gc_6_sphere"),
				SetupBlock.calculator(new BlockGC(TileGC6SphereV.class, Material.ROCK), "block_gc_6_sphere_v"),
				SetupBlock.calculator(new BlockGC(TileGC6SphereW.class, Material.ROCK), "block_gc_6_sphere_w"),
				SetupBlock.calculator(new BlockGC(TileGCEllipticCylindrical.class, Material.ROCK), "block_gc_elliptic_cylindrical"),
				SetupBlock.calculator(new BlockGC(TileGCEllipticCylindricalMu.class, Material.ROCK), "block_gc_elliptic_cylindrical_mu"),
				SetupBlock.calculator(new BlockGC(TileGCEllipticCylindricalNu.class, Material.ROCK), "block_gc_elliptic_cylindrical_nu"),
				
				SetupBlock.basicBlock(new Block(Material.ROCK), "block_mesh_flat"), SetupBlock.basicBlock(new Block(Material.ROCK), "block_mesh_grid"));
		
		for (Block current : event.getRegistry().getValuesCollection())
		{
			if (current.getRegistryName().getResourceDomain().equals(GraphingCalculator3D.MODID))
			{
				GCBlocks.gcBlockList.add(current);
			}
		}
		
		System.out.println("GraphingCalculator3D blocks registered");
	}

}
