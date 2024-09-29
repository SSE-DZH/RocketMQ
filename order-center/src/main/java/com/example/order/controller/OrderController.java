package com.example.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.framework.domain.Result;
import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zlx
 * @since 2021-06-04
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 新增订单
     * zlx
     * 12:24 2021/6/4
     * @param order 订单类型
     * @return com.example.framework.domain.Result
     **/
    @PostMapping
    public Result addOrder(@RequestBody Order order) {
        return Result.success(orderService.addOder(order));
    }

    /**
     * 根据用户id查询订单列表
     * zlx
     * 12:24 2021/6/4
     * @param userId 用户id
     * @return com.example.framework.domain.Result
     **/
    @GetMapping("/{userId}")
    public Result getOrders(@PathVariable Integer userId) {
        QueryWrapper<Order> pointsQueryWrapper = new QueryWrapper<>();
        pointsQueryWrapper.lambda().eq(Order::getUserId,userId);
        return Result.success(orderService.getBaseMapper().selectList(pointsQueryWrapper));
    }
}

