package com.chen.controller.user;

import com.chen.domain.Cartitem;
import com.chen.domain.Order;
import com.chen.domain.OrderItem;
import com.chen.domain.User;
import com.chen.service.CartitemService;
import com.chen.service.OrderService;
import com.chen.utils.CommonUtils;
import com.chen.utils.PaymentUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 订单模块的controller表示层接口
 */
@Controller
@RequestMapping("/order")
public class OrderController {
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
     * 根据用户查询订单
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/findOrderByUser.do")
    public ModelAndView findOrderByUser(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "pageSize",defaultValue = "8")int pageSize, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        //获取存在seesion中的user
        User user = (User)request.getSession().getAttribute("user");
        //调用service方法，得到订单集合
        List<Order> orderList = orderService.findOrderByUser(user.getUid(),page,pageSize);
        PageInfo pageInfo = new PageInfo(orderList);
        //保存信息
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("url",getUrl(request)+"?uid="+user.getUid());
        //转发到订单的页面
        mv.setViewName("forward:/pages/user/order/list.jsp");
        return mv;

    }

    /**
     * 生成订单
     * @return
     */
    @RequestMapping("/createOrder.do")
    public ModelAndView createOrder(HttpSession session,@RequestParam("cartItemIds")String cartItemIds,@RequestParam("address")String address){
        ModelAndView mv = new ModelAndView();
        //获取保存在session中的user
        User sessionUser = (User)session.getAttribute("user");
        //创建order对象
        Order order = new Order();
        order.setOid(CommonUtils.uuid()); //设置订单号
        order.setOrdertime(String.format("%tF %<tT",new Date()));//设置下单时间
        order.setStatus(1); //设置订单状态,未付款
        order.setAddress(address);//设置收获地址
        order.setUser(sessionUser);//设置所属用户
        BigDecimal total = new BigDecimal("0");
        //根据cartItemIds查询购物条目
        List<Cartitem> cartitemList = cartitemService.showCartItems(cartItemIds);
        for (Cartitem cartitem : cartitemList) {
             total = total.add(new BigDecimal(cartitem.getSubTotal()));
        }
        order.setTotal(total.doubleValue());//设置总金额

        //创建多条orderItem条目
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Cartitem cartitem : cartitemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(CommonUtils.uuid());//设置订单条目的id
            orderItem.setQuantity(cartitem.getQuantity());//设置数量
            orderItem.setBook(cartitem.getBook());//设置图书
            orderItem.setSubtotal(cartitem.getSubTotal());//设置小计
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);//设置订单条目
        //调用service生成订单
        orderService.addOrder(order);
        //生成订单之后删除购物车中所购买的购物条目
        cartitemService.batchDelete(cartItemIds);
        mv.addObject("order",order);
        mv.setViewName("forward:/pages/user/order/ordersucc.jsp");
        return mv;
    }

    /**
     * 根据oid查询订单
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
        mv.setViewName("forward:/pages/user/order/desc.jsp");
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
            mv.setViewName("forward:/pages/user/order-msg.jsp");
            return mv;
        }
        //调用service修改状态为5
        orderService.updateStatus(oid,5);
        mv.addObject("code","success");
        mv.addObject("msg","取消订单成功，您确定不后悔！！");
        mv.setViewName("forward:/pages/user/order-msg.jsp");
        return mv;
    }
    /**
     * 确认收货
     * @param oid
     * @return
     */
    @RequestMapping("/confirmOrder.do")
    public ModelAndView confirmOrder(@RequestParam("oid")String oid){
        ModelAndView mv = new ModelAndView();
        //根据oid查询订单
        Order order = orderService.findByOid(oid);
        //校验订单的状态是否为3
        if (order.getStatus() !=3){
            mv.addObject("code","error");
            mv.addObject("msg","状态不对，不能确认收货");
            mv.setViewName("forward:/pages/user/order-msg.jsp");
            return mv;
        }
        //调用service修改状态为4
        orderService.updateStatus(oid,4);
        mv.addObject("code","success");
        mv.addObject("msg","交易成功，欢迎下次购买！！");
        mv.setViewName("forward:/pages/user/order-msg.jsp");
        return mv;
    }

    /**
     * 准备支付
     * @param oid
     * @return
     */
    @RequestMapping("/pay.do")
    public ModelAndView pay(@RequestParam("oid")String oid){
        ModelAndView mv = new ModelAndView();
        //根据oid查询订单
        Order order = orderService.findByOid(oid);
        mv.addObject("order",order);
        mv.setViewName("forward:/pages/user/order/pay.jsp");
        return mv;
    }

    /**
     * 支付
     * @param request
     * @return
     */
    @RequestMapping("/payment.do")
    public void payment(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
        /*
         * 1. 准备13个参数
         */
        String p0_Cmd = "Buy";//业务类型，固定值Buy
        String p1_MerId = props.getProperty("p1_MerId");//商号编码，在易宝的唯一标识
        String p2_Order = request.getParameter("oid");//订单编码
        String p3_Amt = "0.01";//支付金额
        String p4_Cur = "CNY";//交易币种，固定值CNY
        String p5_Pid = "";//商品名称
        String p6_Pcat = "";//商品种类
        String p7_Pdesc = "";//商品描述
        String p8_Url = props.getProperty("p8_Url");//在支付成功后，易宝会访问这个地址。
        String p9_SAF = "";//送货地址
        String pa_MP = "";//扩展信息
        String pd_FrpId = request.getParameter("yh");//支付通道
        String pr_NeedResponse = "1";//应答机制，固定值1

        /*
         * 2. 计算hmac
         * 需要13个参数
         * 需要keyValue
         * 需要加密算法
         */
        String keyValue = props.getProperty("keyValue");
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);

        /*
         * 3. 重定向到易宝的支付网关
         */
        StringBuilder sb = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node");
        sb.append("?").append("p0_Cmd=").append(p0_Cmd);
        sb.append("&").append("p1_MerId=").append(p1_MerId);
        sb.append("&").append("p2_Order=").append(p2_Order);
        sb.append("&").append("p3_Amt=").append(p3_Amt);
        sb.append("&").append("p4_Cur=").append(p4_Cur);
        sb.append("&").append("p5_Pid=").append(p5_Pid);
        sb.append("&").append("p6_Pcat=").append(p6_Pcat);
        sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
        sb.append("&").append("p8_Url=").append(p8_Url);
        sb.append("&").append("p9_SAF=").append(p9_SAF);
        sb.append("&").append("pa_MP=").append(pa_MP);
        sb.append("&").append("pd_FrpId=").append(pd_FrpId);
        sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
        sb.append("&").append("hmac=").append(hmac);

        response.sendRedirect(sb.toString());
    }

    /**
     * 回馈方法
     * 支付成功时，易宝会访问这里
     * 用两种方法访问：
     *    1.引导用户的浏览器重定向，如果用户关闭了浏览器，就不能访问这里
     *    2.易宝的服务器会使用点对点通讯的方法访问这个方法（必须回馈success）,不然易宝服务器会一直调用这个方法
     * @param request
     * @return
     */
    @RequestMapping("/back.do")
    public ModelAndView back(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();
        /*
         * 1. 获取12个参数
         */
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String hmac = request.getParameter("hmac");
        /*
         * 2. 获取keyValue
         */
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
        String keyValue = props.getProperty("keyValue");
        /*
         * 3. 调用PaymentUtil的校验方法来校验调用者的身份
         *   >如果校验失败：保存错误信息，转发到msg.jsp
         *   >如果校验通过：
         *     * 判断访问的方法是重定向还是点对点，如果要是重定向
         *     修改订单状态，保存成功信息，转发到msg.jsp
         *     * 如果是点对点：修改订单状态，返回success
         */
        boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId,
                r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType,
                keyValue);
        if(!bool) {
//            req.setAttribute("code", "error");
//            req.setAttribute("msg", "无效的签名，支付失败！（你不是好人）");
//            return "f:/jsps/msg.jsp";
            mv.addObject("code","error");
            mv.addObject("msg","无效的签名，支付失败！(您不是好人！！)");
            mv.setViewName("forward:/pages/user/order-msg.jsp");
            return mv;
        }
        if(r1_Code.equals("1")) {
            orderService.updateStatus(r6_Order, 2);
            if(r9_BType.equals("1")) {
//                req.setAttribute("code", "success");
//                req.setAttribute("msg", "恭喜，支付成功！");
//                return "f:/jsps/msg.jsp";
                mv.addObject("code","error");
                mv.addObject("msg","支付成功！");
                mv.setViewName("forward:/pages/user/order-msg.jsp");
            } else if(r9_BType.equals("2")) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println("success");
            }
        }


        return mv;

    }
}
