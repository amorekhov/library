package com.library.controller;

import com.library.domain.Book;
import com.library.domain.Orders;
import com.library.domain.User;
import com.library.repository.BookRepo;
import com.library.repository.OrdersRepo;
import com.library.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.concurrent.TransferQueue;

@Controller
public class BookController {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private OrdersRepo ordersRepo;


  @GetMapping("/book")
    public String book(Model model, Book book){

      Iterable<Book> books = bookRepo.findAll();

    /*  Boolean isTrue = book.getCount()==book.getCountAll();
      model.addAttribute("isTrue", isTrue);
    */
      model.addAttribute("book", books);




      return "book";
  }

  @GetMapping("/bookadd")
    public String bookAddView() {
      Book book = new Book();

      return "bookadd";
  }

  @PostMapping("/bookadd")
    public String bookAdd(
            @RequestParam String name,
            @RequestParam String author,
            @RequestParam String description,
           // @RequestParam Integer countAll,
            Model model
  ){


      Book book =new Book(name,author,description);

    //  book.setAvailability(true);

      bookRepo.save(book);

      return "redirect:/book";
  }

    @GetMapping("/book/take/{book}")
    public String takeBookView(@PathVariable Book book, @AuthenticationPrincipal User user, Model model){

        Orders orders = new Orders();
        orders.setBook(book);
        orders.setUser(user);
        ordersRepo.save(orders);


        return "redirect:/book";
    }

    @GetMapping("/book/del/{book}")
    public String bookDel(@PathVariable Book book){
        bookRepo.delete(book);
        return "redirect:/book";
    }



  @GetMapping("/book/edit/{book}")
  public String bookEditView(
          @PathVariable Book book,
          Model model
  ){
      model.addAttribute("name", book.getName());
      model.addAttribute("author", book.getAuthor());
      model.addAttribute("description", book.getDescription());
      return "bookedit";
  }

  @PostMapping("/book/edit/{book}")
    public String editBook(
            Model model,
            Book book,
            User user
  ){
      return "redirect:/book";
  }

    @GetMapping("/book/sortnameasc")
    public String sortNameAsc(){
      Book book =  new Book();
      bookRepo.findByName(book.getName(), Sort.unsorted().ascending());
     bookRepo.save(book);
      return "redirect:/book";
    }

}
