package com.timi.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.timi.entity.Category;
import com.timi.service.CategoryService;
import com.timi.utils.AppHttpCodeEnum;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.utils.WebUtils;
import com.timi.vo.CategoryVo;
import com.timi.vo.ExcelCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }
    //自定义权限校验
    //@PreAuthorize("@ps.hasPermission('content:category:export')")
    @PreAuthorize("hasAuthority('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categorys= categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos= BeanCopyUtils.copyBeanList(categorys,ExcelCategoryVo.class);
            //数据写入到excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        }catch (Exception e){
            //如果出现异常也要响应json数据
            ResponseResult result=ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String name,String status){
        return categoryService.list(pageNum,pageSize,name,status);
    }
    @PostMapping("")
    public ResponseResult addCategory(@RequestBody CategoryVo categoryVo){
        return categoryService.addCategory(categoryVo);
    }
    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable("id")Long id){
        return categoryService.getCategory(id);
    }
    @PutMapping("")
    public ResponseResult updateCategory(@RequestBody CategoryVo categoryVo){
        return categoryService.updateCategory(categoryVo);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id")Long id){
        return categoryService.deleteCategory(id);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody CategoryVo categoryVo){
        return categoryService.changeCategory(categoryVo);
    }
}
