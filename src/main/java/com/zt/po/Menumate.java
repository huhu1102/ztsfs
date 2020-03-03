/**
 *
 */

package com.zt.po;

import javax.persistence.*;
import java.util.List;

/**
 * @author whl
 * @date 2019年4月16日
 *  权限分配的与模块相关按钮
 */
@Entity
@Table(name = "zt_menumate")
@org.hibernate.annotations.Table(appliesTo = "zt_menumate", comment = "前端组件button")
public class Menumate extends BasePo {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TINYINT(1) COMMENT '是否有权限.'")
    private boolean requireAuth;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "zt_mate_menu", joinColumns = {@JoinColumn(name = "mete_id")}
            , inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private List<Menu> menu;
    /**
     * 是否删除
     */
    private boolean  enable;
    /**
     * 功能按钮function名称
     */
    private String code;
    /**
     * 功能按钮显示文字
     *
     */
    private String btnText;
    /**
     *  功能按钮 图标
     */
    private String btnIcon;


    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the requireAuth
     */
    public boolean isRequireAuth() {
        return requireAuth;
    }

    /**
     * @param requireAuth the requireAuth to set
     */
    public void setRequireAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public String getBtnIcon() {
        return btnIcon;
    }

    public void setBtnIcon(String btnIcon) {
        this.btnIcon = btnIcon;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
