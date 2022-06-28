package com.qf.ruigie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qf.ruigie.pojo.Category;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 金桑
 * @Date: 2022/06/09/10:56
 * @Description:
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);

}
