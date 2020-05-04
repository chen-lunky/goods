package com.chen.controller.user;

import com.chen.domain.Book;
import com.chen.service.BookService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 图书的controller表示层
 */
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    private String getURL(HttpServletRequest request){
        return request.getRequestURI();
    }

    /**
     * 按分类查询
     * @param page
     * @param pageSize
     * @param cid
     * @param request
     * @return
     */
    @RequestMapping("/findByCategory.do")
    public ModelAndView findByCategory(@RequestParam(value = "page",defaultValue = "1")int page,
                                       @RequestParam(value = "pageSize",defaultValue = "12")int pageSize,
                                       @RequestParam(value = "cid")String cid,HttpServletRequest request
    ){
        ModelAndView mv = new ModelAndView();
        List<Book> bookList= bookService.findByCategory(page,pageSize,cid);
        PageInfo pageInfo = new PageInfo(bookList);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("url",getURL(request)+"?cid="+cid);
        mv.setViewName("forward:/pages/user/book/list.jsp");
        return mv;

    }

    /**
     * 按书名模糊查询
     * @param page
     * @param pageSize
     * @param bname
     * @param request
     * @return
     */
    @RequestMapping("/findByBname.do")
    public ModelAndView findByBname(@RequestParam(value = "page",defaultValue = "1")int page,
                                       @RequestParam(value = "pageSize",defaultValue = "12")int pageSize,
                                       @RequestParam(value = "bname")String bname,HttpServletRequest request
    ){
        ModelAndView mv = new ModelAndView();
        List<Book> bookList= bookService.findByBname(page,pageSize,bname);
        PageInfo pageInfo = new PageInfo(bookList);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("url",getURL(request)+"?bname="+bname);
        mv.setViewName("forward:/pages/user/book/list.jsp");
        return mv;

    }

    /**
     * 按出版社模糊查询
     * @param page
     * @param pageSize
     * @param press
     * @param request
     * @return
     */
    @RequestMapping("/findByPress.do")
    public ModelAndView findByPress(@RequestParam(value = "page",defaultValue = "1")int page,
                                    @RequestParam(value = "pageSize",defaultValue = "12")int pageSize,
                                    @RequestParam(value = "press")String press,HttpServletRequest request
    ){
        ModelAndView mv = new ModelAndView();
        List<Book> bookList= bookService.findByPress(page,pageSize,press);
        PageInfo pageInfo = new PageInfo(bookList);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("url",getURL(request)+"?press="+press);
        mv.setViewName("forward:/pages/user/book/list.jsp");
        return mv;

    }

    /**
     * 按作者模糊查询
     * @param page
     * @param pageSize
     * @param author
     * @param request
     * @return
     */
    @RequestMapping("/findByAuthor.do")
    public ModelAndView findByAuthor(@RequestParam(value = "page",defaultValue = "1")int page,
                                     @RequestParam(value = "pageSize",defaultValue = "12")int pageSize,
                                     @RequestParam(value = "author")String author,HttpServletRequest request
    ){
        ModelAndView mv = new ModelAndView();
        List<Book> bookList= bookService.findByAuthor(page,pageSize,author);
        PageInfo pageInfo = new PageInfo(bookList);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("url",getURL(request)+"?author="+author);
        mv.setViewName("forward:/pages/user/book/list.jsp");
        return mv;

    }

    /**
     * 按书名，作者，出版社组合条件查询
     * @param page
     * @param pageSize
     * @param book
     * @param request
     * @return
     */
    @RequestMapping("/findCombination.do")
    public ModelAndView findCombination(@RequestParam(value = "page",defaultValue = "1")int page,
                                    @RequestParam(value = "pageSize",defaultValue = "12")int pageSize,
                                     Book book,HttpServletRequest request
    ){
        ModelAndView mv = new ModelAndView();
        List<Book> bookList= bookService.findCombination(page,pageSize,book);
        PageInfo pageInfo = new PageInfo(bookList);
        mv.addObject("pageInfo",pageInfo);
        StringBuilder url = new StringBuilder();
        //获取url
        url.append(getURL(request)+"?1=1");
        if (book.getBname()!=null && !book.getBname().equals(" ")){
            url.append("&bname="+book.getBname());
        }
        if (book.getAuthor()!=null && !book.getAuthor().equals(" ")){
            url.append("&author="+book.getAuthor());
        }
        if (book.getPress()!=null && !book.getPress().equals(" ")){
            url.append("&press="+book.getPress());
        }
        System.out.println("url = "+url.toString());
        mv.addObject("url",url.toString());

        mv.setViewName("forward:/pages/user/book/list.jsp");

        return mv;

    }

    /**
     * 根据bid显示图书的详细信息
     * @return
     */
    @RequestMapping("/load.do")
    public ModelAndView load(@RequestParam(value = "bid")String bid){
        ModelAndView mv = new ModelAndView();
        Book book = bookService.load(bid);
        mv.addObject("book",book);
        mv.setViewName("forward:/pages/user/book/desc.jsp");
        return mv;
    }
}
