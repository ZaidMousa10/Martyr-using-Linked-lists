package com.example.project;

public class Location implements Comparable<Location> {
    private String locationName;
    private SLinkedList<Martyr> sMartyr;

    public Location(String locationName) {
        this.locationName = locationName;
        this.sMartyr = new SLinkedList<>();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public SLinkedList<Martyr> getSMartyr() {
        return sMartyr;
    }


    public void addMartyr(Martyr martyr) {
        sMartyr.insert(martyr);
    }

    //O(n)
    public int getTotalMartyrs() {
        return sMartyr.length(); // Total number of martyrs at this location
    }

    //O(n^2)
    public int getTotalMaleMartyrs() {
        int totalMaleMartyrs = 0;
        for (int i = 0; i < sMartyr.length(); i++) {
            Martyr martyr = sMartyr.get(i);
            if (martyr.getGender().equalsIgnoreCase("M")) {
                totalMaleMartyrs++;
            }
        }
        return totalMaleMartyrs;
    }

    //O(n^2)
    public int getTotalFemaleMartyrs() {
        int totalFemaleMartyrs = 0;
        for (int i = 0; i < sMartyr.length(); i++) {
            Martyr martyr = sMartyr.get(i);
            if (martyr.getGender().equalsIgnoreCase("F")) {
                totalFemaleMartyrs++;
            }
        }
        return totalFemaleMartyrs;
    }

    //O(n^2+ n)
    public double getAverageMartyrAge() {
        if (sMartyr.isEmpty()) {
            return 0;
        }
        double sumAges = 0;
        for (int i = 0; i < sMartyr.length(); i++) {
            Martyr  martyr = sMartyr.get(i);
            sumAges += martyr.getAge();
        }
        return sumAges / sMartyr.length();
    }

    //O(n^2)
    public int countMartyrsByDateL(String dateToCount) {
        int count = 0;

        // Iterate over each martyr in the list of martyrs
        for (int i = 0; i < sMartyr.length(); i++) {
            String dateOfDeath = sMartyr.get(i).getDateOfDeath();

            // Check if the date of death matches the date we want to count (exact match)
            if (dateOfDeath.equals(dateToCount)) {
                count++;
            }
        }

        return count;
    }

    //O(n^2+n)
    public Martyr getYoungestMartyr() {
        if (sMartyr.isEmpty()) {
            return null; // Return null if no martyrs at this location
        }

        Martyr youngest = sMartyr.get(0); // Start with the first martyr
        for (int i = 1; i < sMartyr.length(); i++) {
            Martyr current = sMartyr.get(i);
            if (current.getAge() < youngest.getAge()) {
                youngest = current; // Update youngest martyr if found a younger one
            }
        }

        return youngest; // Return the youngest martyr
    }

    //O(n^2+n)
    public Martyr getOldestMartyr() {
        if (sMartyr.isEmpty()) {
            return null; // Return null if no martyrs at this location
        }

        Martyr oldest = sMartyr.get(0); // Start with the first martyr
        for (int i = 1; i < sMartyr.length(); i++) {
            Martyr current = sMartyr.get(i);
            if (current.getAge() > oldest.getAge()) {
                oldest = current; // Update oldest martyr if found an older one
            }
        }

        return oldest; // Return the oldest martyr
    }

    //O(n^2)
    public double getAverageMartyrAgeL() {
        if (sMartyr.isEmpty()) {
            return 0; // Return 0 if no martyrs at this location
        }

        double sumAges = 0;
        int validMartyrsCount = 0; // Counter for valid martyrs (ages not equal to -1)

        for (int i = 0; i < sMartyr.length(); i++) {
            Martyr martyr = sMartyr.get(i);
            int martyrAge = martyr.getAge();

            if (martyrAge != -1) { // Only include valid ages in the sum
                sumAges += martyrAge;
                validMartyrsCount++;
            }
        }

        if (validMartyrsCount > 0) {
            return sumAges / validMartyrsCount; // Return average age of valid martyrs
        } else {
            return 0; // Return 0 if no valid martyrs found
        }
    }

    //O(n)
    public int getTotalMartyrsL() {
        return sMartyr.length(); // Return the total number of martyrs at this location
    }

    //O(n^2)
    public int getTotalMaleMartyrsL() {
        int totalMaleMartyrs = 0;
        for (int i = 0; i < sMartyr.length(); i++) {
            Martyr martyr = sMartyr.get(i);
            if (martyr.getGender().equalsIgnoreCase("M")) {
                totalMaleMartyrs++;
            }
        }
        return totalMaleMartyrs;
    }

    //O(n^2)
    public int getTotalFemaleMartyrsL() {
        int totalFemaleMartyrs = 0;
        for (int i = 0; i < sMartyr.length(); i++) {
            Martyr martyr = sMartyr.get(i);
            if (martyr.getGender().equalsIgnoreCase("F")) {
                totalFemaleMartyrs++;
            }
        }
        return totalFemaleMartyrs;
    }


    @Override
    public int compareTo(Location other) {
        return this.locationName.compareToIgnoreCase(other.locationName);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationName='" + locationName + '\'' +
                ", sMartyr=" + sMartyr +
                '}';
    }

    public boolean removeMartyr(Martyr martyrToUpdate) {
        for (int i = 0; i < sMartyr.length(); i++) {
            Martyr martyr = sMartyr.get(i);
            if (martyr.equals(martyrToUpdate)) {
                sMartyr.delete(martyr); // Assuming this method removes the specified martyr from the list
                return true;
            }
        }
        System.out.println("Martyr not found in the list: " + martyrToUpdate);
        return false;
    }


    public Martyr findMartyrToUpdate(String name, String dateOfDeath) {
        SLinkedList<Martyr> martyrs = getSMartyr(); // Assuming getSMartyr() returns the list of martyrs for this location
        for (int i = 0; i < martyrs.length(); i++) {
            Martyr martyr = martyrs.get(i);
            if (martyr.getName().equalsIgnoreCase(name) && martyr.getDateOfDeath().equalsIgnoreCase(dateOfDeath)) {
                return martyr; // Return the martyr if name and date of death match
            }
        }
        return null; // Return null if no matching martyr is found
    }

}