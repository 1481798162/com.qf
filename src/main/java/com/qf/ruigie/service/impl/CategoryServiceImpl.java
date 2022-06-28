package com.qf.ruigie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.ruigie.common.CustomException;
import com.qf.ruigie.mapper.CategoryMapper;
import com.qf.ruigie.pojo.Category;
import com.qf.ruigie.pojo.Dish;
import com.qf.ruigie.pojo.Setmeal;
import com.qf.ruigie.service.CategoryService;
import com.qf.ruigie.service.DishService;
import com.qf.ruigie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 金桑
 * @Date: 2022/06/09/10:57
 * @Description:
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    private SetmealService setmealService;

    /**
     * 根据id删除分类,删除之前需要进行判断
     * @param id
     * 有问题黑马46集
     */
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品,如果已经关联抛出一个异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper= new LambdaQueryWrapper<>();

        //添加查询条件,根据分类id查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        if(count1 >0) {
            //已经关联抛出一个异常
            throw new CustomException("当前分类下关联了菜品,不能删除");
        }

        //查询当前分类是否关联了套餐,如果已经关联抛出一个异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件,根据分类id查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = dishService.count(dishLambdaQueryWrapper);
        if (count2>0){
            //已经关联抛出一个异常
            throw new CustomException("当前分类下关联了套餐,不能删除");
        }
        //如果都没关联就删除
        super.removeById(id);
    }



}
