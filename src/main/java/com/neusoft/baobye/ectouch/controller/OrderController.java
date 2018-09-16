package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.OrderInfo;
import com.neusoft.baobye.ectouch.entity.OrderMiddle;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.entity.WapUserAddress;
import com.neusoft.baobye.ectouch.mapper.OrderInfoMapper;
import com.neusoft.baobye.ectouch.mapper.OrderMiddleMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserAddressMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import com.neusoft.baobye.ectouch.util.HttpClientUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.vividsolutions.jts.geomgraph.index.EdgeSetIntersector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{

    @Autowired
    private WapUserMapper userMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private WapUserAddressMapper wapUserAddressMapper;

    @Autowired
    private OrderMiddleMapper orderMiddleMapper;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);


    /**
     * 全部订单
     * @param page
     * @param size
     * @param model
     * @return
     */
    @RequestMapping("/allOrder")
    @Transactional
    public String allOrder(int page,int size ,Model model){
        page = page - 1;
        Sort sort = new Sort(Sort.Direction.DESC,"insert_Date");
        PageRequest pageable = PageRequest.of(page,size,sort);
        Page<OrderInfo> pageObject = orderInfoMapper.nativeAllOrderQuery(getUserId(),pageable);
        model.addAttribute("list",pageObject.getContent());//当前列表
        model.addAttribute("number",pageObject.getNumber()+1);//当前页面
        model.addAttribute("size",pageObject.getSize());//每页个数
        model.addAttribute("pages",pageObject.getTotalPages());//总页数
        return "order/allOrder";
    }


    /**
     * 待收货
     * @param page
     * @param size
     * @param model
     * @return
     */
    @RequestMapping("/notShouHuo")
    @Transactional
    public String notShouHuo(int page, int size, Model model){
        page = page - 1;
        Sort sort = new Sort(Sort.Direction.DESC,"insertDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        //状态2 已发货了
        Page<OrderInfo> pageObject = orderInfoMapper.findByUserIdAndStatus(getUserId(),2,pageable);
        model.addAttribute("list",pageObject.getContent());//当前列表
        model.addAttribute("number",pageObject.getNumber()+1);//当前页面
        model.addAttribute("size",pageObject.getSize());//每页个数
        model.addAttribute("pages",pageObject.getTotalPages());//总页数
        return "order/notShouHuo";
    }

    /**
     * 订单详情
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping("/orderDetail/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model){
        OrderInfo orderInfo = orderInfoMapper.findByOrderId(orderId);
        WapUserAddress address = wapUserAddressMapper.findByAddId(orderInfo.getAddId());
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("address",address);
        List<OrderMiddle> list = orderMiddleMapper.findByOrderId(orderId);
        model.addAttribute("list",list);
        return "order/orderDetail";
    }

    /**
     * 待发货详情
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping("/orderDeliveryDetail/{orderId}")
    public String orderDeliveryDetail(@PathVariable long orderId, Model model){
        OrderInfo orderInfo = orderInfoMapper.findByOrderId(orderId);
        WapUserAddress address = wapUserAddressMapper.findByAddId(orderInfo.getAddId());
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("address",address);
        List<OrderMiddle> list = orderMiddleMapper.findByOrderId(orderId);
        model.addAttribute("list",list);
        return "order/orderDeliveryDetail";
    }

    /**
     * 确认发货
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping("/delivery/{orderId}")
    public String delivery(@Value("${hhmg.server.delivery}") String url, @PathVariable long orderId, Model model){
        Map<String, String> map = new HashMap<String, String>();
        map.put("SHIPPER", ""+getUserId());//发货人ID
        map.put("ORDER_ID", ""+orderId);//订单编号
        map.put("TYPE","1");;// 发货人ID  TYPE=1实体库发货，  TYPE=2云仓库发货
        String body = null;
        try {
            body = HttpClientUtil.sendPostDataByMap(url, map, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("响应结果：" + body);
        return "redirect:/order/lowerOrder";
    }


    /**
     * 待发货订单
     * @param page
     * @param size
     * @param model
     * @return
     */
    @RequestMapping("lowerOrder")
    public String lowerOrder(Integer page,Integer size,Model model){

        if(size == null){
            size = 5;
        }
        if(page == null) {
            page = 0;
        }else{
            page = page - 1;
        }
        Sort sort = new Sort(Sort.Direction.DESC,"insert_Date");
        PageRequest pageable = PageRequest.of(page,size,sort);
        Page<OrderInfo> pageObject = orderInfoMapper.nativeQuery(getUserId(),pageable);
        model.addAttribute("list",pageObject.getContent());//当前列表
        model.addAttribute("number",pageObject.getNumber()+1);//当前页面
        model.addAttribute("size",pageObject.getSize());//每页个数
        model.addAttribute("pages",pageObject.getTotalPages());//总页数
        return "order/lowerOrder";
    }
    /**
     * 确认收货
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping("/receive/{orderId}")
    public String receive(@Value("${hhmg.server}") String url, @PathVariable long orderId, Model model){
        Map<String, String> map = new HashMap<String, String>();
        map.put("ORDER_ID", ""+orderId);//订单编号
        String body = null;
        try {
            body = HttpClientUtil.sendPostDataByMap(url+"/wapImpl/receive", map, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("响应结果：" + body);
        return "redirect:/";
    }
}
