package com.chen.controller.user;

import com.chen.domain.Category;
import com.chen.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 图书分类模块的controller表示层
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类
     * @return
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<Category> parents = categoryService.findAll();
        mv.addObject("parents",parents);
        mv.setViewName("forward:/pages/user/left.jsp");
        return mv;

    }

}
