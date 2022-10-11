package com.wjy.dto;


import com.wjy.pojo.Setmeal;
import com.wjy.pojo.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
