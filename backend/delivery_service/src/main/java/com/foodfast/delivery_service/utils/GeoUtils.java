package com.foodfast.delivery_service.utils;
import com.foodfast.delivery_service.dto.LocationDTO;
public class GeoUtils {

   public static double distance(double lat1, double lon1, double lat2, double lon2) {
        // Tính khoảng cách km
        final int R = 6371; // bán kính trái đất km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public static LocationDTO nextStep(LocationDTO from, LocationDTO to, double stepKm) {
        double distance = distance(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
        if (distance <= stepKm) return to;

        double ratio = stepKm / distance;
        double newLat = from.getLatitude() + (to.getLatitude() - from.getLatitude()) * ratio;
        double newLon = from.getLongitude() + (to.getLongitude() - from.getLongitude()) * ratio;
        return new LocationDTO(newLat, newLon);
    }
}
