package com.example.points.service.impl;

import com.example.points.entity.Points;
import com.example.points.mapper.PointsMapper;
import com.example.points.service.PointsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zlx
 * @since 2021-06-04
 */
@Service
public class PointsServiceImpl extends ServiceImpl<PointsMapper, Points> implements PointsService {

}
