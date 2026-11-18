import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ResumeTrackerApp {
    private static final String RESUME_FILE = "resumes.txt";
    private static final String APPLICATION_FILE = "applications.txt";

    private static ArrayList<ResumeVersion> resumes = new ArrayList<ResumeVersion>();
    private static ArrayList<JobApplication> applications = new ArrayList<JobApplication>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadResumes();
        loadApplications();

        System.out.println("================================");
        System.out.println("       RESUME TRACKER");
        System.out.println("================================");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addResumeVersion();
                    break;
                case 2:
                    viewResumeVersions();
                    break;
                case 3:
                    addJobApplication();
                    break;
                case 4:
                    viewApplications();
                    break;
                case 5:
                    showResumeUsage();
                    break;
                case 6:
                    saveAllData();
                    running = false;
                    System.out.println("Data saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-6.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n----------- MENU -----------");
        System.out.println("1. Add resume version");
        System.out.println("2. View resume versions");
        System.out.println("3. Record job application");
        System.out.println("4. View application history");
        System.out.println("5. View resume usage report");
        System.out.println("6. Save and exit");
        System.out.println("----------------------------");
    }

    private static void addResumeVersion() {
        System.out.println("\n--- Add Resume Version ---");
        System.out.print("Resume version name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Resume name cannot be empty.");
            return;
        }

        System.out.print("Short description (skills or target role): ");
        String description = scanner.nextLine().trim();

        int id = getNextResumeId();
        resumes.add(new ResumeVersion(id, name, description));
        saveResumes();
        System.out.println("Resume version added successfully. ID: " + id);
    }

    private static void viewResumeVersions() {
        System.out.println("\n--- Resume Versions ---");
        if (resumes.isEmpty()) {
            System.out.println("No resume versions found. Add one first.");
            return;
        }

        for (ResumeVersion resume : resumes) {
            System.out.println(resume);
        }
    }

    private static void addJobApplication() {
        System.out.println("\n--- Record Job Application ---");
        if (resumes.isEmpty()) {
            System.out.println("Please add a resume version before recording an application.");
            return;
        }

        System.out.print("Company name: ");
        String company = scanner.nextLine().trim();
        System.out.print("Job title: ");
        String jobTitle = scanner.nextLine().trim();
        System.out.print("Date applied (for example, 2026-11-18): ");
        String dateApplied = scanner.nextLine().trim();

        if (company.isEmpty() || jobTitle.isEmpty() || dateApplied.isEmpty()) {
            System.out.println("Company, job title, and date are required.");
            return;
        }

        viewResumeVersions();
        int resumeId = readInt("Enter the ID of the resume version used: ");
        if (findResumeById(resumeId) == null) {
            System.out.println("Resume version ID not found. Application was not saved.");
            return;
        }

        int id = getNextApplicationId();
        applications.add(new JobApplication(id, company, jobTitle, dateApplied, resumeId));
        saveApplications();
        System.out.println("Job application recorded successfully. ID: " + id);
    }

    private static void viewApplications() {
        System.out.println("\n--- Application History ---");
        if (applications.isEmpty()) {
            System.out.println("No applications recorded yet.");
            return;
        }

        for (JobApplication application : applications) {
            ResumeVersion resume = findResumeById(application.getResumeVersionId());
            String resumeName = (resume == null) ? "Unknown resume" : resume.getName();

            System.out.println("Application ID: " + application.getId());
            System.out.println("Company: " + application.getCompany());
            System.out.println("Job Title: " + application.getJobTitle());
            System.out.println("Date Applied: " + application.getDateApplied());
            System.out.println("Resume Used: " + resumeName);
            System.out.println("----------------------------");
        }
    }

    private static void showResumeUsage() {
        System.out.println("\n--- Resume Usage Report ---");
        if (resumes.isEmpty()) {
            System.out.println("No resume versions available.");
            return;
        }

        for (ResumeVersion resume : resumes) {
            int count = 0;
            for (JobApplication application : applications) {
                if (application.getResumeVersionId() == resume.getId()) {
                    count++;
                }
            }
            System.out.println(resume.getName() + " - Used for " + count + " application(s)");
        }
    }

    private static ResumeVersion findResumeById(int id) {
        for (ResumeVersion resume : resumes) {
            if (resume.getId() == id) {
                return resume;
            }
        }
        return null;
    }

    private static int getNextResumeId() {
        int highest = 0;
        for (ResumeVersion resume : resumes) {
            if (resume.getId() > highest) {
                highest = resume.getId();
            }
        }
        return highest + 1;
    }

    private static int getNextApplicationId() {
        int highest = 0;
        for (JobApplication application : applications) {
            if (application.getId() > highest) {
                highest = application.getId();
            }
        }
        return highest + 1;
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void loadResumes() {
        File file = new File(RESUME_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    resumes.add(new ResumeVersion(
                            Integer.parseInt(parts[0]), parts[1], parts[2]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not load resume data: " + e.getMessage());
        }
    }

    private static void loadApplications() {
        File file = new File(APPLICATION_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 5);
                if (parts.length == 5) {
                    applications.add(new JobApplication(
                            Integer.parseInt(parts[0]), parts[1], parts[2],
                            parts[3], Integer.parseInt(parts[4])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not load application data: " + e.getMessage());
        }
    }

    private static void saveResumes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESUME_FILE))) {
            for (ResumeVersion resume : resumes) {
                writer.println(resume.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Could not save resume data: " + e.getMessage());
        }
    }

    private static void saveApplications() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(APPLICATION_FILE))) {
            for (JobApplication application : applications) {
                writer.println(application.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Could not save application data: " + e.getMessage());
        }
    }

    private static void saveAllData() {
        saveResumes();
        saveApplications();
    }
}
