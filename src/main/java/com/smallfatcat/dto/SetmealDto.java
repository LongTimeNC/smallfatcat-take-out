package com.smallfatcat.dto;

import com.smallfatcat.entity.Setmeal;
import com.smallfatcat.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

}
