package com.example.javacoursework.consoleWork;

import com.example.javacoursework.model.Driver;
import com.example.javacoursework.model.User;
import com.example.javacoursework.model.VechicleType;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuControl {
    public static void generateUserMenu(Scanner scanner, Wolt wolt) {
        var cmd = 0;
        while (cmd != 6) {
            System.out.println("""
                    Choose and option:
                    1 - create
                    2 - view all users
                    3 - update
                    6 - return to main menu
                    """);
            cmd = scanner.nextInt();
            scanner.nextLine();

            switch (cmd) {
                case 1:
                    System.out.println("Which type of user to create? U/BS/D/R");
                    String userType = scanner.nextLine();
                    if(userType.equals("U")) {
                        System.out.println("Enter User data (User class):username;password;name;surname;phoneNum;address;");
                        var input = scanner.nextLine();
                        String[] info = input.split(";");
                        User user = new User(info[0], info[1], info[2], info[3], info[4]);
                        wolt.getAllSystemUsers().add(user);
                    }
                    else if(userType.equals("D")) {
                        System.out.println("Enter User data (User class):username;password;name;surname;phoneNum;address; licence; bdate;vehicle");
                        var input = scanner.nextLine();
                        String[] info = input.split(";");
                        Driver driver = new Driver(info[0], info[1], info[2], info[3], info[4], info[5], info[6], LocalDate.parse(info[7]), VechicleType.valueOf(info[8]));
                    }
                    break;
                case 2:
                    System.out.println("List of users:");
                    for(User user : wolt.getAllSystemUsers()){
                        System.out.println(user.toString());
                    }
                    break;
                case 3:
                    System.out.println("enter username");
                    var username = scanner.nextLine();
                    for(User u : wolt.getAllSystemUsers()){
                        if(u.getUsername().equals(username)){
                            System.out.println("What to update? N/S");
                            var whatToUpdate = scanner.nextLine();
                            if(whatToUpdate.equals("N")){
                                System.out.print("Enter new name: ");
                                String newName = scanner.nextLine();
                                u.setName(newName);
                            }
                            else if(whatToUpdate.equals("S")){
                                System.out.print("Enter new surname: ");
                                String newSurname = scanner.nextLine();
                                u.setSurname(newSurname);
                            }
                        }
                    }
                default:
                    System.out.println();
            }
        }
    }
}
