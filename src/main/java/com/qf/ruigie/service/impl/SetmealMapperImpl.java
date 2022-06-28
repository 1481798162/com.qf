package com.qf.ruigie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.ruigie.mapper.SetmealMapper;
import com.qf.ruigie.pojo.Setmeal;
import com.qf.ruigie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 金桑
 * @Date: 2022/06/15/15:17
 * @Description:
 */
@Mapper
@Slf4j
public class SetmealMapperImpl extends ServiceImpl<SetmealMapper,Setmeal>implements SetmealService {
}
