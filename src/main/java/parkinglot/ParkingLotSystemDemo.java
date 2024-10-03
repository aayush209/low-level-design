package parkinglot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import parkinglot.vehicle.Car;
import parkinglot.vehicle.MotorCycle;
import parkinglot.vehicle.Truck;
import parkinglot.vehicle.Vehicle;
import parkinglot.vehicle.VehicleType;

@Slf4j
public class ParkingLotSystemDemo {

    public static void main(String[] args) {
        // Initialize Parking Spots for each Level
        List<ParkingSpot> level1Spots = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            if (i <= 2) {
                level1Spots.add(new ParkingSpot("L1-M" + i, VehicleType.MOTORCYCLE));
            } else if (i <= 7) {
                level1Spots.add(new ParkingSpot("L1-C" + i, VehicleType.CAR));
            } else {
                level1Spots.add(new ParkingSpot("L1-T" + i, VehicleType.TRUCK));
            }
        }

        List<ParkingSpot> level2Spots = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            if (i <= 3) {
                level2Spots.add(new ParkingSpot("L2-M" + i, VehicleType.MOTORCYCLE));
            } else if (i <= 8) {
                level2Spots.add(new ParkingSpot("L2-C" + i, VehicleType.CAR));
            } else {
                level2Spots.add(new ParkingSpot("L2-T" + i, VehicleType.TRUCK));
            }
        }

        // Initialize Levels
        Level level1 = new Level(1, level1Spots);
        Level level2 = new Level(2, level2Spots);

        // Initialize Parking Lot
        ParkingLot parkingLot = new ParkingLot(Arrays.asList(level1, level2));

        // Executor Service to Simulate Concurrent Entry and Exit
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // List to keep track of tickets
        List<ParkingTicket> tickets = Collections.synchronizedList(new ArrayList<>());

        // Simulate Parking Vehicles
        Runnable parkTask =
                () -> {
                    Vehicle vehicle;
                    // Randomly decide vehicle type
                    int rand = new Random().nextInt(3);
                    vehicle =
                            switch (rand) {
                                case 0 -> new MotorCycle("MC-" + UUID.randomUUID().toString().substring(0, 5));
                                case 1 -> new Car("CAR-" + UUID.randomUUID().toString().substring(0, 5));
                                default -> new Truck("TRK-" + UUID.randomUUID().toString().substring(0, 5));
                            };

                    try {
                        ParkingTicket ticket = parkingLot.parkVehicle(vehicle);
                        tickets.add(ticket);
                    } catch (Exception e) {
                        log.info(
                                "Failed to park vehicle: {}. Reason: {}",
                                vehicle.getLicensePlate(),
                                e.getMessage());
                    }
                };

        // Simulate Unparking Vehicles
        Runnable unparkTask =
                () -> {
                    if (!tickets.isEmpty()) {
                        ParkingTicket ticket;
                        synchronized (tickets) {
                            if (tickets.isEmpty()) {
                                return;
                            }
                            ticket = tickets.remove(0);
                        }
                        try {
                            parkingLot.unparkVehicle(ticket.getTicketId());
                        } catch (Exception e) {
                            log.info(
                                    "Failed to unpark vehicle with Ticket ID: {}. Reason: {}",
                                    ticket.getTicketId(),
                                    e.getMessage());
                        }
                    }
                };

        // Submit Parking Tasks
        for (int i = 0; i < 15; i++) {
            executor.submit(parkTask);
        }

        // Wait a bit before starting to unpark
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Submit Unparking Tasks
        for (int i = 0; i < 10; i++) {
            executor.submit(unparkTask);
        }

        // Shutdown Executor
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display Available Spots
        Map<VehicleType, Long> availability = parkingLot.getAvailableSpots();
        log.info("\nAvailable Spots:");

        for (Map.Entry<VehicleType, Long> entry : availability.entrySet()) {
            log.info("{} : {}", entry.getKey(), entry.getValue());
        }
    }
}
