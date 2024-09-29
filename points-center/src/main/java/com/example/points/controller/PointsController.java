package com.example.points.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.points.entity.Points;
import com.example.points.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zlx
 * @since 2021-06-04
 */
@RestController
@RequestMapping("/points")
public class PointsController {
    @Autowired
    private PointsService pointsService;

    @GetMapping("/{orderId}")
    public Points getPointsByOrderId(@PathVariable String orderId) {
        QueryWrapper<Points> pointsQueryWrapper = new QueryWrapper<>();
        pointsQueryWrapper.lambda().eq(Points::getOrderId,orderId);
        return pointsService.getOne(pointsQueryWrapper);
    }
}

