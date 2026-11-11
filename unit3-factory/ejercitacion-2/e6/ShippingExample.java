package e6;

public class ShippingExample {
    public static void main(String[] args) {
        // CREATORS
        ShippingCreator[] creators = {
            new PostalShippingCreator(),
            new ExpressShippingCreator(),
            new PickupShippingCreator()
        };

        // TEST (With for each, to test inheritance)
        // 2 kg package, 300 km away
        for (ShippingCreator creator : creators) {
            System.out.println(creator.quote("Branch Mendoza Centro", 2, 300));
        }

        // Invalid destination for the pickup
        System.out.println(new PickupShippingCreator().quote("Av. San Martin 123", 2, 300));
    }
}


// Interface

interface ShippingService {
    boolean validateDestination(String destination);
    double getCost(double weight, double distance);
    int getDays(double distance);
}

class PostalShipping implements ShippingService {
    @Override
    public boolean validateDestination(String destination) {
        return !destination.isBlank();
    }

    @Override
    public double getCost(double weight, double distance) {
        return 300 + weight * 50 + distance * 0.5;
    }

    @Override
    public int getDays(double distance) {
        return 5 + (int) (distance / 500);
    }
}

class ExpressShipping implements ShippingService {
    @Override
    public boolean validateDestination(String destination) {
        return !destination.isBlank();
    }

    @Override
    public double getCost(double weight, double distance) {
        return 800 + weight * 120 + distance;
    }

    @Override
    public int getDays(double distance) {
        return 2;
    }
}

class PickupShipping implements ShippingService {
    @Override
    public boolean validateDestination(String destination) {
        // Only works with branches
        return destination.startsWith("Branch");
    }

    @Override
    public double getCost(double weight, double distance) {
        return 0;
    }

    @Override
    public int getDays(double distance) {
        return 3;
    }
}

// Creators

abstract class ShippingCreator {
    abstract ShippingService createService();

    // Template method: creates the service, validates the destination and quotes
    public String quote(String destination, double weight, double distance) {
        ShippingService service = createService();
        String name = service.getClass().getSimpleName();
        if (!service.validateDestination(destination)) {
            return name + ": invalid destination '" + destination + "'";
        }
        return name + ": $" + service.getCost(weight, distance)
            + ", " + service.getDays(distance) + " days";
    }
}

class PostalShippingCreator extends ShippingCreator {
    @Override
    ShippingService createService() {
        return new PostalShipping();
    }
}

class ExpressShippingCreator extends ShippingCreator {
    @Override
    ShippingService createService() {
        return new ExpressShipping();
    }
}

class PickupShippingCreator extends ShippingCreator {
    @Override
    ShippingService createService() {
        return new PickupShipping();
    }
}
