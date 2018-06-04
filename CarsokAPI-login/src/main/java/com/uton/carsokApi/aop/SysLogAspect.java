package com.uton.carsokApi.aop;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * Created by SEELE on 2018/2/26.
 */
@Aspect
@Component
public class SysLogAspect {

    private final static Logger logger = Logger.getLogger(SysLogAspect.class);

    //@Pointcut("execution(public * *(..))")
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void cutController() {}

    @Around("cutController()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        Object result = new Object();

        try {
            String strMethodName = point.getSignature().getName();
            String strClassName = point.getTarget().getClass().getName();
            Object[] params = point.getArgs();

            //Header输出
/*            StringBuffer bfParams = new StringBuffer();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Enumeration enu=request.getHeaderNames();//取得全部头信息
            while(enu.hasMoreElements()) {//以此取出头信息
                String headerName = (String) enu.nextElement();
                String headerValue = request.getHeader(headerName);//取出头信息内容
                bfParams.append(headerName).append(":").append(headerValue).append(";");
            }
            logger.info("Header:"+bfParams.toString());*/

            String paramStr="";
            if (params != null && params.length > 0) {
                for(int i=0;i<params.length;i++)
                {
                    try
                    {
                        //只转换carsokApi的类
                        if(params[i].getClass().getName().indexOf("com.uton.carsokApi")==-1)
                        {
                            continue;
                        }
                        paramStr+=JSON.toJSONString(params[i]);
                    }
                    catch (Exception e)
                    {

                    }
                    finally {
                        //
                    }
                }
            }

            long startTime = System.currentTimeMillis();
            result = point.proceed();
            long endTime = System.currentTimeMillis();

            long diffTime = endTime-startTime;

            String strMessage = String
                    .format("%s|%s|%s ms|参数:%s|返回值:%s", strClassName, strMethodName,String.valueOf(diffTime), paramStr, JSON.toJSONString(result));
            logger.warn(strMessage);

        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        return result;
    }

    private boolean isWriteLog(String method) {
        String[] pattern = {};
        for (String s : pattern) {
            if (method.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
    }


}
