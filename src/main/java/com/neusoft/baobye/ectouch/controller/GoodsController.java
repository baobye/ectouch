package com.neusoft.baobye.ectouch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.baobye.ectouch.entity.*;
import com.neusoft.baobye.ectouch.exception.EcException;
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

        List<GoodCart> list= goodCartMapper.findAllByUserId(getUserId());
        model.addAttribute("total_number",0);
        GoodInfo goodInfo = goodInfoMapper.findByGoodId(id);
        model.addAttribute("goodInfo",goodInfo);
        model.addAttribute("user",getUser());
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
        String goods = request.getParameter("goods");

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

            //先判断这个商品购物车中有没有

            GoodCart goodCart = goodCartMapper.findByUserIdAndGoodsId(getUserId(),(long)goodId);
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
                cart.setUserId(getUserId());
                cart.setPath(path);
                if(getUser().getStatus() == 2){
                    cart.setGoodsPrice(goodInfo.getaPrice());
                }
                Long level = getUser().getLevel() < 6  ? getUser().getLevel() : Long.parseLong((String) map.get("level"));
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
                Long level = getUser().getLevel() < 6  ? getUser().getLevel() : Long.parseLong((String) map.get("level"));
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
            List<GoodCart> carts= goodCartMapper.findAllByUserId(getUserId());
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
    @RequestMapping(value = {"/goods/checkout/{level}","/goods/checkout"})
    public String checkout(@PathVariable(required = false) Integer level ,Model model, @Value("${total.money}") String totalMoney,@Value("${replenishment.money}")  String replenishment){
        if(level == null){
            level = getUser().getLevel();
        }
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
        if(goodCartList.isEmpty()){
            return "redirect:/goods/index";
        }
        /**
         * 根据会员购买金额 和 购买数量重新算购物车内 商品的价格
         */
        if(goodCartList != null && goodCartList.size() >0){
            double subtotal = 0.0;
            double totalNumber = 0;

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


                //第一次购物 从数据库读的ｌｅｖｅｌ
                if(99 == user.getLevel() ){
                    String[] totalMoneys = totalMoney.split(",");
                    if(subtotal < Double.parseDouble(totalMoneys[level-1])){
                        throw new EcException("购买金额未达到要求","购买金额未达到"+Double.parseDouble(totalMoneys[level-1]),"/cart");
                    }
                }else{
                    String[] replenishments = replenishment.split(",");
                    if(subtotal < Double.parseDouble(replenishments[level-1])){
                        throw new EcException("购买金额未达到要求","购买金额未达到要求"+Double.parseDouble(replenishments[level-1]),"/cart");
                    }
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
    public String done(OrderInfo orderInfo, Model model,String listString,@Value("${total.money}") String totalMoney ,@Value("${replenishment.money}")  String replenishment) throws Exception {
        //session 中　用户选择的
        int level = getUser().getLevel();
        //用户信息
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);

        Double taotal = orderInfo.getOrderPriceTotal();
        //第一次购物 从数据库读的ｌｅｖｅｌ
        if(99 == user.getLevel() ){
            String[] totalMoneys = totalMoney.split(",");
            if(taotal < Double.parseDouble(totalMoneys[level-1])){
                throw new EcException("购买金额未达到要求","购买金额未达到"+Double.parseDouble(totalMoneys[level-1]),"/cart");
            }
        }else{
            String[] replenishments = replenishment.split(",");
            if(taotal < Double.parseDouble(replenishments[level-1])){
                throw new EcException("购买金额未达到要求","购买金额未达到要求"+Double.parseDouble(replenishments[level-1]),"/cart");
            }
        }

        if(user.getDzb() < orderInfo.getOrderPriceTotal()){
            throw new EcException("点击充值","电子币不足请充值","/money/recharge");
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
                orderMiddle.setUserLevel(level);
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
    @RequestMapping(value = {"/cart/{level}","/cart"})
    public String cart(@PathVariable(required = false) Integer level ,@Value("${hhmg.server}") String server,Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(level == null){
            level = getUser().getLevel();
        }
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
    public int getTotalMoney(@PathVariable("level") int level,@PathVariable("total") Double total,@Value("${total.money}") String totalMoney,@Value("${replenishment.money}")  String replenishment){

        WapUser user = userMapper.findByUserId(getUserId());
        //第二次购物
        if(user.getLevel() < 6){
            String[] replenishments = replenishment.split(",");
            if(total < Double.parseDouble(replenishments[level-1])){
                return 0;
            }else{
                return 1;
            }
        }
        if(99 == level){
            return 3;
        }
        String[] totalMoneys = totalMoney.split(",");
        if(total < Double.parseDouble(totalMoneys[level-1])){
            return 0;
        }else{
            return 1;
        }
    }

}
