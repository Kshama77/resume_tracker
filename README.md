# ResumeTracker

A beginner-friendly Java console application to store resume versions and track which resume was used for each job application.

## Features
- Add resume versions (for example: Java Backend, Full Stack, React)
- View all saved resume versions
- Record job applications and select the resume version used
- View application history
- See how many applications used each resume version
- Save data in local text files so it is available the next time you run the app

## Java concepts used
- Classes and objects
- ArrayList
- Scanner input
- Loops and conditional statements
- Methods
- Basic file reading and writing

## Requirements
- Java JDK 8 or later

## Run the application

Open a terminal in the `ResumeTracker` folder.

Compile:

```bash
javac -d out src/*.java
```

Run:

```bash
java -cp out ResumeTrackerApp
```

The program creates `resumes.txt` and `applications.txt` in the project folder when you save data.

## Example
1. Add a resume version named `Java Backend Resume`
2. Add a job application for `ABC Technologies`
3. Select `Java Backend Resume` when asked which resume you used
4. View the application history or resume usage report

## Notes
This is a simple learning project. It stores resume names and job application details, not the actual resume PDF or DOCX files.
