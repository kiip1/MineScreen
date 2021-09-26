package nl.kiipdevelopment.minescreen.util.suppliers;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FunctionalInterface
public interface TriSupplier<A, B, C, R> {
    R get(A a, B b, C c);
}
