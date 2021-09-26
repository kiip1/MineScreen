package nl.kiipdevelopment.minescreen.util;

import net.minestom.server.coordinate.Vec;
import org.jetbrains.annotations.ApiStatus;

// Stolen from Rayfast (https://github.com/EmortalMC/Rayfast/)
@ApiStatus.Internal
public final class Intersection3dUtils {
    private Intersection3dUtils() {}

    public static Vec planeIntersection(
        // Line
        double posX, double posY, double posZ, // Position vector
        double dirX, double dirY, double dirZ, // Direction vector
        // Plane
        double minX, double minY, double minZ,
        double adjX, double adjY, double adjZ,
        double maxX, double maxY, double maxZ
    ) {
        Vec vector = getIntersection(
            posX, posY, posZ,
            dirX, dirY, dirZ,

            minX, minY, minZ,
            adjX, adjY, adjZ,
            maxX, maxY, maxZ
        );

        double x = vector.x();
        double y = vector.y();
        double z = vector.z();

        int fits = 0;

        if ((minX != maxX) && MathUtils.isBetweenUnordered(x, minX, maxX)) {
            fits++;
        }

        if ((minY != maxY) && MathUtils.isBetweenUnordered(y, minY, maxY)) {
            fits++;
        }

        if ((minZ != maxZ) && MathUtils.isBetweenUnordered(z, minZ, maxZ)) {
            fits++;
        }

        if (fits < 2) {
            return null;
        }

        return vector;
    }

    public static Vec getIntersection(
        // Line
        double posX, double posY, double posZ, // Position vector
        double dirX, double dirY, double dirZ, // Direction vector
        // Plane
        double planeX, double planeY, double planeZ, // Plane point
        double planeDirX, double planeDirY, double planeDirZ // Plane normal
    ) {
        // Sensitive (speed oriented) code:
        double dotA = planeDirX * planeX + planeDirY * planeY + planeDirZ * planeZ;
        double dotB = planeDirX * posX + planeDirY * posY + planeDirZ * posZ;
        double dotC = planeDirX * dirX + planeDirY * dirY + planeDirZ * dirZ;
        double t = (dotA - dotB) / dotC;

        double x = posX + (dirX * t);
        double y = posY + (dirY * t);
        double z = posZ + (dirZ * t);

        return new Vec(x, y, z);
    }

    public static Vec getIntersection(
        // Line
        double posX, double posY, double posZ, // Position vector
        double dirX, double dirY, double dirZ, // Direction vector
        // Plane
        double minX, double minY, double minZ,
        double adjX, double adjY, double adjZ,
        double maxX, double maxY, double maxZ
    ) {

        double v1x = minX - adjX;
        double v1y = minY - adjY;
        double v1z = minZ - adjZ;
        double v2x = minX - maxX;
        double v2y = minY - maxY;
        double v2z = minZ - maxZ;

        double crossX = v1y * v2z - v2y * v1z;
        double crossY = v1z * v2x - v2z * v1x;
        double crossZ = v1x * v2y - v2x * v1y;

        return getIntersection(
            // Line
            posX, posY, posZ,
            dirX, dirY, dirZ,
            // Plane
            minX, minY, minZ,
            crossX, crossY, crossZ
        );
    }
}
