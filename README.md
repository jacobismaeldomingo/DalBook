# Dalbook

<img src="/frontend/public/images/dalbook_logo.png" width="300" height="300">

This project is a social media platform specifically designed for Dalhousie University. 
The platform draws inspiration from existing social media applications and includes 
features tailored to the needs of students, faculty, and administrators at Dalhousie University.

## Features
### 1. User Accounts  
- **Sign-up:** Users can register using their Dalhousie email address.
Passwords must meet security criteria: a minimum of 8 characters, including at least 1 uppercase letter, 1 lowercase letter,
1 number, and 1 special character. Non-Dal email addresses are restricted from registering.  
- **Login:** Users can log in using their registered Dalhousie email and password.  
- **Forgot Password:** Users can reset their password if forgotten.  

### 2. User and Activity Management  
- **Friends/Follow:** Users can add friends or follow others.  
- **Update Interests:** Users can update their personal interests.  
- **Delete/Deactivate Friends:** Users can delete or deactivate friends.  
- **Groups:** Users can create private or public groups.  
- **User Profile:** Users can manage their personal data via their profile.  
- **Status Update:** Users can change their status to Away, Busy, Available, etc.  

### 3. Admin Capabilities  
- **Manage Users:** Admins can add Dalhousie students and employees.  
- **Deactivate Users:** Admins can deactivate students or employees who are no longer affiliated with the university.  
- **Approve Requests:** Admins can approve user requests to join the platform.  

### 4. Search and Filter  
- **Search:** Users can search based on names, groups, or topics.  
- **Filter:** Users can filter search results based on names, groups, or topics.  

### 5. Notification System  
- **Alerts:** The platform provides alert notifications and app notifications.  

### 6. Role-Based Access  
- **Faculty Administrator:** Faculty admins can create groups, posts, and add members from their faculty to groups.  
- **System Administrator:** System admins can activate or deactivate users, assign roles, and change a user's status.  

### 7. Multimedia Support  
- **Image Content:** Users can include image content in feeds, posts, or profiles.  

## Tech Stack

- **Frontend:** React JS
- **Backend:** Spring Boot with Java Persistence API (JPA)
- **Database:** MySQL hosted on Dalhousie University servers
- **Hosting:** Optional; DAL Virtual Machine

---

## Getting Started

To get started with the project, clone this repository and follow the setup instructions for the frontend, backend, and database.

### Prerequisites

- Node.js (for frontend)
- Java 17 (for backend)
- MySQL (for database)

### Setup Instructions

1. **Clone the repository:**  

   ```bash
   git clone https://github.com/yourusername/dal-social-media-platform.git
   cd dal-social-media-platform
   
2. **Frontend Setup:**  

   ```bash
   cd frontend
   npm install
   npm start
  
3. **Backend Setup:**  

   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
  
4. **Database Setup:**  
   
- Import the provided SQL schema into MySQL.  
- Update the database credentials in the application.properties file.  
  
## Contributing
Contributions are welcome! Please follow the guidelines outlined in the CONTRIBUTING.md file.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.
