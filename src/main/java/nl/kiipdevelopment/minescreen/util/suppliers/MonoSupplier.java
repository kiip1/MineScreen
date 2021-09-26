package nl.kiipdevelopment.minescreen.util.suppliers;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FunctionalInterface
public interface MonoSupplier<A, R> {
    R get(A a);
}
