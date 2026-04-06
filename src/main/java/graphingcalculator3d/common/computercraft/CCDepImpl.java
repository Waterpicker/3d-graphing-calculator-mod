package graphingcalculator3d.common.computercraft;

import java.util.function.Function;

import dan200.computercraft.api.peripheral.IPeripheral;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * 1.20.1 note:
 * Old ComputerCraft peripheral providers are not used here. Register this class's
 * {@link #attachPeripherals(AttachCapabilitiesEvent)} method on the Forge event bus.
 */
public class CCDepImpl implements CCDep {
    public static final Capability<IPeripheral> CAPABILITY_PERIPHERAL = CapabilityManager.get(new CapabilityToken<>() {
    });

    private static final ResourceLocation PERIPHERAL_ID = ResourceLocation.fromNamespaceAndPath("graphingcalculator3d", "peripheral");

    @Override
    public void preInit() {
        EVENT_BUS.addListener(CCDepImpl::attachPeripherals);

        // No-op on 1.20.1. Peripheral registration is capability-based.
    }

    public static void attachPeripherals(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof TileGCBase graph) {
            PeripheralProvider.attach(event, graph, GraphPeripheral::new);
        }
    }

    private static final class PeripheralProvider<O extends BlockEntity> implements ICapabilityProvider {
        private final O blockEntity;
        private final Function<O, IPeripheral> factory;
        private LazyOptional<IPeripheral> peripheral;

        private PeripheralProvider(O blockEntity, Function<O, IPeripheral> factory) {
            this.blockEntity = blockEntity;
            this.factory = factory;
        }

        private static <O extends BlockEntity> void attach(
            AttachCapabilitiesEvent<BlockEntity> event,
            O blockEntity,
            Function<O, IPeripheral> factory
        ) {
            PeripheralProvider<O> provider = new PeripheralProvider<>(blockEntity, factory);
            event.addCapability(PERIPHERAL_ID, provider);
            event.addListener(provider::invalidate);
        }

        private void invalidate() {
            if (peripheral != null) peripheral.invalidate();
            peripheral = null;
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
            if (capability != CAPABILITY_PERIPHERAL) return LazyOptional.empty();
            if (blockEntity.isRemoved()) return LazyOptional.empty();

            LazyOptional<IPeripheral> current = peripheral;
            if (current == null) {
                current = LazyOptional.of(() -> factory.apply(blockEntity));
                peripheral = current;
            }
            return current.cast();
        }
    }
}
