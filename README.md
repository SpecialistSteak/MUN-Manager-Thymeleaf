A Thymeleaf and Spring Boot based MUN Manager application which:
- Integrates with Google Drive
- Can manage hundreds of students
- Allows administrators to create conferences, assign deadlines, and check work through a Drive-connected interface

  This was a recently open-sourced project of mine, so I will update the documentation afterwards. Here is a temporary, AI-Generated overview of the project:
# MUN Manager: A Comprehensive Tool for Model UN Directors

The MUN Manager application is designed to streamline and simplify the management of Model United Nations (MUN) conferences and assignments for MUN Directors. It provides a centralized platform to organize conferences, track student participation, manage assignments, and monitor student submissions.

## Key Features:

*   **Conference Management:** Directors can create and manage details for different MUN conferences.
*   **Assignment Management:** The application allows for the creation and distribution of assignments to students within specific conferences, including setting due dates and providing descriptions.
*   **Student Management:** Directors can upload and maintain a list of participating students, likely through CSV file uploads.
*   **Submission Tracking:** The system provides a clear overview of student submissions for each assignment, including their completion status, submission date, Turnitin plagiarism scores, and word counts.
*   **Flagging Submissions:** Directors can flag specific submissions for review or further action.
*   **Individual Student View:** A dedicated view allows directors to see a comprehensive history of a single student's submissions across different assignments.
*   **Google Drive Integration:** The application integrates with Google Drive, likely for storing and managing assignment documents and student submissions.
*   **Plagiarism Detection:** Integration with Turnitin allows for plagiarism checking of student submissions.

## Underlying Technology:

The application is built using a robust and modern technology stack:

*   **Backend:** The application is powered by Spring Boot, a popular Java framework known for its rapid development capabilities and enterprise-level features.
*   **Database:** It utilizes a MySQL database to store all the application data, including conference details, assignments, student information, and submission records.
*   **Frontend:** The user interface is built using Thymeleaf, a server-side Java template engine that allows for dynamic rendering of web pages. It also leverages Bootstrap for responsive and visually appealing layouts, and jQuery for enhanced interactivity.
*   **Google Services:** The application seamlessly integrates with Google Drive and Google Docs, enabling efficient document management and access.
*  **CSV Handling:** The application can process CSV files, facilitating the easy upload of student lists.

## How it Works:

Directors can use the application to set up their MUN conferences and create assignments. Students, once added to the system, can submit their work. The application then tracks the submission status, retrieves Turnitin scores, and calculates word counts, presenting this information in an organized manner for the director. The flagging feature helps in identifying submissions that require closer inspection. The integration with Google Drive ensures that all relevant documents are easily accessible.

## Overall Goal:

The MUN Manager aims to significantly reduce the administrative burden on MUN Directors, allowing them to focus more on guiding and mentoring their students. By providing a centralized and feature-rich platform, it enhances the efficiency and organization of MUN programs.
