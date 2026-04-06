package graphingcalculator3d.common;

//import graphingcalculator3d.common.computercraft.CCDep;
//import graphingcalculator3d.common.computercraft.CCDepDummy;

import graphingcalculator3d.common.computercraft.CCDep;
import graphingcalculator3d.common.computercraft.CCDepDummy;
import graphingcalculator3d.common.util.events.GCEvents;
import graphingcalculator3d.common.util.events.register.RegisterEventHandlers;
import graphingcalculator3d.common.util.math.expression.Evaluations;
import graphingcalculator3d.common.util.networking.GCPacketHandler;
import graphingcalculator3d.proxy.ClientProxy;
import graphingcalculator3d.proxy.IProxy;
import graphingcalculator3d.proxy.ServerProxy;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GraphingCalculator3D.MODID)
public class GraphingCalculator3D {
    /// /////////////////////////////Mod Variables

    public static final String MODID = "graphingcalculator3d";
    public static final String NAME = "3D Graphing Calculators";
    public static final String VERSION = "1.9.1";

    /// //////////////////////////////////////////////

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static GraphingCalculator3D instance;
    public static CCDep ccDep;

    public GraphingCalculator3D() {
        instance = this;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::preInit);
        RegisterEventHandlers.register(bus);
        proxy.onConstruct(bus);
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(GraphingCalculator3D.MODID, name);
    }

    public void preInit(FMLCommonSetupEvent event) {
        GCPacketHandler.registerPackets();
        GCEvents.setupDomain();
        try {
            Evaluations.loadMappings();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        proxy.preInit();

        // CC:Tweaked dependency instantiation
        if (ModList.get().isLoaded("computercraft"))
            try {
                ccDep = Class.forName("graphingcalculator3d.common.computercraft.CCDepImpl").asSubclass(CCDep.class).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        else
            ccDep = new CCDepDummy();

        ccDep.preInit();
    }
}
