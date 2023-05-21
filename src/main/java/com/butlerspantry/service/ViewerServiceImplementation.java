package main.java.com.butlerspantry.service;

import main.java.com.butlerspantry.connection.ReadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ViewerServiceImplementation implements ViewerService {

    DigitizedFoodInventoryService butlerspantry = new DigitizedFoodInventoryServiceImplementation();
    private Scanner commandLineInput = new Scanner(System.in);
    private String defaultFilePath = "src/main/resources/myPantry.csv";

    @Override
    public File chooseStarterFile() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========Welcome to the Butler's Pantry!=========\n\n");
        sb.append("Please enter an absolute path to your desired starting pantry.\n");
        sb.append("To use the program, each row in your CSV must be formatted similarly to the following:\n\tFlour,kg,1\n");
        sb.append("To utilize the last default pantry, just hit enter.");

        System.out.print(sb);

        String potentialNewFilePath = commandLineInput.next();
        while (!potentialNewFilePath.isEmpty()){
                try {
                    File trialFile = ReadFile.readFile(potentialNewFilePath);
                    return trialFile;
                } catch (FileNotFoundException sorry){
                System.out.println("We got the message that your file path isn't valid:\n" + sorry.getMessage() + "\nPlease try again!\nEnter your desired file path - to give up, just press enter!" );
                } catch (Exception e) {
                System.out.println("Oops! That didn't work...perhaps the following message will explain why: " + e.getMessage());
                break;
                }
        }

        try {
            return ReadFile.readFile(defaultFilePath);
        } catch (Exception e) {
            System.out.println("Sorry, it looks like the butler's pantry wasn't correctly installed on your system: our default starter pantry is missing! Try looking at the README again and try again!");
            throw e;
        }
    }

    public void loadFileWithConfirmation(File itsAFile) {
        try {
            boolean valid = IngredientValidationLogicService.validateFile(itsAFile);


        } catch (Exception e) {

        }


        /**
         * Display Book Manager Options
         *
         * @return - User Choice
         */
        @Override
        public int viewOptions() {

            StringBuilder sb = new StringBuilder();

            sb.append("\n===== Book Manager =====\n\n");
            sb.append("\t1) View all books\n");
            sb.append("\t2) Add a book\n");
            sb.append("\t3) Edit a book\n");
            sb.append("\t4) Remove a book\n");
            sb.append("\t5) Search for a book by title\n");
            sb.append("\t6) Search for a book by description\n");
            sb.append("\t7) Search for a book by title and description\n");
            sb.append("\t8) Save and exit\n");
            sb.append("\t9) Exit without save\n\n");
            sb.append("Choose [1-9]: ");

            System.out.print(sb);

            String selection = sc.next();
            while (!selection.matches("[1-9]")) {
                System.out.print("Please choose correct choice [1-9]: ");
                selection = sc.next();
            }

            int choice = Integer.parseInt(selection);
            return choice;
        }

        /**
         * View all books in the database
         *
         * - List the ID and title of each book
         *
         * - Allow the user to see details of a particular book
         *
         */
        @Override
        public void viewAll() {
            StringBuilder sb = new StringBuilder();
            System.out.println("\n===== View Books =====\n");
            if (books.size() > 0) {
                for (Book book : books) {
                    sb.append("\t[" + book.getId() + "] " + book.getTitle() + "\n");
                }
                System.out.println(sb);
                System.out.println("To view details enter the book ID, to return press <Enter>.");
                sc.nextLine();
                System.out.print("\nBook ID: ");
                String id = sc.nextLine();
                while (!id.isEmpty()) {
                    while (id.matches("[^\\d]+")) {
                        System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
                        System.out.print("Book ID: ");
                        id = sc.nextLine();
                    }
                    if (!id.isEmpty()) {
                        Book book = bookService.get(books, Integer.parseInt(id));

                        if (book != null) {
                            System.out.print("\n\tID: " + book.getId());
                            System.out.print("\n\tTitle: " + book.getTitle());
                            System.out.print("\n\tAuthor: " + book.getAuthor());
                            System.out.print("\n\tDescription: " + book.getDescription() + "\n");

                        } else {
                            System.out.println("\tThere is no book with ID [" + id + "] in the listed books.");
                        }

                        System.out.println("\nTo view details enter the book ID, to return press <Enter>.");
                        System.out.print("\nBook ID: ");
                        id = sc.nextLine();
                    }
                }
            } else {
                System.out.println("\tThere aren't any books available.");
            }
        }

        /**
         * Add a new book
         *
         * - Prompt the user for the book title, author and description
         *
         * - Save their changes to the database
         */
        @Override
        public void addBook() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n===== Add a Book =====\n");
            sb.append("\nPlease enter the following information:\n\n");
            System.out.print(sb);
            sc.nextLine();
            System.out.print("\tTitle: ");
            String title = sc.nextLine();
            System.out.print("\tAuthor: ");
            String author = sc.nextLine();
            System.out.print("\tDescription: ");
            String description = sc.nextLine();
            String status = bookService.save(new Book(title, author, description));
            System.out.println("\n" + status);
        }

        /**
         * Edit an existing book
         *
         * - Display a list of available books
         *
         * - Allow the user to select a book to edit
         *
         * - Display each field, one at a time, and allow them to change the value
         * of the field. The user should be able to leave the value unchanged.
         */
        @Override
        public void editBook() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n===== Edit a Book =====\n\n");
            for (Book book : books) {
                sb.append("\t[" + book.getId() + "] " + book.getTitle() + "\n");
            }
            sb.append("\nEnter the book ID of the book you want to edit; to return press <Enter>.\n\n");
            System.out.print(sb);
            sc.nextLine();
            System.out.print("Book ID: ");
            String id = sc.nextLine();
            while (id.matches("[^\\d]+") && !id.isEmpty()) {
                System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
                System.out.print("Book ID: ");
                id = sc.nextLine();
            }
            if (!id.isEmpty()) {
                // sc.nextLine();
                Book oldBook = bookService.get(Integer.parseInt(id));
                if (oldBook != null) {
                    System.out.print("\nInput the following information. To leave a field unchanged, hit <Enter>.\n\n");

                    System.out.print("\tTitle: [" + oldBook.getTitle() + "]: ");
                    String title = sc.nextLine();
                    System.out.print("\tAuthor: [" + oldBook.getAuthor() + "]: ");
                    String author = sc.nextLine();
                    System.out.print("\tDescription: [" + oldBook.getDescription() + "]: ");
                    String description = sc.nextLine();

                    if (title.isEmpty() && author.isEmpty() && description.isEmpty()) {
                        System.out.println("\nThere are not any changes for book with ID [" + id + "]");
                    } else {
                        String status = bookService.update(oldBook.getId(), title, author, description);
                        System.out.println("\n" + status);
                    }
                } else {
                    System.out.println("\tThere is no book with ID [" + id + "]");
                }
            }
        }

        /**
         * Remove an existing book
         *
         * - Display a list of available books
         *
         * - Allow the user to select a book to delete
         *
         * - Delete the book from the database
         */
        @Override
        public void removeBook() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n===== Remove a Book =====\n");
            for (Book book : books) {
                sb.append("\t[" + book.getId() + "] " + book.getTitle() + "\n");
            }
            sb.append("\nEnter the book ID of the book you want to remove; to return press <Enter>.\n\n");
            System.out.print(sb);
            sc.nextLine();
            System.out.print("Book ID: ");
            String id = sc.nextLine();
            while (id.matches("[^\\d]+") && !id.isEmpty()) {
                System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
                System.out.print("Book ID: ");
                id = sc.nextLine();
            }
            if (!id.isEmpty()) {
                Book book = bookService.get(Integer.parseInt(id));
                if (book != null) {
                    System.out.print("\tAre you sure you want to remove " + book
                            + "? \n\tEnter y for confirmation; to return press <Enter>: ");

                    String title = sc.nextLine();
                    if (!title.isEmpty() && title.equalsIgnoreCase("y")) {
                        String status = bookService.delete(book.getId());
                        System.out.println("\n" + status);
                    }
                } else {
                    System.out.println("\tThere is no book with ID [" + id + "]");
                }
            }
        }

        /**
         * Search for books using keywords for specific field
         *
         * @param by
         */
        public void search(String by) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n===== Search by " + by + " =====\n");

            sb.append("\nType in one or more keywords to search for.\n\n");
            System.out.print(sb);
            sc.nextLine();
            System.out.print("\tSearch: ");
            String keywords = sc.nextLine();
            while (keywords.isEmpty()) {
                System.out.print("Type in one or more keywords to search for.\n\n");
                System.out.print("\tSearch: ");
                keywords = sc.nextLine();
            }
            if (!keywords.isEmpty()) {
                // sc.nextLine();
                List<Book> books = new ArrayList<>();
                if (by.equals("title"))
                    books = bookService.getByTitle(keywords);
                else if (by.equals("description"))
                    books = bookService.getByDescription(keywords);
                else if (by.equals("titleOrDescription"))
                    books = bookService.getByTitleOrDescription(keywords);

                if (books.size() > 0) {
                    System.out.print(
                            "\nThe following books matched your query. Enter the book ID to see more details, or <Enter> to return.\n\n");

                    for (Book book : books) {
                        System.out.print("\t[" + book.getId() + "] " + book.getTitle() + "\n");
                    }
                    System.out.print("\nBook ID: ");
                    String id = sc.nextLine();
                    while (!id.isEmpty()) {
                        while (id.matches("[^\\d]+")) {
                            System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
                            System.out.print("Book ID: ");
                            id = sc.nextLine();
                        }
                        if (!id.isEmpty()) {
                            Book book = bookService.get(books, Integer.parseInt(id));

                            if (book != null) {
                                System.out.print("\n\tID: " + book.getId());
                                System.out.print("\n\tTitle: " + book.getTitle());
                                System.out.print("\n\tAuthor: " + book.getAuthor());
                                System.out.print("\n\tDescription: " + book.getDescription() + "\n");

                            } else {
                                System.out.println(
                                        "\tThere aren't anyn't any books with ID [" + id + "] in the matched books.");
                            }
                        }
                        System.out.print("\nBook ID: ");
                        id = sc.nextLine();
                    }
                } else {
                    System.out.println("\t\tThere aren't any books matched your query for keywords [" + keywords + "].");
                }
            }
        }

        /**
         * Search for books using keywords at title field
         */
        @Override
        public void searchByTitle() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n===== Search by title =====\n");

            sb.append("\nType in one or more keywords to search for.\n\n");
            System.out.print(sb);
            sc.nextLine();
            System.out.print("\tSearch: ");
            String keywords = sc.nextLine();
            while (keywords.isEmpty()) {
                System.out.print("Type in one or more keywords to search for.\n\n");
                System.out.print("\tSearch: ");
                keywords = sc.nextLine();
            }
            if (!keywords.isEmpty()) {
                List<Book> books = bookService.getByTitle(keywords);
                if (books.size() > 0) {
                    System.out.print(
                            "\nThe following books matched your query. Enter the book ID to see more details, or <Enter> to return.\n\n");

                    for (Book book : books) {
                        System.out.print("\t[" + book.getId() + "] " + book.getTitle() + "\n");
                    }
                    System.out.print("\nBook ID: ");
                    String id = sc.nextLine();
                    while (!id.isEmpty()) {
                        while (id.matches("[^\\d]+")) {
                            System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
                            System.out.print("Book ID: ");
                            id = sc.nextLine();
                        }
                        if (!id.isEmpty()) {
                            Book book = bookService.get(books, Integer.parseInt(id));

                            if (book != null) {
                                System.out.print("\n\tID: " + book.getId());
                                System.out.print("\n\tTitle: " + book.getTitle());
                                System.out.print("\n\tAuthor: " + book.getAuthor());
                                System.out.print("\n\tDescription: " + book.getDescription() + "\n");

                            } else {
                                System.out.println("\tThere aren't any books with ID [" + id + "] in the matched books.");
                            }

                            System.out.print("\nBook ID: ");
                            id = sc.nextLine();
                        }

                    }
                } else {
                    System.out.println("\t\tThere aren't any books matched your query for keywords [" + keywords + "]");
                }
            }
        }

        /**
         * Search for books using keywords at description field
         */
        @Override
        public void searchByDescription() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n===== Search by description =====\n");

            sb.append("\nType in one or more keywords to search for.\n\n");
            System.out.print(sb);
            sc.nextLine();
            System.out.print("\tSearch: ");
            String keywords = sc.nextLine();
            while (keywords.isEmpty()) {
                System.out.print("Type in one or more keywords to search for.\n\n");
                System.out.print("\tSearch: ");
                keywords = sc.nextLine();
            }
            if (!keywords.isEmpty()) {
                List<Book> books = bookService.getByDescription(keywords);
                if (books.size() > 0) {
                    System.out.print(
                            "\nThe following books matched your query. Enter the book ID to see more details, or <Enter> to return.\n\n");

                    for (Book book : books) {
                        System.out.print("\t[" + book.getId() + "] " + book.getTitle() + "\n");
                    }
                    System.out.print("\nBook ID: ");
                    String id = sc.nextLine();
                    while (!id.isEmpty()) {
                        while (id.matches("[^\\d]+")) {
                            System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
                            System.out.print("Book ID: ");
                            id = sc.nextLine();
                        }
                        if (!id.isEmpty()) {
                            Book book = bookService.get(books, Integer.parseInt(id));

                            if (book != null) {
                                System.out.print("\n\tID: " + book.getId());
                                System.out.print("\n\tTitle: " + book.getTitle());
                                System.out.print("\n\tAuthor: " + book.getAuthor());
                                System.out.print("\n\tDescription: " + book.getDescription() + "\n");

                            } else {
                                System.out.println("\tThere aren't any books with ID [" + id + "] in the matched books.");
                            }

                            System.out.print("\nBook ID: ");
                            id = sc.nextLine();
                        }

                    }
                } else {
                    System.out.println("\t\tThere aren't any books matched your query for keywords[" + keywords + "].");
                }
            }
        }

        /**
         * Search for books using keywords at title or description fields
         */
        @Override
        public void searchByTitleOrDescription() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n===== Search by title or description =====\n");

            sb.append("\nType in one or more keywords to search for.\n\n");
            System.out.print(sb);
            sc.nextLine();
            System.out.print("\tSearch: ");
            String keywords = sc.nextLine();
            while (keywords.isEmpty()) {
                System.out.print("Type in one or more keywords to search for.\n\n");
                System.out.print("\tSearch: ");
                keywords = sc.nextLine();
            }
            if (!keywords.isEmpty()) {
                List<Book> books = bookService.getByTitleOrDescription(keywords);
                if (books.size() > 0) {
                    System.out.print(
                            "\nThe following books matched your query. Enter the book ID to see more details, or <Enter> to return.\n\n");

                    for (Book book : books) {
                        System.out.print("\t[" + book.getId() + "] " + book.getTitle() + "\n");
                    }
                    System.out.print("\nBook ID: ");
                    String id = sc.nextLine();
                    while (!id.isEmpty()) {
                        while (id.matches("[^\\d]+")) {
                            System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
                            System.out.print("Book ID: ");
                            id = sc.nextLine();
                        }
                        if (!id.isEmpty()) {
                            Book book = bookService.get(books, Integer.parseInt(id));

                            if (book != null) {
                                System.out.print("\n\tID: " + book.getId());
                                System.out.print("\n\tTitle: " + book.getTitle());
                                System.out.print("\n\tAuthor: " + book.getAuthor());
                                System.out.print("\n\tDescription: " + book.getDescription() + "\n");

                            } else {
                                System.out.println("\tThere aren't any books with ID [" + id + "] in the matched books.");
                            }
                            System.out.print("\nBook ID: ");
                            id = sc.nextLine();
                        }

                    }
                } else {
                    System.out.println("\t\tThere aren't any books matched your query for keywords[" + keywords + "].");
                }
            }
        }

        /**
         * Save and Exit
         *
         * - Write the database of books to disk, upon exiting the application
         */
        @Override
        public void saveAndExit() {
            if (bookService.save()) {
                System.out.println("\nLibrary saved.");
                System.exit(0);
            } else
                System.out.println("Sorry, Something has error while saving.");
        }

    }

}
