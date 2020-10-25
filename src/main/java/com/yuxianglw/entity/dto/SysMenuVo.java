package com.yuxianglw.entity.dto;

import com.yuxianglw.entity.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class SysMenuVo extends SysMenu {

    private List<SysMenu> Childs;

}
