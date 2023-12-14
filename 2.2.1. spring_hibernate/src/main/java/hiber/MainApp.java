package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("Model1", 123);
      Car car2 = new Car("Model2", 456);

      userService.add(new User("Иван", "Иванов", "user1@mail.ru"));
      userService.add(new User("Петр", "Петров", "user2@mail.ru"));

      userService.addCar(car1);
      userService.addCar(car2);

      User user1 = new User("Иван", "Иванов", "user1@mail.ru");
      user1.setCar(car1);
      userService.add(user1);

      User user2 = new User("Петр", "Петров", "user2@mail.ru");
      user2.setCar(car2);
      userService.add(user2);

      List<User> users = userService.listUsers();
      for (User user : users) {
         if(user.getCar() != null) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car Model = " + user.getCar().getModel());
            System.out.println("Car Series = " + user.getCar().getSeries());
            System.out.println();
         }
      }

      User userModel1 = userService.getUserByCarModelAndSeries("Model1", 123);
      if(userModel1 != null) {
         System.out.println("Пользователь с данной моделью машины: " + userModel1.getFirstName() + " " + userModel1.getLastName());
      }
      User userModel2 = userService.getUserByCarModelAndSeries("Model2", 456);
      if(userModel2 != null) {
         System.out.println("Пользователь с данной моделью машины: " + userModel2.getFirstName() + " " + userModel2.getLastName());
      }
      context.close();
   }
}
