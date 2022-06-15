## How to execute the code
- The project is built using Java 11
- Gradle is used as the build tool
- Execute project in IDE
  - Import the project into IDE (example: Intellij) and run the main method in ConstructionReportGenerator
- Execute the gradle task "run" in terminal
  - Windows Powershell
    ```
    .\gradlew.bat clean run
    ```
  - Bash terminal
    ```
    ./gradlew clean run
    ```

## Details about the project
- The application uses the "data.csv" file in the projects resources folder.
  - Screenshot of the console output (_Output.png_) is available in the project root 
- Additional libraries such as Junit Jupiter, Mockito and Lombok are used in this project
- Jacoco gradle plugin is used to generate test report
  - Execute the gradle task "build" and the coverage report will be available in 
    "report-generator/build/reports/jacoco/test/html/index.html"
  - Screenshot of the coverage report ("_Test Coverage.png_") is available in the project root
- I have used the Factory pattern for both the file reader and console printer. This is done so 
  that it is possible to extend support other input sources such as XML, JSON, Fixed string files and 
  the output source can be HTML, Text file, etc...