# Thsi is the projectg

## Mini Library Management System (Java Console Application)

A beginner-friendly, menu-driven library management system written in plain Java
(no external libraries). Books are stored in memory using `ArrayList<Book>` and all
input is read with `Scanner`.

### Project structure

```
TRRY/
└── src/
    ├── Book.java                # Model class: fields, constructor, getters/setters, displayBook()
    └── LibraryManagement.java   # Contains main(): welcome banner, menu, switch-case, all features
```

- `Book.java` – one book: Book ID, Book Name, Author Name, Category and availability
  status (Available / Issued). Provides getters, setters, `getStatus()` and
  `displayBook()` which prints the book as a formatted table row.
- `LibraryManagement.java` – the entry point. Keeps the `ArrayList<Book>` list, prints
  the welcome banner and the menu, and dispatches the user's choice with `switch-case`.
  Each feature is a separate small method, plus helper methods for input validation
  (`readInt`, `readNonEmptyText`) and lookup (`findBookById`).

### Features

1. Add Book (duplicate Book IDs are rejected)
2. View All Books
3. Search Book by ID
4. Search Book by Name (partial, case-insensitive match)
5. Issue Book
6. Return Book
7. Delete Book
8. Display Total Number of Books (with issued / available counts)
9. Exit Program

### How to compile and run

**Command line (JDK 8 or newer)**

```bash
cd src
javac Book.java LibraryManagement.java
java LibraryManagement
```

Or compile into a separate output folder:

```bash
javac -d out src/*.java
java -cp out LibraryManagement
```

**VS Code** – install the "Extension Pack for Java", open the `TRRY` folder,
open `src/LibraryManagement.java` and click **Run** above the `main()` method.

**IntelliJ IDEA** – File → Open → select the `TRRY` folder, mark `src` as the
Sources Root if needed, then right-click `LibraryManagement` → **Run 'LibraryManagement.main()'**.

**Eclipse** – File → New → Java Project (uncheck "Use default location" and pick the
`TRRY` folder), then right-click `LibraryManagement.java` → Run As → Java Application.

**NetBeans** – File → New Project → Java with Ant → Java Application, then copy
`Book.java` and `LibraryManagement.java` into the project's default package and press F6.

Both classes are in the default package, so no `package` statement or folder nesting
is required.

Run 1st paste this in terminal---> javac -d out src/*.java
then paste this---> java -cp out LibraryManagement