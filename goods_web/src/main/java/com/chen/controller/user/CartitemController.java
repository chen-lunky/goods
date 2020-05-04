package com.chen.controller.user;

import com.chen.dao.BookDao;
import com.chen.dao.UserDao;
import com.chen.domain.Book;
import com.chen.domain.Cartitem;
import com.chen.domain.User;
import com.chen.service.CartitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 购物车模块的controller表示层
 */
@Controller
@RequestMapping("/cart")
public class CartitemController {
    @Autowired
    private CartitemService cartitemService;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;

    /**
     * 根据当前用户的uid去查询他购物车的购物信息
     * @param session
     * @return
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(HttpSession session){
        ModelAndView mv = new ModelAndView();
        //获取保存在session中的user
        User sessionUser = (User)session.getAttribute("user");
        List<Cartitem> cartitemList = cartitemService.findAll(sessionUser.getUid());
        mv.addObject("cartitemList",cartitemList);

        mv.setViewName("forward:/pages/user/cart/list.jsp");
        return mv;
    }

    /**
     * 添加购物条目
     * @param bid
     * @param quantity
     * @return
     */
    @RequestMapping("/add.do")
    public String add(@RequestParam("bid")String bid,@RequestParam("quantity")String quantity, HttpSession session){
        //获取当前登录的用户
        User sessionUser = (User)session.getAttribute("user");
        Cartitem cartitem = new Cartitem();
        //根据bid得到图书
        Book book = bookDao.findByBid(bid);
        //根据uid得到用户
        User user = userDao.findByUid(sessionUser.getUid());
        //得到购物条目
        cartitem.setBook(book);
        cartitem.setUser(user);
        cartitem.setQuantity(Integer.parseInt(quantity));
        //调用service添加购物条目
        cartitemService.add(cartitem);
        return "redirect:findAll.do";
    }

    /**
     * 批量删除功能
     * @param cartItemIds
     * @return
     */
    @RequestMapping("/batchDelete.do")
    public String batchDelete(@RequestParam("cartItemIds") String cartItemIds){
        System.out.println("cartItemIds = "+cartItemIds);
        //调用service批量删除
        cartitemService.batchDelete(cartItemIds);
        return "redirect:findAll.do";
    }

    /**
     * 修改指定条目的数量
     * @param cartItemIds
     * @param quantity
     * @return
     */
    @RequestMapping("/updateQuantity.do")
    public @ResponseBody Cartitem upateQuantity(@RequestParam("cartItemIds")String cartItemIds,
                                                 @RequestParam("quantity")String quantity
                                                ){

        Cartitem cartitem = cartitemService.upateQuantity(cartItemIds,quantity);
        return cartitem;
    }

    /**
     * 点击结算之后查看所勾选的条目
     * @param cartItemIds
     * @param total
     * @return
     */
    @RequestMapping("/showCartItems.do")
    public ModelAndView showCartItems(@RequestParam("cartItemIds")String cartItemIds,
                                     @RequestParam("total")String total){
        ModelAndView mv = new ModelAndView();
        System.out.println("cartItemIds = "+cartItemIds);
        List<Cartitem> cartitemList =  cartitemService.showCartItems(cartItemIds);
        System.out.println("cartitemList = "+cartitemList);
        mv.addObject("total",Double.parseDouble(total));
        mv.addObject("cartitemList",cartitemList);
        //保存所勾选的购物条目的id,方便传给订单模块的生成订单
        mv.addObject("cartItemIds",cartItemIds);
        mv.setViewName("forward:/pages/user/cart/showitem.jsp");

        return mv;

    }

}
