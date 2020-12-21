package net.zhongli.tech.luwu.admin.core.security;

import net.zhongli.tech.luwu.admin.common.enums.ServiceErrorEnum;
import net.zhongli.tech.luwu.admin.common.exception.ServiceException;
import net.zhongli.tech.luwu.admin.common.utils.HttpUtils;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author: OZY
 * @createTime: 2019-11-16 13:53
 * @description: session被踢出处理
 * @version: 1.0.0
 */
@Component
public class SessionInformationExpiredStrategyImpl implements SessionInformationExpiredStrategy {


    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {

        if(HttpUtils.isAjaxRequest(event.getRequest())) {
            // ajax请求
            throw new ServiceException(ServiceErrorEnum.SYSTEM_SESSION_KICK);

        } else {
            // 非ajax请求
            event.getRequest()
                    .getRequestDispatcher("/error/kickout")
                    .forward(event.getRequest(), event.getResponse());
        }

    }

}
