package com.chen.controller.admin;

import com.chen.domain.Book;
import com.chen.domain.Category;
import com.chen.service.BookService;
import com.chen.service.CategoryService;
import com.chen.utils.CommonUtils;
import com.chen.utils.UploadUtils;
import com.chen.utils.UserException;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * 管理员的图书模块的controller表示层
 */
@Controller
@RequestMapping("/adminBook")
public class AdminBookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    /**
     * 查询所有分类
     * @return
     */
    @RequestMapping("/findAllCategory.do")
    public ModelAndView findAllCategory(){
        ModelAndView mv = new ModelAndView();
        List<Category> parents = categoryService.findAll();
        mv.addObject("parents",parents);
        mv.setViewName("forward:/pages/admin/book/left.jsp");
        return mv;

    }

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
        mv.setViewName("forward:/pages/admin/book/list.jsp");
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
        mv.setViewName("forward:/pages/admin/book/list.jsp");
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
        mv.setViewName("forward:/pages/admin/book/list.jsp");
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
        mv.setViewName("forward:/pages/admin/book/list.jsp");
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

        mv.setViewName("forward:/pages/admin/book/list.jsp");

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
        mv.addObject("parents",categoryService.findParents());//保存一级分类
        //保存一级分类下的所有子分类
        mv.addObject("children",categoryService.findChildCategroyByPid(book.getCategory().getParent().getCid()));
        mv.setViewName("forward:/pages/admin/book/desc.jsp");
        return mv;
    }

    /**
     * 添加图书：第一步
     * 获取所有父分类，保存，在add.jsp页面中下拉框显示
     * @return
     */
    @RequestMapping("/addBookPre.do")
    public ModelAndView addBookPre(){
        ModelAndView mv = new ModelAndView();
        List<Category> parents = categoryService.findParents();
        mv.addObject("parents",parents);
        mv.setViewName("forward:/pages/admin/book/add.jsp");
        return mv;
    }

    /**
     * 根据父分类的cid查询所有的子分类
     * @param pid
     * @return
     */
    @RequestMapping("/findChildrenCategroy.do")
    public @ResponseBody List<Category> findChildrenCategroy(@RequestParam("pid")String pid){
        List<Category> childCategroy = categoryService.findChildCategroyByPid(pid);
        return childCategroy;
    }

    /**
     * 添加图书：第二步
     * @param
     * @param cid
     * @param session
     * @param image_w 大图文件上传名，要与input标签中的name一样
     * @param image_b
     * @return
     */
    @RequestMapping("/addBook.do")
    public ModelAndView addBook(String bname, MultipartFile image_w,MultipartFile image_b,
                                String currPrice,String price,String discount,String author,
                                String press,String publishtime,String edition,String pageNum,
                                String wordNum,String printtime,String booksize,String paper,
                                String cid ,HttpSession session)  {
        ModelAndView mv = new ModelAndView();
        Book book = new Book();
        book.setBid(CommonUtils.uuid());
        book.setBname(bname);
        book.setCurrPrice(Double.parseDouble(currPrice));
        book.setPrice(Double.parseDouble(price));
        book.setDiscount(Double.parseDouble(discount));
        book.setAuthor(author);
        book.setPress(press);
        book.setPublishtime(publishtime);
        book.setEdition(Integer.parseInt(edition));
        book.setPageNum(Integer.parseInt(pageNum));
        book.setWordNum(Integer.parseInt(wordNum));
        book.setPrinttime(printtime);
        book.setPaper(paper);
        book.setBooksize(Integer.parseInt(booksize));
        //获取分类的cid,给book设置category
        book.setCategory(categoryService.findCategoryByCid(cid));
        // 使用fileupload组件完成文件上传
        // 上传的位置
        String path = session.getServletContext().getRealPath("/img/book_img/");
        String fileName = null;
//        try {
//            fileName = UploadUtils.fileName(path, image_w);
//        } catch (UserException e) {
//            mv.addObject("msg",e.getMessage());
//            mv.addObject("book",book);//回显数据
//            mv.setViewName("forward:/pages/admin/book/add.jsp");
//            return mv;
//        }
        fileName = UploadUtils.fileName(path, image_w);
        if (fileName ==null){
            mv.addObject("msg","文件上传失败!");
            mv.addObject("book",book);//回显数据
            mv.addObject("parents",categoryService.findParents());//回显一级分类数据
            mv.setViewName("forward:/pages/admin/book/add.jsp");
            return mv;
        }
        String file_path="img/book_img/"+fileName;
        //设置大图
        book.setImage_w(file_path);
        System.out.println("image_w = "+file_path);

//        //获取文件名称
//        try {
//            fileName = UploadUtils.fileName(path, image_b);
//        } catch (UserException e) {
//            mv.addObject("msg",e.getMessage());
//            mv.addObject("book",book);//回显数据
//            mv.setViewName("forward:/pages/admin/book/add.jsp");
//            return mv;
//        }
        fileName = UploadUtils.fileName(path, image_b);
        if (fileName ==null){
            mv.addObject("msg","文件上传失败!");
            mv.addObject("book",book);//回显数据
            mv.addObject("parents",categoryService.findParents());//回显一级分类数据
            mv.setViewName("forward:/pages/admin/book/add.jsp");
            return mv;
        }
        file_path="img/book_img/"+fileName;
        System.out.println("image_b = "+file_path);
        //设置小图
        book.setImage_b(file_path);
        bookService.addBook(book);
        mv.addObject("msg","添加成功");
        mv.setViewName("forward:/pages/admin/admin_msg.jsp");
        return mv;

    }

    /**
     * 修改图书
     * @param bid
     * @param bname
     * @param image_w
     * @param image_b
     * @param currPrice
     * @param price
     * @param discount
     * @param author
     * @param press
     * @param publishtime
     * @param edition
     * @param pageNum
     * @param wordNum
     * @param printtime
     * @param booksize
     * @param paper
     * @param cid
     * @return
     */
    @RequestMapping("/updateBook.do")
    public ModelAndView updateBook(String bid,String bname, String image_w,String image_b,
                                   String currPrice,String price,String discount,String author,
                                   String press,String publishtime,String edition,String pageNum,
                                   String wordNum,String printtime,String booksize,String paper,
                                   String cid ){
        ModelAndView mv = new ModelAndView();
        Book book = new Book();
        book.setBid(bid);
        book.setBname(bname);
        book.setCurrPrice(Double.parseDouble(currPrice));
        book.setPrice(Double.parseDouble(price));
        book.setDiscount(Double.parseDouble(discount));
        book.setAuthor(author);
        book.setPress(press);
        book.setPublishtime(publishtime);
        book.setEdition(Integer.parseInt(edition));
        book.setPageNum(Integer.parseInt(pageNum));
        book.setWordNum(Integer.parseInt(wordNum));
        book.setPrinttime(printtime);
        book.setPaper(paper);
        book.setBooksize(Integer.parseInt(booksize));
        //获取分类的cid,给book设置category
        book.setCategory(categoryService.findCategoryByCid(cid));

        //调用service完成修改
        bookService.updateBook(book);
        //保存信息，转发到admin_msg.jsp
        mv.addObject("msg","修改图书成功");
        mv.setViewName("forward:/pages/admin/admin_msg.jsp");
        return mv;

    }

    /**
     * 删除图书
     * @param bid
     * @return
     */
    @RequestMapping("/deleteBook.do")
    public ModelAndView deleteBook(String bid,HttpSession session){
        ModelAndView mv = new ModelAndView();
        //根据bid查询图书
        Book book = bookService.load(bid);
        //删除大小图片
        String path = session.getServletContext().getRealPath("/");//获取真实路径
        new File(path,book.getImage_w()).delete();//删除文件(大图片)
        new File(path,book.getImage_b()).delete();//删除文件(小图片)
        //调用service删除图书
        bookService.deleteBook(book);
        //保存信息，转发到admin_msg.jsp
        mv.addObject("msg","删除图书成功！！");
        mv.setViewName("forward:/pages/admin/admin_msg.jsp");
        return mv;
    }

}
