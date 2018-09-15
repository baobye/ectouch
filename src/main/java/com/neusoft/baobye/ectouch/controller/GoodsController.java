package com.neusoft.baobye.ectouch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.baobye.ectouch.entity.*;
import com.neusoft.baobye.ectouch.mapper.*;
import com.neusoft.baobye.ectouch.util.HighPreciseComputor;
import com.neusoft.baobye.ectouch.util.IdGenerator;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GoodsController extends BaseController{

    @Autowired
    private WapUserMapper userMapper;

    @Autowired
    private GoodInfoMapper goodInfoMapper;

    @Autowired
    private GoodCartMapper goodCartMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WapUserAddressMapper addressMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderMiddleMapper orderMiddleMapper;

    final IdGenerator idg = IdGenerator.INSTANCE;

    /**
     * 所有商品
     * @param server
     * @param model
     * @return
     */
    @RequestMapping("/goods/index")
    public String index(@Value("${hhmg.server}") String server,Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user = userMapper.findByUsername(name);
        model.addAttribute("server",server);
        model.addAttribute("wapUser",user);
        return "goods/index";
    }
    @RequestMapping("/goods/indexAjax")
    @ResponseBody
    public List<GoodInfo> indexAjax(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC,"insertDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        //查询所有商品
        Page<GoodInfo> pageObject = goodInfoMapper.findAll(pageable);
        return pageObject.getContent();
    }

    /**
     * 商品详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/goods/detail/{id}")
    public String detail(@Value("${hhmg.server}") String server,@PathVariable Long id,Model model){
        //购物车数量
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
        /**
         * 购物车
         */
        List<GoodCart> list= goodCartMapper.findAllByUserId(user.getUserId());
        model.addAttribute("total_number",0);
        GoodInfo goodInfo = goodInfoMapper.findByGoodId(id);
        model.addAttribute("goodInfo",goodInfo);
        model.addAttribute("user",user);
        model.addAttribute("server",server);
        if(list != null && list.size() >0){
            Integer totalNumber = 0;
            if(list != null && list.size() >0){
                for(GoodCart goodCart : list){
                    totalNumber = Integer.valueOf(HighPreciseComputor.addInt(totalNumber,goodCart.getGoodsNumber()));
                }
                //购物车总数量
                model.addAttribute("total_number",totalNumber);
            }
            return "goods/detail";
        }

        return "goods/detail";
    }
    //添加到购物车
    @RequestMapping("/goods/addToCart")
    @ResponseBody
    public Map purchase(Model model, HttpServletRequest request){
        //1、首先到购物车表 查 是否有记录
//        return "goods/addToCart";
        String goods = request.getParameter("goods");//{"quick":1,"spec":[""],"goods_id":1,"number":"1","parent":0}

        Map map = null;
        try {
            map = objectMapper.readValue(goods,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //商品id
        long goodId = Long.parseLong((String)map.get("goods_id"));
        //获得商品
        GoodInfo goodInfo = goodInfoMapper.findByGoodId(goodId);
        if(goodInfo != null){
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            WapUser user  = userMapper.findByUsername(name);

            //先判断这个商品购物车中有没有

            GoodCart goodCart = goodCartMapper.findByUserIdAndGoodsId(user.getUserId(),(long)goodId);
            List<TbPictures> list = new ArrayList(goodInfo.getPictures());
            String path = "";
            if(list != null && list.size()>0){
                path = list.get(0).getPath();
            }
            if(goodCart == null){
                GoodCart cart = new GoodCart();
                cart.setGoodsId(goodId);
                cart.setGoodsName(goodInfo.getGoodName());
                cart.setGoodsNumber(Integer.parseInt((String)map.get("number")));
                cart.setUserId(user.getUserId());
                cart.setPath(path);
                if(user.getStatus() == 2){
                    cart.setGoodsPrice(goodInfo.getaPrice());
                }
                Long level = Long.parseLong((String) map.get("level"));
                if(level == 1){
//                    0公司，1花冠，2花朵，3花瓣，4花蕾，5花芽，6花粉
                    cart.setGoodsPrice(goodInfo.getaPrice());
                }else if(level == 2){
                    cart.setGoodsPrice(goodInfo.getbPrice());
                }else if(level == 3){
                    cart.setGoodsPrice(goodInfo.getcPrice());
                }else if(level == 4){
                    cart.setGoodsPrice(goodInfo.getdPrice());
                }else if(level == 5){
                    cart.setGoodsPrice(goodInfo.getePrice());
                }else if(level == 6){
                    cart.setGoodsPrice(goodInfo.getfPrice());
                }
                goodCartMapper.save(cart);
                int number = Integer.parseInt((String)map.get("number"));
                double totalNumber = 0;
            }else{

                int number = goodCart.getGoodsNumber()+Integer.parseInt((String)map.get("number"));
                Long level = Long.parseLong((String) map.get("level"));
                if(level == 1){
//                    0公司，1花冠，2花朵，3花瓣，4花蕾，5花芽，6花粉
                    goodCart.setGoodsPrice(goodInfo.getaPrice());
                }else if(level == 2){
                    goodCart.setGoodsPrice(goodInfo.getbPrice());
                }else if(level == 3){
                    goodCart.setGoodsPrice(goodInfo.getcPrice());
                }else if(level == 4){
                    goodCart.setGoodsPrice(goodInfo.getdPrice());
                }else if(level == 5){
                    goodCart.setGoodsPrice(goodInfo.getePrice());
                }else if(level == 6){
                    goodCart.setGoodsPrice(goodInfo.getfPrice());
                }
                //更新购物车价格
                goodCart.setGoodsNumber(number);
                goodCartMapper.save(goodCart);
            }
            //这是现在是单个商品的个数  要返回所有商品的个数
            double totalNumber = 0;
            List<GoodCart> carts= goodCartMapper.findAllByUserId(user.getUserId());
            if(carts != null && carts.size() >0){
                for(GoodCart cart : carts){
                    double sum = HighPreciseComputor.mul(cart.getGoodsNumber(),cart.getGoodsPrice());
                    totalNumber =HighPreciseComputor.add(totalNumber,cart.getGoodsNumber());
                }
                map.put("cart_number",totalNumber);
            }
        }
        map.put("error",0);

        return map;
    }

    /**
     * 前往立即结算页面  首次购物要重新算价格的
     * @param model
     * @return
     */
    @RequestMapping("/goods/checkout/{level}")
    public String checkout(@PathVariable Long level ,Model model, @Value("${total.money}") String totalMoney){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
        //把默认地址查到
        List<WapUserAddress> list = addressMapper.findByUserIdAndDefaultAdd(user.getUserId(),1);
        if(list != null && list.size() >0){
            WapUserAddress address = list.get(0);
            model.addAttribute("address",address);
        }
        //显示余额信息
        model.addAttribute("user",user);

        //显示商品信息
        List<GoodCart> goodCartList= goodCartMapper.findAllByUserId(user.getUserId());

        /**
         * 根据会员购买金额 和 购买数量重新算购物车内 商品的价格
         */
        if(goodCartList != null && goodCartList.size() >0){
            double subtotal = 0.0;
            double totalNumber = 0;

            /**
            if(user.getStatus() == 2){//代表新用户购买。
                List listA = new ArrayList();
                List listB = new ArrayList();
                List listC = new ArrayList();
                List listD = new ArrayList();
                List listE = new ArrayList();
                List listF = new ArrayList();
                double subtotalA = 0.0;
                double totalNumberA = 0.0;
                double subtotalB = 0.0;
                double totalNumberB = 0.0;
                double subtotalC = 0.0;
                double totalNumberC = 0.0;
                double subtotalD = 0.0;
                double totalNumberD = 0.0;
                double subtotalE = 0.0;
                double totalNumberE = 0.0;
                double subtotalF = 0.0;
                double totalNumberF = 0.0;
                //1、新用户购物车价格默认是花冠的价格 要根据实际购买数量修改会员级别
                //1
                for(GoodCart goodCart : goodCartList){

                    GoodCart goodCartA = new GoodCart();
                    GoodCart goodCartB = new GoodCart();
                    GoodCart goodCartC = new GoodCart();
                    GoodCart goodCartD = new GoodCart();
                    GoodCart goodCartE = new GoodCart();
                    GoodCart goodCartF = new GoodCart();

                    try {
                        BeanUtils.copyProperties(goodCartA,goodCart);
                        BeanUtils.copyProperties(goodCartB,goodCart);
                        BeanUtils.copyProperties(goodCartC,goodCart);
                        BeanUtils.copyProperties(goodCartD,goodCart);
                        BeanUtils.copyProperties(goodCartE,goodCart);
                        BeanUtils.copyProperties(goodCartF,goodCart);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    GoodInfo goodInfo = goodInfoMapper.findByGoodId(goodCart.getGoodsId());
                    double sumA = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodInfo.getaPrice());
                    subtotalA = HighPreciseComputor.add(subtotalA,sumA);//总金额
                    totalNumberA =HighPreciseComputor.add(totalNumberA,goodCart.getGoodsNumber());//总数量
                    goodCartA.setGoodsPrice(goodInfo.getaPrice());
                    goodCartA.setRecId(goodCart.getRecId());
                    listA.add(goodCartA);
                    double sumB = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodInfo.getbPrice());
                    subtotalB = HighPreciseComputor.add(subtotalB,sumB);//总金额
                    totalNumberB =HighPreciseComputor.add(totalNumberB,goodCart.getGoodsNumber());//总数量
                    goodCartB.setGoodsPrice(goodInfo.getbPrice());
                    goodCartB.setRecId(goodCart.getRecId());
                    listB.add(goodCartB);
                    double sumC = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodInfo.getcPrice());
                    subtotalC = HighPreciseComputor.add(subtotalC,sumC);//总金额
                    totalNumberC =HighPreciseComputor.add(totalNumberC,goodCart.getGoodsNumber());//总数量
                    goodCartC.setGoodsPrice(goodInfo.getcPrice());
                    goodCartC.setRecId(goodCart.getRecId());
                    listC.add(goodCartC);
                    double sumD = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodInfo.getdPrice());
                    subtotalD = HighPreciseComputor.add(subtotalD,sumD);//总金额
                    totalNumberD =HighPreciseComputor.add(totalNumberD,goodCart.getGoodsNumber());//总数量
                    goodCartD.setGoodsPrice(goodInfo.getdPrice());
                    goodCartD.setRecId(goodCart.getRecId());
                    listD.add(goodCartD);
                    double sumE = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodInfo.getePrice());
                    subtotalE = HighPreciseComputor.add(subtotalE,sumE);//总金额
                    totalNumberE =HighPreciseComputor.add(totalNumberE,goodCart.getGoodsNumber());//总数量
                    goodCartE.setGoodsPrice(goodInfo.getePrice());
                    goodCartE.setRecId(goodCart.getRecId());
                    listE.add(goodCartE);
                    double sumF = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodInfo.getfPrice());
                    subtotalF = HighPreciseComputor.add(subtotalF,sumF);//总金额
                    totalNumberF =HighPreciseComputor.add(totalNumberF,goodCart.getGoodsNumber());//总数量
                    goodCartF.setGoodsPrice(goodInfo.getfPrice());
                    goodCartF.setRecId(goodCart.getRecId());
                    listF.add(goodCartF);
                }
                String[] totalMoneys = totalMoney.split(",");
                //判断金额
                if(subtotalA > Double.parseDouble(totalMoneys[0])){
//                    0公司，1花冠，2花朵，3花瓣，4花蕾，5花芽，6花粉
                    model.addAttribute("level",1);
                    model.addAttribute("total_desc",subtotalA);
                    model.addAttribute("list",listA);
                    try {
                        String listAString = objectMapper.writeValueAsString(listA);
                        model.addAttribute("listString",listAString);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return "goods/checkout";
                }else if(subtotalB > Double.parseDouble(totalMoneys[1])){
                    model.addAttribute("level",2);
                    model.addAttribute("total_desc",subtotalB);
                    model.addAttribute("list",listB);
                    try {
                        String listBString = objectMapper.writeValueAsString(listB);
                        model.addAttribute("listString",listBString);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return "goods/checkout";
                }else if(subtotalC > Double.parseDouble(totalMoneys[2])){
                    model.addAttribute("level",3);
                    model.addAttribute("total_desc",subtotalC);
                    model.addAttribute("list",listC);
                    try {
                        String listCString = objectMapper.writeValueAsString(listC);
                        model.addAttribute("listString",listCString);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return "goods/checkout";
                }else if(subtotalD > Double.parseDouble(totalMoneys[3])){
                    model.addAttribute("level",4);
                    model.addAttribute("total_desc",subtotalD);
                    model.addAttribute("list",listD);
                    try {
                        String listDString = objectMapper.writeValueAsString(listD);
                        model.addAttribute("listString",listDString);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return "goods/checkout";
                }else if(subtotalE > Double.parseDouble(totalMoneys[4])){
                    model.addAttribute("level",5);
                    model.addAttribute("total_desc",subtotalE);
                    model.addAttribute("list",listE);
                    try {
                        String listEString = objectMapper.writeValueAsString(listE);
                        model.addAttribute("listString",listEString);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return "goods/checkout";
                }else{
                    model.addAttribute("level",6);
                    model.addAttribute("total_desc",subtotalF);
                    model.addAttribute("list",listF);
                    try {
                        String listFString = objectMapper.writeValueAsString(listF);
                        model.addAttribute("listString",listFString);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return "goods/checkout";
                }
            }else{
                if(list != null && list.size() >0){
                    for(GoodCart goodCart : goodCartList){
                        double sum = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodCart.getGoodsPrice());
                        subtotal = HighPreciseComputor.add(subtotal,sum);
                        totalNumber =HighPreciseComputor.add(totalNumber,goodCart.getGoodsNumber());
                    }
                    String listString = null;
                    try {
                        listString = objectMapper.writeValueAsString(goodCartList);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    model.addAttribute("listString",listString);
                }
            }*/
            if(goodCartList != null && goodCartList.size() >0){
                for(GoodCart goodCart : goodCartList){
                    GoodInfo goodInfo = goodInfoMapper.findByGoodId(goodCart.getGoodsId());
                    //重新计算价格
                    if(level == 1){
//                    0公司，1花冠，2花朵，3花瓣，4花蕾，5花芽，6花粉
                        goodCart.setGoodsPrice(goodInfo.getaPrice());
                    }else if(level == 2){
                        goodCart.setGoodsPrice(goodInfo.getbPrice());
                    }else if(level == 3){
                        goodCart.setGoodsPrice(goodInfo.getcPrice());
                    }else if(level == 4){
                        goodCart.setGoodsPrice(goodInfo.getdPrice());
                    }else if(level == 5){
                        goodCart.setGoodsPrice(goodInfo.getePrice());
                    }else if(level == 6){
                        goodCart.setGoodsPrice(goodInfo.getfPrice());
                    }
                    double sum = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodCart.getGoodsPrice());
                    subtotal = HighPreciseComputor.add(subtotal,sum);
                    totalNumber =HighPreciseComputor.add(totalNumber,goodCart.getGoodsNumber());
                }
                String listString = null;
                try {
                    listString = objectMapper.writeValueAsString(goodCartList);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                model.addAttribute("listString",listString);
            }
            //小计
            model.addAttribute("total_desc",subtotal);
        }
        model.addAttribute("list",goodCartList);

        return "goods/checkout";
    }

    /**
     * 生成订单
     * @return
     */
    @RequestMapping("/goods/done")
    @Transactional
    public String done(OrderInfo orderInfo, Model model,String listString,String level,@Value("${total.money}") String totalMoney) throws Exception {

        //用户信息
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
        Double taotal = orderInfo.getOrderPriceTotal();
        if(user.getDzb() < orderInfo.getOrderPriceTotal()){
            throw new Exception("电子币不足请充值");
        }

        //1.扣电子币
        user.setDzb(user.getDzb() - orderInfo.getOrderPriceTotal());
        userMapper.save(user);



        //订单号
        String code = idg.nextId();
        orderInfo.setUserId(user.getUserId());
        orderInfo.setOrderId(System.currentTimeMillis());
        orderInfo.setOrderCode(code);
        //        状态 未付款,已付款,已发货,已签收,退货申请,退货中,已退货,取消交易
//
//        0 未付款 ，1已付款 ，2已发货  3已签收  4换货申请中， 5换货处理中 6 取消交易
        orderInfo.setStatus(1);//
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderInfo.setInsertDate(dateFormat.format(new Date()));
        orderInfo.setPayDate(dateFormat.format(new Date()));
        orderInfoMapper.save(orderInfo);

        model.addAttribute("orderInfo",orderInfo);
        //2.生成订单中间表

        try {
            List<GoodCart> goodCartList = objectMapper.readValue(listString,new TypeReference<List<GoodCart>>() { });
            for (GoodCart goodCart:goodCartList){
                OrderMiddle orderMiddle = new OrderMiddle();
                orderMiddle.setGoodId(goodCart.getGoodsId());
                orderMiddle.setGoodName(goodCart.getGoodsName());
                orderMiddle.setGoodPrice(goodCart.getGoodsPrice());
                orderMiddle.setNum(goodCart.getGoodsNumber());
//                orderMiddle.setUserLevel(payment);
                orderMiddle.setStatus(0);
                orderMiddle.setType(0);
                orderMiddle.setOrderMiddleId(System.currentTimeMillis());
                orderMiddle.setOrderId(orderInfo.getOrderId());
                orderMiddle.setUserLevel(Integer.parseInt(level));
                orderMiddleMapper.save(orderMiddle);

            }
            //3.删除购物车
            goodCartMapper.deleteByUserId(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }



        return "goods/done";
    }

    /**
     * 去购物车  先看看本人购物车表的商品
     * @return
     */
    @RequestMapping("/cart/{level}")
    public String cart(@PathVariable Long level ,@Value("${hhmg.server}") String server,Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        WapUser user  = userMapper.findByUsername(name);
        List<GoodCart> list= goodCartMapper.findAllByUserId(user.getUserId());
        if(list != null && list.size() >0){
            double subtotal = 0.0;
            double totalNumber = 0.0;
            for(GoodCart goodCart : list){

                GoodInfo goodInfo = goodInfoMapper.findByGoodId(goodCart.getGoodsId());
                //重新计算价格
                if(level == 1){
//                    0公司，1花冠，2花朵，3花瓣，4花蕾，5花芽，6花粉
                    goodCart.setGoodsPrice(goodInfo.getaPrice());
                }else if(level == 2){
                    goodCart.setGoodsPrice(goodInfo.getbPrice());
                }else if(level == 3){
                    goodCart.setGoodsPrice(goodInfo.getcPrice());
                }else if(level == 4){
                    goodCart.setGoodsPrice(goodInfo.getdPrice());
                }else if(level == 5){
                    goodCart.setGoodsPrice(goodInfo.getePrice());
                }else if(level == 6){
                    goodCart.setGoodsPrice(goodInfo.getfPrice());
                }
                double sum = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodCart.getGoodsPrice());
                subtotal = HighPreciseComputor.add(subtotal,sum);
                totalNumber =HighPreciseComputor.add(totalNumber,goodCart.getGoodsNumber());
            }
            //购物车总数量
            model.addAttribute("total_number",totalNumber);
            //小计
            model.addAttribute("total_desc",subtotal);

            model.addAttribute("list",list);
            model.addAttribute("server",server);
            return "goods/cartList";
        }
        return "goods/cart";
    }

    /**
     * 更新购物车个数
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/cart/ajaxUpdateCart/{level}")
    public Map ajaxUpdateCart(@PathVariable Long level ,HttpServletRequest request){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
        //购物车记录数
        List<GoodCart> list= goodCartMapper.findAllByUserId(user.getUserId());
        double subtotal = 0.0;
        double totalNumber = 0;
        String  number = (String) request.getParameter("goods_number");
        String  recId = (String) request.getParameter("rec_id");
        GoodCart cart = goodCartMapper.findByRecId(Long.parseLong(recId));
        //设置购物车数量
        cart.setGoodsNumber(Integer.parseInt(number));
        goodCartMapper.save(cart);
        Map map = new HashMap();
        if(list != null && list.size() >0){
            for(GoodCart goodCart : list){
                GoodInfo goodInfo = goodInfoMapper.findByGoodId(goodCart.getGoodsId());
                //重新计算价格
                if(level == 1){
//                    0公司，1花冠，2花朵，3花瓣，4花蕾，5花芽，6花粉
                    goodCart.setGoodsPrice(goodInfo.getaPrice());
                }else if(level == 2){
                    goodCart.setGoodsPrice(goodInfo.getbPrice());
                }else if(level == 3){
                    goodCart.setGoodsPrice(goodInfo.getcPrice());
                }else if(level == 4){
                    goodCart.setGoodsPrice(goodInfo.getdPrice());
                }else if(level == 5){
                    goodCart.setGoodsPrice(goodInfo.getePrice());
                }else if(level == 6){
                    goodCart.setGoodsPrice(goodInfo.getfPrice());
                }
                double sum = HighPreciseComputor.mul(goodCart.getGoodsNumber(),goodCart.getGoodsPrice());
                subtotal = HighPreciseComputor.add(subtotal,sum);
                totalNumber =HighPreciseComputor.add(totalNumber,goodCart.getGoodsNumber());
            }
            //购物车总数量
            map.put("total_number",totalNumber);
            //小计
            map.put("total_desc",subtotal);
            map.put("error","0");
            return map;
        }
        map.put("message","购物车更新错误");
        return null;
    }

    /**
     * 购物车删除
     * @param id
     * @return
     */
    @RequestMapping("/cart/delete/{id}")
    @Transactional
    public String deleteCart(@PathVariable String id){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
        goodCartMapper.deleteByRecId(Long.parseLong(id));
        return "redirect:/";
    }

    /**
     * 打开扫描页面
     * @return
     */
    @GetMapping("/goods/barcode")
    public String barcode(){
        return "goods/barcode";
    }

    @PostMapping("/goods/barcode")
    public String barcodePost(String img){

        return "";
    }

    @GetMapping("/goods/totalMoney/{level}/{total}")
    @ResponseBody
    public int getTotalMoney(@PathVariable("level") int level,@PathVariable("total") Double total,@Value("${total.money}") String totalMoney){

        WapUser user = userMapper.findByUserId(getUserId());
        if(user.getStatus() != 2){
            return 1;
        }
        if(99 == level){
            return 3;
        }
        String[] totalMoneys = totalMoney.split(",");

        if(total < Double.parseDouble(totalMoneys[level])){
            return 0;
        }else{
            return 1;
        }
    }

}
