package com.chen.controller.admin;

import com.chen.domain.Order;
import com.chen.domain.User;
import com.chen.service.CartitemService;
import com.chen.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 管理员订单模块的controller表示层
 */
@Controller
@RequestMapping("/adminOrder")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartitemService cartitemService;

    /**
     * 得到请求的路径，用于分页查询时请求的路径
     * @param request
     * @return
     */
    private String getUrl(HttpServletRequest request){
        return request.getRequestURI();
    }

    /**
     * 查询所有订单
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/findAllOrder.do")
    public ModelAndView findAllOrder(@RequestParam(value = "page",defaultValue = "1")int page, @RequestParam(value = "pageSize",defaultValue = "8")int pageSize, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        //调用service方法，得到订单集合
        List<Order> orderList = orderService.findAllOrder(page,pageSize);
        PageInfo pageInfo = new PageInfo(orderList);
        //保存信息
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("url",getUrl(request)+"?1=1");
        //转发到订单的页面
        mv.setViewName("forward:/pages/admin/order/list.jsp");
        return mv;

    }

    /**
     * 根据status查询订单
     * @param status
     * @param request
     * @return
     */
    @RequestMapping("/findByStatus.do")
    public ModelAndView findOrderByStatus(@RequestParam(value = "page",defaultValue = "1")int page, @RequestParam(value = "pageSize",defaultValue = "8")int pageSize,
                                          @RequestParam("status")String status,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        //调用service方法，得到订单集合
        List<Order> orderList = orderService.findByStatus(status,page,pageSize);
        PageInfo pageInfo = new PageInfo(orderList);
        //保存信息
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("url",getUrl(request)+"?status="+status);
        //转发到订单的页面
        mv.setViewName("forward:/pages/admin/order/list.jsp");
        return mv;

    }

    /**
     * 根据oid查询订单详细信息
     * cartItemIds
     * @param oid
     * @param btn
     * @return
     */
    @RequestMapping("/showOrder.do")
    public ModelAndView showOrder(@RequestParam("oid")String oid,@RequestParam("btn")String btn){
        ModelAndView mv  = new ModelAndView();
        Order order = orderService.findByOid(oid);
        mv.addObject("order",order);
        //保存获取到的btn,传回给desc.jsp
        mv.addObject("btn",btn);
        mv.setViewName("forward:/pages/admin/order/desc.jsp");
        return mv;

    }


    /**
     * 取消订单
     * @param oid
     * @return
     */
    @RequestMapping("/cancelOrder.do")
    public ModelAndView cancelOrder(@RequestParam("oid")String oid){
        ModelAndView mv = new ModelAndView();
        //根据oid查询订单
        Order order = orderService.findByOid(oid);
        //校验订单的状态是否为1
        if (order.getStatus() !=1){
            mv.addObject("code","error");
            mv.addObject("msg","状态不对，不能取消");
            mv.setViewName("forward:/pages/admin/admin_msg.jsp");
            return mv;
        }
        //调用service修改状态为5
        orderService.updateStatus(oid,5);
        mv.addObject("code","success");
        mv.addObject("msg","取消订单成功，您确定不后悔！！");
        mv.setViewName("forward:/pages/admin/admin_msg.jsp");
        return mv;
    }
    /**
     * 发货
     * @param oid
     * @return
     */
    @RequestMapping("/confirmOrder.do")
    public ModelAndView confirmOrder(@RequestParam("oid")String oid){
        ModelAndView mv = new ModelAndView();
        //根据oid查询订单
        Order order = orderService.findByOid(oid);
        //校验订单的状态是否为2（是否已付款）
        if (order.getStatus() !=2){
            mv.addObject("code","error");
            mv.addObject("msg","状态不对，不能发货");
            mv.setViewName("forward:/pages/admin/admin_msg.jsp");
            return mv;
        }
        //调用service修改状态为3
        orderService.updateStatus(oid,3);
        mv.addObject("code","success");
        mv.addObject("msg","发货成功，请注意查看物流！！");
        mv.setViewName("forward:/pages/admin/admin_msg.jsp");
        return mv;
    }

    /**
     * 按下单时间模糊查询
     * @param ordertime
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/findByOrdertime.do")
    public ModelAndView findByOrdertime(String ordertime,@RequestParam(value = "page",defaultValue = "1")int page,
    @RequestParam(value = "pageSize",defaultValue = "8")int pageSize,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        //调用service方法，得到订单集合
        List<Order> orderList = orderService.findByOrdertime(ordertime,page,pageSize);
        PageInfo pageInfo = new PageInfo(orderList);
        //保存信息
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("url",getUrl(request)+"?ordertime="+ordertime);
        //转发到订单的页面
        mv.setViewName("forward:/pages/admin/order/list.jsp");
        return mv;
    }

}
