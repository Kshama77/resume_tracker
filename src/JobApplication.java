public class JobApplication {
    private int id;
    private String company;
    private String jobTitle;
    private String dateApplied;
    private int resumeVersionId;

    public JobApplication(int id, String company, String jobTitle,
                          String dateApplied, int resumeVersionId) {
        this.id = id;
        this.company = company;
        this.jobTitle = jobTitle;
        this.dateApplied = dateApplied;
        this.resumeVersionId = resumeVersionId;
    }

    public int getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getDateApplied() {
        return dateApplied;
    }

    public int getResumeVersionId() {
        return resumeVersionId;
    }

    public String toFileLine() {
        return id + "|" + clean(company) + "|" + clean(jobTitle) + "|"
                + clean(dateApplied) + "|" + resumeVersionId;
    }

    private String clean(String value) {
        return value.replace("|", "/").replace("\n", " ");
    }
}
