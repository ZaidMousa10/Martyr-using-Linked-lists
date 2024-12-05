package com.example.project;

public class District implements Comparable<District> {
    private String districtName;
    private SLinkedList<Location> sLocation;
    private int currentLocationIndex = -1; // Track current location index


    public District(String districtName) {
        this.districtName = districtName;
        this.sLocation = new SLinkedList<>();
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public SLinkedList<Location> getsLocation() {
        return sLocation;
    }
    public Location getCurrentLocation() {
        if (currentLocationIndex >= 0 && currentLocationIndex < sLocation.length()) {
            return sLocation.get(currentLocationIndex);
        }
        return null;
    }

    //O(l)
    public String nextLocation() {
        if (sLocation.isEmpty()) {
            return null; // No locations to navigate
        }

        // Increment current location index to move to the next location
        currentLocationIndex = (currentLocationIndex + 1) % sLocation.length();

        // Skip over any dummy head locations (e.g., locations with empty names)
        while (currentLocationIndex < sLocation.length() && isEmptyLocation(sLocation.get(currentLocationIndex))) {
            currentLocationIndex = (currentLocationIndex + 1) % sLocation.length();
        }

        // If the current index is valid, return the location name
        if (currentLocationIndex < sLocation.length()) {
            return sLocation.get(currentLocationIndex).getLocationName();
        }

        return null; // Return null if no valid location is found
    }

    // Helper method to check if a location is considered empty or a dummy head
    //O(1)
    private boolean isEmptyLocation(Location location) {
        // You can define criteria here to determine if the location is a dummy head
        return location.getLocationName() == null || location.getLocationName().isEmpty();
    }


    //O(l^2 *n)
    public int getTotalMartyrs() {
        int totalMartyrs = 0;
        for (int i = 0; i < sLocation.length(); i++) {
            Location location = sLocation.get(i);
            totalMartyrs += location.getTotalMartyrs();
        }
        return totalMartyrs;
    }

    //O(l^2 *n^2)
    public int getTotalMaleMartyrs() {
        int totalMaleMartyrs = 0;
        for (int i = 0; i < sLocation.length(); i++) {
            Location location = sLocation.get(i);
            totalMaleMartyrs += location.getTotalMaleMartyrs();
        }
        return totalMaleMartyrs;
    }

    //O(l^2 *n^2)
    public int getTotalFemaleMartyrs() {
        int totalFemaleMartyrs = 0;
        for (int i = 0; i < sLocation.length(); i++) {
            Location location = sLocation.get(i);
            totalFemaleMartyrs += location.getTotalFemaleMartyrs();
        }
        return totalFemaleMartyrs;
    }

    //O(l^2 *((n^2+ n)*n))
    public double getAverageMartyrAge() {
        double totalAges = 0;
        int totalMartyrs = 0;
        for (int i = 0; i < sLocation.length(); i++) {
            Location location = sLocation.get(i);
            totalAges += location.getAverageMartyrAge() * location.getTotalMartyrs();
            totalMartyrs += location.getTotalMartyrs();
        }
        return totalMartyrs > 0 ? totalAges / totalMartyrs : 0;
    }

    public String getDateWithMaxMartyrs() {
        int maxMartyrs = 0;
        String dateWithMaxMartyrs = "";

        // Initialize location iterator
        SNode<Location> locationNode = sLocation.getsDummyHead().getNext();

        // Iterate over each location in the district
        while (locationNode != null) {
            Location location = locationNode.getData();

            // Initialize martyr iterator for the current location
            SNode<Martyr> martyrNode = location.getSMartyr().getsDummyHead().getNext();

            // Iterate over each martyr in the location
            while (martyrNode != null) {
                Martyr martyr = martyrNode.getData();
                String dateOfDeath = martyr.getDateOfDeath();

                // Count the number of martyrs with the same date of death across all locations
                int count = countMartyrsByDate(dateOfDeath);

                // Update maxMartyrs and dateWithMaxMartyrs if we find a date with more martyrs
                if (count > maxMartyrs) {
                    maxMartyrs = count;
                    dateWithMaxMartyrs = dateOfDeath;
                }

                // Move to the next martyr in the current location
                martyrNode = martyrNode.getNext();
            }

            // Move to the next location in the district
            locationNode = locationNode.getNext();
        }

        return dateWithMaxMartyrs;
    }

    public int countMartyrsByDate(String dateToCount) {
        int count = 0;

        // Initialize location
        SNode<Location> locationNode = sLocation.getsDummyHead().getNext();

        // Iterate over each location in the district
        while (locationNode != null) {
            Location location = locationNode.getData();

            // Initialize martyr iterator for the current location
            SNode<Martyr> martyrNode = location.getSMartyr().getsDummyHead().getNext();

            // Iterate over each martyr in the current location
            while (martyrNode != null) {
                Martyr martyr = martyrNode.getData();
                String dateOfDeath = martyr.getDateOfDeath();

                // Check if the date of death matches the specified date
                if (dateOfDeath.equals(dateToCount)) {
                    count++;
                }

                // Move to the next martyr in the current location
                martyrNode = martyrNode.getNext();
            }

            // Move to the next location in the district
            locationNode = locationNode.getNext();
        }

        return count;
    }

    public Location findLocationByName(String name) {
        for (int i = 0; i < sLocation.length(); i++) {
            Location location = sLocation.get(i);
            if (location.getLocationName().equalsIgnoreCase(name)) {
                return location;
            }
        }
        return null; // Location not found
    }


    @Override
    public int compareTo(District other) {
        return this.districtName.compareToIgnoreCase(other.districtName);
    }

    @Override
    public String toString() {
        return "District{" +
                "districtName='" + districtName + '\'' +
                ", sLocation=" + sLocation +
                '}';
    }
}