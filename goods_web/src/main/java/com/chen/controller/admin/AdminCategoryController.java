package com.chen.controller.admin;

import com.chen.domain.Category;
import com.chen.service.BookService;
import com.chen.service.CategoryService;
import com.chen.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 管理员的分类管理的controller表示层
 */
@Controller
@RequestMapping("/adminCategory")
public class AdminCategoryController {
    @Autowired
    private CategoryService  categoryService;
    @Autowired
    private BookService bookService;

    /**
     * 查询所有分类
     * @return
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<Category> categoryList= categoryService.findAll();
        mv.addObject("categoryList",categoryList);
        mv.setViewName("forward:/pages/admin/category/list.jsp");
        return mv;
    }

    /**
     * 添加二级分类的第一步
     * @param category
     * @return
     */
    @RequestMapping("/addParent.do")
    public String addParent(Category category){
        //设置cid
        category.setCid(CommonUtils.uuid());
        //手动添加pid
        Category parent = new Category();
        parent.setCid(null);
        //调用service添加一级分类
        category.setParent(parent);
        categoryService.addParent(category);
        return "redirect:findAll.do";
    }

    /**
     * 添加二级分类的第一步
     * @param pid
     * @return
     */
    @RequestMapping("/addChildPre.do")
    public ModelAndView addChildPre(@RequestParam("pid")String pid){
        ModelAndView mv = new ModelAndView();
        //查询所有父分类，展示在下拉框，并保存转发到add2.jsp
        List<Category> parents = categoryService.findParents();
       //根据pid查询它的父分类，默认展示它的名称在add2.jsp中下拉框中
        mv.addObject("pid",pid);
        mv.addObject("parents",parents);
        mv.setViewName("forward:/pages/admin/category/add2.jsp");
        return mv;
    }
    /**
     * 添加二级分类的第二步
     * @param category
     * @return
     */
    @RequestMapping("/addChild.do")
    public String addChild(Category category,@RequestParam("pid")String pid){
        //设置cid
        category.setCid(CommonUtils.uuid());
        //手动添加父分类
        Category parent = new Category();
        parent.setCid(pid);
        category.setParent(parent);
        //调用service添加二级分类
        categoryService.addParent(category);
        return "redirect:findAll.do";
    }

    /**
     * 修改一级分类的第一步
     * @param cid
     * @return
     */
    @RequestMapping("/upateParentPre.do")
    public ModelAndView upateParentPre(@RequestParam("cid")String cid){
        ModelAndView mv = new ModelAndView();
        //1.获取cid,根据cid查询分类，并保存，展示在edit.jsp上
        Category category = categoryService.findCategoryByCid(cid);
        mv.addObject("parent",category);
        mv.setViewName("forward:/pages/admin/category/edit.jsp");
        return mv;
    }

    /**
     * 修改一级分类的第二步
     * @param category
     * @return
     */
    @RequestMapping("/upateParent.do")
    public String  upateParent(Category category){
        //封装form表单的数据到category中
        //手动设置父分类的cid
        Category parent = new Category();
        parent.setCid(null);
        category.setParent(parent);
        //调用service修改父分类
        categoryService.updateCategory(category);
        //转发到list.jsp
        return "redirect:findAll.do";
    }

    /**
     * 修改二级分类的第一步
     * @param cid
     * @return
     */
    @RequestMapping("/upateChildPre.do")
    public ModelAndView upateChildtPre(@RequestParam("cid")String cid){
        ModelAndView mv = new ModelAndView();
        //1.获取cid,根据cid查询分类，并保存，展示在edit2.jsp上
        Category category = categoryService.findCategoryByCid(cid);
        //2.查询所有的父分类，并保存，展示在edit2.jsp上的下拉框
        List<Category> parents = categoryService.findParents();
        mv.addObject("child",category);
        mv.addObject("parents",parents);
        mv.setViewName("forward:/pages/admin/category/edit2.jsp");
        return mv;
    }

    /**
     * 修改二级分类的第二步
     * @param category
     * @return
     */
    @RequestMapping("/upateChild.do")
    public String  upateChild(Category category,@RequestParam("pid")String pid){
        //封装form表单的数据到category中
        //获取父分类的cid
        Category parent = new Category();
        parent.setCid(pid);
        category.setParent(parent);
        //调用service修改父分类
        categoryService.updateCategory(category);
        //转发到list.jsp
        return "redirect:findAll.do";
    }
    /**
     * 删除一级分类
     * @param request
     * @return
     */
    @RequestMapping("/deleteParent.do")
    public String deleteParent(HttpServletRequest request){
        //获取cid，根据cid查询一级分类是否有二级分类
        String cid = request.getParameter("cid");
        int count = categoryService.findChildCountByCid(cid);
        if (count>0){//大于0存在二级分类
            //保存错误信息
            request.setAttribute("msg","该分类还有子分类，不能删除！！");
            return "forward:/pages/admin/admin_msg.jsp";
        }else {
            categoryService.deleteCategory(cid);
            return "redirect:findAll.do";
        }

    }
    /**
     * 删除二级分类
     * @param request
     * @return
     */
    @RequestMapping("/deleteChild.do")
    public String deleteChild(HttpServletRequest request){
        //获取cid，根据cid查询二级分类是否存在图书
        String cid = request.getParameter("cid");
        int count = bookService.findBookCountByCategory(cid);
        if (count>0){//大于0存在二级分类
            //保存错误信息
            request.setAttribute("msg","该分类下还有图书，不能删除！！");
            return "forward:/pages/admin/admin_msg.jsp";
        }else {
            categoryService.deleteCategory(cid);
            return "redirect:findAll.do";
        }
    }
}
