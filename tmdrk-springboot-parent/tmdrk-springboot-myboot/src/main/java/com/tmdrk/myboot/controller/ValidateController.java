package com.tmdrk.myboot.controller;

import com.tmdrk.myboot.dto.BargainActivityUpdateDTO;
import com.tmdrk.myboot.redis.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * ValidateController
 *
 * @author Jie.Zhou
 * @date 2020/12/9 13:42
 */
@RestController
@RequestMapping("/bargain-activity")
public class ValidateController {
    @PutMapping("{id}" )
    public Result<Void> update(@PathVariable("id" ) Long id, @Validated @RequestBody BargainActivityUpdateDTO bargainActivityUpdateDTO ){
        System.out.println(bargainActivityUpdateDTO.toString());
        return Result.success();
    }
}
