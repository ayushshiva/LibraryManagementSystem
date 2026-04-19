# Library Management System 

## Overview
A complete Java Swing-based Library Management System with modern UI, supporting:
- **Book Management**: Add, view, search, remove books
- **Member Management**: Add members, view members
- **Issue/Return Books**: Issue books to members, return with tracking
- **User Authentication**: Admin & Student login with registration
- **Data Persistence**: Save/Load data to library_data.txt

## Key Features Implemented
1. **Modern GUI**: Nimbus L&F, gradient headers, styled buttons/fields
2. **Role-Based Dashboard**:
   - Admin: Full CRUD operations
   - Student: View available, search, my issues
3. **Registration/Login**:
   - Student/Admin registration with password (fixed)
   - Secure authentication
4. **Data Model**: Book, Member, IssueEntry, User classes

## Tech Stack
- Java Swing (GUI)
- Java Collections (data storage)
- File I/O (persistence)
- Lambda/Streams (modern Java)

## Architecture
```
Main.java → Login → Library (core logic)
           ↓
   Login → RegisterDialog/RegisterPanel → Authenticate
           ↓
Dashboards (Admin/Student)
```

## Demo Flow
1. Run `java -cp src Main`
2. LOGIN screen → REGISTER → Student → Fill Mobile, Username, Password, Enrolment, GR
3. LOGIN with credentials → Student Dashboard
4. View Available Books, Search, My Issues

## Screenshots Structure
- Login Screen
- Registration Dialog
- Admin Dashboard
- Student Dashboard
- Book Issue/Return

## Future Enhancements
- Database (MySQL)
- Barcode Scanner
- Fine Calculation
- Reports/Statistics