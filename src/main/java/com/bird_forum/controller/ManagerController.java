package com.bird_forum.controller;

import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.ManagerDTO;
import com.bird_forum.domain.query.ManagerQuery;
import com.bird_forum.domain.vo.ManagerVO;
import com.bird_forum.service.IManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/manager")
@Tag(name = "管理员模块")
@Slf4j
public class ManagerController {

    @Resource
    private IManagerService iManagerService;

    @GetMapping("/list")
    @Operation(summary = "分页查询管理员", method = "GET")
    public ResponseData<List<ManagerVO>> list(ManagerQuery query) {
        log.info("分页查询管理员: {}", query);
        return ResponseData.success(iManagerService.listManagers(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询管理员", method = "GET")
    public ResponseData<ManagerVO> getById(@PathVariable Long id) {
        log.info("查询管理员: {}", id);
        return ResponseData.success(iManagerService.getManagerById(id));
    }

    @PostMapping
    @Operation(summary = "新增管理员", method = "POST")
    public ResponseData add(@RequestBody ManagerDTO dto) {
        log.info("新增管理员: {}", dto);
        return iManagerService.addManager(dto) ? ResponseData.success() : ResponseData.error();
    }

    @PutMapping
    @Operation(summary = "修改管理员", method = "PUT")
    public ResponseData update(@RequestBody ManagerDTO dto) {
        log.info("修改管理员: {}", dto);
        return iManagerService.updateManager(dto) ? ResponseData.success() : ResponseData.error();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除管理员", method = "DELETE")
    public ResponseData delete(@PathVariable Long id) {
        log.info("删除管理员: {}", id);
        return iManagerService.deleteManager(id) ? ResponseData.success() : ResponseData.error();
    }
}
