package com.csc396.repairshop.database;

public class RepairVehicles {

    private Repair repair;
    private Vehicle vehicle;

    public RepairVehicles(Repair repairArg, Vehicle vehicleArg) {
        repair = repairArg;
        vehicle = vehicleArg;
    }

    public Repair getRepair() {
        return repair;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
