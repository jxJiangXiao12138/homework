package cn.itcast.mapper;

import cn.itcast.domain.LimitPage;
import cn.itcast.domain.User;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/7 13:19
 * @Description: 处理用户操作的持节层接口
 */
public interface UserMapper {


    /**
     * @Description: 查询总记录数
     * @return
     */
    int findTotalCount();

    /**
     * @Description: 查询每页展示的数据
     * @param page
     * @return
     */
    List<User> findPage(LimitPage page);

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 根据用户id查找用户
     * @param id
     * @return
     */
    User findUserById(int id);

    void updateUser(User user);

    void deleteUser(int id);

    int findTotalCounts(User user);

    User findUserByUsernameAndPassWord(User loginUser);
}
