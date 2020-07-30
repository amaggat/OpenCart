package opencart.Controller;

import opencart.Model.Cart;
import opencart.Model.Customer;
import opencart.Model.Product;
import opencart.Service.ServiceInt.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class CartController{

    @Autowired
    private CartService cartService;

    @RequestMapping("/cartlist")
    public String showCart(Model model){
        Collection<Product> cartProducts = cartService.showProductByCart();
        model.addAttribute("cartProducts",cartProducts);
        return "cartList";
    }

}